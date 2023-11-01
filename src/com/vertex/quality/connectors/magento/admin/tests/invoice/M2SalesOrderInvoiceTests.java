package com.vertex.quality.connectors.magento.admin.tests.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * @author Shivam.Soni
 * Magento O-Series Sales Orders
 */
public class M2SalesOrderInvoiceTests extends MagentoAdminBaseTest {
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-806 CMAG - Test Case - End to End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"magento_regression"})
    public void changeLocationUpdateQtyEndToEndTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        newViewInfoPage = submitOrderForVAT();

        newOrderPage = addProductAddressDetails(MagentoData.quantity_two.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(9.45));
        newViewInfoPage = submitOrderForVAT();

        newOrderPage = addProductAddressDetails(MagentoData.quantity_five.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Colorado.addressLine1,
                Address.Colorado.country.fullName, Address.Colorado.state.fullName,
                Address.Colorado.city, Address.Colorado.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Colorado.addressLine1,
                Address.Colorado.country.fullName, Address.Colorado.state.fullName,
                Address.Colorado.city, Address.Colorado.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTaxForColorado(5, 0));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-809 CMAGM2 - Test Case -Create Sales Order with Invoice
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderWithInvoiceTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-810 CMAG - Test Case -Create Sales Order different ship and bill
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderWithDifferentBillShipTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Delaware.addressLine1,
                Address.Delaware.country.fullName, Address.Delaware.state.fullName,
                Address.Delaware.city, Address.Delaware.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertFalse(newOrderPage.isTaxLabelPresent());
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-813 CMAG - Test Case -Create Sales Order with Invoice US to CAN
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderUSToCANTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(12));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-814 CMAG - Test Case -Create Sales Order with Invoice CAN to US
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderCANToUSTest() {
        setShippingOrigin(Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Gettysburg.addressLine1,
                Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName,
                Address.Gettysburg.city, Address.Gettysburg.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Gettysburg.addressLine1,
                Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName,
                Address.Gettysburg.city, Address.Gettysburg.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(6));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-815 Test Case -Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderCANNBToCANNBTest() {
        setShippingOrigin(Address.GrandManan.country.fullName, Address.GrandManan.province.fullName, Address.GrandManan.zip5, Address.GrandManan.city, Address.GrandManan.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Quispamsis.addressLine1,
                Address.Quispamsis.country.fullName, Address.Quispamsis.province.fullName,
                Address.Quispamsis.city, Address.Quispamsis.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Quispamsis.addressLine1,
                Address.Quispamsis.country.fullName, Address.Quispamsis.province.fullName,
                Address.Quispamsis.city, Address.Quispamsis.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(15));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-816 CMAG - Test Case -Create Sales Order with Invoice CANQC to CANBC different Province (GST/PST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANQCToCANBCTest() {
        setShippingOrigin(Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(12));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-818 CMAG - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANBCToCANONTest() {
        setShippingOrigin(Address.Victoria.country.fullName, Address.Victoria.province.fullName, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Ottawa.addressLine1,
                Address.Ottawa.country.fullName, Address.Ottawa.province.fullName,
                Address.Ottawa.city, Address.Ottawa.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Ottawa.addressLine1,
                Address.Ottawa.country.fullName, Address.Ottawa.province.fullName,
                Address.Ottawa.city, Address.Ottawa.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(13));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-819 CMAG - Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANBCToCANQCTest() {
        setShippingOrigin(Address.Victoria.country.fullName, Address.Victoria.province.fullName, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.QuebecCity.addressLine1,
                Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName,
                Address.QuebecCity.city, Address.QuebecCity.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.QuebecCity.addressLine1,
                Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName,
                Address.QuebecCity.city, Address.QuebecCity.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(14.975));
        newViewInfoPage = submitOrderForVAT();
    }
}