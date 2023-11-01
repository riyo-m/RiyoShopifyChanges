package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for AR Transaction Type tab
 *
 * @author mgaikwad
 */

public enum ArTransactionType
{
	ARTransactionTypeDetails("AR Transaction Type", "Add AR Transaction Type", "View AR Transaction Type",
		"Edit AR Transaction Type");

	public String headerARTransactionTypePage;
	public String headerAddARTransactionTypePage;
	public String headerViewARTransactionTypePage;
	public String headerEditARTransactionTypePage;

	ArTransactionType( String headerARTransactionTypePage, String headerAddARTransactionTypePage,
		String headerViewARTransactionTypePage, String headerEditARTransactionTypePage )
	{
		this.headerARTransactionTypePage = headerARTransactionTypePage;
		this.headerAddARTransactionTypePage = headerAddARTransactionTypePage;
		this.headerViewARTransactionTypePage = headerViewARTransactionTypePage;
		this.headerEditARTransactionTypePage = headerEditARTransactionTypePage;
	}
}
