package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.APInvoiceSource;
import com.vertex.quality.connectors.taxlink.ui.enums.LegalEntity;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.IntStream;

/**
 * This class contains all the all the methods to test the pages in AP Tax Action Ranges tab in TaxLink UI
 *
 * @author Shilpi.Verma
 */
public class TaxLinkAPTaxActionRangesPage extends TaxLinkBasePage
{
	private WebDriverWait wait = new WebDriverWait(driver, 10);
	private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	private Actions act = new Actions(driver);

	public TaxLinkAPTaxActionRangesPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@value='OVERCHARGED']")
	protected WebElement overchargedSection;

	@FindBy(xpath = "//button[@value='UNDERCHARGED']")
	protected WebElement underchargedSection;

	@FindBy(xpath = "//button[@value='ZEROCHARGED']")
	protected WebElement zerochargedSection;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='legalEntityName']")
	private List<WebElement> summaryTableAPTaxActRanges;

	@FindBy(xpath = "//*[@id='myGrid']/div/div/div[1]/div/div[3]/div[3]/div[1]/div/div/div/div/button")
	private WebElement threeDots;

	@FindBy(name = "holdReasonCode")
	private WebElement applyHoldText;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntity;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement businessUnit;

	@FindBy(xpath = "//label[contains(text(), 'AP Invoice Source')]/following-sibling::select")
	private WebElement apInvSrc;

	@FindBy(xpath = "//label[contains(text(), 'Tax Action')]/following-sibling::select")
	private WebElement taxAction;

	@FindBy(xpath = "//label[contains(text(), 'Situs Country')]/following-sibling::select")
	private WebElement situsCountry;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type')]/following-sibling::select")
	protected WebElement supplierType;

	@FindBy(xpath = "//input[@name='Amount From']")
	private WebElement amountFrom;

	@FindBy(xpath = "//input[@name='Amount To']")
	private WebElement amountTo;

	@FindBy(xpath = "//label[contains(text(), 'Start Date')]/following-sibling::div/div/input")
	private WebElement startDate;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDate;

	@FindBy(xpath = "//input[@type = 'checkbox']")
	private WebElement enabledCheckbox;

	@FindBy(xpath = "//input[@name='holdFlag']")
	private WebElement applyHoldCheckBox;

	@FindBy(xpath = "(//div/p[2])[1]")
	private WebElement messageDataSaved;

	@FindBy(xpath = "(//div/p[2])[2]")
	private WebElement messageNewRec;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
	private List<WebElement> endDateCalendar;

	@FindBy(xpath = "//div[@class = 'notification-container']")
	WebElement errorDuplicateData;

	@FindBy(xpath = "//div[@class = 'notification__inner']/p[contains(text(), 'VTX')]/following-sibling::p")
	private WebElement errorMsg;

	@FindBy(xpath = "//button[@class='notification__btn-close']")
	private WebElement closeErrorMsg;

	@FindBy(xpath = "//button[contains(text(), 'Add')]")
	private WebElement addButtonInsideAdd;

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
	 * Method to pass dynamic index values to WebElement
	 *
	 * @param num
	 *
	 * @return
	 */
	public WebElement xpathInd( int num )
	{
		By element = By.xpath("//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div[1]/div[" + num + "]");
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(element));

		return ele;
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
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele, By.tagName("option")));

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
	 * Method to add new record in AP Tax Action Ranges
	 * as per the section name passed
	 *
	 * @param ele
	 *
	 * @return
	 */
	public boolean addAPTaxActRanges( WebElement ele )
	{
		boolean flag;
		if(apTaxActRangesTab.isDisplayed())
		{
			js.executeScript("arguments[0].scrollIntoView();", apTaxActRangesTab);
			expWait.until(ExpectedConditions.visibilityOf(apTaxActRangesTab));
			click.clickElement(apTaxActRangesTab);
		}
		else
		{
			navigateToAPTaxActRanges();
		}

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = summaryPageTitle
			.getText()
			.equals("AP Tax Action Ranges");

		wait.until(ExpectedConditions.visibilityOf(ele));
		click.clickElement(ele);
		click.clickElement(addButton);

		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		String text_LE = drpDown_Select_GetText(legalEntity, 1).get(0);
		String text_BU = drpDown_Select_GetText(businessUnit, 1).get(0);
		String text_AP = drpDown_Select_GetText(apInvSrc, 1).get(0);
		String text_TA = drpDown_Select_GetText(taxAction, 1).get(1);
		dropdown.selectDropdownByIndex(situsCountry, 1);
		dropdown.selectDropdownByIndex(supplierType, 1);

		String amtFrom = randNum();
		text.enterText(amountFrom, amtFrom);

		String amtTo = amtFrom + "1";
		text.enterText(amountTo, amtTo);

		boolean flag2 = startDate
			.getAttribute("value")
			.equals(LegalEntity.FIELDS.defaultStartDate);

		click.clickElement(endDate);
		endDateCalendar
			.stream()
			.filter(date -> date
				.getText()
				.equals(utils.getCurrentDate()))
			.findFirst()
			.get()
			.click();

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag3 = messageDataSaved.isDisplayed() && messageDataSaved
			.getText()
			.equals("Data Saved Successfully");

		boolean flag4 = messageNewRec.isDisplayed() && messageNewRec
			.getText()
			.equals("Please click on the Add button to enter a new record");

		boolean flag6 = addButtonInsideAdd.isEnabled();

		click.clickElement(closeButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		boolean flag5 = false;

		DecimalFormat formatNum = new DecimalFormat("0.00");
		String amt_From = formatNum.format(Integer.parseInt(amtFrom));
		String amt_To = formatNum.format(Integer.parseInt(amtTo));

		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < summaryTableAPTaxActRanges.size() ; j++ )
			{
				WebElement n = summaryTableAPTaxActRanges.get(j);

				if ( n
					.findElement(xpathIndex(5))
					.getText()
					.equals(amt_To) )
				{
					boolean legal_entity = n
						.getText()
						.equals(text_LE);
					boolean business_unit = n
						.findElement(xpathIndex(1))
						.getText()
						.equals(text_BU);
					boolean ap_invoice = n
						.findElement(xpathIndex(2))
						.getText()
						.equals(text_AP);
					boolean amt_from = n
						.findElement(xpathIndex(4))
						.getText()
						.equals(amt_From);

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean tax_action = n
						.findElement(xpathIndex(6))
						.getText()
						.equals(text_TA);

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean apply_hold = n
						.findElement(xpathIndex(7))
						.getText()
						.equals("N");

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean enabled = n
						.findElement(xpathIndex(8))
						.getText()
						.equals("Y");

					if ( legal_entity && business_unit && ap_invoice && amt_from && tax_action && apply_hold &&
						 enabled )
					{
						flag5 = true;
						VertexLogger.log("Data present for " + ele.getText() + "::" + " LegalEntity= " + text_LE +
										 " ,Business Unit= " + text_BU + " ,AP Invoice Source= " + text_AP +
										 " ,Amount From= " + amt_From + " ,Amount To= " + amt_To + " ,Apply Hold= N " +
										 " ,Enabled = Y");

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
	 * Method Overloading------
	 * Method to add new record in AP Tax Action Ranges
	 *
	 * @return
	 */
	public boolean addAPTaxActRanges( )
	{
		boolean flag;

		boolean flag1 = addAPTaxActRanges(overchargedSection);
		boolean flag2 = addAPTaxActRanges(underchargedSection);
		boolean flag3 = addAPTaxActRanges(zerochargedSection);

		if ( flag1 && flag2 && flag3 )
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
	 * Method to verify Edit functionality of AP Tax Action Ranges
	 * as per the section name and index values of WebElement passed
	 *
	 * @return
	 */
	public boolean editAPTaxActRanges( WebElement e, int ind1, int ind2 )
	{
		boolean flag = false;

		click.clickElement(e);

		String apply_hold = xpathInd(ind1).getText();
		String enabled = xpathInd(ind2).getText();

		click.clickElement(editButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		click.clickElement(enabledCheckbox);

		if ( apply_hold.equals("N") )
		{
			boolean flag3 = !applyHoldCheckBox.isSelected();
			if ( flag3 )
			{
				click.clickElement(applyHoldCheckBox);
				wait.until(ExpectedConditions.visibilityOf(applyHoldText));
				text.enterText(applyHoldText, "Apply Hold");
			}
			else
			{
				VertexLogger.log("Apply Hold checkbox is selected but Value in Summary Table is 'N'");
			}
		}
		else
		{

			boolean flag3 = applyHoldCheckBox.isSelected();
			if ( flag3 )
			{
				click.clickElement(applyHoldCheckBox);
				flag3 = !applyHoldText.isDisplayed();
			}
			else
			{
				VertexLogger.log("Apply Hold checkbox is not selected but Value in Summary Table is 'Y'");
			}
		}

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean flag1 = !xpathInd(ind1)
			.getText()
			.equals(apply_hold);
		boolean flag2 = !xpathInd(ind2)
			.getText()
			.equals(enabled);

		if ( flag1 && flag2 )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method Overloading--------------
	 * Method to Edit record in AP Tax Action Ranges
	 *
	 * @return
	 */
	public boolean editAPTaxActRanges( )
	{
		boolean flag = false;

		navigateToAPTaxActRanges();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = editAPTaxActRanges(overchargedSection, 8, 9);
		boolean flag2 = editAPTaxActRanges(underchargedSection, 8, 9);
		boolean flag3 = editAPTaxActRanges(zerochargedSection, 8, 9);

		if ( flag1 && flag2 && flag3 )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to verify View functionality of AP Tax Action Ranges
	 *
	 * @param e
	 *
	 * @return
	 */
	public boolean viewAPTaxActRanges( WebElement e )
	{
		boolean flag = false;

		click.clickElement(e);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		jsWaiter.sleep(2000);
		click.clickElement(threeDots);
		wait.until(ExpectedConditions.visibilityOf(viewButton));
		click.clickElement(viewButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag1 = !legalEntity.isEnabled();
		boolean flag2 = !businessUnit.isEnabled();
		boolean flag3 = !apInvSrc.isEnabled();
		boolean flag4 = !taxAction.isEnabled();
		boolean flag5 = !situsCountry.isEnabled();
		boolean flag6 = !supplierType.isEnabled();
		boolean flag7 = !amountFrom.isEnabled();
		boolean flag8 = !amountTo.isEnabled();
		boolean flag9 = !startDate.isEnabled();
		boolean flag10 = !endDate.isEnabled();
		boolean flag11 = !enabledCheckbox.isEnabled();
		boolean flag12 = !applyHoldCheckBox.isEnabled();
		boolean flag13 = !saveButton.isEnabled();

		click.clickElement(cancelButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		if ( flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10 && flag11 &&
			 flag12 && flag13 )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method Overloading--------
	 * Method to view AP Tax Action Ranges
	 *
	 * @return
	 */
	public boolean viewAPTaxActRanges( )
	{
		boolean flag = false;

		navigateToAPTaxActRanges();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = viewAPTaxActRanges(overchargedSection);
		boolean flag2 = viewAPTaxActRanges(underchargedSection);
		boolean flag3 = viewAPTaxActRanges(zerochargedSection);

		if ( flag1 && flag2 && flag3 )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to format date to YYYY-MM-DD
	 *
	 * @param s
	 *
	 * @return
	 */
	public String dateFormat( String s )
	{

		String[] date = s.split("/");
		String new_Date = date[2] + "-" + date[0] + "-" + date[1];

		return new_Date;
	}

	/**
	 * Method to check error messages
	 * when amount_to is lesser than amount_from
	 * when end date is lesser than start date
	 *
	 * @param ele
	 *
	 * @return
	 */
	public boolean negative_addTaxActionRanges( WebElement ele )
	{
		boolean error_flag = false;

		wait.until(ExpectedConditions.visibilityOf(ele));
		click.clickElement(ele);

		click.clickElement(addButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		drpDown_Select_GetText(taxAction, 1).get(1);
		text.enterText(amountFrom, "11");
		text.enterText(amountTo, "10");

		click.clickElement(saveButton);

		wait.until(ExpectedConditions.visibilityOf(errorDuplicateData));
		List<WebElement> errorString1 = errorDuplicateData.findElements(By.xpath(".//*"));
		if ( errorString1.size() > 0 )
		{
			error_flag = errorMsg
				.getText()
				.equals("Amount To should be greater than Amount From");
		}

		click.clickElement(closeErrorMsg);

		utils.clearTextField(startDate);
		text.enterText(startDate, utils.getFormattedDate());
		click.clickElement(endDate);
		text.enterText(endDate, utils.getYesterdayDate());
		wait.until(ExpectedConditions.visibilityOf(saveButton));
		click.javascriptClick(saveButton);

		wait.until(ExpectedConditions.visibilityOf(errorDuplicateData));
		List<WebElement> errorString2 = errorDuplicateData.findElements(By.xpath(".//*"));
		if ( errorString2.size() > 0 )
		{
			error_flag = errorMsg
				.getText()
				.equals("End Date can't be before Start Date");
		}

		click.javascriptClick(closeButton);
		wait.until(ExpectedConditions.visibilityOf(ele));

		return error_flag;
	}

	/**
	 * Method Overloading------
	 * Method to check error message for each section
	 *
	 * @return
	 */
	public boolean negative_addTaxActionRanges( )
	{
		boolean flag = false;

		navigateToAPTaxActRanges();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean overcharged_flag = negative_addTaxActionRanges(overchargedSection);
		boolean undercharged_flag = negative_addTaxActionRanges(underchargedSection);
		boolean zerocharged_flag = negative_addTaxActionRanges(zerochargedSection);

		if ( overcharged_flag && undercharged_flag && zerocharged_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method of export to CSV - section OVERCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Overcharged( ) throws Exception
	{
		boolean flag = exportToCSV(overchargedSection, 7, 8, 9);

		return flag;
	}

	/**
	 * Method of export to CSV - section UNDERCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Undercharged( ) throws Exception
	{
		boolean flag = exportToCSV(underchargedSection, 7, 8, 9);

		return flag;
	}

	/**
	 * Method of export to CSV - section ZEROCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Zerocharged( ) throws Exception
	{
		boolean flag = exportToCSV(zerochargedSection, 7, 8, 9);

		return flag;
	}

	/**
	 * Method to verify Export To CSV
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV( WebElement e, int ind1, int ind2, int ind3 ) throws Exception
	{
		boolean flag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		navigateToAPTaxActRanges();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		click.clickElement(e);
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> data = summaryTableAPTaxActRanges
				.stream()
				.findFirst();

			String legal_entity = data
				.get()
				.getText();

			String business_unit = data
				.get()
				.findElement(xpathIndex(1))
				.getText();

			String ap_inv = data
				.get()
				.findElement(xpathIndex(2))
				.getText();

			String supplier_type = data
				.get()
				.findElement(xpathIndex(3))
				.getText();

			String amtFrom = data
				.get()
				.findElement(xpathIndex(4))
				.getText();
			String amt_From = (amtFrom.split("\\."))[0];

			String amtTo = data
				.get()
				.findElement(xpathIndex(5))
				.getText();
			String amt_To = (amtTo.split("\\."))[0];

			String tax_action = xpathInd(ind1).getText();
			String apply_hold = xpathInd(ind2).getText();
			String enabled = xpathInd(ind3).getText();

			click.clickElement(editButton);
			wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

			String start_Date = startDate.getAttribute("value");
			String end_Date = endDate.getAttribute("value");

			if ( !start_Date.equals("") )
			{
				formattedStartDate_CSV = utils.getSummaryFormattedDate(start_Date);
				VertexLogger.log("Formatted Start Date : " + formattedStartDate_CSV);
			}
			else
			{
				formattedStartDate_CSV = start_Date;
			}

			if ( !end_Date.equals("") )
			{
				formattedEndDate_CSV = utils.getSummaryFormattedDate(end_Date);
				VertexLogger.log("Formatted End Date : " + formattedEndDate_CSV);
			}
			else
			{
				formattedEndDate_CSV = end_Date;
			}
			click.clickElement(cancelButton);
			wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

			String fileName = "TaxAction_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			setFluentWait(file);
			jsWaiter.sleep(5000);
			List<CSVRecord> records = parseCSVRecord(file);
			boolean header_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

			Optional<CSVRecord> csvData = records
				.stream()
				.filter(rec -> rec
								   .get(0)
								   .contains(e.getAttribute("value")) && rec
								   .get(1)
								   .contains(legal_entity) && rec
								   .get(2)
								   .contains(business_unit) && rec
								   .get(3)
								   .contains(ap_inv) && rec
								   .get(4)
								   .contains(supplier_type) && rec
								   .get(5)
								   .contains(amt_From) && rec
								   .get(6)
								   .contains((amt_To)) && rec
								   .get(7)
								   .contains(tax_action) && rec
								   .get(8)
								   .contains(apply_hold) && rec
								   .get(9)
								   .contains(enabled) && rec
								   .get(10)
								   .contains(formattedStartDate_CSV) && rec
								   .get(11)
								   .equals(formattedEndDate_CSV))
				.findFirst();

			boolean data_flag = false;
			if ( csvData.isPresent() )
			{
				data_flag = true;
				VertexLogger.log("CSV Record Number: " + csvData
					.get()
					.getRecordNumber());
			}

			if ( file.delete() )
			{
				VertexLogger.log("File deleted successfully");
			}
			else
			{
				VertexLogger.log("Failed to delete the file");
			}

			if ( header_flag && data_flag )
			{
				flag = true;
			}
		}
		else
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to add and check empty fields auto populates with "All" prefix
	 *
	 * @param ele
	 * @param ele1
	 * @param ele2
	 * @param ele3
	 * @param ele4
	 * @param val1
	 * @param val2
	 * @param val3
	 * @param val4
	 *
	 * @return
	 */
	public boolean emptyFields_reflectAll( WebElement ele, WebElement ele1, WebElement ele2, WebElement ele3,
		WebElement ele4, String val1, String val2, String val3, String val4 )
	{
		boolean flag = false;

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		wait.until(ExpectedConditions.visibilityOf(ele));
		click.clickElement(ele);
		click.clickElement(addButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele1, By.tagName("option")));
		dropdown.selectDropdownByIndex(ele1, 1);
		String ele1_value = dropdown
			.getDropdownSelectedOption(ele1)
			.getAttribute("name");
		VertexLogger.log(ele1_value);

		String ele2_value = dropdown
			.getDropdownSelectedOption(ele2)
			.getAttribute("name");
		VertexLogger.log(ele2_value);

		String ele3_value = dropdown
			.getDropdownSelectedOption(ele3)
			.getAttribute("name");
		VertexLogger.log(ele3_value);

		String ele4_value = dropdown
			.getDropdownSelectedOption(ele4)
			.getAttribute("name");
		VertexLogger.log(ele4_value);

		dropdown.selectDropdownByIndex(taxAction, 1);

		String amtFrom = randNum();
		text.enterText(amountFrom, amtFrom);

		String amtTo = amtFrom + "1";
		text.enterText(amountTo, amtTo);

		boolean all_flag = ele2_value.contains("All") && ele3_value.contains("All") && ele4_value.contains("All");

		Map<String, String> map = new TreeMap<>();
		map.put(val1, ele1_value);
		map.put(val2, ele2_value);
		map.put(val3, ele3_value);
		map.put(val4, ele4_value);
		List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.elementToBeClickable(closeButton));
		click.clickElement(closeButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean data_flag = false;
		if ( all_flag )
		{
			data_flag = summaryTableAPTaxActRanges
				.stream()
				.anyMatch(el -> el
									.getText()
									.contains(list
										.get(2)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[1]"))
									.getText()
									.contains(list
										.get(1)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[2]"))
									.getText()
									.contains(list
										.get(0)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[3]"))
									.getText()
									.contains(list
										.get(3)
										.getValue()));
		}

		if ( data_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * -- Method overloading
	 * Pass parameters in desired sequence and check the summary table
	 *
	 * @return
	 */
	public boolean emptyFields_reflectAll( )
	{
		boolean flag = false;
		navigateToAPTaxActRanges();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean overcharged_ele1_flag = emptyFields_reflectAll(overchargedSection, legalEntity, businessUnit, apInvSrc,
			supplierType, "LE", "BU", "APInvSrc", "SupplierType");

		boolean overcharged_ele2_flag = emptyFields_reflectAll(overchargedSection, businessUnit, legalEntity, apInvSrc,
			supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean overcharged_ele3_flag = emptyFields_reflectAll(overchargedSection, apInvSrc, legalEntity, businessUnit,
			supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean overcharged_ele4_flag = emptyFields_reflectAll(overchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean undercharged_ele1_flag = emptyFields_reflectAll(underchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean undercharged_ele2_flag = emptyFields_reflectAll(underchargedSection, businessUnit, legalEntity,
			apInvSrc, supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean undercharged_ele3_flag = emptyFields_reflectAll(underchargedSection, apInvSrc, legalEntity,
			businessUnit, supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean undercharged_ele4_flag = emptyFields_reflectAll(underchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean zerocharged_ele1_flag = emptyFields_reflectAll(zerochargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean zerocharged_ele2_flag = emptyFields_reflectAll(zerochargedSection, businessUnit, legalEntity, apInvSrc,
			supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean zerocharged_ele3_flag = emptyFields_reflectAll(zerochargedSection, apInvSrc, legalEntity, businessUnit,
			supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean zerocharged_ele4_flag = emptyFields_reflectAll(zerochargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		if ( overcharged_ele1_flag && overcharged_ele2_flag && overcharged_ele3_flag && overcharged_ele4_flag &&
			 undercharged_ele1_flag && undercharged_ele2_flag && undercharged_ele3_flag && undercharged_ele4_flag &&
			 zerocharged_ele1_flag && zerocharged_ele2_flag && zerocharged_ele3_flag && zerocharged_ele4_flag )
		{
			flag = true;
		}
		return flag;
	}
}

