package com.vertex.quality.connectors.magento.admin.tests.discounts;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Tests Discount applied on line items while Creating Sale Order and Invoice.
 * CMAGM2-877
 *
 * @author vivek-kumar
 */
public class M2DiscountShippingForVatTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests discount applied to line items on Create New Order Page.
     * CMAGM2-884
     */
    @Test(groups = {"magento_regression"})
    public void checkCreateOrderDiscountTest() {
        setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);

        newOrderPage = addMultipleLineProductDetails(MagentoData.FIVE_PERCENT_ITEM.data);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, MagentoData.AT_BILLING_STREET0.data,
                MagentoData.AT_BILLING_COUNTRY.data, MagentoData.AT_BILLING_STATE.data,
                MagentoData.AT_BILLING_CITY.data, MagentoData.AT_BILLING_ZIP.data,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, billingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(19));
        newViewInfoPage = submitOrder();
    }
}
