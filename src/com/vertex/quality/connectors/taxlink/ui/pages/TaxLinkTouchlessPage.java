package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

/**
 * This class contains all the methods to test the pages in Profile Options tab and touchless Resubmission
 *
 * @author Aman Jain
 */
public class TaxLinkTouchlessPage extends TaxLinkBasePage
{
	WebDriverWait wait = new WebDriverWait(driver, 10);

	public TaxLinkTouchlessPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//a[@class='rs-btn rs-btn-subtle rs-dropdown-toggle'])[2]")
	private WebElement taxCalculationSetUps;

	@FindBy(xpath = "//a[@data-cy= 'system-profiles-module']")
	private WebElement profileOptionsTab;

	@FindBy(tagName = "h1")
	private WebElement headerProfileOptionsPage;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'profileOptionName']")
	private List<WebElement> summaryTableProfileOptions;

	@FindBy(xpath = "//label[contains(text(), 'Profile Level Name')]/following-sibling::select")
	private WebElement profileLevelName;

	@FindBy(xpath = "//label[contains(text(), 'Profile Level Value')]/following-sibling::select")
	private WebElement profileLevelValue;

	@FindBy(xpath = "//label[contains(text(), 'Profile Value')]/following-sibling::*")
	private WebElement profileValue;

	@FindBy(tagName = "h1")
	private WebElement headerProfileOptionValues;

	@FindBy(tagName = "h3")
	private WebElement headerViewProfileOptions;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='id.levelName']")
	private List<WebElement> columnsProfileLevel;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div")
	private WebElement blankTable;

	@FindBy(xpath = "//a[@data-cy= 'Retry-Jobs-module']")
	protected WebElement retryButton;

	@FindBy(xpath = "//a[@data-cy = 'advancedConfiguration']")
	WebElement advancedConfigurationTab;

	/**
	 * Method for navigation to Profile Options tab
	 */
	public void navigateToProfileOptions( )
	{
		wait.until(ExpectedConditions.visibilityOf(advancedConfigurationTab));
		click.clickElement(advancedConfigurationTab);
		wait.until(ExpectedConditions.visibilityOf(profileOptionsTab));
		click.clickElement(profileOptionsTab);
	}

	/**
	 * Method to search data in different
	 * summary tables
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresent( List<WebElement> ele, String text )
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
	 * Verify view option of Profile Options
	 *
	 * @return boolean
	 */
	public boolean viewProfileOptions( Map<String, String> map )
	{
		boolean flag = false;
		boolean touchless_flag = false;
		boolean profLevelName_flag = false;
		boolean profLevValue_flag = false;
		boolean profValue_flag = false;

		navigateToProfileOptions();
		wait.until(ExpectedConditions.visibilityOf(headerProfileOptionsPage));

		Iterator<Map.Entry<String, String>> itr = map
			.entrySet()
			.iterator();

		while ( itr.hasNext() )
		{
			Map.Entry<String, String> entry = itr.next();
			jsWaiter.sleep(1000);
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

			wait.until(ExpectedConditions.visibilityOf(headerProfileOptionValues));
			jsWaiter.sleep(1000);

			List<WebElement> rowPresent = blankTable.findElements(By.xpath(".//child::*"));
			if ( rowPresent.size() > 0 )
			{
				Optional<WebElement> profileVal = dataPresent(columnsProfileLevel, "FUSION_INSTANCE"); // FUSION_INSTANCE
				String actionType = "View";
				if ( profileVal.isPresent() )
				{
						actionType = "Edit";

					String text = profileVal
						.get()
						.findElement(By.xpath(".//following-sibling::div[3]"))
						.getText();
					if (text.equalsIgnoreCase("Y") )
					{
						touchless_flag = true;
					}
					else{
						touchless_flag = false;
					}
					WebElement view = profileVal
						.get()
						.findElement(By.xpath(".//following-sibling::div[4]"));
					view.findElement(By.xpath(
							".//div/div/div/div/div/button[contains(text(), "+actionType+")]")) // view for System and Edit for FusionInstance
						.click();

					wait.until(ExpectedConditions.visibilityOf(headerViewProfileOptions));

					profLevelName_flag = !element.isElementEnabled(profileLevelName);

					profLevValue_flag = !element.isElementEnabled(profileLevelValue);

					profValue_flag = !element.isElementEnabled(profileValue);

					click.clickElement(cancelButton);

					click.clickElement(closeButton);

					jsWaiter.sleep(3000);

					if ( touchless_flag && profLevelName_flag && profLevValue_flag && profValue_flag )
					{
						VertexLogger.log("VTX ENABLE TOUCHLESS_FLAG has all the fields correct");
					}
				}else
				{
					Optional<WebElement> profileValSystem = dataPresent(columnsProfileLevel, "SYSTEM");
					if (profileValSystem.isPresent() ){
						touchless_flag = true;
					}
				}
				wait.until(ExpectedConditions.visibilityOf(headerProfileOptionValues));

				checkPageNavigation();
			}
			else
			{
				click.clickElement(closeButton);

				wait.until(ExpectedConditions.visibilityOf(headerProfileOptionsPage));
				jsWaiter.sleep(1000);

				checkPageNavigation();
			}
		}

		if ( touchless_flag )
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
		profileMap.put("VTX_ENABLE_TOUCHLESS", "VTX:Enable Touchless Resubmission");
		return profileMap;
	}

	/**
	 * Method to verify Retry Jobs button enabled or disabled in Taxlink UI
	 * return boolean
	 * @param retryJobs
	 */
	public boolean verifyRetryJobsButton( TaxLinkMonitoringPage retryJobs ) throws InterruptedException
	{
		boolean touchlessFlag = false;
		boolean profileOptnFlag = viewProfileOptions(mapValues());
		if(profileOptnFlag)
		{
			touchlessFlag = checkHiddenRetryButton();
		}
		if(!touchlessFlag) {
			navigateToRetryJobs();
			retryJobs.verifyCommonRecords();
			retryJobs.verifyRecordsAfterAddingBu();
		}
		return touchlessFlag;
	}

	/**
	 * Navigate to Retry Jobs page in Taxlink UI
	 */
	public void navigateToRetryJobs( ) throws InterruptedException
	{
		jsWaiter.sleep(500);
		clickOnAdvancedConfigDropdown();
		jsWaiter.sleep(500);
	}

	/**
	 * Method to verify hidden Retry Jobs button when touchless is enabled in Taxlink UI
	 * return boolean
	 */
	public boolean checkHiddenRetryButton( )
	{
		try
		{
			driver.findElement(By.xpath("//a[@data-cy= 'Retry-Jobs-module']"));
			return false;
		}
		catch ( org.openqa.selenium.NoSuchElementException e )
		{
			return true;
		}
	}

	/**
	 * click on the Advanced Configuration dropdown
	 * in Taxlink Application
	 */
	public void clickOnAdvancedConfigDropdown( )
	{
		expWait.until(ExpectedConditions.visibilityOf(advancedConfigurationLoc));
		click.clickElement(advancedConfigurationLoc);
	}

}


