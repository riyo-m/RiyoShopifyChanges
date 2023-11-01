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
 * CMAGM2-831: CMAG - Perform Address Cleansing on the Shipping Address (SHIP_TO)
 *
 * @author Mayur.Kumbhar
 */
public class M2ACShippingAddressWithCalcOffWithInvalidZipTests extends MagentoAdminBaseTest {
    M2AdminCreateNewOrderPage newOrderPage;
    Map<String, String> billingAddress, shippingAddress;
    M2AdminValidateAddressPage addressValidate;

    /**
     * CMAGM2-834:
     * Test case for address cleansing of shipping address with calc off and invalid zip
     */
    @Test(groups = {"magento_regression"})
    public void shippingAddressCleanseWithCalOffAndInvalidZipTest() {
        newOrderPage = addProductAddressDetailsForAddressCleansing();
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data, MagentoData.US_SHIPPING_LAST_NAME.data, Address.ChesterInvalidAddress.addressLine1,
                Address.ChesterInvalidAddress.country.fullName, Address.ChesterInvalidAddress.state.fullName,
                Address.ChesterInvalidAddress.city, Address.ChesterInvalidAddress.zip5, MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage.addBillingAddress(billingAddress);

        addressValidate = new M2AdminValidateAddressPage(driver);
        addressValidate.clickOnValidateAddressButton();

        assertEquals(MagentoData.ADDRESS_CORRECTION_MSG.data, addressValidate.getAddressSuggestionMessageFromUI());
    }
}
