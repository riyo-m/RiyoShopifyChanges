package com.vertex.quality.connectors.kibo.tests.basicSalesOrder;

import com.vertex.quality.connectors.kibo.enums.KiboAddresses;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboTaxRates;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Case CKIBO-605
 * Create Sales Order basic (required fields only) - Invoice Request.
 *
 * @author vivek kumar
 */
public class KiboSalesOrderModifiedOriginStateTests extends KiboTaxCalculationBaseTest {
    protected String actualTax;

    /**
     * Create Sales Order with Modified Origin State
     * CKIBO-608
     */
    @Test(groups = {"kibo_regression"})
    public void kiboSalesOrderModifiedOriginTest() {

        KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStores();

        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, KiboAddresses.CALIFORNIA_ADDRESS1.value, KiboAddresses.CALIFORNIA_CITY.value,
                KiboAddresses.CALIFORNIA_STATE.value, KiboAddresses.CALIFORNIA_ZIP.value, KiboAddresses.CALIFORNIA_COUNTRY.value);

        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);

        maxinePage.clickSaveAndSubmitOrderButton();

        actualTax = maxinePage.getTaxAmount();
        Assert.assertEquals(KiboTaxRates.CALIFORNIA_TAX.value, actualTax);
    }
}