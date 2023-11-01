package com.vertex.quality.connectors.magento.admin.tests.ShippingTerms;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.components.M2AdminNavigationPanel;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * CMAGM2-943 : Sales VAT - Shipping Term / Incoterms
 * Create Sale Order and Invoice Change Shipping Terms CUS-SUP.
 *
 * @author vivek-kumar
 */
public class M2SalesOrderChangeShippingTermTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminNavigationPanel navigationPanel;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-945
     * tests tax on bottom right of page in Create New Order Page for change in shipping term CUS-SUP.
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInChangeShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
            newTaxConfigPage.saveConfig();
            newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
            billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                    MagentoData.EU_BILLING_LAST_NAME.data, Address.Berwyn.addressLine1,
                    Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                    Address.Berwyn.city, Address.Berwyn.zip5,
                    MagentoData.EU_BILLING_PHONE.data);
            shippingAddress = createShippingAddress(MagentoData.US_SHIPPING2_FIRST_NAME.data,
                    MagentoData.US_SHIPPING2_LAST_NAME.data, Address.Berwyn.addressLine1,
                    Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                    Address.Berwyn.city, Address.Berwyn.zip5,
                    MagentoData.US_SHIPPING2_PHONE.data);
            newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

            assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(6));
            newViewInfoPage = submitOrderForVAT();

            navigationPanel = new M2AdminNavigationPanel(driver);
            navigationPanel.clickStoresButton();
            navigationPanel.clickConfigButton();
            configPage = new M2AdminConfigPage(driver);

            configPage.clickSalesTab();
            configPage.clickTaxTab();

            newTaxConfigPage = new M2AdminSalesTaxConfigPage(driver);
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();

            newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
            billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                    MagentoData.EU_BILLING_LAST_NAME.data, Address.Berwyn.addressLine1,
                    Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                    Address.Berwyn.city, Address.Berwyn.zip5,
                    MagentoData.EU_BILLING_PHONE.data);
            shippingAddress = createShippingAddress(MagentoData.US_SHIPPING2_FIRST_NAME.data,
                    MagentoData.US_SHIPPING2_LAST_NAME.data, Address.Berwyn.addressLine1,
                    Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                    Address.Berwyn.city, Address.Berwyn.zip5,
                    MagentoData.US_SHIPPING2_PHONE.data);
            newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

            assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(6));

            newViewInfoPage = submitOrderForVAT();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            quitDriver();
            createChromeDriver();

            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }
}
