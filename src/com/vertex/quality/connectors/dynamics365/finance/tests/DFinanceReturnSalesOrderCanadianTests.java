package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceReturnSalesOrderCanadianTests extends DFinanceBaseTest
{

	/**
	 * Return the order and verify the posted tax that there is no duplicate tax lines
	 * CD365-1387
	 */

	@Test(groups = { "FO_Integration_Deprecated" }, retryAnalyzer = TestRerun.class, priority = 1)
	public void returnAndVerifyTaxTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);


		//Navigate to all return order
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_RETURN_ORDERS);

		//Find the sales order
		createSalesOrder(allSalesOrdersPage, "Test Canada");

		//Select the order for return
		allSalesOrdersSecondPage.findReturnSalesOrder();

		//Select return reason and disposition code & save
		allSalesOrdersSecondPage.returnReason("21", "21");

		//Navigate to all sales order page
		homePage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Select the above returned order
		allSalesOrdersSecondPage.selectReturnedOrder("true");

		//Invoice this order
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();

		//Verify duplicate tax lines not displayed
		allSalesOrdersSecondPage.countRows();
	}

	/**
	 * creates a new sales order, invoice it by posting
	 * CD365-192
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderCanadaToCanadaSameProvinceTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		//================Data Declaration ===========================
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "22";
		final String unitPrice = "500";

		//================script implementation=======================

		//Navigate to all sales order page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		createSalesOrder(allSalesOrdersPage, "Test Canada");

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Go to "Set up" section
		//allSalesOrdersPage.expandSetupHeaderSection();

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		//Add line item
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

		//Click on "Sales Tax" option
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("35.00"),
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("35.00"),
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.clickOkButton();

		//Invoice this order
		allSalesOrdersSecondPage.clickInvoice();
		//Change Invoice Date and Select Quantity Parameter to "All"
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to Vertex XML Inquiry and verify productClass is equal to EXEMPT and Total Tax
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>35.0</TotalTax>"));
	}
}


