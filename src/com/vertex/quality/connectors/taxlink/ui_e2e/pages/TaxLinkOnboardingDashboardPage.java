package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.OnboardingDashboardCloudCustomer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

import static com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard.INSTANCE_DETAILS;

/**
 * this class represents all the locators and methods for smoke test cases
 * for Onboarding Dashboard.
 *
 * @author mgaikwad
 */

public class TaxLinkOnboardingDashboardPage extends TaxLinkBasePage
{
	public TaxLinkOnboardingDashboardPage( final WebDriver driver )
	{
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

	@FindBy(xpath = "//input[@name='erpCloudUrl']")
	private WebElement instanceURLTextBox;

	@FindBy(xpath = "//input[@name='Trusted ID']")
	private WebElement trustedIDTextBox;

	@FindBy(xpath = "//input[@name='Tax Link Host Name']")
	private WebElement hostNameTextBox;

	@FindBy(xpath = "//h6[contains(text(),'Reset')]")
	private WebElement resetLinkOseriesURL;

	@FindBy(xpath = "//div[@class='row taxActionEditViewAdd_actionViewEditContainer__aMUsm']/div[9]/h6[2]")
	private WebElement viewPageResetLinkOseriesURL;

	@FindBy(xpath = "//h6[contains(text(),'Copy')][1]")
	private WebElement copyLinkOseriesURL;

	@FindBy(xpath = "//label[@for='222']/span[@class='custom__cb__label-text']")
	private WebElement serviceSubscriptionAccountReceivableCheckBox;

	@FindBy(xpath = "//label[@for='200']/span[@class='custom__cb__label-text']")
	private WebElement serviceSubscriptionAccountPayableCheckBox;

	@FindBy(xpath = "//label[@for='201']/span[@class='custom__cb__label-text']")
	private WebElement serviceSubscriptionPurchasingCheckBox;

	@FindBy(xpath = "//label[@for='10046']/span[@class='custom__cb__label-text']")
	private WebElement serviceSubscriptionOrderManagementCheckBox;

	@FindBy(xpath = "(//h6[contains(text(),'Copy')])[last()]")
	private WebElement copyLinkOracleCloudURL;

	@FindBy(xpath = "//input[@name='TaxLink URL']")
	private WebElement acceleratorURL;

	@FindBy(xpath = "//input[@name='TaxLink User Name']")
	private WebElement acceleratorUserName;

	@FindBy(xpath = "//input[@name='TaxLink Password']")
	private WebElement acceleratorPassword;

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

	@FindBy(xpath = "//div[contains(@class,'ag-body-viewport ag-layout-normal ag-row-no-animation')]")
	private WebElement presentationRow;

	@FindBy(xpath = "//input[contains(@name,'Vertex Client ID')]")
	private WebElement clientID;

	@FindBy(xpath = "//input[contains(@name,'Vertex Client Secret')]")
	private WebElement clientSecret;

	@FindBy(xpath = "//label[@for='importRecommendedRules']")
	private WebElement recommendedRulesCheckbox;

	/**
	 * click on Add Instance Button
	 */

	public void clickOnAddInstance( )
	{
		wait.waitForElementEnabled(addInstance);
		click.moveToElementAndClick(addInstance);
	}

	/**
	 * click on Add button on Onboarding dashboard page in Taxlink application
	 *
	 * return @boolean
	 */

	public boolean addAndVerifyInstance(boolean rule )
	{
		boolean flag = false;
		navigateToInstancePage();
		clickOnAddInstance();

		VertexLogger.log("Onboarding a new instance...");
		wait.waitForElementDisplayed(instanceNameTextBox);
		text.enterText(instanceNameTextBox, OnboardingDashboard.INSTANCE_DETAILS.instanceName);
		dropdown.selectDropdownByValue(instanceTypeDropdown, OnboardingDashboard.INSTANCE_DETAILS.instanceType);
		dropdown.selectDropdownByValue(oracleDCDropdown, OnboardingDashboard.INSTANCE_DETAILS.oracleDataCenter);
		text.enterText(cloudUserNameTextBox, OnboardingDashboard.INSTANCE_DETAILS.cloudERPUsername);
		text.enterText(cloudPasswordTextBox, OnboardingDashboard.INSTANCE_DETAILS.cloudERPPassword);
		text.enterText(trustedIDTextBox, OnboardingDashboard.INSTANCE_DETAILS.trustedID);
		checkbox.setCheckbox(recommendedRulesCheckbox,rule);

		text.enterText(clientID, INSTANCE_DETAILS.clientIDTest);
		text.enterText(clientSecret, INSTANCE_DETAILS.clientSecretTest);
		scroll.scrollElementIntoView(saveButton);
		click.moveToElementAndClick(serviceSubscriptionAccountReceivableCheckBox);
		click.moveToElementAndClick(serviceSubscriptionAccountPayableCheckBox);
		js.executeScript("arguments[0].scrollIntoView();", saveButton);
		jsWaiter.sleep(1000);
		saveInstance();

		if ( verifyInstanceCreatedPopUp() )
		{
			VertexLogger.log("Instance has been onboarded!! ");
			js.executeScript("arguments[0]. scrollIntoView();", closeButton);
			wait.waitForElementEnabled(closeButton);
			click.clickElement(closeButton);
			selectAddedInstanceFromDropdown(OnboardingDashboard.INSTANCE_DETAILS.instanceName);
			clickOnOnboardingDashboard();
			wait.waitForElementDisplayed(summaryPageTitle);
			if ( verifyAddedInstanceDetails() )
			{
				VertexLogger.log("Instance details have been verified on summary page!! ");
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Verify Instance created on Add Instance page
	 * in Taxlink application i.e. ecog-dev3-us2
	 *
	 * @return
	 */

	public boolean verifyAddedInstanceDetails( )
	{
		boolean flag = false;
		boolean erpUserNameFlag = false,erpPwdFlag = false;
		wait.waitForElementDisplayed(presentationRow);
		if ( presentationRow.isDisplayed() )
		{
			boolean instanceNameFlag = instanceNamePresentation
				.getText()
				.equals(OnboardingDashboard.INSTANCE_DETAILS.instanceNameWithDC);
			VertexLogger.log(String.valueOf(instanceNameFlag));
			boolean instanceTypeFlag = instanceTypePresentation
				.getText()
				.equals(OnboardingDashboard.INSTANCE_DETAILS.instanceType);
			VertexLogger.log(String.valueOf(instanceTypeFlag));
			if ( erpUserNamePresentation
				.getText()
				.equals(OnboardingDashboard.INSTANCE_DETAILS.cloudERPUsername) )
			{
				erpUserNameFlag = true;
				VertexLogger.log(String.valueOf(erpUserNameFlag));
			}

			if ( erpPwdPresentation.isDisplayed() )
			{
				erpPwdFlag = true;
				VertexLogger.log(String.valueOf(erpPwdFlag));
			}
			if ( (instanceNameFlag && instanceTypeFlag && erpUserNameFlag && erpPwdFlag) )
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * click on Add button on Onboarding dashboard page in Taxlink application
	 *
	 * return @boolean
	 */

	public boolean addAndVerifyInstanceForCC( )
	{
		boolean flag = false;
		navigateToInstancePage();
		clickOnAddInstance();

		VertexLogger.log("Onboarding a new instance...");
		wait.waitForElementDisplayed(instanceNameTextBox);
		text.enterText(instanceNameTextBox, OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceName);
		dropdown.selectDropdownByValue(instanceTypeDropdown,
			OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceType);
		dropdown.selectDropdownByValue(oracleDCDropdown,
			OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.oracleDataCenter);
		text.enterText(cloudUserNameTextBox, OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.cloudERPUsername);
		text.enterText(cloudPasswordTextBox, OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.cloudERPPassword);
		text.enterText(instanceURLTextBox, OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceURLCloudCustomer);
		wait.waitForElementDisplayed(trustedIDTextBox,20);
		text.enterText(trustedIDTextBox, OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.trustedID);
		text.enterText(clientID, INSTANCE_DETAILS.clientIDTest);
		text.enterText(clientSecret, INSTANCE_DETAILS.clientSecretTest);
		scroll.scrollElementIntoView(saveButton);
		click.moveToElementAndClick(serviceSubscriptionAccountReceivableCheckBox);
		click.moveToElementAndClick(serviceSubscriptionAccountPayableCheckBox);

		js.executeScript("arguments[0].scrollIntoView();", saveButton);
		jsWaiter.sleep(1000);
		saveInstance();

		if ( verifyInstanceCreatedPopUp() )
		{
			VertexLogger.log("Instance has been onboarded!! ");
			js.executeScript("arguments[0]. scrollIntoView();", closeButton);
			click.clickElement(closeButton);
			selectAddedInstanceFromDropdown(OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceName);
			clickOnOnboardingDashboard();

			wait.waitForElementDisplayed(summaryPageTitle);
			if ( verifyInstanceDetailsForCC() )
			{
				VertexLogger.log("Instance details have been verified on summary page!! ");
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * click on Save button
	 */

	public void saveInstance( )
	{
		js.executeScript("arguments[0]. scrollIntoView();", saveButton);
		wait.waitForElementEnabled(saveButton);
		click.moveToElementAndClick(saveButton);
	}

	/**
	 * Verify Instance created pop up on Add Instance page in Taxlink application
	 */

	public boolean verifyInstanceCreatedPopUp( )
	{
		boolean flag;
		jsWaiter.sleep(5000);
		js.executeScript("arguments[0]. scrollIntoView();", closeButton);
		wait.waitForElementDisplayed(instancePopup);
		VertexLogger.log(instancePopup.getText());
		if ( instancePopup
			.getText()
			.contains("Onboarded") )
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
		return flag;
	}

	/**
	 * Select added Instance from Instance Dropdown in Taxlink application
	 *
	 * @return
	 */

	public void selectAddedInstanceFromDropdown( String instanceValue )
	{
		expWait.until(ExpectedConditions.visibilityOf(instanceDropdownArrow));
		click.clickElement(instanceDropdownArrow);
		expWait.until(ExpectedConditions.visibilityOfAllElements(instanceList));
		Optional matching = instanceList
			.stream()
			.filter(inst -> inst
				.getAttribute("aria-label")
				.contains(instanceValue))
			.findFirst();
		if ( matching.isPresent() )
		{
			WebElement element = (WebElement) matching.get();
			VertexLogger.log("Added Instance is selected from the Instance list!!");
			element.click();
		}
		else
		{
			VertexLogger.log("Added Instance is not yet present in the list");
		}
	}

	/**
	 * Verify Instance created on Add Instance page in Taxlink application
	 *
	 * @return
	 */

	public boolean verifyInstanceDetailsForCC( )
	{
		boolean flag = false;
		boolean erpUserNameFlag = false,erpPwdFlag = false;
		wait.waitForElementDisplayed(presentationRow);
		if ( presentationRow.isDisplayed() )
		{
			jsWaiter.sleep(2000);
			boolean instanceNameFlag = instanceNamePresentation
				.getText()
				.equals(OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceName);
			VertexLogger.log(String.valueOf(instanceNameFlag));
			boolean instanceTypeFlag = instanceTypePresentation
				.getText()
				.equals(OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.instanceType);
			VertexLogger.log(String.valueOf(instanceTypeFlag));
			if ( erpUserNamePresentation
				.getText()
				.equals(OnboardingDashboardCloudCustomer.INSTANCE_DETAILS.cloudERPUsername) )
			{
				erpUserNameFlag = true;
				VertexLogger.log(String.valueOf(erpUserNameFlag));
			}
			if ( erpPwdPresentation.isDisplayed() )
			{
				erpPwdFlag = true;
				VertexLogger.log(String.valueOf(erpPwdFlag));
			}

			if ( (instanceNameFlag && instanceTypeFlag && erpUserNameFlag && erpPwdFlag) )
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}
}




