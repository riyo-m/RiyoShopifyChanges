package com.vertex.quality.connectors.dynamics365.sales.pages.base;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * represents the base page for all the different pages that edit transactions
 * anything from sales quotes to sales orders,creating new or editing an existing one
 * @author Shruti
 */

public class SalesDocumentBasePage extends SalesBasePage {
    protected Actions action = new Actions(driver);

    protected By SEARCH_INPUT = By.xpath("//input[@title='Select to enter data']");
    protected By NAME_DROPDOWN = By.xpath("//div[contains(@class, 'ag-header')]/div[@col-id='vertex_name']");
    protected By CREATED_DROPDOWN = By.xpath("//div[contains(@class, 'ag-header')]/div[@col-id='createdon']");
    protected By SORT_NEWER = By.xpath("//span[text()='Sort newer to older']");
    protected By FILTER_BUTTON = By.xpath("//span[text()='Filter by']");
    protected By FILTER_OPERATOR = By.cssSelector("[aria-label='Filter by operator']");
    protected By CONTAINS_OPTION = By.xpath("//span[contains(@class, 'Dropdown-optionText') and text()='Contains']");
    protected By FILTER_INPUT = By.xpath("//input[@aria-label='Filter by value']");
    protected By APPLY_BUTTON = By.xpath("//span[contains(@class, 'Button-label') and text()='Apply']");

    protected By BACK_BUTTON = By.xpath("//button[@title='Go back']");
    protected By NEW_BUTTON = By.xpath("//button[@aria-label='New']");
    protected By EDIT_BUTTON = By.xpath("//button[@aria-label='Edit']");
    protected By DELETE_BUTTON = By.xpath("//button[@aria-label='Delete']");
    protected By SAVE_BUTTON = By.xpath("//button[@aria-label='Save (CTRL+S)']");
    protected By OK_BUTTON = By.xpath("//button[@aria-label='OK']");
    protected By YES_BUTTON = By.xpath("//button[text()='Yes']");
    protected By SAVE_AND_CLOSE_BUTTON = By.xpath("//button[@aria-label='Save & Close']");
    protected By SAVING_ALERT = By.xpath("//span[contains(text(), 'Saving')]");
    protected By SAVING_NOTIF = By.xpath("//div[@aria-labelledby = 'notification-message']");
    protected By CALCULATE_TAX_BUTTON = By.xpath("//button[@aria-label='Calculate Tax']");
    protected By MORE_COMMANDS_BUTTON = By.xpath("//ul[@aria-label='Commands']//button[@data-id='OverflowButton']");

    protected By SHIP_FROM_LOC = By.xpath("//ul[contains(@id, 'shipfrom')]/../../../../..");
    protected By SHIP_FROM_DELETE_BUTTON = By.xpath("//ul[contains(@id, 'shipfrom')]/li/button");
    protected By SHIP_FROM_INPUT = By.cssSelector("[aria-label='Ship From, Lookup']");
    protected By CALCULATION_METHOD_DROPDOWN = By.cssSelector("[aria-label='Calculation Method']");

    protected By CURRENCY_LOC = By.xpath("//ul[contains(@id, 'currency')]/../../../../..");
    protected By CURRENCY_DELETE_BUTTON = By.xpath("//ul[contains(@id, 'currency')]/li/button");
    protected By CURRENCY_INPUT = By.cssSelector("[aria-label='Currency, Lookup']");
    protected By CURRENCY_DROPDOWN = By.cssSelector("[aria-label='Search records for Currency, Lookup field']");
    protected By NAME_INPUT = By.cssSelector("[aria-label='Name']");

    protected By CUSTOMER_INPUT = By.xpath("//input[contains(@aria-label,'Customer, Lookup')]");

    protected By PRICE_LIST_LOC = By.xpath("//ul[contains(@id, 'pricelevel')]/../../../../..");
    protected By PRICE_LIST_DELETE_BUTTON = By.xpath("//ul[contains(@id, 'pricelevel')]/li/button");
    protected By PRICE_LIST_INPUT = By.cssSelector("[aria-label='Price List, Lookup']");

    protected By BILL_TO_ADDRESS_1_INPUT = By.cssSelector("[aria-label='Bill To Street 1']");
    protected By BILL_TO_CITY_INPUT = By.cssSelector("[aria-label='Bill To City']");
    protected By BILL_TO_ZIP_INPUT = By.cssSelector("[aria-label='Bill To ZIP/Postal Code']");
    protected By BILL_TO_COUNTRY_INPUT = By.cssSelector("[aria-label='Bill To Country/Region']");
    protected By SHIP_TO_DROPDOWN = By.cssSelector("[aria-label='Ship To']");
    protected By SHIP_TO_ADDRESS_1_INPUT = By.cssSelector("[aria-label='Ship To Street 1']");
    protected By SHIP_TO_CITY_INPUT = By.cssSelector("[aria-label='Ship To City']");
    protected By SHIP_TO_ZIP_INPUT = By.cssSelector("[aria-label='Ship To ZIP/Postal Code']");
    protected By SHIP_TO_COUNTRY_INPUT = By.cssSelector("[aria-label='Ship To Country/Region']");


    protected By ADD_PRODUCT_BUTTON = By.xpath("//button[@aria-label='Add Product']");
    protected By PRODUCT_INPUT = By.cssSelector("[aria-label='Existing Product, Lookup']");
    protected By QUANTITY_INPUT = By.cssSelector("[aria-label='Quantity']");
    protected By DISCOUNT_INPUT = By.xpath("//input[contains(@aria-label,'Manual Discount')]");
    protected By SAVE_PRODUCT_BUTTON = By.xpath("//button[@aria-label='Save and Close' and contains(@id, 'quickCreate')]");


    protected By DISCOUNT_PERCENT_INPUT = By.xpath("//input[contains(@data-id,'discountpercentage')]");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath("//input[contains(@data-id,'discountamount')]");
    protected By TOTAL_TAX_INPUT = By.xpath("//input[contains(@aria-label, 'Total Tax')]");
    protected By TOTAL_AMOUNT_INPUT = By.xpath("//input[contains(@aria-label, 'Total Amount')]");

    protected By TAX_SUM_ROW = By.xpath("//div[contains(@id,'Vertex')]//div[@class='ag-center-cols-container']/div");
    protected By TAX_SUM_NEXT_PAGE = By.xpath("//div[contains(@id,'Vertex')]//button[@aria-label='Next page']");
    protected By FREIGHT_AMOUNT_INPUT = By.xpath("//input[contains(@aria-label, '(+) Freight Amount')]");

    protected By RELATED_TAB = By.cssSelector("[aria-label='Related']");
    protected By INVOICES_NAV = By.id("navInvoices");
    protected By NEW_INVOICE_BUTTON = By.xpath("//button[contains(@aria-label, 'New Invoice')]");
    public SalesDocumentBasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Searches for a document (order, quote, invoice) and opens it
     * @param id
     */
    public void openDocument(String id) {
        WebElement search = wait.waitForElementDisplayed(SEARCH_INPUT);
        text.selectAllAndInputText(search, id);
        text.pressEnter(search);

        waitForPageLoad();

        By docLinkLoc = By.xpath(String.format("//a[@aria-label='%s']", id));
        wait.waitForElementDisplayed(docLinkLoc);
        click.clickElementIgnoreExceptionAndRetry(docLinkLoc);

        waitForPageTitleContains(id);
    }

    /**
     * Click 'New' button
     */
    public void clickNew() {
        waitForLoadingScreen();
        wait.waitForElementDisplayed(NEW_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(NEW_BUTTON);

        waitForLoadingScreen();
    }

    /**
     * Click 'Go back' button
     */
    public void clickGoBack() {
        waitForLoadingScreen();
        wait.waitForElementDisplayed(BACK_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(BACK_BUTTON);

        waitForPageLoad();
    }

    /**
     * Click 'Delete' button
     */
    public void clickDelete() {
        wait.waitForElementDisplayed(DELETE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(DELETE_BUTTON);

        waitForPageLoad();
    }

    /**
     * Click 'Save' button
     */
    public void clickSave() {
        wait.waitForElementDisplayed(SAVE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SAVE_BUTTON);

        waitForPageLoad();
        wait.waitForElementNotDisplayed(SAVING_ALERT, 120);
    }

    /**
     * Clicks on Ok
     */
    public void clickOk() {
        wait.waitForElementDisplayed(OK_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click 'Save & Close' button
     */
    public void clickSaveAndClose() {
        wait.waitForElementDisplayed(SAVE_AND_CLOSE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SAVE_AND_CLOSE_BUTTON);

        waitForPageLoad();
        wait.waitForElementNotDisplayed(SAVING_ALERT, 120);
    }

    /**
     * Click 'Calculate Tax' button
     */
    public void clickCalculateTax() {
        waitForPageLoad();
        if (!element.isElementDisplayed(CALCULATE_TAX_BUTTON)) {
            wait.waitForElementDisplayed(MORE_COMMANDS_BUTTON);
            click.clickElementIgnoreExceptionAndRetry(MORE_COMMANDS_BUTTON);
        }
        wait.waitForElementDisplayed(CALCULATE_TAX_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(CALCULATE_TAX_BUTTON);

        waitForPageLoad();
        wait.waitForElementNotDisplayed(SAVING_ALERT, 30);
    }

    /**
     * Click 'Add Product' button
     */
    public void clickAddProduct() {
        wait.waitForElementDisplayed(ADD_PRODUCT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(ADD_PRODUCT_BUTTON);

        waitForPageLoad();
    }

    /**
     * Get auto-generated id of document
     * @param docType
     * @return id
     */
    public String getID(String docType) {
        By docIDLoc = By.cssSelector(String.format("[aria-label='%s ID'", docType));
        WebElement docIDEl = wait.waitForElementDisplayed(docIDLoc);
        String id = docIDEl.getAttribute("value");

        return id;
    }

    /**
     * Get auto-generated id of document and set document name to id
     * @param docType
     * @return id
     */
    public String getIDAndUpdateName(String docType) {
        By docIDLoc = By.cssSelector(String.format("[aria-label='%s ID'", docType));
        WebElement docIDEl = wait.waitForElementDisplayed(docIDLoc);
        String id = docIDEl.getAttribute("value");

        enterDocumentName(id);
        clickSave();

        String docIdentification = String.format("%s: %s", docType, id);
        System.out.println(docIdentification);
        return id;
    }


    /**
     * Get Calculation Method of document
     * @return method
     */
    public String getCalculationMethod() {
        WebElement calculationMethodDropdown = wait.waitForElementDisplayed(CALCULATION_METHOD_DROPDOWN);
        String method = calculationMethodDropdown.getAttribute("title");

        return method;
    }

    /**
     * Get Total Tax of document
     * @return tax
     */
    public String getTotalTax() {
        jsWaiter.sleep(5000);
        WebElement totalTaxEl = wait.waitForElementDisplayed(TOTAL_TAX_INPUT);
        String tax = parseNumericAmount(totalTaxEl.getAttribute("value"));

        return tax;
    }

    /**
     * Get Total Amount of document
     * @return tax
     */
    public String getTotalAmount() {
        WebElement totalAmountEl = wait.waitForElementDisplayed(TOTAL_AMOUNT_INPUT);
        String amount = parseNumericAmount(totalAmountEl.getAttribute("value"));

        return amount;
    }

    /**
     * Set name of document
     * @param name
     */
    public void enterDocumentName(String name) {
        wait.waitForElementEnabled(NAME_INPUT);
        click.clickElementIgnoreExceptionAndRetry(NAME_INPUT);

        text.selectAllAndInputText(NAME_INPUT, name);
    }

    /**
     * Set currency of document
     * @param currency
     */
    public void enterCurrency(String currency) {
        waitForPageLoad();

        if (element.isElementDisplayed(CURRENCY_LOC)) {
            click.clickElementIgnoreExceptionAndRetry(CURRENCY_LOC);
            if(element.isElementDisplayed(CURRENCY_DELETE_BUTTON)) {
                click.clickElementIgnoreExceptionAndRetry(CURRENCY_DELETE_BUTTON);
            }
            WebElement dropdownSearch = wait.waitForElementDisplayed(CURRENCY_DROPDOWN);
            action.click(dropdownSearch).perform();
        } else {
            click.clickElementIgnoreExceptionAndRetry(CURRENCY_INPUT);
            text.selectAllAndInputText(CURRENCY_INPUT, currency);
        }

        By dropdownEl = By.xpath(String.format("//span[text()='%s']", currency));
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }

    /**
     * Set price list of document
     * @param priceList
     */
    public void enterPriceList(String priceList) {
        if (element.isElementDisplayed(PRICE_LIST_LOC)) {
            click.clickElementIgnoreExceptionAndRetry(PRICE_LIST_LOC);
            wait.waitForElementDisplayed(PRICE_LIST_DELETE_BUTTON);
            click.clickElementIgnoreExceptionAndRetry(PRICE_LIST_DELETE_BUTTON);
        }

        wait.waitForElementEnabled(PRICE_LIST_INPUT);

        click.clickElementIgnoreExceptionAndRetry(PRICE_LIST_INPUT);
        text.selectAllAndInputText(PRICE_LIST_INPUT, priceList);

        By dropdownEl = By.xpath(String.format("//span[contains(@id, 'pricelevelid')]//span[text()='%s']", priceList));
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }

    /**
     * Set the ship from location for document
     * @param location
     */
    public void enterShipFromAddress(String location) {
        waitForPageLoad();

        if (element.isElementDisplayed(SHIP_FROM_LOC)) {
            click.clickElementIgnoreExceptionAndRetry(SHIP_FROM_LOC);
            wait.waitForElementDisplayed(SHIP_FROM_DELETE_BUTTON);
            click.clickElementIgnoreExceptionAndRetry(SHIP_FROM_DELETE_BUTTON);
        }

        click.clickElementIgnoreExceptionAndRetry(SHIP_FROM_INPUT);
        text.selectAllAndInputText(SHIP_FROM_INPUT, location);

        By dropdownEl = By.xpath(String.format("//span[contains(@id, 'shipfrom')]//span[text()='%s']", location));
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }
    /**
     * Set customer of document
     * @param customer
     */
    public void enterCustomer(String customer) {
        waitForPageLoad();
        By dropdownEl = By.xpath(String.format("//span[contains(@id, 'customerid')]//span[text()='%s']", customer));

        wait.waitForElementEnabled(CUSTOMER_INPUT);
        click.clickElementIgnoreExceptionAndRetry(CUSTOMER_INPUT);

        text.selectAllAndInputText(CUSTOMER_INPUT, customer);
        if (!element.isElementDisplayed(dropdownEl)) {
            text.selectAllAndInputText(CUSTOMER_INPUT, customer);
        }
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }

    /**
     * Enter product name when adding new product
     * @param product
     */
    public void enterProductName(String product) {
        waitForPageLoad();

        wait.waitForElementEnabled(PRODUCT_INPUT);
        click.clickElementIgnoreExceptionAndRetry(PRODUCT_INPUT);

        text.selectAllAndInputText(PRODUCT_INPUT, product);

        By dropdownEl = By.xpath(String.format("//span[contains(@id, 'productid')]//span[text()='%s']", product));
        click.clickElementIgnoreExceptionAndRetry(dropdownEl);
    }

    /**
     * Enter quantity when adding new product
     * @param quantity
     */
    public void enterQuantity(String quantity) {
        wait.waitForElementEnabled(QUANTITY_INPUT);
        click.clickElementIgnoreExceptionAndRetry(QUANTITY_INPUT);

        text.selectAllAndInputText(QUANTITY_INPUT, quantity);
        text.pressEnter(QUANTITY_INPUT);
    }

    /**
     * Enter discount amount when adding new product
     * @param discountAmount
     */
    public void enterDiscount(String discountAmount) {
        wait.waitForElementEnabled(DISCOUNT_INPUT);
        click.clickElementIgnoreExceptionAndRetry(DISCOUNT_INPUT);

        text.selectAllAndInputText(DISCOUNT_INPUT, discountAmount);
    }

    /**
     * Enter discount amount for document
     * @param discountAmount
     */
    public void enterDiscountAmount(String discountAmount) {
        wait.waitForElementEnabled(DISCOUNT_AMOUNT_INPUT);
        click.clickElementIgnoreExceptionAndRetry(DISCOUNT_AMOUNT_INPUT);

        text.selectAllAndInputText(DISCOUNT_AMOUNT_INPUT, discountAmount);
    }

    /**
     * Enter discount percentage for document
     * @param discountPercent
     */
    public void enterDiscountPercent(String discountPercent) {
        wait.waitForElementEnabled(DISCOUNT_PERCENT_INPUT);
        click.clickElementIgnoreExceptionAndRetry(DISCOUNT_PERCENT_INPUT);

        text.selectAllAndInputText(DISCOUNT_PERCENT_INPUT, discountPercent);
    }

    /**
     * Enter street address of billing address for document
     * @param address
     */
    public void enterBillToAddress(String address) {
        wait.waitForElementEnabled(BILL_TO_ADDRESS_1_INPUT);
        click.clickElementIgnoreExceptionAndRetry(BILL_TO_ADDRESS_1_INPUT);

        text.selectAllAndInputText(BILL_TO_ADDRESS_1_INPUT, address);
    }

    /**
     * Enter city of billing address for document
     * @param city
     */
    public void enterBillToCity(String city) {
        wait.waitForElementEnabled(BILL_TO_CITY_INPUT);
        click.clickElementIgnoreExceptionAndRetry(BILL_TO_CITY_INPUT);

        text.selectAllAndInputText(BILL_TO_CITY_INPUT, city);
    }

    /**
     * Enter zip code of billing address for document
     * @param zipCode
     */
    public void enterBillToZipCode(String zipCode) {
        wait.waitForElementEnabled(BILL_TO_ZIP_INPUT);
        click.clickElementIgnoreExceptionAndRetry(BILL_TO_ZIP_INPUT);

        text.selectAllAndInputText(BILL_TO_ZIP_INPUT, zipCode);
    }

    /**
     * Enter country of billing address for document
     * @param country
     */
    public void enterBillToCountry(String country) {
        wait.waitForElementEnabled(BILL_TO_COUNTRY_INPUT);
        click.clickElementIgnoreExceptionAndRetry(BILL_TO_COUNTRY_INPUT);

        text.selectAllAndInputText(BILL_TO_COUNTRY_INPUT, country);
    }

    /**
     * Fill in billing address info for document
     * @param docType
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillBillToAddressInfo(String docType, String streetAddress, String city, String zipCode, String country) {
        // Scroll to address window
        By docInfoLoc = By.xpath(String.format("//section[contains(@id, '%s information')]", docType.toLowerCase()));
        scroll.scrollElementIntoView(docInfoLoc, PageScrollDestination.BOTTOM);

        enterBillToAddress(streetAddress);
        enterBillToCity(city);
        enterBillToZipCode(zipCode);
        enterBillToCountry(country);
    }

    /**
     * Enter address of shipping address for document
     * @param address
     */
    public void enterShipToAddress(String address) {
        wait.waitForElementEnabled(SHIP_TO_ADDRESS_1_INPUT);
        click.clickElementIgnoreExceptionAndRetry(SHIP_TO_ADDRESS_1_INPUT);

        text.selectAllAndInputText(SHIP_TO_ADDRESS_1_INPUT, address);
    }

    /**
     * Enter city of shipping address for document
     * @param city
     */
    public void enterShipToCity(String city) {
        wait.waitForElementEnabled(SHIP_TO_CITY_INPUT);
        click.clickElementIgnoreExceptionAndRetry(SHIP_TO_CITY_INPUT);

        text.selectAllAndInputText(SHIP_TO_CITY_INPUT, city);
    }

    /**
     * Enter zip code of shipping address for document
     * @param zipCode
     */
    public void enterShipToZipCode(String zipCode) {
        wait.waitForElementEnabled(SHIP_TO_ZIP_INPUT);
        click.clickElementIgnoreExceptionAndRetry(SHIP_TO_ZIP_INPUT);

        text.selectAllAndInputText(SHIP_TO_ZIP_INPUT, zipCode);
    }

    /**
     * Enter country of shipping address for document
     * @param country
     */
    public void enterShipToCountry(String country) {
        wait.waitForElementEnabled(SHIP_TO_COUNTRY_INPUT);
        click.clickElementIgnoreExceptionAndRetry(SHIP_TO_COUNTRY_INPUT);

        text.selectAllAndInputText(SHIP_TO_COUNTRY_INPUT, country);
    }

    /**
     * Fill in shipping address info for document
     * @param docType
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillShipToAddressInfo(String docType, String streetAddress, String city, String zipCode, String country) {
        // Scroll to address window
        By docInfoLoc = By.xpath(String.format("//section[contains(@id, '%s information')]", docType.toLowerCase()));
        scroll.scrollElementIntoView(docInfoLoc, PageScrollDestination.BOTTOM);

        enterShipToAddress(streetAddress);
        enterShipToCity(city);
        enterShipToZipCode(zipCode);
        enterShipToCountry(country);
    }

    /**
     * Fill in name and price list for document
     * @param name
     * @param priceList
     */
    public void fillGeneralInfo(String name, String priceList) {
        enterDocumentName(name);
        enterPriceList(priceList);
    }

    /**
     * Fill in name, currency and price list for document
     * @param name
     * @param priceList
     */
    public void fillGeneralInfo(String name, String currency, String priceList) {
        enterDocumentName(name);
        enterCurrency(currency);
        enterPriceList(priceList);
    }


    /**
     * Update quantity and discount amount for product in specified row of Product Table
     * @param quantity
     * @param discount
     * @param rowIndex
     */
    public void updateProductTableInfo(String quantity, String discount, int rowIndex) {
        updateQuantity(quantity, rowIndex);
        updateDiscount(discount, rowIndex);
    }

    /**
     * Delete product in specified row of Product Table
     * @param rowIndex
     */
    public void deleteProduct(int rowIndex) {
        String index = String.valueOf(rowIndex + 1);
        By rowLoc = By.xpath(String.format(String.format("//div[@class='wj-row' and @aria-rowindex='%s']//div[@aria-colindex='2']", index)));
        click.clickElementIgnoreExceptionAndRetry(rowLoc);

        click.clickElementIgnoreExceptionAndRetry(EDIT_BUTTON);
        waitForPageLoad();

        wait.waitForElementDisplayed(DELETE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(DELETE_BUTTON);

        wait.waitForElementDisplayed(confirmButton);
        click.clickElementIgnoreExceptionAndRetry(confirmButton);

        waitForPageLoad();
    }

    /**
     * Set quantity of product in specified row of Product Table
     * @param quantity
     * @param rowIndex
     */
    public void updateQuantity(String quantity, int rowIndex) {
        String index = String.valueOf(rowIndex + 1);

        String quantityColLoc = String.format("//div[@class='wj-row' and @aria-rowindex='%s']//div[@aria-colindex='11']", index);
        WebElement quantityCol = wait.waitForElementDisplayed(By.xpath(quantityColLoc));
        click.clickElementIgnoreExceptionAndRetry(quantityCol);

        By quantityInputLoc = By.xpath(quantityColLoc + "//input[@type='text']");
        text.selectAllAndInputText(quantityInputLoc, quantity);
    }

    /**
     * Set discount amount of product in specified row of Product Table
     * @param discountAmount
     * @param rowIndex
     */
    public void updateDiscount(String discountAmount, int rowIndex) {
        String index = String.valueOf(rowIndex + 1);

        String discountColLoc = String.format("//div[@class='wj-row' and @aria-rowindex='%s']//div[@aria-colindex='12']", index);
        WebElement discountCol = wait.waitForElementDisplayed(By.xpath(discountColLoc));
        click.clickElementIgnoreExceptionAndRetry(discountCol);

        By discountInputLoc = By.xpath(discountColLoc + "//input[@type='text']");
        text.selectAllAndInputText(discountInputLoc, discountAmount);
    }

    /**
     * Add product to Product Table
     * @param productName
     * @param quantity
     * @param discountAmount
     */
    public void addProduct(String productName, String quantity, String discountAmount) {
        clickAddProduct();
        enterProductName(productName);
        enterQuantity(quantity);

        jsWaiter.sleep(2500);

        if (discountAmount != null) {
            enterDiscount(discountAmount);
        }

        WebElement saveButton = wait.waitForElementDisplayed(SAVE_PRODUCT_BUTTON);
        action.moveToElement(saveButton).perform();
        click.clickElementIgnoreExceptionAndRetry(saveButton);
        if(element.isElementEnabled(saveButton)) {
            action.click(saveButton).perform();
        }
        waitForPageLoad();
        wait.waitForElementNotDisplayed(SAVING_ALERT);
    }

    /**
     * Enter freight amount for document
     * @param freightAmount
     */
    public void enterFreightAmount(String freightAmount) {
        wait.waitForElementEnabled(FREIGHT_AMOUNT_INPUT);
        click.clickElementIgnoreExceptionAndRetry(FREIGHT_AMOUNT_INPUT);

        text.selectAllAndInputText(FREIGHT_AMOUNT_INPUT, freightAmount);
        text.pressEnter(FREIGHT_AMOUNT_INPUT);
    }

    /**
     * Get specific detail from all rows in Tax Summaries
     * @param detailType
     * @return
     */
    public List<String> getTaxSummariesDetails(String detailType) {
        clickCalculateTax();
        List<String> summaryDetails = new ArrayList<String>();
        int detailIndex = 1;
        switch(detailType) {
            case "Jurisdiction ID":
                detailIndex = 1;
                break;
            case "Jurisdiction Level":
                detailIndex = 2;
                break;
            case "Tax Rate":
                detailIndex = 3;
                break;
            case "Tax Amount":
                detailIndex = 4;
                break;
            case "Taxable Amount":
                detailIndex = 5;
                break;
        }

        WebElement nextPageButton;
        String noMorePages;
        do {
            waitForPageLoad();
            nextPageButton = wait.waitForElementDisplayed(TAX_SUM_NEXT_PAGE);
            List<WebElement> rows = wait.waitForAllElementsDisplayed(TAX_SUM_ROW);

            for (WebElement row : rows) {
                List<WebElement> rowDetails = element.getWebElements(By.tagName("label"), row);
                String detail = parseNumericAmount(rowDetails.get(detailIndex).getText());
                summaryDetails.add(detail);
            }

            noMorePages = nextPageButton.getAttribute("aria-disabled");
            if (noMorePages.equals("false")) {
                click.clickElementIgnoreExceptionAndRetry(nextPageButton);
                waitForPageLoad();
            }
        } while (noMorePages.equals("false"));

        return summaryDetails;
    }

    /**
     * Remove currency sign from string and return numeric value
     * @param amount
     * @return parsedAmount
     */
    public String parseNumericAmount(String amount) {
        String parsedAmount = amount.trim();

        Matcher m = Pattern.compile("\\d+[.]\\d+").matcher(parsedAmount);
        if (m.find()) {
            parsedAmount = m.group(0);
        }

        return parsedAmount;
    }

    /**
     * Remove text before a colon in string and return parsed string
     * @param input
     * @return
     */
    public String removeTextBeforeColon(String input) {
        int colonIndex = input.indexOf(":");
        if (colonIndex != -1 && colonIndex < input.length() - 1) {
            return input.substring(colonIndex + 1).trim();
        }
        return input;
    }


}
