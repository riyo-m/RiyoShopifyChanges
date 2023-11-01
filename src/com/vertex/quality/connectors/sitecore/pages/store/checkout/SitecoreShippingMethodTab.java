package com.vertex.quality.connectors.sitecore.pages.store.checkout;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * represents shipping method tab in sitecore checkout page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreShippingMethodTab extends SitecoreBasePage
{

	protected By shippingMethodDropdown = By.className("shippingoption-control");
	protected By shippingMethods = By.id("shippingMethods");
	protected By shippingTypeLabel = By.tagName("label");
	protected By shippingTypeButton = By.tagName("input");

	public SitecoreShippingMethodTab( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to select the shipping method and type.
	 *
	 * @param shippingMethod   shipping method pickup or delivery
	 * @param shippingTypeText type of shipping ground, air, etc
	 */
	public void selectShippingMethod( final String shippingMethod, final String shippingTypeText )
	{
		wait.waitForElementDisplayed(shippingMethodDropdown);
		dropdown.selectDropdownByDisplayName(shippingMethodDropdown, shippingMethod);

		if ( shippingTypeText != null )
		{
			WebElement shippingTypeContainer = wait.waitForElementEnabled(shippingMethods);
			List<WebElement> shippingTypes = wait.waitForAllElementsEnabled(shippingTypeLabel, shippingTypeContainer);

			int shippingIndex = element.findElementPositionByText(shippingTypes, shippingTypeText);

			final String paymentErr = String.format("Payment Type: %s is not available under Shipping Method: %s",
				shippingTypeText, shippingMethod);
			assertTrue(shippingIndex != -1, paymentErr);

			List<WebElement> shippingTypeElements = element.getWebElements(shippingTypeButton, shippingTypeContainer);
			WebElement shippingTypeElement = shippingTypeElements.get(shippingIndex);

			click.clickElementCarefully(shippingTypeElement);
		}
		else
		{
			final String paymentErr = String.format(
				"\nPayment Type: %s is not valid.\nPlease provide valid Payment type....", null);
			VertexLogger.log(paymentErr, VertexLogLevel.ERROR);
		}
	}
}
