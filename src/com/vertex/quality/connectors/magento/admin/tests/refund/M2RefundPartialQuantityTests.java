package com.vertex.quality.connectors.magento.admin.tests.refund;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreditMemoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Tests tax on Create Sale Order for Credit/Adjustment
 * CMAGM2-886
 *
 * @author rohit-mogane
 */
public class M2RefundPartialQuantityTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    M2AdminCreditMemoPage creditMemoPage;
    Map<String, String> billingAddress, shippingAddress;

    String[] products = {MagentoData.ADMIN_PRODUCT_1.data, MagentoData.ADMIN_PRODUCT_2.data, MagentoData.ADMIN_PRODUCT_3.data};
    String[] qty = {MagentoData.quantity_four.data, MagentoData.quantity_zero.data, MagentoData.quantity_zero.data};
    double calculatedTaxAmount;
    double grandTotalAmount;

    /**
     * tests tax on bottom right of page in Create New Order Page for AT tax rate
     * CMAGM2-888
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialQuantityTest() {
        setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);

        newOrderPage = addMultipleProductDetails(MagentoData.LOGGED_IN.data, products, qty);
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

        calculatedTaxAmount = newOrderPage.getTaxFromUI();
        grandTotalAmount = newOrderPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, newOrderPage.calculatePercentBasedTax(19));

        newViewInfoPage = submitOrderAndInvoice();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        newViewInfoPage = creditMemoPage.addAdjustmentRefund(MagentoData.quantity_two.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);

        assertEquals(creditMemoPage.getTaxFromUI(), creditMemoPage.calculatePercentBasedTax(19));
        creditMemoPage.clickRefundOfflineButton();
        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
    }
}
