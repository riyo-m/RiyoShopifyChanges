package com.vertex.quality.common.utils.properties;

public class CommonDataProperties
{
	private CommonDataProperties( )
	{
	}

	public static String PROJECT_PATH = ".";

	public static String ENV_PROP_FILE_PATH = "resources/properties/EnvironmentURL.properties";
	public static String TEST_CREDENTIALS_FILE_PATH = "resources/properties/TestCredentials.properties";
	public static String CONFIG_DETAILS_FILE_PATH = "resources/properties/ConfigurationSettings.properties";
	public static String COMPANY_DATA_FILE_PATH="resources/properties/CompanyData.properties";

	public static String EMAIL = "ConnectorTestAutomation@vertexinc.com"; // since this is common across all connectors
	public static String COMPANY_NAME = "Vertex"; // since this is common across all connectors

	public static String FIRST_NAME = "Auto";
	public static String LAST_NAME = "Test";
	public static String TITLE = "Mr.";
	public static String FULL_NAME = "Automation Test User";
	public static String COMMENT = "Automation Test Comment";
	public static String PHONE = "235-465-7890";

	public static int DEFAULT_QUANTITY = 1;
}
