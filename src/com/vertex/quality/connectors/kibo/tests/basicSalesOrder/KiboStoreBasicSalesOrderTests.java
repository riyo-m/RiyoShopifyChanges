package com.vertex.quality.connectors.kibo.tests.basicSalesOrder;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import com.vertex.quality.connectors.kibo.enums.KiboProductCategory;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.enums.KiboWarehouses;
import com.vertex.quality.connectors.kibo.pages.*;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test class that contains all basic sales order for Kibo's Storefront.
 *
 * @author Shivam.Soni
 */
public class KiboStoreBasicSalesOrderTests extends KiboTaxCalculationBaseTest {

    KiboWarehouseCaPage warehousePage;
    KiboStoreLoginPage storeLoginPage;
    KiboStoreFrontPage storeFrontPage;
    KiboStoreFrontCartPage cartPage;
    KiboStoreFrontCheckoutPage checkoutPage;

    /**
     * CKIBO-751 KIBO Store - Test Case - Create Sales Order with Modified Origin State
     */
    @Test(groups = {"kibo_smoke", "kibo_regression"})
    public void salesOrderWithModifiedStateTest() {
        // Set Ship From Address
        warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);

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
        checkoutPage = cartPage.goToCheckoutPage();
        checkoutPage.shipping.clickChangeAddressAndAddNewAddress();
        checkoutPage.shipping.enterShippingAddress(Address.UniversalCity.addressLine1, Address.UniversalCity.country.fullName, Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.zip5);
        checkoutPage.shipping.goNextFromShipping();
        checkoutPage.shippingMethod.selectFlatRateForShipping();
        checkoutPage.payment.payWithCheckByMail();
        checkoutPage.payment.clickBillingSameAsShippingAddress();
        checkoutPage.payment.goNextFromPayment();
        assertEquals(checkoutPage.orderSummary.calculatePercentBasedTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }
}
