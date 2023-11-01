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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the pages in AP Scan Invoice Source tab in TaxLink UI
 *
 * @author Shilpi.Verma
 */

public class TaxLinkAPScanINVSrcPage extends TaxLinkBasePage
{

	private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();
	private WebDriverWait wait = new WebDriverWait(driver, 10);
	private Actions actions = new Actions(driver);

	private LinkedList<String> apInvSrcList = new LinkedList<String>();

	String selectedVal;
	int count;

	public TaxLinkAPScanINVSrcPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//select[@class = 'form-control dropDownSelect']")
	private WebElement apInvSrc;

	@FindBy(xpath = "//label[contains(text(), 'Start Date')]/following-sibling::div/div/input")
	private WebElement startDate;

	@FindBy(xpath = "//label[contains(text(), 'End Date')]/following-sibling::div/div/input")
	private WebElement endDate;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = '0']")
	private List<WebElement> summaryTableAPScanInvSrc;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
	private List<WebElement> endDateCalendar;

	@FindBy(xpath = "//div[@class = 'notification-container']")
	WebElement errorDuplicateData;

	@FindBy(xpath = "//div[@class = 'notification__inner']/p[contains(text(), 'VTX')]/following-sibling::p")
	private WebElement errorMsg;

	@FindBy(xpath = "//input[@name='enableFlag']")
	protected WebElement enabledFlag;

	private By edit = By.xpath("//following-sibling::div[4]");
	private By clickEdit = By.xpath("//div/div/div/div/div/button[contains(text(), 'Edit')]");
	private By three_dots = By.xpath(".//following-sibling::div[4]/div/div/div/button");
	private By start_date = By.xpath(".//following-sibling::div");
	private By end_date = By.xpath(".//following-sibling::div[2]");
	private By enabled_status = By.xpath(".//following-sibling::div[3]");

	/**
	 * Method to fetch all data from Summary Table
	 * and filter out from the dropdown list of APScanInvSrc in Add page
	 *
	 * @return
	 */
	public boolean filterData( )
	{
		count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			summaryTableAPScanInvSrc.forEach(x -> apInvSrcList.add(x.getText()));
			VertexLogger.log(String.valueOf(apInvSrcList));

			new StringBuffer().append(apInvSrcList);

			int currCount = Integer.parseInt(currentPageCount.getText());
			if ( currCount < count )
			{
				actions
					.sendKeys(Keys.PAGE_DOWN)
					.perform();

				click.clickElement(nextArrow);
			}
			else
			{
				break;
			}
		}

		click.clickElement(addButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag = addViewEditPageTitle
			.getText()
			.contains("Add");

		click.clickElement(apInvSrc);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(apInvSrc, By.tagName("option")));

		List<WebElement> options = dropdown.getDropdownOptions(apInvSrc);

		LinkedList<String> ll = new LinkedList<>();

		for ( int i = 0 ; i < options.size() ; i++ )
		{
			String s1 = options
				.get(i)
				.getAttribute("value");

			String s2 = options
				.get(i)
				.getAttribute("name");

			for ( String s : apInvSrcList )
			{
				if ( s1.contains(s) || s2.contains(s) )
				{
					ll.add(s1);
					ll.add(s2);

					if ( i == options.size() )
					{
						break;
					}
				}
			}
			if ( !s1.isEmpty() && !s2.isEmpty() && !s1.isBlank() && !s2.isBlank() && (!ll.contains(s1) || !ll.contains(
				s2)) )
			{
				click.clickElement(options.get(i));

				selectedVal = options
					.get(i)
					.getText();

				ll.clear();

				break;
			}
		}

		return flag;
	}

	/**
	 * Method to add new record in AP Scan Invoice Source
	 *
	 * @return
	 */
	public boolean addAPScanInvSrc( )
	{
		boolean flag = false;

		navigateToAPScanInvSrc();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		boolean filterData_flag = filterData();

		boolean startDate_flag = startDate
			.getAttribute("value")
			.contains(LegalEntity.FIELDS.defaultStartDate);

		click.clickElement(endDate);
		endDateCalendar
			.stream()
			.filter(date -> date
				.getText()
				.equals(utils.getCurrentDate()))
			.findFirst()
			.get()
			.click();

		expWait.until(ExpectedConditions.elementToBeClickable(saveButton));
		click.clickElement(saveButton);
		wait.until(ExpectedConditions.visibilityOf(externalFilter));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		jsWaiter.sleep(2000);
		boolean selectedVal_flag = false;
		for ( int i = 1 ; i <= count ; i++ )
		{
			selectedVal_flag = summaryTableAPScanInvSrc
				.stream()
				.anyMatch(ele -> ele
					.getText()
					.contains(selectedVal));

			if ( selectedVal_flag )
			{
				break;
			}
			else
			{
				click.clickElement(nextArrow);
			}
		}

		jsWaiter.sleep(5000);
		if ( filterData_flag && startDate_flag && selectedVal_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to get first data from Summary Table
	 *
	 * @return
	 */
	public Optional<WebElement> getFirstData( )
	{
		Optional<WebElement> data = summaryTableAPScanInvSrc
			.stream()
			.findFirst();

		return data;
	}

	/**
	 * Verify edit option of AP Scan Invoice Source
	 *
	 * @return
	 */
	public boolean editAPScanInvSrc( )
	{
		boolean flag = false;

		navigateToAPScanInvSrc();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		Optional<WebElement> data = getFirstData();

		String originalData = data
			.get()
			.getText();

		WebElement editButton = data
			.get()
			.findElement(edit);

		editButton
			.findElement(clickEdit)
			.click();

		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
		boolean header_flag = addViewEditPageTitle
			.getText()
			.contains("Edit");

		boolean apInvSrc_flag = !element.isElementEnabled(apInvSrc);
		boolean startDate_flag = !element.isElementEnabled(startDate);
		boolean endDate_flag = element.isElementEnabled(endDate);
		boolean chkBox_flag = element.isElementEnabled(enabledFlag);

		click.clickElement(enabledFlag);

		if ( !chkBox_flag )
		{

			chkBox_flag = checkbox.isCheckboxChecked(enabledFlag);
		}
		else
		{
			chkBox_flag = !checkbox.isCheckboxChecked(enabledFlag);
		}

		boolean saveButton_flag = element.isElementEnabled(saveButton);

		click.clickElement(saveButton);

		jsWaiter.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		Optional<WebElement> data1 = getFirstData();

		if ( data1
			.get()
			.getText()
			.equals(originalData) )
		{
			String enabledStatus = data1
				.get()
				.findElement(enabled_status)
				.getText();

			flag = enabledStatus.equals("N") || enabledStatus.equals("Y");
		}

		if ( header_flag && apInvSrc_flag && startDate_flag && endDate_flag && chkBox_flag && saveButton_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to verify View option
	 *
	 * @return
	 */
	public boolean viewAPScanInvSrc( )
	{
		boolean flag;

		navigateToAPScanInvSrc();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		Optional<WebElement> data = getFirstData();

		data
			.get()
			.findElement(three_dots)
			.click();

		click.clickElement(viewButton);

		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));
		flag = addViewEditPageTitle
			.getText()
			.contains("View");

		flag = !element.isElementEnabled(apInvSrc);
		flag = !element.isElementEnabled(startDate);
		flag = !element.isElementEnabled(endDate);
		flag = !element.isElementEnabled(enabledFlag);

		click.clickElement(cancelButton);

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		return flag;
	}

	/**
	 * Method to enter verify error message
	 * when end date is lesser than start date
	 *
	 * @return
	 */
	public boolean negative_start_end_dateAPScanInvSrc( )
	{
		boolean flag = false;
		boolean error_flag = false;

		navigateToAPScanInvSrc();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		boolean filterData_flag = filterData();

		utils.clearTextField(startDate);
		text.enterText(startDate, utils.getFormattedDate());

		text.clickElementAndEnterText(endDate, utils.getYesterdayDate());

		click.clickElement(saveButton);

		wait.until(ExpectedConditions.visibilityOf(errorDuplicateData));
		List<WebElement> errorString = errorDuplicateData.findElements(By.xpath(".//*"));
		if ( errorString.size() > 0 )
		{
			error_flag = errorMsg
				.getText()
				.equals("End Date can't be before Start Date");
		}

		if ( filterData_flag && error_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to verify Export To CSV
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public boolean exportToCSVAPScanInvSrc( ) throws Exception
	{
		boolean flag = false;
		String formattedStartDate_CSV;
		String formattedEndDate_CSV;

		navigateToAPScanInvSrc();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> ele = summaryTableAPScanInvSrc
				.stream()
				.findFirst();

			String apInvSrc = ele
				.get()
				.getText();

			String startDate = ele
				.get()
				.findElement(start_date)
				.getText();

			String endDate = ele
				.get()
				.findElement(end_date)
				.getText();

			String enabled = ele
				.get()
				.findElement(enabled_status)
				.getText();

			String fileName = "AP_Scan_INV_Sources_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			setFluentWait(file);

			List<CSVRecord> records = parseCSVRecord(file);
			boolean header_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

			if ( !startDate.equals("") )
			{
				formattedStartDate_CSV = utils.getSummaryFormattedDate(startDate);
				VertexLogger.log("Formatted Start Date : " + formattedStartDate_CSV);
			}
			else
			{
				formattedStartDate_CSV = startDate;
			}

			if ( !endDate.equals("") )
			{
				formattedEndDate_CSV = utils.getSummaryFormattedDate(endDate);
				VertexLogger.log("Formatted End Date : " + formattedEndDate_CSV);
			}
			else
			{
				formattedEndDate_CSV = endDate;
			}
			Optional<CSVRecord> data = records
				.stream()
				.filter(rec -> rec
								   .get(0)
								   .contains(apInvSrc) && rec
								   .get(1)
								   .contains(formattedStartDate_CSV) && rec
								   .get(2)
								   .contains(formattedEndDate_CSV) && rec
								   .get(3)
								   .contains(enabled))
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
