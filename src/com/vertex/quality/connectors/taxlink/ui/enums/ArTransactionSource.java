package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for AR Transaction Source tab
 *
 * @author mgaikwad
 */

public enum ArTransactionSource
{
	ArTransactionSourceDetails("AR Transaction Source", "Add AR Transaction Source", "View AR Transaction Source",
		"Edit AR Transaction Source");

	public String headerArTransactionSourcePage;
	public String headerAddArTransactionSourcePage;
	public String headerViewArTransactionSourcePage;
	public String headerEditArTransactionSourcePage;

	ArTransactionSource( String headerArTransactionSourcePage, String headerAddArTransactionSourcePage,
		String headerViewArTransactionSourcePage, String headerEditArTransactionSourcePage )
	{
		this.headerArTransactionSourcePage = headerArTransactionSourcePage;
		this.headerAddArTransactionSourcePage = headerAddArTransactionSourcePage;
		this.headerViewArTransactionSourcePage = headerViewArTransactionSourcePage;
		this.headerEditArTransactionSourcePage = headerEditArTransactionSourcePage;
	}
}
