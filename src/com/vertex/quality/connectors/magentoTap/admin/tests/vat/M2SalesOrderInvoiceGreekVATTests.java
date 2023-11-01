package com.vertex.quality.connectors.magentoTap.admin.tests.vat;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
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
    M2AdminConfigPage configPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for Greek Territory tax rate
     * CMAGM2-779
     */
    @Test(groups = {"magentoTap_regression"})
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
}
