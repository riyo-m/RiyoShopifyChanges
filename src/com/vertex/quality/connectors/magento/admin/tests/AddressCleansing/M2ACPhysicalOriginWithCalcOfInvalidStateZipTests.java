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
 * CCMMER-1866 : Magento Oseries: Test Automation Regression suite - IV
 *
 * @author Shivam.Soni
 */
public class M2ACPhysicalOriginWithCalcOfInvalidStateZipTests extends MagentoAdminBaseTest {

    M2AdminCreateNewOrderPage newOrderPage;
    Map<String, String> billingAddress;
    M2AdminValidateAddressPage addressValidate;

    /**
     * CMAGM2-1092
     * Test Case - Address Cleansing OFF with Invalid State and Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginWithCalcOffInvalidZipTest() {
        newOrderPage = addProductAddressDetailsForAddressCleansing();
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING_FIRST_NAME.data, MagentoData.US_SHIPPING_LAST_NAME.data, Address.Philadelphia.addressLine1,
                Address.Philadelphia.country.fullName, Address.ChesterInvalidAddress.state.fullName,
                Address.Philadelphia.city, Address.ChesterInvalidAddress.zip5, MagentoData.US_SHIPPING_PHONE.data);
        newOrderPage.addBillingAddress(billingAddress);

        addressValidate = new M2AdminValidateAddressPage(driver);
        addressValidate.clickOnValidateAddressButton();

        assertEquals(MagentoData.ADDRESS_CORRECTION_MSG.data, addressValidate.getAddressSuggestionMessageFromUI());
    }
}
