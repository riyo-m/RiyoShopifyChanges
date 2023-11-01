package com.vertex.quality.connectors.oraclecloud.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleWebService;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.testng.Assert.fail;

/**
 * Representation of an Oracle SOAP web service.
 * Jira work: COERPC-3497
 *
 * @author msalomone
 */
public class SoapWS extends OracleWebService {

	private String response;
	private String financialType;

	private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

	public SoapWS(String financialType) {
		this.financialType = financialType;
	}

	public SoapWS() {}

	/**
	 * Send SOAP request to get documents for file prefix.
	 *
	 * @param xmlFile XML filename stored in ConnectorQuality/resources/xmlfiles/oracle
	 */
	public void getDocumentsForFilePrefix(String xmlFile){
		String wsResponse = sendGetDocByPrefixSoapRequest(xmlFile);
		this.response = wsResponse;
	}

	/**
	 * Send SOAP request read from an xml file.
	 *
	 * @param xmlFile XML filename stored in ConnectorQuality/resources/xmlfiles/oracle
	 */
	public void runReport(String xmlFile) {
		String wsResponse = sendSoapRequest(xmlFile, financialType);
		this.response = wsResponse;
	}

	/**
	 * Send SOAP request read from an xml file.
	 *
	 * @param xmlFile XML filename stored in ConnectorQuality/resources/xmlfiles/oracle
	 */
	public void runReport(String xmlFile, String orderNum) {
		String wsResponse = sendSoapRequest(xmlFile, financialType);
		this.response = wsResponse;
	}

	/**
	 * Write xml response from server to a resource file.
	 * @param outputFileName Name of csv file to write response.
	 */
	public void writeCsvResponseToFile(String outputFileName) {
		try
		{
			outputFileName = outputFileName+".csv";
			File csvFile = getFile(outputFileName, "csv");
			FileWriter writer = new FileWriter(csvFile.getPath());
			writer.write(response);
			writer.close();

			int count = 0;
			for(int i= 0; i<response.length(); i++)
			{
				if(response.substring(i,i+16) == "variablePOwithGeo")
					count++;
			}
			if(count != 4)
				fail("New Attributes are not verified correctly");

			VertexLogger.log("Writing response to: "+csvFile.getPath());

		} catch( IOException ioe) {
			VertexLogger.log("There was an issue writing the xml response to a result file.",
					VertexLogLevel.ERROR);
			ioe.printStackTrace();
			fail("Test failed due to IOException when writing xml response to file.");
		}
	}

	/**
	 * Write xml response from server to a resource file.
	 * @param outputFileName Name of xml file to write response.
	 */
	public void writeXmlResponseToFile(String outputFileName) {
		try
		{
			outputFileName = outputFileName+".xml";
			File xmlFile = getFile(outputFileName, "xml");
			FileWriter writer = new FileWriter(xmlFile.getPath());
			writer.write(response);
			writer.close();

			VertexLogger.log("Writing response to: "+xmlFile.getPath());

		} catch( IOException ioe) {
			VertexLogger.log("There was an issue writing the xml response to a result file.",
				VertexLogLevel.ERROR);
			ioe.printStackTrace();
			fail("Test failed due to IOException when writing xml response to file.");
		}
	}

	/**
	 * Export encoded data from the Vertex tables in the Oracle application
	 * to a csv file. The file will contain all transaction data relevant to
	 * the test's transaction number.
	 *
	 * @param resultName Name of the result file, just the file name not the full path.
	 */
	public void createCsv(String resultName) {
		try
		{
			String data = "";
			resultName = resultName+".xml";
			File xmlFile = getFile(resultName, "xml");
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			Document xml = domBuilder.parse(xmlFile);

			VertexLogger.log("Retrieving encoded data from response.");

			NodeList nodes;

			nodes = xml.getElementsByTagName("reportBytes");
			if(nodes.getLength() == 0)
				nodes = xml.getElementsByTagName("ns2:reportBytes");

			for(int i = 0; i < nodes.getLength(); i++) {
				Node itemNode = nodes.item(i);
				data = itemNode.getTextContent();
			}

			byte[] encoding = data.getBytes();
			String csvData = decode64(encoding);

			String csvName = xmlFile.getName().replace(".xml", ".csv");
			File csv = getFile(csvName, "csv");
			FileWriter writer = new FileWriter(csv.getPath());
			writer.write(csvData);
			writer.close();

			VertexLogger.log("Resultant csv created.");

		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			fail("Test failed during creation of csv from xml response.");
		}
	}
}
