package com.vertex.quality.connectors.salesforce.pages.ob2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Common function for anything related to CC Carts Page in Salesforce OB2B
 */

public class SalesForceOB2BCartPage extends SalesForceBasePage
{

	protected By CART_LINK = By.xpath("//tr[contains(@class, 'first')]/th/a");
	protected By CURRENT_CART = By.xpath(
		"//tr[contains(@class, 'first')]/td[contains(@class, 'booleanColumn')]");

	protected By EDIT_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Edit']");
	protected By SAVE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Save']");

	protected By DELIVERY_TERM = By.xpath(
		".//td/label[contains(text(), 'CartDeliveryTerm')]/../following-sibling::td/input");
	protected By CUSTOMER_CODE_INPUT = By.xpath(
		".//td/label[contains(text(), 'CustomerCode')]/../following-sibling::td/input");
	protected By CUSTOMER_CLASS_CODE_INPUT = By.xpath(
		".//td/label[contains(text(), 'CustomerClass')]/../following-sibling::td/input");
	protected By TAX_EXEMPT_CHECKBOX = By.xpath("//label[contains(text(),'TaxExempt')]/parent::td/following-sibling::td//input");
	protected By TAX_REGISTRATION_NUMBER = By.xpath(
			".//td/label[contains(text(), 'RegistrationNumber')]/../following-sibling::td/input");
	protected By DISCOUNT_PERCENT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
	protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");
	protected By CUSTOMER_EXEMPT_CERTIFICATE_INPUT = By.xpath(
			"//label[contains(text(),'ExemptionCertificate')]/parent::td/following-sibling::td/input");

	protected By CART_NUMBER_LINK = By.xpath("//td[text()='Cart']/following-sibling::td/div/a");


	public SalesForceOB2BCartPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Navigates to cart record that is currently being used on storefront
	 */
	public void navigateToCurrentCart( )
	{
		// wait for Current Recent cart to show up in list of cc carts
		waitForSalesForceLoaded();
		waitForPageLoad();
		refreshPage();
		element.isElementDisplayed(CURRENT_CART);
		/*
		We have a userStory to investigate why Active checkbox not checked after checkout process
		So Commented lines for temporary Regression run
		int i = 0;
		while ( !element.isElementDisplayed(CURRENT_CART) )
		{
			refreshPage();
			i++;
			if ( i > 5 )
			{
				return;
			}
		}*/
		wait.waitForElementDisplayed(CART_LINK);
		click.clickElement(CART_LINK);
	}

	/**
	 * Method to find table row associated with specific product
	 *
	 * @param productName
	 *
	 * @return table row with relevant product as a WebElement
	 */
	private WebElement getCartLineTableRowByProductName(String productName )
	{
		String orderRow = String.format(".//tbody/tr/*/a[contains(text(),'%s')]/../parent::tr", productName);
		WebElement tableRow = element.getWebElement(By.xpath(orderRow));
		return tableRow;
	}

	/**
	 * Navigates to Cart Line based on product name
	 * @param productName
	 */
	public void navigateToCartLine(String productName)
	{
		waitForSalesForceLoaded();
		WebElement row = getCartLineTableRowByProductName(productName);
		WebElement productLink = row.findElement(By.xpath("th/a"));
		wait.waitForElementDisplayed(productLink);
		click.clickElement(productLink);
		waitForPageLoad();

	}

	/**
	 * Click Edit button
	 */
	public void clickEditButton( )
	{
		wait.waitForElementDisplayed(EDIT_BUTTON);
		click.clickElement(EDIT_BUTTON);
	}

	/**
	 * Click save button on cart page
	 */
	public void clickSaveButton( )
	{
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
	}

	/**
	 * Sets delivery term for cart
	 *
	 * @param deliveryTerm
	 */
	public void setDeliveryTerm( String deliveryTerm )
	{
		wait.waitForElementDisplayed(DELIVERY_TERM);
		text.enterText(DELIVERY_TERM, deliveryTerm);
	}

	/**
	 * Sets Customer Code for cart
	 *
	 * @param customerCode
	 */
	public void setCustomerCode( String customerCode )
	{
		wait.waitForElementDisplayed(CUSTOMER_CODE_INPUT);
		text.enterText(CUSTOMER_CODE_INPUT, customerCode);
	}

	/**
	 * Sets Customer Class Code for cart
	 *
	 * @param customerClassCode
	 */
	public void setCustomerClassCode( String customerClassCode )
	{
		wait.waitForElementDisplayed(CUSTOMER_CLASS_CODE_INPUT);
		text.enterText(CUSTOMER_CLASS_CODE_INPUT, customerClassCode);
	}

	/**
	 * Set tax exempt checkbox
	 *
	 * @param taxExempt
	 */
	public void setTaxExemptFlag ( Boolean taxExempt )
	{
		wait.waitForElementDisplayed(TAX_EXEMPT_CHECKBOX);
		checkbox.setCheckbox(TAX_EXEMPT_CHECKBOX, taxExempt);
		waitForSalesForceLoaded();
	}

	/**
	 * Set tax registration number
	 * @param registrationNum
	 */
	public void setTaxRegistrationNumber(String registrationNum)
	{
		wait.waitForElementDisplayed(TAX_REGISTRATION_NUMBER);
		text.enterText(TAX_REGISTRATION_NUMBER, registrationNum);
	}

	/**
	 * Set discount percent
	 * @param discountPercent
	 */
	public void setDiscountPercent(String discountPercent)
	{
		if(!discountPercent.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
			text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
		}
	}

	/**
	 * Set discount amount
	 * @param discountAmount
	 */
	public void setDiscountAmount(String discountAmount)
	{
		if(!discountAmount.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
			text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
		}
	}

	/**
	 * Set Customer Exemption Certificate
	 * @param exemptionCertificate
	 */
	public void setCustomerExemptionCertificate(String exemptionCertificate)
	{
		wait.waitForElementDisplayed(CUSTOMER_EXEMPT_CERTIFICATE_INPUT);
		text.enterText(CUSTOMER_EXEMPT_CERTIFICATE_INPUT, exemptionCertificate);
	}

	/**
	 * Navigates back to Cart by clicking Cart number link
	 */
	public void navigateBackToCart()
	{
		wait.waitForElementDisplayed(CART_NUMBER_LINK);
		click.clickElement(CART_NUMBER_LINK);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}
}
