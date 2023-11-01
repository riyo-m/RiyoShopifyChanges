package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.TaxLinkDatabase;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.APInvoiceSource;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * This class contains all the methods to test the pages in Offline BIP Extract Job tab in TaxLink UI
 *
 * @author Shilpi.Verma
 */

public class TaxLinkOfflineBIPExtractJobsPage extends TaxLinkBasePage
{

	private String dayName;

	public TaxLinkOfflineBIPExtractJobsPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(text(), 'Offline BIP Extract Jobs')]")
	private WebElement headerSummaryOfflineBIP;

	@FindBy(tagName = "h1")
	private WebElement headerJobPage;

	@FindBy(xpath = "//h1[contains(text(), 'Enable Offline Extract Jobs')]")
	private WebElement headerPopUp;

	@FindBy(xpath = "//button[@data-cy='btn-enableName']")
	private WebElement enableBIPButton;

	@FindBy(xpath = "//label[contains(text(), 'Frequency')]/following-sibling::div/select")
	private WebElement frequencyDropdown;

	@FindBy(xpath = "//input[@name='incValue']")
	private WebElement hoursField;

	@FindBy(xpath = "//input[@name='incValueTwiceDay']")
	private WebElement hoursField2;

	@FindBy(xpath = "//input[@name='incValue2']")
	private WebElement minutesField;

	@FindBy(xpath = "//input[not(@data-cy)][@type='number']")
	private WebElement minutesField2;

	@FindBy(xpath = "//select[@name='AMPM']")
	private WebElement amPMDropdown;

	@FindBy(xpath = "//select[@name='AMPMTwiceDay']")
	private WebElement amPMDropdown2;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'userBatchJobName']")
	private List<WebElement> summaryTableBIP;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'id.PO_SUPPLIER_ID']")
	private List<WebElement> summaryJobDetails;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
	private WebElement enableBIPPopUP;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]")
	private WebElement emptyBIPPopUp;

	@FindBy(xpath = "//button[contains(text(), 'SELECT')]")
	private WebElement selectButton;

	@FindBy(xpath = "(//h4[@class='secondModalHeading'])[1]")
	private WebElement confirmationMessage;

	@FindBy(xpath = "//button[contains(text(), 'SAVE')]")
	private WebElement savePopUpButton;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[2]")
	private WebElement jobNamePopUp;

	@FindBy(xpath = "(//button[contains(text(), 'Cancel')])[1]")
	private WebElement cancelButton;

	@FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])[last()-1]")
	private WebElement totalPageCount;

	@FindBy(tagName = "h6")
	WebElement headerFrequencyPopUp;

	@FindBy(xpath = "//button[contains(text(), 'Confirm')]")
	WebElement confirmButton;

	@FindBy(xpath = "(//h4[@class='secondModalHeading'])[3]")
	WebElement editConfirmMsg;

	@FindBy(xpath = "//label[contains(text(), 'Day')]/following-sibling::div/div")
	List<WebElement> checkBoxList;

	@FindBy(xpath = "//button[@data-cy = 'threeDot']")
	private WebElement three_dots;

	@FindBy(xpath = "//button[contains(text(), 'Status')]")
	private WebElement status;

	@FindBy(xpath = "//button[contains(text(), 'Details')]")
	private WebElement details;

	@FindBy(xpath = "//input[@data-cy = 'Supplier_Type']")
	private WebElement supplierType;

	@FindBy(xpath = "//button[contains(text(), 'Search')]")
	private WebElement searchButton;

	@FindBy(xpath = "//span[contains(text(), 'No Rows To Show')]")
	private WebElement blankTable;

	@FindBy(xpath = "//span[contains(text(), 'Supplier_Id')]")
	private WebElement firstCol_JobDetails;

	@FindBy(xpath = "//button[text() = 'Filter by Date']")
	private WebElement filterDateButton;

	@FindBy(xpath = "//div[contains(@class, 'Modal')]/div/div")
	private WebElement filterPopUp;

	@FindBy(xpath = "//label[contains(text(), 'Days')]/following::select[contains(@data-cy, 'days-dropdown')]")
	private WebElement daysDropDown;

	@FindBy(xpath = "//label[contains(text(), 'Processed Date')]/following::select[contains(@data-cy, 'afterBefore-dropdown')]")
	private WebElement afBfDropDown;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'processTimestamp']")
	private List<WebElement> summaryTableFilter;

	@FindBy(xpath = "//button[contains(text(), 'Filter')]")
	private WebElement filterButton;

	@FindBy(xpath = "(//span[contains(@ref, 'lbTotal')])")
	private WebElement filterPageCount;

	@FindBy(xpath = "//input[contains(@class, 'start-date')]")
	private WebElement dateField;

	@FindBy(xpath = "//div[contains(@class, 'today')]")
	private WebElement firstDay;

	@FindBy(xpath = "//button[contains(text(), 'Reset')]")
	private WebElement resetButton;

	@FindBy(xpath = "//button[contains(@data-cy, 'modal')][contains(text(), 'Cancel')]")
	private WebElement filterPopUpCancelButton;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div/following::div[(contains(@class, 'today'))]")
	private WebElement today;

	@FindBy(xpath = "//input[@name = 'twiceDay']")
	private WebElement twiceDayCheckBox;

	By secondColumn = By.xpath(".//following-sibling::div[1]");
	By thirdColumn = By.xpath(".//following-sibling::div[2]");
	By fourthColumn = By.xpath(".//following-sibling::div[3]");
	By fifthColumn = By.xpath(".//following-sibling::div[4]");

	/**
	 * Method to search data in summary table of Offline BIP
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( String text )
	{
		Optional<WebElement> dataFound = summaryTableBIP
			.stream()
			.filter(col -> col
				.getText()
				.equals(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Method to check Enabled status of BIP jobs
	 *
	 * @param ele
	 * @param s
	 *
	 * @return
	 */
	public boolean checkEnabledText( Optional<WebElement> ele, String s )
	{
		boolean flag = false;

		String enabledText = ele
			.get()
			.findElement(By.xpath(".//following-sibling::div[3]"))
			.getText();

		if ( enabledText.contains(s) )
		{
			flag = true;
			VertexLogger.log("Data is present in Summary Table");
		}

		return flag;
	}

	/**
	 * Method to select BIP job from pop up
	 */
	public boolean enableBIPs( )
	{
		AtomicBoolean flag = new AtomicBoolean(false);

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		click.clickElement(enableBIPButton);
		wait.waitForElementDisplayed(headerPopUp);

		try
		{
			if ( enableBIPPopUP.isDisplayed() )
			{
				click.clickElement(enableBIPPopUP);
				String selectedVal = jobNamePopUp.getText();

				click.clickElement(cancelButton);
				wait.waitForElementDisplayed(headerSummaryOfflineBIP);

				int count = Integer.parseInt(totalPageCount.getText());

				for ( int i = 1 ; i <= count ; i++ )
				{
					Optional<WebElement> data = dataPresent(selectedVal);

					if ( data.isPresent() )
					{
						flag.set(checkEnabledText(data, "N"));
						if ( flag.get() )
						{
							VertexLogger.log("The selected BIP job status in 'N' in the summary table");
						}
						else
						{
							VertexLogger.log("The selected BIP job status is incorrect in the summary table");
						}
						break;
					}
					else
					{
						click.clickElement(nextArrow);
					}
				}

				click.clickElement(enableBIPButton);
				wait.waitForElementDisplayed(headerPopUp);

				click.clickElement(enableBIPPopUP);
				String selectedVal1 = jobNamePopUp.getText();
				click.clickElement(selectButton);
				wait.waitForElementDisplayed(confirmationMessage);
				if ( confirmationMessage
					.getText()
					.contains("Are you sure you want to enable these BIPs") )
				{
					click.clickElement(savePopUpButton);
				}
				wait.waitForElementDisplayed(headerSummaryOfflineBIP);

				for ( int j = 1 ; j <= count ; j++ )
				{
					Optional<WebElement> data1 = dataPresent(selectedVal1);

					if ( data1.isPresent() )
					{
						flag.set(checkEnabledText(data1, "Y"));
						if ( flag.get() )
						{
							VertexLogger.log("The selected BIP job status in 'Y' in the summary table");
						}
						else
						{
							VertexLogger.log("The selected BIP job status is incorrect in the summary table");
						}

						break;
					}
					else
					{
						click.clickElement(nextArrow);
					}
				}
			}
		}
		catch ( Exception e )

		{
			VertexLogger.log("Enable BIP pop up is empty");
		}
		return flag.get();
	}

	/**
	 * Generate random number 1-12 for Hour field
	 * Generate random number 0-59 for Minutes field
	 *
	 * @return
	 */
	public List<String> generateFrequency( )
	{
		int randHour = IntStream
			.generate(( ) -> (int) (Math.random() * 12) + 1)
			.limit(1)
			.findAny()
			.getAsInt();

		int randMin = IntStream
			.generate(( ) -> (int) (Math.random() * 60))
			.limit(1)
			.findAny()
			.getAsInt();

		List<String> freq = Arrays.asList(String.valueOf(randHour), String.valueOf(randMin));
		return freq;
	}

	/**
	 * Method to change frequency of BIP jobs
	 *
	 * @param freqType
	 * @param amPM
	 *
	 * @return
	 */
	public List<String> changeFrequency( String freqType, String amPM )
	{
		click.clickElement(frequencyDropdown);
		dropdown.selectDropdownByDisplayName(frequencyDropdown, freqType);

		List<String> rand = generateFrequency();

		text.enterText(hoursField, rand.get(0));
		text.enterText(minutesField, rand.get(1));

		dropdown.selectDropdownByDisplayName(amPMDropdown, amPM);

		return rand;
	}

	/**
	 * Method Overloading------
	 * Method to get first data from Summary Table
	 *
	 * @return
	 */
	public String dataPresent( )
	{
		Optional<WebElement> data1 = summaryTableBIP
			.stream()
			.findFirst();

		String schedule = data1
			.get()
			.findElement(By.xpath(".//following-sibling::div[2]"))
			.getText();

		return schedule;
	}

	/**
	 * Method to get Text data
	 * and click on Edit button in summary table
	 *
	 * @return List<String>
	 */
	public List<String> editClick( )
	{

		Optional<WebElement> data = summaryTableBIP
			.stream()
			.findFirst();

		String jobName = data
			.get()
			.getText();

		String schedule = data
			.get()
			.findElement(By.xpath(".//following-sibling::div[2]"))
			.getText();

		data
			.get()
			.findElement(By.xpath(".//following-sibling::div[5]/div/div/div/div/div/button"))
			.click();

		List<String> list = Arrays.asList(jobName, schedule);

		return list;
	}

	/**
	 * Method to split
	 *
	 * @param list
	 *
	 * @return
	 */
	public List<String> getTime( List<String> list )
	{
		String[] arr = list
			.get(1)
			.split(" ");
		String[] timeArr = arr[1].split(":");
		String hour = timeArr[0];
		String minute = timeArr[1];

		List<String> timeList = Arrays.asList(hour, minute);

		return timeList;
	}

	/**
	 * Method to edit BIPs and set the frequency type
	 * to Daily
	 *
	 * @return
	 */
	public boolean editDailyFrequencyBIPs( )
	{
		boolean flag = false;
		boolean msgAM_flag = false;
		boolean msgPM_flag = false;

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		List<String> list = editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		boolean header_freq_flag = headerFrequencyPopUp
									   .getText()
									   .contains(list.get(0)) && headerFrequencyPopUp
									   .getText()
									   .contains(list.get(1));

		String hour = getTime(list).get(0);
		String minute = getTime(list).get(1);

		boolean hour_flag = hoursField
			.getAttribute("value")
			.equals(hour);
		boolean minute_flag = minutesField
			.getAttribute("value")
			.equals(minute);

		List<String> amNum = changeFrequency("Daily", "AM");
		VertexLogger.log("Hour entered for AM: " + amNum.get(0));
		VertexLogger.log("Minutes entered for AM: " + amNum.get(1));

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(editConfirmMsg);

		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgAM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);
		String newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for AM: " + newSchedule);

		boolean scheduleAM_flag = newSchedule.contains(amNum.get(0)) && newSchedule.contains(amNum.get(1)) &&
								  newSchedule.contains("AM");

		editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		List<String> pmNum = changeFrequency("Daily", "PM");
		VertexLogger.log("Hour entered for PM: " + pmNum.get(0));
		VertexLogger.log("Minutes entered for PM: " + pmNum.get(1));

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(editConfirmMsg);

		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgPM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);

		newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for PM: " + newSchedule);

		boolean schedulePM_flag = newSchedule.contains(pmNum.get(0)) && newSchedule.contains(pmNum.get(1)) &&
								  newSchedule.contains("PM");

		if ( header_freq_flag && hour_flag && minute_flag && msgAM_flag && scheduleAM_flag && msgPM_flag &&
			 schedulePM_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to enter hours and minutes for twice a day option
	 *
	 * @param s1
	 * @param s2
	 * @param s3
	 *
	 * @return
	 */
	public boolean enterFrequency( String s1, String s2, String s3 )
	{
		wait.waitForElementDisplayed(hoursField2);

		text.enterText(hoursField2, s1);
		String minTwiceADay = minutesField2.getAttribute("value");
		boolean minField_flag = minTwiceADay.equals(s2);

		dropdown.selectDropdownByDisplayName(amPMDropdown2, s3);
		VertexLogger.log("Hour entered for Twice a Day " + s3 + ": " + s1);
		VertexLogger.log("Minutes present for Twice a Day " + s3 + ": " + minTwiceADay);

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(editConfirmMsg);

		return minField_flag;
	}

	/**
	 * Method to verify twice a day for Daily BIP job
	 *
	 * @return
	 */
	public boolean editDailyRunTwiceADay( )
	{
		boolean flag = false;
		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		List<String> list = editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		List<String> amNum = changeFrequency("Daily", "AM");
		if ( !twiceDayCheckBox.isSelected() )
		{
			click.clickElement(twiceDayCheckBox);
		}
		VertexLogger.log("Hour entered for AM: " + amNum.get(0));
		VertexLogger.log("Minutes entered for AM: " + amNum.get(1));

		boolean twiceAM_flag = false;
		List<String> freqAM = generateFrequency();
		if ( freqAM
			.get(0)
			.equals(hoursField.getAttribute("value")) )
		{
			String amHour = String.valueOf(Integer.parseInt(freqAM.get(0)) - 1);
			twiceAM_flag = enterFrequency(amHour, amNum.get(1), "AM");
		}
		else
		{
			twiceAM_flag = enterFrequency(freqAM.get(0), amNum.get(1), "AM");
		}

		boolean msgAM_flag = false;
		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgAM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);

		String newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for AM: " + newSchedule);

		boolean scheduleAM_flag = newSchedule.contains(freqAM.get(0)) && newSchedule.contains(amNum.get(1)) &&
								  newSchedule.contains("AM");

		editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		List<String> pmNum = changeFrequency("Daily", "PM");
		VertexLogger.log("Hour entered for PM: " + pmNum.get(0));
		VertexLogger.log("Minutes entered for PM: " + pmNum.get(1));

		boolean twicePM_flag = false;
		List<String> freqPM = generateFrequency();
		if ( freqPM
			.get(0)
			.equals(hoursField.getAttribute("value")) )
		{
			String pmHour = String.valueOf(Integer.parseInt(freqPM.get(0)) - 1);
			twicePM_flag = enterFrequency(pmHour, pmNum.get(1), "PM");
		}
		else
		{
			twicePM_flag = enterFrequency(freqPM.get(0), pmNum.get(1), "PM");
		}

		boolean msgPM_flag = false;
		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgPM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);

		newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for PM: " + newSchedule);

		boolean schedulePM_flag = newSchedule.contains(freqPM.get(0)) && newSchedule.contains(pmNum.get(1)) &&
								  newSchedule.contains("PM");

		if ( twiceAM_flag && msgAM_flag && msgPM_flag && scheduleAM_flag && twicePM_flag && schedulePM_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to edit BIPs and set the frequency type
	 * to Weekly
	 *
	 * @return
	 */
	public boolean editWeeklyFrequencyBIPs( )
	{
		boolean flag = false;
		boolean msgAM_flag = false;
		boolean msgPM_flag = false;
		boolean scheduleAM_flag = false;
		boolean schedulePM_flag = false;

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		List<String> list = editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		boolean header_freq_flag = headerFrequencyPopUp
									   .getText()
									   .contains(list.get(0)) && headerFrequencyPopUp
									   .getText()
									   .contains(list.get(1));

		String hour = getTime(list).get(0);
		String minute = getTime(list).get(1);

		boolean hour_flag = hoursField
			.getAttribute("value")
			.equals(hour);
		boolean minute_flag = minutesField
			.getAttribute("value")
			.equals(minute);

		List<String> amNum = changeFrequency("Weekly", "AM");
		VertexLogger.log("Hour entered for AM: " + amNum.get(0));
		VertexLogger.log("Minutes entered for AM: " + amNum.get(1));

		checkBoxList.forEach(ele ->
		{
			if ( !ele.isSelected() )
			{
				ele.click();
			}
		});

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(editConfirmMsg);

		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgAM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);

		String newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for AM: " + newSchedule);

		dayName = newSchedule.split(",")[1];
		if ( newSchedule.contains(dayName) )
		{
			if ( newSchedule.contains(amNum.get(0)) && newSchedule.contains(amNum.get(1)) && newSchedule.contains(
				"AM") )
			{
				scheduleAM_flag = true;
			}
		}

		editClick();
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		List<String> pmNum = changeFrequency("Weekly", "PM");
		VertexLogger.log("Hour entered for PM: " + pmNum.get(0));
		VertexLogger.log("Minutes entered for PM: " + pmNum.get(1));

		checkBoxList.forEach(ele ->
		{
			if ( ele.isSelected() )
			{
				ele.click();
			}
		});

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(editConfirmMsg);

		if ( editConfirmMsg
			.getText()
			.contains(list.get(0) + " on Enable Offline Extract Jobs will be change the new schedule job time") )
		{
			msgPM_flag = true;
			click.clickElement(confirmButton);
		}

		jsWaiter.sleep(2000);

		newSchedule = dataPresent();
		VertexLogger.log("Value in summary table for PM: " + newSchedule);

		dayName = newSchedule.split(",")[1];
		if ( newSchedule.contains(dayName) )
		{
			if ( newSchedule.contains(pmNum.get(0)) && newSchedule.contains(pmNum.get(1)) && newSchedule.contains(
				"PM") )
			{
				schedulePM_flag = true;
			}
		}

		if ( header_freq_flag && hour_flag && minute_flag && msgAM_flag && scheduleAM_flag && msgPM_flag &&
			 schedulePM_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to iterate to #Days dropdown in Filter status pop up
	 *
	 * @param processedDate
	 */
	public void iterateDays_FilterPopUP( String processedDate )
	{
		List<WebElement> daysList = dropdown.getDropdownOptions(daysDropDown);
		daysList.remove(0);
		daysList.forEach(day ->
		{
			day.click();
			click.clickElement(filterButton);
			wait.waitForElementDisplayed(filterPageCount);

			int count = Integer.parseInt(filterPageCount.getText());
			if ( count > 0 )
			{
				for ( int i = 0 ; i < count ; i++ )
				{
					VertexLogger.log(processedDate + " " + day.getAttribute("value") + "days ===========");
					summaryTableFilter.forEach(ele ->
					{
						String processDate = ele.getText();
						String statusCode = ele
							.findElement(secondColumn)
							.getText();
						String statusReason = ele
							.findElement(thirdColumn)
							.getText();

						VertexLogger.log(processDate + " - " + statusCode + " - " + statusReason);
					});
				}
			}
			else
			{
				VertexLogger.log("Data not present for " + processedDate + " " + day.getAttribute("value") + "days");
			}

			click.clickElement(filterDateButton);
			wait.waitForElementDisplayed(filterPopUp);
			if ( day
				.getAttribute("value")
				.equals("90") )
			{
				click.clickElement(resetButton);
				wait.waitForElementDisplayed(headerJobPage);
			}
		});
	}

	/**
	 * Method to check Status of BIP job by filtering dates
	 *
	 * @return
	 */
	public boolean statusBIPS( )
	{
		boolean flag = false;

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		String jobName = summaryTableBIP
			.stream()
			.findFirst()
			.get()
			.getText();

		click.clickElement(three_dots);
		wait.waitForElementDisplayed(status);

		click.clickElement(status);
		wait.waitForElementDisplayed(headerJobPage);

		boolean headerText_flag = headerJobPage
			.getText()
			.equals(jobName + " Status");

		click.clickElement(filterDateButton);
		wait.waitForElementDisplayed(filterPopUp);

		boolean filterJobName_flag = filterPopUp
			.getText()
			.equals("Filter " + jobName + " based on number of days before or after the processed date.");

		click.clickElement(startDate);
		click.clickElement(firstDay);

		iterateDays_FilterPopUP("After");
		click.clickElement(filterPopUpCancelButton);
		click.clickElement(filterDateButton);
		wait.waitForElementDisplayed(filterPopUp);
		dropdown.selectDropdownByDisplayName(afBfDropDown, "Before");

		click.clickElement(startDate);
		click.clickElement(today);

		iterateDays_FilterPopUP("Before");

		if ( headerText_flag && filterJobName_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to change status of BIP job
	 * Enabled/Disabled
	 *
	 * @return
	 */
	public boolean changeStatus( )
	{
		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		click.clickElement(editButton);
		wait.waitForElementDisplayed(headerFrequencyPopUp);

		click.clickElement(enabledFlag);
		boolean checked = false;
		if ( enabledFlag.isSelected() )
		{
			checked = true;
			VertexLogger.log("Enabled check box is checked");
		}
		else
		{
			VertexLogger.log("Enabled check box is unchecked");
		}

		driver
			.manage()
			.window()
			.setSize(new Dimension(1920, 1080));

		click.clickElement(saveButton);
		wait.waitForElementDisplayed(confirmButton);

		click.clickElement(confirmButton);
		jsWaiter.sleep(2000);

		String enabled = summaryTableBIP
			.stream()
			.findFirst()
			.get()
			.findElement(fourthColumn)
			.getText();

		boolean enabled_statusFlag;
		if ( checked )
		{
			enabled_statusFlag = enabled.equals("Y");
			VertexLogger.log("Enabled status is 'Y'");
		}
		else
		{
			enabled_statusFlag = enabled.equals("N");
			VertexLogger.log("Enabled status is 'N'");
		}

		return enabled_statusFlag;
	}

	/**
	 * Method to search BIP job data
	 * and compare the summary table with DB data
	 *
	 * @return
	 */
	@SneakyThrows
	public boolean searchBIPS( )
	{
		boolean final_flag = false;
		boolean po_flag;
		boolean pn_flag;
		boolean vn_flag;
		boolean vNum_flag;

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		int rowId = 0;
		int pageCount = Integer.parseInt(totalPageCount.getText());
		for ( int i = 1 ; i <= pageCount ; i++ )
		{
			Optional<WebElement> data = dataPresent("PO_SUPPLIER BIP Job");

			if ( data.isPresent() )
			{
				rowId = Integer.parseInt(data
					.get()
					.findElement(By.xpath("./.."))
					.getAttribute("row-id"));

				rowId++;

				break;
			}
			else
			{
				click.clickElement(nextArrow);
			}
		}

		By poSupplierStatus = By.xpath("(" + "//button[@data-cy = 'threeDot']" + ")" + "[" + rowId + "]");
		click.clickElement(poSupplierStatus);
		click.clickElement(details);
		wait.waitForElementDisplayed(headerJobPage);

		text.enterText(supplierType, "supplier");
		click.clickElement(searchButton);

		try
		{
			wait.waitForElementDisplayed(firstCol_JobDetails);
			if ( blankTable.isDisplayed() )
			{
				VertexLogger.log("No search results found");
				final_flag = true;
			}
		}
		catch ( Exception e )
		{
			TaxLinkDatabase dbPage = new TaxLinkDatabase();
			LinkedList<String> po_supplierID = dbPage
				.db_searchSupplierBIPJob()
				.get(0);
			LinkedList<String> partyNumber = dbPage
				.db_searchSupplierBIPJob()
				.get(1);
			LinkedList<String> vendorName = dbPage
				.db_searchSupplierBIPJob()
				.get(2);
			LinkedList<String> vendorNumber = dbPage
				.db_searchSupplierBIPJob()
				.get(3);

			int count = Integer.parseInt(filterPageCount.getText());
			for ( int i = 0 ; i < count ; i++ )
			{
				po_flag = summaryJobDetails
					.stream()
					.allMatch(cl -> po_supplierID
						.stream()
						.anyMatch(po -> po.equals(cl.getText())));

				for ( WebElement sc : summaryJobDetails )
				{
					String partyNum = sc
						.findElement(thirdColumn)
						.getText();

					String vendName = sc
						.findElement(fourthColumn)
						.getText();

					String vendNum = sc
						.findElement(fifthColumn)
						.getText();

					pn_flag = partyNumber
						.stream()
						.anyMatch(pn -> pn.equals(partyNum));

					vn_flag = vendorName
						.stream()
						.anyMatch(vn -> vn.equals(vendName));

					vNum_flag = vendorNumber
						.stream()
						.anyMatch(vNum -> vNum.equals(vendNum));

					if ( po_flag && pn_flag && vn_flag && vNum_flag )
					{
						final_flag = true;
					}
				}

				if ( count != Integer.parseInt(currentPageCount.getText()) )
				{
					click.clickElement(nextArrow);
				}
				else
				{
					break;
				}
			}
		}

		click.clickElement(cancelButton);
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);

		return final_flag;
	}

	/**
	 * Method to verify Export to CSV
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSVOfflineBIPS( ) throws Exception
	{
		boolean flag = false;

		navigateToOfflineBIP();
		wait.waitForElementDisplayed(headerSummaryOfflineBIP);
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> ele = summaryTableBIP
				.stream()
				.findFirst();

			String jobName = ele
				.get()
				.getText();

			String serviceDesc = ele
				.get()
				.findElement(By.xpath(".//following-sibling::div"))
				.getText();

			String schedule = ele
				.get()
				.findElement(By.xpath(".//following-sibling::div[2]"))
				.getText();

			String enabled = ele
				.get()
				.findElement(By.xpath(".//following-sibling::div[3]"))
				.getText();

			String fileName = "Offline_BIP_Jobs_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			setFluentWait(file);

			List<CSVRecord> records = parseCSVRecord(file);
			boolean header_flag = checkHeader(records, APInvoiceSource.FIELDS.instanceName);

			Optional<CSVRecord> data = records
				.stream()
				.filter(rec -> rec
								   .get(0)
								   .contains(jobName) && rec
								   .get(2)
								   .contains(serviceDesc) && rec
								   .get(4)
								   .contains(schedule) && rec
								   .get(5)
								   .contains(enabled))
				.findFirst();

			boolean data_flag = false;
			if ( data.isPresent() )
			{
				flag = true;
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

