package com.vertex.quality.connectors.taxlink.ui_e2e.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * @author mgaikwad
 */

public class TaxLinkBasePage extends VertexPage
{
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();
	String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";
	Path filePath = Paths.get(this.rootPath.toString() + textFilePath);
	public Actions actionPageDown = new Actions(driver);
	public TaxLinkUIUtilities utils = new TaxLinkUIUtilities();
	public WebDriverWait expWait = new WebDriverWait(driver, 120);
	public JavascriptExecutor js = (JavascriptExecutor) driver;
	public Wait<WebDriver> fluentWait = new FluentWait<>(driver)
		.withTimeout(Duration.ofSeconds(30L))
		.pollingEvery(Duration.ofSeconds(5L))
		.ignoring(NoSuchElementException.class);

	@FindBy(xpath = "//a[@data-cy='dashboard']")
	protected WebElement dashboardMenuLoc;
	@FindBy(xpath = "(//a[@class='rs-btn rs-btn-subtle rs-dropdown-toggle'])[2]")
	protected WebElement taxCalculationSetUpsLoc;

	@FindBy(xpath = "//a[@class='rs-nav-item-content']")
	protected WebElement onboardingDashboardButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/taxAction']")
	protected WebElement apTaxActRangesTab;
	@FindBy(xpath = "//a[@href = '/vertex-tl-web-ui/taxExclusion']")
	protected WebElement apTaxCalcExclTab;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/arBatchSource']")
	protected WebElement arTransactionSourceButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/aRTransactionType']")
	protected WebElement arTransactionTypeButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/businessUnits']")
	protected WebElement businessUnitButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/legalEntity']")
	protected WebElement legalEntityTab;

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle']/span[contains(text(),'Rules Mapping')])[last()-1]")
	protected WebElement rulesMappingTab;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/rules']")
	protected WebElement preRulesButton;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/postRules']")
	protected WebElement postRulesButton;
	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[last()-3]")
	protected WebElement invRulesMappingTab;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryRules']")
	protected WebElement invPreRulesButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryJournalOutputFile']")
	protected WebElement invJournalOutputButton;
	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryProjectOutputFile']")
	protected WebElement invProjectOutputButton;
	@FindBy(xpath = "//a[@data-cy = 'advancedConfiguration']")
	protected WebElement advancedConfigurationTab;

	@FindBy(xpath = "//span[contains(text(), 'Monitoring')]")
	protected WebElement monitoringTab;

	@FindBy(xpath = "//a[@href = '/vertex-tl-web-ui/batchJobDetail']")
	protected WebElement ptdeJobStatusTab;

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[2]")
	protected WebElement suppliesTabLoc;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/arTaxExclusion']")
	protected WebElement arTaxCalcExclusionsTabLoc;
	@FindBy(xpath = "(//span[@ref='lbTotal'])[2]")
	protected WebElement importTotalPageCount;

	@FindBy(xpath = "(//button[contains(text(), 'Next')])[2]")
	protected WebElement importNextArrow;

	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement loginButton;

	@FindBy(tagName = "h1")
	protected WebElement summaryPageTitle;

	@FindBy(xpath = "//button[contains(text(),'Next')][1]")
	protected WebElement nextArrowOnSummaryTable;

	@FindBy(xpath = "//span[contains(@ref,'lbTotal')]")
	protected WebElement totalPageCountSummaryTable;

	@FindBy(xpath = "//button[contains(text(),'IMPORT')]")
	protected WebElement importButtonOnSummaryPage;

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

	@FindBy(xpath = "//button[contains(text(), 'Edit')]")
	protected WebElement editButton;

	@FindBy(xpath = "//button[contains(text(), 'View')]")
	protected WebElement viewButton;

	@FindBy(xpath = "//button[contains(text(), 'Save')]")
	protected WebElement saveButton;

	@FindBy(xpath = "//button[contains(text(), 'Cancel')]")
	protected WebElement cancelButton;

	@FindBy(xpath = "//button[contains(text(), 'Close')]")
	protected WebElement closeButton;

	@FindBy(xpath = "//a[contains(text(), 'Sign Out')]")
	protected WebElement signOutOracleERP;

	@FindBy(xpath = "//button[contains(text(), 'Confirm')]")
	protected WebElement confirmSignOutOracleERP;

	@FindBy(xpath = "//button[contains(text(), 'SELECT')]")
	protected WebElement selectImportButton;

	@FindBy(xpath = "//button[contains(text(), 'ADD')]")
	protected WebElement addButton;

	@FindBy(xpath = "//label[@for= 'startDate']/following-sibling::div/div/input")
	protected WebElement startDate;

	@FindBy(xpath = "//label[@for= 'endDate']/following-sibling::div/div/input")
	protected WebElement endDate;

	@FindBy(xpath = "//button[contains(text(), 'Next')][1]")
	protected WebElement nextArrow;

	@FindBy(xpath = "//select[@class='external-filter-select']")
	protected WebElement externalFilter;

	@FindBy(xpath = "//input[@name='enabledFlag']")
	protected WebElement enabledFlag;

	@FindBy(tagName = "h3")
	protected WebElement addViewEditPageTitle;


	public TaxLinkBasePage( WebDriver driver )
	{
		super(driver);
		this.driver = driver;
	}

	/**
	 * click on Dashboard Dropdown in Taxlink application
	 */
	public void clickOnDashboardDropdown( )
	{
		wait.waitForElementEnabled(dashboardMenuLoc);
		click.clickElement(dashboardMenuLoc);
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
	 * method to be called for each test as a pre-requisite
	 */

	public void navigateToInstancePage( )
	{
		clickOnDashboardDropdown();
		clickOnOnboardingDashboard();
	}

	/**
	 * click on Business Unit Button
	 */
	public void clickOnBusinessUnit( )
	{
		expWait.until(ExpectedConditions.visibilityOf(businessUnitButton));
		click.clickElement(businessUnitButton);
		String bUTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		bUTitle.equalsIgnoreCase("Business Units");
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
	 * Method for navigation to Legal Entity tab
	 */
	public void navigateToLegalEntity( )
	{
		clickOnDashboardDropdown();
		wait.waitForElementDisplayed(legalEntityTab);
		click.clickElement(legalEntityTab);
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
		arTransactionSourceTitle.equalsIgnoreCase("AR Transaction Source");
	}

	/**
	 * Navigate to AR Transaction Source in Taxlink UI
	 */
	public void navigateToArTransSource( )
	{
		clickOnDashboardDropdown();
		jsWaiter.sleep(100);
		clickOnArTransactionSource();
		jsWaiter.waitForLoadAll();
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


	/*
	 * click on Tax Calculation Set Ups Dropdown in Taxlink application
	 */

	public void clickOnTaxCalculationSetUpsDropdown( )
	{
		wait.waitForElementEnabled(taxCalculationSetUpsLoc);
		click.clickElement(taxCalculationSetUpsLoc);
	}

	/*
	 * click on the Pre-Rules Mapping button from Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */
	public void clickOnRulesMapping( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(preRulesButton));
		click.clickElement(preRulesButton);
	}

	/**
	 * Navigate to Pre calc Rules Mapping in Taxlink UI
	 */

	public void navigateToPreCalcRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnPreCalcRulesMapping();
	}

	/**
	 * click on the Post-
	 * Rules Mapping button from Rules Mapping dropdown
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
	 * click on the Pre-Rules Mapping button from Rules Mapping dropdown
	 * under Tax Calculation Set up
	 * in Taxlink Application
	 */

	public void clickOnPreCalcRulesMapping( )
	{
		expWait.until(ExpectedConditions.visibilityOf(rulesMappingTab));
		click.clickElement(rulesMappingTab);
		js.executeScript("arguments[0].scrollIntoView();", preRulesButton);
		expWait.until(ExpectedConditions.visibilityOf(preRulesButton));
		click.clickElement(preRulesButton);
	}

	/**
	 * Navigate to Post calc Rules Mapping in Taxlink UI
	 */

	public void navigateToPostCalcRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnPostCalcRulesMapping();
	}

	/**
	 * Navigate to INV Pre-Rules Mapping in Taxlink UI
	 */

	public void navigateToInvPreRulesMappingPage( )
	{
		clickOnTaxCalculationSetUpsDropdown();
		clickOnInvPreCalcRulesMapping();
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

	/**
	 * Method for navigation to PTDE job status Tab
	 */
	public void navigateToPTDEJobStatus( )
	{
		wait.waitForElementDisplayed(advancedConfigurationTab);
		click.clickElement(advancedConfigurationTab);
		wait.waitForElementDisplayed(monitoringTab);
		click.clickElement(monitoringTab);
		wait.waitForElementDisplayed(ptdeJobStatusTab);
		click.clickElement(ptdeJobStatusTab);
	}

	/**
	 * Method to search data in summary table
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text, List<WebElement> table )
	{
		Optional<WebElement> dataFound = table
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Method to Read file on the given path and return
	 * String text from file amd the Path of the file
	 *
	 * @return
	 */
	public List<Object> getFileReadPath( )
	{
		Path fileCreatePath = filePath.resolve("taxlink_OracleERP.txt");

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
		Path filePathToDelete = Paths.get(filePath + File.separator + "taxlink_OracleERP.txt");
		Files.delete(filePathToDelete);
		VertexLogger.log("The text file has been deleted!!");
	}
}