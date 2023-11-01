package com.vertex.quality.connectors.magento.admin.tests.vat;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Tests tax on Create Sale Order for VAT (US-EU) and Invoice
 * CMAGM2-772
 *
 * @author rohit-mogane
 */
public class M2SalesOrderInvoiceDEFRVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for DE FR tax rate
     * CMAGM2-778
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxAmountInCreateNewOrderDEFRTest() {
        setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.FR_SHIPPING_FIRST_NAME.data,
                MagentoData.FR_SHIPPING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.FR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1133 CMAG - Test Case -Consignment Sales Order Invoice for VAT (FR-FR)
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderFranceTest() {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.FR_SHIPPING_FIRST_NAME.data,
                MagentoData.FR_SHIPPING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.FR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1134 CMAG - Test Case -Consignment Sales Order Invoice for VAT (US-FR)
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSToFranceTest() {
        setShippingOrigin(Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.FR_SHIPPING_FIRST_NAME.data,
                MagentoData.FR_SHIPPING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.FR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }
}
