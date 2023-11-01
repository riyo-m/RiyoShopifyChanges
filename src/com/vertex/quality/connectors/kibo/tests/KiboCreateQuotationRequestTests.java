package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboAddresses;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboTaxRates;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test Case CKIBO-594
 * Create Quotation Request from the back office.
 * same billing and shipping Addresses
 *
 * @author vivek kumar
 */
public class KiboCreateQuotationRequestTests extends KiboTaxCalculationBaseTest {
    /**
     * CKIBO-595
     * Test case to -Create Sales Quote
     */
    @Test(groups = {"kibo_ui"})
    public void kiboCreateQuotationRequestTest() {


        KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStores();

        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, KiboAddresses.CALIFORNIA_ADDRESS1.value, KiboAddresses.CALIFORNIA_CITY.value,
                KiboAddresses.CALIFORNIA_STATE.value, KiboAddresses.CALIFORNIA_ZIP.value, KiboAddresses.CALIFORNIA_COUNTRY.value);

        maxinePage.orderDetailsDialog.clickItemListArrow();
        maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_TEST_SHOES);
        maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
        maxinePage.orderDetailsDialog.clickAddButton();
        maxinePage.orderDetailsDialog.clickShippingMethodButton();
        maxinePage.orderDetailsDialog.clickFlatRate();
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        String actualTax = maxinePage.getTaxAmount();
        boolean isTaxCorrect = actualTax.equals(KiboTaxRates.EXPECTED_TAX.value);
        assertTrue(isTaxCorrect);
    }
}