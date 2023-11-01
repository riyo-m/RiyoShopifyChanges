package com.vertex.quality.connectors.magento.admin.tests.vat;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.enums.MagentoEUAddresses;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Ship To - Latvia Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderLatviaVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1172 Sales order Latvia to Latvia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderLVLVTest( )
    {
        setShippingOrigin(MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region, MagentoEUAddresses.LATVIA_ADDRESS.zip5, MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1171 Sales order Germany to Latvia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEULVTest( )
    {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1173 Sales order US to Latvia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSLVTest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.LATVIA_ADDRESS.addressLine1,
                MagentoEUAddresses.LATVIA_ADDRESS.country, MagentoEUAddresses.LATVIA_ADDRESS.region,
                MagentoEUAddresses.LATVIA_ADDRESS.city, MagentoEUAddresses.LATVIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }
}
