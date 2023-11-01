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
public class M2ACPhysicalOriginWithCalcOfNoZipTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    Map<String, String> billingAddress;
    M2AdminValidateAddressPage addressValidate;
    String suggestedAddress;

    /**
     * CMAGM2-1091
     * Test Case - Address Cleansing OFF with No Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginWithCalcOffInvalidZipTest() {
        newOrderPage = addProductAddressDetailsForAddressCleansing();
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data, MagentoData.US_SHIPPING_LAST_NAME.data, Address.Philadelphia.addressLine1,
                Address.Philadelphia.country.fullName, Address.Philadelphia.state.fullName,
                Address.Philadelphia.city, "empty", MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage.addBillingAddress(billingAddress);

        addressValidate = new M2AdminValidateAddressPage(driver);
        addressValidate.clickOnValidateAddressButton();

        suggestedAddress = addressValidate.getSuggestedAddressFromUI();

        assertEquals(MagentoData.ADDRESS_SUGGESTION_MSG.data, addressValidate.getAddressSuggestionMessageFromUI());
        assertTrue(suggestedAddress.contains(Address.Philadelphia.addressLine1));
        assertTrue(suggestedAddress.contains(Address.Philadelphia.city));
        assertTrue(suggestedAddress.contains(Address.Philadelphia.state.fullName));
        assertTrue(suggestedAddress.contains(Address.Philadelphia.zip9));
        assertTrue(suggestedAddress.contains(Address.Philadelphia.country.fullName));
    }
}
