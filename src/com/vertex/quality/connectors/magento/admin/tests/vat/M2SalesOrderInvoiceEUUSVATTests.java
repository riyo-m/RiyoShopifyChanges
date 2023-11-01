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
public class M2SalesOrderInvoiceEUUSVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for EU US tax rate
     * CMAGM2-773
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxAmountInCreateNewOrderEUUSTest() {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, Address.UniversalCity.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data,
                MagentoData.US_SHIPPING_LAST_NAME.data, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, Address.UniversalCity.zip5,
                MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1132 CMAG - Test Case - Create Sale Order for VAT (EU-US - Paris to California) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderFRToUSTest() {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        newViewInfoPage = submitOrderForVAT();
    }
}
