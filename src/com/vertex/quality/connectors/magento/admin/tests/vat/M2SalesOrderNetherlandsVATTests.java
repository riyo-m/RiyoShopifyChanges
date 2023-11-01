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
 * Ship To - Netherlands Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderNetherlandsVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1185 Sales order Netherlands to Netherlands
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderNLNLTest( )
    {
        setShippingOrigin(MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5, MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1183 Sales order France to Netherlands
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEUNLTest( )
    {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1184 Sales order US to Netherlands
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSNLTest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.country, MagentoEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoEUAddresses.NETHERLANDS_ADDRESS.city, MagentoEUAddresses.NETHERLANDS_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }
}
