package com.vertex.quality.connectors.vertexrestapi.common.utils;

/**
 * Utility class that holds all testing data for the Vertex Rest API
 *
 * @author hho
 */
public class VertexRestAPITestData
{
	public static final String STRING_TEST_VALUE = "string";
	public static final String BAD_ENUM_TEST_VALUE = "somebadnonsensetestingvalue";
	public static final String NULL_VALUE = null;
	public static final int INTEGER_TEST_VALUE = 123;
	public static final String INTEGER_TO_STRING_TEST_VALUE = "123";
	public static final float FLOAT_TEST_VALUE = 5f;
	public static final double DOUBLE_TEST_VALUE = 5.0;
	public static final boolean BOOLEAN_TEST_VALUE = true;
	public static final String BOOLEAN_TO_STRING_TEST_VALUE = "true";
	public static final String BAD_FIELD_MEMBER_NAME = "somebadnonsensetestingvalue";
	public static final String EMPTY_STRING_VALUE = "";
	public static final String SIZE_ONE_STRING_VALUE = "a";
	public static final String SIZE_FOUR_STRING_VALUE = "aaaa";
	public static final String SIZE_FIVE_STRING_VALUE = "aaaaa";
	public static final String SIZE_FORTY_STRING_VALUE = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	public static final String SIZE_FORTY_ONE_STRING_VALUE = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	public static final String SIZE_SIXTY_STRING_VALUE = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	public static final String SIZE_SIXTY_ONE_STRING_VALUE
		= "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

	public static final String ERROR_PATH = "errors[0].detail";
	public static final String ROOT_PATH = "$";
	public static final String ROOT_RESPONSE_PATH = "data";
	public static final String UNRECOGNIZED_FIELD_ERROR = "Unrecognized field ";

	private VertexRestAPITestData( )
	{
	}
}
