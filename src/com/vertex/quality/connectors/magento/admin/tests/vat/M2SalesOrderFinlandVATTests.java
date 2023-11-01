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
 * Ship To - Finland Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderFinlandVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1163 Sales order Finland to Finland
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderFLFLTest( )
    {
        setShippingOrigin(MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region, MagentoEUAddresses.FINLAND_ADDRESS.zip5, MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1164 Sales order Germany to Finland
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEUFLTest( )
    {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1162 Sales order US to Finland
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSFLTest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoEUAddresses.FINLAND_ADDRESS.country, MagentoEUAddresses.FINLAND_ADDRESS.region,
                MagentoEUAddresses.FINLAND_ADDRESS.city, MagentoEUAddresses.FINLAND_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(24));
        newViewInfoPage = submitOrderForVAT();
    }
}
