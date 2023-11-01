package com.vertex.quality.common.enums;

/**
 * The response codes that can be returned with the response when sending a request to the Vertex
 * Rest API
 *
 * @author hho
 */
public enum ResponseCodes
{
	SUCCESS(200),
	CREATED(201),
	SUCCESS_NO_CONTENT(204),
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	FORBIDDEN(403),
	NOT_FOUND(404),
	UNPROCESSABLE_ENTITY(422),
	INTERNAL_ERROR(500);

	protected int responseCode;

	ResponseCodes( int responseCode )
	{
		this.responseCode = responseCode;
	}

	/**
	 * Gets the response code
	 *
	 * @return the response code
	 */
	public int getResponseCode( )
	{
		return responseCode;
	}

	public int getValue( )
	{
		return getResponseCode();
	}
}
