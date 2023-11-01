package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the initial settings after logging into TaxLink UI
 *
 * @Author Shilpi Verma, Manjiri Gaikwad
 */
public class TaxLinkHomePage extends TaxLinkBasePage
{
	public TaxLinkHomePage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class = 'notification__container notification__container--alert']")
	private WebElement errorUserNotAssigned;

	@FindBy(xpath = "//div[@class = 'Select-noresults']")
	private WebElement instanceNoResult;

	@FindBy(xpath = "//span[@class='Select-arrow']")
	private WebElement instanceDropdownArrow;

	@FindBy(xpath = "//div[@class= 'Select-menu-outer']/div[@id='react-select-2--list']/div")
	private List<WebElement> instanceList;

	@FindBy(xpath = "//div[@class='Select-input']/input")
	private WebElement selectInstance;

	@FindBy(xpath = "//a[@class='rs-btn rs-btn-subtle rs-dropdown-toggle']")
	private WebElement dashboardMenu;

	@FindBy(xpath = "//ul[@class='rs-dropdown-menu rs-dropdown-menu-collapse-in']/li/a[contains(@href, 'businessUnits')]")
	private WebElement businessUnit;

	@FindBy(xpath = "//h1[contains(text(),'Oracle Cloud ERP')]")
	public WebElement homePageAvailableInstances;

	/**
	 * Verify if user role is assigned
	 *
	 * @return flag
	 */

	public boolean verifyUserRoleAssigned( )
	{
		boolean flag = false;
		try
		{
			if ( errorUserNotAssigned.isDisplayed() )
			{
				VertexLogger.log("User Role Not Assigned");
				flag = false;
			}
		}
		catch ( NoSuchElementException e )
		{

			VertexLogger.log(e.getMessage(), VertexLogLevel.INFO);
			flag = true;
		}

		return flag;
	}

	/**
	 * Verify if correct Instance is selected
	 *
	 * @param instanceValue
	 *
	 * @return flag
	 */
	public boolean selectInstance( String instanceValue )
	{
		boolean flag;
		expWait.until(ExpectedConditions.visibilityOf(instanceDropdownArrow));
		click.clickElement(instanceDropdownArrow);
		try
		{
			Thread.sleep(1000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		try
		{
			expWait.until(ExpectedConditions.visibilityOfAllElements(instanceList));
			Optional matches = instanceList
				.stream()
				.filter(inst -> inst
					.getAttribute("class")
					.equals("Select-noresults"))
				.findFirst();
			if ( matches.isPresent() )
			{
				WebElement element = (WebElement) matches.get();
				element.click();
				VertexLogger.log("No Instance is present in the Instance list");
				flag = false;
			}
			else
			{
				expWait.until(ExpectedConditions.visibilityOfAllElements(instanceList));
				Optional matching = instanceList
					.stream()
					.filter(inst -> inst
						.getAttribute("aria-label")
						.contains(instanceValue))
					.findFirst();
				if ( matching.isPresent() )
				{
					WebElement element = (WebElement) matching.get();
					VertexLogger.log("Instance " + instanceValue + " is present in the Instance list!!");
					element.click();
					flag = true;
				}
				else
				{
					VertexLogger.log("Required Instance " + instanceValue + " is not present in the list");
					instanceDropdownArrow.click();
					flag = false;
				}
			}
		}
		catch ( StaleElementReferenceException elementHasDisappeared )
		{
			return true;
		}
		return flag;
	}
}
