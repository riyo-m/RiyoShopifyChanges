package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersSecondPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSettingsPage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DRetailValidateTaxAfterRecallAndChangingQuantityTests extends DRetailBaseTest {

    public class DFinanceSalesOrderCreationTests extends DFinanceBaseTest {
        /**
         * @TestCase CD365-2079
         * @Description - Create A Sales Order With Line Item That Has Multiple Quantity Amount
         * @Author Mario Saint-Fleur
         *
         */
        @Test(groups = { "FO_Integration", "salesOrderCreationWithMultipleQuantity" }, priority = 26 )
        public void salesOrderCreationWithMultipleQuantityTest() {
            String itemNumber = "0005";
            String quantity = "30";
            String expectedSalesTaxAmount = "7.77";

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
            //Add Multiple line items with multiple quantities to line item of Sales Order
            allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
            allSalesOrdersSecondPage.changeDeliveryMode("11");
            allSalesOrdersSecondPage.changeUnderDelivery("100");
            allSalesOrdersSecondPage.clickPriceAndDiscount();
            allSalesOrdersSecondPage.reasonCode("CustSat");

            //Navigate to Sales Tax
            allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
            allSalesOrdersPage.openSalesTaxCalculation();
            allSalesOrdersPage.clickOnCancel();
            String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            assertTrue(calculatedSalesTaxAmount.contains(expectedSalesTaxAmount), "Actual tax amount: " + calculatedSalesTaxAmount + "is not equal to the expected sales tax amount: " + expectedSalesTaxAmount);

            //Click to close Sales Tax
            allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

            //Click the "Complete" Tab to complete the Sales Order
            allSalesOrdersSecondPage.clickOnComplete();
            String balance1 = allSalesOrdersSecondPage.getBalance();
            allSalesOrdersSecondPage.addPayment(balance1, "1");

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
     * @TestCase CD365R-410
     * @Description - Recall Sales Order With Multiple Quantity In POS, Adjust Quantity And Validate Taxes
     * @Author Mario Saint-Fleur
     */

    @Test(groups = { "FO_Integration" }, dependsOnGroups = {"salesOrderCreationWithMultipleQuantity"}, priority = 27 )
    public void recallPOSOrderWithMultipleQuantityAdjustQuantityAndValidateTaxTest( )
    {
        String expectedAmount = "5.63";

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

        //Verify tax
        String taxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(taxAmount.equalsIgnoreCase("7.76"),
                "'Total sales tax amount' value is not expected");

        //Click actions and set quantity less than current amount
        allRetailSalesOrderPage.clickOnAction();
        allRetailSalesOrderPage.clickTransactionOptions();
        allRetailSalesOrderPage.clickBackArrowTransactionOptions();
        allRetailSalesOrderPage.clickOnSetQuantity("25");

        //Verify tax is different after quantity is changed
        taxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(taxAmount.equalsIgnoreCase("6.47"),
                "'Total sales tax amount' value is not expected");

        //Select the "Pay Cash" option and complete the payment
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

}
