package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.ThreadLocalRandom;

/**
 * this class represents the payment section in the checkout page for the Maxine live front store
 * contains methods necessary to interact with elements in that section to automate test cases
 *
 * @author osabha
 */
public class KiboCheckoutPagePayment extends VertexComponent
{
	protected By checkByMailButtonLoc = By.id("paymentType-check-0");
	protected By billingAddressCheckBoxLoc = By.cssSelector("input[data-mz-value='isSameBillingShippingAddress']");
	protected By checkByMailRadio = By.xpath(".//label[normalize-space()='Check by Mail']//preceding-sibling::input");
	protected By nameOnCheckBox = By.id("nameOnCheck");
	protected By routingNumberBox = By.xpath(".//input[@data-mz-value='check.routingNumber']");
	protected By checkNumberBox = By.xpath(".//input[@data-mz-value='check.checkNumber']");
	protected By paymentNextButton = By.xpath("(.//button[text()='Next'])[3]");

	public KiboCheckoutPagePayment( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * this method clicks on the Check by mail checkbox in the payment section in the checkout page
	 * for the Maxine live front store after locating it
	 * uses Javascript click to optimize test performance
	 */
	public void clickCheckByMail( )
	{
		WebElement checkByMail = wait.waitForElementPresent(checkByMailButtonLoc);

		scroll.scrollElementIntoView(checkByMail);

		checkByMail.click();
		System.out.println("clicked on check by mail checkBox");
	}

	/**
	 * to locate and click on the check box to take the billing address info
	 * from the shipping address
	 */
	public void clickBillingSameAsShippingAddress( )
	{
		WebElement billingShippingAddressCheckBox = wait.waitForElementEnabled(billingAddressCheckBoxLoc);
		scroll.scrollElementIntoView(billingShippingAddressCheckBox);
		billingShippingAddressCheckBox.click();
	}

	/**
	 * Pay for the order with the option: Check by Mail
	 */
	public void payWithCheckByMail() {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementPresent(checkByMailRadio));
		WebElement checkName = wait.waitForElementPresent(nameOnCheckBox);
		scroll.scrollElementIntoView(checkName);
		text.enterTextIgnoreExceptionsAndRetry(nameOnCheckBox, KiboData.CUSTOMER_FIRSTNAME.value + " " + KiboData.CUSTOMER_LASTNAME.value);
		WebElement routingNo = wait.waitForElementPresent(routingNumberBox);
		scroll.scrollElementIntoView(routingNo);
		text.enterText(routingNumberBox, String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
		WebElement checkNo = wait.waitForElementPresent(checkNumberBox);
		scroll.scrollElementIntoView(checkNo);
		text.enterText(checkNumberBox, String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
	}

	/**
	 * Clicks next button after adding payment details
	 */
	public void goNextFromPayment() {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementPresent(paymentNextButton));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(paymentNextButton));
		waitForPageLoad();
	}
}
