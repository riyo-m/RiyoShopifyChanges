package com.vertex.quality.common.utils.api;

import com.vertex.quality.common.enums.VertexHTTPMethod;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * a utility class for api testing
 *
 * @author ssalisbury
 */
public class VertexAPITestUtilities
{
	@Getter
	protected String baseUrl;

	public VertexAPITestUtilities( )
	{
	}

	/**
	 * loads the API base URLs and credentials
	 * This should be overloaded by eac APITestUtilities subclass for a specific connector to fetch
	 * the urls/credentials/etc. which are relevant for that connector's api testing
	 */
	protected void loadAPIAccessInformation( ) { }

	/**
	 * Sets up the auth for the Rest call to use Basic auth with the set credentials
	 */
	public void setBasicAuth( final String username, final String password )
	{
		PreemptiveBasicAuthScheme scheme = new PreemptiveBasicAuthScheme();
		scheme.setUserName(username);
		scheme.setPassword(password);
		RestAssured.authentication = scheme;
	}

	/**
	 * Gets the full file path to the file name given
	 *
	 * @param fileName the file name
	 *
	 * @return the full file path
	 */
	public Path getXmlFilepath( final String fileName )
	{
		String xmlFileDirectory = "resources/xmlfiles";
		Path xmlFilepath = Paths.get(xmlFileDirectory + "/" + fileName);
		return xmlFilepath;
	}

	/**
	 * Overrides {@link #sendRequest(VertexHTTPMethod, String, Map)} by setting its params to null so that in the
	 * actual request they are left as unspecified
	 */
	public Response sendRequest( final VertexHTTPMethod httpMethods, final String url )
	{
		Response response = sendRequest(httpMethods, url, null);
		return response;
	}

	/**
	 * Overrides {@link #sendRequest(VertexHTTPMethod, String, Map)} by setting its params to null so that in the
	 * actual request they are left as unspecified
	 */
	public Response sendRequestWithCookie( final VertexHTTPMethod httpMethods, final String url, Cookie cookie )
	{
		Response response = sendRequestWithCookie(httpMethods, url, null, cookie);
		return response;
	}

	/**
	 * Overrides {@link #sendRequest(VertexHTTPMethod, String, String, Map, Object)} by setting its
	 * contentType and requestBody to null so that in the actual request they are left as unspecified
	 *
	 * @param params the parameters to send
	 */
	public Response sendRequest( final VertexHTTPMethod httpMethod, final String url, final Map<String, Object> params )
	{
		Response response = sendRequest(httpMethod, null, url, params, null);
		return response;
	}

	/**
	 * Overrides {@link #sendRequest(VertexHTTPMethod, String, String, Map, Object)} by setting its
	 * contentType and requestBody to null so that in the actual request they are left as unspecified
	 *
	 * @param params the parameters to send
	 */
	public Response sendRequestWithCookie( final VertexHTTPMethod httpMethod, final String url,
		final Map<String, Object> params, Cookie cookie )
	{
		Response response = sendRequestWithCookie(httpMethod, null, url, params, null, cookie);
		return response;
	}

	/**
	 * Sends a request and gets the response
	 *
	 * @param httpMethod  the http method for interacting with the endpoint
	 * @param contentType the content type of the request message
	 * @param url         the url
	 * @param params      the parameters to send in the request
	 * @param requestBody the request body
	 *
	 * @return the response message from the api
	 *
	 * @author hho ssalisbury
	 */
	public Response sendRequest( final VertexHTTPMethod httpMethod, final String contentType, final String url,
		final Map<String, Object> params, final Object requestBody )
	{

		RequestSpecification requestSpecification = generateRequestSpecification();

		if ( contentType != null )
		{
			requestSpecification = requestSpecification.contentType(contentType);
		}
		if ( params != null )
		{
			requestSpecification = requestSpecification.params(params);
		}
		if ( requestBody != null )
		{
			requestSpecification = requestSpecification.body(requestBody);
		}
		Response response = switchHttpMethods(httpMethod, requestSpecification, url);
		return response;
	}

	/**
	 * Sends a request and gets the response
	 *
	 * @param httpMethod  the http method for interacting with the endpoint
	 * @param contentType the content type of the request message
	 * @param url         the url
	 * @param params      the parameters to send in the request
	 * @param requestBody the request body
	 *
	 * @return the response message from the api
	 *
	 * @author hho ssalisbury
	 */
	public Response sendRequestWithCookie( final VertexHTTPMethod httpMethod, final String contentType,
		final String url, final Map<String, Object> params, final Object requestBody, final Cookie cookie )
	{

		RequestSpecification requestSpecification = generateRequestSpecification();
		requestSpecification.cookie(cookie);
		if ( contentType != null )
		{
			requestSpecification = requestSpecification.contentType(contentType);
		}
		if ( params != null )
		{
			requestSpecification = requestSpecification.params(params);
		}
		if ( requestBody != null )
		{
			requestSpecification = requestSpecification.body(requestBody);
		}
		Response response = switchHttpMethods(httpMethod, requestSpecification, url);
		return response;
	}

	/**
	 * Sends a request and gets the response
	 *
	 * @param httpMethod  the http method for interacting with the endpoint
	 * @param contentType the content type of the request message
	 * @param url         the url
	 * @param params      the parameters to send in the request
	 * @param requestBody the request body
	 *
	 * @return the response message from the api
	 *
	 * @author hho ssalisbury
	 */
	public Response sendRequest( final VertexHTTPMethod httpMethod, final String contentType, final String url,
		final Map<String, Object> params, final Object requestBody, String username, String password )
	{

		RequestSpecification requestSpecification = generateRequestSpecification();

		if ( contentType != null )
		{
			requestSpecification = requestSpecification.contentType(contentType);
		}
		if ( params != null )
		{
			requestSpecification = requestSpecification.params(params);
		}
		if ( requestBody != null )
		{
			requestSpecification = requestSpecification.body(requestBody);
			requestSpecification = requestSpecification
				.auth()
				.basic(username, password);
		}
		Response response = switchHttpMethods(httpMethod, requestSpecification, url);
		return response;
	}

	/**
	 * creates the base for an api request by initializing things like logging filters that all api requests need
	 *
	 * @return the base for an api request
	 *
	 * @author ssalisbury
	 */
	protected RequestSpecification generateRequestSpecification( )
	{
		RequestSpecification initialRequestSpecification = given()
			.filter(new RequestLoggingFilter())
			.filter(new ResponseLoggingFilter());
		return initialRequestSpecification;
	}

	/**
	 * adds specification to a request based on http method passed,sends the request and returns a response
	 *
	 * @param httpMethod
	 * @param requestSpecification
	 * @param url
	 *
	 * @return response after completing the request specification with the target http method and sending it
	 */
	public Response switchHttpMethods( final VertexHTTPMethod httpMethod,
		final RequestSpecification requestSpecification, final String url )
	{
		Response response = null;

		switch ( httpMethod )
		{
			case GET:
				response = requestSpecification.get(url);
				break;
			case POST:
				response = requestSpecification.post(url);
				break;
			case PUT:
				response = requestSpecification.put(url);
				break;
			case PATCH:
				response = requestSpecification.patch(url);
				break;
			case DELETE:
				response = requestSpecification.delete(url);
				break;
		}
		return response;
	}
}
