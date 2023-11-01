package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaEnableDisableFeaturesPage;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaItemClassesPage;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaVertexSetupPage;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * To setup web service end points and then to check the "Test Connection" check the communication
 * with end points.
 *
 * @author saidulu kodadala
 */
@Test(groups = "config")
public class AcumaticaConfigurationTests extends AcumaticaBaseTest
{
	final String expectedClassId = "ATMITMCLAS";
	final String expectedClassDescription = "AUTOMATIONTEST ITEMCLASS";

	final String testTaxCategoryId = "TAXABLE";
	final String expectedTaxCategory = String.format("%s - Taxable Goods and Services", testTaxCategoryId);
	final String testPostingClassId = "CCLASS";
	final String expectedPostingClass = String.format("%s - Self-centered class", testPostingClassId);
	final String expectedPriceClassId = "INPRICE001";
	final String expectedPriceClass = String.format("%s - Inventory price class 1", expectedPriceClassId);
	final String expectedUnitOfMeasure = "PC";

	/**
	 * checks that the standard basic configuration settings of the system allow a connection to Vertex servers
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author saidulu kodadala, ssalisbury
	 */
	@Ignore
	@Test(groups = { "healthCheck", "smoke" })
	public void healthCheckTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to Vertex Setup Page

		AcumaticaVertexSetupPage vertexSetupPage = homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES,
			AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Verify 'active ' check box status
		//TODO this should return a value & then that value should be asserted true
		vertexSetupPage.setConnectorActiveCheckbox(true);

		//Verify health check urls working condition
		vertexSetupPage.performHealthCheckToValidateURLs();
		//TODO this should return values which are validated here
	}

	/**
	 * VertexConnectorConfigTest
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author sai ssalisbury
	 */
	@Ignore
	@Test(groups = { "smoke" })
	public void VertexConnectorConfigTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();
		//TODO is this test supposed to also do something with address cleansing? if not, why in the world is this last part here?
	}

	/**
	 * Configure Product Class (Item Class)
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author sai ssalisbury
	 */
	@Ignore
	@Test
	public void ConfigureProductClass( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to VertexSetup page
		AcumaticaItemClassesPage itemClassesPage = homePage.openMenuPage(AcumaticaGlobalSubMenuOption.INVENTORY,
			AcumaticaLeftPanelLink.ITEM_CLASSES);

		itemClassesPage.setClassId(expectedClassId);
		itemClassesPage.setDescription(expectedClassDescription);
		itemClassesPage.setCheckedStockItemCheckbox(true);
		itemClassesPage.setCheckedAllowNegativeQuantityCheckbox(true);
		itemClassesPage.setType("Finished Good");
		itemClassesPage.setValuationMethod("Average");
		itemClassesPage.setTaxCategory(testTaxCategoryId);
		itemClassesPage.setPostingClass(testPostingClassId);
		itemClassesPage.setPriceClass(expectedPriceClassId);
		itemClassesPage.setCheckedDeductQtyShippedCheckbox(true);
		itemClassesPage.setCheckedDeductQtyAllocatedCheckbox(true);
		itemClassesPage.setCheckedIncludeQtyOnReturnsCheckbox(true);

		itemClassesPage.setCheckedDeductQtyOnIssuesCheckbox(false);
		itemClassesPage.setCheckedDeductQtyOnSalesPreparedCheckbox(false);
		itemClassesPage.setCheckedDeductQtyOnSalesOrdersCheckbox(false);
		itemClassesPage.setCheckedDeductQtyOfKitAssemblyDemandCheckbox(false);
		itemClassesPage.setCheckedDeductQtyOnBackOrdersCheckbox(false);

		itemClassesPage.setCheckedIncludeQtyOnReceiptsCheckbox(false);
		itemClassesPage.setCheckedIncludeQtyInTransitCheckbox(false);
		itemClassesPage.setCheckedIncludeQtyOnPOReceiptsCheckbox(false);
		itemClassesPage.setCheckedIncludeQtyOnPurchasePreparedCheckbox(false);
		itemClassesPage.setCheckedIncludeQtyOnPurchaseOrdersCheckbox(false);
		itemClassesPage.setCheckedIncludeQtyOfKitAssemblySupplyCheckbox(false);

		itemClassesPage.setBaseUnit(expectedUnitOfMeasure);
		itemClassesPage.setSalesUnit(expectedUnitOfMeasure);
		itemClassesPage.setPurchaseUnit(expectedUnitOfMeasure);

		itemClassesPage.clickSaveButton();

		//this seems like it would mask problems where saving the class changed the class id unexpectedly:  itemClassesPage.setClassId(expectedClassId);
		String classId = itemClassesPage.getClassId();
		assertTrue(expectedClassId.equalsIgnoreCase(classId), "Expected class id is not displayed");
		boolean stockItem = itemClassesPage.setCheckedStockItemCheckbox(true);
		assertTrue(stockItem, "Stock item is unchecked ");
		boolean allowNegativeQuantity = itemClassesPage.setCheckedAllowNegativeQuantityCheckbox(true);
		assertTrue(allowNegativeQuantity, "Allow Negative Quantity unchecked ");

		String taxCategory = itemClassesPage.getTaxCategory();
		assertTrue(expectedTaxCategory.equalsIgnoreCase(taxCategory), "Expected tax category is not displayed");
		String postingClass = itemClassesPage.getPostingClass();
		assertTrue(expectedPostingClass.equalsIgnoreCase(postingClass), "Expected posting class is not displayed");
		String priceClass = itemClassesPage.getPriceClass();
		assertTrue(expectedPriceClass.equalsIgnoreCase(priceClass), "Expected price class is not displayed");

		String baseUnit = itemClassesPage.getBaseUnit();
		assertTrue(expectedUnitOfMeasure.equalsIgnoreCase(baseUnit), "Expected base unit is not displayed");
		String salesUnit = itemClassesPage.getSalesUnit();
		assertTrue(expectedUnitOfMeasure.equalsIgnoreCase(salesUnit), "Expected sales unit is not displayed");
		String purchaseUnit = itemClassesPage.getPurchaseUnit();
		assertTrue(expectedUnitOfMeasure.equalsIgnoreCase(purchaseUnit), "Expected purchase unit is not displayed");

		common.clickDeleteButton();
	}

	/**
	 * To create a new Product Code (Inventory ID) under a newly created Product Class (Item Class)
	 * and then
	 * create a Sales Order using the newly created Product Code
	 */
	@Test
	public void ConfigureProductCodeAndUseInSalesOrder( )
	{
		commonSetup();

		//Navigate to ItemClasses page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.INVENTORY, AcumaticaLeftPanelLink.ITEM_CLASSES);

		itemClass.setClassId("ATMNSOCLAS");
		itemClass.setDescription("AUTOMATIONITEMCLAS FORSALESORDER");
		itemClass.setCheckedStockItemCheckbox(true);
		itemClass.setCheckedAllowNegativeQuantityCheckbox(true);
		itemClass.setType("Finished Good");
		itemClass.setValuationMethod("Average");
		itemClass.setTaxCategory("TAXABLE-Taxable Goods and Services");
		itemClass.setPostingClass("CCLASS-Self-centered class");
		itemClass.setPriceClass("INPRICE001-Inventory price class 1");
		itemClass.setCheckedDeductQtyShippedCheckbox(true);
		itemClass.setCheckedDeductQtyAllocatedCheckbox(true);
		itemClass.setCheckedIncludeQtyOnReturnsCheckbox(true);

		itemClass.setCheckedDeductQtyOnIssuesCheckbox(false);
		itemClass.setCheckedDeductQtyOnSalesPreparedCheckbox(false);
		itemClass.setCheckedDeductQtyOnSalesOrdersCheckbox(false);
		itemClass.setCheckedDeductQtyOfKitAssemblyDemandCheckbox(false);
		itemClass.setCheckedDeductQtyOnBackOrdersCheckbox(false);

		itemClass.setCheckedIncludeQtyOnReceiptsCheckbox(false);
		itemClass.setCheckedIncludeQtyInTransitCheckbox(false);
		itemClass.setCheckedIncludeQtyOnPOReceiptsCheckbox(false);
		itemClass.setCheckedIncludeQtyOnPurchasePreparedCheckbox(false);
		itemClass.setCheckedIncludeQtyOnPurchaseOrdersCheckbox(false);
		itemClass.setCheckedIncludeQtyOfKitAssemblySupplyCheckbox(false);

		itemClass.setBaseUnit("PC");
		itemClass.setSalesUnit("PC");
		itemClass.setPurchaseUnit("PC");

		common.clickSaveButton();
		common.switchToParentPage();

		//Navigate to Stock Items page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.INVENTORY, AcumaticaLeftPanelLink.STOCK_ITEMS);

		stockItems.setInventoryId("ATMNPRODSO");
		stockItems.setDescription("ATMN-NEW-PRODUCTID-FOR-SALESORDER");
		stockItems.setItemClass("ATMNSOCLAS");
		stockItems.setType("Component Part");
		stockItems.setValuationMethod("Standard");

		String taxCategory = itemClass.getTaxCategory();
		assertTrue(taxCategory.equalsIgnoreCase("TAXABLE-Taxable Goods and Services"),
			"Expected tax category is not displayed");
		String postingClass = itemClass.getPostingClass();
		assertTrue(postingClass.equalsIgnoreCase("CCLASS-Self-centered class"),
			"Expected posting class is not displayed");

		String baseUnit = itemClass.getBaseUnit();
		assertTrue(baseUnit.equalsIgnoreCase("PC"), "Expected base unit is not displayed");
		String salesUnit = itemClass.getSalesUnit();
		assertTrue(salesUnit.equalsIgnoreCase("PC"), "Expected sales unit is not displayed");
		String purchaseUnit = itemClass.getPurchaseUnit();
		assertTrue(purchaseUnit.equalsIgnoreCase("PC"), "Expected purchase unit is not displayed");

		stockItems.setWareHouseId("WHOLESALE");
		stockItems.setDefaultIssueFrom("R01C01L01 - Row 1, column 1, level 1");
		stockItems.setDefaultReceiptTo("R01C01L01 - Row 1, column 1, level 1");
		customers.clickSubMenu(AcumaticaMainPanelTab.PRICE_COST_INFO);
		itemClass.clearPriceClass();
		customers.clickSubMenu(AcumaticaMainPanelTab.WAREHOUSE_DETAILS);
		//		stockItems.isDefaultCheckBox(true);
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();
		common.switchToParentPage();

		//Navigate to Vertex Setup Page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Implemented predefined settings
		String[] branchAndCompanyCode = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");
		//Page navigation
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);
		VertexLogger.log("Successfully completed Basic Configuration", AcumaticaConfigurationTests.class);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("KMARTCUST", "KMARTCUSTFOR NEWPRDCLASNCODE", email, false, false,
			"951 S Rand Road", "Lake Zurich", Country.USA.iso2code, "IL", "60047");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "951 S Rand Road", "Lake Zurich",
			"US - United States of America", "IL - ILLINOIS", "60047");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		validateOrderType();
		setSalesOrderPageActions("KMARTCUST", "ATMNPRODSO", "3", "500");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		salesOrdersValidation.setShipViaFromShippingInformationSection(DEFAULT_SHIP_VIA);
		boolean status = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(false);
		assertFalse(status, "Override Address is not UnTicked");
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(false);
		assertFalse(acsStatus, "ACS is not UnTicked");
		common.clickSaveButton();

		validateVertexPostalAddressPopup("951 S Rand Road", "Lake Zurich", Country.USA.iso2code, "IL", "60047-2547");
		selectAction("Confirm");

		mainFormContentValidations("93.75", "1593.75");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertTrue(lineTotal_result.equalsIgnoreCase("1,500.00"), "Line total is not displayed");
		String miscTotal_result = salesOrdersValidation.getMiscTotal();
		assertTrue(miscTotal_result.equalsIgnoreCase("0.00"), "Misc total is not displayed");
		String discountTotal_result = salesOrdersValidation.getDiscountTotal();
		assertTrue(discountTotal_result.equalsIgnoreCase("0.00"), "Discount total is not displayed");
		common.clickDeleteButton();
		common.switchToParentPage();

		//Navigate to Customers Page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);
		customers.setCustomerId("KMARTCUST");
		common.clickDeleteButton();
		common.switchToParentPage();

		//Navigate to VertexSetup page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.INVENTORY, AcumaticaLeftPanelLink.STOCK_ITEMS);
		common.clickDeleteButton();
		common.switchToParentPage();

		//Navigate to ItemClasses page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.INVENTORY, AcumaticaLeftPanelLink.ITEM_CLASSES);

		itemClass.setClassId("ATMNSOCLAS");
		common.clickDeleteButton();
	}
}
