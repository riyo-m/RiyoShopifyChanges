package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.PoTaxCalcExcl;
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
import java.util.*;

/**
 * this class represents all the locators and methods for PO Tax Calc Excl page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkPoTaxCalcExclPage extends TaxLinkBasePage
{
	public TaxLinkPoTaxCalcExclPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(text(),'Legal Entity')]/ancestor::div/select")
	private WebElement leDropDown;

	@FindBy(xpath = "//label[contains(text(),'Business Unit')]/ancestor::div/select")
	private WebElement buDropDown;

	@FindBy(xpath = "//label[contains(text(),'Supplier Type')]/ancestor::div/select")
	protected WebElement supplierTypeDropdown;

	@FindBy(xpath = "//button[contains(text(), 'SAVE')]")
	private WebElement saveButton;

	@FindBy(xpath = "//button[contains(text(), 'CANCEL')]")
	private WebElement cancelButton;

	@FindBy(xpath = "//div[@col-id='legalEntityName']")
	private List<WebElement> lePresentation;

	@FindBy(xpath = "//div[@col-id='businessUnitName']")
	private List<WebElement> buPresentation;

	@FindBy(xpath = "//div[@col-id='supplierName']")
	private List<WebElement> supplierTypePresentation;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'legalEntityName']")
	private List<WebElement> summaryTablePOTaxCalcExcl;

	/**
	 * Verify title of PO Tax Calc Excl Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitlePOTaxCalcExclPage( )
	{
		boolean matchFlag = false;
		wait.waitForElementDisplayed(summaryPageTitle);
		String poTaxCalcExclTitle = summaryPageTitle.getText();
		if ( poTaxCalcExclTitle.equalsIgnoreCase(PoTaxCalcExcl.PoTaxCalcExclDetails.headerPoTaxCalcExclPage) )
		{
			matchFlag = true;
		}
		return matchFlag;
	}

	/**
	 * Method to search data in summary table of AR Transaction Source page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(lePresentation));
		Optional<WebElement> dataFound = lePresentation
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Method to search data in particular column of
	 * summary tables
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresentInParticularColumn( List<WebElement> ele, String text )
	{
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Add PO Tax calc excl record
	 * and verify the details on summary table
	 * in PO tax calc excl Tab
	 * in Taxlink Application
	 *
	 * @return flagPassed
	 */
	public boolean addAndVerifyPOTaxCalcExcl( )
	{
		boolean flagAddPOTaxExclRecord = false;
		boolean flagSummaryTableVerified = false;
		boolean flagPassed = false;
		String leText = null, buText = null, supplierTypeText;

		navigateToPOTaxCalcExclPage();
		if ( verifyTitlePOTaxCalcExclPage() )
		{
			expWait.until(ExpectedConditions.visibilityOf(addButton));
			click.clickElement(addButton);
			wait.waitForElementDisplayed(addViewEditPageTitle);
			if ( addViewEditPageTitle
				.getText()
				.equals(PoTaxCalcExcl.PoTaxCalcExclDetails.headerAddPoTaxCalcExclPage) )
			{
				jsWaiter.sleep(2000);
				dropdown.selectDropdownByIndex(leDropDown, 1);
				wait.waitForElementDisplayed(buDropDown);
				dropdown.selectDropdownByIndex(buDropDown, 1);
				wait.waitForElementDisplayed(supplierTypeDropdown);
				dropdown.selectDropdownByIndex(supplierTypeDropdown, 1);

				checkbox.isCheckboxChecked(enabledFlag);

				WebElement le = dropdown.getDropdownSelectedOption(leDropDown);
					leText = le.getText();
					WebElement bu = dropdown.getDropdownSelectedOption(buDropDown);
					buText = bu.getText();
					WebElement supplierType = dropdown.getDropdownSelectedOption(supplierTypeDropdown);
					supplierTypeText = supplierType.getText();

					click.clickElement(saveButton);
					if ( verifyTitlePOTaxCalcExclPage() )
					{
						flagAddPOTaxExclRecord = true;
					}

					List<String> dataEntered = Arrays.asList(leText, buText, supplierTypeText);
					VertexLogger.log("New record added : " + dataEntered);
				}
		}

		wait.waitForElementDisplayed(exportToCSVSummaryPage);
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

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
				jsWaiter.sleep(2000);
				Optional<WebElement> data = dataPresent(leText);
				if ( data.isPresent() )
				{
					expWait.until(ExpectedConditions.visibilityOfAllElements(lePresentation));
					final String finalLeText = leText;
					boolean leFlag = lePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.contains(finalLeText));
					VertexLogger.log(String.valueOf(leFlag));
					final String finalBuText = buText;
					boolean buFlag = buPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.contains(finalBuText));
					VertexLogger.log(String.valueOf(buFlag));
					final String finalLeText1 = leText;
					boolean supplierTypeFlag = lePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.contains(finalLeText1));
					VertexLogger.log(String.valueOf(supplierTypeFlag));
					boolean enabledFlag = enabledFlagPresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.contains("Y"));
					VertexLogger.log(String.valueOf(enabledFlag));
					if ( leFlag && buFlag && supplierTypeFlag && enabledFlag )
					{
						flagSummaryTableVerified = true;
						break;
					}
				}
				else
				{
					js.executeScript("arguments[0].scrollIntoView();", nextArrowOnSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}

		if ( flagAddPOTaxExclRecord && flagSummaryTableVerified )
		{
			flagPassed = true;
		}
		return flagPassed;
	}

	/**
	 * View PO Tax calc excl record
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewPOTaxCalcExcl( )
	{
		boolean flagViewPoTaxCalcExcl = false;

		navigateToPOTaxCalcExclPage();
		wait.waitForElementDisplayed(externalFilter);
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		expWait.until(ExpectedConditions.visibilityOf(actions));
		actions.click();
		viewButton.click();
		String viewPoTaxCalcExclTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();

		if ( viewPoTaxCalcExclTitle.equalsIgnoreCase(PoTaxCalcExcl.PoTaxCalcExclDetails.headerViewPoTaxCalcExclPage) )
		{
			boolean leFlag = !leDropDown.isEnabled();
			VertexLogger.log(String.valueOf(leFlag));
			boolean buFlag = !buDropDown.isEnabled();
			VertexLogger.log(String.valueOf(buFlag));
			boolean supplierTypeFlag = !supplierTypeDropdown.isEnabled();
			VertexLogger.log(String.valueOf(supplierTypeFlag));
			boolean flagEnabledFlag = !enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(flagEnabledFlag));
			boolean saveButtonFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveButtonFlag));
			boolean cancelFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelFlag));

			if ( leFlag && buFlag && supplierTypeFlag && flagEnabledFlag && saveButtonFlag && cancelFlag )
			{
				flagViewPoTaxCalcExcl = true;
			}
		}
		return flagViewPoTaxCalcExcl;
	}

	/**
	 * Edit PO Tax calc excl record
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean editPOTaxCalcExcl( )
	{
		boolean flagFieldsEditPoTaxCalcExcl = false;
		boolean flagSummaryTableVerified = false;
		boolean flagEditPoTaxCalcExcl = false;

		navigateToPOTaxCalcExclPage();

		expWait.until(ExpectedConditions.visibilityOf(editButton));
		editButton.click();

		String editPoTaxCalcExclTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();

		if ( editPoTaxCalcExclTitle.equalsIgnoreCase(PoTaxCalcExcl.PoTaxCalcExclDetails.headerEditPoTaxCalcExclPage) )
		{
			jsWaiter.sleep(2500);
			boolean leFlag = !leDropDown.isEnabled();
			WebElement leText = dropdown.getDropdownSelectedOption(leDropDown);
			String selectedLE = leText.getText();
			VertexLogger.log(String.valueOf(leFlag));
			boolean buFlag = !buDropDown.isEnabled();
			VertexLogger.log(String.valueOf(buFlag));
			boolean supplierTypeFlag = !supplierTypeDropdown.isEnabled();
			VertexLogger.log(String.valueOf(supplierTypeFlag));
			boolean flagEnabledFlag = enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(flagEnabledFlag));
			boolean saveButtonFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveButtonFlag));
			boolean cancelFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelFlag));

			if ( leFlag && buFlag && supplierTypeFlag && flagEnabledFlag && saveButtonFlag && cancelFlag )
			{
				flagFieldsEditPoTaxCalcExcl = true;
			}

			click.clickElement(enabledFlag);
			wait.waitForElementEnabled(saveButton);
			click.clickElement(saveButton);

			wait.waitForElementDisplayed(exportToCSVSummaryPage);
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Both");

			wait.waitForElementDisplayed(totalPageCountSummaryTable);
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
					Optional<WebElement> data = dataPresent(selectedLE);
					if ( data.isPresent() )
					{
						final String finalLeText = selectedLE;
						leFlag = lePresentation
							.stream()
							.anyMatch(col -> col
								.getText()
								.contains(finalLeText));
						VertexLogger.log(String.valueOf(finalLeText));
						flagEnabledFlag = enabledFlagPresentation
											  .stream()
											  .anyMatch(col -> col
												  .getText()
												  .contains("N")) || enabledFlagPresentation
											  .stream()
											  .anyMatch(col -> col
												  .getText()
												  .contains("Y"));
						VertexLogger.log(String.valueOf(flagEnabledFlag));

						if ( leFlag && flagEnabledFlag )
						{
							flagSummaryTableVerified = true;
							break;
						}
					}
					else
					{
						js.executeScript("arguments[0].scrollIntoView();", nextArrowOnSummaryTable);
						click.clickElement(nextArrowOnSummaryTable);
					}
				}
			}
		}
		if ( flagFieldsEditPoTaxCalcExcl && flagSummaryTableVerified )
		{
			flagEditPoTaxCalcExcl = true;
		}
		return flagEditPoTaxCalcExcl;
	}

	/**
	 * Export to CSV on PO Tax Calc Excl page
	 */

	public boolean exportToCSVPoTaxCalcExcl( String instName ) throws IOException
	{
		boolean flagExportToCSVPoTaxCalcExcl = false;
		boolean flagInstanceNameMatch = false;
		boolean flagBuNameMatch = false;
		boolean flagSupplierTypeMatch = false;
		boolean flagEnabledMatch = false;

		String fileName = "POTaxExclusions_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToPOTaxCalcExclPage();
		wait.waitForElementDisplayed(exportToCSVSummaryPage, 60);
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
						flagInstanceNameMatch = true;
						break;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("Legal Entity", "Business Unit", "Supplier Type", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					// Accessing values by Header names
					String legalEntity_CSV = csvRecord.get("Legal Entity");
					String bUID_CSV = csvRecord.get("Business Unit");
					String supplierType_CSV = csvRecord.get("Supplier Type");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Legal Entity : " + legalEntity_CSV);
					VertexLogger.log("Business Unit : " + bUID_CSV);
					VertexLogger.log("Supplier Type : " + supplierType_CSV);
					VertexLogger.log("Enabled : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					Optional legalEntity = dataPresentInParticularColumn(lePresentation, legalEntity_CSV);
					if ( legalEntity.isPresent() )
					{
						Optional buName = dataPresentInParticularColumn(buPresentation, bUID_CSV);
						flagBuNameMatch = buName.isPresent();
						VertexLogger.log("" + flagBuNameMatch);
						Optional supplierType = dataPresentInParticularColumn(supplierTypePresentation,
							supplierType_CSV);
						flagSupplierTypeMatch = supplierType.isPresent();
						VertexLogger.log("" + flagSupplierTypeMatch);
						Optional enabled = dataPresentInParticularColumn(enabledFlagPresentation, enabled_CSV);
						flagEnabledMatch = enabled.isPresent();
						VertexLogger.log("" + flagEnabledMatch);
					}
					else
					{
						htmlElement.sendKeys(Keys.END);
						jsWaiter.sleep(100);
						click.clickElement(nextArrowOnSummaryTable);
					}
					if ( flagInstanceNameMatch && flagSupplierTypeMatch && flagBuNameMatch && flagEnabledMatch )
					{
						flagExportToCSVPoTaxCalcExcl = true;
					}
					else
					{
						flagExportToCSVPoTaxCalcExcl = false;
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
			flagExportToCSVPoTaxCalcExcl = true;
		}
		return flagExportToCSVPoTaxCalcExcl;
	}

	/**
	 * Method to add and check empty fields auto populates with "All" prefix
	 *
	 * @param ele1
	 * @param ele2
	 * @param ele3
	 * @param val1
	 * @param val2
	 * @param val3
	 *
	 * @return
	 */
	public boolean emptyFields_reflectAll( WebElement ele1, WebElement ele2, WebElement ele3, String val1, String val2,
		String val3 )
	{

		boolean flag = false;

		click.clickElement(addButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

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

		boolean all_flag = ele2_value.contains("All") && ele3_value.contains("All");

		Map<String, String> map = new TreeMap<>();
		map.put(val1, ele1_value);
		map.put(val2, ele2_value);
		map.put(val3, ele3_value);
		List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(summaryPageTitle);

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean data_flag = false;
		if ( all_flag )
		{
			data_flag = summaryTablePOTaxCalcExcl
				.stream()
				.anyMatch(ele -> ele
									 .getText()
									 .contains(list
										 .get(1)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[1]"))
									 .getText()
									 .contains(list
										 .get(0)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[2]"))
									 .getText()
									 .contains(list
										 .get(2)
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
		navigateToPOTaxCalcExclPage();
		wait.waitForElementDisplayed(summaryPageTitle);

		boolean ele1_flag = emptyFields_reflectAll(leDropDown, buDropDown, supplierTypeDropdown, "LE", "BU",
			"SuppType");

		boolean ele2_flag = emptyFields_reflectAll(buDropDown, leDropDown, supplierTypeDropdown, "BU", "LE",
			"SuppType");

		boolean ele3_flag = emptyFields_reflectAll(supplierTypeDropdown, leDropDown, buDropDown, "SuppType", "LE",
			"BU");

		if ( ele1_flag && ele2_flag && ele3_flag )
		{
			flag = true;
		}
		return flag;
	}
}


