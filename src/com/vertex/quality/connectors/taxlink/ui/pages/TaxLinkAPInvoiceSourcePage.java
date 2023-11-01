package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.APInvoiceSource;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the pages in AP Invoice Source tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPInvoiceSourcePage extends TaxLinkBasePage {
    private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();
    private String apInvSrcCode;
    private boolean enStatus_flag;
    private List<String> dataEntered;
    Actions action = new Actions(driver);

    public TaxLinkAPInvoiceSourcePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
	
    @FindBy(xpath = "//label[contains(text(), 'AP Invoice Source Code')]/following-sibling::input")
    private WebElement apInvoiceSourceCode;

    @FindBy(xpath = "//label[contains(text(), 'AP Invoice Source Meaning')]/following-sibling::input")
    private WebElement apInvoiceSourceMeaning;

    @FindBy(xpath = "//label[contains(text(), 'Start Date')]/following-sibling::div/div/input")
    private WebElement startDate;

    @FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
    private WebElement endDate;

    @FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
    private List<WebElement> endDateCalendar;

    @FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
    private WebElement endDateSelectedValue;

    @FindBy(xpath = "//input[@name='enableFlag']")
    private WebElement enabledCheckBox;

    @FindBy(xpath = "//div[@class = 'notification-container']")
    WebElement errorDuplicateData;

    @FindBy(tagName = "h3")
    WebElement headerEditAPInvoiceSourcePage;

    @FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCode']/following-sibling::div[4]/div/div/div/button")
    WebElement three_dots;

    @FindBy(tagName = "h1")
    private WebElement headerImportLegalEntity;

    @FindBy(xpath = "(//div/div[1]/div/div[1]/div[2]/div/div/div[1]/div[2]/div/div/span)[last()]")
    private WebElement importSelectAllCheckBox;

    @FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
    private List<WebElement> importListCheckBox;

    @FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[2]")
    private WebElement apInvoiceSourceText;

    @FindBy(xpath = "(//h4[@class='secondModalHeading'])[last()-1]")
    private WebElement popUpImport;

    @FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCode']")
    private List<WebElement> summaryTableAPInvoiceSource;

    @FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])[last()-1]")
    private WebElement totalPageCount;

    @FindBy(xpath = "//div[@class = 'notification__inner']/p/following-sibling::p")
    private WebElement errorMsg;

	@FindBy(xpath = "(//button[contains(text(), 'View')])[last()]")
	private WebElement viewButtonAPInvSrc;

    /**
     * Method for entering all the data in Add AP Invoice Source page
     *
     * @return boolean
     */
    public boolean enterAPInvoiceSourceData() {
        boolean flag;

        wait.waitForElementDisplayed(addButton);
        click.clickElement(addButton);

        boolean flag1 = text
                .getElementText(addViewEditPageTitle)
                .contains(APInvoiceSource.FIELDS.headerAddAPInvoiceSource);

        apInvSrcCode = utils.randomNumber("5");
        text.enterText(apInvoiceSourceCode, apInvSrcCode);

        String apInvSrcMean = utils.randomText();
        text.enterText(apInvoiceSourceMeaning, apInvSrcMean);

        boolean flag2 = checkbox.isCheckboxChecked(enabledCheckBox);

        click.clickElement(saveButton);

        dataEntered = Arrays.asList(apInvSrcCode, apInvSrcMean);
        VertexLogger.log("Data Entered is: " + dataEntered);

        if (flag1 && flag2) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    /**
     * Method to search data in summary table of AP Invoice Source
     * based on data entered for AP Invoice Source Code
     *
     * @return Optional<WebElement>
     */
    public Optional<WebElement> dataPresent(String text) {
        Optional<WebElement> dataFound = summaryTableAPInvoiceSource
                .stream()
                .filter(col -> col
                        .getText()
                        .equals(text))
                .findFirst();

        return dataFound;
    }

    /**
     * Method Overloading ----
     * Method to search data in summary table of AP Invoice Source
     * based on Access Type as 'USER' and
     * End Date is not empty
     *
     * @return Optional<WebElement>
     */
    public Optional<WebElement> dataPresent() {
        Optional<WebElement> dataFound = summaryTableAPInvoiceSource
                .stream()
                .filter(ele -> (ele
                        .findElement(By.xpath(".//following-sibling::div[2]"))
                        .getText()
                        .equals("USER")) && !(ele
                        .findElement(By.xpath(".//following-sibling::div[4]"))
                        .getText()
                        .isEmpty()))
                .findFirst();

        return dataFound;
    }

    /**
     * Verify add new record in AP Invoice Source
     *
     * @return boolean
     */
    public boolean addAPInvoiceSource() {
        boolean flag = false;

        navigateToAPInvoiceSource();

        String summaryPageHeader = wait
                .waitForElementDisplayed(summaryPageTitle)
                .getText();
        boolean headerText_flag = summaryPageHeader.equals(APInvoiceSource.FIELDS.headerSummaryPage);

        boolean enterData_flag = enterAPInvoiceSourceData();

        boolean headerDisplay_flag = wait
                .waitForElementDisplayed(summaryPageTitle)
                .isDisplayed();

        checkPageNavigation();

        int count = Integer.parseInt(totalPageCount.getText());
        boolean newRec_flag = false;
        for (int i = 1; i <= count; i++) {
            Optional<WebElement> data = dataPresent(apInvSrcCode);

            if (data.isPresent()) {
                WebElement accessType = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[2]"));

                if (accessType
                        .getText()
                        .equals("USER")) {
                    VertexLogger.log("New record added in Summary Table");
                    newRec_flag = true;
                } else {
                    VertexLogger.log("New record not added in Summary Table");
                }

                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        if (headerText_flag && enterData_flag && headerDisplay_flag && newRec_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Verify Edit option of AP Invoice Source
     *
     * @return boolean
     */
    public boolean editAPInvoiceSource_userCreated() {
        boolean flag = false;

        navigateToAPInvoiceSource();
        wait.waitForElementDisplayed(summaryPageTitle);
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");
        wait.waitForElementDisplayed(summaryPageTitle);

        int count = Integer.parseInt(totalPageCount.getText());
        for (int i = 1; i <= count; i++) {
            Optional<WebElement> data = dataPresent();

            if (data.isPresent()) {
                WebElement edit = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[4]"));

                edit
                        .findElement(By.xpath(".//div/div/div/div/div/button[contains(text(), 'Edit')]"))
                        .click();

                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        wait.waitForElementDisplayed(headerEditAPInvoiceSourcePage);
        boolean header_flag = headerEditAPInvoiceSourcePage
                .getText()
                .contains("Edit");

        boolean apInvSrcCode_flag = !element.isElementEnabled(apInvoiceSourceCode);

        boolean apInvSrcMean_flag = element.isElementEnabled(apInvoiceSourceMeaning);
        boolean svEnabled_flag;
        String invSrcMeaningText = null;
        if (apInvSrcMean_flag) {
            utils.clearTextField(apInvoiceSourceMeaning);
            invSrcMeaningText = utils.randomAlphaNumericText();
            text.enterText(apInvoiceSourceMeaning, invSrcMeaningText);
            element.isElementEnabled(saveButton);
        }

        boolean enChckBox_flag = element.isElementEnabled(enabledCheckBox);

        enabledCheckBox.click();

        boolean clickChckBox_flag = checkbox.isCheckboxChecked(enabledCheckBox) || !checkbox.isCheckboxChecked(
                enabledCheckBox);

        svEnabled_flag = element.isElementEnabled(saveButton);

        click.clickElement(saveButton);

        wait.waitForElementDisplayed(summaryPageTitle);
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        boolean invSrcMeaning_flag = false;
        for (int j = 1; j <= count; j++) {
            Optional<WebElement> data = dataPresent();

            if (data.isPresent()) {
                String invSrcMeaning = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[@col-id = 'lookupCodeDesc']"))
                        .getText();
                invSrcMeaning_flag = invSrcMeaning.equals(invSrcMeaningText);

                String enabledStatus = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[@col-id = 'enabledFlag']"))
                        .getText();
                enStatus_flag = enabledStatus.equals("N") || enabledStatus.equals("Y");

                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        if (header_flag && apInvSrcCode_flag && apInvSrcMean_flag && invSrcMeaning_flag && enChckBox_flag && clickChckBox_flag &&
                svEnabled_flag && enStatus_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Verify view option of AP Invoice Source
     *
     * @return boolean
     */
    public boolean viewAPInvoiceSource() {
		boolean flag = false;

		navigateToAPInvoiceSource();
		wait.waitForElementDisplayed(externalFilter);

		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		click.clickElement(three_dots);
		click.clickElement(viewButtonAPInvSrc);

		wait.waitForElementDisplayed(headerEditAPInvoiceSourcePage);

		boolean apSrcCode_flag = !element.isElementEnabled(apInvoiceSourceCode);
		boolean apInvSrcMean_flag = !element.isElementEnabled(apInvoiceSourceMeaning);
		boolean apEnChk_flag = !element.isElementEnabled(enabledCheckBox);
		boolean saveBtn_flag = !element.isElementEnabled(saveButton);
        boolean cancelBtn_flag = element.isElementEnabled(cancelButton);

        click.clickElement(cancelButton);

        if (apSrcCode_flag && apInvSrcMean_flag && apEnChk_flag && saveBtn_flag && cancelBtn_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Method to iterate over list of checkboxes in Import pop up
     *
     * @param xPath
     * @param attribute
     * @return boolean
     */
    public boolean checkBoxIterate(String xPath, String attribute) {
        boolean flag = false;

        Iterator<WebElement> itr = importListCheckBox.listIterator();
        while (itr.hasNext()) {
            WebElement ele = itr.next();
            flag = ele
                    .findElements(By.xpath(xPath))
                    .stream()
                    .anyMatch(attr -> attr
                            .getAttribute("class")
                            .contains(attribute));
        }
        return flag;
    }

    /**
     * Verify import option of AP Invoice Source
     *
     * @return
     */
    public boolean importAPInvoiceSource() throws Exception {
        boolean flag = false;
        boolean checkLoop_checked_flag = false;
        boolean chkBox_iterateChecked_flag = false;
        boolean checkLoop_unchecked_flag = false;
        boolean chkBox_iterateUnchecked_flag = false;
        boolean data_flag = false;

        navigateToAPInvoiceSource();
        wait.waitForElementDisplayed(summaryPageTitle);

        click.clickElement(importButtonOnSummaryPage);

        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

        click.clickElement(importSelectAllCheckBox);
        if (importSelectAllCheckBox
                .getAttribute("class")
                .contains("checked")) {
            checkLoop_checked_flag = true;

            chkBox_iterateChecked_flag = checkBoxIterate(".//span[1]", "ag-icon ag-icon-checkbox-checked");
        }

        click.clickElement(importSelectAllCheckBox);
        boolean chkBox_check_flag = !checkbox.isCheckboxChecked(importSelectAllCheckBox);

        if (importSelectAllCheckBox
                .getAttribute("class")
                .contains("unchecked")) {
            checkLoop_unchecked_flag = true;

            chkBox_iterateUnchecked_flag = checkBoxIterate(".//span[2]", "ag-icon ag-icon-checkbox-unchecked");
        }

        String selectedVal = apInvoiceSourceText.getText();
        VertexLogger.log(selectedVal);

        importListCheckBox
                .stream()
                .findFirst()
                .get()
                .click();

        click.clickElement(selectImportButton);

        wait.until(
                ExpectedConditions.textToBePresentInElement(popUpImport, "Are you sure you want to save these records?"));

        click.clickElement(savePopUpImportButton);
        wait.until(ExpectedConditions.invisibilityOf(savePopUpImportButton));
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        action
                .sendKeys(Keys.PAGE_DOWN)
                .perform();

        int count = Integer.parseInt(totalPageCount.getText());
        for (int i = 1; i <= count; i++) {
            Optional<WebElement> data = dataPresent(selectedVal);

            if (data.isPresent()) {
                VertexLogger.log(data
                        .get()
                        .getText());

                data_flag = true;
                break;
            } else {
                action
                        .sendKeys(Keys.PAGE_DOWN)
                        .perform();

                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        if (checkLoop_checked_flag && chkBox_iterateChecked_flag && chkBox_check_flag && checkLoop_unchecked_flag &&
                chkBox_iterateUnchecked_flag && data_flag) {
            flag = true;
        }

        if (flag) {
            writeToFile(selectedVal);
        }

        return flag;
    }

    /**
     * Method to edit AP Invoice Source Meaning field
     * for imported data in AP Invoice Source
     *
     * @return
     */
    public boolean editAPInvoiceSource_importedData() throws Exception {
        boolean final_flag = false;
        String importData = String.valueOf(getFileReadPath().get(0));

        navigateToAPInvoiceSource();
        wait.waitForElementDisplayed(summaryPageTitle);
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");
        wait.waitForElementDisplayed(summaryPageTitle);

        int count = Integer.parseInt(totalPageCount.getText());
        for (int i = 1; i <= count; i++) {
            Optional<WebElement> data = dataPresent(importData);

            if (data.isPresent()) {
                WebElement edit = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[4]"));

                edit
                        .findElement(By.xpath(".//div/div/div/div/div/button[contains(text(), 'Edit')]"))
                        .click();

                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        wait.waitForElementDisplayed(headerEditAPInvoiceSourcePage);
        boolean header_flag = headerEditAPInvoiceSourcePage
                .getText()
                .contains("Edit");

        boolean apInvSrcCode_flag = !element.isElementEnabled(apInvoiceSourceCode);

        boolean apInvSrcMean_flag = element.isElementEnabled(apInvoiceSourceMeaning);
        boolean svEnabled_flag;
        String invSrcMeaningText = null;
        if (apInvSrcMean_flag) {
            utils.clearTextField(apInvoiceSourceMeaning);
            invSrcMeaningText = utils.randomAlphaNumericText();
            text.enterText(apInvoiceSourceMeaning, invSrcMeaningText);
            element.isElementEnabled(saveButton);
        }

        boolean enChckBox_flag = element.isElementEnabled(enabledCheckBox);

        enabledCheckBox.click();

        boolean clickChckBox_flag = checkbox.isCheckboxChecked(enabledCheckBox) || !checkbox.isCheckboxChecked(
                enabledCheckBox);

        svEnabled_flag = element.isElementEnabled(saveButton);

        click.clickElement(saveButton);

        wait.waitForElementDisplayed(summaryPageTitle);
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        boolean invSrcMeaning_flag = false;
        for (int j = 1; j <= count; j++) {
            Optional<WebElement> data = dataPresent();

            if (data.isPresent()) {
                String invSrcMeaning = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[@col-id = 'lookupCodeDesc']"))
                        .getText();
                invSrcMeaning_flag = invSrcMeaning.equals(invSrcMeaningText);

                String enabledStatus = data
                        .get()
                        .findElement(By.xpath(".//following-sibling::div[@col-id = 'enabledFlag']"))
                        .getText();
                enStatus_flag = enabledStatus.equals("N") || enabledStatus.equals("Y");

                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        if (header_flag && apInvSrcCode_flag && apInvSrcMean_flag && invSrcMeaning_flag && enChckBox_flag && clickChckBox_flag &&
                svEnabled_flag && enStatus_flag) {
            final_flag = true;
        }

        return final_flag;
    }

    /**
     * Method to check error msg when
     * duplicate APInvSrc code is added
     *
     * @return
     */
    public boolean negative_addDuplicateAPInvSrc() {
        boolean flag = false;
        boolean error_flag = false;

        navigateToAPInvoiceSource();
        wait.waitForElementDisplayed(summaryPageTitle);

        boolean enterData_flag = enterAPInvoiceSourceData();
        wait.waitForElementDisplayed(summaryPageTitle);

        click.clickElement(addButton);
        wait.waitForElementDisplayed(addViewEditPageTitle);

        text.enterText(apInvoiceSourceCode, dataEntered.get(0));
        text.enterText(apInvoiceSourceMeaning, dataEntered.get(1));

        click.clickElement(saveButton);

        new WebDriverWait(driver, 10).until(
                ExpectedConditions.presenceOfNestedElementLocatedBy(errorDuplicateData, By.xpath(".//*")));
        List<WebElement> errorString = errorDuplicateData.findElements(By.xpath(".//*"));
        if (errorString.size() > 0) {
            error_flag = errorMsg
                    .getText()
                    .equals("Records already exists");
        }

        if (enterData_flag && error_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Verify Export to CSV
     *
     * @return boolean
     * @throws Exception
     */
    public boolean exportToCSVAPInvoiceSrc() throws Exception {
        boolean flag = false;

        navigateToAPInvoiceSource();

        wait.waitForElementDisplayed(summaryPageTitle);
        if (!checkNoRecordsPresent()) {
            String idText = summaryTableAPInvoiceSource
                    .stream()
                    .findFirst()
                    .get()
                    .getText();

            String fileName = "AP_Invoice_Source_Extract.csv";
            String fileDownloadPath = String.valueOf(getFileDownloadPath());
            File file = new File(fileDownloadPath + File.separator + fileName);
            VertexLogger.log(String.valueOf(file));

            setFluentWait(file);

            List<CSVRecord> records = parseCSVRecord(file);
            boolean header_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

            Optional<CSVRecord> data = records
                    .stream()
                    .filter(rec -> rec
                            .get(0)
                            .contains("VTX_AP_INVOICE_SOURCES") && rec
                            .get(1)
                            .contains(idText))
                    .findFirst();

            boolean data_flag = false;
            if (data.isPresent()) {
                data_flag = true;
                VertexLogger.log("CSV Record Number: " + data
                        .get()
                        .getRecordNumber());
            }

            if (file.delete()) {
                VertexLogger.log("File deleted successfully");
            } else {
                VertexLogger.log("Failed to delete the file");
            }

            if (header_flag && data_flag) {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }
}
