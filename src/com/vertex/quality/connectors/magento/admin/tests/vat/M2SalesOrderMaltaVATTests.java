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
 * Ship To - Malta Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderMaltaVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1180 Sales order Malta to Malta
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderMTMTTest( )
    {
        setShippingOrigin(MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region, MagentoEUAddresses.MALTA_ADDRESS.zip5, MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(18));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1181 Sales order Germany to Malta
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEUMTTest( )
    {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(18));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1182 Sales order US to Malta
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSMTTest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.MALTA_ADDRESS.addressLine1,
                MagentoEUAddresses.MALTA_ADDRESS.country, MagentoEUAddresses.MALTA_ADDRESS.region,
                MagentoEUAddresses.MALTA_ADDRESS.city, MagentoEUAddresses.MALTA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(18));
        newViewInfoPage = submitOrderForVAT();
    }
}
