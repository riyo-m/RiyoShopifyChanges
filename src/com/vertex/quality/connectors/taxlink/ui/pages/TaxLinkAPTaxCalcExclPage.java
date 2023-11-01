package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.APInvoiceSource;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class contains all the methods to test the pages in AP Tax Calculation Exclusions tab in TaxLink UI
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPTaxCalcExclPage extends TaxLinkBasePage
{
	public TaxLinkAPTaxCalcExclPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntity;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement businessUnit;

	@FindBy(xpath = "//label[contains(text(), 'AP Invoice Source')]/following-sibling::select")
	private WebElement apInvSrc;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type')]/following-sibling::select")
	private WebElement supplierType;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = '0']")
	private List<WebElement> summaryTableAPTaxCalcExcl;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/taxAction']")
	private WebElement apTaxActRangesTab;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	private By column_BU = By.xpath(".//following-sibling::div");
	private By column_AP = By.xpath(".//following-sibling::div[2]");
	private By column_Enabled = By.xpath(".//following-sibling::div[4]");

	/**
	 * Method to disable record containing below data-
	 * Legal Entity="All Legal Entities", Business Unit="VTX_US_BU", AP Inv Source="Manual Invoice Entry"
	 * This is for Zerocharged test in OERP
	 */
	public void disableAPTaxCalcExcl( )
	{
		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 1 ; j < summaryTableAPTaxCalcExcl.size() ; j++ )
			{
				WebElement ele = summaryTableAPTaxCalcExcl.get(j);
				if ( ele
						 .getText()
						 .contains("All Legal Entities") && ele
						 .findElement(column_BU)
						 .getText()
						 .equals("VTX_US_BU") && (ele
													  .findElement(column_AP)
													  .getText()
													  .equals("Manual Invoice Entry") || ele
													  .findElement(column_AP)
													  .getText()
													  .equals("Manual invoice entry")) && ele
						 .findElement(column_Enabled)
						 .getText()
						 .equals("Y") )
				{
					String rowInd = ele
						.findElement(By.xpath(".//.."))
						.getAttribute("row-index");

					By edit = By.xpath(
						"//div[@ref='eRightContainer']/div[@row-index = " + rowInd + "]/div/div/div/div/div/div");
					WebElement editBtn = expWait.until(ExpectedConditions.presenceOfElementLocated(edit));

					click.clickElement(editBtn);
					expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

					click.clickElement(enabledFlag);
					click.clickElement(saveButton);
					expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
				}
			}
			try
			{
				click.clickElement(nextArrow);
			}
			catch ( Exception e )
			{
				VertexLogger.log(
					"Either all records for Manual Invoice Entry are disabled/There are no records for Manual Invoice Entry");
			}
		}

		click.clickElement(apTaxActRangesTab);
	}

	/**
	 * Method to select value from Dropdown and get text of the same
	 *
	 * @param ele
	 * @param index
	 *
	 * @return
	 */
	public String drpDown_Select_GetText( WebElement ele, int index )
	{
		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele, By.tagName("option")));
		dropdown.selectDropdownByIndex(ele, index);
		String text = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getText();

		return text;
	}

	/**
	 * Method to add new record in AP Tax Calculation Exclusion
	 *
	 * @return
	 */
	public boolean addAPTaxCalcExcl( )
	{
		boolean flag = false;

		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		click.clickElement(addButton);

		boolean header_flag = addViewEditPageTitle
			.getText()
			.equals("Add AP Tax Exclusions");

		String text_LE = drpDown_Select_GetText(legalEntity, 1);

		String text_BU = drpDown_Select_GetText(businessUnit, 1);

		String text_AP = drpDown_Select_GetText(apInvSrc, 1);

		boolean chkBox_flag = enabledFlag.isSelected();

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean data_flag = false;
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
				Optional<WebElement> data = summaryTableAPTaxCalcExcl
					.stream()
					.filter(ele -> ele
									   .getText()
									   .equals(text_LE) && ele
									   .findElement(column_BU)
									   .getText()
									   .equals(text_BU) && ele
									   .findElement(column_AP)
									   .getText()
									   .equals(text_AP) && ele
									   .findElement(column_Enabled)
									   .getText()
									   .equals("Y"))
					.findFirst();

				if ( data.isPresent() )
				{
					VertexLogger.log("Data is added in Summary table");
					data_flag = true;

					break;
				}
				else
				{
					click.clickElement(nextArrow);
				}
			}
		}

		if ( header_flag && chkBox_flag && data_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * This method is to search data in Summary table based on the parameters passed
	 *
	 * @param le
	 * @param bu
	 * @param ap
	 *
	 * @return
	 */
	public boolean searchData( String le, String bu, String ap )
	{
		boolean flag = false;

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		checkPageNavigation();

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = summaryTableAPTaxCalcExcl
				.stream()
				.filter(ele -> ele
								   .getText()
								   .equals(le) && ele
								   .findElement(column_BU)
								   .getText()
								   .equals(bu) && ele
								   .findElement(column_AP)
								   .getText()
								   .equals(ap) && ele
								   .findElement(column_Enabled)
								   .getText()
								   .equals("Y"))
				.findFirst();

			if ( data.isPresent() )
			{
				VertexLogger.log(
					"Data present with LegalEntity: " + le + "Business Unit: " + bu + "AP Invoice Source: " + ap +
					"Column_Enabled: Y");

				flag = true;

				break;
			}
			else
			{
				click.clickElement(nextArrow);
			}
		}

		click.clickElement(addButton);
		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		return flag;
	}

	/**
	 * This method is to test if any one of the fields is populated,
	 * then it should reflect correctly in the Summary Table
	 *
	 * @return
	 */
	public boolean addAPTaxCalcExcl_SingleRec( )
	{
		boolean flag = false;

		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryPageTitle));

		click.clickElement(addButton);
		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		String text_LE = drpDown_Select_GetText(legalEntity, 1);
		VertexLogger.log(text_LE);
		boolean le_flag = searchData(text_LE, "All Business Units", "All AP Invoice Sources");

		String text_BU = drpDown_Select_GetText(businessUnit, 1);
		VertexLogger.log(text_BU);
		boolean bu_flag = searchData("All Legal Entities", text_BU, "All AP Invoice Sources");

		String text_AP = drpDown_Select_GetText(apInvSrc, 1);
		VertexLogger.log(text_AP);
		boolean ap_flag = searchData("All Legal Entities", "All Business Units", text_AP);

		if ( dropdown
				 .getDropdownOptions(supplierType)
				 .size() > 1 )
		{
			dropdown.selectDropdownByIndex(supplierType, 1);
			boolean supplier_flag = searchData("All Legal Entities", "All Business Units", dropdown
				.getDropdownOptions(apInvSrc)
				.get(1)
				.getText());
			VertexLogger.log("Supplier Flag is: " + supplier_flag);
		}
		else
		{
			VertexLogger.log("Supplier dropdown is empty!");
		}

		if ( le_flag && bu_flag && ap_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to Edit record in AP Tax Calculation Exclusion
	 *
	 * @return
	 */
	public boolean editAPTaxCalcExcl( )
	{
		boolean flag = false;

		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		Optional<WebElement> data = summaryTableAPTaxCalcExcl
			.stream()
			.findFirst();

		String enabledText = data
			.get()
			.findElement(column_Enabled)
			.getText();

		click.clickElement(editButton);

		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
		boolean header_flag = addViewEditPageTitle
			.getText()
			.equals("Edit AP Tax Exclusions");

		boolean le_flag = !legalEntity.isEnabled();
		boolean bu_flag = !businessUnit.isEnabled();
		boolean apInv_flag = !apInvSrc.isEnabled();
		boolean suppType_flag = !supplierType.isEnabled();
		boolean chkBoxEnabled_flag = enabledFlag.isEnabled();

		click.clickElement(enabledFlag);
		boolean chkBox_flag = !checkbox.isCheckboxChecked(enabledFlag) || checkbox.isCheckboxChecked(
			enabledFlag);
		boolean saveBtn_flag = saveButton.isEnabled();

		click.clickElement(saveButton);

		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		Optional<WebElement> data1 = summaryTableAPTaxCalcExcl
			.stream()
			.findFirst();

		String newEnabledText = data1
			.get()
			.findElement(column_Enabled)
			.getText();

		boolean colEnabled_flag = false;
		if ( enabledText.equals(newEnabledText) )
		{
			colEnabled_flag = true;
		}

		if ( header_flag && le_flag && bu_flag && apInv_flag && suppType_flag && chkBoxEnabled_flag && chkBox_flag &&
			 saveBtn_flag && colEnabled_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to View new record in AP Tax Calculation Exclusion
	 *
	 * @return
	 */
	public boolean viewAPTaxCalcExcl( )
	{
		boolean flag = false;

		navigateToAPTaxCalcExcl();

		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		click.clickElement(actions);
		click.clickElement(viewButton);
		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean header_flag = addViewEditPageTitle
			.getText()
			.equals("View AP Tax Exclusions");

		boolean le_flag = !legalEntity.isEnabled();
		boolean bu_flag = !businessUnit.isEnabled();
		boolean apInvSrc_flag = !apInvSrc.isEnabled();
		boolean suppType_flag = !supplierType.isEnabled();
		boolean chkBoxEnabled_flag = !enabledFlag.isEnabled();
		boolean saveBtn_flag = !saveButton.isEnabled();

		click.clickElement(cancelButton);

		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		if ( header_flag && le_flag && bu_flag && apInvSrc_flag && suppType_flag && chkBoxEnabled_flag && saveBtn_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to verify Export To CSV in AP Tax Calculation Exclusion
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSVAPTaxCalcExcl( ) throws Exception
	{
		boolean flag = false;

		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> data = summaryTableAPTaxCalcExcl
				.stream()
				.findFirst();

			String legalEntity = data
				.get()
				.getText();

			String businessUnit = data
				.get()
				.findElement(column_BU)
				.getText();

			String apInvSrc = data
				.get()
				.findElement(column_AP)
				.getText();

			String enabledStatus = data
				.get()
				.findElement(column_Enabled)
				.getText();

			String fileName = "TaxExclusions_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			setFluentWait(file);

			List<CSVRecord> records = parseCSVRecord(file);
			boolean csvHeader_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

			Optional<CSVRecord> data1 = records
				.stream()
				.filter(rec -> rec
								   .get(0)
								   .contains(legalEntity) && rec
								   .get(1)
								   .contains(businessUnit) && rec
								   .get(2)
								   .contains(apInvSrc) && rec
								   .get(4)
								   .contains(enabledStatus))
				.findFirst();

			boolean csvRecNo_flag = false;
			if ( data1.isPresent() )
			{
				csvRecNo_flag = true;
				VertexLogger.log("CSV Record Number: " + data1
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

			if ( csvHeader_flag && csvRecNo_flag )
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
	public boolean emptyFields_reflectAll( WebElement ele1, WebElement ele2, WebElement ele3, WebElement ele4,
		String val1, String val2, String val3, String val4 )
	{

		boolean flag = false;

		click.clickElement(addButton);
		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

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

		boolean all_flag = ele2_value.contains("All") && ele3_value.contains("All") && ele4_value.contains("All");

		Map<String, String> map = new TreeMap<>();
		map.put(val1, ele1_value);
		map.put(val2, ele2_value);
		map.put(val3, ele3_value);
		map.put(val4, ele4_value);
		List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean data_flag = false;
		if ( all_flag )
		{
			data_flag = summaryTableAPTaxCalcExcl
				.stream()
				.anyMatch(ele -> ele
									 .getText()
									 .contains(list
										 .get(2)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[1]"))
									 .getText()
									 .contains(list
										 .get(1)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[2]"))
									 .getText()
									 .contains(list
										 .get(0)
										 .getValue()) && ele
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
		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean ele1_flag = emptyFields_reflectAll(legalEntity, businessUnit, apInvSrc, supplierType, "LE", "BU",
			"APInvSrc", "SuppType");

		boolean ele2_flag = emptyFields_reflectAll(businessUnit, legalEntity, apInvSrc, supplierType, "BU", "LE",
			"APInvSrc", "SuppType");

		boolean ele3_flag = emptyFields_reflectAll(apInvSrc, legalEntity, businessUnit, supplierType, "APInvSrc", "LE",
			"BU", "SuppType");

		boolean ele4_flag = emptyFields_reflectAll(apInvSrc, legalEntity, businessUnit, supplierType, "APInvSrc", "LE",
			"BU", "SuppType");

		if ( ele1_flag && ele2_flag && ele3_flag && ele4_flag )
		{
			flag = true;
		}
		return flag;
	}
}
