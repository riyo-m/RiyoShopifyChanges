package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.APInvoiceSource;
import com.vertex.quality.connectors.taxlink.ui.enums.LegalEntity;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the pages in AP Assume Tax Sources tab in TaxLink UI
 *
 * @author Shilpi.Verma
 */
public class TaxLinkAPAssumeTaxSrcPage extends TaxLinkBasePage {

    private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();
    private WebDriverWait wait = new WebDriverWait(driver, 10);
    private Actions actions = new Actions(driver);

    private LinkedList<String> apInvSrcList = new LinkedList<>();

    String selectedVal;
    int count;

    public TaxLinkAPAssumeTaxSrcPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//select[@class = 'form-control dropDownSelect'][@data-cy='apInvoiceSource']")
    private WebElement apInvSrc;

    @FindBy(xpath = "//select[@class = 'form-control dropDownSelect'][@data-cy='businessUnitId']")
    private WebElement businessUnit;

    @FindBy(xpath = "//label[contains(text(), 'Start Date')]/following-sibling::div/div/input")
    private WebElement startDate;

    @FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
    private WebElement endDate;

    @FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'invoiceSource']")
    private List<WebElement> summaryTableAPAssumeTaxInvSrc;

    @FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
    private List<WebElement> endDateCalendar;

    @FindBy(xpath = "//div[@class = 'notification-container']")
    WebElement errorDuplicateData;

    @FindBy(xpath = "//div[@class = 'notification__inner']/p[contains(text(), 'VTX')]/following-sibling::p")
    private WebElement errorMsg;

    @FindBy(xpath = "//input[@name='enableFlag']")
    protected WebElement enabledFlag;

    @FindBy(xpath = "//button[@class='table-flyout__btn threeDot']")
    protected WebElement three_dots;

    private By edit = By.xpath(".//following-sibling::div[4]");
    private By clickEdit = By.xpath("//div/div/div/div/div/button[contains(text(), 'Edit')]");

    private By business_unit = By.xpath(".//following-sibling::div");
    private By start_date = By.xpath(".//following-sibling::div[2]");
    private By end_date = By.xpath(".//following-sibling::div[3]");
    private By enabled_status = By.xpath(".//following-sibling::div[4]");

    /**
     * Method to fetch all data from Summary Table
     * and filter out from the dropdown list of APAssumeTaxSrc in Add page
     *
     * @return
     */
    public boolean filterData() {
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        count = Integer.parseInt(totalPageCountSummaryTable.getText());
        for (int i = 1; i <= count; i++) {
            summaryTableAPAssumeTaxInvSrc.forEach(x -> apInvSrcList.add(x.getText()));
            VertexLogger.log(String.valueOf(apInvSrcList));

            new StringBuffer().append(apInvSrcList);

            int currCount = Integer.parseInt(currentPageCount.getText());
            if (currCount < count) {
                actions
                        .sendKeys(Keys.PAGE_DOWN)
                        .perform();

                click.clickElement(nextArrow);
            } else {
                break;
            }
        }

        click.clickElement(addButton);

        wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
        boolean flag = addViewEditPageTitle
                .getText()
                .contains("Add");

        click.clickElement(apInvSrc);

        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(apInvSrc, By.tagName("option")));
        List<WebElement> options = dropdown.getDropdownOptions(apInvSrc);

        LinkedList<String> ll = new LinkedList<>();

        for (int i = 0; i < options.size(); i++) {
            String s1 = options
                    .get(i)
                    .getAttribute("value");

            String s2 = options
                    .get(i)
                    .getAttribute("name");

            for (String s : apInvSrcList) {
                if (s1.contains(s) || s2.contains(s)) {
                    ll.add(s1);
                    ll.add(s2);

                    if (i == options.size()) {
                        break;
                    }
                }
            }
            if (!s1.isEmpty() && !s2.isEmpty() && !s1.isBlank() && !s2.isBlank() && (!ll.contains(s1) || !ll.contains(
                    s2))) {
                click.clickElement(options.get(i));

                selectedVal = options
                        .get(i)
                        .getText();

                ll.clear();

                break;
            }
        }

        return flag;
    }

    /**
     * Method to add new record in AP Assume Tax Invoice Source
     *
     * @return
     */
    public boolean addAPAssumeTaxSrc() {
        boolean flag = false;
		boolean selectedVal_flag = false;
        navigateToAPAssumeTaxSrc();
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        boolean filterData_flag = filterData();

        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(businessUnit, By.tagName("option")));
        dropdown.selectDropdownByIndex(businessUnit, 1);
        String bu = dropdown.getDropdownSelectedOption(businessUnit).getText();

        boolean startDate_flag = startDate
                .getAttribute("value")
                .contains(LegalEntity.FIELDS.defaultStartDate);

        click.clickElement(endDate);
        endDateCalendar
                .stream()
                .filter(date -> date
                        .getText()
                        .equals(utils.getCurrentDate()))
                .findFirst()
                .get()
                .click();

        expWait.until(ExpectedConditions.elementToBeClickable(saveButton));
        click.clickElement(saveButton);

        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
        click.clickElement(externalFilter);
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        jsWaiter.sleep(5000);
        for (int i = 1; i <= count; i++) {
            Optional<WebElement> selectedVal_data = summaryTableAPAssumeTaxInvSrc
                    .stream()
                    .filter(ele -> ele
                            .getText()
                            .contains(selectedVal) && ele.findElement(By.xpath(".//following-sibling::div")).getText().contains(bu)).findFirst();

            if (selectedVal_data.isPresent()) {
                selectedVal_flag = true;
                break;
            } else {
                click.clickElement(nextArrowOnSummaryTable);
            }
        }

        if (filterData_flag && startDate_flag && selectedVal_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Method to get first data from Summary Table
     *
     * @return
     */
    public Optional<WebElement> getFirstData() {
        Optional<WebElement> data = summaryTableAPAssumeTaxInvSrc
                .stream()
                .findFirst();

        return data;
    }

    /**
     * Verify edit option of AP Assume Tax Source
     *
     * @return
     */
    public boolean editAPAssumeTaxSrc() {
        boolean flag = false;

        navigateToAPAssumeTaxSrc();
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        Optional<WebElement> data = getFirstData();

        String originalData = data
                .get()
                .getText();

        WebElement editButton = data
                .get()
                .findElement(edit);

        editButton
                .findElement(clickEdit)
                .click();

        wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
        boolean headerAdd_EditPage_flag = addViewEditPageTitle
                .getText()
                .contains("Edit");

        boolean apInvSrc_flag = !element.isElementEnabled(apInvSrc);
        boolean bu_flag = !element.isElementEnabled(businessUnit);
        boolean startDate_flag = !element.isElementEnabled(startDate);
        boolean endDate_flag = element.isElementEnabled(endDate);
        boolean enabledCheckbox_flag = element.isElementEnabled(enabledFlag);

        click.clickElement(enabledFlag);

        boolean clickEnabledCheckbox_flag = checkbox.isCheckboxChecked(enabledFlag) || !checkbox.isCheckboxChecked(
                enabledFlag);
        boolean saveButton_flag = element.isElementEnabled(saveButton);

        click.clickElement(saveButton);

        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
        dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        Optional<WebElement> data1 = getFirstData();
        boolean enabledStatus_flag = false;
        if (data1
                .get()
                .getText()
                .equals(originalData)) {
            String enabledStatus = data1
                    .get()
                    .findElement(enabled_status)
                    .getText();

            enabledStatus_flag = enabledStatus.equals("N") || enabledStatus.equals("Y");
        }

        if (headerAdd_EditPage_flag && apInvSrc_flag && bu_flag && startDate_flag && endDate_flag && enabledCheckbox_flag &&
                saveButton_flag && clickEnabledCheckbox_flag && enabledStatus_flag) {
            flag = true;
        }
        return flag;
    }

    /**
     * Method to verify View option
     *
     * @return
     */
    public boolean viewAPAssumeTaxSrc() {
        boolean flag = false;

        navigateToAPAssumeTaxSrc();
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        click.clickElement(three_dots);
        click.clickElement(viewButton);

        wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
        boolean headerAdd_EditPage_flag = addViewEditPageTitle
                .getText()
                .contains("View");

        boolean apInvSrc_flag = !element.isElementEnabled(apInvSrc);
        boolean bu_flag = !element.isElementEnabled(businessUnit);
        boolean startDate_flag = !element.isElementEnabled(startDate);
        boolean endDate_flag = !element.isElementEnabled(endDate);
        boolean enabledCheckbox_flag = !element.isElementEnabled(enabledFlag);

        click.clickElement(cancelButton);

        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        if (headerAdd_EditPage_flag && apInvSrc_flag && bu_flag && startDate_flag && endDate_flag && enabledCheckbox_flag) {
            flag = true;
        }
        return flag;
    }

    /**
     * Method to check error message
     * when end date is lesser than start date
     *
     * @return
     */
    public boolean negative_end_dateAssumeTaxSrc() {
        boolean flag = false;
        boolean error_flag = false;

        navigateToAPAssumeTaxSrc();
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        boolean filterData_flag = filterData();

        utils.clearTextField(startDate);
        text.enterText(startDate, utils.getFormattedDate());

        click.clickElement(endDate);
        text.enterText(endDate, utils.getYesterdayDate());
        click.clickElement(startDate);


        click.clickElement(saveButton);

        wait.until(ExpectedConditions.visibilityOf(errorDuplicateData));
        List<WebElement> errorString = errorDuplicateData.findElements(By.xpath(".//*"));
        if (errorString.size() > 0) {
            error_flag = errorMsg
                    .getText()
                    .equals("End Date can't be before Start Date");
        }

        if (filterData_flag && error_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Method to verify Export To CSV
     *
     * @return
     * @throws Exception
     */
    public boolean exportToCSVAPAssumeTaxSrc() throws Exception {
        boolean flag = false;
        String formattedStartDate_CSV;
        String formattedEndDate_CSV;

        navigateToAPAssumeTaxSrc();
        wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

        dropdown.selectDropdownByDisplayName(externalFilter, "Both");
        if (!checkNoRecordsPresent()) {
            Optional<WebElement> ele = summaryTableAPAssumeTaxInvSrc
                    .stream()
                    .findFirst();

            String apInvSrc = ele
                    .get()
                    .getText();

            String bu = ele.get().findElement(business_unit).getText();

            String startDate = ele
                    .get()
                    .findElement(start_date)
                    .getText();

            String endDate = ele
                    .get()
                    .findElement(end_date)
                    .getText();

            String enabled = ele
                    .get()
                    .findElement(enabled_status)
                    .getText();

            String fileName = "APAssumeTax_Extract.csv";
            String fileDownloadPath = String.valueOf(getFileDownloadPath());
            File file = new File(fileDownloadPath + File.separator + fileName);
            VertexLogger.log(String.valueOf(file));

            setFluentWait(file);

            List<CSVRecord> records = parseCSVRecord(file);
            boolean header_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

            if (!startDate.equals("")) {
                formattedStartDate_CSV = utils.getSummaryFormattedDate(startDate);
                VertexLogger.log("Formatted Start Date : " + formattedStartDate_CSV);
            } else {
                formattedStartDate_CSV = startDate;
            }

            if (!endDate.equals("")) {
                formattedEndDate_CSV = utils.getSummaryFormattedDate(endDate);
                VertexLogger.log("Formatted End Date : " + formattedEndDate_CSV);
            } else {
                formattedEndDate_CSV = endDate;
            }
            Optional<CSVRecord> data = records
                    .stream()
                    .filter(rec -> rec
                            .get(0)
                            .contains(apInvSrc) && rec.get(1).contains(bu) && rec
                            .get(2)
                            .contains(formattedStartDate_CSV) && rec
                            .get(3)
                            .contains(formattedEndDate_CSV) && rec
                            .get(4)
                            .contains(enabled))
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
