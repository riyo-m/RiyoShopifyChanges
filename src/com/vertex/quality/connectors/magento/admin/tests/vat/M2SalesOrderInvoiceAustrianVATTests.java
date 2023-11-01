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
public class M2SalesOrderInvoiceAustrianVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for AT tax rate
     * CMAGM2-776
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderAustrianTest() {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1135 CMAG - Test Case - Create Sale Order for VAT (Austrian Sub-Division, US to Austria) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSToAustrianTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1136 CMAG - Test Case - Create Sale Order for VAT (Austrian Sub-Division, Austria to Austria) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderAustriaTest() {
        setShippingOrigin(MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data, MagentoData.AT_SHIPPING_ZIP.data, MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_STREET0.data);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }
}
