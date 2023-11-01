package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.enums.TaxlinkSoapEndpoints;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
 * An xml-based SOAP request for sending AP invoices.
 *
 * @author msalomone
 */
public class TaxlinkApSoapRequest extends TaxlinkSoapApi {

    public Map<String, String> reqArgs = new HashMap<String, String>();

    /**
     * Create Taxlink AP SOAP request.
     * @param xmlFile XML file used for SOAP request.
     */
    public void createRequest(String xmlFile) {
        try {
            initializeSoapComponents();
            formEndpointUrl();
            createMessage(getXml(xmlFile));
            createAndAddAttachment();
            message = addAuthHeader(message);
        }
        catch (SOAPException | IOException ex) {
            VertexLogger.log("There was an issue creating an AP SOAP request. Error: "+ex.getMessage(),
                    VertexLogLevel.ERROR);
        }
    }

    /**
     * Input all request arguments.
     */
    public void setArgs(String testName) {
        String date = getTodaysDate();
        reqArgs.put("typ:TrxId", getRandId());
        reqArgs.put("typ:TrxNumber", createTrxNumber(testName));
        reqArgs.put("typ:TrxDate", date);
        reqArgs.put("typ:TrxLineDate", date);
        reqArgs.put("typ:TrxLineGlDate", date);
    }

    /**
     * Initialize and form the SOAP endpoint.
     *
     * @throws SOAPException
     */
    @Override
    protected void formEndpointUrl() throws MalformedURLException {
        settings.setSoapEndpoint(TaxlinkSoapEndpoints.TAX_INTEGRATION);
        endpoint = new URL(settings.soap_endpointUrl);
    }

    /**
     * Creates the entire request message including the header.
     *
     * @param encodedXML Encoded String read from XML file.
     * @throws SOAPException
     * @throws IOException
     */
    @Override
    protected void createMessage(byte[] encodedXML) throws SOAPException, IOException {
        message = messageFactory.createMessage(headers, new ByteArrayInputStream(encodedXML));
    }

    /**
     * Adds necessary attachments to message.
     *
     * @throws SOAPException
     */
    @Override
    protected void createAndAddAttachment() throws SOAPException {
        attachment = message.createAttachmentPart();
        attachment.setContent("sm_content", "text/plain");
        attachment.setContentId("1.9f910338bf0cac0e783bfdec7e53be9237684caa8f8f4e6d@apache.org");
        message.addAttachmentPart(attachment);
    }

    /**
     * Retrieves XML from specified folder location.
     * @return xmlData - The file data as an array of bytes.
     */
    private byte[] getXml(String xmlFileName) {
        byte[] encoded = "".getBytes(StandardCharsets.UTF_8);
        try {
            xmlFileName = xmlFileName+".xml";
            Path resourcePath = utilities.getResourcePath("xml");
            resourcePath = Paths.get(resourcePath.toString() + "/" + xmlFileName);
            String resourceFile = resourcePath.toString();

            File xml = new File(resourceFile); // The xml as a File object.

            // Now update all necessary tags using updateTagsValues method below.

            updateTagsValues(xml); // Updates all necessary values in the xml File.
            encoded = Files.readAllBytes(Paths.get(resourceFile)); // Grabs updated File data.
        } catch(Exception ex) {
            VertexLogger.log("There was in issue in the getXml method. See printed stack trace " +
                    "for details.", VertexLogLevel.ERROR);
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
    private void updateTagsValues(File xmlFile) throws IOException, SAXException,
            ParserConfigurationException, TransformerException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document xml = domBuilder.parse(xmlFile);

        Set<String> tagsSet = reqArgs.keySet();
        String[] tags = new String[tagsSet.size()];

        // Convert the set of desired tags to an array.
        int j = 0;
        for(String key : reqArgs.keySet() ) {
            tags[j++] = key;
        }

        VertexLogger.log("Updating tag values.");

        String currNodeName = "";
        for (String currTag : tags) {
            NodeList nodes = xml.getElementsByTagName(currTag);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node itemNode = nodes.item(i);
                Node currChild = itemNode.getFirstChild();
                currNodeName = itemNode.getNodeName();
                if(currNodeName.equals(currTag)) {
                    currChild.setTextContent(reqArgs.get(currTag));
                }
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(xml),
                new StreamResult(xmlFile));

        VertexLogger.log("Tags successfully update.");
    }

}
