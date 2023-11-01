package com.vertex.quality.connectors.sitecore.pages.store.checkout;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreAmount;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represents confirm tab in sitecore checkout page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreConfirmOrderTab extends SitecoreBasePage
{

	protected By unitPrice = By.className("cart-price");
	protected By quantity = By.className("cart-quantity");
	protected By total = By.className("cart-total");

	protected By subtotalShippingTaxTotal = By.className("total");

	protected By amount = By.className("amount");

	protected By orderNumberText = By.className("text-justify");
	protected By orderNumber = By.tagName("strong");
	protected By checkoutLoc = By.id("checkoutContainer");
	protected By rowLoc = By.className("row");

	public SitecoreConfirmOrderTab( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to get the unit price of the specified item in the
	 * confirmation page.
	 *
	 * @param itemName name of item to get price of
	 *
	 * @return a double value (i.e. unit price of the item)
	 */
	public double getUnitPrice( final String itemName )
	{

		final String unitPriceString = findItemByLoc(itemName, unitPrice);

		double unitPriceAmount = VertexCurrencyUtils.cleanseCurrencyString(unitPriceString);

		return unitPriceAmount;
	}

	/**
	 * This method is used to get the item quantity of the specified item in the
	 * confirmation page.
	 *
	 * @param itemName name of item to get quantity of
	 *
	 * @return an integer value (i.e. Quantity of the item)
	 */
	public int getQuantity( final String itemName )
	{

		final String actualQuantity = findItemByLoc(itemName, quantity);

		int quantity = Integer.parseInt(actualQuantity.trim());

		return quantity;
	}

	/**
	 * This method is used to get the total amount of the specified item displayed
	 * in the confirmation page.
	 *
	 * @param itemName name of item to get total of
	 *
	 * @return an integer value (i.e. Total amount of the item)
	 */
	public double getTotal( final String itemName )
	{

		String totalValue = findItemByLoc(itemName, total);

		double totalDouble = VertexCurrencyUtils.cleanseCurrencyString(totalValue);

		return totalDouble;
	}

	/**
	 * This method is used to get the specified amount value
	 *
	 * @param amountName - Valid options are any one of these 4 values - {"SHIPPING_COST", "SUBTOTAL", "TAX", "TOTAL"}
	 *
	 * @return - respective amount dollar value in double format
	 */
	private double getAmount( final SitecoreAmount amountName )
	{
		waitForPageLoad();
		List<WebElement> amountElements = element.getWebElements(subtotalShippingTaxTotal);

		final String amountString = amountName.getText() + ":";
		WebElement amountElementContainer = null;

		for ( WebElement tempAmountElement : amountElements )
		{
			WebElement productDetails = wait.waitForElementDisplayed(By.className("text"), tempAmountElement);
			String tempStringAmount = text.getElementText(productDetails);

			if ( amountString.equalsIgnoreCase(tempStringAmount) )
			{
				amountElementContainer = tempAmountElement;
			}
		}

		WebElement amountElement = wait.waitForElementEnabled(amount, amountElementContainer);
		final String amountText = text.getElementText(amountElement);

		double amount = VertexCurrencyUtils.cleanseCurrencyString(amountText);
		return amount;
	}

	/**
	 * This method is used to click the "Confirm" button
	 *
	 * @return - the Sales Order Number as a string
	 */
	public String clickConfirmButton( )
	{
		final String confirmText = "Confirm";
		WebElement confirmElement = element.getButtonByText(confirmText);
		scroll.scrollElementIntoView(confirmElement);
		wait.waitForElementEnabled(confirmElement);
		click.clickElement(confirmElement);

		final String loadingText = "Loading...";
		WebElement loadingElement = element.getButtonByText(loadingText);
		wait.waitForElementNotDisplayed(loadingElement);

		WebElement orderNumberContainer = wait.waitForElementDisplayed(orderNumberText);
		WebElement orderNumberElement = element.getWebElement(orderNumber, orderNumberContainer);

		final String orderNumberText = text.getElementText(orderNumberElement);
		return orderNumberText;
	}

	/**
	 * This method is used to get the entire cart Sub-total amount
	 *
	 * @return - a double value of Sub-total
	 */
	public double getSubtotal( )
	{
		double subtotal = getAmount(SitecoreAmount.SUBTOTAL);
		return subtotal;
	}

	/**
	 * This method is used to get the total tax amount
	 *
	 * @return - a double value
	 */
	public double getTax( )
	{
		double subtotal = getAmount(SitecoreAmount.TAX);
		return subtotal;
	}

	/**
	 * This method is used to get the entire cart total amount
	 *
	 * @return - a double value of total (i.e. sum of sub-total & tax amount
	 */
	public double getTotalAmount( )
	{

		double subtotal = getAmount(SitecoreAmount.TOTAL);
		return subtotal;
	}

	/**
	 * This method is used to get the shipping cost amount
	 *
	 * @return - a double value
	 */
	public double getShippingCost( )
	{
		double subtotal = getAmount(SitecoreAmount.SHIPPING_COST);
		return subtotal;
	}

	/**
	 * This method is used to get the Billing/Shipping address details from Order
	 * Confirmation page into a map which are given as part of SO Creation.
	 *
	 * @param addressType - takes either "Billing Address" or "Shipping Address".
	 *
	 * @return map with all address values
	 *
	 * @Example: {"Name":"FirstName LastName", "Email":"emaild_address",
	 * "City":"<city>", ....}
	 */
	private Map<String, String> getAddress( final String addressType )
	{

		Map<String, String> addressMap = new HashMap<>();

		WebElement checkoutContainer = wait.waitForElementDisplayed(checkoutLoc);

		WebElement rowAddressContainer = wait.waitForElementDisplayed(rowLoc, checkoutContainer);

		WebElement billingAddressContainer = wait.waitForElementDisplayed(By.tagName("div"), rowAddressContainer);

		List<WebElement> addressElementsList = element.getWebElements(By.tagName("div"), billingAddressContainer);

		for ( int i = 0 ; i < addressElementsList.size() ; i++ )
		{

			String elementText = addressElementsList
				.get(i)
				.getText();

			if ( i == 0 )
			{
				addressMap.put("Name", elementText);
			}
			else if ( elementText.contains(":") )
			{

				String[] array = elementText.split(":");

				if ( array.length < 2 )
				{
					addressMap.put(array[0].trim(), "");
					continue;
				}
				else
				{
					addressMap.put(array[0].trim(), array[1].trim());
				}
			}
		}
		return addressMap;
	}

	/**
	 * This method is used to get the Billing address details from Order
	 * Confirmation page into a map which are given as part of SO Creation.
	 *
	 * @return map with all address values
	 *
	 * @Example: {"Name":"FirstName LastName", "Email":"emaild_address",
	 * "City":"<city>", ....}
	 */
	public Map<String, String> getBillingAddress( )
	{

		Map<String, String> addressMap = getAddress("Billing Address");

		return addressMap;
	}

	/**
	 * This method is used to get the Shipping address details from Order
	 * Confirmation page into a map which are given as part of SO Creation.
	 *
	 * @return map with all address values
	 *
	 * @Example: {"Name":"FirstName LastName", "Email":"emaild_address",
	 * "City":"<city>", ....}
	 */
	public Map<String, String> getShippingAddress( )
	{

		Map<String, String> addressMap = getAddress("Shipping Address");

		return addressMap;
	}

	/**
	 * find index of item and get attribute of element
	 *
	 * @param itemName name of element to get
	 * @param itemLoc  descriptor of the location of the item
	 *
	 * @return attribute of element
	 */
	protected String findItemByLoc( final String itemName, final By itemLoc )
	{
		int productIndex = getProductIndex(itemName);

		List<WebElement> productElements = wait.waitForAnyElementsDisplayed(itemLoc);
		WebElement productElement = productElements.get(productIndex);

		final String elementText = text.getElementText(productElement);

		return elementText;
	}
}
