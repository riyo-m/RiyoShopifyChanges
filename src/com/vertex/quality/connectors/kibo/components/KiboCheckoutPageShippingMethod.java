package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the shipping method portion of the checkout page from the storefront
 * contains all the methods necessary to interact with the the shipping method buttons
 *
 * @author osabha
 */
public class KiboCheckoutPageShippingMethod extends VertexComponent
{
	protected By nextButtonLoc = By.className("mz-button");
	protected By shippingMethodContainerLoc = By.id("step-shipping-method");
	protected By noShippingMethodAvailableMSG = By.xpath(".//p[text()='Sorry, no shipping methods are available.']");
	protected By shippingFlatRate = By.xpath(".//label[contains(normalize-space(),'Flat Rate')]//input");

	public KiboCheckoutPageShippingMethod( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * Selects the flat rate checkbox  from the shipping method section in the checkout page
	 * and clicks on it
	 */
	public void selectFlatRateShipping( )
	{
		String expectedText = "Flat Rate $15.00";
		WebElement shippingMethodsCont = wait.waitForElementDisplayed(shippingMethodContainerLoc);
		List<WebElement> shippingMethodsOptions = wait.waitForAllElementsEnabled(By.tagName("label"),
			shippingMethodsCont);
		WebElement flatShippingOption = element.selectElementByText(shippingMethodsOptions, expectedText);
		wait.waitForElementEnabled(flatShippingOption);
		click.clickElementCarefully(flatShippingOption);
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * gets the next button between the shipping method and payment sections and clicks on it
	 */
	public void clickNextToPayment( )
	{
		WebElement nextButtonContainer = wait.waitForElementPresent(shippingMethodContainerLoc);
		WebElement nextButton = wait.waitForElementDisplayed(nextButtonLoc, nextButtonContainer);
		click.clickElementCarefully(nextButton);
		VertexLogger.log("clicked on next to payment button");
	}

	/**
	 * Selects Flat Rate as shipping method
	 */
	public void selectFlatRateForShipping() {
		waitForPageLoad();
		if (!element.isElementPresent(noShippingMethodAvailableMSG)) {
			click.moveToElementAndClick(wait.waitForElementPresent(shippingFlatRate));
		}
	}
}
