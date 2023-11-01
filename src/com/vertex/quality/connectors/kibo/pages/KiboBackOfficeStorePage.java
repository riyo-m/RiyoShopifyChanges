package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.kibo.components.KiboBackOfficeStoreHeaderPane;
import com.vertex.quality.connectors.kibo.components.KiboBackOfficeStoreOrderDetailReturn;
import com.vertex.quality.connectors.kibo.components.KiboBackOfficeStoreOrderDetailsFulfillment;
import com.vertex.quality.connectors.kibo.components.KiboBackOfficeStoreOrderDetailsPayment;
import com.vertex.quality.connectors.kibo.dialog.KiboEditContactsEditAddressDialog;
import com.vertex.quality.connectors.kibo.dialog.KiboMaxineEditContactsDialog;
import com.vertex.quality.connectors.kibo.dialog.KiboOrderDetailsDialog;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * the class represents the maxine store in the back office
 * contains all the methods necessary to interact with the different fields and elements
 * mostly through the instances of other component classes
 *
 * @author osabha
 */
public class KiboBackOfficeStorePage extends VertexPage {
    public KiboEditContactsEditAddressDialog editAddressDialog;
    public KiboMaxineEditContactsDialog editContactsDialog;
    public KiboBackOfficeStoreOrderDetailReturn returns;
    public KiboBackOfficeStoreHeaderPane orderHeader;
    public KiboOrderDetailsDialog orderDetailsDialog;
    public KiboBackOfficeStoreOrderDetailsFulfillment fulfillment;
    public KiboBackOfficeStoreOrderDetailsPayment payment;
    protected By loadMaskLoc = By.className("taco-loadmask");
    protected By saveAndCreateAddressContainerLoc = By.className("x-btn-action-primary-toolbar-medium");
    protected By elemToAppearLoc = By.className("x-header-draggable");
    protected By addressValidationButtonLoc = By.className("x-btn-action-toolbar-medium-noicon");
    protected By saveCreateAddress = By.tagName("span");
    protected By address1Field = By.cssSelector("input[name='address1']");
    protected By city1Field = By.cssSelector("input[name='cityOrTown']");
    protected By state1Field = By.cssSelector("input[name='stateOrProvince']");
    protected By zip1Field = By.cssSelector("input[name='postalOrZipCode']");
    protected By phone1Field = By.cssSelector("input[name='homePhone']");
    protected By listTriggerContainer = By.className("taco-editor-wrapper");
    protected By orderDetailButton = By.className("x-btn-action-toolbar-medium");
    protected By listTriggerClass = By.className("x-form-arrow-trigger");
    protected By orderTotalContainerLoc = By.cssSelector("thead[itemid='taco-subTpl_9']");
    protected By taxAndDuty = By.cssSelector("thead[itemid='taco-subTpl_4']");
    protected By taxAmountClass = By.className("summary-price");
    protected By changeAddressContainerLoc = By.className("pane-change-address");
    protected By changeAddressLoc = By.className("x-btn-link-small");
    protected By SaveButton = By.xpath("(//span[contains(text(),'Cancel')])/following::span[text()='Save'][last()]");
    protected By SaveButtons = By.xpath("(//span[contains(text(),'Add New Address')])//following::span[contains(text(),'Save')]");
    protected By EditButton = By.xpath("//div[text() ='Customer Contact']/following-sibling::a[1]");
    protected By orderTotalLoc = By.className("x-tool-img x-tool-close");
    protected By AddressLine = By.xpath("//input[@name='address1']");
    protected By AddressCity = By.xpath("//input[@name='cityOrTown']");
    protected By AddressState = By.xpath("//input[@name='stateOrProvince']");
    protected By AddressZip = By.xpath("//input[@name='postalOrZipCode']");
    protected By CountryCode = By.xpath("//input[@name='countryCode']");
    protected By SaveAddressButtons = By.xpath("(//span[contains(text(),'Validate')])//following::span[6]");
    protected By quantityToReturn = By.xpath("//span[contains(text(),'Return Quantity')]");
    protected By newCustomerButtonLoc = By.className("x-btn-action-primary-medium-noicon");
    protected By discountValue = By.xpath("//div[@class='price-detail  taco-grid-cell-inner  negative-currency']");
    protected By discountSummary = By.xpath("//div[@class='adjustment-summary']");
    protected By billToButton = By.xpath("//label[contains(text(),'Bill to this address')]");
    protected By customerContactShipToButton = By.xpath(".//div[text()='Customer Contact']/parent::div/parent::div/parent::div/following-sibling::table//label[text()='Ship to this address']");
    protected By saveAddressButton = By.xpath("//span[text() ='Save']");
    protected By crossButton = By.xpath("//span[contains(text(),'Cancel Button')]");
    protected By fulFillmentButton = By.xpath(".//li[text()='Fulfillment']");
    protected By fulfillmentSelected = By.xpath(".//li[text()='Fulfillment'][contains(@class,'selected')]");
    protected By paymentTab = By.xpath(".//li[text()='Payments']");
    protected By paymentTabSelected = By.xpath(".//li[text()='Payments'][contains(@class,'selected')]");
    protected By moveToButton = By.xpath("//span[contains(text(),'Move to')]");
    protected By newPackageButton = By.xpath("//span[contains(text(),'New Package')]");
    protected By markAsShippedButton = By.xpath("//span[contains(text(),'Mark as Shipped')]");
    protected By returnButton = By.xpath(".//li[text()='Returns']");
    protected By returnSelected = By.xpath(".//li[text()='Returns'][contains(@class,'selected')]");
    protected By createReturnButton = By.xpath("//span[contains(text(),'Create Return')]");
    protected By createRefundButton = By.xpath("(.//div[text()='Refunds'])[1]");
    protected By issueRefundButton = By.xpath("//button[normalize-space(.)='Issue Refund']");
    protected By refundForReturnLabel = By.xpath("(.//div[starts-with(normalize-space(.),'Refund for Return')])[4]");
    protected By orderAmount = By.xpath("//span[contains(text(),'$')]");
    protected By issueRefund = By.xpath("(.//button[normalize-space(.)='Issue Refund'])[2]");
    protected By orderDetailsLabel = By.xpath(".//span[normalize-space(.)='Order Details']");
    protected By orderAdjustmentsValue = By.xpath(".//th[normalize-space(.)='Order Adjustments']/following-sibling::th/div");
    protected By orderSubtotalValue = By.xpath(".//th[normalize-space(.)='Order Subtotal']/following-sibling::th/div");
    protected By shippingValue = By.xpath(".//th[normalize-space(.)='Shipping']/following-sibling::th/div");
    protected By handlingValue = By.xpath(".//th[normalize-space(.)='Handling']/following-sibling::th/div");
    protected By allLineItems = By.xpath(".//*[text()='Items Ordered']//following::tbody[starts-with(@id,'gridview')]/tr");
    protected By removeAllLineItemCoupons = By.xpath(".//*[starts-with(text(),'Order No.')]//following::tbody[starts-with(@id,'gridview')]/tr");
    protected By expandCollapseShipping = By.xpath(".//th[normalize-space(.)='Shipping']/preceding-sibling::th/div");
    protected By shippingFee = By.xpath(".//*[contains(text(),'Order Shipping')]/parent::td/following-sibling::td/div");
    protected By shippingDiscount = By.xpath(".//*[contains(text(),'Shipping Discount')]/parent::td/following-sibling::td/div[starts-with(@class,'price-detail')]");
    protected By calculatedRefund = By.xpath(".//label[normalize-space(.)='Calculcated Refund Amount']//preceding-sibling::input");
    protected By storeCreditBox = By.xpath(".//td[normalize-space(.)='New Store Credit']//following::td[4]");
    protected By storeCreditInput = By.xpath(".//td[normalize-space(.)='New Store Credit']//following::td[4]//input");
    protected By refundedStatus = By.xpath(".//span[text()='Refunded']");
    protected By closeRefundButton = By.xpath("(.//button[normalize-space()='Close'])[1]");
    protected By closeRefundMenu = By.xpath("(.//button[normalize-space()='Close'])[1]//following-sibling::button");
    protected By refundChequeNo = By.xpath(".//input[@placeholder='Check Number']");
    protected By refundChequeNoBox = By.xpath(".//input[@placeholder='Check Number']//following::div[3]");
    protected By refundChequeNoInput = By.xpath(".//input[@placeholder='Check Number']//following::div[3]//input");
    protected By validateButton = By.xpath(".//a[normalize-space(.)='Validate']");
    protected By addressCleansingMSG = By.xpath(".//div[@class='x-form-display-field']");
    protected String itemAmount = ".//*[text()='<<text_replace>>']//following::div[4]";
    protected String itemQty = ".//*[text()='<<text_replace>>']//following::div[5]";
    protected String itemDiscount = ".//*[text()='<<text_replace>>']//following::div[12]";
    protected String discountedItemQty = ".//*[text()='<<text_replace>>']//following::div[13]";
    // Below XPaths are relative, we need incremental values to verify tabular formatted things so to pass dynamic row no. we have divided in two parts.
    // And hence, it seems as like absolute Xpath, but we do concat & use it in the method name: calculatePercentBaseDiscount(double percent)
    protected String lineItemDetailsCommonXpath = ".//*[normalize-space(.)='Items Ordered']//following::tbody/tr[";
    protected String lineItemAmount = "]//tr[1]//td[6]//div";
    protected String lineItemQuantity = "]//tr[1]//td[7]//div";
    // Below XPaths are relative, we need incremental values to verify tabular formatted things so to pass dynamic row no. we have divided in two parts.
    // And hence, it seems as like absolute Xpath, but we do concat & use it in the method name: removeAllLineItemsCoupons()
    protected String removeCouponCommonXpath = ".//*[starts-with(normalize-space(.),'Order No.')]//following::tbody/tr[";
    protected String removeLineitemCoupon = "]//tr[2]//td[9]//div[@class='order-action-icon discount-suppress ']";
    protected String removedLineitemCoupon = "]//tr[2]//td[9]//div[@class='order-action-icon discount-activate ']";
    protected String refundItemQtyBox = "(//*[contains(text(),'<<text_replace>>')])[2]//following::div[8]";
    protected String refundItemQtyInput = "(//*[contains(text(),'<<text_replace>>')])[2]//following::input[1]";
    protected String refundTypeBox = "(//*[contains(text(),'<<text_replace>>')])[2]//following::div[7]";
    protected String refundTypeInput = "(//*[contains(text(),'<<text_replace>>')])[2]//following::input[2]";
    protected String refundReportedIssueBox = "(//*[contains(text(),'<<text_replace>>')])[2]//following::div[6]";
    protected String refundReportedIssueInput = "(//*[contains(text(),'TestShoes')])[2]//following::input[3]";
    protected String selectProduct = "(.//td[contains(normalize-space(.),'<<text_replace>>')]//preceding::td)[4]//input";
    protected String refundQty = ".//td[contains(normalize-space(.),'<<text_replace>>')]//following::td[2]//span";
    protected String priceAndTaxPaid = ".//td[contains(normalize-space(.),'<<text_replace>>')]//following::td[3]//span";
    protected String shPaid = ".//td[contains(normalize-space(.),'<<text_replace>>')]//following::td[4]//span";
    protected String refundSH = ".//td[contains(normalize-space(.),'<<text_replace>>')]//following::td[5]//input";
    protected String refundAmount = "(.//td[contains(normalize-space(.),'<<text_replace>>')]//following::td[8]//div)[1]";

    protected String orderAmountField;
    String streetAddress = "1041 old cassatt rd";
    String city = "berwyn";
    String state = "PA";
    String phoneNumber = "2676704348";
    public String discountAmount;

    public KiboBackOfficeStorePage(WebDriver driver) {
        super(driver);
        this.orderHeader = new KiboBackOfficeStoreHeaderPane(driver, this);
        this.fulfillment = new KiboBackOfficeStoreOrderDetailsFulfillment(driver, this);
        this.orderDetailsDialog = new KiboOrderDetailsDialog(driver, this);
        this.payment = new KiboBackOfficeStoreOrderDetailsPayment(driver, this);
        this.returns = new KiboBackOfficeStoreOrderDetailReturn(driver, this);
        this.editContactsDialog = new KiboMaxineEditContactsDialog(driver, this);
        this.editAddressDialog = new KiboEditContactsEditAddressDialog(driver, this);
    }

    /**
     * locates the customer list arrow
     *
     * @return customer list arrow WebElement
     */
    protected WebElement findTriggerButton() {
        WebElement listArrowContainer = wait.waitForElementPresent(listTriggerContainer);

        WebElement triggerLabels = listArrowContainer.findElement(listTriggerClass);

        return triggerLabels;
    }

    /**
     * uses the getter method to locate the customer list arrow and then clicks it
     */
    public void clickCustomerList() {
        waitForPageLoad();
        WebElement arrow = findTriggerButton();
        arrow.click();
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                System.out.println("Load mask isn't present");
            }
        }
    }

    /**
     * locates and selects the desired customer from the list
     *
     * @param customer Enum  passed from enum class
     */
    public void selectCustomer(KiboCustomers customer) {
        waitForPageLoad();
        String customerLoc = customer.value;
        WebElement tester = wait.waitForElementPresent(By.cssSelector(customerLoc));
        tester.click();
    }

    /**
     * getter method to locate the create new customer button
     *
     * @return new customer button WebElement
     */
    protected WebElement getCreateNewCustomerButton() {
        String expectedText = "Create New Customer";
        WebElement createNewCustomerButton = null;

        List<WebElement> newCustomerContainers = wait.waitForAllElementsPresent(newCustomerButtonLoc);
        createNewCustomerButton = element.selectElementByText(newCustomerContainers, expectedText);

        return createNewCustomerButton;
    }

    /**
     * uses the getter method to locate the create new customer button and clicks on it
     * not in use at the time
     */
    public void clickCreateNewCustomerButton() {
        WebElement createNewCustomerButton = getCreateNewCustomerButton();
        createNewCustomerButton.click();
    }

    /**
     * getter method to locate the edit details button
     *
     * @return edit details button WebElement
     */
    protected WebElement findEditDetails() {
        String expectedText = "Edit Details";
        WebElement editDetailButton = null;

        List<WebElement> editDetailContainers = wait.waitForAllElementsPresent(orderDetailButton);
        editDetailButton = element.selectElementByText(editDetailContainers, expectedText);

        return editDetailButton;
    }

    /**
     * uses the getter method to locate the edit details button and then clicks it
     */
    public void clickEditDetails() {
        WebElement editDetailsButton = findEditDetails();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", editDetailsButton);
    }

    /**
     * uses the method to cancel button
     */
    public void clickCancelButton() {
        WebElement cancelButton = wait.waitForElementDisplayed(crossButton);
        click.javascriptClick(cancelButton);
    }

    /**
     * uses the getter method to locate the save Address details button and then clicks it
     */
    public void clickSaveAddressDetail() {
        WebElement saveButton = wait.waitForElementPresent(SaveAddressButtons);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", saveButton);
    }

    /**
     * uses the method to click on Bill to address.
     */
    public void clickBillToAddress() {
        wait.waitForAnyElementsDisplayed(billToButton);
        List buttonList = driver.findElements(billToButton);
        if (buttonList.size() > 1) {
            WebElement billToAddressButton = (WebElement) buttonList.get(1);
            click.moveToElementAndClick(billToAddressButton);
        }
    }

    /**
     * selects customer's contact ship to radio button
     */
    public void clickShipToAddress() {
        WebElement csShip = wait.waitForElementEnabled(customerContactShipToButton);
        if (csShip.isDisplayed() && !csShip.isSelected()) {
            click.moveToElementAndClick(csShip);
        }
    }

    /**
     * uses the method to locate saves details Button.
     */
    public void clickSaveDetails() {
        WebElement saveDetailsButton = wait.waitForElementDisplayed(SaveButton);
        click.javascriptClick(saveDetailsButton);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(saveDetailsButton));
        wait.waitForElementEnabled(findEditDetails());
        waitForPageLoad();
    }

    /**
     * uses the method to locate save the order details
     */
    public void clickSavesDetails() {
        WebElement saveOrderDetails = wait.waitForElementDisplayed(SaveButtons);
        click.moveToElementAndClick(saveOrderDetails);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(saveOrderDetails));
        wait.waitForElementEnabled(findEditDetails());
        waitForPageLoad();
    }

    /**
     * uses the method to click on save button.
     */
    public void clickSaveButton() {
        List buttonList = driver.findElements(saveAddressButton);
        WebElement saveButton = (WebElement) buttonList.get(1);
        click.javascriptClick(saveButton);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(saveButton));
        wait.waitForElementEnabled(findEditDetails());
        waitForPageLoad();
    }

    /**
     * uses the getter method to locate the edit details button and then clicks it
     */
    public void clickEditButton() {
        WebElement editDetailsButton = wait.waitForElementDisplayed(EditButton);

        click.moveToElementAndClick(editDetailsButton);
    }

    /**
     * uses the method to enter address line
     *
     * @param address1
     */
    public void enterAddressLine(String address1) {
        WebElement salutationNameField = wait.waitForElementDisplayed(AddressLine);
        text.enterText(salutationNameField, address1);
    }

    /**
     * uses the method to enter address city.
     *
     * @param Address
     */
    public void enterAddressCity(String Address) {
        WebElement salutationNameField = wait.waitForElementDisplayed(AddressCity);
        text.enterText(salutationNameField, Address);
    }

    /**
     * uses the method to enter address state.
     *
     * @param Address
     */
    public void enterAddressState(String Address) {
        WebElement addressState = wait.waitForElementDisplayed(AddressState);
        text.enterText(addressState, Address);
    }

    /**
     * uses the method to enter zip address.
     *
     * @param Address
     */
    public void enterAddressZip(String Address) {
        WebElement zipCode = wait.waitForElementDisplayed(AddressZip);
        text.enterText(zipCode, Address);
    }

    /**
     * uses the method to enter country code.
     *
     * @param Address
     */
    public void enterCountryCode(String Address) {
        WebElement countryCd = wait.waitForElementDisplayed(CountryCode);
        text.enterText(countryCd, Address);
        text.pressEnter(countryCd);
    }

    /**
     * Used to enter refund product, product quantities, Type and Reported Issue.
     * However, user can only pass product name & quantity because other things are handled as default value in this method.
     * example1: kiboBackOfficeStorePage.enterQuantityToBeReturned("TestShoes", "1", "Refund", "Damaged"); // To set all values by full user driven approach
     * example2: kiboBackOfficeStorePage.enterQuantityToBeReturned("Tshirt", "2"); // To set all values but partially user-driven & rest things program driven.
     *
     * @param refundDetails Parameters must be passed in the given sequence: Product Name, Quantity, Refund Type, Reported issue for refund.
     */
    public void enterQuantityToBeReturned(String... refundDetails) {
        if (refundDetails.length < 2 | refundDetails.length > 4) {
            Assert.fail("Atleast pass product name & Quantity which should be refunded." +
                    "Rest things are handled inside the method!" +
                    "Also, only total 4 params are required for this purpose.");
        }
        if (refundDetails.length == 2) {
            refundDetails = new String[]{refundDetails[0], refundDetails[1],
                    KiboData.REFUND_TYPE_REFUND.value, KiboData.REFUND_REPORTED_ISSUE_OTHER.value};
        }
        if (refundDetails.length == 3) {
            refundDetails = new String[]{refundDetails[0], refundDetails[1], refundDetails[2],
                    KiboData.REFUND_REPORTED_ISSUE_OTHER.value};
        }
        WebElement refundBox = wait.waitForElementPresent(By.xpath(refundItemQtyBox.replace("<<text_replace>>", refundDetails[0])));
        click.moveToElementAndClick(refundBox);
        WebElement refundQtyInput = wait.waitForElementEnabled(By.xpath(refundItemQtyInput.replace("<<text_replace>>", refundDetails[0])));
        click.moveToElementAndClick(refundQtyInput);
        text.enterText(refundQtyInput, refundDetails[1]);
        WebElement typeBox = wait.waitForElementPresent(By.xpath(refundTypeBox.replace("<<text_replace>>", refundDetails[0])));
        click.moveToElementAndClick(typeBox);
        WebElement typeInput = wait.waitForElementEnabled(By.xpath(refundTypeInput.replace("<<text_replace>>", refundDetails[0])));
        text.enterText(typeInput, refundDetails[2]);
        text.pressEnter(typeInput);
        WebElement issueBox = wait.waitForElementPresent(By.xpath(refundReportedIssueBox.replace("<<text_replace>>", refundDetails[0])));
        click.moveToElementAndClick(issueBox);
        WebElement issueInput = wait.waitForElementEnabled(By.xpath(refundReportedIssueInput.replace("<<text_replace>>", refundDetails[0])));
        text.enterText(issueInput, refundDetails[3]);
        text.pressTab(issueInput);
    }

    /**
     * getter method to locate the change address button
     */
    protected WebElement getChangeAddressButton() {
        WebElement changeAddressButtonContainer = driver.findElement(changeAddressContainerLoc);
        WebElement changeAddressButton = changeAddressButtonContainer.findElement(changeAddressLoc);
        return changeAddressButton;
    }

    /**
     * uses the getter method to locate the change address button and click on it
     * which triggers the change address dialog
     */
    public void clickChangeAddress() {
        do {
            WebElement changeAddressButton = getChangeAddressButton();
            click.javascriptClick(changeAddressButton);
        }
        while (!element.isElementDisplayed(elemToAppearLoc));

        waitForPageLoad();
    }

    /**
     * function to verify the calculated shipping  amount and compare it to the expected one
     *
     * @return String order Total value
     */
    public String getOrderTotal() {
        WebElement orderTotalContainer;

        orderTotalContainer = wait.waitForElementPresent(orderTotalContainerLoc);

        WebElement orderTotal = wait.waitForElementPresent(orderTotalLoc, orderTotalContainer);
        String orderTotalValue = orderTotal.getText();

        return orderTotalValue;
    }

    /**
     * function to get the actual tax amount calculated for the order
     *
     * @return connector tax estimate for the order in a String
     */
    public String getTaxAmount() {
        waitForPageLoad();
        WebElement taxAndDutyId = wait.waitForElementDisplayed(taxAndDuty);
        WebElement taxAmountField = wait.waitForElementDisplayed(taxAmountClass, taxAndDutyId);
        return text.getElementText(taxAmountField).replace(",", "");
    }

    /**
     * Counts discounted amount of percentage based tax
     *
     * @param taxPercent need to pass percentage of percentage based tax
     * @return calculated tax amount based on percentage
     */
    public String calculatePercentageBasedTax(double taxPercent) {
        waitForPageLoad();
        double shipping = 0;
        wait.waitForElementPresent(orderDetailsLabel);
        if (wait.waitForElementPresent(shippingValue).isDisplayed()) {
            shipping = Double.parseDouble(wait.waitForElementPresent(shippingValue).getText().replace("$", ""));
        }
        double total = (Double.parseDouble(wait.waitForElementPresent(orderSubtotalValue).getText()
                .replace("$", "").replace(",", ""))
                + shipping) * (taxPercent / 100);
        return "$" + String.format("%.2f", total);
    }

    /**
     * Calculate tax only on shipping amount (Used in product code excempt test-cases)
     *
     * @param taxPercent pass percentage of percentage based tax
     * @return calculated tax amount based on percentage
     */
    public String calculatePercentBaseTaxOnShippingOnly(double taxPercent) {
        waitForPageLoad();
        double taxOnShip = 0;
        wait.waitForElementPresent(orderDetailsLabel);
        wait.waitForElementPresent(shippingValue).isDisplayed();
        taxOnShip = Double.parseDouble(wait.waitForElementPresent(shippingValue).getText().replace("$", ""))
                * (taxPercent / 100);
        return "$" + String.format("%.2f", taxOnShip);
    }

    /**
     * This method calculates individual ordered item's total value (inclusive of: applied tax, shipping charge, handling charge, adjustment if any)
     * Purpose of this method is to be used for refund related cases
     *
     * @param itemName   pass product name which total price to be calculated
     * @param taxPercent pass tax percent which is applicable to order.
     * @return calculated total amount of individual item
     */
    public double calculateIndividualProductsTotalAmount(String itemName, double taxPercent, boolean isSH) {
        waitForPageLoad();
        wait.waitForElementPresent(orderDetailsLabel);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SaveButton));
        wait.waitForElementEnabled(findEditDetails());
        double itemBasePrice = 0;
        double shipping = 0;
        double adjustment = 0;
        double handling = 0;
        double allProductQuantity = 0;
        double wholePrice = 0;
        wait.waitForElementPresent(By.xpath(itemAmount.replace("<<text_replace>>", itemName)));
        itemBasePrice = Double.parseDouble(text.getElementText(By.xpath(itemAmount.replace("<<text_replace>>", itemName)))
                .replace("$", "").replace(",", ""));
        wait.waitForAllElementsPresent(allLineItems);
        int size = element.getWebElements(allLineItems).size();
        for (int i = 1; i <= size; i++) {
            wait.waitForElementPresent(By.xpath(lineItemDetailsCommonXpath + i + lineItemQuantity));
            allProductQuantity = allProductQuantity + (Double.parseDouble(text.getElementText(By.xpath(lineItemDetailsCommonXpath + i + lineItemQuantity))
                    .replace("$", "").replace(",", "")));
        }
        if (wait.waitForElementPresent(orderAdjustmentsValue).isDisplayed()) {
            adjustment = Double.parseDouble(wait.waitForElementPresent(orderAdjustmentsValue).getText().replace("$", "")
                    .replace("(", "").replace(")", "")) / allProductQuantity;
        }
        if (isSH) {
            if (wait.waitForElementPresent(shippingValue).isDisplayed()) {
                shipping = Double.parseDouble(wait.waitForElementPresent(shippingValue).getText().replace("$", "")) / allProductQuantity;
            }
            if (wait.waitForElementPresent(handlingValue).isDisplayed()) {
                handling = Double.parseDouble(wait.waitForElementPresent(handlingValue).getText().replace("$", "")
                        .replace("(", "").replace(")", "")) / allProductQuantity;
            }
        }
        wholePrice = itemBasePrice + shipping + handling - adjustment;
        wholePrice = wholePrice + (wholePrice * (taxPercent / 100));
        return wholePrice;
    }

    /**
     * Clicks on payments tab to go to payments option
     */
    public void gotoPaymentsTab() {
        WebElement payment = wait.waitForElementDisplayed(paymentTab);
        click.javascriptClick(payment);
        wait.waitForElementPresent(paymentTabSelected);
        waitForPageLoad();
    }

    /**
     * get a Discount Value for the order
     *
     * @return discount rate
     */
    public String getDiscountValue() {
        try {
            WebElement discount = driver.findElement(discountSummary);
            click.javascriptClick(discount);
            discountAmount = text.getElementText(discountValue).replaceAll("[^0-9]", "");
        } catch (Exception e) {
            discountAmount = "0";
        }
        return discountAmount;
    }

    /**
     * Verify amount or percent based coupon(s) applicable on line-item.
     * example1: kiboBackOfficeStorePage.verifyLineitemDiscount(true, "TestShoes", 5); // pass true as parameter to verify percentage based coupon(s).
     * example2: kiboBackOfficeStorePage.verifyLineitemDiscount(false, "TestShoes", 10); // pass false as parameter to verify amount based coupon(s).
     *
     * @param itemName        pass line-item name which values should be verified
     * @param amountOrPercent pass coupon's discount rate or discount percentage
     * @return true or false based on verification condition
     */
    public boolean verifyLineitemDiscount(boolean percentage, String itemName, double amountOrPercent) {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SaveButton));
        wait.waitForElementEnabled(findEditDetails());
        boolean isVerified = false;
        double calculatedDiscount = 0;
        double uiDiscount = 0;
        wait.waitForElementPresent(By.xpath(itemDiscount.replace("<<text_replace>>", itemName)));
        wait.waitForElementPresent(By.xpath(discountedItemQty.replace("<<text_replace>>", itemName)));
        wait.waitForElementPresent(By.xpath(itemQty.replace("<<text_replace>>", itemName)));
        wait.waitForElementPresent(By.xpath(itemAmount.replace("<<text_replace>>", itemName)));
        if (percentage) {
            calculatedDiscount = Double.parseDouble(text.getElementText(By.xpath(itemAmount.replace("<<text_replace>>", itemName)))
                    .replace("$", "").replace(",", ""))
                    * Double.parseDouble(text.getElementText(By.xpath(itemQty.replace("<<text_replace>>", itemName))))
                    * (amountOrPercent / 100);
            uiDiscount = Double.parseDouble(text.getElementText(By.xpath(itemDiscount.replace("<<text_replace>>", itemName)))
                    .replace("($", "").replace(")", ""));
            if (uiDiscount == calculatedDiscount) {
                isVerified = true;
            }
        } else {
            double baseDiscount = Double.parseDouble(text.getElementText(By.xpath(itemDiscount.replace("<<text_replace>>", itemName)))
                    .replace("($", "").replace(")", ""))
                    / Double.parseDouble(text.getElementText(By.xpath(discountedItemQty.replace("<<text_replace>>", itemName))));
            if (amountOrPercent == baseDiscount) {
                isVerified = true;
            }
        }
        return isVerified;
    }

    /**
     * Verify shipping discount
     *
     * @param expectedShipDiscount pass expected shipping discount amount
     * @return true or false based on verification condition
     */
    public boolean verifyShippingDiscount(double expectedShipDiscount) {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SaveButton));
        wait.waitForElementEnabled(findEditDetails());
        boolean isVerified = false;
        wait.waitForElementPresent(shippingValue);
        if (!element.isElementDisplayed(shippingFee)) {
            wait.waitForElementPresent(expandCollapseShipping);
            click.moveToElementAndClick(expandCollapseShipping);
        }
        wait.waitForElementPresent(shippingDiscount);
        wait.waitForElementPresent(shippingFee);
        if (Double.parseDouble(text.getElementText(shippingDiscount).replace("($", "").replace(")", ""))
                == expectedShipDiscount
                && Double.parseDouble(text.getElementText(shippingValue).replace("$", ""))
                == (Double.parseDouble(text.getElementText(shippingFee).replace("$", "")) - expectedShipDiscount)) {
            isVerified = true;
        }
        return isVerified;
    }

    /**
     * Calculates single or multiple line-items price & calculates percent based discount
     *
     * @param percent pass percent which should be calculated
     * @return calculated discount based on percent base coupons
     */
    public String calculatePercentBaseDiscount(double percent) {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SaveButton));
        wait.waitForAllElementsPresent(allLineItems);
        int size = element.getWebElements(allLineItems).size();
        double itemTotal = 0;
        for (int i = 1; i <= size; i++) {
            wait.waitForElementPresent(By.xpath(lineItemDetailsCommonXpath + i + lineItemAmount));
            wait.waitForElementPresent(By.xpath(lineItemDetailsCommonXpath + i + lineItemQuantity));
            itemTotal = itemTotal + (Double.parseDouble(text.getElementText(By.xpath(lineItemDetailsCommonXpath + i + lineItemAmount))
                    .replace("$", "").replace(",", ""))
                    * Double.parseDouble(text.getElementText(By.xpath(lineItemDetailsCommonXpath + i + lineItemQuantity))));
        }
        return "$" + String.format("%.2f", itemTotal * (percent / 100));
    }

    /**
     * Removes all line-item's coupon code.
     */
    public void removeAllLineItemsCoupons() {
        waitForPageLoad();
        wait.waitForElementPresent(removeAllLineItemCoupons);
        int size = element.getWebElements(removeAllLineItemCoupons).size();
        for (int i = 1; i <= size; i++) {
            wait.waitForElementPresent(By.xpath(removeCouponCommonXpath + i + removeLineitemCoupon));
            click.moveToElementAndClick(By.xpath(removeCouponCommonXpath + i + removeLineitemCoupon));
            waitForPageLoad();
            wait.waitForElementPresent(By.xpath(removeCouponCommonXpath + i + removedLineitemCoupon));
        }
        waitForPageLoad();
    }

    /**
     * Get total applied discount from UI
     *
     * @return discounted amount from UI
     */
    public String getDiscountFromUI() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SaveButton));
        wait.waitForElementEnabled(findEditDetails());
        return text.getElementText(orderAdjustmentsValue).replace("(", "").replace(")", "");
    }

    /**
     * This Method looks for the Save& Create Address button by the text contained
     * in it,after finding its container.
     *
     * @return returns the Save& Create Address button element.
     */
    protected WebElement getSaveAndCreateAddress() {
        WebElement newCustomerAddress = null;
        List<WebElement> saveAndCreateAddressContainers = wait.waitForAllElementsPresent(
                saveAndCreateAddressContainerLoc);

        for (WebElement container : saveAndCreateAddressContainers) {
            List<WebElement> subContainers = null;
            String expectedText = "Save & Create Address";

            try {
                wait.waitForElementPresent(saveCreateAddress, container, 5);
                subContainers = container.findElements(saveCreateAddress);
            } catch (TimeoutException | NoSuchElementException e) {
                String noChildrenContainerMessage = String.format(
                        "No Save&CreateAddress Button inside the %dth container");

                VertexLogger.log(noChildrenContainerMessage);
                subContainers = new ArrayList<WebElement>();
            }

            newCustomerAddress = element.selectElementByText(subContainers, expectedText);
            if (newCustomerAddress != null) {
                break;//fixme scott added this because it seemed to belong here, but check this first if the function breaks
            }
        }
        return newCustomerAddress;
    }

    /**
     * uses the method to locate the save and create address button and then clicks on it
     */
    public void clickSaveAndCreateAddressButton() {
        WebElement newCustomerAddress = getSaveAndCreateAddress();
        wait.waitForElementEnabled(newCustomerAddress);
        newCustomerAddress.click();
    }

    /**
     * to fill in the address fields
     */
    public void addressFields() {
        WebElement streetField = wait.waitForElementPresent(address1Field);

        streetField.clear();
        streetField.sendKeys(streetAddress);

        WebElement cityField = wait.waitForElementPresent(city1Field);
        cityField.clear();
        cityField.sendKeys(city);
        WebElement stateField = wait.waitForElementPresent(state1Field);
        stateField.clear();
        stateField.sendKeys(state);
        WebElement zipField = wait.waitForElementPresent(zip1Field);
        zipField.clear();
        zipField.sendKeys(state);
        WebElement phoneField = wait.waitForElementPresent(phone1Field);
        phoneField.clear();
        phoneField.sendKeys(phoneNumber);
    }

    /**
     * getter method to locate the validate address button
     *
     * @return validate address button WebElement
     */
    protected WebElement getAddressValidationButton() {
        WebElement validateButton = null;
        String expectedText = "Validate";
        List<WebElement> validateButtonContainers = wait.waitForAllElementsPresent(addressValidationButtonLoc);

        validateButton = element.selectElementByText(validateButtonContainers, expectedText);

        return validateButton;
    }

    /**
     * uses the method to locate the validate address button and then clicks it
     */
    public void clickValidateButton() {
        WebElement validateButton = getAddressValidationButton();
        validateButton.click();
    }

    /**
     * Clicks on validate button to cleanse address for Ship To
     */
    public void clickValidateAddress() {
        WebElement validate = wait.waitForElementPresent(validateButton);
        click.moveToElementAndClick(validate);
    }

    /**
     * uses the method to click Fulfillment Button.
     */
    public void clickFulfillmentButton() {
        WebElement fulfillment = wait.waitForElementDisplayed(fulFillmentButton);
        click.javascriptClick(fulfillment);
        wait.waitForElementPresent(fulfillmentSelected);
        waitForPageLoad();
    }

    /**
     * uses the method to click MoveToButton
     */
    public void clickMoveToButton() {
        WebElement moveButton = wait.waitForElementDisplayed(moveToButton);
        click.javascriptClick(moveButton);
        waitForPageLoad();
    }

    /**
     * uses the method to click New Package Button
     */
    public void clickNewPackageButton() {
        WebElement newPackagesButton = wait.waitForElementDisplayed(newPackageButton);
        click.javascriptClick(newPackagesButton);
        waitForPageLoad();
        wait.waitForElementEnabled(markAsShippedButton);
    }

    /**
     * uses the method to click marked as Shipped Button.
     */
    public void clickMarkedAsShippedButton() {
        WebElement markedAsShippedButton = wait.waitForElementDisplayed(markAsShippedButton);
        click.javascriptClick(markedAsShippedButton);
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(markAsShippedButton));
    }

    /**
     * uses the method to click return button.
     */
    public void clickReturnButton() {
        WebElement returnButtons = wait.waitForElementDisplayed(returnButton);
        click.javascriptClick(returnButtons);
        wait.waitForElementPresent(returnSelected);
        waitForPageLoad();
    }

    /**
     * uses the method to click create return Button.
     */
    public void clickCreateReturnButton() {
        WebElement createReturn = wait.waitForElementDisplayed(createReturnButton);
        click.javascriptClick(createReturn);
        waitForPageLoad();
        wait.waitForElementEnabled(createRefundButton);
    }

    /**
     * uses the method to click on Refund Button
     */
    public void clickRefundButton() {
        WebElement refundButton = wait.waitForElementDisplayed(createRefundButton);
        click.javascriptClick(refundButton);
    }

    /**
     * uses the method to click Issue Refund Button.
     */
    public void clickIssueRefundButton() {
        WebElement issueRefund = wait.waitForElementPresent(issueRefundButton);
        click.javascriptClick(issueRefund);
        waitForPageLoad();
        wait.waitForElementPresent(refundForReturnLabel);
    }

    /**
     * uses the method to click on IssueRefunds Button to confirm refund quantity.
     */
    public void clickIssueRefunds() {
        WebElement issueRefundsButton = wait.waitForElementPresent(issueRefund);
        click.moveToElementAndClick(issueRefundsButton);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(issueRefundsButton));
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(refundForReturnLabel));
        waitForPageLoad();
    }

    /**
     * get Adjustment amount for order.
     *
     * @ return adjustment amount for order.
     */
    public double getAdjustmentsOrderAmounts() {
        WebElement orderAmountValue = wait.waitForElementDisplayed(orderAmount);
        orderAmountField = text.getElementText(orderAmountValue).replaceAll("[^0-9]", "");
        double priceDoubleAmount = Double.parseDouble(orderAmountField);
        double Amounts = priceDoubleAmount / 100;
        return Amounts;
    }

    /**
     * perform Adjustment for order.
     *
     * @param product  product name which is to be refunded
     * @param quantity quantity to be refunded while Adjustment
     */
    public void fulfillOrderAndGotoReturn() {
        clickFulfillmentButton();
        clickMoveToButton();
        clickNewPackageButton();
        clickMarkedAsShippedButton();
        clickReturnButton();
    }

    /**
     * This method will create refund and will open issue refund
     */
    public void createAndssueRefund() {
        clickCreateReturnButton();
        clickRefundButton();
        clickIssueRefundButton();
    }

    /**
     * Used to verify & enter refund amount to store credit
     * example1: kiboBackOfficeStorePage.enterRefundAmountForStoreCredit("Tshirt", true); // true to refund shipping & handling fee (S&H Paid)
     * example2: kiboBackOfficeStorePage.enterRefundAmountForStoreCredit("Tshirt", false); // false not to refund shipping & handling fee (S&H Paid)
     *
     * @param productName pass product name which amount should be credited back
     * @param isSHRefund  pass true or false to refund or not to refund S&H Paid fee
     */
    public void enterRefundAmountForStoreCredit(String productName, boolean isSHRefund) {
        waitForPageLoad();
        wait.waitForElementPresent(refundForReturnLabel);
        boolean isVerified = false;
        double expectedRefund = 0;
        String finalRefundAmt;
        WebElement selectItem = wait.waitForElementEnabled(By.xpath(selectProduct.replace("<<text_replace>>", productName)));
        if (!selectItem.isSelected()) {
            click.moveToElementAndClick(selectItem);
        }
        WebElement qty = wait.waitForElementPresent(By.xpath(refundQty.replace("<<text_replace>>", productName)));
        WebElement ptPaid = wait.waitForElementPresent(By.xpath(priceAndTaxPaid.replace("<<text_replace>>", productName)));
        expectedRefund = expectedRefund + (Double.parseDouble(text.getElementText(qty))
                * Double.parseDouble(text.getElementText(ptPaid).replace("$", "").replace(",", "")));
        WebElement shRefund = wait.waitForElementPresent(By.xpath(refundSH.replace("<<text_replace>>", productName)));
        if (isSHRefund) {
            if (!shRefund.isSelected()) {
                click.javascriptClick(shRefund);
            }
            expectedRefund = expectedRefund
                    + Double.parseDouble(text.getElementText(By.xpath(shPaid.replace("<<text_replace>>", productName))).replace("$", ""));
        } else if (!isSHRefund && shRefund.isSelected()) {
            click.javascriptClick(shRefund);
        }
        WebElement refundAmt = wait.waitForElementPresent(By.xpath(refundAmount.replace("<<text_replace>>", productName)));
        finalRefundAmt = wait.waitForElementPresent(calculatedRefund).getAttribute("value");
        if (!("$" + String.format(".2f", expectedRefund)).equals(refundAmt.getText())
                && refundAmt.getText().equals(finalRefundAmt)) {
            Assert.fail("Refund amount is not matching calculation expections!");
        }
        wait.waitForElementPresent(storeCreditBox);
        click.moveToElementAndClick(storeCreditBox);
        WebElement creditRefund = wait.waitForElementEnabled(storeCreditInput);
        text.enterText(creditRefund, finalRefundAmt.replace("$", ""));
        text.pressTab(creditRefund);
    }

    /**
     * Helps to mark close refund request
     */
    public void markCloseRefund() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(closeRefundMenu));
        wait.waitForElementPresent(refundedStatus);
        WebElement close = wait.waitForElementEnabled(closeRefundButton);
        click.moveToElementAndClick(close);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(close));
        waitForPageLoad();
        wait.waitForElementPresent(refundedStatus);
    }

    /**
     * Used to verify & enter refund amount to store credit
     * example1: kiboBackOfficeStorePage.processRefundForReturn("Tshirt", true); // true to refund shipping & handling fee (S&H Paid)
     * example2: kiboBackOfficeStorePage.processRefundForReturn("Tshirt", false); // false not to refund shipping & handling fee (S&H Paid)
     *
     * @param product  pass product name which amount should be credited back
     * @param refundSH pass true or false to refund or not to refund S&H Paid fee
     */
    public void processRefundForReturn(String product, boolean refundSH) {
        enterRefundAmountForStoreCredit(product, refundSH);
        clickIssueRefunds();
        markCloseRefund();
    }

    /**
     * This method takes parameter as multiple individual product's total price & individual product's refund quantity
     * And perfoems expected refund amount's calculation & compares it with actual refunded amount from UI.
     * For accurate calculation consider below explaination:
     * Let's suppose there are 2 products which needs to be refunded: (Product1 = Tshirt and Product2 = Shoes and base prices are: p1=$200, p2=$300),
     * Ordered quantities of Product1 = 5 & Product2 = 1
     * Intended Refund quantities of p1 = 3 & p2 = 1
     * Total prices (Inclusive of S&H, Tax, Adjustment if any): p1 = $223.15, p2 = $335.03
     * Then must have to follow the sequence as like: {total amount of p1, total amount of p2}, {refund quantities of p1, refund quantities of p2}
     * considering above example parameters should be: {223.15, 335.03} and {"3", "1"}
     * Here quantities taken as String array because we are maintaining quantities in our enum files as String & we are converting them inside the method to avoid duplication
     *
     * @param productsWholePrice pass individual products' total price (like: {108.4, 53.99})
     * @param quantities         pass intended refund product's quantities (like: {"3", "1"})
     * @return true or false based on verification condition.
     */
    public boolean verifyRefundedAmount(double[] productsWholePrice, String[] quantities) {
        if (productsWholePrice.length != quantities.length) {
            Assert.fail("Length of products & quantities must be same");
        }
        By totalAmountRefunded = By.xpath("(.//label[normalize-space(.)='Amount Refunded']//following-sibling::p)[1]");
        double calculatedRefund = 0;
        boolean isVerified = false;
        for (int i = 0; i < productsWholePrice.length; i++) {
            calculatedRefund = calculatedRefund + (productsWholePrice[i] * Double.parseDouble(quantities[i]));
        }
        if (wait.waitForElementPresent(refundedStatus).isDisplayed()
                && !wait.waitForElementPresent(closeRefundButton).isDisplayed()
                && text.getElementText(totalAmountRefunded).replace("$", "").replace(",", "").split("\\.")[0]
                .equalsIgnoreCase(String.valueOf(calculatedRefund).split("\\.")[0])) {
            isVerified = true;
        }
        return isVerified;
    }

    /**
     * click Save and Submit Order Button.
     */

    public void clickSaveAndSubmitOrderButton() {
        clickChangeAddress();
        clickBillToAddress();
        clickShipToAddress();
        clickSaveDetails();
        orderHeader.clickSubmitOrder();
    }
}