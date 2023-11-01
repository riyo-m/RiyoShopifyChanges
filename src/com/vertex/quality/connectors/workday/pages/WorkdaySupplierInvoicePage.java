package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * this class represents the workday supplier invoice page
 * contains all the code necessary to fill out supplier invoice form
 *
 * @author dPatel
 */
public class WorkdaySupplierInvoicePage extends VertexPage {

    public WorkdaySupplierInvoicePage(WebDriver driver) {
        super(driver);
    }

    protected By submit = By.cssSelector("button[title='Submit']");
    protected By salesItem = By.xpath("(//input[@aria-required='false'])[17]");
    protected By salesItemAdj = By.xpath("(//div[@data-automation-id='multiselectInputContainer'])[17]");
    protected By termsAndTaxes = By.xpath("//span[@title='Terms and Taxes']/div");
    protected By adjtermsAndTaxes = By.xpath("//span[contains(text(),'Reference')]");
    protected By unitCostBox = By.xpath("(//input[@data-automation-id='numericInput'])[7]");
    protected By taxOptionDropDown = By.xpath("//div[@data-automation-id='selectShowAll']");
    protected By selfAssessedTax = By.xpath("(//ul[@aria-label='Default Tax Option']/li)[2]/div");
    protected By enterTaxDue = By.xpath("(//ul[@aria-label='Default Tax Option']/li)[4]/div");
    protected By calcTaxDue = By.xpath("(//ul[@aria-label='Default Tax Option']/li)[3]/div");
    protected By selectedOption = By.xpath("//div[@data-automation-id='selectSelectedOption']");
    protected By salesInputFirst = By.xpath("(//div[@data-automation-id='multiselectInputContainer']/div/input)[16]");
    protected By salesInput = By.xpath("(//tbody//tr[@tabindex='0']//input[@data-uxi-widget-type='selectinput'])[1]");
    protected By banners = By.cssSelector("p[title='Banners and Displays']");
    protected By company = By.xpath("(//input[@placeholder='Search'])[2]");
    protected By supplier = By.xpath("(//input[@placeholder='Search'])[3]");
    protected By adjustmentReason = By.xpath("(//input[@placeholder='Search'])[5]");
    protected By refInvoice = By.xpath("(//input[@placeholder='Search'])[9]");
    protected By priceAdjustment = By.cssSelector("p[title='Price Adjustment']");
    protected By spectre = By.xpath("(//p[@title='Spectre, Inc.'])[1]");
    protected By americanElec = By.cssSelector("p[title='American Electric Power']");
    protected By alcoholSwab = By.cssSelector("p[title^='Alcohol Swabs']");
    protected By freightAmount = By.xpath("(//input[@data-automation-id='numericInput'])[2]");
    protected By otherChargesAmount = By.xpath("(//input[@data-automation-id='numericInput'])[3]");
    protected By dueDateOverRide = By.xpath("(//div[@aria-hidden='true'][contains(.,'MM')])[4]/following-sibling::input");
    protected By defaultWithHoldingTaxCode = By.xpath(".//li/div/label[text()='Default Withholding Tax Code']/../..//input");
    protected By onHold = By.xpath("(//div[@data-automation-id='checkboxPanel'])[2]");
    protected By payPracticeTextBox = By.xpath("//label[text()='Payment Practices']");
    protected By approver = By.xpath("//label[text()='Approver']/../..//input");
    protected By addInvoiceLine = By.xpath("(//button[@title='Add Row'])");
    protected By gps = By.xpath("(//p[@title='Green Planet Solutions, Inc. (USA)'])[1]");
    protected By dpSample = By.xpath("(//p[@data-automation-label='DP_Sample'])[1]");
    protected By paymentTerms = By.xpath("//label[text()='Payment Terms']/../..//ul");
    protected By twoPercent = By.xpath("//div[@data-automation-label='2% 10, net 30']");
    protected By overridePayment = By.xpath(".//li/div/label[text()='Override Payment Type']/../..//input");
    protected By check = By.xpath("(//p[@title='Check'])[1]");
    protected By referenceType = By.xpath("(//input[@data-uxi-widget-type='selectinput'])[7]");
    protected By other = By.xpath("(//div[@data-automation-label='Other'])[1]");
    protected By titleOther = By.xpath("(//p[@title='Other'])[1]");
    protected By referenceNumber = By.xpath("(//input[@data-automation-id='textInputBox'])[1]");
    protected By handlingCode = By.xpath(".//li/div/label[text()='Handling Code']/../..//input");
    protected By messenger = By.xpath("(//p[@title='Messenger'])[1]");
    protected By poNumber = By.xpath("(//input[@data-automation-id='textInputBox'])[3]");
    protected By referencedInvoice = By.xpath(".//li/div/label[text()='Referenced Invoices']/../..//input");
    protected By documentLink = By.xpath("(//input[@data-automation-id='textInputBox'])[4]");
    protected By memo = By.xpath("(//input[@data-automation-id='textInputBox'])[5]");
    protected By referenceNumTitle = By.xpath("//label[text()='Reference Number']");
    protected By taxableSales = By.xpath("//p[@title='Vertex']");
    protected By each = By.xpath("//p[@title='Each']");
    protected By shipToContact = By.xpath("//table[@class='mainTable']//td[10]//input");
    protected By mike = By.xpath("//p[@data-automation-label='Justin Dillman']");
    protected By jDillman = By.xpath("(//div[@data-automation-id='menuItem'])[2]");
    protected By approverSelectorPopup = By.xpath("//div[@data-automation-id='multiSelectHeader']");
    protected By withholdingTax = By.xpath("//p[@data-automation-label='USA 28% No Tax ID']");
    protected By location = By.xpath("//div[@title='Amsterdam']");
    protected By region = By.xpath("//div[@title='AA - Central Africa']");
    protected By additionalWork = By.xpath("//p[@data-automation-label='Cash Flow Code: Sale']");
    protected By prepaid = By.xpath("(//div[@data-automation-id='checkboxPanel'])[5]");
    protected By billable = By.xpath("(//div[@data-automation-id='checkboxPanel'])[6]");
    protected By memoLineLevel = By.xpath("(//textarea[@data-automation-id='textAreaField'])[1]");
    protected By prepaidTab = By.xpath("(//div[text()='Prepaid Details'])[1]");
    protected By prepaidAuthType = By.xpath("//label[text()='Prepaid Amortization Type']/../..//input");
    protected By scheduleRadio = By.xpath("//div[@data-automation-label='Schedule']");
    protected By schedule = By.xpath("//div[@title='Schedule']");
    protected By statutoryInvoice = By.xpath(".//li/div/label[text()='Statutory Invoice Type']/../..//input");
    protected By demoUSA = By.xpath("//div[@data-automation-label='demo_USA - United States of America']");
    protected By supplierContract = By.xpath(".//li/div/label[text()='Supplier Contract']/../..//input");
    protected By contract = By.xpath("//p[contains(@data-automation-label,'CON')]");
    protected By headerTax = By.xpath("//label[text()='Tax Amount']/../..//input");
    protected By maximize = By.xpath("//div[@data-automation-id='gridFullscreenIconButton']");
    protected By gasElectric = By.xpath("//p[@aria-label='Gas & Electric']");
    protected By quantityView = By.xpath("(//div[@data-automation-id='numericWidget'])[2]");
    protected By quantityInput = By.xpath("(//div[@data-automation-id='numericWidget'])[2]//input");
    protected By net = By.xpath("//p[contains(@title,'Net')]");
    protected By adjustedLineItem = By.xpath("(//div[@data-automation-id='checkboxPanel'])[2]");
    protected By saveContinue = By.xpath("//span[@title='Save and Continue']");
    protected By creditAdjReason = By.xpath("//input[@dir='ltr' and @aria-required='true']");
    protected By enteredReason = By.xpath("//p[@title='Price Adjustment']");
    protected By book = By.xpath("//div[@data-automation-label='Book']");
    protected By shippingAddressClose = By.xpath("(//div[@data-automation-id='DELETE_charm'])[5]");
    protected By controlTotal = By.xpath("(//input[@data-automation-id='numericInput'])[1]");
    String[] invoices = {"17878", "17879", "17800"};
    String[] spectreInvoices = {"17814", "17815", "17816"};
    String[] textField = {"Withholding Tax Code", "Location", "Region", "Additional Worktags",};
    String[] textValue = {"USA 28% No Tax ID", "Amsterdam", "AA - Central Africa", "Cash Flow Code: Sale"};
    String[] textValueCyber = {"USA 28% No Tax ID", "Amsterdam", "AA - Central Africa", "Cash Flow Code: Sale"};
    protected By supplierContractvalue = By.xpath("//div[contains(text(),'on Invoice')]");
    protected By supplierContractcon1083 = By.xpath("//div[contains(text(),'CON-1083')]");
    protected By suplContract1084 = By.xpath("//div[contains(text(),'CON-1084')]");
    protected By addressInput = By.xpath("(//span[@data-automation-id='promptIcon'])[17]");
    protected By addressByCompany = By.xpath("(//div[@data-automation-id='multiselectInputContainer'])[17]//input");
    protected By dell = By.xpath("//div[@data-automation-label='Dell']");
    protected By increaseLiability = By.xpath("(//span[@data-automation-id='radioBtn'])[1]/..");
    protected By adjDate = By.xpath("//div[@data-automation-id='dateWidgetContainer']/div[1]/input");
    protected By viewInvoiceDetails = By.xpath("//button[contains(text(), 'View Details')]");
    protected By detailsProcessesArrow = By.xpath("//div[@data-automation-id='icon' and @role='presentation']");
    protected By additionalFieldsTab = By.xpath("//div[contains(text(), 'Additional Fields')]");
    String firstAdditionalField = "(//input[@data-automation-id='textInputBox'])[%s]";
    protected By headerCompanyShipToAddress = By.xpath("(//input[@dir='ltr'])[13]");
    protected By costCenterLineLvl = By.xpath("(//input[@dir='ltr'])[31]");

    /**
     * It enters the value in the field available in the Supplier Invoice Form
     *
     * @param nameOfTextField name of the field you want to send the value for ex. "Company"
     * @param value           values to send for ex. "Spectre"
     * @param isAdj           true if Supplier Invoice Adjustment
     */
    public void enterValuesToTextBox(String nameOfTextField, String value, Boolean isAdj) {

        WebElement textBox = wait.waitForElementPresent(getByObjOfTextField(nameOfTextField));
        text.clickElementAndEnterText(textBox, value);
        if (isAdj) {
            termsAndTaxes = adjtermsAndTaxes;
        }
        click.performDoubleClick(wait.waitForElementDisplayed(termsAndTaxes));

        if (!nameOfTextField.equals("Reference Invoice")) {
            wait.waitForElementDisplayed(getByObjOfTextField(value));
        }
    }

    /**
     * This method locates the Company text box and send the values
     *
     * @param companyName company name ex. "Spectre"
     */
    public void enterCompanyInfo(String companyName) {
        WebElement ele = wait.waitForElementPresent(company);
        text.clickElementAndEnterText(ele, companyName);
        ele.sendKeys(Keys.ENTER);
        wait.waitForElementNotDisplayed(payPracticeTextBox);
    }

    /**
     * This method locates the Supplier text box and send the values
     *
     * @param supplierName company name ex. "Spectre"
     */
    public void enterSupplier(String supplierName) {
        WebElement ele;
        try {
            wait.waitForElementDisplayed(alcoholSwab, 3);
        } catch (TimeoutException e) {
            ele = wait.waitForElementPresent(supplier);
            text.clickElementAndEnterText(ele, supplierName);
            ele.sendKeys(Keys.ENTER);
        }
        if (supplierName.equalsIgnoreCase("dell"))
        {
            click.clickElementCarefully(dell);
        }
        wait.waitForElementDisplayed(net);
    }
    /**
     * this method select default tax option while creating supplier invoiced
     *
     * @param taxOption for Example: "Calculate Self-Assessed Tax" or "Calculate Tax Due to Supplier"
     */
    public void selectDefaultTaxOption(String taxOption) {
        By tax;
        click.clickElement(taxOptionDropDown);
        if (taxOption.equalsIgnoreCase("Calculate Self-Assessed Tax")) {
            tax = selfAssessedTax;
        } else if (taxOption.equalsIgnoreCase("Enter Tax Due to Supplier")) {
            tax = enterTaxDue;
        } else {
            tax = calcTaxDue;
        }
        wait.waitForElementDisplayed(tax);
        hover.hoverOverElement(tax);
        click.moveToElementAndClick(tax);
        wait.waitForTextInElement(selectedOption, taxOption);
    }

    /**
     * It submits the supplier Invoice
     *
     * @return the workdayInvoiceReview page object
     */
    public WorkdayInvoiceReviewPage clickOnSubmit() {
        wait.waitForElementDisplayed(submit).click();
        try {
            wait.waitForElementNotDisplayed(submit, 5);
        }catch (TimeoutException exception){
            wait.waitForElementDisplayed(submit).click();
        }
        return (new WorkdayInvoiceReviewPage(driver));
    }

    /**
     * this method clear the default unit cost and enter the new one
     *
     * @param unitCost new unit cost
     */
    public void enterUnitCost(int unitCost) {
        WorkdayCustomerInvoicePage invoice = new WorkdayCustomerInvoicePage(driver);
        invoice.clickOnMaximizeSalesScreen();
        WebElement unitCostEle = wait.waitForElementDisplayed(unitCostBox);
        scroll.scrollElementIntoView(unitCostEle);
        unitCostEle.clear();
        invoice.clickOnMinimizeSalesScreen();
        invoice.clickOnMaximizeSalesScreen();
        for (int i = 0; i < 4; i++) {
            unitCostEle.sendKeys(Keys.BACK_SPACE);
        }
        unitCostEle.sendKeys(Integer.toString(unitCost));
        invoice.clickOnMinimizeSalesScreen();
    }

    /**
     * Helper method to get the by obj of the Field
     *
     * @param nameOfTextField ex. "Supplier"
     *
     * @return by obj of the text field
     */
    protected By getByObjOfTextField(String nameOfTextField) {

        switch (nameOfTextField) {
            case "Company":
                return company;
            case "Supplier":
                return supplier;
            case "Adjustment Reason":
                return adjustmentReason;
            case "Reference Invoice":
                return refInvoice;
            case "Spectre, Inc.":
                return spectre;
            case "American Electric Power":
                return americanElec;
            case "GPS":
                return gps;
            case "DP_Sample":
                return dpSample;
            case "overridepayment":
                return overridePayment;
            case "check":
                return check;
            case "referencetype":
                return referenceType;
            case "other":
                return other;
            case "handlingcode":
                return handlingCode;
            case "messenger":
                return messenger;
            case "USA 28% No Tax ID":
                return withholdingTax;
            case "Amsterdam":
                return location;
            case "AA - Central Africa":
                return region;
            case "Cash Flow Code: Sale":
                return additionalWork;
            default:
                return priceAdjustment;
        }
    }

    /**
     * this method enters the sales item after maximizing adjustment lines window
     *
     * @param salesProduct name of the sales item for ex. "Banners and Displays"
     * @param isAdj        set to true if the sales invoice Adjustment
     */
    public void enterSalesItem(String salesProduct, Boolean isAdj) {
        WorkdayCustomerInvoicePage invoice = initializePageObject(WorkdayCustomerInvoicePage.class);
        invoice.clickOnMaximizeSalesScreen();
        if (isAdj) {
            salesItem = salesItemAdj;
        }
        WebElement sales = wait.waitForElementPresent(salesInputFirst);
        click.clickElement(sales);
        sales = wait.waitForElementEnabled(salesInputFirst);
        text.enterText(sales, salesProduct);
        sales.sendKeys(Keys.ENTER);
        if (salesProduct.equals("Banners and Displays")) {
            wait.waitForElementDisplayed(banners);
        } else if (salesProduct.contains("Alcohol Swabs")) {
            wait.waitForElementPresent(alcoholSwab);
        }
        invoice.clickOnMinimizeSalesScreen();
    }

    /**
     * this method enters line level invoice details
     *
     * @param salesProduct name of the sales item for ex. "Banners and Displays"
     * @param taxOption    Tax option
     */
    public void enterLineLevelInvoiceDetails(String salesProduct, String taxOption, boolean isBillable) {
        enterSingularSalesItem(salesProduct);
        enterShipToContact();
        if (isBillable) {
            textValue = textValueCyber;
        }
        for (int i = 0; i < textField.length; i++) {
            enterLineLevelDetails(textField[i], textValue[i], taxOption);
        }
        click.clickElementCarefully(prepaid);
        text.clickElementAndEnterText(memoLineLevel, "dp_2");
    }

    /**
     * this method enters Sales item for first Line
     *
     * @param item item purchase/sales item to enter
     */
    public void enterSingularSalesItem(String item) {

        WebElement sales = wait.waitForElementPresent(salesInput);
        scroll.scrollElementIntoView(sales);
        text.enterText(sales, item);
        sales.sendKeys(Keys.ENTER);
        jsWaiter.sleep(3000); // Dynamic loading application
    }

    /**
     * this method enters Freight amount
     *
     * @param amount amount to enter
     */
    public void enterFreightAmount(String amount) {
        WebElement ele = wait.waitForElementEnabled(freightAmount);
        for (int i = 0; i < 100; i++) {
            ele.sendKeys(Keys.BACK_SPACE);
        }
        ele.sendKeys(amount);
    }

    /**
     * this method enters "Other Charges amount"
     *
     * @param amount amount to enter
     */
    public void enterOtherChargesAmount(String amount) {
        WebElement ele = wait.waitForElementEnabled(otherChargesAmount);
        for (int i = 0; i < 100; i++) {
            ele.sendKeys(Keys.BACK_SPACE);
        }
        ele.sendKeys(amount);
    }

    /**
     * this method enters "Due Date Override"
     */
    public void enterdueDateOverRide() {
        WebDriverWait _wait = new WebDriverWait(driver, 5);
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        Date date = new Date();

        _wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(dueDateOverRide)));
        click.javascriptClick(dueDateOverRide);
        text.enterText(dueDateOverRide, dateFormat.format(date));
        waitForPageLoad();
    }

    /**
     * this method enters "WithHolding Tax Code"
     *
     */
    public void enterDefaultWithHoldingTaxCode() {
        WebElement ele = wait.waitForElementDisplayed(defaultWithHoldingTaxCode);
        text.clickElementAndEnterText(ele, "USA 30% NRWT");
        ele.sendKeys(Keys.ENTER);
        waitForPageLoad();
    }

    /**
     * this method make "OnHold" checked
     */
    public void clickOnHold() {
        WebElement ele = wait.waitForElementEnabled(onHold);
        if (!ele.isSelected()) {
            click.clickElementCarefully(ele);
        }
        waitForPageLoad();
    }

    /**
     * this method enters "Approver" Information
     *
     */
    public void enterApprover() {
        WebElement approverEle = wait.waitForElementDisplayed(approver);
        text.clickElementAndEnterText(approverEle, "Justin Dillman");
        approverEle.sendKeys(Keys.ENTER);
        wait.waitForElementPresent(approverSelectorPopup);


        WebElement chooseFirstApproverOption = wait.waitForElementPresent(jDillman);
        chooseFirstApproverOption.click();
    }

    /**
     * this method enters "Payment Terms"
     *
     */
    public void enterPaymentTerms() {
        WebElement postOptionEle = wait.waitForElementPresent(paymentTerms);
        postOptionEle.click();
        click.clickElementCarefully(twoPercent);
    }

    /**
     * this method enters "Header Tax Amount"
     *
     * @param headerTaxAmount Header Tax amount
     */
    public void enterHeaderTaxAmount(String headerTaxAmount) {
        if (!headerTaxAmount.equalsIgnoreCase("0")) {

            for (int i = 0; i < 20; i++) {
                WebElement ele = wait.waitForElementDisplayed(headerTax);
                ele.sendKeys(Keys.BACK_SPACE);
            }
            text.clickElementAndEnterText(headerTax, headerTaxAmount);
        }
    }

    /**
     * this method enters Reference Number
     */
    public void enterReferenceNumber() {
        wait.waitForElementDisplayed(referenceNumTitle);
        text.enterText(referenceNumber, "123456");
        jsWaiter.waitForLoadAll();
    }

    /**
     * this method enters External PO number
     */
    public void enterExternalPONumber() {
        text.enterText(poNumber, "123456");
    }

    /**
     * this method enters Reference Invoices depending upon the company
     *
     * @param company company information
     */
    public void enterReferencedInvoices(String company) {
        WebElement textBox = wait.waitForElementPresent(referencedInvoice);
        if (company.equalsIgnoreCase("GPS")) {
            spectreInvoices = invoices;
        }
        for (String invoice : spectreInvoices) {
            text.clickElementAndEnterText(textBox, invoice);
            textBox.sendKeys(Keys.ENTER);
            wait.waitForElementDisplayed(getByObjOfRefInvoices(invoice));
        }
    }

    /**
     * this method enters Reference Type
     */
    public void enterReferenceType() {
        WebElement postOptionEle = wait.waitForElementPresent(referenceType);
        postOptionEle.click();
        click.clickElementCarefully(other);
        wait.waitForElementDisplayed(titleOther);
    }

    /**
     * this method enters Document Link
     */
    public void enterDocumentLink() {
        text.clickElementAndEnterText(documentLink, "http://www.hp.com");
    }

    /**
     * this method enters Memo information
     *
     */
    public void enterMemo() {
        text.clickElementAndEnterText(memo, "dp_memo");
    }

    /**
     * this method enters ship to contact
     *
     */
    public void enterShipToContact() {
        scroll.scrollElementIntoView(shipToContact);
        WebElement ele = wait.waitForElementEnabled(shipToContact);

        text.clickElementAndEnterText(ele, "Justin Dillman");
        ele.sendKeys(Keys.ENTER);
        wait.waitForElementDisplayed(mike);
    }

    /**
     * this method enters Line level Details
     *
     * @param textField text field
     * @param textValue text value
     * @param taxOption Default Tax Option
     */
    public void enterLineLevelDetails(String textField, String textValue, String taxOption) {
        WebElement ele = wait.waitForElementEnabled(getByobjOfFirstLineLevelInputTextField(textField, taxOption));
        scroll.scrollElementIntoView(ele);
        text.clickElementAndEnterText(ele, textValue);
        jsWaiter.sleep(1000);
        ele.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(getByObjOfTextField(textValue));
    }

    /**
     * this method enters Prepaid Authorization
     */
    public void enterPrepaidAuthorization() {
        click.clickElementCarefully(prepaidTab);
        click.clickElementCarefully(prepaidAuthType);
        click.clickElementCarefully(scheduleRadio);
        wait.waitForElementDisplayed(schedule);
    }

    /**
     * this method enters statutory Invoice Type
     */
    public void enterStatutoryInvoiceType() {
        WebElement invoiceType = wait.waitForElementDisplayed(statutoryInvoice);
        invoiceType.click();
        wait.waitForElementDisplayed(demoUSA);
        click.clickElementCarefully(demoUSA);
    }

    /**
     * this method enters supplier contract depending on the company
     *
     * @param company company name
     */
    public void enterSupplierContract(String company) {
        WebElement invoiceType = wait.waitForElementPresent(supplierContract);
        invoiceType.click();
        wait.waitForElementDisplayed(supplierContractvalue);
        click.clickElement(supplierContractvalue);
        if (company.equalsIgnoreCase("GPS")) {
            wait.waitForElementDisplayed(supplierContractcon1083);
            click.clickElement(supplierContractcon1083);
        } else {
            wait.waitForElementDisplayed(suplContract1084);
            click.clickElement(suplContract1084);
        }
        wait.waitForElementDisplayed(contract);
    }

    /**
     * this method add More Invoice Line, add Sales Item and wait till "Gas Electric" disappears
     *
     * @param item      Sales Item
     * @param lineNo    Invoice Line Number
     * @param taxOption Tax Option
     */
    public void addMoreInvoiceLine(String item, int lineNo, String taxOption) {
        wait.waitForElementEnabled(maximize);
        try {
            click.clickElementCarefully(maximize);
        } catch (TimeoutException e) {
            click.clickElementCarefully(maximize);
        }
        wait.waitForElementNotDisplayed(submit);
        WebElement addLine = wait.waitForAllElementsDisplayed(addInvoiceLine).get(lineNo - 1);
        click.clickElementCarefully(addLine);
        wait.waitForElementDisplayed(gasElectric);
        WebElement sales = wait.waitForElementPresent(getByObjOfAllLineItemsExceptFirst("item", lineNo));
        scroll.scrollElementIntoView(sales);
        click.clickElementCarefully(sales);
        text.enterText(sales, item);
        jsWaiter.waitForLoadAll();
        text.enterText(sales, Keys.ENTER);
        jsWaiter.waitForLoadAll();
        text.enterText(sales, Keys.TAB);
        jsWaiter.waitForLoadAll();
        if (item.equalsIgnoreCase("book")) {
            click.clickElementCarefully(book);
        }
        jsWaiter.sleep(3000);
        click.clickElementCarefully(maximize);
        wait.waitForElementDisplayed(submit);
    }

    /**
     * this method is the helper method to get "By" object of Reference Invoices
     *
     * @param invNo Invoice Number
     * @return By object
     */
    public By getByObjOfRefInvoices(String invNo) {
        return By.xpath(String.format("//p[contains(@aria-label,'%s')]", invNo));
    }

    /**
     * this method is the helper method to get By object of all line levels except first line input text fields
     *
     * @param fieldName  name of the sales item for ex. "Banners and Displays"
     * @param lineNumber line number
     */
    public By getByObjOfAllLineItemsExceptFirst(String fieldName, int lineNumber) {
        if (fieldName.equalsIgnoreCase("item")) {
            // Because element was keep updating with different Tax option
            return By.xpath(String.format("(//tbody//tr[@tabindex='0'][%s]//input[@data-uxi-widget-type='selectinput'])[2]", lineNumber));
        } else {
            return By.xpath(String.format("(//p[@aria-label='Vertex'])[%s]", lineNumber));
        }
    }

    /**
     * this method is the helper method to get By object of first line level input text fields
     *
     * @param fieldName name of the sales item for ex. "Banners and Displays"
     * @param taxOption Tax option
     * @return By object of input text field
     */
    public By getByobjOfFirstLineLevelInputTextField(String fieldName, String taxOption) {

        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("Additional Worktags", "11");
        hmap.put("Region", "10");
        hmap.put("Location", "9");
        hmap.put("Withholding Tax Code", "6");
        hmap.put("Ship-To Contact", "4");

        HashMap<String, String> hmapCalcTax = new HashMap<>();
        hmapCalcTax.put("Additional Worktags", "10");
        hmapCalcTax.put("Region", "9");
        hmapCalcTax.put("Location", "8");
        hmapCalcTax.put("Withholding Tax Code", "5");
        hmapCalcTax.put("Ship-To Contact", "4");

        if (taxOption.contains("Calculate")) {
            hmap = hmapCalcTax;
        }
        // Because element was keep updating with different Tax option
        return By.xpath(String.format("(//tr[@aria-label='row' and @tabindex='0']//input[@placeholder='Search'])[%s]", hmap.get(fieldName)));

    }

    /**
     * This method first locates and clear the Quantity field then send the values in the text box
     * I used Static wait as workday dynamically changes the QTY field to "0" after clearing it so static wait can wait till this happens
     *
     * @param newQty Quantity to be passed for product or services
     */
    public void changeInQuantity(int newQty) {

        WebElement quantity = wait.waitForElementDisplayed(quantityView);
        scroll.scrollElementIntoView(quantity);
        quantity.click();
        quantity = wait.waitForElementDisplayed(quantityInput);

        String quantityInputValue = quantity.getAttribute("value");

        for (int i = 0; i < quantityInputValue.length()+1; i++) {
            text.enterText(quantity, Keys.BACK_SPACE);
        }
        text.enterText(quantity, Integer.toString(newQty));
    }

    /**
     * this method clicks on Billable check box
     */
    public void clicksOnBillableCheckBOx() {
        WebElement ele = wait.waitForElementPresent(billable);
        if (!ele.isSelected()) {
            ele.click();
        }
        waitForPageLoad();
    }

    /**
     * this method clicks on Line Item in Adjusted Supplier invoice Page
     */
    public void selectLineItemFromAdjustmentPage() {
        WebElement ele = wait.waitForElementPresent(adjustedLineItem);
        scroll.scrollElementIntoView(ele);
        if (!ele.isSelected()) {
            ele.click();
        }
        waitForPageLoad();
    }

    /**
     * this method clicks on Save and Continue and wait till loads the next page
     */
    public void clickSaveAndContinue() {
        WebElement ele = wait.waitForElementPresent(saveContinue);
        click.clickElementCarefully(ele);

        waitForPageLoad();
    }

    /**
     * this method enter the Adjustment Reason as "Price Adjustment"
     */
    public void enterAdjReason() {
        WebElement ele = wait.waitForElementDisplayed(creditAdjReason);
        text.clickElementAndEnterText(ele, "Price Adjustment");
        ele.sendKeys(Keys.ENTER);
        wait.waitForElementDisplayed(enteredReason);
        ele.sendKeys(Keys.TAB);
    }

    /**
     * this method changes adjustment date
     */
    public void changeAdjDate() {
        WorkdayInvoiceReportPage reportPage = new WorkdayInvoiceReportPage(driver);
        wait.waitForElementPresent(adjDate);
        text.enterText(adjDate, reportPage.getYesterdaysDateMMddyyyy());
    }

    /**
     * This method clicks on Increase Liability radio button
     */
    public void clickOnIncreaseLiability() {
        click.clickElementCarefully(increaseLiability);
    }

    /**
     * This method first locates and clear the default address and select required address
     *
     * @param address address string
     */
    public void changeShippingAddress(String address) {
        WebElement removeAddEle;
        removeAddEle = wait.waitForElementDisplayed(addressInput);
        scroll.scrollElementIntoView(removeAddEle);
        WebElement shipToAddressInput = wait.waitForElementEnabled(addressByCompany);
        click.javascriptClick(shipToAddressInput);
        shipToAddressInput.sendKeys(address);
        shipToAddressInput.sendKeys(Keys.ENTER);


        // This is dynamically choosing Address according to input
//        WebElement addressEle = wait.waitForElementDisplayed(By.xpath(String.format("//div[contains(text(),'%s')]", address)));
//        click.clickElementCarefully(addressEle);
    }

    /**
     * This test clears the header level shipping address
     */
    public void clearHeaderLevelShippingAddress() {
        WebElement removeHeaderAddressElement = wait.waitForElementEnabled(shippingAddressClose);
        scroll.scrollElementIntoView(removeHeaderAddressElement);
        click.clickElementCarefully(removeHeaderAddressElement);
    }

    /**
     * This test clears the header level shipping address
     */
    public void clearHeaderLevelAndChangeShippingAddress(String address) {
        WebElement removeHeaderAddressElement = wait.waitForElementEnabled(shippingAddressClose);
        WebElement shipToHeaderAddressInputElement = wait.waitForElementEnabled(headerCompanyShipToAddress);

        scroll.scrollElementIntoView(removeHeaderAddressElement);
        click.clickElementCarefully(removeHeaderAddressElement);
        shipToHeaderAddressInputElement.sendKeys(address);
        shipToHeaderAddressInputElement.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        boolean isDropDownPresent = driver.findElements(By.xpath("(//input[@type='radio'])[1]")).size() > 0;
        if (isDropDownPresent) {
            wait.waitForElementDisplayed(By.xpath("(//input[@type='radio'])[1]"));
            click.javascriptClick(By.xpath("(//input[@type='radio'])[1]"));
        }
    }

    /**
     * this method add More Invoice Line for Canada, add Sales Item and wait till "Vertex" appears
     *
     * @param item   Sales Item
     * @param lineNo Invoice Line Number
     */
    public void addMoreInvoiceLineforCanada(String item, int lineNo) {
        wait.waitForElementEnabled(maximize);
        try {
            click.clickElementCarefully(maximize);
        } catch (TimeoutException e) {
            click.clickElementCarefully(maximize);
        }
        wait.waitForElementNotDisplayed(submit);
        WebElement addLine = wait.waitForAllElementsDisplayed(addInvoiceLine).get(lineNo - 1);
        click.clickElementCarefully(addLine);
        WebElement sales = wait.waitForElementPresent(getByObjOfAllLineItemsExceptFirst("item", lineNo));
        scroll.scrollElementIntoView(sales);
        click.clickElementCarefully(sales);
        text.enterText(sales, item);
        text.enterText(sales, Keys.ENTER);
        jsWaiter.sleep(3000);
        click.clickElementCarefully(maximize);
        wait.waitForElementDisplayed(submit);

    }

    /**
     * Function that takes a String containing the control total as input and enters it into the relevant field in
     * the supplier invoice form
     *
     * @param controlTotalValue a string containing the numerical Control Total value
     */
    public void enterControlTotal(String controlTotalValue) {
        WebElement ele = wait.waitForElementEnabled(controlTotal);
        for (int i = 0; i < 20; i++) {
            ele.sendKeys(Keys.BACK_SPACE);
        }
        ele.sendKeys(controlTotalValue);
    }

    /**
     * Function that navigates from page after submitting a supplier invoice to the supplier invoice landing page
     */
    public void navigateToSupplierInvoicePage() {
        WebElement ele = wait.waitForElementEnabled(viewInvoiceDetails);
        click.javascriptClick(ele);
    }

    /**
     * Function that clicks on the additional fields tab on the create supplier invoice page
     */
    public void clickAdditionalFieldsTab() {
        WebElement ele = wait.waitForElementEnabled(additionalFieldsTab);
        click.clickElementCarefully(ele);
    }

    /**
     * Function that takes a list of Strings and enters them into the additional fields on a create supplier invoice page
     */
    public void enterAdditionalFields(String[] fields) {
        for (int i = 0; i < fields.length; i++) {
            By fieldLoc = By.xpath(String.format(firstAdditionalField, i + 6));
            WebElement ele = wait.waitForElementEnabled(fieldLoc);
            scroll.scrollElementIntoView(ele);
            click.clickElementCarefully(ele);
            text.enterText(ele, fields[i]);
            jsWaiter.sleep(1000);
        }
    }

}
