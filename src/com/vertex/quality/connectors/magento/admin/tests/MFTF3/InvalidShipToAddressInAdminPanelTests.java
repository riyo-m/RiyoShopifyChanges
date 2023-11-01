package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * BUNDLE-1607
 * Tests address cleansing is working correctly
 * 
 * @author alewis
 */
public class InvalidShipToAddressInAdminPanelTests extends MagentoAdminBaseTest
{
	protected String customerName = "Gruffydd Llywelyn";

	/**
	 * Checks to see if error message appears when address doesn't exist.
	 * Then corrects address and checks if correct tax value appears
	 */
	@Test()
	public void checkAddressValidationTest( )
	{
		String address = "100 Universal City Plaza";
		String city = "Universal City";
		String state = "12"; //California
		String zipCode = "91608";
		String correctTaxRate = "1.0";

		M2AdminCreateNewOrderPage newOrderPage = fillInIncorrectAddress();
		String exclTax = newOrderPage.getPriceTotalExclTax();
		String inclTax = newOrderPage.getPriceTotalInclTax();

		assertTrue(exclTax.equals(inclTax));

		newOrderPage.enterCustomerInfo(address, city, state, zipCode);

		newOrderPage.clickBillSameAsShipButton();
		newOrderPage.clickBillSameAsShipButton();

		newOrderPage.addShippingMethodSecond();

		String exclUpdateTax = newOrderPage.getPriceTotalExclTax();
		String inclUpdateTax = newOrderPage.getPriceTotalInclTax();

		String parseExclPrice = exclUpdateTax.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclUpdateTax.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(correctTaxRate));

		newOrderPage.clickSubmitOrderButton();
	}

	/**
	 * loads and signs into this configuration site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing into this configuration site
	 */
	protected M2AdminHomepage signInToAdminHomepage( )
	{
		driver.get(url);

		M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

		signOnPage.enterUsername(username);

		signOnPage.enterPassword(password);

		M2AdminHomepage homepage = signOnPage.login();

		return homepage;
	}

	/**
	 * Selects a customer and navigates to the create new order page
	 *
	 * @return a representation of the page create New Order Page
	 */
	protected M2AdminCreateNewOrderPage navigateToCreateNewOrderPage( )
	{
		M2AdminHomepage homePage = signInToAdminHomepage();

		homePage.navPanel.clickSalesButton();

		M2AdminOrdersPage m2AdminOrdersPage = homePage.navPanel.clickOrdersButton();

		M2AdminOrderCustomerPage orderCustomerPage = m2AdminOrdersPage.clickNewOrderButton();

		M2AdminCreateNewOrderPage createNewOrderPage = orderCustomerPage.clickCustomer(customerName);

		createNewOrderPage.clickBillSameAsShipButton();

		return createNewOrderPage;
	}

	/**
	 * Fills in a incorrect address for the new order
	 *
	 * @return a representation of the page create New Order Page
	 */
	protected M2AdminCreateNewOrderPage fillInIncorrectAddress( )
	{
		String SKU1 = "24-WG087";
		String address = "100 Universal City Plaza";
		String city = "Hyderabad";
		String state = "49"; // Oregon
		String zipCode = "99999";

		M2AdminCreateNewOrderPage createNewOrderPage = navigateToCreateNewOrderPage();
		createNewOrderPage.clickAddSKUButton();
		createNewOrderPage.enterSKUNumber(SKU1);
		createNewOrderPage.enterQty();
		createNewOrderPage.clickAddToOrderButton();
		createNewOrderPage.enterCustomerInfo(address, city, state, zipCode);
		createNewOrderPage.clickBillSameAsShipButton();

		createNewOrderPage.addShippingMethod(2);

		return createNewOrderPage;
	}
}
