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
 * Ship To - Czechia Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderCzechiaVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1158 Sales order Czechia to Czechia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderCZCZTest( )
    {
        setShippingOrigin(MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region, MagentoEUAddresses.CZECHIA_ADDRESS.zip5, MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1156 Sales order Germany to Czechia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEUCZTest( )
    {
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1157 Sales order US to Czechia
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSCZTest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.CZECHIA_ADDRESS.addressLine1,
                MagentoEUAddresses.CZECHIA_ADDRESS.country, MagentoEUAddresses.CZECHIA_ADDRESS.region,
                MagentoEUAddresses.CZECHIA_ADDRESS.city, MagentoEUAddresses.CZECHIA_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(21));
        newViewInfoPage = submitOrderForVAT();
    }
}
