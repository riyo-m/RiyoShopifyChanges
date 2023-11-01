package com.vertex.quality.connectors.oraclecloud.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.common.utils.VertexLogger;
import org.apache.geronimo.mail.util.Base64;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Representation of various utilities used for Oracle testing.
 *
 * COERPC-3434
 *
 * @author msalomone
 */
public class OracleUtilities extends VertexAPITestUtilities
{
	public Path rootPath = Paths.get("").toAbsolutePath();

	/**
	 * Decode information formatted in 64-bit encoding.
	 * @return Decoded information as a String.
	 */
	public String decode64(byte[] encoding) {
		byte[] byteContent = Base64.decode(encoding);
		String content = new String(byteContent, StandardCharsets.UTF_8);
		return content;
	}

	/**
	 * Return a filepath from the resource directory as a
	 * File object.
	 * @param fileName (String) Name of the file in the resource directory.
	 * @param fileType (String) Filetype of requested file (i.e. "xml")
	 *
	 * @return file The file from the resource dir represented as a File object.
	 */
	public File getFile(String fileName, String fileType) {
		Path resourcePath = this.getResourcePath(fileType);
		resourcePath = Paths.get(resourcePath.toString()+"/"+fileName);

		File file = new File(resourcePath.toString());

		return file;
	}

	/**
	 * Returns a random alpha character.
	 *
	 * @return A randomly selected capitalized character from A - Z
	 */
	public String getRandomLetter() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int k = alphabet.length();
		Random rand = new Random();
		char randomChar = alphabet.charAt(rand.nextInt(k));
		String charAsStr = String.valueOf(randomChar);
		return charAsStr;
	}

	/**
	 * Returns a single digit number.
	 *
	 * @return Randomly selected number 0-9
	 */
	public String getRandomDigit() {
		int p = 9;
		Random rand = new Random();
		int randomDigit = rand.nextInt(p);
		String digitAsStr = Integer.toString(randomDigit);
		return digitAsStr;
	}

	/**
	 * Retrieve a file from connector-quality-java/ConnectorQuality/resources/oracle.
	 *i
	 * @param resourceType - Type of file needed. Valid values are:
	 *                       "xml", "json", "csv", or "excel".
	 * @paramtype String
	 *
	 * @return the resource folder as a string.
	 */
	public Path getResourcePath(String resourceType)
	{
		Path resourcePath = this.rootPath;

		if(resourceType.equals("xml")) {
			String xmlDir = "/resources/xmlfiles/oracle";
			resourcePath = Paths.get(resourcePath.toString() + xmlDir);
		} else if(resourceType.equals("json")) {
			String jsonDir = "/resources/jsonfiles/oracle";
			resourcePath = Paths.get(resourcePath.toString() + jsonDir);
		} else if(resourceType.equals("csv")) {
			String csvDir = "/resources/csvfiles/oracle";
			resourcePath = Paths.get(resourcePath.toString() + csvDir);
		} else if(resourceType.equals("excel")) {
			String excelDir = "/resources/templates/oracle";
			resourcePath = Paths.get(resourcePath.toString() + excelDir);
		} else {
			VertexLogger.log("Warning. Resource type specified is not valid. " +
							 "Valid values include 'xml', 'json', 'csv', or 'excel'.", VertexLogLevel.WARN);
		}
		return resourcePath;
	}

	/**
	 * Get today's date in format provided by the user.
	 *
	 * @param format A string representing the desired date format.
	 *               Ex: "yyyy-mm-dd"
	 * @return String representation of LocalDateTime Object containing today's date.
	 */
	public String getTodaysDate(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();

		return dtf.format(now);
	}
}
