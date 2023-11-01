package com.vertex.quality.connectors.kibo.tests.configSetting;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboTaxRates;
import com.vertex.quality.connectors.kibo.enums.KiboWarehouses;
import com.vertex.quality.connectors.kibo.pages.KiboApplicationsPage;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * All tests related to config setting on/off & validate sales order
 *
 * @author Shivam.Soni
 */
public class KiboConfigSettingTests extends KiboTaxCalculationBaseTest {

    KiboVertexConnectorPage connectorPage;
    KiboMainMenuNavPanel navPanel;
    KiboApplicationsPage applicationsPage;
    KiboWarehouseCaPage warehousePage;
    KiboBackOfficeStorePage maxinePage;

    /**
     * CKIBO-742 Test Case - Basic Config -settings on /off
     */
    @Test(groups = {"kibo_smoke"})
    public void configSettingOffTest() {
        try {
            // Go to application page from Kibo.
            connectorPage = new KiboVertexConnectorPage(driver);
            navPanel = connectorPage.clickMainMenu();
            navPanel.gotoSystemTab();
            applicationsPage = navPanel.goToApplicationsPage();
            applicationsPage.selectVertexConnector();

            // Disable vertex connector
            connectorPage.disableApplication();

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
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Enable vertex connector
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();
            navigateToVertexConfiguration();
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-743 Test Case -Create Sales Order with Invoice OFF
     */
    @Test(groups = {"kibo_smoke"})
    public void salesOrderInvoiceOffTest() {
        try {
            // Go to vertex connector config page
            gotoVertexConfiguration();

            // Disable invoice from vertex connector
            enableOrDisableInvoiceFromKibo(false);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();

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
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Go to vertex connector config page
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();
            navigateToVertexConfiguration();

            // Enable invoice from vertex connector
            enableOrDisableInvoiceFromKibo(true);
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }
}
