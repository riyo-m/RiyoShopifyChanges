package com.vertex.quality.common.utils.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to convert POJO to JSON
 *
 * @author hho
 */
public class VertexPojoToJsonUtil
{
	private final static ObjectMapper objectMapper = initializeMapper();

	private VertexPojoToJsonUtil( )
	{
	}

	/**
	 * Method to initialize the mapper used to convert POJO's to JSON
	 *
	 * @return the initialized mapper
	 */
	private static ObjectMapper initializeMapper( )
	{
		ObjectMapper mapper = new ObjectMapper();
		// sets the mapper to NOT include null or empty values in the resulting JSON
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		return mapper;
	}

	/**
	 * Converts the given instance of a POJO to JSON
	 *
	 * @param pojoInstance the instance of a POJO that models the structure of a JSON object
	 *
	 * @return the JSON for that POJO
	 */
	public static JSONObject toJson( Object pojoInstance )
	{
		return new JSONObject(objectToMap(pojoInstance));
	}

	/**
	 * The intermediate step in converting a POJO to a JSON
	 * Converts the given instance of a POJO to a hashmap
	 * The hashmap is then finally converted to a JSONObject later
	 *
	 * @return the hashmap for the POJO
	 */
	private static Map<String, Object> objectToMap( Object pojoInstance )
	{
		Map<String, Object> map = null;
		try
		{
			String jsonString = objectMapper.writeValueAsString(pojoInstance);
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
