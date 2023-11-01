package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.Lookups;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Lookups page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkLookupsPage extends TaxLinkBasePage
{
	public TaxLinkLookupsPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@col-id='lookupType']")
	private List<WebElement> lookupTypePresentation;

	@FindBy(xpath = "//*[@col-id='lookupTypeDesc']")
	private List<WebElement> lookupTypeDescPresentation;

	@FindBy(xpath = "//*[@col-id='accessType']")
	private List<WebElement> lookupAccessTypePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='lookupCode']")
	private WebElement lookupCodeVTX_CALC_EXCL_TAXESPresentation;

	@FindBy(xpath = "//div[@class='ag-center-cols-container']/div/div[@col-id='enabledFlag']")
	private WebElement lookupEnabledFlagVTX_CALC_EXCL_TAXESPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='lookupCode']")
	private List<WebElement> lookupCodeVTX_IMPOSITION_TYPEPresentation;

	@FindBy(xpath = "//div[@class='ag-center-cols-container']/div/div[@col-id='enabledFlag']")
	private List<WebElement> lookupEnabledFlagVTX_IMPOSITION_TYPEPresentation;

	@FindBy(xpath = "(//input[@id='Optionsname'])[3]")
	private WebElement lookupCodeViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "(//input[@id='Optionsname'])[2]")
	private WebElement lookupDescViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "(//input[@id='Optionsname'])[1]")
	private WebElement lookupMeaningViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "(//input[@id='Optionsname'])[last()]")
	private WebElement lookupTagValueViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container']/input)[1]")
	private WebElement lookupStartDateViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container']/input)[last()]")
	private WebElement lookupEndDateViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement lookupEnabledFlagViewPage_VTX_CALC_EXCL_TAXES;

	@FindBy(xpath = "//button[@class='btn btn-sm btn--tertiary']")
	private WebElement lookupCancel_VTX_CALC_EXCL_TAXES_Button;

	private By viewButtonLookupType = By.xpath(
		".//following-sibling::div[3]/div/div/div/div/div/button[contains(text(), 'View')]");
	private Actions actionPageDown = new Actions(driver);

	/**
	 * Verify title of Lookups Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleLookupsPage( )
	{
		boolean flag = false;
		String lookupsTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = lookupsTitle.equalsIgnoreCase(Lookups.LOOKUP_DETAILS.headerLookupsPage);

		if ( verifyFlag )
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
	 * Verify Number of records in the lookup table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean verifyNumberOfRecords( )
	{
		boolean flag = false;
		long sumOfSystemAccessType = 0;
		navigateToLookups();
		if ( verifyTitleLookupsPage() )
		{
			int count = Integer.valueOf(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				long systemAccessType = lookupAccessTypePresentation
					.stream()
					.filter(col -> col
						.getText()
						.contains("SYSTEM"))
					.count();
				VertexLogger.log("" + systemAccessType);
				sumOfSystemAccessType = sumOfSystemAccessType + systemAccessType;
				String systemAccessTypeString = String.valueOf(sumOfSystemAccessType);

				if ( systemAccessTypeString.equals(Lookups.LOOKUP_DETAILS.totalNumberOfLookups) )
				{
					flag = true;
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
		}
		return flag;
	}

	/**
	 * View Lookup - VTX_CALC_EXCL_TAXES in Lookups table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewLookup_VTX_CALC_EXCL_TAXES( )
	{
		boolean flag = false;
		navigateToLookups();
		jsWaiter.sleep(5000);

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> flagLookupType = dataPresentInParticularColumn(lookupTypePresentation,
				"VTX_CALC_EXCL_TAXES");

			wait.waitForElementDisplayed(viewButtonLookupType);

			if ( flagLookupType.isPresent() )
			{
				flagLookupType
					.get()
					.findElement(viewButtonLookupType)
					.click();
				if ( verifyLookUpType_VTX_CALC_EXCL_TAXES() )
				{
					VertexLogger.log("Verified LookUpType_VTX_CALC_EXCL_TAXES");
					flag = true;
					break;
				}
				else
				{
					flag = false;
				}
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return flag;
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
	 * Method to verify view page of LookUp Type VTX_CALC_EXCL_TAXES
	 *
	 * @return boolean
	 */
	public boolean verifyLookUpType_VTX_CALC_EXCL_TAXES( )
	{
		boolean flag = false;
		if ( verifyTitleLookUpType_VTX_CALC_EXCL_TAXES() )
		{
			boolean flag1 = lookupCancel_VTX_CALC_EXCL_TAXES_Button.isEnabled();
			boolean flag2 = exportToCSVSummaryPage.isEnabled();
			if ( flag1 && flag2 && dataPresentLookUp("CONVERSION TAX") )
			{
				VertexLogger.log("Verified summary table for LookUpType_VTX_CALC_EXCL_TAXES");
				wait.waitForElementDisplayed(viewButton);
				viewButton.click();
				if ( verifyViewPage_VTX_CALC_EXCL_TAXES() )
				{
					VertexLogger.log("Verified View functionality for LookUpType_VTX_CALC_EXCL_TAXES");
					flag = true;
				}
				else
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * Method to title of LookUp Type VTX_CALC_EXCL_TAXES page
	 *
	 * @return boolean
	 */
	public boolean verifyTitleLookUpType_VTX_CALC_EXCL_TAXES( )
	{
		boolean flag = false;
		String lookupTypeVTX_CALC_EXCL_TAXES_Title = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( lookupTypeVTX_CALC_EXCL_TAXES_Title.equalsIgnoreCase(
			Lookups.LOOKUP_DETAILS.headerLookupType_VTX_CALC_EXCL_TAXES) )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to search data in summary table of Rules Mapping page
	 *
	 * @return boolean
	 */
	public boolean dataPresentLookUp( String text )
	{
		boolean dataFound = lookupCodeVTX_CALC_EXCL_TAXESPresentation
								.getText()
								.equals(text) && lookupEnabledFlagVTX_CALC_EXCL_TAXESPresentation
								.getText()
								.equals("Y");
		VertexLogger.log("Look Up code : CONVERSION TAX and Enabled Flag : Y");
		return dataFound;
	}

	/**
	 * Method to title of view page of LookUp Type VTX_CALC_EXCL_TAXES
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewPageLookUpType_VTX_CALC_EXCL_TAXES( )
	{
		boolean flag = false;
		String lookupTypeViewPageVTX_CALC_EXCL_TAXES_Title = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		if ( lookupTypeViewPageVTX_CALC_EXCL_TAXES_Title.equalsIgnoreCase(
			Lookups.LOOKUP_DETAILS.headerLookupType_ViewLookup_VTX_CALC_EXCL_TAXES) )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to verify view page of LookUp Type VTX_CALC_EXCL_TAXES
	 *
	 * @return boolean
	 */
	public boolean verifyViewPage_VTX_CALC_EXCL_TAXES( )
	{
		boolean flag = false;
		if ( verifyTitleViewPageLookUpType_VTX_CALC_EXCL_TAXES() )
		{
			boolean lookupCodeFlag = !lookupCodeViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupCodeFlag));
			boolean lookupDescFlag = !lookupDescViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupDescFlag));
			boolean lookupMeaningFlag = !lookupMeaningViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupMeaningFlag));
			boolean lookupStartDateFlag = !lookupStartDateViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupStartDateFlag));
			boolean lookupEndDateFlag = !lookupEndDateViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupEndDateFlag));
			boolean lookupEnabledFlag = !lookupEnabledFlagViewPage_VTX_CALC_EXCL_TAXES.isEnabled();
			VertexLogger.log(String.valueOf(lookupEnabledFlag));
			boolean lookupSaveButtonFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(lookupSaveButtonFlag));
			boolean lookupCancelButtonFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(lookupCancelButtonFlag));

			if ( lookupCodeFlag && lookupDescFlag && lookupMeaningFlag && lookupStartDateFlag && lookupEndDateFlag &&
				 lookupEnabledFlag && lookupSaveButtonFlag && lookupCancelButtonFlag )
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * View Lookup - VTX_IMPOSITION_TYPE in Lookups table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewLookup_VTX_IMPOSITION_TYPE( )
	{
		boolean flag = false;
		navigateToLookups();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> flagLookupType = dataPresentInParticularColumn(lookupTypePresentation,
				"VTX_IMPOSITION_TYPE");

			wait.waitForElementDisplayed(viewButtonLookupType);

			if ( flagLookupType.isPresent() )
			{
				flagLookupType
					.get()
					.findElement(viewButtonLookupType)
					.click();
				if ( verifyLookUpType_VTX_IMPOSITION_TYPE() )
				{
					VertexLogger.log("Verified LookUpType_VTX_IMPOSITION_TYPE");
					flag = true;
					break;
				}
				if(flag = true)
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return flag;
	}

	/**
	 * Method to verify view page of LookUp Type VTX_IMPOSITION_TYPE
	 *
	 * @return boolean
	 */
	public boolean verifyLookUpType_VTX_IMPOSITION_TYPE( )
	{
		boolean flag = false;
		if ( verifyTitleLookUpType_VTX_IMPOSITION_TYPE() )
		{
			if ( totalPageCountSummaryTable
				.getText()
				.equals(Lookups.LOOKUP_DETAILS.totalNumberOfLookupsForVTX_IMPOSITION_TYPE) )
			{
				VertexLogger.log("Verified total number of records for LookUpType VTX_IMPOSITION_TYPE");
				flag = true;
			}
			else
			{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Method to title of view page of LookUp Type VTX_IMPOSITION_TYPE
	 *
	 * @return boolean
	 */
	public boolean verifyTitleLookUpType_VTX_IMPOSITION_TYPE( )
	{
		boolean flag = false;
		String lookupTypeViewPageVTX_IMPOSITION_TYPE_Title = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( lookupTypeViewPageVTX_IMPOSITION_TYPE_Title.equalsIgnoreCase(
			Lookups.LOOKUP_DETAILS.headerLookupType_VTX_IMPOSITION_TYPE) )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Verify Export to CSV functionality on Look Up Type - VTX_CALC_EXCL_TAXES
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSV_VTX_CALC_EXCL_TAXES( String instName ) throws IOException
	{
		boolean flag = false;
		boolean flag1 = false;

		String fileName = "LookupOptions_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToLookups();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		Optional<WebElement> flagLookupType = dataPresentInParticularColumn(lookupTypePresentation,
			"VTX_CALC_EXCL_TAXES");
		expWait.until(ExpectedConditions.visibilityOfAllElements(lookupTypePresentation));
		if ( flagLookupType.isPresent() )
		{
			flagLookupType
				.get()
				.findElement(viewButtonLookupType)
				.click();
			verifyTitleLookUpType_VTX_CALC_EXCL_TAXES();
			exportToCSVSummaryPage.click();

			FluentWait<WebDriver> wait = new FluentWait<>(driver);
			wait
				.pollingEvery(Duration.ofSeconds(1))
				.withTimeout(Duration.ofSeconds(15))
				.until(x -> file.exists());

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String name = csvRecord.get(0);
					if ( name.contains(instName) )
					{
						VertexLogger.log("Fusion Instance Name : " + name);
						flag = true;
						break;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withQuote(null)
					.withHeader("Lookup Type", "Lookup Code", "Lookup Type Desc", "Lookup Meaning", "Tag Value",
						"Access Type", "Start Date", "End Date", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String lookupType_CSV = csvRecord.get("Lookup Type");
					String lookupCode_CSV = csvRecord.get("Lookup Code");
					String lookupTypeDesc_CSV = csvRecord.get("Lookup Type Desc");
					String lookupMeaning_CSV = csvRecord.get("Lookup Meaning");
					String tagValue_CSV = csvRecord.get("Tag Value");
					String accessType_CSV = csvRecord.get("Access Type");
					String startDate_CSV = csvRecord.get("Start Date");
					String endDate_CSV = csvRecord.get("End Date");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Lookup Type : " + lookupType_CSV);
					VertexLogger.log("Lookup Code : " + lookupCode_CSV);
					VertexLogger.log("Lookup Type Desc : " + lookupTypeDesc_CSV);
					VertexLogger.log("Lookup Meaning : " + lookupMeaning_CSV);
					VertexLogger.log("Tag Value : " + tagValue_CSV);
					VertexLogger.log("Access Type : " + accessType_CSV);
					VertexLogger.log("Start Date : " + startDate_CSV);
					VertexLogger.log("End Date : " + endDate_CSV);
					VertexLogger.log("Enabled Flag : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( lookupCode_CSV.contains(lookupCodeVTX_CALC_EXCL_TAXESPresentation.getText()) )
					{
						flag1 = enabled_CSV.contains(lookupEnabledFlagVTX_CALC_EXCL_TAXESPresentation.getText());
						VertexLogger.log("" + flag1);
					}
				}
				if ( flag1 )
				{
					flag = true;
				}
				else
				{
					flag = false;
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
		return flag;
	}

	/**
	 * Verify Export to CSV functionality on Look Up Type - VTX_IMPOSITION_TYPE
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSV_VTX_IMPOSITION_TYPE( String instName ) throws IOException
	{
		boolean flag = false;
		boolean flag1 = false;

		String fileName = "LookupOptions_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToLookups();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		Optional<WebElement> flagLookupType = dataPresentInParticularColumn(lookupTypePresentation,
			"VTX_IMPOSITION_TYPE");
		expWait.until(ExpectedConditions.visibilityOfAllElements(lookupTypePresentation));
		if ( flagLookupType.isPresent() )
		{
			flagLookupType
				.get()
				.findElement(viewButtonLookupType)
				.click();
			verifyTitleLookUpType_VTX_IMPOSITION_TYPE();
			exportToCSVSummaryPage.click();

			FluentWait<WebDriver> wait = new FluentWait<>(driver);
			wait
				.pollingEvery(Duration.ofSeconds(1))
				.withTimeout(Duration.ofSeconds(15))
				.until(x -> file.exists());

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String name = csvRecord.get(0);
					if ( name.contains(instName) )
					{
						VertexLogger.log("Fusion Instance Name : " + name);
						flag = true;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("Lookup Type", "Lookup Code", "Lookup Type Desc", "Lookup Meaning", "Tag Value",
						"Access Type", "Start Date", "End Date", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String lookupType_CSV = csvRecord.get("Lookup Type");
					String lookupCode_CSV = csvRecord.get("Lookup Code");
					String lookupTypeDesc_CSV = csvRecord.get("Lookup Type Desc");
					String lookupMeaning_CSV = csvRecord.get("Lookup Meaning");
					String tagValue_CSV = csvRecord.get("Tag Value");
					String accessType_CSV = csvRecord.get("Access Type");
					String startDate_CSV = csvRecord.get("Start Date");
					String endDate_CSV = csvRecord.get("End Date");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Lookup Type : " + lookupType_CSV);
					VertexLogger.log("Lookup Code : " + lookupCode_CSV);
					VertexLogger.log("Lookup Type Desc : " + lookupTypeDesc_CSV);
					VertexLogger.log("Lookup Meaning : " + lookupMeaning_CSV);
					VertexLogger.log("Tag Value : " + tagValue_CSV);
					VertexLogger.log("Access Type : " + accessType_CSV);
					VertexLogger.log("Start Date : " + startDate_CSV);
					VertexLogger.log("End Date : " + endDate_CSV);
					VertexLogger.log("Enabled Flag : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !lookupCode_CSV.equals("Lookup Code") )
					{
						Optional dataLookupCode_Imp = dataPresentInParticularColumn(
							lookupCodeVTX_IMPOSITION_TYPEPresentation, lookupCode_CSV);
						if ( dataLookupCode_Imp.isPresent() )
						{
							Optional enabled = dataPresentInParticularColumn(
								lookupEnabledFlagVTX_IMPOSITION_TYPEPresentation, enabled_CSV);
							flag1 = enabled.isPresent();
							VertexLogger.log("" + flag1);
						}
						else
						{
							htmlElement.sendKeys(Keys.END);
							jsWaiter.sleep(100);
							click.clickElement(nextArrowOnSummaryTable);
						}
					}
				}
				if ( flag1 )
				{
					flag = true;
				}
				else
				{
					flag = false;
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
		return flag;
	}

	/**
	 * Verify Export to CSV functionality on Look Up Type
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVLookups( String instName ) throws IOException
	{
		boolean flag = false;
		boolean flag1 = false;

		String fileName = "Lookups_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToLookups();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		exportToCSVSummaryPage.click();

		FluentWait<WebDriver> wait = new FluentWait<>(driver);
		wait
			.pollingEvery(Duration.ofSeconds(1))
			.withTimeout(Duration.ofSeconds(15))
			.until(x -> file.exists());

		try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
		{
			for ( CSVRecord csvRecord : csvParser )
			{
				String name = csvRecord.get(0);
				if ( name.contains(instName) )
				{
					VertexLogger.log("Fusion Instance Name : " + name);
					flag = true;
					break;
				}
			}
		}

		try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withHeader("Lookup Type", "Lookup Type Desc", "Access Type")
				.withTrim()) ; )
		{
			for ( CSVRecord csvRecord : csvParser )
			{
				String lookupType_CSV = csvRecord.get("Lookup Type");
				String lookupTypeDesc_CSV = csvRecord.get("Lookup Type Desc");
				String accessType_CSV = csvRecord.get("Access Type");

				VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
				VertexLogger.log("---------------");
				VertexLogger.log("Lookup Type : " + lookupType_CSV);
				VertexLogger.log("Lookup Type Desc : " + lookupTypeDesc_CSV);
				VertexLogger.log("Access Type : " + accessType_CSV);
				VertexLogger.log("---------------\n\n");

				if ( !lookupType_CSV.equals("Lookup Type") )
				{
					Optional dataLookupType = dataPresentInParticularColumn(lookupTypePresentation, lookupType_CSV);
					if ( dataLookupType.isPresent() )
					{
						Optional dataAccessType = dataPresentInParticularColumn(lookupAccessTypePresentation,
							accessType_CSV);
						flag1 = dataAccessType.isPresent();
						VertexLogger.log("" + flag1);
					}
					else
					{
						htmlElement.sendKeys(Keys.END);
						jsWaiter.sleep(100);
						click.clickElement(nextArrowOnSummaryTable);
					}
				}
			}
			if ( flag1 )
			{
				flag = true;
			}
			else
			{
				flag = false;
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
		return flag;
	}
}