package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.EpiOseriesCompanyAddressPage;
import com.vertex.quality.connectors.episerver.pages.EpiOseriesInvocingPage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EpiUpdatePhysicalOriginTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverUpdatePhysicalOriginTest( )
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
		String testcompanycode = "INVALID_COMPANY_CODE";
		String addressLine1 = Address.NewYork.addressLine2;
		String city = Address.NewYork.city;
		String state = Address.NewYork.state.fullName;
		String zip = Address.NewYork.zip5;
		String country = Address.NewYork.country.fullName;

		compaddress.fillConfiguartionAddress(testcompanycode, addressLine1, city, state, zip, country);

		String expectedmsg = "Company name & address saved";
		String actualmsg = compaddress.getBasicConfigurationSaveConfirmationMsg();
		boolean compaddresult = actualmsg
			.toUpperCase()
			.contains(expectedmsg.toUpperCase());
		assertTrue(compaddresult, "Failed to save Basic Config");
		// Clearing address physical region and validation

		compaddress.fillConfiguartionAddress("", "", "", "", "", "");
		String expectedmsg_clear
			= "Company name is required\nLine 1 is required\nCity is required\nPostal code is required\nCountry is required";
		String actualmsg_clear = compaddress.getBasicConfigurationSaveConfirmationFailMsg();
		boolean compaddresult_clear = actualmsg_clear
			.toUpperCase()
			.contains(expectedmsg_clear.toUpperCase());
		assertTrue(compaddresult_clear, "Failed to save Basic Config");

		// Physical region values only company code entered and saved
		String testcompanycode_1 = "TEST_COMPANY_CODE";

		compaddress.fillConfiguartionAddress(testcompanycode_1, "", "", "", "", "");
		String expectedmsg_1 = "Line 1 is required\nCity is required\nPostal code is required\nCountry is required";
		String actualmsg_clear_1 = compaddress.getBasicConfigurationSaveConfirmationFailMsg();
		boolean compaddresult_clear_1 = actualmsg_clear_1
			.toUpperCase()
			.contains(expectedmsg_1.toUpperCase());
		assertTrue(compaddresult_clear_1, "Failed to save Basic Config");

		// Physical region values only company code and addressline1 entered and saved
		String testcompanycode_2 = testcompanycode_1;
		String addressLine1_2 = Address.Berwyn.addressLine1;

		compaddress.fillConfiguartionAddress(testcompanycode_2, addressLine1_2, "", "", "", "");
		String expectedmsg_2 = "City is required\nPostal code is required\nCountry is required";
		String actualmsg_2 = compaddress.getBasicConfigurationSaveConfirmationFailMsg();
		boolean compaddresult_2 = actualmsg_2
			.toUpperCase()
			.contains(expectedmsg_2.toUpperCase());
		assertTrue(compaddresult_2, "Failed to save Basic Config");

		// Physical region values only company code, addressline1 and city entered and
		// saved
		String testcompanycode_3 = testcompanycode_1;
		String addressLine1_3 = Address.Berwyn.addressLine1;
		String city_3 = Address.Berwyn.city;

		compaddress.fillConfiguartionAddress(testcompanycode_3, addressLine1_3, city_3, "", "", "");
		String expectedmsg_3 = "Postal code is required\nCountry is required";
		String actualmsg_3 = compaddress.getBasicConfigurationSaveConfirmationFailMsg();
		boolean compaddresult_3 = actualmsg_3
			.toUpperCase()
			.contains(expectedmsg_3.toUpperCase());
		assertTrue(compaddresult_3, "Failed to save Basic Config");

		// Physical region values only company code, addressline1,city and zip entered
		// and saved
		String testcompanycode_5 = testcompanycode_1;
		String addressLine1_5 = Address.Berwyn.addressLine1;
		String city_5 = Address.Berwyn.city;
		String state_5 = Address.Berwyn.state.fullName;
		String zip_5 = Address.Berwyn.zip5;

		compaddress.fillConfiguartionAddress(testcompanycode_5, addressLine1_5, city_5, state_5, zip_5, "");
		String expectedmsg_5 = "Country is required";
		String actualmsg_5 = compaddress.getBasicConfigurationSaveConfirmationFailMsg();
		boolean compaddresult_5 = actualmsg_5
			.toUpperCase()
			.contains(expectedmsg_5.toUpperCase());
		assertTrue(compaddresult_5, "Failed to save Basic Config");

		// Physical region values only company code, addressline1,city,zip and country
		// entered and saved
		String testcompanycode_6 = testcompanycode_1;
		String addressLine1_6 = Address.Berwyn.addressLine1;
		String city_6 = Address.Berwyn.city;
		String state_6 = Address.Berwyn.state.fullName;
		String zip_6 = Address.Berwyn.zip5;
		String country_6 = Address.Berwyn.country.fullName;

		compaddress.fillConfiguartionAddress(testcompanycode_6, addressLine1_6, city_6, state_6, zip_6, country_6);

		String expectedmsg_6 = "Company name & address saved";
		String actualmsg_6 = compaddress.getBasicConfigurationSaveConfirmationMsg();
		boolean compaddresult_6 = actualmsg_6
			.toUpperCase()
			.contains(expectedmsg_6.toUpperCase());
		assertTrue(compaddresult_6, "Failed to save Basic Config");

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
	}
}
