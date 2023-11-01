package com.vertex.quality.connectors.oraclecloud.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecTimestamp;
import org.apache.wss4j.dom.message.WSSecUsernameToken;

import javax.xml.soap.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.testng.Assert.fail;

 public class SoapRequest {
 public SOAPMessage message;
 public String response = "";
 public SOAPMessage responseAsSoap;

 private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

 /**
 * Create and send a SOAP request to the Oracle ERP system.
 *
 * @param encodedXML - xml file to be used. File must be located in ConnectorQuality/resources/xmlfiles
 *
 */
 public void createSoapRequest(byte[] encodedXML)
 {
     //Handle Logic in OracleWebService sendSoapRequest
     SOAPConnectionFactory soapConnectionFactory = null;
     try {
         soapConnectionFactory = SOAPConnectionFactory.newInstance();

         URL endpoint = new URL(settings.environment + ":443/fscmService/ErpIntegrationService");
         MimeHeaders headers = new MimeHeaders();
         SOAPConnection connection = soapConnectionFactory.createConnection();
         MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
         message = factory.createMessage(headers, new ByteArrayInputStream(encodedXML));
         AttachmentPart attachment = message.createAttachmentPart();
         attachment.setContent("sm_content", "text/plain");
         attachment.setContentId("1.9f910338bf0cac0e783bfdec7e53be9237684caa8f8f4e6d@apache.org");
         message.addAttachmentPart(attachment);

         message = addAuthHeader(message);

         response = getResponse(connection, endpoint);

         VertexLogger.log("Response: \n" + response, VertexLogLevel.INFO);

     } catch (SOAPException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
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
    * Store the attachments returned through SOAP request as Zip
    *
    * @param att The response to be converted to zip
    */
    public void storeAttachmentPartZip(AttachmentPart att) throws IOException, SOAPException {
        try (ZipInputStream zis = new ZipInputStream(att.getRawContent())) {
            byte[] buffer = new byte[1024];

            String responseString = "";
            for (ZipEntry zipEntry = zis.getNextEntry(); zipEntry != null; zipEntry = zis.getNextEntry()) {
                if (zipEntry.isDirectory()) {
                    continue;
                }
                VertexLogger.log(zipEntry.getName(), VertexLogLevel.INFO);
                for (int len = zis.read(buffer); len > 0; len = zis.read(buffer)) {
                    responseString = responseString + new String(buffer, StandardCharsets.UTF_8);
                }
            }
            response = responseString;

        }
    }

     /**
      * Get the response from Oracle ERP system and store as a String.
      *
      * @param connection - soap connection
      * @param endpoint   - Oracle endpoint to be used
      *
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

            VertexLogger.log("Response: \n" + outMsg, VertexLogLevel.INFO);
        }
        catch(SOAPException | IOException se){
            VertexLogger.log("SOAP error. Please refer to your request", VertexLogLevel.ERROR);
        }
        return outMsg;
    }

     /**
      * Store the attachments returned through SOAP request as Zip
      *
      */
    public void updateResponseAsAttachmentContent(){
        Iterator<?> attachments = responseAsSoap.getAttachments();

        while(attachments.hasNext()){
            AttachmentPart attachment = (AttachmentPart) attachments.next();
            try {
                storeAttachmentPartZip(attachment);
                VertexLogger.log(response, VertexLogLevel.INFO);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }
    }
}
