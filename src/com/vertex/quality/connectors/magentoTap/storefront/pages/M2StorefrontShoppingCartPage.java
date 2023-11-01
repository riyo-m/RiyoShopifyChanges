package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Shopping Cart Page
 *
 * @author alewis
 */
public class M2StorefrontShoppingCartPage extends MagentoStorefrontPage {
    protected By itemInfoClass = By.className("item-info");
    protected By subtotalClass = By.className("subtotal");
    protected By shippingClass = By.className("shipping");
    protected By includingTaxClass = By.className("price-including-tax");
    protected By excludingTaxClass = By.className("price-excluding-tax");
    protected By cartPriceClass = By.className("cart-price");
    protected By priceClass = By.className("price");

    protected By shippingTaxID = By.id("block-shipping-heading");

    protected By shippingSelectorID = By.id("s_method_flatrate_flatrate");
    protected By freeShippingSelectorID = By.id("s_method_freeshipping_freeshipping");

    protected By checkoutContainerClass = By.className("checkout-methods-items");
    protected By itemClass = By.className("item");
    protected By checkoutClass = By.className("checkout");
    protected String checkoutString = "Proceed to Checkout";
    protected By multipleAddressCheckoutClass = By.className("multicheckout");

    protected By countryDropDown = By.name("country_id");
    protected By stateDropDown = By.name("region_id");
    protected By postcodeField = By.name("postcode");

    protected By taxBlindClass = By.className("totals-tax-summary");

    protected By cartClass = By.id("cart-totals");
    protected By tableWrapperClass = By.className("table-wrapper");
    protected By dataClass = By.className("data");
    protected By subClass = By.className("sub");
    protected By grandClass = By.className("grand");
    protected By markClass = By.className("mark");
    protected By amountClass = By.className("amount");
    protected By subPriceClass = By.className("price");

    protected By totalTaxDetailsClass = By.className("totals-tax-details");
    protected By deleteButtonClass = By.className("action-delete");

    protected String inclSubtotalTaxString = "Subtotal (Incl. Tax)";
    protected String exclSubtotalTaxString = "Subtotal (Excl. Tax)";
    protected String inclTaxString = "Order Total Incl. Tax";
    protected String exclTaxString = "Order Total Excl. Tax";
    protected String exclTaxShippingString = "Shipping Excl. Tax (Flat Rate - Fixed)";
    protected String inclTaxShippingString = "Shipping Incl. Tax (Flat Rate - Fixed)";
    protected String salesAndUseString = "Sales and Use (6%)";
    protected String shippingTaxString = "Shipping (6%)";
    protected By salesUseClass = By.className("details-1");
    protected By summaryDetails = By.xpath("//div[@class='table-wrapper']");
    By maskClass = By.className("loading-mask");
    By popupLogin = By.className("block-authentication");
    By closePopUpLogin = By.xpath("//div[@class='modal-inner-wrap']//*//button[@data-role='closeBtn']");

    protected By radioButton = By.className("radio");

    protected By specifyOptionsClass = By.className("configure");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate excluding tax
     */
    public String getExcludingTaxSubtotalFirstItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(0);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement excludingTax = subtotal.findElement(excludingTaxClass);
        WebElement cartPriceExcluding = excludingTax.findElement(cartPriceClass);
        WebElement excludingPrice = cartPriceExcluding.findElement(priceClass);

        String price = excludingPrice.getText();

        return price;
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate including tax
     */
    public String getIncludingTaxSubtotalFirstItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(0);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement includingTax = subtotal.findElement(includingTaxClass);
        WebElement cartPriceIncluding = includingTax.findElement(cartPriceClass);
        WebElement includingPrice = cartPriceIncluding.findElement(priceClass);

        String price = includingPrice.getText();

        return price;
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate excluding tax
     */
    public String getExcludingTaxSubtotalSecondItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(1);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement excludingTax = subtotal.findElement(excludingTaxClass);
        WebElement cartPriceExcluding = excludingTax.findElement(cartPriceClass);
        WebElement excludingPrice = cartPriceExcluding.findElement(priceClass);

        String price = excludingPrice.getText();

        return price;
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate including tax
     */
    public String getIncludingTaxSubtotalSecondItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(1);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement includingTax = subtotal.findElement(includingTaxClass);
        WebElement cartPriceIncluding = includingTax.findElement(cartPriceClass);
        WebElement includingPrice = cartPriceIncluding.findElement(priceClass);

        String price = includingPrice.getText();

        return price;
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate excluding tax
     */
    public String getExcludingTaxSubtotalThirdItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(2);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement excludingTax = subtotal.findElement(excludingTaxClass);
        WebElement cartPriceExcluding = excludingTax.findElement(cartPriceClass);
        WebElement excludingPrice = cartPriceExcluding.findElement(priceClass);

        String price = excludingPrice.getText();

        return price;
    }

    /**
     * Gets the Subtotal price of the product displayed before estimate shipping and tax info
     * is entered in the 'Item' section
     *
     * @return a string of the subtotal estimate including tax
     */
    public String getIncludingTaxSubtotalThirdItem() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> itemInfos = wait.waitForAllElementsPresent(itemInfoClass);
        WebElement itemInfo = itemInfos.get(2);
        WebElement subtotal = itemInfo.findElement(subtotalClass);
        WebElement includingTax = subtotal.findElement(includingTaxClass);
        WebElement cartPriceIncluding = includingTax.findElement(cartPriceClass);
        WebElement includingPrice = cartPriceIncluding.findElement(priceClass);

        String price = includingPrice.getText();

        return price;
    }

    /**
     * clicks and opens or closes the Estimate Shipping and Tax blind
     */
    public void clickEstimateShippingTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement shippingTax = wait.waitForElementDisplayed(shippingTaxID);
        click.clickElementCarefully(shippingTax);
        wait.waitForElementNotDisplayed(maskClass);
    }

    /**
     * verifies the login popup is displaying or not
     */
    public boolean verifyPopUpLogin() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement loginPopup = wait.waitForElementPresent(popupLogin);
        boolean verifyLogin = loginPopup.isDisplayed();
        return verifyLogin;
    }

    /**
     * click close Popup login  button
     */
    public void closePopUpLogin() {
        WebElement closeLoginPopupButton = wait.waitForElementDisplayed(closePopUpLogin);
        click.clickElement(closeLoginPopupButton);
    }

    /**
     * clicks the drop-down menu and selects a country
     *
     * @param countryName country name
     */
    public void selectCountry(String countryName) {
        dropdown.selectDropdownByValue(countryDropDown, countryName);
    }

    /**
     * clicks the drop-down menu and selects a state/province
     *
     * @param stateName state name
     */
    public void selectState(String stateName) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(stateDropDown);
        dropdown.selectDropdownByValue(stateDropDown, stateName);
        waitForPageLoad();
    }

    /**
     * clicks the Zip Code box and inputs a zipcode
     *
     * @param zipCode Zip code
     */
    public void enterZipCode(String zipCode) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(postcodeField);
        WebElement field = driver.findElement(postcodeField);
        field.clear();
        field.sendKeys(zipCode);
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
    }

    /**
     * clicks the shipping rate
     */
    public void clickShippingRateSelector() {
        wait.waitForElementNotDisplayed(maskClass);
        try {
            waitForPageLoad();
            WebElement shippingSelector = wait.waitForElementPresent(shippingSelectorID);
            click.clickElement(shippingSelector);
            waitForPageLoad();
        } catch (Exception e) {
            //clickEstimateShippingTax();
            waitForPageLoad();
            wait.waitForElementDisplayed(shippingSelectorID);
            WebElement field = wait.waitForElementPresent(shippingSelectorID);
            click.clickElement(field);
            waitForPageLoad();
        }
    }

    /**
     * clicks the free shipping rate
     */
    public void clickFreeShippingRate() {
        wait.waitForElementNotDisplayed(maskClass);
        try {
            waitForPageLoad();
            WebElement shippingSelector = wait.waitForElementPresent(freeShippingSelectorID);
            shippingSelector.click();
        } catch (Exception e) {
            wait.waitForElementDisplayed(freeShippingSelectorID);
            WebElement field = wait.waitForElementPresent(freeShippingSelectorID);
            waitForPageLoad();
            wait.waitForElementEnabled(field);
            field.click();
        }
    }

    /**
     * opens the tax blind if closed
     */
    public void openTaxBlind() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement taxBlindDisplayed;
        WebElement amount;
        try {
            taxBlindDisplayed = wait.waitForElementDisplayed(taxBlindClass, 5);
            amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed, 5);
        } catch (Exception e) {
            wait.waitForElementNotDisplayed(maskClass);
            taxBlindDisplayed = wait.waitForElementDisplayed(taxBlindClass, 5);
            amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed, 5);
        }

        try {
            attribute.waitForElementAttributeChange(amount, "innerHTML", 10);
        } catch (Exception e) {
            System.out.println("Couldn't do");
        }
        waitForPageLoad();
        scroll.scrollElementIntoView(amount);
        jsWaiter.sleep(5000);
        click.clickElementCarefully(amount);
    }

    /**
     * Clicks the 'Proceed to Checkout' Button
     *
     * @return the Shipping Info Page
     */
    public M2StorefrontShippingInfoPage clickProceedToCheckout() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(summaryDetails);
        scroll.scrollElementIntoView(checkoutContainerClass);
        WebElement checkoutContainer = wait.waitForElementDisplayed(checkoutContainerClass);
        WebElement checkoutButton = wait.waitForElementDisplayed(By.tagName("button"), checkoutContainer);
        click.clickElement(checkoutButton);
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        return initializePageObject(M2StorefrontShippingInfoPage.class);
    }

    /**
     * gets the subtotal price including tax from the summary section before
     * shipping is added
     *
     * @return a string of subtotal price of the item
     */
    public String getSubtotalIncludingTaxInSummary() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement checkoutItem = wait.waitForElementPresent(cartClass);
        WebElement tableWrapper = wait.waitForElementPresent(tableWrapperClass, checkoutItem);
        WebElement data = wait.waitForElementPresent(dataClass, tableWrapper);
        waitForPageLoad();
        List<WebElement> subtotals = wait.waitForAllElementsPresent(subClass, data);
        String price = null;

        for (WebElement subtotal : subtotals) {
            waitForPageLoad();
            WebElement mark = wait.waitForElementEnabled(markClass, subtotal);
            String label = mark.getText();

            if (inclSubtotalTaxString.equals(label)) {
                waitForPageLoad();
                WebElement amount = wait.waitForElementPresent(amountClass, subtotal);
                WebElement grandPrice = wait.waitForElementEnabled(subPriceClass, amount);
                price = grandPrice.getText();
                waitForPageLoad();
            }
        }
        return price;
    }

    /**
     * gets the subtotal price excluding tax from the summary section before
     * shipping is added
     *
     * @return a string of subtotal price of the item
     */
    public String getSubtotalExcludingTaxInSummary() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement checkoutItem = wait.waitForElementPresent(cartClass);
        WebElement tableWrapper = wait.waitForElementPresent(tableWrapperClass, checkoutItem);
        waitForPageLoad();
        WebElement data = wait.waitForElementPresent(dataClass, tableWrapper);
        waitForPageLoad();
        List<WebElement> subtotals = wait.waitForAllElementsPresent(subClass, data);
        String price = null;

        for (WebElement subtotal : subtotals) {
            WebElement mark = wait.waitForElementEnabled(markClass, subtotal);
            String label = mark.getText();

            if (exclSubtotalTaxString.equals(label)) {
                waitForPageLoad();
                WebElement amount = wait.waitForElementPresent(amountClass, subtotal);
                WebElement grandPrice = wait.waitForElementEnabled(subPriceClass, amount);
                price = grandPrice.getText();
            }
        }
        return price;
    }

    /**
     * Gets the price of shipping for the product displayed before tax is applied
     *
     * @return a string of the price estimate of shipping excluding tax
     */
    public String getShippingExclTaxInSummary() {
        wait.waitForElementNotDisplayed(maskClass);
        String price = null;
        WebElement checkoutItem = wait.waitForElementPresent(cartClass);
        waitForPageLoad();
        WebElement tableWrapper = wait.waitForElementPresent(tableWrapperClass, checkoutItem);
        waitForPageLoad();
        WebElement data = wait.waitForElementPresent(dataClass, tableWrapper);
        waitForPageLoad();
        List<WebElement> shippingRates = wait.waitForAllElementsPresent(shippingClass, data);
        waitForPageLoad();
        WebElement shippingRate = element.selectElementByNestedLabel(shippingRates, markClass, exclTaxShippingString);
        if (shippingRate != null) {
            waitForPageLoad();
            WebElement amount = wait.waitForElementDisplayed(amountClass, shippingRate);
            price = text.getElementText(amount);
        }

        return price;
    }

    /**
     * Gets the price of shipping for the product displayed after tax is applied
     *
     * @return a string of the price estimate of shipping including tax
     */
    public String getShippingInclTaxInSummary() {
        waitForPageLoad();
        WebElement checkoutItem = wait.waitForElementPresent(cartClass);
        WebElement tableWrapper = wait.waitForElementPresent(tableWrapperClass, checkoutItem);
        waitForPageLoad();
        WebElement data = wait.waitForElementPresent(dataClass, tableWrapper);
        waitForPageLoad();
        List<WebElement> shippingRates = wait.waitForAllElementsPresent(shippingClass, data);
        String price = null;
        waitForPageLoad();
        WebElement shippingRate = element.selectElementByNestedLabel(shippingRates, markClass, inclTaxShippingString);
        if (shippingRate != null) {
            waitForPageLoad();
            WebElement amount = wait.waitForElementDisplayed(amountClass, shippingRate);
            price = text.getElementText(amount);
        }

        return price;
    }

    /**
     * Gets the total tax applied for the order in the summary section
     *
     * @return a string of the total tax of the order
     */
    public String getTaxBlindTotal() {
        wait.waitForElementNotDisplayed(maskClass);
        WebElement taxBlind = wait.waitForElementPresent(taxBlindClass);
        WebElement price = wait.waitForElementPresent(priceClass, taxBlind);
        String priceText = price.getText();

        return priceText;
    }

    /**
     * Gets the Sales and Use tax applied in the tax blind in the summary section
     *
     * @return a string of the Sales and Use tax
     */
    public String getSalesAndUseTax() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        List<WebElement> totalTaxDetails = wait.waitForAllElementsPresent(totalTaxDetailsClass);
        String price = null;
        waitForPageLoad();
        WebElement totalTaxDetail = element.selectElementByNestedLabel(totalTaxDetails, markClass, salesAndUseString);
        if (totalTaxDetail != null) {
            WebElement amount = wait.waitForElementDisplayed(amountClass, totalTaxDetail);
            price = text.getElementText(amount);
        }
        return price;
    }

    /**
     * Gets the Shipping tax applied in the tax blind in the summary section
     *
     * @return a string of the Shipping tax
     */
    public String getShippingTaxInBlind() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        List<WebElement> totalTaxDetails = wait.waitForAllElementsPresent(totalTaxDetailsClass);
        String price = null;

        WebElement totalTaxDetail = element.selectElementByNestedLabel(totalTaxDetails, markClass, shippingTaxString);
        if (totalTaxDetail != null) {
            WebElement amount = wait.waitForElementDisplayed(amountClass, totalTaxDetail);
            price = text.getElementText(amount);
        }
        return price;
    }

    /**
     * gets the total price including tax from the summary section
     *
     * @return a string of total price of the item
     */
    public String getTotalIncludingTaxInSummary() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        List<WebElement> grandTotals = wait.waitForAllElementsDisplayed(grandClass);
        String price = null;

        for (WebElement grandTotal : grandTotals) {
            WebElement mark = grandTotal.findElement(markClass);
            String label = mark.getText();

            if (inclTaxString.equals(label)) {
                WebElement amount = grandTotal.findElement(amountClass);
                WebElement grandPrice = amount.findElement(subPriceClass);
                price = grandPrice.getText();
            }
        }
        return price;
    }

    /**
     * gets the total price excluding tax from the summary section
     *
     * @return a string of total price excluding tax of the item
     */
    public String getTotalExcludingTaxInSummary() {
        wait.waitForElementNotDisplayed(maskClass);
        String price = null;
        waitForPageLoad();
        List<WebElement> grandTotals = wait.waitForAllElementsDisplayed(grandClass);
        WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, exclTaxString);
        if (grandTotal != null) {
            WebElement amount = element.getWebElement(amountClass, grandTotal);
            WebElement grandPrice = element.getWebElement(subPriceClass, amount);
            price = text.getElementText(grandPrice);
        }
        return price;
    }

    /**
     * deletes the items in the shopping cart
     */
    public void deleteItems() {
        wait.waitForElementNotDisplayed(maskClass);
        WebElement deleteButton = wait.waitForElementPresent(deleteButtonClass);
        deleteButton.click();
        waitForPageLoad();
        WebElement deleteButton2 = wait.waitForElementPresent(deleteButtonClass);
        deleteButton2.click();
    }

    /**
     * clicks Specify Options link
     *
     * @return product details page
     */
    public M2StorefrontProductDetailsPage clickSpecifyOptions() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement link = wait.waitForElementEnabled(specifyOptionsClass);

        click.clickElementCarefully(link);

        M2StorefrontProductDetailsPage productDetailsPage = initializePageObject(M2StorefrontProductDetailsPage.class);

        return productDetailsPage;
    }

    /**
     * clicks Checkout With Multiple Addresses button,
     * while not currently logged in
     *
     * @return login page
     */
    public M2StorefrontLoginPage clickCheckoutWithMultipleAddressesWhileLoggedOut() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement checkoutButton = wait.waitForElementEnabled(multipleAddressCheckoutClass);

        checkoutButton.click();

        M2StorefrontLoginPage loginPage = initializePageObject(M2StorefrontLoginPage.class);

        return loginPage;
    }
}