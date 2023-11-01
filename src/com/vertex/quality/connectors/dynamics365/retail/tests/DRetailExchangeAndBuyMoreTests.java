package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class DRetailExchangeAndBuyMoreTests extends DRetailBaseTest
{
	String salesOrderNumber;

	public class DFinanceSalesOrderCreationTests extends DFinanceBaseTest {
		/**
		 * Creating a Sales Order
		 * CD365-1390
		 *
		 */
		@Test(groups = { "FO_Integration", "salesOrderCreation" }, priority = 0)
		public void salesOrderCreationTest() {
			String itemNumber = "0005";
			String quantity = "30";
			String deliveryRemainder = "10";

			DFinanceHomePage homePage = new DFinanceHomePage(driver);
			DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

			settingPage.selectCompany("USRT");

			//Navigate to All Sales Orders page
			DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
					DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
					DFinanceModulePanelLink.ALL_SALES_ORDERS);

			//Click on "New" option
			allSalesOrdersPage.openNewSalesOrder();

			//Enter "Customer account"
			allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);

			//Click on "OK"  button on "Create Sales Order" screen
			allSalesOrdersPage.clickOkBTN();

			DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
			salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
			//Add Multiple line items with multiple quantities to line item of Sales Order
			allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
			allSalesOrdersSecondPage.changeDeliveryMode("11");
			allSalesOrdersSecondPage.changeUnderDelivery("100");
			allSalesOrdersSecondPage.clickPriceAndDiscount();
			allSalesOrdersSecondPage.reasonCode("CustSat");
			allSalesOrdersPage.addLineItem();

			allSalesOrdersSecondPage.fillItemsInfoForUSRT("0008","1",  1);
			allSalesOrdersSecondPage.changeDeliveryMode("11");
			allSalesOrdersSecondPage.changeUnderDelivery("100");
			allSalesOrdersPage.addLineItem();

			allSalesOrdersSecondPage.fillItemsInfoForUSRT("0015", "2",  2);
			allSalesOrdersSecondPage.changeDeliveryMode("11");
			allSalesOrdersSecondPage.changeUnderDelivery("100");

			//Navigate to Sales Tax
			allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
			allSalesOrdersPage.openSalesTaxCalculation();

			//Click to close Sales Tax
			allSalesOrdersPage.clickOnCancel();
			allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

			//Click the "Complete" Tab to complete the Sales Order
			allSalesOrdersSecondPage.clickOnComplete();
			String balance1 = allSalesOrdersSecondPage.getBalance();
			allSalesOrdersSecondPage.addPayment(balance1, "1");

			//Modify the Sales Order
			allSalesOrdersPage.clickOnTab(allSalesOrdersPage.salesOrderTabName);
			allSalesOrdersPage.modifySalesOrder();

			//Click on the "Update Line" Tab in the Sales Order line table
			allSalesOrdersPage.clickOnUpdateLine();

			//Select delivery remainder
			allSalesOrdersPage.selectDeliveryRemainder();

			//Update quantity
			allSalesOrdersPage.changeQuantity(deliveryRemainder);
			allSalesOrdersPage.clickOnOKBtn();

			//Navigate to Sales Tax
			allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
			allSalesOrdersPage.openSalesTaxCalculation();

			//Click on to close Sales Tax
			allSalesOrdersPage.clickOnCancel();
			allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

			//Click the "Complete" Tab to complete the Sales Order
			allSalesOrdersSecondPage.clickOnComplete();
			allSalesOrdersSecondPage.clickOnSubmit();

			//Generate invoice
			allSalesOrdersSecondPage.clickOnInvoiceTab();
			allSalesOrdersSecondPage.generateInvoice();
			allSalesOrdersPage.clickOnOKBtn();
			allSalesOrdersPage.clickOnOKPopUp();

			//Journal invoice
			allSalesOrdersSecondPage.clickOnInvoiceTab();
			allSalesOrdersSecondPage.journalInvoice();

		}
	}

	/**
	 * @TestCase CD365R-336
	 * @Description - Validate that if gift card is added first and than other items, it's not causing any tax issue
	 * @Author Mario Saint-Fleur
	 */

	@Test(groups = { "FO_Integration", "exchangeInStore" }, dependsOnGroups = {"salesOrderCreation"}, priority = 1)
	public void ExchangeInStoreAndBuyingMoreCausingTest( )
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

		//Select the "Pay Cash" option and complete the payment
		allRetailSalesOrderPage.selectPayCash();
		allRetailSalesOrderPage.cashAccepted();
		allRetailSalesOrderPage.closeButton();

	}

	public class DFinanceValidateReturnOrderTaxTests extends DFinanceBaseTest
	{
		/**
		 * Validate the Return Order Tax amount
		 * CD365-1602
		 *
		 */
		@Test(groups = { "FO_Integration" }, dependsOnGroups = {"exchangeInStore"}, priority = 2)
		public void validateReturnOrderTaxTest( )
		{
			//Navigate to F&O and check to see that sales order has negative tax value
			DFinanceHomePage DFinanceHomePage = new DFinanceHomePage(driver);
			DFinanceXMLInquiryPage xmlInquiryPage;
			DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

			settingPage.selectCompany("USRT");

			xmlInquiryPage = DFinanceHomePage.navigateToPage(
					DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
					DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

			xmlInquiryPage.clickOnFirstResponse();

			//Validate that tax amount is present in the log
			String response = xmlInquiryPage.getLogResponseValue();
			System.out.println(response);
			assertTrue(response.contains("<TotalTax>-7.06</TotalTax>"));
		}
	}
}
