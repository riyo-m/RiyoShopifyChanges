package com.vertex.quality.common.utils.properties;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties
{
	private String propertiesFilePath;
	private Properties properties;
	private FileInputStream input = null;

	public ReadProperties( String propertiesFilePath )
	{
		this.propertiesFilePath = propertiesFilePath;
		this.properties = new Properties();

		try
		{
			this.input = new FileInputStream(this.propertiesFilePath);
		}
		catch ( FileNotFoundException e )
		{
			String missingFileMessage = String.format("The given properties file: %s  is not found",
				this.propertiesFilePath);
			VertexLogger.log(missingFileMessage, VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * extracts the property and returns it's value as a string
	 *
	 * @param propertyName name of property to extract
	 *
	 * @return property value
	 */
	private String extractProperty( String propertyName )
	{
		String propertyValue = null;

		try
		{
			properties.load(this.input);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		propertyValue = this.properties.getProperty(propertyName);

		return propertyValue;
	}

	/**
	 * uses the extract property method to get the property value and then returns it as a string.
	 *
	 * @param propertyName name of property to extract
	 *
	 * @return property value
	 */
	public String getProperty( String propertyName )
	{
		return extractProperty(propertyName);
	}

	/**
	 * uses the extract property method to get the property value and then returns it as an integer.
	 *
	 * @return parsed value as integer.
	 */
	public int getPropertyAsInteger( String propertyName )
	{
		int parsedValue = -1;
		String propertyValue = extractProperty(propertyName);

		try
		{
			parsedValue = Integer.parseInt(propertyValue);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return parsedValue;
	}

	/**
	 * closes the file.
	 */
	public void close( )
	{
		if ( this.input != null )
		{
			try
			{
				this.input.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}
}
