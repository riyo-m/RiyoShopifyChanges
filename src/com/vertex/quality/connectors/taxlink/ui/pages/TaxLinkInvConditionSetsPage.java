package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.InventoryRulesMapping;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class TaxLinkInvConditionSetsPage extends TaxLinkBasePage
{
	public TaxLinkInvConditionSetsPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//button[contains(text(),'NEXT')])[1]")
	private WebElement nextMainTab;

	@FindBy(xpath = "//input[@id='code_input']")
	private WebElement addPreRuleMappingName;

	@FindBy(xpath = "//input[@id='rule_order']")
	private WebElement addPreRuleMappingOrder;

	@FindBy(xpath = "//div/h3[@class='u-margin-hug--top h2']")
	private WebElement viewCondSetPageTitle;

	@FindBy(xpath = "//div/h3[@class='u-margin-hug--top h2']")
	private WebElement copyCondSetPageTitle;

	@FindBy(xpath = "//input[@name='Codition Name']")
	private WebElement condNameCondSetPage;

	@FindBy(xpath = "//div[@id='tax-assist-condition-con']/div/select[@class='form-control dropDownSelect']")
	private WebElement conditionSetRuleMappingDropdown;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='conditionSetName']")
	private List<WebElement> conditionSetNamePresentation;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='0']")
	private List<WebElement> accessTypeConditionSetPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='startDate']")
	private List<WebElement> startDatePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='endDate']/span")
	private List<WebElement> endDatePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='enabledFlag']")
	private List<WebElement> enabledPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='conditionSetScope']")
	private List<WebElement> condSetScopePresentation;

	@FindBy(xpath = "//button[contains(text(), 'Add')]")
	private WebElement addConditionButton;

	@FindBy(xpath = "//select[@data-cy='inventoryConditionSet-conditionSetScope']")
	private WebElement invCondSetScopeDropdown;

	@FindBy(xpath = "//div[@class='col-md-5 form__title']")
	private WebElement addCondPopUpTitle;

	@FindBy(xpath = "//label[contains(text(),'Source *')]/ancestor::div/select")
	private WebElement conditionSourceDropdown;

	@FindBy(xpath = "//label[contains(text(),'Operator')]/ancestor::div/select")
	private WebElement conditionOpDropdown;

	@FindBy(xpath = "//input[@name='value']")
	private WebElement condValueTextBox;

	@FindBy(xpath = "(//button[contains(text(), 'Save')])[last()]")
	private WebElement saveButtonConditionPopUp;
	@FindBy(xpath = "//div[@class='notification__inner']/p[1]")
	private WebElement condErrorNotificationCode;

	@FindBy(xpath = "//div[@class='notification__inner']/p[2]")
	private WebElement condErrorNotificationMessage;
	@FindBy(xpath = "//div[@class='summary-page-table-wrapper']")
	private WebElement summaryPageAddCondition;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='sourceAttribute.attributeDisplayName']")
	private List<WebElement> sourceAddCondPresentation;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='operationValue']")
	private List<WebElement> opAddCondPresentation;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='0']")
	private List<WebElement> valAddCondPresentation;

	@FindBy(xpath = "(//div[@class='showMoreTextBlock u-list-unstyled flyout__list childDiv']/div/button)[last()-3]")
	private WebElement viewButtonUserConditionSet;

	@FindBy(xpath = "//button[@data-cy='btn-copy']")
	private WebElement copyButtonUserConditionSet;

	@FindBy(xpath = "//button[@data-cy='btn-cancel']")
	private WebElement closeButtonConditionSet;

	@FindBy(xpath = "//span[contains(text(),'Do you want to Continue')]/ancestor::div[2]")
	private WebElement popUpConditionUpdate;

	@FindBy(xpath = "//span[contains(text(),'Do you want to Continue')]/ancestor::div/h5[2]/b")
	private WebElement messageOnEditSaveCondition;
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	private WebElement yesOnEditSaveConditionPopUp;

	@FindBy(xpath = "//button[contains(text(),'Details')]")
	private WebElement detailsButton;

	@FindBy(xpath = "//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value')][@col-id='ruleName']")
	private List<WebElement> ruleNamePresentationInDetails;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='phaseType']")
	private List<WebElement> phaseTypePresentationInDetails;

	@FindBy(xpath = "//span[contains(text(),'Do you want to Continue')]/ancestor::div/h5/b")
	private WebElement messageOnCopySaveCondition;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlagRules;

	@FindBy(xpath = "//div[@col-id='ruleName']")
	private List<WebElement> ruleNamePresentation;
	@FindBy(xpath = "//span[contains(text(),'Access Type')]/ancestor::div/span/span")
	private WebElement filterAccessType;

	@FindBy(xpath = "(//input[@class='ag-filter-filter'])[last()-1]")
	private WebElement filterInput;

	@FindBy(xpath = "//input[@name='copyAssociatedRule']")
	protected WebElement copyAssociatedRuleFlag;
	private By actionsButton = By.xpath(".//following-sibling::div[5]/div/div/div/button");
	private By condSetNamePresentation = By.xpath(".//ancestor::div/div[@col-id='conditionSetName']");
	private By editButtonConditionSet = By.xpath(
		".//following-sibling::div[5]/div/div/div/div/div/button[contains(text(),'Edit')]");
	private By endDateFollowingCondName = By.xpath(".//following-sibling::div[4]");
	private By editButtonFollowingCondName = By.xpath(
		".//following-sibling::div[6]/div/div/div/div/div/button[contains(text(),'Edit')]");
	private By enabledConditionPresentation = By.xpath(".//following-sibling::div[5]");
	private By threeDots = By.xpath(".//following-sibling::div[6]/div/div/div/button");
	private By editButtonFollowingSrcName = By.xpath(
		".//following-sibling::div[3]/div/div/div/div/div/button[contains(text(),'Edit')]");
	private By valFollowingOperator = By.xpath(".//following-sibling::div");
	private By enabledRulePresentation = By.xpath(".//following-sibling::div[3]");
	private By viewButtonConditionSet = By.xpath(
		".//following-sibling::div[6]/div/div/div/div/div/button[contains(text(),'View')]");

	/**
	 * Verify title of INV Condition Sets Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleInvConditionSetsPage( )
	{
		boolean titleMatch = false;
		String conditionSetsTitle;
		wait.waitForElementDisplayed(summaryPageTitle);
		conditionSetsTitle = summaryPageTitle.getText();
		if ( conditionSetsTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvConditionSetPage) )
		{
			titleMatch = true;
		}
		return titleMatch;
	}

	/**
	 * Verify title of INV Pre-Rules Mapping Page in Taxlink application
	 */
	public void verifyTitleInvPreRulesMappingPage( )
	{
		wait.waitForElementDisplayed(summaryPageTitle);
		String rulesMappingTitle = summaryPageTitle.getText();
		rulesMappingTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvPreRuleMappingPage);
	}

	/**
	 * Method to verify title of Copy page of Condition Set
	 *
	 * @return boolean
	 */
	public boolean verifyTitleCopyInvConditionSetPage( )
	{
		boolean flagCopyCondTitleMatches = false;

		wait.waitForElementDisplayed(copyCondSetPageTitle);
		String copyCondSetTitle = copyCondSetPageTitle.getText();
		if ( copyCondSetTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerCopyConditionSetPage) )
		{
			flagCopyCondTitleMatches = true;
		}
		return flagCopyCondTitleMatches;
	}

	/**
	 * Method to title of add page of Condition Set
	 *
	 * @return boolean
	 */
	public boolean verifyTitleAddConditionSetPage( )
	{
		boolean flagAddCondTitleMatches = false;
		String addCondSetTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		if ( addCondSetTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvAddConditionSetPage) )
		{
			flagAddCondTitleMatches = true;
		}
		return flagAddCondTitleMatches;
	}

	/**
	 * Method to title of view page of Inv Condition Set
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewInvConditionSetPage( )
	{
		boolean flagViewCondTitleMatches = false;
		wait.waitForElementDisplayed(viewCondSetPageTitle);
		String viewCondSetTitle = viewCondSetPageTitle.getText();
		if ( viewCondSetTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvViewConditionSetPage) )
		{
			flagViewCondTitleMatches = true;
		}
		return flagViewCondTitleMatches;
	}

	/**
	 * Method to title of edit page of INV Condition Set
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditInvConditionSetPage( )
	{
		boolean flagEditCondTitleMatches = false;
		wait.waitForElementDisplayed(addViewEditPageTitle);
		String editCondSetTitle = addViewEditPageTitle.getText();
		if ( editCondSetTitle.equalsIgnoreCase(InventoryRulesMapping.RuleMappingDetails.headerInvEditConditionSetPage) )
		{
			flagEditCondTitleMatches = true;
		}
		return flagEditCondTitleMatches;
	}

	/**
	 * Method to add and verify add page of Condition Set
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyINVConditionSetWithEndDate( )
	{
		boolean flagAddConditionSetWithEndDate = false, flagCondAdded = false, flagAddDisabled = false,
			flagVerifySummaryTable = false, flagSaveEnabled = false;
		String value = null, value1 = null, value2 = null;
		String selectedSrc, selectedOp;
		WebElement source;

		navigateToInvConditionSets();
		if ( verifyTitleInvConditionSetsPage() )
		{
			click.clickElement(addButton);
		}

		if ( verifyTitleAddConditionSetPage() )
		{
			String conditionName = utils.randomAlphaNumericText();
			wait.waitForElementDisplayed(condNameCondSetPage);
			text.enterText(condNameCondSetPage, conditionName);

			dropdown.selectDropdownByValue(invCondSetScopeDropdown, "INV");
			String condSetScope = invCondSetScopeDropdown.getAttribute("value");

			click.clickElement(endDate);

			String nextDate = utils.getNextDayDate();
			text.enterText(endDate, nextDate);
			VertexLogger.log("End Date: " + nextDate);

			wait.waitForElementDisplayed(enabledFlag);
			checkbox.isCheckboxChecked(enabledFlag);

			String currDate = utils.getFormattedDate();
			VertexLogger.log("Start Date: " + currDate);

			wait.waitForElementDisplayed(startDate);
			click.clickElement(startDate);

			utils.clearTextField(startDate);
			text.enterText(startDate, currDate);

			List<String> dataEntered = Arrays.asList(conditionName, currDate, nextDate);

			VertexLogger.log("Condition Set created is: " + dataEntered);
			for ( int c = 1 ; c < 6 ; c++ )
			{
				wait.waitForElementDisplayed(condNameCondSetPage);
				click.clickElement(condNameCondSetPage);
				click.clickElement(addConditionButton);

				getPageTitle().equals("Add Condition Set");
				wait.waitForElementDisplayed(condValueTextBox);

				dropdown.selectDropdownByIndex(conditionOpDropdown, c);
				WebElement operator = dropdown.getDropdownSelectedOption(conditionOpDropdown);
				selectedOp = operator.getText();

				switch ( selectedOp )
				{
					case "EQUAL_TO":
						wait.tryWaitForElementEnabled(conditionSourceDropdown, 20);
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Number01");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						if ( negativeScenarioForOnlyNumericValues(utils.randomAlphaNumericText()) )
						{
							value = utils.randomNumber("6");
							text.enterText(condValueTextBox, value);
						}
						break;

					case "LESS_THAN_OR_EQUAL_TO":
						wait.tryWaitForElementEnabled(conditionSourceDropdown, 20);
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Attribute01");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						if ( negativeScenarioForEmptyValues("") )
						{
							value = utils.randomNumber("4");
							text.enterText(condValueTextBox, value);
						}
						break;

					case "NOT_IN":
						wait.tryWaitForElementEnabled(conditionSourceDropdown, 20);
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Expenditure Type");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						if ( negativeScenarioForCommaValues(utils.randomNumber("4")) )
						{
							value1 = utils.randomNumber("5");
							value2 = utils.randomNumber("5");
							text.enterText(condValueTextBox, value1 + "," + value2);
						}
						break;

					case "IN":
						wait.tryWaitForElementEnabled(conditionSourceDropdown, 20);
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Inventory Product Code");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						if ( negativeScenarioForCommaValues(utils.randomNumber("4")) )
						{
							text.enterText(condValueTextBox, "Test1,Test2");
						}
						break;

					case "IS_NULL":
						wait.tryWaitForElementEnabled(conditionSourceDropdown, 20);
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Inventory Application ID");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + selectedOp);
				}
				List<String> condDataEntered = Arrays.asList(selectedSrc, selectedOp, value);
				VertexLogger.log("Condition added is: " + condDataEntered);

				click.clickElement(saveButtonConditionPopUp);

				if ( verifyConditionAddedOnAddConditionPage(selectedSrc, selectedOp, value, value1, value2) )
				{
					flagCondAdded = true;
				}
				if ( !flagCondAdded )
				{
					VertexLogger.log("There is issue with the Condition verification on Add condition set page!!");
					break;
				}
			}
			if ( !addConditionButton.isEnabled() )
			{
				flagAddDisabled = true;
			}

			if ( !condNameCondSetPage
				.getAttribute("value")
				.isEmpty() && !invCondSetScopeDropdown
				.getText()
				.isEmpty() && !startDate
				.getAttribute("value")
				.isEmpty() )
			{
				flagSaveEnabled = true;
			}

			wait.waitForElementDisplayed(saveButton);
			click.clickElement(saveButton);

			wait.waitForElementDisplayed(externalFilter);

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int d = 1 ; d <= count ; d++ )
			{
				String formattedEndDate = utils.getNextDayDate();
				Optional<WebElement> data = dataPresentInParticularColumn(conditionSetNamePresentation, conditionName);
				if ( data.isPresent() )
				{
					boolean flagAccessType = accessTypeConditionSetPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals("USER"));
					VertexLogger.log(String.valueOf(flagAccessType));
					jsWaiter.sleep(2500);
					boolean flagEndDate = endDatePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(formattedEndDate));
					VertexLogger.log(String.valueOf(flagEndDate));
					boolean flagEnabled = enabledPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals("Y"));
					VertexLogger.log(String.valueOf(flagEnabled));
					boolean flagCondSetScope = condSetScopePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(condSetScope));
					VertexLogger.log(String.valueOf(flagCondSetScope));
					if ( flagAccessType && flagEndDate && flagEnabled && flagCondSetScope )
					{
						VertexLogger.log(
							"Added " + conditionName + " with End date and Verified it's summary table details!!");
						flagVerifySummaryTable = true;
						break;
					}
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}

			if ( flagCondAdded && flagAddDisabled && flagSaveEnabled && flagVerifySummaryTable &&
				 verifyNewConditionInRulesMapping(conditionName) )
			{
				flagAddConditionSetWithEndDate = true;
			}
		}
		return flagAddConditionSetWithEndDate;
	}

	/**
	 * Method to add and verify Condition Set
	 * without end date
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyINVConditionSetWithoutEndDate( )
	{
		boolean flagAddConditionSetWithoutEndDate = false, flagCondAdded1 = false, flagAddDisabled = false,
			flagVerifySummaryTable = false;
		String valueCond = null, valueCond1 = null, valueCond2 = null;
		String selectedSrc, selectedOp;
		WebElement source;

		jsWaiter.sleep(2000);
		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		scroll.scrollBottom();
		click.clickElement(invConditionSetsButton);
		if ( verifyTitleInvConditionSetsPage() )
		{
			click.clickElement(addButton);
		}

		if ( verifyTitleAddConditionSetPage() )
		{
			String conditionName = utils.randomAlphaNumericText();
			wait.waitForElementDisplayed(condNameCondSetPage);
			text.enterText(condNameCondSetPage, conditionName);

			dropdown.selectDropdownByValue(invCondSetScopeDropdown, "INV");
			String condSetScope = invCondSetScopeDropdown.getAttribute("value");

			wait.waitForElementDisplayed(enabledFlag);
			checkbox.isCheckboxChecked(enabledFlag);

			String currDate = utils.getFormattedDate();
			VertexLogger.log("Start Date: " + currDate);

			wait.waitForElementDisplayed(startDate);
			click.clickElement(startDate);

			utils.clearTextField(startDate);
			text.enterText(startDate, currDate);

			List<String> dataEntered = Arrays.asList(conditionName, currDate);

			VertexLogger.log("Condition Set created is: " + dataEntered);
			for ( int i = 1 ; i < 6 ; i++ )
			{
				click.clickElement(condNameCondSetPage);
				click.clickElement(addConditionButton);

				getPageTitle().equals("Add Condition Set");
				wait.waitForElementDisplayed(condValueTextBox);

				dropdown.selectDropdownByIndex(conditionOpDropdown, i);
				WebElement operator = dropdown.getDropdownSelectedOption(conditionOpDropdown);
				selectedOp = operator.getText();

				switch ( selectedOp )
				{
					case "EQUAL_TO":
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Project Name");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();

						valueCond = utils.randomNumber("6");
						text.enterText(condValueTextBox, valueCond);
						break;

					case "LESS_THAN_OR_EQUAL_TO":
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Attribute01");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						valueCond = utils.randomNumber("3");
						text.enterText(condValueTextBox, valueCond);
						break;
					case "NOT_IN":
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Expenditure Type");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						valueCond1 = utils.randomNumber("5");
						valueCond2 = utils.randomNumber("5");
						text.enterText(condValueTextBox, valueCond1 + "," + valueCond2);
						break;
					case "IN":
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Inventory Product Code");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						text.enterText(condValueTextBox, "Test1,Test2");
						break;
					case "IS_NULL":
						dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Inventory Application ID");
						source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
						selectedSrc = source.getText();
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + selectedOp);
				}

				List<String> condDataEntered = Arrays.asList(selectedSrc, selectedOp, valueCond);
				VertexLogger.log("Condition added is: " + condDataEntered);

				click.clickElement(saveButtonConditionPopUp);

				if ( verifyConditionAddedOnAddConditionPage(selectedSrc, selectedOp, valueCond, valueCond1,
					valueCond2) )
				{
					flagCondAdded1 = true;
				}
				if ( !flagCondAdded1 )
				{
					VertexLogger.log("There is issue with the Condition verification on Add condition set page!!");
					break;
				}
			}
			if ( !addConditionButton.isEnabled() )
			{
				flagAddDisabled = true;
			}
			wait.waitForElementDisplayed(saveButton);
			click.clickElement(saveButton);

			wait.waitForElementDisplayed(externalFilter);

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				String finalCondName = conditionName;
				Optional<WebElement> data = dataPresentInParticularColumn(conditionSetNamePresentation, finalCondName);
				if ( data.isPresent() )
				{
					boolean flagAccessType = accessTypeConditionSetPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals("USER"));
					VertexLogger.log("flagAccessType: " + flagAccessType);
					boolean flagEnabled = enabledPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals("Y"));
					VertexLogger.log("flagEnabled: " + flagEnabled);
					boolean flagCondSetScope = condSetScopePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(condSetScope));
					VertexLogger.log(String.valueOf(flagCondSetScope));
					if ( flagAccessType && flagEnabled && flagCondSetScope )
					{
						VertexLogger.log(
							"Added " + conditionName + " without End date and Verified it's summary table details!!");
						flagVerifySummaryTable = true;
						break;
					}
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}

			if ( flagCondAdded1 && flagAddDisabled && flagVerifySummaryTable && verifyNewConditionInRulesMapping(
				conditionName) )
			{
				flagAddConditionSetWithoutEndDate = true;
			}
		}
		return flagAddConditionSetWithoutEndDate;
	}

	/**
	 * Method to verify conditions added
	 * to add condition set Page
	 *
	 * @param selectedSrc
	 * @param selectedOp
	 * @param enteredVal
	 * @param enteredVal1
	 * @param enteredVal2
	 * @return boolean
	 */
	public boolean verifyConditionAddedOnAddConditionPage( String selectedSrc, String selectedOp, String enteredVal,
		String enteredVal1, String enteredVal2 )
	{
		boolean flagVerify = false, srcFlag = false, valFlag = false, opFlag = false, editFlag = false, valFlagAfterEdit
			= false;
		String value1 = null, value2 = null, value = null;
		if ( summaryPageAddCondition.isDisplayed() )
		{
			Optional src = dataPresentInParticularColumn(sourceAddCondPresentation, selectedSrc);
			Optional op = dataPresentInParticularColumn(opAddCondPresentation, selectedOp);

			for ( int i = 0 ; i < opAddCondPresentation.size() ; i++ )
			{
				if ( op.isPresent() )
				{
					String s1 = opAddCondPresentation
						.get(i)
						.findElement(valFollowingOperator)
						.getText();

					if ( selectedOp.equals("IN") || selectedOp.equals("NOT_IN") )
					{
						if ( s1.equals(enteredVal1 + "," + enteredVal2) )
						{
							valFlag = true;
							VertexLogger.log("valFlag: " + valFlag);
							scroll.scrollElementIntoView(editButtonFollowingSrcName);
							sourceAddCondPresentation
								.get(i)
								.findElement(editButtonFollowingSrcName)
								.click();
							if ( addCondPopUpTitle.isDisplayed() )
							{
								if ( conditionSourceDropdown.isEnabled() && conditionOpDropdown.isEnabled() &&
									 condValueTextBox.isEnabled() )
								{
									VertexLogger.log(
										"Source, Operator and Value fields are editable on Edit condition pop up!");
									text.enterText(condValueTextBox, "Test3,Test4");
									click.clickElement(saveButtonConditionPopUp);
									editFlag = true;
									break;
								}
							}
						}
					}
					else
					{
						if ( s1.equals(enteredVal) )
						{
							valFlag = true;
							VertexLogger.log("valFlag: " + valFlag);
							scroll.scrollElementIntoView(editButtonFollowingSrcName);
							sourceAddCondPresentation
								.get(i)
								.findElement(editButtonFollowingSrcName)
								.click();
							if ( addCondPopUpTitle.isDisplayed() )
							{
								if ( conditionSourceDropdown.isEnabled() && conditionOpDropdown.isEnabled() &&
									 condValueTextBox.isEnabled() )
								{
									VertexLogger.log(
										"Source, Operator and Value fields are editable on Edit condition pop up!");
									value = utils.randomNumber("4");
									text.enterText(condValueTextBox, value);
									click.clickElement(saveButtonConditionPopUp);
									editFlag = true;
									break;
								}
							}
						}
					}
				}
				else
				{
					valFlag = false;
					VertexLogger.log("Value is not present in value column for a condition!!");
				}
			}

			for ( int i = 0 ; i < opAddCondPresentation.size() ; i++ )
			{
				if ( op.isPresent() )
				{
					String s1 = opAddCondPresentation
						.get(i)
						.findElement(valFollowingOperator)
						.getText();

					if ( selectedOp.equals("IN") || selectedOp.equals("NOT_IN") )
					{
						if ( s1.equals(value1 + "," + value2) )
						{
							valFlagAfterEdit = true;
							VertexLogger.log("valFlagAfterEdit: " + valFlagAfterEdit);
							break;
						}
					}
					else
					{
						if ( s1.equals(value) )
						{
							valFlagAfterEdit = true;
							VertexLogger.log("valFlagAfterEdit: " + valFlagAfterEdit);
							break;
						}
					}
				}
			}

			if ( src.isPresent() && op.isPresent() && valFlag && editFlag && valFlagAfterEdit )
			{
				srcFlag = sourceAddCondPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(selectedSrc));
				VertexLogger.log("srcFlag: " + srcFlag);

				opFlag = opAddCondPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(selectedOp));
				VertexLogger.log("opFlag: " + opFlag);
			}

			if ( srcFlag && opFlag )
			{
				VertexLogger.log("Condition with source " + selectedSrc + " is added to the condition set");
				flagVerify = true;
			}
			else
			{
				flagVerify = false;
			}
		}
		return flagVerify;
	}

	/**
	 * Method to test negative scenario for non-numeric value in numeric field
	 * for a particular condition in condition set
	 *
	 * @return boolean
	 */
	public boolean negativeScenarioForOnlyNumericValues( String value )
	{
		boolean flagNumeric = false;
		text.enterText(condValueTextBox, value);
		click.clickElement(saveButtonConditionPopUp);
		if ( condErrorNotificationCode.isDisplayed() )
		{
			if ( condErrorNotificationCode
					 .getText()
					 .equals("VTX-UI-1012") && condErrorNotificationMessage
					 .getText()
					 .equals("Condition value should be numeric") )
			{
				flagNumeric = true;
				VertexLogger.log("Validated negative scenario for only numeric values needed!!");
			}
		}
		return flagNumeric;
	}

	/**
	 * Method to test negative scenario for empty value in Value field
	 * for a particular condition in condition set
	 *
	 * @return boolean
	 */
	public boolean negativeScenarioForEmptyValues( String value )
	{
		boolean flagEmpty = false;
		text.enterText(condValueTextBox, value);
		click.clickElement(saveButtonConditionPopUp);
		if ( condErrorNotificationCode.isDisplayed() )
		{
			if ( condErrorNotificationCode
					 .getText()
					 .equals("VTX-UI-1010") && condErrorNotificationMessage
					 .getText()
					 .equals("Please enter Condition value") )
			{
				flagEmpty = true;
				VertexLogger.log("Validated negative scenario for values needed, cannot be empty!!");
			}
		}
		return flagEmpty;
	}

	/**
	 * Method to test negative scenario for comma-separated values in value field
	 * for a particular condition in condition set
	 *
	 * @return boolean
	 */
	public boolean negativeScenarioForCommaValues( String value )
	{
		boolean flagComma = false;
		text.enterText(condValueTextBox, value);
		click.clickElement(saveButtonConditionPopUp);
		if ( condErrorNotificationCode.isDisplayed() )
		{
			if ( condErrorNotificationCode
					 .getText()
					 .equals("VTX-UI-1011") && condErrorNotificationMessage
					 .getText()
					 .equals("Please enter comma separated values") )
			{
				flagComma = true;
				VertexLogger.log("Validated negative scenario for comma separated values needed!!");
			}
		}
		return flagComma;
	}

	/**
	 * Adding the data on Mains Tab for INV Rules Mapping in Taxlink UI
	 */

	public String addDataOnMainsInvTab( )
	{
		String ruleName;
		String ruleOrder;

		navigateToInvPreRulesMappingPage();
		verifyTitleInvPreRulesMappingPage();
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
	 * Method to verify condition added
	 * is present in condition dropdown
	 * on Add rules page
	 *
	 * @param conditionName
	 * @return boolean
	 */
	public boolean verifyNewConditionInRulesMapping( String conditionName )
	{
		boolean flagCondPresence = false;

		js.executeScript("arguments[0].scrollIntoView();", invRulesMappingTab);
		expWait.until(ExpectedConditions.visibilityOf(invRulesMappingTab));
		click.clickElement(invPreRulesButton);
		clickOnTaxCalculationSetUpsDropdown();
		addDataOnMainsInvTab();
		jsWaiter.sleep(20000);
		click.clickElement(conditionSetRuleMappingDropdown);

		expWait.until(ExpectedConditions.visibilityOf(conditionSetRuleMappingDropdown));
		if ( dropdown
			.getDropdownDisplayOptions(conditionSetRuleMappingDropdown)
			.contains(conditionName) )
		{
			VertexLogger.log(
				"Verified Condition " + conditionName + " in the Condition dropdown in Rules mapping tab..");
			flagCondPresence = true;
		}
		return flagCondPresence;
	}

	/**
	 * View Condition Sets with user level access type in INV Rules Mapping
	 * in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean viewUSERInvConditionSetsRulesMapping( )
	{
		boolean flagViewCondition = true;
		String condSelected;
		navigateToInvConditionSets();
		if ( verifyTitleInvConditionSetsPage() )
		{
			int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= countSummaryTable ; i++ )
			{
				Optional<WebElement> dataFoundForUserAccess = dataPresentInParticularColumn(
					accessTypeConditionSetPresentation, "USER");
				if ( dataFoundForUserAccess.isPresent() )
				{
					WebElement dataFound = dataFoundForUserAccess
						.get()
						.findElement(actionsButton);
					actionPageDown.moveToElement(dataFound);
					expWait.until(ExpectedConditions.visibilityOf(actions));
					dataFoundForUserAccess
						.get()
						.findElement(actionsButton)
						.click();
					condSelected = dataFoundForUserAccess
						.get()
						.findElement(condSetNamePresentation)
						.getText();
					VertexLogger.log("Condition Set selected : " + condSelected);
					viewButtonUserConditionSet.click();
					break;
				}
				else
				{
					if ( i == countSummaryTable )
					{
						VertexLogger.log("No Condition Set is present with USER access Type!!");
						flagViewCondition = false;
						break;
					}
					else
					{
						actionPageDown
							.sendKeys(Keys.PAGE_DOWN)
							.perform();
					}
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		if ( flagViewCondition )
		{
			if ( verifyViewConditionUser() )
			{
				flagViewCondition = true;
			}
		}
		return flagViewCondition;
	}

	/**
	 * Method to verify view page of Condition Set
	 * for User access level
	 *
	 * @return boolean
	 */
	public boolean verifyViewConditionUser( )
	{
		boolean flagViewConditionForUser = false;

		if ( verifyTitleViewInvConditionSetPage() )
		{
			boolean condNameViewCondSetPageFlag = !condNameCondSetPage.isEnabled();
			VertexLogger.log("condNameViewCondSetPageFlag: " + condNameViewCondSetPageFlag);
			boolean stDateViewCondSetPageFlag = !startDate.isEnabled();
			VertexLogger.log("stDateViewCondSetPageFlag: " + stDateViewCondSetPageFlag);
			boolean endDateViewCondSetPageFlag = !endDate.isEnabled();
			VertexLogger.log("endDateViewCondSetPageFlag: " + endDateViewCondSetPageFlag);
			boolean enabledViewCondSetPageFlag = !enabledFlag.isEnabled();
			VertexLogger.log("enabledViewCondSetPageFlag: " + enabledViewCondSetPageFlag);
			boolean saveViewCondSetPageFlag = !saveButton.isEnabled();
			VertexLogger.log("saveViewCondSetPageFlag: " + saveViewCondSetPageFlag);
			boolean addViewCondSetPageFlag = !addConditionButton.isEnabled();
			VertexLogger.log("addViewCondSetPageFlag: " + addViewCondSetPageFlag);
			boolean cancelViewCondSetPageFlag = cancelButton.isEnabled();
			VertexLogger.log("cancelViewCondSetPageFlag: " + cancelViewCondSetPageFlag);

			if ( condNameCondSetPage
				.getAttribute("value")
				.equals("VTX_INV_ONLY") )
			{
				Optional<WebElement> src = dataPresentInParticularColumn(sourceAddCondPresentation,
					"Inventory Application ID");
				Optional<WebElement> op = dataPresentInParticularColumn(opAddCondPresentation, "EQUAL_TO");
				Optional<WebElement> val = dataPresentInParticularColumn(valAddCondPresentation, "-401");

				if ( src.isPresent() && op.isPresent() && val.isPresent() && condNameViewCondSetPageFlag &&
					 stDateViewCondSetPageFlag && endDateViewCondSetPageFlag && enabledViewCondSetPageFlag &&
					 saveViewCondSetPageFlag && addViewCondSetPageFlag && cancelViewCondSetPageFlag )
				{
					flagViewConditionForUser = true;
				}
			}
			else if ( condNameViewCondSetPageFlag && stDateViewCondSetPageFlag && endDateViewCondSetPageFlag &&
					  enabledViewCondSetPageFlag && saveViewCondSetPageFlag && addViewCondSetPageFlag &&
					  cancelViewCondSetPageFlag )
			{
				flagViewConditionForUser = true;
			}
		}
		return flagViewConditionForUser;
	}

	/**
	 * Edit INV Condition Sets with user level access type in INV Rules Mapping
	 * in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean editUSERInvConditionSetsRulesMapping( )
	{
		boolean flagEditCondition = false, flagEditConditionWithEndDate = false, flagEditConditionWithoutEndDate
			= false;

		navigateToInvConditionSets();
		if ( verifyTitleInvConditionSetsPage() )
		{
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Enabled");
		}
		jsWaiter.sleep(5000);
		if ( selectUserConditionSetWithEndDate() )
		{
			if ( verifyEditConditionSetUser() )
			{
				flagEditConditionWithEndDate = true;
			}
		}
		if ( selectUserConditionSetWithoutEndDate() )
		{
			if ( verifyEditConditionSetUser() )
			{
				flagEditConditionWithoutEndDate = true;
			}
		}
		if ( flagEditConditionWithEndDate && flagEditConditionWithoutEndDate )
		{
			flagEditCondition = true;
		}
		return flagEditCondition;
	}

	/**
	 * Method to select condition set with USER access type
	 */
	public boolean selectUserConditionSetWithEndDate( )
	{
		String condSelected;
		boolean flagEditCondition = false;

		click.clickElement(filterAccessType);
		wait.waitForElementDisplayed(filterInput);
		text.enterText(filterInput, "USER");
		click.clickElement(externalFilter);
		jsWaiter.sleep(1000);
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		mainLoop:
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			jsWaiter.sleep(5000);
			for ( int j = 0 ; j < accessTypeConditionSetPresentation.size() ; j++ )
			{
				if ( j == 9 )
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
					break;
				}
				else if ( (endDatePresentation
							   .get(j)
							   .getText()
							   .length() != 0) && (enabledPresentation
					.get(j)
					.getText()
					.equals("Y")) )
				{
					jsWaiter.sleep(5000);
					condSelected = conditionSetNamePresentation
						.get(j)
						.findElement(condSetNamePresentation)
						.getText();
					VertexLogger.log("Condition Set selected : " + condSelected);

					jsWaiter.sleep(1500);
					js.executeScript("arguments[0].scrollIntoView();", exportToCSVSummaryPage);
					jsWaiter.sleep(5000);
					conditionSetNamePresentation
						.get(j)
							.findElement(editButtonFollowingCondName)
							.click();
						flagEditCondition = true;
						break mainLoop;
				}
			}
		}
		return flagEditCondition;
	}

	/**
	 * Method to select condition set with USER access type
	 * without End date
	 */
	public boolean selectUserConditionSetWithoutEndDate( )
	{
		String condSelectedWithoutEndDate;
		boolean flagEditCondition = false;
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Enabled");

		checkPageNavigation();
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		mainLoop1:
		for ( int k = 1 ; k <= countSummaryTable ; k++ )
		{
			jsWaiter.sleep(15000);
			for ( int l = 0 ; l < accessTypeConditionSetPresentation.size() ; l++ )
			{
				if ( l == 9 )
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
					break;
				}
				else if ( endDatePresentation
							  .get(l)
							  .getText()
							  .isEmpty() && (enabledPresentation
					.get(l)
					.getText()
					.equals("Y")) )
				{
					jsWaiter.sleep(5000);
					condSelectedWithoutEndDate = conditionSetNamePresentation
						.get(l)
						.findElement(condSetNamePresentation)
						.getText();
					VertexLogger.log("Condition Set selected : " + condSelectedWithoutEndDate);

					jsWaiter.sleep(1500);
					js.executeScript("arguments[0].scrollIntoView();", accessTypeConditionSetPresentation
						.get(l)
						.findElement(editButtonConditionSet));
					jsWaiter.sleep(5000);
					accessTypeConditionSetPresentation
							.get(l)
							.findElement(editButtonConditionSet)
							.click();
						flagEditCondition = true;
						break mainLoop1;
				}
			}
		}
		return flagEditCondition;
	}

	/**
	 * Method to verify edit page of Condition Set
	 * for User access level
	 *
	 * @return boolean
	 */
	public boolean verifyEditConditionSetUser( )
	{
		boolean flagEditConditionSetForUser = false, flagDisabledVerified = false, flagEndDateVerified = false;

		if ( verifyTitleEditInvConditionSetPage() )
		{
			String condSelectedWithoutLastDate = condNameCondSetPage.getAttribute("value");
			boolean condNameViewCondSetPageFlag = !condNameCondSetPage.isEnabled();
			VertexLogger.log("conditionNameTextBoxFlag: " + condNameViewCondSetPageFlag);
			boolean stDateViewCondSetPageFlag = !startDate.isEnabled();
			VertexLogger.log("startDateFlag: " + stDateViewCondSetPageFlag);
			boolean enabledViewCondSetPageFlag = enabledFlag.isEnabled();
			VertexLogger.log("EnabledFlag: " + enabledViewCondSetPageFlag);
			boolean saveViewCondSetPageFlag = !saveButton.isEnabled();
			VertexLogger.log("saveCondSetFlag: " + saveViewCondSetPageFlag);
			boolean addViewCondSetPageFlag = !addConditionButton.isEnabled();
			VertexLogger.log("addCondSetFlag: " + addViewCondSetPageFlag);
			boolean cancelViewCondSetPageFlag = cancelButton.isEnabled();
			VertexLogger.log("cancelCondSetFlag: " + cancelViewCondSetPageFlag);

			if ( endDate
				.getAttribute("value")
				.equals("") )
			{
				boolean endDateCondSetPageFlag = endDate.isEnabled();
				VertexLogger.log("endDateCondSetFlag: " + endDateCondSetPageFlag);

				click.clickElement(endDate);

				String nextDate = utils.getNextDayDate();
				text.enterText(endDate, nextDate);
				VertexLogger.log("End Date: " + nextDate);

				boolean saveCondSetPageFlag = saveButton.isEnabled();
				VertexLogger.log("saveCondSetFlag: " + saveCondSetPageFlag);
				click.clickElement(saveButton);

				if ( popUpConditionUpdate.isDisplayed() )
				{
					if ( messageOnEditSaveCondition
						.getText()
						.equals(InventoryRulesMapping.RuleMappingDetails.messageEditEndDateConditionSetPage) )
					{
						click.clickElement(yesOnEditSaveConditionPopUp);
						VertexLogger.log("End date has been updated for Empty End Date condition Set: " +
										 condSelectedWithoutLastDate);
					}
				}
			}
			else if ( cancelButton.isDisplayed() )
			{
				boolean endDateViewCondSetPageFlag = !endDate.isEnabled();
				VertexLogger.log("endDateCondSetFlag: " + endDateViewCondSetPageFlag);
				click.clickElement(cancelButton);
			}
			wait.waitForElementDisplayed(externalFilter);
			checkPageNavigation();
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Both");
			int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= countSummaryTable ; i++ )
			{
				Optional<WebElement> dataFound = dataPresentInParticularColumn(conditionSetNamePresentation,
					condSelectedWithoutLastDate);
				if ( dataFound.isPresent() )
				{
					String endDateSummary = dataFound
						.get()
						.findElement(endDateFollowingCondName)
						.getText();
					if ( !endDateSummary.isEmpty() )
					{
						flagEndDateVerified = true;
						VertexLogger.log(
							"End date has been verified for condition set: " + condSelectedWithoutLastDate);
						break;
					}
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}

			if ( disabledConditionSetFunctionality(condSelectedWithoutLastDate) )
			{
				flagDisabledVerified = true;
			}

			if ( condNameViewCondSetPageFlag && stDateViewCondSetPageFlag && enabledViewCondSetPageFlag &&
				 saveViewCondSetPageFlag && addViewCondSetPageFlag && cancelViewCondSetPageFlag &&
				 flagEndDateVerified && flagDisabledVerified )
			{
				VertexLogger.log(
					"Checked Edit functionality for Enabled Flag for condition : " + condSelectedWithoutLastDate);
				flagEditConditionSetForUser = true;
			}
			else
			{
				flagEditConditionSetForUser = false;
			}
		}
		return flagEditConditionSetForUser;
	}

	/**
	 * Method to verify Enabled flag - edit condition set
	 * User should be able to disable the Condition set
	 * by unchecking the Enabled flag checkbox
	 *
	 * @param condSelected
	 * @return boolean
	 */
	public boolean disabledConditionSetFunctionality( String condSelected )
	{
		boolean flagDisabledConditionSet = false, flagDisabledVerified = false, enableNotPossibleFlag = false;

		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(conditionSetNamePresentation, condSelected);
			jsWaiter.sleep(5000);
			if ( dataFound.isPresent() )
			{
				jsWaiter.sleep(5000);

				if ( dataFound
					.get()
					.findElement(editButtonFollowingCondName)
					.isDisplayed() )
				{
					js.executeScript("arguments[0].scrollIntoView();", exportToCSVSummaryPage);
					jsWaiter.sleep(5000);
					dataFound
						.get()
						.findElement(editButtonFollowingCondName)
						.click();
					break;
				}
				else
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
				}
			}
			else
			{
				js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		wait.waitForElementDisplayed(enabledFlag);
		click.clickElement(enabledFlag);

		boolean saveCondSetPageFlag = saveButton.isEnabled();
		VertexLogger.log("saveCondSetFlag: " + saveCondSetPageFlag);

		click.clickElement(saveButton);

		if ( popUpConditionUpdate.isDisplayed() )
		{
			if ( messageOnEditSaveCondition
				.getText()
				.equals(InventoryRulesMapping.RuleMappingDetails.messageEditConditionSetPage) )
			{
				click.clickElement(yesOnEditSaveConditionPopUp);
			}
		}

		wait.waitForElementDisplayed(externalFilter);
		checkPageNavigation();

		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(conditionSetNamePresentation, condSelected);
			if ( dataFound.isPresent() )
			{
				dataFound
					.get()
					.findElement(enabledConditionPresentation)
					.getText()
					.equals("N");
				flagDisabledVerified = true;

				if ( dataFound
					.get()
					.findElement(editButtonFollowingCondName)
					.isDisplayed() )
				{
					dataFound
						.get()
						.findElement(editButtonFollowingCondName)
						.click();
				}
				else
				{
					js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
				}

				if ( verifyEnableConditionSet() )
				{
					click.clickElement(cancelButton);
					enableNotPossibleFlag = true;
					break;
				}
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagDisabledVerified && enableNotPossibleFlag )
		{
			flagDisabledConditionSet = true;
		}
		return flagDisabledConditionSet;
	}

	/**
	 * Method to verify Enabled flag - edit condition set
	 * User should not be able to Enable the Condition set if already disabled
	 * by checking the Enabled flag checkbox
	 *
	 * Enabled Flag and End date should be grayed out
	 * @return boolean
	 */
	public boolean verifyEnableConditionSet( )
	{
		boolean flagVerifyEnabled = false;

		wait.waitForElementDisplayed(enabledFlag);
		boolean enabledFlagGrayedOut = !enabledFlag.isEnabled();
		VertexLogger.log("enabledFlagGrayedOut: " + enabledFlagGrayedOut);

		boolean endDateGrayedOut = !endDate.isEnabled();
		VertexLogger.log("endDateGrayedOut: " + endDateGrayedOut);

		boolean cancelButtonActiveFlag = cancelButton.isEnabled();
		VertexLogger.log("cancelButtonActiveFlag: " + cancelButtonActiveFlag);

		if ( enabledFlagGrayedOut && endDateGrayedOut && cancelButtonActiveFlag )
		{
			flagVerifyEnabled = true;
		}
		return flagVerifyEnabled;
	}

	/**
	 * Method to search data in particular column of
	 * summary tables
	 *
	 * @param ele
	 * @param text
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresentInParticularColumn( List<WebElement> ele, String text )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(ele));
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Export to CSV on INV Condition Sets page
	 *
	 * @param instName
	 * @return boolean
	 */

	public boolean exportToCSVInvConditionSets( String instName ) throws IOException
	{
		boolean flagInstanceMatch = false;
		boolean flagCsvRecordsMatch = false;
		boolean flagAccessTypeMatch = false;
		boolean startDateFlag = false;
		boolean endDateFlag = false;
		boolean enabledFlagRules = false;
		boolean condSetScopeFlag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		String fileName = "InventoryConditionSets_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToInvConditionSets();
		if ( verifyTitleInvConditionSetsPage() )
		{
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
						// Accessing Values by Column Index
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
						.withHeader("Condition Name", "Access Type", "Condition Set Scope", "Start Date", "End Date",
							"Enabled")
						.withTrim()) ; )
				{
					for ( CSVRecord csvRecord : csvParser )
					{
						// Accessing values by Header names
						String conditionName_CSV = csvRecord.get("Condition Name");
						String accessType_CSV = csvRecord.get("Access Type");
						String scope_CSV = csvRecord.get("Condition Set Scope");
						String startDate_CSV = csvRecord.get("Start Date");
						String endDate_CSV = csvRecord.get("End Date");
						String enabled_CSV = csvRecord.get("Enabled");

						VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
						VertexLogger.log("---------------");
						VertexLogger.log("Condition Name : " + conditionName_CSV);
						VertexLogger.log("Access Type : " + accessType_CSV);
						VertexLogger.log("Condition Set Scope : " + scope_CSV);
						VertexLogger.log("Start Date : " + startDate_CSV);
						VertexLogger.log("End Date : " + endDate_CSV);
						VertexLogger.log("Enabled : " + enabled_CSV);
						VertexLogger.log("---------------\n\n");

						if ( !conditionName_CSV.equals("Condition Name") )
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

							Optional dataConditionName = dataPresentInParticularColumn(conditionSetNamePresentation,
								conditionName_CSV);
							if ( dataConditionName.isPresent() )
							{
								Optional accessType = dataPresentInParticularColumn(accessTypeConditionSetPresentation,
									accessType_CSV);
								flagAccessTypeMatch = accessType.isPresent();
								VertexLogger.log("" + flagAccessTypeMatch);
								Optional condSetScope = dataPresentInParticularColumn(condSetScopePresentation,
									scope_CSV);
								condSetScopeFlag = condSetScope.isPresent();
								VertexLogger.log("" + condSetScopeFlag);
								Optional startDate = dataPresentInParticularColumn(startDatePresentation,
									formattedStartDate_CSV);
								startDateFlag = startDate.isPresent();
								VertexLogger.log("" + startDateFlag);
								Optional endDate = dataPresentInParticularColumn(endDatePresentation,
									formattedEndDate_CSV);
								endDateFlag = endDate.isPresent();
								VertexLogger.log("" + endDateFlag);
								Optional enabled = dataPresentInParticularColumn(enabledPresentation, enabled_CSV);
								enabledFlagRules = enabled.isPresent();
								VertexLogger.log("" + enabledFlagRules);
							}
							else
							{
								htmlElement.sendKeys(Keys.END);
								jsWaiter.sleep(100);
								click.clickElement(nextArrowOnSummaryTable);
							}
						}
					}
					if ( flagInstanceMatch && flagAccessTypeMatch && condSetScopeFlag && startDateFlag && endDateFlag &&
						 enabledFlagRules )
					{
						flagCsvRecordsMatch = true;
					}
					else
					{
						flagCsvRecordsMatch = false;
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
				flagCsvRecordsMatch = true;
			}
		}
		return flagCsvRecordsMatch;
	}

	/**
	 * Method to add and verify Inventory Condition Set
	 *
	 * @return conditionSet
	 *
	 */
	public String addAndVerifyINVConditionSet( )
	{
		String value = null;
		String conditionName = null;
		String selectedSrc, selectedOp;
		WebElement source;

		jsWaiter.sleep(2000);
		navigateToInvConditionSets();
		if ( verifyTitleInvConditionSetsPage() )
		{
			click.clickElement(addButton);
		}
		if ( verifyTitleAddConditionSetPage() )
		{
			conditionName = utils.randomAlphaNumericText();
			wait.waitForElementDisplayed(condNameCondSetPage);
			text.enterText(condNameCondSetPage, conditionName);

			dropdown.selectDropdownByValue(invCondSetScopeDropdown, "INV");

			wait.waitForElementDisplayed(enabledFlag);
			checkbox.isCheckboxChecked(enabledFlag);

			String currDate = utils.getFormattedDate();
			VertexLogger.log("Start Date: " + currDate);

			wait.waitForElementDisplayed(startDate);
			click.clickElement(startDate);

			utils.clearTextField(startDate);
			text.enterText(startDate, currDate);

			List<String> dataEntered = Arrays.asList(conditionName, currDate);

			VertexLogger.log("Condition Set created is: " + dataEntered);

			click.clickElement(condNameCondSetPage);
			click.clickElement(addConditionButton);

			getPageTitle().equals("Add Condition");
			wait.waitForElementDisplayed(condValueTextBox);

			dropdown.selectDropdownByDisplayName(conditionSourceDropdown, "Attribute01");
			source = dropdown.getDropdownSelectedOption(conditionSourceDropdown);
			selectedSrc = source.getText();

			dropdown.selectDropdownByDisplayName(conditionOpDropdown, "IS_NULL");
			WebElement operator = dropdown.getDropdownSelectedOption(conditionOpDropdown);
			selectedOp = operator.getText();

			List<String> condDataEntered = Arrays.asList(selectedSrc, selectedOp, value);
			VertexLogger.log("Condition added is: " + condDataEntered);

			click.clickElement(saveButtonConditionPopUp);

			click.clickElement(saveButton);
			expWait.until(ExpectedConditions.visibilityOf(externalFilter));

			int count = Integer.valueOf(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresentInParticularColumn(conditionSetNamePresentation, conditionName);
				if ( data.isPresent() )
				{
					break;
				}
				else
				{
					scroll.scrollElementIntoView(nextArrowOnSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		return conditionName;
	}

	/**
	 * Verify Inv Rules tied to the User defined Condition Set in Tax link application
	 *
	 * @param ruleName
	 * @param conditionSetName
	 * @param phaseTypeOfRule
	 *
	 * @return boolean
	 */

	public boolean verifyInvPreCalcRulesForConditionSetTest( String conditionSetName, String ruleName,
		String phaseTypeOfRule )
	{
		boolean finalFlag = false;
		js.executeScript("arguments[0].scrollIntoView();", invConditionSetsButton);
		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		click.clickElement(invConditionSetsButton);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagConditionPresence = dataPresentInParticularColumn(conditionSetNamePresentation,
				conditionSetName);

			if ( flagConditionPresence.isPresent() )
			{
				WebElement data = flagConditionPresence
					.get()
					.findElement(threeDots);
				scroll.scrollElementIntoView(data);
				jsWaiter.sleep(5000);

				flagConditionPresence
					.get()
					.findElement(threeDots)
					.click();
				expWait.until(ExpectedConditions.visibilityOf(detailsButton));
				detailsButton.click();
				VertexLogger.log("Clicked on Details button for the Condition set" + conditionSetName);
				break;
			}
			else
			{
				actionPageDown
					.sendKeys(Keys.PAGE_DOWN)
					.perform();
				jsWaiter.sleep(5000);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		try
		{
			if ( summaryPageTitle
				.getText()
				.equals("Inventory Condition " + conditionSetName + " Details") )
			{
				int pageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
				for ( int i = 1 ; i <= pageCount ; i++ )
				{
					Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentationInDetails,
						ruleName);
					if ( flagRulePresence.isPresent() )
					{
						Optional<WebElement> flagPhaseType = dataPresentInParticularColumn(
							phaseTypePresentationInDetails, phaseTypeOfRule);
						if ( flagPhaseType.isPresent() )
						{
							VertexLogger.log("Rule verified in the details tab for condition set: " + conditionSetName);
							finalFlag = true;
							break;
						}
					}
					else
					{
						js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
						click.clickElement(nextArrowOnSummaryTable);
					}
				}
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Data is not present in the Summary Table");
		}
		return finalFlag;
	}

	/**
	 * Disable the User defined Condition Set in Tax link application
	 *
	 * @param conditionSetName
	 *
	 * @return boolean
	 */

	public boolean disableConditionset( String conditionSetName )
	{
		boolean finalFlag = false;

		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		click.clickElement(invConditionSetsButton);
		if ( verifyTitleInvConditionSetsPage() )
		{
			expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		}
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> condsetNameForName = dataPresentInParticularColumn(conditionSetNamePresentation,
				conditionSetName);
			if ( condsetNameForName.isPresent() )
			{
				expWait.until(ExpectedConditions.visibilityOf(actions));
				condsetNameForName
					.get()
					.findElement(editButtonFollowingCondName)
					.click();
				break;
			}
			else
			{
				actionPageDown
					.sendKeys(Keys.PAGE_DOWN)
					.perform();
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( verifyTitleEditInvConditionSetPage() )
		{
			click.clickElement(enabledFlag);
			wait.waitForElementEnabled(saveButton);
			click.clickElement(saveButton);
			if ( popUpConditionUpdate.isDisplayed() )
			{
				if ( messageOnEditSaveCondition
					.getText()
					.equals(InventoryRulesMapping.RuleMappingDetails.messageEditConditionSetPage) )
				{
					click.clickElement(yesOnEditSaveConditionPopUp);
					finalFlag = true;
					wait.waitForElementDisplayed(externalFilter);
				}
			}
		}
		return finalFlag;
	}

	/**
	 * Copy INV Condition Sets in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean copyUSERInvConditionSetsRulesMapping( )
	{
		boolean flagcopyConditionSet = true;
		String condSelected;
		verifyTitleInvConditionSetsPage();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFoundForUserAccess = dataPresentInParticularColumn(
				accessTypeConditionSetPresentation, "USER");
			if ( dataFoundForUserAccess.isPresent() )
			{
				WebElement dataFound = dataFoundForUserAccess
					.get()
					.findElement(actionsButton);
				actionPageDown.moveToElement(dataFound);
				expWait.until(ExpectedConditions.visibilityOf(actions));
				dataFoundForUserAccess
					.get()
					.findElement(actionsButton)
					.click();
				condSelected = dataFoundForUserAccess
					.get()
					.findElement(condSetNamePresentation)
					.getText();
				VertexLogger.log("Condition Set selected : " + condSelected);
				copyButtonUserConditionSet.click();
				break;
			}
			else
			{
				if ( i == countSummaryTable )
				{
					VertexLogger.log("No Condition Set is present with USER access Type!!");
					flagcopyConditionSet = false;
					break;
				}
				else
				{
					actionPageDown
						.sendKeys(Keys.PAGE_DOWN)
						.perform();
				}
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		if ( flagcopyConditionSet )
		{
			if ( verifyCopyConditionUser() )
			{
				flagcopyConditionSet = true;
			}
			else
			{
				flagcopyConditionSet = false;
			}
		}
		return flagcopyConditionSet;
	}

	/**
	 * Method to verify copy of Condition Set
	 *
	 * @return boolean
	 */
	public boolean verifyCopyConditionUser( )
	{
		boolean flagCopyConditionSet = false;

		if ( verifyTitleCopyInvConditionSetPage() )
		{
			boolean condNameCopyCondSetPageFlag = condNameCondSetPage.isEnabled();
			VertexLogger.log("condNameCopyCondSetPageFlag: " + condNameCopyCondSetPageFlag);

			String conditionSetName = condNameCondSetPage.getAttribute("value");
			boolean condNameContainsCopywordFLag = condNameCondSetPage
				.getAttribute("value")
				.contains("-COPY");
			VertexLogger.log("condNameContainsCopywordFLag: " + condNameContainsCopywordFLag);

			boolean stDateCopyCondSetPageFlag = startDate.isEnabled();
			VertexLogger.log("stDateCopyCondSetPageFlag: " + stDateCopyCondSetPageFlag);

			boolean endDateCopyCondSetPageFlag = endDate.isEnabled();
			VertexLogger.log("endDateCopyCondSetPageFlag: " + endDateCopyCondSetPageFlag);

			boolean enabledCopyCondSetPageFlag = enabledFlag.isEnabled();
			VertexLogger.log("enabledCopyCondSetPageFlag: " + enabledCopyCondSetPageFlag);

			boolean enabledCopyAssociatedRuleFlag = copyAssociatedRuleFlag.isEnabled();
			VertexLogger.log("enabledCopyAssociatedRuleFlag: " + enabledCopyAssociatedRuleFlag);

			boolean addCondSetPageFlag = addConditionButton.isEnabled();
			VertexLogger.log("addCondSetPageFlag: " + addCondSetPageFlag);

			boolean cancelCopyCondSetPageFlag = cancelButton.isEnabled();
			VertexLogger.log("cancelViewCondSetPageFlag: " + cancelCopyCondSetPageFlag);

			saveButton.click();

			if ( popUpConditionUpdate.isDisplayed() )
			{
				if ( messageOnCopySaveCondition
					.getText()
					.equals(InventoryRulesMapping.RuleMappingDetails.messageCopyConditionSetPage) )
				{
					click.clickElement(yesOnEditSaveConditionPopUp);
				}
			}
			flagCopyConditionSet = isRecordInConditionSetSummary(conditionSetName);
		}
		return flagCopyConditionSet;
	}

	/**
	 * Check the record is present in summary table
	 *
	 * @param conditionSetName
	 *
	 * @return boolean
	 */
	private boolean isRecordInConditionSetSummary( String conditionSetName )
	{
		boolean flagCopiedCondSetInSummary = false;
		wait.waitForElementDisplayed(externalFilter);
		checkPageNavigation();
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(conditionSetNamePresentation,
				conditionSetName.trim());
			if ( dataFound.isPresent() )
			{
				String copiedRecordInSummary = dataFound
					.get()
					.findElement(condSetNamePresentation)
					.getText();
				if ( !copiedRecordInSummary.isEmpty() )
				{
					flagCopiedCondSetInSummary = true;
					VertexLogger.log("Copied record present in Summary Table " + copiedRecordInSummary);
					break;
				}
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return flagCopiedCondSetInSummary;
	}

	/**
	 * get count of Rules tied to the User defined Condition Set in Tax link application
	 *
	 * @param conditionSetName
	 *
	 * @return rulesCount
	 */

	public int getRulesCountForUserDefinedInvConditionSetTest( String conditionSetName )
	{
		int rulesCount = 0;

		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		click.clickElement(invConditionSetsButton);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagConditionPresence = dataPresentInParticularColumn(conditionSetNamePresentation,
				conditionSetName);

			if ( flagConditionPresence.isPresent() )
			{
				WebElement data = flagConditionPresence
					.get()
					.findElement(threeDots);
				scroll.scrollElementIntoView(data);
				jsWaiter.sleep(5000);

				flagConditionPresence
					.get()
					.findElement(threeDots)
					.click();
				expWait.until(ExpectedConditions.visibilityOf(detailsButton));
				detailsButton.click();
				VertexLogger.log("Clicked on Details button for the Condition set " + conditionSetName);
				break;
			}
			else
			{
				actionPageDown
					.sendKeys(Keys.PAGE_DOWN)
					.perform();
				jsWaiter.sleep(5000);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		try
		{
			if ( summaryPageTitle
				.getText()
				.equals("Inventory Condition " + conditionSetName + " Details") )
			{
				rulesCount = Integer.parseInt(totalPageCountSummaryTable.getText());
				VertexLogger.log("Available Rules count : " + rulesCount);

				if ( rulesCount == 0 )
				{
					VertexLogger.log("No Rules associated with condition set");
				}
				jsWaiter.sleep(5000);
				closeButtonConditionSet.click();
			}
		}
		catch ( Exception ex )
		{
			VertexLogger.log("Data is not present in the Summary Table" + ex.getMessage());
		}
		return rulesCount;
	}

	/**
	 * Verify Inv Rules tied to the User copied Condition Set in Tax link application
	 *
	 * @param ruleName
	 * @param conditionSetName
	 *
	 * @return boolean
	 */
	public boolean verifyInvRulesForUserCopiedInvConditionSetTest( String conditionSetName, String ruleName )
	{
		boolean finalFlag = false;

		expWait.until(ExpectedConditions.visibilityOf(invConditionSetsButton));
		click.clickElement(invConditionSetsButton);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> flagConditionPresence = dataPresentInParticularColumn(conditionSetNamePresentation,
				conditionSetName);

			if ( flagConditionPresence.isPresent() )
			{
				WebElement data = flagConditionPresence
					.get()
					.findElement(threeDots);
				scroll.scrollElementIntoView(data);
				jsWaiter.sleep(5000);

				flagConditionPresence
					.get()
					.findElement(threeDots)
					.click();
				expWait.until(ExpectedConditions.visibilityOf(detailsButton));
				detailsButton.click();
				VertexLogger.log("Clicked on Details button for the Condition set " + conditionSetName);
				break;
			}
			else
			{
				actionPageDown
					.sendKeys(Keys.PAGE_DOWN)
					.perform();
				jsWaiter.sleep(5000);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		try
		{
			jsWaiter.sleep(1000);
			if ( summaryPageTitle
				.getText()
				.equals("Inventory Condition " + conditionSetName + " Details") )
			{
				int pageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
				for ( int i = 1 ; i <= pageCount ; i++ )
				{
					Optional<WebElement> flagRulePresence = dataPresentInParticularColumn(ruleNamePresentationInDetails,
						ruleName);
					if ( flagRulePresence.isPresent() )
					{
						Optional<WebElement> flagRulePresent = dataPresentInParticularColumn(
							ruleNamePresentationInDetails, ruleName);
						if ( flagRulePresent.isPresent() )
						{
							VertexLogger.log(
								ruleName + " Rule verified in the details tab for condition set: " + conditionSetName);
							finalFlag = true;
							break;
						}
					}
					else
					{
						js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
						click.clickElement(nextArrowOnSummaryTable);
					}
				}
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Data is not present in the Summary Table");
		}
		return finalFlag;
	}

	/**
	 * Method to check when an inv condition set is disabled,
	 * all associated inventory Pre calc rules tied to that condition set gets disabled
	 *
	 * @param preCalcRuleName
	 *
	 * @return boolean
	 */
	public boolean verifyPreCalcDisabledInvRulesForDisabledInvConditionSet( String preCalcRuleName )
	{
		boolean flagRuleDisabledVerified = false;

		expWait.until(ExpectedConditions.visibilityOf(invPreRulesButton));
		click.clickElement(invPreRulesButton);

		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		jsWaiter.sleep(2000);
		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(ruleNamePresentation, preCalcRuleName);
			if ( dataFound.isPresent() )
			{
				if ( dataFound
					.get()
					.findElement(enabledRulePresentation)
					.getText()
					.equals("N") )
				{
					flagRuleDisabledVerified = true;
					VertexLogger.log("Pre-calc rule has been disabled for the condition set");
					break;
				}
				else
				{
					VertexLogger.log("Pre-calc rule is still enabled for the condition set");
				}
			}
			else
			{
				js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return flagRuleDisabledVerified;
	}

	/**
	 * View INV Condition Set with System level access type in INV Rules Mapping
	 * in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean viewInvSystemConditionSet( )
	{
		final AtomicBoolean flagViewSYSTEMConditionSets = new AtomicBoolean(false);

		navigateToInvConditionSets();
		verifyTitleInvConditionSetsPage();
		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int l = 0 ; l < accessTypeConditionSetPresentation.size() ; l++ )
			{
				String condSetName = conditionSetNamePresentation
					.get(l)
					.getText();

				if ( accessTypeConditionSetPresentation
						 .get(l)
						 .getText()
						 .equals("SYSTEM") && (conditionSetNamePresentation
					.get(l)
					.getText()).contains("ONLY") )
				{
					VertexLogger.log(condSetName);
					expWait.until(ExpectedConditions.visibilityOfAllElements(accessTypeConditionSetPresentation));
					WebElement dataFound = conditionSetNamePresentation
						.get(l)
						.findElement(viewButtonConditionSet);
					scroll.scrollElementIntoView(dataFound);
					conditionSetNamePresentation
						.get(l)
						.findElement(viewButtonConditionSet)
						.click();
					if ( verifyViewConditionUser() )
					{
						click.clickElement(cancelButton);
						wait.waitForElementDisplayed(summaryPageTitle);
						VertexLogger.log("passed");
						flagViewSYSTEMConditionSets.set(true);
						break;
					}
					else
					{
						flagViewSYSTEMConditionSets.set(false);
					}
				}
				else
				{
					if ( l == 9 )
					{
						click.clickElement(nextArrowOnSummaryTable);
					}
				}
			}
		}
		return flagViewSYSTEMConditionSets.get();
	}

}

