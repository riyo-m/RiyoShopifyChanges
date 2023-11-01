package com.vertex.quality.connectors.magento.admin.tests.MFTF2A;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for an order where an invoice is made for the entire order, but credit memo is only done for partial order
 * Writing to Vertex tax journal when Magento Order Status = Complete,
 *
 * @author alewis
 */
public class M2AOICMBillNotShipPhysicalCO2Tests extends MagentoAdminBaseTest {
    protected String url = MagentoData.ADMIN_SIGN_ON_URL.data;
    protected String username = MagentoData.ADMIN_USERNAME.data;
    protected String password = MagentoData.ADMIN_PASSWORD.data;

    protected String configTitleText = "Configuration / Settings / Stores / Magento Admin";
    protected String salesNavPanelHeaderText = "Sales";
    protected String orderTitleTest = "Orders / Operations / Sales / Magento Admin";
    protected String vertexSettingsHead = "Vertex Settings";
    protected String customerOrdersTitleText = "New Order / Orders / Operations / Sales / Magento Admin";
    protected String SKU1 = "24-WG085_Group";
    protected String SKU2 = "24-WG080";
    protected String customerID = "Jeff Goldblum";
    protected String refundProductString = "Sprite Yoga Strap 6 foot";
    protected String qty = "0";

    String totalRefundedString = "$126.14";

    protected String vertexInvoiceRefunded = "The Vertex invoice has been refunded.";
    protected String magentoCredMemoCreated = "You created the credit memo.";

    /**
     * Test to see that the right refund amount is credited for a partial refund
     */
    @Test()
    public void vertexInvoiceSentAfterCreditMemoTest() {
        M2AdminOrderViewInfoPage infoPage = submitCreditMemo();

        boolean correctString = infoPage.checkMessage(vertexInvoiceRefunded);
        boolean magentoCorrectString = infoPage.checkMessage(magentoCredMemoCreated);

        infoPage.clickTaxBlind();

        String totalRefunded = infoPage.getTotalRefundAmount();

        M2AdminSalesTaxConfigPage configVertexSetting = navigateToSalesTaxConfig();
        configVertexSetting.resetConfigSettingToDefault();
        configVertexSetting.clickIntegrationSettingsTab();

        assertTrue(correctString == true);
        assertTrue(magentoCorrectString == true);
        assertEquals(totalRefunded, totalRefundedString);
        assertFalse(configVertexSetting.verifyWhenOrderStatusField());
    }

    /**
     * loads and signs into this configuration site
     *
     * @return a representation of the page that loads immediately after
     * successfully signing into this configuration site
     */
    protected M2AdminHomepage signInToAdminHomepage() {
        driver.get(url);

        M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

        signOnPage.enterUsername(username);

        signOnPage.enterPassword(password);

        M2AdminHomepage homepage = signOnPage.login();

        return homepage;
    }

    /**
     * tests whether navigation can reach the configPage
     *
     * @return the configuration page
     */
    protected M2AdminConfigPage navigateToConfig() {
        M2AdminHomepage homePage = signInToAdminHomepage();

        homePage.navPanel.clickStoresButton();

        M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

        return configPage;
    }

    /**
     * tests whether navigation can reach the M2AdminSalesTaxConfigPage
     *
     * @return Tax Settings Page
     */
    protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfigChange() {
        M2AdminConfigPage configPage = navigateToConfig();

        configPage.clickSalesTab();
        M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

        taxSettingsPage.clickIntegrationSettingsTab();
        taxSettingsPage.changeSendInvoiceToVertexField("Order Status Is Changed");
        taxSettingsPage.changeInvoiceWhenOrderStatusField("Complete");
        taxSettingsPage.saveConfig();

        return taxSettingsPage;
    }

    /**
     * tests whether navigation can reach the OrdersPage
     *
     * @return the Orders Page
     */
    protected M2AdminOrdersPage navigateToOrders() {
        M2AdminSalesTaxConfigPage salesTaxConfigPage = navigateToSalesTaxConfigChange();

        salesTaxConfigPage.navPanel.clickSalesButton();

        M2AdminOrdersPage m2AdminOrdersPage = salesTaxConfigPage.navPanel.clickOrdersButton();

        return m2AdminOrdersPage;
    }

    /**
     * tests whether navigation can reach the M2AdminOrderCustomerPage
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage navigateToOrderCustomerPage() {
        M2AdminOrdersPage ordersPage = navigateToOrders();
        M2AdminOrderCustomerPage orderCustomerPage = ordersPage.clickNewOrderButton();
        M2AdminCreateNewOrderPage newOrderPage = orderCustomerPage.clickCustomer(customerID);
        return newOrderPage;
    }

    /**
     * tests whether navigation can reach the M2AdminOrderCustomerPage
     *
     * @return the Order Customers Page
     */
    protected M2AdminCreateNewOrderPage addProductToSalesOrders() {
        M2AdminCreateNewOrderPage newOrderPage = navigateToOrderCustomerPage();

        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumber(SKU1);
        newOrderPage.enterQty();
        newOrderPage.clickAddToOrderButton();
        newOrderPage.configureOrder();
        newOrderPage.clickAddProductsToOrderButton();

        newOrderPage.clickUpdateItemsAndQuantitiesButton();

        newOrderPage.clickAddSKUButton();
        newOrderPage.enterSKUNumber(SKU2);
        newOrderPage.enterQty();
        newOrderPage.clickAddToOrderButton();
        newOrderPage.simpleConfigureOrder();
        newOrderPage.clickAddProductsToOrderButton();

        return newOrderPage;
    }

    /**
     * Navigation to Order Info after order is placed
     *
     * @return Order View Info Page
     */
    protected M2AdminOrderViewInfoPage navigateOrderViewInfoPage() {
        M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();
        newOrderPage.selectCheckAsPaymentMethod();
        newOrderPage.addShippingMethod(2);
        M2AdminOrderViewInfoPage infoPage = newOrderPage.clickSubmitOrderButton();

        infoPage.clickTaxBlind();

        return infoPage;
    }

    /**
     * Navigation to New Invoice page after placing an order
     *
     * @return Order View Info page
     */
    protected M2AdminNewInvoicePage navigateToNewInvoicePage() {
        M2AdminOrderViewInfoPage newOrderPage = navigateOrderViewInfoPage();
        M2AdminNewInvoicePage invoicePage = newOrderPage.clickInvoiceButton();

        invoicePage.openTaxBlind();

        return invoicePage;
    }

    /**
     * Navigation to Credit Memo page after inputting an invoice
     *
     * @return Credit Order page
     */
    protected M2AdminCreditMemoPage navigateToNewMemoPage() {
        M2AdminNewInvoicePage newInvoicePage = navigateToNewInvoicePage();
        newInvoicePage.clickSubmitInvoiceButton();
        M2AdminShipPage shipPage = newInvoicePage.navigateToShipPage();
        shipPage.submitShipment();
        M2AdminCreditMemoPage creditMemoPage = newInvoicePage.clickCreditMemoButton();
        creditMemoPage.clicksTaxBlind();

        return creditMemoPage;
    }

    /**
     * submits the order
     *
     * @return the admin order view info page
     */
    protected M2AdminOrderViewInfoPage submitCreditMemo() {
        M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();
        creditMemoPage.doPartialRefund(refundProductString, qty);
        M2AdminOrderViewInfoPage orderViewInfoPage = creditMemoPage.clickRefundOfflineButton();

        return orderViewInfoPage;
    }

    /**
     * tests whether navigation can reach the M2AdminSalesTaxConfigPage
     *
     * @return Tax Settings Page
     */
    protected M2AdminSalesTaxConfigPage navigateToSalesTaxConfig() {
        M2AdminHomepage homePage = new M2AdminHomepage(driver);

        homePage.navPanel.clickStoresButton();

        M2AdminConfigPage configPage = homePage.navPanel.clickConfigButton();

        configPage.clickSalesTab();

        M2AdminSalesTaxConfigPage taxSettingsPage = configPage.clickTaxTab();

        return taxSettingsPage;
    }
}