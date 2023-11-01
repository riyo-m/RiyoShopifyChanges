package com.vertex.quality.connectors.woocommerceTap.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.woocommerceTap.enums.WooCommerceData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Homepage of WooCommerce Admin.
 *
 * @author Vivek.Kumar
 */
public class WooCommerceAdminHomePage extends VertexPage {
    public WooCommerceAdminHomePage(WebDriver driver) {
        super(driver);
    }

    protected By userAvatar = By.xpath(".//a[text()='Howdy, ']//img[contains(@class,'avatar')]");
    protected By userAvatarLogoutOption = By.xpath(".//a[text()='Howdy, ']/following-sibling::div//li[normalize-space(.)='Log Out']");
    protected By vertexIcon = By.xpath("//div[contains(text(),'Vertex')]");
    protected By vertexConnectionTest = By.xpath("//a[@class='vertex-btn vertex-btn--small vertex-btn--white']");
    protected By healthCheck = By.xpath("//span[contains(text(),'Vertex Health Check')]");
    protected By healthCheckStatus = By.xpath("//p[contains(text(),'Vertex O Series')]");
    protected By taxEndpoint = By.xpath("//input[@name='vertex-connector-endpoint']");
    protected By addressEndpoint = By.xpath("//input[@name='vertex-connector-address_endpoint']");
    protected By saveButton = By.xpath("//input[@id='submit']");
    protected By WooCommerceButton = By.xpath("//div[contains(text(),'WooCommerce')]");
    protected By settingsButton = By.xpath("//a[@href='admin.php?page=wc-settings']");
    protected By addressLine1 = By.xpath("//input[@name='woocommerce_store_address']");
    protected By addressCity = By.xpath("//input[@name='woocommerce_store_city']");
    protected By addressZip = By.xpath("//input[@name='woocommerce_store_postcode']");
    protected By addressZipLabel = By.xpath(".//label[text()='Postcode / ZIP ']");
    protected By countryField = By.xpath("//*[@name='woocommerce_default_country']");
    protected By saveChanges = By.xpath("//button[@value='Save changes']");
    protected By taxCheckbox = By.xpath("//input[@name='woocommerce_calc_taxes']");
    protected By orderButton = By.xpath("//a[@href='edit.php?post_type=shop_order']");
    protected By addOrderButton = By.xpath("//a[contains(text(),'Add order')]");
    protected By editShippingAddressButton = By.xpath("//h3[contains(text(),'Shipping')]//a[@class='edit_address']");
    protected By editBillingAddressButton = By.xpath("//h3[contains(text(),'Billing')]//a[@class='edit_address']");
    protected By shippingAddressLine1 = By.xpath("//input[@id='_shipping_address_1']");
    protected By billingAddressLine1 = By.xpath("//input[@id='_billing_address_1']");
    protected By shipToCity = By.xpath("//input[@id='_shipping_city']");
    protected By billingCity = By.xpath("//input[@id='_billing_city']");
    protected By shippingToState = By.xpath("//span[@id='select2-_shipping_state-container']");
    protected By billingState = By.xpath("//span[@id='select2-_billing_state-container']");
    protected By shipToPostalCode = By.xpath("//input[@id='_shipping_postcode']");
    protected By billingPostalCode = By.xpath("//input[@id='_billing_postcode']");
    protected By stateInputField = By.xpath("//input[@aria-owns='select2-_shipping_state-results']");
    protected By billingInputField = By.xpath("//input[@aria-owns='select2-_billing_state-results']");
    protected By users = By.xpath("//div[contains(text(),'Users')]");
    protected By userList = By.xpath("//a[contains(text(),'siteadmin')]");
    protected By customerCode = By.xpath("//input[@id='customer_code']");
    protected By customerClass = By.xpath("//input[@id='customer_class']");
    protected By updateProfile = By.xpath("//input[@id='submit']");
    protected By productButton = By.xpath("//div[contains(text(),'Products')]");
    protected By addNewButton = By.xpath("//a[@class='page-title-action' and @href='http://mercury-php.vertexconnectors.com/wordpress-test/wp-admin/post-new.php?post_type=product']");
    protected By productNameCode = By.xpath("//input[@id='title']");
    protected By regularPrice = By.xpath("//input[@id='_regular_price']");
    protected By salePrice = By.xpath("//input[@id='_sale_price']");
    protected By taxStatus = By.xpath("//select[@id='_tax_status']");
    protected By taxClass = By.xpath("//select[@id='_tax_class']");
    protected By publish = By.xpath("//input[@id='publish']");
    protected By vertexTestLink = By.xpath(".//a[text()='Vertex-test']");
    protected By visitStoreLink = By.id("wp-admin-bar-view-store");
    protected By billingCountry = By.id("_billing_country");
    protected By shippingCountry = By.id("_shipping_country");
    protected By billingZip = By.xpath("//input[@id='_billing_postcode']");
    protected By searchOrderBox = By.xpath(".//input[@id='post-search-input']");
    protected By searchOrderButton = By.id("search-submit");
    protected By refundLink = By.xpath(".//button[text()='Refund']");
    protected By allItems = By.xpath(".//*[@class='item ']");
    protected By refundManuallyButton = By.className("wc-order-refund-amount");
    protected By totalRefund = By.xpath(".//td[@class='total refunded-total']");
    protected By getShippingAmountUI = By.xpath(".//tr[@class='shipping ']//span[@class='woocommerce-Price-amount amount']");
    protected By shippingRefundBox = By.xpath(".//tr[@class='shipping ']//div[@class='refund']//input");
    protected By refundedShippingAmount = By.xpath(".//tr[@class='shipping ']//small");
    protected String orderNoLocator = ".//a[@class='order-view']//strong[contains(text(),'<<text_replace>>')]";
    protected String refundItemQuantityBox = ".//*[@class='item ']//td[@data-sort-value='<<text_replace>>']/following-sibling::td//div[@class='refund']//input[@type='number']";
    protected String itemCost = ".//*[@class='item ']//td[@data-sort-value='<<text_replace>>']/following-sibling::td[@class='item_cost']";
    protected String itemQuantities = ".//*[@class='item ']//td[@data-sort-value='<<text_replace>>']/following-sibling::td[@class='quantity']";
    protected String refundedItemsQuantity = ".//*[@class='item ']//td[@data-sort-value='<<text_replace>>']/following-sibling::td[@class='quantity']//small[@class='refunded']";
    protected String refundedItemsAmount = ".//*[@class='item ']//td[@data-sort-value='<<text_replace>>']/following-sibling::td[@class='line_cost']//small";

    /**
     * Search particular order number and click on order to edit order
     * example: wooCommerceAdminHomePage.searchAndEditOrder("99");
     *
     * @param orderNo pass order number which order to be edited
     */
    public void searchAndEditOrder(String orderNo) {
        waitForPageLoad();
        wait.waitForElementPresent(searchOrderBox);
        text.enterText(searchOrderBox, orderNo);
        wait.waitForElementPresent(searchOrderButton);
        click.moveToElementAndClick(searchOrderButton);
        waitForPageLoad();
        WebElement order = wait.waitForElementPresent(By.xpath(orderNoLocator.replace("<<text_replace>>", orderNo)));
        click.moveToElementAndClick(order);
        waitForPageLoad();
    }

    /**
     * This method will be used in refunding partial or full refund from admin panel
     * example: wooCommerceAdminHomePage.refundOrder(WooData.PRODUCTS_TWO_HOODIES_BELT.data, WooData.QUANTITIES_FIVE_ONE_TWO.data);
     *
     * @param items      pass items which is to be refunded
     * @param quantities pass quantities for items which should be refunded
     */
    public void refundOrder(String[] items, String[] quantities) {
        if (items.length != quantities.length) {
            Assert.fail("items & quantities must have same number of parameters");
        }
        waitForPageLoad();
        wait.waitForElementPresent(refundLink);
        click.moveToElementAndClick(refundLink);
        wait.waitForElementPresent(refundManuallyButton);
        for (int i = 0; i < items.length; i++) {
            text.enterText(By.xpath(refundItemQuantityBox.replace("<<text_replace>>", items[i])), quantities[i]);
            text.pressTab(By.xpath(refundItemQuantityBox.replace("<<text_replace>>", items[i])));
        }
        wait.waitForElementPresent(shippingRefundBox);
        text.enterText(shippingRefundBox, text.getElementText(getShippingAmountUI).replace("$", ""));
        text.pressTab(shippingRefundBox);
        click.moveToElementAndClick(refundManuallyButton);
        alert.waitForAlertPresent(1);
        alert.acceptAlert();
        waitForPageLoad();
    }

    /**
     * verify refunded item's amount based on item's quantity
     * example: wooCommerceAdminHomePage.verifyRefundedItem("Belt");
     *
     * @param item pass item which refunded amount should be verified
     * @return true or false based on condition
     */
    public boolean verifyRefundedItemAmount(String item) {
        double refundedQuantities = Double.parseDouble(getRefundedItemQuantities(item));
        double basePrice = Double.parseDouble(getItemBasePrice(item));
        double refundedPrice = Double.parseDouble(getRefundedItemAmount(item));
        return refundedPrice == (basePrice * refundedQuantities);
    }

    /**
     * verify refunded shipping amount
     * example: wooCommerceAdminHomePage.refundedShippingAmount("30.00");
     *
     * @param shippingAmount pass shipping amount which is expected to be refunded
     * @return compares expected (parameter) & actual (from UI) and returns true or false based on condition match
     */
    public boolean refundedShippingAmount(String shippingAmount) {
        waitForPageLoad();
        return shippingAmount.equals(text.getElementText(refundedShippingAmount).replace("-$", ""));
    }

    /**
     * verifies total refunded amount with the summation of individual refunded items
     * example: wooCommerceAdminHomePage.verifyAllRefundedItems(WooData.PRODUCTS_TWO_HOODIES_BELT.data);
     *
     * @param items pass all items which are refunded
     * @return true or false based on condition
     */
    public boolean verifyAllRefundedItems(String... items) {
        double refundedPriceOfItems = 0;
        for (String item : items) {
            refundedPriceOfItems = refundedPriceOfItems + Double.parseDouble(getRefundedItemAmount(item));
        }
        double shipping = Double.parseDouble(text.getElementText(getShippingAmountUI).replace("$", ""));
        double totalRefundAmount = Double.parseDouble(getTotalRefundedAmount());
        return totalRefundAmount == (refundedPriceOfItems + shipping);
    }

    /**
     * Gets total refunded amount
     * wooCommerceAdminHomePage.getTotalRefundedAmount();
     *
     * @return total refund
     */
    public String getTotalRefundedAmount() {
        waitForPageLoad();
        return String.format("%.2f", Double.parseDouble(text.getElementText(totalRefund).replace("-$", "")));
    }

    /**
     * get ordered items' price
     * example: wooCommerceAdminHomePage.getRefundedItemAmount("Belt");
     *
     * @param itemName pass item which base price to be fetched
     * @return price of an item
     */
    public String getRefundedItemAmount(String itemName) {
        waitForPageLoad();
        return String.format("%.2f", Double.parseDouble(text.getElementText(By.xpath(refundedItemsAmount.replace("<<text_replace>>", itemName))).replace("-$", "")));
    }

    /**
     * get ordered items' quantities
     * example: wooCommerceAdminHomePage.getRefundedItemQuantities("Belt");
     *
     * @param itemName pass item which base price to be fetched
     * @return quantities of an item
     */
    public String getRefundedItemQuantities(String itemName) {
        waitForPageLoad();
        return String.format("%.2f", Double.parseDouble(text.getElementText(By.xpath(refundedItemsQuantity.replace("<<text_replace>>", itemName))).replace("-", ""))).replace(".00", "");
    }

    /**
     * get ordered items base price (excluded no of quantities & applied tax & discounts if any)
     * example: wooCommerceAdminHomePage.getItemBasePrice("Belt");
     *
     * @param itemName pass item which base price to be fetched
     * @return base price of an item
     */
    public String getItemBasePrice(String itemName) {
        waitForPageLoad();
        return String.format("%.2f", Double.parseDouble(text.getElementText(By.xpath(itemCost.replace("<<text_replace>>", itemName))).replace("$", "")));
    }

    /**
     * select country in billing address
     *
     * @param country value of country
     */
    public void selectBillingCountry(String country) {
        wait.waitForElementPresent(billingCountry);
        dropdown.selectDropdownByDisplayName(billingCountry, country);
    }

    /**
     * select country in shipping address
     *
     * @param country value of country
     */
    public void selectShippingCountry(String country) {
        wait.waitForElementPresent(shippingCountry);
        dropdown.selectDropdownByDisplayName(shippingCountry, country);
    }

    /**
     * Navigate to Store Front from Admin panel
     */
    public void goToStoreFromAdmin() {
        WebElement vertexTest = wait.waitForElementEnabled(vertexTestLink);
        hover.hoverOverElement(vertexTest);
        WebElement store = wait.waitForElementEnabled(visitStoreLink);
        click.moveToElementAndClick(store);
    }

    /**
     * clicks the vertex health Check Icon for the WooCommerce connector.
     */
    public void clickVertexHealthCheck() {
        WebElement healthCheckIcon = wait.waitForElementDisplayed(healthCheck);
        click.clickElementCarefully(healthCheckIcon);
    }

    /**
     * clicks the publish Button for the WooCommerce connector.
     */
    public void clickPublishButton() {
        WebElement publishIcon = wait.waitForElementDisplayed(publish);
        click.clickElementCarefully(publishIcon);
    }

    /**
     * clicks the product button for the WooCommerce connector.
     */
    public void clickProductButton() {
        WebElement productIcon = wait.waitForElementPresent(productButton);
        click.javascriptClick(productIcon);
    }

    /**
     * clicks the add new button for the WooCommerce connector.
     */
    public void clickAddNewButton() {
        WebElement addNewIcon = wait.waitForElementDisplayed(addNewButton);
        click.clickElementCarefully(addNewIcon);
    }

    /**
     * clicks the users Button for the WooCommerce connector.
     */
    public void clickUsersButton() {
        WebElement usersIcon = wait.waitForElementPresent(users);
        click.javascriptClick(usersIcon);
    }

    /**
     * clicks the users list for the WooCommerce connector.
     */
    public void clickUsersList() {
        WebElement usersListIcon = wait.waitForElementDisplayed(userList);
        click.clickElementCarefully(usersListIcon);
    }

    /**
     * clicks the update profile for the WooCommerce connector.
     */
    public void clickUpdateProfile() {
        WebElement updateProfileIcon = wait.waitForElementDisplayed(updateProfile);
        click.clickElementCarefully(updateProfileIcon);
    }

    /**
     * clicks the edit ship To Address Button.
     */
    public void clickEditShippingAddressButton() {
        WebElement clickShipToAddressButton = wait.waitForElementDisplayed(editShippingAddressButton);
        click.javascriptClick(clickShipToAddressButton);
    }

    /**
     * clicks the edit Billing Address Button.
     */
    public void clickEditBillingAddressButton() {
        WebElement clickBillingAddressButton = wait.waitForElementDisplayed(editBillingAddressButton);
        click.clickElementCarefully(clickBillingAddressButton);
    }

    /**
     * clicks the order Button Icon for the WooCommerce connector.
     */
    public void clickOrderButton() {
        WebElement orderIcon = wait.waitForElementPresent(orderButton);
        click.javascriptClick(orderIcon);
    }

    /**
     * clicks the add order Button Icon for the WooCommerce connector.
     */
    public void clickAddOrderButton() {
        WebElement addOrderIcon = wait.waitForElementDisplayed(addOrderButton);
        click.javascriptClick(addOrderIcon);
    }

    /**
     * clicks the tax checkbox for the WooCommerce connector.
     */
    public void clickTaxCheckBox() {
        WebElement taxCheckField = wait.waitForElementDisplayed(taxCheckbox);
        click.clickElementCarefully(taxCheckField);
    }

    /**
     * Check or Un-Check Tax calculation check box
     * example1: wooCommerceAdminHomePage.enableDisableTaxCalc(true); // To enable Tax calculation
     * example2: wooCommerceAdminHomePage.enableDisableTaxCalc(false); // To disable Tax calculation
     *
     * @param enable To check, enable=true and To un-check enable=false
     */
    public void enableDisableTaxCalc(boolean enable) {
        WebElement taxCheckField = wait.waitForElementDisplayed(taxCheckbox);
        if (enable && !taxCheckField.isSelected()) {
            click.clickElementCarefully(taxCheckField);
        } else if (!enable && taxCheckField.isSelected()) {
            click.clickElementCarefully(taxCheckField);
        }
    }

    /**
     * Check or Un-Check Tax calculation check box
     * example1: wooCommerceAdminHomePage.enableDisableTaxCalculation(true); // To enable Tax calculation
     * example2: wooCommerceAdminHomePage.enableDisableTaxCalculation(false); // To disable Tax calculation
     *
     * @param enable To check, enable=true and To un-check enable=false
     */
    public void enableDisableTaxCalculation(boolean enable) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.clickWooCommerceSetting();
        homePage.enableDisableTaxCalc(enable);
        homePage.clickSaveChangesButton();
    }

    /**
     * clicks the save changes Button for saving ship from address for the WooCommerce connector.
     */
    public void clickSaveChangesButton() {
        WebElement saveAddress = wait.waitForElementDisplayed(saveChanges);
        click.clickElementCarefully(saveAddress);
    }

    /**
     * clicks the countryFiled for setting ship from country of WooCommerce connector.
     */
    public void clickCountryField() {
        WebElement countryDropdown = wait.waitForElementPresent(countryField);
        click.javascriptClick(countryDropdown);
    }

    /**
     * clicks the WooCommerceButton on WooCommerce connector home page.
     */
    public void clickWooCommerceIcon() {
        WebElement wooCommerceIcon = wait.waitForElementPresent(WooCommerceButton);
        click.javascriptClick(wooCommerceIcon);
    }

    /**
     * clicks the settingsButton on WooCommerce connector home page.
     */
    public void clickSettingsButton() {
        WebElement settingIcon = wait.waitForElementPresent(settingsButton);
        click.javascriptClick(settingIcon);
    }

    /**
     * clicks the shipTo address on WooCommerce connector home page.
     */
    public void clickShipToAddressButton() {
        clickWooCommerceIcon();
        clickOrderButton();
        clickAddOrderButton();
        clickEditShippingAddressButton();
    }

    /**
     * clicks the billing address on WooCommerce connector home page.
     */
    public void clickBillingAddressButton() {
        clickWooCommerceIcon();
        clickOrderButton();
        clickAddOrderButton();
        clickEditBillingAddressButton();
    }

    /**
     * clicks the save Button for the WooCommerce connector.
     */
    public void clickSaveButtonField() {
        WebElement saveButtonIcon = wait.waitForElementPresent(saveButton);
        click.clickElementCarefully(saveButtonIcon);
    }

    /**
     * clicks the vertex Icon for the WooCommerce connector.
     */
    public void clickVertexIcon() {
        WebElement vertexIconField = wait.waitForElementPresent(vertexIcon);
        click.javascriptClick(vertexIconField);
    }

    /**
     * clicks the vertex Connection Test for the WooCommerce connector.
     *
     * @return alertMessage health check message.
     */
    public String clickVertexConnectionTest() {
        WebElement vertexConnection = wait.waitForElementDisplayed(vertexConnectionTest);
        click.moveToElementAndClick(vertexConnection);
        String alertMessage = alert.getAlertText();
        return alertMessage;
    }

    /**
     * Checks the successful message in admin page while health check.
     *
     * @return checkHealthCheckMessageField check message field true or false.
     */
    public boolean checkHealthCheckMessage() {
        WebElement healthStatusMessageField = wait.waitForElementDisplayed(healthCheckStatus);
        boolean checkHealthCheckMessageField = healthStatusMessageField.isDisplayed();
        return checkHealthCheckMessageField;
    }

    /**
     * method to change tax calculation url while health check.
     *
     * @param url tax calculation url.
     */
    public void changeTaxUrl(String url) {
        WebElement calculateTaxEndpoint = wait.waitForElementDisplayed(taxEndpoint);
        text.enterText(calculateTaxEndpoint, url);
    }

    /**
     * method to change address url while health check.
     *
     * @param url address cleansing url.
     */
    public void changeAddressUrl(String url) {
        WebElement addressCleansingEndpoint = wait.waitForElementDisplayed(addressEndpoint);
        text.enterText(addressCleansingEndpoint, url);
    }

    /**
     * method to enter address line for ship To address.
     *
     * @param addressLine addressLine1 for setting ship To address.
     */
    public void enterShipToAddressLineOne(String addressLine) {
        WebElement shipToAddressLineInput = wait.waitForElementDisplayed(shippingAddressLine1);
        text.enterText(shipToAddressLineInput, addressLine);
        text.pressTab(shipToAddressLineInput);
    }

    /**
     * method to enter customer code for woocommerce customer.
     *
     * @param customerCd customer code for users.
     */
    public void enterCustomerCode(String customerCd) {
        WebElement customerCodeInput = wait.waitForElementDisplayed(customerCode);
        text.enterText(customerCodeInput, customerCd);
    }

    /**
     * method to enter product name for woocommerce customer.
     *
     * @param productCd product code for users.
     */
    public void enterProductCode(String productCd) {
        WebElement productCodeInput = wait.waitForElementDisplayed(productNameCode);
        text.enterText(productCodeInput, productCd);
    }

    /**
     * method to enter customer class for woocommerce customer.
     *
     * @param customerCls customer class for users.
     */
    public void enterCustomerClass(String customerCls) {
        WebElement customerClassInput = wait.waitForElementDisplayed(customerClass);
        text.enterText(customerClassInput, customerCls);
    }

    /**
     * method to enter product price amount for woocommerce customer.
     *
     * @param productPrice product price for users.
     */
    public void enterProductPriceAmount(String productPrice) {
        WebElement productPriceInput = wait.waitForElementDisplayed(regularPrice);
        text.enterText(productPriceInput, productPrice);
    }

    /**
     * method to enter product sale price for woocommerce customer.
     *
     * @param productSalePrice product sale price for users.
     */
    public void enterProductSalePriceAmount(String productSalePrice) {
        WebElement productSaleInput = wait.waitForElementDisplayed(salePrice);
        text.enterText(productSaleInput, productSalePrice);
    }

    /**
     * method to enter address line for Billing address.
     *
     * @param addressLine addressLine1 for setting Billing address.
     */
    public void enterBillingLineOne(String addressLine) {
        WebElement billingAddressLineInput = wait.waitForElementDisplayed(billingAddressLine1);
        text.enterText(billingAddressLineInput, addressLine);
        text.pressTab(billingAddressLineInput);
    }

    /**
     * method to enter city for ship To address.
     *
     * @param city city for setting Ship To address.
     */
    public void enterShipToCityAddress(String city) {
        WebElement shipToCityInput = wait.waitForElementDisplayed(shipToCity);
        text.enterText(shipToCityInput, city);
        text.pressTab(shipToCityInput);
    }

    /**
     * method to enter city for Billing address.
     *
     * @param city city for setting Billing address.
     */
    public void enterBillingAddressCity(String city) {
        WebElement billingCityInput = wait.waitForElementDisplayed(billingCity);
        text.enterText(billingCityInput, city);
        text.pressTab(billingCityInput);
    }

    /**
     * method to enter postal code for ship To address.
     *
     * @param postal postal for setting ship To address.
     */
    public void enterShipToPostalCode(String postal) {
        WebElement shipToPostalCodeInput = wait.waitForElementDisplayed(shipToPostalCode);
        text.enterText(shipToPostalCodeInput, postal);
        text.pressTab(shipToPostalCodeInput);
    }

    /**
     * method to enter postal code for Billing address.
     *
     * @param postal postal for setting Billing address.
     */
    public void enterBillingPostalCode(String postal) {
        WebElement billingPostalCodeInput = wait.waitForElementDisplayed(billingPostalCode);
        removeBillingPostalCode();
        text.enterText(billingPostalCodeInput, postal, false);
        text.pressTab(billingPostalCodeInput);
    }

    /**
     * Removes postal code
     */
    public void removeBillingPostalCode() {
        WebElement billingPostalCodeInput = wait.waitForElementDisplayed(billingPostalCode);
        cancelAlertMessage();
        text.clearText(billingPostalCodeInput);
        cancelAlertMessage();
        text.pressTab(billingPostalCodeInput);
    }

    /**
     * method to select state for ship To address.
     *
     * @param state state for setting ship To address.
     */
    public void selectShippingToState(String state) {
        WebElement shipToStateInput = wait.waitForElementPresent(shippingToState);
        click.clickElementCarefully(shipToStateInput);
        WebElement stateInput = wait.waitForElementDisplayed(stateInputField);
        text.enterText(stateInput, state);
        text.pressTab(stateInput);
    }

    /**
     * method to select state for Billing address.
     *
     * @param state state for setting Billing address.
     */
    public void selectBillingState(String state) {
        WebElement billingStateInput = wait.waitForElementPresent(billingState);
        click.clickElementCarefully(billingStateInput);
        WebElement stateInput = wait.waitForElementDisplayed(billingInputField);
        text.enterText(stateInput, state);
        text.pressTab(stateInput);
    }

    /**
     * method to enter address line for ship from address.
     *
     * @param addressLine addressLine1 for setting shipping origin address.
     */
    public void enterAddressLineOne(String addressLine) {
        WebElement addressLineInput = wait.waitForElementDisplayed(addressLine1);
        text.clearText(addressLineInput);
        text.enterText(addressLineInput, addressLine);
    }

    /**
     * method to clear address line for ship from address.
     */
    public void clearAddressLine() {
        WebElement addressLineInput = wait.waitForElementDisplayed(addressLine1);
        text.clearText(addressLineInput);
        WebElement addressCityInput = wait.waitForElementDisplayed(addressCity);
        text.clearText(addressCityInput);
        WebElement addressZipInput = wait.waitForElementPresent(addressZip);
        text.clearText(addressZipInput);
        clickSaveChangesButton();
    }

    /**
     * method to enter city for ship from address.
     *
     * @param city for setting shipping origin city.
     */
    public void enterAddressCity(String city) {
        WebElement addressCityInput = wait.waitForElementDisplayed(addressCity);
        text.clearText(addressCityInput);
        text.enterText(addressCityInput, city, false);
    }

    /**
     * method to enter zipcode for ship from address.
     *
     * @param zip for setting shipping origin zip.
     */
    public void enterAddressZip(String zip) {
        WebElement addressZipInput = wait.waitForElementPresent(addressZip);
        cancelAllAlerts();
        text.clearText(addressZipInput);
        cancelAllAlerts();
        text.enterText(addressZipInput, zip, false);
        text.pressTab(addressZipInput);
    }

    /**
     * Removes postal code
     */
    public void removePostalCode() {
        cancelAllAlerts();
        WebElement postalCodeInput = wait.waitForElementDisplayed(addressZip);
        text.clearText(postalCodeInput);
        cancelAllAlerts();
        text.pressTab(postalCodeInput);
    }

    /**
     * method to enter blank zipcode for ship from address.
     *
     * @param zip blank zip for setting shipping origin zip.
     */
    public void enterAddressBlankZip(String zip) {
        WebElement addressZipInput = wait.waitForElementPresent(addressZip);
        text.clearText(addressZipInput);
        text.pressTab(addressZipInput);
    }

    /**
     * method to enter country for ship from address.
     *
     * @param country for setting shipping origin country.
     */
    public void enterCountry(String country) {
        WebElement countryInput = wait.waitForElementDisplayed(countryField);
        dropdown.selectDropdownByDisplayName(countryInput, country);
    }

    /**
     * method to select tax status.
     *
     * @param status select tax status.
     */
    public void selectTaxStatus(String status) {
        WebElement taxStatusInput = wait.waitForElementDisplayed(taxStatus);
        dropdown.selectDropdownByDisplayName(taxStatusInput, status);
    }

    /**
     * method to select tax class.
     *
     * @param taxCls select tax class.
     */
    public void selectTaxClass(String taxCls) {
        WebElement taxClassInput = wait.waitForElementDisplayed(taxClass);
        dropdown.selectDropdownByDisplayName(taxClassInput, taxCls);
    }

    /**
     * method to change and enter ship from address.
     *
     * @param addressLine1 for setting shipping origin address line.
     * @param city         for setting shipping origin city.
     * @param zip          for setting shipping origin zip.
     */
    public void changeShipFromAddress(String addressLine1, String city, String zip) {
        enterAddressLineOne(addressLine1);
        enterAddressCity(city);
        enterAddressZip(zip);
    }

    /**
     * method to change and enter ship To address.
     *
     * @param addressLine1 for setting ship To address line.
     * @param city         for setting ship To city.
     * @param state        for setting ship To state.
     * @param zip          for setting ship To zip.
     */
    public void enterShipToAddress(String addressLine1, String city, String state, String zip) {
        enterShipToAddressLineOne(addressLine1);
        enterShipToCityAddress(city);
        selectShippingToState(state);
        enterShipToPostalCode(zip);
    }

    /**
     * method to change and enter Billing address.
     *
     * @param addressLine1 for setting Billing address line.
     * @param city         for setting Billing city.
     * @param state        for setting Billing state.
     * @param zip          for setting Billing zip.
     */
    public void enterBillingAddress(String addressLine1, String city, String state, String zip) {
        enterBillingLineOne(addressLine1);
        enterBillingAddressCity(city);
        selectBillingState(state);
        enterBillingPostalCode(zip);
    }

    /**
     * method to get alertMessage for incorrect address.
     *
     * @return alertMessage for addressCleansing.
     */
    public String getAlertMessage() {
        alert.waitForAlertPresent(1);
        String alertMessage = alert.getAlertText();
        alert.acceptAlert();
        return alertMessage;
    }

    /**
     * method to cancel alertMessage for ship from address.
     *
     * @return alertMessage for addressCleansing.
     */
    public String cancelAlertMessage() {
        alert.waitForAlertPresent(2);
        String alertMessage = null;
        if (alert.isAlertPresent()) {
            alertMessage = alert.getAlertText();
            alert.dismissAlert();
        }
        return alertMessage;
    }

    /**
     * Cancels all alerts (Somewhere multiple alerts appears)
     */
    public void cancelAllAlerts() {
        alert.waitForAlertPresent(2);
        while (alert.isAlertPresent()) {
            alert.dismissAlert();
        }
    }

    /**
     * click woocommerce setting.
     */
    public void clickWooCommerceSetting() {
        clickWooCommerceIcon();
        clickSettingsButton();
    }

    /**
     * click woocommerce Tax Checkbox setting.
     */
    public void clickWooCommerceTaxCheckbox() {
        clickTaxCheckBox();
        clickSaveChangesButton();
    }

    /**
     * set customer code for woocommerce connector.
     *
     * @param customerCodes customer code for customer.
     */
    public void setCustomerCode(String customerCodes) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage loginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage adminHomePage = loginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data,
                WooCommerceData.ADMIN_PASSWORD.data);
        clickUsersButton();
        clickUsersList();
        enterCustomerCode(customerCodes);
        clickUpdateProfile();
    }

    /**
     * set customer class for woocommerce connector.
     *
     * @param customerClass customer class for customer.
     */
    public void setCustomerClass(String customerClass) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage loginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage adminHomePage = loginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data,
                WooCommerceData.ADMIN_PASSWORD.data);
        clickUsersButton();
        clickUsersList();
        enterCustomerClass(customerClass);
        clickUpdateProfile();
    }

    /**
     * set product class for woocommerce connector.
     *
     * @param productClass product class for customer.
     */
    public void setProductClass(String productClass) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage loginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage adminHomePage = loginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data,
                WooCommerceData.ADMIN_PASSWORD.data);
        clickProductButton();
        clickAddNewButton();
        enterProductCode(productClass);
        enterProductPriceAmount(WooCommerceData.PRODUCT_PRICE.data);
        enterProductSalePriceAmount(WooCommerceData.SALE_PRICE.data);
        selectTaxStatus(WooCommerceData.PRODUCT_TAXABLE_CODE.data);
        selectTaxClass(WooCommerceData.PRODUCT_TAX_CLASS.data);
        clickPublishButton();
    }

    /**
     * set product code for woocommerce connector.
     *
     * @param productCd product code for customer.
     */
    public void setProductCode(String productCd) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage loginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage adminHomePage = loginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data,
                WooCommerceData.ADMIN_PASSWORD.data);
        clickProductButton();
        clickAddNewButton();
        enterProductCode(productCd);
        enterProductPriceAmount(WooCommerceData.PRODUCT_PRICE.data);
        enterProductSalePriceAmount(WooCommerceData.SALE_PRICE.data);
        selectTaxStatus(WooCommerceData.PRODUCT_STATUS_CODE.data);
        selectTaxClass(WooCommerceData.PRODUCT_TAX_CLASS.data);
        clickPublishButton();
    }

    /**
     * Sets Woo Commerce Store address from Admin Panel
     * Kindly enter parameters in the given sequence: Country, Address Line 1, City, Postal Code.
     * example1: wooCommerceAdminHomePage.setWooStoreAddress("United States (US) — Pennsylvania", "1270 York Rd", "Gettysburg", "17325-7562"); // US Address
     * example2: wooCommerceAdminHomePage.setWooStoreAddress("Germany", "Allée du Stade", "Berlin", "13405"); // EU-DE Address
     *
     * @param country     enter Country — State for US & enter Country for EU
     * @param addressLine enter address line 1
     * @param city        enter correct city under entered country
     * @param zip         enter correct zip code under entered country & city combination
     */
    public void setWooStoreAddress(String country, String addressLine, String city, String zip) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.clickWooCommerceSetting();
        homePage.enterCountry(country);
        homePage.enterAddressLineOne(addressLine);
        homePage.enterAddressCity(city);
        homePage.enterAddressZip(zip);
        cancelAllAlerts();
        homePage.clickSaveChangesButton();
    }

    /**
     * Place refund of ordered items
     * example: wooCommerceAdminHomePage.refundItems("99", WooData.PRODUCTS_TWO_HOODIES_BELT.data, WooData.QUANTITIES_FIVE_ONE_TWO.data);
     *
     * @param orderNo    pass order number which item(s) should be refunded
     * @param products   pass product(s) which should be refunded
     * @param quantities pass quantity of products which many should be refunded
     */
    public void refundItems(String orderNo, String[] products, String[] quantities) {
        driver.get(WooCommerceData.ADMIN_URL.data);
        WooCommerceAdminLoginPage adminLoginPage = new WooCommerceAdminLoginPage(driver);
        WooCommerceAdminHomePage homePage = adminLoginPage.loginAsUser(WooCommerceData.ADMIN_USERNAME.data, WooCommerceData.ADMIN_PASSWORD.data);
        homePage.clickWooCommerceIcon();
        homePage.clickOrderButton();
        homePage.searchAndEditOrder(orderNo);
        homePage.refundOrder(products, quantities);
    }

    /**
     * Signs out from the Admin Panel
     */
    public void signOutOfAdminPanel() {
        waitForPageLoad();
        hover.hoverOverElement(wait.waitForElementPresent(userAvatar));
        click.javascriptClick(wait.waitForElementPresent(userAvatarLogoutOption));
        waitForPageLoad();
    }
}

