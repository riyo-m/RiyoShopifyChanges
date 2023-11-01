package com.vertex.quality.connectors.ariba.portal.enums;

/**
 * contains the different ariba connector endpoints with their urls
 *
 * @author osabha
 */
public enum AribaConnectorIntegrationEndpoints
{
	ON_DEMAND_IP("On Demand IP", "https://12.96.60.51/vertex-ariba/ariba"),
	VERTEX_API_1_PROD("Vertex API1 PROD", "http://connector-prod.vertexconnectors.com:8096/vertex-ariba/ariba"),
	VERTEX_API_2_PROD("Vertex API2 PROD", "http://connector-prod.vertexconnectors.com:8111/vertex-ariba/ariba"),
	VERTEX_DEMO("VertexDemo", "http://10.138.101.232:8088/mockTaxServiceExportBinding"),
	VERTEX_DEV_API_2("Vertex_DevAPI2", "http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/ariba");

	private String endpoint;
	private String url;

	AribaConnectorIntegrationEndpoints( final String endpoint, final String url )
	{
		this.endpoint = endpoint;
		this.url = url;
	}

	public String getEndpoint( )
	{
		return this.endpoint;
	}

	public String getUrl( ) {return this.url;}
}
