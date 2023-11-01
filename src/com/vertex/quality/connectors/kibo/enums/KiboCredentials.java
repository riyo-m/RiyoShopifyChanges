package com.vertex.quality.connectors.kibo.enums;

/**
 * contains most of the Credentials for the test environment and connector configurations
 *
 * @author osabha
 */
public enum KiboCredentials
{
	OSERIES_URL("http://oseries8-dev.vertexconnectors.com:8095/vertex-ws/services/CalculateTax80?wsdl"),
	COMPANY_CODE("100"),
	OSERIES_PASSWORD("kibotest!"),
	OSERIES_USERNAME("kibotest"),
	CONFIG_SIGN_ON_URL("https://t25892.sandbox.mozu.com/Admin/m-1/"),
	KIBO_STORE_URL("https://t25892-s39795.sandbox.mozu.com/"),
	KIBO_STORE_USER1("user1@vertexinc.com"),
	CONFIG_USERNAME("connectorsdevelopment@vertexinc.com"),
	CONFIG_PASSWORD("vertex1");

	public String value;

	KiboCredentials( String val )
	{
		this.value = val;
	}
}
