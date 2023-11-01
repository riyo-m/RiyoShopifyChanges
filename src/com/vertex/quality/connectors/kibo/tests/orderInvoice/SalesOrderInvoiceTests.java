package com.vertex.quality.connectors.kibo.tests.orderInvoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboTaxRates;
import com.vertex.quality.connectors.kibo.enums.KiboWarehouses;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This class contains test-cases related to Sales order invoice
 *
 * @author Shivam.Soni
 */
public class SalesOrderInvoiceTests extends KiboTaxCalculationBaseTest {

    KiboBackOfficeStorePage maxinePage;
    KiboWarehouseCaPage warehousePage;

    /**
     * CKIBO-689 Test Case -Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = "kibo_regression")
    public void consignmentOrderDEFRTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city,
                Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Marseille.addressLine1, Address.Marseille.city,
                Address.Marseille.country.iso2code, Address.Marseille.zip5, Address.Marseille.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(20), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-690 KIBO - Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderFRDETest() {
        // Set Ship From Address
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
        assertEquals(maxinePage.calculatePercentageBasedTax(19), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-691 Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderAustriaSubDivisionTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Berlin.addressLine1, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Mittelberg.addressLine1, Address.Mittelberg.city,
                Address.Mittelberg.country.iso2code, Address.Mittelberg.zip5, Address.Mittelberg.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(0), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-692 Test Case - Create Sale Order with currency conversion for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderEUFRDETest() {
        // Set Ship From Address
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
        assertEquals(maxinePage.calculatePercentageBasedTax(19), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-693 Test Case - Create Sale Order for APAC (Hong Kong no tax) and Invoice
     */
    @Test(groups = "kibo_regression")
    public void salesOrderAPACHongKongTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.MongKok.addressLine1, Address.MongKok.city,
                Address.MongKok.city, Address.MongKok.zip5, Address.MongKok.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.ShuenWan.addressLine1, Address.ShuenWan.city,
                Address.ShuenWan.country.iso2code, Address.ShuenWan.zip5, Address.ShuenWan.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion with calculated tax by sub-total & tax appeared on UI.
        assertEquals(maxinePage.calculatePercentageBasedTax(0), maxinePage.getTaxAmount());
    }
}
