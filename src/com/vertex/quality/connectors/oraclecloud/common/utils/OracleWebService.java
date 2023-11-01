package com.vertex.quality.connectors.oraclecloud.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.components.SoapRequest;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;
import static io.restassured.RestAssured.given;

import static org.testng.Assert.fail;

/**
 * Parent representation for Oracle ERP Cloud web services. This class enables
 * the building of an Oracle request and endpoint, and sending the request.
 *
 * Jira work tracking: COERPC-3431
 *
 * @author msalomone
 */
public class OracleWebService extends OracleUtilities {

	private OracleSettings settings = OracleSettings.getOracleSettingsInstance();
	private static OracleProperties properties = OracleProperties.getOraclePropertiesInstance();

	/**
	 * Send a REST request to the Oracle ERP system.
	 * Default url is https://ecog-dev1.fa.us2.oraclecloud.com
	 *
	 * @param operationName  Name of Oracle job to schedule. Example: importBulkData
	 * @param zipFileName    Name of zip file from resources folder. Zip must contain a
	 *                       csv with test data.
	 * @param oracleFileName Name of zip file from Oracle cloud.
	 * @param jobName        Orale ERP path to enterprise job. Example:
	 *                       "/oracle/apps/ess/financials/receivables/transactions/autoInvoices,AutoInvoiceImportEss"
	 * @param jobType        The area of testing that relates to the job ran. Valid inputs are:
	 *                       "AR","AP", and "CA_BU_AP".
	 * @param businessUnit   Collection of taxation rules used by Oracle ERP. String literals for each
	 *                       business unit such as "VTX_US_BU" and "VTX_CA_BU" are acceptable, as well
	 *                       as their Oracle codes 300000005503393 and 300000006755657 respectively.
	 * @param batchId        The ID listed in PO and Requisition templates used in the upload process.
	 */
	public void sendRestRequest(String operationName, String zipFileName, String oracleFileName, String jobName,
								String jobType, String businessUnit, String trxSource, String extractMode,
								String batchId) {
		try {
			String ledger = "";
			String source = "External";

			if (!trxSource.equals("")) {
				source = trxSource;
			}

			if (businessUnit.equals("VTX_US_BU")) {
				businessUnit = "300000005503393"; //arguement1 Import AutoInvoice
				ledger = "300000001139008";
			} else if (businessUnit.equals("VTX_CA_BU")) {
				businessUnit = "300000006755657";
				ledger = "300000001139009";
			} else if(businessUnit.equals("VTX_IN_BU")) {
				businessUnit = "300000019543207";
				ledger = "300000019533269";
			} else if(businessUnit.equals("VTX_BR_BU")) {
				businessUnit = "300000005648568";
				ledger = "300000004084869";
			} else {
				VertexLogger.log("Invalid business unit supplied by user.", VertexLogLevel.WARN);
			}

			String today = getTodaysDate("yyyy-MM-dd");
			String params = "";
			String jsonRequest = "";
			String jobPackageName = "";

			if (jobType.equals("AR_AutoInvoice")) {
				params = businessUnit + "," + source + "," + today + ",#NULL,#NULL,#NULL,#NULL,#NULL," +
						today + "," + today +
						",#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,#NULL,Y,#NULL";
			} else if (jobType.equals("AP_PayablesInvoices") && zipFileName.equals("apinvoiceimport_1000Lines.zip")) {
				params = "#NULL," + businessUnit + ",N,#NULL,#NULL,#NULL,1000," + source + "," + transactionNumber + "A," +
						"N,N," + ledger + ",#NULL,1";
			} else if (jobType.equals("AP_PayablesInvoices")) {
				params = "#NULL," + businessUnit + ",N,#NULL,#NULL,#NULL,1000," + source + "," + transactionNumber + "000A," +
						"N,N," + ledger + ",#NULL,1";
			} else if (jobType.equals("AP_ValidateInvoices")) {
				params = businessUnit + ",All," + today + "," + today + ",#NULL,#NULL,#NULL," +
						"#NULL,#NULL," + ledger + ",#NULL,1,#NULL,#NULL,#NULL,1,#NULL";
			} else if (jobType.equals("PartnerDataExtract")) {
				params = "200," + businessUnit + "," + today + "," + today + ",#NULL,#NULL";
			} else if (jobType.equals("OM_importSalesOrder")) {
				params = transactionNumber + "A," + "#NULL,#NULL,#NULL,#NULL,#NULL,Y";
			} else if (jobType.equals("REQ_importRequisitions")) {
				params = "VTXTEST," + batchId + ",2500," + businessUnit + ",BUYER,#NULL,Y,ALL";
			} else if (jobType.equals("PO_importOrder")) {
				params = businessUnit + ",20200602006,SUBMIT," + businessUnit + "," + batchId + ",N,VTXTEST,Y";
			} else if (jobType.equals("TPRSE_report")) {
				params = businessUnit + "," + extractMode + "," + today + "," + today;
			} else {
				VertexLogger.log("No valid job type specified.", VertexLogLevel.WARN);
			}

			URL oracleEndpoint = new URL(settings.URL);

			if (operationName.equals("importBulkData")) {
				Path zippedCsvPath = this.getResourcePath("csv");
				zippedCsvPath = Paths.get(zippedCsvPath.toString() + "/" + zipFileName);
				String resourceFile = zippedCsvPath.toString();
				String fileContent = this.encodeZip64(resourceFile);

				jsonRequest = "{\n" +
						"\"OperationName\":\"" + operationName + "\",\n" + //importBulkData
						"\"DocumentContent\":\"" + fileContent + "\",\n" +
						"\"ContentType\":\"zip\",\n" +
						"\"FileName\":\"" + oracleFileName + "\",\n" +
						"\"JobName\":\"" + jobName + "\",\n" +
						"\"ParameterList\":\"" + params + "\"\n" +
						"}";
			} else if (operationName.equals("submitESSJobRequest")) {
				String[] jobDetails = jobName.split(",");
				jobPackageName = jobDetails[0];
				jobName = jobDetails[1];

				jsonRequest = "{\n" +
						"\"OperationName\":\"" + operationName + "\",\n" + //submitESSJobRequest
						"\"JobPackageName\":\"" + jobPackageName + "\",\n" +
						"\"JobDefName\":\"" + jobName + "\",\n" +
						"\"ESSParameters\":\"" + params + "\",\n" +
						"\"ReqstId\":null\n" +
						"}";
			} else if (operationName.equals("salesOrderRequest")) {
				oracleEndpoint = new URL("https://ecog-dev1.fa.us2.oraclecloud.com/" +
						"fscmRestApi/resources/11.13.18.05/salesOrdersForOrderHub?" +
						"onlyData=true&q=SourceTransactionNumber LIKE " + transactionNumber +
						"%&fields=SourceTransactionNumber;" + "totals:TotalCode,TotalAmount");
				jsonRequest = "";
			} else {
				VertexLogger.log("Invalid Operation Name supplied.", VertexLogLevel.WARN);
			}
			Response response;

			if (operationName.equals("salesOrderRequest")) {
				response = given().auth().basic(settings.username, settings.password).contentType("application/json")
						.body(jsonRequest).get(oracleEndpoint);
				response.then().statusCode(200);
			} else {
				response = given().auth().basic(settings.username, settings.password).
						contentType("application/json").body(jsonRequest).post(oracleEndpoint);
				VertexLogger.log(jsonRequest, VertexLogLevel.INFO);
				response.then().statusCode(201);
			}

			VertexLogger.log("ERP operation successfully started.", VertexLogLevel.INFO);

		} catch (MalformedURLException mue) {
			VertexLogger.log("REST endpoint is not formatted correctly. " +
					"Please verify the endpoint works in a browser, or contact an " +
					"automation engineer.", VertexLogLevel.ERROR);
			mue.printStackTrace();
			fail("Test failed due to malformed REST endpoint.");
		} catch (IOException io) {
			VertexLogger.log("There was an issue reading the csv from the " +
					"zip file path provided. Please double-check the path.", VertexLogLevel.ERROR);
			io.printStackTrace();
			fail("Test failed due to missing csv when zipping.");
		}
	}

	/**
	 * Send a SOAP request to the Oracle ERP system.
	 * Default url is https://ecog-dev1.fa.us2.oraclecloud.com
	 *
	 * @param xmlFileName - Name of xml file. File must be located in ConnectorQuality/resources/xmlfiles
	 * @return Response received from endpoint.
	 */
	public String sendSoapRequest(String xmlFileName, String financialType) {
		String theResponse = "";
		try {
			Path resourcePath = this.getResourcePath("xml");
			resourcePath = Paths.get(resourcePath.toString() + "/" + xmlFileName);
			String resourceFile = resourcePath.toString();

			String financial = "222"; //For AR

			if (financialType.equals("AP")) {
				financial = "200";
			}

			File xml = new File(resourceFile);
			if (financialType.equals("OM"))
				updateTagValues(xml, "ns2:item", "OM");
			else if (financialType.equals("REQUISITION"))
				updateTagValues(xml, "ns2:item", "REQUISITION");
			else if (financialType.equals("PO"))
				updateTagValues(xml, "ns2:item", "PO");
			else if (financialType.equals("OM_OrderNum"))
				updateTagValues(xml, "ns2:item", "OM_OrderNum");
			else
				updateTagValues(xml, "ns2:item", financial);

			VertexLogger.log("Reading data from " + resourceFile);

			byte[] encoded = Files.readAllBytes(Paths.get(resourceFile));
			String soapXml = new String(encoded, StandardCharsets.UTF_8);

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			URL endpoint = new URL(settings.environment + ":443/xmlpserver/services/ExternalReportWSSService");
			MimeHeaders headers = new MimeHeaders();
			SOAPConnection connection = soapConnectionFactory.createConnection();
			MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			SOAPMessage message = factory.createMessage(headers, new ByteArrayInputStream(encoded));
			AttachmentPart attachment = message.createAttachmentPart();
			attachment.setContent("sm_content", "text/plain");
			attachment.setContentId("1.9f910338bf0cac0e783bfdec7e53be9237684caa8f8f4e6d@apache.org");
			message.addAttachmentPart(attachment);

			message = this.addAuthHeader(message);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			SOAPMessage response = connection.call(message, endpoint);
			response.writeTo(out);
			String outMsg = new String(out.toByteArray());
			message.writeTo(out);

			VertexLogger.log("Response: \n" + outMsg, VertexLogLevel.INFO);

			theResponse = outMsg;

		} catch (IOException io) {
			VertexLogger.log("There was an issue reading data from the " +
					"file path provided. Please double-check the path.", VertexLogLevel.ERROR);
			io.printStackTrace();
			fail("Test failed due to IOException.");
		} catch (SOAPException se) {
			VertexLogger.log("There was an issue with the SOAP request. " +
					"Please verify the request content in the supplied xml file.", VertexLogLevel.ERROR);
			se.printStackTrace();
			fail("Test failed due to SOAPException");
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			fail("Test failed after calling updateTagValues in sendSoapRequest.");
		}
		return theResponse;
	}

	/**
	 * Send getDocumentsByPrefix SOAP request to the Oracle ERP system.
	 * Default url is https://ecog-dev1.fa.us2.oraclecloud.com
	 *
	 * @param xmlFileName - Name of xml file. File must be located in ConnectorQuality/resources/xmlfiles
	 * @return Response received from endpoint.
	 */
	public String sendGetDocByPrefixSoapRequest(String xmlFileName){
		String returnedResponse = "";
		try {
			Path resourcePath = this.getResourcePath("xml");
			resourcePath = Paths.get(resourcePath.toString() + "/" + xmlFileName);
			String resourceFile = resourcePath.toString();


			File xml = new File(resourceFile);

			SoapRequest request = new SoapRequest();
			byte[] encoded = Files.readAllBytes(Paths.get(resourceFile));
			request.createSoapRequest(encoded);
			request.updateResponseAsAttachmentContent();

			returnedResponse = request.response;

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return returnedResponse;
	}

	/**
	 * @param message XML request read from file without auth header.
	 * @return message  A SOAPMessage containing auth components in the header.
	 */
	private SOAPMessage addAuthHeader(SOAPMessage message) {
		try {
			SOAPPart soappart = message.getSOAPPart();
			SOAPEnvelope envelope = soappart.getEnvelope();
			SOAPHeader header = envelope.getHeader();
			WSSecHeader wsheader = new WSSecHeader(soappart);
			wsheader.insertSecurityHeader();

			WSSecTimestamp timeStamp = new WSSecTimestamp(wsheader);
			timeStamp.getWsTimeSource();
			timeStamp.build();

			WSSecUsernameToken token = new WSSecUsernameToken(wsheader);
			token.setPasswordType(WSConstants.PASSWORD_TEXT);
			token.setUserInfo(settings.username, settings.password);
			token.addNonce();
			token.addCreated();
			token.build();

		} catch (SOAPException se) {
			VertexLogger.log("There was an issue adding the authorization " +
					"header to the SOAP request. Please check the xml file being used, " +
					"or ask an automation engineer for assistance.", VertexLogLevel.ERROR);
			se.printStackTrace();
			fail("Test failed when adding auth header to SOAP request.");
		} catch (WSSecurityException wse) {
			VertexLogger.log("WS Security error. Please refer to stack trace below", VertexLogLevel.ERROR);
			wse.printStackTrace();
			fail("Test failed due to invalid authorization in SOAP request.");
		}
		return message;
	}

	/**
	 * @param zipPath Path to file contained in the resources directory of
	 *                the connector-quality-java framework.
	 * @return fileContent An array of bytes encoded using 64bit encoding.
	 * @throws IOException
	 */
	private String encodeZip64(String zipPath) throws IOException {
		File aFile = new File(zipPath);
		byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(aFile));
		String fileContent = new String(encoded, StandardCharsets.UTF_8);
		return fileContent;
	}

	/**
	 * Update provided tags in xml file.
	 *
	 * @param xmlFile File containing tags to update.
	 * @param tagName The name of the tag that the content of which will be updated.
	 */
	private void updateTagValues(File xmlFile, String tagName, String financial)
			throws IOException, SAXException, ParserConfigurationException, TransformerException {

		String txNumPattern = "^[A-Z]*[0-9][0-9][0-9][0-9][0-9][0-9][\\S]";
		String datePattern = "^[0-9][0-9][-][0-9][0-9][-][0-9][0-9][0-9][0-9]";
		String today = getTodaysDate("MM-dd-yyyy");
		String txNum = transactionNumber + "%";
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document xml = domBuilder.parse(xmlFile);

		VertexLogger.log("Updating tags in " + xmlFile.toString());

		NodeList nodes = xml.getElementsByTagName(tagName);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node itemNode = nodes.item(i);
			Node currChild = itemNode.getFirstChild();
			if (Pattern.matches(txNumPattern, currChild.getTextContent()) || currChild.getTextContent().equals("null%")
					|| currChild.getTextContent().contains("AUTOTST")) {
				currChild.setTextContent(txNum);
			}
			if (currChild.getTextContent().equals("222") || currChild.getTextContent().equals("200"))
				currChild.setTextContent(financial);
			if (Pattern.matches(datePattern, currChild.getTextContent()))
				currChild.setTextContent(today);
			if (financial.equals("OM_OrderNum")) {
				if ((currChild.getTextContent().equals(" ")) ||
						currChild.getTextContent().equals("[0-9][0-9][0-9][0-9][\\S]*"))
					currChild.setTextContent(properties.getOmOrderNum());
				if (Pattern.matches(txNumPattern, currChild.getTextContent()) || currChild.getTextContent().equals("null%")
						|| currChild.getTextContent().contains("AUTOTST")) {
					currChild.setTextContent("  ");
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(xml),
					new StreamResult(xmlFile));
		}
		VertexLogger.log("Tags successfully update.");
	}
}