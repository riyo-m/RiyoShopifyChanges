package com.vertex.quality.connectors.tradeshift.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TradeshiftUtils {

    public TradeshiftUtils(){}

    /**
     * Generates a random 7 digit string to be used for the
     * invoice number since they have to be unique
     *
     * @return the random invoice number for the invoice number field
     * */
    public static String randomInvoiceNumber(){
        int INVOICE_NUM_LENGTH = 7;
        Random num = new Random();
        StringBuilder invoiceNumber = new StringBuilder();
        while(invoiceNumber.length() < INVOICE_NUM_LENGTH){
            invoiceNumber.append(num.nextInt(10));
        }
        return invoiceNumber.toString();
    }

    /**
     * This method is used to change the invoice number in an xml for easier testing
     * it takes in a file path and invoice number, parses the file changes the number,
     * then saves the file with the new change
     *
     * @param xmlFile the xml file to change
     * @param invoiceNumber the new invoice number for the invoice
     * */
    public static void changeXMLFileInvoiceNumber(File xmlFile, String invoiceNumber){
        DocumentBuilderFactory docBuilderInstance = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        //allows us to get XML documents for parsing
        try {
            documentBuilder = docBuilderInstance.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //doc is our entire xml document for parsing
        Document doc = null;
        try {
            doc = documentBuilder.parse(xmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Used for finding the invoice-number xPath as it is a direct child of invoice-header
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node invoiceNumberNode = null;
        try {
            //Finds and returns the invoice-number node
            invoiceNumberNode = (Node) xPath.compile("/invoice-header/invoice-number").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        //change the invoice number
        invoiceNumberNode.setTextContent(invoiceNumber);

        //Transformer is used to change the data back into an XML file
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        try {
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
