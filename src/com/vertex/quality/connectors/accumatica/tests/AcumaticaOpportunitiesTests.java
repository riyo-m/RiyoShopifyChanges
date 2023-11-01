package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To verify the tax amounts in "Cash Sales" transaction type *
 * Note: At "Cash Sales screen", Tax calculations are on based on branch address only ( not on
 * shipping address)
 *
 * @author saidulu kodadala
 */
public class AcumaticaOpportunitiesTests extends AcumaticaBaseTest
{
	/**
	 * To verify the tax amounts in Opportunities transaction
	 * Note: At "Opportunities screen", Tax calculations are on based on branch
	 * address only ( not on shipping address) (29)
	 */
	@Test
	public void Opportunities_TaxAmountsTest( )
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

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.OPPORTUNITIES);

		String opportunityID = opportunities.getOpportunitiesId();
		assertTrue(opportunityID.equalsIgnoreCase("<NEW>"), "Opportunity ID is not matching");
		String status = opportunities.getStatus();
		assertTrue(status.equalsIgnoreCase("New"), "Status is not matching");
		String reason = opportunities.getReason();
		assertTrue(reason.equalsIgnoreCase("Assign"), "Reason is not matching");
		String stage = opportunities.getStage();
		assertTrue(stage.equalsIgnoreCase("Prospect"), "Stage is not matching");
		opportunities.setSubject("Testing TAXes in Opportunities");
		opportunities.setClassId("INSIDE");
		String classId = opportunities.getClassId();
		assertTrue(classId.equalsIgnoreCase("INSIDE -Inside Sales"), "Class id is not matching");

		opportunities.setBusinessAccount("OPPRTCUST");
		opportunities.setBranch("MAIN");
		String opportunityBranch = opportunities.getBranch();
		assertTrue(opportunityBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");
		opportunities.setTaxZoneId("VERTEX");
		String taxZoneId = opportunities.getTaxZoneId();
		assertTrue(taxZoneId.equalsIgnoreCase("VERTEX - Vertex Tax Zone"), "Tax zone id is not matching");

		customers.clickSubMenu("Contact Info");
		String addressLine1 = opportunities.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase(" 2495 Iron Point Rd #11"), "Address Line1 is not matching");
		String addressLine2 = opportunities.getAddressLine2();
		assertTrue(addressLine2.isEmpty(), "Address Line2 is not matching");
		String city = opportunities.getCity();
		assertTrue(city.equalsIgnoreCase("Folsom"), "City is not matching");
		String opportunityCountry = opportunities.getCountry();
		assertTrue(opportunityCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String state = opportunities.getState();
		assertTrue(state.equalsIgnoreCase("CA - CALIFORNIA"), "State is not matching");
		String postalCode = opportunities.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("95630"), "Postal Code is not matching");

		customers.clickSubMenu("Products");
		opportunities.setInventoryId("301CMPNS01");
		opportunities.setQuantity("1");
		opportunities.setUnitPrice("1000");

		common.clickSaveButton();
		String amount = opportunities.getAmount();
		assertTrue(amount.equalsIgnoreCase("1,000"), "Amount is not matching");
		String total = opportunities.getTotal();
		assertTrue(total.equalsIgnoreCase("1,000"), "Total is not matching");
		String discount = opportunities.getDiscount();
		assertTrue(discount.equalsIgnoreCase("0.00"), "Discount is not matching");
	}
}
