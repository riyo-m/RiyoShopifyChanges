package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the pages in Legal Entity tab in TaxLink UI
 *
 * @Author Shilpi Verma
 */

public class TaxLinkLegalEntityPage extends TaxLinkBasePage
{
	private String leIntId;
	private List<String> dataEntered;

	public TaxLinkLegalEntityPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(text(), 'ADD')]")
	private WebElement addLegalEntityButton;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity Internal ID')]/following-sibling::input")
	private WebElement legalEntityInternalID;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity Identifier')]/following-sibling::input")
	private WebElement legalEntityIdentifier;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity Name')]/following-sibling::input")
	private WebElement legalEntityName;

	@FindBy(xpath = "//label[contains(text(), 'Start Date')]/following-sibling::div/div/input")
	private WebElement startDate;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDate;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
	private List<WebElement> endDateCalendar;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDateSelectedValue;

	@FindBy(xpath = "//div[@class = 'notification-container']")
	WebElement errorDuplicateData;

	@FindBy(xpath = "//button[contains(text(), 'Edit')]")
	List<WebElement> editButton;

	@FindBy(tagName = "h3")
	WebElement headerEditLegalEntityPage;

	@FindBy(xpath = "//*[@id='myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div[1]/div[3]")
	WebElement enabledStatus;

	@FindBy(xpath = "//button[@class='table-flyout__btn threeDot']")
	WebElement viewOption;

	@FindBy(tagName = "h1")
	private WebElement headerImportLegalEntity;

	@FindBy(xpath = "(//div/div[1]/div/div[1]/div[2]/div/div/div[1]/div[2]/div/div/span)[last()]")
	private WebElement importSelectAllCheckBox;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
	private List<WebElement> importListCheckBox;

	@FindBy(xpath = "(//h4[@class='secondModalHeading'])[last()-1]")
	private WebElement popUpImport;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCode']")
	private List<WebElement> summaryTableLegalEntity;

	@FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])[last()-1]")
	private WebElement totalPageCount;

	@FindBy(xpath = "//div[@class = 'notification__inner']/p[contains(text(), 'VTX')]/following-sibling::p")
	private WebElement errorMsg;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	/**
	 * Method for entering all the data in Add Legal Entity page
	 *
	 * @return boolean
	 */
	public boolean enterLegalEntityData( )
	{
		boolean flag;
		wait.waitForElementDisplayed(addLegalEntityButton);
		click.clickElement(addLegalEntityButton);

		boolean flag1 = text
			.getElementText(addViewEditPageTitle)
			.contains(LegalEntity.FIELDS.headerAddLegalEntity);

		leIntId = utils.randomNumber("5");
		text.enterText(legalEntityInternalID, leIntId);

		String leIden = utils.randomAlphaNumericText();
		text.enterText(legalEntityIdentifier, leIden);

		String leName = utils.randomText();
		text.enterText(legalEntityName, leName);

		boolean flag2 = checkbox.isCheckboxChecked(enabledFlag);

		click.clickElement(saveButton);

		dataEntered = Arrays.asList(leIntId, leIden, leName);

		VertexLogger.log("Data Entered is: " + dataEntered);

		if ( flag1 && flag2 )
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
	 * Method to search data in summary table of Legal Entity
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = summaryTableLegalEntity
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Verify add new record in Legal Entity
	 *
	 * @return boolean
	 */
	public boolean addLegalEntity( )
	{
		boolean flag = false;

		navigateToLegalEntity();

		String summaryPageHeader = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean summaryPageHeader_flag = summaryPageHeader.equals(LegalEntity.FIELDS.headerSummaryPage);

		boolean dataEntered_flag = enterLegalEntityData();

		wait
			.waitForElementDisplayed(summaryPageTitle)
			.isDisplayed();

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		checkPageNavigation();

		boolean newRecord_flag = false;

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCount.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(leIntId);

			if ( data.isPresent() )
			{
				VertexLogger.log("New record added in Summary Table");
				newRecord_flag = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		if ( summaryPageHeader_flag && dataEntered_flag && newRecord_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Verify Edit option of Legal Entity
	 *
	 * @return
	 */
	public boolean editLegalEntity( )
	{
		boolean flag = false;

		navigateToLegalEntity();
		wait.waitForElementDisplayed(summaryPageTitle);

		editButton
			.stream()
			.findFirst()
			.get()
			.click();

		wait.waitForElementDisplayed(headerEditLegalEntityPage);

		boolean header_flag = (headerEditLegalEntityPage.isDisplayed());

		boolean le_intID_flag = !element.isElementEnabled(legalEntityInternalID);
		boolean le_iden_flag = !element.isElementEnabled(legalEntityIdentifier);
		boolean le_name_flag = !element.isElementEnabled(legalEntityName);
		boolean le_en_flag = element.isElementEnabled(enabledFlag);

		enabledFlag.click();

		boolean chkBox_flag = checkbox.isCheckboxChecked(enabledFlag) || !checkbox.isCheckboxChecked(
			enabledFlag);
		boolean saveBtn_flag = element.isElementEnabled(saveButton);

		click.clickElement(saveButton);

		wait.waitForElementDisplayed(summaryPageTitle);

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		checkPageNavigation();
		boolean enStatus_flag = enabledStatus
									.getText()
									.equals("N") || enabledStatus
									.getText()
									.equals("Y");

		if ( header_flag && le_intID_flag && le_iden_flag && le_name_flag && le_en_flag && chkBox_flag &&
			 saveBtn_flag && enStatus_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Verify view option of Legal Entity
	 *
	 * @return
	 */

	public boolean viewLegalEntity( )
	{
		boolean flag = false;

		navigateToLegalEntity();
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(viewOption);

		click.clickElement(viewButton);

		boolean legalEntityInternalID_flag = !element.isElementEnabled(legalEntityInternalID);
		boolean legalEntityIdentifier_flag = !element.isElementEnabled(legalEntityIdentifier);
		boolean legalEntityName_flag = !element.isElementEnabled(legalEntityName);
		boolean enabledCheckBox_flag = !element.isElementEnabled(enabledFlag);
		boolean saveButton_flag = !element.isElementEnabled(saveButton);
		boolean cancelButton_flag = element.isElementEnabled(cancelButton);

		click.clickElement(cancelButton);

		if ( legalEntityInternalID_flag && legalEntityIdentifier_flag && legalEntityName_flag && enabledCheckBox_flag &&
			 saveButton_flag && cancelButton_flag )
		{
			flag = true;
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
	 * Verify import option of Legal Entity
	 *
	 * @return boolean
	 */
	public boolean importLegalEntity( )
	{
		boolean flag = false;
		boolean selectAllChckBox_flag = false;
		boolean checkBoxIterate_flag = false;
		boolean dataPresent_flag = false;

		navigateToLegalEntity();
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(importButtonOnSummaryPage);

		expWait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

		click.clickElement(importSelectAllCheckBox);
		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("checked") )
		{
			selectAllChckBox_flag = true;

			checkBoxIterate_flag = checkBoxIterate(".//span[1]", "ag-icon ag-icon-checkbox-checked");
		}

		click.clickElement(importSelectAllCheckBox);
		boolean chckBox_flag = !checkbox.isCheckboxChecked(importSelectAllCheckBox);

		if ( importSelectAllCheckBox
			.getAttribute("class")
			.contains("unchecked") )
		{
			selectAllChckBox_flag = true;

			checkBoxIterate_flag = checkBoxIterate(".//span[2]", "ag-icon ag-icon-checkbox-unchecked");
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

		click.clickElement(selectImportButton);

		expWait.until(
			ExpectedConditions.textToBePresentInElement(popUpImport, "Are you sure you want to save these records?"));

		click.clickElement(savePopUpImportButton);

		expWait.until(ExpectedConditions.visibilityOf(externalFilter));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int count = Integer.parseInt(totalPageCount.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = dataPresent(selectedVal);

			if ( data.isPresent() )
			{

				dataPresent_flag = true;
				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}

		if ( selectAllChckBox_flag && checkBoxIterate_flag && chckBox_flag && dataPresent_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to check addition of duplicate Legal Entity
	 *
	 * @return
	 */
	public boolean negative_addDuplicateLE( )
	{
		boolean flag = false;
		boolean error_flag = false;

		navigateToLegalEntity();
		wait.waitForElementDisplayed(summaryPageTitle);

		boolean enterData_flag = enterLegalEntityData();
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(addLegalEntityButton);
		wait.waitForElementDisplayed(addViewEditPageTitle);

		text.enterText(legalEntityInternalID, dataEntered.get(0));
		text.enterText(legalEntityIdentifier, dataEntered.get(1));
		text.enterText(legalEntityName, dataEntered.get(2));

		click.clickElement(saveButton);

		wait.waitForElementDisplayed(errorDuplicateData);
		List<WebElement> errorString = errorDuplicateData.findElements(By.xpath(".//*"));
		if ( errorString.size() > 0 )
		{
			error_flag = errorMsg
				.getText()
				.equals("Records already exists");
		}

		if ( enterData_flag && error_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Verify Export to CSV
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	public boolean exportToCSVLegalEntity( ) throws Exception
	{
		boolean flag = false;

		navigateToLegalEntity();

		wait.waitForElementDisplayed(summaryPageTitle);
		if ( !checkNoRecordsPresent() )
		{
			String idText = summaryTableLegalEntity
				.stream()
				.findFirst()
				.get()
				.getText();

			String fileName = "LegalEntity_Extract.csv";
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
								   .contains("VTX_LEGAL_ENTITIES_LIST") && rec
								   .get(1)
								   .contains(idText))
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
				flag = true;
			}
		}
		else
		{
			flag = true;
		}
		return flag;
	}
}
