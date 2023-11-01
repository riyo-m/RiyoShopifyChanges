package com.vertex.quality.connectors.netsuite.singlecompany.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCustomerPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the customer page for Single Company
 *
 * @author hho
 */
public class NetsuiteSCCustomerPage extends NetsuiteCustomerPage
{
	public NetsuiteSCCustomerPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Edits the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteSCCustomerPage edit( )
	{
		click.clickElement(editButton);
		return initializePageObject(NetsuiteSCCustomerPage.class);
	}

	/**
	 * Saves the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteSCCustomerPage save( )
	{
		click.clickElement(saveButton);
		return initializePageObject(NetsuiteSCCustomerPage.class);
	}
}
