package com.vertex.quality.connectors.magentoTap.admin.tests.vat;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
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
public class M2SalesOrderInvoiceIntraFRDEVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for Intra EU FR-DE tax rate
     * CMAGM2-775
     */
    @Test(groups = {"magentoTap_regression"})
    public void checkTaxAmountInCreateNewOrderIntraFRDETest() {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

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

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(19));
        newViewInfoPage = submitOrderForVAT();
    }
}
