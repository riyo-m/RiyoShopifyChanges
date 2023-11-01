package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.BusinessUnit;
import com.vertex.quality.connectors.taxlink.ui.enums.Monitoring;
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
import java.util.*;

import static com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard.INSTANCE_DETAILS;

/**
 * this class represents all the locators and methods for Retry Jobs page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkMonitoringPage extends TaxLinkBasePage
{
	public TaxLinkMonitoringPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='rs-nav-item-content']")
	private WebElement onboardingDashboardButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/retryJob']")
	private WebElement retryJobsButton;

	@FindBy(xpath = "//button[@data-cy='btn-enableName']")
	private WebElement enableRetryJobsButton;

	@FindBy(xpath = "//div[@class='modalCustome modalShow2 react-draggable']")
	private WebElement enableRetryJobsPopUp;

	@FindBy(xpath = "//button[@class='btn btn-sm btn--primary btn-colored SAVE']")
	private WebElement yesButtonEnableRetryJobsPopUp;

	@FindBy(xpath = "//button[@class='btn btn-sm btn--tertiary btn-white SAVE']")
	private WebElement noButtonEnableRetryJobsPopUp;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='userBatchJobName']")
	private WebElement jobNameRetryJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='externalServiceDescription']")
	private WebElement serviceDescRetryJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='businessUnitName']")
	private WebElement buNameRetryJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@comp-id='1794']")
	private WebElement serviceSubscriptionsRetryJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='batchJobCron']")
	private WebElement scheduleRetryJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='userBatchJobName']")
	private List<WebElement> jobNamePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='0']")
	private List<WebElement> serviceSubscriptionPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='externalServiceDescription']")
	private List<WebElement> serviceDescPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='businessUnitName']")
	private List<WebElement> buNamePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='batchJobCron']")
	private List<WebElement> schedulePresentation;

	@FindBy(xpath = "//*[@col-id='docType']")
	private List<WebElement> docTypePresentPollingJobs;

	@FindBy(xpath = "//*[@col-id='subscriptionService']")
	private List<WebElement> serviceSubscriptionPresentationPollingJobs;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/businessUnits']")
	private WebElement businessUnitButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/businessUnitsAdd']")
	private WebElement addBusinessUnit;

	@FindBy(xpath = "//input[@name='Business Unit Id ']")
	private WebElement addBusinessUnitID;

	@FindBy(xpath = "//input[@name='Business Unit Name']")
	private WebElement addBusinessUnitName;

	@FindBy(xpath = "//span[contains(text(),'Countries of Operation')]/ancestor::p/following-sibling::div")
	private WebElement buCountryNameSelectBox;

	@FindBy(id = "searchCountries")
	private WebElement buCountryNameTextBox;

	@FindBy(className = "Business Unit Name")
	private WebElement buCountryName;

	@FindBy(xpath = "//*[@id='my-modal']/div/div[3]/div/label/span")
	private WebElement buCountryNameCheckbox;

	@FindBy(xpath = "//*[@id='my-modal']/div/div[4]/div/div[1]/button")
	private WebElement savebuCountryName;

	@FindBy(xpath = "//div[contains(text(),'Clear All')]")
	private WebElement clearAllCountriesCheckBox;

	@FindBy(xpath = "(//button[@class='btn btn-sm btn--primary btn-colored'])[last()-1]")
	private WebElement saveBusinessUnit;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='defaultMapping']")
	private List<WebElement> summaryReportsNamePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='docType']")
	private List<WebElement> docTypePresentationPollingJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='enabledFlag']")
	private List<WebElement> enabledPresentationPollingJobs;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='enabledFlag']")
	private List<WebElement> enabledPresentation;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
	private List<WebElement> importListCheckBox;

	@FindBy(xpath = "//div[contains(text(),'Select All')]")
	private WebElement selectAllCountriesCheckBox;

	@FindBy(xpath = "(//label[contains(text(),'Event Class')]/ancestor::div/div/div/select)[last()-1]")
	private WebElement eventClassCodeDropDown;

	@FindBy(xpath = "//label[contains(text(),'Business Unit')]/ancestor::div/div[2]/div/select")
	private WebElement busUnitDropdown;

	@FindBy(xpath = "//label[contains(text(),'Transaction ID')]/ancestor::div/input")
	private WebElement transactionIDTextBox;

	@FindBy(xpath = "//button[contains(text(),'SEARCH')]")
	private WebElement searchButton;

	@FindBy(xpath = "//button[contains(text(),'CLEAR')]")
	private WebElement clearButton;

	@FindBy(xpath = "//div[@class='monitoring-header']")
	private WebElement monitoringHeader;

	@FindBy(xpath = "//div/h1[contains(text(),'Details')]")
	private WebElement monitoringDetails;

	@FindBy(xpath = "(//div[@col-id='companyCode'])[last()]")
	private WebElement monitoringHeaderContents;

	@FindBy(xpath = "(//div[@col-id='trxSource'])[last()]")
	private WebElement monitoringDetailsContents;

	@FindBy(xpath = "//label[@for='222']/span")
	private WebElement serviceSubscriptionAccountReceivableCheckBox;

	@FindBy(xpath = "//label[@for='200']/span")
	private WebElement serviceSubscriptionAccountPayableCheckBox;

	@FindBy(xpath = "//label[@for='201']/span")
	private WebElement serviceSubscriptionPurchasingCheckBox;

	@FindBy(xpath = "//label[@for='10046']/span")
	private WebElement serviceSubscriptionOrderManagementCheckBox;

	private By downloadLink = By.xpath(".//following-sibling::div/div/button[contains(text(), ' Download')]");

	/**
	 * click on the Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnAdvancedConfigDropdown( )
	{
		scroll.scrollBottom();
		expWait.until(ExpectedConditions.visibilityOf(advancedConfigurationLoc));
		click.clickElement(advancedConfigurationLoc);
	}

	/**
	 * click on the Retry Jobs tab from Monitoring dropdown
	 * in Taxlink Application
	 */
	public void clickOnRetryJobsTab( )
	{
		wait.waitForElementDisplayed(retryJobsButton);
		click.clickElement(retryJobsButton);
	}

	/**
	 * Navigate to Retry Jobs page in Taxlink UI
	 */
	public void navigateToRetryJobs( ) throws InterruptedException
	{
		jsWaiter.sleep(500);
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(500);
		clickOnMonitoringDropdown();
		jsWaiter.sleep(500);
		clickOnRetryJobsTab();
	}

	/**
	 * Verify title of Retry Jobs Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleRetryJobsPage( )
	{
		boolean flag;
		String retryJobsPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = retryJobsPageTitle.equalsIgnoreCase(Monitoring.MONITORING_DETAILS.headerRetryJobsPage);

		if ( verifyFlag )
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
	 * Verify Number of common records once a new instance has been onboarded
	 * in the Retry Jobs page
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean verifyCommonRecords( ) throws InterruptedException
	{
		boolean flag = false;
		navigateToRetryJobs();
		if ( verifyTitleRetryJobsPage() )
		{
			click.clickElement(enableRetryJobsButton);
			wait.waitForElementDisplayed(enableRetryJobsPopUp);
			if ( enableRetryJobsPopUp.isDisplayed() )
			{
				enableRetryJobsPopUp
					.getText()
					.equals("Do you want to Enable the Retry Jobs?");
				click.clickElement(yesButtonEnableRetryJobsPopUp);

				jsWaiter.sleep(5000);
				expWait.until(ExpectedConditions.textToBePresentInElement(summaryPageTitle, "Retry Jobs"));

				List<String> jobsPresentationList = new ArrayList<String>();
				for ( WebElement elements : jobNamePresentation )
				{
					jobsPresentationList.add(elements.getText());
				}

				List<String> jobsValidationList = new ArrayList<String>();
				jobsValidationList.add("Retry Error Documents");
				jobsValidationList.add("Retry Submit Error Documents");
				jobsValidationList.add("Retry Upload Error Documents");

				expWait.until(ExpectedConditions.visibilityOfAllElements(jobNamePresentation));
				int count = Integer.valueOf(totalPageCountSummaryTable.getText());
				for ( int i = 1 ; i <= count ; i++ )
				{
					Optional<WebElement> flagDataPresentFirstCommonJob = dataPresentInParticularColumn(
						jobNamePresentation, "Retry Error Documents");
					if ( flagDataPresentFirstCommonJob.isPresent() )
					{
						Optional<WebElement> flagDataPresentSecondCommonJob = dataPresentInParticularColumn(
							jobNamePresentation, "Retry Submit Error Documents");
						if ( flagDataPresentSecondCommonJob.isPresent() )
						{
							Optional<WebElement> flagDataPresentThirdCommonJob = dataPresentInParticularColumn(
								jobNamePresentation, "Retry Upload Error Documents");
							if ( flagDataPresentThirdCommonJob.isPresent() )
							{
								flag = true;
								VertexLogger.log("All the common jobs are present in the Job Name column");
								break;
							}
							else
							{
								htmlElement.sendKeys(Keys.END);
								jsWaiter.sleep(100);
								click.clickElement(nextArrowOnSummaryTable);
								Optional<WebElement> flagDataPresentThirdCommonJobNextPage
									= dataPresentInParticularColumn(jobNamePresentation,
									"Retry Upload Error Documents");
								if ( flagDataPresentThirdCommonJobNextPage.isPresent() )
								{
									flag = true;
									VertexLogger.log("All the common jobs are present in the Job Name column");
									break;
								}
							}
						}
						else
						{
							jsWaiter.sleep(1000);
							click.clickElement(nextArrowOnSummaryTable);
						}
					}
				}
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
	public Optional<WebElement> dataPresentInParticularColumn( List<WebElement> ele, String text )
	{
		jsWaiter.sleep(500);
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/*
	 * Select Countries for Business Unit on Add business unit page
	 */

	public void selectCountry( String selectCountryName )
	{
		wait.waitForElementDisplayed(buCountryNameSelectBox);
		click.moveToElementAndClick(buCountryNameSelectBox);
		wait.waitForElementDisplayed(buCountryNameTextBox);
		text.enterText(buCountryNameTextBox, selectCountryName);
		wait.waitForElementEnabled(buCountryNameCheckbox, 50);
		checkbox.setCheckbox(buCountryNameCheckbox, true);
		wait.waitForElementEnabled(savebuCountryName, 50);
		click.clickElement(savebuCountryName);
	}

	/**
	 * Save the Business Unit on Add business unit page
	 */
	public void saveBusinessUnit( )
	{
		expWait.until(ExpectedConditions.visibilityOf(saveBusinessUnit));
		click.clickElement(saveBusinessUnit);
	}

	/**
	 * Verify job is present for newly added BU record
	 * in the Retry Jobs page
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean verifyRecordsAfterAddingBu( )
	{
		boolean flag = false;
		boolean flag1;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		String bUID;
		String bUName;
		navigateToBusinessUnit();
		wait.waitForElementEnabled(addBusinessUnit);
		click.clickElement(addBusinessUnit);
		expWait.until(ExpectedConditions.visibilityOf(addBusinessUnitID));
		bUID = utils.randomNumber("5");
		wait.waitForElementDisplayed(addBusinessUnitID);
		text.enterText(addBusinessUnitID, bUID);
		bUName = utils.randomText();
		wait.waitForElementDisplayed(addBusinessUnitName);
		text.enterText(addBusinessUnitName, bUName);
		selectCountry(BusinessUnit.BU_DETAILS.selectCountryName);
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(saveBusinessUnit);
		saveBusinessUnit();
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(summaryPageTitle);
		List<String> dataEntered = Arrays.asList(bUID, bUName);
		VertexLogger.log("New Business Unit added : " + dataEntered);

		jsWaiter.sleep(500);
		htmlElement.sendKeys(Keys.END);
		click.clickElement(retryJobsButton);
		jsWaiter.sleep(5000);
		verifyTitleRetryJobsPage();
		click.clickElement(enableRetryJobsButton);
		expWait.until(ExpectedConditions.visibilityOf(enableRetryJobsButton));

		if ( enableRetryJobsPopUp.isDisplayed() )
		{
			enableRetryJobsPopUp
				.getText()
				.equals("Do you want to Enable the Retry Jobs?");
			click.clickElement(yesButtonEnableRetryJobsPopUp);
			expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

			int count = Integer.valueOf(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresent(bUName);

				if ( data.isPresent() )
				{
					jsWaiter.sleep(5000);
					flag1 = jobNamePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(Monitoring.MONITORING_DETAILS.jobNamePTDEResetRetryJobsRecord));
					VertexLogger.log(String.valueOf(flag1));
					wait.waitForElementDisplayed(summaryPageTitle);
					jsWaiter.sleep(1000);
					flag2 = serviceDescPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(Monitoring.MONITORING_DETAILS.serviceDescRetryJobsRecord));
					VertexLogger.log(String.valueOf(flag2));
					if ( serviceSubscriptionPresentation
						.stream()
						.anyMatch(col -> col
											 .getText()
											 .equals(Monitoring.MONITORING_DETAILS.serviceSubAPRetryJobsRecord) || col
											 .getText()
											 .equals(Monitoring.MONITORING_DETAILS.serviceSubARRetryJobsRecord)) )
					{
						flag3 = true;
					}
					else
					{
						flag3 = false;
					}
					VertexLogger.log(String.valueOf(flag3));
					flag4 = schedulePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(Monitoring.MONITORING_DETAILS.scheduleRetryJobsRecord));
					VertexLogger.log(String.valueOf(flag4));

					if ( flag1 && flag2 && flag3 && flag4 )
					{
						flag = true;
						break;
					}
					else
					{
						flag = false;
					}
				}
				else
				{
					jsWaiter.sleep(10000);
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		return flag;
	}

	/**
	 * Method to search data in summary table of Retry Jobs page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		jsWaiter.sleep(500);
		Optional<WebElement> dataFound = buNamePresentation
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Verify title of Polling Jobs Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitlePollingJobsPage( )
	{
		boolean flag;
		String pollingJobsPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = pollingJobsPageTitle.equalsIgnoreCase(Monitoring.MONITORING_DETAILS.headerPollingJobsPage);

		if ( verifyFlag )
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
	 * Verify Edit Instance page to check if AR AND AP checkboxes
	 * of service subscription have been checked in the edit instance test
	 * if not then select it otherwise verify it in Taxlink application
	 *
	 * @return flag
	 */
	public boolean verifyEditInstanceForPollingJobs( )
	{
		boolean finalFlag = false;
		boolean isDisabledARFlag = false;
		boolean isDisabledAPFlag = false;

		navigateToInstancePage();
		editButton.click();
		wait.waitForElementDisplayed(addViewEditPageTitle);
		if ( verifyTitleEditInstancePage() )
		{
			String isDisabledAR = serviceSubscriptionAccountReceivableCheckBox.getAttribute("disabled");
			if ( isDisabledAR == null || !isDisabledAR.equals("disabled") )
			{
				isDisabledARFlag = true;
			}
			String isDisabledAP = serviceSubscriptionAccountPayableCheckBox.getAttribute("disabled");
			if ( isDisabledAP == null || !isDisabledAP.equals("disabled") )
			{
				isDisabledAPFlag = true;
			}

			htmlElement.sendKeys(Keys.END);
			click.clickElement(cancelButton);

			if ( isDisabledARFlag && isDisabledAPFlag )
			{
				finalFlag = true;
			}
		}
		return finalFlag;
	}

	/**
	 * Verify title of Edit Instance Page in Taxlink application
	 *
	 * @return
	 */
	public boolean verifyTitleEditInstancePage( )
	{
		boolean flag;
		String editInstanceTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = editInstanceTitle.equalsIgnoreCase(INSTANCE_DETAILS.headerEditInstancePage);
		if ( verifyFlag )
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
	 * Verify View polling jobs functionality on Polling Jobs Page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean viewPollingJobs( ) throws InterruptedException
	{
		boolean flag = false;
		clickOnPollingJobsTab();
		if ( verifyTitlePollingJobsPage() )
		{
			List<String> jobsPresentationList = new ArrayList<String>();
			for ( WebElement elements : jobNamePresentation )
			{
				jobsPresentationList.add(elements.getText());
			}

			List<String> jobsValidationARList = new ArrayList<String>();
			jobsValidationARList.add("AR Rejection polling job");
			jobsValidationARList.add("AR Sync polling job");

			Optional<WebElement> flagDataPresentSecondARJob = dataPresentInParticularColumn(jobNamePresentation,
				"AR Rejection polling job");
			if ( flagDataPresentSecondARJob.isPresent() )
			{
				Optional<WebElement> flagDataPresentThirdARJob = dataPresentInParticularColumn(jobNamePresentation,
					"AR Sync polling job");
				if ( flagDataPresentThirdARJob.isPresent() )
				{
					flag = true;
					VertexLogger.log(
						"All the three jobs are present for AR service description in the Job Name column");
				}
			}
			Optional<WebElement> flagDataPresentSecondAPJob = dataPresentInParticularColumn(jobNamePresentation,
				"AP Rejection polling job");
			if ( flagDataPresentSecondAPJob.isPresent() )
			{
				Optional<WebElement> flagDataPresentThirdAPJob = dataPresentInParticularColumn(jobNamePresentation,
					"AP Sync polling job");
				if ( flagDataPresentThirdAPJob.isPresent() )
				{
					flag = true;
					VertexLogger.log(
						"All the three jobs are present for AP service description in the Job Name column");
				}
			}
		}
		return flag;
	}

	/**
	 * Verify transaction monitoring status functionality
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean transactionMonitoringStatus( String eventClassCode, String busUnit, String transactionID )
		throws InterruptedException
	{
		boolean clearFieldsFlag = false, detailsPresentFlag = false, finalFlag = false;
		navigateToTransMonitoringTab();
		if ( verifyTitleTransactionMonitoringPage() )
		{
			jsWaiter.sleep(5000);
			dropdown.selectDropdownByDisplayName(eventClassCodeDropDown, eventClassCode);
			dropdown.selectDropdownByDisplayName(busUnitDropdown, busUnit);
			text.enterText(transactionIDTextBox, transactionID);
			click.clickElement(clearButton);

			jsWaiter.sleep(5000);

			WebElement eventClassCodeVal = dropdown.getDropdownSelectedOption(eventClassCodeDropDown);
			String eventCode = eventClassCodeVal.getText();

			WebElement buVal = dropdown.getDropdownSelectedOption(busUnitDropdown);
			String busUnitVal = buVal.getText();

			String transIDVal = transactionIDTextBox.getText();

			if ( (eventCode.isBlank()) && busUnitVal.isBlank() && transIDVal.isBlank() )
			{
				clearFieldsFlag = true;
			}

			dropdown.selectDropdownByDisplayName(eventClassCodeDropDown, eventClassCode);
			dropdown.selectDropdownByDisplayName(busUnitDropdown, busUnit);
			text.enterText(transactionIDTextBox, transactionID);
			click.clickElement(searchButton);

			jsWaiter.sleep(5000);
			if ( monitoringHeader.isDisplayed() && monitoringDetails.isDisplayed() )
			{
				if ( (!Objects.equals(monitoringHeaderContents.getText(), "No Rows To Show")) && (!Objects.equals(
					monitoringDetailsContents.getText(), "No Rows To Show")) && (!Objects.nonNull(
					monitoringHeaderContents.getText())) && (!Objects.nonNull(monitoringDetailsContents.getText())) )
				{
					detailsPresentFlag = true;
				}
			}
		}
		if ( clearFieldsFlag && detailsPresentFlag )

		{
			VertexLogger.log("Transaction monitoring header and details are displayed on the Search!!");
			finalFlag = true;
		}
		return finalFlag;
	}

	/**
	 * Verify title of Transaction Monitoring Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleTransactionMonitoringPage( )
	{
		boolean flag;
		String transMonitoringPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = transMonitoringPageTitle.equalsIgnoreCase(
			Monitoring.MONITORING_DETAILS.headerTransMonitoringPage);

		if ( verifyFlag )
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
	 * Verify title of Summary Reports Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleSummaryReportsPage( )
	{
		boolean flag;
		String summaryReportsPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = summaryReportsPageTitle.equalsIgnoreCase(
			Monitoring.MONITORING_DETAILS.headerSummaryReportsPage);

		if ( verifyFlag )
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
	 * Verify Export to CSV functionality on Retry Jobs Page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVRetryJobs( String instName ) throws IOException, InterruptedException
	{
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;

		String fileName = "Retry_Jobs_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToRetryJobs();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(enableRetryJobsButton);
		jsWaiter.sleep(500);
		if ( enableRetryJobsPopUp.isDisplayed() )
		{
			enableRetryJobsPopUp
				.getText()
				.equals("Do you want to Enable the Retry Jobs?");
			click.clickElement(yesButtonEnableRetryJobsPopUp);
			expWait.until(ExpectedConditions.visibilityOfAllElements(summaryPageTitle));

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String name = csvRecord.get(0);
					if ( name.contains(instName) )
					{
						VertexLogger.log("Fusion Instance Name : " + name);
						flag = true;
						break;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("Job Name", "Service Description", "Business Unit Name", "Service Subscription",
						"Schedule", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String jobName_CSV = csvRecord.get("Job Name");
					String serviceDescription_CSV = csvRecord.get("Service Description");
					String businessUnitName_CSV = csvRecord.get("Business Unit Name");
					String serviceSubscription_CSV = csvRecord.get("Service Subscription");
					String schedule_CSV = csvRecord.get("Schedule");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Job Name : " + jobName_CSV);
					VertexLogger.log("Service Description : " + serviceDescription_CSV);
					VertexLogger.log("Business Unit Name : " + businessUnitName_CSV);
					VertexLogger.log("Service Subscription : " + serviceSubscription_CSV);
					VertexLogger.log("Schedule : " + schedule_CSV);
					VertexLogger.log("Enabled : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !businessUnitName_CSV.equals("Business Unit Name") )
					{
						Optional dataBUName = dataPresentInParticularColumn(buNamePresentation, businessUnitName_CSV);
						if ( dataBUName.isPresent() )
						{
							Optional dataServDesc = dataPresentInParticularColumn(serviceDescPresentation,
								serviceDescription_CSV);
							flag1 = dataServDesc.isPresent();
							VertexLogger.log("" + flag1);
							Optional dataJobName = dataPresentInParticularColumn(jobNamePresentation, jobName_CSV);
							flag2 = dataJobName.isPresent();
							VertexLogger.log("" + flag2);
							Optional dataServSub = dataPresentInParticularColumn(serviceSubscriptionPresentation,
								serviceSubscription_CSV);
							flag3 = dataServSub.isPresent();
							VertexLogger.log("" + flag3);
							Optional dataSchedule = dataPresentInParticularColumn(schedulePresentation, schedule_CSV);
							flag4 = dataSchedule.isPresent();
							VertexLogger.log("" + flag4);
							Optional dataEnabled = dataPresentInParticularColumn(enabledPresentation, enabled_CSV);
							flag5 = dataEnabled.isPresent();
							VertexLogger.log("" + flag5);
						}
						else
						{
							htmlElement.sendKeys(Keys.END);
							jsWaiter.sleep(100);
							click.clickElement(nextArrowOnSummaryTable);
						}
					}
				}
				if ( flag1 && flag2 && flag3 && flag4 && flag5 )
				{
					flag = true;
				}
				else
				{
					flag = false;
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( file.delete() )
				{
					VertexLogger.log("File deleted successfully");
				}
				else
				{
					VertexLogger.log("Failed to delete the file");
				}
			}
		}
		return flag;
	}

	/**
	 * Verify Export to CSV functionality on Polling Jobs Page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVPollingJobs( String instName ) throws IOException, InterruptedException
	{
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;

		String fileName = "Polling_Jobs_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToPollingJobs();
		verifyTitlePollingJobsPage();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		exportToCSVSummaryPage.click();

		FluentWait<WebDriver> wait = new FluentWait<>(driver);
		wait
			.pollingEvery(Duration.ofSeconds(1))
			.withTimeout(Duration.ofSeconds(15))
			.until(x -> file.exists());

		try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
		{
			for ( CSVRecord csvRecord : csvParser )
			{
				String name = csvRecord.get(0);
				if ( name.contains(instName) )
				{
					VertexLogger.log("Fusion Instance Name : " + name);
					flag = true;
					break;
				}
			}
		}

		try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withHeader("Job Name", "Fusion Instance Name", "Service Description", "Doc Type",
					"Subscription Service", "Schedule", "Enabled")
				.withTrim()) ; )
		{

			for ( CSVRecord csvRecord : csvParser )
			{
				String jobName_CSV = csvRecord.get("Job Name");
				String fusionInstanceName_CSV = csvRecord.get("Fusion Instance Name");
				String serviceDescription_CSV = csvRecord.get("Service Description");
				String docType_CSV = csvRecord.get("Doc Type");
				String serviceSubscription_CSV = csvRecord.get("Subscription Service");
				String schedule_CSV = csvRecord.get("Schedule");
				String enabled_CSV = csvRecord.get("Enabled");

				VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
				VertexLogger.log("---------------");
				VertexLogger.log("Job Name : " + jobName_CSV);
				VertexLogger.log("Fusion Instance Name : " + fusionInstanceName_CSV);
				VertexLogger.log("Service Description : " + serviceDescription_CSV);
				VertexLogger.log("Doc Type : " + docType_CSV);
				VertexLogger.log("Subscription Service : " + serviceSubscription_CSV);
				VertexLogger.log("Schedule : " + schedule_CSV);
				VertexLogger.log("Enabled : " + enabled_CSV);
				VertexLogger.log("---------------\n\n");

				Optional dataJobName = dataPresentInParticularColumn(jobNamePresentation, jobName_CSV);
				if ( dataJobName.isPresent() )
				{
					Optional dataServDesc = dataPresentInParticularColumn(serviceDescPresentation,
						serviceDescription_CSV);
					flag1 = dataServDesc.isPresent();
					VertexLogger.log("" + flag1);
					Optional dataDocType = dataPresentInParticularColumn(docTypePresentationPollingJobs, docType_CSV);
					flag2 = dataDocType.isPresent();
					VertexLogger.log("" + flag2);
					Optional dataServSub = dataPresentInParticularColumn(serviceSubscriptionPresentationPollingJobs,
						serviceSubscription_CSV);
					flag3 = dataServSub.isPresent();
					VertexLogger.log("" + flag3);
					Optional dataSchedule = dataPresentInParticularColumn(schedulePresentation, schedule_CSV);
					flag4 = dataSchedule.isPresent();
					VertexLogger.log("" + flag4);
					Optional dataEnabled = dataPresentInParticularColumn(enabledPresentationPollingJobs, enabled_CSV);
					flag5 = dataEnabled.isPresent();
					VertexLogger.log("" + flag5);
				}
			}
			if ( flag1 && flag2 && flag3 && flag4 && flag5 )
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( file.delete() )
			{
				VertexLogger.log("File deleted successfully");
			}
			else
			{
				VertexLogger.log("Failed to delete the file");
			}
		}
		return flag;
	}

	/**
	 * Verify View summary report functionality under Monitoring Section
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean viewSummaryReport( String instname ) throws IOException, InterruptedException
	{
		boolean flag = false;
		navigateToSummaryReports();
		if ( verifyTitleSummaryReportsPage() )
		{
			int count = summaryReportsNamePresentation.size();
			for ( int i = 0 ; i < count ; i++ )
			{
				VertexLogger.log("Summary Report : " + summaryReportsNamePresentation
					.get(i)
					.getText());
				summaryReportsNamePresentation
					.get(i)
					.findElement(downloadLink)
					.click();
				String instanceName = instname;
				String fileName = instanceName + "_report.csv";
				VertexLogger.log(fileName);

				String fileDownloadPath = String.valueOf(getFileDownloadPath());
				File file = new File(fileDownloadPath + File.separator + fileName);
				VertexLogger.log(String.valueOf(file));

				jsWaiter.sleep(5000);

				try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
					CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						.withFirstRecordAsHeader()
						.withQuote(null)
						.withHeader("Fusion_Instance_Id", "Fusion_Instance_Name", "Instance_Usage")
						.withTrim()) ; )
				{

					for ( CSVRecord csvRecord : csvParser )
					{
						String jobName_CSV = csvRecord.get("Fusion_Instance_Id");
						String fusionInstanceName_CSV = csvRecord.get("Fusion_Instance_Name");
						String serviceDescription_CSV = csvRecord.get("Instance_Usage");

						VertexLogger.log("---------------");
						VertexLogger.log("Fusion_Instance_Id : " + jobName_CSV);
						VertexLogger.log("Fusion_Instance_Name : " + fusionInstanceName_CSV);
						VertexLogger.log("Instance_Usage : " + serviceDescription_CSV);
						VertexLogger.log("---------------\n\n");
						if ( fusionInstanceName_CSV.equals(instname) )
						{
							flag = true;
							break;
						}
					}
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
				finally
				{
					if ( file.delete() )
					{
						VertexLogger.log("File deleted successfully");
					}
					else
					{
						VertexLogger.log("Failed to delete the file");
					}
				}
			}
		}
		return flag;
	}
}