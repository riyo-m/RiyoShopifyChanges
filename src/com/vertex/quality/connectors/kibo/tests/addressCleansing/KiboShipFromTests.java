package com.vertex.quality.connectors.kibo.tests.addressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboAddresses;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import com.vertex.quality.connectors.kibo.enums.KiboWarehouses;
import com.vertex.quality.connectors.kibo.pages.KiboLocationsPage;
import com.vertex.quality.connectors.kibo.pages.KiboReCleanseWarehouse;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * All test methods address cleansing for ship from.
 *
 * @author Shivam.Soni
 */
public class KiboShipFromTests extends KiboTaxCalculationBaseTest {
    KiboMainMenuNavPanel navPanel;
    KiboVertexConnectorPage connectorPage;
    KiboLocationsPage locationsPage;
    KiboWarehouseCaPage warehousePage;
    KiboReCleanseWarehouse reCleanseWarehouse;

    /**
     * CKIBO-721 Test Case - Address Cleansing OFF with Invalid Zip
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

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.Philadelphia.addressLine1, Address.Philadelphia.city,
                    Address.Philadelphia.state.fullName, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getErrorMessage(), KiboData.SHIP_FROM_INVALID_ZIP_ADDRESS_CLEANSING_OFF_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelAddressCleansingError();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-718 Test Case - Address Cleansing OFF with No Zip
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

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.Philadelphia.addressLine1, Address.Philadelphia.city,
                    Address.Philadelphia.state.fullName, KiboAddresses.EMPTY_ZIP.value, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getErrorMessage(), KiboData.SHIP_FROM_EMPTY_ZIP_ADDRESS_CLEANSING_OFF_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelAddressCleansingError();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-722 Test Case - Address Cleansing Continue to Calc OFF with Invalid Zip - Physical Origin
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

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.ChesterInvalidAddress.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.ChesterInvalidAddress.state.fullName, Address.ChesterInvalidAddress.zip5, Address.ChesterInvalidAddress.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getErrorMessage(), KiboData.SHIP_FROM_ADDRESS_CLEANSING_ON_INVALID_ADDRESS_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelAddressCleansingError();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Tax calc-on
            enterOrRemoveBadUrlsForTaxAndCleansing(false, false);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-720 Test Case - Address Cleansing OFF with Invalid City and Zip
     */
    @Test(groups = {"kibo_regression"})
    public void addressCleansingOffInvalidCityZipTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Disable address-cleansing
            enableOrDisableAddressCleansingFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.Philadelphia.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.Philadelphia.state.fullName, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getErrorMessage(), KiboData.SHIP_FROM_INVALID_ZIP_ADDRESS_CLEANSING_OFF_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelAddressCleansingError();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-717 Test Case - Address Cleansing OFF with Invalid State and Zip
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

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.Philadelphia.addressLine1, Address.ChesterInvalidAddress.city,
                    Address.Delaware.state.abbreviation, Address.ChesterInvalidAddress.zip5, Address.Philadelphia.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getErrorMessage(), KiboData.SHIP_FROM_INVALID_ZIP_ADDRESS_CLEANSING_OFF_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelAddressCleansingError();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-719 Test Case - Validate addresses with address cleansing OFF - Physical Origin
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

            // Go to set Ship From address
            navPanel = connectorPage.clickMainMenu();
            navPanel.clickMainTab();
            locationsPage = navPanel.goToLocationsPage();
            locationsPage.selectWareHouse(KiboWarehouses.WH_WRH_OO1.value);

            // Change & Validate ship from address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.changeAndValidateWarehouseAddress(Address.Birmingham.addressLine1, Address.Birmingham.city,
                    Address.Birmingham.state.fullName, KiboAddresses.BLANK_ZIP.value, Address.Birmingham.country.fullName);

            // Assertion on Address-Cleansing message
            reCleanseWarehouse = new KiboReCleanseWarehouse(driver);
            assertEquals(reCleanseWarehouse.getCleansedAddress(), KiboData.SHIP_FROM_BLANK_ZIP_CLEANSED_MSG.value);

            // Close address-cleansing pop-up.
            reCleanseWarehouse.cancelValidatedAddress();
            warehousePage.clickOnCancelButton();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Enable address-cleansing
            enableOrDisableAddressCleansingFromKibo(true);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }
}
