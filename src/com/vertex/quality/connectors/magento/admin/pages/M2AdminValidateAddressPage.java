package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represent the page for validate address for address cleansing.
 *
 * @author Mayur.Kumbhar
 */
public class M2AdminValidateAddressPage extends VertexPage
{
	public M2AdminValidateAddressPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By validateAddressButton=By.xpath("//button[@title='Validate address']");
	protected By sameBillingCheckBox=By.xpath("//input[@name='shipping_same_as_billing']");
	protected By validateShippingAddressButton=By.xpath("//button[@data-ui-id='widget-button-1']");
	protected By addressSuggestMessage = By.className("vertex__address-suggest_message");
	protected By suggestedAddress = By.className("vertex__address-suggestion");
	protected By addressWarning = By.xpath(".//div[@class='vertex__address-suggestion']/following-sibling::span");


	/**
	 * click on validate address button for address cleansing.
	 */
	public void clickOnValidateAddressButton()
	{
		WebElement validateAddressButtonField=wait.waitForElementDisplayed(validateAddressButton);
		click.moveToElementAndClick(validateAddressButtonField);
		waitForPageLoad();
	}

	/**
	 * click on same billing and ship address.
	 */
	public void clickOnSameBillingCheckBox()
	{
		WebElement sameBillCheckBoxField=wait.waitForElementDisplayed(sameBillingCheckBox);
		click.moveToElementAndClick(sameBillCheckBoxField);
	}

	/**
	 * click on validate shipping address button
	 */
	public void clickOnValidateShippingAddressButton()
	{
		WebElement validateShippingAddressButtonField=wait.waitForElementEnabled(validateShippingAddressButton);
		click.moveToElementAndClick(validateShippingAddressButtonField);
	}

	/**
	 * Reads address suggestion message from the UI
	 *
	 * @return address suggestion message
	 */
	public String getAddressSuggestionMessageFromUI() {
		waitForPageLoad();
		String suggestedMessage = text.getElementText(wait.waitForElementPresent(addressSuggestMessage));
		VertexLogger.log(suggestedMessage);
		return suggestedMessage;
	}

	/**
	 * Reads address warning message
	 *
	 * @return warning message
	 */
	public String getWarningMessageFromUI() {
		waitForPageLoad();
		return text.getElementText(wait.waitForElementPresent(addressWarning));
	}

	/**
	 * Reads suggested address from the UI
	 *
	 * @return suggested or cleansed address
	 */
	public String getSuggestedAddressFromUI() {
		waitForPageLoad();
		String address = text.getElementText(wait.waitForElementPresent(suggestedAddress));
		VertexLogger.log(address);
		return address;
	}
}
