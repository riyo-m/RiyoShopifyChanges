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
public class M2SalesOrderInvoiceGreekVATTests extends MagentoAdminBaseTest {
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for Greek Territory tax rate
     * CMAGM2-779
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderGreekTest() {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();

    }

    /**
     * CMAGM2-1130 CMAG - Test Case - Create Sale Order for VAT (Greek Territory - US to Greece) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSToGreeceTest() {
        setShippingOrigin(Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.addressLine1);

        newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1131 CMAG - Test Case - Create Sale Order for VAT (Greek Territory - Greece to Greece) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderGreeceTest() {
        setShippingOrigin(MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data, MagentoData.GR_SHIPPING_ZIP.data, MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_STREET0.data);

        newOrderPage = addProductsAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.GR_SHIPPING_FIRST_NAME.data,
                MagentoData.GR_SHIPPING_LAST_NAME.data, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data,
                MagentoData.GR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();
    }
}
