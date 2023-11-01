package com.vertex.quality.connectors.salesforce.enums;

public enum VersionDetails
{
	ID("VertexInc"),
	ACTIVE_STATUS("Active"),
	WebDAV(
		"https://astound29-alliance-prtnr-eu04-dw.demandware.net/on/demandware.servlet/webdav/Sites/Cartridges/VertexInc"),
	CARTRIDGES(
		"app_storefront_core, app_storefront_controllers, app_storefront_pipelines, appstore_storefront_core, appstore_storefront_controllers, bm_vertex, int_vertex, appstore_storefront_pipelines");

	public String text;

	VersionDetails( String text )
	{
		this.text = text;
	}
}
