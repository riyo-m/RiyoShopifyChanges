package com.vertex.quality.connectors.netsuite.oneworld.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCustomerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the customer page for One World
 *
 * @author hho
 */
public class NetsuiteOWCustomerPage extends NetsuiteCustomerPage
{
	protected By salesRepDropdownLoc = By.name("inpt_salesrep");
	protected By subsidiaryDropdownLoc = By.id("inpt_subsidiary5");
	protected By subsidiaryInContactsDropdownLoc = By.id("inpt_subsidiary14");

	protected String subsidiaryHeader = "Subsidiary";

	public NetsuiteOWCustomerPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Edits the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteOWCustomerPage edit( )
	{
		click.clickElement(editButton);
		return initializePageObject(NetsuiteOWCustomerPage.class);
	}

	/**
	 * Saves the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteOWCustomerPage save( )
	{
		click.clickElement(saveButton);
		return initializePageObject(NetsuiteOWCustomerPage.class);
	}

	/**
	 * Sets the sales rep dropdown to the value
	 *
	 * @param salesRep the sales rep to select
	 */
	public void selectSalesRep( String salesRep )
	{
		selectInformationTab();
		setDropdownToValue(salesRepDropdownLoc, salesRep);
	}

	/**
	 * Set the subsidiary dropdown to the value
	 *
	 * @param subsidiary the subsidiary to select
	 */
	public void selectSubsidiary( String subsidiary )
	{
		selectInformationTab();
		setDropdownToValue(subsidiaryDropdownLoc, subsidiary);
	}

	/**
	 * Selects the subsidiary in the contacts table in the contacts tab
	 *
	 * @param subsidiary the subsidiary to select
	 */
	public void selectSubsidiaryInContactsTable( String subsidiary )
	{
		selectInformationContactsTab();
		WebElement subsidiaryTableCell = tableComponent.getFocusedTableCell(contactTable, contactHeaderRow,
			subsidiaryHeader);
		tableComponent.focusOnInteractableTableCell(subsidiaryTableCell);
		setDropdownToValue(subsidiaryInContactsDropdownLoc, subsidiary);
		waitForPageLoad();
	}
}
