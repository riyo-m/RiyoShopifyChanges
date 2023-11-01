package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.LegalEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
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
 * This class contains all the methods to test the pages in Profile Options tab
 *
 * @author Shilpi.Verma
 */
public class TaxLinkUserProfileOptionsPage extends TaxLinkBasePage
{
	String fileName = "ProfileOptions_Extract.csv";
	String fileDownloadPath = String.valueOf(getFileDownloadPath());
	File file = new File(fileDownloadPath + File.separator + fileName);

	public TaxLinkUserProfileOptionsPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//a[@class='rs-btn rs-btn-subtle rs-dropdown-toggle'])[2]")
	private WebElement taxCalculationSetUps;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/systemProfiles']")
	private WebElement systemProfileOptionsTab;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'profileOptionName']")
	private List<WebElement> summaryTableProfileOptions;

	@FindBy(xpath = "//select[@data-cy='profileLevelName']")
	private WebElement profileLevelName;

	@FindBy(xpath = "//select[@data-cy='profileLevelValue']")
	private WebElement profileLevelValue;

	@FindBy(xpath = "//select[@data-cy='profileValue']")
	private WebElement profileValue;

	@FindBy(xpath = "//div[@class= 'childSummaryHeader']")
	private WebElement childHeaderProfileOptionName;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='id.levelName']")
	private List<WebElement> columnsProfileLevel;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div")
	private WebElement blankTable;

	@FindBy(xpath = "//input[@name='enableFlag']")
	private WebElement enabledFlag;

	@FindBy(xpath = "//button[@data-cy='btn-cancel']")
	protected WebElement closeButton;

	/**
	 * Method to search data in different
	 * summary tables
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( List<WebElement> ele, String text )
	{
		jsWaiter.sleep(2000);
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Verify view option of Profile Options
	 *
	 * @return boolean
	 */
	public boolean viewProfileOptions( Map<String, String> map )
	{
		boolean flag = false;
		boolean header_flag = false;
		boolean paycharged_flag = false;
		boolean profLevelName_flag;
		boolean profLevValue_flag;
		boolean profValue_flag;

		VertexLogger.log(String.valueOf(file));

		navigateToProfileOptions();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		Iterator<Map.Entry<String, String>> itr = map
			.entrySet()
			.iterator();

		while ( itr.hasNext() )
		{
			Map.Entry<String, String> entry = itr.next();

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data1 = dataPresent(summaryTableProfileOptions, entry.getKey());

				if ( data1.isPresent() )
				{
					String rowId = data1
						.get()
						.findElement(By.xpath(".//parent::div"))
						.getAttribute("row-id");

					WebElement ele = data1
						.get()
						.findElement(By.xpath(".//parent::div[@row-id = '" + rowId + "']"));

					try
					{
						ele
							.findElement(By.xpath(".//div[@col-id = 'action-col']/*/*/*/div/*/button[text() = 'View']"))
							.click();
					}
					catch ( Exception e )
					{

						executeJs("window.scrollBy(0,250)", "");
						ele
							.findElement(By.xpath(".//div[@col-id = 'action-col']/*/*/*/div/*/button[text() = 'View']"))
							.click();
					}

					break;
				}
				else
				{
					click.clickElement(nextArrow);
				}
			}

			expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

			header_flag = childHeaderProfileOptionName
				.getText()
				.equals(entry.getValue());

			Optional<WebElement> profileVal = dataPresent(columnsProfileLevel, "SYSTEM");

			if ( profileVal.isPresent() )
			{
				String text = profileVal
					.get()
					.findElement(By.xpath(".//following-sibling::div[3]"))
					.getText();

				if ( entry
					.getKey()
					.contains("CHARGED") )
				{
					paycharged_flag = text.contains("PAYCHARGED");
				}

				WebElement view = profileVal
					.get()
					.findElement(By.xpath(".//following-sibling::div[4]"));
				view
					.findElement(By.xpath(".//div/div/div/div/div/button[contains(text(), 'View')]"))
					.click();

				expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

				profLevelName_flag = !element.isElementEnabled(profileLevelName);

				profLevValue_flag = !element.isElementEnabled(profileLevelValue);

				profValue_flag = !element.isElementEnabled(profileValue);

				click.clickElement(cancelButton);

				click.clickElement(closeButton);

				jsWaiter.sleep(3000);

				if ( paycharged_flag && profLevelName_flag && profLevValue_flag && profValue_flag )
				{
					VertexLogger.log("VTX_OVER_CHARGED_ACTION has all the fields correct");
				}
				else
				{
					VertexLogger.log("VTX_OVER_CHARGED_ACTION has some incorrect fields");
				}
			}
			else
			{
				click.clickElement(closeButton);

				expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

				checkPageNavigation();
			}
		}

		if ( header_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to select dropdown values
	 * from Profile Level Name and Profile Value
	 *
	 * @param s1
	 * @param s2
	 */
	public void dropdownSelection( String s1, String s2 )
	{
		dropdown.selectDropdownByDisplayName(profileLevelName, s1);

		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(profileValue, By.tagName("option")));
		jsWaiter.sleep(1000);

		dropdown.selectDropdownByDisplayName(profileValue, s2);

		click.clickElement(saveButton);
	}

	/**
	 * Method to expWait and check child element
	 * is present or not
	 */
	public void childElementWait( )
	{
		try
		{
			expWait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(blankTable, By.xpath(".//child::*")));
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method to verify table contents after saving profile data by User
	 *
	 * @param ele
	 * @param s1
	 * @param s2
	 *
	 * @return boolean
	 */
	public boolean checkTableContents( Optional<WebElement> ele, String s1, String s2 )
	{
		boolean flag = false;

		boolean s1_flag = ele
			.get()
			.findElement(By.xpath(".//following-sibling::div[2]"))
			.getText()
			.contains(s1);

		boolean s2_flag = ele
			.get()
			.findElement(By.xpath(".//following-sibling::div[3]"))
			.getText()
			.contains(s2);

		ele
			.get()
			.findElement(By.xpath(".//following-sibling::div[4]/div/div/div/div/div/button"))
			.click();

		expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		click.clickElement(enabledFlag);

		click.clickElement(saveButton);

		if ( s1_flag && s2_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to check options present in Dropdown
	 *
	 * @param ele
	 * @param e
	 */
	public void checkDropdownOptions( WebElement ele, String e )
	{
		List<WebElement> drpOptions = dropdown.getDropdownOptions(ele);
		drpOptions.forEach(x -> VertexLogger.log("Dropdown List for: " + e + "= " + x.getText()));
	}

	/**
	 * Verify edit option of AP Invoice Source
	 *
	 * @return boolean
	 */
	public boolean addAndEditProfileOptions( Map<String, String> map, String instName )
	{
		boolean flag = false;
		boolean childHeaderProfOptName_flag = false;
		boolean tableContents_flag = false;

		navigateToProfileOptions();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		Iterator<Map.Entry<String, String>> itr = map
			.entrySet()
			.iterator();

		while ( itr.hasNext() )
		{
			Map.Entry<String, String> entry = itr.next();

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresent(summaryTableProfileOptions, entry.getKey());
				if ( data.isPresent() )
				{
					String rowId = data
						.get()
						.findElement(By.xpath(".//parent::div"))
						.getAttribute("row-id");

					WebElement ele = data
						.get()
						.findElement(By.xpath(".//parent::div[@row-id = '" + rowId + "']"));

					try
					{
						ele
							.findElement(By.xpath(".//div[@col-id = 'action-col']/*/*/*/div/*/button[text() = 'View']"))
							.click();
					}
					catch ( Exception e )
					{
						executeJs("window.scrollBy(0,-250)", "");
						ele
							.findElement(By.xpath(".//div[@col-id = 'action-col']/*/*/*/div/*/button[text() = 'View']"))
							.click();
					}

					break;
				}
				else
				{
					click.clickElement(nextArrow);
				}
			}

			expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

			childHeaderProfOptName_flag = childHeaderProfileOptionName
				.getText()
				.equals(entry.getValue());

			if ( entry
				.getKey()
				.contains("VTX_OVER_CHARGED_ACTION") )
			{
				click.clickElement(addButton);

				expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

				expWait.until(ExpectedConditions.visibilityOf(profileLevelValue));
				expWait.until(ExpectedConditions.visibilityOf(profileValue));
				click.clickElement(profileValue);
				dropdownSelection("FUSION INSTANCE", "Pay Charged Tax");

				expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));

				childElementWait();

				click.clickElement(addButton);

				expWait.until(ExpectedConditions.visibilityOf(profileLevelName));
				expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(profileLevelName, By.tagName("option")));

				dropdown.selectDropdownByDisplayName(profileLevelName, "BUSINESS UNIT");

				expWait.until(
					ExpectedConditions.presenceOfNestedElementLocatedBy(profileLevelValue, By.tagName("option")));

				List<WebElement> prLevValoptions = dropdown.getDropdownOptions(profileLevelValue);

				String proLevVal = prLevValoptions
					.stream()
					.filter(x -> !x
						.getText()
						.isEmpty())
					.findFirst()
					.get()
					.getText();

				dropdown.selectDropdownByDisplayName(profileLevelValue, proLevVal);
				dropdown.selectDropdownByDisplayName(profileValue, "Short Pay Vendor");

				click.clickElement(saveButton);

				expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));
				childElementWait();

				Optional<WebElement> data = dataPresent(columnsProfileLevel, "FUSION_INSTANCE");

				if ( data.isPresent() )
				{
					tableContents_flag = checkTableContents(data, instName, "PAYCHARGED");

					expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));

					childElementWait();

					Optional<WebElement> data1 = dataPresent(columnsProfileLevel, "BUSINESS_UNIT");
					if ( data1.isPresent() )
					{
						tableContents_flag = checkTableContents(data1, proLevVal, "PAYCALCULATED");
					}

					childElementWait();

					Optional<WebElement> newData = dataPresent(columnsProfileLevel, "FUSION");

					Optional<WebElement> newData1 = dataPresent(columnsProfileLevel, "BUSINESS");

					if ( newData.isEmpty() && newData1.isEmpty() )
					{
						expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));
						click.clickElement(closeButton);
						expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

						checkPageNavigation();
					}
					else
					{
						click.clickElement(closeButton);
						expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

						checkPageNavigation();
					}
				}
			}

			else if ( entry
				.getKey()
				.contains("VTX_EXEMPT_OVERRIDE") )
			{
				click.clickElement(addButton);

				expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

				dropdownSelection("FUSION INSTANCE", "Yes");

				expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));

				childElementWait();

				Optional<WebElement> data = dataPresent(columnsProfileLevel, "FUSION_INSTANCE");
				if ( data.isPresent() )
				{
					tableContents_flag = checkTableContents(data, instName, "Y");

					expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));
					click.clickElement(closeButton);
					expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

					checkPageNavigation();
				}
			}

			else if ( entry
				.getKey()
				.contains("VTX_NONRT_EXT_SVC_ENABLED_FLG") )
			{
				expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));
				click.clickElement(addButton);

				expWait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

				checkDropdownOptions(profileLevelName, entry.getKey());
				checkDropdownOptions(profileValue, entry.getKey());

				dropdownSelection("FUSION INSTANCE", "No");

				expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));

				childElementWait();

				Optional<WebElement> data = dataPresent(columnsProfileLevel, "FUSION_INSTANCE");
				if ( data.isPresent() )
				{
					tableContents_flag = checkTableContents(data, instName, "N");

					expWait.until(ExpectedConditions.visibilityOf(childHeaderProfileOptionName));
					click.clickElement(closeButton);
					expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

					checkPageNavigation();
				}
			}
		}
		if ( childHeaderProfOptName_flag && tableContents_flag )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to create hashmap for Profile Options
	 *
	 * @return Map<String, String>
	 */
	public Map<String, String> mapValues( )
	{
		Map<String, String> profileMap = new LinkedHashMap<>();

		profileMap.put("VTX_OVER_CHARGED_ACTION", "VTX: Default Vertex Over Charged Action");
		profileMap.put("VTX_EXEMPT_OVERRIDE", "VTX: Exempt Override");
		profileMap.put("VTX_NONRT_EXT_SVC_ENABLED_FLG", "VTX: Non Realtime External Services Enabled Flag");

		return profileMap;
	}

	/**
	 * Method to check data in CSV file for User Profile Options
	 *
	 * @param map
	 *
	 * @return boolean
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_UserProfileOptions( Map<String, String> map ) throws IOException
	{
		boolean flag = false;

		navigateToProfileOptions();

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		if ( !checkNoRecordsPresent() )
		{
			click.clickElement(exportToCSVSummaryPage);
			jsWaiter.sleep(5000);
			Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
			VertexLogger.log("File downloaded");

			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withDelimiter(',')
				.withFirstRecordAsHeader());

			flag = csvParser
				.getHeaderMap()
				.containsKey(LegalEntity.FIELDS.instanceName);

			List<CSVRecord> records = csvParser.getRecords();

			Iterator<Map.Entry<String, String>> itr = map
				.entrySet()
				.iterator();

			while ( itr.hasNext() )
			{
				Map.Entry<String, String> entry = itr.next();

				Optional<CSVRecord> data = records
					.stream()
					.filter(rec -> rec
									   .get(0)
									   .contains(entry.getKey()) && rec
									   .get(2)
									   .contains(entry.getValue()))
					.findFirst();

				if ( data.isPresent() )
				{
					flag = true;
					VertexLogger.log("CSV Record Number: " + data
						.get()
						.getRecordNumber());
				}
			}
			if ( file.delete() )
			{
				VertexLogger.log("File deleted successfully");
			}
			else
			{
				VertexLogger.log("Failed to delete the file");
			}
		}
		else
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method to check data in CSV file for System Profile Options
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public boolean exportToCSV_SystemProfileOptions( ) throws Exception
	{
		boolean flag;

		expWait.until(ExpectedConditions.visibilityOf(advancedConfigurationLoc));
		click.clickElement(advancedConfigurationLoc);
		expWait.until(ExpectedConditions.visibilityOf(systemProfileOptionsTab));
		click.clickElement(systemProfileOptionsTab);

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		String profileOption = summaryTableProfileOptions
			.stream()
			.findFirst()
			.get()
			.getText();

		String profileOptionDesc = summaryTableProfileOptions
			.stream()
			.findFirst()
			.get()
			.findElement(By.xpath(".//following::div"))
			.getText();

		String profileOptionName = summaryTableProfileOptions
			.stream()
			.findFirst()
			.get()
			.findElement(By.xpath(".//following::div[2]"))
			.getText();

		String fileName = "SystemProfileOptions_Extract.csv";
		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		File file = new File(fileDownloadPath + File.separator + fileName);
		VertexLogger.log(String.valueOf(file));

		setFluentWait(file);
		List<CSVRecord> records = parseCSVRecord(file);
		flag = checkHeader(records, LegalEntity.FIELDS.instanceName);

		Optional<CSVRecord> data = records
			.stream()
			.filter(rec -> rec
							   .get(0)
							   .contains(profileOption) && rec
							   .get(1)
							   .contains(profileOptionDesc) && rec
							   .get(2)
							   .contains(profileOptionName))
			.findFirst();

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
		return flag;
	}
}


