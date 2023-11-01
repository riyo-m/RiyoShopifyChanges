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
 * this class represents all the locators and methods for Project output files
 * in INV Rules Mapping module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkProjectOutputFilePage extends TaxLinkBasePage
{
	public TaxLinkProjectOutputFilePage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement ruleNameField;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement ruleOrderField;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[last()]")
	private WebElement nextConditionTab;
	@FindBy(xpath = "//label[contains(text(),'Condition Set')]/ancestor::div/div/select")
	private WebElement conditionSetRuleMappingDropdown;

	@FindBy(xpath = "//label[contains(text(),'Function')]/ancestor::div/div/select")
	private WebElement functionField;

	@FindBy(xpath = "//h4[contains(text(),'Set: Source')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfSrc;

	@FindBy(xpath = "//h4[contains(text(),'To: Target')]/ancestor::div[3]")
	private WebElement popUpConclusionsTabOfTarget;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']")
	private WebElement sourceButtonConclusions;

	@FindBy(xpath = "//input[@id='SRC']")
	private WebElement searchOnSourcePopUp;

	@FindBy(xpath = "//button[@id='undoProjectOutputFileCondition']")
	private WebElement refreshConditionSetButton;

	@FindBy(xpath = "//button[@id='undoProjectOutputFileFunction']")
	private WebElement refreshFunctionButton;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[1]")
	private WebElement refreshSourceButton;

	@FindBy(xpath = "(//button[@name='rule-conclusion-undo'])[last()]")
	private WebElement refreshTargetButton;

	@FindBy(xpath = "(//div[contains(@class,'treeView_treeViewRow')]/div[2]/div/label)[1]")
	private WebElement radioButtonSetSourcePostRefreshPopUp;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-value-tar']/div[1]")
	private WebElement targetButtonConclusions;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[last()]")
	private WebElement selectButtonTargetConclusionsPopUp;

	@FindBy(xpath = "(//div/button[contains(text(),'Select')])[1]")
	private WebElement selectButtonSourceConclusionsPopUp;

	@FindBy(xpath = "//input[@id='TGT']")
	private WebElement searchOnTargetPopUp;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;

	@FindBy(xpath = "//div[@col-id='ruleOrder']")
	private List<WebElement> ruleOrderPresentation;

	@FindBy(xpath = "//div[@col-id='enabledFlag']")
	private List<WebElement> enabledPresentation;

	@FindBy(xpath = "//div[@col-id='startDate']")
	private List<WebElement> startDatePresentation;

	@FindBy(xpath = "//div[@col-id='endDate']")
	private List<WebElement> endDatePresentation;

	@FindBy(xpath = "//div[contains(text(),'Starting Position')]/ancestor::div/div[2]/div/input")
	private WebElement startingPositionSubstringBox;

	@FindBy(xpath = "//div[contains(text(),'Ending Position')]/ancestor::div/div[2]/div/input")
	private WebElement endingPositionSubstringBox;

	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement warningPopup;

	@FindBy(xpath = "//button[contains(text(),'SAVE')]")
	private WebElement saveOnConclusions;
	@FindBy(xpath = "(//button[contains(text(),'Cancel')])[last()]")
	private WebElement cancel;

	@FindBy(tagName = "h2")
	public WebElement viewEditProjectRulePageTitle;

	@FindBy(xpath = "(//div[@class=' flyout__item']/button[contains(text(),'View')])[last()]")
	private WebElement viewButtonPerRule;

	@FindBy(xpath = "//div[@id='tax-assist-conclusion-field-0']/div[1]")
	private WebElement sourceButtonViewConclusions;

	@FindBy(xpath = "//button[contains(text(), 'Back')]")
	private WebElement backButtonConditionsTab;

	@FindBy(xpath = "(//button[contains(text(), 'BACK')])[last()]")
	private WebElement backButtonConclusionsTab;

	@FindBy(xpath = "(//button[contains(text(),'SAVE')])[1]")
	private WebElement saveOnMainsAndConclusions;
	private By editButtonRule = By.xpath(
		".//following-sibling::div/div/div/div/div/div/button[contains(text(), 'Edit')]");
	private By threeDotsPerRule = By.xpath(".//following-sibling::div/div/div/div/div/parent::div/button");

	/**
	 * Verify title of Inventory Project Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleProjectRulesPage( )
	{
		boolean titleFlag = false;
		wait.waitForElementDisplayed(summaryPageTitle);
		String rulesMappingTitle = summaryPageTitle.getText();
		if ( rulesMappingTitle.equalsIgnoreCase(
			InventoryRulesMapping.RuleMappingDetails.headerInvProjectRuleMappingPage) )
		{
			titleFlag = true;
		}
		return titleFlag;
	}

	/**
	 * Verify title of View Project Output - INV Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewProjectINVRulesMappingPage( )
	{
		boolean flagTitleViewProjectRule = false;
		String viewRuleTitle = wait
			.waitForElementDisplayed(viewEditProjectRulePageTitle)
			.getText();
		boolean verifyFlag = viewRuleTitle.contains(
			InventoryRulesMapping.RuleMappingDetails.headerViewInvProjectRuleMappingPage);

		if ( verifyFlag )
		{
			flagTitleViewProjectRule = true;
		}
		return flagTitleViewProjectRule;
	}

	/**
	 * Verify title of Edit Project Output - INV Rules Mapping Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditProjectRulesMappingPage( )
	{
		boolean flagTitleViewProjectRule = false;
		String viewRuleTitle = wait
			.waitForElementDisplayed(viewEditProjectRulePageTitle)
			.getText();
		boolean verifyFlag = viewRuleTitle.contains(
			InventoryRulesMapping.RuleMappingDetails.headerEditInvProjectRuleMappingPage);

		if ( verifyFlag )
		{
			flagTitleViewProjectRule = true;
		}
		return flagTitleViewProjectRule;
	}

	/**
	 * Adding the data on Mains Tab for Inv Rules Mapping in Taxlink UI
	 *
	 * @return string
	 */

	public String addDataOnMainsTab( )
	{
		String ruleName = null;
		String ruleOrder;

		if ( verifyTitleProjectRulesPage() )
		{
			click.clickElement(addButton);

			ruleName = utils.alphaNumericWithTimeStampText();
			wait.waitForElementDisplayed(ruleNameField);
			text.enterText(ruleNameField, ruleName);
			ruleOrder = utils.randomNumber("5");
			wait.waitForElementDisplayed(ruleOrderField);
			text.enterText(ruleOrderField, ruleOrder);

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
		}
		return ruleName;
	}

	/**
	 * Select Condition Set and Function to add a Inv Project output rule
	 * in Add Rule Page in Taxlink application
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
		if ( functionSelected.equals(functionValue) )
		{
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
		}
		dropdown.selectDropdownByDisplayName(functionField, functionValue);
		VertexLogger.log("Function " + functionValue + " has been selected post refresh!!");
		click.clickElement(nextConditionTab);
	}

	/**
	 * Click on Source radio button on Source pop up to add a Inv Project output rule
	 * in Add Rule Page in Taxlink application
	 *
	 * @param value
	 */
	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		wait.waitForElementDisplayed(radioButton);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

	/**
	 * Click on Target radio button on Taregt pop up to add a Inv Project output rule
	 * in Add Rule Page in Taxlink application
	 *
	 * @param value
	 */
	public void selectTargetRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[last()]", value));
		expWait.until(ExpectedConditions.visibilityOfElementLocated(radioButton));
		click.clickElement(radioButton);
		VertexLogger.log("Selected Target Value: " + value);
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

		wait.waitForElementDisplayed(sourceButtonConclusions);
		String sourceSelected = sourceButtonConclusions.getText();
		if ( sourceSelected.equals(sourceValue) )
		{
			jsWaiter.sleep(10000);
			click.clickElement(refreshSourceButton);
			expWait.until(ExpectedConditions.visibilityOf(sourceButtonConclusions));
			String sourcePostRefresh = sourceButtonConclusions.getText();
			if ( sourcePostRefresh.isEmpty() )
			{
				VertexLogger.log("Source has been refreshed.. No value is present!!");
			}
			else
			{
				VertexLogger.log("Unable to refresh the Source value!!");
			}
		}
		click.clickElement(sourceButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfSrc);
		text.enterText(searchOnSourcePopUp, sourceValue);
		jsWaiter.sleep(1500);
		wait.waitForElementDisplayed(radioButtonSetSourcePostRefreshPopUp);

		if ( radioButtonSetSourcePostRefreshPopUp
			.getAttribute("for")
			.equals(sourceValue) )
		{
			wait.waitForTextInElement(radioButtonSetSourcePostRefreshPopUp, sourceValue);
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
		expWait.until(ExpectedConditions.visibilityOf(targetButtonConclusions));
		click.clickElement(targetButtonConclusions);
		wait.waitForElementDisplayed(popUpConclusionsTabOfTarget);
		text.enterText(searchOnTargetPopUp, targetValue);

		selectTargetRadioButton(targetValue);
		wait.waitForElementEnabled(selectButtonTargetConclusionsPopUp);
		click.clickElement(selectButtonTargetConclusionsPopUp);

		String targetSelected = targetButtonConclusions.getText();
		if ( targetSelected.equals(targetValue) )
		{
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
	 * Method to search data in summary table of Rules Mapping page
	 *
	 * @param text
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = ruleNamePresentation
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();
		return dataFound;
	}

	/**
	 * Add INV Project Rule with passed condition and function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param invSrcVal
	 * @param invTargetVal
	 * @param startIndex
	 * @param endIndex
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyInvProjectRule( String functionValue, String conditionSetValue, String invSrcVal,
		String invTargetVal, String startIndex, String endIndex )
	{
		boolean flagAddRule = false, flagSummaryTableVerified = false;
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);

		switch ( functionValue )
		{
			case "MAP":
				selectValueForSource(invSrcVal);
				selectValueForTarget(invTargetVal);
				break;
			case "SUBSTRING":
				selectValueForSource(invSrcVal);
				jsWaiter.sleep(5000);
				text.enterText(startingPositionSubstringBox, startIndex);
				VertexLogger.log("Entered starting position : " + startIndex);
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
					text.enterText(endingPositionSubstringBox, endIndex);
					VertexLogger.log("Entered ending position : " + endIndex);
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
	 * Add INV Project Rule with passed condition and function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param invSrcVal
	 * @param invTargetVal
	 * @param startIndex
	 * @param endIndex
	 *
	 * @return boolean
	 */
	public String addAndVerifyInvProjectRuleToViewOrEdit( String functionValue, String conditionSetValue,
		String invSrcVal, String invTargetVal, String startIndex, String endIndex )
	{
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);

		switch ( functionValue )
		{
			case "MAP":
				selectValueForSource(invSrcVal);
				selectValueForTarget(invTargetVal);
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
		}
		wait.waitForElementDisplayed(saveOnConclusions);
		click.clickElement(saveOnConclusions);

		expWait.until(ExpectedConditions.visibilityOfAllElements(ruleNamePresentation));

		return ruleName;
	}

	/**
	 * View Project output - INV Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewProjectOutputRulesMapping( String ruleName )
	{
		boolean flagViewProjectRule = false;

		expWait.until(ExpectedConditions.visibilityOf(invProjectOutputButton));
		click.clickElement(invProjectOutputButton);
		if ( verifyTitleProjectRulesPage() )
		{
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Both");

			jsWaiter.sleep(2000);
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
		}
		try
		{
			if ( verifyTitleViewProjectINVRulesMappingPage() )
			{
				boolean ruleNameViewEditPagesFlag = !ruleNameField.isEnabled();
				VertexLogger.log(String.valueOf(ruleNameViewEditPagesFlag));
				boolean ruleNameTextViewEditPagesFlag = !ruleNameField
					.getAttribute("value")
					.isBlank();

				boolean ruleMappingOrderViewEditPagesFlag = !ruleOrderField.isEnabled();
				VertexLogger.log(String.valueOf(ruleMappingOrderViewEditPagesFlag));
				boolean ruleMappingOrderTextViewEditPagesFlag = !ruleOrderField
					.getAttribute("value")
					.isBlank();

				boolean ruleStartDateViewEditPagesFlag = !startDate.isEnabled();
				VertexLogger.log(String.valueOf(ruleStartDateViewEditPagesFlag));
				boolean ruleStartDateTextViewEditPagesFlag = !startDate
					.getAttribute("value")
					.isBlank();

				boolean ruleEndDateViewEditPagesFlag = !endDate.isEnabled();
				VertexLogger.log(String.valueOf(ruleEndDateViewEditPagesFlag));
				boolean ruleEnabledFlagRulesViewEditPagesFlag = !enabledFlag.isEnabled();
				VertexLogger.log(String.valueOf(ruleEnabledFlagRulesViewEditPagesFlag));
				boolean cancelFlag = cancel.isEnabled();
				VertexLogger.log(String.valueOf(cancelFlag));

				click.clickElement(nextMainTab);
				wait.waitForElementDisplayed(conditionSetRuleMappingDropdown);
				boolean conditionSetFieldFlag = !conditionSetRuleMappingDropdown.isEnabled();
				VertexLogger.log(String.valueOf(conditionSetFieldFlag));

				boolean functionFieldFlag = !functionField.isEnabled();
				VertexLogger.log(String.valueOf(functionFieldFlag));

				jsWaiter.sleep(5000);

				boolean condSetTextDisabled = !dropdown
					.getDropdownSelectedOption(conditionSetRuleMappingDropdown)
					.getText()
					.isBlank();

				jsWaiter.sleep(5000);
				String function = dropdown
					.getDropdownSelectedOption(functionField)
					.getText();
				VertexLogger.log("Function used for rule : " + ruleName + " is " + function);

				click.clickElement(nextConditionTab);
				expWait.until(ExpectedConditions.elementToBeClickable(cancel));

				switch ( function )
				{
					case "MAP":
						if ( sourceButtonViewConclusions
								 .getAttribute("class")
								 .contains("Disabled") && targetButtonConclusions
								 .getAttribute("class")
								 .contains("Disabled") )
						{
							VertexLogger.log(String.valueOf(true));
						}
						break;
					case "SUBSTRING":
						if ( sourceButtonViewConclusions
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
				}
				if ( ruleNameViewEditPagesFlag && ruleMappingOrderViewEditPagesFlag && ruleStartDateViewEditPagesFlag &&
					 ruleEndDateViewEditPagesFlag && ruleEnabledFlagRulesViewEditPagesFlag && cancelFlag &&
					 conditionSetFieldFlag && functionFieldFlag && condSetTextDisabled &&
					 ruleNameTextViewEditPagesFlag && ruleMappingOrderTextViewEditPagesFlag &&
					 ruleStartDateTextViewEditPagesFlag )
				{
					flagViewProjectRule = true;
				}
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Data is not present in the Summary Table");
		}
		return flagViewProjectRule;
	}

	/**
	 * Edit Project output INV Rules Mapping for a rule
	 * in Taxlink Application
	 *
	 * @param ruleName
	 *
	 * @return boolean
	 */
	public boolean editProjectOutputRulesMapping( String ruleName )
	{
		boolean flagEditProjectRule = false;

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(15000);
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

		if ( verifyTitleEditProjectRulesMappingPage() )
		{
			boolean ruleNameViewEditPagesFlag = ruleNameField.isEnabled();
			VertexLogger.log(String.valueOf(ruleNameViewEditPagesFlag));
			boolean ruleMappingOrderViewEditPagesFlag = ruleOrderField.isEnabled();
			VertexLogger.log(String.valueOf(ruleMappingOrderViewEditPagesFlag));
			boolean ruleStartDateViewEditPagesFlag = !startDate.isEnabled();
			VertexLogger.log(String.valueOf(ruleStartDateViewEditPagesFlag));
			boolean ruleEndDateViewEditPagesFlag = endDate.isEnabled();
			VertexLogger.log(String.valueOf(ruleEndDateViewEditPagesFlag));
			boolean ruleEnabledFlagRulesViewEditPagesFlag = enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(ruleEnabledFlagRulesViewEditPagesFlag));
			boolean cancelFlag = cancel.isEnabled();
			VertexLogger.log(String.valueOf(cancelFlag));
			boolean saveOnMainsFlag = !saveOnMainsAndConclusions.isEnabled();
			VertexLogger.log(String.valueOf(saveOnMainsFlag));

			click.clickElement(nextMainTab);
			wait.waitForElementDisplayed(conditionSetRuleMappingDropdown);
			boolean conditionSetFieldFlag = !conditionSetRuleMappingDropdown.isEnabled();
			VertexLogger.log(String.valueOf(conditionSetFieldFlag));
			boolean functionFieldFlag = !functionField.isEnabled();
			VertexLogger.log(String.valueOf(functionFieldFlag));

			jsWaiter.sleep(5000);
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
					if ( sourceButtonViewConclusions
							 .getAttribute("class")
							 .contains("Disabled") && targetButtonConclusions
							 .getAttribute("class")
							 .contains("Disabled") )
					{
						VertexLogger.log(String.valueOf(true));
					}
					break;
				case "SUBSTRING":
					if ( sourceButtonViewConclusions
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
			}
			if ( ruleNameViewEditPagesFlag && ruleMappingOrderViewEditPagesFlag && ruleStartDateViewEditPagesFlag &&
				 ruleEndDateViewEditPagesFlag && ruleEnabledFlagRulesViewEditPagesFlag && cancelFlag &&
				 saveOnMainsFlag && conditionSetFieldFlag && functionFieldFlag )
			{
				if ( verifyEditFunctionalityRulesMapping() )
				{
					flagEditProjectRule = true;
				}
			}
		}
		return flagEditProjectRule;
	}

	/**
	 * Verify Edit Rules Functionality in Taxlink application
	 *
	 * returns @boolean
	 */
	public boolean verifyEditFunctionalityRulesMapping( )
	{
		String updatedRuleOrder;
		boolean flagEditFunctionalityJournalRule = false, flagVerify = false;

		wait.waitForElementDisplayed(backButtonConclusionsTab);
		click.clickElement(backButtonConclusionsTab);
		wait.waitForElementDisplayed(backButtonConditionsTab);
		click.clickElement(backButtonConditionsTab);

		String updatedDate = utils.getNextDayDate();
		String editRuleWithRuleName = ruleNameField.getAttribute("value");
		wait.waitForElementDisplayed(ruleOrderField);
		updatedRuleOrder = utils.randomNumber("5");

		click.clickElement(ruleOrderField);
		text.enterText(ruleOrderField, updatedRuleOrder);

		click.clickElement(endDate);

		text.enterText(endDate, updatedDate);

		List<String> dataEntered = Arrays.asList(updatedRuleOrder, updatedDate);

		VertexLogger.log("Updated Rule order and End date selected : " + dataEntered);

		expWait.until(ExpectedConditions.elementToBeClickable(saveOnMainsAndConclusions));
		click.clickElement(saveOnMainsAndConclusions);
		VertexLogger.log("Clicked on the Save button");

		jsWaiter.sleep(5000);

		htmlElement.sendKeys(Keys.END);
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());

		if ( !currentPageCount
			.getText()
			.equals("1") )
		{
			click.clickElement(firstPage);
		}
		else
		{
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
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		if ( flagVerify )
		{
			flagEditFunctionalityJournalRule = true;
		}
		return flagEditFunctionalityJournalRule;
	}

	/**
	 * Verify Export to CSV functionality on Project output- INV Rules Mapping page
	 * in Taxlink application
	 *
	 * @param instName
	 *
	 * @return boolean
	 */

	public boolean exportToCSVProjectOutputRulesMapping( String instName ) throws IOException
	{
		boolean flagExportToCSV = false;
		boolean flagInstanceMatch = false;
		boolean ruleOrderFlag = false;
		boolean startDateFlag = false;
		boolean endDateFlag = false;
		boolean enabledFlag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		String fileName = "Inventory_Project_Output_File_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToInvProjectOutputFile();
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

						Optional<WebElement> ruleName = dataPresentInParticularColumn(ruleNamePresentation,
							ruleName_CSV);
						if ( ruleName.isPresent() )
						{
							Optional<WebElement> ruleOrder = dataPresentInParticularColumn(ruleOrderPresentation,
								ruleOrder_CSV);
							ruleOrderFlag = ruleOrder.isPresent();
							VertexLogger.log("" + ruleOrderFlag);
							Optional<WebElement> startDate = dataPresentInParticularColumn(startDatePresentation,
								formattedStartDate_CSV);
							startDateFlag = startDate.isPresent();
							VertexLogger.log("" + startDateFlag);
							Optional<WebElement> endDate = dataPresentInParticularColumn(endDatePresentation,
								formattedEndDate_CSV);
							endDateFlag = endDate.isPresent();
							VertexLogger.log("" + endDateFlag);
							Optional<WebElement> enabled = dataPresentInParticularColumn(enabledPresentation,
								enabled_CSV);
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
