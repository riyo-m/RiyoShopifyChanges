package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontCustomerExemptPage extends ShopifyPage
{
	protected By emailField = By.id("email");
	protected By countryFields = By.name("countryCode");
	protected By firstNameFields = By.xpath("(//input[@name='firstName'])[1]");
	protected By lastNameField = By.xpath("(//input[@name='lastName'])[1]");
	protected By addressFields = By.xpath("(//input[@name='address1'])[1]");
	protected By cityFields = By.xpath("(//input[@name='city'])[1]");
	protected By stateFields = By.xpath("//select[@name='zone']");
	protected By postalCodeField = By.xpath("(//input[@name='postalCode'])[1]");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontCustomerExemptPage( final WebDriver driver )
	{
		super(driver);
	}
}
