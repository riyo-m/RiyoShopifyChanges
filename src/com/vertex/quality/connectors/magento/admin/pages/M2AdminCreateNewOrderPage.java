package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

/**
 * Page where products and account info are added to the order. Order is
 * submitted on this page
 *
 * @author alewis
 */
public class M2AdminCreateNewOrderPage extends MagentoAdminPage {
    public M2AdminCreateNewOrderPage(WebDriver driver) {
        super(driver);
    }

    protected By billingAddressId = By.id("order-billing_address_street0");
    protected By billingCityId = By.id("order-billing_address_city");
    protected By billingStateId = By.id("order-billing_address_region_id");
    protected By billingPostCodeID = By.id("order-billing_address_postcode");
    protected By orderItemsID = By.id("order-items");
    protected By adminClass = By.className("admin__page-section-title");
    protected By actionsClass = By.className("actions");
    protected By addProductsBySKUClass = By.className("action-add");
    protected By applyCouponCode = By.id("coupons:code");
    protected By updateItemsAndQuantities = By.xpath("//div[@class='order-discounts']//button/span[contains(text(),'Update Items and Quantities')]");

    protected String addProductsBySKUString = "Add Products By SKU";

    protected By productSKUBlock = By.className("product-sku-block");

    protected By addProductsID = By.id("add_products");
    protected By configureContainerClass = By.className("sku-configure-button");
    protected By configureTitle = By.className("action-default");
    protected By firstItemID = By.id("super_group[33]");
    protected By secondItemID = By.id("super_group[34]");
    protected By thirdItemID = By.id("super_group[35]");
    protected By okButtonContainerClass = By.className("page-actions-buttons");
    protected By okButtonClass = By.className("action-primary");
    protected By qtyID = By.id("product_composite_configure_input_qty");

    protected By skuField = By.id("sales_order_create_search_grid_filter_sku");
    protected By skuFieldOne = By.id("sku_0");
    protected By orderFormAccountID = By.id("order-form_account");
    protected By addProdToOrderContainer = By.className("admin__fieldset-wrapper-title");

    protected By SKUField = By.id("sku_0");

    protected By qtyField = By.id("sales_order_create_search_grid_filter_name");
    protected String productCheckbox = ".//td[normalize-space(.)='<<text_replace>>']/following-sibling::td//input[@type='checkbox']";
    protected String productQtyBox = ".//td[normalize-space(.)='<<text_replace>>']/following-sibling::td//input[@type='text']";
    protected By qtyItemField = By.id("sku_qty_1");
    protected String qty = "1";
    protected String qtyThree = "3";

    protected String addToOrderText = "Add to Order";
    protected String addSelectedProductsText = "Add Selected Product(s) to Order";
    protected String submitOrderText = "Submit Order";
    protected String addProductsText = "Add Products to Order";

    protected By rowSubtotalClass = By.className("col-row-subtotal");
    protected By labelClass = By.className("label");
    protected By priceClass = By.className("price");
    protected String exclLabel = "Excl. Tax:";

    protected By rowTotalsClass = By.className("row-totals");
    protected By adminTotalMark = By.className("admin__total-mark");
    protected By adminTotalAmount = By.className("admin__total-amount");
    protected String markStringExcl = "Grand Total Excl. Tax";
    protected String markStringIncl = "Grand Total Incl. Tax";

    protected By orderShippingMethodSummary = By.id("order-shipping-method-summary");
    protected By orderBillingMethodSummary = By.id("order-billing_method_summary");
    protected By actionDefaultClass = By.className("action-default");
    protected By methodFlatRate = By.id("s_method_flatrate_flatrate");

    protected By shippingSameAsBillingClass = By.className("admin__field-shipping-same-as-billing");
    protected By shippingContainerClass = By.className("admin__field-label");
    protected By shippingSameAsBillingBoxClass = By.className("order-shipping_same_as_billing");

    protected By submitOrderTopPageID = By.id("submit_order_top_button");
    protected By checkID = By.id("p_method_checkmo");

    protected final By billingAddressFirstName = By.id("order-billing_address_firstname");
    protected final By billingAddressLastName = By.id("order-billing_address_lastname");
    protected final By billingAddressStreet0 = By.id("order-billing_address_street0");
    protected final By billingAddressStreet1 = By.id("order-billing_address_street1");
    protected final By billingAddressCountry = By.id("order-billing_address_country_id");
    protected final By billingAddressState = By.id("order-billing_address_region_id");
    protected final By billingAddressesState = By.id("order-billing_address_region");
    protected final By billingAddressCity = By.id("order-billing_address_city");
    protected final By billingAddressZip = By.id("order-billing_address_postcode");
    protected final By billingAddressPhone = By.id("order-billing_address_telephone");

    protected final By shippingAddressFirstName = By.id("order-shipping_address_firstname");
    protected final By shippingAddressLastName = By.id("order-shipping_address_lastname");
    protected final By shippingAddressStreet0 = By.id("order-shipping_address_street0");
    protected final By shippingAddressCountry = By.id("order-shipping_address_country_id");
    protected final By shippingAddressState = By.id("order-shipping_address_region_id");
    protected final By shippingAddressesState = By.id("order-shipping_address_region");
    protected final By shippingAddressCity = By.id("order-shipping_address_city");
    protected final By shippingAddressZip = By.id("order-shipping_address_postcode");
    protected final By shippingAddressPhone = By.id("order-shipping_address_telephone");
    protected final By getPaymentMethods = By.xpath("//*[contains(text(),'Get available payment methods')]//parent::a");
    protected final By taxValue = By.xpath("//td[contains(text(),'Sales and Use')]");
    protected final By discountValue = By.xpath("//*[@id=\"totals-default\"]/td[2]");
    protected final By subtotalAmount = By.xpath("//*[@id=\"subtotal-exclude-tax\"]/td[2]/span");
    protected final By subtotalAmountItem = By.xpath("//*[@id=\"order-items_grid\"]/table/tbody[1]/tr/td[4]/span[2]");
    protected final By taxCollapse = By.xpath("//*[contains(text(),'Grand Total Excl. Tax')]//following::td[2]");
    protected final By taxPrice = By.xpath("//tr[@id='tax-summary-1']//child::span");
    protected final By stateInput = By.xpath("//*[@id='order-shipping_address_region']");
    protected final By addAnotherButton = By.xpath("//span[contains(text(),'Add another')]");
    protected By applyDiscountButton = By.xpath("//button[@title='Apply']");
    protected By deleteDiscountButton = By.xpath("//*[@title='Remove Coupon Code']");
    protected By taxPercent1 = By.xpath("//*[@id=\"sales_order_view_tabs_order_info_content\"]/section[4]/div[2]/table/tbody[1]/tr/td[8]");
    protected By taxPercent2 = By.xpath("//*[@id=\"sales_order_view_tabs_order_info_content\"]/section[4]/div[2]/table/tbody[2]/tr/td[8]");
    protected By taxPercent3 = By.xpath("//*[@id=\"sales_order_view_tabs_order_info_content\"]/section[4]/div[2]/table/tbody[3]/tr/td[8]");
    protected By addProductsButton = By.xpath(".//button[normalize-space(.)='Add Products']");
    protected By sameBillingShippingChkBox = By.id("order-shipping_same_as_billing");
    protected By orderTotalLabel = By.xpath(".//span[text()='Order Totals']");
    protected By taxAmount = By.xpath(".//td[normalize-space(.)='Tax']/following-sibling::td//span");
    protected By subTotalAmount = By.xpath(".//td[normalize-space(.)='Subtotal']/following-sibling::td//span");
    protected By shippingHandlingAmount = By.xpath(".//td[contains(normalize-space(.),'Shipping & Handling')]/following-sibling::td//span");
    protected By grandTotalAmount = By.xpath(".//td[normalize-space(.)='Grand Total']/following-sibling::td//span");
    protected By discountAmount = By.xpath(".//td[normalize-space(.)='Discount']/following-sibling::td//span");
    protected By defaultViewStore = By.id("store_1");
    By maskClass = By.className("loading-mask");
    By spinnerClass = By.className("admin__data-grid-loading-mask");

    By shippingOptionClass = By.className("admin__order-shipment-methods-options-list");

    protected String innerHTML = "innerHTML";
    protected String okString = "OK";
    protected double taxRate;
    protected String taxValueField;
    protected String taxValueField1;
    protected String discountFieldValue;
    protected String subTotalValue;


    /**
     * clicks Same as Billing Address button
     */
    public void clickBillSameAsShipButton() {
        WebElement shipIsBillButton = wait.waitForElementEnabled(shippingSameAsBillingClass);
        WebElement shipIsBillBox = wait.waitForElementDisplayed(shippingContainerClass, shipIsBillButton);

        try {
            waitForMaskGone(maskClass);
        } catch (Exception e) {

        }

        click.clickElementCarefully(shipIsBillBox);
        waitForMaskGone(maskClass);
        waitForPageLoad();
    }

    public void enterCustomerInfo(String address, String city, String state, String zipCode) {
        WebElement billingAddressField = wait.waitForElementDisplayed(billingAddressId);
        text.setTextFieldCarefully(billingAddressField, address, true);

        WebElement billingCityField = wait.waitForElementDisplayed(billingCityId);
        text.setTextFieldCarefully(billingCityField, city, true);

        WebElement billingStateField = wait.waitForElementDisplayed(billingStateId);
        dropdown.selectDropdownByValue(billingStateField, state);

        WebElement billingPostCodeField = wait.waitForElementDisplayed(billingPostCodeID);
        text.setTextFieldCarefully(billingPostCodeField, zipCode, true);
    }

    /**
     * locates add to SKU button
     *
     * @return the add to SKU button
     */
    private WebElement findAddSKUButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement customerButton = null;
        String customerName = null;

        wait.waitForElementDisplayed(orderItemsID);
        WebElement orderItems = wait.waitForElementEnabled(orderItemsID);

        WebElement admin = wait.waitForElementEnabled(adminClass, orderItems);
        WebElement actions = wait.waitForElementEnabled(actionsClass, admin);
        List<WebElement> customerButtons = wait.waitForAllElementsPresent(addProductsBySKUClass, actions);
        customerButton = element.selectElementByText(customerButtons, addProductsBySKUString);

        return customerButton;
    }

    /**
     * clicks the Add SKU Button
     */
    public void clickAddSKUButton() {
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementNotDisplayed(spinnerClass);
        WebElement customerButton = findAddSKUButton();
        waitForPageLoad();

        if (customerButton != null) {
            wait.waitForElementDisplayed(customerButton);
            waitForPageLoad();
            wait.waitForElementNotDisplayed(maskClass);
            wait.tryWaitForElementEnabled(customerButton, 2);
            click.clickElement(customerButton);
            waitForPageLoad();
        } else {
            String errorMsg = "Add SKU button not found";
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * Selects Default View Store or Main Store
     */
    public void selectDefaultStore() {
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(defaultViewStore)) {
            click.moveToElementAndClick(wait.waitForElementPresent(defaultViewStore));
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Clicks on Add Products button
     */
    public void clickAddProducts() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementNotDisplayed(spinnerClass);
        click.moveToElementAndClick(wait.waitForElementPresent(addProductsButton));
        waitForSpinnerToBeDisappeared();
    }

    /**
     * clicks Update Items And Quantities button
     */
    public void clickUpdateItemsAndQuantitiesButton() {
        wait.waitForElementNotDisplayed(maskClass);
        WebElement updateItemsQuantitiesField = wait.waitForElementDisplayed(updateItemsAndQuantities, 3);
        click.clickElement(updateItemsQuantitiesField);
    }

    /**
     * clicks Update Items And Quantities button
     */
    public void clickAddAnotherButton() {
        WebElement updateItemsQuantitiesField = wait.waitForElementDisplayed(addAnotherButton, 3);
        click.clickElement(updateItemsQuantitiesField);
    }

    /**
     * clicks Update Items And Quantities button
     */
    public void clickApplyDiscountButton() {
        WebElement updateItemsQuantitiesField = wait.waitForElementDisplayed(applyDiscountButton);
        click.performDoubleClick(updateItemsQuantitiesField);
    }

    /**
     * clicks Apply Discount button.
     */
    public void clickDeleteDiscountButton() {
        WebElement updateItemsQuantitiesField = wait.waitForElementDisplayed(deleteDiscountButton);
        click.performDoubleClick(updateItemsQuantitiesField);
    }

    /**
     * enters the SKU number in the SKU field
     */
    public void enterCouponCode(String coupon) {
        waitForSpinnerToBeDisappeared();
        WebElement field = wait.waitForElementPresent(applyCouponCode);
        field.sendKeys(coupon);
    }

    /**
     * enters the SKU number in the SKU field
     */
    public void enterSKUNumber(String SKU) {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        WebElement field = wait.waitForElementPresent(skuField);
        field.sendKeys(SKU);
        text.pressEnter(field);
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
    }

    /**
     * enters the SKU number in the SKU field
     */
    public void enterSKUDetails(String SKU, By SKUField) {
        WebElement field = wait.waitForElementPresent(SKUField);
        field.sendKeys(SKU);
    }

    /**
     * enters the SKU number for the second product in SKU field
     */
    public void enterSKUNumberOne(String SKU) {
        WebElement field = wait.waitForElementPresent(skuFieldOne);
        field.sendKeys(SKU);
    }

    /**
     * enters the quantity in the quantity field
     */
    public void enterQty() {
        WebElement field = wait.waitForElementPresent(qtyField);
        text.enterText(field, qty);
    }

    /**
     * enters the quantity in the quantity field
     */
    public void enterNewQty(String qty) {
        WebElement field = wait.waitForElementPresent(qtyField);
        text.enterText(field, qty);
    }

    /**
     * Enters the quantity for product(s) added in the cart
     *
     * @param productName name of product which quantity is to be updated
     * @param qty         No. of quantity
     */
    public void enterNewQty(String productName, String qty) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(productCheckbox.replace("<<text_replace>>", productName))));
        text.enterText(wait.waitForElementPresent(By.xpath(productQtyBox.replace("<<text_replace>>", productName))), qty);
    }

    /**
     * enters the quantity in the quantity field
     */
    public void enterMultiQty(String qty, By qtyField) {
        WebElement field = wait.waitForElementPresent(qtyField);
        text.enterText(field, qty);
    }

    /**
     * enters the specified quantity in the quantity field
     */
    public void enterSpecifiedQty(String quantity) {
        WebElement field = wait.waitForElementPresent(qtyField);
        text.enterText(field, quantity);
    }

    /**
     * configures the bundled order when only needs to open and hit 'OK'
     */
    public void simpleConfigureOrder() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementNotDisplayed(spinnerClass);
        WebElement configureContainer = wait.waitForElementEnabled(configureContainerClass);
        WebElement configureButton = wait.waitForElementEnabled(configureTitle, configureContainer);
        configureButton.click();
        waitForPageLoad();
        wait.waitForElementNotDisplayed(spinnerClass);
        wait.waitForElementNotDisplayed(maskClass);

        List<WebElement> okButtonContainers = wait.waitForAllElementsPresent(okButtonContainerClass);

        WebElement okButtonContainer = element.selectElementByText(okButtonContainers, okString);
        if (okButtonContainer != null) {
            wait.waitForElementNotDisplayed(spinnerClass);
            wait.waitForElementNotDisplayed(maskClass);
            WebElement okButton = wait.waitForElementEnabled(okButtonClass, okButtonContainer);
            click.clickElement(okButton);
        }
        waitForPageLoad();
        wait.waitForElementNotDisplayed(spinnerClass);
        wait.waitForElementNotDisplayed(maskClass);
    }

    /**
     * configures the bundled order
     */
    public void configureOrder() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement configureContainer = wait.waitForElementEnabled(configureContainerClass);
        WebElement configureButton = wait.waitForElementEnabled(configureTitle, configureContainer);
        configureButton.click();
        waitForPageLoad();
        WebElement firstTextBox = wait.waitForElementEnabled(firstItemID);
        text.enterText(firstTextBox, qty);
        WebElement secondTextBox = wait.waitForElementEnabled(secondItemID);
        text.enterText(secondTextBox, qty);
        WebElement thirdTextBox = wait.waitForElementEnabled(thirdItemID);
        text.enterText(thirdTextBox, qty);
        waitForPageLoad();
        List<WebElement> okButtonContainers = wait.waitForAllElementsPresent(okButtonContainerClass);

        WebElement okButtonContainer = element.selectElementByText(okButtonContainers, okString);
        if (okButtonContainer != null) {
            WebElement okButton = wait.waitForElementEnabled(okButtonClass, okButtonContainer);
            click.clickElement(okButton);
            //fixme should break
        }
        waitForPageLoad();
    }

    /**
     * configures the bundled order for partial refund
     */
    public void configurePartialRefundOrder() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement configureContainer = wait.waitForElementEnabled(configureContainerClass);
        WebElement configureButton = wait.waitForElementEnabled(configureTitle, configureContainer);
        configureButton.click();

        WebElement qty = wait.waitForElementDisplayed(qtyID);
        qty.clear();
        qty.sendKeys(qtyThree);

        List<WebElement> okButtonContainers = wait.waitForAllElementsDisplayed(okButtonContainerClass);

        WebElement okButtonContainer = element.selectElementByText(okButtonContainers, okString);
        if (okButtonContainer != null) {
            WebElement okButton = wait.waitForElementEnabled(okButtonClass, okButtonContainer);
            click.clickElement(okButton);
            //fixme should break
        }
    }

    /**
     * clicks the Add To Order Button
     */
    public void clickAddToOrderButton() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> actionButtons = wait.waitForAllElementsPresent(actionsClass);

        WebElement addOrderButton = element.selectElementByText(actionButtons, addToOrderText);
        if (addOrderButton != null) {
            //fixme this used to just wait for displayed, scott made it wait enabled, if this function breaks check that first
            click.clickElementCarefully(addOrderButton);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * clicks the Add Selected Product(s) to Order button
     */
    public void clickAddSelectedProductToOrderButton() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> actionButtons = wait.waitForAllElementsPresent(actionsClass);

        WebElement addOrderButton = element.selectElementByText(actionButtons, addSelectedProductsText);
        if (addOrderButton != null) {
            //fixme this used to just wait for displayed, scott made it wait enabled, if this function breaks check that first
            click.clickElementCarefully(addOrderButton);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * clicks the Add Products To Order Button
     */
    public void clickAddProductsToOrderButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(spinnerClass);
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(addProdToOrderContainer);
        WebElement container = wait.waitForElementDisplayed(addProdToOrderContainer, 5);
        waitForPageLoad();
        WebElement addProdButton = wait.waitForElementEnabled(addProductsBySKUClass, container);
        wait.waitForElementDisplayed(addProdButton, 7);
        click.clickElement(addProdButton);
        waitForPageLoad();
    }

    public void clickCheckOption() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement check = wait.waitForElementDisplayed(checkID);
        click.clickElementCarefully(check);
        waitForPageLoad();
    }

    /**
     * Gets price excluding tax from Row Subtotal at top of page
     *
     * @return string of the subtotal price of order
     */
    public String getPriceSubtotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> rowSubtotals = wait.waitForAllElementsPresent(rowSubtotalClass);
        String price = null;

        WebElement rowSubtotal = element.selectElementByNestedLabel(rowSubtotals, labelClass, exclLabel);
        if (rowSubtotal != null) {
            WebElement priceElement = wait.waitForElementPresent(priceClass, rowSubtotal);
            price = text.getElementText(priceElement);
            //fixme should break
        }

        return price;
    }

    /**
     * Gets price including tax from Row Subtotal at top of page
     *
     * @return string of the subtotal price of order
     */
    public String getPriceSubtotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> rowSubtotals = wait.waitForAllElementsPresent(rowSubtotalClass);
        WebElement rowSubtotal = rowSubtotals.get(0);
        List<WebElement> priceElements = wait.waitForAllElementsPresent(priceClass, rowSubtotal);
        WebElement priceElement = priceElements.get(1);
        String price = text.getElementText(priceElement);
        return price;
    }

    /**
     * Gets price excluding tax from Row total at bottom right corner of page
     *
     * @return a string of the total price of order
     */
    public String getPriceTotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String amountText = null;
        waitForPageLoad();
        List<WebElement> rowTotals = wait.waitForAllElementsPresent(rowTotalsClass);

        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, adminTotalMark, markStringExcl);
        if (rowTotal != null) {
            WebElement amount = wait.waitForElementPresent(adminTotalAmount, rowTotal);
            amountText = text.getElementText(amount);
            //fixme should break
        }
        return amountText;
    }

    /**
     * Gets price including tax from Row total at bottom right corner of page
     *
     * @return a string of the total price of order
     */
    public String getPriceTotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> rowTotals = wait.waitForAllElementsPresent(rowTotalsClass);
        String amountText = null;

        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, adminTotalMark, markStringIncl);
        if (rowTotal != null) {
            WebElement amount = wait.waitForElementPresent(adminTotalAmount, rowTotal);
            amountText = text.getElementText(amount);
            //fixme should break
        }
        return amountText;
    }

    /**
     * selects a shipping rate for the order
     */
    public void addShippingMethod(int i) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementNotDisplayed(spinnerClass);

        WebElement orderShippingMethod = wait.waitForElementDisplayed(orderShippingMethodSummary);
        WebElement orderShippingMethodButton = wait.waitForElementDisplayed(configureTitle, orderShippingMethod);
        click.clickElementCarefully(orderShippingMethodButton);
        waitForPageLoad();
        List<WebElement> shippingOptions = wait.waitForAllElementsDisplayed(shippingOptionClass);
        WebElement tableRate = shippingOptions.get(i);
        wait.waitForElementDisplayed(tableRate);
        click.clickElement(tableRate);
        waitForPageLoad();
    }

    public void addShippingMethodSecond() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementNotDisplayed(spinnerClass);

        WebElement orderShippingMethod = wait.waitForElementDisplayed(orderShippingMethodSummary);
        WebElement orderShippingMethodButton = wait.waitForElementDisplayed(configureTitle, orderShippingMethod);
        click.clickElementCarefully(orderShippingMethodButton);
    }

    /**
     * clicks the Submit Order Button
     *
     * @return the order view info page
     */
    public M2AdminOrderViewInfoPage clickSubmitOrderButton() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        waitForMaskGone(maskClass);
        wait.waitForElementNotDisplayed(maskClass);
        WebElement submitButton = wait.waitForElementEnabled(submitOrderTopPageID);
        scroll.scrollElementIntoView(submitButton);
        waitForPageLoad();
        wait.waitForElementNotDisplayed(spinnerClass);
        wait.waitForElementNotDisplayed(maskClass);
        click.clickElementCarefully(submitButton);

        return initializePageObject(M2AdminOrderViewInfoPage.class);
    }

    /**
     * clicks the Submit Order Button
     */
    public void clickSubmitOrder() {
        waitForMaskGone(maskClass);
        wait.waitForElementNotDisplayed(maskClass);
        WebElement submitButton = wait.waitForElementEnabled(submitOrderTopPageID);
        scroll.scrollElementIntoView(submitButton);
        waitForPageLoad();
        wait.waitForElementNotDisplayed(spinnerClass);
        wait.waitForElementNotDisplayed(maskClass);
        click.clickElementCarefully(submitButton);
    }

    /**
     * retrieve first line Tax Percent for order.
     *
     * @return taxValueField
     */
    public String getLine1TaxPercent() {
        taxValueField = text.getElementText(taxPercent1);
        return taxValueField;
    }

    /**
     * retrieve second line Tax Percent for order.
     *
     * @return taxValueField
     */
    public String getLine2TaxPercent() {
        taxValueField1 = text.getElementText(taxPercent2);
        return taxValueField1;
    }
    /**
     * retrieve second line Tax Percent for order.
     *
     * @return taxValueField
     */
    public String getLine3TaxPercent() {
        taxValueField1 = text.getElementText(taxPercent3);
        return taxValueField1;
    }

    /**
     * clicks the Submit Order Button
     *
     * @return the order view info page
     */
    public M2AdminOrderViewInfoPage clickSubmitOrderButtonNoMask() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(submitOrderTopPageID, 10);
        WebElement submitButton = wait.waitForElementPresent(submitOrderTopPageID);
        scroll.scrollElementIntoView(submitButton);
        click.clickElement(submitButton);

        return initializePageObject(M2AdminOrderViewInfoPage.class);
    }

    /**
     * select check/money order as the payment method
     */
    public void selectCheckAsPaymentMethod() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(checkID);
        WebElement checkMoneyOrder = wait.waitForElementEnabled(checkID);
        click.clickElement(checkMoneyOrder);
    }

    /**
     * add billing address
     */
    public void addBillingAddress(Map<String, String> billingAddress) {
        text.enterText(billingAddressFirstName, billingAddress.get("firstName"));
        text.enterText(billingAddressLastName, billingAddress.get("lastName"));
        text.enterText(billingAddressStreet0, billingAddress.get("street0"));
        text.enterText(billingAddressStreet1, billingAddress.get("street0"));
        dropdown.selectDropdownByDisplayName(billingAddressCountry, billingAddress.get("country"));
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(billingAddressesState)) {
            text.enterText(billingAddressesState, billingAddress.get("state"));
        } else {
            dropdown.selectDropdownByDisplayName(billingAddressState, billingAddress.get("state"));
        }
        text.enterText(billingAddressCity, billingAddress.get("city"));
        text.enterText(billingAddressZip, billingAddress.get("zip"));
        text.enterText(billingAddressPhone, billingAddress.get("phone"));

    }
    /**
     * add billing address with state field as text field.
     */
    public void addBillingAddresses(Map<String, String> billingAddress) {
        text.enterText(billingAddressFirstName, billingAddress.get("firstName"));
        text.enterText(billingAddressLastName, billingAddress.get("lastName"));
        text.enterText(billingAddressStreet0, billingAddress.get("street0"));
        dropdown.selectDropdownByDisplayName(billingAddressCountry, billingAddress.get("country"));
        text.enterText(billingAddressesState, billingAddress.get("state"));
        text.enterText(billingAddressCity, billingAddress.get("city"));
        text.enterText(billingAddressZip, billingAddress.get("zip"));
        text.enterText(billingAddressPhone, billingAddress.get("phone"));

    }

    /**
     * Selects/Deselects same as billing address checkbox.
     *
     * @param isSame same as billing = true, different = false
     */
    public void selectDeselectSameAsAddress(boolean isSame) {
        waitForSpinnerToBeDisappeared();
        if (isSame && !checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingChkBox))) {
            waitForSpinnerToBeDisappeared();
            click.moveToElementAndClick(sameBillingShippingChkBox);
        }
        else if (!isSame && checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingChkBox))) {
            waitForSpinnerToBeDisappeared();
            click.moveToElementAndClick(sameBillingShippingChkBox);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Adds Shipping Address
     *
     * @param isSame          same as billing = true, different = false
     * @param shippingAddress shipping address
     */
    public void addShippingAddress(boolean isSame, Map<String, String> shippingAddress) {
        waitForSpinnerToBeDisappeared();
        if (isSame && !checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingChkBox))) {
            click.javascriptClick(sameBillingShippingChkBox);
        }
        else if (!isSame && checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingChkBox))) {
            text.enterText(shippingAddressFirstName, shippingAddress.get("firstName"));
            text.enterText(shippingAddressLastName, shippingAddress.get("lastName"));
            text.enterText(shippingAddressStreet0, shippingAddress.get("street0"));
            dropdown.selectDropdownByDisplayName(shippingAddressCountry, shippingAddress.get("country"));
            waitForSpinnerToBeDisappeared();
            if (shippingAddress.get("country").equals("Greece"))
                text.enterText(stateInput, "Please select");
            else
                dropdown.selectDropdownByDisplayName(shippingAddressState, shippingAddress.get("state"));
            waitForSpinnerToBeDisappeared();
            text.enterText(shippingAddressCity, shippingAddress.get("city"));
            text.enterText(shippingAddressZip, shippingAddress.get("zip"));
            text.enterText(shippingAddressPhone, shippingAddress.get("phone"));
        }
    }

    /**
     * add shipping address
     */
    public void addShippingAddress(Map<String, String> shippingAddress) {
        selectDeselectSameAsAddress(false);
        text.enterText(wait.waitForElementPresent(shippingAddressFirstName), shippingAddress.get("firstName"));
        text.enterText(wait.waitForElementPresent(shippingAddressLastName), shippingAddress.get("lastName"));
        text.enterText(wait.waitForElementPresent(shippingAddressStreet0), shippingAddress.get("street0"));
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(shippingAddressCountry), shippingAddress.get("country"));
        if (shippingAddress.get("country").equals("Greece"))
            text.enterText(wait.waitForElementPresent(stateInput), "Please select");
        else
            dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(shippingAddressState), shippingAddress.get("state"));
        text.enterText(wait.waitForElementPresent(shippingAddressCity), shippingAddress.get("city"));
        text.enterText(wait.waitForElementPresent(shippingAddressZip), shippingAddress.get("zip"));
        text.enterText(wait.waitForElementPresent(shippingAddressPhone), shippingAddress.get("phone"));
    }
    /**
     * add shipping address with state as input text.
     */
    public void addShippingAddresses(Map<String, String> shippingAddress) {
        text.enterText(shippingAddressFirstName, shippingAddress.get("firstName"));
        text.enterText(shippingAddressLastName, shippingAddress.get("lastName"));
        text.enterText(shippingAddressStreet0, shippingAddress.get("street0"));
        dropdown.selectDropdownByDisplayName(shippingAddressCountry, shippingAddress.get("country"));
        if (shippingAddress.get("country").equals("Greece"))
            text.enterText(stateInput, "Please select");
        else
            text.enterText(shippingAddressesState, shippingAddress.get("state"));
        text.enterText(shippingAddressCity, shippingAddress.get("city"));
        text.enterText(shippingAddressZip, shippingAddress.get("zip"));
        text.enterText(shippingAddressPhone, shippingAddress.get("phone"));
    }

    /**
     * select check/money order as the payment method without page load
     */
    public void selectPaymentMethod() {
        waitForSpinnerToBeDisappeared();
        if (element.isElementPresent(getPaymentMethods)) {
            WebElement paymentMethods = wait.waitForElementPresent(getPaymentMethods);
            click.performDoubleClick(paymentMethods);
        }
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(checkID)) {
            WebElement checkMoneyOrder = wait.waitForElementPresent(checkID);
            click.moveToElementAndClick(checkMoneyOrder);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * selects a shipping rate for the order
     */
    public void selectShippingMethod(int i) {
        waitForSpinnerToBeDisappeared();
        WebElement orderShippingMethod = wait.waitForElementPresent(orderShippingMethodSummary);
        WebElement orderShippingMethodButton = wait.waitForElementPresent(configureTitle, orderShippingMethod);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(orderShippingMethodButton));
        waitForSpinnerToBeDisappeared();
        click.javascriptClick(orderShippingMethodButton);
        waitForSpinnerToBeDisappeared();
        List<WebElement> shippingOptions = wait.waitForAllElementsPresent(shippingOptionClass);
        WebElement tableRate = shippingOptions.get(i);
        wait.waitForElementDisplayed(tableRate);
        waitForSpinnerToBeDisappeared();
        click.clickElement(tableRate);
        waitForSpinnerToBeDisappeared();
    }

    /**
     * get Magento Discount Value for the order
     *
     * @return subTotal value
     */
    public String getMagentoSubTotalPerItemValue() {
        subTotalValue = text.getElementText(subtotalAmountItem).replaceAll("[^0-9]", "");
        return subTotalValue;
    }

    /**
     * selects a shipping rate for the order
     *
     * @return tax rate
     */
    public String getMagentoTaxValue() {
        try {
            WebElement taxCollapseElement = driver.findElement(taxCollapse);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", taxCollapseElement);
            taxValueField = text.getElementText(taxValue).replaceAll("[^0-9]", "");
        } catch (Exception e) {
            taxValueField = "0";
        }
        return taxValueField;
    }

    /**
     * Gets tax amount from the UI
     *
     * @return tax amount from UI.
     */
    public double getTaxFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderTotalLabel);
        wait.waitForElementPresent(subTotalAmount);
        wait.waitForElementPresent(shippingHandlingAmount);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("$", "").replace(",", ""));
    }

    /**
     * Verify whether tax label & tax is present or not?
     * Used in Customer Code/ Class & Product Code/ Class scenarios
     *
     * @return true or false based on condition
     */
    public boolean isTaxLabelPresent() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderTotalLabel);
        wait.waitForElementPresent(subTotalAmount);
        wait.waitForElementPresent(shippingHandlingAmount);
        return element.isElementPresent(taxAmount);
    }

    /**
     * Gets tax amount from the UI
     *
     * @return Total amount (Including Shipping, Tax & Excluding Discount)
     */
    public double getGrandTotalFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderTotalLabel);
        wait.waitForElementPresent(subTotalAmount);
        wait.waitForElementPresent(shippingHandlingAmount);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(grandTotalAmount)).replace("$", "").replace(",", ""));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(double taxAmount) {
        double subtotal = 0;
        double shipHandle = 0;
        double discount = 0;
        double expectedTax = 0;
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(orderTotalLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subTotalAmount)).replace("$", "").replace(",", ""));
        shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingHandlingAmount)).replace("$", "").replace(",", ""));
        if (element.isElementPresent(discountAmount)) {
            discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param isOnlyProduct  true to calculate tax only on product & false to ignore tax on product
     * @param isOnlyShipping true to calculate tax only on shipping & false to ignore tax on shipping
     * @param taxAmount      percent of tax
     * @return calculated percent based tax
     */
    public double calculateIndividualPercentBasedTax(boolean isOnlyProduct, boolean isOnlyShipping, double taxAmount) {
        double subtotal = 0;
        double shipHandle = 0;
        double discount = 0;
        double expectedTax = 0;
        waitForPageLoad();
        wait.waitForElementPresent(orderTotalLabel);
        WebElement st = wait.waitForElementPresent(subTotalAmount);
        WebElement sh = wait.waitForElementPresent(shippingHandlingAmount);
        if (isOnlyProduct) {
            subtotal = Double.parseDouble(text.getElementText(st).replace("$", "").replace(",", ""));
        }
        if (isOnlyShipping) {
            shipHandle = Double.parseDouble(text.getElementText(sh).replace("$", "").replace(",", ""));
        }
        if (element.isElementPresent(discountAmount)) {
            discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Calculates percentage based or expected tax for Colorado addresses as it has additional retail delivery fee
     * Separating this method tax calculation from regular tax calculation method to avoid confusion in future
     * Retail Delivery Fee is only applicable to Colorado and not for any other addresses
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax, Add fixed Delivery Fee & return combined amount
     */
    public double calculatePercentBasedTaxForColorado(double taxAmount, double coAdditionalTax) {
        double coTax = Double.parseDouble(String.format("%.2f", MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value * (coAdditionalTax / 100)));
        return Double.parseDouble(String.format("%.2f", calculatePercentBasedTax(taxAmount) + MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value + coTax));
    }

    /**
     * get Magento Discount Value for the order
     *
     * @return discount value
     */
    public String getMagentoDiscountValue() {
        discountFieldValue = text.getElementText(discountValue).replaceAll("[^0-9]", "");
        return discountFieldValue;
    }

    /**
     * get Magento Discount Value for the order
     *
     * @return subTotal value
     */
    public String getMagentoSubTotalValue() {
        subTotalValue = text.getElementText(subtotalAmount).replaceAll("[^0-9]", "");
        return subTotalValue;
    }

    public void addShippingAddressForAddressCleanse(Map<String, String> shippingAddress) {
        text.enterText(billingAddressFirstName, shippingAddress.get("firstName"));
        text.enterText(billingAddressLastName, shippingAddress.get("lastName"));
        text.enterText(billingAddressStreet0, shippingAddress.get("street0"));
        dropdown.selectDropdownByDisplayName(billingAddressCountry, shippingAddress.get("country"));
        dropdown.selectDropdownByDisplayName(billingAddressState, shippingAddress.get("state"));
        text.enterText(billingAddressCity, shippingAddress.get("city"));
        text.enterText(billingAddressZip, shippingAddress.get("zip"));
        text.enterText(billingAddressPhone, shippingAddress.get("phone"));

    }

}