package com.vertex.quality.connectors.woocommerce.admin.tests;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.admin.tests.base.WooCommerceAdminBaseTest;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CWOO-472 WOO - Perform Address Cleansing on Physical Origin (SHIP_FROM).
 *
 * @author Vivek.Kumar
 */
public class WooCommerceShipFromAddressCleansingTests extends WooCommerceAdminBaseTest {
    /**
     * CWOO-466 WOO - Address Cleansing OFF with Invalid Zip
     * this method is to check address cleansing features with Invalid zip.
     */
    @Test(groups = {"woo_smoke", "woo_addressCleansing"})
    public void addressCleansingInvalidZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickWooCommerceSetting();
        homePage.changeShipFromAddress(WooCommerceData.US_PA_ADDRESS_LINE_ONE.data, WooCommerceData.US_PA_CITY.data, WooCommerceData.US_PA_ZIP.data);
        homePage.enterAddressZip(WooCommerceData.INCORRECT_ZIP.data);
        assertEquals(homePage.getAlertMessage(), WooCommerceData.ADDRESS_CLEANSING_SHIP_TO_INVALID_ZIP_MESSAGE.data);
    }

    /**
     * CWOO-470 WOO - Address Cleansing OFF with Invalid State and Zip
     * this method is to check address cleansing features with Invalid zip and state.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void addressCleansingInvalidStateZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickWooCommerceSetting();
        homePage.enterCountry(WooCommerceData.INVALID_STATE.data);
        homePage.enterAddressLineOne(WooCommerceData.PHILADELPHIA_ADDRESS_LINE.data);
        homePage.enterAddressCity(WooCommerceData.US_CITIES.data);
        homePage.enterAddressZip(WooCommerceData.PHILADELPHIA_INCOMPLETE_ZIP.data);
        assertEquals(homePage.cancelAlertMessage(), WooCommerceData.INVALID_STATE_ZIP_MESSAGE.data);
    }

    /**
     * CWOO-471 WOO- Validate addresses with address cleansing OFF - Physical Origin
     * this method is to check address cleansing features with blank zip.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void addressCleansingBlankZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickWooCommerceSetting();
        homePage.enterCountry(WooCommerceData.US_PA_COUNTRY.data);
        homePage.enterAddressLineOne(WooCommerceData.US_PA_ADDRESS_LINE_ONE.data);
        homePage.enterAddressCity(WooCommerceData.US_PA_CITY.data);
        homePage.enterAddressZip(WooCommerceData.INCORRECT_ZIP.data);
        assertEquals(homePage.getAlertMessage(), WooCommerceData.ADDRESS_CLEANSING_SHIP_TO_INVALID_ZIP_MESSAGE.data);
    }

    /**
     * CWOO-469 WOO - Address Cleansing OFF with Invalid City and Zip
     * this method is to check address cleansing features with invalid zip and city.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void addressCleansingInvalidCityZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickWooCommerceSetting();
        homePage.enterCountry(WooCommerceData.US_PA_COUNTRY.data);
        homePage.enterAddressLineOne(WooCommerceData.PHILADELPHIA_ADDRESS_LINE.data);
        homePage.enterAddressCity(WooCommerceData.INVALID_CITY.data);
        homePage.enterAddressZip(WooCommerceData.PHILADELPHIA_INCOMPLETE_ZIP.data);
        assertEquals(homePage.getAlertMessage(), WooCommerceData.INVALID_CITY_ZIP_MESSAGE.data);
    }

    /**
     * CWOO-467 WOO - Address Cleansing OFF with No Zip
     * this method is to check address cleansing features with no zip.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void addressCleansingNoZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickWooCommerceSetting();
        homePage.enterCountry(WooCommerceData.US_PA_COUNTRY.data);
        homePage.enterAddressLineOne(WooCommerceData.PHILADELPHIA_ADDRESS_LINE.data);
        homePage.enterAddressCity(WooCommerceData.US_CITIES.data);
        homePage.enterAddressZip(WooCommerceData.INCORRECT_ZIP.data);
        assertEquals(homePage.getAlertMessage(), WooCommerceData.NO_ZIP_MESSAGE.data);
    }

    /**
     * CWOO-468 WOO - Address Cleansing Continue to Calc OFF with Invalid Zip - Physical Origin
     * this method is to check address cleansing features with Invalid zip and Calc Off.
     */
    @Test(groups = {"woo_regression", "woo_addressCleansing"})
    public void addressCleansingCalcOffInvalidZipTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        try {
            homePage.clickWooCommerceSetting();
            homePage.enableDisableTaxCalc(false);
            homePage.clickSaveChangesButton();
            homePage.enterCountry(WooCommerceData.US_PA_COUNTRY.data);
            homePage.enterAddressLineOne(WooCommerceData.US_PA_ADDRESS_LINE_ONE.data);
            homePage.enterAddressCity(WooCommerceData.US_PA_CITY.data);
            homePage.enterAddressZip(WooCommerceData.INCORRECT_ZIP.data);
            assertEquals(homePage.getAlertMessage(), WooCommerceData.ADDRESS_CLEANSING_SHIP_TO_INVALID_ZIP_MESSAGE.data);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            homePage.clickWooCommerceSetting();
            homePage.enableDisableTaxCalc(true);
            homePage.clickSaveChangesButton();
        }
    }
}
