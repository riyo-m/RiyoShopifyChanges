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
public class M2RefundPartialWithShippingTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    M2AdminCreditMemoPage creditMemoPage;
    Map<String, String> billingAddress, shippingAddress;

    String[] products = {MagentoData.ADMIN_PRODUCT_1.data, MagentoData.ADMIN_PRODUCT_2.data, MagentoData.ADMIN_PRODUCT_3.data};
    String[] qty = {MagentoData.quantity_one.data, MagentoData.quantity_five.data, MagentoData.quantity_two.data};
    double calculatedTaxAmount;
    double grandTotalAmount;

    /**
     * tests tax on Return Sales order - partial order with shipping
     * CMAGM2-889
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialWithShippingTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addMultipleProductDetails(MagentoData.LOGGED_IN.data, products, qty);
        billingAddress = createBillingAddress(MagentoData.AT_BILLING_FIRST_NAME.data,
                MagentoData.AT_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.AT_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        calculatedTaxAmount = newOrderPage.getTaxFromUI();
        grandTotalAmount = newOrderPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));

        newViewInfoPage = submitOrderAndInvoice();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        newViewInfoPage = creditMemoPage.enterAdjustedFee(MagentoData.PARTIAL_RETURN_AMOUNT.data);
        creditMemoPage.clickRefundOfflineButton();

        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(newViewInfoPage.getRefundedAmountFromUI(), grandTotalAmount - Double.parseDouble(MagentoData.PARTIAL_RETURN_AMOUNT.data));
    }
}
