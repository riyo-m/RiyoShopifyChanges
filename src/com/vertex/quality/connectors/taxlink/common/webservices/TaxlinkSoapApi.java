package com.vertex.quality.connectors.taxlink.common.webservices;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.dom.message.WSSecUsernameToken;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static org.testng.Assert.fail;

/**
 * A generic Taxlink SOAP API.
 *
 * @author msalomone
 */
public abstract class TaxlinkSoapApi {

    protected TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();
    protected TaxlinkAPIUtilities utilities = new TaxlinkAPIUtilities();

    public SOAPConnectionFactory connectionFactory = null;
    public SOAPConnection connection;
    public MessageFactory messageFactory;
    public SOAPMessage message;
    public MimeHeaders headers;
    public URL endpoint;
    public AttachmentPart attachment;
    public String response = "";
    public SOAPMessage responseAsSoap;

    public TaxlinkSoapApi() {  }

    /**
     * Calls all other methods (i.e. createSoapConnectionFactory)
     * to initialize all generic fields so that SOAP requests can be
     * built in the child classes.
     */
    public void initializeSoapComponents() throws SOAPException {
        createFactories();
        createConnection();
        createHeaders();
    }

    /**
     * @param localMessage XML request read from file without auth header.
     * @return message  A SOAPMessage containing auth components in the header.
     */
    public SOAPMessage addAuthHeader(SOAPMessage localMessage) {
        try {
            SOAPPart soappart = localMessage.getSOAPPart();
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
                    "header to the Taxlink SOAP request. Please check the xml file being used, " +
                    "or ask an automation engineer for assistance.", VertexLogLevel.ERROR);
            se.printStackTrace();
            fail("Test failed when adding auth header to SOAP request.");
        } catch (WSSecurityException wse) {
            VertexLogger.log("WS Security error. Please refer to stack trace below", VertexLogLevel.ERROR);
            wse.printStackTrace();
            fail("Test failed due to invalid authorization in SOAP request.");
        }
        return localMessage;
    }

    /**
     * Sends created request. Will return the response
     * automatically.
     *
     * Call ONLY AFTER calling createRequest.
     *
     * @return response The SOAP web service's XML response.
     */
    public String sendRequest() {
        response = getResponse(connection, endpoint);

        VertexLogger.log("Response: \n" + response, VertexLogLevel.INFO);

        return response;
    }

    /**
     * Creates a unique string used as the transaction number
     * for the request by appending the testname and a random
     * sequence of numbers to the prefix "AUTOTST".
     *
     * @param testName a short, descriptive String of what the test is doing (i.e. "APSMOKE" or "DELETEINVOICE")
     * @return trxNumber
     */
    public String createTrxNumber(String testName) {
        Random rnd = new Random();
        int number = rnd.nextInt(100000);

        String invoiceNumber = String.format("AUTOTST_%1$s_%2$s", testName, number);

        return invoiceNumber;
    }

    /**
     * Randomly generate a 7-digit number used for supplying random
     * trx id numbers.
     * @return randId
     */
    public String getRandId() {
        Random rnd = new Random();
        int number = rnd.nextInt(1000000);
        String randId = String.format("%s", number);

        return randId;
    }

    /**
     * Calls utility class method to retreive current date.
     * @return todaysDate
     */
    public String getTodaysDate() {
        String todaysDate = utilities.getTodaysDate("yyyy-MM-dd");
        return todaysDate;
    }

    /**
     * Write xml response from server to a resource file.
     * @param outputFileName Name of xml file to write response.
     */
    public void writeXmlResponseToFile(String outputFileName) {
        try
        {
            outputFileName = outputFileName+"-resp.xml";
            File xmlFile = utilities.getFile(outputFileName, "xml");
            FileWriter writer = new FileWriter(xmlFile.getPath());
            writer.write(response);
            writer.close();

            VertexLogger.log("Writing response to: "+xmlFile.getPath());

        } catch( IOException ioe) {
            VertexLogger.log("There was an issue writing the xml response to a result file.",
                    VertexLogLevel.ERROR);
            ioe.printStackTrace();
            fail("Test failed due to IOException when writing xml response to file. File name: "+outputFileName);
        }
    }

    // Private methods below.

    /**
     * Obtain a web service response from the specified SOAP endpoint.
     *
     * @param connection established connection to SOAP endpoint.
     * @param endpoint The endpoint URL.
     * @return outMsg The SOAP response as a String.
     */
    private String getResponse( SOAPConnection connection, URL endpoint) {
        String outMsg = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SOAPMessage theResponse = connection.call(message, endpoint);
            theResponse.writeTo(out);
            responseAsSoap = theResponse;
            outMsg = new String(out.toByteArray());
            message.writeTo(out);
        }
        catch(SOAPException | IOException se){
            VertexLogger.log("SOAP error. Please refer to your request", VertexLogLevel.ERROR);
        }
        return outMsg;
    }

    /**
     * Initialize a connection factory instance.
     *
     * @throws SOAPException
     */
    private void createFactories() throws SOAPException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
    }

    /**
     * Initialize a SOAP connection.
     *
     * @throws SOAPException
     */
    private void createConnection() throws SOAPException {
        connection = connectionFactory.createConnection();
    }

    /**
     * Initialize SOAP mime headers.
     *
     * @throws SOAPException
     */
    private void createHeaders() throws SOAPException {
        headers = new MimeHeaders();
    }

    protected abstract void formEndpointUrl() throws MalformedURLException;

    protected abstract void createMessage(byte[] encodedXML) throws SOAPException, IOException;

    protected abstract void createAndAddAttachment() throws SOAPException;
}
