package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.Supplies;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import static org.testng.Assert.fail;

/**
 * Taxlink page for handling supply inputs.
 *
 * @author msalomone
 */
public class TaxLinkSuppliesPage extends TaxLinkBasePage
{

	// AR Tax Exclusions Page locs
	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'legalEntityName']")
	private List<WebElement> summaryTableArExclusions;

	@FindBy(xpath = "//h1[contains(text(), 'AR / OM Tax Calculation Exclusions')]")
	private WebElement arTaxExclusionsPageHeaderLoc;

	@FindBy(xpath = "//button[contains(text(), 'ADD')]")
	private WebElement addArExclusionButton;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='legalEntityName']")
	private List<WebElement> legalEntityColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='businessUnitName']")
	private List<WebElement> busUnitColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='batchSourceName']")
	private List<WebElement> arTrxSrcColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='trxTypeName']")
	private List<WebElement> arTrxTypeColLoc;

	// Add AR Tax Exclusion Page locs
	@FindBy(xpath = "//h3[contains(text(), 'Add AR / OM Tax Exclusion')]")
	private WebElement addArTaxExclusionPageHeaderLoc;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntityDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement busUnitDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'AR Transaction Source')]/following-sibling::select")
	private WebElement arTrxSourceDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'AR Transaction Type')]/following-sibling::select")
	private WebElement arTrxTypeDropdownLoc;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	public String ele1_value;

	/**
	 * Taxlink Supplies page object constructor.
	 *
	 * @param driver
	 */
	public TaxLinkSuppliesPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Handles addition of a tax calculation exclusion entry
	 * via the AR Tax Calculation Exclusions tab.
	 *
	 * @return selectedDropdownList - list of element selected in respective dropdowns
	 *
	 * @Param legalEntity
	 * @Param busUnit
	 * @Param arTrxSource
	 * @Param arTrxType
	 * @Param enabled
	 */

	public List<String> addArTaxCalcExclusion( )
	{
		boolean enabled = false;
		wait.waitForElementDisplayed(addArExclusionButton);
		click.javascriptClick(addArExclusionButton);

		String addPageHeader = wait
			.waitForElementDisplayed(addArTaxExclusionPageHeaderLoc)
			.getText();

		if ( !addPageHeader.equals(Supplies.SUPPLIES.pageHeaderText_ADD_AR_EXCLUSION_HEADER) )
		{
			fail("Test failed due to inability to verify the driver reached the AR Tax Calculation Exclusions " +
				 "add exclusion page.");
		}
		List<String> selectedDropdownList = new ArrayList<String>();

		String legalEntity = drpDown_Select_GetText(legalEntityDropdownLoc, 1);

		selectedDropdownList.add(legalEntity);

		String busUnit = drpDown_Select_GetText(busUnitDropdownLoc, 1);
		selectedDropdownList.add(busUnit);

		String arTrxSource = drpDown_Select_GetText(arTrxSourceDropdownLoc, 1);
		selectedDropdownList.add(arTrxSource);

		String arTrxType = drpDown_Select_GetText(arTrxTypeDropdownLoc, 1);

		selectedDropdownList.add(arTrxType);

		inputArExclusionFieldValues(legalEntity, busUnit, arTrxSource, arTrxType);

		if ( enabledFlag.isEnabled() )
		{
			enabled = true;
		}
		String enabledFLag = getEnabledValue(enabled);

		selectedDropdownList.add(enabledFLag);

		clickSaveButton();

		return selectedDropdownList;
	}

	/**
	 * Handles heavy lifting for test scenario. Will add an AR
	 * exclusion, and verify its details are displayed correctly
	 *
	 * @return verifiedFlag
	 */
	public boolean addAndVerifyArTaxExclusion( )
	{
		boolean verifiedFlag = false;

		navigateToArTaxExclusionsPage();

		String summaryPageHeader = wait
			.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc)
			.getText();

		if ( !summaryPageHeader.equals(Supplies.SUPPLIES.pageHeaderText_AR_TAX_CALC_EXCLUSIONS) )
		{
			fail("Test failed due to inability to verify the driver reached the AR Tax Calculation Exclusions page.");
		}

		List<String> dropdownListSavedOption = addArTaxCalcExclusion();

		String legalEntity = dropdownListSavedOption.get(0);
		String busUnit = dropdownListSavedOption.get(1);
		String arTrxSource = dropdownListSavedOption.get(2);
		String arTrxType = dropdownListSavedOption.get(3);
		String enabledVal = dropdownListSavedOption.get(4);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(legalEntity);

			if ( data.isPresent() )
			{
				final String finalBusUnit = busUnit;
				boolean flagBuName = busUnitColLoc
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(finalBusUnit));
				VertexLogger.log(String.valueOf(flagBuName));

				final String finalArTrxType = arTrxType;
				boolean flagArTrxType = arTrxTypeColLoc
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(finalArTrxType));
				VertexLogger.log(String.valueOf(flagArTrxType));

				final String finalArTrxSource = arTrxSource;
				boolean flagArTrxSource = arTrxSrcColLoc
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(finalArTrxSource));
				VertexLogger.log(String.valueOf(flagArTrxSource));

				boolean flagEnabledFlag = enabledFlagPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(enabledVal));
				VertexLogger.log(String.valueOf(flagEnabledFlag));

				if ( flagBuName && flagArTrxType && flagArTrxSource && flagEnabledFlag )
				{
					verifiedFlag = true;
					break;
				}
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return verifiedFlag;
	}

	/**
	 * Method to search data in summary table
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = legalEntityColLoc
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();
		return dataFound;
	}

	/**
	 * Locates a save button loc on the page, and clicks the element if found.
	 */
	private void clickSaveButton( )
	{
		WebElement save = wait.waitForElementDisplayed(saveButton);
		click.javascriptClick(save);
	}

	/**
	 * Clicks the ... option located on the exclusion table, selects edit,
	 * and verifies that all fields are disabled except the enabled check box.
	 *
	 * @return verified
	 */
	public boolean editAndVerifyArExclusion( )
	{
		boolean verified;

		navigateToArTaxExclusionsPage();

		wait.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc);

		click.javascriptClick(editButton);

		boolean flag1 = !element.isElementEnabled(legalEntityDropdownLoc);

		boolean flag2 = !element.isElementEnabled(busUnitDropdownLoc);

		boolean flag3 = !element.isElementEnabled(arTrxSourceDropdownLoc);

		boolean flag4 = !element.isElementEnabled(arTrxTypeDropdownLoc);

		boolean flag5 = element.isElementEnabled(enabledFlag);

		click.clickElement(cancelButton);

		if ( flag1 && flag2 && flag3 && flag4 && flag5 )
		{
			verified = true;
		}
		else
		{
			verified = false;
		}
		return verified;
	}

	/**
	 * Returns "Y" or "N" to represent the enabled switch as
	 * a String value.
	 *
	 * @param isEnabled whether enabled box is checked or not.
	 *
	 * @return enabledValue
	 */
	private String getEnabledValue( boolean isEnabled )
	{

		String enabledValue;

		if ( !isEnabled )
		{
			enabledValue = "N";
		}
		else
		{
			enabledValue = "Y";
		}
		return enabledValue;
	}

	/**
	 * Enters fields located on the Add AR Tax Exclusion page.
	 *
	 * @param legalEntity
	 * @param busUnit
	 * @param arTrxSource
	 * @param arTrxType
	 */
	private void inputArExclusionFieldValues( String legalEntity, String busUnit, String arTrxSource, String arTrxType )
	{
		if ( null != legalEntity )
		{
			wait.waitForElementDisplayed(legalEntityDropdownLoc);
			dropdown.selectDropdownByDisplayName(legalEntityDropdownLoc, legalEntity);
		}

		if ( null != busUnit )
		{
			wait.waitForElementDisplayed(busUnitDropdownLoc);
			dropdown.selectDropdownByDisplayName(busUnitDropdownLoc, busUnit);
		}

		if ( null != arTrxSource )
		{
			wait.waitForElementDisplayed(arTrxSourceDropdownLoc);
			dropdown.selectDropdownByDisplayName(arTrxSourceDropdownLoc, arTrxSource);
		}

		if ( null != arTrxType )
		{
			wait.waitForElementDisplayed(arTrxTypeDropdownLoc);
			dropdown.selectDropdownByDisplayName(arTrxTypeDropdownLoc, arTrxType);
		}
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
		jsWaiter.sleep(5000);
		dropdown.selectDropdownByIndex(ele, index);
		String text = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getText();

		return text;
	}

	/**
	 * Clicks the ... option located on the exclusion table, selects view,
	 * and verifies that all fields are disabled except the enabled check box.
	 *
	 * @return verified
	 */
	public boolean viewAndVerifyArExclusion( )
	{
		boolean verified;

		navigateToArTaxExclusionsPage();

		wait.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc);

		click.javascriptClick(actions);

		wait.waitForElementDisplayed(viewButton);
		click.javascriptClick(viewButton);

		boolean flag1 = !element.isElementEnabled(legalEntityDropdownLoc);

		boolean flag2 = !element.isElementEnabled(busUnitDropdownLoc);

		boolean flag3 = !element.isElementEnabled(arTrxSourceDropdownLoc);

		boolean flag4 = !element.isElementEnabled(arTrxTypeDropdownLoc);

		boolean flag5 = !element.isElementEnabled(enabledFlag);

		click.clickElement(cancelButton);

		if ( flag1 && flag2 && flag3 && flag4 )
		{
			verified = true;
		}
		else
		{
			verified = false;
		}
		return verified;
	}

	/**
	 * Checks if the Save button is enabled after selecting at least
	 * one value from the page's fields.
	 *
	 * @return saveEnabled
	 */
	private boolean verifySaveButtonEnabled( )
	{

		boolean saveEnabled = false;

		if ( !saveEnabled )
		{
			VertexLogger.log("The save button on the AR Tax Calc Exclusions page is not enabled. Test failed.",
				VertexLogLevel.ERROR);
		}

		return saveEnabled;
	}

	/**
	 * Clicks the export button the AR Tax Calculation Exclusions page,
	 * copies the file to a new directory, and verifies expected data
	 * exists within the file.
	 *
	 * @return verified
	 */
	public boolean exportAndVerifyArExclusions( String instanceName )
	{

		boolean verified = false;
		boolean check1;
		boolean check2 = false;

		int firstRecord = 1;
		int secondRecord = 2;

		navigateToArTaxExclusionsPage();

		wait.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc);
		if ( !checkNoRecordsPresent() )
		{
			String idText = summaryTableArExclusions
				.stream()
				.findFirst()
				.get()
				.getText();

			String fileName = "AR_OM_Tax_Calculation_Exclusions_Extract.csv";

			wait.waitForElementDisplayed(exportToCSVSummaryPage);

			click.javascriptClick(exportToCSVSummaryPage);
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			FluentWait<WebDriver> wait = new FluentWait<>(driver);
			wait
				.pollingEvery(Duration.ofSeconds(1))
				.withTimeout(Duration.ofSeconds(15))
				.until(x -> file.exists());

			try
			{
				Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
				VertexLogger.log("File downloaded", VertexLogLevel.INFO);

				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(','));

				List<CSVRecord> records = csvParser.getRecords();

				String encodedInstance = Base64
					.getEncoder()
					.encodeToString(instanceName.getBytes());
				String encodedRecord = Base64
					.getEncoder()
					.encodeToString(records
						.get(0)
						.get(0)
						.getBytes());
				String groomedRecord = encodedRecord.substring(4);

				check1 = encodedInstance.equals(groomedRecord);

				if ( records
					.get(secondRecord)
					.get(0)
					.contains(idText) )
				{
					check2 = true;
					VertexLogger.log("CSV Record Number: " + firstRecord);
				}

				if ( check1 && check2 )
				{
					verified = true;
				}
			}
			catch ( IOException ioe )
			{
				ioe.printStackTrace();
				fail("Test failed while handling ar exclusions file.");
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
			verified = true;
		}
		return verified;
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
		wait.waitForElementDisplayed(addArTaxExclusionPageHeaderLoc);

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

		clickSaveButton();
		wait.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc);

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean data_flag = false;
		if ( all_flag )
		{
			data_flag = summaryTableArExclusions
				.stream()
				.anyMatch(ele -> ele
									 .getText()
									 .contains(list
										 .get(3)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[1]"))
									 .getText()
									 .contains(list
										 .get(2)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[2]"))
									 .getText()
									 .contains(list
										 .get(0)
										 .getValue()) && ele
									 .findElement(By.xpath(".//following::div[3]"))
									 .getText()
									 .contains(list
										 .get(1)
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
		navigateToArTaxExclusionsPage();
		wait.waitForElementDisplayed(arTaxExclusionsPageHeaderLoc);

		boolean ele1_flag = emptyFields_reflectAll(legalEntityDropdownLoc, busUnitDropdownLoc, arTrxSourceDropdownLoc,
			arTrxTypeDropdownLoc, "LE", "BU", "ARTranSrc", "ARTransType");

		boolean ele2_flag = emptyFields_reflectAll(busUnitDropdownLoc, legalEntityDropdownLoc, arTrxSourceDropdownLoc,
			arTrxTypeDropdownLoc, "BU", "LE", "ARTranSrc", "ARTransType");

		boolean ele3_flag = emptyFields_reflectAll(arTrxSourceDropdownLoc, legalEntityDropdownLoc, busUnitDropdownLoc,
			arTrxTypeDropdownLoc, "ARTranSrc", "LE", "BU", "ARTransType");

		boolean ele4_flag = emptyFields_reflectAll(arTrxTypeDropdownLoc, legalEntityDropdownLoc, busUnitDropdownLoc,
			arTrxSourceDropdownLoc, "ARTransType", "LE", "BU", "ARTranSrc");

		if ( ele1_flag && ele2_flag && ele3_flag && ele4_flag )
		{
			flag = true;
		}
		return flag;
	}
}
