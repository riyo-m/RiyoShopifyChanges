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
public class M2MultiDiscountsChangeDiscountsTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests discount applied to line items on Create New Order Page.
     * CMAGM2-878
     */
    @Test(groups = {"magento_regression"})
    public void checkCreateOrderDiscountTest() {
        setShippingOrigin(Address.Colorado.country.fullName, Address.Colorado.state.fullName, Address.Colorado.zip5, Address.Colorado.city, Address.Colorado.addressLine1);

        newOrderPage = addMultipleLineProductDetails(MagentoData.TEN_PERCENT_ORDER.data);
        newOrderPage.enterCouponCode(MagentoData.FIVE_PERCENT_ORDER.data);
        newOrderPage.clickApplyDiscountButton();
        billingAddress = createBillingAddress(MagentoData.US_BILLING_FIRST_NAME.data,
                MagentoData.US_BILLING_LAST_NAME.data, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5,
                MagentoData.US_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data,
                MagentoData.US_SHIPPING_LAST_NAME.data, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5,
                MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, billingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(9.45));
        newViewInfoPage = submitOrder();
    }
}
