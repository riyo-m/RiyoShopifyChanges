package com.vertex.quality.connectors.kibo.tests.productCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test Case CKIBO-615
 * Create Sales order with Product Code and Product Class Exemption.
 *
 * @author vivek kumar
 */
public class KiboProductCodeExemptionTests extends KiboTaxCalculationBaseTest {
    KiboWarehouseCaPage warehousePage;
    KiboBackOfficeStorePage maxinePage;

    protected String actualTax;

    /**
     * tests tax on order with product code exemption
     * CKIBO-617
     */
    @Test(groups = {"kibo_regression"})
    public void kiboProductCodeExemptionTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Set product for product class
        maxinePage = navigateToProductCode(KiboData.PRODUCT_CODE_VTXP.value);

        // Process & Submit Order
        navigateToBackOfficeMysticStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Louisiana.addressLine1, Address.Louisiana.city,
                Address.Louisiana.state.fullName, Address.Louisiana.zip5, Address.Louisiana.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_VTXP_CODE, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's shipping amount only as product is 100% Tax Exempt.
        assertEquals(maxinePage.calculatePercentBaseTaxOnShippingOnly(9.45), maxinePage.getTaxAmount());
    }
}