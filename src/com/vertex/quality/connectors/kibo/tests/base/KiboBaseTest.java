package com.vertex.quality.connectors.kibo.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.pages.*;
import org.testng.annotations.Test;

/**
 * this class represents all the common methods used in most of the test cases
 * such as logging in to the admin home page and other helper methods
 *
 * @author osabha
 */
@Test(groups = { "kibo" })
public abstract class KiboBaseTest extends VertexUIBaseTest<KiboAdminSignOnPage>
{
	protected String KiboUrl;

	protected EnvironmentInformation environmentInformation;
	protected EnvironmentCredentials environmentCredentials;
	protected String username;
	protected String password;
	protected String environmentURL;

	/**
	 * Used to be able to set environment descriptor from the child base test
	 *
	 * @return the environment descriptor based on the base test
	 */
	protected DBEnvironmentDescriptors getEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.KIBO;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	@Override
	public KiboAdminSignOnPage loadInitialTestPage( )
	{
		try
		{
			environmentInformation = SQLConnection.getEnvironmentInformation(DBConnectorNames.KIBO,
				DBEnvironmentNames.QA, getEnvironmentDescriptor());
			environmentCredentials = SQLConnection.getEnvironmentCredentials(environmentInformation);
			KiboUrl = environmentInformation.getEnvironmentUrl();
			username = environmentCredentials.getUsername();
			password = environmentCredentials.getPassword();
			environmentURL = environmentInformation.getEnvironmentUrl();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		KiboAdminSignOnPage signOnPage = loadAdminSite();
		return signOnPage;
	}

	/**
	 * loads the login page of ariba's portal site
	 *
	 * @return a representation of this site's login screen
	 */
	protected KiboAdminSignOnPage loadAdminSite( )
	{
		KiboAdminSignOnPage signOnPage;
		String url = this.KiboUrl;

		driver.get(url);

		VertexLogger.log(String.format("Kibo URL - %s", url), VertexLogLevel.DEBUG, getClass());
		signOnPage = new KiboAdminSignOnPage(driver);
		return signOnPage;
	}

	/**
	 * does the sign in to the test environment
	 *
	 * @return new instance of the admin home page class
	 */
	protected KiboAdminHomePage signInToHomePage( final KiboAdminSignOnPage signOnPage )
	{
		VertexLogger.log("Signing in to Kibo Admin Site...");
		KiboAdminHomePage homePage = signOnPage.loginAsUser(username, password);
		return homePage;
	}

	/**
	 * navigates to the Maxine store in the back office from the vertex connector page
	 *
	 * @return new instance of the Kibo back office store page
	 */
	protected KiboBackOfficeStorePage navigateToBackOfficeStore( )
	{
		KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
		KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
		navPanel.clickMainTab();
		KiboOrdersPage orderPage = navPanel.goToOrderPage();
		orderPage.clickCreateNewOrder();
		orderPage.goToMaxineStore();
		return new KiboBackOfficeStorePage(driver);
	}

	/**
	 * Helper method to select a customer from the registered customers list
	 */
	protected void selectCustomerAndOpenOrderDetailsDialog( KiboCustomers customer )
	{
		KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

		maxinePage.clickCustomerList();
		maxinePage.selectCustomer(customer);
		maxinePage.clickEditDetails();
	}

	/**
	 * Helper method to select a customer from the registered customers list
	 *
	 * @param maxinePage instance of the maxine store page
	 * @param customer   name of the customer to select and edit details of.
	 */
	protected void selectCustomerAndOpenEditAddressDialog( final KiboCustomers customer,
		final KiboBackOfficeStorePage maxinePage )
	{
		maxinePage.clickCustomerList();
		maxinePage.selectCustomer(customer);
		maxinePage.clickChangeAddress();
	}

	/**
	 * Helper method to do the fulfillment steps for orders to ship
	 */
	protected void fulfillOrderForShipping( )
	{
		KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

		maxinePage.fulfillment.clickFulfillmentButton();
		maxinePage.fulfillment.clickMoveToButton();
		maxinePage.fulfillment.selectNewPackage();
		maxinePage.fulfillment.clickMarkAsShippedButton();
	}

	/**
	 * Helper method to do all the navigation to make payment for orders
	 */
	protected void payForTheOrder( String checkNumber )
	{
		KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

		maxinePage.payment.clickPaymentButton();
		maxinePage.payment.clickAddPaymentTypeButton();
		maxinePage.payment.selectCheck();
		maxinePage.payment.enterPayerFirstName();
		maxinePage.payment.clickCheckSaveButton();
		maxinePage.payment.clickCaptureButton();
		maxinePage.payment.enterCheckNumber(checkNumber);
		maxinePage.payment.clickSaveCollectedCheck();
	}

	/**
	 * does sign in and navigate to setup all the preReq to the test cases
	 *
	 * @param signOnPage instance of the sign on page
	 *
	 * @return new instance of the Kibo Vertex Connector Page
	 */
	protected KiboVertexConnectorPage signInAndSetupConfigs( final KiboAdminSignOnPage signOnPage )
	{
		KiboAdminHomePage homePage = signInToHomePage(signOnPage);
		homePage.clickMainMenu();
		homePage.navPanel.clickMainTab();
		KiboLocationsPage locationsPage = homePage.navPanel.goToLocationsPage();
		KiboHomeBasePage homeBasePage = locationsPage.goToHomeBasePage();
		homeBasePage.clickAddressField();
		homeBasePage.enterStreetAddress();
		homeBasePage.enterCity();
		homeBasePage.enterState();
		homeBasePage.enterZip();
		homeBasePage.clickConfirmButton();
		homeBasePage.clickSaveButton();
		homeBasePage.clickMainMenu();
		homeBasePage.navPanel.clickSystemTab();
		KiboApplicationsPage applicationsPage = homeBasePage.navPanel.goToApplicationsPage();
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

		return new KiboVertexConnectorPage(driver);
	}
}

