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
 * Ship To - Sweden Tests
 *
 * @author Shivam.Soni
 */
public class M2SalesOrderSwedenVATTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String,String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1204 Sales order Sweden to Sweden
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderSESETest( )
    {
        setShippingOrigin(MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region, MagentoEUAddresses.SWEDEN_ADDRESS.zip5, MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1);
        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(25));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1206 Sales order France to Sweden
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderEUSETest( )
    {
        setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(25));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1205 Sales order US to Sweden
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInCreateNewOrderUSSETest( )
    {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
                MagentoData.EU_SHIPPING_LAST_NAME.data, MagentoEUAddresses.SWEDEN_ADDRESS.addressLine1,
                MagentoEUAddresses.SWEDEN_ADDRESS.country, MagentoEUAddresses.SWEDEN_ADDRESS.region,
                MagentoEUAddresses.SWEDEN_ADDRESS.city, MagentoEUAddresses.SWEDEN_ADDRESS.zip5,
                MagentoData.EU_SHIPPING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(25));
        newViewInfoPage = submitOrderForVAT();
    }
}
