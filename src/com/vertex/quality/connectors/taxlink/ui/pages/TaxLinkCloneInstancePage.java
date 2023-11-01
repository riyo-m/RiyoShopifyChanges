package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.CloneInstance;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.*;

/**
 * This class contains all the methods to test Clone Instance functionality of TaxLink
 *
 * @author Shilpi.Verma, mgaikwad
 */
public class TaxLinkCloneInstancePage extends TaxLinkBasePage {

    private HashMap<Integer, WebElement> map = new LinkedHashMap<>();

    public TaxLinkCloneInstancePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(), 'No Rows To Show')]")
    private WebElement emptySummaryTable;

    @FindBy(xpath = "//button[contains(text(), 'Clone Instance')]")
    private WebElement cloneInstanceButton;

    @FindBy(xpath = "//label[contains(text(), 'Source Instance')]/following-sibling::select")
    private WebElement srcInstDrpDwn;

    @FindBy(xpath = "//label[contains(text(), 'Target Instance')]/following-sibling::select")
    private WebElement targetInstDrpDwn;

    @FindBy(xpath = "//div[contains(@class,'Modal modalShow')]")
    private WebElement cloneConfirmPopUp;

    @FindBy(tagName = "h5")
    private List<WebElement> popUpMsg;

    @FindBy(xpath = "//span[@class = 'confirm-msg']")
    private WebElement popUpMsg_Confirm;

    @FindBy(xpath = "//button[contains(text(), 'Yes')]")
    private WebElement yesButton;

    @FindBy(xpath = "//button[contains(text(), 'No')]")
    private WebElement noButton;

    @FindBy(xpath = "//span[contains(@ref,'lbRecordCount')]")
    private WebElement totalRecordCount;

    @FindBy(xpath = "//button[@value='UNDERCHARGED']")
    private WebElement underchargedSection;

    @FindBy(xpath = "//button[@value='ZEROCHARGED']")
    private WebElement zerochargedSection;

    @FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id= 'srcfusionInstance.fusionInstanceName']")
    private WebElement summaryTableCloneInstance;

    @FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'enabledFlag']")
    private List<WebElement> summaryTablePreCalcRulesMapping;

    private String srcInstance_Name;
    private String targetInstance_Name;
    private String cloneStatus;

    /**
     * Method to dynamically pass String value to xpath
     * for each Menu
     *
     * @param menuName
     * @return
     */
    public WebElement getMenuPath(String menuName) {
        By element = By.xpath(
                "//a[@class= 'rs-btn rs-btn-subtle rs-dropdown-toggle'][contains(text(), '" + menuName + "')]");
        WebElement ele = wait.waitForElementDisplayed(element);

        return ele;
    }

    /**
     * Method to dynamically pass String value to xpath
     * for each Tab under each Menu
     *
     * @param tabName
     * @return
     */
    public WebElement getTabPath(String tabName, String moduleName) {
        By element = By.xpath("//a[text()= '" + tabName + "'][@data-cy = '" + moduleName + "']");
        WebElement ele = wait.waitForElementDisplayed(element);

        return ele;
    }

    /**
     * Method to click on Menu
     *
     * @param menuName
     */
    public void clickMenu(WebElement menuName) {
        wait.waitForElementDisplayed(menuName);
        click.clickElement(menuName);
    }

    /**
     * Method to click on Tab and get the count of total no. of records
     *
     * @param tabName
     * @return
     */
    public int clickTab(WebElement tabName) {
        jsWaiter.sleep(2000);
        js.executeScript("arguments[0].scrollIntoView();", tabName);
        wait.waitForElementDisplayed(tabName);
        click.clickElement(tabName);
        jsWaiter.sleep(3000);

        try {
            dropdown.selectDropdownByDisplayName(externalFilter, "Both");
            jsWaiter.sleep(1000);
        } catch (Exception e) {
            VertexLogger.log("Enabled filter is not present in:" + tabName.getText());
        }

        wait.waitForElementDisplayed(totalRecordCount);
        int getTotalCount = Integer.parseInt(totalRecordCount.getText());

        return getTotalCount;
    }

    /**
     * Method to create HashMap for tabs under Dashboard menu
     *
     * @return
     */
    public LinkedHashMap<String, Integer> dashboardMap() {
        map.put(1, getTabPath("Business Unit", "businessUnits-module"));
        map.put(2, getTabPath("Legal Entity", "legalEntity-module"));
        map.put(3, getTabPath("AP Invoice Source", "APInvoceSource-module"));
        map.put(4, getTabPath("AR Transaction Type", "aRTransactionType-module"));
        map.put(5, getTabPath("AR Transaction Source", "arBatchSource-module"));
        map.put(6, getTabPath("Supplier Type", "supplierType-module"));

        LinkedHashMap<String, Integer> dashboardMap = new LinkedHashMap<>();
        map.forEach((k, v) ->
        {
            int recCount = clickTab(map.get(k));
            dashboardMap.put(v.getText(), recCount);
        });

        return dashboardMap;
    }

    /**
     * Method to create HashMap for tabs under TaxCalculationSetUps menu
     *
     * @return
     */
    public LinkedHashMap<String, Integer> taxCalcSetUpsMap() {
        map.clear();
        map.put(1, getTabPath("User Profile Options", "profiles-module"));
        map.put(2, getTabPath("Pre-Calculation", "preRulesMapping-module"));
        map.put(3, getTabPath("Post-Calculation", "postRulesMapping-module"));
        map.put(4, getTabPath("Condition Sets", "conditionSets-module"));
        map.put(5, getTabPath("Pre-Calculation", "inventoryPreRulesMapping-module"));
        map.put(6, getTabPath("Journal Output File", "inventoryJournalOutputFile-module"));
        map.put(7, getTabPath("Project Output File", "inventoryProjectOutputFile-module"));
        map.put(8, getTabPath("Condition Sets", "inventoryConditionSets-module"));
        map.put(9, getTabPath("AR / OM Tax Calculation Exclusion", "ARTaxCalculationExclusions-module"));

        LinkedHashMap<String, Integer> taxCalcSetUpsMap = new LinkedHashMap<>();
        map.forEach((k, v) ->
        {
            int taxCalcRecCount = clickTab(map.get(k));
            if (k == 5) {
                int Inventory_Pre_Calculation_RecCount = Integer.parseInt(totalRecordCount.getText());
                taxCalcSetUpsMap.put("Inventory Pre-Calculation", Inventory_Pre_Calculation_RecCount);
            } else if (k == 8) {
                int Inventory_Condition_Sets_RecCount = Integer.parseInt(totalRecordCount.getText());
                taxCalcSetUpsMap.put("Inventory Condition Sets", Inventory_Condition_Sets_RecCount);
            }
            if (!(k == 5 || k == 8)) {
                taxCalcSetUpsMap.put(v.getText(), taxCalcRecCount);
            }

        });

        return taxCalcSetUpsMap;
    }

    /**
     * Method to create HashMap for tabs under Procurement sub-menu
     *
     * @return
     */
    public LinkedHashMap<String, Integer> procureMap() {
        map.clear();
        map.put(1, getTabPath("AP Scan INV Sources", "APScanINVSources-module"));
        map.put(2, getTabPath("AP Assume Tax Sources", "APAssumeTaxSources-module"));
        map.put(3, getTabPath("AP Tax Action Ranges", "APTaxActionRanges-module"));
        map.put(4, getTabPath("AP Tax Action Override", "APTaxActionOverride-module"));
        map.put(5, getTabPath("AP Tax Calculation Exclusion", "APTaxCalculationExclusions-module"));
        map.put(6, getTabPath("PO Tax Calculation Exclusion", "poTaxCalculationExclusion-module"));

        LinkedHashMap<String, Integer> procureMap = new LinkedHashMap<>();
        map.forEach((k, v) ->
        {
            int procureMapRecCount = clickTab(map.get(k));
            if (k == 3) {
                click.clickElement(underchargedSection);
                wait.waitForElementDisplayed(totalRecordCount);
                int APTaxActionRanges_Undercharged_RecCount = Integer.parseInt(totalRecordCount.getText());

                click.clickElement(zerochargedSection);
                wait.waitForElementDisplayed(totalRecordCount);
                int APTaxActionRanges_Zerocharged_RecCount = Integer.parseInt(totalRecordCount.getText());

                procureMap.put("APTaxActionRanges_Undercharged", APTaxActionRanges_Undercharged_RecCount);
                procureMap.put("APTaxActionRanges_Zerocharged", APTaxActionRanges_Zerocharged_RecCount);
            }
            if (k == 4) {
                click.clickElement(underchargedSection);
                wait.waitForElementDisplayed(totalRecordCount);
                int APTaxActionOverride_Undercharged_RecCount = Integer.parseInt(totalRecordCount.getText());

                click.clickElement(zerochargedSection);
                wait.waitForElementDisplayed(totalRecordCount);
                int APTaxActionOverride_Zerocharged_RecCount = Integer.parseInt(totalRecordCount.getText());

                procureMap.put("APTaxActionOverride_Undercharged", APTaxActionOverride_Undercharged_RecCount);
                procureMap.put("APTaxActionOverride_Zerocharged", APTaxActionOverride_Zerocharged_RecCount);
            }
            procureMap.put(v.getText(), procureMapRecCount);
        });

        return procureMap;
    }

    /**
     * Method for returning list of menus
     *
     * @return
     */
    public List<WebElement> menuList() {

        List<WebElement> menuList = Arrays.asList(getMenuPath("Dashboard"), getMenuPath("Tax Calculation Setups"),
                getMenuPath("Advanced Configuration"));

        return menuList;
    }

    /**
     * Method to pass dynamic index values to By locator
     *
     * @param num
     * @return
     */
    public By xpathIndex(int num) {
        By column = By.xpath(".//following-sibling::div[" + num + "]");
        return column;
    }

    /**
     * Method to verify Clone Instance functionality
     *
     * @return
     */
    public boolean cloneInstance() throws Exception {
        boolean flag = false;

        clickMenu(menuList().get(0));
        LinkedHashMap<String, Integer> Dashboard_srcInstance = dashboardMap();
        VertexLogger.log("Dashboard_srcInstance: " + Dashboard_srcInstance);

        clickMenu(menuList().get(1));
        LinkedHashMap<String, Integer> TaxCalcSetUp_srcInstance = taxCalcSetUpsMap();
        VertexLogger.log("TaxCalcSetUp_srcInstance: " + TaxCalcSetUp_srcInstance);

        LinkedHashMap<String, Integer> ProcureMap_srcInstance = procureMap();
        VertexLogger.log("ProcureMap_srcInstance: " + ProcureMap_srcInstance);

        clickMenu(menuList().get(2));
        int systemProfOptions_srcInstance = clickTab(getTabPath("System Profile Options", "system-profiles-module"));
        VertexLogger.log("systemProfOptions_srcInstance: " + systemProfOptions_srcInstance);

        executeJs("arguments[0].scrollIntoView(true);", getTabPath("Clone Instance", "cloneInstance-module"));
        clickTab(getTabPath("Clone Instance", "cloneInstance-module"));

        wait.waitForElementDisplayed(summaryPageTitle);
        boolean summaryHeader_flag = summaryPageTitle
                .getText()
                .equals("Clone Instances");

        try {
            boolean emptyTable_flag = emptySummaryTable.isDisplayed();

            click.clickElement(cloneInstanceButton);
            wait.waitForElementDisplayed(addViewEditPageTitle);

            boolean addHeader_flag = addViewEditPageTitle
                    .getText()
                    .equals("Clone Instance");

            dropdown.selectDropdownByDisplayName(srcInstDrpDwn, CloneInstance.INSTANCE_DETAILS.source_instanceName);
            wait.waitForElementDisplayed(targetInstDrpDwn);

            dropdown.selectDropdownByDisplayName(targetInstDrpDwn, CloneInstance.INSTANCE_DETAILS.target_instanceName);

            wait.waitForElementEnabled(saveButton);
            boolean saveEnabled_flag = saveButton.isEnabled();

            click.clickElement(saveButton);
            wait.waitForElementDisplayed(cloneConfirmPopUp);

            boolean cloneConfirm_flag = popUpMsg
                    .get(0)
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.cloneConfirm_msg1) && popUpMsg
                    .get(1)
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.cloneConfirm_msg2) && popUpMsg
                    .get(2)
                    .findElement(By.xpath(".//b"))
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.cloneConfirm_msg3) &&
                    popUpMsg_Confirm
                            .getText()
                            .equals(CloneInstance.INSTANCE_DETAILS.cloneConfirm_msg4);

            click.clickElement(noButton);
            wait.waitForElementDisplayed(cancelButton);
            click.clickElement(cancelButton);
            wait.waitForElementDisplayed(summaryPageTitle);

            click.clickElement(cloneInstanceButton);
            wait.waitForElementDisplayed(addViewEditPageTitle);
            dropdown.selectDropdownByDisplayName(srcInstDrpDwn, CloneInstance.INSTANCE_DETAILS.source_instanceName);
            wait.waitForElementDisplayed(targetInstDrpDwn);
            dropdown.selectDropdownByDisplayName(targetInstDrpDwn, CloneInstance.INSTANCE_DETAILS.target_instanceName);
            click.clickElement(saveButton);
            wait.waitForElementDisplayed(cloneConfirmPopUp);
            click.clickElement(yesButton);
            wait.waitForElementDisplayed(summaryPageTitle);

            boolean srcInstance_Name_flag = summaryTableCloneInstance
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.source_instanceName);
            boolean targetInstance_Name_flag = summaryTableCloneInstance
                    .findElement(xpathIndex(1))
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.target_instanceName);
            boolean clonestatus_flag = summaryTableCloneInstance
                    .findElement(xpathIndex(2))
                    .getText()
                    .equals("Clone completed successfully");

            new TaxLinkHomePage(driver).selectInstance(CloneInstance.INSTANCE_DETAILS.target_instanceName);

            LinkedHashMap<String, Integer> Dashboard_targetInstance = dashboardMap();
            VertexLogger.log("Dashboard_targetInstance: " + Dashboard_targetInstance);
            boolean dashboard_recordMatch_flag = Dashboard_srcInstance.equals(Dashboard_targetInstance);

            LinkedHashMap<String, Integer> TaxCalcSetUpsMap_targetInstance = taxCalcSetUpsMap();
            VertexLogger.log("TaxCalcSetUpsMap_targetInstance: " + TaxCalcSetUpsMap_targetInstance);

            int offlineBIPjob_RecCount_targetInstance = clickTab(getTabPath("Offline BIP Extract Jobs", "offlineExtractJob-module"));
            VertexLogger.log("offlineBIPjob_RecCount_targetInstance: " + offlineBIPjob_RecCount_targetInstance);
            boolean BIPSrec_flag = offlineBIPjob_RecCount_targetInstance == 0;

            LinkedHashMap<String, Integer> procureMap_targetInstance = procureMap();
            VertexLogger.log("ProcureMap_targetInstance: " + procureMap_targetInstance);
            boolean procure_recordMatch_flag = ProcureMap_srcInstance.equals(procureMap_targetInstance);

            int systemProfOptions_targetInstance = clickTab(getTabPath("System Profile Options", "system-profiles-module"));
            VertexLogger.log("systemProfOptions_targetInstance: " + systemProfOptions_targetInstance);
            boolean sysProfOptions_flag = systemProfOptions_srcInstance == systemProfOptions_targetInstance;

            if (summaryHeader_flag && emptyTable_flag && addHeader_flag && saveEnabled_flag && cloneConfirm_flag &&
                    srcInstance_Name_flag && targetInstance_Name_flag && clonestatus_flag && dashboard_recordMatch_flag &&
                    BIPSrec_flag && procure_recordMatch_flag && sysProfOptions_flag) {
                flag = true;
            }
        } catch (Exception e) {
            boolean srcInstance_Name_flag = summaryTableCloneInstance
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.source_instanceName);
            boolean targetInstance_Name_flag = summaryTableCloneInstance
                    .findElement(xpathIndex(1))
                    .getText()
                    .equals(CloneInstance.INSTANCE_DETAILS.target_instanceName);
            boolean clonestatus_flag = summaryTableCloneInstance
                    .findElement(xpathIndex(2))
                    .getText()
                    .equals("Clone completed successfully");

            if (srcInstance_Name_flag && targetInstance_Name_flag && clonestatus_flag) {
                VertexLogger.log("Instance is already cloned");
                VertexLogger.log("Source Instance is: " + summaryTableCloneInstance.getText());
                VertexLogger.log("Target Instance is: " + summaryTableCloneInstance
                        .findElement(xpathIndex(1))
                        .getText());
                VertexLogger.log("Status is: " + summaryTableCloneInstance
                        .findElement(xpathIndex(2))
                        .getText());
                flag = true;
            } else {
                VertexLogger.log("Data in clone instance summary table is incorrect");
                VertexLogger.log("Source Instance is: " + summaryTableCloneInstance.getText());
                VertexLogger.log("Target Instance is: " + summaryTableCloneInstance
                        .findElement(xpathIndex(1))
                        .getText());
                VertexLogger.log("Status is: " + summaryTableCloneInstance
                        .findElement(xpathIndex(2))
                        .getText());
            }
        }
        return flag;
    }

    /**
     * Method for navigation to Clone Instance tab
     */
    public void navigateToCloneInstance() {
        wait.waitForElementDisplayed(advancedConfigurationLoc);
        click.clickElement(advancedConfigurationLoc);
        wait.waitForElementDisplayed(getTabPath("Clone Instance", "cloneInstance-module"));
        clickTab(getTabPath("Clone Instance", "cloneInstance-module"));
    }

    /**
     * Method to create instance without checking default rules box and clone it to target instance
     * which was onboarded with default rules checked
     * and verify if the target has default rules as Disabled
     *
     * @return
     */
    public boolean cloneWithoutDefaultRules() {

        boolean final_flag = false;
        new TaxLinkOnboardingDashboardPage(driver).addAndVerifyInstance("ecog-dev2", "DEV", "us2", "test", "test", "test",
                "ecog-dev2-us2", false);

        navigateToCloneInstance();
        wait.waitForElementDisplayed(summaryPageTitle);

        click.clickElement(cloneInstanceButton);
        wait.waitForElementDisplayed(addViewEditPageTitle);
        dropdown.selectDropdownByDisplayName(srcInstDrpDwn, "ecog-dev2-us2");
        wait.waitForElementDisplayed(targetInstDrpDwn);
        dropdown.selectDropdownByDisplayName(targetInstDrpDwn, CloneInstance.INSTANCE_DETAILS.target_instanceName);
        click.clickElement(saveButton);
        wait.waitForElementDisplayed(cloneConfirmPopUp);
        click.clickElement(yesButton);
        wait.waitForElementDisplayed(summaryPageTitle);

        new TaxLinkHomePage(driver).selectInstance(CloneInstance.INSTANCE_DETAILS.target_instanceName);

        clickMenu(menuList().get(1));
        clickTab(getTabPath("Pre-Calculation", "preRulesMapping-module"));
        wait.waitForElementDisplayed(summaryPageTitle);
        dropdown.selectDropdownByDisplayName(externalFilter, "Enabled");
        boolean enabledRulesTotalPageCount_flag = totalPageCountSummaryTable.getText().equals("0");
        if (enabledRulesTotalPageCount_flag) {
            VertexLogger.log("No rules are enabled");
        } else {
            VertexLogger.log("Some/All rules are enabled");
        }

        dropdown.selectDropdownByDisplayName(externalFilter, "Disabled");
        int totalCount = Integer.parseInt(totalPageCountSummaryTable.getText());
        for (int i = 0; i <= totalCount; i++) {
            boolean enabledData_flag = summaryTablePreCalcRulesMapping.stream().allMatch(en -> en.getText().equals("N"));
            if (enabledData_flag) {
                VertexLogger.log("All the rules are disabled in this page");
            }
        }

        if (enabledRulesTotalPageCount_flag) {
            final_flag = true;
        }
        return final_flag;

    }


    /**
     * Verify Export to CSV
     *
     * @return boolean
     * @throws Exception
     */
    public boolean exportToCSVCloneInstance() throws Exception {
        boolean flag = false;
        boolean data_flag = false;

        navigateToCloneInstance();
        wait.waitForElementDisplayed(summaryPageTitle);

        try {
            if (emptySummaryTable.isDisplayed()) {
                VertexLogger.log("Clone Instance summary table is empty");
                data_flag = true;
            }
        } catch (Exception e) {
            srcInstance_Name = summaryTableCloneInstance.getText();
            targetInstance_Name = summaryTableCloneInstance
                    .findElement(xpathIndex(1))
                    .getText();
            cloneStatus = summaryTableCloneInstance
                    .findElement(xpathIndex(2))
                    .getText();

            String fileName = "CloneInstances.csv";
            String fileDownloadPath = String.valueOf(getFileDownloadPath());
            File file = new File(fileDownloadPath + File.separator + fileName);
            VertexLogger.log(String.valueOf(file));

            setFluentWait(file);

            List<CSVRecord> records = parseCSVRecord(file);
            Optional<CSVRecord> data = records
                    .stream()
                    .filter(rec -> rec
                            .get(0)
                            .contains(srcInstance_Name) && rec
                            .get(1)
                            .contains(targetInstance_Name) && rec
                            .get(2)
                            .contains(cloneStatus))
                    .findFirst();

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
        }

        if (data_flag) {
            flag = true;
        }

        return flag;
    }

    /**
     * Verify that Non FI_ADMIN instances are not present in the instance dropdown i.e. LOV
     * are not listed in the Clone instance source and target dropdowns for cloning
     * in Taxlink UI
     *
     * @param instance
     * @param user
     * @return boolean
     */
    public boolean verifyCloneInstanceAsPerChangeInRole(String user, String instance) {
        boolean nonAdminSrcFlag = false, nonAdminTargetFlag = false, resetRolesFlag = false, finalFlag = false;
        jsWaiter.sleep(500);
        navigateToCloneInstance();
        jsWaiter.sleep(2000);
        js.executeScript("arguments[0].scrollIntoView();", cloneInstanceButton);
        click.clickElement(cloneInstanceButton);
        wait.waitForElementDisplayed(addViewEditPageTitle);
        List<WebElement> optionsSrc = dropdown.getDropdownOptions(srcInstDrpDwn);
        for (WebElement option : optionsSrc) {
            if (!option
                    .getText()
                    .equals(instance)) {
                VertexLogger.log("Non FI_ADMIN instances are not present in the Source Clone instance dropdown!!");
                nonAdminSrcFlag = true;
            }
        }
        List<WebElement> optionsTarget = dropdown.getDropdownOptions(targetInstDrpDwn);
        for (WebElement option : optionsTarget) {
            if (!option
                    .getText()
                    .equals(instance)) {
                VertexLogger.log("Non FI_ADMIN instances are not present in the Target Clone instance dropdown!!");
                nonAdminTargetFlag = true;
            }
        }
        if (nonAdminSrcFlag && nonAdminTargetFlag) {
            finalFlag = true;
        }
        return finalFlag;
    }

    /**
     * Remove FI_ADMIN role for an instance
     * and assign Users role
     *
     * @param instance
     * @return boolean
     */
    public boolean reassignRolesForAnInstance(String instance) {
        boolean roleFIADMINChangeFlag = false, roleUserFlag = false, roleChangeFlag = false;
        jsWaiter.sleep(15000);
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
                VertexLogger.log("Assigned FI_ADMIN and unassigned Users role to " + instance);
            }
            if (roleFIADMINChangeFlag && roleUserFlag) {
                roleChangeFlag = true;
            }
        }
        return roleChangeFlag;
    }
}
