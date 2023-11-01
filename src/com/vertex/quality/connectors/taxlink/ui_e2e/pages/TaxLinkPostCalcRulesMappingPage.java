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
 * this class represents all the end to end test cases for Post-Rules Mapping Module Tab containing
 * Rules Mapping tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPostCalcRulesMappingPage extends TaxLinkBasePage
{

	public TaxLinkPostCalcRulesMappingPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(text(),'Condition Set')]/ancestor::div/div/select[@class='form-control dropDownSelect']")
	private WebElement conditionSetPreRuleMappingDropdown;

	@FindBy(xpath = "//button[@id='undoRuleCondition']")
	private WebElement refreshConditionSetButton;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[last()]")
	private WebElement nextConditionTab;

	@FindBy(xpath = "(//button[contains(text(),'CANCEL')])[last()]")
	private WebElement cancel;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[1]")
	private WebElement saveOnConclusions;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement addPostRuleMappingName;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement addPostRuleMappingOrder;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDateSelectedValue;

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

	@FindBy(xpath = "//div[@class='ag-body-viewport ag-layout-normal ag-row-no-animation']")
	private WebElement presentationRow;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;

	@FindBy(xpath = "//div[@col-id='ruleOrder']")
	private List<WebElement> ruleOrderPresentation;

	@FindBy(xpath = "//label[contains(text(),'Function')]/ancestor::div/div/select[@class='form-control dropDownSelect']")
	private WebElement functionField;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']/div[1]")
	private WebElement sourceButtonConclusionsViewEditPages;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='ruleName']")
	private List<WebElement> postRuleNamePresentation;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[1]")
	private WebElement saveOnMainsAndConclusions;

	@FindBy(xpath = "//button[contains(text(), 'SAVE')]")
	private WebElement saveButton;

	@FindBy(xpath = "//div[@class='ag-overlay']")
	private WebElement noElementToDisplay;

	@FindBy(xpath = "//input[@name='enabledFlag']")
	private WebElement enabledFlagPostCalcRules;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlagPreCalcRules;

	private By column_Enabled = By.xpath(".//following-sibling::div[3]");
	private By editButtonPostCalc = By.xpath(
		".//ancestor::div/div/div/div/div/div/div/button[@class='flyout__link optionFont']");

	/**
	 * Add Rule with passed condition for MAP function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param conditionSetValue
	 *
	 * @return String
	 */

	public String addMapPostCalcFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "MAP");
		selectValueForSource("Situs");
		selectValueForTarget("Attribute02");
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(postRuleNamePresentation));

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
	 * Add Post-Rule with passed condition for Sub String function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param conditionSetValue
	 *
	 * @return String
	 */
	public String addSubstringPostCalcFunctionRule( String conditionSetValue )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, "SUBSTRING");
		selectValueForSource("Situs");
		text.enterText(startingPositionSubstringBox, "4");
		VertexLogger.log("Entered starting position : 4");
		wait.waitForElementDisplayed(endingPositionSubstringBox);
		text.enterText(endingPositionSubstringBox, "6");
		VertexLogger.log("Entered starting position : 6");
		selectValueForTarget("Attribute02");
		wait.waitForElementDisplayed(saveOnMainsAndConclusions);
		click.clickElement(saveOnMainsAndConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

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
		rulesMappingTitle.equalsIgnoreCase(RulesMapping.RuleMappingDetails.headerPostRuleMappingPage);
	}

	/**
	 * Adding the data on Mains Tab for Rules Mapping in Taxlink UI
	 */

	public String addDataOnMainsTab( )
	{
		String ruleName;
		String ruleOrder;
		clickOnPostCalcRulesMapping();
		verifyTitleRulesMappingPage();
		click.clickElement(addButton);

		ruleName = utils.alphaNumericWithTimeStampText();
		wait.waitForElementDisplayed(addPostRuleMappingName);
		text.enterText(addPostRuleMappingName, ruleName);
		ruleOrder = utils.randomNumber("5");
		wait.waitForElementDisplayed(addPostRuleMappingOrder);
		text.enterText(addPostRuleMappingOrder, ruleOrder);

		click.clickElement(endDate);

		String nextDate = utils.getNextDayDate();
		VertexLogger.log("End Date: " + nextDate);
		text.enterText(endDate, nextDate);

		wait.waitForElementDisplayed(enabledFlagPostCalcRules);
		checkbox.isCheckboxChecked(enabledFlagPostCalcRules);

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
	 * @param text
	 *
	 * @return Optional<WebElement>
	 */

	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = postRuleNamePresentation
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
	 *
	 * @param conditionSetValue
	 * @param functionValue
	 */

	public void addConditionSetAndFunctionForRule( String conditionSetValue, String functionValue )
	{
		expWait.until(ExpectedConditions.visibilityOf(conditionSetPreRuleMappingDropdown));
		dropdown.selectDropdownByDisplayName(conditionSetPreRuleMappingDropdown, conditionSetValue);
		VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected!!");
		String conditionSetSelected = dropdown
			.getDropdownSelectedOption(conditionSetPreRuleMappingDropdown)
			.getText();
		if ( conditionSetSelected.equals(conditionSetValue) )
		{
			dropdown.selectDropdownByDisplayName(conditionSetPreRuleMappingDropdown, conditionSetValue);
			VertexLogger.log("Condition Set Value " + conditionSetValue + " has been selected post refresh!!");
		}
		dropdown.selectDropdownByDisplayName(functionField, functionValue);
		VertexLogger.log("Function " + functionValue + " has been selected!!");
		String functionSelected = dropdown
			.getDropdownSelectedOption(functionField)
			.getText();
		if ( functionSelected.equals(functionValue) )
		{
			dropdown.selectDropdownByDisplayName(functionField, functionValue);
			VertexLogger.log("Function " + functionValue + " has been selected post refresh!!");
		}
		click.clickElement(nextConditionTab);
	}

	/**
	 * Select Standard Payload Selection for Source on Conclusions tab to add a rule
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
	}

	/**
	 * Select radio button for Source on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @param value
	 */

	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		wait.waitForTextInElement(radioButton, value);
		jsWaiter.sleep(1000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

	/**
	 * Select radio button for Target on Conclusions tab to add a rule
	 * in Add Rules Mapping Page in Taxlink application
	 *
	 * @param value
	 */

	public void selectTargetRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[last()]", value));
		expWait.until(ExpectedConditions.visibilityOfElementLocated(radioButton));
		jsWaiter.sleep(1000);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Target Value: " + value);
	}

	/**
	 * Method to disable all existing (Precalc/Postcalc) calc rules
	 * for (Precalc/Postcalc) calc rules mapping tab in TaxLink UI
	 * reason being Invoice won't get validated in ERP until and unless
	 * these records are disabled
	 *
	 * @return boolean
	 */
	public boolean disableRules( String calcType )
	{
		boolean finalFlag = false;

		if ( calcType.equals("POSTCALC") )
		{
			if(postRulesButton.isDisplayed())
			{
				expWait.until(ExpectedConditions.visibilityOf(postRulesButton));
				click.clickElement(postRulesButton);
			}
			else
			{
				navigateToPostCalcRulesMappingPage();
			}
			if ( disablePostCalcRules() )
			{
				finalFlag = true;
			}
		}
		else if ( calcType.equals("PRECALC") )
		{
			navigateToPreCalcRulesMappingPage();
			if ( disablePreCalcRules() )
			{
				finalFlag = true;
			}
		}
		return finalFlag;
	}

	/**
	 * Method to disable all existing (Precalc) rules
	 * depending on the tab passed as an argument
	 *
	 * @return boolean
	 */
	public boolean disablePreCalcRules( )
	{
		boolean finalFlag = false;
		wait.waitForElementDisplayed(externalFilter, 60);

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		mainloop:
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < postRuleNamePresentation.size() ; )
			{
				expWait.until(ExpectedConditions.visibilityOfAllElements(postRuleNamePresentation));
				if ( postRuleNamePresentation
						 .get(j)
						 .findElement(column_Enabled)
						 .getText()
						 .equals("Y") && !(postRuleNamePresentation
					.get(j)
					.getText()
					.contains("O2C")) && !(postRuleNamePresentation
					.get(j)
					.getText()
					.contains("P2P")) )
				{
					jsWaiter.sleep(5000);
					if ( !(postRuleNamePresentation
						.get(j)
						.isDisplayed()) )
					{
						js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					}
					postRuleNamePresentation
						.get(j)
						.findElement(editButtonPostCalc)
						.click();
					VertexLogger.log("Clicked on Edit button");
					if ( enabledFlagPreCalcRules.isSelected() )
					{
						jsWaiter.sleep(2000);
						click.clickElement(enabledFlagPreCalcRules);
						VertexLogger.log("Disabling the rule!!");
						click.clickElement(saveButton);
						finalFlag = true;
						jsWaiter.sleep(2000);
					}
				}
				else
				{
					finalFlag = true;
					VertexLogger.log("No pre calc rule is enabled");
					break mainloop;
				}
			}
		}
		if ( count == 0 )
		{
			finalFlag = true;
			VertexLogger.log("No Pre Calc rules are present!!");
		}
		return finalFlag;
	}

	/**
	 * Method to disable all existing (Postcalc) rules
	 * depending on the tab passed as an argument
	 *
	 * @return boolean
	 */
	public boolean disablePostCalcRules( )
	{
		boolean finalFlag = false;
		wait.waitForElementDisplayed(externalFilter, 60);

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		mainloop:
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < postRuleNamePresentation.size() ; )
			{
				expWait.until(ExpectedConditions.visibilityOfAllElements(postRuleNamePresentation));
				if ( postRuleNamePresentation
						 .get(j)
						 .findElement(column_Enabled)
						 .getText()
						 .equals("Y") && !(postRuleNamePresentation
					.get(j)
					.getText()
					.contains("O2C")) && !(postRuleNamePresentation
					.get(j)
					.getText()
					.contains("P2P")) )
				{
					jsWaiter.sleep(5000);
					if ( !(postRuleNamePresentation
						.get(j)
						.isDisplayed()) )
					{
						js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					}
					postRuleNamePresentation
						.get(j)
						.findElement(editButtonPostCalc)
						.click();
					VertexLogger.log("Clicked on Edit button");
					if ( enabledFlagPostCalcRules.isSelected() )
					{
						jsWaiter.sleep(2000);
						click.clickElement(enabledFlagPostCalcRules);
						VertexLogger.log("Disabling the rule!!");
						click.clickElement(saveButton);
						finalFlag = true;
						jsWaiter.sleep(2000);
					}
				}
				else
				{
					finalFlag = true;
					VertexLogger.log("No post calc rule is enabled");
					break mainloop;
				}
			}
		}
		if ( count == 0 )
		{
			finalFlag = true;
			VertexLogger.log("No Post Calc rules are present!!");
		}
		return finalFlag;
	}
}