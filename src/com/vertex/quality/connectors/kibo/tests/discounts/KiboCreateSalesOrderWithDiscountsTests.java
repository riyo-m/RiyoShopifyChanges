package com.vertex.quality.connectors.kibo.tests.discounts;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboOrdersPage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * CKIBO-596
 * This test creates a basic sales order with an existing customer info,
 * same shipping and billing Addresses for this customer.
 * applying discount on the item selected .
 * then verifying the order total and calculated tax amount.
 *
 * @author vivek.kumar
 */
public class KiboCreateSalesOrderWithDiscountsTests extends KiboTaxCalculationBaseTest {
    KiboBackOfficeStorePage maxinePage;
    KiboWarehouseCaPage warehousePage;
    KiboOrdersPage orderPage;

    protected SoftAssert assertTax;
    protected String discountValue;
    protected String actualTax;
    protected SoftAssert assertDiscount;

    /**
     * CKIBO-597
     * runs a basic sales order and selects a discount
     * Change Discount from 10% to 5% off order.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboChangeDiscountTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Colorado.addressLine1, Address.Colorado.city,
                Address.Colorado.state.fullName, Address.Colorado.zip5, Address.Colorado.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.Louisiana.addressLine1, Address.Louisiana.city,
                Address.Louisiana.state.fullName, Address.Louisiana.zip5, Address.Louisiana.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);

        // Apply discount coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_5PERCENT_ITEM);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyLineitemDiscount(true, KiboProductNames.PRODUCT_TEST_SHOES.value, 5));
        assertTrue(maxinePage.verifyLineitemDiscount(true, KiboProductNames.PRODUCT_T_SHIRT.value, 5));

        // Remove coupons & apply new coupon
        maxinePage.clickEditDetails();
        maxinePage.removeAllLineItemsCoupons();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10PERCENT_ORDER);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupon(s)
        assertEquals(maxinePage.calculatePercentBaseDiscount(10), maxinePage.getDiscountFromUI());

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.45), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-602
     * runs a basic sales order and selects multiple discounts
     * Discount Shipping Amount, Discount Order Percent, Discount Line Amount.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboMultiDiscountsTest() {
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

        // Add coupon
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10PERCENT_ORDER);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupon(s)
        assertEquals(maxinePage.calculatePercentBaseDiscount(10), maxinePage.getDiscountFromUI());

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10DOLLAR_ITEM);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_TEST_SHOES.value, 10));
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_T_SHIRT.value, 10));

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_1OFF_SHIP);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyShippingDiscount(1));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-599
     * runs a basic sales order and selects multiple discounts
     * Multi line order with Discount line Amount.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboMultiDiscountLineAmountTest() {
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

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10DOLLAR_ITEM);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_TEST_SHOES.value, 10));
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_T_SHIRT.value, 10));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-598
     * runs a basic sales order and selects multiple discounts
     * Multi line order with Discount Line Percentage.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboMultiLineDiscountLinePercentageTest() {
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

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_5PERCENT_ITEM);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyLineitemDiscount(true, KiboProductNames.PRODUCT_TEST_SHOES.value, 5));
        assertTrue(maxinePage.verifyLineitemDiscount(true, KiboProductNames.PRODUCT_T_SHIRT.value, 5));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-600
     * runs a basic sales order and selects multiple discounts
     * Multi Line Order with Discount Order Amount.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboMultiLineDiscountAmountTest() {
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

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.removeAllOrderAdjustments();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_5DOLLAR_ORDER);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertEquals(KiboTaxRates.DISCOUNT_FLAT_5DOLLAR.value, maxinePage.getDiscountFromUI());

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-604
     * runs a basic sales order Discounts and Invoice
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboDiscountAndInvoiceTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrderDetails(KiboCustomers.Customer1, Address.Washington.addressLine1, Address.Washington.city,
                Address.Washington.state.fullName, Address.Washington.zip5, Address.Washington.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);

        // Add coupon
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10PERCENT_ORDER);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupon(s)
        assertEquals(maxinePage.calculatePercentBaseDiscount(10), maxinePage.getDiscountFromUI());

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_1OFF_SHIP);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyShippingDiscount(1));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(10.1), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-601
     * runs a basic sales order with discounts.
     * Multi Line Order with Discount Shipping Amount
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboDiscountShippingAmountTest() {
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

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_1OFF_SHIP);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyShippingDiscount(1));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());
    }

    /**
     * CKIBO-603
     * runs a basic sales order with discounts.
     * Discount Shipping for VAT (Intra EU DE-DE) and Invoice.
     * verified discount is applied and verified tax calculations
     */
    @Test(groups = {"kibo_regression"})
    public void kiboDiscountShippingForVATTest() {
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city,
                Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.fullName);

        // Process Order
        maxinePage = navigateToBackOfficeStores();
        selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.Berlin.addressLine1, Address.Berlin.city,
                Address.Berlin.country.iso2code, Address.Berlin.zip5, Address.Berlin.country.fullName);
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY5.value);
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_T_SHIRT, KiboTaxRates.KIBO_QUANTITY1.value);

        // Add coupon
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10PERCENT_ORDER);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupon(s)
        assertEquals(maxinePage.calculatePercentBaseDiscount(10), maxinePage.getDiscountFromUI());

        // Add additional coupon(s)
        maxinePage.clickEditDetails();
        maxinePage.orderDetailsDialog.applyCouponCode(KiboDiscounts.DISCOUNT_10DOLLAR_ITEM);
        maxinePage.orderDetailsDialog.clickOrderSaveButton();

        // Assertion based on discount coupons
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_TEST_SHOES.value, 10));
        assertTrue(maxinePage.verifyLineitemDiscount(false, KiboProductNames.PRODUCT_T_SHIRT.value, 10));

        // Submit order
        maxinePage.clickSaveAndSubmitOrderButton();

        // Assertion of Tax on order's amount.
        assertEquals(maxinePage.calculatePercentageBasedTax(19), maxinePage.getTaxAmount());
    }
}
