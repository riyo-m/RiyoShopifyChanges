package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the billing information page
 *
 * @author alewis
 */
public class M2StorefrontBillingInformationPage extends MagentoStorefrontPage
{
	protected By checkMoneyOrderMethodId = By.id("p_method_checkmo");

	protected By reviewOrderButtonId = By.id("payment-continue");

	By maskClass = By.className("loading-mask");

	public M2StorefrontBillingInformationPage( WebDriver driver ) { super(driver); }

	/**
	 * select check/money order as the payment method
	 */
	public void selectCheckAsPaymentMethod( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementDisplayed(checkMoneyOrderMethodId);
		WebElement checkMoneyOrder = wait.waitForElementEnabled(checkMoneyOrderMethodId);

		checkMoneyOrder.click();
	}

	/**
	 * clicks the Go To Review Order button
	 *
	 * @return review order page
	 */
	public M2StorefrontReviewOrderPage clickGoToReviewOrderButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(reviewOrderButtonId);
		WebElement button = wait.waitForElementEnabled(reviewOrderButtonId);

		click.clickElement(button);

		M2StorefrontReviewOrderPage reviewOrderPage = initializePageObject(M2StorefrontReviewOrderPage.class);

		return reviewOrderPage;
	}
}
