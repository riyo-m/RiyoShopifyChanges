package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.enums.OracleErpSoapEndpoints;
import com.vertex.quality.connectors.taxlink.api.enums.TaxlinkSoapEndpoints;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.fail;

/**
 * A container for all the method required for sending upload request to oracle ERP UCM server.
 *
 * @author ajain
 */
public class UploadToUCMRequest extends OracleSoapApi
{

	public Map<String, String> reqArgs = new HashMap<String, String>();
	public String resourceFile = null;

	/**
	 * Create Taxlink AP SOAP request.
	 *
	 * @param xmlFile XML file used for SOAP request.
	 */
	public void createRequest( String zipfileName, String xmlFile )
	{
		String encodedStr = null;
		try
		{
			initializeSoapComponents();
			formOracleEndpointUrl();
			File zip = getZipFile(zipfileName);
			encodedStr = getZipAsEncodedStr(zip);
			setContentAsReqArgs(encodedStr);
			createMessage(getXml(xmlFile));
			createAndAddAttachment();
			message = addAuthHeader(message);
		}
		catch ( SOAPException | IOException ex )
		{
			VertexLogger.log("There was an issue creating an SOAP request. Error: " + ex.getMessage(),
				VertexLogLevel.ERROR);
		}
	}

	/**
	 * Input all request arguments.
	 */
	public void setArgs( String fileName )
	{
		reqArgs.put("erp:FileName", fileName + ".zip"); // Adding .zip extenstion to FileName in uploadToUCM request
		reqArgs.put("erp:DocumentTitle", fileName);
		reqArgs.put("erp:Content", fileName);
	}

	/**
	 * Input content as request arguments.
	 *
	 * @param encodedContent string that needs to be passed
	 */
	public void setContentAsReqArgs( String encodedContent )
	{
		reqArgs.put("erp:Content", encodedContent);
	}

	/**
	 * Initialize and form the SOAP endpoint.
	 *
	 * @throws SOAPException
	 */
	@Override
	protected void formEndpointUrl( ) throws MalformedURLException
	{
		settings.setSoapEndpoint(TaxlinkSoapEndpoints.OERP_INTERGRATION);
		endpoint = new URL(settings.soap_endpointUrl);
	}

	protected void formOracleEndpointUrl( ) throws MalformedURLException
	{
		settings.setOracleSoapEndpoint(OracleErpSoapEndpoints.OERP_INTERGRATION);
		endpoint = new URL(settings.oerp_endpointUrl);
	}

	/**
	 * Creates the entire request message including the header.
	 *
	 * @param encodedXML Encoded String read from XML file.
	 *
	 * @throws SOAPException
	 * @throws IOException
	 */
	@Override
	protected void createMessage( byte[] encodedXML ) throws SOAPException, IOException
	{
		message = messageFactory.createMessage(headers, new ByteArrayInputStream(encodedXML));
	}

	/**
	 * Adds necessary attachments to message.
	 *
	 * @throws SOAPException
	 */
	@Override
	protected void createAndAddAttachment( ) throws SOAPException
	{
		attachment = message.createAttachmentPart();
		attachment.setContent("sm_content", "text/plain");
		attachment.setContentId("1.9f910338bf0cac0e783bfdec7e53be9237684caa8f8f4e6d@apache.org");
		message.addAttachmentPart(attachment);
	}

	/**
	 * Retrieves XML from specified folder location.
	 *
	 * @return xmlData - The file data as an array of bytes.
	 */
	private byte[] getXml( String xmlFileName )
	{
		byte[] encoded = "".getBytes(StandardCharsets.UTF_8);
		try
		{
			xmlFileName = xmlFileName + ".xml";
			Path resourcePath = utilities.getResourcePath("xml");
			resourcePath = Paths.get(resourcePath.toString() + "/" + xmlFileName);
			String resourceFile = resourcePath.toString();
			VertexLogger.log("Xml file Path: " + resourceFile);
			File xml = new File(resourceFile); // The xml as a File object.

			// Now update all necessary tags using updateTagsValues method below.

			updateTagsValues(xml); // Updates all necessary values in the xml File.
			encoded = Files.readAllBytes(Paths.get(resourceFile)); // Grabs updated File data.
		}
		catch ( Exception ex )
		{
			VertexLogger.log("There was in issue in the getXml method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML and updating tags.");
		}

		return encoded;
	}

	/**
	 * Update all necessary values unique to this XML
	 * request type (Ap Single requests).
	 *
	 * @return updatedXmlString
	 */
	private void updateTagsValues( File xmlFile )
		throws IOException, SAXException, ParserConfigurationException, TransformerException
	{

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document xml = domBuilder.parse(xmlFile);

		Set<String> tagsSet = reqArgs.keySet();
		String[] tags = new String[tagsSet.size()];

		// Convert the set of desired tags to an array.
		int j = 0;
		for ( String key : reqArgs.keySet() )
		{
			tags[j++] = key;
		}

		VertexLogger.log("Updating tag values.");

		String currNodeName = "";
		for ( String currTag : tags )
		{
			NodeList nodes = xml.getElementsByTagName(currTag);
			for ( int i = 0 ; i < nodes.getLength() ; i++ )
			{
				Node itemNode = nodes.item(i);
				Node currChild = itemNode.getFirstChild();
				currNodeName = itemNode.getNodeName();
				if ( currNodeName.equals(currTag) )
				{
					currChild.setTextContent(reqArgs.get(currTag));
				}
			}
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(xml), new StreamResult(xmlFile));

		VertexLogger.log("Tags successfully update.");
	}

	/**
	 * Retrieves Zip File from specified folder location.
	 *
	 * @return zip - The file data as an array of bytes.
	 */
	private File getZipFile( String zipFileName )
	{
		File zipFile = null;
		try
		{
			zipFileName = zipFileName + ".zip";
			Path resourcePath = utilities.getResourcePath("zip");
			resourcePath = Paths.get(resourcePath.toString() + "/" + zipFileName);
			resourceFile = resourcePath.toString();
			VertexLogger.log("resourceFile path: " + resourceFile);
			zipFile = new File(resourceFile); // The zip as a File object.
		}
		catch ( Exception ex )
		{
			VertexLogger.log("There was in issue in the getZip method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting ZIP and updating tags.");
		}

		return zipFile;
	}

	/**
	 * Converts the zip file attachment into base64 encoded string
	 *
	 * @param zipFile attachment zipFile
	 *
	 * @return encodedString base64Encoded string
	 */
	private String getZipAsEncodedStr( File zipFile ) throws IOException
	{
		String encodedString = null;
		FileInputStream fileInputStreamReader = null;
		byte[] bytes = null;

		try
		{
			fileInputStreamReader = new FileInputStream(zipFile);
			bytes = new byte[(int) zipFile.length()];
			fileInputStreamReader.read(bytes);
			encodedString = Base64.encodeBase64String(bytes);
		}
		catch ( IOException ioException )
		{
			VertexLogger.log("Base64 Encoding process failed " + ioException.getMessage());
		}
		finally
		{
			fileInputStreamReader.close();
		}

		return encodedString;
	}
}
