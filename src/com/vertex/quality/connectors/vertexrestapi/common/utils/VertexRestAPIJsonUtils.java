package com.vertex.quality.connectors.vertexrestapi.common.utils;

import com.vertex.quality.common.utils.misc.VertexJsonFileUtils;
import com.vertex.quality.connectors.vertexrestapi.common.enums.EndPoint;

import java.io.File;

/**
 * This class is a way to help parse JSON files into strings if needed or to
 * simplify calls to retrieve the path to a JSON file needed for a Vertex Rest API call
 *
 * @author hho
 */
public class VertexRestAPIJsonUtils
{
	private VertexRestAPIJsonUtils( )
	{
	}

	/**
	 * Helps to retrieve the specified JSON file in the project using the file name and end point to
	 * determine where to locate it.
	 * After locating the JSON file, uses the generic method in JsonFileUtils to get a JSON string
	 * from the file
	 *
	 * @param endPoint the Vertex Rest API end point that the post request should be sent to
	 * @param fileName the name of the JSON file
	 *
	 * @return a JSON string of the file
	 */
	public static String getRestApiStringFromJsonFile( EndPoint endPoint, String fileName )
	{
		String relativeFilePath = endPoint.getEndPoint() + "\\" + fileName;
		return VertexJsonFileUtils.getStringFromJsonFile(relativeFilePath);
	}

	/**
	 * Helps to retrieve the specified JSON file in the project using the file name and end point to
	 * determine where to locate it.
	 * After locating the JSON file, uses the generic method in JsonFileUtils to get the file
	 *
	 * @param endPoint the Vertex Rest API end point that the post request should be sent to
	 * @param fileName the name of the JSON file
	 *
	 * @return a JSON string of the file
	 */
	public static File getRestApiFileFromJsonFile( EndPoint endPoint, String fileName )
	{
		String relativeFilePath = endPoint.getEndPoint() + "\\" + fileName;
		return VertexJsonFileUtils.getFileFromJsonFile(relativeFilePath);
	}
}
