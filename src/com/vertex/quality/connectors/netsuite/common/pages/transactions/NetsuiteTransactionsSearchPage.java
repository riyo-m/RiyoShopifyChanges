package com.vertex.quality.connectors.netsuite.common.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The transactions search page
 *
 * @author hho
 */
public class NetsuiteTransactionsSearchPage extends NetsuiteSearchPage
{
	public NetsuiteTransactionsSearchPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuitePage> T submitSearch( )
	{
		click.clickElement(submitButton);
		return initializePageObject(NetsuiteTransactionsSearchResultPage.class);
	}

	/**
	 * Enters the transaction number into the search in the "TRANSACTION NUMBER/ID" text field
	 *
	 * @param transactionNumber the transaction number to input
	 */
	public void enterTransactionNumberAndId( String transactionNumber )
	{
		By transactionNumberTextField = By.xpath("//input[@id='Transaction_TRANSACTIONNUMBERTEXT']");
		text.enterText(transactionNumberTextField, transactionNumber);
	}

	/**
	 * Enters the document number into the search under the "VALUE" text field
	 *
	 * @param documentNumber the document number to input
	 */
	public void enterDocumentNumberValue( String documentNumber )
	{
		By documentNumberTextField = By.id("Transaction_NUMBERfrom_formattedValue");
		text.enterText(documentNumberTextField, documentNumber);
	}
}
