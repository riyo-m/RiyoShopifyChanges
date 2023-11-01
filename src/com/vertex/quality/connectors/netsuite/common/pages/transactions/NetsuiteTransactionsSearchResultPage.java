package com.vertex.quality.connectors.netsuite.common.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The search results page for all transactions
 *
 * @author hho
 */
public class NetsuiteTransactionsSearchResultPage extends NetsuiteSearchResultsPage
{
	protected String typeHeader = "Type";

	public NetsuiteTransactionsSearchResultPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Gets the transaction type
	 *
	 * @param identifier the identifying element of the row
	 *
	 * @return the transaction type
	 */
	public String getTransactionType( String identifier )
	{
		WebElement cell = tableComponent.getTableCellByIdentifier(tableLocator, tableHeaderRowLocator, identifier,
			typeHeader);
		return cell.getText();
	}

	/**
	 * Gets the transaction type
	 *
	 * @param rowOrder the row's order in the table
	 *
	 * @return the transaction type
	 */
	public String getTransactionType( int rowOrder )
	{
		WebElement cell = tableComponent.getTableCellByCount(tableLocator, tableHeaderRowLocator, rowOrder, typeHeader);
		return cell.getText();
	}
}
