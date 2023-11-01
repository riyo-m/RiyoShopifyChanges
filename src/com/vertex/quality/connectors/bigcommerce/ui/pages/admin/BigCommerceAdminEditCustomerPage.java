package com.vertex.quality.connectors.bigcommerce.ui.pages.admin;

import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base.BigCommerceAdminPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * this page allows you to modify the details of a customer profile on the Big Commerce site
 *
 * @author ssalisbury
 */
public class BigCommerceAdminEditCustomerPage extends BigCommerceAdminPostLoginBasePage
{
	//this field shows up in Big Commerce API Customer data structures as 'taxability_code'
	// It is also mapped by the connector to the o-series 'customer code' value
	protected final By taxExemptCodeField = By.id("taxExemptCategory");

	protected final By saveExitButton = By.className("button--primary");

	public BigCommerceAdminEditCustomerPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * writes a value into Big Commerce's 'tax exempt code' field for the customer
	 *
	 * @param taxExemptCodeValue the customer's new tax exempt code
	 */
	public void setTaxExemptCode( final String taxExemptCodeValue )
	{
		wait.waitForElementEnabled(taxExemptCodeField);
		text.enterText(taxExemptCodeField, taxExemptCodeValue);
	}

	/**
	 * saves the pending changes to the customer and returns to the customers management page
	 *
	 * @return the customers management page
	 */
	public BigCommerceAdminViewCustomersPage submitEdits( )
	{
		wait.waitForElementEnabled(saveExitButton);
		click.clickElement(saveExitButton);
		BigCommerceAdminViewCustomersPage customersPage = initializePageObject(BigCommerceAdminViewCustomersPage.class);
		return customersPage;
	}
}
