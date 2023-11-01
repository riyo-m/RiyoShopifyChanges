package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.RulesMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the end to end test cases for Pre-Rules Mapping Module Tab containing
 * Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcRulesMappingPage extends TaxLinkBasePage
{

	public TaxLinkPreCalcRulesMappingPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[last()]")
	private WebElement nextConditionTab;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[2]")
	private WebElement saveOnConclusions;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement addPreRuleMappingName;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement addPreRuleMappingOrder;

	@FindBy(xpath = "//div[@id='tax-assist-condition-con']/div/select[@class='form-control dropDownSelect']")
	private WebElement conditionSetPreRuleMappingDropdown;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfSrc;

	@FindBy(xpath = "//h4[contains(text(),'To: Target')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfTarget;

	@FindBy(xpath = "//input[@id='SRC']")
	private WebElement searchOnSourcePopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[last()]")
	private WebElement selectButtonTargetConclusionsPopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[1]")
	private WebElement selectButtonSourceConclusionsPopUp;

	@FindBy(xpath = "//input[@id='TGT']")
	private WebElement searchOnTargetPopUp;

	@FindBy(xpath = "//*[@col-id='ruleName']")
	private List<WebElement> preRuleNamePresentation;

	@FindBy(xpath = "//div[@id='tax-assist-condition-fun']/div/select[@class='form-control dropDownSelect']")
	private WebElement functionField;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "//div[@class='modalCustome modalShow2 react-draggable']")
	private WebElement sourcePopUp;

	@FindBy(xpath = "//input[@id='SRC1']")
	private WebElement searchOnSourceToBePopUp;

	@FindBy(xpath = "//button[@id='undoRuleCondition']")
	private WebElement refreshConditionSetButton;

	@FindBy(xpath = "//button[@id='undoRuleCondition-ref']")
	private WebElement refreshFunctionButton;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']/div[1]")
	private WebElement sourceNumberTwoConcatConditionSet;

	@FindBy(xpath = "(//div[@class='modalCustome modalShow2']/div/div[2]/div/div[2]/div/label)[2]")
	private WebElement radioButtonSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div/button[contains(text(),'Select')]")
	private WebElement saveSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div[contains(text(),'Constant')]/ancestor::div/div[2]/div/input")
	private WebElement constantTextBox;

	@FindBy(xpath = "//div[contains(text(),'Delimiter')]/ancestor::div/div/div/input")
	private WebElement delimiterSplitFunctionTextBox;

	@FindBy(xpath = "//div[contains(text(),'Index Value')]/ancestor::div/div/div/input")
	private WebElement indexValueSplitFunctionTextBox;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[last()]")
	private WebElement saveOnMainsAndConclusions;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlagRules;

	/**
	 * Add Rule with passed condition for MAP function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addMapFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "MAP");
		selectValueForSource("Application Short Name");
		selectValueForTarget("Flexfield Code01");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(preRuleNamePresentation));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Rule with passed condition for UPPER function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addUpperFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "UPPER");
		selectValueForSource("Transaction Number");
		selectValueForTarget("Transaction Number");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(preRuleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Rule with passed condition for LOWER function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addLowerFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "LOWER");
		selectValueForSource("Transaction Number");
		selectValueForTarget("Transaction Number");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(preRuleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Rule with passed condition for CONCAT function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addConcatFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "CONCAT");
		firstPayloadSourceToConcat("Transaction Number");
		secondPayloadSourceToConcat("Transaction ID");
		selectValueForTargetForConcatenation("Transaction Type Name");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		jsWaiter.sleep(5000);

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName, preRuleNamePresentation);
			jsWaiter.sleep(5000);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
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
		jsWaiter.sleep(100);
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
		text.enterText(searchOnSourceToBePopUp, SecondPayload);
		selectTargetRadioButton(SecondPayload);
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

		jsWaiter.sleep(5000);
		click.clickElement(selectButtonTargetConclusionsPopUp);
	}

	/**
	 * Add rule with passed condition for Constant function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addConstantFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "CONSTANT");
		selectValueForTarget("Flexfield Code01");
		enterValueForConstant("Automated invoice");
		jsWaiter.sleep(5000);
		click.clickElement(saveOnConclusions);

		jsWaiter.sleep(5000);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Rule with passed condition for NVL function, Place a CONSTANT value
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addNVLFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "NVL");
		selectValueForSourceNVL("Bill From TAID");
		enterValueForConstant("MGNVL2812");
		selectValueForTarget("Bill From Geography Type01");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		jsWaiter.sleep(10000);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Rule with passed condition for Split function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addSplitFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "SPLIT");
		selectValueForSource("GL Account String");
		text.enterText(delimiterSplitFunctionTextBox, "-");
		VertexLogger.log("Delimiter : - ");
		wait.waitForElementDisplayed(indexValueSplitFunctionTextBox);
		text.enterText(indexValueSplitFunctionTextBox, "3");
		VertexLogger.log("Index Value : 3");
		selectValueForTarget("Purchase / Product Code");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(preRuleNamePresentation));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Add Pre-Rule with passed condition for Sub String function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return String
	 */
	public String addSubstringPreCalcFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "SUBSTRING");
		selectValueForSource("Internal Organization ID");
		text.enterText(startingPositionSubstringBox, "4");
		VertexLogger.log("Entered starting position : 4");
		wait.waitForElementDisplayed(endingPositionSubstringBox);
		text.enterText(endingPositionSubstringBox, "6");
		VertexLogger.log("Entered ending position : 6");
		selectValueForTarget("Ship From TAID");
		wait.waitForElementDisplayed(saveOnMainsAndConclusions);
		click.clickElement(saveOnMainsAndConclusions);

		jsWaiter.sleep(10000);

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " is verified in the Summary Table");
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return ruleName;
	}

	/**
	 * Verify title of Rules Mapping Page in Taxlink application
	 */
	public void verifyTitleRulesMappingPage( )
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
		clickOnRulesMapping();
		verifyTitleRulesMappingPage();
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

		wait.waitForElementDisplayed(enabledFlagRules);
		checkbox.isCheckboxChecked(enabledFlagRules);

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
	 * Method to search data in summary table of Rules Mapping page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		fluentWait.until(driver -> summaryPageTitle);
		Optional<WebElement> dataFound = preRuleNamePresentation
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
		expWait.until(ExpectedConditions.visibilityOf(conditionSetPreRuleMappingDropdown));
		dropdown.selectDropdownByDisplayName(conditionSetPreRuleMappingDropdown, conditionSetValue);
		VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected!!");
		String conditionSetSelected = dropdown
			.getDropdownSelectedOption(conditionSetPreRuleMappingDropdown)
			.getText();
		conditionSetSelected.equals(conditionSetValue);
		click.clickElement(refreshConditionSetButton);
		String conditionSetPostRefresh = dropdown
			.getDropdownSelectedOption(conditionSetPreRuleMappingDropdown)
			.getText();
		if ( conditionSetPostRefresh.isEmpty() )
		{
			VertexLogger.log("Condition Set " + conditionSetValue + " has been refreshed.. No value is present!!");
		}
		else
		{
			VertexLogger.log("Unable to refresh the Condition Set Value!!");
		}
		dropdown.selectDropdownByDisplayName(conditionSetPreRuleMappingDropdown, conditionSetValue);
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
	}

	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		jsWaiter.sleep(5000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

	public void selectTargetRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[last()]", value));
		expWait.until(ExpectedConditions.visibilityOfElementLocated(radioButton));
		jsWaiter.sleep(1000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Target Value: " + value);
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
	}
}