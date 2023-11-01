package com.vertex.quality.connectors.magento.admin.tests.AddressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminValidateAddressPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;

/**
 * CMAGM2-824 : Test for address cleansing for physical origin
 *
 * @author Mayur.Kumbhar
 */
public class M2ACPhysicalOriginWithInvalidCityAndZipTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    Map<String, String> billingAddress;
    M2AdminValidateAddressPage addressValidate;

    /**
     * CMAGM2-829
     * Test case for physical origin with invalid city and zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginInvalidCityAndZipTest() {
        newOrderPage = addProductAddressDetailsForAddressCleansing();
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data, MagentoData.US_SHIPPING_LAST_NAME.data, Address.Philadelphia.addressLine1,
                Address.Philadelphia.country.fullName, Address.Philadelphia.state.fullName,
                Address.ChesterInvalidAddress.city, Address.ChesterInvalidAddress.zip5, MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage.addBillingAddress(billingAddress);

        addressValidate = new M2AdminValidateAddressPage(driver);
        addressValidate.clickOnValidateAddressButton();

        assertEquals(MagentoData.ADDRESS_CORRECTION_MSG.data, addressValidate.getAddressSuggestionMessageFromUI());
    }
}
