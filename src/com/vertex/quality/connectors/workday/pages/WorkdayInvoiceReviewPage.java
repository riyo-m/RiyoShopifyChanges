package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexFileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class represents Invoice review page that comes after successfully submitting customer/Supplier Invoice
 * contains all the methods necessary to interact with the page
 *
 * @author dpatel
 */
public class WorkdayInvoiceReviewPage extends VertexPage {

    public WorkdayInvoiceReviewPage(WebDriver driver) {
        super(driver);
    }

    protected By detailsTab = By.xpath("(//div[contains(text(),'Details')])[1]");
    protected By processTab = By.xpath("(//div[@data-automation-id='tabLabel' and contains(text(),'Process')])[1]");
    protected By retrieveLogFilesButton = By.xpath("//button[@title='Retrieve Log Files']");
    protected By confirmCheckbox = By.xpath("//div[@data-automation-id='checkboxPanel']");
    protected By retrieveLogOkButton = By.xpath("//span[@title='OK']");
    protected By processHistory = By.xpath("(//div[contains(text(),'Process')])[1]");
    protected By taxTab = By.xpath("(//div[text()='Tax'])[1]");
    protected By bpTab = By.xpath("(//div[text()='Business Process'])[1]");
    protected By customerInvoiceStausLink = By.xpath("(//div[contains(text(),'Customer Invoice')])[1]");
    protected By overallStatus = By.xpath("(//div[@data-automation-id='textView'])[1]");
    protected By errorComment = By.xpath("(//div[@data-automation-id='textView'])[35]");
    protected By submitVerify = By.xpath("//span[@title= 'You have submitted']");
    protected By reviewButton = By.xpath("//button[@title='Review']");
    protected By approveButton = By.xpath("//button[@title='Approve']");
    protected By doneButton = By.xpath("//button[@title='Done']");
    protected By taxAmount = By.xpath("//label[text()='Tax Amount']/../following-sibling::div");
    protected By thirdPartyTaxCalc = By.xpath("//div[@title='Complete']");
    protected By custInvDropdown = By.xpath("//div[@data-automation-label='Customer Invoice']");
    protected By suplInvDropdown = By.xpath("//div[@data-automation-label='Supplier Invoice']");
    protected By creditRebill = By.xpath("//div[@data-automation-label='Create Credit and Rebill']");
    protected By change = By.xpath("//div[@data-automation-label='Change']");
    protected By adjustment = By.xpath("//div[@data-automation-label='Create Adjustment']");
    protected By custInvAdj = By.xpath("//span[@title='Create Customer Invoice Adjustment']");
    protected By suplInvAdj = By.xpath("//span[@title='Create Adjustment Supplier Invoice']");
    protected By creditAdjustment = By.xpath("//span[text()='Credit Adjustment Information']");
    protected By changeCustInv = By.xpath("//span[text()='Change Customer Invoice']");
    protected By changeSuplInv = By.xpath("//span[text()='Change Supplier Invoice']");
    protected By newCustInvLink = By.xpath("(//div[contains(text(),'Customer Invoice:')])[2]");
    protected By invoiceRelatedActions = By.xpath("//*[@title='View Customer Invoice']/../..//ul//img");
    protected By creditAdjReason = By.xpath("//input[@dir='ltr' and @aria-required='true']");
    protected By adjustType = By.xpath("(//span[@data-automation-id='radioBtn'])[2]");
    protected By enteredReason = By.xpath("//p[@title='Price Adjustment']");
    protected By okButton = By.xpath("//span[@title='OK']");
    protected By unpaidTab = By.xpath("//div[text()='Unpaid']");
    protected By adjustedInvoiceLink = By.xpath("//div[contains(@data-automation-label,'Customer Invoice Adjustment')]");
    protected By invAdjTitle = By.xpath("//span[@title='View Customer Invoice Adjustment']");
    protected By supplierInvoiceStatusLink = By.xpath("(//div[contains(text(),'Supplier Invoice')])[1]");
    protected By invoiceNo = By.xpath("(//div[@data-automation-id='textView'])[4]");
    protected By firstBPLink = By.xpath("(//div[contains(@data-automation-label,'Integration Process: VTX')])[2]");
    protected By secondBPLink = By.xpath("(//div[contains(@data-automation-label,'Integration Process: VTX')])[4]");
    protected By preprocessLink = By.xpath("(//div[contains(@data-automation-label,'VTX-SupplierInvoicePreProcessBP')])[2]");
    protected By server = By.xpath("(//span[contains(text(),'server-')])[1]");
    protected By secondServer = By.xpath("(//span[contains(text(),'server-')])[2]");
    protected By consolidatedReport = By.xpath("(//span[@title= 'Consolidated Report and Logs'])[2]");
    protected By custInvLink = By.xpath("(//div[@data-automation-id='promptOption'])[1]");
    protected By custInvAdjLink = By.xpath("(//div[@data-automation-id='promptOption'])[2]");
    protected By title = By.cssSelector("span[data-automation-id='pageHeaderTitleText']");
    protected By viewSuplInv = By.xpath("//span[contains(@title,'View Supplier Invoice')]");
    protected By additionalData = By.xpath("//div[@data-automation-label='Additional Data']");
    protected By vertexCallType = By.xpath("(//div[@data-automation-id='textView'])[2]");
    protected By vertexCallStatus = By.xpath("(//div[@data-automation-id='textView'])[3]");
    protected By vertexPostedFlag = By.xpath("(//div[@data-automation-id='textView'])[5]");
    protected By invStatus = By.xpath("//label[text()='Invoice Status']");
    protected By custInvoice = By.xpath("//span[text()='VertexCustomerInvoice']");
    protected By taxCalcError = By.xpath("(//div[@data-automation-id='textView'])[14]");
    protected By maximizeScreen = By.xpath("(//div[@title='Toggle Fullscreen Viewing Mode'])[2]");
    protected By lineTaxes = By.xpath("//div[@title='The maximum estimated tax amount when realization point is payment.']");
    protected By rollUpTax = By.xpath("//div[text()='ROLLUP']");
    protected By error = By.xpath("//div[@title='Error']");
    protected By newSuplInvLink = By.xpath("(//div[contains(@title,'Supplier Invoice')])[2]");
    protected By ellipsis = By.xpath("(//div[@data-automation-id='menuItem']//li)[2]");
    protected By transactionsTaxTable = By.xpath("//table[contains(.,'Transaction Tax')]/tbody/tr");
    protected By detailsReview = By.xpath("//span[text()='Details and Process']");
    protected By lineTransactionsTaxTable = By.xpath("//table[@data-automation-id='gridChunk-0']/tbody/tr");
    protected By drillDownNumber = By.xpath("//div[@data-automation-id='drillDownNumber']");
    protected By closeSymbol = By.xpath("//div[@data-automation-id='closeButton']");
    protected By parentEventLink = By.xpath("(//div[@data-automation-id='promptOption'])[5]");
    protected By submit = By.xpath("//span[@title='Submit']");


    /**
     * This method verify whether submit was successful or not
     *
     * @return "true" if successful , "false" if it fails
     */
    public boolean verifySubmit() {

        boolean isSubmitDone = true;
        try {
            wait.waitForElementPresent(submitVerify, 15);
        } catch (TimeoutException e) {
            VertexLogger.log("Customer Invoice is not submitted : Please check the screenshot");
            isSubmitDone = false;
        }
        return isSubmitDone;
    }

    /**
     * This method clicks on customer Invoice Process link to get the updated "Overall Status"
     */
    public void clickCustomerProcessLink() {

        try {
            wait.waitForElementEnabled(customerInvoiceStausLink).click();
        } catch (StaleElementReferenceException e) {
            wait.waitForElementEnabled(customerInvoiceStausLink).click();
        }
    }

    /**
     * This method clicks on supplier Invoice Process link to get the updated "Overall Status"
     */
    public void clickSupplierProcessLink() {

        try {
            wait.waitForElementEnabled(supplierInvoiceStatusLink).click();
        } catch (StaleElementReferenceException e) {
            wait.waitForElementEnabled(supplierInvoiceStatusLink).click();
        }
    }

    /**
     * This method verifies the connector Integration is completed or not
     * If not completed then it print out the error logs
     *
     * @return "true" if completed , "False" if not completed
     */
    public boolean integrationCompletedVerification(String typeOfInvoice) {

        String status;
        boolean isReviewPresent;
        boolean isIntegrationComplete = false;
        int time;
        click.clickElementCarefully(detailsReview);
        if (typeOfInvoice.equals("Customer")) {
            clickCustomerProcessLink();
        } else {
            clickSupplierProcessLink();
        }
        waitForPageLoad();
        isReviewPresent = element.isElementEnabled(reviewButton);
        time = 0;
        while (!isIntegrationComplete && (time <= 120)) {
            if (isReviewPresent) {
                click.javascriptClick(reviewButton);
                wait.waitForElementEnabled(approveButton);
                click.javascriptClick(approveButton);
                wait.waitForElementEnabled(doneButton);
                click.javascriptClick(doneButton);
            }
            if (typeOfInvoice.equals("Customer")) {
                clickCustomerProcessLink();
            } else {
                clickSupplierProcessLink();
            }
            try {
                status = wait.waitForElementDisplayed(overallStatus).getText();
            } catch (StaleElementReferenceException e) {
                status = wait.waitForElementDisplayed(overallStatus).getText();
            }
            if (status.toLowerCase().contains("in progress")) {
                isReviewPresent = element.isElementDisplayed(reviewButton);
            } else if (status.toLowerCase().equalsIgnoreCase("In Progress") && typeOfInvoice.equalsIgnoreCase("Supplier") && !element.isElementDisplayed(error)) {
                break;
            } else if (status.equals("Successfully Completed")) {
                isIntegrationComplete = true;
            } else {
                switchToTab("Process");
                if (driver.findElements(errorComment).size() != 0) {
                    VertexLogger.log(wait.waitForElementEnabled(errorComment).getText());
                }
                break;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time += 5;
            VertexLogger.log("Waited for " + time + " Seconds for Integration to be completed ");
        }
        if (!isIntegrationComplete) {
            VertexLogger.log("Integration is not completed successfully");
        }
        return isIntegrationComplete;
    }

    /**
     * This method verifies the connector Integration is completed or not
     * If not completed then it print out the error logs
     *
     * @return "true" if completed , "False" if not completed
     */
    public boolean integrationNotCompletedVerification() {

        String status = null;
        boolean isReviewPresent;
        boolean isIntegrationCompleted = false;
        int i = 0;
        clickSupplierProcessLink();
        waitForPageLoad();
        isReviewPresent = element.isElementDisplayed(reviewButton);
        while ((!isIntegrationCompleted) && (i < 10)) {
            if (isReviewPresent) {
                click.javascriptClick(reviewButton);
                wait.waitForElementEnabled(approveButton);
                click.javascriptClick(approveButton);
                wait.waitForElementEnabled(doneButton);
                click.javascriptClick(doneButton);
            }
            clickSupplierProcessLink();
            try {
                status = wait.waitForElementDisplayed(overallStatus).getText();
            } catch (StaleElementReferenceException e) {
                status = wait.waitForElementDisplayed(overallStatus).getText();
            }
            try {
                wait.waitForElementDisplayed(adjustment, 3);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            isReviewPresent = element.isElementDisplayed(reviewButton);
            i++;
        }
        assert status != null;
        if (status.equalsIgnoreCase("in progress")) {
            isIntegrationCompleted = true;
        } else {
            isIntegrationCompleted = false;
            VertexLogger.log("Overall Status is not expected : Expected status is 'In Progress' and actual is: " + status);
        }
        return isIntegrationCompleted;
    }

    /**
     * This method is to switch tab such as "Details" , "Process" and "Related Links"
     *
     * @param tabName Name of the tab shows on UI
     */
    public void switchToTab(String tabName) {

        switch (tabName) {

            case "Details":
                WebDriverWait shortWait = new WebDriverWait(driver, QUARTER_MINUTE_TIMEOUT);
                shortWait
                        .ignoring(StaleElementReferenceException.class)
                        .until(ExpectedConditions.elementToBeClickable(detailsTab));
                wait.waitForElementDisplayed(detailsTab).click();
                waitForPageLoad();
                break;

            case "Process":
                wait.waitForElementDisplayed(processTab).click();
                waitForPageLoad();
                break;

            case "Related Links":
                //To Do
                break;

            case "Tax":
                wait.waitForElementDisplayed(taxTab).click();
                waitForPageLoad();
                break;

            case "Business Process":
                wait.waitForElementEnabled(bpTab).click();
                waitForPageLoad();
                break;

            case "Process History":
                wait.waitForElementDisplayed(processHistory).click();
                waitForPageLoad();
                break;
        }
    }

    /**
     * this method locates and clicks the Maxine heading in the cart page, which redirects the user
     * back to the store to shop for more items
     *
     * @return value from the tax amount field
     */
    public String getValuesFromTaxAmount() {

        String taxCalcAmount;
        if (element.isElementDisplayed(detailsTab)) {
            switchToTab("Details");
        }
        try {
            WebElement text = wait.waitForElementDisplayed(taxAmount, 2);
            taxCalcAmount = text.getText();
        } catch (TimeoutException e) {
            taxCalcAmount = "0";
        }
        return taxCalcAmount;

    }

    /**
     * this method locates and clicks the Maxine heading in the cart page, which redirects the user
     * back to the store to shop for more items
     *
     * @param jurisdiction
     * @param lineLevelTaxes
     * @return value from the jurisdiction and country level tax amount fields
     */
    public boolean verifyJurisdictionLineLevelTaxes(String[] jurisdiction, String[] lineLevelTaxes) {
        boolean isVerified = false;
        String productXpath;
        for (int i = 0; i < jurisdiction.length; i++) {
            productXpath = String.format("//div[text()='%s']/ancestor::tr//td[12]", jurisdiction[i]);
            WebElement productName = wait.waitForElementPresent(By.xpath(productXpath));
            String actualTax = text.getElementText(productName);
            if (actualTax.equalsIgnoreCase(lineLevelTaxes[i])) {
                isVerified = true;
            } else {
                VertexLogger.log("Jurisdiction :" + jurisdiction[i] + " has incorrect tax, Actual Tax is: " + actualTax +
                        " Expected Tax is:" + lineLevelTaxes[i]);
                isVerified = false;
                break;
            }
        }
        return isVerified;
    }

    /**
     * this method locates and clicks the Maxine heading in the cart page, which redirects the user
     * back to the store to shop for more items
     *
     * @return values from the each products and tax amount from transaction tax drill down table and compares
     * @author dpatel
     */

    public boolean verifyProductLineTax(String[] productText, String[] productTaxCalcAmount) {
        boolean isVerified = false;
        String productXpath;
        if (element.isElementDisplayed(detailsTab)) {
            switchToTab("Details");
        }
        if (element.isElementDisplayed(taxTab)) {
            switchToTab("Tax");
        }
        click.clickElement(drillDownNumber);
        waitForPageLoad();
        for (int i = 0; i < productText.length; i++) {
            productXpath = String.format("(//div[@data-uxi-element-id='popupInfo']//div[@data-automation-id='scrollPanel']//div[contains(@title,'%s')]/ancestor::tr//div[@data-automation-id='numericText'])[2]", productText[i]);
            WebElement productName = wait.waitForElementPresent(By.xpath(productXpath));
            String actualTax = text.getElementText(productName);
            if (actualTax.equalsIgnoreCase(productTaxCalcAmount[i])) {
                isVerified = true;
            } else {
                System.out.println(actualTax);
                VertexLogger.log("Sales Item :" + productText[i] + " has incorrect tax, \n\nActual Tax is: " + actualTax +
                        "\nExpected Tax is: " + productTaxCalcAmount[i]);
                isVerified = false;
                break;
            }
        }
        waitForPageLoad();
        return isVerified;
    }

    /**
     * This method locates and clicks the close symbol
     * It will navigate user to the Invoice review page
     *
     * @return workdayInvoiceReviewPage object
     */
    public WorkdayInvoiceReviewPage clickOnClose() {
        wait.waitForElementDisplayed(closeSymbol).click();
        return (new WorkdayInvoiceReviewPage(driver));
    }

    /**
     * this method verifies that the third party tax calculation is "Complete"
     *
     * @return true if "Complete" and false if "incomplete"
     */
    public boolean verifyThirdPartyTaxCalculationStatus() {

        boolean isElementDisplayed = element.isElementDisplayed(thirdPartyTaxCalc);
        if (!isElementDisplayed) {
            VertexLogger.log("Third party Tax Calculation status failed");
        }
        return isElementDisplayed;
    }

    /**
     * this method clicks on "Credit/Re-bill" or "Create Adjustment" depending upon the parameter
     *
     * @param nameOfAction "Credit/Re-bill" or "Create Adjustment"
     */
    public void clickonCustomerInvoiceAction(String nameOfAction) {

        click.clickElement(wait.waitForElementDisplayed(invoiceRelatedActions));
        wait.waitForElementDisplayed(custInvDropdown);
        hover.hoverOverElement(custInvDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(custInvDropdown)).build().perform();
        if (nameOfAction.equals("CreditRebill")) {
            click.clickElement(creditRebill);
            wait.waitForElementDisplayed(creditAdjustment);
        } else if (nameOfAction.equalsIgnoreCase("change")) {
            click.clickElement(change);
            wait.waitForElementDisplayed(changeCustInv);
        } else {
            actions.moveToElement(wait.waitForElementDisplayed(adjustment)).build().perform();
            click.clickElement(adjustment);
            wait.waitForElementDisplayed(custInvAdj);
        }

    }

    /**
     * this method clicks on "Credit/Re-bill" or "Create Adjustment" depending upon the parameter
     *
     * @param nameOfAction "Credit/Re-bill" or "Create Adjustment"
     */
    public void clickonSupplierInvoiceAction(String nameOfAction) {

        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(ellipsis);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        wait.waitForElementDisplayed(suplInvDropdown);
        hover.hoverOverElement(suplInvDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(suplInvDropdown)).build().perform();
        if (nameOfAction.equalsIgnoreCase("change")) {
            wait.waitForElementDisplayed(change).click();
            wait.waitForElementDisplayed(changeSuplInv);
        } else {
            actions.moveToElement(wait.waitForElementDisplayed(adjustment)).build().perform();
            click.clickElement(adjustment);
            wait.waitForElementDisplayed(suplInvAdj);
        }

    }

    /**
     * this method clicks on fully credited invoice link
     */
    public void clickOnFullCreditedInvoiceLink() {
        click.clickElement(adjustedInvoiceLink);
        wait.waitForElementDisplayed(invAdjTitle);

    }

    /**
     * this method converts the Tax value to Negative one
     *
     * @param values values to enter
     * @return negative value
     */
    public String convertToNegativeValues(String values) {
        return "(" + values + ")";
    }

    /**
     * this method enter the Adjustment Reason as "Price Adjustment"
     */
    public void enterAdjReason() {
        WebElement ele = wait.waitForElementDisplayed(creditAdjReason);
        text.clickElementAndEnterText(ele, "Price Adjustment");
        ele.sendKeys(Keys.ENTER);
        wait.waitForElementDisplayed(enteredReason);
    }

    /**
     * this method clicks on Debit Adjustment
     *
     * @param isDebit true if debit
     */
    public void clickOnCreditOrDebit(boolean isDebit) {
        if (isDebit) {
            click.clickElementCarefully(adjustType);
        }
    }

    /**
     * this method clicks on Ok button after providing the adjustment reason
     *
     * @return WorkdayCustomerInvoicePage object
     */
    public WorkdayCustomerInvoicePage clickonOK() {

        click.clickElement(okButton);
        wait.waitForElementPresent(submit);
        return initializePageObject(WorkdayCustomerInvoicePage.class);
    }

    /**
     * this method clicks on newly created customer Invoice Adjustment
     */
    public void clickOnNewlyCreatedCustInvAdj() {

        click.clickElement(newCustInvLink);
        wait.waitForElementDisplayed(unpaidTab);
    }

    /**
     * this method gets the "invoice number" of newly created invoice
     *
     * @return invoice Number
     */
    public String getInvoiceNumber() {
        return text.getElementText(invoiceNo);
    }

    /**
     * this method clicks on business process link and wait till next page is loaded
     */
    public void clickOnBusinessProcessLink() {

        click.clickElementCarefully(firstBPLink);
        wait.waitForElementDisplayed(retrieveLogFilesButton);
    }

    /**
     * this method clicks on business process link and wait till next page is loaded
     */
    public void clickOnPostBusinessProcessLink() {

        click.clickElementCarefully(secondBPLink);
        wait.waitForElementDisplayed(retrieveLogFilesButton);
    }

    /**
     * It clicks on PreProcess BP link
     */
    public void clickOnPreProcessBPLink() {
        click.clickElementCarefully(preprocessLink);
        wait.waitForElementDisplayed(retrieveLogFilesButton);
    }

    /**
     * this method clicks on second business process link and wait till next page is loaded
     *
     * @author Vishwa
     */
    public void clickOnSecondBusinessProcessLink() {

        click.clickElementCarefully(secondBPLink);
    }

    /**
     * this method clicks on navigating back to invoice page
     *
     * @author Vishwa
     */
    public void navigateBackToInvoicePage() {

        wait.waitForElementDisplayed(parentEventLink);
        click.clickElementCarefully(parentEventLink);
        jsWaiter.sleep(3000);
        wait.waitForElementDisplayed(custInvAdjLink);
        click.clickElementCarefully(custInvAdjLink);
        wait.waitForElementPresent(taxTab);
    }

    /**
     * this method is navigating to View Customer Invoice page
     *
     * @author Vishwa
     */

    public void ClickOnCustomerInvoiceAdjustmentLink() {

        wait.waitForElementDisplayed(custInvAdjLink);
        click.clickElementCarefully(custInvAdjLink);
    }

    /**
     * this method clicks on navigating to Log files and wait till next page is loaded
     *
     * @author Vishwa
     */
    public void navigateToServerLogFile() {

        wait.waitForElementDisplayed(retrieveLogFilesButton);
        click.clickElementCarefully(retrieveLogFilesButton);
        wait.waitForElementDisplayed(confirmCheckbox);
        click.clickElementCarefully(confirmCheckbox);
        wait.waitForElementDisplayed(retrieveLogOkButton);
        click.clickElementCarefully(retrieveLogOkButton);
        wait.waitForElementDisplayed(consolidatedReport);
    }

    /**
     * this is just a helper method that waits for the serverlogs to be downloaded in newly created
     * folder "C:\\SeleniumTestDownloads"
     *
     * @param fileName filename that is downloading
     */
    public void waitForTheServerLogToDownload(String fileName) {

        System.out.println(DOWNLOAD_DIRECTORY_PATH);
        File dir = new File(DOWNLOAD_DIRECTORY_PATH);
        boolean bool = dir.mkdir();
        if (bool) {
            VertexLogger.log("Directory created successfully");
        } else {
            VertexLogger.log("Directory already exist");
        }
        int length = Objects.requireNonNull(dir.listFiles()).length;
        while (length == 0) {
            length = Objects.requireNonNull(dir.listFiles()).length;
        }
        String file = Objects.requireNonNull(dir.listFiles())[0].getName();
        while (!file.equals(fileName)) {
            file = Objects.requireNonNull(dir.listFiles())[0].getName();
        }
    }

    /**
     * this method click on the link that download the server lags and return the name of the logs
     *
     * @return name of the logs that gets downloaded
     */
    public String clickOnFirstServerLog() {
        String serverlog;
        wait.waitForElementDisplayed(server);
        serverlog = text.getElementText(server);
        click.clickElementCarefully(server);
        return serverlog;
    }

    /**
     * this method click on the link that download the server lags and return the name of the logs
     *
     * @return name of the logs that gets downloaded
     * @author Vishwa
     */
    public String clickOnSecondServerLog() {

        String serverlog;
        wait.waitForElementDisplayed(secondServer);
        serverlog = text.getElementText(secondServer);
        click.clickElementCarefully(secondServer);
        return serverlog;
    }

    /**
     * this method verify that particular string exists in the server logs+
     *
     * @param fileName       filename
     * @param expectedString Expected String
     * @return boolean true if string exists in the server logs
     */
    public boolean verifyTextIsPresentInServerLogs(String fileName, String expectedString) {
        boolean isVerified = false;
        VertexLogger.log("Complete File Path: " + DOWNLOAD_DIRECTORY_PATH + File.separator + fileName);
        File file = new File(DOWNLOAD_DIRECTORY_PATH + "//" + fileName);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if (line.contains(expectedString)) {
                    VertexLogger.log(expectedString + " : Found in Line no: " + lineNum);
                    isVerified = true;
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (!isVerified) {
            VertexLogger.log(expectedString + ": Is not present in the server logs");
        }
        assert scanner != null;
        scanner.close();
        return isVerified;
    }

    /**
     * this method verify that particular string exists in the server logs+
     *
     * @param fileName       filename
     * @param expectedString Expected String
     * @return boolean true if string exists in the server logs
     */
    public String getFullLineFromTextPresentInServerLogs(String fileName, String expectedString) {
        boolean isPresent = false;
        VertexLogger.log("Complete File Path: " + DOWNLOAD_DIRECTORY_PATH + File.separator + fileName);
        File file = new File(DOWNLOAD_DIRECTORY_PATH + "//" + fileName);
        Scanner scanner = null;
        String fullLine = null;

        try {
            scanner = new Scanner(file);
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if (line.contains(expectedString)) {
                    fullLine = line;
                    VertexLogger.log(expectedString + " : Found in Line no: " + lineNum);
                    isPresent = true;
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (!isPresent) {
            VertexLogger.log(expectedString + ": Is not present in the server logs");
        }
        assert scanner != null;
        scanner.close();
        return fullLine;
    }


    /**
     * This method loops through server logs and gets the time to measure time delay
     *
     * @param line1   Before Delay applied Line From the server logs
     * @param line2   After Delay applied Line from the server logs
     * @param isFirst true if Before time and false for after time
     * @return time in Second.MiliSeconds
     */
    public String getBeforeAfterTimeForDelay(String line1, String line2, boolean isFirst) {
        String[] arrLine1 = line1.split(" ");
        String time1 = arrLine1[1];
        String[] arrLine2 = line2.split(" ");
        String time2 = arrLine2[1];
        String timeMin1 = time1.split(":")[2];
        String timeMin2 = time2.split(":")[2];
        if (isFirst) {
            return timeMin1;
        } else {
            return timeMin2;
        }
    }

    /**
     * It verifies that delay is more than 100 Miliseconds
     *
     * @param beforeTime Before time in Second.MiliSeconds
     * @param afterTime  After time in Second.MiliSeconds
     * @return true if verified
     */
    public boolean verifyDelay(String beforeTime, String afterTime) {
        int diff;
        double overallDiff;
        boolean isVerified = false;
        String[] array1 = beforeTime.split("\\.");
        String sec1 = array1[0];
        String[] array2 = afterTime.split("\\.");
        String sec2 = array2[0];
        String milisec1 = array1[1];
        String milisec2 = array2[1];
        if (sec1.equals(sec2)) {
            diff = Integer.parseInt(milisec2) - Integer.parseInt(milisec1);
            if (diff >= 100) {
                isVerified = true;
                VertexLogger.log("MiliSeconds difference is : " + diff);
            } else {
                VertexLogger.log("TimeDelay Failed: MiliSeconds difference is : " + diff);
            }
        } else {
            overallDiff = Double.parseDouble(afterTime) - Double.parseDouble(beforeTime);
            if (overallDiff > 0.100) {
                isVerified = true;
                VertexLogger.log("Overall difference is : " + overallDiff);
            } else {
                VertexLogger.log("TimeDelay Failed: Overall difference is : " + overallDiff);
            }
        }
        return isVerified;
    }

    /**
     * this method verify that particular string exists in the server logs+
     */
    public void deleteFilesFromDirectory() {
        VertexFileUtils.deleteFilesInDirectory(new File(DOWNLOAD_DIRECTORY_PATH));
    }

    /**
     * this method extract vertex request from the server logs
     *
     * @param fileName    serverlogs
     * @param newFileName newfile name in which it will be extracted
     * @return boolean true if extracted successfully
     */
    public boolean createVertexRequestTextFile(String fileName, String newFileName) {
        VertexLogger.log("Complete File Path: " + DOWNLOAD_DIRECTORY_PATH + File.separator + fileName);
        int i = 0, j = 0, lineNo;
        File tmpDir = null;
        try {
            File fout = new File(newFileName);
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            LineNumberReader br1 = new LineNumberReader(new FileReader(DOWNLOAD_DIRECTORY_PATH + File.separator + fileName));
            String line1;
            while ((line1 = br1.readLine()) != null) {
                if (line1.startsWith("VERTEX REQUEST:")) {
                    i = br1.getLineNumber();
                } else if (line1.startsWith("</vtx:VertexEnvelope></env:Body></env:Envelope>")) {
                    j = br1.getLineNumber();
                }
            }
            for (lineNo = i; lineNo < j; lineNo++) {
                String line = Files.readAllLines(Paths.get(DOWNLOAD_DIRECTORY_PATH + "//" + fileName)).get(lineNo);
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            br1.close();
            tmpDir = new File(newFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File operation performed successfully");
        assert tmpDir != null;
        return tmpDir.exists();
    }

    /**
     * this method updates extracted vertex request so it has current date and new invoice number
     *
     * @param originalFile filename
     * @param updatedFile  Updated file name
     * @param invNo        invoice Number
     */
    public void updateExpectedVertexRequest(String originalFile, String updatedFile, String invNo) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Path path = Paths.get(originalFile);
        Path updatedPath = Paths.get(updatedFile);
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = Files.readString(path, charset);
            content = content.replaceAll("17632", invNo).replaceAll("2020-04-09", dateFormat.format(date));
            Files.write(updatedPath, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method click on the Customer Invoice link and dynamically wait till it finish loading
     */
    public void clickOnCustomerInvoiceLink() {
        click.clickElementCarefully(custInvLink);
        wait.waitForElementDisplayed(invStatus);
    }

    /**
     * this method click on the Customer Invoice link and dynamically wait till it finish loading
     */
    public void clickOnCustomerInvoiceNewAdjustmentLink() {
        click.clickElementCarefully(custInvAdjLink);
        wait.waitForElementDisplayed(invAdjTitle);
    }

    /**
     * this method click on the Customer Invoice link under status bar and dynamically wait till it finish loading
     *
     * @author Vishwa
     */
    public void clickOnNewCustomerInvoiceApprovedLink() {
        click.clickElementCarefully(custInvAdjLink);
        wait.waitForElementDisplayed(invStatus);
    }

    /**
     * this method verifies Custom object status is "SUCCESS" and Posted flag is "Yes"
     *
     * @return boolean 'true' if successful verification else 'false' with logs
     */
    public boolean verifycustomObjectStatus() {
        click.clickElement(wait.waitForElementDisplayed(invoiceRelatedActions));
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.clickElementCarefully(additionalData);
        try {
            wait.waitForTextInElement(title, "View All Additional Data");
        } catch (StaleElementReferenceException e) {
            wait.waitForElementDisplayed(custInvoice);
        }
        String callStatus = text.getElementText(vertexCallStatus);
        String postedFlag = text.getElementText(vertexPostedFlag);
        VertexLogger.log("VertexCallType is: " + text.getElementText(vertexCallType));
        if (callStatus.equals("SUCCESS") && postedFlag.equals("Yes")) {
            return true;
        } else {
            VertexLogger.log("VertexCallStatus is: " + callStatus);
            VertexLogger.log("VertexPostedFlag is:" + postedFlag);
            return false;
        }
    }

    /**
     * this method verify Tax Calculation error message
     *
     * @param expectedErrorMessage Expected Error Message
     * @return boolean 'true' if successful verification else 'false'
     */
    public boolean verifyTaxCalcErrorMessage(String expectedErrorMessage) {
        scroll.scrollElementIntoView(taxCalcError);
        return text.getElementText(taxCalcError).equalsIgnoreCase(expectedErrorMessage);
    }

    /**
     * this method gets the line level taxes by maximizing tax windows
     *
     * @return String[] of the tax line level items
     */
    public String[] getLineLevelTaxes() {
        List<String> lineLevelTaxes = new ArrayList<>();
        click.clickElementCarefully(maximizeScreen);
        waitForPageLoad();
        List<WebElement> elements = wait.waitForAllElementsPresent(lineTaxes);
        for (WebElement w : elements) {
            lineLevelTaxes.add(w.getText());
        }

        String[] arr = lineLevelTaxes.toArray(new String[0]);

        for (String x : arr)
            System.out.print(x + " ");

        return arr;
    }

    /**
     * this method verifies that roll up tax is present in the transaction tax window
     *
     * @return boolean 'true' if successful verification else 'false'
     */
    public boolean isRollUpTaxAvailable() {
        click.clickElementCarefully(maximizeScreen);
        waitForPageLoad();
        return element.isElementPresent(rollUpTax);
    }

    /**
     * this method verify line level tax array with expected array of line level taxes
     *
     * @param expectedLineTaxes String array of expected line level taxes
     * @param actualLineTaxes   String array of actual line level taxes
     * @return boolean 'true' if successful verification else 'false'
     */
    public boolean compareLineLevelTaxes(String[] expectedLineTaxes, String[] actualLineTaxes) {
        boolean stop = false;
        boolean comparison = false;
        if (expectedLineTaxes.length == actualLineTaxes.length) {

            for (int i = 0; i < expectedLineTaxes.length && !stop; i++) {
                for (int j = 0; j < actualLineTaxes.length; j++) {
                    if (expectedLineTaxes[i].equals(actualLineTaxes[j])) {
                        comparison = true;
                        VertexLogger.log("Expected Line Tax " + expectedLineTaxes[i] + " :Matches with actual ");
                        break;
                    } else {
                        if (j == actualLineTaxes.length - 1) {
                            comparison = false;
                            stop = true;
                            VertexLogger.log("Line level Taxes doesn't Match with expected");
                        }
                    }
                }
            }
        } else {
            VertexLogger.log("Total number of Line level taxes are different in actual and expected");
        }
        return comparison;
    }

    /**
     * this method clicks on newly created supplier Invoice
     *
     * @return WorkdaySupplierInvoiceReviewPage object
     */
    public WorkdaySupplierInvoiceReviewPage clickonNewlyCreatedSupplierInvoice() {
        click.javascriptClick(newSuplInvLink);
        wait.waitForElementPresent(viewSuplInv);
        return (initializePageObject(WorkdaySupplierInvoiceReviewPage.class));
    }


    /**
     * this method is called when retrieving log files and decides whether to navigate to the process tab when
     * available or the business process tab when it is not
     */
    public void switchToProcessTab() {
        if (element.isElementDisplayed(processTab)) {
            System.out.println("Made it to if statement (process)");
            switchToTab("Process");
        } else {
            System.out.println("Made it to else statement (business process)");
            switchToTab("Business Process");
        }
    }
}
