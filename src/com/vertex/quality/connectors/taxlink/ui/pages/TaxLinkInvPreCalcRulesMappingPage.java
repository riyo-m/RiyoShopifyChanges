package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.InventoryRulesMapping;
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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Pre-Calc INV Rules Mapping Tab contained
 * in INV Rules Mapping module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkInvPreCalcRulesMappingPage extends TaxLinkBasePage
{
	public TaxLinkInvPreCalcRulesMappingPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[last()]")
	private WebElement nextConditionTab;

	@FindBy(xpath = "(//button[contains(text(),'CANCEL')])[last()]")
	private WebElement cancel;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[2]")
	private WebElement saveOnConclusions;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[1]")
	private WebElement saveOnMains;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement addPreCalcRuleName;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement addPreRuleOrder;

	@FindBy(xpath = "//div[@id='tax-assist-condition-con']/div/select[@class='form-control dropDownSelect']")
	private WebElement conditionSetRuleMappingDropdown;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfSrc;

	@FindBy(xpath = "//h4[contains(text(),'To: Target')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfTarget;

	@FindBy(xpath = "//input[@id='SRC']")
	private WebElement searchOnSourcePopUp;

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

	@FindBy(xpath = "//div[@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;

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

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']/div[1]")
	private WebElement sourceButtonConclusionsViewEditPages;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']/div[1]")
	private WebElement sourceNumberTwoConcatConditionSet;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;
	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement warningPopup;
	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']/div[1]")
	private WebElement sourceToBeButtonConclusionsViewEditPages;

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

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[last()]")
	private WebElement refreshTargetButton;

	@FindBy(xpath = "(//div[@class=' flyout__item']/button[contains(text(),'View')])[last()]")
	private WebElement viewButtonPerRule;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[2]")
	private WebElement refreshTargetForConcatButton;

	@FindBy(xpath = "//div[@class='modalCustome modalShow2 react-draggable']")
	private WebElement sourcePopUp;
	@FindBy(xpath = "//input[@id='SRC1']")
	private WebElement searchOnSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div/div[2]/div/div[2]/div/label/span")
	private WebElement radioButtonSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div/button[contains(text(),'Select')]")
	private WebElement saveSourceNumberTwoConcatPopUp;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-14']")
	private WebElement sourceToBeButtonConclusions;
	@FindBy(xpath = "//input[@id='SRC1']")
	private WebElement searchOnSourceToBePopUp;
	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[1]")
	private WebElement refreshSourceButtonNVL;
	@FindBy(xpath = "//a[@id='cancelTaxAssistRuleLink_link']")
	private WebElement backButtonConditionsTab;
	@FindBy(xpath = "(//button[contains(text(), 'BACK')])[last()]")
	private WebElement backButtonConclusionsTab;
	@FindBy(xpath = "//div[@col-id='ruleOrder']")
	private List<WebElement> ruleOrderPresentation;
	@FindBy(xpath = "//div[@col-id='enabledFlag']")
	private List<WebElement> enabledPresentation;
	@FindBy(xpath = "//div[@col-id='startDate']")
	private List<WebElement> startDatePresentation;
	@FindBy(xpath = "//div[@col-id='endDate']")
	private List<WebElement> endDatePresentation;
	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[1]")
	private WebElement selectButtonSourceToBeConclusionsNVLPopUp;
	private By threeDotsPerRule = By.xpath(".//following-sibling::div/div/div/div/div/parent::div/button");
	private By editButtonRule = By.xpath(
		".//following-sibling::div/div/div/div/div/div/button[contains(text(), 'Edit')]");

	/**
	 * Verify title of INV Pre-Calc Rules Mapping Page in Taxlink application
	 */
	public void verifyTitleInvPreRulesMappingPage( )
	{
		String rulesMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		rulesMappingTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvPreRuleMappingPage);
	}

	/**
	 * Verify title of View Page INV Pre-Calc Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewInvPreRulesMappingPage( )
	{
		boolean flagViewPreCalcRules = false;
		String rulesMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( rulesMappingTitle.contains(InventoryRulesMapping.RuleMappingDetails.headerViewInvPreRuleMappingPage) )
		{
			flagViewPreCalcRules = true;
		}
		return flagViewPreCalcRules;
	}

	/**
	 * Verify title of Edit Page INV Pre-Calc Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditInvPreRulesMappingPage( )
	{
		boolean flagEditPreCalcRules = false;
		String rulesMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( rulesMappingTitle.contains(InventoryRulesMapping.RuleMappingDetails.headerEditInvPreRuleMappingPage) )
		{
			flagEditPreCalcRules = true;
		}
		return flagEditPreCalcRules;
	}

	/**
	 * Adding the data on Mains Tab for Inventory Rules Mapping in Taxlink UI
	 *
	 * @return invRuleName
	 */

	public String addDataOnMainsTab( )
	{
		String invRuleName;
		String invRuleOrder;

		verifyTitleInvPreRulesMappingPage();
		click.clickElement(addButton);

		invRuleName = utils.alphaNumericWithTimeStampText();
		wait.waitForElementDisplayed(addPreCalcRuleName);
		text.enterText(addPreCalcRuleName, invRuleName);
		invRuleOrder = utils.randomNumber("5");
		wait.waitForElementDisplayed(addPreRuleOrder);
		text.enterText(addPreRuleOrder, invRuleOrder);

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

		List<String> dataEntered = Arrays.asList(invRuleName, invRuleOrder, currDate, nextDate);

		VertexLogger.log("Data Entered for creating new rule is: " + dataEntered);

		click.clickElement(nextMainTab);
		return invRuleName;
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
	 * Select Condition Set and Function to add an INV rule
	 * in Add Pre-calc Inv Rules Mapping Page in Taxlink application
	 *
	 * @param conditionSetValue
	 * @param functionValue
	 */
	public void addConditionSetAndFunctionForRule( String conditionSetValue, String functionValue )
	{
		jsWaiter.sleep(10000);
		dropdown.selectDropdownByDisplayName(conditionSetRuleMappingDropdown, conditionSetValue);
		VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected!!");
		String conditionSetSelected = dropdown
			.getDropdownSelectedOption(conditionSetRuleMappingDropdown)
			.getText();
		if ( conditionSetSelected.equals(conditionSetValue) )
		{
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

	/**
	 * Select Source value in Source field to add an INV rule
	 * in Add Pre-calc Inv Rules Mapping Page in Taxlink application
	 *
	 * @param value
	 */
	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(radioButton);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

	/**
	 * Select Target value in Target field to add an INV rule
	 * in Add Pre-calc Inv Rules Mapping Page in Taxlink application
	 *
	 * @param value
	 */
	public void selectTargetRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[last()]", value));
		jsWaiter.sleep(5000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Target Value: " + value);
	}

	/**
	 * Select Standard Payload Selection for Source on Conclusions tab to add a Pre-calc Inv rule
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @param sourceValue
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
		jsWaiter.sleep(5000);

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
	 * Select Target Value on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @param targetValue
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
	 * Add Inv Pre-Calc Rule with Constant function to check view and edit functionality
	 * in Taxlink Application
	 *
	 * @param conditionSetValue
	 *
	 * @return ruleName
	 */
	public String addConstantFunctionInvPreRule( String functionValue, String conditionSetValue, String targetValue,
		String constantValue )
	{
		js.executeScript("arguments[0].scrollIntoView();", invPreRulesButton);
		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);
		selectValueForTarget(targetValue);
		enterValueForConstant(constantValue);
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);
		VertexLogger.log("Clicked on Save Button!");
		jsWaiter.sleep(5000);
		return ruleName;
	}

	/**
	 * Enter Constant Value on Conclusions tab to add a rule
	 * in Add Inv Pre-calc Rules Mapping Page in Taxlink application
	 *
	 * @param constantValue
	 */
	public void enterValueForConstant( String constantValue )
	{
		wait.waitForElementDisplayed(constantTextBox);
		text.enterText(constantTextBox, constantValue);
		VertexLogger.log("Entered constant value: " + constantValue);
	}

	/**
	 * View INV Pre-Calc Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean viewInvPreCalcRulesMapping( String ruleName )
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
			if ( verifyTitleViewInvPreRulesMappingPage() )
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
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Data is not present in the Summary Table");
		}
		return flagViewRule;
	}

	/**
	 * Add Pre-Calc INV Rule with passed condition and function(CONCAT)
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param invSrcVal
	 * @param invSrcVal1
	 * @param invSrcVal2
	 * @param invTargetVal
	 * @param invConstantVal
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyInvPreCalcRule( String functionValue, String conditionSetValue, String invSrcVal,
		String invSrcVal1, String invSrcVal2, String invTargetVal, String invConstantVal )
	{
		boolean flagAddRule = false, flagSummaryTableVerified = false;
		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);
		jsWaiter.sleep(2000);
		switch ( functionValue )
		{
			case "MAP":
			case "UPPER":
			case "LOWER":
				selectValueForSource(invSrcVal);
				selectValueForTarget(invTargetVal);
				break;
			case "CONSTANT":
				selectValueForTarget(invTargetVal);
				enterValueForConstant(invConstantVal);
				break;
			case "CONCAT":
				firstPayloadSourceToConcat(invSrcVal1);
				secondPayloadSourceToConcat(invSrcVal2);
				selectValueForTargetForConcatenation(invTargetVal);
				break;
			case "SUBSTRING":
				selectValueForSource(invSrcVal);
				jsWaiter.sleep(5000);
				text.enterText(startingPositionSubstringBox, "4");
				VertexLogger.log("Entered starting position : 4");
				wait.waitForElementDisplayed(endingPositionSubstringBox);
				text.enterText(endingPositionSubstringBox, "1");
				VertexLogger.log("Entered ending position to check for an error: 1");
				selectValueForTarget(invTargetVal);
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
					selectValueForTarget(invTargetVal);
				}
				break;
			case "SPLIT":
				selectValueForSource(invSrcVal);
				if ( verifySpecialCharsValOnDelimiter() )
				{
					text.clearText(delimiterSplitFunctionTextBox);
					jsWaiter.sleep(2000);
					text.enterText(delimiterSplitFunctionTextBox, ",");
					VertexLogger.log("Delimiter : , ");
					wait.waitForElementDisplayed(indexValueSplitFunctionTextBox);
					text.enterText(indexValueSplitFunctionTextBox, "4");
					VertexLogger.log("Index Value : 4");
					selectValueForTarget(invTargetVal);
				}
				break;
		}
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);
			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " using " + functionValue + " function and " +
								 conditionSetValue + "condition is verified in the Summary Table");
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
			flagAddRule = true;
		}
		return flagAddRule;
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
	 * in Add INV PRECALC Rules Mapping Page in Taxlink application
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
		if ( targetSelected.equals(targetValue) )
		{
			click.clickElement(refreshTargetForConcatButton);
			String targetPostRefresh = targetButtonConclusions.getText();
			if ( targetPostRefresh.isEmpty() )
			{
				VertexLogger.log("Target value has been refreshed.. No value is present!!");
			}
			else
			{
				VertexLogger.log("Unable to refresh the Target value!!");
			}
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
	 * Verify an error message for alphanumeric values
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
	 * Add INV Pre-Rule with passed condition for NVL function, place another source value
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param nullVal
	 * @param replaceSource
	 * @param targetVal
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyNvlFunction_placeSourceValPreRule( String functionValue, String conditionSetValue,
		String nullVal, String replaceSource, String targetVal )
	{
		boolean flagAddNVLSrcFunctionRule = false;
		boolean flagSummaryTableVerified = false;
		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);
		selectValueForSourceNVL(nullVal);
		selectValueToBeFilledFromSource(replaceSource);
		if ( !constantTextBox.isEnabled() )
		{
			VertexLogger.log("NVL function is disabled for Constant text box");
		}
		jsWaiter.sleep(2000);
		selectValueForTarget(targetVal);
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);
		jsWaiter.sleep(10000);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " using " + functionValue + " function and " +
								 conditionSetValue + "condition is verified in the Summary Table");
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
	 * Add INV Pre-Rule with passed condition for NVL function, Place a CONSTANT value
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param nullVal
	 * @param replaceConstant
	 * @param targetVal
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyNvlFunction_placeConstantValPreRule( String functionValue, String conditionSetValue,
		String nullVal, String replaceConstant, String targetVal )
	{
		boolean flagAddNVLConstFunctionRule = false;
		boolean flagSummaryTableVerified = false;
		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);

		selectValueForSourceNVL(nullVal);
		enterValueForConstant(replaceConstant);
		if ( !sourceToBeButtonConclusions.isEnabled() )
		{
			VertexLogger.log("NVL function is disabled for other Source value text box");
		}
		selectValueForTarget(targetVal);
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(ruleName);

			if ( data.isPresent() )
			{
				VertexLogger.log("New Rule added with Name " + ruleName + " using " + functionValue + " function and " +
								 conditionSetValue + "condition is verified in the Summary Table");
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
		return flagAddNVLConstFunctionRule;
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
	 * View INV Pre-calc Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean viewPreCalcInvRulesMapping( String ruleName )
	{
		boolean flagViewRule = false;
		jsWaiter.sleep(5000);

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
			if ( verifyTitleViewInvPreRulesMappingPage() )
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
	 * Edit Pre-Calc INV Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean editPreCalcInvRulesMapping( String ruleName )
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

		if ( verifyTitleEditInvPreRulesMappingPage() )
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

	/**
	 * Verify Edit INV Precalc Rules Functionality in Taxlink application
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
		wait.waitForElementDisplayed(addPreRuleOrder);
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
		}
		return flagEditFunctionalityRule;
	}

	/**
	 * Verify Export to CSV functionality on Inv Pre-Calc Rules Mapping page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVPreCalcInvRulesMapping( String instName ) throws IOException
	{
		boolean flagExportToCSV = false;
		boolean flagInstanceMatch = false;
		boolean ruleOrderFlag = false;
		boolean startDateFlag = false;
		boolean endDateFlag = false;
		boolean enabledFlag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		String fileName = "Pre-Calculation_Inventory_Rules_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToInvPreRulesMappingPage();
		verifyTitleInvPreRulesMappingPage();
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
}