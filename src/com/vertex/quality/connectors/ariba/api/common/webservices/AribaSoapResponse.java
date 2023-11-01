package com.vertex.quality.connectors.ariba.api.common.webservices;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.testng.Assert.fail;

/**
 * A container for all the method for reading the soap Response
 *
 * @author vgosavi
 */

public class AribaSoapResponse {

	public Map<String, String> tagNameMap = new HashMap<String, String>();
	public Map<String, String> respValueMap = new HashMap<String, String>();
	protected AribaAPITestUtilities apiUtil=new AribaAPITestUtilities();

	int limit = 3;

	/**
	 * Input all request arguments.
	 * @param status for line 1
	 * @param TaxType for line 1
	 * @param TaxAmount for line 1
	 */
	public  void setStatusTaxTypeTaxAmountForSingleLine(String status, String TaxType, String TaxAmount)
	{
		tagNameMap.put("ns2:Success",status);
		tagNameMap.put("ns2:UniqueName", TaxType);
		tagNameMap.put("ns2:Amount",TaxAmount);
	}

	/**
	 * Input all request arguments.
	 * @param abatementPercent for line 1
	 * @param percent for line 1
	 * @param taxAmount for line 1
	 */
	public  void setAbatementPercentPercentTaxAmt(String abatementPercent, String percent, String taxAmount)
	{
		tagNameMap.put("ns2:AbatementPercent",abatementPercent);
		tagNameMap.put("ns2:Percent", percent);
		tagNameMap.put("ns2:Amount",taxAmount);
	}

	/**
	 * Input all request arguments.
	 * @param category for line 1
	 * @param taxType for line 1
	 * @param percent for line 1
	 * @param taxAmount for line 1
	 */
	public  void setCategoryTaxTypePercentTaxAmt(String category, String taxType, String percent, String taxAmount)
	{
		tagNameMap.put("ns2:Category",category);
		tagNameMap.put("ns2:UniqueName", taxType);
		tagNameMap.put("ns2:Percent",percent);
		tagNameMap.put("ns2:Amount",taxAmount);
	}

	/**
	 * Input all request arguments.
	 * @param category for line 1
	 * @param taxType for line 1
	 * @param taxAmt1 for line 1
	 * @param taxAmt2 for line 2
	 */
	public  void setTaxTypeTaxAmountForMultipleLine(String category, String taxType, String taxAmt1, String taxAmt2)
	{
		tagNameMap.put("ns2:Category",category);
		tagNameMap.put("ns2:UniqueName",taxType);
		tagNameMap.put("ns2:Amount", taxAmt1);
		tagNameMap.put("ns2:Amount",taxAmt2);
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
			Path resourcePath = apiUtil.getResourcePath("xml");
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
	 * @param taxType tax type to be compared
	 * @param taxAmount tax amount to be compared
	 *
	 *  @return testPassedFlag
	 * */
	public boolean compareValuesInResp(String taxType, String taxAmount ){
		boolean testPassedFlag = true;

			if ( !respValueMap.containsValue(taxType) )
			{
				VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(taxType);

			if ( !respValueMap.containsValue(taxAmount) )
			{
				VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(taxAmount);

		return testPassedFlag;
	}

	/** Compares the values in response with passed parameter
	 *
	 *
	 * @param category category to be compared
	 * @param taxType  tax Type to be compared
	 * @param percent percent to be compared
	 * @param taxAmt tax Amount to be compared
	 *
	 *  @return testPassedFlag
	 * */
	public boolean compareValuesInRespForCategory(String category, String taxType, String percent, String taxAmt ){
		boolean testPassedFlag = true;

		if ( !respValueMap.containsValue(category) )
			{
				VertexLogger.log("Expected value is not present in response", VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(category);

		if ( !respValueMap.containsValue(taxType) )
		{
			VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
			testPassedFlag = false;
		}
		removeFromMap(taxType);

		if ( !respValueMap.containsValue(percent) )
		{
			VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
			testPassedFlag = false;
		}
		removeFromMap(percent);

		if ( !respValueMap.containsValue(taxAmt) )
		{
			VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
			testPassedFlag = false;
		}
		removeFromMap(taxAmt);

		return testPassedFlag;
	}

	/** Compares the values in response with passed parameter
	 *
	 *
	 * @param abatementPercent to be compared
	 * @param percent to be compared
	 * @param taxAmt to be compared
	 *
	 *  @return testPassedFlag
	 * */
	public boolean compareValuesInResp2(String abatementPercent, String percent, String taxAmt )
	{
		boolean testPassedFlag = true;

		if ( !respValueMap.containsValue(abatementPercent) )
		{
			VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
			testPassedFlag = false;
		}
		removeFromMap(abatementPercent);

		if ( !respValueMap.containsValue(percent) )
		{
			VertexLogger.log("Expected value is not present in response",VertexLogLevel.ERROR);
			testPassedFlag = false;
		}
		removeFromMap(percent);

		if ( !respValueMap.containsValue(taxAmt) )
			{
				VertexLogger.log("Expected value is not present in response", VertexLogLevel.ERROR);
				testPassedFlag = false;
			}
			removeFromMap(taxAmt);

		return testPassedFlag;
	}

	/** Fetches the key from Map
	 *
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
	 *
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

	/**
	 * Write xml response from server to a resource file.
	 *
	 * @response response to be written
	 * @param outputFileName Name of xml file to write response.
	 */
	public void writeXmlResponseToFile(String response, String outputFileName) {
		try
		{
			outputFileName = outputFileName+"-resp.xml";
			File xmlFile = apiUtil.getFile(outputFileName, "xml");
			FileWriter writer = new FileWriter(xmlFile.getPath());
			writer.write(response);
			writer.close();

				VertexLogger.log("Writing response to: "+xmlFile.getPath());

		} catch(IOException ioe) {
				VertexLogger.log("There was an issue writing the xml response to a result file.",
						VertexLogLevel.ERROR);
			ioe.printStackTrace();
				fail("Test failed due to IOException when writing xml response to file. File name: "+outputFileName);
		}
	}
}