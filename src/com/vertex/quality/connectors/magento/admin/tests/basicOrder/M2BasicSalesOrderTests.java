package com.vertex.quality.connectors.magento.admin.tests.basicOrder;

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
 * Magento O-Series Basic Sales Order tests
 */
public class M2BasicSalesOrderTests extends MagentoAdminBaseTest {
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-1115 CMAGM2 - Test Case -Create Sales Order with no State Tax
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderWithNoStateTaxTest() {
        setShippingOrigin(Address.Colorado.country.fullName, Address.Colorado.state.fullName, Address.Colorado.zip5, Address.Colorado.city, Address.Colorado.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Delaware.addressLine1,
                Address.Delaware.country.fullName, Address.Delaware.state.fullName,
                Address.Delaware.city, Address.Delaware.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Delaware.addressLine1,
                Address.Delaware.country.fullName, Address.Delaware.state.fullName,
                Address.Delaware.city, Address.Delaware.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertFalse(newOrderPage.isTaxLabelPresent());
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1116 CMAGM2 - Test Case -Create Sales Order with no State Tax, locally administered
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderWithNoStateTaxLocallyAdministeredTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Juneau.addressLine1,
                Address.Juneau.country.fullName, Address.Juneau.state.fullName,
                Address.Juneau.city, Address.Juneau.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.Juneau.addressLine1,
                Address.Juneau.country.fullName, Address.Juneau.state.fullName,
                Address.Juneau.city, Address.Juneau.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(5));
        newViewInfoPage = submitOrderForVAT();
    }

    /**
     * CMAGM2-1117 CMAGM2 - Test Case -Create Sales Order with Modified Origin State
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderWithModifiedOriginStateTest() {
        setShippingOrigin(Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.addressLine1);

        newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, Address.UniversalCity.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
                MagentoData.EU_BILLING_LAST_NAME.data, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, Address.UniversalCity.zip5,
                MagentoData.EU_BILLING_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        newViewInfoPage = submitOrderForVAT();
    }
}