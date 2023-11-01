package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.LegalEntity;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This page class contains all the methods for verifying Supplier Type tab
 *
 * @author Shilpi.Verma
 */

public class TaxLinkSupplierTypePage extends TaxLinkBasePage
{
	/**
	 * Constructor of TaxLinkBasePage class
	 * inheriting properties of VertexPage
	 *
	 * @param driver
	 */
	public TaxLinkSupplierTypePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCode']")
	private List<WebElement> summaryTableSuppType;

	@FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])[last()-1]")
	private WebElement totalPageCount;

	@FindBy(xpath = "//label[contains(text(), 'SEQ ID')]/following-sibling::input")
	private WebElement seqIdField;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type Name')]/following-sibling::input")
	private WebElement suppTypeNameField;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type Description')]/following-sibling::input")
	private WebElement suppTypeDescField;

	By suppTypeNameCol = By.xpath(".//following-sibling::div");
	By suppTypeDescCol = By.xpath(".//following-sibling::div[2]");
	By enabledCol = By.xpath(".//following-sibling::div[3]");

	/**
	 * Method to add new record for Supplier Type
	 *
	 * @return
	 */
	public boolean addSupplierType( )
	{
		boolean finalFlag = false;

		navigateToSupplierType();
		wait.waitForElementDisplayed(summaryPageTitle);

		boolean headerSummary_flag = summaryPageTitle
			.getText()
			.equals("Supplier Type");

		click.clickElement(addButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		boolean headerAdd_flag = addViewEditPageTitle
			.getText()
			.equals("Add Supplier Type");

		String seqId = utils.randomNumber("4");
		text.enterText(seqIdField, seqId);

		String suppTypeName = utils.randomAlphaNumericText();
		text.enterText(suppTypeNameField, suppTypeName);

		String suppTypeDesc = utils.randomText();
		text.enterText(suppTypeDescField, suppTypeDesc);

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(summaryPageTitle);

		List<String> dataEntered = Arrays.asList(seqId, suppTypeName, suppTypeDesc);
		VertexLogger.log("Data entered is: " + dataEntered);

		checkPageNavigation();
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCount.getText());
		boolean newRecPresent_flag = false;
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = summaryTableSuppType
				.stream()
				.filter(col -> col
					.getText()
					.equals(seqId))
				.findFirst();

			if ( data.isPresent() )
			{
				newRecPresent_flag = data
										 .get()
										 .getText()
										 .equals(seqId) && data
										 .get()
										 .findElement(suppTypeNameCol)
										 .getText()
										 .equals(suppTypeName) && data
										 .get()
										 .findElement(suppTypeDescCol)
										 .getText()
										 .equals(suppTypeDesc) && data
										 .get()
										 .findElement(enabledCol)
										 .getText()
										 .equals("Y");

				VertexLogger.log("New record is present in the summary table");
				break;
			}
			else
			{
				try
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
				catch ( Exception e )
				{
					VertexLogger.log("Data is not present in the Summary Table");
				}
			}
		}

		if ( headerSummary_flag && headerAdd_flag && newRecPresent_flag )
		{
			finalFlag = true;
		}

		return finalFlag;
	}

	/**
	 * Method to edit existing record of Supplier Type tab
	 *
	 * @return
	 */
	public boolean editSupplierType( )
	{
		boolean finalFlag = false;

		navigateToSupplierType();
		wait.waitForElementDisplayed(summaryPageTitle);

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		click.clickElement(editButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		boolean header_flag = addViewEditPageTitle
			.getText()
			.equals("Edit Supplier Type");

		String seqId = seqIdField.getAttribute("value");
		String suppTypename = suppTypeNameField.getAttribute("value");
		String suppTypeDesc = suppTypeDescField.getAttribute("value");
		click.clickElement(enabledFlag);

		boolean enabledCheckbox_flag = enabledFlag.isSelected();

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(summaryPageTitle);

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		final AtomicBoolean enabledCol_flag = new AtomicBoolean(false);
		summaryTableSuppType.forEach(col ->
		{
			if ( !enabledCheckbox_flag )
			{
				if ( col
						 .getText()
						 .equals(seqId) && col
						 .findElement(suppTypeNameCol)
						 .getText()
						 .equals(suppTypename) && col
						 .findElement(suppTypeDescCol)
						 .getText()
						 .equals(suppTypeDesc) )
				{
					enabledCol_flag.set(col
						.findElement(enabledCol)
						.getText()
						.equals("N"));
				}
			}
			else
			{
				if ( col
						 .getText()
						 .equals(seqId) && col
						 .findElement(suppTypeNameCol)
						 .getText()
						 .equals(suppTypename) && col
						 .findElement(suppTypeDescCol)
						 .getText()
						 .equals(suppTypeDesc) )
				{
					enabledCol_flag.set(col
						.findElement(enabledCol)
						.getText()
						.equals("Y"));
				}
			}
		});

		if ( header_flag && enabledCol_flag.get() )
		{
			finalFlag = true;
		}

		return finalFlag;
	}

	/**
	 * This method is to view existing record of Supplier Type
	 *
	 * @return
	 */
	public boolean viewSupplierType( )
	{
		boolean finalFlag = false;

		navigateToSupplierType();
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(actions);
		wait.waitForElementDisplayed(viewButton);
		click.clickElement(viewButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		boolean headerViewPage_flag = addViewEditPageTitle
			.getText()
			.equals("View Supplier Type");

		boolean seqID_flag = !seqIdField.isEnabled();
		boolean suppTypeNameField_flag = !suppTypeNameField.isEnabled();
		boolean suppTypeDescField_flag = !suppTypeDescField.isEnabled();
		boolean enabled_flag = !enabledFlag.isEnabled();
		boolean saveButton_flag = !saveButton.isEnabled();

		click.clickElement(cancelButton);
		wait.waitForElementDisplayed(summaryPageTitle);

		if ( headerViewPage_flag && seqID_flag && suppTypeNameField_flag && suppTypeDescField_flag && enabled_flag &&
			 saveButton_flag )
		{
			finalFlag = true;
		}

		return finalFlag;
	}

	/**
	 * This method is to check Supplier Type dropdown in Tax Action Ranges tab
	 *
	 * @param ele
	 * @param option
	 * @param suppType
	 *
	 * @return
	 */
	public boolean supplierDropdown_TaxActionRanges( WebElement ele, WebElement option, String suppType )
	{
		wait.waitForElementDisplayed(ele);
		click.clickElement(ele);
		click.clickElement(addButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		boolean flag = dropdown
			.getDropdownOptions(option)
			.stream()
			.anyMatch(name -> name
				.getText()
				.equals(suppType));

		click.clickElement(closeButton);
		wait.waitForElementDisplayed(summaryPageTitle);

		return flag;
	}

	/**
	 * This method is to check whether Supplier dropdown in Add page of Tax Action Ranges
	 * and PO Tax Calculation Exclusions contains Supplier Name present in summary table of Supplier Type
	 *
	 * @return
	 */
	public boolean supplierDropdown( )
	{
		boolean finalFlag = false;

		navigateToSupplierType();
		wait.waitForElementDisplayed(summaryPageTitle);

		String suppTypeName = summaryTableSuppType
			.stream()
			.findFirst()
			.get()
			.findElement(suppTypeNameCol)
			.getText();

		TaxLinkAPTaxActionRangesPage taxActionRangesPage = new TaxLinkAPTaxActionRangesPage(driver);
		taxActionRangesPage.navigateToAPTaxActRanges();
		wait.waitForElementDisplayed(summaryPageTitle);

		boolean suppTypeDropdown_overcharged_flag = supplierDropdown_TaxActionRanges(
			taxActionRangesPage.overchargedSection, taxActionRangesPage.supplierType, suppTypeName);

		boolean suppTypeDropdown_undercharged_flag = supplierDropdown_TaxActionRanges(
			taxActionRangesPage.underchargedSection, taxActionRangesPage.supplierType, suppTypeName);

		boolean suppTypeDropdown_zerocharged_flag = supplierDropdown_TaxActionRanges(
			taxActionRangesPage.zerochargedSection, taxActionRangesPage.supplierType, suppTypeName);

		TaxLinkPoTaxCalcExclPage poTaxCalcExclPage = new TaxLinkPoTaxCalcExclPage(driver);
		click.clickElement(procurementTab);
		wait.waitForElementDisplayed(poTaxCalcExclTab);
		click.clickElement(poTaxCalcExclTab);
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(addButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(poTaxCalcExclPage.supplierTypeDropdown,
			By.tagName("option")));

		boolean suppTypeDropdown_poTaxCalcExcl_flag = dropdown
			.getDropdownOptions(poTaxCalcExclPage.supplierTypeDropdown)
			.stream()
			.anyMatch(name -> name
				.getText()
				.equals(suppTypeName));

		if ( suppTypeDropdown_overcharged_flag && suppTypeDropdown_undercharged_flag &&
			 suppTypeDropdown_zerocharged_flag && suppTypeDropdown_poTaxCalcExcl_flag )
		{
			VertexLogger.log(suppTypeName + " is present in all the dropdowns");
			finalFlag = true;
		}
		return finalFlag;
	}

	/**
	 * This method is to check if downloaded CSV file contains matching records with summary table
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public boolean exportToCSV( ) throws Exception
	{
		boolean finalFlag = false;

		navigateToSupplierType();
		wait.waitForElementDisplayed(summaryPageTitle);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> ele = summaryTableSuppType
				.stream()
				.findFirst();

			String seqId = ele
				.get()
				.getText();
			String suppTypeName = ele
				.get()
				.findElement(suppTypeNameCol)
				.getText();
			String suppTypeDesc = ele
				.get()
				.findElement(suppTypeDescCol)
				.getText();
			String enabledStatus = ele
				.get()
				.findElement(enabledCol)
				.getText();

			String fileName = "_Supplier_Type_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			setFluentWait(file);

			List<CSVRecord> records = parseCSVRecord(file);
			boolean header_flag = checkHeader(records, LegalEntity.FIELDS.instanceName);

			Optional<CSVRecord> data = records
				.stream()
				.filter(rec -> rec
								   .get(0)
								   .contains(seqId) && rec
								   .get(1)
								   .contains(suppTypeName) && rec
								   .get(2)
								   .contains(suppTypeDesc) && rec
								   .get(3)
								   .contains(enabledStatus))
				.findFirst();

			boolean data_flag = false;
			if ( data.isPresent() )
			{
				data_flag = true;
				VertexLogger.log("CSV Record Number: " + data
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
				finalFlag = true;
			}
		}
		else
		{
			finalFlag = true;
		}

		return finalFlag;
	}
}
