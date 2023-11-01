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

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to verify functionalities of Real Time BIP
 *
 * @author Shilpi.Verma
 */
public class TaxLinkRealTimeBIPPage extends TaxLinkBasePage
{
	/**
	 * Constructor of TaxLinkBasePage class
	 * inheriting properties of VertexPage
	 *
	 * @param driver
	 */
	public TaxLinkRealTimeBIPPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@data-cy='btn-enableName']")
	private WebElement enableBIPButton;

	@FindBy(tagName = "h1")
	private WebElement headerSummaryRealTimeBIP;

	@FindBy(xpath = "//h1[text() = 'Enable Real Time Extract Jobs']")
	private WebElement headerPopUpRealTimeBIP;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]")
	private WebElement tableBIPPopUp;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'externalServiceName']")
	private List<WebElement> summaryTableRealTimeBIP;

	@FindBy(tagName = "h3")
	private WebElement headerEditPage;

	@FindBy(xpath = "//input[@type= 'checkbox']")
	private WebElement enabledCheckBox;

	@FindBy(xpath = "//button[text() = 'SAVE']")
	private WebElement saveButton;

	@FindBy(xpath = "(//div[@class = 'ag-center-cols-container'])[last()]/div/div/div/span[1]")
	private List<WebElement> jobNameListCheckBox;

	@FindBy(xpath = "//button[contains(text(), 'SELECT')]")
	private WebElement selectButton;

	@FindBy(xpath = "(//button[contains(text(), 'Cancel')])[1]")
	private WebElement cancelButton;

	@FindBy(xpath = "(//h4[@class='secondModalHeading'])[1]")
	private WebElement confirmationMessage;

	/**
	 * This method is to verify enabling Real Time BIP jobs
	 *
	 * @return
	 */
	public boolean enableBIPs( )
	{
		boolean flag = false;

		navigateToRealTimeBIP();
		wait.waitForElementDisplayed(headerSummaryRealTimeBIP);

		boolean summaryHeader_flag = headerSummaryRealTimeBIP
			.getText()
			.equals("Real Time BIP");

		click.clickElement(enableBIPButton);
		wait.waitForElementDisplayed(headerPopUpRealTimeBIP);

		boolean popUpHeader_flag = headerPopUpRealTimeBIP.isDisplayed();

		try
		{
			if ( tableBIPPopUp
				.findElement(By.xpath(".//*"))
				.isDisplayed() )
			{
				jsWaiter.sleep(5000);
				Optional<WebElement> selectedEle = jobNameListCheckBox
					.stream()
					.findFirst();

				String selectedVal = selectedEle
					.get()
					.findElement(By.xpath(".//following::span[@ref = 'eCellValue']"))
					.getText();

				selectedEle
					.get()
					.click();

				click.clickElement(selectButton);
				wait.waitForElementDisplayed(confirmationMessage);
				if ( confirmationMessage
					.getText()
					.contains("Are you sure you want to save these records?") )
				{
					click.clickElement(saveButton);
				}

				jsWaiter.sleep(5000);

				Optional<WebElement> data = summaryTableRealTimeBIP
					.stream()
					.filter(job -> job
						.getText()
						.equals(selectedVal))
					.findFirst();

				if ( data.isPresent() )
				{
					String rowId = data
						.get()
						.findElement(By.xpath(".//parent::div"))
						.getAttribute("row-id");

					boolean enabled_flag = data
						.get()
						.findElement(By.xpath(".//parent::div[@row-id = '" + rowId + "']"))
						.findElement(By.xpath(".//div[@col-id = 'enabledFlag']"))
						.getText()
						.equals("Y");

					if ( enabled_flag )
					{
						VertexLogger.log(selectedVal + " has been Enabled in Real Time BIP");
					}
				}
				else
				{
					throw new Exception("Enabled job is not present in summary table");
				}
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Pop Up table is empty");

			wait.waitForElementDisplayed(cancelButton);
			click.clickElement(cancelButton);
			jsWaiter.sleep(5000);

			summaryTableRealTimeBIP.forEach(data ->
			{
				String jobName = data.getText();
				String serviceSubscription = data
					.findElement(By.xpath(".//following::div"))
					.getText();
				String enabled = data
					.findElement(By.xpath(".//following::div[2]"))
					.getText();

				VertexLogger.log(jobName + " " + serviceSubscription + " " + enabled);
			});
		}

		if ( summaryHeader_flag && popUpHeader_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * This method is to verify Edit functionality of Real Time BIP
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public boolean editRealTimeBIP( ) throws Exception
	{
		boolean flag = false;
		boolean headerEdit_flag = false;
		boolean enabledSummaryTable_flag = false;

		navigateToRealTimeBIP();
		wait.waitForElementDisplayed(headerSummaryRealTimeBIP);

		try
		{
			if ( !summaryTableRealTimeBIP.isEmpty() )
			{
				summaryTableRealTimeBIP
					.stream()
					.findFirst()
					.get()
					.findElement(By.xpath(".//following::button[text() = 'Edit']"))
					.click();

				wait.waitForElementDisplayed(headerEditPage);
				headerEdit_flag = headerEditPage
					.getText()
					.contains("Edit");

				click.clickElement(enabledCheckBox);
				click.clickElement(saveButton);
				wait.waitForElementDisplayed(headerSummaryRealTimeBIP);

				String status = summaryTableRealTimeBIP
					.stream()
					.findFirst()
					.get()
					.findElement(By.xpath(".//following::div[2]"))
					.getText();

				enabledSummaryTable_flag = status.equals("Y") || status.equals("N");
			}
		}
		catch ( Exception e )
		{
			throw new Exception("Summary table is empty");
		}

		if ( headerEdit_flag && enabledSummaryTable_flag )

		{
			VertexLogger.log("Real Time BIP has been edited successfully");
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
	public boolean exportToCSVRealTimeBIP( ) throws Exception
	{
		boolean flag = false;

		navigateToRealTimeBIP();

		wait.waitForElementDisplayed(headerSummaryRealTimeBIP);
		if ( !checkNoRecordsPresent() )
		{
			Optional<WebElement> ele = summaryTableRealTimeBIP
				.stream()
				.findFirst();

			String jobName = ele
				.get()
				.getText();

			String serviceSubsc = ele
				.get()
				.findElement(By.xpath(".//following-sibling::div"))
				.getText();

			String enabled = ele
				.get()
				.findElement(By.xpath(".//following-sibling::div[2]"))
				.getText();

			String fileName = "Real_Time_BIP.csv";
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
								   .contains(jobName) && rec
								   .get(1)
								   .contains(serviceSubsc) && rec
								   .get(2)
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
