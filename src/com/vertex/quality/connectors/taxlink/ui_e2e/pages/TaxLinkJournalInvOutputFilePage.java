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
 * this class represents all the locators and methods for Journal Output file
 * in INV Rules Mapping menu in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkJournalInvOutputFilePage extends TaxLinkBasePage
{
	public TaxLinkJournalInvOutputFilePage( final WebDriver driver ) throws IOException
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
	@FindBy(xpath = "//button[@id='undoJournalOutputFileCondition']")
	private WebElement refreshConditionSetButton;

	@FindBy(xpath = "//button[@id='undoJournalOutputFileFunction']")
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
	@FindBy(xpath = "//button[contains(text(),'SAVE')]")
	private WebElement saveOnConclusions;

	/**
	 * Verify title of Inventory Journal Rules Mapping Page in Taxlink application
	 */
	public boolean verifyTitleJournalRulesPage( )
	{
		boolean titleFlag = false;
		wait.waitForElementDisplayed(summaryPageTitle);
		String rulesMappingTitle = summaryPageTitle.getText();
		if ( rulesMappingTitle.equalsIgnoreCase(InventoryRulesMapping.INVENTORY_RULES_MAPPING.headerJournalOutputPage) )
		{
			titleFlag = true;
		}
		return titleFlag;
	}

	/**
	 * Adding the data on Mains Tab for Inv Rules Mapping in Taxlink UI
	 */

	public String addDataOnMainsTab( )
	{
		String ruleName = null;
		String ruleOrder;

		if ( verifyTitleJournalRulesPage() )
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
	 * Select Condition Set and Function to add a Inv Journal rule
	 * in Add Rule Page in Taxlink application
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

	public void selectSourceRadioButton( String value )
	{
		By radioButton = By.xpath(String.format("(//label[@for='%s'])[1]", value));
		wait.waitForElementDisplayed(radioButton);
		click.clickElement(radioButton);
		VertexLogger.log("Selected Source Value: " + value);
	}

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
	 * Add INV Journal Rule with passed condition and function
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @param functionValue
	 * @param conditionSetValue
	 * @param invSrcVal
	 * @param invTargetVal
	 *
	 * @return String
	 */
	public String addAndVerifyInvJournalRule( String functionValue, String conditionSetValue, String invSrcVal,
		String invTargetVal )
	{
		navigateToInvJournalOutputFile();
		String ruleName = addDataOnMainsTab();
		addConditionSetAndFunctionForRule(conditionSetValue, functionValue);

		switch ( functionValue )
		{
			case "MAP":
				selectValueForSource(invSrcVal);
				selectValueForTarget(invTargetVal);
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
}
