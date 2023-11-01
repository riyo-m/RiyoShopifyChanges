package com.vertex.quality.connectors.woocommerceTap.storefront.pages;

import com.vertex.quality.connectors.woocommerceTap.enums.WooData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Locale;

/**
 * shows checkout page for WooCommerce which shows the details to add like billing address
 * shipping address and tax
 *
 * @author rohit.mogane
 */
public class WooCommerceCheckoutPage extends WooCommerceCartPage {
    public WooCommerceCheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "billing_first_name")
    private WebElement billingFirstName;

    @FindBy(name = "billing_last_name")
    private WebElement billingLastname;

    @FindBy(id = "select2-billing_country-container")
    private WebElement billingCountry;

    @FindBy(name = "billing_address_1")
    private WebElement billingAddress1;

    @FindBy(name = "billing_postcode")
    private WebElement billingPostCode;

    @FindBy(name = "billing_city")
    private WebElement billingCity;

    @FindBy(name = "billing_phone")
    private WebElement billingPhone;

    @FindBy(name = "billing_tax_vat_number")
    private WebElement vatNumber;

    @FindBy(name = "woocommerce_checkout_place_order")
    private WebElement placeOrder;

    protected final By billingFirstId = By.xpath("//*[@id='billing_first_name']");
    protected final By billingLastId = By.xpath("//*[@id='billing_last_name']");
    protected final By billingCount = By.xpath("//select[@id='billing_country']");
    protected final By billingState = By.xpath("//select[@id='billing_state']");
    protected final By billCity = By.xpath("//input[@id='billing_city']");
    protected final By email = By.xpath("//input[@type='email']");
    protected final By billPhone = By.xpath("//input[@id='billing_phone']");
    protected final By vatNo = By.xpath("//*[@id='billing_tax_vat_number']");
    protected final By placeOrd = By.xpath("//*[@id='place_order']");
    protected final By discountCpn = By.xpath("//a[contains(text(),'Click here to enter your code')]");
    protected final By couponField = By.xpath("//input[@id='coupon_code']");
    protected final By applyCpn = By.xpath("//button[contains(text(),'Apply coupon')]");

    protected final By billingAddress = By.xpath("//input[@id='billing_address_1']");
    protected final By postalCd = By.xpath("//input[@id='billing_postcode']");
    protected final By shipToDiffAddress = By.xpath("//span[contains(text(),'Ship to a different address?')]");
    protected final By shipFirstName = By.xpath("//*[@id='shipping_first_name']");
    protected final By shipLastName = By.xpath("//*[@id='shipping_last_name']");
    protected final By shipCountry = By.xpath("//select[@id='shipping_country']");
    protected final By shipState = By.xpath("//select[@id='shipping_state']");
    protected final By shipAddress = By.xpath("//input[@id='shipping_address_1']");
    protected final By shipPostal = By.xpath("//input[@id='shipping_postcode']");
    protected final By shipCity = By.xpath("//input[@id='shipping_city']");
    protected final By taxAmountBeforeOrderPlace = By.xpath(".//th[text()='Tax']/following-sibling::td/span");
    protected final By taxAmountAfterOrderPlace = By.xpath(".//th[contains(text(),'EUROPEAN VAT PHYSICAL:')]/following-sibling::td/span");
    protected final By subTotalAfterOrderPlace = By.xpath(".//th[text()='Subtotal:']/following-sibling::td/span");
    protected final By shippingAfterOrderPlace = By.xpath(".//th[text()='Shipping:']/following-sibling::td/span");
    protected final By discountAfterOrderPlace = By.xpath(".//th[text()='Discount:']/following-sibling::td/span");
    protected final By subTotals = By.xpath("//table[1]/tfoot[1]/tr[1]/td[1]/span[1]");
    protected final By orderReceivedLabel = By.xpath(".//h1[text()='Order received']");
    protected final By enableCouponSection = By.className("showcoupon");
    protected final By couponCodeBox = By.xpath(".//input[@id='coupon_code']");
    protected final By applyCouponButton = By.name("apply_coupon");
    protected String removeCouponCode = ".//th[text()='Coupon: <<text_replace>>']/following-sibling::td/a[text()='[Remove]']";
    protected final By subTotalAmount = By.xpath(".//th[text()='Subtotal']/following-sibling::td/span");
    protected final By addressCleansingPopup = By.className("ajs-content");
    protected final By yesAddressCleansing = By.className("ajs-button ajs-ok");
    protected final By closeAddressCleansing = By.className("ajs-close");
    protected final By orderNoText = By.xpath(".//li[@class='woocommerce-order-overview__order order']//strong");
    protected String couponDiscount = ".//th[text()='Coupon: <<text_replace>>']/following-sibling::td/span";
    protected String taxValueField;
    protected String subTotalValue;

    /**
     * Verify address cleansing popup
     *
     * @return true or false based on address cleansing popup availability
     */
    public boolean isAddressCleansingPopupPresent() {
        return element.isElementPresent(addressCleansingPopup);
    }

    /**
     * Verify order received or not
     * example: wooCommerceCheckoutPage.verifyOrderReceived();
     *
     * @return true or false based on order received label presence
     */
    public boolean verifyOrderReceived() {
        waitForPageLoad();
        return element.isElementPresent(orderReceivedLabel);
    }

    /**
     * verify tax is not present after order received
     * example: wooCommerceCheckoutPage.verifyTaxLabel();
     *
     * @return true or false based on tax field presence
     */
    public boolean verifyTaxNotApplied() {
        waitForPageLoad();
        return !element.isElementPresent(taxAmountAfterOrderPlace);
    }

    /**
     * Reads address cleansing message from address cleansing popup
     *
     * @return message from address cleansing popup
     */
    public String getAddressCleansingMessage() {
        wait.waitForElementPresent(addressCleansingPopup, 30);
        return text.getElementText(addressCleansingPopup);
    }

    /**
     * handle popup of address cleansing (either accept or close popup)
     * example1: wooCommerceCheckoutPage.closeAddressCleansing(true); // To accept popup
     * example2: wooCommerceCheckoutPage.closeAddressCleansing(false); // To close popup
     *
     * @param accept pass true or false based on acceptance or closure of popup
     */
    public void handleAddressCleansing(boolean accept) {
        if (isAddressCleansingPopupPresent()) {
            wait.waitForElementPresent(addressCleansingPopup, 10);
            if (accept) {
                wait.waitForElementPresent(yesAddressCleansing);
                click.moveToElementAndClick(yesAddressCleansing);
            } else {
                wait.waitForElementPresent(closeAddressCleansing);
                click.moveToElementAndClick(closeAddressCleansing);
            }
        }
    }

    /**
     * adds billing address for the current order
     *
     * @param firstName of the billing address customer
     * @param lastName  of the billing address customer
     * @param country   of the billing address customer.
     * @param address1  of the billing address customer.
     * @param postal    zip code of the billing address customer.
     * @param city      of the billing address customer.
     * @param phone     of the billing address customer.
     * @param vat       vat number of the billing customer.
     */
    public void addBillingAddress(String firstName, String lastName, String country,
                                  String address1, String postal, String city, String phone, String vat) {
        WebElement billingFirstName = wait.waitForElementDisplayed(billingFirstId);
        text.enterText(billingFirstName, firstName);

        WebElement billingLastname = wait.waitForElementDisplayed(billingLastId);
        text.enterText(billingLastname, lastName);


        WebElement billingCountry = wait.waitForElementDisplayed(billingCount);
        Select sel = new Select(billingCountry);
        sel.selectByVisibleText(country);

        WebElement billingAddress1 = wait.waitForElementDisplayed(billingAddress);
        text.enterText(billingAddress1, address1);

        WebElement billingPostCode = wait.waitForElementDisplayed(postalCd);
        text.enterText(billingPostCode, postal);

        WebElement billingCity = wait.waitForElementDisplayed(billCity);
        text.enterText(billingCity, city);

        WebElement billingPhone = wait.waitForElementDisplayed(billPhone);
        text.enterText(billingPhone, phone);

        if (vat != null) {
            WebElement vatNumber = wait.waitForElementDisplayed(vatNo);

            text.enterText(vatNumber, vat);
        }
    }

    /**
     * click on place order button
     */
    public void clickPlaceOrder() {
        WebElement placeOrder = wait.waitForElementDisplayed(placeOrd);
        click.javascriptClick(placeOrder);
        waitForPageLoad();
    }

    /**
     * Get order number after order placed from store-front.
     * example: wooCommerceCheckoutPage.getOrderNoFromUI();
     *
     * @return order number
     */
    public String getOrderNoFromUI() {
        wait.waitForElementPresent(orderReceivedLabel);
        return text.getElementText(orderNoText);
    }

    /**
     * Get shipping amount after order placed from store-front.
     * example: wooCommerceCheckoutPage.getShippingAmountUI();
     *
     * @return shipping amount
     */
    public String getShippingAmountUI() {
        wait.waitForElementPresent(orderReceivedLabel);
        return text.getElementText(shippingAfterOrderPlace).replace("$", "");
    }

    /**
     * click on Discount Coupon button
     */
    public void clickDiscountCoupon() {
        WebElement discountCoupon = wait.waitForElementDisplayed(discountCpn);
        click.javascriptClick(discountCoupon);
    }

    /**
     * adds billing address without vat for the current order
     *
     * @param firstName of the billing address customer
     * @param lastName  of the billing address customer
     * @param country   of the billing address customer.
     * @param address1  of the billing address customer.
     * @param postal    zip code of the billing address customer.
     * @param city      of the billing address customer.
     * @param phone     of the billing address customer.
     * @param mail      mail number of the billing customer.
     */
    public void addBillingsAddress(String firstName, String lastName, String country,
                                   String address1, String postal, String city, String phone, String mail) {
        WebElement billingFirstName = wait.waitForElementDisplayed(billingFirstId);
        text.enterText(billingFirstName, firstName);

        WebElement billingLastname = wait.waitForElementDisplayed(billingLastId);
        text.enterText(billingLastname, lastName);


        WebElement billingCountry = wait.waitForElementDisplayed(billingCount);
        Select sel = new Select(billingCountry);
        sel.selectByVisibleText(country);

        WebElement billingAddress1 = wait.waitForElementDisplayed(billingAddress);
        text.enterText(billingAddress1, address1);

        WebElement billingPostCode = wait.waitForElementDisplayed(postalCd);
        text.enterText(billingPostCode, postal);

        WebElement billingCity = wait.waitForElementDisplayed(billCity);
        text.enterText(billingCity, city);

        WebElement billingPhone = wait.waitForElementDisplayed(billPhone);
        text.enterText(billingPhone, phone);

        WebElement emailId = wait.waitForElementDisplayed(email);
        text.enterText(emailId, mail);
    }

    /**
     * Add Name, Billing Address and Contact details
     * While passing parameters sequence must be followed as described below,
     * {"Country", "Street", "Town", "State", "ZIP"}, All 5 fields are mandatory on UI for US & Canada countries.
     * {"Country", "Street", "Town", "ZIP"}, Only 4 fields (excluding State) are mandatory on UI for UK & Germany countries.
     * No need to pass Name & Contact details, however it is managed inside this method internally.
     * example1: wooCommerceCheckoutPage.addBillingsAddress("USA", "street x, road 3", "Los Angeles", "California", "90030"); // For US or Canada countries
     * example2: wooCommerceCheckoutPage.addBillingsAddress("Germany", "street x, road 3", "Berlin", "13405"); // For Europe or UK countries
     *
     * @param isStateRequired true to enter state & false to ignore state details
     * @param billingAddress  Sets billing address
     */
    public void addBillingsAddress(boolean isStateRequired, String... billingAddress) {
        if (billingAddress.length < 4 | billingAddress.length > 5) {
            Assert.fail("Kindly check parameters, All parameters are mandatory. " +
                    "parameters must be as per given format. For more details kindly read Javadoc of Method.");
        }
        WebElement billingFirstName = wait.waitForElementDisplayed(billingFirstId);
        text.enterText(billingFirstName, WooData.FIRST_NAME.value);

        WebElement billingLastname = wait.waitForElementDisplayed(billingLastId);
        text.enterText(billingLastname, WooData.LAST_NAME.value);

        WebElement billingPhone = wait.waitForElementDisplayed(billPhone);
        text.enterText(billingPhone, WooData.CONTACT_NO.value);

        WebElement emailId = wait.waitForElementDisplayed(email);
        text.enterText(emailId, WooData.EMAIL.value);

        WebElement billingCountry = wait.waitForElementDisplayed(billingCount);
        Select country = new Select(billingCountry);
        country.selectByVisibleText(billingAddress[0]);

        WebElement billingAddress1 = wait.waitForElementDisplayed(this.billingAddress);
        text.enterText(billingAddress1, billingAddress[1]);

        WebElement billingCity = wait.waitForElementDisplayed(billCity);
        text.clearText(billingCity);
        handleAddressCleansing(false);
        text.enterText(billingCity, billingAddress[2], false);

        handleAddressCleansing(false);
        if (isStateRequired) {
            WebElement billState = wait.waitForElementDisplayed(billingState);
            Select state = new Select(billState);
            state.selectByVisibleText(billingAddress[3]);
        }

        WebElement billingPostCode = wait.waitForElementDisplayed(postalCd);
        if (!element.isElementDisplayed(billingState)) {
            text.clearText(billingPostCode);
            handleAddressCleansing(false);
            text.enterText(billingPostCode, billingAddress[3], false);
        } else {
            text.clearText(billingPostCode);
            handleAddressCleansing(false);
            text.enterText(billingPostCode, billingAddress[4], false);
        }
        text.pressTab(billingPostCode);
    }

    /**
     * Add Name, Billing Address and Contact details
     * While passing parameters sequence must be followed as described below,
     * {"Country", "Street", "Town", "State", "ZIP"}, All 5 fields are mandatory on UI for US & Canada countries.
     * {"Country", "Street", "Town", "ZIP"}, Only 4 fields (excluding State) are mandatory on UI for UK & Germany countries.
     * No need to pass Name & Contact details, however it is managed inside this method internally.
     * example1: wooCommerceCheckoutPage.addBillingsAddress("USA", "street x, road 3", "Los Angeles", "California", "90030"); // For US or Canada countries
     * example2: wooCommerceCheckoutPage.addBillingsAddress("Germany", "street x, road 3", "Berlin", "13405"); // For Europe or UK countries
     *
     * @param billingAddress Sets billing address
     */
    public void addBillingsAddress(String... billingAddress) {
        addBillingsAddress(true, billingAddress);
    }

    /**
     * click on apply coupon button
     */
    public void clickApplyCouponButton() {
        WebElement applyCoupon = wait.waitForElementDisplayed(applyCpn);
        click.javascriptClick(applyCoupon);
    }

    /**
     * enter Particular Discount Coupon.
     *
     * @param coupon discount coupon code to be selected.
     */
    public void enterDiscountCoupon(String coupon) {
        WebElement discountCpnField = wait.waitForElementDisplayed(couponField);

        text.enterText(discountCpnField, coupon);
    }

    /**
     * apply Particular Discount for order.
     *
     * @param coupon for particular order.
     */
    public void enterDiscount(String coupon) {
        clickDiscountCoupon();
        enterDiscountCoupon(coupon);
        clickApplyCouponButton();
    }

    /**
     * Reads total amount of Tax from UI page
     * example: wooCommerceCheckoutPage.getTaxFromUI();
     *
     * @return total tax value of order
     */
    public String getTaxFromUIAfterOrderPlace() {
        waitForPageLoad();
        wait.waitForElementPresent(orderReceivedLabel);
        return text.getElementText(wait.waitForElementPresent(taxAmountAfterOrderPlace));
    }

    /**
     * Reads total amount of Tax from UI page
     * example: wooCommerceCheckoutPage.getTaxFromUI();
     *
     * @return total tax value of order
     */
    public double getTaxFromUIAfterPlacingOrder() {
        waitForPageLoad();
        wait.waitForElementPresent(orderReceivedLabel);
        return Double.parseDouble(getTaxFromUIAfterOrderPlace().replace("€", "").replace(",", ""));
    }

    /**
     * Validates if tax is calculated or not for the order placed
     * example: wooCommerceCheckoutPage.verifyIsTaxPresent();
     *
     * @return true or false based on condition
     */
    public boolean verifyIsTaxPresent() {
        waitForPageLoad();
        wait.waitForElementPresent(orderReceivedLabel);
        return element.isElementPresent(taxAmountAfterOrderPlace);
    }

    /**
     * Counts discounted amount of percentage based coupons
     * example: wooCommerceProductPage.calculatePercentageBasedTax(5);
     *
     * @param taxPercent need to pass percentage of percentage based tax
     * @return calculated tax amount based on percentage
     */
    public String calculatePercentageBasedTax(double taxPercent) {
        double shipping = 0;
        double discount = 0;
        wait.waitForElementPresent(orderReceivedLabel);
        if (element.isElementDisplayed(shippingAfterOrderPlace)) {
            shipping = Double.parseDouble(text.getElementText(shippingAfterOrderPlace).replace("€", ""));
        }
        if (element.isElementDisplayed(discountAfterOrderPlace)) {
            discount = Double.parseDouble(text.getElementText(discountAfterOrderPlace).replace("€", ""));
        }
        double total = (Double.parseDouble(text.getElementText(subTotalAfterOrderPlace).replace("€", "")) + shipping - discount)
                * (taxPercent / 100);
        return "€" + String.format("%.2f", total);
    }

    /**
     * Counts discounted amount of percentage based coupons
     * example: wooCommerceProductPage.calculatePercentageBasedTax(5);
     *
     * @param taxPercent need to pass percentage of percentage based tax
     * @return calculated tax amount based on percentage
     */
    public double calculatePercentBasedTax(double taxPercent) {
        waitForPageLoad();
        wait.waitForElementPresent(orderReceivedLabel);
        return Double.parseDouble(calculatePercentageBasedTax(taxPercent).replace("€", "").replace(",", ""));
    }

    /**
     * Reads total amount of Tax from UI page
     * example: wooCommerceCheckoutPage.getTaxFromUI();
     *
     * @return total tax value of order
     */
    public String getTaxFromUIBeforeOrderPlace() {
        wait.waitForElementPresent(taxAmountBeforeOrderPlace);
        return text.getElementText(taxAmountBeforeOrderPlace);
    }

    /**
     * calculate tax rate for particular order.
     */
    public double calculateTax() {
        waitForPageLoad();
        wait.waitForElementPresent(orderReceivedLabel);
        WebElement tax = wait.waitForElementDisplayed(taxAmountAfterOrderPlace);
        taxValueField = text.getElementText(tax).replaceAll("[^0-9]", "");
        WebElement subTotal = wait.waitForElementDisplayed(subTotals);
        subTotalValue = text.getElementText(subTotal).replaceAll("[^0-9]", "");
        double priceDoubleTotal = Double.parseDouble(subTotalValue);
        double priceDoubleTax = Double.parseDouble(taxValueField);
        double taxRate = priceDoubleTax / priceDoubleTotal * 100;
        double roundOff = (double) Math.round(taxRate * 100) / 100;
        return roundOff;
    }

    /**
     * click on Shipping Address checkbox.
     */
    public void clickOnCheckBox() {
        WebElement checkBox = wait.waitForElementDisplayed(shipToDiffAddress);
        click.javascriptClick(checkBox);
    }

    /**
     * adds shipping address for the current order
     *
     * @param firstName of the shipping address customer.
     * @param lastName  of the ship to address customer.
     * @param country   of ship to address customer.
     * @param address1  of ship to address customer.
     * @param postal    zip of ship to address customer.
     * @param city      of ship to address customer.
     */
    public void addShippingAddress(String firstName, String lastName, String country,
                                   String address1, String postal, String city) {
        WebElement shippingFirstName = wait.waitForElementDisplayed(shipFirstName);
        text.enterText(shippingFirstName, firstName);

        WebElement shippingLastname = wait.waitForElementDisplayed(shipLastName);
        text.enterText(shippingLastname, lastName);


        WebElement billingCountry = wait.waitForElementDisplayed(shipCountry);
        Select sel = new Select(billingCountry);
        sel.selectByVisibleText(country);


        WebElement billingAddress1 = wait.waitForElementDisplayed(shipAddress);
        text.enterText(billingAddress1, address1);


        WebElement billingPostCode = wait.waitForElementDisplayed(shipPostal);

        text.enterText(billingPostCode, postal);

        WebElement billingCity = wait.waitForElementDisplayed(shipCity);
        text.enterText(billingCity, city);
    }

    /**
     * Add Name and Shipping Address details.
     * While passing parameters sequence must be followed as described below,
     * {"Country", "Street", "Town", "State", "ZIP"}, All 5 fields are mandatory on UI for US & Canada countries.
     * {"Country", "Street", "Town", "ZIP"}, Only 4 fields (excluding State) are mandatory on UI for UK & Germany countries.
     * No need to pass Name & Contact details, however it is managed inside this method internally.
     * example1: wooCommerceCheckoutPage.addBillingsAddress("USA", "street x, road 3", "Los Angeles", "California", "90030"); // For US or Canada countries
     * example2: wooCommerceCheckoutPage.addBillingsAddress("Germany", "street x, road 3", "Berlin", "13405"); // For Europe or UK countries
     *
     * @param shippingAddress Sets shipping address
     */
    public void addShippingAddress(String... shippingAddress) {
        if (shippingAddress.length < 4 | shippingAddress.length > 5) {
            Assert.fail("Kindly check parameters, All parameters are mandatory. " +
                    "parameters must be as per given format. For more details kindly read Javadoc of Method.");
        }
        WebElement shippingFirstName = wait.waitForElementDisplayed(shipFirstName);
        text.enterText(shippingFirstName, WooData.FIRST_NAME.value);

        WebElement shippingLastname = wait.waitForElementDisplayed(shipLastName);
        text.enterText(shippingLastname, WooData.LAST_NAME.value);

        WebElement shippingCountry = wait.waitForElementDisplayed(shipCountry);
        Select country = new Select(shippingCountry);
        country.selectByVisibleText(shippingAddress[0]);

        WebElement shippingAddress1 = wait.waitForElementDisplayed(shipAddress);
        text.enterText(shippingAddress1, shippingAddress[1]);

        WebElement shippingCity = wait.waitForElementDisplayed(shipCity);
        text.clearText(shippingCity);
        handleAddressCleansing(false);
        text.enterText(shippingCity, shippingAddress[2], false);

        handleAddressCleansing(false);
        if (element.isElementDisplayed(shipState)) {
            WebElement shippingState = wait.waitForElementDisplayed(shipState);
            Select state = new Select(shippingState);
            state.selectByVisibleText(shippingAddress[3]);
        }

        WebElement shippingPostCode = wait.waitForElementDisplayed(shipPostal);
        if (!element.isElementDisplayed(shipState)) {
            text.clearText(shippingPostCode);
            handleAddressCleansing(false);
            text.enterText(shippingPostCode, shippingAddress[3], false);
        } else {
            text.clearText(shippingPostCode);
            handleAddressCleansing(false);
            text.enterText(shippingPostCode, shippingAddress[4], false);
        }
    }

    /**
     * Removes the coupon code
     * example: wooCommerceCheckoutPage.removeCouponCode("10DollarItem");
     *
     * @param couponCode based on coupon value applied coupon code will be removed
     */
    public void removeCouponCode(String couponCode) {
        waitForPageLoad();
        WebElement coupon = wait.waitForElementPresent(By.xpath(removeCouponCode.replace("<<text_replace>>", couponCode.toLowerCase(Locale.ROOT))));
        click.javascriptClick(coupon);
        waitForPageLoad();
    }

    /**
     * Add coupon(s) from checkout page for addition of discount
     * example: wooCommerceCheckoutPage.addCouponCode("5DollarItem");
     *
     * @param couponCode apply coupon code
     */
    public void addCouponCode(String couponCode) {
        waitForPageLoad();
        if (!element.isElementDisplayed(couponCodeBox) && element.isElementDisplayed(enableCouponSection)) {
            wait.waitForElementPresent(enableCouponSection);
            click.javascriptClick(enableCouponSection);
        }
        wait.waitForElementPresent(couponCodeBox);
        text.enterText(couponCodeBox, couponCode);
        wait.waitForElementEnabled(applyCouponButton);
        click.javascriptClick(applyCouponButton);
        waitForPageLoad();
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
     * example: wooCommerceProductPage.getPercentBaseDiscountedAmount(10);
     *
     * @param discountPercent need to pass percentage of percentage based coupons
     * @return calculated discounted amount of percentage based coupons
     */
    public String getPercentBaseDiscountedAmount(float discountPercent) {
        waitForPageLoad();
        return "$" + String.format("%.2f", Double.parseDouble(text.getElementText(subTotalAmount).replace("$", "")) * (discountPercent / 100));
    }

    /**
     * Adjustment amount for particular order.
     *
     * @param Amount to be adjusted/credited for customer.
     * @return AdjustAmount adjustment amount for the order.
     */
    public double AdjustAmountOrder(String Amount) {
        WebElement tax = wait.waitForElementDisplayed(taxAmountAfterOrderPlace);
        taxValueField = text.getElementText(tax).replaceAll("[^0-9]", "");
        double priceDoubleTax = Double.parseDouble(taxValueField);
        double Amounts = priceDoubleTax / 100;
        double AdjustAmount = Amounts - Double.parseDouble(Amount);
        return AdjustAmount;
    }

    /**
     * Adjustment amount for particular order.
     *
     * @return Amounts order amounts for order.
     */
    public double getOrderAmounts() {
        WebElement tax = wait.waitForElementDisplayed(taxAmountAfterOrderPlace);
        taxValueField = text.getElementText(tax).replaceAll("[^0-9]", "");
        double priceDoubleTax = Double.parseDouble(taxValueField);
        double Amounts = priceDoubleTax / 100;
        return Amounts;
    }

}
