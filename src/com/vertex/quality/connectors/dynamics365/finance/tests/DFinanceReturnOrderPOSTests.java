package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Return Sales Order through Retail POS
 *
 * @author Sgupta
 */
@Listeners(TestRerunListener.class)
public class DFinanceReturnOrderPOSTests extends DFinanceBaseTest
{
	String salesOrderNumber;
	/**
	 * creates a new sales order, change physical origin & invoice it. Return this order from Retail POS and then deletes that new
	 * sales order
	 * CD365-160
	 */
	@Test(groups = { "FO_Integration", "salesOrderForReturn"}, retryAnalyzer = TestRerun.class, priority = 3)
	public void createdSalesOrderForReturnTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersPage salesOrderPage;
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//================Data Declaration ===========================
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

		final String itemNumber = "81331";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		//================script implementation=======================

		settingsPage.selectCompany("USRT");

		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage =homePage.navigateToAllSalesOrdersPage();

		//Click on "New" option
		salesOrderPage = settingsPage.clickOnNewButton();

		//Enter "Customer account"
		salesOrderPage.setCustomerAccount("test auto");

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrderPage.clickOkBTN();

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Go to "Set up" section

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		//Go to "Sales order lines" section and Enter "Item Number"
		allSalesOrdersSecondPage.fillItemsInfoForUSRT("0015", "2",  0);

		//Change Delivery Mode
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		//Click on "Sales Tax" option
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Click on "OK" button
		allSalesOrdersPage.clickOnCancel();
		allSalesOrdersPage.salesTaxCalculator.clickOkButton();

		//Click the "Complete" Tab to complete the Sales Order
		allSalesOrdersSecondPage.clickOnComplete();
		String balance1 = allSalesOrdersSecondPage.getBalance();
		allSalesOrdersSecondPage.addPayment(balance1, "1");

		// Invoice the order
		allSalesOrdersSecondPage.clickInvoice();

		//Operation complete message
		allSalesOrdersSecondPage.setQuantity();
	}

	public class DRetailReturnTests extends DRetailBaseTest
	{
		/**
		 * Return the above created order in POS
		 * CD365-1385
		 */
		@Test(groups = { "FO_Integration", "returnOrder" }, retryAnalyzer = TestRerun.class, dependsOnGroups = {"salesOrderForReturn"}, priority = 4)
		public void returnOrderTest()
		{
			//================script implementation========================
			//Navigate to add product page
			DRetailHomePage homePage = new DRetailHomePage(driver);
			DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

			//Navigate to transaction page and click the Recall Order Button
			allRetailSalesOrderPage.transactionButton();
			allRetailSalesOrderPage.clickOrders();
			allRetailSalesOrderPage.clickRecallOrder();

			//Apply filters for customer "Test Auto" and order status "Invoiced"
			allRetailSalesOrderPage.clickAddFilterBtn();
			allRetailSalesOrderPage.clickOnFilterOption("Order date");
			allRetailSalesOrderPage.enterOrderDate();

			allRetailSalesOrderPage.clickAddFilterBtn();
			allRetailSalesOrderPage.clickOnFilterOption("Order status");
			allRetailSalesOrderPage.selectOrderStatusFilterOption("Invoiced");

			allRetailSalesOrderPage.clickApplyFilterBtn();

			//Select the first sales order
			allRetailSalesOrderPage.selectSalesOrder();

			//Click the return button until the return order is completed
			allRetailSalesOrderPage.selectReturnOrderItems();

			allRetailSalesOrderPage.clickOkBtn();
			allRetailSalesOrderPage.clickOkBtn();

			//Select the "Pay Cash" option and complete the payment
			allRetailSalesOrderPage.selectPayCash();
			allRetailSalesOrderPage.cashAccepted();
			allRetailSalesOrderPage.closeButton();
		}
	}

	/**
	 * Validating the physical origin in F&O XML
	 * CD365-1386
	 */

	public class ValidationTests extends DFinanceBaseTest
	{

		@Test(groups = { "FO_Integration" }, retryAnalyzer = TestRerun.class, dependsOnGroups = {"returnOrder"}, priority = 5)
		public void validateXMLTest( )
		{
			DFinanceHomePage homePage = new DFinanceHomePage(driver);
			DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
			DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

			//Navigate to  XML inquiry
			homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

			settingsPage.selectCompany("USRT");

			//Select the Correct Response from the list
			xmlInquiryPage.clickOnFirstRequest();

			//Validate that address is present in the log
			String request = xmlInquiryPage.getLogRequestValue();
			assertTrue(request.contains("InvoiceRequest"));
			assertTrue(request.contains("<PhysicalOrigin taxAreaId=\"140890090\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
					"\t\t\t\t<StreetAddress1>1650 Premium Outlet Blvd Ste 565</StreetAddress1>\n" +
					"\t\t\t\t<City>Aurora</City>\n" +
					"\t\t\t\t<MainDivision>IL</MainDivision>\n" +
					"\t\t\t\t<PostalCode>60502-2948</PostalCode>\n" +
					"\t\t\t\t<Country>USA</Country>\n" +
					"\t\t\t</PhysicalOrigin>"));
		}
	}
}