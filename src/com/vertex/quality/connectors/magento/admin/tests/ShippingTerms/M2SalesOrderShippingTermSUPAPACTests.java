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

/**
 * CMAGM2-943 : Sales VAT - Shipping Term / Incoterms
 * Create Sale Order and Invoice with Shipping Terms SUP APAC.
 *
 * @author vivek-kumar
 */
public class M2SalesOrderShippingTermSUPAPACTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for Shipping Term SUP APAC.
     * CMAGM2-954
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInSUPShippingTermTest() {
        configPage = setShippingOrigin(Address.Singapore.country.fullName, Address.Singapore.city, Address.Singapore.zip5, Address.Singapore.city, Address.Singapore.addressLine1);
        newTaxConfigPage = configPage.clickTaxTab();
        newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
        newTaxConfigPage.saveConfig();
        newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, Address.Japan.addressLine1,
                Address.Japan.country.fullName, Address.Japan.city,
                Address.Japan.city, Address.Japan.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.Japan.addressLine1,
                Address.Japan.country.fullName, Address.Japan.city,
                Address.Japan.city, Address.Japan.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(10));
        newViewInfoPage = submitOrderForVAT();
    }
}
