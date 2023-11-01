package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Representation of the Payment Method Page
 *
 * @author alewis
 */
public class M2StorefrontPaymentMethodPage extends MagentoStorefrontPage {
    protected By billingAddressShippingId = By.id("billing-address-same-as-shipping-checkmo");
    protected By addressDropDownName = By.name("billing_address_id");
    protected By editAddressClass = By.className("action-edit-address");
    protected By actionUpdateClass = By.className("action-update");
    protected By billingAddressDetailsClass = By.className("billing-address-details");

    protected By grandClass = By.className("grand");
    protected By subClass = By.className("sub");
    protected By markClass = By.className("mark");
    protected By labelClass = By.className("label");
    protected By amountClass = By.className("amount");
    protected By shippingClass = By.className("shipping");
    protected By subPriceClass = By.className("price");

    protected By paymentMethodContentClass = By.className("payment-method-content");
    protected By checkoutAgreementsClass = By.className("actions-toolbar");
    protected By primaryClass = By.className("primary");
    protected By checkoutClass = By.className("checkout");
    protected By buttonClass = By.tagName("button");
    protected By tncCheckbox = By.xpath("(.//input[@name='taxamo-liability'])[1]");

    protected By blindClass = By.className("totals-tax-summary");
    protected By taxTotalsClass = By.className("totals-tax-details");
    protected By taxNoBlindClass = By.className("totals-tax");

    protected String placeOrderButtonText = "Place Order";
    protected String subtotalInclString = "Cart Subtotal Incl. Tax";
    protected String subtotalExclString = "Cart Subtotal Excl. Tax";
    protected String exclShippingString = "Shipping Excl. Tax";
    protected String inclShippingString = "Shipping Incl. Tax";
    protected String salesUseString = "Sales and Use (6%)";
    protected String salesUseStringCali = "Sales and Use (10.25%)";
    protected String salesUseStringNineFive = "Sales and Use (9.5%)";
    protected String shippingTaxString = "Shipping (6%)";
    protected String shippingTaxStringCali = "Shipping (10.25%)";
    protected String inclTaxString = "Order Total Incl. Tax";
    protected String exclTaxString = "Order Total Excl. Tax";
    By maskClass = By.className("loading-mask");
    protected By checkID = By.id("checkmo");
    protected By paymentMethodLabel = By.xpath(".//div[text()='Payment Method']");
    protected By creditCardRadioButton = By.xpath(".//span[text()='Credit Card']");
    protected By payPalRadioButton = By.xpath(".//input[@id='paypal_express']");
    protected By paypalButtonIframe = By.xpath(".//iframe[@class='component-frame visible']");
    protected By paypalAgreeTermsCheckbox = By.xpath(".//*[@id='paypal_express']/parent::div/following-sibling::div//input");
    protected By payPalButton = By.xpath(".//img[contains(@class,'paypal-logo')]");
    protected By paypalLoaderAtLogin = By.xpath(".//*[@class='spinWrap']");
    protected By paypalLoginPageDialog = By.id("login");
    protected By paypalLoginEmailBox = By.id("email");
    protected By paypalLockIconSpinner = By.xpath(".//*[@class='lockIcon']");
    protected By paypalAppLoader = By.id("app-loader");
    protected By paypalEmailNextButton = By.id("btnNext");
    protected By paypalLoginPasswordBox = By.id("password");
    protected By paypalLoginButton = By.id("btnLogin");
    protected By paypalCustomerDetailDialog = By.id("hermione-container");
    protected By customerNameOnPaypal = By.xpath(".//div[@data-testid='shipping-address']//p[1]");
    protected By customerAddressOnPaypal = By.xpath(".//div[@data-testid='shipping-address']//p[2]");
    protected By subtotalOnPaypal = By.xpath(".//div[@data-testid='fi-amount']//span[1]");
    protected By paypalCompletePurchaseButton = By.xpath(".//button[text()='Complete Purchase']");
    protected By paypalCheckoutModal = By.className("paypal-checkout-modal");
    protected By sameBillingShippingCreditCardCheckbox = By.xpath("(.//input[contains(@id,'billing-address-same-as-shipping')])[2]");
    protected By sameBillingShippingCheckMoneyCheckbox = By.xpath("(.//input[contains(@id,'billing-address-same-as-shipping')])[1]");
    protected By iFrameCreditCardNo = By.xpath(".//iframe[@type='number']");
    protected By creditCardNoBox = By.xpath(".//input[@id='credit-card-number']");
    protected By iFrameExpiration = By.xpath(".//iframe[@type='expirationDate']");
    protected By expirationDateBox = By.xpath(".//input[@id='expiration']");
    protected By iFrameCvv = By.xpath(".//iframe[@type='cvv']");
    protected By cvvBox = By.xpath(".//input[@id='cvv']");
    protected By otpIframe = By.xpath(".//iframe[@id='Cardinal-CCA-IFrame']");
    protected By otpPopup = By.xpath(".//div[@id='Cardinal-Modal']");
    protected By otpCode = By.xpath(".//p[@class='challengeinfotext']");
    protected By otpCodeBox = By.xpath(".//input[@name='challengeDataEntry']");
    protected By otpSubmitButton = By.xpath(".//input[@value='SUBMIT']");
    protected By orderSummaryLabel = By.xpath(".//span[text()='Order Summary']");
    protected By cartSubtotalLabel = By.xpath(".//th[text()='Cart Subtotal']");
    protected By taxLabel = By.xpath(".//th[text()='Tax']");
    protected By shippingLabel = By.xpath(".//th[contains(normalize-space(),'Shipping')]");
    protected By subtotalAmount = By.xpath(".//th[text()='Cart Subtotal']//following-sibling::td//span");
    protected By discountAmount = By.xpath(".//th[contains(normalize-space(.),'Discount')]//following-sibling::td//span");
    protected By shippingAmount = By.xpath(".//th[contains(normalize-space(.),'Shipping')]//following-sibling::td//span");
    protected By taxAmount = By.xpath(".//th[text()='Tax']//following-sibling::td//span");
    protected By orderTotalAmount = By.xpath(".//th[normalize-space()='Order Total']//following-sibling::td//span");
    protected By placeOrderButtonCheckMoney = By.xpath("(.//button[normalize-space(.)='Place Order'])[1]");
    protected By placeOrderButtonCreditCard = By.xpath("(.//button[normalize-space(.)='Place Order'])[2]");

    protected By discountXpath = By.xpath("//*[@id=\"block-discount-heading\"]");
    protected By discountFieldXpath = By.xpath("//*[@id=\"discount-code\"]");
    protected By discountButtonXpath = By.xpath("//*[@id=\"discount-form\"]/div[2]/div/button/span/span");

    protected String innerHTMLString = MagentoData.innerHTML.data;

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontPaymentMethodPage(WebDriver driver) {
        super(driver);
    }

    /**
     * switches the address for the order for customers with multiple addresses
     */
    public void switchAddress(String address) {
        dropdown.selectDropdownByIndex(addressDropDownName, 1);
    }

    /**
     * changes the address of shipping
     */
    public void clickEditAddress(String address) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement editAddress = wait.waitForElementPresent(editAddressClass);
        editAddress.click();
        switchAddress(address);
        WebElement actionUpdate = wait.waitForElementPresent(actionUpdateClass);
        actionUpdate.click();
        waitForPageLoad();
        try {
            attribute.waitForElementAttributeChange(billingAddressDetailsClass, innerHTMLString);
        } catch (Exception e) {
            VertexLogger.log("Error / Exception occurred please check the logs");
            e.printStackTrace();
        }
    }

    /**
     * selects Check/ Money Order payment method
     */
    public void selectCheckMoneyOrderPaymentMethod() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement checkField = wait.waitForElementDisplayed(checkID);
        click.clickElementCarefully(checkField);
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(sameBillingShippingCheckMoneyCheckbox)) {
            if (!checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingCheckMoneyCheckbox))) {
                click.moveToElementAndClick(sameBillingShippingCheckMoneyCheckbox);
            }
        }
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Changes the address for a physical product
     *
     * @param address address detail
     */
    public void editAddressPhysicalProduct(String address) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement billingAddressShipping = wait.waitForElementPresent(billingAddressShippingId);
        billingAddressShipping.click();
        switchAddress(address);
        WebElement actionUpdate = wait.waitForElementPresent(actionUpdateClass);
        actionUpdate.click();
        waitForPageLoad();
        attribute.waitForElementAttributeChange(billingAddressDetailsClass, innerHTMLString);
    }

    /**
     * gets the subtotal price excluding tax from the Order Summary section
     *
     * @return double of subtotal excluding tax
     */
    public double getSubtotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceString = null;
        List<WebElement> subs = wait.waitForAllElementsPresent(subClass);

        WebElement sub = element.selectElementByNestedLabel(subs, markClass, subtotalExclString);
        if (sub != null) {
            WebElement price = wait.waitForElementPresent(subPriceClass, sub);
            priceString = price.getText();
        }

        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the subtotal price including tax from the Order Summary section
     *
     * @return double of shipping including tax
     */
    public double getSubtotalInclTax() {
        this.refreshPage();
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);

        String priceString = null;
        List<WebElement> subs = wait.waitForAllElementsPresent(subClass);

        WebElement sub = element.selectElementByNestedLabel(subs, markClass, subtotalInclString);
        if (sub != null) {
            WebElement price = wait.waitForElementPresent(subPriceClass, sub);
            priceString = text.getElementText(price);
        }

        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the shipping price excluding tax from the Order Summary section
     *
     * @return double of shipping excluding tax
     */
    public double getShippingExclTax() {
        String priceString = null;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> shippings = wait.waitForAllElementsPresent(shippingClass);

        for (WebElement shipping : shippings) {
            WebElement mark = wait.waitForElementPresent(markClass, shipping);
            WebElement label = wait.waitForElementPresent(labelClass, mark);
            String labelText = label.getText();

            if (labelText.equals(exclShippingString)) {
                WebElement price = wait.waitForElementPresent(subPriceClass, shipping);
                priceString = price.getText();
            }
        }

        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the shipping price including tax from the Order Summary section
     *
     * @return double of shipping including tax
     */
    public double getShippingInclTax() {
        String priceString = null;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> shippings = wait.waitForAllElementsPresent(shippingClass);

        for (WebElement shipping : shippings) {
            WebElement mark = wait.waitForElementPresent(markClass, shipping);
            WebElement label = wait.waitForElementPresent(labelClass, mark);
            String labelText = label.getText();

            if (labelText.equals(inclShippingString)) {
                WebElement price = wait.waitForElementPresent(subPriceClass, shipping);
                priceString = price.getText();
            }
        }

        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * Opens tax blind
     */
    public void openTaxBlind() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement taxBlindDisplayed;
        WebElement amount;
        try {
            taxBlindDisplayed = wait.waitForElementDisplayed(blindClass, 3);
            amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed, 3);
        } catch (Exception e) {
            taxBlindDisplayed = wait.waitForElementDisplayed(blindClass);
            amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed);
        }

        try {
            attribute.waitForElementAttributeChange(amount, "innerHTML", 10);
        } catch (Exception e) {
            System.out.println("Couldn't do");
        }


        waitForPageLoad();

        scroll.scrollElementIntoView(amount);
        click.clickElementCarefully(amount);
    }

    /**
     * gets the tax from the Order Summary section when there is no tax blind,
     * so there is only one category
     *
     * @return a double of tax
     */
    public double getTaxNoBlind() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement taxTotal = wait.waitForElementDisplayed(taxNoBlindClass);

        WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
        String price = amount.getText();

        String parsedTaxValue = price.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the Sales and Use tax from the Order Summary section
     *
     * @return a double of Sales and Use tax
     */
    public double getSalesUseTax() {
        String price = null;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> taxTotals = wait.waitForAllElementsPresent(taxTotalsClass);

        for (WebElement taxTotal : taxTotals) {
            WebElement mark = wait.waitForElementPresent(markClass, taxTotal);
            String markText = mark.getText();

            if (markText.equals(salesUseString) || markText.equals(salesUseStringCali) || markText.equals(
                    salesUseStringNineFive)) {
                WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
                price = amount.getText();
            }
        }
        String parsedTaxValue = price.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the Shipping tax from the Order Summary section
     *
     * @return a double of shipping tax
     */
    public double getShippingTaxInBlind() {
        String price = null;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> taxTotals = wait.waitForAllElementsPresent(taxTotalsClass);

        for (WebElement taxTotal : taxTotals) {
            WebElement mark = wait.waitForElementPresent(markClass, taxTotal);
            String markText = mark.getText();

            if (markText.equals(shippingTaxString) || markText.equals(shippingTaxStringCali)) {
                WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
                price = amount.getText();
            }
        }
        String parsedTaxValue = price.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the total price excluding tax from the Order Summary section
     *
     * @return a double of total price excluding tax of the item
     */
    public Double getTotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandClass);
        String price = null;

        WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, exclTaxString);
        if (grandTotal != null) {
            WebElement amount = grandTotal.findElement(amountClass);
            WebElement grandPrice = amount.findElement(subPriceClass);
            price = grandPrice.getText();
        }
        String parsedTaxValue = price.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * gets the total price including tax from the Order Summary section
     *
     * @return a double of total price of the item
     */
    public Double getTotalInclTax() {
        this.refreshPage();
        waitForPageLoad();

        List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandClass);
        String price = null;

        WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, inclTaxString);
        if (grandTotal != null) {
            WebElement amount = grandTotal.findElement(amountClass);
            WebElement grandPrice = amount.findElement(subPriceClass);
            price = grandPrice.getText();
        }
        String parseTaxValue = price.substring(1);
        double priceDouble = Double.parseDouble(parseTaxValue);

        return priceDouble;
    }

    /**
     * Apply discount or coupon
     *
     * @param discountString coupon or discount code
     */
    public void applyDiscount(String discountString) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement discount = wait.waitForElementDisplayed(discountXpath);
        click.clickElementCarefully(discount);

        WebElement discountField = wait.waitForElementDisplayed(discountFieldXpath);
        click.clickElementCarefully(discountField);
        text.enterText(discountField, discountString);

        WebElement discountButton = wait.waitForElementDisplayed(discountButtonXpath);
        click.clickElementCarefully(discountButton);
    }

    /**
     * clicks the Place Order Button on the Payment Method page. Inputting the order
     *
     * @return Thank you page
     */
    public M2StorefrontThankYouPage clickPlaceOrderButton() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        String buttonText = null;
        wait.waitForElementDisplayed(paymentMethodContentClass);
        WebElement paymentMethodContent = wait.waitForElementPresent(paymentMethodContentClass);
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(tncCheckbox)) {
            checkbox.setCheckbox(tncCheckbox, true);
        }
        List<WebElement> orderButtons = paymentMethodContent.findElements(checkoutAgreementsClass);

        for (WebElement orderButton : orderButtons) {
            waitForPageLoad();
            WebElement placeButtonPrimary = orderButton.findElement(primaryClass);
            WebElement button = placeButtonPrimary.findElement(buttonClass);
            buttonText = button.getAttribute("title");
            waitForPageLoad();
            if (buttonText != null && buttonText.equals(placeOrderButtonText)) {
                waitForPageLoad();
                orderButton = button;
                wait.waitForElementDisplayed(orderButton, 5);
                click.clickElement(orderButton);
            }
        }
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        return new M2StorefrontThankYouPage(driver);
    }

    /**
     * Clicks the place order button
     *
     * @return Thank you page
     */
    public M2StorefrontThankYouPage clickPlaceOrderButtonDiscount() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String buttonText = null;
        WebElement paymentMethodContent = wait.waitForElementPresent(paymentMethodContentClass);
        List<WebElement> orderButtons = paymentMethodContent.findElements(checkoutAgreementsClass);

        for (WebElement orderButton : orderButtons) {
            WebElement placeButtonPrimary = orderButton.findElement(primaryClass);
            WebElement button = placeButtonPrimary.findElement(buttonClass);
            buttonText = button.getAttribute("title");

            if (buttonText != null && buttonText.equals(placeOrderButtonText)) {
                orderButton = button;
                System.out.println(orderButton.getText());
                WebElement checkoutButton = wait.waitForElementPresent(checkoutClass, orderButton);
                click.clickElement(checkoutButton);
            }
        }
        return new M2StorefrontThankYouPage(driver);
    }

    /**
     * Select credit card as payment method & enter credit card details
     *
     * @param cardNo  credit card number
     * @param expDate credit card's expiration date
     * @param cvv     CVV of credit card
     */
    public void enterCreditCardDetails(String cardNo, String expDate, String cvv) {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(paymentMethodLabel);
        click.moveToElementAndClick(wait.waitForElementPresent(creditCardRadioButton));
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        if (!checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingCreditCardCheckbox))) {
            click.moveToElementAndClick(sameBillingShippingCreditCardCheckbox);
        }
        window.switchToFrame(iFrameCreditCardNo);
        text.enterText(wait.waitForElementPresent(creditCardNoBox), cardNo);
        window.switchToFrame(iFrameExpiration);
        text.enterText(wait.waitForElementPresent(expirationDateBox), expDate);
        window.switchToFrame(iFrameCvv);
        text.enterText(wait.waitForElementPresent(cvvBox), cvv);
        window.switchToDefaultContent();
    }

    /**
     * Enter Credit Card OTP
     *
     * @param otp Credit Card OTP
     */
    public void enterOTP(String otp) {
        waitForPageLoad();
        window.switchToFrame(otpIframe);
        waitForSpinnerToBeDisappeared();
        window.switchToDefaultContent();
        if (element.isElementPresent(otpPopup)) {
            window.switchToFrame(otpIframe);
            text.enterText(wait.waitForElementPresent(otpCodeBox), otp);
            click.moveToElementAndClick(wait.waitForElementPresent(otpSubmitButton));
            window.switchToDefaultContent();
        }
    }

    /**
     * Reads OTP from the UI
     *
     * @return OTP from UI
     */
    public String getOTPFromUI() {
        String otp = "";
        waitForPageLoad();
        window.switchToFrame(otpIframe);
        waitForSpinnerToBeDisappeared();
        window.switchToDefaultContent();
        if (element.isElementPresent(otpPopup)) {
            window.switchToFrame(otpIframe);
            otp = text.getElementText(wait.waitForElementPresent(otpCode)).split("OTP:")[1].trim().replace(")", "");
            window.switchToDefaultContent();
        }
        return otp;
    }

    /**
     * Get subtotal amount from the UI
     *
     * @return subtotal amount
     */
    public double getSubTotalFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        wait.waitForElementPresent(subtotalAmount);
        String subTotal = text.getElementText(subtotalAmount).replace("€", "").replace(",", "");
        return Double.parseDouble(subTotal);
    }

    /**
     * Gets tax amount from the UI
     *
     * @return tax amount from UI.
     */
    public double getTaxFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        wait.waitForElementPresent(taxLabel);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("€", "").replace(",", ""));
    }

    /**
     * Gets order total (including tax, shipping and discount) amount from the UI
     *
     * @return order total amount from UI.
     */
    public double getGrandTotalFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(orderTotalAmount)).replace("€", "").replace(",", ""));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(double taxAmount) {
        double subtotal = 0;
        double discount = 0;
        double shipHandle = 0;
        double expectedTax = 0;
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("€", "").replace(",", ""));
        shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("€", "").replace(",", ""));
        if (element.isElementDisplayed(discountAmount)) {
            discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(discountAmount)).replace("-€", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Validates whether Tax field is visible on UI while check-out time or not?
     *
     * @return true or false based on condition match.
     */
    public boolean isTaxFieldVisibleOnUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        return element.isElementDisplayed(taxLabel) | element.isElementPresent(taxAmount);
    }

    /**
     * Validates whether Shipping field is visible on UI while check-out time or not?
     *
     * @return true or false based on condition match.
     */
    public boolean isShippingFieldVisibleOnUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        return element.isElementDisplayed(shippingLabel) | element.isElementPresent(shippingAmount);
    }

    /**
     * Click Place Order button
     */
    public void placeTheOrderCreditCard() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(cartSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        click.javascriptClick(wait.waitForElementPresent(placeOrderButtonCreditCard));
        waitForPageLoad();
    }

    /**
     * Waits until PayPal's loaders gets disappeared.
     */
    private void waitForPaypalSpinners() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(paypalAppLoader));
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(paypalLoaderAtLogin));
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(paypalLockIconSpinner));
    }

    /**
     * Select PayPal payment method
     */
    private void selectPayPalPaymentMethod() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(payPalRadioButton));
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(paypalAgreeTermsCheckbox)) {
            click.moveToElementAndClick(paypalAgreeTermsCheckbox);
            waitForPageLoad();
            waitForPaypalSpinners();
        }
        window.switchToFrame(paypalButtonIframe);
        click.moveToElementAndClick(payPalButton);
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        window.switchToDefaultContent();
        VertexLogger.log("Selected PayPal payment method");
    }

    /**
     * Login to PayPal sandbox with Dummy User configured to PayPal Sandbox.
     */
    private void loginToPaypalWithDummyUser() {
        waitForPageLoad();
        waitForPaypalSpinners();
        wait.waitForElementPresent(paypalLoginPageDialog);
        text.enterText(wait.waitForElementPresent(paypalLoginEmailBox), MagentoData.PAYPAL_DUMMY_USERNAME.data);
        click.moveToElementAndClick(wait.waitForElementPresent(paypalEmailNextButton));
        waitForPaypalSpinners();
        text.enterText(wait.waitForElementPresent(paypalLoginPasswordBox), MagentoData.PAYPAL_DUMMY_PASSWORD.data);
        click.moveToElementAndClick(wait.waitForElementPresent(paypalLoginButton));
        waitForPaypalSpinners();
        waitForPageLoad();
        VertexLogger.log("Successfully logged in to PayPal with Dummy user");
    }

    /**
     * Compares the order subtotal of MagentoTap checkout page & PayPal page
     * Please use Order total (Incl. of Item price, Tax, Shipping etc...)
     *
     * @param subtotal subtotal of MagentoTap
     * @return true or false based on condition match.
     */
    private boolean compareSubtotalOnPaypal(double subtotal) {
        waitForPageLoad();
        waitForPaypalSpinners();
        wait.waitForElementPresent(paypalCustomerDetailDialog);
        double paypalSubtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalOnPaypal)).replace("€", ""));
        double formattedPaypalSubtotal = Double.parseDouble(String.format("%2f", paypalSubtotal));
        VertexLogger.log("Magento Subtotal = " + subtotal);
        VertexLogger.log("Paypal Subtotal = " + formattedPaypalSubtotal);
        return formattedPaypalSubtotal == subtotal;
    }

    /**
     * Compares the customer's name of MagentoTap checkout page & PayPal page
     *
     * @param customerName Name of customer
     * @return true or false based on condition match.
     */
    private boolean compareCustomerNameOnPaypal(String customerName) {
        waitForPageLoad();
        waitForPaypalSpinners();
        wait.waitForElementPresent(paypalCustomerDetailDialog);
        String paypalUICustomerName = text.getElementText(wait.waitForElementPresent(customerNameOnPaypal));
        VertexLogger.log("Customer name from Magento: " + customerName);
        VertexLogger.log("Customer name on PayPal: " + paypalUICustomerName);
        return paypalUICustomerName.contains(customerName);
    }

    /**
     * Compares the customer's address of MagentoTap checkout page & PayPal page
     * Please use address line, City & Postal code
     *
     * @param address Customer Address from the Magento
     * @return true or false based on condition match.
     */
    private boolean compareAddressOnPaypal(String... address) {
        if (address.length != 3) {
            Assert.fail();
        }
        waitForPageLoad();
        waitForPaypalSpinners();
        wait.waitForElementPresent(paypalCustomerDetailDialog);
        String paypalUIAddress = text.getElementText(wait.waitForElementPresent(customerAddressOnPaypal));
        VertexLogger.log("Magento Address = " + Arrays.toString(address));
        VertexLogger.log("PayPal address = " + paypalUIAddress);
        return paypalUIAddress.contains(address[0]) && paypalUIAddress.contains(address[1]) && paypalUIAddress.contains(address[2]);
    }

    /**
     * Select PayPal payment method, Login to PayPal with dummy user & complete the purchase order
     * Please use address line, City & Postal code
     * Please use Order total (Incl. of Item price, Tax, Shipping etc...)
     *
     * @param isOnlyVirtualProductOrdered true if only Virtual product is ordered & false for Physical product or Virtual + Physical product combination
     * @param customerName                Name of the customer
     * @param subtotal                    Subtotal amount of order from MagentoTap checkout page
     * @param address                     Address of customer which is used as shipping address
     * @return true or false based on condition match
     */
    public boolean verifyDetailsOnPaypalAndPayOrderWithPaypal(boolean isOnlyVirtualProductOrdered, String customerName, double subtotal, String... address) {
        String magentoWindow = window.getCurrentWindowHandle();
        boolean isMatched = false;
        selectPayPalPaymentMethod();
        Set<String> openedWindows = window.getWindowHandles();
        for (String paypalWindow : openedWindows) {
            if (!paypalWindow.equalsIgnoreCase(magentoWindow)) {
                driver.switchTo().window(paypalWindow);
                VertexLogger.log("Switched to PayPal window");
                break;
            }
        }
        loginToPaypalWithDummyUser();
        if (isOnlyVirtualProductOrdered) {
            VertexLogger.log("Only virtual product ordered by customer, validating only Subtotal on PayPal");
            if (compareSubtotalOnPaypal(subtotal)) {
                isMatched = true;
                VertexLogger.log("Subtotals of Magento & PayPal are matched");
            }
        } else {
            VertexLogger.log("Any of physical product is ordered in the order, validating all details");
            if (compareSubtotalOnPaypal(subtotal) && compareAddressOnPaypal(address) && compareCustomerNameOnPaypal(customerName)) {
                isMatched = true;
                VertexLogger.log("Customer's name, Subtotal & Address on Magento & PayPal are matched");
            }
        }
        click.javascriptClick(wait.waitForElementPresent(paypalCompletePurchaseButton));
        VertexLogger.log("Completed the purchase order from PayPal.");
        waitForPaypalSpinners();
        waitForPageLoad();
        driver.switchTo().window(magentoWindow);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(paypalCheckoutModal));
        VertexLogger.log("Switched to Magento Window");
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        return isMatched;
    }
}