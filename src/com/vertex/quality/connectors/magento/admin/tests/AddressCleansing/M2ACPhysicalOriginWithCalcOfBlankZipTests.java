package com.vertex.quality.connectors.magento.admin.tests.AddressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminValidateAddressPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * CCMMER-1866 : Magento Oseries: Test Automation Regression suite - IV
 *
 * @author Shivam.Soni
 */
public class M2ACPhysicalOriginWithCalcOfBlankZipTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    Map<String, String> billingAddress;
    M2AdminValidateAddressPage addressValidate;
    String suggestedAddress;

    /**
     * CMAGM2-1094
     * Test Case - Validate addresses with address cleansing OFF - Shipping Address
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void physicalOriginWithCalcOffNullZipTest() {
        newOrderPage = addProductAddressDetailsForAddressCleansing();
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data, MagentoData.US_SHIPPING_LAST_NAME.data, Address.Birmingham.addressLine1,
                Address.Birmingham.country.fullName, Address.Birmingham.state.fullName,
                Address.Birmingham.city, "blank", MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage.addBillingAddress(billingAddress);

        addressValidate = new M2AdminValidateAddressPage(driver);
        addressValidate.clickOnValidateAddressButton();

        suggestedAddress = addressValidate.getSuggestedAddressFromUI();

        assertEquals(MagentoData.ADDRESS_SUGGESTION_MSG.data, addressValidate.getAddressSuggestionMessageFromUI());
        assertTrue(suggestedAddress.contains(Address.Birmingham.addressLine1));
        assertTrue(suggestedAddress.contains(Address.Birmingham.city));
        assertTrue(suggestedAddress.contains(Address.Birmingham.state.fullName));
        assertTrue(suggestedAddress.contains(Address.Birmingham.zip5));
        assertTrue(suggestedAddress.contains(Address.Birmingham.country.fullName));
    }
}
