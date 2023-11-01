package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.CleanUpInstance;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test Clone Instance functionality of TaxLink
 *
 * @author mgaikwad
 */

public class TaxLinkCleanUpInstancePage extends TaxLinkBasePage {
    public TaxLinkCleanUpInstancePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(), 'No Rows To Show')]")
    private WebElement emptySummaryTable;

    @FindBy(xpath = "//div[@class='childSummaryHeader']/div/p")
    protected WebElement summaryPageChildTitle;

    @FindBy(xpath = "//div[@class='content__inner']/div/p")
    protected WebElement pageFirstChildTitle;

    @FindBy(xpath = "//div[@class='content__inner']/div/p/p")
    protected WebElement pageSecondChildTitle;
    @FindBy(xpath = "//div[@class= 'Select-menu-outer']/div[@id='react-select-2--list']/div")
    private List<WebElement> instanceList;

    @FindBy(xpath = "//span[@class='Select-arrow']")
    private WebElement instanceDropdownArrow;

    @FindBy(xpath = "//button[contains(text(), 'Cleanup Instance')]")
    private WebElement cleanUpInstanceButton;

    @FindBy(xpath = "//div[@col-id='fusionInstanceName'][contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')]")
    private List<WebElement> instanceNamesCleanUp;

    @FindBy(xpath = "//div[@col-id='instanceUsage'][contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')]")
    private List<WebElement> instanceUsageCleanUp;

    @FindBy(xpath = "//label[contains(text(), 'Select Instance')]/following-sibling::select")
    private WebElement selectInstDrpDwn;

    @FindBy(xpath = "//label[contains(text(), 'Select Instance')]/following-sibling::select/option")
    private WebElement selectInstDrpDwnVals;

    @FindBy(xpath = "//button[contains(text(),'CLEANUP INSTANCE')]")
    private WebElement confirmCleanUpButton;

    @FindBy(xpath = "//div[contains(@class,'Modal modalShow')]")
    private WebElement confirmPopUp;

    @FindBy(xpath = "//div[contains(@class,'Modal modalShow')]/div/p")
    private WebElement popUpContents;

    @FindBy(xpath = "//button[contains(text(), 'Yes')]")
    private WebElement yesButton;

    @FindBy(xpath = "//button[contains(text(), 'No')]")
    private WebElement noButton;

    @FindBy(xpath = "//span[contains(@ref,'lbRecordCount')]")
    private WebElement totalRecordCount;


	@FindBy(xpath = "//div[@class='Select-value']/span")
	private WebElement presentInstance;
    private By listInstances = By.xpath(".//li");

    /**
     * Verify title of Clean Up Instance Page in Taxlink application
     *
     * @return boolean
     */
    public boolean verifyTitleCleanUpInstancePage() {
        boolean verifyFlag;
        wait.waitForElementDisplayed(summaryPageTitle);
        String cleanUpTitle = summaryPageTitle.getText();
        verifyFlag = cleanUpTitle.equalsIgnoreCase(CleanUpInstance.Details.headerCleanUpInstancePage);
        return verifyFlag;
    }

    /**
     * Verify that all instances present in the instance dropdown i.e. LOV
     * are listed in the summary page of Cleanup instance page in Taxlink UI
     *
     * @return boolean
     */
    public boolean verifyAllInstancesInSummaryOfCleanUp() {
        boolean final_flag = false;
        boolean allInstSummaryPresentFlag = false;
        expWait.until(ExpectedConditions.visibilityOf(instanceDropdownArrow));

        navigateToCleanUpInstancePage();
        boolean pageTitle_flag = verifyTitleCleanUpInstancePage();
        if (summaryPageChildTitle
                .getText()
                .equals(CleanUpInstance.Details.summaryPageChildTitle)) {
            click.clickElement(instanceDropdownArrow);
            ArrayList<WebElement> instancesLOV = new ArrayList<>(instanceList);
            for (WebElement webElement : instancesLOV) {
                jsWaiter.sleep(2000);
                Optional<WebElement> data = dataPresent(webElement
                        .getAttribute("aria-label"));
                if (data.isPresent()) {
                    allInstSummaryPresentFlag = true;
                    VertexLogger.log("Instance from LOV : " + webElement
                            .getAttribute("aria-label") + " is present in Cleanup instance summary page!!");
                }
            }
        }
        if (pageTitle_flag && allInstSummaryPresentFlag) {
            final_flag = true;
        }
        return final_flag;
    }

    /**
     * Verify that Prod instances present in the instance dropdown i.e. LOV
     * are not listed in the Clean up instance dropdown for cleanup
     * in Taxlink UI
     *
     * @return boolean
     */
    public boolean verifyNonProdInstancesInSourceCleanUp(String prodInstance) {
        boolean nonProdFlag = false;
        navigateToCleanUpInstancePage();
        boolean pageTitle_flag = verifyTitleCleanUpInstancePage();
        click.clickElement(cleanUpInstanceButton);

        wait.waitForElementDisplayed(addViewEditPageTitle);
        if (addViewEditPageTitle
                .getText()
                .equals(CleanUpInstance.Details.headerCleanUpInstancePage) && pageFirstChildTitle
                .getText()
                .equals(CleanUpInstance.Details.pageFirstChildTitle)) {
            List<WebElement> options = dropdown.getDropdownOptions(selectInstDrpDwn);
            for (WebElement option : options) {
                VertexLogger.log("Checking the Cleanup instance dropdown if Prod instance is present..");
                if (!option
                        .getText()
                        .equals(prodInstance) && pageTitle_flag) {
                    VertexLogger.log("Prod instances are not present in the Cleanup instance dropdown!!");
                    nonProdFlag = true;
                }
            }
        }
        return nonProdFlag;
    }

    /**
     * Method to search data in summary table of Rules Mapping page
     *
     * @return Optional<WebElement>
     */
    public Optional<WebElement> dataPresent(String text) {
        fluentWait.until(driver -> summaryPageTitle);
        Optional<WebElement> dataFound = instanceNamesCleanUp
                .stream()
                .filter(col -> col
                        .getText()
                        .equals(text))
                .findFirst();
        return dataFound;
    }

    /**
     * Verify that Non FI_ADMIN instances are not present in the instance dropdown i.e. LOV
     * are not listed in the Cleanup instance dropdown for cleanup
     * in Taxlink UI
     *
     * @return boolean
     */
    public boolean verifyCleanUpInstanceAsPerChangeInRole(String user, String instance) {
        boolean nonAdminFlag = false, resetRolesFlag = false, finalFlag = false;
        navigateToUsersTab();
        jsWaiter.sleep(15000);
        if (selectUser(user)) {
            jsWaiter.sleep(500);
            if ( removeRoleFromAnInstance(instance) )
            {
                navigateToCleanUpInstancePage();
                boolean pageTitle_flag = verifyTitleCleanUpInstancePage();
                jsWaiter.sleep(2000);
                js.executeScript("arguments[0].scrollIntoView();", cleanUpInstanceButton);
                click.clickElement(cleanUpInstanceButton);
                wait.waitForElementDisplayed(addViewEditPageTitle);
                List<WebElement> options = dropdown.getDropdownOptions(selectInstDrpDwn);
                for ( WebElement option : options )
                {
                    if ( !option
                            .getText()
                            .equals(instance) && pageTitle_flag) {
                        VertexLogger.log("Non FI_ADMIN instances are not present in the Cleanup instance dropdown!!");
                        nonAdminFlag = true;
                    }
                }
            }
        }
        clickOnUsersTab();
        jsWaiter.sleep(10000);
        if (selectUser(user)) {
            if (reassignRolesForAnInstance(instance)) {
                VertexLogger.log("Roles have been reset for " + instance + " !!");
                resetRolesFlag = true;
            }
        }
        if (nonAdminFlag && resetRolesFlag) {
            finalFlag = true;
        }
        return finalFlag;
    }

    /**
     * Remove FI_ADMIN role for an instance
     * and assign Users role
     *
     * @return boolean
     */
    public boolean reassignRolesForAnInstance(String instance) {
        boolean roleFIADMINChangeFlag = false, roleUserFlag = false, roleChangeFlag = false;
        jsWaiter.sleep(5000);
        click.clickElement(addEditButton);
        if (addEditPopUp.isDisplayed()) {
            click.clickElement(expandAllLinkOnPopUp);
            if (instancesFIADMINOnPopUp.isDisplayed() && instancesUSERSOnPopUp.isDisplayed()) {
                List<WebElement> optionsUSERS = instancesUSERSOnPopUp.findElements(listInstances);
                for (WebElement option : optionsUSERS) {
                    if (option
                            .getText()
                            .equals(instance)) {
                        jsWaiter.sleep(2000);
                        if (option
                                .findElement(By.xpath(".//span/input"))
                                .getAttribute("name")
                                .contains(instance)) {
                            click.clickElement(option.findElement(By.xpath(".//span/input")));
                            roleUserFlag = true;
                            break;
                        }
                    }
                }

                List<WebElement> optionsFIADMIN = instancesFIADMINOnPopUp.findElements(listInstances);
                for (WebElement option : optionsFIADMIN) {
                    VertexLogger.log(option.getText());
                    if (option
                            .getText()
                            .equals(instance)) {
                        jsWaiter.sleep(2000);
                        if (option
                                .findElement(By.xpath(".//span/input"))
                                .getAttribute("name")
                                .contains(instance)) {
                            click.clickElement(option.findElement(By.xpath(".//span/input")));
                            roleFIADMINChangeFlag = true;
                            break;
                        }
                    }
                }

                jsWaiter.sleep(2000);
                click.clickElement(saveButton);
                expWait.until(ExpectedConditions.textToBePresentInElement(popUpMessage,
                        "Are you sure you want to modify User roles"));

                click.clickElement(savePopUpMessageButton);
                VertexLogger.log("Assigned FI_ADMIN and assigned Users role to " + instance);
            }
            if (roleFIADMINChangeFlag && roleUserFlag) {
                roleChangeFlag = true;
            }
        }
        return roleChangeFlag;
    }

    /**
     * Verify Export to CSV functionality on Cleanup Page
     * in Taxlink application
     *
     * @return boolean
     */

    public boolean exportToCSVCleanUpInstance() throws IOException {
        boolean instanceTypeFlag = false;

        String fileName = "Instances.csv";
        String fileDownloadPath = String.valueOf(getFileDownloadPath());
        File file = new File(fileDownloadPath + File.separator + fileName);
        VertexLogger.log(String.valueOf(file));

        navigateToCleanUpInstancePage();
        {
            expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
            click.clickElement(exportToCSVSummaryPage);

            jsWaiter.sleep(2000);

            try (Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                         .withFirstRecordAsHeader()
                         .withHeader("Instance Name", "Instance Type")
                         .withTrim());) {
                for (CSVRecord csvRecord : csvParser) {
                    String instanceName_CSV = csvRecord.get("Instance Name");
                    String instanceType_CSV = csvRecord.get("Instance Type");

                    VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
                    VertexLogger.log("---------------");
                    VertexLogger.log("Instance Name : " + instanceName_CSV);
                    VertexLogger.log("Instance Type : " + instanceType_CSV);
                    VertexLogger.log("---------------\n\n");

                    if (!instanceName_CSV.equals("Instance Name")) {
                        Optional dataInstName = dataPresentInParticularColumn(instanceNamesCleanUp, instanceName_CSV);
                        if (dataInstName.isPresent()) {
                            Optional instanceFullNm = dataPresentInParticularColumn(instanceUsageCleanUp,
                                    instanceType_CSV);
                            instanceTypeFlag = instanceFullNm.isPresent();
                            VertexLogger.log("" + instanceTypeFlag);
                        }
                    }
                }
            }
        }
        return instanceTypeFlag;
    }

    /**
     * Verify the cleanup functionality for Non-prod, FI_ADMIN instance
     * in Cleanup tab in taxlink application
     *
     * @return boolean
     */

    public boolean verifyCleanUpInstance( String instanceName ) throws IOException
    {
        boolean cleanedUpFromSummaryFlag = false, cleanedUpFlag = false, cleanupFromLOVFlag = false;

		if(!presentInstance.getText().equals(instanceName))
		{
			click.clickElement(instanceDropdownArrow);
			expWait.until(ExpectedConditions.visibilityOfAllElements(instanceList));
			Optional<WebElement> matching = instanceList
				.stream()
				.filter(inst -> inst
					.getAttribute("aria-label")
					.contains(instanceName))
				.findFirst();
			if ( matching.isPresent() )
			{
				WebElement element = (WebElement) matching.get();
				VertexLogger.log("Instance " + instanceName + " is present in the Instance list!!");
				element.click();
			}
			else
			{
				VertexLogger.log("Required Instance " + instanceName + " is not present in the list");
				instanceDropdownArrow.click();
			}
		}
        if ( cleanUpInstanceLink.isDisplayed() )
        {
            expWait.until(ExpectedConditions.visibilityOf(cloneInstanceLink));
            click.clickElement(cloneInstanceLink);
            expWait.until(ExpectedConditions.visibilityOf(cleanUpInstanceLink));
            click.clickElement(cleanUpInstanceLink);
        }
        else
        {
            navigateToCleanUpInstancePage();
        }
        boolean pageTitle_flag = verifyTitleCleanUpInstancePage();
        click.clickElement(cleanUpInstanceButton);
        jsWaiter.sleep(5000);
        if ( addViewEditPageTitle.isDisplayed() )
        {
            click.clickElement(selectInstDrpDwn);
            dropdown.selectDropdownByDisplayName(selectInstDrpDwn, instanceName);
            VertexLogger.log("Instance" + instanceName + " selected for cleanup in the Cleanup instance dropdown!!");

            click.clickElement(confirmCleanUpButton);
            if ( popUpContents
                    .getText()
                    .contains(CleanUpInstance.Details.popUpMessage) )
            {
                click.clickElement(yesButton);
                wait.waitForElementDisplayed(summaryPageTitle);
                Optional<WebElement> data = dataPresent(instanceName);
                if (data.isEmpty())
				{
                    cleanedUpFromSummaryFlag = true;
                    VertexLogger.log("Instance " + instanceName + " is not present in Cleanup instance summary page!!");
                }
            }
        }

        click.clickElement(instanceDropdownArrow);

        jsWaiter.sleep(2000);
        Optional<WebElement> matching = instanceList
                .stream()
                .filter(inst -> inst
                        .getAttribute("aria-label")
                        .contains(instanceName))
                .findAny();
        if (matching.isEmpty()) {
            cleanupFromLOVFlag = true;
            VertexLogger.log("Instance from LOV " + instanceName + " is not present in LOV!!");
			expWait.until(ExpectedConditions.visibilityOf(cleanUpInstanceLink));
			click.clickElement(cleanUpInstanceLink);
        }
        if (cleanedUpFromSummaryFlag && cleanupFromLOVFlag && pageTitle_flag) {
            cleanedUpFlag = true;
        }
        return cleanedUpFlag;
    }
}