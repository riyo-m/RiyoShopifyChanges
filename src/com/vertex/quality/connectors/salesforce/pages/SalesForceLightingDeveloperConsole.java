package com.vertex.quality.connectors.salesforce.pages;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Common functions for anything related to Salesforce Developer Page.
 *
 * @author brendaj
 */
public class SalesForceLightingDeveloperConsole extends SalesForceBasePage
{
	protected By ICON_SETUP_GEAR = By.xpath(".//lightning-icon/span/lightning-primitive-icon/*[@data-key='setup']");
	protected By LINK_DEVELOPER_CONSOLE = By.xpath(".//*[contains(@id,'developer-console')]");

	protected By USER_NAV_ARROW = By.xpath("//*[@id='userNav-arrow']");
	protected By LINK_CLASSIC_DEVELOPER_CONSOLE = By.xpath(".//a[@title='Developer Console (New Window)']");

	protected By MENU_FILE = By.xpath("//*[@id='editorMenuEntry']");
	protected By MENU_FILE_NEW = By.xpath("//*[@id='editorMenuEntry']");
	protected By MENU_DEBUG = By.xpath("//*[@id='debugMenuEntry']");
	protected By MENU_DEBUG_ANON_WINDOW = By.xpath("//*[@id='openExecuteAnonymousWindow']");
	protected By MENU_TEST = By.xpath("//*[@id='testMenuEntry']");
	protected By MENU_TEST_RUNALL = By.xpath("//*[@id='testRunAllButton-textEl']");

	protected By TAB_LOGS = By.xpath(".//*[contains(@id,'bottomPanel')]/.//span[contains(text(),'Logs')]");
	protected By TAB_QUERY = By.xpath(".//*[contains(@id,'bottomPanel')]/.//*[contains(text(),'Query Editor')]");
	protected By TAB_TEST = By.xpath(".//*[contains(@id,'bottomPanel')]/.//span[contains(text(),'Test')]");
	protected By TEST_STATUS = By.xpath(".//*[@id='testResultTree-body']/.//tr/td[1]/div/div/img");

	protected By QUERY_TEXT = By.xpath(".//*[@id='queryEditorText-bodyEl']/textarea");
	protected By QUERY_BUTTON = By.xpath("//*[@id='queryExecuteButton-btnEl']");
	protected By QUERY_RETURN_RECORDS = By.xpath(".//span[starts-with(text(), 'Query Results')]");

	protected By linesBy = By.xpath("//*[@id='ExecAnon-body']/.//*[@class='CodeMirror-code']//pre");
	protected By CODE_LINES = By.xpath("//div[@class='CodeMirror cm-s-default']");
	protected By OPEN_EXTERNAL_EDITOR_BUTTON = By.id("openExternalEditorToolButton");
	protected By BUTTON_EXECUTE = By.xpath("//button//span[text()='Execute' and not(contains(@id, 'queryExecuteButton'))]");

	protected By SUCCESS_MESSAGE = By.xpath("//table/tbody/tr/td/div[text()='Success']");

	protected By DEV_CONSOLE_ALERT = By.xpath(".//span[contains(@id, 'btnInnerEl') and text() = 'OK']");

	protected By LOG_MESSAGE = By.xpath(".//*[@text()='OK']");

	public SalesForceLightingDeveloperConsole( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Launch Developer Console
	 */
	public void launchClassicDeveloperConsole( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(USER_NAV_ARROW);
		click.clickElement(USER_NAV_ARROW);
		wait.waitForElementDisplayed(LINK_CLASSIC_DEVELOPER_CONSOLE);
		click.clickElement(LINK_CLASSIC_DEVELOPER_CONSOLE);
		waitForPageLoad();
		window.switchToWindowTextInTitle("Developer");
	}

	/**
	 * Launch Developer Console
	 */
	public void launchLightningDeveloperConsole( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ICON_SETUP_GEAR);
		click.clickElement(ICON_SETUP_GEAR);
		wait.waitForElementDisplayed(LINK_DEVELOPER_CONSOLE);
		click.clickElement(LINK_DEVELOPER_CONSOLE);
		waitForPageLoad();
		window.switchToWindowTextInTitle("Developer");
		waitForSalesForceLoaded();
		driver
			.manage()
			.window()
			.setSize(new Dimension(1024, 768));
	}

	/**
	 * Set Developer Console value to text
	 */
	public void setDeveloperConsole( String developerConsoleMessage ) throws InterruptedException
	{
		setDeveloperConsole( developerConsoleMessage, false);
	}

	/**
	 * Set Developer Console value to text
	 */
	public void setDeveloperConsole( String developerConsoleMessage, Boolean expectingSuccess ) throws InterruptedException
	{
		try
		{
			waitForPageLoad();
			if(element.isElementDisplayed(DEV_CONSOLE_ALERT))
			{
				acceptDevConsoleAlert();
			}

			switchToLogsTab();
			wait.waitForElementDisplayed(MENU_DEBUG);
			Thread.sleep(1000);

			click.clickElement(MENU_DEBUG);
			wait.waitForElementDisplayed(MENU_DEBUG_ANON_WINDOW);
			click.clickElement(MENU_DEBUG_ANON_WINDOW);

			wait.waitForElementDisplayed(BUTTON_EXECUTE);

			//Open external code editor
			wait.waitForElementDisplayed(OPEN_EXTERNAL_EDITOR_BUTTON);
			click.clickElement(OPEN_EXTERNAL_EDITOR_BUTTON);
			window.switchToWindowTextInTitle("Execute Anonymous - Google Chrome");

			Actions actions = new Actions(driver);
			List<WebElement> lines = driver.findElements(linesBy);

			while ( lines.size() > 1 )
			{
				//deleting old Apex code
				try
				{
					lines.get(0).click();
					actions.sendKeys(lines.get(0),Keys.chord(Keys.CONTROL, "A")).click().perform();
					actions.sendKeys(Keys.BACK_SPACE).perform();
					lines = driver.findElements((linesBy));
				}
				catch ( Exception e )
				{
					e.printStackTrace();
					actions.sendKeys(Keys.BACK_SPACE).perform();
					lines = driver.findElements((linesBy));
				}
			}

			actions
				.sendKeys(developerConsoleMessage)
				.perform();
			VertexLogger.log("Console Message: "+developerConsoleMessage);

			click.clickElement(BUTTON_EXECUTE);
			waitForPageLoad();

			if(expectingSuccess)
				wait.waitForElementDisplayed(SUCCESS_MESSAGE);
			driver.close();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			closeConsole();
		}
	}

	/**
	 * Run all Unit tests
	 */
	public void runAllTests( )
	{
		window.switchToWindowTextInTitle("Developer Console");
		waitForPageLoad();
		wait.waitForElementDisplayed(MENU_TEST);
		click.clickElement(MENU_TEST);
		wait.waitForElementDisplayed(MENU_TEST_RUNALL);
		click.clickElement(MENU_TEST_RUNALL);
		waitForPageLoad();
	}

	/**
	 * wait for tests to complete executing
	 */
	public void waitForTestComplete( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(TAB_TEST);
		click.javascriptClick(TAB_TEST);
		String status = getTestStatus();
		int i = 0;
		while ( status != "Completed" || status != "Failed" )
		{
			i++;
			try
			{
				Thread.sleep(1000);
				status = getTestStatus();
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
			if ( i > 120 )
			{
				return;
			}
		}
	}

	/**
	 * Get Test Status value
	 *
	 * @return test status
	 */
	public String getTestStatus( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(TEST_STATUS);
		List<WebElement> listOfElements = driver.findElements(TEST_STATUS);
		int countOfElements = listOfElements.size();
		return attribute.getElementAttribute(listOfElements.get(countOfElements - 1), "title");
	}

	/**
	 * close developer console and switch back to salesforce window
	 */
	public void closeConsole( )
	{
		jsWaiter.sleep(3000);
		window.switchToWindowTextInTitle("Developer");
		driver.close();
		window.switchToWindowTextInTitle("Home");
	}

	/**
	 * switch to Logs tab in developer console
	 */
	public void switchToLogsTab( )
	{
		wait.waitForElementDisplayed(TAB_LOGS);
		click.clickElement(TAB_LOGS);
		waitForPageLoad();
	}

	/**
	 * switch to Logs tab in developer console
	 */
	public void acceptDevConsoleAlert( )
	{
		wait.waitForElementDisplayed(DEV_CONSOLE_ALERT);
		click.clickElement(DEV_CONSOLE_ALERT);
		waitForPageLoad();
	}

	/**
	 * switch to Query tab in developer console
	 */
	public void switchToQueryTab( )
	{
		wait.waitForElementDisplayed(TAB_QUERY);
		click.clickElement(TAB_QUERY);
		waitForPageLoad();
	}

	/**
	 * set Query Editor field
	 * @param query
	 */
	public void setQueryEditor(String query)
	{
		wait.waitForElementDisplayed(QUERY_TEXT);
		text.enterText(QUERY_TEXT, query);
	}

	/**
	 * click Query Button
	 */
	public void clickQuery()
	{
		wait.waitForElementDisplayed(QUERY_BUTTON);
		click.clickElement(QUERY_BUTTON);
		wait.waitForElementDisplayed(QUERY_RETURN_RECORDS);
	}

	/** return total rows result
	 *
	 * @return String
	 */
	public String getTotalRowsReturned()
	{
		wait.waitForElementDisplayed(QUERY_RETURN_RECORDS);
		return text.getElementText(QUERY_RETURN_RECORDS);
	}
}
