package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains method to check in the Inv's functionality
 * according to the Inv rules created in taxlink
 * part of E2E scenario for INV rules mapping
 *
 * @author mgaikwad
 */

public class OracleCloudInvRulesMappingPage extends OracleCloudBasePage
{
	public OracleCloudInvRulesMappingPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public JavascriptExecutor js = (JavascriptExecutor) driver;

	public Actions action = new Actions(driver);
	@FindBy(xpath = "//a[contains(text(),'New Check-In')]")
	private WebElement newCheckInLink;

	@FindBy(xpath = "//h1[contains(text(),'Content Check-In Form')]")
	private WebElement headerNewCheckInForm;

	@FindBy(xpath = "//select[@name='dDocType']")
	private WebElement typeDropdownInForm;

	@FindBy(xpath = "//input[@name='dDocTitle']")
	private WebElement titleInForm;
	@FindBy(xpath = "//tr[@class='idcRowClass-dDocTitle idcRequiredEntry']/td[3]/input[@name='dDocTitle']")
	private WebElement titleInSearchForm;
	@FindBy(xpath = "//select[@name='dSecurityGroup']")
	private WebElement securityGroupInForm;
	@FindBy(xpath = "//input[@name='dDocAccount']")
	private WebElement accountName;
	@FindBy(xpath = "//input[@name='dSecurityGroup']")
	private WebElement securityGroupInSearchForm;

	@FindBy(xpath = "//input[@name='primaryFile']")
	private WebElement browseButtonInForm;

	@FindBy(xpath = "//input[@name='dRevLabel']")
	private WebElement revisionInForm;

	@FindBy(xpath = "//input[@name='dInDate']")
	private WebElement releaseDateInForm;

	@FindBy(xpath = "//input[@value='Check In']")
	private WebElement checkInButtonInForm;

	@FindBy(xpath = "//li[@id='MENU_A_SEARCH']/a[contains(text(),'Search')]")
	private WebElement searchLink;

	@FindBy(xpath = "//div[@id='MENU_A']")
	private WebElement menuRow;

	@FindBy(xpath = "//tr[@class='idcRowClass-dInDate idcRequiredEntry']/td[3]/input[@name='dInDate']")
	private WebElement fromReleaseDate;

	@FindBy(xpath = "//tr[@class='idcRowClass-dInDate idcRequiredEntry']/td[4]/input[@name='dInDate']")
	private WebElement toReleaseDate;

	@FindBy(xpath = "(//input[@value='Search'])[last()-1]")
	private WebElement searchButtonInForm;

	@FindBy(xpath = "//td[@class='xuiListContentCell_Odd']/div[@class='xuiDisplayText_Sm']")
	private WebElement displayColumnValues;

	@FindBy(xpath = "//h1[@class='underlinePageTitle']")
	private WebElement headerConfirmation;

	@FindBy(xpath = "//a[@id='menuLink_2']")
	private WebElement searchResultMenu;

	@FindBy(xpath = "//a[contains(text(),'Content Information')]")
	private WebElement contentInfo;

	@FindBy(xpath = "(//td[@class='idcFieldEntry  idcInfoEntry']/span)[last()-12]")
	private WebElement titleInResults;

	@FindBy(xpath = "(//td[@class='idcFieldEntry  idcInfoEntry']/span)[last()-10]")
	private WebElement commentsInResults;

	/**
	 * Method to check in new request
	 * in UCM server
	 *
	 * @param title
	 * @param type
	 * @param secGroup
	 * @param filePath
	 *
	 * @return boolean
	 */
	public boolean addContentCheckIn( String title, String type, String secGroup, String account, String filePath )
	{
		boolean flagCheckIn = false;
		wait.waitForElementDisplayed(newCheckInLink);

		try
		{
			click.javascriptClick(newCheckInLink);
		}
		catch ( NoSuchElementException | TimeoutException ex )
		{
			VertexLogger.log("New check-in link is not displayed", VertexLogLevel.INFO);
		}

		driver
			.switchTo()
			.frame("contentFrame");

		wait.waitForElementDisplayed(typeDropdownInForm);
		dropdown.selectDropdownByDisplayName(typeDropdownInForm, type);

		text.enterText(titleInForm, title);
		dropdown.selectDropdownByDisplayName(securityGroupInForm, secGroup);
		text.enterText(accountName, account);
		wait.waitForElementDisplayed(browseButtonInForm);
		WebElement element = driver.findElement(By.name("primaryFile"));
		jsWaiter.sleep(1000);
		element.sendKeys(filePath);

		scroll.scrollElementIntoView(checkInButtonInForm);
		click.clickElement(checkInButtonInForm);

		jsWaiter.sleep(1000);
		if ( headerConfirmation
			.getText()
			.contains("Check-In Confirmation") )
		{
			flagCheckIn = true;
		}
		return flagCheckIn;
	}

	/**
	 * Method to validate job ID for a successful check-in
	 * in UCM server
	 *
	 * @param title
	 * @param secGroup
	 *
	 * @return jobID
	 */
	public String validateJobId( String title, String secGroup )
	{
		String jobID = null;
		String[] splitString;
		jsWaiter.sleep(10000);
		driver
			.switchTo()
			.defaultContent();
		click.javascriptClick(searchLink);
		driver
			.switchTo()
			.frame("contentFrame");
		wait.waitForElementDisplayed(titleInSearchForm);
		text.enterText(titleInSearchForm, title);
		text.enterText(securityGroupInSearchForm, secGroup);
		jsWaiter.sleep(20000);
		scroll.scrollElementIntoView(searchButtonInForm);
		click.clickElement(searchButtonInForm);
		jsWaiter.sleep(500000);
		if ( searchResultMenu.isDisplayed() )
		{
			click.clickElement(searchResultMenu);
			wait.waitForElementDisplayed(contentInfo);
			click.clickElement(contentInfo);
			if ( titleInResults
				.getText()
				.equals(title) )
			{
				if ( commentsInResults
					.getText()
					.contains("JOB") )
				{
					String comment = commentsInResults.getText();
					VertexLogger.log("Comments for Job : " + comment);
					splitString = comment.split(":");
					jobID = splitString[1];
					VertexLogger.log("Job ID: " + splitString[1]);
				}
			}
		}
		return jobID;
	}
}
