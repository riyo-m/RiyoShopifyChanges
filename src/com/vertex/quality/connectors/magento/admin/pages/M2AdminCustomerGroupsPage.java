package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Customer Groups Page
 *
 * @author alewis
 */
public class M2AdminCustomerGroupsPage extends MagentoAdminPage
{
	protected By addNewCustomerGroupButtonId = By.id("add");

	public M2AdminCustomerGroupsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * click the Add New Customer Group button to navigate
	 * to the New Customer Group page
	 *
	 * @return New Customer Group page
	 */
	public M2AdminAddNewCustomerGroupPage clickAddNewCustomerGroupButton( )
	{
		WebElement addNewButton = wait.waitForElementEnabled(addNewCustomerGroupButtonId);

		click.clickElement(addNewButton);

		return initializePageObject(M2AdminAddNewCustomerGroupPage.class);
	}
}
