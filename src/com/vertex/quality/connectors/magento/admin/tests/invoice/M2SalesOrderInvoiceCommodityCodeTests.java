package com.vertex.quality.connectors.magento.admin.tests.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Tests tax on Create Sale Order with commodity codes
 * CMAGM2-821
 *
 * @author rohit-mogane
 */
public class M2SalesOrderInvoiceCommodityCodeTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * test to create a basic sales order with commodity codes and invoice and validate tax rate
     * CMAGM2-822
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderCommodityTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductsAddressDetails(MagentoData.QA_COMMODITY_CODE_PRODUCT.data, MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, Address.Berwyn.addressLine1,
                Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                Address.Berwyn.city, Address.Berwyn.zip5,
                MagentoData.AT_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.AT_SHIPPING_FIRST_NAME.data,
                MagentoData.AT_SHIPPING_LAST_NAME.data, Address.Berwyn.addressLine1,
                Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                Address.Berwyn.city, Address.Berwyn.zip5,
                MagentoData.AT_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(false, true, 6));
        newViewInfoPage = submitOrderForVAT();
    }
}
