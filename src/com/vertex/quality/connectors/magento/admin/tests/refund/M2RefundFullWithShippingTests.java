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
public class M2RefundFullWithShippingTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    M2AdminCreditMemoPage creditMemoPage;
    Map<String, String> billingAddress, shippingAddress;

    String[] products = {MagentoData.ADMIN_PRODUCT_1.data, MagentoData.ADMIN_PRODUCT_2.data, MagentoData.ADMIN_PRODUCT_3.data};
    String[] qty = {MagentoData.quantity_one.data, MagentoData.quantity_five.data, MagentoData.quantity_two.data};
    double calculatedTaxAmount;
    double grandTotalAmount;

    /**
     * tests tax on Return Sales order - full order with shipping
     * CMAGM2-890
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxForRefundFullWithShippingTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addMultipleProductDetails(MagentoData.LOGGED_IN.data, products, qty);
        billingAddress = createBillingAddress(MagentoData.PR_SHIPPING_FIRST_NAME.data,
                MagentoData.PR_SHIPPING_LAST_NAME.data, MagentoData.PR_SHIPPING_STREET0.data,
                MagentoData.PR_SHIPPING_COUNTRY.data, MagentoData.PR_SHIPPING_STATE.data,
                MagentoData.PR_SHIPPING_CITY.data, MagentoData.PR_SHIPPING_ZIP.data,
                MagentoData.PR_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.PR_SHIPPING_FIRST_NAME.data,
                MagentoData.PR_SHIPPING_LAST_NAME.data, MagentoData.PR_SHIPPING_STREET0.data,
                MagentoData.PR_SHIPPING_COUNTRY.data, MagentoData.PR_SHIPPING_STATE.data,
                MagentoData.PR_SHIPPING_CITY.data, MagentoData.PR_SHIPPING_ZIP.data,
                MagentoData.PR_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        calculatedTaxAmount = newOrderPage.getTaxFromUI();
        grandTotalAmount = newOrderPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, newOrderPage.calculatePercentBasedTax(11.5));

        newViewInfoPage = submitOrderAndInvoice();
        newViewInfoPage = submitCreditMemoWithRefund(qty, MagentoData.FULL_RETURN_AMOUNT.data);

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(newViewInfoPage.getRefundedAmountFromUI(), grandTotalAmount);
    }
}
