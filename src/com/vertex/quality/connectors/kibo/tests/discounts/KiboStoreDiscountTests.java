package com.vertex.quality.connectors.kibo.tests.discounts;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.*;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class KiboStoreDiscountTests extends KiboTaxCalculationBaseTest {

    KiboWarehouseCaPage warehousePage;
    KiboStoreLoginPage storeLoginPage;
    KiboStoreFrontPage storeFrontPage;
    KiboStoreFrontCartPage cartPage;
    KiboStoreFrontCheckoutPage checkoutPage;

    /**
     * CKIBO-754 KIBO Store - Test Case -Create Sales Order with multiple Discounts - Discount Shipping Amount, Discount Order Percent, Discount Line Amount
     */
    @Test(groups = {"kibo_smoke", "kibo_regression"})
    public void kiboMultiDiscountsTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

        // Login to Kibo Store
        storeLoginPage = new KiboStoreLoginPage(driver);
        storeLoginPage.loadKiboStoreLoginPage();
        storeLoginPage.loginToKiboStore(KiboCredentials.KIBO_STORE_USER1.value, KiboCredentials.CONFIG_PASSWORD.value);

        // Clear the cart
        storeFrontPage = new KiboStoreFrontPage(driver);
        storeFrontPage.goToCartFromHeader();
        cartPage = new KiboStoreFrontCartPage(driver);
        cartPage.clearCart();
        cartPage.clickMaxineHeading();

        // Select product & add to cart
        storeFrontPage.selectProductCategory(KiboProductCategory.CAMPING.value);
        storeFrontPage.loadMaximumRecords();
        storeFrontPage.selectProduct(KiboProductNames.MEN_OUTWEST_TRAIL_PRO_40_PACK.value);
        storeFrontPage.clickAddToCart();
        cartPage.applyDiscountCoupon(KiboDiscounts.DISCOUNT_10DOLLAR_ITEM.value);
        checkoutPage = cartPage.goToCheckoutPage();
        checkoutPage.shipping.clickChangeAddressAndAddNewAddress();
        checkoutPage.shipping.enterShippingAddress(Address.LosAngeles.addressLine1, Address.LosAngeles.country.fullName, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        checkoutPage.shipping.goNextFromShipping();
        checkoutPage.shippingMethod.selectFlatRateForShipping();
        checkoutPage.payment.payWithCheckByMail();
        checkoutPage.payment.clickBillingSameAsShippingAddress();
        checkoutPage.payment.goNextFromPayment();
        assertEquals(checkoutPage.orderSummary.calculatePercentBasedTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }
}
