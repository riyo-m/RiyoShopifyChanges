package com.vertex.quality.connectors.woocommerceTap.storefront.pages;

import com.vertex.quality.connectors.woocommerceTap.storefront.pages.base.WooCommercePreCheckoutBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Locale;

/**
 * representation of the products page of WooCommerce store front site
 * which displays the details of few item in the store's catalog
 *
 * @author rohit.mogane
 */
public class WooCommerceProductsPage extends WooCommercePreCheckoutBasePage {
    public WooCommerceProductsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@data-product_sku='Woo-beanie-logo']")
    private WebElement AddCartBeanieWithLogo;

    @FindBy(xpath = "//*[@data-product_sku='Woo-beanie-logo']//following::a[1]")
    private WebElement viewCartBeanieWithLogo;

    protected final By addCart = By.xpath("//a[@aria-label='Add “Beanie with Logo” to your cart']");
    protected final By addAnotherProductCart = By.xpath("//a[@aria-label='Add “Belt” to your cart']");
    protected final By shop = By.xpath("//a[contains(text(),'Shop')]");
    protected final By couponCode = By.xpath("//input[@id='coupon_code']");
    protected final By validateDiscount = By.xpath("//tbody/tr[2]/td[1]/span[1]");
    protected final By applyCoupon = By.xpath("//button[contains(text(),'Apply coupon')]");
    protected final By updateCart = By.xpath(".//button[contains(text(),'Update cart')]");
    protected final By viewCart = By.xpath(".//a[@title='View cart']");
    protected final By cartFromHeader = By.xpath(".//*[@id='site-header']//a[normalize-space(.)='Cart']");
    protected final By lineQuantity = By.xpath("//label[contains(text(),'Beanie quantity')]//following::input");
    protected final By product = By.xpath("//input[@placeholder='Product']");
    protected final By save = By.xpath("//span[@name='Save']");
    protected final By code = By.xpath("//button[contains(text(),'code')]");
    protected final By subTotalAmount = By.xpath(".//th[text()='Subtotal']/following-sibling::td/span");
    protected String couponDiscount = ".//th[text()='Coupon: <<text_replace>>']/following-sibling::td/span";
    protected String discount;
    protected String productByName = "//a[@aria-label='Add “<<text_replace>>” to your cart']";


    /**
     * Adds particular item to cart and shows view cart link
     */
    public void addItemToCart(String[] productName) {
        for (String product : productName) {
            By locator = By.xpath("//a[@aria-label='Add “" + product + "” to your cart']");
            WebElement addToCart = wait.waitForElementEnabled(locator);
            click.javascriptClick(addToCart);
            waitForPageLoad();
        }
    }

    /**
     * Adds particular item to cart and shows view cart link
     */
    public void addItemToCart() {
        WebElement addToCart = wait.waitForElementDisplayed(addCart);
        click.javascriptClick(addToCart);
    }

    /**
     * Adds second item to cart and shows view cart link
     */
    public void addSecondItemToCart() {
        WebElement addToCart = wait.waitForElementDisplayed(addAnotherProductCart);
        click.javascriptClick(addToCart);
    }

    /**
     * Adds particular item to cart and go to cart page
     */
    public void addItemToCartViewCart() {
        addItemToCart();
        goToCart();
    }

    /**
     * Adds particular item to cart and go to cart
     *
     * @param product pass product name which should to be ordered
     */
    public void addItemToCartViewCart(String product) {
        WebElement addToCart = wait.waitForElementDisplayed(By.xpath(productByName.replace("<<text_replace>>", product)));
        click.javascriptClick(addToCart);
        goToCart();
    }

    /**
     * Adds particular item to cart and go to cart page
     */
    public void addItemToCartViewCarts(String[] productName) {
        addItemToCart(productName);
        goToCart();
    }

    /**
     * Go to shop to add another Product.
     */
    public void gotoShopCart() {
        WebElement addToCart = wait.waitForElementDisplayed(shop);
        click.javascriptClick(addToCart);
    }

    /**
     * Adds second item to cart and go to cart page
     */
    public void addSecondItemToCartViewCart() {
        addSecondItemToCart();
        goToCart();
    }

    /**
     * go to cart page
     */
    public void goToCart() {
        WebElement viewCartBeanieWithLogo = wait.waitForElementDisplayed(viewCart);
        click.javascriptClick(viewCartBeanieWithLogo);
    }

    /**
     * navigate to cart from global header
     * wooCommerceProductsPage.goToCartFromHeader();
     */
    public void goToCartFromHeader() {
        WebElement cart = wait.waitForElementPresent(cartFromHeader);
        click.javascriptClick(cart);
    }

    /**
     * update cart with given line item.
     *
     * @param Qty         line for customer.
     * @param productName array of product
     */
    public void updateLineItems(String[] Qty, String[] productName) {
        int i = 0;
        for (String product : productName) {
            By locator = By.xpath("//label[contains(text(),'" + product + "')]//following::input");
            WebElement lineQty = wait.waitForElementDisplayed(locator);
            text.enterText(lineQty, Qty[i]);
            i = i + 1;
        }
        clickUpdateCart();
        waitForPageLoad();
    }

    /**
     * Click on update cart button
     * example: wooCommerceProductsPage.clickUpdateCart();
     */
    public void clickUpdateCart() {
        wait.waitForElementEnabled(updateCart);
        click.javascriptClick(updateCart);
        waitForPageLoad();
    }

    /**
     * update cart with given line item.
     *
     * @param Qty line for customer.
     */
    public void updateLineItem(String Qty) {
        WebElement lineQty = wait.waitForElementDisplayed(lineQuantity);
        text.enterText(lineQty, Qty);
    }

    /**
     * enter coupon code for given item.
     *
     * @param couponCd code for customer.
     */
    public void enterCouponCode(String couponCd) {
        WebElement coupon = wait.waitForElementEnabled(couponCode);
        text.enterText(coupon, couponCd);
    }

    /**
     * enter Product Code.
     *
     * @param productCode for exempted customer
     */
    public void enterProductCode(String productCode) {
        WebElement productCd = wait.waitForElementDisplayed(product);
        text.enterText(productCd, productCode);
    }

    /**
     * click on save button.
     */
    public void clickSaveButton() {
        WebElement saveButton = wait.waitForElementDisplayed(save);
        click.moveToElementAndClick(saveButton);
    }

    /**
     * click on apply coupon button.
     */
    public void clickApplyCouponButton() {
        WebElement applyCpnButton = wait.waitForElementEnabled(applyCoupon);
        click.javascriptClick(applyCpnButton);
    }

    /**
     * validate coupon.
     *
     * @return discountRate
     */
    public double validateDiscountCoupon() {
        WebElement validateDisc = wait.waitForElementDisplayed(validateDiscount);
        discount = text.getElementText(validateDisc).replaceAll("[^0-9]", "");
        double priceDoubleDiscount = Double.parseDouble(discount);
        double discountRate = priceDoubleDiscount / 100;
        return discountRate;
    }

    /**
     * Reads discounted amount of applied coupon
     * example: wooCommerceProductPage.getDiscountedAmount("10DollarItem");
     *
     * @param couponCode need to pass coupon code to read discount on particular coupon
     * @return discounted amount of applied coupon
     */
    public String getDiscountedAmount(String couponCode) {
        waitForPageLoad();
        return text.getElementText(By.xpath(couponDiscount.replace("<<text_replace>>", couponCode.toLowerCase(Locale.ROOT))));
    }

    /**
     * Counts discounted amount of percentage based coupons
     * example: wooCommerceProductPage.getPercentBaseDiscountedAmount(5);
     *
     * @param discountPercent need to pass percentage of percentage based coupons
     * @return calculated discounted amount of percentage based coupons
     */
    public String getPercentBaseDiscountedAmount(float discountPercent) {
        waitForPageLoad();
        return "$" + String.format("%.2f", Double.parseDouble(text.getElementText(subTotalAmount).replace("$", "")) * (discountPercent / 100));
    }

    /**
     * click on apply coupon code.
     */
    public void applyCouponCode(String coupon) {
        waitForPageLoad();
        enterCouponCode(coupon);
        clickApplyCouponButton();
        waitForPageLoad();
    }

    /**
     * click on Product code.
     */
    public void clickProductCode() {
        WebElement productCode = wait.waitForElementDisplayed(code);
        click.moveToElementAndClick(productCode);
    }

    /**
     * navigates to the Product Code.
     *
     * @param productCode for exempted customer.
     */
    public void navigateToProductCode(String productCode) {
        clickProductCode();
        enterProductCode(productCode);
        clickSaveButton();
    }

    /**
     * click on Product class.
     */
    public void clickProductClass() {
        WebElement productClass = wait.waitForElementDisplayed(code);
        click.moveToElementAndClick(productClass);
    }

    /**
     * enter Product Class name.
     *
     * @param productClass for exempted customer.
     */
    public void enterProductClass(String productClass) {
        WebElement productCls = wait.waitForElementDisplayed(product);
        text.enterText(productCls, productClass);
    }

    /**
     * navigates to the Product Class.
     *
     * @param productClass for the customer.
     */
    public void navigateToProductClass(String productClass) {
        clickProductClass();
        enterProductClass(productClass);
        clickSaveButton();
    }
}