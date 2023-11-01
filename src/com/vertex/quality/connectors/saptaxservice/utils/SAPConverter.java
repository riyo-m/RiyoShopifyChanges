package com.vertex.quality.connectors.saptaxservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to convert between formats
 *
 * @author hho
 */
public class SAPConverter
{
	private ObjectMapper objectMapper;

	public SAPConverter( )
	{
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	/**
	 * Converts the given instance of a java object to JSON
	 *
	 * @param javaObject the java object that models the structure of a JSON object
	 *
	 * @return the JSON
	 */
	public JSONObject toJson( Object javaObject )
	{
		return new JSONObject(objectToMap(javaObject));
	}

	/**
	 * Converts the given instance of a java object to a hashmap
	 *
	 * @return the hashmap
	 */
	private Map<String, Object> objectToMap( Object javaObject )
	{
		Map<String, Object> map = null;
		try
		{
			String jsonString = objectMapper.writeValueAsString(javaObject);
			map = objectMapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>()
			{ });
		}
		catch ( Exception e )
		{
			System.out.println(e);
		}
		return map;
	}
}
