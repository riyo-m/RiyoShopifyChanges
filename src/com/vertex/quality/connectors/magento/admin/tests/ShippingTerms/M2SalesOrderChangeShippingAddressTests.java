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
 * CMAGM2-943 : Sales VAT - Shipping Term / Incoterms.
 * Create Sale Order and Invoice different Shipping Address.
 *
 * @author vivek-kumar
 */
public class M2SalesOrderChangeShippingAddressTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-944
     * tests tax on bottom right of page in Create New Order Page for different ship address.
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInChangeShippingAddressTest() {
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

        shippingAddress = createShippingAddress(MagentoData.US_SHIPPING6_FIRST_NAME.data,
                MagentoData.US_SHIPPING6_LAST_NAME.data, Address.Washington.addressLine1,
                Address.Washington.country.fullName, Address.Washington.state.fullName,
                Address.Washington.city, Address.Washington.zip5,
                MagentoData.US_SHIPPING6_PHONE.data);
        newOrderPage = addBillingShippingAddress(shippingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(10.1));
        newViewInfoPage = submitOrderForVAT();
    }
}
