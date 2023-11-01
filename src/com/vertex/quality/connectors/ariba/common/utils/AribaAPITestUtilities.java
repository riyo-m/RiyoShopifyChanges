package com.vertex.quality.connectors.ariba.common.utils;

import com.vertex.quality.common.enums.*;
import com.vertex.quality.common.utils.TemplateEditor;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIPhases;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.pojos.AribaAPIDataMap;
import com.vertex.quality.connectors.ariba.api.pojos.AribaAPIDateTimes;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

/**
 * a utility class for api testing of ariba
 *
 * @author ssalisbury osabha
 */
public class AribaAPITestUtilities extends VertexAPITestUtilities
{
	private final DateTimeFormatter iso8601Format = ISODateTimeFormat.dateHourMinuteSecondMillis();
	private final TemplateEditor templateEditor = new TemplateEditor(getXmlFilepath("ariba"));
	protected final String oneXCookieName = "AribaAuth";
	protected String oneXCookieUrl = "https://ariba2.qa.vertexconnectors.com/vertex-ariba/data/login";
	public Cookie oneXCookie;
	protected String twoXCookieUrl = "https://ariba-stage-primary.cst-stage.vtxdev.net/vertex-ariba-2.0/data/login";
	protected final String twoXCookieName = "Ariba2Auth";
	public Cookie cookie;
	public static final DBConnectorNames CONNECTOR_NAME = DBConnectorNames.ARIBA;
	public static final DBEnvironmentNames QA_ENVIRONMENT_NAME = DBEnvironmentNames.QA;
	private String oneXUsername;
	private String oneXPassword;
	private String statusUrl;
	private String versionUrl;
	private String xmlLogRetrieverUrl;
	private String oneXLogRetrieverUrl;
	private String oneXUrl;
	protected AribaAPIType defaultAPIType;
	protected AribaAPIRequestType defaultAPIRequestType;
	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;
	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;

	// Preset data used in most cases for SOAP API
	public static final ResponseCodes STATUS_SUCCESS = ResponseCodes.SUCCESS;
	public static final String PARTITION = "default";
	public static final String TENANT = "default";
	public static final String COMPANY_CODE = "3000";
	public static final String ORGANIZATION_NAME = "3000";
	public static final String ORGANIZATION_UNIT = "CompanyCode";

	public static final String DEFAULT_COMMODITY_CODE = "CAPX00008";
	public static final String DEFAULT_CURRENCY = "USD";

	//object used to store data mappings for generating requests
	public AribaAPIDataMap requestData;

	//object used to store the date time
	public AribaAPIDateTimes dateTimes;

	public Path rootPath = Paths
			.get("")
			.toAbsolutePath();
	public String resourceFile = null;

	protected String docID;

	public AribaAPITestUtilities( final AribaAPIType apiType, final AribaAPIRequestType apiRequestType )
	{
		this.defaultAPIType = apiType;
		this.defaultAPIRequestType = apiRequestType;

		requestData = new AribaAPIDataMap();
		dateTimes = getDateTimes();
		setupPresetDocument();

		if ( apiType != null && apiRequestType != null )
		{
			setRequestTypeFields();
		}

		setupPresetAddresses(requestData);
		//TODO is this really needed in all these tests?
		requestData.setItemDescriptionCommonCommodityCode(1, null, null, "EA");

		loadAPIAccessInformation();
	}

	public AribaAPITestUtilities() {

	}

	public String getOneXLogRetrieverUrl( String tenantId )
	{
		String url = oneXLogRetrieverUrl + tenantId;
		return url;
	}

	public String getStatusUrl( )
	{
		return statusUrl;
	}

	public String getVersionUrl( )
	{
		return versionUrl;
	}

	public String getOneXUrl( )
	{
		return oneXUrl;
	}

	public String getXmlLogRetrieverUrl( )
	{
		String url = xmlLogRetrieverUrl + TENANT;
		VertexLogger.log(url);
		return url;
	}

	public String getOneXUsername( )
	{

		return oneXUsername;
	}

	public String getOneXPassword( )
	{

		return oneXPassword;
	}

	public String getXmlLogRetrieverUrl( String tenantId )
	{
		String url = xmlLogRetrieverUrl + tenantId;
		VertexLogger.log(url);
		return url;
	}

	/**
	 * Connects to the database and retrieves the required urls for the API's
	 */
	@Override
	protected void loadAPIAccessInformation( )
	{
		try
		{
			try{
				File testcredential = new File(TEST_CREDENTIALS_FILE_PATH);
				if (testcredential != null && testcredential.exists()) {
					readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
				} else {
					VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
							EpiBaseTest.class);
				}
				File testPropFilePath = new File(ENV_PROP_FILE_PATH);
				if (testPropFilePath != null && testPropFilePath.exists()) {
					readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
				} else {
					VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
							EpiBaseTest.class);
				}
			}catch (Exception e){
				e.printStackTrace();
			}

			baseUrl = readEnvUrls.getProperty("TEST.ENV.ARIBA.BASE.URL");
			statusUrl = readEnvUrls.getProperty("TEST.ENV.ARIBA.STATUS.URL");
			versionUrl = readEnvUrls.getProperty("TEST.ENV.ARIBA.VERSION.URL");
			xmlLogRetrieverUrl = readEnvUrls.getProperty("TEST.ENV.ARIBA.XMLLOGRETRIEVER.URL");
			PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
			authScheme.setUserName(readCredentials.getProperty("TEST.CREDENTIALS.ARIBA.USERNAME"));
			authScheme.setPassword(readCredentials.getProperty("TEST.CREDENTIALS.ARIBA.PASSWORD"));
			RestAssured.authentication = authScheme;
		}
		catch ( Exception e )
		{
			VertexLogger.log("unable to load the base URLs for Ariba's APIs", VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	protected void setRequestTypeFields( )
	{
		requestData.setDocumentTypeFields(defaultAPIType, defaultAPIRequestType);
	}

	/**
	 * Gets the ISO-8601 formatted dates for a previous, current, and next date
	 * The previous and next dates are used as the start and end dates for the XML log viewer
	 * The now date should be used as the document name in the API
	 *
	 * @return the POJO containing the ISO-8601 formatted dates
	 */
	protected AribaAPIDateTimes getDateTimes( )
	{
		AribaAPIDateTimes dateTimes = new AribaAPIDateTimes();
		DateTime now = new DateTime();
		DateTime previous = now.minusMinutes(59);
		DateTime next = now.plusDays(1);

		dateTimes.setPreviousDateTime(previous.toString(iso8601Format));
		dateTimes.setCurrentDateTime(now.toString(iso8601Format));
		dateTimes.setNextDateTime(next.toString(iso8601Format));

		return dateTimes;
	}

	/**
	 * Sends the common XML request
	 *
	 * @return the response
	 */
	public Response sendXMLRequest( String tenant )
	{
		requestData.setTenantField(tenant);
		requestData.setPartitionField(tenant);
		String xmlRequest = templateEditor.processTemplate("base.ftl", requestData.getRequestData());
		Response response = sendRequest(VertexHTTPMethod.POST, "text/xml", baseUrl, null, xmlRequest);

		return response;
	}

	/**
	 * Different implementation for sending preset request XML files instead of
	 * building them dynamically
	 *
	 * @param filepath
	 * */
	public Response sendXmlRequestByFile(String filepath){
		File xmlFile = new File(filepath);
		Response response = given().contentType("text/xml").header("Authorization", "Basic YXJpYmFfY29ubmVjdG9yX3VzZXI6Q29ubmVjdG9yQXJpYmEwMQ==")
				.body(xmlFile).post("https://ariba2.qa.vertexconnectors.com/vertex-ariba-2.0/ariba");
		response.then().statusCode(200);

		return response;
	}

	/**
	 * Sends the common XML request
	 *
	 * @return the response
	 */
	public Response sendXMLRequestWithCookie( String tenant, Cookie cookie )
	{
		requestData.setTenantField(tenant);
		requestData.setPartitionField(tenant);
		String xmlRequest = templateEditor.processTemplate("base.ftl", requestData.getRequestData());
		Response response = sendRequestWithCookie(VertexHTTPMethod.POST, "text/xml", baseUrl, null, xmlRequest, cookie);

		return response;
	}

	/**
	 * Sends the common XML request
	 *
	 * @return the response
	 */
	public Response sendXMLRequest( )
	{
		String xmlRequest = templateEditor.processTemplate("base.ftl", requestData.getRequestData());
		Response response = sendRequest(VertexHTTPMethod.POST, "text/xml", baseUrl, null, xmlRequest);

		return response;
	}

	/**
	 * Verifies the basics details of the response are correct
	 *
	 * @param response             the data
	 * @param expectedResponseCode the response code
	 * @param isResponseSuccessful if the expected response should be successful
	 *
	 * @return true if correct
	 *
	 * @author dgorecki
	 */
	public boolean isResponseCorrect( final Response response, final ResponseCodes expectedResponseCode,
		final boolean isResponseSuccessful )
	{
		final String successField = "**.findAll { it.name() == 'ExternalTaxResponseStatus' }.Success";

		String expectedSuccessValue = String.valueOf(isResponseSuccessful);

		response
			.then()
			.statusCode(expectedResponseCode.getValue())
			.body(successField, equalTo(expectedSuccessValue));

		return true;
	}

	/**
	 * Retrieves the XML log response for Ariba specified by its phase
	 *
	 * @param dateTimes            the times to use to search for the log
	 * @param expectedNumberOfLogs the expected number of logs
	 * @param phase                the phase
	 *
	 * @return the xml log
	 */
	public ValidatableResponse retrieveXMLLogResponse( final AribaAPIDateTimes dateTimes,
		final int expectedNumberOfLogs, final AribaAPIPhases phase )
	{
		Map<String, Object> params = new HashMap<>();
		params.put("document", docID);
		params.put("start", dateTimes.getPreviousDateTime());
		params.put("end", dateTimes.getNextDateTime());

		Response response = sendRequestWithCookie(VertexHTTPMethod.GET, getXmlLogRetrieverUrl(), params, cookie);

		String xml = response
			.then()
			.body("size()", equalTo(expectedNumberOfLogs))
			.extract()
			.path("find { it.phase == '%s' }.message", phase.getPhase());

		ValidatableResponse xmlResponse = new ResponseBuilder()
			.setBody(xml)
			.setContentType("text/xml")
			.setStatusCode(STATUS_SUCCESS.getResponseCode())
			.build()
			.then()
			.log()
			.body();

		return xmlResponse;
	}

	/**
	 * Retrieves the XML log response for Ariba specified by its phase
	 *
	 * @param docID                unique name of the documents we are looking to retrieve
	 * @param logMessageIndex      type of log message we are looking for
	 * @param expectedNumberofLogs expected number of logs to find
	 *
	 * @return the target xml log message as a validatable response
	 *
	 * @author osabha
	 */
	public ValidatableResponse getXMLMessageLog( final String docID, final String logMessageIndex,
		final int expectedNumberofLogs, String tenant )
	{
		Map<String, Object> params = new HashMap<>();

		DateTime nowTime = new DateTime();
		DateTime previousTime = nowTime.minusDays(2);
		DateTime nextTime = nowTime.plusDays(2);
		String previous = previousTime.toString(iso8601Format);
		String next = nextTime.toString(iso8601Format);
		params.put("document", docID);
		params.put("start", previous);
		params.put("end", next);

		Response response = sendRequestWithCookie(VertexHTTPMethod.GET, getXmlLogRetrieverUrl(tenant), params, cookie);

		String xmlMessages = response
			.then()
			.body("size()", equalTo(expectedNumberofLogs))
			.extract()
			.path("message[%s]", logMessageIndex);

		ValidatableResponse xmlResponse = new ResponseBuilder()
			.setBody(xmlMessages)
			.setContentType("text/xml")
			.setStatusCode(STATUS_SUCCESS.getResponseCode())
			.build()
			.then()
			.log()
			.body();

		return xmlResponse;
	}

	/**
	 * Retrieves the XML log response for Ariba specified by its phase
	 *
	 * @param dateTimes            the times to use to search for the log
	 * @param expectedNumberOfLogs the expected number of logs
	 * @param phase                the phase
	 *
	 * @return the xml log
	 */
	public ValidatableResponse retrieveXMLLogResponse( final AribaAPIDateTimes dateTimes,
		final int expectedNumberOfLogs, final AribaAPIPhases phase, String tenantId )
	{
		Map<String, Object> params = new HashMap<>();
		params.put("document", docID);
		params.put("start", dateTimes.getPreviousDateTime());
		params.put("end", dateTimes.getNextDateTime());

		Response response = sendRequestWithCookie(VertexHTTPMethod.GET, getXmlLogRetrieverUrl(tenantId), params,
			cookie);

		String xml = response
			.then()
			.body("size()", equalTo(expectedNumberOfLogs))
			.extract()
			.path("find { it.phase == '%s' }.message", phase.getPhase());

		ValidatableResponse xmlResponse = new ResponseBuilder()
			.setBody(xml)
			.setContentType("text/xml")
			.setStatusCode(STATUS_SUCCESS.getResponseCode())
			.build()
			.then()
			.log()
			.body();

		return xmlResponse;
	}

	/**
	 * checks for a specific String field value in the oseries request xml
	 *
	 * @param logPath   path of the field we want to check the value of
	 *                  looking for a field that doesnt exist in a field that doesnt exist eiter will fail
	 *                  even if you are expecting the first to be null
	 * @param testValue the value in the field we are asserting for
	 *
	 * @return true if the field value matches the expected.
	 */
	public boolean areXMLLogsCorrect( final String logPath, final String testValue, int expectedNumberOfLogs )
	{
		if ( testValue == null )
		{
			ValidatableResponse xmlLogResponse = retrieveXMLLogResponse(dateTimes, expectedNumberOfLogs,
				AribaAPIPhases.O_SERIES_REQUEST);
			xmlLogResponse
				.assertThat()
				.body(logPath, nullValue());
			return true;
		}
		else
		{
			ValidatableResponse xmlLogResponse = retrieveXMLLogResponse(dateTimes, expectedNumberOfLogs,
				AribaAPIPhases.O_SERIES_REQUEST);
			xmlLogResponse
				.assertThat()
				.body(logPath, equalTo(testValue));
			return true;
		}
	}

	/**
	 * checks for a specific String field value in the oseries request xml
	 *
	 * @param logPath   path of the field we want to check the value of
	 * @param testValue the value in the field we are asserting for
	 *
	 * @return true if the field value matches the expected.
	 */
	public boolean areXMLLogsCorrect( final String logPath, final String testValue, int expectedNumberOfLogs,
		AribaAPIPhases phase )
	{
		ValidatableResponse xmlLogResponse = retrieveXMLLogResponse(dateTimes, expectedNumberOfLogs, phase);
		xmlLogResponse
			.assertThat()
			.body(logPath, equalTo(testValue));
		return true;
	}

	/**
	 * checks for a specific String field value in the oseries request xml
	 *
	 * @param logPath   path of the field we want to check the value of
	 * @param testValue the value in the field we are asserting for
	 *
	 * @return true if the field value matches the expected.
	 */
	public boolean areXMLLogsCorrect( final String logPath, final String testValue, int expectedNumberOfLogs,
		String tenantId )
	{
		ValidatableResponse xmlLogResponse = retrieveXMLLogResponse(dateTimes, expectedNumberOfLogs,
			AribaAPIPhases.O_SERIES_REQUEST, tenantId);
		xmlLogResponse
			.assertThat()
			.body(logPath, equalTo(testValue));
		return true;
	}

	/**
	 * checks for a specific boolean field value in the oseries request xml
	 *
	 * @param logPath       path of the field we want to check the value of
	 * @param expectedValue the value in the field we are asserting for
	 *
	 * @return true if the field value matches the expected.
	 */
	public boolean areXMLLogsCorrect( final String logPath, final boolean expectedValue, int expectedNumberOfLogs )
	{
		ValidatableResponse xmlLogResponse = retrieveXMLLogResponse(dateTimes, expectedNumberOfLogs,
			AribaAPIPhases.O_SERIES_REQUEST);
		xmlLogResponse
			.assertThat()
			.body(logPath, equalTo(expectedValue));
		return true;
	}

	/**
	 * Sets up some preset information to send out requests faster
	 */
	protected void setupPresetDocument( )
	{
		requestData.setGeneralRequiredValues(PARTITION, TENANT, dateTimes.getCurrentDateTime());

		String docId = generateDocumentId(dateTimes);

		requestData.setGeneralOptionalValues(COMPANY_CODE, ORGANIZATION_NAME, ORGANIZATION_UNIT, docId, null);

		requestData.setNumberOfItems(1);
		requestData.setItemNetAmountValues(1, "231", DEFAULT_CURRENCY);
		requestData.setItemRequiredValues(1, "1", "0", "1", "false");
	}

	/**
	 * retrieves the document id generated for the request to use it to look at the logs
	 *
	 * @return the request document id as a string
	 */
	public String getDocumentId( )
	{
		String documentId = "Auto_" + dateTimes.getCurrentDateTime();
		documentId = documentId.replace(':', '_');
		documentId = documentId.replace('.', '_');
		documentId = documentId.replace('-', '_');
		return documentId;
	}

	/**
	 * Generates a random document id for use in senging requests based on the current
	 * date/time.  Returns null if a null value is passed in.
	 *
	 * @param dateTimes a date time object to pull the current value from
	 *
	 * @return a string representation of the date time for use as a document id or null if no valid dateTime object was
	 * provided
	 *
	 * @author dgorecki
	 */
	public String generateDocumentId( final AribaAPIDateTimes dateTimes )
	{
		String currentDateTime = null;

		if ( dateTimes != null )
		{
			currentDateTime = dateTimes.getCurrentDateTime();
		}

		docID = "AUTO_" + currentDateTime;
		docID = docID.replace(':', '_');
		docID = docID.replace('.', '_');
		docID = docID.replace('-', '_');
		return docID;
	}

	/**
	 * Sets up some preset information for addresses
	 */
	public void setupPresetAddresses( final AribaAPIDataMap data )
	{
		data.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "King of Prussia", "US", "2301 Renaissance Blvd", null, null,
			"19406", "PA");
		data.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Berwyn", "US", "1041 Old Cassatt Rd", null, null, "19312",
			"PA");
		data.setAddress(1, AribaAPIAddressTypes.BILLING, "Berwyn", "US", "1041 Old Cassatt Rd", null, null, "19312",
			"PA");
	}

	/**
	 * does simple login request and waits for the success response to capture specific cookie from it
	 *
	 * @return response header cookie
	 */
	public Cookie retrieveSessionCookie( String url, String cookieName )
	{
		Response response = given()
			.contentType("application/hal+json;charset=UTF-8")
			.when()
			.body("{\"credentials\":\"ariba_connector_user:ConnectorAriba01\"}")
			.post(url);
		Cookie currentCookie = response.getDetailedCookie(cookieName);
		return currentCookie;
	}

	/**
	 * Return a filepath from the resource directory as a File object.
	 *
	 * @param fileName (String) Name of the file in the resource directory.
	 * @param fileType (String) Filetype of requested file (i.e. "xml")
	 *
	 * @return file The file from the resource dir represented as a File object.
	 */
	public File getFile( String fileName, String fileType )
	{
		Path resourcePath = this.getResourcePath(fileType);
		resourcePath = Paths.get(resourcePath.toString() + "/" + fileName);

		File file = new File(resourcePath.toString());

		return file;
	}

	/**
	 * Retrieve a file from connector-quality-java/ConnectorQuality/resources/xmlfiles/ariba.
	 *
	 * @param resourceType - Type of file needed. Valid values is : "xml".
	 *
	 * @return the resource folder as a string.
	 *
	 * @paramtype String
	 */
	public Path getResourcePath( String resourceType )
	{
		Path resourcePath = this.rootPath;

		if ( resourceType.equals("xml") )
		{
			String xmlDir = "/resources/xmlfiles/ariba";
			resourcePath = Paths.get(resourcePath.toString() + xmlDir);
		}
		else
		{
			VertexLogger.log(
					"Warning. Resource type specified is not valid. " + "Valid values include 'xml'.",
					VertexLogLevel.WARN);
		}
		return resourcePath;
	}
}