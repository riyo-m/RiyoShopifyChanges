package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.*;
import com.vertex.quality.connectors.kibo.tests.base.KiboBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this test class is to test the application response to wrong Credentials entries
 *
 * @author osabha
 */
public class KiboApplicationCredentialsTests extends KiboBaseTest
{
	/**
	 * this test case tests the connector application error message response after entering wrong
	 * trusted ID for the O-series product
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboWrongTrustedIdTest( )
	{
		//**********************Test-Data********************//
		//this is actually the wrong trusted Id intentionally entered
		String trustedId = "kibo1234";
		String expectedMessage = "One or more errors occurred";

		KiboAdminHomePage homePage = signInToHomePage(testStartPage);
		homePage.clickMainMenu();
		homePage.navPanel.clickMainTab();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();

		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectOSeriesProduct();
		connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
		connectorPage.configurationDialog.clickAuthenticationTypeTrustedId();
		connectorPage.configurationDialog.enterOseriesTrustedId(trustedId);
		connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickOptionsButton();
		connectorPage.configurationDialog.turnOnInvoicing();
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.closeConfigAppPopup();

		KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
		navPanel.clickMainTab();
		KiboOrdersPage orderPage = navPanel.goToOrderPage();
		orderPage.clickCreateNewOrder();
		KiboBackOfficeStorePage maxinePage = orderPage.goToMaxineStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer2);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_TRENCH_PUMP);
		maxinePage.orderDetailsDialog.selectPickupHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();

		String actualMessage = maxinePage.orderDetailsDialog.getErrorMessage();
		boolean isMessageCorrect = expectedMessage.equals(actualMessage);
		assertTrue(isMessageCorrect);
	}

	/**
	 * CKIBO-236 to test the application response to a wrong cloud trusted ID
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboEnterWrongCloudTrustedIdTest( )
	{
		//**********************Test-Data********************//
		//this is actually the wrong trusted Id intentionally entered
		String trustedId = "kibo1234";
		String expectedErrorMessage = "Invalid Vertex Credentials";

		KiboAdminHomePage homePage = signInToHomePage(testStartPage);
		homePage.clickMainMenu();
		homePage.navPanel.clickMainTab();
		homePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homePage.navPanel.goToApplicationsPage();
		KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
		connectorPage.makeSureApplicationEnabled();
		connectorPage.clickConfigureApplicationButton();

		connectorPage.configurationDialog.clickProductField();
		connectorPage.configurationDialog.selectCloudProduct();
		connectorPage.configurationDialog.enterCloudTrustedId(trustedId);
		connectorPage.configurationDialog.clickSaveButton();
		connectorPage.configurationDialog.clickValidateConnection();

		String errorMessage = connectorPage.configurationDialog.getErrorMessage();
		boolean isExpectedErrorMessage = expectedErrorMessage.equals(errorMessage);
		assertTrue(isExpectedErrorMessage);
	}
}
