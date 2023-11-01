package com.vertex.quality.connectors.sitecore.pages.store;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreItem;
import com.vertex.quality.connectors.sitecore.common.enums.SitecorePaymentMethod;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreShippingMethod;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import com.vertex.quality.connectors.sitecore.pages.store.checkout.*;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreCheckoutAmount;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreItemValues;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents checkout page in sitecore
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreCheckoutPage extends SitecoreBasePage
{

	protected By nextButton = By.className("btn-success");

	public SitecoreAddressTab billingAddress;
	public SitecoreAddressTab shippingAddress;
	public SitecoreShippingMethodTab shippingMethod;
	public SitecorePaymentMethodTab paymentMethod;
	public SitecorePaymentInfoTab paymentInfo;
	public SitecoreConfirmOrderTab orderConfirmation;

	/**
	 * This constructor is used to initialize the all tab pages related to this
	 * page.
	 *
	 * @param driver
	 */
	public SitecoreCheckoutPage( WebDriver driver )
	{

		super(driver);

		this.billingAddress = initializePageObject(SitecoreAddressTab.class);
		this.shippingAddress = initializePageObject(SitecoreAddressTab.class);
		this.shippingMethod = initializePageObject(SitecoreShippingMethodTab.class);
		this.paymentMethod = initializePageObject(SitecorePaymentMethodTab.class);
		this.paymentInfo = initializePageObject(SitecorePaymentInfoTab.class);
		this.orderConfirmation = initializePageObject(SitecoreConfirmOrderTab.class);
	}

	/**
	 * pair containing the expected and actual price, quantity, and subtotal values
	 *
	 * @param item             to get the price, quantity, and subtotal of
	 * @param expectedQuantity the expected quantity of the item
	 *
	 * @return pair containing the expected and actual price, quantity, and subtotal values
	 */
	public Pair<SitecoreItemValues, SitecoreItemValues> getValues( SitecoreItem item, int expectedQuantity )
	{
		double expectedPrice = Double.parseDouble(item.getPrice());
		double actualPrice = orderConfirmation.getUnitPrice(item.getName());

		double actualQuantity = Double.valueOf(orderConfirmation.getQuantity(item.getName()));
		double expectedQuantityDouble = Double.valueOf(expectedQuantity);

		double expectedSubtotal = expectedPrice * expectedQuantity;
		double actualSubtotal = orderConfirmation.getTotal(item.getName());

		SitecoreItemValues actualItemValues = new SitecoreItemValues(actualPrice, actualQuantity, actualSubtotal);
		SitecoreItemValues expectedItemValues = new SitecoreItemValues(expectedPrice, expectedQuantityDouble,
			expectedSubtotal);

		Pair<SitecoreItemValues, SitecoreItemValues> actualExpectedItems = Pair.of(actualItemValues,
			expectedItemValues);

		return actualExpectedItems;
	}

	/**
	 * get pairs containing the expected and actual shipping cost, tax amount, and subtotal values
	 *
	 * @param updatedItemTotal expected item total
	 * @param state            the state used to calculate tax amount
	 * @param city             the city used to calculate tax amount
	 *
	 * @return pairs containing the expected and actual price, quantity, and subtotal values
	 */
	public Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> verifyOrderValues( final Double updatedItemTotal,
		final String state, final String city )
	{
		double shippingCost = orderConfirmation.getShippingCost();

		VertexLogger.log(String.format("Shipping Cost: %s", shippingCost), getClass());

		double taxRate = TaxRate.valueOf(Address.NewYork.state.abbreviation).tax;
		VertexLogger.log(String.format("%s - %s State Tax rate: %s", state, city, taxRate), getClass());

		double expectedTaxAmount = (updatedItemTotal + shippingCost) * taxRate / 100;
		expectedTaxAmount = VertexCurrencyUtils.getDecimalFormatAmount(expectedTaxAmount);
		double actualTaxAmount = orderConfirmation.getTax();

		double expectedTotalAmount = updatedItemTotal + shippingCost + expectedTaxAmount;
		double actualTotalAmount = orderConfirmation.getTotalAmount();

		double actualSubtotal = orderConfirmation.getSubtotal();
		double expectedSubTotal = updatedItemTotal;

		SitecoreCheckoutAmount actualCheckoutAmount = new SitecoreCheckoutAmount(actualSubtotal, actualTaxAmount,
			actualTotalAmount);
		SitecoreCheckoutAmount expectedCheckoutAmount = new SitecoreCheckoutAmount(expectedSubTotal, expectedTaxAmount,
			expectedTotalAmount);

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> actualExpectedCheckout = Pair.of(actualCheckoutAmount,
			expectedCheckoutAmount);
		return actualExpectedCheckout;
	}

	/**
	 * selects shipping method, shipping item and then selects payment method and payment item
	 *
	 * @param shippingMethodVal shipping method pickup or delivery
	 * @param shipItems         type of shipping ground, air, etc
	 * @param paymentMethodVal  payment method card pay or pay online
	 * @param payType           payment type credit card, debit
	 */
	public void selectShippingAndPaymentMethod( final SitecoreShippingMethod shippingMethodVal,
		final SitecoreShippingMethod.ShipItems shipItems, final SitecorePaymentMethod paymentMethodVal,
		final SitecorePaymentMethod.PayCard payType )
	{
		// select shipping method and proceed next
		String shippingMethodString = shippingMethodVal.getText();
		String shippingTypeString = shipItems.getText();

		shippingMethod.selectShippingMethod(shippingMethodString, shippingTypeString);
		clickNextButton();

		// select payment method and proceed next
		String paymentMethodString = paymentMethodVal.getText();
		String paymentType = payType.getText();

		paymentMethod.selectPaymentMethod(paymentMethodString, paymentType);
		clickNextButton();
	}

	/**
	 * This method is used to click the next method. This is common across all the
	 * different tabs available in this page.
	 */
	public void clickNextButton( )
	{
		WebElement nextBtnElement = element.getWebElement(nextButton);
		scroll.scrollElementIntoView(nextBtnElement);
		click.clickElement(nextBtnElement);
		final String loadingText = "Loading...";
		List<WebElement> buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"));
		WebElement loadingButton = null;
		//search for leading button
		for ( WebElement btn : buttons )
		{
			try
			{
				String btnText = text.getElementText(btn);
				if ( btnText.equals(loadingText) )
				{
					loadingButton = btn;
					break;
				}
			}
			catch ( StaleElementReferenceException e )
			{
				//Loading element disappeared in time it took looking for loading button so loading button
				//webelment is stale and caused error
				break;
				//we just wanted to wait for the loading button to disappear so just break from loop and ignore exception
			}
		}
		//loading button has been found
		if ( loadingButton != null )
		{
			try
			{
				wait.waitForElementNotDisplayed(loadingButton);
			}
			catch ( StaleElementReferenceException e )
			{
				//loading button element became stale and is no longer on page
				//ignore exception
			}
		}
	}
}
