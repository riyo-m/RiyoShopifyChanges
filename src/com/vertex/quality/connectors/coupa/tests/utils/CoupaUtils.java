package com.vertex.quality.connectors.coupa.tests.utils;

import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class CoupaUtils {
    private static String CONFIG_PROP_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;
    private static ReadProperties readConfig = new ReadProperties(CONFIG_PROP_FILE_PATH);
    private String OSERIESURL = readConfig.getProperty("TEST.VERTEX.COUPA.OSERIES.URL");

    public String getOSERIESURL(){
        return OSERIESURL;
    }


    /**
     * gets the current time and subtracts 1 hour
     * then formats to ensure we can get the correct invoice from the api
     *
     * @return the properly formatted date and time minus 1 hour
     */
    public String getCurrentDateTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime currentDayAndTime = LocalDateTime.now();
        String dayAndTimeMinus1h = dateFormatter.format(currentDayAndTime.minusHours(1));
        return dayAndTimeMinus1h;
    }

    /**
     * Generates access token from Coupa
     *
     * @return token
     */
    public String createAccessTokenTestMode() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().header("Content-Type","text/plain").header("Accept","*/*").header("Content-Type","application/json;charset=UTF-8")
                .body("{\"username\":\"testsuperuser\",\"password\":\"testsuperuserpw\"}").post("https://coupa.qa.vertexconnectors.com/testapi/accesstokens");

        response.then().statusCode(201);
        String token = response.body().path("token");

        System.out.println(token);

        return token;
    }

    /**
     * Generates access token from Coupa
     *
     * @return token
     */
    public String createAccessToken() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().header("Content-Type","text/plain").header("Accept","*/*").header("Content-Type","application/json;charset=UTF-8")
                .body("{\"username\":\"coupaUser\",\"password\":\"vertex\"}").post("https://coupa.qa.vertexconnectors.com/api/accesstokens");

        response.then().statusCode(201);
        String token = response.body().path("token");

        System.out.println(token);

        return token;
    }

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
