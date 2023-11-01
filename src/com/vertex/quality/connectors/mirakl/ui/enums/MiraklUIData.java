package com.vertex.quality.connectors.mirakl.ui.enums;

/**
 * Data for Mirakl connector UI
 *
 * @author Rohit.Mogane
 */
public enum MiraklUIData
{
	MIRAKL_ALL_CONNECTIONS("All connections are running smoothly."),
	MIRAKL_STATUS_UP("Up"),
	MIRAKL_STATUS_DOWN("Down"),
	MIRAKL_CONNECTOR_VERSION("2.1.0-SNAPSHOT"),
	MIRAKL_CONNECTOR_URL(
		"https://auth-dev.mirakl.net/authorize?client_id=f_1S-8kNOvZhUAHfLcILl-0_3OQMJZw3o-PVM8wo&state=eyJvcmlnaW4iOiJodHRwczovL21pcmFrbC5kZXYudmVydGV4Y29ubmVjdG9ycy5jb20ifQ=="),
	MIRAKL_CONNECTOR_USERNAME("connectorsdevelopment@vertexinc.com"),
	MIRAKL_CONNECTOR_PASSWORD("R3d#Kan^jqs*S##b!fwz"),
	MIRAKL_HOMEPAGE_TITLE("Vertex Connector for Mirakl"),
	MIRAKL_CONNECTOR_STATUS_PAGE_TITLE("Connector Status"),
	MIRAKL_OPERATOR_PAGE_TITLE("Operator"),
	MIRAKL_MARKETPLACE_PAGE_TITLE("Marketplace"),
	MIRAKL_STORES_PAGE_TITLE("Stores"),
	MIRAKL_ORDERS_PAGE_TITLE("Orders");

	public String data;

	MiraklUIData( String data )
	{
		this.data = data;
	}
}
