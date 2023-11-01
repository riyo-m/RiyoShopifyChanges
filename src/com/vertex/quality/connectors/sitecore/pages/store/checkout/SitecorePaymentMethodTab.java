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
 * represents payment method tab in sitecore checkout page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecorePaymentMethodTab extends SitecoreBasePage
{

	protected By paymentMethodDropdown = By.className("paymentoption-control");
	protected By paymentMethods = By.id("paymentMethods");
	protected By paymentTypeLabel = By.tagName("label");
	protected By paymentTypeButton = By.tagName("input");

	public SitecorePaymentMethodTab( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to select the Payment method and type
	 *
	 * @param paymentMethod   i.e. pay card, online payment
	 * @param paymentTypeText i.e. credit card, debit card
	 */
	public void selectPaymentMethod( final String paymentMethod, final String paymentTypeText )
	{
		wait.waitForElementEnabled(paymentMethodDropdown);
		dropdown.selectDropdownByDisplayName(paymentMethodDropdown, paymentMethod);

		if ( paymentTypeText != null )
		{
			waitForPageLoad();
			WebElement paymentTypeContainer = wait.waitForElementDisplayed(paymentMethods);
			List<WebElement> paymentTypes = wait.waitForAnyElementsDisplayed(paymentTypeLabel, paymentTypeContainer);

			int paymentIndex = element.findElementPositionByText(paymentTypes, paymentTypeText);

			assertTrue(paymentIndex != -1,
				String.format("Payment Type: %s is not available under Payment Method: %s", paymentTypeText,
					paymentMethod));

			List<WebElement> paymentTypeElements = element.getWebElements(paymentTypeButton, paymentTypeContainer);
			WebElement paymentTypeElement = paymentTypeElements.get(paymentIndex);

			wait.waitForElementEnabled(paymentTypeElement);
			click.clickElement(paymentTypeElement);
			waitForPageLoad();
		}
		else
		{
			VertexLogger.log(String.format("\nPayment Type: %s is not valid.\nPlease provide valid Payment type....",
				paymentTypeText), VertexLogLevel.ERROR);
		}
	}
}
