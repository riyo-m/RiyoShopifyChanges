package com.vertex.quality.connectors.kibo.tests.creditAdjustment;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboTaxRates;
import com.vertex.quality.connectors.kibo.enums.KiboWarehouses;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * CKIBO-609
 * KIBO - Sales - Credit/Adjustment.
 *
 * @author vivek kumar
 */
public class KiboSalesCreditAdjustmentTests extends KiboTaxCalculationBaseTest {
    protected String actualTax;
    protected String totalString;
    protected double applicableTax;
    protected double amountProduct1;
    protected double amountProduct2;
    protected double[] expectedRefundOfIndividualItem;
    protected String[] expectedRefundProductQty;

    KiboBackOfficeStorePage maxinePage;
    KiboWarehouseCaPage warehousePage;

    /**
     * KIBO - Test Case - Return Sales order - partial quantity returned
     * CKIBO-611
     */
    @Test(groups = {"kibo_regression"})
    public void kiboPartialQuantityRefundTest() {
        applicableTax = 8.25;

        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Edison.addressLine1, Address.Edison.city,
                Address.Edison.state.fullName, Address.Edison.zip5, Address.Edison.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.Dallas.addressLine1, Address.Dallas.city,
                Address.Dallas.state.fullName, Address.Dallas.zip5, Address.Dallas.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.removeAllOrderAdjustments();
        maxinePage.orderDetailsDialog.clickOrderSaveButton();
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(applicableTax), maxinePage.getTaxAmount());

        // Calculate individual item's total amount
        amountProduct1 = maxinePage.calculateIndividualProductsTotalAmount(KiboProductNames.PRODUCT_TEST_SHOES.value, applicableTax, false);

        // Do check payment for the order
        payForTheOrder(String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
        assertTrue(maxinePage.payment.checkIfPaid());

        // Process return & refund.
        maxinePage.fulfillOrderAndGotoReturn();
        maxinePage.enterQuantityToBeReturned(KiboProductNames.PRODUCT_TEST_SHOES.value, KiboTaxRates.KIBO_QUANTITY3.value);
        maxinePage.createAndssueRefund();
        maxinePage.processRefundForReturn(KiboProductNames.PRODUCT_TEST_SHOES.value, false);

        // Preparing data to calculate expected refund amount
        expectedRefundOfIndividualItem = new double[]{amountProduct1};
        expectedRefundProductQty = new String[]{KiboTaxRates.KIBO_QUANTITY3.value};

        // Assertion on calculated expected refund amount with actual refunded amount
        assertTrue(maxinePage.verifyRefundedAmount(expectedRefundOfIndividualItem, expectedRefundProductQty));
    }

    /**
     * KIBO - Test Case - Return Sale Order with Shipping for VAT (Intra EU DE-DE) and Invoice
     * KIBO-614
     */
    @Test(groups = {"kibo_regression"})
    public void kiboShippingForVATRefundTest() {
        applicableTax = 19;

        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city,
                Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.removeAllOrderAdjustments();
        maxinePage.orderDetailsDialog.clickOrderSaveButton();
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(applicableTax), maxinePage.getTaxAmount());

        // Calculate individual item's total amount
        amountProduct1 = maxinePage.calculateIndividualProductsTotalAmount(KiboProductNames.PRODUCT_TEST_SHOES.value, applicableTax, true);

        // Do check payment for the order
        payForTheOrder(String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
        assertTrue(maxinePage.payment.checkIfPaid());

        // Process return & refund.
        maxinePage.fulfillOrderAndGotoReturn();
        maxinePage.enterQuantityToBeReturned(KiboProductNames.PRODUCT_TEST_SHOES.value, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.createAndssueRefund();
        maxinePage.processRefundForReturn(KiboProductNames.PRODUCT_TEST_SHOES.value, true);

        // Preparing data to calculate expected refund amount
        expectedRefundOfIndividualItem = new double[]{amountProduct1};
        expectedRefundProductQty = new String[]{KiboTaxRates.KIBO_QUANTITY5.value};

        // Assertion on calculated expected refund amount with actual refunded amount
        assertTrue(maxinePage.verifyRefundedAmount(expectedRefundOfIndividualItem, expectedRefundProductQty));
    }

    /**
     * KIBO - Test Case - Return Sales order - partial order with shipping
     * CKIBO-612
     */
    @Test(groups = {"kibo_regression"})
    public void kiboPartialOrderWithShippingRefundTest() {
        applicableTax = 9.5;

        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.removeAllOrderAdjustments();
        maxinePage.orderDetailsDialog.clickOrderSaveButton();
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(applicableTax), maxinePage.getTaxAmount());

        // Calculate individual item's total amount
        amountProduct1 = maxinePage.calculateIndividualProductsTotalAmount(KiboProductNames.PRODUCT_TEST_SHOES.value, applicableTax, true);

        // Do check payment for the order
        payForTheOrder(String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
        assertTrue(maxinePage.payment.checkIfPaid());

        // Process return & refund.
        maxinePage.fulfillOrderAndGotoReturn();
        maxinePage.enterQuantityToBeReturned(KiboProductNames.PRODUCT_TEST_SHOES.value, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.createAndssueRefund();
        maxinePage.processRefundForReturn(KiboProductNames.PRODUCT_TEST_SHOES.value, true);

        // Preparing data to calculate expected refund amount
        expectedRefundOfIndividualItem = new double[]{amountProduct1};
        expectedRefundProductQty = new String[]{KiboTaxRates.KIBO_QUANTITY5.value};

        // Assertion on calculated expected refund amount with actual refunded amount
        assertTrue(maxinePage.verifyRefundedAmount(expectedRefundOfIndividualItem, expectedRefundProductQty));
    }

    /**
     * KIBO - Test Case - Return Sales order - full order with shipping
     * CKIBO-613
     */
    @Test(groups = {"kibo_regression"})
    public void kiboFullOrderWithShippingRefundTest() {
        applicableTax = 9.50;

        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.removeAllOrderAdjustments();
        maxinePage.orderDetailsDialog.clickOrderSaveButton();
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(applicableTax), maxinePage.getTaxAmount());

        // Calculate individual item's total amount
        amountProduct1 = maxinePage.calculateIndividualProductsTotalAmount(KiboProductNames.PRODUCT_TEST_SHOES.value, applicableTax, true);

        // Do check payment for the order
        payForTheOrder(String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
        assertTrue(maxinePage.payment.checkIfPaid());

        // Process return & refund.
        maxinePage.fulfillOrderAndGotoReturn();
        maxinePage.enterQuantityToBeReturned(KiboProductNames.PRODUCT_TEST_SHOES.value, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.createAndssueRefund();
        maxinePage.enterRefundAmountForStoreCredit(KiboProductNames.PRODUCT_TEST_SHOES.value, true);
        maxinePage.clickIssueRefunds();
        maxinePage.markCloseRefund();

        // Preparing data to calculate expected refund amount
        expectedRefundOfIndividualItem = new double[]{amountProduct1};
        expectedRefundProductQty = new String[]{KiboTaxRates.KIBO_QUANTITY5.value};

        // Assertion on calculated expected refund amount with actual refunded amount
        assertTrue(maxinePage.verifyRefundedAmount(expectedRefundOfIndividualItem, expectedRefundProductQty));
    }
}