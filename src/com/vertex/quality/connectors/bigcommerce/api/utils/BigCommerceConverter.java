package com.vertex.quality.connectors.bigcommerce.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to convert between formats
 *
 * @author osabha ssalisbury
 */
public class BigCommerceConverter
{
	private ObjectMapper objectMapper;

	public BigCommerceConverter( )
	{
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	/**
	 * serializes the given Java object as a JSON string
	 *
	 * @param javaObject a Java object to be serialized
	 *
	 * @return a string representing the state of the Java object
	 *
	 * @author ssalisbury
	 */
	public String convertToJsonString( final Object javaObject )
	{
		String jsonString = null;
		try
		{
			jsonString = objectMapper.writeValueAsString(javaObject);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return jsonString;
	}

	/**
	 * Converts the given instance of a java object to JSON
	 *
	 * @param javaObject the java object that models the structure of a JSON object
	 *
	 * @return the JSON
	 */
	public JSONObject convertToJson( final Object javaObject )
	{
		JSONObject jsonObject = null;
		Map<String, Object> mapRepresentationOfObject = convertToMap(javaObject);
		jsonObject = new JSONObject(mapRepresentationOfObject);
		return jsonObject;
	}

	/**
	 * Converts the given instance of a java object to a hashmap
	 *
	 * @return the hashmap
	 */
	private Map<String, Object> convertToMap( final Object javaObject )
	{
		Map<String, Object> map = null;
		try
		{
			String jsonString = objectMapper.writeValueAsString(javaObject);
			map = objectMapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() { });
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return map;
	}
}

