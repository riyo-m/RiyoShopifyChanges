package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui_e2e.enums.InventoryRulesMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Pre-Calc INV Rules Mapping Tab contained
 * in INV Rules Mapping module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPreCalcInvRulesMappingPage extends TaxLinkBasePage
{
	public TaxLinkPreCalcInvRulesMappingPage( final WebDriver driver ) throws IOException
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

	@FindBy(xpath = "//div[@id='tax-assist-condition-fun']/div/select[@class='form-control dropDownSelect']")
	private WebElement functionField;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;
	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement warningPopup;

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

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/inventoryRules']")
	protected WebElement invPreRulesButton;

	/**
	 * Verify title of INV Pre-Calc Rules Mapping Page in Taxlink application
	 */
	public void verifyTitleInvPreRulesMappingPage( )
	{
		String rulesMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		rulesMappingTitle.equalsIgnoreCase(
			InventoryRulesMapping.INVENTORY_RULES_MAPPING.headerPreCalcInvRulesMappingPage);
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
	 * Add Pre-Calc INV Rule with passed condition and function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param invSrcVal
	 * @param invTargetVal
	 * @param invConstantVal
	 *
	 * @return String
	 */
	public String addAndVerifyInvPreCalcRule( String functionValue, String conditionSetValue, String invSrcVal,
		String invTargetVal, String invConstantVal )
	{
		navigateToInvPreRulesMappingPage();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);
		jsWaiter.sleep(2000);
		switch ( functionValue )
		{
			case "MAP":
				selectValueForSource(invSrcVal);
				selectValueForTarget(invTargetVal);
				break;
			case "CONSTANT":
				selectValueForTarget(invTargetVal);
				enterValueForConstant(invConstantVal);
				break;
			case "SUBSTRING":
				selectValueForSource(invSrcVal);
				jsWaiter.sleep(5000);
				text.enterText(startingPositionSubstringBox, "1");
				VertexLogger.log("Entered starting position : 1");
				wait.waitForElementDisplayed(endingPositionSubstringBox);
				text.enterText(endingPositionSubstringBox, "0");
				VertexLogger.log("Entered ending position to check for an error: 0");
				selectValueForTarget(invTargetVal);
				wait.waitForElementDisplayed(saveOnConclusions);
				click.clickElement(saveOnConclusions);
				wait.waitForElementDisplayed(warningPopup);
				if ( positionError() )
				{
					VertexLogger.log("Entering correct ending position!!");
					wait.waitForElementDisplayed(endingPositionSubstringBox);
					endingPositionSubstringBox.clear();
					text.enterText(endingPositionSubstringBox, "40");
					VertexLogger.log("Entered ending position : 40");
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
}