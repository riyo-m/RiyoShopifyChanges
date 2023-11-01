package com.vertex.quality.connectors.netsuite.common.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the customer search page
 *
 * @author hho
 */
public class NetsuiteCustomerSearchPage extends NetsuiteSearchPage
{
	protected By nameOrIdTextField = By.id("Entity_ENTITYID");

	public NetsuiteCustomerSearchPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuitePage> T submitSearch( )
	{
		click.clickElement(submitButton);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Enters the name or id into the textfield
	 *
	 * @param nameOrId the name or id
	 */
	public void enterNameOrId( String nameOrId )
	{
		text.enterText(nameOrIdTextField, nameOrId);
	}
}
