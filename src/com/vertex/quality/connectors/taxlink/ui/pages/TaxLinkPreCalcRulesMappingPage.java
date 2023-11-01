package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.OnboardingDashboard;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Pre-Rules Mapping Tab contained
 * in Rules Mapping module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcRulesMappingPage extends TaxLinkBasePage
{
	public TaxLinkPreCalcRulesMappingPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1/b")
	public WebElement editPreRulePageTitle;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[last()]")
	private WebElement nextConditionTab;

	@FindBy(xpath = "(//button[contains(text(),'CANCEL')])[last()]")
	private WebElement cancel;

	@FindBy(xpath = "(//button[contains(text(),'Cancel')])[last()]")
	private WebElement cancelOnPopUp;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[2]")
	private WebElement saveOnConclusions;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[1]")
	private WebElement saveOnMains;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement addPreRuleMappingName;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement addPreRuleMappingOrder;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDateSelectedValue;

	@FindBy(xpath = "//div[@id='tax-assist-condition-con']/div/select[@class='form-control dropDownSelect']")
	private WebElement conditionSetRuleMappingDropdown;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfSrc;

	@FindBy(xpath = "//h4[contains(text(),'To: Target')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfTarget;

	@FindBy(xpath = "//input[@id='SRC']")
	private WebElement searchOnSourcePopUp;

	@FindBy(xpath = "(//span[@class='search_cancel__unWU8'])[2]")
	private WebElement clearSearchOnSourcePopUp;

	@FindBy(xpath = "(//div[contains(@class,'treeView_treeViewRow')]/div[2]/div/label)[1]")
	private WebElement radioButtonSetSourcePostRefreshPopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[last()]")
	private WebElement selectButtonTargetConclusionsPopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[1]")
	private WebElement selectButtonSourceConclusionsPopUp;

	@FindBy(xpath = "//input[@id='TGT']")
	private WebElement searchOnTargetPopUp;

	@FindBy(xpath = "//div[contains(text(),'Constant')]/ancestor::div/div[2]/div/input")
	private WebElement constantTextBox;

	@FindBy(className = "notification__inner")
	private WebElement verifyRuleMappingAlreadyExistsError;

	@FindBy(xpath = "//div[@class='ag-body-viewport ag-layout-normal ag-row-no-animation']")
	private WebElement presentationRow;

	@FindBy(xpath = "//div[@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;

	@FindBy(xpath = "//div[@col-id='ruleOrder']")
	private List<WebElement> ruleOrderPresentation;

	@FindBy(xpath = "//div[@col-id='enabledFlag']")
	private List<WebElement> enabledPresentation;

	@FindBy(xpath = "//div[@col-id='startDate']")
	private List<WebElement> startDatePresentation;

	@FindBy(xpath = "//div[@col-id='endDate']")
	private List<WebElement> endDatePresentation;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement ruleNameViewEditPages;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement ruleMappingOrderViewEditPages;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container']/input)[1]")
	private WebElement ruleStartDateViewEditPages;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container']/input)[last()]")
	private WebElement ruleEndDateViewEditPages;

	@FindBy(xpath = "//div[@id='tax-assist-condition-fun']/div/select[@class='form-control dropDownSelect']")
	private WebElement functionField;

	@FindBy(xpath = "//a[@id='cancelTaxAssistRuleLink_link']")
	private WebElement backButtonConditionsTab;

	@FindBy(xpath = "(//button[contains(text(), 'BACK')])[last()]")
	private WebElement backButtonConclusionsTab;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']/div[1]")
	private WebElement sourceButtonConclusionsViewEditPages;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']/div[1]")
	private WebElement sourceNumberTwoConcatConditionSet;

	@FindBy(xpath = "//div[@class='modalCustome modalShow2 react-draggable']")
	private WebElement sourcePopUp;

	@FindBy(xpath = "//input[@id='SRC1']")
	private WebElement searchOnSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div/div[2]/div/div[2]/div/label/span")
	private WebElement radioButtonSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div/button[contains(text(),'Select')]")
	private WebElement saveSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;

	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement warningPopup;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']")
	private WebElement sourceToBeButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']/div[1]")
	private WebElement sourceToBeButtonConclusionsViewEditPages;

	@FindBy(xpath = "//input[@id='SRC1']")
	private WebElement searchOnSourceToBePopUp;

	@FindBy(xpath = "(//div[contains(@class,'treeView_treeViewRow')]/div[2]/div/label)[last()]")
	private WebElement radioButtonSetSourceToBePopUp;

	@FindBy(xpath = "(//div/button[contains(@class,'btn btn-sm btn--primary btn-colored rulesMappingPopup_smallButtonSaveCancel')])[1]")
	private WebElement selectButtonSourceToBeConclusionsPopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[1]")
	private WebElement selectButtonSourceToBeConclusionsNVLPopUp;

	@FindBy(xpath = "//div[contains(text(),'Delimiter')]/ancestor::div/div/div/input")
	private WebElement delimiterSplitFunctionTextBox;

	@FindBy(xpath = "//div[contains(text(),'Index Value')]/ancestor::div/div/div/input")
	private WebElement indexValueSplitFunctionTextBox;

	@FindBy(xpath = "//button[@id='undoRuleCondition']")
	private WebElement refreshConditionSetButton;

	@FindBy(xpath = "//button[@id='undoRuleCondition-ref']")
	private WebElement refreshFunctionButton;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[1]")
	private WebElement refreshSourceButton;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[1]")
	private WebElement refreshSourceButtonNVL;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[last()]")
	private WebElement refreshTargetButton;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[2]")
	private WebElement refreshTargetForConcatAndConstantButton;

	@FindBy(xpath = "//div[contains(text(),'Collection Condition')]/ancestor::div/div[2]/div/input")
	private WebElement colConditionTextBox;

	@FindBy(xpath = "//div[contains(text(),'Collection Attribute')]/ancestor::div/div[2]/div/select")
	private WebElement colAttributeDropDown;

	@FindBy(xpath = "(//div[@class=' flyout__item']/button[contains(text(),'View')])[last()]")
	private WebElement viewButtonPerRule;

	@FindBy(xpath = "//a[@class='rs-nav-item-content']")
	private WebElement onboardingDashboardButton;

	@FindBy(xpath = "//label[@for='importRecommendedRules']")
	private WebElement recommendedRulesCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Rule Order')]/ancestor::div[@class='ag-cell-label-container ag-header-cell-sorted-none']/span")
	private WebElement filterRuleOrder;

	@FindBy(xpath = "//span[contains(text(),'Rule Name')]/ancestor::div[@class='ag-cell-label-container ag-header-cell-sorted-none']/span")
	private WebElement filterRuleName;

	@FindBy(xpath = "(//input[@class='ag-filter-filter'])[last()-1]")
	private WebElement filterInput;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	private By editButtonRule = By.xpath(
		".//following-sibling::div/div/div/div/div/div/button[contains(text(), 'Edit')]");
	private By threeDotsPerRule = By.xpath(".//following-sibling::div/div/div/div/div/parent::div/button");

	/**
	 * Verify title of Pre-Rules Mapping Page in Taxlink application
	 */
	public void verifyTitlePreRulesMappingPage( )
	{
		String rulesMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		rulesMappingTitle.equalsIgnoreCase(RulesMapping.RuleMappingDetails.headerPreRuleMappingPage);
	}

	/**
	 * Adding the data on Mains Tab for Rules Mapping in Taxlink UI
	 */

	public String addDataOnMainsTab( )
	{
		String ruleName;
		String ruleOrder;

		verifyTitlePreRulesMappingPage();
		click.clickElement(addButton);

		ruleName = utils.alphaNumericWithTimeStampText();
		wait.waitForElementDisplayed(addPreRuleMappingName);
		text.enterText(addPreRuleMappingName, ruleName);
		ruleOrder = utils.randomNumber("5");
		wait.waitForElementDisplayed(addPreRuleMappingOrder);
		text.enterText(addPreRuleMappingOrder, ruleOrder);

		click.clickElement(endDate);

		String nextDate = utils.getNextDayDate();
		VertexLogger.log("End Date: " + nextDate);
		text.enterText(endDate, nextDate);

		wait.waitForElementDisplayed(enabledFlag);
		checkbox.isCheckboxChecked(enabledFlag);

		String currDate = utils.getFormattedDate();
		VertexLogger.log("Start Date: " + currDate);
		wait.waitForElementDisplayed(startDate);
		click.clickElement(startDate);
		utils.clearTextField(startDate);
		jsWaiter.sleep(1000);
		text.enterText(startDate, currDate);

		List<String> dataEntered = Arrays.asList(ruleName, ruleOrder, currDate, nextDate);

		VertexLogger.log("Data Entered for creating new rule is: " + dataEntered);

		click.clickElement(nextMainTab);
		return ruleName;
	}

	/**
	 * Add Pre-Rule with passed condition for MAP function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyMapFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddMapFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "MAP");
		if ( selectValueForSourceRealTimeTransMap("Realtime Transaction Detail Extensions") )
		{
			if ( selectValueForSourceRealTimeTransMap("Realtime Transaction Header Extension") )
			{
				selectValueForSource("Application ID");
				selectValueForTarget("Department Code");
				wait.waitForElementDisplayed(saveOnConclusions);
				click.clickElement(saveOnConclusions);
			}
		}
		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);
			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddMapFunctionRule = true;
		}
		else
		{
			flagAddMapFunctionRule = false;
		}
		return flagAddMapFunctionRule;
	}

	/**
	 * Method to search data in summary table of Rules Mapping page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		fluentWait.until(driver -> summaryPageTitle);
		Optional<WebElement> dataFound = ruleNamePresentation
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();
		return dataFound;
	}

	/**
	 * Select Condition Set and Function to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void addConditionSetAndFunctionForRule( String conditionSetValue, String functionValue )
	{
		jsWaiter.sleep(10000);
		dropdown.selectDropdownByDisplayName(conditionSetRuleMappingDropdown, conditionSetValue);
		VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected!!");
		String conditionSetSelected = dropdown
			.getDropdownSelectedOption(conditionSetRuleMappingDropdown)
			.getText();
		conditionSetSelected.equals(conditionSetValue);
		click.clickElement(refreshConditionSetButton);
		String conditionSetPostRefresh = dropdown
			.getDropdownSelectedOption(conditionSetRuleMappingDropdown)
			.getText();
		if ( conditionSetPostRefresh.isEmpty() )
		{
			VertexLogger.log("Condition Set " + conditionSetValue + " has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Condition Set Value!!");
		}
		dropdown.selectDropdownByDisplayName(conditionSetRuleMappingDropdown, conditionSetValue);
		VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected post refresh!!");

		dropdown.selectDropdownByDisplayName(functionField, functionValue);
		VertexLogger.log("Function " + functionValue + " has been selected!!");
		String functionSelected = dropdown
			.getDropdownSelectedOption(functionField)
			.getText();
		functionSelected.equals(functionValue);
		click.clickElement(refreshFunctionButton);
		String functionPostRefresh = dropdown
			.getDropdownSelectedOption(functionField)
			.getText();
		if ( functionPostRefresh.isEmpty() )
		{
			VertexLogger.log("Function " + functionValue + " has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Function value!!");
		}
		dropdown.selectDropdownByDisplayName(functionField, functionValue);
		VertexLogger.log("Function " + functionValue + " has been selected post refresh!!");
		click.clickElement(nextConditionTab);
	}

	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(radioButton);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

	public void selectTargetRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[last()]", value));
		jsWaiter.sleep(5000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Target Value: " + value);
	}

	/**
	 * Enter Collection Condition and select Collection Attribute for Source Realtime Transaction*
	 * on Conclusions tab to add a rule with Map function
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @return flagRealtime
	 */
	public boolean selectValueForSourceRealTimeTransMap( String selectedSrc )
	{
		boolean flagRealtime = false;
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, selectedSrc);
		jsWaiter.sleep(1500);
		wait.waitForElementDisplayed(radioButtonSetSourcePostRefreshPopUp);
		selectSourceRadioButton(selectedSrc);

		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);
		if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
		{
			VertexLogger.log(
				"Checking Collection Condition And Collection Attribute fields for it's mandatory property..");
			jsWaiter.sleep(5000);
			dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Project Number");

			boolean saveButtonDisabledFlag = !saveOnConclusions.isEnabled();
			VertexLogger.log(
				"saveButtonDisabledFlag if Collection Condition is not mentioned: " + saveButtonDisabledFlag);
			text.enterText(colConditionTextBox, "PRJ_DETAILS");
			wait.waitForElementDisplayed(targetButtonConclusions);
			click.clickElement(targetButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
			text.enterText(searchOnTargetPopUp, "Department Code");
			selectTargetRadioButton("Department Code");
			wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
			click.clickElement(selectButtonTargetConclusionsPopUp);

			boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
			VertexLogger.log("saveButtonEnabledFlag if Collection Condition is mentioned: " + saveButtonEnabledFlag);
			if ( saveButtonDisabledFlag && saveButtonEnabledFlag )
			{
				flagRealtime = true;
			}
		}
		else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
		{
			wait.waitForElementDisplayed(colAttributeDropDown);
			VertexLogger.log("Checking Collection Attribute field for it's mandatory property..");
			jsWaiter.sleep(5000);
			dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Reference Value01");
			wait.waitForElementDisplayed(targetButtonConclusions);
			click.clickElement(targetButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
			text.enterText(searchOnTargetPopUp, "Department Code");

			selectTargetRadioButton("Department Code");
			wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
			click.clickElement(selectButtonTargetConclusionsPopUp);
			wait.waitForElementDisplayed(saveOnConclusions);

			boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
			VertexLogger.log(
				"saveButtonEnabledFlag if Collection Condition is not mentioned: " + saveButtonEnabledFlag);
			if ( saveButtonEnabledFlag )
			{
				flagRealtime = true;
			}
		}
		else
		{
			flagRealtime = false;
			VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
		}
		return flagRealtime;
	}

	/**
	 * Select Standard Payload Selection for Source on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void selectValueForSource( String sourceValue )
	{
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, sourceValue);
		selectSourceRadioButton(sourceValue);

		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);

		String sourceSelected = sourceButtonConclusions.getText();
		sourceSelected.equals(sourceValue);
		click.clickElement(refreshSourceButton);
		String sourcePostRefresh = sourceButtonConclusions.getText();
		if ( sourcePostRefresh.isEmpty() )
		{
			VertexLogger.log("Source has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Source value!!");
		}

		jsWaiter.sleep(2000);
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, sourceValue);
		jsWaiter.sleep(1500);

		if ( radioButtonSetSourcePostRefreshPopUp
			.getAttribute("for")
			.equals(sourceValue) )
		{
			jsWaiter.sleep(1000);
			click.clickElement(radioButtonSetSourcePostRefreshPopUp);
			VertexLogger.log("Selected Source Value: " + sourceValue + " post refresh!!");
		}
		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);
	}

	/**
	 * Select Standard Payload Selection for Source on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void selectValueForSourceNVL( String sourceValue )
	{
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, sourceValue);

		selectSourceRadioButton(sourceValue);

		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);

		String sourceSelected = sourceButtonConclusions.getText();
		sourceSelected.equals(sourceValue);
		click.clickElement(refreshSourceButtonNVL);
		String sourcePostRefresh = sourceButtonConclusions.getText();
		if ( sourcePostRefresh.isEmpty() )
		{
			VertexLogger.log("Source has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Source value!!");
		}

		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, sourceValue);
		jsWaiter.sleep(1500);

		if ( radioButtonSetSourcePostRefreshPopUp
			.getAttribute("for")
			.equals(sourceValue) )
		{
			jsWaiter.sleep(2000);
			click.clickElement(radioButtonSetSourcePostRefreshPopUp);
			VertexLogger.log("Selected Source Value: " + sourceValue + " post refresh!!");
		}
		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);
	}

	/**
	 * Select Target Value on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void selectValueForTarget( String targetValue )
	{
		wait.waitForElementDisplayed(targetButtonConclusions);
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);

		selectTargetRadioButton(targetValue);
		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);

		String targetSelected = targetButtonConclusions.getText();
		targetSelected.equals(targetValue);
		wait.waitForElementDisplayed(refreshTargetButton);
		click.clickElement(refreshTargetButton);
		String targetPostRefresh = targetButtonConclusions.getText();
		if ( targetPostRefresh.isEmpty() )
		{
			VertexLogger.log("Target value has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Target value!!");
		}

		wait.waitForElementDisplayed(targetButtonConclusions);
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);

		selectTargetRadioButton(targetValue);
		VertexLogger.log("Selected Target Value: " + targetValue + " post refresh!!");

		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);
	}

	/**
	 * Select Target Value on Conclusions tab to verify
	 * if constant value is mandatory for the selected attribute
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean selectTargetAttributeToCheckCompulsionOnConstant( String targetValue )
	{
		boolean finalFlag = false, constantIsRequiredFlag = false, saveEnabledFlag = false;
		click.clickElement(refreshTargetForConcatAndConstantButton);
		wait.waitForElementDisplayed(targetButtonConclusions);
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);

		selectTargetRadioButton(targetValue);
		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);

		wait.waitForElementDisplayed(saveOnConclusions);
		if ( !saveOnConclusions.isEnabled() )
		{
			constantIsRequiredFlag = true;
			VertexLogger.log(
				"Save button is not enabled, please enter constant value in Constant field to add a rule!!");
		}

		enterValueForConstant("AM0006475");
		wait.waitForElementDisplayed(saveOnConclusions);
		if ( saveOnConclusions.isEnabled() )
		{
			saveEnabledFlag = true;
			VertexLogger.log("Save button is now enabled on entering constant value!!");
		}
		if ( saveEnabledFlag && constantIsRequiredFlag )
		{
			finalFlag = true;
		}
		return finalFlag;
	}

	/**
	 * Add Pre-Rule with passed condition for Upper function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyUpperFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddUpperFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "UPPER");
		selectValueForSource("Application Short Name");
		selectValueForTarget("Purchasing Category Name");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);
			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddUpperFunctionRule = true;
		}
		else
		{
			flagAddUpperFunctionRule = false;
		}
		return flagAddUpperFunctionRule;
	}

	/**
	 * Add Pre-Rule with passed condition for Map Address function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyMapAddressFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddMapAddrFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "MAPADDRESS");

		selectValueForSource("Bill To Address");
		selectValueForTarget("POO Address");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);
			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddMapAddrFunctionRule = true;
		}
		else
		{
			flagAddMapAddrFunctionRule = false;
		}
		return flagAddMapAddrFunctionRule;
	}

	/**
	 * Add Pre-Rule with passed condition for ToNumber function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyToNumberFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddToNumberFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "TONUMBER");

		selectValueForSource("Event Type Code");
		selectValueForTarget("Ship To TAID");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddToNumberFunctionRule = true;
		}
		else
		{
			flagAddToNumberFunctionRule = false;
		}
		return flagAddToNumberFunctionRule;
	}

	/**
	 * Add Pre-Rule with passed condition for lower function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyLowerFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddLowerFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "LOWER");
		selectValueForSource("Batch Source Name");
		selectValueForTarget("Product Class");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddLowerFunctionRule = true;
		}
		else
		{
			flagAddLowerFunctionRule = false;
		}
		return flagAddLowerFunctionRule;
	}

	/**
	 * Add Pre-Rule with passed condition for Constant function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyConstantFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddConstFunctionRule, flagConstantFieldRequired = false;
		boolean flagSummaryTableVerified = false;
		String[] requiredConstantValAttributes = new String[] { "Line Amount Includes Tax Flag", "Line Level Action",
			"Prorate Across All Lines Flag", "Tax Calculation Allowed Flag", "Calculate Vendor Tax Indicator",
			"Document Level Action", "Transaction Number" };

		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "CONSTANT");

		for ( int i = 0 ; i < requiredConstantValAttributes.length ; i++ )
		{
			if ( selectTargetAttributeToCheckCompulsionOnConstant(requiredConstantValAttributes[i]) )
			{
				flagConstantFieldRequired = true;
				VertexLogger.log("Verified!!" + requiredConstantValAttributes[i]);
			}
		}
		selectValueForTarget("Material Code");
		enterValueForConstant("AM0006475");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);
		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified && flagConstantFieldRequired )
		{
			flagAddConstFunctionRule = true;
		}
		else
		{
			flagAddConstFunctionRule = false;
		}
		return flagAddConstFunctionRule;
	}

	/**
	 * Enter Constant Value on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void enterValueForConstant( String constantValue )
	{
		wait.waitForElementDisplayed(constantTextBox);
		text.enterText(constantTextBox, constantValue);
		VertexLogger.log("Entered constant value: " + constantValue);
	}

	/**
	 * Add Pre-Rule with passed condition for Concat function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyConcatFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddConcatFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "CONCAT");
		if ( selectValueForSourceRealTimeTransConcat("Realtime Transaction Detail Extensions") )
		{
			if ( selectValueForSourceRealTimeTransConcat("Realtime Transaction Header Extension") )
			{
						firstPayloadSourceToConcat("Bill To Country");
						secondPayloadSourceToConcat("Bill To Postal Code");
						selectValueForTargetForConcatenation("Flexfield Code01");
						wait.waitForElementDisplayed(saveOnConclusions);
						click.clickElement(saveOnConclusions);
			}
		}
		expWait.until(ExpectedConditions.visibilityOf(externalFilter));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddConcatFunctionRule = true;
		}
		else
		{
			flagAddConcatFunctionRule = false;
		}
		return flagAddConcatFunctionRule;
	}

	/**
	 * First Source Standard Payload selection on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void firstPayloadSourceToConcat( String firstPayload )
	{
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(sourcePopUp);
		text.enterText(searchOnSourcePopUp, firstPayload);
		selectSourceRadioButton(firstPayload);
		VertexLogger.log("Selected First Payload for Concatenation " + firstPayload);
		jsWaiter.sleep(1000);
		click.clickElement(selectButtonSourceConclusionsPopUp);
	}

	/**
	 * Second Source Standard Payload selection on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void secondPayloadSourceToConcat( String SecondPayload )
	{
		wait.waitForElementDisplayed(sourceNumberTwoConcatConditionSet);
		click.clickElement(sourceNumberTwoConcatConditionSet);
		wait.waitForElementDisplayed(sourcePopUp);
		text.enterText(searchOnSourceNumberTwoConcatPopUp, SecondPayload);
		jsWaiter.sleep(5000);
		click.clickElement(radioButtonSourceNumberTwoConcatPopUp);
		VertexLogger.log("Selected Second Payload for Concatenation " + SecondPayload);
		wait.waitForElementDisplayed(saveSourceNumberTwoConcatPopUp);
		click.clickElement(saveSourceNumberTwoConcatPopUp);
	}

	/**
	 * Select Target Value for Concat function on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void selectValueForTargetForConcatenation( String targetValue )
	{
		wait.waitForElementDisplayed(targetButtonConclusions);
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);
		selectTargetRadioButton(targetValue);
		VertexLogger.log("Selected Target Value: " + targetValue);

		jsWaiter.sleep(5000);
		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);

		String targetSelected = targetButtonConclusions.getText();
		targetSelected.equals(targetValue);
		click.clickElement(refreshTargetForConcatAndConstantButton);
		String targetPostRefresh = targetButtonConclusions.getText();
		if ( targetPostRefresh.isEmpty() )
		{
			VertexLogger.log("Target value has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Target value!!");
		}

		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(targetButtonConclusions);
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);
		selectTargetRadioButton(targetValue);
		VertexLogger.log("Selected Target Value: " + targetValue + " post refresh!!");
		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);
	}

	/**
	 * Add Pre-Rule with passed condition for Sub String function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifySubStringFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddSubstrFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "SUBSTRING");
		if ( selectValueForSourceRealTimeTransSubstring("Realtime Transaction Detail Extensions") )
		{
			if ( selectValueForSourceRealTimeTransSubstring("Realtime Transaction Header Extension") )
			{
				selectValueForSource("Transaction Type Name");
				jsWaiter.sleep(5000);
				startingPositionSubstringBox.clear();
				text.enterText(startingPositionSubstringBox, "4");
				VertexLogger.log("Entered starting position : 4");
				wait.waitForElementDisplayed(endingPositionSubstringBox);
				endingPositionSubstringBox.clear();
				text.enterText(endingPositionSubstringBox, "1");
				VertexLogger.log("Entered ending position to check for an error: 1");
				selectValueForTarget("Usage Class Code");
				wait.waitForElementDisplayed(saveOnConclusions);
				click.clickElement(saveOnConclusions);

				wait.waitForElementDisplayed(warningPopup);

				if ( positionError() )
				{
					VertexLogger.log("Entering correct ending position!!");
					wait.waitForElementDisplayed(endingPositionSubstringBox);
					endingPositionSubstringBox.clear();
					text.enterText(endingPositionSubstringBox, "8");
					VertexLogger.log("Entered ending position : 8");

					selectValueForTarget("Usage Class Code");
					wait.waitForElementDisplayed(saveOnConclusions);
					click.clickElement(saveOnConclusions);
				}
			}
		}
		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddSubstrFunctionRule = true;
		}
		else
		{
			flagAddSubstrFunctionRule = false;
		}
		return flagAddSubstrFunctionRule;
	}

	/**
	 * Verify an error message for Length on endingPositionSubstringBox
	 * for Ending Position cannot be less than starting position
	 *
	 * @return boolean
	 */

	public boolean positionError( )
	{
		boolean flagPositionError;

		if ( warningPopup
			.getText()
			.contains("Ending Position can't be less than Starting position") )
		{
			VertexLogger.log("Ending position should be greater than the starting position!!");
			flagPositionError = true;
		}
		else
		{
			VertexLogger.log("Ending position is not verified!!");
			flagPositionError = false;
		}
		return flagPositionError;
	}

	/**
	 * Add Pre-Rule with passed condition for NVL function, place another source value
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyNvlFunction_placeSourceValPreRule( String conditionSetValue )
	{
		boolean flagAddNVLSrcFunctionRule = false;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "NVL");

		if ( selectValueForSecondSourceRealTimeTransNVL("Realtime Transaction Detail Extensions") )
		{
			if ( selectValueForSecondSourceRealTimeTransNVL("Realtime Transaction Header Extension") )
			{
				selectValueForSourceNVL("Customer PO Number");
				selectValueToBeFilledFromSource("Applied From Transaction Number");
				if ( !constantTextBox.isEnabled() )
				{
					VertexLogger.log("NVL function is disabled for Constant text box");
				}
				jsWaiter.sleep(2000);
						selectValueForTarget("Customer Account Number");
						wait.waitForElementDisplayed(saveOnConclusions);
						click.clickElement(saveOnConclusions);
					}
				}
		jsWaiter.sleep(10000);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddNVLSrcFunctionRule = true;
		}
		return flagAddNVLSrcFunctionRule;
	}

	/**
	 * Check Realtime Transaction header/details attribute does not appear
	 * on Conclusions tab to add a rule for 'If the source is NULL' with NVL function
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @return flagRealtime
	 */
	public boolean selectValueForSourceRealTimeTransNVL( String selectedSrc )
	{
		boolean flagRealtime = false;
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, selectedSrc);

		if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
		{
			VertexLogger.log("Checking Realtime Transaction Detail Extensions's availability for second source..");
			try
			{
				radioButtonSetSourceToBePopUp.isDisplayed();
			}
			catch ( NoSuchElementException exp )
			{
				flagRealtime = true;
				VertexLogger.log("Realtime Transaction Detail Extension is not present!");
				click.clickElement(cancelOnPopUp);
			}
		}
		else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
		{
			VertexLogger.log("Checking Realtime Transaction Header Extensions's availability for second source..");
			try
			{
				radioButtonSetSourceToBePopUp.isDisplayed();
			}
			catch ( NoSuchElementException exp )
			{
				flagRealtime = true;
				VertexLogger.log("Realtime Transaction Header Extension is not present!");
				click.clickElement(cancelOnPopUp);
			}
		}
		else
		{
			flagRealtime = false;
			VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
		}
		return flagRealtime;
	}

	/**
	 * Check Realtime transaction header and realtime transaction detail attributes are present
	 * in second source on Conclusions tab to add a rule with NVL function
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @return flagRealtime
	 */
	public boolean selectValueForSecondSourceRealTimeTransNVL( String selectedSrc )
	{
		boolean flagRealtime = false;
		selectValueForSource("Customer PO Number");
		click.clickElement(sourceNumberTwoConcatConditionSet);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourceNumberTwoConcatPopUp, selectedSrc);
		jsWaiter.sleep(1500);
		wait.waitForElementDisplayed(radioButtonSetSourcePostRefreshPopUp);
		selectSourceRadioButton(selectedSrc);

		wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
		click.clickElement(selectButtonSourceConclusionsPopUp);
		if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
		{
			VertexLogger.log(
				"Checking Collection Condition And Collection Attribute fields for it's mandatory property..");
			jsWaiter.sleep(5000);
			dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Project Number");

			boolean saveButtonDisabledFlag = !saveOnConclusions.isEnabled();
			VertexLogger.log(
				"saveButtonDisabledFlag if Collection Condition is not mentioned: " + saveButtonDisabledFlag);
			text.enterText(colConditionTextBox, "PRJ_DETAILS");
			wait.waitForElementDisplayed(targetButtonConclusions, 60);
			click.clickElement(targetButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
			text.enterText(searchOnTargetPopUp, "Department Code");
			selectTargetRadioButton("Department Code");
			wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
			click.clickElement(selectButtonTargetConclusionsPopUp);

			boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
			VertexLogger.log("saveButtonEnabledFlag if Collection Condition is mentioned: " + saveButtonEnabledFlag);
			if ( saveButtonDisabledFlag && saveButtonEnabledFlag )
			{
				flagRealtime = true;
			}
		}
		else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
		{
			wait.waitForElementDisplayed(colAttributeDropDown);
			VertexLogger.log("Checking Collection Attribute field for it's mandatory property..");
			jsWaiter.sleep(5000);
			dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Reference Value1");
			wait.waitForElementDisplayed(targetButtonConclusions);
			click.clickElement(targetButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
			text.enterText(searchOnTargetPopUp, "Department Code");

			selectTargetRadioButton("Department Code");
			wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
			click.clickElement(selectButtonTargetConclusionsPopUp);
			wait.waitForElementDisplayed(saveOnConclusions);

			boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
			VertexLogger.log(
				"saveButtonEnabledFlag if Collection Condition is not mentioned: " + saveButtonEnabledFlag);
			if ( saveButtonEnabledFlag )
			{
				flagRealtime = true;
			}
		}
		else
		{
			flagRealtime = false;
			VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
		}
		return flagRealtime;
	}

	/**
	 * Add Pre-Rule with passed condition for NVL function, Place a CONSTANT value
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyNvlFunction_placeConstantValPreRule( String conditionSetValue )
	{
		boolean flagAddNVLConstFunctionRule;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "NVL");

		selectValueForSourceNVL("Bill From Party Number");
		enterValueForConstant("MGNVL2812");
		if ( !sourceToBeButtonConclusions.isEnabled() )
		{
			VertexLogger.log("NVL function is disabled for other Source value text box");
		}
		selectValueForTarget("Flexfield Code01");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				flagSummaryTableVerified = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagSummaryTableVerified )
		{
			flagAddNVLConstFunctionRule = true;
		}
		else
		{
			flagAddNVLConstFunctionRule = false;
		}
		return flagAddNVLConstFunctionRule;
	}

	/**
	 * Select a NULL Source value on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 */
	public void selectValueToBeFilledFromSource( String chosenSourceToBe )
	{
		wait.waitForElementDisplayed(sourceToBeButtonConclusions);
		click.clickElement(sourceToBeButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourceToBePopUp, chosenSourceToBe);
		selectSourceRadioButton(chosenSourceToBe);
		wait.waitForElementEnabled(selectButtonSourceToBeConclusionsNVLPopUp);
		click.clickElement(selectButtonSourceToBeConclusionsNVLPopUp);
	}

	/**
	 * Add Pre-Rule with passed condition for Split function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifySplitFunctionPreRule( String conditionSetValue )
	{
		boolean flagAddSplitFunctionRule = false;
		boolean flagSummaryTableVerified = false;
		navigateToPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "SPLIT");

		selectValueForSource("Buyer Admin Destination Registration number");
		if ( verifySpecialCharsValOnDelimiter() )
		{
			text.clearText(delimiterSplitFunctionTextBox);
			jsWaiter.sleep(2000);
			text.enterText(delimiterSplitFunctionTextBox, ",");
			VertexLogger.log("Delimiter : , ");
			wait.waitForElementDisplayed(indexValueSplitFunctionTextBox);
			text.enterText(indexValueSplitFunctionTextBox, "4");
			VertexLogger.log("Index Value : 4");
			selectValueForTarget("Ship From Party Number");
			wait.waitForElementDisplayed(saveOnConclusions);
			click.clickElement(saveOnConclusions);

			expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

			int count = Integer.valueOf(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresent(ruleName);

				if ( data.isPresent() )
				{
					VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
					flagSummaryTableVerified = true;
					break;
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
			if ( flagSummaryTableVerified )
			{
				flagAddSplitFunctionRule = true;
			}
			else
			{
				flagAddSplitFunctionRule = false;
			}
		}
		return flagAddSplitFunctionRule;
	}

	/**
	 * Verify an error message for alphanumeric values on BUID textbox
	 *
	 * Should accept only special characters
	 *
	 * @return boolean
	 */
	public boolean verifySpecialCharsValOnDelimiter( )
	{
		boolean flagVerifyDelVal;
		String delVal;
		VertexLogger.log("Entering delimiter value as alphanumeric value..");
		delVal = utils.randomAlphaNumericText();
		wait.waitForElementDisplayed(delimiterSplitFunctionTextBox);
		text.enterText(delimiterSplitFunctionTextBox, delVal);
		if ( warningPopup
			.getText()
			.contains("Delimiter accepts special characters only") )
		{
			VertexLogger.log("Delimiter contains alphanumeric value! Kindly enter special character value!!");
			flagVerifyDelVal = true;
		}
		else
		{
			VertexLogger.log("Error!!");
			flagVerifyDelVal = false;
		}
		return flagVerifyDelVal;
	}

	/**
	 * Verify title of View Pre-Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewPreRulesMappingPage( )
	{
		boolean flagTitleViewRule;
		String viewRuleTitle = wait
			.waitForElementDisplayed(editPreRulePageTitle)
			.getText();
		boolean verifyFlag = viewRuleTitle.contains(RulesMapping.RuleMappingDetails.headerPreViewRuleMappingPage);

		if ( verifyFlag )
		{
			flagTitleViewRule = true;
		}
		else
		{
			flagTitleViewRule = false;
		}
		return flagTitleViewRule;
	}

	/**
	 * View Pre-Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean viewPreRulesMapping( String ruleName )
	{
		boolean flagViewRule = false;
		jsWaiter.sleep(5000);

		expWait.until(ExpectedConditions.visibilityOf(externalFilter));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(15000);
			Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentation, ruleName);

			if ( flagRulePresence.isPresent() )
			{
				flagRulePresence
					.get()
					.findElement(threeDotsPerRule)
					.click();
				viewButtonPerRule.click();
				VertexLogger.log("Clicked on View button for the rule: " + ruleName);
				break;
			}
			else
			{
				scroll.scrollElementIntoView(nextArrowOnSummaryTable);
				jsWaiter.sleep(15000);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		try
		{
			if ( verifyTitleViewPreRulesMappingPage() )
			{
				boolean ruleNameEnabledViewEditPagesFlag = !ruleNameViewEditPages.isEnabled();
				VertexLogger.log(ruleNameViewEditPages.getAttribute("value"));
				boolean ruleNameTextViewEditPagesFlag = !ruleNameViewEditPages
					.getAttribute("value")
					.isBlank();
				VertexLogger.log(String.valueOf(ruleNameEnabledViewEditPagesFlag && ruleNameTextViewEditPagesFlag));

				boolean ruleMappingOrderEnabledViewEditPagesFlag = !ruleMappingOrderViewEditPages.isEnabled();
				boolean ruleMappingOrderTextViewEditPagesFlag = !ruleMappingOrderViewEditPages
					.getAttribute("value")
					.isBlank();
				VertexLogger.log(
					String.valueOf(ruleMappingOrderEnabledViewEditPagesFlag && ruleMappingOrderTextViewEditPagesFlag));

				boolean ruleStartDateEnabledViewEditPagesFlag = !ruleStartDateViewEditPages.isEnabled();
				boolean ruleStartDateTextViewEditPagesFlag = !ruleStartDateViewEditPages
					.getAttribute("value")
					.isBlank();
				VertexLogger.log(
					String.valueOf(ruleStartDateEnabledViewEditPagesFlag && ruleStartDateTextViewEditPagesFlag));

				boolean ruleEndDateViewEditPagesFlag = !ruleEndDateViewEditPages.isEnabled();
				VertexLogger.log(String.valueOf(ruleEndDateViewEditPagesFlag));

				boolean ruleenabledFlagRulesViewEditPagesFlag = !enabledFlag.isEnabled();
				VertexLogger.log(String.valueOf(ruleenabledFlagRulesViewEditPagesFlag));

				boolean cancelFlag = cancel.isEnabled();
				VertexLogger.log(String.valueOf(cancelFlag));

				click.clickElement(nextMainTab);

				boolean conditionSetFieldFlag = !conditionSetRuleMappingDropdown.isEnabled();
				VertexLogger.log(String.valueOf(conditionSetFieldFlag));

				boolean functionFieldFlag = !functionField.isEnabled();
				VertexLogger.log(String.valueOf(functionFieldFlag));

				jsWaiter.sleep(15000);
				expWait.until(ExpectedConditions.visibilityOf(functionField));

				boolean condSetTextDisabled = !dropdown
					.getDropdownSelectedOption(conditionSetRuleMappingDropdown)
					.getText()
					.isBlank();
				VertexLogger.log(String.valueOf(condSetTextDisabled));

				String function = dropdown
					.getDropdownSelectedOption(functionField)
					.getText();
				VertexLogger.log("Function used for rule : " + ruleName + " is " + function);

				click.clickElement(nextConditionTab);
				expWait.until(ExpectedConditions.elementToBeClickable(cancel));

				switch ( function )
				{
					case "MAP":
					case "UPPER":
					case "LOWER":
					case "MAPADDRESS":

					case "TONUMBER":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "CONCAT":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && sourceNumberTwoConcatConditionSet
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "SUBSTRING":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && startingPositionSubstringBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && endingPositionSubstringBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "NVL":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && constantTextBox
								 .getAttribute("class")
								 .contains("Disabled") && sourceToBeButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "CONSTANT":
						if ( constantTextBox
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "SPLIT":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && delimiterSplitFunctionTextBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && indexValueSplitFunctionTextBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
				}

				if ( ruleNameEnabledViewEditPagesFlag && ruleNameTextViewEditPagesFlag &&
					 ruleMappingOrderEnabledViewEditPagesFlag && ruleMappingOrderTextViewEditPagesFlag &&
					 ruleStartDateEnabledViewEditPagesFlag && ruleStartDateTextViewEditPagesFlag &&
					 condSetTextDisabled && ruleEndDateViewEditPagesFlag && ruleenabledFlagRulesViewEditPagesFlag &&
					 cancelFlag && conditionSetFieldFlag && functionFieldFlag )
				{
					flagViewRule = true;
				}
				else
				{
					flagViewRule = false;
				}
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Data is not present in the Summary Table");
		}
		return flagViewRule;
	}

	/**
	 * Verify title of Edit Pre-Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditPreRulesMappingPage( )
	{
		boolean flagTitleEditRule;
		String editRuleTitle = wait
			.waitForElementDisplayed(editPreRulePageTitle)
			.getText();
		boolean verifyFlag = editRuleTitle.contains(RulesMapping.RuleMappingDetails.headerPreEditRuleMappingPage);

		if ( verifyFlag )
		{
			flagTitleEditRule = true;
		}
		else
		{
			flagTitleEditRule = false;
		}
		return flagTitleEditRule;
	}

	/**
	 * Edit Disabled Pre-Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean editDisabledPreCalcRulesMapping( String ruleName )
	{
		boolean flagDisableEditRule = false;
		jsWaiter.sleep(5000);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentation, ruleName);

			if ( flagRulePresence.isPresent() )
			{
				flagRulePresence
					.get()
					.findElement(editButtonRule)
					.click();
				VertexLogger.log("Clicked on Edit button for the rule: " + ruleName);
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

		if ( verifyTitleEditPreRulesMappingPage() )
		{
			click.clickElement(enabledFlag);
			expWait.until(ExpectedConditions.elementToBeClickable(saveOnMains));
			click.clickElement(saveOnMains);
		}
		jsWaiter.sleep(5000);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentation, ruleName);

			if ( flagRulePresence.isPresent() )
			{
				try
				{
					flagRulePresence
						.get()
						.findElement(editButtonRule)
						.click();
					VertexLogger.log("Edit button is present for disabled Pre-calc rule: " + ruleName);
					break;
				}
				catch ( Exception e )
				{
					VertexLogger.log("Edit button is not present for disabled Pre-calc rule: " + ruleName);
					flagDisableEditRule = true;
					break;
				}
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
		return flagDisableEditRule;
	}

	/**
	 * Verify Edit Rules Functionality in Taxlink application
	 *
	 * returns @boolean
	 */
	public boolean verifyEditFunctionalityRulesMapping( )
	{
		String updatedRuleOrder;
		boolean flagEditFunctionalityRule = false, flagVerify = false;

		wait.waitForElementDisplayed(backButtonConclusionsTab);
		click.clickElement(backButtonConclusionsTab);
		wait.waitForElementDisplayed(backButtonConditionsTab);
		click.clickElement(backButtonConditionsTab);

		String updatedDate = utils.getNextDayDate();
		String editRuleWithRuleName = ruleNameViewEditPages.getAttribute("value");
		wait.waitForElementDisplayed(addPreRuleMappingOrder);
		updatedRuleOrder = utils.randomNumber("5");

		click.clickElement(ruleMappingOrderViewEditPages);
		text.enterText(ruleMappingOrderViewEditPages, updatedRuleOrder);

		click.clickElement(endDate);

		text.enterText(endDate, updatedDate);

		List<String> dataEntered = Arrays.asList(updatedRuleOrder, updatedDate);

		VertexLogger.log("Updated Rule order and End date selected : " + dataEntered);

		expWait.until(ExpectedConditions.elementToBeClickable(saveOnMains));
		click.clickElement(saveOnMains);
		VertexLogger.log("Clicked on the Save button");

		jsWaiter.sleep(5000);

		expWait.until(ExpectedConditions.visibilityOf(externalFilter));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		htmlElement.sendKeys(Keys.END);

		if ( currentPageCount
			.getText()
			.equals("1") )
		{
			int count = Integer.valueOf(totalPageCountSummaryTable.getText());

			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresent(editRuleWithRuleName);

				if ( data.isPresent() )
				{
					jsWaiter.sleep(500);
					boolean updatedRuleOrderFlag = ruleOrderPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(updatedRuleOrder));
					VertexLogger.log(String.valueOf(updatedRuleOrderFlag));

					if ( updatedRuleOrderFlag )
					{
						VertexLogger.log("Rule with Name " + editRuleWithRuleName + " is updated" +
										 " and verified in the summary table");
						flagVerify = true;
						break;
					}
					break;
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
			if ( flagVerify )
			{
				flagEditFunctionalityRule = true;
			}
			else
			{
				flagEditFunctionalityRule = false;
			}
		}
		return flagEditFunctionalityRule;
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
	 * Verify Export to CSV functionality on Pre-Rules Mapping page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVPreRulesMapping( String instName ) throws IOException
	{
		boolean flagExportToCSV = false;
		boolean flagInstanceMatch = false;
		boolean ruleOrderFlag = false;
		boolean startDateFlag = false;
		boolean endDateFlag = false;
		boolean enabledFlag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		String fileName = "Pre-Calculation_Rules_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToPreRulesMappingPage();
		verifyTitlePreRulesMappingPage();
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		if ( !checkNoRecordsPresent() )
		{
			exportToCSVSummaryPage.click();
			jsWaiter.sleep(2000);

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String name = csvRecord.get(0);
					if ( name.contains(instName) )
					{
						VertexLogger.log("Fusion Instance Name : " + name);
						flagInstanceMatch = true;
						break;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("Rule Order", "Rule Name", "Start Date", "End Date", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String ruleOrder_CSV = csvRecord.get("Rule Order");
					String ruleName_CSV = csvRecord.get("Rule Name");
					String startDate_CSV = csvRecord.get("Start Date");
					String endDate_CSV = csvRecord.get("End Date");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Rule Order : " + ruleOrder_CSV);
					VertexLogger.log("Rule Name : " + ruleName_CSV);
					VertexLogger.log("Start Date : " + startDate_CSV);
					VertexLogger.log("End Date : " + endDate_CSV);
					VertexLogger.log("Enabled : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !ruleOrder_CSV.equals("Rule Order") )
					{
						if ( !startDate_CSV.equals("") )
						{
							formattedStartDate_CSV = utils.getCSVFormattedDate(startDate_CSV);
							VertexLogger.log("Formatted Start Date : " + formattedStartDate_CSV);
						}
						else
						{
							formattedStartDate_CSV = startDate_CSV;
						}

						if ( !endDate_CSV.equals("") )
						{
							formattedEndDate_CSV = utils.getCSVFormattedDate(endDate_CSV);
							VertexLogger.log("Formatted End Date : " + formattedEndDate_CSV);
						}
						else
						{
							formattedEndDate_CSV = endDate_CSV;
						}

						Optional ruleName = dataPresentInParticularColumn(ruleNamePresentation, ruleName_CSV);
						if ( ruleName.isPresent() )
						{
							Optional ruleOrder = dataPresentInParticularColumn(ruleOrderPresentation, ruleOrder_CSV);
							ruleOrderFlag = ruleOrder.isPresent();
							VertexLogger.log("" + ruleOrderFlag);
							Optional startDate = dataPresentInParticularColumn(startDatePresentation,
								formattedStartDate_CSV);
							startDateFlag = startDate.isPresent();
							VertexLogger.log("" + startDateFlag);
							Optional endDate = dataPresentInParticularColumn(endDatePresentation, formattedEndDate_CSV);
							endDateFlag = endDate.isPresent();
							VertexLogger.log("" + endDateFlag);
							Optional enabled = dataPresentInParticularColumn(enabledPresentation, enabled_CSV);
							enabledFlag = enabled.isPresent();
							VertexLogger.log("" + enabledFlag);
						}
						else
						{
							htmlElement.sendKeys(Keys.END);
							jsWaiter.sleep(100);
							click.clickElement(nextArrowOnSummaryTable);
						}
						if ( flagInstanceMatch && ruleOrderFlag && startDateFlag && endDateFlag && enabledFlag )
						{
							flagExportToCSV = true;
						}
						else
						{
							flagExportToCSV = false;
						}
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
		else
		{
			flagExportToCSV = true;
		}

		return flagExportToCSV;
	}

	/**
	 * Add Pre-Rule to check view and edit functionality
	 * in Taxlink Application
	 *
	 * @return ruleName
	 */
	public String addConstantFunctionPreRuleToViewAndEdit( String conditionSetValue )
	{
		expWait.until(ExpectedConditions.visibilityOf(preRulesButton));
		click.clickElement(preRulesButton);
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "CONSTANT");
		selectValueForTarget("Material Code");
		enterValueForConstant("AM0006475");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);
		VertexLogger.log("Clicked on Save Button!");
		jsWaiter.sleep(5000);
		return ruleName;
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
		onboardingDashboardTitle.equalsIgnoreCase(OnboardingDashboard.INSTANCE_DETAILS.headerOnboardingDashboardPage);
	}

	/**
	 * Verify default rules(O2C&P2P) for Pre-Rules Mapping in Tax link application
	 * as a result of selected checkbox - Vertex recommended rules on Onboarding screen
	 *
	 * @return boolean
	 */
	public boolean verifyDefaultRulesInPreRules( )
	{
		boolean flagEnableRuleCheck = false;
		boolean flagVerifyO2C = false;
		boolean flagVerifyP2P = false;
		boolean flagCountDefaultRules = false;
		int dataO2C = 0, dataP2P = 0;
		navigateToInstancePage();
		wait.waitForElementEnabled(editButton);
		editButton.click();
		wait.waitForElementDisplayed(addViewEditPageTitle);
		String isCheckedEnableRules = recommendedRulesCheckbox.getAttribute("disabled");
		if ( isCheckedEnableRules == null || !isCheckedEnableRules.equals("disabled") )
		{
			flagEnableRuleCheck = true;
		}

		clickOnTaxCalculationSetUpsDropdown();
		js.executeScript("arguments[0].scrollIntoView();", preRulesButton);
		wait.waitForElementDisplayed(preRulesButton);
		click.clickElement(preRulesButton);

		verifyTitlePreRulesMappingPage();
		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		click.clickElement(filterRuleOrder);
		wait.waitForElementDisplayed(filterInput);
		text.enterText(filterInput, "100");

		jsWaiter.sleep(1000);

		click.clickElement(filterRuleName);
		wait.waitForElementDisplayed(filterInput);
		text.enterText(filterInput, "VTX_O2C");

		click.clickElement(summaryPageTitle);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		mainLoop:
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 1 ; j <= ruleNamePresentation.size() ; j++ )
			{
				if ( j == 11 )
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
					break;
				}
				else if ( ruleNamePresentation
					.get(j)
					.getText()
					.startsWith("VTX_O2C") )
				{
					VertexLogger.log(ruleNamePresentation
						.get(j)
						.getText());
					dataO2C++;
					if ( dataO2C == 15 )
					{
						jsWaiter.sleep(500);
						VertexLogger.log("Default rules starting with Name O2C are verified in the summary table");
						flagVerifyO2C = true;
						break mainLoop;
					}
				}
			}
		}

		jsWaiter.sleep(1000);

		checkPageNavigation();
		click.clickElement(filterRuleName);
		wait.waitForElementDisplayed(filterInput);
		text.clearText(filterInput);
		wait.waitForTextInElement(filterInput, "");
		text.enterText(filterInput, "VTX_P2P");

		click.clickElement(summaryPageTitle);
		mainLoop1:
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 1 ; j <= ruleNamePresentation.size() ; j++ )
			{
				jsWaiter.sleep(2000);
				if ( j == 11 )
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
					break;
				}
				else if ( ruleNamePresentation
					.get(j)
					.getText()
					.startsWith("VTX_P2P") )
				{
					jsWaiter.sleep(500);
					VertexLogger.log(ruleNamePresentation
						.get(j)
						.getText());
					dataP2P++;
					if ( dataP2P == 18 )
					{
						jsWaiter.sleep(500);
						VertexLogger.log("Default rules starting with Name P2P are verified in the summary table");
						flagVerifyP2P = true;
						break mainLoop1;
					}
				}
			}
		}

		if ( flagVerifyO2C && flagVerifyP2P && flagEnableRuleCheck)
		{
			VertexLogger.log("O2C Count: " + dataO2C);
			VertexLogger.log("P2P Count: " + dataP2P);
			flagCountDefaultRules = true;
		}
		return flagCountDefaultRules;
	}

		/**
		 * Enter Collection Condition and select Collection Attribute for Source Realtime Transaction*
		 * on Conclusions tab to add a rule with Concat function
		 * in Add Rules Mapping Page in Taxlink application
		 *
		 * @return flagRealtime
		 */
		public boolean selectValueForSourceRealTimeTransConcat (String selectedSrc )
		{
			boolean flagRealtime = false;
			click.clickElement(sourceButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
			text.enterText(searchOnSourcePopUp, selectedSrc);
			jsWaiter.sleep(1500);
			wait.waitForElementDisplayed(radioButtonSetSourcePostRefreshPopUp);
			selectSourceRadioButton(selectedSrc);

			wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
			click.clickElement(selectButtonSourceConclusionsPopUp);
			if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
			{
				VertexLogger.log(
					"Checking Collection Condition And Collection Attribute fields for it's mandatory property..");
				jsWaiter.sleep(5000);
				dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Project Number");

				boolean saveButtonDisabledFlag = !saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonDisabledFlag if Collection Condition is not mentioned: " + saveButtonDisabledFlag);
				text.enterText(colConditionTextBox, "PRJ_DETAILS");
				if ( sourceNumberTwoConcatConditionSet.isEnabled() )
				{
					secondPayloadSourceToConcat("Bill To Postal Code");
				}
				wait.waitForElementDisplayed(targetButtonConclusions, 60);
				click.clickElement(targetButtonConclusions);
				wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
				text.enterText(searchOnTargetPopUp, "Department Code");
				selectTargetRadioButton("Department Code");
				wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
				click.clickElement(selectButtonTargetConclusionsPopUp);

				boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonEnabledFlag if Collection Condition is mentioned: " + saveButtonEnabledFlag);
				if ( saveButtonDisabledFlag && saveButtonEnabledFlag )
				{
					flagRealtime = true;
				}
			}
			else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
			{
				wait.waitForElementDisplayed(colAttributeDropDown);
				VertexLogger.log("Checking Collection Attribute field for it's mandatory property..");
				jsWaiter.sleep(5000);
				dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Reference Value01");
				wait.waitForElementDisplayed(targetButtonConclusions);
				click.clickElement(targetButtonConclusions);
				wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
				text.enterText(searchOnTargetPopUp, "Department Code");

				selectTargetRadioButton("Department Code");
				wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
				click.clickElement(selectButtonTargetConclusionsPopUp);
				wait.waitForElementDisplayed(saveOnConclusions);
				if ( sourceNumberTwoConcatConditionSet.isDisplayed() )
				{
					secondPayloadSourceToConcat("Bill To Postal Code");
				}
				boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonEnabledFlag if Collection Condition is not mentioned: " + saveButtonEnabledFlag);
				if ( saveButtonEnabledFlag )
				{
					flagRealtime = true;
				}
			}
			else
			{
				flagRealtime = false;
				VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
			}
			return flagRealtime;
		}

		/**
		 * Check Realtime transaction header and realtime transaction detail attributes are not present
		 * in second source on Conclusions tab to add a rule with Concat function
		 * in Add Rules Mapping Page in Taxlink application
		 *
		 * @return flagRealtime
		 */
		public boolean selectValueForSecondSourceRealTimeTransConcat (String selectedSrc )
		{
			boolean flagRealtime = false;
			click.clickElement(sourceNumberTwoConcatConditionSet);
			wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
			text.enterText(searchOnSourceNumberTwoConcatPopUp, selectedSrc);

			if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
			{
				VertexLogger.log("Checking Realtime Transaction Detail Extensions's availability for second source..");
				jsWaiter.sleep(5000);
				try
				{
					radioButtonSourceNumberTwoConcatPopUp.isDisplayed();
				}
				catch ( NoSuchElementException exp )
				{
					flagRealtime = true;
					VertexLogger.log("Realtime Transaction Detail Extension is not present!");
					click.clickElement(cancelOnPopUp);
				}
			}
			else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
			{
				wait.waitForElementDisplayed(colAttributeDropDown);
				VertexLogger.log("Checking Realtime Transaction Header Extensions's availability for second source..");
				jsWaiter.sleep(5000);
				try
				{
					radioButtonSourceNumberTwoConcatPopUp.isDisplayed();
				}
				catch ( NoSuchElementException exp )
				{
					flagRealtime = true;
					VertexLogger.log("Realtime Transaction Header Extension is not present!");
					click.clickElement(cancelOnPopUp);
				}
			}
			else
			{
				flagRealtime = false;
				VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
			}
			return flagRealtime;
		}

		/**
		 * Enter Collection Condition and select Collection Attribute for Source Realtime Transaction*
		 * on Conclusions tab to add a rule with Substring function
		 * in Add Rules Mapping Page in Taxlink application
		 *
		 * @return flagRealtime
		 */
		public boolean selectValueForSourceRealTimeTransSubstring (String selectedSrc )
		{
			boolean flagRealtime = false;
			click.clickElement(sourceButtonConclusions);
			wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
			text.enterText(searchOnSourcePopUp, selectedSrc);
			jsWaiter.sleep(1500);
			wait.waitForElementDisplayed(radioButtonSetSourcePostRefreshPopUp);
			selectSourceRadioButton(selectedSrc);

			wait.waitForElementEnabled(selectButtonSourceConclusionsPopUp);
			click.clickElement(selectButtonSourceConclusionsPopUp);
			if ( selectedSrc.equals("Realtime Transaction Detail Extensions") )
			{
				VertexLogger.log(
					"Checking Collection Condition And Collection Attribute fields for it's mandatory property..");
				jsWaiter.sleep(5000);
				dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Project Number");

				boolean saveButtonDisabledFlag = !saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonDisabledFlag if Collection Condition is not mentioned: " + saveButtonDisabledFlag);
				text.enterText(colConditionTextBox, "PRJ_DETAILS");
				wait.waitForElementDisplayed(targetButtonConclusions);
				click.clickElement(targetButtonConclusions);
				wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
				text.enterText(searchOnTargetPopUp, "Department Code");
				selectTargetRadioButton("Department Code");
				wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
				click.clickElement(selectButtonTargetConclusionsPopUp);

				if ( startingPositionSubstringBox.isEnabled() )
				{
					text.enterText(startingPositionSubstringBox, "4");
					VertexLogger.log("Entered starting position : 4");
					wait.waitForElementDisplayed(endingPositionSubstringBox);
					text.enterText(endingPositionSubstringBox, "1");
					VertexLogger.log("Entered ending position : 1");
				}
				boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonEnabledFlag if Collection Condition is mentioned: " + saveButtonEnabledFlag);
				if ( saveButtonDisabledFlag && saveButtonEnabledFlag )
				{
					flagRealtime = true;
				}
			}
			else if ( selectedSrc.equals("Realtime Transaction Header Extension") )
			{
				wait.waitForElementDisplayed(colAttributeDropDown);
				VertexLogger.log("Checking Collection Attribute field for it's mandatory property..");
				jsWaiter.sleep(5000);
				dropdown.selectDropdownByDisplayName(colAttributeDropDown, "Reference Value01");
				wait.waitForElementDisplayed(targetButtonConclusions);
				click.clickElement(targetButtonConclusions);
				wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
				text.enterText(searchOnTargetPopUp, "Department Code");

				selectTargetRadioButton("Department Code");
				wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
				click.clickElement(selectButtonTargetConclusionsPopUp);
				wait.waitForElementDisplayed(saveOnConclusions);

				if ( startingPositionSubstringBox.isDisplayed() )
				{
					text.enterText(startingPositionSubstringBox, "4");
					VertexLogger.log("Entered starting position : 4");
					wait.waitForElementDisplayed(endingPositionSubstringBox);
					text.enterText(endingPositionSubstringBox, "1");
					VertexLogger.log("Entered ending position : 1");
				}
				boolean saveButtonEnabledFlag = saveOnConclusions.isEnabled();
				VertexLogger.log(
					"saveButtonEnabledFlag if Collection Condition is not mentioned: " + saveButtonEnabledFlag);
				if ( saveButtonEnabledFlag )
				{
					flagRealtime = true;
				}
			}
			else
			{
				flagRealtime = false;
				VertexLogger.log("Unable to validate Realtime Transaction Detail/header Extension fields!!");
			}
			return flagRealtime;
		}

		/**
		 * Edit Pre-Rules Mapping for a rule
		 * in Taxlink Application
		 *
		 * @param ruleName
		 *
		 * @return boolean
		 */
		public boolean editPreRulesMapping (String ruleName )
		{
			boolean flagEditRule = false;
			jsWaiter.sleep(5000);

			expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				jsWaiter.sleep(10000);
				Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentation, ruleName);

				if ( flagRulePresence.isPresent() )
				{
					flagRulePresence
						.get()
						.findElement(editButtonRule)
						.click();
					VertexLogger.log("Clicked on Edit button for the rule: " + ruleName);
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

			if ( verifyTitleEditPreRulesMappingPage() )
			{
				boolean ruleNameViewEditPagesFlag = ruleNameViewEditPages.isEnabled();
				VertexLogger.log(String.valueOf(ruleNameViewEditPagesFlag));
				boolean ruleMappingOrderViewEditPagesFlag = ruleMappingOrderViewEditPages.isEnabled();
				VertexLogger.log(String.valueOf(ruleMappingOrderViewEditPagesFlag));
				boolean ruleStartDateViewEditPagesFlag = !ruleStartDateViewEditPages.isEnabled();
				VertexLogger.log(String.valueOf(ruleStartDateViewEditPagesFlag));
				boolean ruleEndDateViewEditPagesFlag = ruleEndDateViewEditPages.isEnabled();
				VertexLogger.log(String.valueOf(ruleEndDateViewEditPagesFlag));
				boolean ruleenabledFlagRulesViewEditPagesFlag = enabledFlag.isEnabled();
				VertexLogger.log(String.valueOf(ruleenabledFlagRulesViewEditPagesFlag));
				boolean cancelFlag = cancel.isEnabled();
				VertexLogger.log(String.valueOf(cancelFlag));
				boolean saveOnMainsFlag = !saveOnMains.isEnabled();
				VertexLogger.log(String.valueOf(saveOnMainsFlag));

				click.clickElement(nextMainTab);
				boolean conditionSetFieldFlag = !conditionSetRuleMappingDropdown.isEnabled();
				VertexLogger.log(String.valueOf(conditionSetFieldFlag));
				boolean functionFieldFlag = !functionField.isEnabled();
				VertexLogger.log(String.valueOf(functionFieldFlag));

				jsWaiter.sleep(15000);
				expWait.until(ExpectedConditions.visibilityOf(functionField));
				String function = dropdown
					.getDropdownSelectedOption(functionField)
					.getText();
				VertexLogger.log("Function used for rule : " + ruleName + " is " + function);

				click.clickElement(nextConditionTab);
				expWait.until(ExpectedConditions.elementToBeClickable(cancel));
				jsWaiter.sleep(1000);

				switch ( function )
				{
					case "MAP":
					case "UPPER":
					case "LOWER":
					case "MAPADDRESS":

					case "TONUMBER":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "CONCAT":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && sourceNumberTwoConcatConditionSet
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "SUBSTRING":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && startingPositionSubstringBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && endingPositionSubstringBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "NVL":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && constantTextBox
								 .getAttribute("class")
								 .contains("Disabled") && sourceToBeButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "CONSTANT":
						if ( constantTextBox
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "SPLIT":
						if ( sourceButtonConclusionsViewEditPages
								 .getAttribute("class")
								 .contains("Disabled") && delimiterSplitFunctionTextBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && indexValueSplitFunctionTextBox
								 .getAttribute("disabled")
								 .equalsIgnoreCase("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
				}
				if ( ruleNameViewEditPagesFlag && ruleMappingOrderViewEditPagesFlag && ruleStartDateViewEditPagesFlag &&
					 ruleEndDateViewEditPagesFlag && ruleenabledFlagRulesViewEditPagesFlag && cancelFlag &&
					 saveOnMainsFlag && conditionSetFieldFlag && functionFieldFlag )
				{
					if ( verifyEditFunctionalityRulesMapping() )
					{
						flagEditRule = true;
					}
				}
				else
				{
					flagEditRule = false;
				}
			}
			return flagEditRule;
		}
	}