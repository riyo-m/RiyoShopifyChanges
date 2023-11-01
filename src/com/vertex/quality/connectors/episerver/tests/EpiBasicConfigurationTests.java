package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EpiBasicConfigurationTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episervrVertexConnectorInitialConfigTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiOseriesPage oSeriespage;

		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Enter Company Address Details in Required fields And Validate the
		// Confirmation Message
		EpiOseriesCompanyAddressPage compaddress = new EpiOseriesCompanyAddressPage(driver);
		String testcompanycode = "TEST_COMPANY_CODE";
		String addressLine1 = Address.Berwyn.addressLine1;
		String city = Address.Berwyn.city;
		String state = Address.Berwyn.state.fullName;
		String zip = Address.Berwyn.zip5;
		String country = Address.Berwyn.country.fullName;
		compaddress.fillConfiguartionAddress(testcompanycode, addressLine1, city, state, zip, country);
		String expectedmsg = "Company name & address saved";
		String actualmsg = compaddress.getBasicConfigurationSaveConfirmationMsg();
		boolean compaddresult = actualmsg
			.toUpperCase()
			.contains(expectedmsg.toUpperCase());
		assertTrue(compaddresult, "Failed to save Basic Config");

		// Disable Option - Invoice Automatically And Validate the Connector Status
		String expectedStatus = Status.GOOD.text;
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(false);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		oSeriespage.validateConnectorTooltip("Vertex O Series");
		String actualstatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean invoiceresult = actualstatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(invoiceresult, "Vertex O Series Connector status is not valid/good");

		// Validate fields on OSeries Settings page
		String USERNAME = getUsername();
		String TAX_CAL_URL = getTaxCalUrl();
		String ADDRESS_VALIDATION_URL = getAddressUrl();
		EpiOseriesSettingsPage settingspage;
		settingspage = oSeriespage.clickOnSettingsTab();
		settingspage.validateValueAndReadOnlyStatusOfUsername("Username", USERNAME, "true");
		settingspage.validateReadOnlyStatusOfPassword("Password", "true");
		settingspage.validateValueAndReadOnlyStatusOfCALCENDPOINTURL("O Series Calculation Endpoint", TAX_CAL_URL,
			"true");
		settingspage.validateValueAndReadOnlyStatusOfADDRESSENDPOINTURL("O Series Address Lookup Endpoint",
			ADDRESS_VALIDATION_URL, "true");
		settingspage.validateValueAndReadOnlyStatusOfTrustedID("TrustedID", "[Not Set]", "true");
	}
}
