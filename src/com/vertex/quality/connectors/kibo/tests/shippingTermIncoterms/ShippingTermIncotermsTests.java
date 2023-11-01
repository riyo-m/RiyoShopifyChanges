package com.vertex.quality.connectors.kibo.tests.shippingTermIncoterms;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.KiboAdminHomePage;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.pages.oseries.OSeriesLoginPage;
import com.vertex.quality.connectors.kibo.pages.oseries.OSeriesTaxpayers;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * This class tests contains all test-cases related to shipping term & incoterms for Kibo.
 *
 * @author Shivam.Soni
 */
public class ShippingTermIncotermsTests extends KiboTaxCalculationBaseTest {

    KiboAdminHomePage adminHomePage;
    KiboBackOfficeStorePage maxinePage;
    KiboWarehouseCaPage warehousePage;
    OSeriesLoginPage oSeriesLogin;
    OSeriesTaxpayers oSeriesTax;

    /**
     * CKIBO-694 Test Case - Create Sales Invoice with Shipping - change shipping address
     */
    @Test(groups = "kibo_regression")
    public void salesOrderChangeShippingAddressTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berwyn.addressLine1, Address.Berwyn.city,
                Address.Berwyn.state.fullName, Address.Berwyn.zip5, Address.Berwyn.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickChangeAddress();
        maxinePage.clickBillToAddress();
        maxinePage.clickShipToAddress();
        maxinePage.clickSaveDetails();
        maxinePage.clickEditDetails();
        maxinePage.clickSaveDetails();

        // Initial Assertion with first address
        assertEquals(maxinePage.calculatePercentageBasedTax(6), maxinePage.getTaxAmount());

        // Changing shipping address to re-calculate tax
        maxinePage.clickChangeAddress();
        maxinePage.clickEditButton();
        maxinePage.enterAddressLine(Address.Washington.addressLine1);
        maxinePage.enterAddressCity(Address.Washington.city);
        maxinePage.enterAddressState(Address.Washington.state.fullName);
        maxinePage.enterAddressZip(Address.Washington.zip5);
        maxinePage.enterCountryCode(Address.Washington.country.fullName);
        maxinePage.clickSaveAddressDetail();
        maxinePage.clickSavesDetails();
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with re-calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(10.101), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-695 Test Case - Create Sales Order with shipping
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithShippingAddressTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berwyn.addressLine1, Address.Berwyn.city,
                Address.Berwyn.state.fullName, Address.Berwyn.zip5, Address.Berwyn.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(6), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-696 Test Case - Create Sales Order with shipping on line item only
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithShippingLineItemTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY3.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-697 Test Case - Create Sale Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithCUSShippingTermTest() {
        try {
            // Select CUS shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_CUS.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Paris.addressLine1, Address.Paris.city,
                    Address.Paris.city, Address.Paris.zip5, Address.Paris.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                    Address.Berlin.country.iso2code, Address.Berlin.zip5, Address.Berlin.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(20), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * CKIBO-698 Test Case - Create Sale Order for VAT with Shipping Terms SUP and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithSUPShippingTermTest() {
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Paris.addressLine1, Address.Paris.city,
                    Address.Paris.city, Address.Paris.zip5, Address.Paris.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                    Address.Berlin.country.iso2code, Address.Berlin.zip5, Address.Berlin.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(20), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * CKIBO-699 Test Case - Create Sale Order for VAT with Shipping Terms EXW and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithEXWShippingTermTest() {
        try {
            // Select EXW shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_EXW.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Paris.addressLine1, Address.Paris.city,
                    Address.Paris.city, Address.Paris.zip5, Address.Paris.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                    Address.Berlin.country.iso2code, Address.Berlin.zip5, Address.Berlin.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(20), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * CKIBO-700 Test Case - Create Sale Order for VAT with Shipping Terms DDP and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderWithDDPShippingTermTest() {
        try {
            // Select DDP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_DDP.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Paris.addressLine1, Address.Paris.city,
                    Address.Paris.city, Address.Paris.zip5, Address.Paris.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                    Address.Berlin.country.iso2code, Address.Berlin.zip5, Address.Berlin.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(20), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * CKIBO-701 Test Case - Create Sale Order with Shipping DDP for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderEUDEDEWithDDPShippingTermTest() {
        try {
            // Select DDP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_DDP.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Berlin.addressLine1, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city,
                    Address.BerlinAlternate.country.iso2code, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(19), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * CKIBO-702 Test Case - Create Sale Order with shipping terms SUP for APAC (SG-JP) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderSGJPWithSUPShippingTermTest() {
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            // Open Kibo Admin & Set Ship From Address
            driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);
            adminHomePage = new KiboAdminHomePage(driver);
            adminHomePage.clickMainMenu();
            adminHomePage.navPanel.clickMainTab();
            adminHomePage.navPanel.goToLocationsPage();
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Singapore.addressLine1, Address.Singapore.city,
                    Address.Singapore.city, Address.Singapore.zip5, Address.Singapore.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Japan.addressLine1, Address.Japan.city,
                    Address.Japan.country.iso2code, Address.Japan.zip5, Address.Japan.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion with calculated tax by sub-total & tax appeared on UI.
            assertEquals(maxinePage.calculatePercentageBasedTax(0), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");
        }
    }
}
