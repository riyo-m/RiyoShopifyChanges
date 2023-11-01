package com.vertex.quality.connectors.magento.admin.tests.CustomerCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertFalse;

/**
 * Tests tax on order with customer code & customer class exemption
 * CMAGM2-939
 *
 * @author rohit-mogane
 */
public class M2CustomerCodeExemptionTests extends MagentoAdminBaseTest {
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * tests tax on order with customer code exemption
     * CMAGM2-937
     */
    @Test(groups = {"magento_regression"})
    public void checkCustomerCodeExemptionTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetailsForCustomerCodeClass(MagentoData.CUSTOMER_CODE_CUSTOMER_NAME.data, MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data,
                MagentoData.US_SHIPPING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.US_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data,
                MagentoData.US_SHIPPING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertFalse(newOrderPage.isTaxLabelPresent());
        newViewInfoPage = submitOrderAndInvoice();
    }
}
