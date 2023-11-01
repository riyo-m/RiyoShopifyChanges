package com.vertex.quality.connectors.kibo.tests.customerCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test Case CKIBO-618
 * Create Sales order with Customer Code and Customer Class Exemption.
 *
 * @author vivek kumar
 */
public class KiboCreateSalesOrderCustomerCodeExemptionTests extends KiboTaxCalculationBaseTest {
    KiboBackOfficeStorePage maxinePage;
    KiboWarehouseCaPage warehousePage;

    /**
     * tests tax on order with customer code exemption
     * CKIBO-620
     */
    @Test(groups = {"kibo_regression"})
    public void kiboCreateCustomerCodeExemptionTest() {
        try {
            // Set Ship From Address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                    Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

            // Select customer code
            maxinePage = navigateToCustomerCode(KiboData.CUSTOMER_CODE_VTXC.value);

            // Process & Submit Order
            navigateToBackOfficeStores();
            selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                    Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion of Tax on order's amount.
            assertEquals(maxinePage.calculatePercentageBasedTax(0), maxinePage.getTaxAmount());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            // Post Condition - Removing all selected customer code & class.
            removeCustomerCodeClass(KiboCustomers.Customer5.value);
        }
    }
}