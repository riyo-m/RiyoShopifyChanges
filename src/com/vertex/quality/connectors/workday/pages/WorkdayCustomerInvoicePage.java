package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * this class represents the workday customer invoice page
 * contains all the methods necessary to interact with the page
 *
 * @author dpatel
 */

public class WorkdayCustomerInvoicePage extends VertexPage {

    protected By salesItem = By.xpath("(//tbody//tr[@tabindex='0']//input[@data-uxi-widget-type='selectinput'])[1]");
    protected By submit = By.cssSelector("button[title='Submit']");
    protected By maximizeSalesSection = By.xpath("//div[@data-automation-id='gridFullscreenIconButton']");
    protected By quantityView = By.xpath("(//div[contains(@data-automation-id,'numericWidget')])[1]");
    protected By quantityInput = By.xpath("(//div[contains(@data-automation-id,'numericWidget')])[1]//input");
    protected By deferredRevenueStatus = By.cssSelector("input[type='checkbox']");
    protected By deferredRevenueCheckbox = By.cssSelector("div[data-automation-id='checkboxPanel']");
    protected By defaultTaxCode = By.xpath("//label[text()='Default Tax Code']");
    protected By lexCorp = By.xpath("(//p[@title='LexCorp'])[1]");
    protected By conners = By.xpath("(//p[@title='Conners & Myers'])[1]");
    protected By net = By.xpath("//p[@data-automation-label='Net 30']");
    protected By amountInfo = By.xpath("(//span[@title='Amount Information'])[1]");
    protected By billTo = By.xpath("(//input[@placeholder='Search'])[3]");
    protected By company = By.xpath("(//input[contains(@dir,'ltr')])[1]");
    protected By tax = By.xpath("(//div[text()='Tax'])[1]");
    protected By deleteAddress = By.xpath("(//div[@data-automation-id='DELETE_charm'])[18]");
    protected By shippingAddress = By.xpath("(//div[@data-automation-id='multiSelectContainer'])[19]");
    protected By removeAddress = By.xpath("(//span[@data-automation-id='promptSearchButton'])[1]");
    protected By addressInput = By.xpath("//div[@data-automation-label='Ship-To Addresses']");
    protected By addInvoiceLine = By.xpath("//button[@title='Add Row']");
    protected By lineItem = By.xpath("//table[@class='mainTable']//tbody//tr");
    protected By done = By.xpath("//span[@title='Done']");
    protected By quantity1 = By.xpath("(//table[@class='mainTable']//tbody//tr[1]//div[text()='1'])[1]");
    protected By quantityText1 = By.xpath("(//table[@class='mainTable']//tbody//tr[1]//div[@data-automation-id='numericText'])[1]");
    protected By unitPrice = By.xpath("(//table[@class='mainTable']//tbody//tr[1]//input)[5]");
    protected By unitPriceClick = By.xpath("(//table[@class='mainTable']//tbody//tr[1]//div[@data-automation-id='numericText'])[3]");
    protected By addressBy = By.xpath("//table[@class='mainTable']//td[22]//div[@data-automation-id=\"multiselectInputContainer\"]");
    protected By shipToAdd = By.xpath("//div[@data-automation-label='Ship-To Addresses']");
    protected By shipToAddHeader = By.xpath("(//input[@dir='ltr'])[9]");
    protected By viewInvoiceDetails = By.xpath("//button[contains(text(), 'View Details')]");

    public WorkdayCustomerInvoicePage(WebDriver driver) {
        super(driver);
    }

    /**
     * This is a generic method to send values to any field in Customer Invoice Page.
     * It waits dynamically till the value's been loaded in the field
     *
     * @param value values to be entered
     */
    public void enterBillToCustomer(String value) {
        WebElement textBox = wait.waitForElementPresent(billTo);
        click.javascriptClick(textBox);
        text.enterText(textBox, value);

        //text.clickElementAndEnterText(textBox, value);
        click.performDoubleClick(wait.waitForElementDisplayed(amountInfo));
        if (value.equals("LexCorp")) {
            wait.waitForElementDisplayed(lexCorp);
        } else if (value.equals("Conners & Myers")) {
            wait.waitForElementDisplayed(conners);
        } else {
            wait.waitForElementDisplayed(net);
        }
    }

    /**
     * This method locate and send values to "sales Item" field
     *
     * @param salesProduct name of the sales Item
     */
    public void enterSalesItem(String salesProduct) {
        int numbOfLines = element.getWebElements(lineItem).size();
        String salesItemEle;
        if (element.isElementPresent(quantity1)) {
            salesItemEle = String.format("(//table[@class='mainTable']//tbody//tr[%s]//input)[2]", numbOfLines);
        } else {
            salesItemEle = String.format("(//table[@class='mainTable']//tbody//tr[%s]//input)[1]", numbOfLines);
        }
        String quantityEle = String.format("(//table[@class='mainTable']//tbody//tr[%s]//div[text()='1'])[1]", numbOfLines);
        WebElement sales = wait.waitForElementPresent(By.xpath(salesItemEle));
        scroll.scrollElementIntoView(sales);
        hover.hoverOverElement(sales);
        click.javascriptClick(sales);
        text.enterText(sales, salesProduct);
        text.pressTab(sales);
        wait.waitForElementPresent(By.xpath(quantityEle), 10);
    }

    /**
     * This method locate and send values to "sales Item" field
     *
     * @param salesProduct name of the sales Item
     */
    public void enterSalesForm(String salesProduct) {
        WebElement sales = wait.waitForElementPresent(salesItem);
        scroll.scrollElementIntoView(sales);
        text.enterText(sales, salesProduct);
        jsWaiter.waitForLoadAll();
        sales.sendKeys(Keys.ENTER);
        jsWaiter.waitForLoadAll();
        sales.sendKeys(Keys.ENTER);
        jsWaiter.waitForLoadAll();
        sales.sendKeys(Keys.TAB);
    }

    /**
     * This method locates and click on Maximize sales Item window in Customer Invoice Page
     * It dynamically waits till submit button not displayed
     */
    public void clickOnMaximizeSalesScreen() {

        wait.waitForElementEnabled(maximizeSalesSection);
        try {
            click.clickElementCarefully(maximizeSalesSection);
            wait.waitForElementNotDisplayed(submit);
        } catch (TimeoutException e) {
            click.clickElementCarefully(maximizeSalesSection);
            wait.waitForElementNotDisplayed(submit);
        }

    }

    /**
     * This method locates and click on Minimize sales Item window in Customer Invoice Page
     */
    public void clickOnMinimizeSalesScreen() {

        click.clickElementCarefully(maximizeSalesSection);
        wait.waitForElementDisplayed(submit);
    }

    /**
     * This Method Changes quantity
     *
     * @param newQty Quantity to be passed for product or services
     */
    public void changeInQuantity(int newQty) {
        int numbOfLines = element.getWebElements(lineItem).size();
        String quantityEle = String.format("(//table[@class='mainTable']//tbody//tr[%s]//td[8]//input)", numbOfLines);
        WebElement quantity = wait.waitForElementPresent(By.xpath(quantityEle));
        scroll.scrollElementIntoView(quantity);
        click.javascriptClick(quantity);
        quantity.sendKeys(Keys.chord(Keys.CONTROL, "a"), Integer.toString(newQty));
    }

    /**
     * This Method Changes quantity
     *
     * @param newQty Quantity to be passed for product or services
     */
    public void updateQuantity(int newQty) {
        WebElement quantity = wait.waitForElementDisplayed(quantityView);
        scroll.scrollElementIntoView(quantity);
        click.clickElementCarefully(quantity);
        quantity = wait.waitForElementPresent(quantityInput);

        String textInsideInputBox = quantity.getAttribute("value");

        // Check whether input field is blank
        if (!textInsideInputBox.isEmpty()) {
            for (int i = 0; i < textInsideInputBox.length(); i++) {
                text.enterText(quantity, Keys.BACK_SPACE);
            }
        }
        quantity = wait.waitForElementEnabled(quantityInput);
        quantity.sendKeys(Integer.toString(newQty));
    }

    /**
     * This Method Changes the Unit Price
     *
     * @param unitPrice Quantity to be passed for product or services
     */
    public void changeInUnitPrice(int unitPrice) {
        int numbOfLines = element.getWebElements(lineItem).size();
        String quantityEle = String.format("(//table[@class='mainTable']//tbody//tr[%s]//input)[5]", numbOfLines);
        WebElement quantity = wait.waitForElementDisplayed(By.xpath(quantityEle));
        scroll.scrollElementIntoView(quantity);
        quantity.sendKeys(Keys.chord(Keys.CONTROL, "a"), Integer.toString(unitPrice));
        quantity.sendKeys(Keys.TAB);

    }

    /**
     * This Method Changes the Unit Price
     *
     * @param unitPriceAmount Quantity to be passed for product or services
     */
    public void changeInUnitPriceForPartialAdj(int unitPriceAmount) {
        hover.hoverOverElement(unitPriceClick);
        click.clickElementCarefully(unitPriceClick);
        WebElement unitPriceEle = wait.waitForElementDisplayed(unitPrice);
        scroll.scrollElementIntoView(unitPriceEle);
        unitPriceEle.sendKeys(Keys.chord(Keys.CONTROL, "a"), Integer.toString(unitPriceAmount));
        unitPriceEle.sendKeys(Keys.TAB);
    }

    /**
     * This method first locates and clear the default address and select required address
     *
     * @param address address string
     */
    public void changeShippingAddressOnHeader(String address) {

        WebElement addressHeaderEle = wait.waitForElementPresent(shipToAddHeader);
        click.javascriptClick(addressHeaderEle);
        addressHeaderEle.sendKeys(address);
        addressHeaderEle.sendKeys(Keys.ENTER);
    }

    /**
     * This method first locates and clear the default address and select required address
     *
     * @param address address string
     */
    public void changeShippingAddress(String address) {
        WebElement addressEle = wait.waitForElementPresent(addressBy);
        click.javascriptClick(addressEle);
        click.clickElementCarefully(shipToAdd);
        String addressString = String.format("//div[contains(@data-automation-label, '%s')]", address);
        WebElement addEle = wait.waitForElementPresent(By.xpath(addressString));
        click.javascriptClick(addEle);
        jsWaiter.waitForLoadAll();
        String fullAddress = String.format("//table[@class='mainTable']//p[contains(text(),'%s')]", address);
        wait.waitForElementDisplayed(By.xpath(fullAddress));
        //Working in Local but not in Jenkins so had to put static wait
        jsWaiter.sleep(5000);
    }

    /**
     * This method first find if deferred revenue checkbox is checked
     * If it is checked then it uncheck it to submit the invoice
     */
    public void uncheckDeferredRevenueIfChecked() {
        WebElement radioButtonStatus = wait.waitForElementPresent(deferredRevenueStatus);
        scroll.scrollElementIntoView(radioButtonStatus);
        String isChecked = radioButtonStatus.getAttribute("aria-checked");
        if (Boolean.parseBoolean(isChecked)) {
            WebElement radioButton = wait.waitForElementPresent(deferredRevenueCheckbox);
            scroll.scrollElementIntoView(radioButton);
            click.clickElementCarefully(radioButton);
        }
    }

    /**
     * This method locates and clicks the submit button
     * It will navigate user to the Invoice review page
     *
     * @return workdayInvoiceReviewPage object
     */
    public WorkdayInvoiceReviewPage clickOnSubmit() {
        wait.waitForElementDisplayed(submit).click();

        return (new WorkdayInvoiceReviewPage(driver));
    }

    /**
     * This method locates the Company text box and send the values
     *
     * @param companyName company name ex. "Spectre"
     */
    public void enterCompanyInfo(String companyName) {

        WebElement ele = wait.waitForElementPresent(company);

        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(ele));
        click.javascriptClick(ele);
        text.enterText(ele, companyName);
        //text.clickElementAndEnterText(ele, companyName);
        ele.sendKeys(Keys.TAB);
        //wait.waitForElementNotDisplayed(defaultTaxCode);

    }

    /**
     * This method locates, delete default address and then add new ship to address
     *
     * @param address new ship to address
     * @return WorkdayCustomerInvoicePage object
     */
    public WorkdayCustomerInvoicePage updateShipToAddress(String address) {
        WebElement deleteAddEle;
        WebElement removeAddEle;
        deleteAddEle = wait.waitForElementDisplayed(deleteAddress);
        click.clickElementCarefully(deleteAddEle);
        removeAddEle = wait.waitForElementDisplayed(removeAddress);
        click.clickElementCarefully(removeAddEle);
        click.clickElementCarefully(addressInput);
        // This is dynamically choosing Address according to input
        click.clickElementCarefully(By.xpath(String.format("//div[contains(@data-automation-label, '%s')]", address)));
        return initializePageObject(WorkdayCustomerInvoicePage.class);
    }

    /**
     * This method locates, delete default address and then add new ship to address
     *
     * @param address new ship to address
     * @return WorkdayCustomerInvoicePage object
     */
    public WorkdayCustomerInvoicePage deletesAutoCreatedLineLvlItem(String address) {
        WebElement deleteAddEle;
        WebElement removeAddEle;
        deleteAddEle = wait.waitForElementDisplayed(deleteAddress);
        click.clickElementCarefully(deleteAddEle);
        removeAddEle = wait.waitForElementDisplayed(removeAddress);
        click.clickElementCarefully(removeAddEle);
        return initializePageObject(WorkdayCustomerInvoicePage.class);
    }

    /**
     * this method is the helper method to get By object of all line levels except first line input text fields
     *
     * @param fieldName  name of the sales item for ex. "Services 2"
     * @param lineNumber multiple line number
     */

    public By getByObjOfAllLineItemsExceptFirst(String fieldName, int lineNumber) {
        if (fieldName.equalsIgnoreCase("product")) {
            // Because element was keep updating with different Tax option
            return By.xpath(String.format("(//tbody//tr[@tabindex='0'][%s]//input[@data-uxi-widget-type='selectinput'])[2]", lineNumber));
        } else {
            return By.xpath(String.format("(//div[@aria-label='Taxable Sales, press delete to clear value.'])[%s]", lineNumber));
        }
    }

    /**
     * this method add More Invoice Line add Sales Item
     *
     * @param product multiple Sales Item
     * @param lineNo  Invoice Line Number
     */
    public void addMoreProducts(String product, int lineNo) {
        wait.waitForElementNotDisplayed(submit);
        WebElement addLine = wait.waitForAllElementsDisplayed(addInvoiceLine).get(lineNo - 1);
        click.clickElementCarefully(addLine);
        WebElement sales = wait.waitForElementPresent(getByObjOfAllLineItemsExceptFirst("product", lineNo));
        scroll.scrollElementIntoView(sales);
        click.clickElementCarefully(sales);
        text.enterText(sales, product);
        jsWaiter.sleep(1000);
        sales.sendKeys(Keys.TAB);
        jsWaiter.sleep(3000);
    }

    /**
     * It clicks on add method if needed
     */
    public void clickOnAddLineButtonIfNeeded() {
        int numbOfLines = element.getWebElements(lineItem).size();
        String addRowStr = "(//table[@class='mainTable']//button[@title='Add Row']//div[@data-automation-id='icon'])[%s]";
        if (element.isElementPresent(quantity1)) {
            click.javascriptClick(By.xpath(String.format(addRowStr, numbOfLines)));
            wait.waitForElementDisplayed(By.xpath(String.format(addRowStr, numbOfLines + 1)));
        }
    }

    /**
     * Function that navigates from page after submitting a customer invoice form to the customer invoice landing page
     */
    public void navigateToCustomerInvoicePage() {
        WebElement ele = wait.waitForElementEnabled(viewInvoiceDetails);
        click.clickElementCarefully(ele);
    }

}
