package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.fail;

/**
 * A containor for all the method for reading the soap Response
 *
 * @author Ajain
 */
public class TaxlinkApSoapResponse extends TaxlinkSoapApi {

	public Map<String, String> tagNameMap = new HashMap<String, String>();
	public Map<String, String> respValueMap = new HashMap<String, String>();
	protected TaxlinkAPIUtilities utilities = new TaxlinkAPIUtilities();
	int noOfExpectedLines = 1;
	int limit = 3;

	/**
	 * Input all request arguments.
	 * @param taxAmt for line 1
	 * @param taxRate for line 1
	 * @param lineAmt for line 1
	 * @param taxAmt for line 2
	 * @param taxRate for line 2
	 * @param lineAmt2 for line 2
	 */
	public void setTaxAmtTaxRateLineAmtArgs( String taxAmt, String taxRate, String lineAmt,String taxAmt2,String taxRate2, String lineAmt2)
	{
		limit = 3;
		tagNameMap.put("ns2:TaxAmt", taxAmt);
		tagNameMap.put("ns2:TaxRate", taxRate);
		tagNameMap.put("ns2:LineAmt", lineAmt);
	}

	/**
	 * Input all request arguments.
	 * @param lineAmt for line 1
	 * @param lineAmt for line 2
	 */
	public void setTwoLineAmtArgs( String lineAmt, String lineAmt1)
	{
		limit = 2;
		tagNameMap.put("ns2:LineAmt", lineAmt);
		tagNameMap.put("ns2:LineAmt", lineAmt1);
	}

	/**
	 * Input all request arguments.
	 * @param taxAmt
	 * @param taxRate
	 * @param lineAmt
	 */
	public void setTaxAmtTaxRateTaxLineArgs( String taxAmt, String taxRate, String lineAmt )
	{
		limit = 3;
		tagNameMap.put("ns2:TaxAmt", taxAmt);
		tagNameMap.put("ns2:TaxRate", taxRate);
		tagNameMap.put("ns2:LineAmt", lineAmt);

	}

	/** Get the response Xml file
	 * @param  xmlFileName
	 *
	 * @return xmlFile
	* */
	public File getRespXmlFile( String xmlFileName )
	{
		byte[] encoded = "".getBytes(StandardCharsets.UTF_8);
		File xmlFile = null;
		try
		{
			xmlFileName = xmlFileName + "-resp.xml";
			Path resourcePath = utilities.getResourcePath("xml");
			resourcePath = Paths.get(resourcePath.toString() + "/" + xmlFileName);
			String resourceFile = resourcePath.toString();

			xmlFile = new File(resourceFile); 

		}
		catch ( Exception ex )
		{
			VertexLogger.log("There was in issue in the getXml method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting XML tags.");
		}

		return xmlFile;
	}

	/**
	 * read tags values present in response xml
	 *
	 * @param xmlFile
	 */
	public void readTagsValues( File xmlFile)
		throws IOException, SAXException, ParserConfigurationException
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document xml = domBuilder.parse(xmlFile);

		Set<String> tagsSet = tagNameMap.keySet();
		String[] tags = new String[tagsSet.size()];

		// Convert the set of desired tags to an array.
		int j = 0;
		for ( String key : tagNameMap.keySet() )
		{
			tags[j++] = key;
		}

		VertexLogger.log("Updating tag values.");

		String currNodeName = "";
		for ( String currTag : tags )
		{
			VertexLogger.log("currTag:  "+currTag);
			NodeList nodes = xml.getElementsByTagName(currTag);
			for ( int i = 0 ; i < nodes.getLength() ; i++ )
			{
				Node itemNode = nodes.item(i);
				Node currChild = itemNode.getFirstChild();
				currNodeName = itemNode.getNodeName();
				if ( currNodeName.equals(currTag) )
				{
					respValueMap.put(currTag+i,itemNode.getTextContent());
				}
				if ( i == noOfExpectedLines ){
					break;
				}
			}
		}
		if ( respValueMap.size() < limit){
			fail("Expected tags are missing in the response xml");
		}
		else{
			VertexLogger.log("Tags values fetched successfully.");
		}
	}

	/**
	 * Compares line Amount values present in response with passed parameter
	 * @param lineAmt
	 * @param lineAmt1
	 *
	 * @return testPassedFlag
	 * */
	public boolean compareTwoLineAmtInResp( String lineAmt, String lineAmt1){
		boolean testPassedFlag = true;
		for ( int i = 0 ; i <= noOfExpectedLines ; i++ ){

			String lineAmtVal = respValueMap.get("ns2:LineAmt" + i );
			if( i != noOfExpectedLines )
			{
				if ( !lineAmtVal.equalsIgnoreCase(lineAmt) )
				{
					VertexLogger.log("LineAmt - Expected : " + lineAmt + " Actual : " + lineAmtVal,VertexLogLevel.ERROR);
					testPassedFlag = false;
				}
			}
			if( i == noOfExpectedLines )
			{
				if ( !lineAmtVal.equalsIgnoreCase(lineAmt1) )
				{
					VertexLogger.log("LineAmt - Expected : " + lineAmt1 + " Actual : " + lineAmtVal,VertexLogLevel.ERROR);
					testPassedFlag = false;
				}
			}
		}
		return testPassedFlag;
	}

	@Override
	protected void formEndpointUrl( ) throws MalformedURLException
	{

	}

	@Override
	protected void createMessage( final byte[] encodedXML ) throws SOAPException, IOException
	{

	}

	@Override
	protected void createAndAddAttachment( ) throws SOAPException
	{

	}

	/**
	 * read tags values present in response xml
	 *
	 * @param xmlFile
	 */
	public void readAllTagsValues( File xmlFile)
		throws IOException, SAXException, ParserConfigurationException
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document xml = domBuilder.parse(xmlFile);

		Set<String> tagsSet = tagNameMap.keySet();
		String[] tags = new String[tagsSet.size()];

		// Convert the set of desired tags to an array.
		int j = 0;
		for ( String key : tagNameMap.keySet() )
		{
			tags[j++] = key;
		}

		VertexLogger.log("Updating tag values.");

		String currNodeName = "";
		for ( String currTag : tags )
		{
			VertexLogger.log("currTag:  "+currTag);
			NodeList nodes = xml.getElementsByTagName(currTag);
			VertexLogger.log("No of Line Items in Response : " + nodes.getLength());
			for ( int i = 0 ; i < nodes.getLength() ; i++ )
			{
				Node itemNode = nodes.item(i);
				Node currChild = itemNode.getFirstChild();
				currNodeName = itemNode.getNodeName();
				if ( currNodeName.equals(currTag) )
				{
					respValueMap.put(currTag+i,itemNode.getTextContent());
				}
			}
		}
		if ( respValueMap.size() < limit){
			fail("Expected tags are missing in the response xml");
		}
		else{
			VertexLogger.log("Tags values fetched successfully.");
		}
	}
	/** Compares the values in response with passed parameter
	 *
	 * @param taxAmt
	 * @param taxRate
	 * @param lineAmt
	 *
	 *  @return testPassedFlag
	 * */
	public boolean compareValuesInResp( String taxAmt, String taxRate, String lineAmt ){
		boolean testPassedFlag = true;

			if ( !respValueMap.containsValue(taxAmt) )
			{
				VertexLogger.log("Expected value is not present in response", VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(taxAmt);

			if ( !respValueMap.containsValue(taxRate) )
			{
				VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(taxRate);

			if ( !respValueMap.containsValue(lineAmt) )
			{
				VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(lineAmt);

		return testPassedFlag;
	}

	/** Fetches the key from Map
	 * @param valueFromMap value to be looked into Map
	 * */
	public String getKeyFromValue( String valueFromMap )
	{
		String nodeName = null;
		for ( Map.Entry<String, String> entry : respValueMap.entrySet() )
		{
			if ( entry.getValue().equals(valueFromMap) )
			{
				nodeName = entry.getKey();
				break;
			}
		}
		VertexLogger.log("Key associated with "+ valueFromMap + " is "+ nodeName);
		return nodeName;
	}

	/** Removes the key-value from Map
	 * @param valueItem value to be removed from Map
	 * */
	public void removeFromMap( String valueItem )
	{
		String keyName = getKeyFromValue(valueItem);
		if ( keyName != null )
		{
			respValueMap.remove(keyName);
			VertexLogger.log("Removed " + keyName + " from Response Map");
		}
	}
}
