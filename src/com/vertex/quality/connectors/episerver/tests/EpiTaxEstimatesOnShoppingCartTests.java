package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiAddressPage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiCartPage;
import com.vertex.quality.connectors.episerver.pages.epiCommon.EpiStoreFrontHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EpiTaxEstimatesOnShoppingCartTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverTaxEstimatesOnShoppingCartTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiStoreFrontHomePage portalhomepage;
		EpiAddressPage addresspage;
		EpiAddAddressPage addaddresspage;
		EpiOseriesPage oSeriespage;

		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Validate OSeries Connector Status
		String expectedStatus = Status.GOOD.text;
		String actualstatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean connectorresult = actualstatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(connectorresult, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Enable Invoice Option - Invoice Automatically And Validate the Connector
		// Status
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(true);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		String actualinvoicestatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean invoiceresult = actualinvoicestatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(invoiceresult, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Logout from Admin portal
		admindashboardpage.adminlogout();

		// Login into Episerver Customer Portal with Customer Credentials
		portalhomepage = logInAsCustomer();

		EpiCartPage cart = new EpiCartPage(driver);
		portalhomepage.navigateToHomePage();
		// Clear all items from Cart
		cart.clearAllItemsInCart();

		// fill the billing address
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addaddresspage = addresspage.clickOnNewButton();
		addaddresspage.enterSammamishCityAddressDetails();
		portalhomepage.clickAddressBookLink();
		// Address validation
		String actualSelectedAddress = addresspage.getAddressFromAddressBook("WASHINGTON ADDRESS", true);
		String expectedSelectedAddressName = "WASHINGTON ADDRESS";
		assertTrue(actualSelectedAddress.contains(expectedSelectedAddressName));
		VertexLogger.log(
			String.format("Actual address contains expected address name : %s ", expectedSelectedAddressName));
		String expectedSelectedAddressFirstAndLastName = "FirstWAADDR LastWAADDR";
		assertTrue(actualSelectedAddress.contains(expectedSelectedAddressName));
		VertexLogger.log(String.format("Actual address contains expected address name : %s ",
			expectedSelectedAddressFirstAndLastName));

		// add new address to address book
		addaddresspage = addresspage.clickOnNewButton();
		addaddresspage.enterGrandRapidsCityAddressDetails();
		portalhomepage.clickAddressBookLink();
		// Address validation
		String actualSelectedAddress1 = addresspage.getAddressFromAddressBook("MICHIGAN ADDRESS", true);
		String expectedSelectedAddressName1 = "MICHIGAN ADDRESS";
		assertTrue(actualSelectedAddress1.contains(expectedSelectedAddressName1));
		VertexLogger.log(
			String.format("Actual address contains expected address name : %s ", expectedSelectedAddressName));
		String expectedSelectedAddressFirstAndLastName1 = "FirstMIADDR LastMIADDR";
		assertTrue(actualSelectedAddress1.contains(expectedSelectedAddressName1));
		VertexLogger.log(String.format("Actual address contains expected address name : %s ",
			expectedSelectedAddressFirstAndLastName1));

		// add new address to address book
		addaddresspage = addresspage.clickOnNewButton();
		addaddresspage.enterGrandRapidsCityAddressDetails();
		portalhomepage.clickAddressBookLink();
		// Address validation
		String actualSelectedAddress2 = addresspage.getAddressFromAddressBook("CALIFORNIA ADDRESS", true);
		String expectedSelectedAddressName2 = "CALIFORNIA ADDRESS";
		assertTrue(actualSelectedAddress2.contains(expectedSelectedAddressName2));
		VertexLogger.log(
			String.format("Actual address contains expected address name : %s ", expectedSelectedAddressName2));
		String expectedSelectedAddressFirstAndLastName2 = "FirstCAADDR LastCAADDR";
		assertTrue(actualSelectedAddress.contains(expectedSelectedAddressName2));
		VertexLogger.log(String.format("Actual address contains expected address name : %s ",
			expectedSelectedAddressFirstAndLastName2));
	}
}
