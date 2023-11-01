package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.enums.LegalEntity;
import com.vertex.quality.connectors.taxlink.ui.pages.TaxLinkAPTaxActionRangesPage;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class contains all the methods to verify the E2E scenario of AP Tax Ranges tab
 *
 * @author Shilpi.Verma, mgaikwad
 */

public class TaxLinkAPTaxActRangesPage extends TaxLinkBasePage
{
	private Actions act = new Actions(driver);

	public TaxLinkAPTaxActRangesPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@value='OVERCHARGED']")
	private WebElement overchargedSection;

	@FindBy(xpath = "//button[@value='UNDERCHARGED']")
	private WebElement underchargedSection;

	@FindBy(xpath = "//button[@value='ZEROCHARGED']")
	private WebElement zerochargedSection;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement businessUnit;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntity;
	@FindBy(xpath = "//label[contains(text(), 'AP Invoice Source')]/following-sibling::select")
	private WebElement apInvSrc;

	@FindBy(xpath = "//label[contains(text(), 'Situs Country')]/following-sibling::select")
	private WebElement situsCountry;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type')]/following-sibling::select")
	protected WebElement supplierType;
	@FindBy(xpath = "//label[contains(text(), 'Tax Action')]/following-sibling::select")
	private WebElement taxAction;

	@FindBy(xpath = "//input[@name='Amount From']")
	private WebElement amountFrom;

	@FindBy(xpath = "//input[@name='Amount To']")
	private WebElement amountTo;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
	private List<WebElement> endDateCalendar;
	@FindBy(xpath = "//input[@name  = 'holdFlag']")
	private WebElement holdFlag;

	@FindBy(xpath = "//textarea[@name  = 'holdReasonCode']")
	private WebElement holdReasonField;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='legalEntityName']")
	private List<WebElement> summaryTableAPTaxActRanges;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='legalEntityName']")
	private List<WebElement> legalEntityPresentation;

	@FindBy(xpath = "//div[@class='ag-overlay']")
	private WebElement noElementToDisplay;

	@FindBy(xpath = "(//div/p[2])[1]")
	private WebElement messageDataSaved;

	@FindBy(xpath = "(//div/p[2])[2]")
	private WebElement messageNewRec;
	@FindBy(xpath = "//button[contains(text(), 'Add')]")
	private WebElement addButtonInsideAdd;

	private By column_ApplyHoldEnabled = By.xpath(".//following-sibling::div[7]");
	private By editButtonTaxActionRanges = By.xpath(
		".//ancestor::div/div/div/div/div/div/div/button[@class='flyout__link optionFont']");

	TaxLinkAPTaxActionRangesPage taxAct = new TaxLinkAPTaxActionRangesPage(driver);

	/**
	 * Method to add new record in AP Tax Action Ranges
	 * as per the section name passed
	 *
	 * @param section
	 * @param taxActn
	 * @param s1
	 *
	 * @return boolean
	 */
	public boolean addAPTaxActRanges( WebElement section, String taxActn, String s1 )
	{
		boolean flag;
		if ( apTaxActRangesTab.isDisplayed() )
		{
			js.executeScript("arguments[0].scrollIntoView();", apTaxActRangesTab);
			expWait.until(ExpectedConditions.visibilityOf(apTaxActRangesTab));
			click.clickElement(apTaxActRangesTab);
		}
		else
		{
			navigateToAPTaxActRanges();
		}

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = summaryPageTitle
			.getText()
			.equals("AP Tax Action Ranges");

		expWait.until(ExpectedConditions.visibilityOf(section));
		click.clickElement(section);
		click.clickElement(addButton);

		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		if ( addViewEditPageTitle
			.getText()
			.equals("Add Tax Scenario:ZERO CHARGED") )
		{
			click.clickElement(holdFlag);
			text.enterText(holdReasonField, "check for Hold");
		}

		String text_BU = drpDown_Select_GetText(businessUnit, 1).get(0);
		dropdown.selectDropdownByDisplayName(taxAction, taxActn);

		text.enterText(amountFrom, "1");

		text.enterText(amountTo, "100");

		boolean flag2 = startDate
			.getAttribute("value")
			.equals(LegalEntity.FIELDS.defaultStartDate);

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag3 = messageDataSaved.isDisplayed() && messageDataSaved
			.getText()
			.equals("Data Saved Successfully");

		boolean flag4 = messageNewRec.isDisplayed() && messageNewRec
			.getText()
			.equals("Please click on the Add button to enter a new record");

		boolean flag6 = addButtonInsideAdd.isEnabled();

		click.clickElement(closeButton);
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		boolean flag5 = false;

		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < summaryTableAPTaxActRanges.size() ; j++ )
			{
				WebElement n = summaryTableAPTaxActRanges.get(j);

				if ( n
					.findElement(xpathIndex(5))
					.getText()
					.equals("100.00") )
				{
					boolean business_unit = n
						.findElement(xpathIndex(1))
						.getText()
						.equals(text_BU);

					boolean amt_from = n
						.findElement(xpathIndex(4))
						.getText()
						.equals("1.00");

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean tax_action = n
						.findElement(xpathIndex(6))
						.getText()
						.equals(s1);

					act
						.sendKeys(Keys.TAB)
						.perform();

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean enabled = n
						.findElement(xpathIndex(8))
						.getText()
						.equals("Y");

					if ( business_unit && amt_from && tax_action && enabled )
					{
						flag5 = true;
						VertexLogger.log(
							"Data present for " + section.getText() + "::" + " ,Business Unit= " + text_BU +
							" ,Amount From= 1" + " ,Amount To= 100" + " ,Enabled = Y");

						break;
					}
				}
				if ( j == summaryTableAPTaxActRanges.size() - 1 )
				{
					click.clickElement(nextArrow);
				}
			}

			if ( flag5 )
			{
				break;
			}
		}

		if ( flag1 && flag2 && flag3 && flag4 && flag5 && flag6 )

		{
			flag = true;
		}
		else

		{
			flag = false;
		}
		return flag;
	}

	/**
	 * Method to pass dynamic index values to By locator
	 *
	 * @param num
	 *
	 * @return
	 */
	public By xpathIndex( int num )
	{
		By column = By.xpath(".//following-sibling::div[" + num + "]");
		return column;
	}

	/**
	 * Method to generate 3-digit random number
	 * range from 100 to 500
	 *
	 * @return
	 */
	public String randNum( )
	{
		int num = IntStream
			.generate(( ) -> (int) (Math.random() * 500) + 100)
			.limit(1)
			.findAny()
			.getAsInt();

		return String.valueOf(num);
	}

	/**
	 * Method to select value from Dropdown and get text of the same
	 *
	 * @param ele
	 * @param index
	 *
	 * @return
	 */
	public List<String> drpDown_Select_GetText( WebElement ele, int index )
	{
		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele, By.tagName("option")));

		click.clickElement(ele);
		dropdown.selectDropdownByIndex(ele, index);
		String text = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getText();
		String attr = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getAttribute("value");

		List<String> drpDown = Arrays.asList(text, attr);

		return drpDown;
	}

	/**
	 * Method for Overcharged
	 *
	 * @return boolean
	 */
	public boolean addAPTaxActRanges_Overcharged( )
	{
		navigateToAPTaxActRanges();
		boolean finalFlag = addAPTaxActRanges(overchargedSection, "Pay Charged Tax", "PAYCHARGED");
		return finalFlag;
	}

	/**
	 * Method for Undercharged
	 *
	 * @return boolean
	 */
	public boolean addAPTaxActRanges_Undercharged( )
	{
		navigateToAPTaxActRanges();
		boolean finalFlag = addAPTaxActRanges(underchargedSection, "Pay Calculated Tax", "PAYCALCULATED");
		return finalFlag;
	}

	/**
	 * Method for Zerocharged
	 *
	 * @return boolean
	 */
	public boolean addAPTaxActRanges_Zerocharged( )
	{
		navigateToAPTaxActRanges();
		boolean finalFlag = addAPTaxActRanges(zerochargedSection, "Accrue All Tax", "ACCRUEALL");
		return finalFlag;
	}

	/**
	 * Method to disable all the records where Apply hold is enabled
	 * for AP Tax Calculation Exclusions tab in TaxLink UI
	 * reason being an Invoice won't get validate until and unless
	 * these records are disabled
	 *
	 * @return boolean
	 */

	public boolean disableApplyHoldRecords( )
	{
		boolean disabledApplyHoldFlag = false;
		taxAct.navigateToAPTaxActRanges();
		boolean disableApplyHoldForOvercharged = disableAPTaxActRanges(overchargedSection);
		boolean disableApplyHoldForUndercharged = disableAPTaxActRanges(underchargedSection);
		boolean disableApplyHoldForZerocharged = disableAPTaxActRanges(zerochargedSection);

		if ( disableApplyHoldForOvercharged && disableApplyHoldForUndercharged && disableApplyHoldForZerocharged )
		{
			disabledApplyHoldFlag = true;
		}
		return disabledApplyHoldFlag;
	}

	/**
	 * Method to disable the Apply hold records in Tax Action ranges
	 *
	 * @param section
	 *
	 * @return finalFlag
	 */
	public boolean disableAPTaxActRanges( WebElement section )
	{
		boolean finalFlag = false;
		wait.waitForElementDisplayed(summaryPageTitle);
		wait.waitForElementDisplayed(section);
		VertexLogger.log("In the section: " + section);
		click.clickElement(section);

		wait.waitForElementDisplayed(addButton);

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());

		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < legalEntityPresentation.size() ; j++ )
			{
				jsWaiter.sleep(5000);
				if ( legalEntityPresentation
					.get(j)
					.findElement(column_ApplyHoldEnabled)
					.getText()
					.equals("Y") )
				{
					jsWaiter.sleep(5000);
					if ( !(legalEntityPresentation
						.get(j)
						.isDisplayed()) )
					{
						js.executeScript("arguments[0].scrollIntoView();", totalPageCountSummaryTable);
					}
					legalEntityPresentation
						.get(j)
						.findElement(By.xpath(".//ancestor::div[4]/following-sibling::div/div[" + (j + 1) +
											  "]/div/div/div/div/div/div/button"))
						.click();
					VertexLogger.log("Clicked on Edit button");
					jsWaiter.sleep(5000);
					if ( holdFlag.isSelected() )
					{
						click.clickElement(holdFlag);
						VertexLogger.log("Disabling the record!!");
						scroll.scrollElementIntoView(saveButton);
						click.clickElement(saveButton);
						finalFlag = true;
						jsWaiter.sleep(5000);
					}
				}
				else
				{
					VertexLogger.log("No Apply hold present on this record!!");
					finalFlag = true;
				}
			}
		}
		if ( count == 0 )
		{
			VertexLogger.log("No record is present!!");
			finalFlag = true;
		}
		return finalFlag;
	}

}
