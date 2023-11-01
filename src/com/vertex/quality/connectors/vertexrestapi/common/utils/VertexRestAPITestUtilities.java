package com.vertex.quality.connectors.vertexrestapi.common.utils;

import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.entity.ContentType;

import static io.restassured.RestAssured.given;

/**
 * a utility class for api testing of Vertex's REST API
 *
 * @author ssalisbury
 */
public class VertexRestAPITestUtilities extends VertexAPITestUtilities
{
	private static final String BASE_URL_PROPERTY = "TEST.VERTEX.REST.API.BASE.URL";
	private static final String TOKEN_URL_PROPERTY = "TEST.VERTEX.REST.API.TOKEN.URL";
	private static final String CLIENT_ID_PROPERTY = "TEST.VERTEX.REST.API.CLIENT.ID";
	private static final String CLIENT_SECRET_PROPERTY = "TEST.VERTEX.REST.API.CLIENT.SECRET";
	private static final String SCOPE_PROPERTY = "TEST.VERTEX.REST.API.SCOPE";
	private static final String GRANT_TYPE_PROPERTY = "TEST.VERTEX.REST.API.GRANT.TYPE";
	private static final String USERNAME_PROPERTY = "TEST.VERTEX.REST.API.USERNAME";
	private static final String PASSWORD_PROPERTY = "TEST.VERTEX.REST.API.PASSWORD";
	private String CONFIGURATION_SETTING_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;
	private ReadProperties readConfigurationSettings = new ReadProperties(CONFIGURATION_SETTING_FILE_PATH);

	public VertexRestAPITestUtilities( )
	{
		loadAPIAccessInformation();
	}

	@Override
	protected void loadAPIAccessInformation( )
	{
		this.baseUrl = readConfigurationSettings.getProperty(BASE_URL_PROPERTY);

		Response responseBody = given()
			.contentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
			.accept(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
			.formParam("client_id", readConfigurationSettings.getProperty(CLIENT_ID_PROPERTY))
			.formParam("client_secret", readConfigurationSettings.getProperty(CLIENT_SECRET_PROPERTY))
			.formParam("scope", readConfigurationSettings.getProperty(SCOPE_PROPERTY))
			.formParam("grant_type", readConfigurationSettings.getProperty(GRANT_TYPE_PROPERTY))
			.formParam("username", readConfigurationSettings.getProperty(USERNAME_PROPERTY))
			.formParam("password", readConfigurationSettings.getProperty(PASSWORD_PROPERTY))
			.when()
			.post(readConfigurationSettings.getProperty(TOKEN_URL_PROPERTY));

		JsonPath jsonPath = new JsonPath(responseBody.asString());
		String oauth2Token = jsonPath.getString("access_token");

		PreemptiveOAuth2HeaderScheme scheme = new PreemptiveOAuth2HeaderScheme();
		scheme.setAccessToken(oauth2Token);
		RestAssured.authentication = scheme;
	}

	/**
	 * Creates a default API call request given the end point and file name
	 *
	 * @param endPoint the Vertex Rest API end point that the json file
	 *                 should be post to
	 * @param fileName the name of the JSON file in the relative path of the
	 *                 project
	 *
	 * @return the response to the request
	 */
	public Response buildRequestBasedOnEndPointAndFile( final EndPoint endPoint, final String fileName )
	{
		Response response = given()
			.filter(new RequestLoggingFilter())
			.filter(new ResponseLoggingFilter())
			.contentType(io.restassured.http.ContentType.JSON)
			.body(VertexRestAPIJsonUtils.getRestApiFileFromJsonFile(endPoint, fileName))
			.when()
			.post(baseUrl + "/" + endPoint.getEndPoint());

		return response;
	}

	/**
	 * Creates a default API call request given the end point and JSON string
	 *
	 * @param endPoint   the Vertex Rest API end point that the JSON string
	 *                   should be sent to
	 * @param jsonString the JSON string
	 *
	 * @return the response to the request
	 */
	public Response buildRequestBasedOnEndPointAndString( final EndPoint endPoint, final String jsonString )
	{
		Response response = given()
			.filter(new RequestLoggingFilter())
			.filter(new ResponseLoggingFilter())
			.contentType(io.restassured.http.ContentType.JSON)
			.body(jsonString)
			.when()
			.post(baseUrl + "/" + endPoint.getEndPoint());

		return response;
	}
}
