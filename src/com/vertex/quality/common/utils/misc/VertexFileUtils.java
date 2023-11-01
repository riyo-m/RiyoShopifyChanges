package com.vertex.quality.common.utils.misc;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Utility class containing methods related to file handling and manipulation
 *
 * @author dgorecki
 */
public class VertexFileUtils
{
	private VertexFileUtils( )
	{
	}

	/**
	 * Generic utility method to delete all files in a specified directory (excluding subdirectories)
	 *
	 * @param dir the directory to delete the files from; note this method will fail
	 *            if the specified File object is not actually a directory
	 *
	 * @author ssalisbury dgorecki
	 */
	public static void deleteFilesInDirectory( final File dir )
	{
		List<File> fileList = retrieveFilesInDirectory(dir);

		if ( fileList != null )
		{
			for ( File file : fileList )
			{
				boolean deletionSuccessful;

				try
				{
					deletionSuccessful = file.delete();
				}
				catch ( Exception e )
				{
					deletionSuccessful = false;
					e.printStackTrace();
				}

				if ( !deletionSuccessful )
				{
					String filePath = file.getAbsolutePath();
					String message = String.format("Failed to delete file: %s", filePath);
					VertexLogger.log(message);
				}
			}
		}
	}

	/**
	 * Retrieves the files in a specified directory (excluding subdirectories)
	 *
	 * @param dir the directory to retrieve the list of files from; note tis method will fail
	 *            if the specified File object is not actually a directory
	 *
	 * @return A list of the files in the directory or null if an exception occurred or
	 * the specified File was not a directory
	 *
	 * @author ssalisbury dgorecki
	 */
	public static List<File> retrieveFilesInDirectory( final File dir )
	{
		List<File> files = null;
		String path = dir.toString();

		//first verify the File object is a directory
		if ( dir.isDirectory() )
		{
			//if so, then attempt to generate a list of the files in it
			try
			{
				File[] filesArray = dir.listFiles();

				if ( filesArray != null )
				{
					files = Arrays.asList(filesArray);
				}
			}
			catch ( Exception e )
			{
				String errorMessage = String.format("Unable to get list of files from directory %s", path);
				VertexLogger.log(errorMessage);
				e.printStackTrace();
			}
		}
		else
		{
			//if not, log an error and move on
			String errorMessage = String.format("The specified parameter %s is not a directory", path);
			VertexLogger.log(errorMessage);
		}

		return files;
	}

	/**
	 * Returns the contents of the file specified by a file path
	 *
	 * @param filepath the filepath to the file
	 *
	 * @return the string contents of the specified file or null if an exception occurs
	 *
	 * @author hho dgorecki
	 */
	public static String getFileContents( Path filepath )
	{
		String fileString = null;

		try
		{
			fileString = new String(Files.readAllBytes(filepath));
		}
		catch ( Exception e )
		{
			VertexLogger.log(e.toString());
			e.printStackTrace();
		}

		return fileString;
	}

	/**
	 * Unzip a specified file
	 *
	 * @param zipFilepath a string representing the location of the zipped file
	 * @param zipFilename a string representing the name of the zipped file
	 *
	 * @return a boolean indicating whether the operation was successful
	 *
	 * @author dgorecki
	 */
	public static boolean unzipFile( String zipFilepath, String zipFilename )
	{
		String expectedFileType = "zip";
		ZipFile zipFile = null;
		boolean unzipSuccessful = false;
		boolean createDestinationDirSuccessful = false;
		boolean isFileTypeValid;
		String destinationFolder = null;
		InputStream in = null;
		OutputStream out = null;

		try
		{
			//split the filename into the name and the file type
			String[] splitZipFileName = zipFilename.split("\\.");
			String fileName = splitZipFileName[0];
			String fileType = splitZipFileName[1];

			//verify the file is actually a zip file
			isFileTypeValid = expectedFileType.equals(fileType);

			if ( isFileTypeValid )
			{
				destinationFolder = String.format("%s\\%s", zipFilepath, fileName);

				String augmentedZipFileDirectoryName = String.format("\\%s", fileName);
				File destinationDir = new File(zipFilepath, augmentedZipFileDirectoryName);
				createDestinationDirSuccessful = destinationDir.mkdirs();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		if ( createDestinationDirSuccessful )
		{
			try
			{
				String fullZipFilePathName = String.format("%s\\%s", zipFilepath, zipFilename);
				zipFile = new ZipFile(fullZipFilePathName);

				Enumeration<? extends ZipEntry> entries = zipFile.entries();

				while ( entries.hasMoreElements() )
				{
					ZipEntry entry = entries.nextElement();
					File entryDestination = new File(destinationFolder, entry.getName());

					if ( entry.isDirectory() )
					{
						entryDestination.mkdirs();
					}
					else
					{
						entryDestination
							.getParentFile()
							.mkdirs();
						in = zipFile.getInputStream(entry);
						out = new FileOutputStream(entryDestination);

						IOUtils.copy(in, out);
					}
				}
				unzipSuccessful = true;
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if ( zipFile != null )
					{
						zipFile.close();
					}
					if ( in != null )
					{
						in.close();
					}
					if ( out != null )
					{
						out.close();
					}
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		}
		return unzipSuccessful;
	}

	/**
	 * Waits for a specified number of seconds for a file to be created
	 *
	 * @param file             a string path indicating the file to wait for
	 * @param timeoutInSeconds an int specifying the number of seconds to wait
	 *
	 * @return a boolean indicating whether the file was created or if the method timed out
	 *
	 * @author dgorecki
	 */
	public static boolean waitForFileToExist( String file, int timeoutInSeconds )
	{
		int count = 0;
		boolean isFilePresent = false;

		while ( !isFilePresent && (count < timeoutInSeconds) )
		{
			File temp = new File(file);
			isFilePresent = temp.exists();

			try
			{
				Thread.sleep(1000);
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}

			count++;
		}
		return isFilePresent;
	}

	/**
	 * It updates line in a Text File
	 * @param fileName full file location
	 * @param newLine Line to change to
	 * @param lineNumber Line Number that needs to be changed
	 */
	public static void changeALineInATextFile(String fileName, String newLine, int lineNumber) {
		String content;
		String editedContent;
		content = readFile(fileName);
		editedContent = editLineInContent(content, newLine, lineNumber);
		writeToFile(fileName, editedContent);
	}

	/**
	 * It gets Number of lines present in the text file
	 * @param content content of the file as a string
	 * @return Number of lines
	 */
	private static int numberOfLinesInFile(String content) {
		int numberOfLines = 0;
		int index = 0;
		int lastIndex;
		lastIndex = content.length() - 1;
		while (true) {

			if (content.charAt(index) == '\n') {
				numberOfLines++;
			}
			if (index == lastIndex) {
				numberOfLines = numberOfLines + 1;
				break;
			}
			index++;
		}
		return numberOfLines;
	}

	/**
	 * This is the helper method that turns file into Array of strings
	 * @param content content of the file as a string
	 * @param lines Lines in the file
	 * @return Array of Strings
	 */
	private static String[] turnFileIntoArrayOfStrings(String content, int lines) {
		String[] array = new String[lines];
		int index = 0;
		int tempInt = 0;
		int startIndext = 0;
		int lastIndex = content.length() - 1;

		while (true) {
			if (content.charAt(index) == '\n') {
				tempInt++;
				StringBuilder temp2 = new StringBuilder();
				for (int i = 0; i < index - startIndext; i++) {
					temp2.append(content.charAt(startIndext + i));
				}
				startIndext = index;
				array[tempInt - 1] = temp2.toString();
			}

			if (index == lastIndex) {
				tempInt++;
				StringBuilder temp2 = new StringBuilder();
				for (int i = 0; i < index - startIndext + 1; i++) {
					temp2.append(content.charAt(startIndext + i));
				}
				array[tempInt - 1] = temp2.toString();
				break;
			}
			index++;
		}
		return array;
	}

	/**
	 * It is helper Method to edit line content
	 * @param content  content of the file as a string
	 * @param newLine  Line to modify to
	 * @param line line number
	 * @return Edited line content
	 */
	private static String editLineInContent(String content, String newLine, int line) {

		int lineNumber;
		lineNumber = numberOfLinesInFile(content);

		String[] lines;
		lines = turnFileIntoArrayOfStrings(content, lineNumber);

		if (line != 1) {
			lines[line - 1] = "\n" + newLine;
		} else {
			lines[line - 1] = newLine;
		}

		StringBuilder contentBuilder = new StringBuilder();
		for (int i = 0; i < lineNumber; i++) {
			contentBuilder.append(lines[i]);
		}
		content = contentBuilder.toString();
		return content;
	}

	/**
	 * This is helper method to Write to a Text file
	 * @param file File to write in
	 * @param content Content to write as a String
	 */
	private static void writeToFile(String file, String content) {

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is Helper method to read text file
	 * @param filename name of the file
	 * @return Content of the file as a String
	 */
	private static String readFile(String filename) {
		String content = null;
		File file = new File(filename);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}


	/**
	 * It compares to Text files
	 * @param file1 First file
	 * @param file2 Second file
	 * @return true if files are identical
	 */
	public static boolean comparingTxtFile(String file1, String file2)
	{
		boolean areEqual = false;
		try{
			BufferedReader reader1 = new BufferedReader(new FileReader(file1));
			BufferedReader reader2 = new BufferedReader(new FileReader(file2));
			String line1 = reader1.readLine();
			String line2 = reader2.readLine();
			areEqual = true;
			int lineNum = 1;
			while (line1 != null || line2 != null)
			{
				if(line1 == null || line2 == null)
				{
					areEqual = false;
					break;
				}
				else if(! line1.equalsIgnoreCase(line2))
				{
					areEqual = false;
					break;
				}
				line1 = reader1.readLine();
				line2 = reader2.readLine();
				lineNum++;
			}
			if(areEqual)
			{
				VertexLogger.log("Two files have same content.");
			}
			else
			{
				VertexLogger.log("Two files have different content. They differ at line "+lineNum);
				VertexLogger.log("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
			}
			reader1.close();
			reader2.close();

		}
		catch (IOException e)  {
			e.printStackTrace();
		}
		return areEqual;
	}


	/**
	 * this method verify that particular string exists in the server logs+
	 *
	 * @param fileName       filename
	 * @param expectedString Expected String
	 * @return boolean true if string exists in the server logs
	 */
	public boolean verifyTextIsPresentInServerLogs(String fileName, String expectedString) {
		boolean isVerified = false;
		VertexLogger.log("Complete File Path: " + VertexPage.DOWNLOAD_DIRECTORY_PATH + File.separator + fileName);
		File file = new File(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName);
		Scanner scanner = null;

		try {
			scanner = new Scanner(file);
			int lineNum = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				if (line.contains(expectedString)) {
					VertexLogger.log(expectedString + " : Found in Line no: " + lineNum);
					isVerified = true;
				}
			}
		} catch(IOException e) {

			e.printStackTrace();
		}
		if (!isVerified) {
			VertexLogger.log(expectedString + ": Is not present in the server logs");
		}
		assert scanner != null;
		scanner.close();
		return isVerified;
	}

}
