package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailCustomerPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

/**
 * This class represents Retail Shipping charges Tests
 * @author dpatel
 */
public class DRetailShippingChargeTaxTests extends DRetailBaseTest {

    /**
     * Validate that if user select an item to be shipped than there is no error message displayed
     * CD365R-182
     */
    @Test(groups = { "Retail_regression"})
    public void taxOnShippingChargesTest( )
    {
        //================script implementation========================
        //Navigate to add product page
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);
        DRetailCustomerPage customerPage = new DRetailCustomerPage(driver);

        //Navigate to all product page
        allRetailSalesOrderPage.productPage();

        //Navigate to accessories page
        allRetailSalesOrderPage.addSunglass();

        //Select pink sun glass and add to the cart
        allRetailSalesOrderPage.selectPinkSunglass();

        //Click on add item button
        allRetailSalesOrderPage.addItemButton();

        //Navigate to transaction page
        allRetailSalesOrderPage.transactionButton();

        //Navigate to all product page
        allRetailSalesOrderPage.productPage();

        //Select the exchange same item and add to the cart
        allRetailSalesOrderPage.addMensPants();

        //Click on add item button
        allRetailSalesOrderPage.addItemButton();

        //Navigate to transaction page
        allRetailSalesOrderPage.transactionButton();

        //Add customer to the sale
        //Navigate to transaction page
        allRetailSalesOrderPage.transactionButton();

        customerPage.clickFindCustomerButton();
        customerPage.searchCustomer();
        customerPage.addCustomer();

        //Add standard shipping
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.selectFirstItem("Pink Thick Rimmed Sunglasses");
        allRetailSalesOrderPage.shipSelectedStandardOvernightShipping();
        allRetailSalesOrderPage.selectItem("Straight Leg Pants");
        allRetailSalesOrderPage.selectCarryOutSelected();
        //Validate the tax amount after adding the returning item to the cart
        String returnTaxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(returnTaxAmount.equalsIgnoreCase("13.57"),
                "'Total sales tax amount' value is not expected");
        //Complete the transaction , pay cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }
}
