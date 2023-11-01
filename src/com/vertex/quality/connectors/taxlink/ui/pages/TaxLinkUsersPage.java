package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.Users;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Users page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkUsersPage extends TaxLinkBasePage {
    public TaxLinkUsersPage(final WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),'SYNC USER')]")
    private WebElement syncUsersButton;

    @FindBy(xpath = "//h1[contains(text(),'Users')]/parent::div/parent::div/parent::div")
    private WebElement syncUsersPopup;

    @FindBy(xpath = "(//div/div[1]/div/div[1]/div[2]/div/div/div[1]/div[2]/div/div/span)[last()]")
    private WebElement selectAllCheckBox;

    @FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
    private List<WebElement> listCheckBox;

    @FindBy(xpath = "//span[@ref='eCellValue']")
    private List<WebElement> userNameListOnPopUp;

    @FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])[last()]")
    private WebElement totalPageCountPopUp;

    @FindBy(xpath = "//button[contains(text(),'SELECT')]")
    private WebElement selectButtonPopUp;

    @FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='username']")
    private List<WebElement> userNamePresentation;

    @FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='fullName']")
    private List<WebElement> userFullNamePresentation;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement userNameTextBox;

    @FindBy(xpath = "//input[@name='fullName']")
    private WebElement userFullNameTextBox;

    @FindBy(xpath = "//button[contains(text(), 'Expand')]")
    private WebElement expandAllLinkOnPopUp;

    @FindBy(xpath = "//button[contains(text(), 'Collapse')]")
    private WebElement collapseAllLinkOnPopUp;

    @FindBy(xpath = "//input[@name='ONBOARD_UIADMIN']")
    private WebElement onboardRoleCheckboxOnPopUp;

    @FindBy(xpath = "//input[@name='USER_MAINTENANCE']")
    private WebElement userMaintenanceCheckboxOnPopUp;

    @FindBy(xpath = "//ul[@id='USER']")
    private WebElement childNodesUSERSOnPopUp;

    @FindBy(xpath = "//ul[@id='READONLY']")
    private WebElement childNodesREADONLYOnPopUp;

    @FindBy(xpath = "//ul[@id='FI_ADMIN']")
    private WebElement childNodesFIADMINOnPopUp;

    @FindBy(xpath = "//*[@col-id='userRoleCode']")
    private List<WebElement> roleUsersPage;

    @FindBy(xpath = "//*[@col-id='fusionInstance']")
    private List<WebElement> instanceUsersPage;

    @FindBy(xpath = "//button[contains(text(), 'Close')]")
    private WebElement closeUsersPage;

    @FindBy(xpath = "//span[@ref='eCellValue']")
    private List<WebElement> selectUserName;

    @FindBy(xpath = "//div[@class='ag-cell-wrapper']/span[2]")
    private List<WebElement> importUserNameList;

    @FindBy(xpath = "//div[@class='ag-cell-wrapper']")
    private List<WebElement> importUserCheckboxList;

    @FindBy(xpath = "//button[contains(text(),'Export Roles to CSV')]")
    public WebElement exportToCSVSummaryPage;

    @FindBy(xpath = "//div[@ref='eOverlayWrapper']/span")
    public WebElement noRowsToShow;

    @FindBy(xpath = "//button[text()='Last']")
    public WebElement lastPageArrow;

	@FindBy(xpath = "//div[@ref='btLast']")
	public WebElement lastPageArrowClass;

    private By actionsButton = By.xpath(".//following-sibling::div[3]/div/div/div/button");
    private By editButton = By.xpath(".//following-sibling::div[3]/div/div/div/div/div/button");
    private By enabledUserColumn = By.xpath(".//following-sibling::div[2]");
    private By checkBoxUserName = By.xpath(".//span");

    /**
     * Method to dynamically pass role and instance name
     *
     * @param role
     * @param instance
     * @return
     */
    public WebElement roleCheckbox(String role, String instance) {
        By instanceRole = By.xpath("//ul[@id='" + role + "']/li[text()= '" + instance + "']/span/input");
        WebElement selectRole = expWait.until(ExpectedConditions.presenceOfElementLocated(instanceRole));

        return selectRole;
    }

    /**
     * Verify title of Users Page in Taxlink application
     *
     * @return boolean
     */
    public boolean verifyTitleUsersPage() {
        boolean flag;
        String headerUsersTitle = wait
                .waitForElementDisplayed(summaryPageTitle)
                .getText();
        boolean verifyFlag = headerUsersTitle.equalsIgnoreCase(Users.USERS_DETAILS.headerUsersPage);

        if (verifyFlag) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify sync and Edit user (i.e. assign roles to user) functionality in the Users Page
     * in Taxlink Application
     *
     * @return boolean
     */
    public boolean editUsers(String selectedVal) {
        boolean flagUserEdited = false;

        jsWaiter.sleep(15000);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
        for (int i = 1; i <= countSummaryTable; i++) {
            jsWaiter.sleep(5000);
            Optional<WebElement> flagUserPresence = dataPresentInParticularColumn(userNamePresentation, selectedVal);

            if (flagUserPresence.isPresent()) {
                VertexLogger.log(selectedVal + " User has been added to the Users Summary Table");
                flagUserPresence
                        .get()
                        .findElement(editButton)
                        .click();
                VertexLogger.log("Clicked on Edit button for the User: " + selectedVal);
                break;
            } else {
                actionPageDown
                        .sendKeys(Keys.PAGE_DOWN)
                        .perform();
                jsWaiter.sleep(15000);
                click.clickElement(nextArrowOnSummaryTable);
            }
        }
        jsWaiter.sleep(500);
        if (verifyEditUserPage()) {
            VertexLogger.log("Verified Edit Page for an User: " + selectedVal);
            flagUserEdited = true;
        }
        return flagUserEdited;
    }

    /**
     * Method to search data in summary table of Users Page
     *
     * @return Optional<WebElement>
     */
    public Optional<WebElement> dataPresent(String text) {
        jsWaiter.sleep(1500);
        Optional<WebElement> dataFound = userNamePresentation
                .stream()
                .filter(col -> col
                        .getText()
                        .equals(text))
                .findFirst();

        return dataFound;
    }

    /**
     * Method to add user from Sync Users pop up of Users Page
     *
     * @return String
     */
    public String addUserFromSyncUsersPopUp() {
        String selectedValue = null;

        navigateToUsersTab();
        if (verifyTitleUsersPage()) {
            click.clickElement(syncUsersButton);
            wait.waitForElementDisplayed(syncUsersPopup, 50);
            syncUsersPopup.isDisplayed();

            expWait.until(ExpectedConditions.elementToBeClickable(selectAllCheckBox));

            if (listCheckBox.size() > 0) {
                for (int i = 0; i < importUserNameList.size(); i++) {
                    String userName = importUserNameList
                            .get(i)
                            .getText();
                    VertexLogger.log("" + userName);
                    if (userName.equals(Users.USERS_DETAILS.superUser)) {
                        String nextUser = importUserNameList
                                .get(i + 1)
                                .getText();
                        VertexLogger.log("" + nextUser);
                        if (nextUser != "") {
                            selectedValue = importUserNameList
                                    .get(i)
                                    .findElement(checkBoxUserName)
                                    .getText();
                            importUserNameList
                                    .get(i + 1)
                                    .findElement(checkBoxUserName)
                                    .click();
                        } else {
                            VertexLogger.log("All users have already been imported!! No users are present to sync!!");
                        }
                        break;
                    } else {
                        selectedValue = importUserNameList
                                .get(i)
                                .getText();
                        importUserCheckboxList
                                .get(i)
                                .findElement(checkBoxUserName)
                                .click();
                    }
                    break;
                }
            }

            click.clickElement(selectButtonPopUp);
            expWait.until(ExpectedConditions.textToBePresentInElement(popUpMessage,
                    "Are you sure you want to save these records?"));

            click.clickElement(savePopUpMessageButton);
        } else {
            VertexLogger.log("All users have been imported. No user is present to sync!!");
        }
        return selectedValue;
    }

    /**
     * View a user on Users page
     */

    public boolean viewUser() {
        boolean flag = false;
        String enabledUserFlag;
        navigateToUsersTab();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

        for (int j = 0; j <= 4; j++) {
            String selectedUser = userNamePresentation
                    .get(j)
                    .getText();
            VertexLogger.log(selectedUser);
            enabledUserFlag = userNamePresentation
                    .get(j)
                    .findElement(enabledUserColumn)
                    .getText();
            Optional<WebElement> flagUserPresence = dataPresent(selectedUser);

            jsWaiter.sleep(5000);
            if (flagUserPresence.isPresent()) {
                flagUserPresence
                        .get()
                        .findElement(actionsButton)
                        .click();
                viewButton.click();
                VertexLogger.log("Clicked on View button for an User");
                if (verifyViewUserPage(enabledUserFlag)) {
                    VertexLogger.log("Verified View Page for an User");
                    actionPageDown
                            .sendKeys(Keys.PAGE_DOWN)
                            .perform();
                    click.clickElement(closeUsersPage);
                    wait.waitForElementDisplayed(exportToCSVSummaryPage);
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * Verify View User Page
     * <p>
     * return @boolean
     */
    public boolean verifyViewUserPage(String enabledUserFlag) {
        boolean viewUserFlag, checkRoleAssignedFlag = false, checkRoleUnassignedFlag = false;
        expWait.until(ExpectedConditions.visibilityOf(userNameTextBox));
        boolean userNameTextBoxFlag = !userNameTextBox.isEnabled();
        VertexLogger.log(String.valueOf(userNameTextBoxFlag));
        boolean userFullNameTextBoxFlag = !userFullNameTextBox.isEnabled();
        VertexLogger.log(String.valueOf(userFullNameTextBoxFlag));
        boolean addEditButtonFlag = !addEditButton.isEnabled();
        VertexLogger.log(String.valueOf(addEditButtonFlag));
        boolean closeUsersPageFlag = closeUsersPage.isEnabled();
        VertexLogger.log(String.valueOf(closeUsersPageFlag));

        if (enabledUserFlag.equals("N")) {
            if (noRowsToShow.isDisplayed()) {
                checkRoleUnassignedFlag = true;
            }
        } else {
            checkRoleAssignedFlag = true;
        }

        if (userNameTextBoxFlag && userFullNameTextBoxFlag && addEditButtonFlag && closeUsersPageFlag &&
                (checkRoleAssignedFlag || checkRoleUnassignedFlag)) {
            viewUserFlag = true;
        } else {
            viewUserFlag = false;
        }
        return viewUserFlag;
    }

    /**
     * Verify Edit User Page
     * <p>
     * return @boolean
     */
    public boolean verifyEditUserPage() {
        boolean flag;
        expWait.until(ExpectedConditions.visibilityOf(userNameTextBox));
        boolean userNameTextBoxFlag = !userNameTextBox.isEnabled();
        VertexLogger.log(String.valueOf(userNameTextBoxFlag));
        boolean userFullNameTextBoxFlag = !userFullNameTextBox.isEnabled();
        VertexLogger.log(String.valueOf(userFullNameTextBoxFlag));
        boolean addEditButtonFlag = addEditButton.isEnabled();
        VertexLogger.log(String.valueOf(addEditButtonFlag));
        boolean closeUsersPageFlag = closeUsersPage.isEnabled();
        VertexLogger.log(String.valueOf(closeUsersPageFlag));

        if (userNameTextBoxFlag && userFullNameTextBoxFlag && addEditButtonFlag && closeUsersPageFlag) {
            jsWaiter.sleep(5000);
            if (verifyAddEditFunctionalityForAnUser()) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Verify Add/Edit roles functionality for an User
     * <p>
     * return @boolean
     */
    public boolean verifyAddEditFunctionalityForAnUser() {
        boolean flag = false;
        boolean expandAllLinkOnPopUpFlag = false;
        boolean collapseAllLinkOnPopUpFlag = false;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean saveOnRolesPopUpFlag = false;
        boolean cancelOnRolesPopUpFlag = false;

        click.clickElement(addEditButton);
        if (addEditPopUp.isDisplayed()) {
            expandAllLinkOnPopUpFlag = expandAllLinkOnPopUp.isEnabled();
            VertexLogger.log(String.valueOf(expandAllLinkOnPopUpFlag));
            collapseAllLinkOnPopUpFlag = collapseAllLinkOnPopUp.isEnabled();
            VertexLogger.log(String.valueOf(collapseAllLinkOnPopUpFlag));

            click.clickElement(expandAllLinkOnPopUp);
            if (childNodesFIADMINOnPopUp.isDisplayed() && childNodesREADONLYOnPopUp.isDisplayed() &&
                    childNodesUSERSOnPopUp.isDisplayed()) {
                flag4 = true;
            }
            click.clickElement(collapseAllLinkOnPopUp);

            if ((childNodesUSERSOnPopUp
                    .getAttribute("class")
                    .contains("disable")) && (childNodesREADONLYOnPopUp
                    .getAttribute("class")
                    .contains("disable")) && (childNodesUSERSOnPopUp
                    .getAttribute("class")
                    .contains("disable"))) {
                flag5 = true;
            }

            checkbox.setCheckbox(onboardRoleCheckboxOnPopUp, true);
            checkbox.setCheckbox(userMaintenanceCheckboxOnPopUp, true);

            flag6 = checkbox.isCheckboxChecked(userMaintenanceCheckboxOnPopUp);
            if (!flag6) {
                click.clickElement(userMaintenanceCheckboxOnPopUp);
            }

            saveOnRolesPopUpFlag = saveButton.isEnabled();
            VertexLogger.log(String.valueOf(saveOnRolesPopUpFlag));
            cancelOnRolesPopUpFlag = cancelButton.isEnabled();
            VertexLogger.log(String.valueOf(cancelOnRolesPopUpFlag));
        }
        if (expandAllLinkOnPopUpFlag && collapseAllLinkOnPopUpFlag && flag4 && flag5 && flag6 &&
                saveOnRolesPopUpFlag && cancelOnRolesPopUpFlag) {
            click.clickElement(saveButton);
            expWait.until(ExpectedConditions.textToBePresentInElement(popUpMessage,
                    "Are you sure you want to modify User roles"));

            click.clickElement(savePopUpMessageButton);
            if (verifySelectedRolesInTableForAnUser("ONBOARD_UIADMIN")) {
                if (verifySelectedRolesInTableForAnUser("USER_MAINTENANCE")) {
                    flag = true;
                    click.clickElement(closeUsersPage);
                    jsWaiter.sleep(500);
                }
            }
        } else {
            flag = false;
        }

        return flag;
    }

    /**
	 * Method to change role of any instance
	 * This method is called in Clone Instance page class
	 *
	 * @param role
	 * @param instance
	 */
	public void editInstanceRole( String role, String instance )
	{
		if ( usersLink.isDisplayed() )
		{
			expWait.until(ExpectedConditions.visibilityOf(usersLink));
			click.clickElement(usersLink);
		}
		else
		{
			navigateToUsersTab();
		}

		jsWaiter.sleep(2000);

		if(!lastPageArrowClass.getAttribute("class").contains("disabled"))
		{
			click.clickElement(lastPageArrow);
		}
		Optional<WebElement> userName = dataPresent("logintest");

		if ( userName.isPresent() )
		{

			WebElement editBtn = userName
				.get()
				.findElement(By.xpath(".//following::button[text()='Edit']"));

			click.clickElement(editBtn);
			wait.waitForElementDisplayed(addEditButton);

			click.clickElement(addEditButton);
			if ( addEditPopUp.isDisplayed() )
			{
				click.clickElement(expandAllLinkOnPopUp);

				if ( !roleCheckbox(role, instance).isEnabled() )
				{
					click.clickElement(roleCheckbox("USER", instance));
                    click.clickElement(roleCheckbox(role, instance));
                    click.clickElement(saveButton);
                    click.clickElement(savePopUpMessageButton);
                    VertexLogger.log(instance + " now has " + role + " role");
                } else if (!roleCheckbox(role, instance).isSelected()) {
                    click.clickElement(roleCheckbox(role, instance));
                    click.clickElement(saveButton);
                    click.clickElement(savePopUpMessageButton);
                    VertexLogger.log(instance + " now has " + role + " role");
                } else {
                    click.clickElement(cancelButton);
                    VertexLogger.log(instance + " already has " + role + " role");
                }

            }
        }
    }

    /**
     * Verify Roles selected in Add/Edit roles pop up in a summary table for a User
     * return @boolean
     */
    public boolean verifySelectedRolesInTableForAnUser(String role) {
        boolean flag = false;
        jsWaiter.sleep(5000);
        Optional<WebElement> flagRolePresence = dataPresentInParticularColumn(roleUsersPage, role);

        for (int i = 1; i <= roleUsersPage.size(); i++) {
            if (flagRolePresence.isPresent()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Verify Export to CSV functionality on Users Page
     * in Taxlink application
     *
     * @return boolean
     */

    public boolean exportToCSVUsers() {
        boolean flag = false;
        boolean userFullNmFlag = false, verifyRoleFlag = false;
        String extractedRole;

        ArrayList<String> roles = new ArrayList<>();
        ArrayList<String> userName = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        String fileName = "USERS_Extract.csv";
        String fileDownloadPath = String.valueOf(getFileDownloadPath());
        File file = new File(fileDownloadPath + File.separator + fileName);
        VertexLogger.log(String.valueOf(file));

        navigateToUsersTab();
        {
            expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
            click.clickElement(exportToCSVSummaryPage);

            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);
            fluentWait
                    .pollingEvery(Duration.ofSeconds(1))
                    .withTimeout(Duration.ofSeconds(15))
                    .until(x -> file.exists());

            try (Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                         .withFirstRecordAsHeader()
                         .withHeader("User Name", "User Full Name", "Role (Fusion Instance Name)")
                         .withTrim());) {
                for (CSVRecord csvRecord : csvParser) {
                    String userName_CSV = csvRecord.get("User Name");
                    String userFullName_CSV = csvRecord.get("User Full Name");
                    String userRole_CSV = csvRecord.get("Role (Fusion Instance Name)");

                    VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
                    VertexLogger.log("---------------");
                    VertexLogger.log("User Name : " + userName_CSV);
                    VertexLogger.log("User Full Name : " + userFullName_CSV);
                    VertexLogger.log("Role (Fusion Instance Name) : " + userRole_CSV);
                    VertexLogger.log("---------------\n\n");

                    roles.add(userRole_CSV);
                    userName.add(userName_CSV);
                    if (!userName_CSV.equals("User Name")) {
                        Optional dataUserName = dataPresentInParticularColumn(userNamePresentation, userName_CSV);
                        if (dataUserName.isPresent()) {
                            Optional userFullNm = dataPresentInParticularColumn(userFullNamePresentation,
                                    userFullName_CSV);
                            userFullNmFlag = userFullNm.isPresent();
                            VertexLogger.log("" + userFullNmFlag);
                        } else {
                            htmlElement.sendKeys(Keys.END);
                            jsWaiter.sleep(500);
                            click.clickElement(nextArrowOnSummaryTable);
                        }
                    }
                }

                checkPageNavigation();
                expWait.until(ExpectedConditions.visibilityOf(totalPageCountSummaryTable));

                for (int j = 1; j <= 4; j++) {
                    expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
                    VertexLogger.log(" " + userName.get(j));
                    Optional<WebElement> flagUserPresence = dataPresentInParticularColumn(userNamePresentation,
                            userName.get(j));

                    if (flagUserPresence.isPresent()) {
                        htmlElement.sendKeys(Keys.PAGE_UP);
                        jsWaiter.sleep(5000);
                        flagUserPresence
                                .get()
                                .findElement(editButton)
                                .click();
                        wait.waitForElementDisplayed(summaryPageTitle, 50);
                        if (summaryPageTitle
                                .getText()
                                .equals("User Roles")) {
                            VertexLogger.log("In User Roles Page!!");
                            for (String role : roles) {
                                sb.append(role);
                                sb.append(" ");
                            }
                            extractedRole = sb.toString();
                            VertexLogger.log("" + extractedRole);

                            for (int l = 1; l < roleUsersPage.size(); l++) {
                                String roleName = roleUsersPage
                                        .get(l)
                                        .getText();
                                String instanceName = instanceUsersPage
                                        .get(l)
                                        .getText();
                                if (extractedRole.contains(roleName) && extractedRole.contains(instanceName)) {
                                    VertexLogger.log(roleName + " " + instanceName +
                                            " is/are verified from the exported CSV for this user!!");
                                    verifyRoleFlag = true;
                                }
                            }
                            actionPageDown
                                    .sendKeys(Keys.PAGE_DOWN)
                                    .perform();
                            click.clickElement(closeUsersPage);
                        }
                    }
                }

                if (userFullNmFlag && verifyRoleFlag) {
                    flag = true;
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
        }
        return flag;
    }
}