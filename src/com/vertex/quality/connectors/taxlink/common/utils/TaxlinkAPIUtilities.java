package com.vertex.quality.connectors.taxlink.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import org.apache.geronimo.mail.util.Base64;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.Assert.fail;

/**
 * Representation of various utilities used for Taxlink testing.
 *
 * @author msalomone, ajain
 */
public class TaxlinkAPIUtilities extends VertexAPITestUtilities
{
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();
	public String resourceFile = null;

	/**
	 * Decode information formatted in 64-bit encoding.
	 *
	 * @return Decoded information as a String.
	 */
	public String decode64( byte[] encoding )
	{
		byte[] byteContent = Base64.decode(encoding);
		String content = new String(byteContent, StandardCharsets.UTF_8);
		return content;
	}

	/**
	 * Return a filepath from the resource directory as a
	 * File object.
	 *
	 * @param fileName (String) Name of the file in the resource directory.
	 * @param fileType (String) Filetype of requested file (i.e. "xml")
	 *
	 * @return file The file from the resource dir represented as a File object.
	 */
	public File getFile( String fileName, String fileType )
	{
		Path resourcePath = this.getResourcePath(fileType);
		resourcePath = Paths.get(resourcePath.toString() + "/" + fileName);

		File file = new File(resourcePath.toString());

		return file;
	}

	/**
	 * Retrieve a file from connector-quality-java/ConnectorQuality/resources/taxlink.
	 * i
	 *
	 * @param resourceType - Type of file needed. Valid values are:
	 *                     "xml", "csv", or "excel".
	 *
	 * @return the resource folder as a string.
	 *
	 * @paramtype String
	 */
	public Path getResourcePath( String resourceType )
	{
		Path resourcePath = this.rootPath;

		if ( resourceType.equals("xml") )
		{
			String xmlDir = "/resources/xmlfiles/taxlink";
			resourcePath = Paths.get(resourcePath.toString() + xmlDir);
		}
		else if ( resourceType.equals("csv") )
		{
			String csvDir = "/resources/csvfiles/taxlink";
			resourcePath = Paths.get(resourcePath.toString() + csvDir);
		}
		else if ( resourceType.equals("excel") )
		{
			String excelDir = "/resources/templates/taxlink";
			resourcePath = Paths.get(resourcePath.toString() + excelDir);
		}
		else if ( resourceType.equals("zip") )
		{
			String excelDir = "/resources/zipfile/taxlink";
			resourcePath = Paths.get(resourcePath.toString() + excelDir);
		}
		else
		{
			VertexLogger.log(
				"Warning. Resource type specified is not valid. " + "Valid values include 'xml', 'csv', or 'excel'.",
				VertexLogLevel.WARN);
		}
		return resourcePath;
	}

	/**
	 * Get today's date in format provided by the user.
	 *
	 * @param format A string representing the desired date format.
	 *               Ex: "yyyy-mm-dd"
	 *
	 * @return String representation of LocalDateTime Object containing today's date.
	 */
	public String getTodaysDate( String format )
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();

		return dtf.format(now);
	}

	/**
	 * Generate result csv file and adds data in it
	 *
	 * @param dataAsArrList data in form of arraylist
	 * @param appendFlag
	 *
	 * @return databaseResultFile
	 */
	public void addDataIntoCsvFile( ArrayList dataAsArrList, boolean appendFlag, String resultFileName )
		throws IOException
	{
		File databaseResultFile = null;
		FileWriter fileWriter = null;
		databaseResultFile = getCSVFile(resultFileName);
		fileWriter = new FileWriter(databaseResultFile, appendFlag);

		String arraylistDataAsString = dataAsArrList.toString();
		arraylistDataAsString = arraylistDataAsString
			.replace("[", "")
			.replace("]", "")
			.replace(", ",",");

		if ( !appendFlag )
		{
			fileWriter.write(arraylistDataAsString);
		}
		else
		{
			fileWriter.append(arraylistDataAsString);
		}
		fileWriter.append("\n");
		fileWriter.close();
	}

	/**
	 * Converts the map into ArrayList
	 *
	 * @param columnDataMap
	 * @param rowNum
	 *
	 * @return arraylist values inform of arrayList
	 */
	public ArrayList<String> convertMapToList( Map<String, String> columnDataMap, int rowNum )
	{
		ArrayList arrayList = null;
		Set<String> keySet = columnDataMap.keySet();

		ArrayList<String> listOfKeys = new ArrayList<String>(keySet);

		Collection<String> values = columnDataMap.values();

		ArrayList<String> listOfValues = new ArrayList<>(values);

		if ( rowNum == 1 )
		{
			arrayList = listOfKeys;
		}
		else if ( rowNum > 1 )
		{
			arrayList = listOfValues;
		}
		return arrayList;
	}

	/**
	 * Gets the csv from specified folder location if already present.
	 * Else creates the new file
	 *
	 * @param csvFileName
	 *
	 * @return csvFiles - The file data as an array of bytes.
	 */
	public File getCSVFile( String csvFileName )
	{
		File csvFile = null;
		try
		{
			Path resourcePath = this.getResourcePath("csv");
			resourcePath = Paths.get(resourcePath.toString() + "/" + csvFileName);
			resourceFile = resourcePath.toString();
			csvFile = new File(resourceFile);
			if ( !csvFile.exists() )
			{
				csvFile.createNewFile(); // Create file only if it does not exist
			}
		}
		catch ( Exception ex )
		{
			VertexLogger.log("There was in issue in the getZip method. See printed stack trace " + "for details.",
				VertexLogLevel.ERROR);
			ex.printStackTrace();
			fail("Test failed when getting ZIP and updating tags.");
		}

		return csvFile;
	}
}
