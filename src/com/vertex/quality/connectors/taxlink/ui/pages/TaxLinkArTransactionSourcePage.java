package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.ArTransactionSource;
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
 * This class represents all the locators and methods for AR Transaction Source page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionSourcePage extends TaxLinkBasePage
{
	public TaxLinkArTransactionSourcePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/arBatchSourceAdd']")
	private WebElement addArTransactionSource;

	@FindBy(xpath = "//label[contains(text(),'SEQ ID')]/ancestor::div/input")
	private WebElement addArSeqID;

	@FindBy(xpath = "//label[contains(text(),'AR Transaction Source Name')]/ancestor::div/input")
	private WebElement addArTransName;

	@FindBy(xpath = "//label[contains(text(),'AR Transaction Source Description')]/ancestor::div/input")
	private WebElement addArTransactionSourceDesc;

	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement arTransSourcePopup;

	@FindBy(xpath = "//button[@class='notification__btn-close']")
	private WebElement arTransSourceClosePopup;

	@FindBy(className = "notification__inner")
	private WebElement verifyArTransAlreadyExistsError;

	@FindBy(xpath = "(//div[@class='ag-body-viewport ag-layout-normal ag-row-no-animation'])[last()-1]")
	private WebElement presentationRow;

	@FindBy(xpath = "//div[@col-id='lookupCode']")
	private List<WebElement> arTransactionSeqIDPresentation;

	@FindBy(xpath = "//div[@col-id='lookupCodeDesc']")
	private List<WebElement> arTransactionSourceNamePresentation;

	@FindBy(xpath = "//div[@col-id='lookupMeaning']")
	private List<WebElement> arTransactionSourceDescPresentation;

	@FindBy(xpath = "//div[@col-id='enabledFlag']")
	private List<WebElement> arTransactionSourceFlagEnabledPresentation;

	@FindBy(xpath = "//label[contains(text(),'AR Transaction Source Description')]/ancestor::div/input")
	private WebElement transactionSourceDesc;

	@FindBy(xpath = "//label[contains(text(),'AR Transaction Source Name')]/ancestor::div/input")
	private WebElement transactionSourceName;

	/**
	 * Save the AR Transaction Source on Add AR Transaction Source page
	 */
	public void saveArTransactionSource( )
	{
		htmlElement.sendKeys(Keys.END);
		wait.waitForElementDisplayed(saveButton);
		click.clickElement(saveButton);
	}

	/**
	 * Add AR Transaction Source
	 * and verify the details on summary table
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean addAndVerifyArTransactionSource( )
	{
		boolean flag = false;
		String arSeqID;
		String arTransactionSourceName;
		String arTransactionSourceDesc;
		navigateToArTransactionSource();
		wait.waitForElementEnabled(addArTransactionSource);
		click.clickElement(addArTransactionSource);
		arSeqID = utils.randomNumber("5");
		wait.waitForElementDisplayed(addArSeqID);
		text.enterText(addArSeqID, arSeqID);
		arTransactionSourceName = utils.randomAlphaNumericText();
		wait.waitForElementDisplayed(transactionSourceName);
		text.enterText(transactionSourceName, arTransactionSourceName);
		arTransactionSourceDesc = utils.randomText();
		wait.waitForElementDisplayed(addArTransactionSourceDesc);
		text.enterText(addArTransactionSourceDesc, arTransactionSourceDesc);
		saveArTransactionSource();
		List<String> dataEntered = Arrays.asList(arSeqID, arTransactionSourceName, arTransactionSourceDesc);
		VertexLogger.log("New record added : " + dataEntered);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		wait.waitForElementEnabled(addArTransactionSource);
		click.clickElement(addArTransactionSource);
		VertexLogger.log("Entering same AR transaction Source details again..");
		wait.waitForElementDisplayed(addArSeqID);
		text.enterText(addArSeqID, arSeqID);
		wait.waitForElementDisplayed(transactionSourceName);
		text.enterText(transactionSourceName, arTransactionSourceName);
		wait.waitForElementDisplayed(addArTransactionSourceDesc);
		text.enterText(addArTransactionSourceDesc, arTransactionSourceDesc);
		saveArTransactionSource();

		wait.waitForElementDisplayed(arTransSourcePopup);
		String arSeqIDNew = null;
		String arTransactionSourceNameNew = null;
		String arTransactionSourceDescNew = null;
		String arSeqIDUpdated = null;
		if ( arTransSourcePopup
			.getText()
			.contains("Records already exists") )
		{
			VertexLogger.log("ERROR : Duplicate data!!");
			click.clickElement(arTransSourceClosePopup);
			VertexLogger.log("Entering New AR transaction Source details..");
			click.clickElement(cancelButton);
			wait.waitForElementDisplayed(summaryPageTitle);
			wait.waitForElementEnabled(addArTransactionSource);
			click.clickElement(addArTransactionSource);
			arSeqIDNew = utils.randomNumber("8");
			wait.waitForElementDisplayed(addArSeqID);
			text.enterText(addArSeqID, arSeqIDNew);
			VertexLogger.log("arSeqIDNew : " + arSeqIDNew);
			arSeqIDUpdated = arSeqIDNew.replaceFirst("^0+(?!$)", "");
			VertexLogger.log("arSeqIDUpdated for trailing preceeding zeros:: " + arSeqIDUpdated);
			arTransactionSourceNameNew = utils.randomAlphaNumericText();
			wait.waitForElementDisplayed(transactionSourceName);
			text.enterText(transactionSourceName, arTransactionSourceNameNew);
			arTransactionSourceDescNew = utils.randomText();
			wait.waitForElementDisplayed(addArTransactionSourceDesc);
			text.enterText(addArTransactionSourceDesc, arTransactionSourceDescNew);
			saveArTransactionSource();
			List<String> newDataEntered = Arrays.asList(arSeqIDUpdated, arTransactionSourceNameNew,
				arTransactionSourceDescNew);
			VertexLogger.log("New record added : " + newDataEntered);
		}

		wait.waitForElementDisplayed(exportToCSVSummaryPage);
		int count = Integer.valueOf(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			String finalArTransactionSourceName = arTransactionSourceNameNew;
			Optional<WebElement> data = dataPresent(finalArTransactionSourceName);
			if ( data.isPresent() )
			{
				String finalArTransactionSourceDesc = arTransactionSourceDescNew;
				String finalArSeqID = arSeqIDUpdated;
				jsWaiter.sleep(50000);
				boolean seqIDFlag = arTransactionSeqIDPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.contains(finalArSeqID));
				VertexLogger.log(String.valueOf(seqIDFlag));
				boolean transSourceDescFlag = arTransactionSourceDescPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.equals(finalArTransactionSourceDesc));
				VertexLogger.log(String.valueOf(transSourceDescFlag));
				boolean transEnabledFlag = arTransactionSourceFlagEnabledPresentation
					.stream()
					.anyMatch(col -> col
						.getText()
						.contains("Y"));
				VertexLogger.log(String.valueOf(transEnabledFlag));
				if ( seqIDFlag && transSourceDescFlag && transEnabledFlag )
				{
					flag = true;
					break;
				}
			}
			else
			{
				js.executeScript("arguments[0].scrollIntoView();", nextArrowOnSummaryTable);
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return flag;
	}

	/**
	 * Method to search data in summary table of AR Transaction Source page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		jsWaiter.sleep(1000);
		Optional<WebElement> dataFound = arTransactionSourceNamePresentation
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Verify title of View AR Transaction Source Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleViewArTransactionSourcePage( )
	{
		boolean flag;
		String viewArTransactionSourceTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = viewArTransactionSourceTitle.equalsIgnoreCase(
			ArTransactionSource.ArTransactionSourceDetails.headerViewArTransactionSourcePage);

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
	 * View AR Transaction Source
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean viewArTransactionSource( )
	{
		boolean flag = false;
		navigateToArTransactionSource();
		String viewRecord = arTransactionSourceNamePresentation
			.stream()
			.findFirst()
			.get()
			.getText();
		VertexLogger.log("Selected Record with Name : " + viewRecord + " to check View functionality");
		expWait.until(ExpectedConditions.visibilityOf(actions));
		actions.click();
		viewButton.click();

		if ( verifyTitleViewArTransactionSourcePage() )
		{
			boolean transactionSeqIDViewPageFlag = !addArSeqID.isEnabled();
			VertexLogger.log(String.valueOf(transactionSeqIDViewPageFlag));
			boolean transactionSourceNameFlag = !transactionSourceName.isEnabled();
			VertexLogger.log(String.valueOf(transactionSourceNameFlag));
			boolean transactionSourceDescFlag = !transactionSourceDesc.isEnabled();
			VertexLogger.log(String.valueOf(transactionSourceDescFlag));
			boolean enabledArTransactionSourceFlag = !enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(enabledArTransactionSourceFlag));
			boolean saveArTransactionSourceFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveArTransactionSourceFlag));
			boolean cancelArTransactionSourceFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelArTransactionSourceFlag));

			if ( transactionSeqIDViewPageFlag && transactionSourceNameFlag && transactionSourceDescFlag &&
				 enabledArTransactionSourceFlag && saveArTransactionSourceFlag && cancelArTransactionSourceFlag )
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
	 * Verify title of Edit AR Transaction Source Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditArTransactionSourcePage( )
	{
		boolean flag;
		String editArTransactionSourceTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = editArTransactionSourceTitle.equalsIgnoreCase(
			ArTransactionSource.ArTransactionSourceDetails.headerEditArTransactionSourcePage);

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
	 * Edit AR Transaction Source
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean editArTransactionSource( )
	{
		boolean flag = false;
		String transSrcName = null;
		navigateToArTransactionSource();
		String editRecord = arTransactionSourceNamePresentation
			.stream()
			.findFirst()
			.get()
			.getText();
		VertexLogger.log("Selected Record with Name : " + editRecord + " to check Edit functionality");
		expWait.until(ExpectedConditions.visibilityOf(actions));
		editButton.click();

		if ( verifyTitleEditArTransactionSourcePage() )
		{
			boolean transactionSeqIDEditPageFlag = !addArSeqID.isEnabled();
			VertexLogger.log(String.valueOf(transactionSeqIDEditPageFlag));
			boolean transactionSourceNameFlag = !transactionSourceName.isEnabled();
			transSrcName = transactionSourceName.getText();
			VertexLogger.log(String.valueOf(transactionSourceNameFlag));
			boolean transactionSourceDescFlag = !transactionSourceDesc.isEnabled();
			VertexLogger.log(String.valueOf(transactionSourceDescFlag));
			boolean enabledArTransactionSourceFlag = enabledFlag.isEnabled();
			VertexLogger.log(String.valueOf(enabledArTransactionSourceFlag));
			boolean saveArTransactionSourceFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveArTransactionSourceFlag));
			boolean cancelArTransactionSourceFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelArTransactionSourceFlag));

			if ( transactionSeqIDEditPageFlag && transactionSourceNameFlag && transactionSourceDescFlag &&
				 enabledArTransactionSourceFlag && saveArTransactionSourceFlag && cancelArTransactionSourceFlag )
			{
				verifyEditFunctionality(transSrcName);
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
	 * @param transSrcNameEdit
	 *
	 * @return boolean
	 */

	public boolean verifyEditFunctionality( String transSrcNameEdit )
	{
		boolean flag;
		click.clickElement(enabledFlag);
		boolean saveArTransactionSourceFlag = saveButton.isEnabled();
		VertexLogger.log(String.valueOf(saveArTransactionSourceFlag));
		saveArTransactionSource();
		if ( verifyUpdatedArTransactionSourceDetails(transSrcNameEdit) )
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
	 * Verify AR Transaction Source updated after edit action
	 * on AR Transaction Source page in Taxlink application
	 *
	 * @param transSrcNameEditPage
	 *
	 * @return
	 */

	public boolean verifyUpdatedArTransactionSourceDetails( String transSrcNameEditPage )
	{
		boolean flag = false;
		wait.waitForElementDisplayed(externalFilter);
		checkPageNavigation();
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			Optional<WebElement> dataFound = dataPresentInParticularColumn(arTransactionSourceNamePresentation,
				transSrcNameEditPage);
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
	 * Verify title of AR Transaction Source Page in Taxlink application
	 *
	 * @return
	 */
	public boolean verifyTitleArTransactionSourcePage( )
	{
		boolean flag;
		String arTransactionSourceTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = arTransactionSourceTitle.equalsIgnoreCase(
			ArTransactionSource.ArTransactionSourceDetails.headerArTransactionSourcePage);

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
	 * Verify import option of AR Transaction Source
	 * @return boolean
	 */

	public boolean importArTransactionSource( )
	{
		boolean flag;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag4 = false;

		navigateToArTransactionSource();
		verifyTitleArTransactionSourcePage();
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

		wait.waitForElementDisplayed(externalFilter);

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
	 * Verify Export to CSV functionality on AR Transaction Source page
	 * in Taxlink application
	 *
	 * @return flag
	 */

	public boolean exportToCSVArTransactionSource( String instName ) throws IOException
	{
		boolean flag = false, flag1 = false, flag2 = false;

		String fileName = "AR_Transaction_Source_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToArTransactionSource();
		if ( !checkNoRecordsPresent() )
		{
			exportToCSVSummaryPage.click();
			jsWaiter.sleep(5000);

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
					.withHeader("AR Transaction Source Type", "SEQ ID", "AR Transaction Source Name",
						"AR Transaction Source Description", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					String arTransactionSourceType_CSV = csvRecord.get("AR Transaction Source Type");
					String seqId_CSV = csvRecord.get("SEQ ID");
					String arTransactionSourceName_CSV = csvRecord.get("AR Transaction Source Name");
					String arTransactionSourceDescription_CSV = csvRecord.get("AR Transaction Source Description");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("AR Transaction Source Type : " + arTransactionSourceType_CSV);
					VertexLogger.log("Seq ID : " + seqId_CSV);
					VertexLogger.log("AR Transaction Source Name : " + arTransactionSourceName_CSV);
					VertexLogger.log("AR Transaction Source Description : " + arTransactionSourceDescription_CSV);
					VertexLogger.log("Enabled Flag : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !arTransactionSourceName_CSV.equals("AR Transaction Source Name") )
					{
						Optional arTransactionName = dataPresentInParticularColumn(arTransactionSourceNamePresentation,
							arTransactionSourceName_CSV);
						if ( arTransactionName.isPresent() )
						{
							Optional seqID = dataPresentInParticularColumn(arTransactionSeqIDPresentation, seqId_CSV);
							flag1 = seqID.isPresent();
							VertexLogger.log("" + flag1);
							Optional transDesc = dataPresentInParticularColumn(arTransactionSourceDescPresentation,
								arTransactionSourceDescription_CSV);
							flag2 = transDesc.isPresent();
							VertexLogger.log("" + flag2);
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
				if ( flag1 && flag2 )
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
		else
		{
			flag = true;
		}
		return flag;
	}
}

