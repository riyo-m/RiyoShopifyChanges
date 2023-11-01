package com.vertex.quality.connectors.kibo.tests.addressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.KiboAddresses;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class KiboShipToTests extends KiboTaxCalculationBaseTest {

    KiboVertexConnectorPage connectorPage;
    KiboBackOfficeStorePage maxinePage;

    /**
     * CKIBO-723 Test Case - Address Cleansing OFF with Invalid Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffInvalidZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Disable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Philadelphia.addressLine1, Address.Philadelphia.city,
                    Address.Philadelphia.state.fullName, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-730 Test Case - Address Cleansing OFF with No Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffEmptyZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Disable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Philadelphia.addressLine1, Address.Philadelphia.city,
                    Address.Philadelphia.state.fullName, KiboAddresses.EMPTY_ZIP.value, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-724 Test Case - Address Cleansing Continue to Calc OFF with Invalid Zip - Shipping Address
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOnCalcOffInvalidZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Tax-Calc off
            enterOrRemoveBadUrlsForTaxAndCleansing(true, false);

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.ChesterInvalidAddress.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.ChesterInvalidAddress.state.fullName, Address.ChesterInvalidAddress.zip5, Address.ChesterInvalidAddress.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Tax calc-on
            enterOrRemoveBadUrlsForTaxAndCleansing(false, false);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-725 Test Case - Validate addresses with address cleansing OFF - Shipping Address
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffBlankZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Disable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Birmingham.addressLine1, Address.Birmingham.city,
                    Address.Birmingham.state.fullName, KiboAddresses.BLANK_ZIP.value, Address.Birmingham.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_BLANK_ZIP_CLEANSED_MSG.value);
            maxinePage.editAddressDialog.clickUseThis();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-726 Test Case - Validate Sales Order with Address Cleansing No Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOnNoZipTest() {
        // Go to configuration page.
        gotoVertexConfiguration();

        // Disable address-cleansing
        enableOrDisableAddressCleansingFromKibo(true);
        connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.configurationDialog.closeConfigAppPopup();

        // Go to set & validate Ship To address
        maxinePage = navigateToBackOfficeStores();
        selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Philadelphia.addressLine1, Address.Philadelphia.city,
                Address.Philadelphia.state.fullName, KiboAddresses.NULL_ZIP.value, Address.Philadelphia.country.fullName);

        // Assertion on Address-Cleansing message
        assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_NULL_ZIP_CLEANSED_MSG.value);
        maxinePage.editAddressDialog.clickUseThis();
    }

    /**
     * CKIBO-728 Test Case - Address Cleansing OFF with Invalid City and Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffInvalidCityZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Philadelphia.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.Philadelphia.state.fullName, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-727 Test Case - Address Cleansing OFF with Invalid State and Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffInvalidStateZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Disable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Philadelphia.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.Delaware.state.abbreviation, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-729 Test Case - Validate Sales Order with Address Cleansing Continue to Calc OFF with Invalid Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOnInvalidZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Tax-Calc off
            enterOrRemoveBadUrlsForTaxAndCleansing(true, false);

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set & validate Ship To address
            maxinePage = navigateToBackOfficeStores();
            selectCustomerEnterAndValidateAddress(KiboCustomers.Customer1, Address.Birmingham.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.Birmingham.state.fullName, Address.ChesterInvalidAddress.zip5, Address.Birmingham.country.fullName);

            // Assertion on Address-Cleansing message
            assertEquals(maxinePage.editAddressDialog.getErrorMessage(), KiboData.SHIP_TO_INVALID_ADDRESS_CLEANSING_MSG.value);
            maxinePage.editAddressDialog.clickOkToCloseErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            navigateToVertexConfiguration();

            // Tax calc-on
            enterOrRemoveBadUrlsForTaxAndCleansing(false, false);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }
}
