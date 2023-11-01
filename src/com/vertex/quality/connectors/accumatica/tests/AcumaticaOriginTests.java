package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * This class is for Acumatica Origin related tests.
 * (To change the Physical Origin and Administrative Origin address and then to verify the tax
 * amounts in the Sales orders)
 *
 * @author Saidulu kodadala
 */
public class AcumaticaOriginTests extends AcumaticaBaseTest
{
	/**
	 * To change the Physical Origin and Administrative Origin address and then to
	 * verify the tax amounts in the Sales orders
	 * Note: This also covers the address cleansing at Branch(27)
	 */
	@Test
	public void ChangePhysicalOriginAndAdministrativeOriginTest( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		String[] branchAndCompanyCode = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(false, false);

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ORGANIZATION_STRUCTURE, AcumaticaLeftPanelLink.BRANCHES);
		//set basic details of branches page (General Info)
		this.setBasicDetailsInBranchesPageFromGeneralInfoTab("MAIN", "22833 NE 8th St", "Sammamish",
			Country.USA.iso2code, "WA", "98074");

		//switch to Delivery Settings
		customers.clickSubMenu("Delivery Settings");

		String resultTaxZoneID = branches.getTaxZoneID();
		assertTrue(resultTaxZoneID.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax Zone Id is not matching");

		boolean results = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean result = branches.isACSCheckedFromBranchesPage(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate contact info tab
		String addressLine1 = branches.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("22833 NE 8th St"), "Address Line1 is not matching");
		String addressLine2 = branches.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Suite 1407"), "Address Line2 is not matching");
		String resultCity = branches.getCity();
		assertTrue(resultCity.equalsIgnoreCase("Sammamish"), "City is not matching");
		String resultCountry = branches.getCountry();
		assertTrue(resultCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState = branches.getState();
		assertTrue(resultState.equalsIgnoreCase("WA - WASHINGTION"), "State is not matching");
		String resultZipCode = branches.getZipCode();
		assertTrue(resultZipCode.equalsIgnoreCase("98074"), "Zip Code is not matching");

		common.clickSaveButton();
		this.validateVertexPostalAddressPopup("22833 NE 8th St", "Sammamish", "USA", "WA", "98074-7232");
		this.selectAction("Confirm");

		boolean resultsSameAsMain = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(resultsSameAsMain, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean resultAcs = branches.isACSCheckedFromBranchesPage(false);
		assertFalse(resultAcs, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate contact info tab
		String addressLine11 = branches.getAddressLine1();
		assertTrue(addressLine11.equalsIgnoreCase("22833 NE 8th St"), "Address Line1 is not matching");
		String addressLine21 = branches.getAddressLine2();
		assertTrue(addressLine21.equalsIgnoreCase("Ste 1407"), "Address Line2 is not matching");
		String resultCity1 = branches.getCity();
		assertTrue(resultCity1.equalsIgnoreCase("Sammamish"), "City is not matching");
		String resultCountry1 = branches.getCountry();
		assertTrue(resultCountry1.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState1 = branches.getState();
		assertTrue(resultState1.equalsIgnoreCase("WA - WASHINGTION"), "State is not matching");
		String resultZipCode1 = branches.getZipCode();
		assertTrue(resultZipCode1.equalsIgnoreCase("98074-7232"), "Zip Code is not matching");

		//switch to Delivery Settings
		customers.clickSubMenu("General Info");
		boolean resultsSameAsMain1 = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(resultsSameAsMain1, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean resultAcs1 = branches.isACSCheckedFromBranchesPage(true);
		assertTrue(resultAcs1, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate contact info tab
		String addressLine12 = branches.getAddressLine1();
		assertTrue(addressLine12.equalsIgnoreCase("22833 NE 8th St"), "Address Line1 is not matching");
		String addressLine22 = branches.getAddressLine2();
		assertTrue(addressLine22.equalsIgnoreCase("Ste 1407"), "Address Line2 is not matching");
		String resultCity11 = branches.getCity();
		assertTrue(resultCity11.equalsIgnoreCase("Sammamish"), "City is not matching");
		String resultCountry11 = branches.getCountry();
		assertTrue(resultCountry11.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState11 = branches.getState();
		assertTrue(resultState11.equalsIgnoreCase("WA - WASHINGTION"), "State is not matching");
		String resultZipCode11 = branches.getZipCode();
		assertTrue(resultZipCode11.equalsIgnoreCase("98074-7232"), "Zip Code is not matching");

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("PHYSICAL11", "CUSTOMERFOR-PHYSICAL-ORIGIN-ADDR-CHANGE", email, false,
			false, "555 California St 7th floor", "San Francisco", Country.USA.iso2code, "CA", "94104");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(true, false);
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("PHYSICAL11", "301CMPNS01", "1", "500");
		this.validateSalesOrderPage(DEFAULT_SHIP_VIA, false, false, "555 California St 7th floor", "San Francisco",
			"US - United States of America", "CA - CALIFORNIA", "94104");
		this.validateVertexPostalAddressPopup("555 California St", "San Francisco", Country.USA.iso2code, "CA",
			"94104-1510");
		this.selectAction("Confirm");
		this.validateAddressShippingTab(true, true, "555 California St", "San Francisco",
			"US - United States of America", "CA - CALIFORNIA", "94104-1510");
		this.mainFormContentValidations("42.50", "542.50");
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TAX_DETAILS);
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TOTALS);

		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertTrue(lineTotal_result.equalsIgnoreCase("500.00"), "Line total is not displayed");
		String taxTotal_result = salesOrdersValidation.getTaxTotal();
		assertTrue(taxTotal_result.equalsIgnoreCase("42.50"), "Tax total is not displayed");
		String unpaidBalance_result = salesOrdersValidation.getUnPaidBalance();
		assertTrue(unpaidBalance_result.equalsIgnoreCase("542.50"), "Unpaid Balance is not displayed");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);
		customers.setCustomerId("PHYSICAL11");
		common.clickDeleteButton();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ORGANIZATION_STRUCTURE, AcumaticaLeftPanelLink.BRANCHES);
		//set basic details of branches page (General Info)
		this.setBasicDetailsInBranchesPageFromGeneralInfoTab("MAIN", "232 Madison Ave", "New York",
			Country.USA.iso2code, "NY", "10016");

		//switch to Delivery Settings
		customers.clickSubMenu("Delivery Settings");

		String resultTaxZoneID1 = branches.getTaxZoneID();
		assertTrue(resultTaxZoneID1.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax Zone Id is not matching");

		boolean resultsSameAsMain11 = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(resultsSameAsMain11, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean result1 = branches.isACSCheckedFromBranchesPage(false);
		assertFalse(result1, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate contact info tab
		String resultAddressLine1 = branches.getAddressLine1();
		assertTrue(resultAddressLine1.equalsIgnoreCase("232 Madison Ave"), "Address Line1 is not matching");
		String resultAddressLine2 = branches.getAddressLine2();
		assertTrue(resultAddressLine2.equalsIgnoreCase("Rm 1407"), "Address Line2 is not matching");
		String result_City = branches.getCity();
		assertTrue(result_City.equalsIgnoreCase("New York"), "City is not matching");
		String result_Country = branches.getCountry();
		assertTrue(result_Country.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String result_State = branches.getState();
		assertTrue(result_State.equalsIgnoreCase("NY - NEW YORK"), "State is not matching");
		String result_ZipCode = branches.getZipCode();
		assertTrue(result_ZipCode.equalsIgnoreCase("10016"), "Zip Code is not matching");

		common.clickSaveButton();
		this.validateVertexPostalAddressPopup("232 Madison Ave", "New York", "USA", "NY", "10016-2919");
		this.selectAction("Confirm");

		boolean resultsSameAsMain2 = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(resultsSameAsMain2, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean resultAcs2 = branches.isACSCheckedFromBranchesPage(false);
		assertFalse(resultAcs2, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate contact info tab
		String addressLine13 = branches.getAddressLine1();
		assertTrue(addressLine13.equalsIgnoreCase("232 Madison Ave"), "Address Line1 is not matching");
		String addressLine23 = branches.getAddressLine2();
		assertTrue(addressLine23.equalsIgnoreCase("Rm 1407"), "Address Line2 is not matching");
		String resultCity13 = branches.getCity();
		assertTrue(resultCity13.equalsIgnoreCase("New York"), "City is not matching");
		String resultCountry13 = branches.getCountry();
		assertTrue(resultCountry13.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState13 = branches.getState();
		assertTrue(resultState13.equalsIgnoreCase("NY - NEW YORK"), "State is not matching");
		String resultZipCode13 = branches.getZipCode();
		assertTrue(resultZipCode13.equalsIgnoreCase("10016-2919"), "Zip Code is not matching");

		//switch to General Info
		customers.clickSubMenu("General Info");
		boolean resultsSameAsMain13 = branches.isSameAsMainCheckedFromBranchesPage(true);
		assertTrue(resultsSameAsMain13, "UnTicked Checkbox 'Same as Main' under DELIVERY ADDRESS");
		boolean resultAcs13 = branches.isACSCheckedFromBranchesPage(true);
		assertTrue(resultAcs13, "UnTicked Checkbox 'ACS' under DELIVERY ADDRESS");

		//validate General Info tab
		String addressLine14 = branches.getAddressLine1();
		assertTrue(addressLine14.equalsIgnoreCase("232 Madison Ave"), "Address Line1 is not matching");
		String addressLine24 = branches.getAddressLine2();
		assertTrue(addressLine24.equalsIgnoreCase("Rm 1407"), "Address Line2 is not matching");
		String resultCity14 = branches.getCity();
		assertTrue(resultCity14.equalsIgnoreCase("New York"), "City is not matching");
		String resultCountry14 = branches.getCountry();
		assertTrue(resultCountry14.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState14 = branches.getState();
		assertTrue(resultState14.equalsIgnoreCase("NY - NEW YORK"), "State is not matching");
		String resultZipCode14 = branches.getZipCode();
		assertTrue(resultZipCode14.equalsIgnoreCase("10016-2919"), "Zip Code is not matching");
	}
}
