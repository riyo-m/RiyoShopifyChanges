package com.vertex.quality.connectors.oseriesfinal.api.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.oseriesfinal.api.enums.OSeriesFinalData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class to store common api methods
 *
 * @author Rohit.Mogane
 */
public class OSeriesFinalAPIBaseTest extends VertexAPITestUtilities {

    private static ReadProperties readEnvUrls;
    private static ReadProperties readCredentials;
    private static final String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
    private static final String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;
    protected String OSERIES_FINAL_USERNAME;
    protected String OSERIES_FINAL_PASSWORD;

    public OSeriesFinalAPIBaseTest() {
        initializeVariables();
    }

    /**
     * this method initializes credentials and url O-Series for login page
     */
    private void initializeVariables() {
        try {
            File testCredential = new File(TEST_CREDENTIALS_FILE_PATH);
            if (testCredential.exists()) {
                readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
            } else {
                VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
                        OSeriesFinalAPIBaseTest.class);
            }
            File testPropFilePath = new File(ENV_PROP_FILE_PATH);
            if (testPropFilePath.exists()) {
                readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
            } else {
                VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
                        OSeriesFinalAPIBaseTest.class);
            }
            OSERIES_FINAL_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.OSERIES.FINAL.USERNAME");
            OSERIES_FINAL_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.OSERIES.FINAL.PASSWORD");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Request specification for OSeries web services APIs
     *
     * @return initialRequestSpecification request specification details
     */
    @Override
    protected RequestSpecification generateRequestSpecification() {
        RequestSpecification initialRequestSpecification = super
                .generateRequestSpecification()
                .auth()
                .none()
                .header("Content-Type", "text/xml");
        return initialRequestSpecification;
    }

    /**
     * To read XML data into string
     *
     * @param input input stream from xml file
     * @return XML data as string
     */
    protected String getXMLData(InputStream input) {
        String result = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(input);
            NodeList loginNodeList = doc.getElementsByTagName("urn:Login");
            Node LoginNode = loginNodeList.item(0);
            NodeList childNodes = LoginNode.getChildNodes();
            Node username = childNodes.item(1);
            username.setTextContent(OSERIES_FINAL_USERNAME);
            Node password = childNodes.item(3);
            password.setTextContent(OSERIES_FINAL_PASSWORD);
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "no");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource domSource = new DOMSource(doc);
            URL url = OSeriesFinalAPIBaseTest.class.getResource(
                    OSeriesFinalData.O_SERIES_CACHE_REFRESH_DATA.data);
            StreamResult sr = new StreamResult(new File(url.toURI().getPath()));
            tf.transform(domSource, sr);
            input = OSeriesFinalAPIBaseTest.class.getResourceAsStream(
                    OSeriesFinalData.O_SERIES_CACHE_REFRESH_DATA.data);
            result = IOUtils.toString(input, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Read data from xml file from resources folder
     *
     * @return input stream from xml file to getXMLData()
     */
    public String getRefreshCacheData() {
        InputStream inputStream = OSeriesFinalAPIBaseTest.class.getResourceAsStream(
                OSeriesFinalData.O_SERIES_CACHE_REFRESH_DATA.data);
        return getXMLData(inputStream);
    }

    /**
     * API to refresh cache for OSeries
     *
     * @param refreshSOAPString refresh SOAP request body
     * @return response true for refresh cache
     */
    public Response refreshCacheOseries(String refreshSOAPString) {
        String requestUrl = OSeriesFinalData.O_SERIES_ENDPOINT.data + "/" +
                OSeriesFinalData.O_SERIES_ADMIN_SERVICES.data + "/" +
                OSeriesFinalData.O_SERIES_CACHE_REFRESH_SERVICES.data;
        Response createOrdersResponse = generateRequestSpecification()
                .body(refreshSOAPString)
                .post(requestUrl);
        return createOrdersResponse;
    }
}
