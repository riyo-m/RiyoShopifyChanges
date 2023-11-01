package com.vertex.quality.connectors.taxlink.ui.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.enums.ArTransactionSource;
import com.vertex.quality.connectors.taxlink.ui.enums.BusinessUnit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard.INSTANCE_DETAILS;

/**
 * This class contains constructor for TaxLinkBasePage for TaxLink UI
 *
 * @author mgaikwad, Shilpi.Verma
 */

public class TaxLinkBasePage extends VertexPage
{
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();

	String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";
	Path filePath = Paths.get(this.rootPath.toString() + textFilePath);

	public WebDriverWait expWait = new WebDriverWait(driver, 150);
	public Actions actionPageDown = new Actions(driver);
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();
	public JavascriptExecutor js = (JavascriptExecutor) driver;

	public Wait<WebDriver> fluentWait = new FluentWait<>(driver)
		.withTimeout(Duration.ofSeconds(30L))
		.pollingEvery(Duration.ofSeconds(5L))
		.ignoring(NoSuchElementException.class);

	//common locators across all the page classes

	@FindBy(xpath = "//a[@data-cy='dashboard']")
	protected WebElement dashboardMenuLoc;

	@FindBy(xpath = "//a[@data-cy='taxCalculationSetups']")
	protected WebElement taxCalculationSetUpsLoc;

	@FindBy(xpath = "//span[text()= 'Procurement']")
	protected WebElement procurementTab;

	@FindBy(xpath = "//a[contains(text(),'Advanced Configuration')]")
	protected WebElement advancedConfigurationLoc;

	@FindBy(xpath = "//a[contains(text(),'Security')]")
	protected WebElement securityLoc;
	@FindBy(xpath = "//a[@data-cy='customerOnboarding-module']")
	protected WebElement onboardingDashboardButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/businessUnits']")
	protected WebElement businessUnitButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/legalEntity']")
	protected WebElement legalEntityTab;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/APInvoceSource']")
	protected WebElement apInvoiceSourceTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/arBatchSource']")
	protected WebElement arTransactionSourceButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/aRTransactionType']")
	protected WebElement arTransactionTypeButton;

	@FindBy(xpath = "//a[@href= '/vertex-tl-web-ui/SupplierType']")
	private WebElement supplierTypeTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/offlineExtractJob']")
	protected WebElement offlineBIPExtractJobs;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/realTimeExtractJobs']")
	protected WebElement realTimeBIPExtractJob;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/profiles']")
	protected WebElement userProfileOptionsTab;

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[last()-4]")
	protected WebElement rulesMappingTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/rules']")
	protected WebElement preRulesButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/postRules']")
	protected WebElement postRulesButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/conditionSets']")
	protected WebElement conditionSetsButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/defaultRulesMapping']")
	protected WebElement defaultMappingButton;

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[last()-3]")
	protected WebElement invRulesMappingTab;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryRules']")
	protected WebElement invPreRulesButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryJournalOutputFile']")
	protected WebElement invJournalOutputButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryProjectOutputFile']")
	protected WebElement invProjectOutputButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryConditionSets']")
	protected WebElement invConditionSetsButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/arTaxExclusion']")
	protected WebElement arTaxCalcExclusionsTabLoc;

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[2]")
	protected WebElement suppliesTabLoc;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/lookupCodeOCR']")
	protected WebElement apScanInvSrcTab;

	@FindBy(xpath = "//a[@href = '/vertex-tl-web-ui/lookupCodeAPSourceAssume']")
	protected WebElement apAssumeTaxSrcTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/taxAction']")
	protected WebElement apTaxActRangesTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/taxOverride']")
	protected WebElement apTaxActOverrideTab;

	@FindBy(xpath = "//a[@href = '/vertex-tl-web-ui/taxExclusion']")
	protected WebElement apTaxCalcExclTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/POTaxExclusion']")
	protected WebElement poTaxCalcExclTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/lookups']")
	protected WebElement lookupsLink;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/batchJobScheduleInfo']")
	protected WebElement pollingJobsButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/summaryReport']")
	protected WebElement summaryReportsTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/transactionMonitoring']")
	protected WebElement transMonitoringTab;
	@FindBy(xpath = "//span[contains(text(),'Monitoring')]")
	protected WebElement monitoringDropdown;
	@FindBy(xpath = "//a[contains(text(), 'Cleanup Instance')]")
	protected WebElement cleanUpInstanceLink;
	@FindBy(xpath = "//a[contains(text(), 'Clone Instance')]")
	protected WebElement cloneInstanceLink;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/users']")
	protected WebElement usersLink;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/roles']")
	protected WebElement rolesTab;

	@FindBy(xpath = "//a[contains(text(),'Generate New Password')]")
	protected WebElement generateNewPwdLink;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/logout']")
	protected WebElement logout;

	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement loginButton;

	@FindBy(tagName = "h1")
	protected WebElement summaryPageTitle;

	@FindBy(tagName = "h3")
	protected WebElement addViewEditPageTitle;

	@FindBy(tagName = "html")
	protected WebElement htmlElement;

	@FindBy(xpath = "//button[contains(text(),'Next')]")
	protected WebElement nextArrowOnSummaryTable;

	@FindBy(xpath = "//span[contains(@ref,'lbTotal')]")
	protected WebElement totalPageCountSummaryTable;

	@FindBy(xpath = "//button[contains(text(),'Export to CSV')]")
	public WebElement exportToCSVSummaryPage;

	@FindBy(xpath = "//button[contains(text(),'IMPORT')]")
	protected WebElement importButtonOnSummaryPage;

	@FindBy(xpath = "//button[contains(text(), 'SELECT')]")
	protected WebElement selectImportButton;

	@FindBy(xpath = "(//div/div[1]/div/div[1]/div[2]/div/div/div[1]/div[2]/div/div/span)[last()]")
	protected WebElement importSelectAllCheckBox;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
	protected List<WebElement> importListCheckBox;

	@FindBy(xpath = "//h4[contains(text(),'Are you sure')]/ancestor::div[2]")
	protected WebElement popUpForImport;

	@FindBy(xpath = "//button[@class='btn btn-sm btn--primary btn-colored undefined']")
	protected WebElement importButtonPopUp;

	@FindBy(xpath = "//button[@class='btn btn-sm btn--primary btn-colored smallButtonSaveCancel']")
	protected WebElement savePopUpImportButton;

	@FindBy(xpath = "//button[@class='table-flyout__btn threeDot']")
	protected WebElement actions;

	@FindBy(xpath = "//button[contains(text(), 'ADD')]")
	protected WebElement addButton;

	@FindBy(xpath = "//button[contains(text(), 'Edit')]")
	protected WebElement editButton;

	@FindBy(xpath = "//button[contains(text(), 'View')]")
	protected WebElement viewButton;

	@FindBy(xpath = "//button[contains(text(), 'Close')]")
	protected WebElement closeButton;

	@FindBy(xpath = "//input[@name='enabledFlag']")
	protected WebElement enabledFlag;

	@FindBy(xpath = "//label[@for= 'startDate']/following-sibling::div/div/input")
	protected WebElement startDate;

	@FindBy(xpath = "//label[@for= 'endDate']/following-sibling::div/div/input")
	protected WebElement endDate;

	@FindBy(xpath = "//button[contains(text(), 'Save')]")
	protected WebElement saveButton;

	@FindBy(xpath = "//button[contains(text(), 'Cancel')]")
	protected WebElement cancelButton;

	@FindBy(xpath = "//button[contains(text(),'First')]")
	protected WebElement firstPage;

	@FindBy(xpath = "//span[@ref='lbCurrent']")
	protected WebElement currentPageCount;

	@FindBy(xpath = "//a[@data-cy= 'accessType']/span/span")
	protected WebElement accessTypeSelected;

	@FindBy(xpath = "//div[@role='menuitem']/div/div/label[text() = 'System']")
	protected WebElement accessType;

	@FindBy(xpath = "//a[@data-cy= 'accessType']/span[@class= 'rs-picker-toggle-caret']")
	protected WebElement accessTypeDrpdwn;

	@FindBy(xpath = "//select[@class='external-filter-select']")
	protected WebElement externalFilter;

	@FindBy(xpath = "//span[contains(text(), 'No Rows To Show')]")
	protected WebElement blankTable;
	@FindBy(xpath = "//button[contains(text(), 'Add/Edit')]")
	protected WebElement addEditButton;
	@FindBy(xpath = "//div[contains(@class,'modalCustomeLebel modalShow2')]")
	protected WebElement addEditPopUp;
	@FindBy(xpath = "//button[contains(text(), 'Expand')]")
	protected WebElement expandAllLinkOnPopUp;
	@FindBy(xpath = "//ul[@id='FI_ADMIN']")
	protected WebElement instancesFIADMINOnPopUp;

	@FindBy(xpath = "//ul[@id='USER']")
	protected WebElement instancesUSERSOnPopUp;

	@FindBy(xpath = "//h4[contains(text(),'Are you sure')]/parent::div/parent::div")
	protected WebElement popUpMessage;

	@FindBy(xpath = "//button[contains(text(), 'SAVE')]")
	protected WebElement savePopUpMessageButton;

	@FindBy(xpath = "//div[@col-id='enabledFlag']")
	protected List<WebElement> enabledFlagPresentation;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='username']")
	protected List<WebElement> userNamePresentation;

	@FindBy(xpath = "//button[contains(text(), 'Next')][1]")
	protected WebElement nextArrow;

	protected By listInstances = By.xpath(".//li");
	protected By editButtonUsers = By.xpath(".//following-sibling::div[3]/div/div/div/div/div/button");

	/**
	 * Constructor of TaxLinkBasePage class
	 * inheriting properties of VertexPage
	 */
	public TaxLinkBasePage( WebDriver driver )
	{
		super(driver);
		this.driver = driver;
	}

	/**
	 * Method to get the file download path
	 */
	public Path getFileDownloadPath( )
	{
		Path resourcePath = this.rootPath;
		String csvDir = File.separator + "resources" + File.separator + "csvfiles" + File.separator + "taxlink";
		resourcePath = Paths.get(resourcePath.toString() + csvDir);
		VertexLogger.log(String.valueOf(resourcePath));

		return resourcePath;
	}

	/**
	 * Method to click on Export to CSV button and apply Fluent Wait
	 *
	 * @param file
	 */
	public void setFluentWait( File file )
	{
		click.clickElement(exportToCSVSummaryPage);
		FluentWait<WebDriver> wait = new FluentWait<>(driver);
		wait
			.pollingEvery(Duration.ofSeconds(1))
			.withTimeout(Duration.ofSeconds(15))
			.until(x -> file.exists());
	}

	/**
	 * Method to parse CSV record
	 *
	 * @param file
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public List<CSVRecord> parseCSVRecord( File file ) throws Exception
	{
		Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
		VertexLogger.log("File downloaded");

		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(','));
		List<CSVRecord> records = csvParser.getRecords();

		return records;
	}

	/**
	 * Method to verify first record as header in CSV records
	 *
	 * @param records
	 * @param s1
	 *
	 * @return
	 */
	public boolean checkHeader( List<CSVRecord> records, String s1 )
	{
		boolean header_flag = records
			.stream()
			.anyMatch(header -> header
				.get(0)
				.contains(s1));

		return header_flag;
	}

	/**
	 * Method to select access type
	 */
	public void selectAccessType( )
	{
		VertexLogger.log("Current Access Type is: " + accessTypeSelected.getText());
		click.clickElement(accessTypeDrpdwn);
		wait.waitForElementDisplayed(accessType);
		click.clickElement(accessType);
		wait.waitForElementDisplayed(accessTypeSelected);

		String accType = accessTypeSelected.getText();
		VertexLogger.log("Access Type changed to: " + accType + " " + accType.equals("User , System"));
	}

	/**
	 * This method is to check if first page arrow is enabled for navigation
	 * if not, do not click on the arrow
	 */
	public void checkPageNavigation( )
	{
		try
		{
			if ( firstPage.isEnabled() )
			{
				click.clickElement(firstPage);
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Record is present in first page of summary table");
		}
	}

	/**
	 * This method is to check if no rows are present in summary table
	 * if not, return result
	 */
	public boolean checkNoRecordsPresent( )
	{
		boolean final_flag = false;
		try
		{
			wait.waitForElementDisplayed(summaryPageTitle);
			if ( blankTable.isDisplayed() )
			{
				VertexLogger.log("No rows are present on summary table");
				final_flag = true;
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Records are present on summary table");
		}
		return final_flag;
	}

	/**
	 * Method to create a text file, if doesn't exist already
	 * and write data into it
	 *
	 * @param text
	 */
	public void writeToFile( String text ) throws IOException
	{

		Path rootPath = Paths
			.get("")
			.toAbsolutePath();

		String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";

		Path filePath = Paths.get(rootPath.toString() + textFilePath);
		Path fileCreatePath = filePath.resolve("taxlink.txt");
		VertexLogger.log("File created in path: " + fileCreatePath);

		try
		{
			if ( !Files.exists(fileCreatePath, LinkOption.NOFOLLOW_LINKS) )
			{
				Files.createFile(fileCreatePath);
				Files.write(fileCreatePath, text.getBytes());
			}
			else
			{
				Files.write(fileCreatePath, text.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			}
		}
		catch ( FileAlreadyExistsException ignored )
		{
		}
	}

	/**
	 * Method to Read file on the given path and return
	 * String text from file amd the Path of the file
	 *
	 * @return
	 */
	public List<Object> getFileReadPath( )
	{
		Path fileCreatePath = filePath.resolve("taxlink.txt");

		String textRead = null;
		try
		{
			textRead = Files
				.lines(fileCreatePath)
				.collect(Collectors.joining(System.lineSeparator()));
			VertexLogger.log("Reading Text: " + textRead);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		List<Object> fileDetails = Arrays.asList(textRead, fileCreatePath);
		return fileDetails;
	}

	/**
	 * Method to delete file on the given path
	 */
	public void deleteFile( ) throws IOException
	{
		Path filePathToDelete = Paths.get(filePath + File.separator + "taxlink.txt");
		Files.delete(filePathToDelete);
		VertexLogger.log("The text file has been deleted!!");
	}

	/**
	 * Remove FI_ADMIN role for an instance
	 * and assign Users role
	 *
	 * @param instance
	 *
	 * @return boolean
	 */
	public boolean removeRoleFromAnInstance( String instance )
	{
		boolean roleFIADMINChangeFlag = false, roleUserFlag = false, roleChangeFlag = false;
		jsWaiter.sleep(5000);
		click.clickElement(addEditButton);
		if ( addEditPopUp.isDisplayed() )
		{
			click.clickElement(expandAllLinkOnPopUp);
			if ( instancesFIADMINOnPopUp.isDisplayed() && instancesUSERSOnPopUp.isDisplayed() )
			{
				List<WebElement> optionsFIADMIN = instancesFIADMINOnPopUp.findElements(listInstances);
				for ( WebElement option : optionsFIADMIN )
				{
					VertexLogger.log(option.getText());
					if ( option
						.getText()
						.equals(instance) )
					{
						jsWaiter.sleep(2000);
						if ( option
							.findElement(By.xpath(".//span/input"))
							.getAttribute("name")
							.contains(instance) )
						{
							click.clickElement(option.findElement(By.xpath(".//span/input")));
							roleFIADMINChangeFlag = true;
							break;
						}
					}
				}
				List<WebElement> optionsUSERS = instancesUSERSOnPopUp.findElements(listInstances);
				for ( WebElement option : optionsUSERS )
				{
					if ( option
						.getText()
						.equals(instance) )
					{
						jsWaiter.sleep(2000);
						if ( option
							.findElement(By.xpath(".//span/input"))
							.getAttribute("name")
							.contains(instance) )
						{
							click.clickElement(option.findElement(By.xpath(".//span/input")));
							jsWaiter.sleep(5000);
							roleUserFlag = true;
							break;
						}
					}
				}
				jsWaiter.sleep(2000);
				click.clickElement(saveButton);
				expWait.until(ExpectedConditions.textToBePresentInElement(popUpMessage,
					"Are you sure you want to modify User roles"));

				click.clickElement(savePopUpMessageButton);
				VertexLogger.log("Unassigned FI_ADMIN and assigned Users role to " + instance);
				jsWaiter.sleep(5000);
				js.executeScript("arguments[0].scrollIntoView();", closeButton);
				click.moveToElementAndClick(closeButton);
				wait.waitForElementDisplayed(summaryPageTitle);
			}
			if ( roleFIADMINChangeFlag && roleUserFlag )
			{
				roleChangeFlag = true;
			}
		}
		return roleChangeFlag;
	}

	/**
	 * method to be called for each test as a pre-requisite
	 */

	public void navigateToInstancePage( )
	{
		clickOnDashboardDropdown();
		clickOnOnboardingDashboard();
	}

	/**
	 * click on Dashboard Dropdown in Taxlink application
	 */
	public void clickOnDashboardDropdown( )
	{
		wait.waitForElementEnabled(dashboardMenuLoc);
		click.moveToElementAndClick(dashboardMenuLoc);
	}

	/**
	 * click on Onboarding Dashboard Button
	 */

	public void clickOnOnboardingDashboard( )
	{
		expWait
			.until(ExpectedConditions.elementToBeClickable(onboardingDashboardButton))
			.click();
		String onboardingDashboardTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		onboardingDashboardTitle.equalsIgnoreCase(INSTANCE_DETAILS.headerOnboardingDashboardPage);
	}

	/**
	 * Navigate to Business Unit in Taxlink UI
	 */

	public void navigateToBusinessUnit( )
	{
		clickOnDashboardDropdown();
		clickOnBusinessUnit();
	}

	/**
	 * click on Business Unit Button
	 */
	public void clickOnBusinessUnit( )
	{
		WebDriverWait expWait = new WebDriverWait(driver, 30);
		expWait.until(ExpectedConditions.visibilityOf(businessUnitButton));
		click.clickElement(businessUnitButton);
		String bUTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		bUTitle.equalsIgnoreCase(BusinessUnit.BU_DETAILS.headerBusinessUnitPage);
	}

	/**
	 * Method for navigation to Legal Entity tab
	 */
	public void navigateToLegalEntity( )
	{
		clickOnDashboardDropdown();
		wait.waitForElementDisplayed(legalEntityTab);
		click.clickElement(legalEntityTab);
	}

	/**
	 * Method for navigation to AP Invoice Source tab
	 */
	public void navigateToAPInvoiceSource( )
	{
		clickOnDashboardDropdown();
		wait.waitForElementDisplayed(apInvoiceSourceTab);
		click.clickElement(apInvoiceSourceTab);
	}

	/**
	 * click on the AR Transaction Source button from Dashboard dropdown
	 * in Taxlink Application
	 */
	public void clickOnArTransactionSource( )
	{
		expWait.until(ExpectedConditions.visibilityOf(arTransactionSourceButton));
		click.clickElement(arTransactionSourceButton);
		String arTransactionSourceTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		arTransactionSourceTitle.equalsIgnoreCase(
			ArTransactionSource.ArTransactionSourceDetails.headerArTransactionSourcePage);
	}

	/**
	 * Navigate to AR Transaction Source in Taxlink UI
	 */

	public void navigateToArTransactionSource( )
	{
		clickOnDashboardDropdown();
		clickOnArTransactionSource();
	}

	/**
	 * click on the AR Transaction Type button from Dashboard dropdown
	 * in Taxlink Application
	 */
	public void clickOnArTransType( )
	{
		expWait.until(ExpectedConditions.visibilityOf(arTransactionTypeButton));
		click.clickElement(arTransactionTypeButton);
	}

	/**
	 * Navigate to AR Transaction Type in Taxlink UI
	 */
	public void navigateToArTransType( )
	{
		clickOnDashboardDropdown();
		jsWaiter.sleep(100);
		clickOnArTransType();
		jsWaiter.waitForLoadAll();
	}

	/**
	 * Method to navigate to Supplier Type tab
	 */
	public void navigateToSupplierType( )
	{
		wait.waitForElementDisplayed(dashboardMenuLoc);
		click.clickElement(dashboardMenuLoc);
		js.executeScript("arguments[0].scrollIntoView();", supplierTypeTab);
		jsWaiter.sleep(2000);
		click.clickElement(supplierTypeTab);
	}

	/**
	 * Method for navigation to Offline BIP Extract Jobs tab
	 */
	public void navigateToOfflineBIP( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		wait.waitForElementDisplayed(offlineBIPExtractJobs);
		click.clickElement(offlineBIPExtractJobs);
	}

	/**
	 * Method for navigation to Real Time BIP Jobs tab
	 */
	public void navigateToRealTimeBIP( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		wait.waitForElementDisplayed(realTimeBIPExtractJob);
		click.clickElement(realTimeBIPExtractJob);
	}

	/**
	 * Method for navigation to Profile Options tab
	 */
	public void navigateToProfileOptions( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		expWait.until(ExpectedConditions.visibilityOf(userProfileOptionsTab));
		click.clickElement(userProfileOptionsTab);
	}

	/*
	 * click on Tax Calculation SetUps Dropdown in Taxlink application
	 */
	public void clickOnTaxCalculationSetUpsDropdown( )
	{
		wait.waitForElementEnabled(taxCalculationSetUpsLoc);
		click.clickElement(taxCalculationSetUpsLoc);
	}

	/*
	 * click on the Rules Mapping button from Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnPreCalcRulesMapping( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(preRulesButton));
		click.clickElement(preRulesButton);
	}

	/**
	 * Navigate to Pre-Rules Mapping in Taxlink UI
	 */

	public void navigateToPreRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnPreCalcRulesMapping();
	}

	/*
	 * click on the Pre-Calc Rules Mapping button from INV Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnInvPreCalcRulesMapping( )
	{
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invRulesMappingTab));
		click.clickElement(invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);
		jsWaiter.sleep(2000);
	}

	/**
	 * Navigate to INV Journal Output File in Taxlink UI
	 */

	public void navigateToInvJournalOutputFile( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnInvJournalOutputFile();
	}

	/*
	 * click on the Journal output file button from INV Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnInvJournalOutputFile( )
	{
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invRulesMappingTab));
		click.clickElement(invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invJournalOutputButton));
		click.clickElement(invJournalOutputButton);
		jsWaiter.sleep(2000);
	}

	/**
	 * Navigate to INV Pre-Rules Mapping in Taxlink UI
	 */

	public void navigateToInvPreRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnInvPreCalcRulesMapping();
	}

	/**
	 * Navigate to INV Project Output File in Taxlink UI
	 */

	public void navigateToInvProjectOutputFile( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnInvProjectOutputFile();
	}

	/*
	 * click on the Project output file button from INV Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnInvProjectOutputFile( )
	{
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invRulesMappingTab));
		click.clickElement(invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invProjectOutputButton));
		click.clickElement(invProjectOutputButton);
		jsWaiter.sleep(2000);
	}

	/*
	 * click on the Post-calc Rules Mapping button from Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnPostCalcRulesMapping( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		js.executeScript("arguments[0].scrollIntoView();", postRulesButton);
		expWait.until(ExpectedConditions.visibilityOf(postRulesButton));
		click.clickElement(postRulesButton);
	}

	/**
	 * Navigate to Post-Rules Mapping in Taxlink UI
	 */
	public void navigateToPostRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnPostCalcRulesMapping();
	}

	/**
	 * Navigate to Condition set - Rules Mapping in Taxlink UI
	 */

	public void navigateToConditionSets( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnConditionSets();
	}

	/**
	 * Navigate to INV Condition set - INV Rules Mapping in Taxlink UI
	 */

	public void navigateToInvConditionSets( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnInvConditionSets();
	}

	/*
	 * click on the Condition set button from Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnConditionSets( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		scroll.scrollBottom();
		expWait.until(ExpectedConditions.visibilityOf(conditionSetsButton));
		click.clickElement(conditionSetsButton);
	}

	/*
	 * click on the INV Condition set button from INV Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnInvConditionSets( )
	{
		scroll.scrollBottom();
		js.executeScript("arguments[0].scrollIntoView();", invConditionSetsButton);
		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		scroll.scrollBottom();
		jsWaiter.sleep(1000);
		js.executeScript("arguments[0].scrollIntoView();", invConditionSetsButton);
		click.clickElement(invConditionSetsButton);
	}

	/**
	 * Method to navigate To Default Mapping Page
	 */
	public void clickOnDefaultMapping( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		js.executeScript("arguments[0].scrollIntoView();", defaultMappingButton);
		expWait.until(ExpectedConditions.visibilityOf(defaultMappingButton));
		click.clickElement(defaultMappingButton);
	}

	/**
	 * Method to navigate To Default Mapping Page
	 */
	public void navigateToDefaultMapping( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnDefaultMapping();
	}

	/**
	 * Navigate to the Supplies page via the left-hand
	 * navigation panel.
	 */
	public void navigateToArTaxExclusionsPage( )
	{
		wait.waitForElementDisplayed(taxCalculationSetUpsLoc);
		click.clickElement(taxCalculationSetUpsLoc);
		js.executeScript("arguments[0].scrollIntoView();", suppliesTabLoc);
		wait.waitForElementDisplayed(suppliesTabLoc);
		click.clickElement(suppliesTabLoc);
		js.executeScript("arguments[0].scrollIntoView();", arTaxCalcExclusionsTabLoc);
		wait.waitForElementDisplayed(arTaxCalcExclusionsTabLoc);
		click.clickElement(arTaxCalcExclusionsTabLoc);
	}

	/**
	 * Method for navigation to AP Scan Invoice Source Tab
	 */
	public void navigateToAPScanInvSrc( )
	{
		expWait.until(ExpectedConditions.visibilityOf(taxCalculationSetUpsLoc));
		click.clickElement(taxCalculationSetUpsLoc);
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", apScanInvSrcTab);
		expWait.until(ExpectedConditions.visibilityOf(apScanInvSrcTab));
		click.clickElement(apScanInvSrcTab);
	}

	/**
	 * Method for navigation to AP Assume Tax Source Tab
	 */
	public void navigateToAPAssumeTaxSrc( )
	{
		expWait.until(ExpectedConditions.visibilityOf(taxCalculationSetUpsLoc));
		click.clickElement(taxCalculationSetUpsLoc);
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", procurementTab);
		expWait.until(ExpectedConditions.visibilityOf(procurementTab));
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", apAssumeTaxSrcTab);
		expWait.until(ExpectedConditions.visibilityOf(apAssumeTaxSrcTab));
		click.clickElement(apAssumeTaxSrcTab);
	}

	/**
	 * Method for navigation to AP Tax Action Ranges Tab
	 */
	public void navigateToAPTaxActRanges( )
	{
		expWait.until(ExpectedConditions.visibilityOf(taxCalculationSetUpsLoc));
		click.clickElement(taxCalculationSetUpsLoc);
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", apTaxActRangesTab);
		expWait.until(ExpectedConditions.visibilityOf(apTaxActRangesTab));
		click.clickElement(apTaxActRangesTab);
	}

	/**
	 * Method for navigation to AP Tax Action Override Tab
	 */
	public void navigateToAPTaxActOverride( )
	{
		expWait.until(ExpectedConditions.visibilityOf(taxCalculationSetUpsLoc));
		click.clickElement(taxCalculationSetUpsLoc);
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		jsWaiter.sleep(2000);
		click.clickElement(apTaxActOverrideTab);
	}

	/**
	 * Method for navigation to AP Tax Calculation Exclusion Tab
	 */
	public void navigateToAPTaxCalcExcl( )
	{
		expWait.until(ExpectedConditions.visibilityOf(taxCalculationSetUpsLoc));
		click.clickElement(taxCalculationSetUpsLoc);
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(apTaxCalcExclTab));
		click.clickElement(apTaxCalcExclTab);
	}

	/*
	 * click on the PO Tax Calc Excl button from Tax Calculation Set Ups Dropdown
	 * in Taxlink Application
	 */
	public void clickOnPOTaxCalcExcl( )
	{
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(poTaxCalcExclTab));
		click.clickElement(poTaxCalcExclTab);
	}

	/**
	 * Navigate to PO Tax Calc Excl in Taxlink UI
	 */
	public void navigateToPOTaxCalcExclPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnPOTaxCalcExcl();
	}

	/**
	 * click on the Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnAdvancedConfigDropdown( )
	{
		expWait.until(ExpectedConditions.visibilityOf(advancedConfigurationLoc));
		click.clickElement(advancedConfigurationLoc);
	}

	/**
	 * click on the Lookups tab from Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnLookupsTab( )
	{
		expWait.until(ExpectedConditions.visibilityOf(lookupsLink));
		click.clickElement(lookupsLink);
	}

	/**
	 * Navigate to Lookups page in Taxlink UI
	 */
	public void navigateToLookups( )
	{
		clickOnAdvancedConfigDropdown();
		clickOnLookupsTab();
	}

	/**
	 * click on the Monitoring tab from Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnMonitoringDropdown( )
	{
		expWait.until(ExpectedConditions.visibilityOf(monitoringDropdown));
		click.clickElement(monitoringDropdown);
	}

	/**
	 * click on the Transaction Monitoring tab from Monitoring dropdown
	 * in Taxlink Application
	 */
	public void clickOnTransMonitoringTab( )
	{
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(transMonitoringTab));
		click.clickElement(transMonitoringTab);
	}

	/**
	 * Navigate to Transaction Monitoring Tab in Taxlink UI
	 */
	public void navigateToTransMonitoringTab( ) throws InterruptedException
	{
		jsWaiter.sleep(500);
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(500);
		clickOnMonitoringDropdown();
		jsWaiter.sleep(500);
		clickOnTransMonitoringTab();
	}

	/**
	 * click on the Polling Jobs tab from Monitoring dropdown
	 * in Taxlink Application
	 */
	public void clickOnPollingJobsTab( )
	{
		jsWaiter.sleep(500);
		scroll.scrollBottom();
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		clickOnMonitoringDropdown();
		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(pollingJobsButton));
		click.clickElement(pollingJobsButton);
	}

	/**
	 * Navigate to Polling Jobs page in Taxlink UI
	 */
	public void navigateToPollingJobs( ) throws InterruptedException
	{
		jsWaiter.sleep(500);
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(500);
		clickOnMonitoringDropdown();
		jsWaiter.sleep(500);
		clickOnPollingJobsTab();
	}

	/**
	 * click on the Summary reports tab from Monitoring dropdown
	 * in Taxlink Application
	 */
	public void clickOnSummaryReportsTab( )
	{
		js.executeScript("arguments[0].scrollIntoView();", summaryReportsTab);
		expWait.until(ExpectedConditions.visibilityOf(summaryReportsTab));
		click.clickElement(summaryReportsTab);
	}

	/**
	 * Navigate to Summary reports in Taxlink UI
	 */
	public void navigateToSummaryReports( ) throws InterruptedException
	{
		jsWaiter.sleep(500);
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(1000);
		clickOnMonitoringDropdown();
		jsWaiter.sleep(1000);
		clickOnSummaryReportsTab();
	}

	/**
	 * click on the Cleanup instance tab from Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnCleanUpInstanceTab( )
	{
		jsWaiter.sleep(2000);
		scroll.scrollBottom();
		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(cleanUpInstanceLink));
		click.clickElement(cleanUpInstanceLink);
	}

	/**
	 * Navigate to Cleanup instance page in Taxlink UI
	 */
	public void navigateToCleanUpInstancePage( )
	{
		clickOnAdvancedConfigDropdown();
		clickOnCleanUpInstanceTab();
	}

	/**
	 * click on the Security dropdown
	 * in Taxlink Application
	 */
	public void clickOnSecurityDropdown( )
	{
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", securityLoc);
		expWait.until(ExpectedConditions.visibilityOf(securityLoc));
		click.clickElement(securityLoc);
	}

	/**
	 * click on the Users tab from Security dropdown
	 * in Taxlink Application
	 */
	public void clickOnUsersTab( )
	{
		jsWaiter.sleep(1000);
		scroll.scrollBottom();
		expWait.until(ExpectedConditions.visibilityOf(usersLink));
		click.clickElement(usersLink);
	}

	/**
	 * Navigate to Users page in Taxlink UI
	 */
	public void navigateToUsersTab( )
	{
		jsWaiter.sleep(5000);
		clickOnSecurityDropdown();
		clickOnUsersTab();
	}

	/**
	 * Select User from Users tab and click on Edit button
	 * to modify the roles for any instance
	 *
	 * @param user
	 * @return boolean
	 */
	public boolean selectUser( String user )
	{
		boolean editFlag = false;
		wait.waitForElementDisplayed(summaryPageTitle);
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagUserPresence = dataPresentInParticularColumn(userNamePresentation, user);

			if ( flagUserPresence.isPresent() )
			{
				VertexLogger.log(user + " selected on the the Users Summary Table");
				flagUserPresence
					.get()
					.findElement(editButtonUsers)
					.click();
				VertexLogger.log("Clicked on Edit button for the User: " + user);
				editFlag = true;
				break;
			}
			else
			{
				actionPageDown
					.sendKeys(Keys.PAGE_DOWN)
					.perform();
				jsWaiter.sleep(15000);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return editFlag;
	}

	/**
	 * Method to search data in particular column of
	 * summary tables
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresentInParticularColumn( List<WebElement> ele, String text )
	{
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Method to click on Roles Page
	 */
	public void clickOnRoles( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rolesTab));
		click.clickElement(rolesTab);
	}

	/**
	 * Method to navigate To Roles Page
	 */
	public void navigateToRoles( )
	{
		clickOnSecurityDropdown();
		clickOnRoles();
	}

	/**
	 * click on the Generate new password tab from Security dropdown
	 * in Taxlink Application
	 */
	public void clickOnGenerateNewPasswordTab( )
	{
		jsWaiter.sleep(2000);
		js.executeScript("arguments[0].scrollIntoView();", generateNewPwdLink);
		expWait.until(ExpectedConditions.visibilityOf(generateNewPwdLink));
		click.clickElement(generateNewPwdLink);
	}

	/**
	 * Navigate to Generate new password page in Taxlink UI
	 */
	public void navigateToGenerateNewPasswordTab( )
	{
		wait.waitForElementEnabled(dashboardMenuLoc);
		click.moveToElementAndClick(dashboardMenuLoc);
		clickOnSecurityDropdown();
		clickOnGenerateNewPasswordTab();
	}
}





