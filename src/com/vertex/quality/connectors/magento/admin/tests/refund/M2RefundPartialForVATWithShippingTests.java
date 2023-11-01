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
public class M2RefundPartialForVATWithShippingTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    M2AdminCreditMemoPage creditMemoPage;
    Map<String, String> billingAddress, shippingAddress;

    String[] products = {MagentoData.ADMIN_PRODUCT_1.data, MagentoData.ADMIN_PRODUCT_2.data, MagentoData.ADMIN_PRODUCT_3.data};
    String[] qty = {MagentoData.quantity_four.data, MagentoData.quantity_zero.data, MagentoData.quantity_zero.data};
    double calculatedTaxAmount;
    double grandTotalAmount;

    /**
     * tests tax on Return Sales order - partial quantity returned
     * CMAGM2-891
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialForVATWithShippingTest() {
        setShippingOrigin(Address.Edison.country.fullName, Address.Edison.state.fullName, Address.Edison.zip5, Address.Edison.city, Address.Edison.addressLine1);

        newOrderPage = addMultipleProductDetails(MagentoData.LOGGED_IN.data, products, qty);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.Dallas.addressLine1,
                Address.Dallas.country.fullName, Address.Dallas.state.fullName,
                Address.Dallas.city, Address.Dallas.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.DE_SHIPPING_FIRST_NAME.data,
                MagentoData.DE_SHIPPING_LAST_NAME.data, Address.Dallas.addressLine1,
                Address.Dallas.country.fullName, Address.Dallas.state.fullName,
                Address.Dallas.city, Address.Dallas.zip5,
                MagentoData.DE_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        calculatedTaxAmount = newOrderPage.getTaxFromUI();
        grandTotalAmount = newOrderPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, newOrderPage.calculatePercentBasedTax(8.25));

        newViewInfoPage = submitOrderAndInvoice();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.removeShippingFromRefund();
        creditMemoPage.addAdjustmentRefund(MagentoData.quantity_two.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);
        assertEquals(creditMemoPage.getTaxFromUI(), creditMemoPage.calculatePercentBasedTax(8.25));
        creditMemoPage.clickRefundOfflineButton();
        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
    }
}
