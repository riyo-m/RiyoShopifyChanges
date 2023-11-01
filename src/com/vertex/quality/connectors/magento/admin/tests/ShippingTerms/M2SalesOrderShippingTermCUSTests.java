package com.vertex.quality.connectors.magento.admin.tests.ShippingTerms;

import com.vertex.quality.common.enums.Address;
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
 * Create Sale Order and Invoice with Shipping Terms CUS.
 *
 * @author vivek-kumar
 */
public class M2SalesOrderShippingTermCUSTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-948
     * tests tax on bottom right of page in Create New Order Page for Shipping Term CUS.
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxAmountInCUSShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
            newTaxConfigPage.saveConfig();
            newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
            billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                    MagentoData.EU_SHIPPING_LAST_NAME.data, Address.Berlin.addressLine1,
                    Address.Berlin.country.fullName, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5,
                    MagentoData.EU_SHIPPING_PHONE.data);
            shippingAddress = createShippingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                    MagentoData.AT_BILLING_LAST_NAME.data, Address.Berlin.addressLine1,
                    Address.Berlin.country.fullName, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5,
                    MagentoData.AT_BILLING_PHONE.data);
            newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

            assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
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
