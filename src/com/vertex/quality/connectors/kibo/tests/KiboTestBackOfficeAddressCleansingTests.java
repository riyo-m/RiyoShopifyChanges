package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboAddresses;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.*;
import com.vertex.quality.connectors.kibo.tests.base.KiboBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * this class represents all the tests performed on the back  office site related to address cleansing
 *
 * @author osabha
 */
public class KiboTestBackOfficeAddressCleansingTests extends KiboBaseTest
{
	/**
	 * Test Case CKIBO 385 , create sales order , different billing and shipping Addresses
	 * address cleansing turned on, change existing customer address and validate using address cleansing
	 * make sure address cleansing works( verification )
	 */

	@Test(groups = { "kibo_ui" })
	public void kiboInvalidAndValidExistingAddressesValidationTest( )
	{
		//**********************Test-Data********************//
		String checkNumber = "123456789";
		String expectedTax = "$5.64";
		String expectedOrderTotal = "$99.64";
		String expectedMessage = "Unable to validate address";
		String expectedAddress = "Valid address is:\n1041 Old Cassatt Rd\nBerwyn, PA 19312-1152\nUS";

		signInAndSetupConfigs(testStartPage);

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenEditAddressDialog(KiboCustomers.Customer3, maxinePage);

		maxinePage.editContactsDialog.clickEditBillingAddress();
		//WRONG CITY, INTENTIONALLY ENTERED
		maxinePage.editAddressDialog.enterCity(KiboAddresses.CITY_EXTON.value);
		maxinePage.editAddressDialog.enterZip(KiboAddresses.ZIP_99999.value);
		maxinePage.editAddressDialog.clickValidateButton();

		String actualMessage = maxinePage.editAddressDialog.getErrorMessage();
		assertEquals(expectedMessage, actualMessage);
		maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
		maxinePage.editAddressDialog.enterCity(KiboAddresses.CITY_BERWYN.value);
		maxinePage.editAddressDialog.enterZip(KiboAddresses.ZIP_19312.value);
		maxinePage.editAddressDialog.clickValidateButton();

		String actualAddress = maxinePage.editAddressDialog.getValidatedAddress();
		assertEquals(expectedAddress, actualAddress);

		maxinePage.editAddressDialog.clickUseThis();
		maxinePage.editAddressDialog.clickSaveButton();
		maxinePage.editContactsDialog.clickShipToThisAddress();
		maxinePage.editContactsDialog.clickSaveButton();

		maxinePage.clickEditDetails();

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_DRIVING_CAP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();

		maxinePage.orderHeader.clickSubmitOrder();

		String actualTax = maxinePage.getTaxAmount();
		assertEquals(expectedTax, actualTax);

		String orderTotal = maxinePage.getOrderTotal();
		assertEquals(expectedOrderTotal, orderTotal);

		fulfillOrderForShipping();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPayed = maxinePage.payment.checkIfPaid();
		assertTrue(isPayed);
	}

	/**
	 * this test case verifies that address cleansing works on the warehouse Addresses when address
	 * cleansing is turned on , test case (CKIBO-184)
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboWarehouseAddressCleansingTest( )
	{
		KiboAdminHomePage homePage = signInToHomePage(testStartPage);

		homePage.clickMainMenu();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();

		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectOSeriesProduct();
		connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
		connectorPage.configurationDialog.clickAuthenticationTypeUsernamePassword();
		connectorPage.configurationDialog.enterOseriesUsername(KiboCredentials.OSERIES_USERNAME);
		connectorPage.configurationDialog.enterOseriesPassword(KiboCredentials.OSERIES_PASSWORD);
		connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickOptionsButton();
		connectorPage.configurationDialog.turnOnInvoicing();
		connectorPage.configurationDialog.enableAddressCleansing();
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.closeConfigAppPopup();

		KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
		navPanel.clickMainTab();

		KiboLocationsPage locationsPage = navPanel.goToLocationsPage();
		KiboWarehouseCaPage warehouseCa = locationsPage.clickWHCA();

		warehouseCa.clickAddressField();
		warehouseCa.enterStreetAddress();
		warehouseCa.enterCity();
		warehouseCa.enterState();
		warehouseCa.enterZip();

		warehouseCa.clickValidateAddress();

		warehouseCa.selectValidatedAddress();
	}

	/**
	 * test case CKIBO-223
	 * to test address cleansing / re-cleanse physical origin
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboReCleansePhysicalOriginAddressTest( )
	{
		//**********************Test-Data********************//
		String streetAddress = "1041 Old Cassatt Rd";
		String expectedMessage = "Address could not be validated, please check your address and try again";

		KiboAdminHomePage homePage = signInToHomePage(testStartPage);

		homePage.clickMainMenu();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();
		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectOSeriesProduct();
		connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
		connectorPage.configurationDialog.clickAuthenticationTypeUsernamePassword();
		connectorPage.configurationDialog.enterOseriesUsername(KiboCredentials.OSERIES_USERNAME);
		connectorPage.configurationDialog.enterOseriesPassword(KiboCredentials.OSERIES_PASSWORD);
		connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickOptionsButton();
		connectorPage.configurationDialog.turnOnInvoicing();
		connectorPage.configurationDialog.enableAddressCleansing();
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.closeConfigAppPopup();
		KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
		navPanel.clickMainTab();
		KiboLocationsPage locationsPage = navPanel.goToLocationsPage();

		KiboReCleanseWarehouse reCleanseWarehouse = locationsPage.clickReCleanseWarehouse();
		reCleanseWarehouse.clickAddressField();
		reCleanseWarehouse.enterStreetAddress(streetAddress);
		// this is the wrong city of the address intentionally entered
		reCleanseWarehouse.enterCity(KiboAddresses.CITY_DALLAS.value);
		reCleanseWarehouse.enterState(KiboAddresses.STATE_PA.value);
		reCleanseWarehouse.enterZip(KiboAddresses.ZIP_99999.value);
		reCleanseWarehouse.clickValidateAddress();

		String actualMessage = reCleanseWarehouse.getErrorMessage();
		assertEquals(expectedMessage, actualMessage);

		reCleanseWarehouse.enterCity(KiboAddresses.CITY_BERWYN.value);
		reCleanseWarehouse.enterState(KiboAddresses.STATE_PA.value);
		reCleanseWarehouse.enterZip(KiboAddresses.ZIP_19312.value);
		reCleanseWarehouse.clickValidateAddress();
		reCleanseWarehouse.selectValidatedAddress();
	}

	/**
	 * to test address cleansing by only entering wrong zip code and then asking for validation
	 * CKIBO-213 TEST CASE
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboValidateAddressBadZipTest( )
	{
		KiboAdminHomePage homePage = signInToHomePage(testStartPage);
		homePage.clickMainMenu();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();

		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectOSeriesProduct();
		connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
		connectorPage.configurationDialog.clickAuthenticationTypeUsernamePassword();
		connectorPage.configurationDialog.enterOseriesUsername(KiboCredentials.OSERIES_USERNAME);
		connectorPage.configurationDialog.enterOseriesPassword(KiboCredentials.OSERIES_PASSWORD);
		connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickOptionsButton();
		connectorPage.configurationDialog.turnOnInvoicing();
		connectorPage.configurationDialog.enableAddressCleansing();
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.closeConfigAppPopup();
		KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
		navPanel.clickMainTab();
		KiboLocationsPage locationsPage = navPanel.goToLocationsPage();
		KiboReCleanseWarehouse reCleanseWarehouse = locationsPage.clickReCleanseWarehouse();
		reCleanseWarehouse.clickAddressField();
		reCleanseWarehouse.enterZip(KiboAddresses.ZIP_99999.value);
		reCleanseWarehouse.clickValidateAddress();
		reCleanseWarehouse.selectValidatedAddress();
	}

	/**
	 * test case CKIBO-214
	 * to test address cleansing feature on physical origin address, by entering wrong city and zip
	 * then checking for an error message
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboInvalidCityAndZipTest( )
	{
		//**************************Test-Data*************************************//
		String expectedMessage = "Address could not be validated, please check your address and try again";

		KiboAdminHomePage homePage = signInToHomePage(testStartPage);
		homePage.clickMainMenu();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();

		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectOSeriesProduct();
		connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
		connectorPage.configurationDialog.clickAuthenticationTypeUsernamePassword();
		connectorPage.configurationDialog.enterOseriesUsername(KiboCredentials.OSERIES_USERNAME);
		connectorPage.configurationDialog.enterOseriesPassword(KiboCredentials.OSERIES_PASSWORD);
		connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickOptionsButton();
		connectorPage.configurationDialog.turnOnInvoicing();
		connectorPage.configurationDialog.enableAddressCleansing();
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.closeConfigAppPopup();

		KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
		navPanel.clickMainTab();
		KiboLocationsPage locationsPage = navPanel.goToLocationsPage();
		KiboReCleanseWarehouse reCleanseWarehouse = locationsPage.clickReCleanseWarehouse();
		reCleanseWarehouse.clickAddressField();
		// the wrong city of the address intentionally entered
		reCleanseWarehouse.enterCity(KiboAddresses.CITY_DALLAS.value);
		reCleanseWarehouse.enterZip(KiboAddresses.ZIP_99999.value);
		reCleanseWarehouse.clickValidateAddress();

		String actualMessage = reCleanseWarehouse.getErrorMessage();
		assertEquals(expectedMessage, actualMessage);

		reCleanseWarehouse.enterCity(KiboAddresses.CITY_BERWYN.value);
		reCleanseWarehouse.enterZip(KiboAddresses.ZIP_19312.value);
		reCleanseWarehouse.clickValidateAddress();
		reCleanseWarehouse.selectValidatedAddress();
	}
}



