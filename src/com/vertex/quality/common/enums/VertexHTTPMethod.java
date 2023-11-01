package com.vertex.quality.common.enums;

import lombok.Getter;

/**
 * HTTP methods
 *
 * @author hho
 */
@Getter
public enum VertexHTTPMethod
{
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	PATCH("PATCH");

	private String httpMethod;

	VertexHTTPMethod( final String httpMethod )
	{
		this.httpMethod = httpMethod;
	}
}
