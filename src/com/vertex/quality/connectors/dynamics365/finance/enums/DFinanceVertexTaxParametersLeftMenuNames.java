package com.vertex.quality.connectors.dynamics365.finance.enums;

/**
 * Vertex tax parameters page left menu names
 *
 * @author Saidulu Kodadala
 */
public enum DFinanceVertexTaxParametersLeftMenuNames
{
	VERTEX_SETTINGS("Vertex Settings"),
	TAX_GROUP_SETTINGS("General"),
	ADDRESS_VALIDATION("Invoice Text Codes");

	String dataName;

	DFinanceVertexTaxParametersLeftMenuNames( String data )
	{
		this.dataName = data;
	}

	/**
	 * Get the data from enum
	 *
	 * @return
	 */
	public String getData( )
	{
		return dataName;
	}
}
