package com.vertex.quality.common.utils.misc;

import com.jayway.jsonpath.JsonPath;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a way to help parse JSON files into strings if needed or to
 * simplify calls to retrieve the path to a JSON file inside the project directory
 *
 * @author hho
 */
public class VertexJsonFileUtils
{
	private VertexJsonFileUtils( )
	{
	}

	public static String JSON_FILES_PATH = CommonDataProperties.PROJECT_PATH + "\\resources\\jsonfiles";

	/**
	 * Returns a JSON file located in the project's relative path in the form
	 * of a string
	 *
	 * @param fileName the name of the JSON file in the relative path of the
	 *                 project
	 *
	 * @return the JSON file in the form of a string
	 */
	public static String getStringFromJsonFile( String fileName )
	{
		String jsonFileString = null;
		try
		{
			jsonFileString = new String(Files.readAllBytes(Paths.get(JSON_FILES_PATH + "\\" + fileName)));
		}
		catch ( IOException e )
		{
			return jsonFileString;
		}
		return jsonFileString;
	}

	/**
	 * Returns a JSON file located in the project's relative path in the form
	 * of a file
	 *
	 * @param fileName the name of the JSON file in the relative path of the
	 *                 project
	 *
	 * @return the JSON file in the form of a file
	 */
	public static File getFileFromJsonFile( String fileName )
	{
		return new File(JSON_FILES_PATH + "\\" + fileName);
	}

	/**
	 * Retrieves the JSON string from the given JSON file and then edits that JSON string.
	 *
	 * @param jsonFile the JSON file
	 * @param jsonPath the JSON key's path
	 * @param value    the new value to replace for the JSON key's path
	 *
	 * @return the edited JSON string
	 */
	public static String retrieveJsonFileAsStringAndEdit( File jsonFile, String jsonPath, Object value )
	{
		String jsonString = null;
		try
		{
			jsonString = JsonPath
				.parse(jsonFile)
				.set(jsonPath, value)
				.jsonString();
		}
		catch ( IOException e )
		{
			return jsonString;
		}
		return jsonString;
	}

	/**
	 * Edits the given JSON String in the form of a string
	 *
	 * @param jsonFileAsString the JSON file as a string
	 * @param jsonPath         the JSON key's path
	 * @param value            the new value to replace for the JSON key's path
	 *
	 * @return the edited JSON
	 */
	public static String editJsonString( String jsonFileAsString, String jsonPath, Object value )
	{
		return JsonPath
			.parse(jsonFileAsString)
			.set(jsonPath, value)
			.jsonString();
	}

	/**
	 * Creates a JSON string from the file located by its relative path and name and creates edits
	 * to that JSON string corresponding to the given key and value pairs
	 *
	 * @param fileName     the name of the JSON file in the relative path of the
	 *                     project
	 * @param pathAndValue the list of key value pair to modify inside the json string
	 *
	 * @return the JSON string
	 */
	public static String createAndEditJsonString( String fileName, HashMap<String, Object> pathAndValue )
	{
		String jsonString = getStringFromJsonFile(fileName);

		for ( Map.Entry<String, Object> entry : pathAndValue.entrySet() )
		{
			jsonString = editJsonString(jsonString, entry.getKey(), entry.getValue());
		}

		return jsonString;
	}

	/**
	 * Adds a JSON object to the given JSON string. Places the object according to the given JSON
	 * path. The object will have its key as the given field name
	 * and its value will be field members.
	 *
	 * @param jsonString   the JSON string to add a JSON object to
	 * @param jsonPath     the path to add the JSON object to
	 * @param fieldName    the name of the JSON object
	 * @param fieldMembers they list of key value pairs for the field
	 *
	 * @return the JSON string
	 */
	public static String addJsonFieldsToJsonString( String jsonString, String jsonPath, String fieldName,
		JSONObject fieldMembers )
	{
		return JsonPath
			.parse(jsonString)
			.put(jsonPath, fieldName, fieldMembers)
			.jsonString();
	}

	/**
	 * Adds a JSON member to the given JSON string.
	 *
	 * @param jsonString the JSON string to add a JSON object to
	 * @param jsonPath   the new path to add
	 * @param fieldName  the name of the JSON object
	 * @param fieldValue the path's value
	 *
	 * @return the JSON string
	 */
	public static String addJsonFieldsToJsonString( String jsonString, String jsonPath, String fieldName,
		Object fieldValue )
	{
		return JsonPath
			.parse(jsonString)
			.put(jsonPath, fieldName, fieldValue)
			.jsonString();
	}

	/**
	 * Removes a JSON object from the given JSON string.
	 *
	 * @param jsonString the JSON string to remove a JSON field from
	 * @param jsonPath   the path to remove
	 *
	 * @return the JSON string
	 */
	public static String removeJsonFieldsFromJsonString( String jsonString, String jsonPath )
	{
		return JsonPath
			.parse(jsonString)
			.delete(jsonPath)
			.jsonString();
	}
}
