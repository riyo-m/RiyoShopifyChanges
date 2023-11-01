package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * All tests related to POS and order recall
 */

public class DRetailPOSOrderRecallTest extends DRetailBaseTest {

    /**
     * @TestCase CD365R-296
     * @Description - Validate POS Recall Tax Amount And Amount Due Is Valid
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "Retail_regression" })
    public void validatePOSRecallTaxAmountAndAmountDueTest(){
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        allRetailSalesOrderPage.transactionButton();

        //Add customer "Test Auto"
        allRetailSalesOrderPage.addCustomer("Test Auto");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();
        allRetailSalesOrderPage.productPage();
        allRetailSalesOrderPage.addMensShoes();
        allRetailSalesOrderPage.addItemButton();

        //Create customer order once on transaction page
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.clickCreateCustomerOrder();
        allRetailSalesOrderPage.clickPickUpAll();
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

        //Recall order, filter, select customer and recent order
        allRetailSalesOrderPage.clickRecallOrder();
        allRetailSalesOrderPage.clickAddFilterBtn();
        allRetailSalesOrderPage.clickOnFilterOption("Order date");
        allRetailSalesOrderPage.enterOrderDate();
        allRetailSalesOrderPage.clickApplyFilterBtn();
        allRetailSalesOrderPage.selectSalesOrder();
        allRetailSalesOrderPage.clickPickingAndPacking("Pick up");
        allRetailSalesOrderPage.selectPickUpProducts(0);
        allRetailSalesOrderPage.clickPickUp();

        //Verify tax is only for one item and amount due is 0
        String taxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(taxAmount.equalsIgnoreCase("10.73"),
                "'Total sales tax amount' value is not expected");
        String amountDue = allRetailSalesOrderPage.amountDueValidation("Change due");
        assertTrue(amountDue.equalsIgnoreCase("0.00"),
                "'Total sales tax amount' value is not expected");

        //Pay Cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.closeButton();

        //Recall the same order and select pick up
        allRetailSalesOrderPage.clickRecallOrder();
        allRetailSalesOrderPage.clickAddFilterBtn();
        allRetailSalesOrderPage.clickOnFilterOption("Order date");
        allRetailSalesOrderPage.enterOrderDate();
        allRetailSalesOrderPage.clickApplyFilterBtn();
        allRetailSalesOrderPage.selectSalesOrder();
        allRetailSalesOrderPage.clickPickingAndPacking("Pick up");
        allRetailSalesOrderPage.selectPickUpProducts(1);
        allRetailSalesOrderPage.clickPickUp();

        //Verify tax is only for one item and amount due is 0
        taxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(taxAmount.equalsIgnoreCase("7.42"),
                "'Total sales tax amount' value is not expected");
        amountDue = allRetailSalesOrderPage.amountDueValidation("Change due");
        assertTrue(amountDue.equalsIgnoreCase("0.00"),
                "'Total sales tax amount' value is not expected");

        //Pay Cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.closeButton();
    }
}
