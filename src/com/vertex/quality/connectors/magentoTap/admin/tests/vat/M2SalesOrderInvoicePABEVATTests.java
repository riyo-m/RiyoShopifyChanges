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
public class M2SalesOrderInvoicePABEVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on bottom right of page in Create New Order Page for US EU tax rate
     * CMAGM2-774
     */
    @Test(groups = {"magentoTap_regression"})
    public void checkTaxAmountInCreateNewOrderPABETest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
        newViewInfoPage = submitOrderForVAT();
    }
}
