package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.BusinessUnit;
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
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for Business Unit page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkBusinessUnitPage extends TaxLinkBasePage
{

	public TaxLinkBusinessUnitPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/businessUnitsAdd']")
	private WebElement addBusinessUnit;

	@FindBy(xpath = "//input[@name='Business Unit Id ']")
	private WebElement businessUnitID;

	@FindBy(xpath = "//input[@name='Business Unit Name']")
	private WebElement businessUnitName;

	@FindBy(xpath = "//span[contains(text(),'Countries of Operation')]/ancestor::p/following-sibling::div")
	private WebElement buCountryNameSelectBox;

	@FindBy(id = "searchCountries")
	private WebElement buCountryNameTextBox;

	@FindBy(xpath = "//div[contains(@class,'businessUnit_modal_body')]/div/label/span")
	private WebElement buCountryNameCheckbox;

	@FindBy(xpath = "//button[@data-cy='btn-modal-save']")
	private WebElement saveBuCountryName;

	@FindBy(xpath = "//button[@data-cy='btn-modal-cancel']")
	private WebElement cancelBuCountryName;

	@FindBy(xpath = "//div[@col-id='taxRegimeName']")
	private List<WebElement> verifyCountryInTaxRegimeName;

	@FindBy(className = "notification__inner")
	private WebElement verifyBUAlreadyExistsError;

	@FindBy(xpath = "//*[@id='myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='businessUnitName']")
	private List<WebElement> summaryTableBU;

	@FindBy(xpath = "//*[@col-id='businessUnitId']")
	private List<WebElement> businessUnitIDPresentation;

	@FindBy(xpath = "//*[@col-id='businessUnitName']")
	private List<WebElement> businessUnitNamePresentation;

	@FindBy(xpath = "//*[@col-id='countriesOfOperation']")
	private List<WebElement> buCountryNamePresentation;

	@FindBy(xpath = "//div[@class='custom__cb']")
	private List<WebElement> checkboxesCountryBuEditPage;

	@FindBy(xpath = "//*[@id='my-modal']/div/div[3]/div/div[1]/button")
	private WebElement savebuCountryEditPage;

	@FindBy(xpath = "//div[contains(text(),'Clear All')]")
	private WebElement clearAllCountriesCheckBox;

	@FindBy(xpath = "//div[contains(text(),'Select All')]")
	private WebElement selectAllCountriesCheckBox;

	@FindBy(xpath = "//span[@class='modal-close']")
	private WebElement closeCountryPopUpButton;

	@FindBy(xpath = "//div[@class = 'notification-container']")
	private WebElement errorDuplicateData;

	@FindBy(xpath = "//div[@class='notification__inner']")
	private WebElement businessUnitPopup;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[2]")
	public List<WebElement> importListBuID;

	@FindBy(xpath = "//input[@name='enableFlag']")
	protected WebElement enabledFlag;

	private By buNameFollowingBuID = By.xpath(".//following-sibling::div");

	/*
	 * Select Countries for Business Unit on Add business unit page
	 */

	public void selectCountry( String selectCountryName )
	{
		wait.waitForElementDisplayed(buCountryNameSelectBox);
		click.moveToElementAndClick(buCountryNameSelectBox);
		wait.waitForElementDisplayed(buCountryNameTextBox);
		text.enterText(buCountryNameTextBox, selectCountryName);
		expWait.until(ExpectedConditions.visibilityOf(buCountryNameCheckbox));
		buCountryNameCheckbox.click();

		jsWaiter.sleep(5000);
		buCountryNameTextBox.clear();
		buCountryNameTextBox.sendKeys(Keys.ENTER);

		click.clickElement(selectAllCountriesCheckBox);
		if ( selectAllCountriesCheckBox
			.getAttribute("class")
			.contains("checked") )
		{
			checkBoxIterate(".//span[1]", "ag-icon ag-icon-checkbox-checked");
		}

		// check clear all button
		click.clickElement(clearAllCountriesCheckBox);
		if ( !buCountryNameCheckbox.isSelected() )
		{
			if ( !saveBuCountryName.isEnabled() )
			{
				selectAllCountriesCheckBox.click();
			}
		}
		jsWaiter.sleep(3000);
		wait.waitForElementEnabled(saveBuCountryName);
		click.clickElement(saveBuCountryName);
	}

	/**
	 * Verify country added in the Business Unit on Add business unit page
	 */
	public boolean verifyTaxRegimeForCountry( String selectedCountry )
	{
		boolean regimeForCountry = false;
		String[] countryList = selectedCountry.split(",");
		expWait.until(ExpectedConditions.visibilityOfAllElements(verifyCountryInTaxRegimeName));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( String s : countryList )
		{
			checkPageNavigation();
			for ( int j = 1 ; j <= count ; j++ )
			{
				Optional<WebElement> data = dataPresentInParticularColumn(verifyCountryInTaxRegimeName, s);

				if ( data.isPresent() )
				{
					VertexLogger.log(s + " is verified on the tax regime name list!!");
					regimeForCountry = true;
					break;
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		return regimeForCountry;
	}

	/**
	 * Save the Business Unit on Add business unit page
	 */
	public void saveBusinessUnit( )
	{
		htmlElement.sendKeys(Keys.END);
		expWait.until(ExpectedConditions.elementToBeClickable(saveButton));
		click.clickElement(saveButton);
	}

	/**
	 * Add a Business Unit on Business unit page
	 *
	 * @return boolean
	 */

	public boolean addAndVerifyBusinessUnit( )
	{
		boolean flagAddBusinessUnit = false;
		String bUID;
		String bUName;
		String strPattern = "^0+(?!$)";
		String buIDUpdated;
		String selectedCountry = null;

		navigateToBusinessUnit();

		wait.waitForElementEnabled(addBusinessUnit);
		click.clickElement(addBusinessUnit);
		if ( verifyLengthExceedsErrorOnBUID() )
		{
			VertexLogger.log("Adding Business Unit details!!");
			bUID = utils.randomNumber("5");
			wait.waitForElementDisplayed(businessUnitID);
			text.enterText(businessUnitID, bUID);
			bUName = utils.randomText();
			wait.waitForElementDisplayed(businessUnitName);
			text.enterText(businessUnitName, bUName);
			selectCountry(BusinessUnit.BU_DETAILS.selectCountryName);
			jsWaiter.sleep(1000);
			saveBusinessUnit();

			List<String> dataEntered = Arrays.asList(bUID, bUName);
			VertexLogger.log("New record added : " + dataEntered);
			wait.waitForElementEnabled(addBusinessUnit);
			click.clickElement(addBusinessUnit);
			VertexLogger.log("Entering same Business Unit details again..");
			wait.waitForElementDisplayed(businessUnitID);
			text.enterText(businessUnitID, bUID);
			wait.waitForElementDisplayed(businessUnitName);
			text.enterText(businessUnitName, bUName);
			selectCountry(BusinessUnit.BU_DETAILS.selectCountryName);
			jsWaiter.sleep(5000);
			saveBusinessUnit();
			jsWaiter.sleep(5000);

			if ( businessUnitPopup
				.getText()
				.contains("Records already exists") )
			{
				VertexLogger.log("ERROR : Duplicate data!!");
				VertexLogger.log("Entering New Business Unit details..");
				click.clickElement(cancelButton);
				wait.waitForElementEnabled(addBusinessUnit);
				click.clickElement(addBusinessUnit);
				bUID = utils.randomNumber("5");
				wait.waitForElementDisplayed(businessUnitID);
				text.enterText(businessUnitID, bUID);
				VertexLogger.log("buID : " + bUID);
				buIDUpdated = bUID.replaceAll(strPattern, "");
				VertexLogger.log("buIDUpdated for trailing preceeding zeros: " + buIDUpdated);
				bUName = utils.randomText();
				wait.waitForElementDisplayed(businessUnitName);
				text.enterText(businessUnitName, bUName);
				selectCountry(BusinessUnit.BU_DETAILS.selectCountryName);
				selectedCountry = buCountryNameSelectBox.getText();
				VertexLogger.log("selectedCountry on Add Page " + selectedCountry);
				if ( verifyTaxRegimeForCountry(selectedCountry) )
				{
					jsWaiter.sleep(1000);
					saveBusinessUnit();
				}
			}

			List<String> newDataEntered = Arrays.asList(bUID, bUName, selectedCountry);
			VertexLogger.log("New record added : " + newDataEntered);

			expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableBU));

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				String finalBUName = bUName;
				Optional<WebElement> data = dataPresent(businessUnitNamePresentation, finalBUName);
				if ( data.isPresent() )
				{
					boolean flagBuName = businessUnitNamePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(finalBUName));
					VertexLogger.log(String.valueOf(flagBuName));
					final String finalSelectedCountry = selectedCountry;
					boolean flagBuCountryName = buCountryNamePresentation
						.stream()
						.anyMatch(col -> col
							.getText()
							.equals(finalSelectedCountry));
					VertexLogger.log(String.valueOf(flagBuCountryName));
					if ( flagBuName && flagBuCountryName )
					{
						flagAddBusinessUnit = true;
						break;
					}
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		return flagAddBusinessUnit;
	}

	/**
	 * Verify an error message for Length on BUID textbox
	 * for Numeric values exceeding 18 digits
	 *
	 * @return boolean
	 */

	public boolean verifyLengthExceedsErrorOnBUID( )
	{
		boolean flagVerifyBUID;
		String bUID;
		VertexLogger.log("Entering Business Unit ID length more than 18 digits..");
		bUID = utils.randomNumber("19");
		wait.waitForElementDisplayed(businessUnitID);
		text.enterText(businessUnitID, bUID);
		if ( businessUnitPopup
			.getText()
			.contains("Value exceeds Business Unit ID length") )
		{
			VertexLogger.log("Business Unit ID length exceeded! Kindly enter numeric value less than 19 digits!!");
			flagVerifyBUID = true;
		}
		else
		{
			VertexLogger.log("Business Unit ID length has not been verified!!");
			flagVerifyBUID = false;
		}
		return flagVerifyBUID;
	}

	/**
	 * Verify title of View Business Unit Page in Taxlink application
	 *
	 * @return boolean
	 */

	public boolean verifyTitleViewBuPage( )
	{
		boolean flagTitleViewBuPage;
		String viewBuTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = viewBuTitle.equalsIgnoreCase(BusinessUnit.BU_DETAILS.headerViewBusinessUnitPage);

		if ( verifyFlag )
		{
			flagTitleViewBuPage = true;
		}
		else
		{
			flagTitleViewBuPage = false;
		}
		return flagTitleViewBuPage;
	}

	/**
	 * View a Business Unit on Business unit page
	 *
	 * @return boolean
	 */

	public boolean viewBusinessUnit( )
	{
		boolean flagTitleViewBusinessUnit = false;
		boolean bUCountryViewEditPageFlag = false;
		navigateToBusinessUnit();
		expWait.until(ExpectedConditions.visibilityOf(actions));
		actions.click();
		viewButton.click();

		if ( verifyTitleViewBuPage() )
		{
			boolean businessUnitIDFlag = !businessUnitID.isEnabled();
			VertexLogger.log(String.valueOf(businessUnitIDFlag));
			boolean businessUnitNameFlag = !businessUnitName.isEnabled();
			VertexLogger.log(String.valueOf(businessUnitNameFlag));
			boolean saveBuViewEditPageFlag = !saveButton.isEnabled();
			VertexLogger.log(String.valueOf(saveBuViewEditPageFlag));
			boolean cancelBuViewEditPageFlag = cancelButton.isEnabled();
			VertexLogger.log(String.valueOf(cancelBuViewEditPageFlag));
			if ( buCountryNameSelectBox
				.getAttribute("class")
				.contains("Disabled") )
			{
				bUCountryViewEditPageFlag = true;
			}
			else
			{
				bUCountryViewEditPageFlag = false;
			}
			VertexLogger.log(String.valueOf(bUCountryViewEditPageFlag));
			if ( businessUnitIDFlag && businessUnitNameFlag && bUCountryViewEditPageFlag && saveBuViewEditPageFlag &&
				 cancelBuViewEditPageFlag )
			{
				flagTitleViewBusinessUnit = true;
			}
			else
			{
				flagTitleViewBusinessUnit = false;
			}
		}
		return flagTitleViewBusinessUnit;
	}

	/**
	 * Verify title of View Business Unit Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleEditBuPage( )
	{
		boolean flagTitleEditBusinessUnit;
		String editBuTitle = wait
			.waitForElementDisplayed(addViewEditPageTitle)
			.getText();
		boolean verifyFlag = editBuTitle.equalsIgnoreCase(BusinessUnit.BU_DETAILS.headerEditBusinessUnitPage);

		if ( verifyFlag )
		{
			flagTitleEditBusinessUnit = true;
		}
		else
		{
			flagTitleEditBusinessUnit = false;
		}
		return flagTitleEditBusinessUnit;
	}

	/**
	 * Verify title of Business Unit Page in Taxlink application
	 *
	 * @return
	 */
	public boolean verifyTitleBuPage( )
	{
		boolean flagTitleBusinessUnit;
		String bUPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = bUPageTitle.equalsIgnoreCase(BusinessUnit.BU_DETAILS.headerBusinessUnitPage);

		if ( verifyFlag )
		{
			flagTitleBusinessUnit = true;
		}
		else
		{
			flagTitleBusinessUnit = false;
		}
		return flagTitleBusinessUnit;
	}

	/**
	 * Edit a Business Unit on Business unit page
	 *
	 * @return boolean
	 */

	public boolean editBusinessUnit( )
	{
		boolean flagEditBusinessUnit = false;
		navigateToBusinessUnit();
		wait.waitForElementDisplayed(actions);
		editButton.click();

		if ( verifyTitleEditBuPage() )
		{
			boolean businessUnitIDFlag = !businessUnitID.isEnabled();
			String busUnitID = businessUnitID.getAttribute("value");
			VertexLogger.log(String.valueOf(businessUnitIDFlag));
			boolean businessUnitNameFlag = businessUnitName.isEnabled();
			businessUnitName.clear();
			text.enterText(businessUnitName, utils.randomText());
			String busUnitName = businessUnitName.getAttribute("value");
			VertexLogger.log(String.valueOf(businessUnitNameFlag));
			boolean buCountryNameSelectBoxFlag = buCountryNameSelectBox.isEnabled();
			VertexLogger.log(String.valueOf(buCountryNameSelectBoxFlag));
			click.clickElement(buCountryNameSelectBox);
			boolean saveBuViewEditPageFlag = !saveBuCountryName.isEnabled();
			VertexLogger.log(String.valueOf(saveBuViewEditPageFlag));
			boolean cancelBuViewEditPageFlag = cancelBuCountryName.isEnabled();
			VertexLogger.log(String.valueOf(cancelBuViewEditPageFlag));

			buCountryNameCheckbox.click();
			boolean saveBuCountryPageFlag = saveBuCountryName.isEnabled();
			VertexLogger.log(String.valueOf(saveBuCountryPageFlag));
			boolean cancelBuCountryPageFlag = cancelBuCountryName.isEnabled();
			VertexLogger.log(String.valueOf(cancelBuCountryPageFlag));
			saveBuCountryName.click();

			if ( businessUnitIDFlag && businessUnitNameFlag && buCountryNameSelectBoxFlag && saveBuViewEditPageFlag &&
				 cancelBuViewEditPageFlag )
			{
				if ( verifyEditFunctionality(busUnitID, busUnitName) )
				{
					flagEditBusinessUnit = true;
				}
			}
		}
		return flagEditBusinessUnit;
	}

	/**
	 * Verify Edit functionality on Edit page in Taxlink application
	 *
	 * @param busUnitID
	 * @param busUnitName
	 *
	 * @return boolean
	 */
	public boolean verifyEditFunctionality( final String busUnitID, final String busUnitName )
	{
		boolean flag = false;
		saveBusinessUnit();
		jsWaiter.sleep(5000);
		if ( verifyUpdatedBuDetails(busUnitID, busUnitName) )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Verify Business Unit updated
	 * on summary page in Taxlink application
	 *
	 * @param busUnitID
	 * @param busUnitName
	 *
	 * @return boolean
	 */
	public boolean verifyUpdatedBuDetails( final String busUnitID, final String busUnitName )
	{
		boolean flag = false;
		wait.waitForElementDisplayed(addButton);

		int countSummaryTable = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= countSummaryTable ; i++ )
		{
			jsWaiter.sleep(5000);
			Optional<WebElement> dataFound = dataPresentInParticularColumn(businessUnitIDPresentation, busUnitID);
			if ( dataFound.isPresent() )
			{
				String buSummaryName = dataFound
					.get()
					.findElement(buNameFollowingBuID)
					.getText();
				if ( buSummaryName.equals(busUnitName) )
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
	 * Method to search data in summary table of Business Unit page
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( List<WebElement> summaryTableCol, String text )
	{
		Optional<WebElement> dataFound = summaryTableCol
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
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
		boolean flagCheckBoxIterate = false;

		Iterator<WebElement> itr = importListCheckBox.listIterator();
		while ( itr.hasNext() )
		{
			WebElement ele = itr.next();
			flagCheckBoxIterate = ele
				.findElements(By.xpath(xPath))
				.stream()
				.anyMatch(attr -> attr
					.getAttribute("class")
					.contains(attribute));
		}
		return flagCheckBoxIterate;
	}

	/**
	 * Verify import option of Business Unit
	 *
	 * @return boolean
	 */
	public boolean importBusinessUnit( )
	{
		boolean flagImportBusinessUnit;
		boolean flagSelectAllCheck = false;
		boolean flagCheckBoxIterate = false;
		boolean flagDataPresent = false;
		boolean flagIsCheckboxChecked = false;

		navigateToBusinessUnit();
		verifyTitleBuPage();
		click.clickElement(importButtonOnSummaryPage);

		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

		click.clickElement(importSelectAllCheckBox);
		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("checked") )
		{
			flagSelectAllCheck = true;

			flagCheckBoxIterate = checkBoxIterate(".//span[1]", "ag-icon ag-icon-checkbox-checked");
		}

		click.clickElement(importSelectAllCheckBox);
		flagIsCheckboxChecked = !checkbox.isCheckboxChecked(importSelectAllCheckBox);

		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("unchecked") )
		{
			flagSelectAllCheck = true;

			flagCheckBoxIterate = checkBoxIterate(".//span[2]", "ag-icon ag-icon-checkbox-unchecked");
		}

		Optional<WebElement> selectedEleID = importListBuID
			.stream()
			.findFirst();
		String selectedVal = selectedEleID
			.get()
			.getText();
		VertexLogger.log("" + selectedVal);

		Optional<WebElement> selectedEle = importListCheckBox
			.stream()
			.findFirst();
		selectedEle
			.get()
			.getText();
		selectedEle
			.get()
			.click();

		click.clickElement(importButtonPopUp);

		wait.until(ExpectedConditions.textToBePresentInElement(popUpForImport,
			"Are you sure you want to save these records?"));

		click.clickElement(savePopUpImportButton);

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(businessUnitIDPresentation, selectedVal);

			if ( data.isPresent() )
			{
				flagDataPresent = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		if ( flagIsCheckboxChecked && flagSelectAllCheck && flagCheckBoxIterate && flagDataPresent )
		{
			flagImportBusinessUnit = true;
		}
		else
		{
			flagImportBusinessUnit = false;
		}
		return flagImportBusinessUnit;
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
	 * Export to CSV on Business unit page
	 */

	public boolean exportToCSVBusinessUnit( String instName ) throws IOException
	{
		boolean flagExportToCSVBusinessUnit = false;
		boolean flagInstanceNameMatch = false;
		boolean flagBuNameMatch = false;
		boolean flagCountryMatch = false;

		String fileName = "BusinessUnits_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		navigateToBusinessUnit();
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
					.withHeader("Business Unit ID", "Business Unit Name", "Countries Of Operation")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					// Accessing values by Header names
					String bUID_CSV = csvRecord.get("Business Unit ID");
					String bUName_CSV = csvRecord.get("Business Unit Name");
					String countriesOfOp_CSV = csvRecord.get("Countries Of Operation");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Business Unit ID : " + bUID_CSV);
					VertexLogger.log("Business Unit Name : " + bUName_CSV);
					VertexLogger.log("Countries Of Operation : " + countriesOfOp_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !bUName_CSV.equals("Business Unit Name") )
					{
						Optional bUID = dataPresentInParticularColumn(businessUnitIDPresentation, bUID_CSV);
						if ( bUID.isPresent() )
						{
							Optional buName = dataPresentInParticularColumn(businessUnitNamePresentation, bUName_CSV);
							flagBuNameMatch = buName.isPresent();
							VertexLogger.log("" + flagBuNameMatch);
							Optional countryOfOperations = dataPresentInParticularColumn(buCountryNamePresentation,
								countriesOfOp_CSV);
							flagCountryMatch = countryOfOperations.isPresent();
							VertexLogger.log("" + flagCountryMatch);
						}
						else
						{
							htmlElement.sendKeys(Keys.END);
							jsWaiter.sleep(100);
							click.clickElement(nextArrowOnSummaryTable);
						}
						if ( flagInstanceNameMatch && flagBuNameMatch && flagCountryMatch )
						{
							flagExportToCSVBusinessUnit = true;
						}
						else
						{
							flagExportToCSVBusinessUnit = false;
						}
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
			flagExportToCSVBusinessUnit = true;
		}
		return flagExportToCSVBusinessUnit;
	}

	/**
	 * Export to CSV for Tax Regimes
	 * on Business unit page
	 */

	public boolean exportToCSVTaxRegimesBu( )
	{
		boolean flagExportToCSVTaxRegimesBu = false;
		boolean flagCountryMatch = false;

		String fileName = "TaxRegimes_Extract.csv";
		File CSV_FILE_PATH = new File(System.getProperty("user.dir") + "/resources/csvfiles/taxlink" + "/" + fileName);
		exportToCSVSummaryPage.click();

		FluentWait<WebDriver> wait = new FluentWait<>(driver);
		wait
			.pollingEvery(Duration.ofSeconds(1))
			.withTimeout(Duration.ofSeconds(15))
			.until(x -> CSV_FILE_PATH.exists());

		try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(CSV_FILE_PATH))) ;
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withQuote(null)
				.withFirstRecordAsHeader()
				.withHeader("Tax Regime Name", "Tax Regime Code", "Country")
				.withTrim()) ; )
		{
			for ( CSVRecord csvRecord : csvParser )
			{
				// Accessing values by Header names
				String taxRegimeName = csvRecord.get("Tax Regime Name");
				String taxRegimeCode = csvRecord.get("Tax Regime Code");
				String countryTax = csvRecord.get("Country");

				VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
				VertexLogger.log("---------------");
				VertexLogger.log("Business Unit ID : " + taxRegimeName);
				VertexLogger.log("Business Unit Name : " + taxRegimeCode);
				VertexLogger.log("Country : " + countryTax);
				VertexLogger.log("---------------\n\n");

				if ( countryTax.contains(buCountryNameSelectBox.getText()) )
				{
					flagCountryMatch = true;
					break;
				}
			}
			if ( flagCountryMatch )
			{
				flagExportToCSVTaxRegimesBu = true;
			}
			else
			{
				flagExportToCSVTaxRegimesBu = false;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( CSV_FILE_PATH.delete() )
			{
				VertexLogger.log("File deleted successfully");
			}
			else
			{
				VertexLogger.log("Failed to delete the file");
			}
		}
		return flagExportToCSVTaxRegimesBu;
	}
}

