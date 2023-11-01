package com.vertex.quality.connectors.kibo.tests.basicSalesOrder;

import com.vertex.quality.common.enums.Address;
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
public class KiboOrderNoStateTaxLocallyAdministeredTests extends KiboTaxCalculationBaseTest {
    protected String actualTax;

    /**
     * Create Sales Order with no State Tax, locally administered
     * CKIBO-607
     */
    @Test(groups = {"kibo_regression"})
    public void kiboNoStateTaxLocallyAdministeredTest() {

        KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStores();

        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Alaska.addressLine1, Address.Alaska.city,
                Address.Alaska.state.fullName, Address.Alaska.zip5, Address.Alaska.country.fullName);

        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);

        maxinePage.clickSaveAndSubmitOrderButton();

        actualTax = maxinePage.getTaxAmount();
        Assert.assertEquals(KiboTaxRates.ALASKA_TAX_RATE.value, actualTax);
    }
}