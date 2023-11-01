package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard.INSTANCE_DETAILS;

/**
 * this class represents all the locators and methods for smoke test cases
 * for Onboarding Dashboard.
 *
 * @author mgaikwad
 */

public class TaxLinkOnboardingDashboardPage extends TaxLinkBasePage {
    public TaxLinkOnboardingDashboardPage(final WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class = 'notification__container notification__container--alert']")
    private WebElement errorRoleNotAssigned;

    @FindBy(xpath = "//span[@class='Select-arrow']")
    private WebElement instanceDropdownArrow;

    @FindBy(xpath = "//div[@class= 'Select-menu-outer']/div[@id='react-select-2--list']/div")
    private List<WebElement> instanceList;

    @FindBy(xpath = "//button[contains(text(),'ADD')]")
    private WebElement addInstance;

    @FindBy(xpath = "//input[@name='Instance Name']")
    private WebElement instanceNameTextBox;

    @FindBy(xpath = "//label[contains(text(),'Instance Type')]/following-sibling::select")
    private WebElement instanceTypeDropdown;

    @FindBy(xpath = "//label[contains(text(),'Oracle Data Center')]/ancestor::div/select")
    private WebElement oracleDCDropdown;

    @FindBy(xpath = "//input[@name='ERP Cloud Username']")
    private WebElement cloudUserNameTextBox;

    @FindBy(xpath = "//input[@name='ERP Cloud Password']")
    private WebElement cloudPasswordTextBox;

    @FindBy(xpath = "//input[@name='Trusted ID']")
    private WebElement trustedIDTextBox;

    @FindBy(xpath = "//input[@name='Tax Link Host Name']")
    private WebElement hostNameTextBox;

    @FindBy(xpath = "//span[contains(text(),'Reset')]")
    private WebElement resetLinkOseriesURL;

    @FindBy(xpath = "//div[@class='row taxActionEditViewAdd_actionViewEditContainer__aMUsm']/div[9]/h6[2]")
    private WebElement viewPageResetLinkOseriesURL;

    @FindBy(xpath = "//span[contains(text(),'Copy')]")
    private WebElement copyLinkOseriesURL;

    @FindBy(xpath = "//label[@for='importRecommendedRules']")
    private WebElement recommendedRulesCheckbox;

    @FindBy(xpath = "//label[@for='222']/span")
    private WebElement serviceSubscriptionAccountReceivableCheckBox;

    @FindBy(xpath = "//label[@for='200']/span")
    private WebElement serviceSubscriptionAccountPayableCheckBox;

    @FindBy(xpath = "//label[@for='201']/span")
    private WebElement serviceSubscriptionPurchasingCheckBox;

    @FindBy(xpath = "//label[@for='10046']/span")
    private WebElement serviceSubscriptionOrderManagementCheckBox;

    @FindBy(xpath = "(//h6[contains(text(),'Copy')])[last()]")
    private WebElement copyLinkOracleCloudURL;

    @FindBy(xpath = "//input[@name='TaxLink URL']")
    private WebElement acceleratorURL;

    @FindBy(xpath = "//input[@name='TaxLink User Name']")
    private WebElement acceleratorUserName;

    @FindBy(xpath = "//input[@name='TaxLink Password']")
    private WebElement acceleratorPassword;

    @FindBy(xpath = "//button[contains(text(),'Oracle Cloud ERP')]")
    private WebElement erpConnectionButton;

    @FindBy(xpath = "//button[contains(text(),'O Series')]")
    private WebElement oSeriesConnectionButton;

    @FindBy(xpath = "//h2[contains(text(),'Oracle Cloud')]")
    private WebElement connectionMessageOERP;

    @FindBy(xpath = "//h2[contains(text(),'O Series')]")
    private WebElement connectionMessageOSeries;

    @FindBy(className = "notification__inner")
    private WebElement instancePopup;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='instanceIdentifier']")
    private WebElement instanceNamePresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='dataCenterShortName']")
    private WebElement dataCenterPresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='instanceType']")
    private WebElement instanceTypePresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='oracleErpUserName']")
    private WebElement erpUserNamePresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='0']")
    private WebElement erpPwdPresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='oseriesTrustedId']")
    private WebElement trustedIDPresentation;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='instanceIdentifier']")
    private List<WebElement> instanceNamePresent;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='dataCenterShortName']")
    private List<WebElement> dataCenterPresent;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='instanceType']")
    private List<WebElement> instanceTypePresent;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='oracleErpUserName']")
    private List<WebElement> erpUserNamePresent;

    @FindBy(xpath = "//div[@role='gridcell'][@col-id='oseriesTrustedId']")
    private List<WebElement> trustedIDPresent;

    @FindBy(xpath = "//div[contains(@class,'ag-body-viewport ag-layout-normal ag-row-no-animation')]")
    private WebElement presentationRow;

    @FindBy(xpath = "//h2[contains(text(),'Oracle Cloud')]/parent::div/span")
    private WebElement modalCloseOERP;

    @FindBy(xpath = "//h2[contains(text(),'O Series')]/parent::div/span")
    private WebElement modalCloseOSeries;

    @FindBy(xpath = "//input[contains(@name,'Vertex Client ID')]")
    private WebElement clientID;

    @FindBy(xpath = "//input[contains(@name,'Vertex Client Secret')]")
    private WebElement clientSecret;

    String updatedClientID;
    String getClientID;

    /**
     * click on Add Instance Button
     */

    public void clickOnAddInstance() {
        wait.waitForElementEnabled(addInstance);
        click.moveToElementAndClick(addInstance);
    }

    /**
     * click on Add button on Onboarding dashboard page in Taxlink application
     * <p>
     * return @boolean
     */

    public boolean addAndVerifyInstance(String instanceName, String instanceType, String oracleDataCenter,
                                        String cloudERPUsername, String cloudERPPassword, String trustedID, String instanceNameWithDC, boolean rule) {
        boolean flag = false;
        navigateToInstancePage();
        clickOnAddInstance();
        if (verifyNegativeTestInvalidInstanceName(instanceType, oracleDataCenter, cloudERPUsername, cloudERPPassword,
                trustedID)) {
            if (verifyNegativeTestInvalidDataCenter(instanceName, instanceType, cloudERPUsername, cloudERPPassword,
                    trustedID)) {
                VertexLogger.log("Onboarding a new instance...");
                wait.waitForElementDisplayed(instanceNameTextBox);
                text.enterText(instanceNameTextBox, instanceName);
                dropdown.selectDropdownByValue(instanceTypeDropdown, instanceType);
                dropdown.selectDropdownByValue(oracleDCDropdown, oracleDataCenter);
                text.enterText(cloudUserNameTextBox, cloudERPUsername);
                text.enterText(cloudPasswordTextBox, cloudERPPassword);
                text.enterText(trustedIDTextBox, trustedID);
				scroll.scrollElementIntoView(recommendedRulesCheckbox);
				checkbox.setCheckbox(recommendedRulesCheckbox, rule);

                js.executeScript("arguments[0].scrollIntoView();", saveButton);
                jsWaiter.sleep(1000);
                saveInstance();
            }
        }
        if (verifyInstanceCreatedPopUp()) {
            VertexLogger.log("Instance has been onboarded!! ");
            js.executeScript("arguments[0]. scrollIntoView();", closeButton);
            wait.waitForElementEnabled(closeButton);
            click.clickElement(closeButton);
            selectAddedInstanceFromDropdown(instanceName);
            clickOnOnboardingDashboard();
            wait.waitForElementDisplayed(summaryPageTitle);
            if (verifyInstanceDetails(instanceNameWithDC, instanceType, cloudERPUsername)) {
                VertexLogger.log("Instance details have been verified on summary page!! ");
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * click on Save button
     */

    public void saveInstance() {
        js.executeScript("arguments[0]. scrollIntoView();", saveButton);
        wait.waitForElementEnabled(saveButton);
        click.moveToElementAndClick(saveButton);
    }

    /**
     * Verify Invalid Instance Name pop up on Add Instance page in Taxlink application
     */

    public boolean verifyNegativeTestInvalidInstanceName(String instanceType, String oracleDataCenter,
                                                         String cloudERPUsername, String cloudERPPassword, String trustedID) {
        boolean flag = false;
        List<String> instanceNames = Arrays.asList(
                new String[]{"ecog-dev1", "ecog-dev2", "ecog-dev3", "ecog-test", "ecog", "ehyg", "ehyg-test"});
        VertexLogger.log("Validating Invalid Instance Name Error...");
        wait.waitForElementDisplayed(instanceNameTextBox);
        text.enterText(instanceNameTextBox, INSTANCE_DETAILS.instanceNameNegativeTest);
        dropdown.selectDropdownByValue(instanceTypeDropdown, instanceType);
        dropdown.selectDropdownByValue(oracleDCDropdown, oracleDataCenter);
        text.enterText(cloudUserNameTextBox, cloudERPUsername);
        text.enterText(cloudPasswordTextBox, cloudERPPassword);
        text.enterText(trustedIDTextBox, trustedID);

        if (!clientSecret.isEnabled()) {
            VertexLogger.log("Client secret is disabled. Kindly enter the Client ID to enable the client secret!");
            text.enterText(clientID, INSTANCE_DETAILS.clientIDTest);
        }

        VertexLogger.log("Client secret is mandatory if Client ID is given! Entering Client secret..");
        text.enterText(clientSecret, INSTANCE_DETAILS.clientSecretTest);
        scroll.scrollElementIntoView(saveButton);
        click.moveToElementAndClick(serviceSubscriptionAccountReceivableCheckBox);
        wait.waitForElementDisplayed(serviceSubscriptionAccountPayableCheckBox);
        click.moveToElementAndClick(serviceSubscriptionAccountPayableCheckBox);
        wait.waitForElementDisplayed(serviceSubscriptionOrderManagementCheckBox);
        click.moveToElementAndClick(serviceSubscriptionOrderManagementCheckBox);
        wait.waitForElementDisplayed(serviceSubscriptionPurchasingCheckBox);
        click.moveToElementAndClick(serviceSubscriptionPurchasingCheckBox);

        String selectedDC = dropdown
                .getDropdownSelectedOption(oracleDCDropdown)
                .getText();
        String enteredInstanceName = instanceNameTextBox.getText();
        saveInstance();

        if (selectedDC != "Cloud@ Customer") {
            for (int i = 0; i < instanceNames.size(); i++) {
                if (enteredInstanceName != instanceNames.get(i)) {
                    if (verifyInvalidInstanceNamePopUp()) {
                        flag = true;
                        VertexLogger.log("Instance Name is invalid. Kindly verify!! ");
                        break;
                    } else {
                        flag = false;
                        VertexLogger.log("Instance Name is not verified!! ");
                        break;
                    }
                }
            }
        }

        return flag;
    }

    /**
     * Verify Invalid Instance Name pop up on Add Instance page in Taxlink application
     */

    public boolean verifyNegativeTestInvalidDataCenter(String instanceName, String instanceType,
                                                       String cloudERPUsername, String cloudERPPassword, String trustedID) {
        boolean flag = false;
        VertexLogger.log("Validating Invalid combination of Instance Name & Data center Error...");
        wait.waitForElementDisplayed(instanceNameTextBox);
        text.enterText(instanceNameTextBox, instanceName);
        dropdown.selectDropdownByValue(instanceTypeDropdown, instanceType);
        dropdown.selectDropdownByValue(oracleDCDropdown, INSTANCE_DETAILS.dataCenterNegativeTest);
        text.enterText(cloudUserNameTextBox, cloudERPUsername);
        text.enterText(cloudPasswordTextBox, cloudERPPassword);
        text.enterText(trustedIDTextBox, trustedID);

        String selectedDC = dropdown
                .getDropdownSelectedOption(oracleDCDropdown)
                .getText();
        VertexLogger.log("Selected DC : " + selectedDC);
        saveInstance();

        if (selectedDC != "Chicago (US Commercial 2)" && selectedDC != "Ashburn (US Commercial 6)" &&
                selectedDC != "Cloud@ Customer)") {
            if (verifyInvalidDataCenterPopUp()) {
                flag = true;
                VertexLogger.log("Instance Name & data center combination is invalid. Kindly verify!! ");
            } else {
                flag = false;
                VertexLogger.log("Instance Name & data center combination is not verified!! ");
            }
        }
        return flag;
    }

    /**
     * Verify Instance created pop up on Add Instance page in Taxlink application
     */

    public boolean verifyInstanceCreatedPopUp() {
        boolean flag;
        jsWaiter.sleep(5000);
        js.executeScript("arguments[0]. scrollIntoView();", saveButton);
        wait.waitForElementDisplayed(instancePopup);
        VertexLogger.log(instancePopup.getText());
        if (instancePopup
                .getText()
                .contains("Onboarded")) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify Invalid Instance Name pop up is displayed on Add Instance page in Taxlink application
     */

    public boolean verifyInvalidInstanceNamePopUp() {
        boolean flag;
        wait.waitForElementDisplayed(instancePopup);
        VertexLogger.log(instancePopup.getText());
        if (instancePopup
                .getText()
                .contains("Invalid Instance Name")) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify Invalid Data center pop up is displayed on Add Instance page in Taxlink application
     */

    public boolean verifyInvalidDataCenterPopUp() {
        boolean flag;
        wait.waitForElementDisplayed(instancePopup);
        VertexLogger.log(instancePopup.getText());
        if (instancePopup
                .getText()
                .contains("Invalid")) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Select added Instance from Instance Dropdown in Taxlink application
     *
     * @return
     */

    public void selectAddedInstanceFromDropdown(String instanceValue) {
        expWait.until(ExpectedConditions.visibilityOf(instanceDropdownArrow));
        click.clickElement(instanceDropdownArrow);
        expWait.until(ExpectedConditions.visibilityOfAllElements(instanceList));
        Optional matching = instanceList
                .stream()
                .filter(inst -> inst
                        .getAttribute("aria-label")
                        .contains(instanceValue))
                .findAny();
        if (matching.isPresent()) {
            WebElement element = (WebElement) matching.get();
            VertexLogger.log("Added Instance is selected from the Instance list!!");
            element.click();
            jsWaiter.sleep(2000);
        } else {
            VertexLogger.log("Added Instance is not yet present in the list");
        }
    }

    /**
     * Verify Instance created on Add Instance page in Taxlink application
     *
     * @return
     */

    public boolean verifyInstanceDetails(String instanceNameWithDC, String instanceType, String cloudERPUsername) {
        boolean flag = false;
        boolean erpUserNameFlag = false, erpPwdFlag = false;
        jsWaiter.sleep(5000);
        if (presentationRow.isDisplayed()) {
            boolean instanceNameFlag = instanceNamePresentation
                    .getText()
                    .equals(instanceNameWithDC);
            VertexLogger.log(String.valueOf(instanceNameFlag));
            boolean instanceTypeFlag = instanceTypePresentation
                    .getText()
                    .equals(instanceType);
            VertexLogger.log(String.valueOf(instanceTypeFlag));
            if (erpUserNamePresentation
                    .getText()
                    .equals(cloudERPUsername)) {
                erpUserNameFlag = true;
                VertexLogger.log(String.valueOf(erpUserNameFlag));
            }

            if (erpPwdPresentation.isDisplayed()) {
                erpPwdFlag = true;
                VertexLogger.log(String.valueOf(erpPwdFlag));
            }

            if ((instanceNameFlag && instanceTypeFlag && erpUserNameFlag && erpPwdFlag)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Verify title of View Instance Page in Taxlink application
     *
     * @return
     */
    public boolean verifyTitleViewInstancePage() {
        boolean flag;
        String viewInstanceTitle = wait
                .waitForElementDisplayed(addViewEditPageTitle)
                .getText();
        boolean verifyFlag = viewInstanceTitle.equalsIgnoreCase(INSTANCE_DETAILS.headerViewInstancePage);

        if (verifyFlag) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify View Instance Page in Taxlink application
     *
     * @return
     */
    public boolean verifyViewInstancePage() {
        boolean flag = false;
        navigateToInstancePage();
        expWait.until(ExpectedConditions.visibilityOf(actions));
        actions.click();
        expWait.until(ExpectedConditions.visibilityOf(viewButton));
        viewButton.click();
        if (verifyTitleViewInstancePage()) {
            boolean instanceNameFlag = !instanceNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(instanceNameFlag));
            boolean instanceTypeFlag = !instanceTypeDropdown.isEnabled();
            VertexLogger.log(String.valueOf(instanceTypeFlag));
            boolean oracleDCFlag = !oracleDCDropdown.isEnabled();
            VertexLogger.log(String.valueOf(oracleDCFlag));
            boolean cloudUserNameFlag = !cloudUserNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(cloudUserNameFlag));
            boolean cloudPasswordFlag = !cloudPasswordTextBox.isEnabled();
            VertexLogger.log(String.valueOf(cloudPasswordFlag));
            boolean trustedIDFlag = !trustedIDTextBox.isEnabled();
            VertexLogger.log(String.valueOf(trustedIDFlag));
            boolean clientIDFlag = !clientID.isEnabled();
            VertexLogger.log(String.valueOf(clientIDFlag));
            boolean clientSecretFlag = !clientSecret.isEnabled();
            VertexLogger.log(String.valueOf(clientSecretFlag));
            boolean hostNameFlag = !hostNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(hostNameFlag));
            boolean saveFlag = !saveButton.isEnabled();
            VertexLogger.log(String.valueOf(saveFlag));
            boolean copyLinkOracleCloudFlag = copyLinkOracleCloudURL.isEnabled();
            VertexLogger.log(String.valueOf(copyLinkOracleCloudFlag));
            boolean acceleratorURLFlag = !acceleratorURL.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorURLFlag));
            boolean acceleratorUserNameFlag = !acceleratorUserName.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorUserNameFlag));
            boolean acceleratorPasswordFlag = !acceleratorPassword.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorPasswordFlag));

            boolean arCheckboxFlag = false;
            boolean apCheckboxFlag = false;
            boolean purCheckboxFlag = false;
            boolean ordManagementCheckboxFlag = false;
            String isDisabledAR = serviceSubscriptionAccountReceivableCheckBox.getAttribute("disabled");
            if (isDisabledAR == null || !isDisabledAR.equals("disabled")) {
                arCheckboxFlag = true;
            }
            String isDisabledAP = serviceSubscriptionAccountPayableCheckBox.getAttribute("disabled");
            if (isDisabledAP == null || !isDisabledAP.equals("disabled")) {
                apCheckboxFlag = true;
            }
            String isDisabledPurchasing = serviceSubscriptionPurchasingCheckBox.getAttribute("disabled");
            if (isDisabledPurchasing == null || !isDisabledPurchasing.equals("disabled")) {
                purCheckboxFlag = true;
            }
            String isDisabledOM = serviceSubscriptionOrderManagementCheckBox.getAttribute("disabled");
            if (isDisabledOM == null || !isDisabledOM.equals("disabled")) {
                ordManagementCheckboxFlag = true;
            }

            if ((instanceNameFlag && instanceTypeFlag && oracleDCFlag && cloudUserNameFlag && cloudPasswordFlag &&
                    trustedIDFlag && clientIDFlag && clientSecretFlag && hostNameFlag && saveFlag &&
                    copyLinkOracleCloudFlag && acceleratorURLFlag && acceleratorUserNameFlag && acceleratorPasswordFlag &&
                    arCheckboxFlag && apCheckboxFlag && purCheckboxFlag && ordManagementCheckboxFlag)) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Verify title of Edit Instance Page in Taxlink application
     *
     * @return
     */
    public boolean verifyTitleEditInstancePage() {
        boolean flag;
        String editInstanceTitle = wait
                .waitForElementDisplayed(addViewEditPageTitle)
                .getText();
        boolean verifyFlag = editInstanceTitle.equalsIgnoreCase(INSTANCE_DETAILS.headerEditInstancePage);
        if (verifyFlag) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify ERP connection on Edit page in Taxlink application
     *
     * @return flag
     */

    public boolean verifyErpConnection() {
        boolean flag;
        scroll.scrollElementIntoView(saveButton);

        jsWaiter.sleep(5000);
        erpConnectionButton.click();
        expWait.until(ExpectedConditions.visibilityOf(connectionMessageOERP));

        if (connectionMessageOERP
                .getText()
                .contains("Passed")) {
            flag = true;
        } else {
            flag = false;
        }
        modalCloseOERP.click();
        return flag;
    }

    /**
     * Verify O-series connection on Edit page in Taxlink application
     *
     * @return flag
     */

    public boolean verifyOseriesConnection() {
        boolean flag;
        scroll.scrollElementIntoView(saveButton);
        jsWaiter.sleep(1000);
        oSeriesConnectionButton.click();
        wait.waitForElementDisplayed(connectionMessageOSeries);

        if (connectionMessageOSeries
                .getText()
                .contains("Passed")) {
            flag = true;
        } else {
            flag = false;
        }
        modalCloseOSeries.click();
        return flag;
    }

    /**
     * Verify Edit functionality on Edit page in Taxlink application
     *
     * @return flag
     */

    public boolean verifyEditFunctionality() {
        boolean flag;
        wait.waitForElementDisplayed(clientID);
        clientID.clear();
        updatedClientID = utils.alphaNumericWithTimeStampText();
        text.enterText(clientID, updatedClientID);
        scroll.scrollElementIntoView(saveButton);
        saveInstance();
        if (verifyUpdatedInstanceDetails()) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify Instance created on Add Instance page in Taxlink application
     *
     * @return
     */

    public boolean verifyUpdatedInstanceDetails() {
        boolean flag;
        wait.waitForElementDisplayed(summaryPageTitle);
        editButton.click();
        wait.waitForElementDisplayed(addViewEditPageTitle);

        getClientID = clientID.getText();
        if (getClientID.equals(updatedClientID)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify Edit Instance Page in Taxlink application
     *
     * @return flag
     */
    public boolean verifyEditInstancePage() {
        boolean flag = false;
        boolean flag15 = false;
        boolean flag16 = false;
        boolean flag17 = false;
        navigateToInstancePage();

        wait.waitForElementDisplayed(editButton, 10);
        editButton.click();
        wait.waitForElementDisplayed(addViewEditPageTitle);
        if (verifyTitleEditInstancePage()) {
            boolean instanceNameFlag = !instanceNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(instanceNameFlag));
            boolean instanceTypeFlag = instanceTypeDropdown.isEnabled();
            VertexLogger.log(String.valueOf(instanceTypeFlag));
            boolean oracleDCFlag = !oracleDCDropdown.isEnabled();
            VertexLogger.log(String.valueOf(oracleDCFlag));
            boolean cloudUserNameFlag = cloudUserNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(cloudUserNameFlag));
            boolean cloudPasswordFlag = cloudPasswordTextBox.isEnabled();
            VertexLogger.log(String.valueOf(cloudPasswordFlag));
            boolean trustedIDFlag = trustedIDTextBox.isEnabled();
            VertexLogger.log(String.valueOf(trustedIDFlag));
            boolean clientIDFlag = clientID.isEnabled();
            VertexLogger.log(String.valueOf(clientIDFlag));
            boolean clientSecretFlag = clientSecret.isEnabled();
            VertexLogger.log(String.valueOf(clientSecretFlag));
            boolean hostNameFlag = hostNameTextBox.isEnabled();
            VertexLogger.log(String.valueOf(hostNameFlag));
            boolean saveButtonFlag = !saveButton.isEnabled();
            VertexLogger.log(String.valueOf(saveButtonFlag));
            boolean resetLinkOseriesFlag = resetLinkOseriesURL.isEnabled();
            VertexLogger.log(String.valueOf(resetLinkOseriesFlag));
            boolean copyLinkOseriesFlag = copyLinkOseriesURL.isEnabled();
            VertexLogger.log(String.valueOf(copyLinkOseriesFlag));
            boolean copyLinkOracleCloudFlag = copyLinkOracleCloudURL.isEnabled();
            VertexLogger.log(String.valueOf(copyLinkOracleCloudFlag));
            boolean acceleratorURLFlag = !acceleratorURL.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorURLFlag));
            boolean acceleratorUserNameFlag = !acceleratorUserName.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorUserNameFlag));
            boolean acceleratorPasswordFlag = !acceleratorPassword.isEnabled();
            VertexLogger.log(String.valueOf(acceleratorPasswordFlag));

            String isDisabledAR = serviceSubscriptionAccountReceivableCheckBox.getAttribute("disabled");
            if (isDisabledAR == null || !isDisabledAR.equals("disabled")) {
                flag15 = true;
            }
            String isDisabledAP = serviceSubscriptionAccountPayableCheckBox.getAttribute("disabled");
            if (isDisabledAP == null || !isDisabledAP.equals("disabled")) {
                flag16 = true;
            }

            String isDisabledPr = serviceSubscriptionPurchasingCheckBox.getAttribute("disabled");
            if (isDisabledPr == null || !isDisabledPr.equals("disabled")) {
                flag17 = true;
            }
            boolean orderMgtFlag = serviceSubscriptionOrderManagementCheckBox.isEnabled();

            htmlElement.sendKeys(Keys.END);

            if (instanceNameFlag && instanceTypeFlag && oracleDCFlag && cloudUserNameFlag && cloudPasswordFlag &&
                    trustedIDFlag && clientIDFlag && clientSecretFlag && hostNameFlag && saveButtonFlag &&
                    resetLinkOseriesFlag && copyLinkOracleCloudFlag && copyLinkOracleCloudFlag && acceleratorURLFlag &&
                    acceleratorUserNameFlag && acceleratorPasswordFlag && flag15 && flag16 && flag17 && orderMgtFlag) {
                verifyErpConnection();
                verifyOseriesConnection();
                verifyEditFunctionality();
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Method to search data in particular column of
     * summary tables
     *
     * @return Optional<WebElement>
     */
    public Optional<WebElement> dataPresentInParticularColumn(List<WebElement> ele, String text) {
        Optional<WebElement> dataFound = ele
                .stream()
                .filter(col -> col
                        .getText()
                        .contains(text))
                .findFirst();

        return dataFound;
    }

    /**
     * Verify Export to excel data for an instance in Taxlink application
     *
     * @return flag
     */
    public boolean exportToCSVOnboardingDashboard(String instName) throws IOException, InterruptedException {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;

        String fileName = "Customer_Extract.csv";

        String fileDownloadPath = String.valueOf(getFileDownloadPath());
        File file = new File(fileDownloadPath + File.separator + fileName);
        VertexLogger.log(String.valueOf(file));

        navigateToInstancePage();
        exportToCSVSummaryPage.click();

        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(15))
                .until(x -> file.exists());

        try (Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String name = csvRecord.get(0);
                if (name.contains(instName)) {
                    VertexLogger.log("Fusion Instance Name : " + name);
                    flag = true;
                    break;
                }
            }
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withHeader("Instance Name", "Instance Type", "Cloud ERP User Name", "Cloud ERP Password", "Trusted ID")
                     .withTrim());) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String instanceName = csvRecord.get("Instance Name");
                String instanceType = csvRecord.get("Instance Type");
                String cloudErpUserName = csvRecord.get("Cloud ERP User Name");
                String cloudErpPassword = csvRecord.get("Cloud ERP Password");

                VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
                VertexLogger.log("---------------");
                VertexLogger.log("Instance Name : " + instanceName);
                VertexLogger.log("Instance Type : " + instanceType);
                VertexLogger.log("Cloud ERP User Name : " + cloudErpUserName);
                VertexLogger.log("Cloud ERP Password : " + cloudErpPassword);
                VertexLogger.log("---------------\n\n");

                if (!instanceName.equals("Instance Name")) {
                    Optional instanceNameCSV = dataPresentInParticularColumn(instanceNamePresent, instanceName);
                    if (instanceNameCSV.isPresent()) {
                        Optional orc1 = dataPresentInParticularColumn(instanceTypePresent, instanceType);
                        flag2 = orc1.isPresent();
                        VertexLogger.log("" + flag2);
                        Optional orc2 = dataPresentInParticularColumn(erpUserNamePresent, cloudErpUserName);
                        flag3 = orc2.isPresent();
                        VertexLogger.log("" + flag3);
                    } else {
                        htmlElement.sendKeys(Keys.END);
                        jsWaiter.sleep(100);
                        click.clickElement(nextArrowOnSummaryTable);
                    }
                    if (flag2 && flag3) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file.delete()) {
                VertexLogger.log("File deleted successfully");
            } else {
                VertexLogger.log("Failed to delete the file");
            }
        }
        return flag;
    }
}




