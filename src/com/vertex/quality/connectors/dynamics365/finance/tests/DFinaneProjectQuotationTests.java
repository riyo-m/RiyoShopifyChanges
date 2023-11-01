package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinaneProjectQuotationTests extends DFinanceBaseTest
{
	/**
	 * creates a new project quotation, validates tax calculation in the default/normal case, and then deletes that new
	 * quotation
	 * CD365-611
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createProjectQuotationTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceQuotationsPage quotationsPage = new DFinanceQuotationsPage(driver);

		//================Data Declaration ===========================

		final String proj = DFinanceLeftMenuNames.PROJECT_MANAGEMENT.getData();
		final String quotation = DFinanceLeftMenuNames.QUOTATIONS.getData();
		final String proj_quo = DFinanceLeftMenuNames.PROJECT_QUOTATIONS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(proj);
		homePage.collapseAll();
		homePage.expandModuleCategory(quotation);
		settingsPage = homePage.selectModuleTabLink(proj_quo);

		settingsPage.clickOnNewButton();

		//Enter "Customer account"
		quotationsPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		quotationsPage.clickOkBTN();

		//ADD a new line
		quotationsPage.addNewLine();
		quotationsPage.setTransactionType();
		quotationsPage.setItemNO("1000");

		//Select site
		quotationsPage.setSite("1");
		quotationsPage.setUnitPrice("899");

		//Validate Sales Tax
		quotationsPage.clickSalesTaxButton();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = quotationsPage.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("71.92"),
			"'Total calculated sales tax amount' value is not expected");
	}

	/**
	 * @TestCase CD365-1076
	 * @Description - Creates a Sales Quotation and verifies the correct calculation of the extended price
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesQuotationExtendedPriceTest(){

		final String itemNum = "DP_001";
		final String quantity = "2";
		final String site = "2";
		final String wareHouse = "21";
		final String unitPrice = "1000";

		final int expectedLinesCount = 6;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		DFinanceSettingsPage settingsPage;

		//Navigate to All Quotations page
		DFinanceAllQuotationsPage quotationsPage = homePage.navigateToPage(DFinanceLeftMenuModule.SALES_AND_MARKETING,
				DFinanceModulePanelCategory.SALES_QUOTATIONS, DFinanceModulePanelLink.ALL_QUOTATIONS);

		//Create a new Quotation
		allSalesOrdersPage.openNewSalesOrder();

		quotationsPage.setAccountType("Customer");

		quotationsPage.setCustomerAccount(defaultCustomerAccount);

		allSalesOrdersPage.clickOnOKBtn();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Get the quotation number
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Add a line item
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNum, site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNum, site, wareHouse, quantity, "1200", 1);

		//Add a Maintain Charge
		quotationsPage.clickSalesQuotationLine();
		quotationsPage.clickMaintainCharges();
		allSalesOrdersPage.addCharges("Other", false,"Pcs.","20", "VertexAR", "All");

		//close Charges page
		allSalesOrdersPage.closeCharges();

		//Navigate to Sales Tax
		quotationsPage.clickOnTab("Quotation");
		allSalesOrdersPage.openSalesTaxCalculation();

		//Count the number of lines and verify that it is the correct amount
		int actualLinesCount = allSalesOrdersPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
				String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
						expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = allSalesOrdersPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		Map<String, String> tempLineMap1 = DFinanceFreightSOLineDetails.Line7.getLineDataMap();

		Map<String, String> tempLineMap2 = DFinanceFreightSOLineDetails.Line8.getLineDataMap();

		Map<String, String> tempLineMap3 = DFinanceFreightSOLineDetails.Line9.getLineDataMap();

		Map<String, String> tempLineMap4 = DFinanceFreightSOLineDetails.Line10.getLineDataMap();

		Map<String, String> tempLineMap5 = DFinanceFreightSOLineDetails.Line11.getLineDataMap();

		Map<String, String> tempLineMap6 = DFinanceFreightSOLineDetails.Line12.getLineDataMap();

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(tempLineMap3);
		expectedAllLinesDataList.add(tempLineMap4);
		expectedAllLinesDataList.add(tempLineMap5);
		expectedAllLinesDataList.add(tempLineMap6);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("355.20"),
				"'Total calculated sales tax amount' value is not expected");

		//Click to close Sales Tax
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to Vertex XML Inquiry
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);

		//Select the Correct Response from the list
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickOnFirstRequest();

		//Validate that the unit of measure and price is in the response
		String response = xMLInquiryPage.getLogRequestValue();
		assertTrue(response.contains("<Product productClass=\"ALL\">DP_001</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"pcs\">2</Quantity>\n" +
				"\t\t\t<ExtendedPrice>2000.000</ExtendedPrice>"));


	}
}
