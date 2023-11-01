package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.ArTransactionType;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for AR Transaction Type page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionTypePage extends TaxLinkBasePage
{
	public TaxLinkArTransactionTypePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/aRTransactionTypeAdd']")
	private WebElement addArTransactionType;

	@FindBy(xpath = "//input[@name='custTrxTypeSeqId']")
	private WebElement seqID;

	@FindBy(xpath = "//input[@name='trxTypeName']")
	private WebElement arTransactionName;

	@FindBy(xpath = "//input[@name='trxTypeDesc']")
	private WebElement arTransactionDesc;

	@FindBy(className = "notification__inner")
	private WebElement verifyArTransAlreadyExistsError;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='trxTypeName']")
	private List<WebElement> summaryTableArTransactionType;

	@FindBy(xpath = "(//div[@class='ag-body-viewport ag-layout-normal ag-row-no-animation'])[last()-1]")
	private WebElement presentationRow;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='custTrxTypeSeqId']")
	private List<WebElement> arTransTypeIDPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='trxTypeName']")
	private List<WebElement> arTransTypeNamePresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='trxTypeDesc']")
	private List<WebElement> arTransTypeDescPresentation;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='enabledFlag']")
	private List<WebElement> arTransTypeFlagEnabledPresentation;

	/**
	 * Add AR Transaction Type
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyArTransactionType( )
	{
		boolean flag = false;
		String seqIDInput;
		String arTransName;
		String arTransDesc;
		navigateToArTransType();
		wait.waitForElementEnabled(addArTransactionType);
		click.clickElement(addArTransactionType);
		seqIDInput = utils.randomNumber("5");
		wait.waitForElementDisplayed(seqID);
		text.enterText(seqID, seqIDInput);
		arTransName = utils.randomText();
		wait.waitForElementDisplayed(arTransactionName);
		text.enterText(arTransactionName, arTransName);
		arTransDesc = utils.randomText();
		wait.waitForElementDisplayed(arTransactionDesc);
		text.enterText(arTransactionDesc, arTransDesc);
		saveArTransactionType();
		List<String> dataEntered = Arrays.asList(seqIDInput, arTransName, arTransDesc);
		VertexLogger.log("New record added : " + dataEntered);

		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableArTransactionType));

		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			String finalArTransName = arTransName;
			Optional<WebElement> data = dataPresent(finalArTransName);
			if ( data.isPresent() )
			{
				boolean flag2 = arTransTypeDescPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(arTransDesc));
				VertexLogger.log(String.valueOf(flag2));
				boolean flag3 = arTransTypeFlagEnabledPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.contains("Y"));
				VertexLogger.log(String.valueOf(flag3));

				if ( flag2 && flag3 )
				{
					flag = true;
					break;
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
	 * Save the AR Transaction Type on Add AR Transaction Type page
	 *
	 * @return boolean
	 */
	public void saveArTransactionType( )
	{
		htmlElement.sendKeys(Keys.END);
		wait.waitForElementDisplayed(saveButton);
		click.clickElement(saveButton);
	}

	/**
	 * Method to search data in summary table of AR Transaction Type page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = summaryTableArTransactionType
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Verify title of View AR Transaction Type Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewArTransTypePage( )
	{
		boolean flag;
		String viewArTransTypeTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = viewArTransTypeTitle.equalsIgnoreCase(
			ArTransactionType.ARTransactionTypeDetails.headerViewARTransactionTypePage);

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
	 * View AR Transaction Type
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewArTransactionType( )
	{
		boolean flag = false;
		navigateToArTransType();
		expWait.until(ExpectedConditions.visibilityOf(actions));
		actions.click();
		viewButton.click();

		if ( verifyTitleViewArTransTypePage() )
		{
			boolean seqIDFlag = !seqID.isEnabled();
			VertexLogger.log(String.valueOf(seqIDFlag));
			boolean arTransactionNameFlag = !arTransactionName.isEnabled();
			VertexLogger.log(String.valueOf(arTransactionNameFlag));
			boolean arTransactionDescFlag = !arTransactionDesc.isEnabled();
			VertexLogger.log(String.valueOf(arTransactionDescFlag));
			boolean enabledArTransactionTypeFlag = !enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(enabledArTransactionTypeFlag));
			boolean saveArTransactionTypeFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveArTransactionTypeFlag));
			boolean cancelArTransactionTypeFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelArTransactionTypeFlag));

			if ( seqIDFlag && arTransactionNameFlag && arTransactionDescFlag && enabledArTransactionTypeFlag &&
				 saveArTransactionTypeFlag && cancelArTransactionTypeFlag )
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
	 * Verify title of Edit AR Transaction Type Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditArTransTypePage( )
	{
		boolean flag;
		String editArTransTypeTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = editArTransTypeTitle.equalsIgnoreCase(
			ArTransactionType.ARTransactionTypeDetails.headerEditARTransactionTypePage);

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
	 * Edit AR Transaction Type
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean editArTransactionType( )
	{
		boolean flag = false;
		String arTransName;
		navigateToArTransType();
		expWait.until(ExpectedConditions.visibilityOf(actions));
		editButton.click();

		if ( verifyTitleEditArTransTypePage() )
		{
			boolean seqIDFlag = !seqID.isEnabled();
			VertexLogger.log(String.valueOf(seqIDFlag));
			boolean arTransactionNameFlag = !arTransactionName.isEnabled();
			arTransName = arTransactionName.getText();
			VertexLogger.log(String.valueOf(arTransactionNameFlag));
			boolean arTransactionDescFlag = !arTransactionDesc.isEnabled();
			VertexLogger.log(String.valueOf(arTransactionDescFlag));
			boolean enabledArTransactionTypeFlag = enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(enabledArTransactionTypeFlag));
			boolean saveArTransactionTypeFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveArTransactionTypeFlag));
			boolean cancelArTransactionTypeFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelArTransactionTypeFlag));

			if ( seqIDFlag && arTransactionNameFlag && arTransactionDescFlag && enabledArTransactionTypeFlag &&
				 saveArTransactionTypeFlag && cancelArTransactionTypeFlag )
			{
				verifyEditFunctionality(arTransName);
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
	 * Verify Edit functionality on Edit page in Taxlink application
	 *
	 * @param arTransNameEdit
	 *
	 * @return boolean
	 */
	public boolean verifyEditFunctionality( String arTransNameEdit )
	{
		boolean flag;
		click.clickElement(enabledFlag);
		boolean flag1 = saveButton.isEnabled();
		VertexLogger.log(String.valueOf(flag1));
		saveArTransactionType();
		if ( verifyUpdatedArTransTypeDetails(arTransNameEdit) )
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
	 * Verify AR Transaction Type updated
	 * on summary Transaction Type page in Taxlink application
	 *
	 * @param arTransNameEditPage
	 *
	 * @return boolean
	 */
	public boolean verifyUpdatedArTransTypeDetails( String arTransNameEditPage )
	{
		boolean flag = false;
		wait.waitForElementDisplayed(externalFilter);
		checkPageNavigation();
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(arTransTypeNamePresentation,
				arTransNameEditPage);
			if ( dataFound.isPresent() )
			{
				flag = true;
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
	 * Verify title of AR Transaction Type Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleArTransTypePage( )
	{
		boolean flag;
		String arTransTypeTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = arTransTypeTitle.equalsIgnoreCase(
			ArTransactionType.ARTransactionTypeDetails.headerARTransactionTypePage);

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

	/*
	 * Verify import option of AR Transaction Type
	 *
	 * @return boolean
	 */

	public boolean importArTransactionType( )
	{
		boolean flag;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag4 = false;

		navigateToArTransType();
		verifyTitleArTransTypePage();
		wait.waitForElementDisplayed(importButtonOnSummaryPage);
		click.clickElement(importButtonOnSummaryPage);

		expWait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

		click.clickElement(importSelectAllCheckBox);
		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("checked") )
		{
			flag1 = true;

			flag2 = checkBoxIterate(".//span[1]", "ag-icon ag-icon-checkbox-checked");
		}

		click.clickElement(importSelectAllCheckBox);
		boolean flag3 = !checkbox.isCheckboxChecked(importSelectAllCheckBox);

		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("unchecked") )
		{
			flag1 = true;

			flag2 = checkBoxIterate(".//span[2]", "ag-icon ag-icon-checkbox-unchecked");
		}

		Optional<WebElement> selectedEle = importListCheckBox
			.stream()
			.findFirst();
		String selectedVal = selectedEle
			.get()
			.getText();
		selectedEle
			.get()
			.click();

		click.clickElement(importButtonPopUp);

		expWait.until(ExpectedConditions.textToBePresentInElement(popUpForImport,
			"Are you sure you want to save these records?"));

		click.clickElement(savePopUpImportButton);

		expWait.until(ExpectedConditions.visibilityOf(externalFilter));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(selectedVal);

			if ( data.isPresent() )
			{
				flag4 = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		if ( flag1 && flag2 && flag3 && flag4 )
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
	 * Method to iterate over list of checkboxes in Import pop up
	 *
	 * @param xPath
	 * @param attribute
	 *
	 * @return boolean
	 */
	public boolean checkBoxIterate( String xPath, String attribute )
	{
		boolean flag = false;

		Iterator<WebElement> itr = importListCheckBox.listIterator();
		while ( itr.hasNext() )
		{
			WebElement ele = itr.next();
			flag = ele
				.findElements(By.xpath(xPath))
				.stream()
				.anyMatch(attr -> attr
					.getAttribute("class")
					.contains(attribute));
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
	 * Verify Export to CSV functionality on AR Transaction type page
	 * in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean exportToCSVArTransactionType( String instName ) throws IOException
	{
		boolean flag = false;
		boolean seqIDFlag = false;
		boolean transDescFlag = false;
		String fileName = "AR_Transaction_Type_Extract.csv";

		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToArTransType();
		verifyTitleArTransTypePage();
		if ( !checkNoRecordsPresent() )
		{
			wait.waitForElementDisplayed(exportToCSVSummaryPage);
			exportToCSVSummaryPage.click();

			jsWaiter.sleep(5000);

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
						flag = true;
						break;
					}
				}
			}
			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) )
			{
				try ( CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("SEQ ID", "AR Transaction Name", "AR Transaction Description", "Enabled")
					.withTrim()) )
				{
					for ( CSVRecord csvRecord : csvParser )
					{
						String seqId_CSV = csvRecord.get("SEQ ID");
						String arTransactionName_CSV = csvRecord.get("AR Transaction Name");
						String arTransactionDescription_CSV = csvRecord.get("AR Transaction Description");
						String enabled_CSV = csvRecord.get("Enabled");

						VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
						VertexLogger.log("---------------");
						VertexLogger.log("SEQ ID : " + seqId_CSV);
						VertexLogger.log("AR Transaction Name : " + arTransactionName_CSV);
						VertexLogger.log("AR Transaction Description : " + arTransactionDescription_CSV);
						VertexLogger.log("Enabled Flag : " + enabled_CSV);
						VertexLogger.log("---------------\n\n");

						if ( !arTransactionName_CSV.equals("AR Transaction Name") )
						{
							Optional arTransactionName = dataPresentInParticularColumn(arTransTypeNamePresentation,
								arTransactionName_CSV);
							if ( arTransactionName.isPresent() )
							{
								Optional seqID = dataPresentInParticularColumn(arTransTypeIDPresentation, seqId_CSV);
								seqIDFlag = seqID.isPresent();
								VertexLogger.log("" + seqIDFlag);
								Optional transDesc = dataPresentInParticularColumn(arTransTypeDescPresentation,
									arTransactionDescription_CSV);
								transDescFlag = transDesc.isPresent();
								VertexLogger.log("" + transDescFlag);
							}
							else
							{
								htmlElement.sendKeys(Keys.END);
								jsWaiter.sleep(100);
								click.clickElement(nextArrowOnSummaryTable);
							}
							break;
						}
					}
					if ( seqIDFlag && transDescFlag )
					{
						flag = true;
					}
					else
					{
						flag = false;
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
			flag = true;
		}
		return flag;
	}
}

