package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOCronJobCodes;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents execute Cronjob and Perform operations on CronJob Page
 * i.e. Execute the VertexCronjob and Validate the Status
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOCronJobsPage extends HybrisBasePage
{
	public HybrisBOCronJobsPage( WebDriver driver )
	{
		super(driver);
	}

	protected By CRONJOB_SEARCH_INPUT = By.cssSelector("span[class*='textsearch-searchbox']>input");
	protected By CRONJOB_SEARCH_BUTTON = By.cssSelector("[class^='yw-textsearch-searchbutton']");
	protected By CRONJOB_SEARCHLIST_CONTAINER = By.cssSelector("[class*='yw-coll-browser-hyperlink']");
	protected By CRONJOB_SEARCHLIST_COLUMNNAMES = By.className("z-listcell-content");
	protected By RUN_CRONJOB = By.cssSelector("img[class*='action'][title='Run CronJob']");
	protected By CONFIRM_MSG_YES_BUTTON = By.xpath("//button[text()='Yes']");
	protected By REFRESH_BUTTON = By.xpath(
		"//div[contains(@class,'toolbar')]//button[contains(@class,'button') and contains(text(),'Refresh')]");
	protected By CURRENT_STATUS = By.xpath("//td[*[*[text()='pingVertexPerformable']]]/..//td[3]//span");
	protected By LATEST_RESULT_STATUS = By.xpath("//td[*[*[text()='pingVertexPerformable']]]/..//td[4]//span");
	protected By LAST_END_TIME = By.xpath(
		"//span[@title='endTime']/../following-sibling::div//input[contains(@class,'datebox-input')]");
	protected By VERTEX_CRONJOB_AT_FIRST_POSITION = By.xpath(".//tr[1]//span[normalize-space(.)='pingVertexPerformable']");

	/***
	 * Method to search for VertexCronJob
	 * i.e. Searches for VertexCronJob based on VertexCronJobCode and execute the Job
	 *
	 * @param cronJobCode - Enter Cronjobcode and search for VertexCronJob from list of Cronjobs
	 */
	public void searchVertexCronJob( String cronJobCode )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementEnabled(CRONJOB_SEARCH_INPUT);
		text.enterText(CRONJOB_SEARCH_INPUT, cronJobCode);
		hybrisWaitForPageLoad();
		wait.waitForElementEnabled(CRONJOB_SEARCH_BUTTON);
		click.javascriptClick(CRONJOB_SEARCH_BUTTON);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to select VertexCronjob
	 */
	public void selectVertexCronJob( )
	{
		wait.waitForElementDisplayed(CRONJOB_SEARCHLIST_CONTAINER);
		wait.waitForElementPresent(VERTEX_CRONJOB_AT_FIRST_POSITION);
		WebElement searchListContainer = driver.findElement(CRONJOB_SEARCHLIST_CONTAINER);
		List<WebElement> searchListColumnNames = searchListContainer.findElements(CRONJOB_SEARCHLIST_COLUMNNAMES);
		WebElement columnName = null;
		for ( int i = 0 ; i < searchListColumnNames.size() ; i++ )
		{
			columnName = searchListColumnNames.get(i);
			if ( columnName
				.getText()
				.equalsIgnoreCase(HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode()) )
			{
				scroll.scrollElementIntoView(columnName);
				columnName.click();
			}
		}
		wait.waitForElementDisplayed(RUN_CRONJOB);
	}

	/***
	 * Method to confirm to run Vertex Cronjob
	 */
	public void confirmVertexCronJobExecution( )
	{
		wait.waitForElementDisplayed(CONFIRM_MSG_YES_BUTTON);
		click.clickElement(CONFIRM_MSG_YES_BUTTON);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to run VertexCronjob
	 */
	public void runVertexCronJob( )
	{
		wait.waitForElementDisplayed(RUN_CRONJOB);
		click.clickElement(RUN_CRONJOB);
		hybrisWaitForPageLoad();
		this.confirmVertexCronJobExecution();
	}

	/***
	 * Method to Refresh Cron Job
	 * Wait for RefreshButton to Enable and Click Refresh Button
	 * Again wait for 'disabled attribute' to change to hit the Refresh button again for next time 
	 */
	public void refreshCronJob( )
	{
		wait.waitForElementEnabled(REFRESH_BUTTON);
		click.clickElement(REFRESH_BUTTON);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to Get Vertex CronJob Status
	 *
	 * @return map cronJobStatusMap
	 *         It returns two status (LastResultStatus & Current Status) & adding both status to Map
	 *         Last Result Status returns either Success or Failure
	 *         Current Status returns either Running or Finished
	 *         Both status are required to validate the CronJobStatus
	 *         Hit the Refresh button for maximum of 15 times to get the Cronjob Status 
	 */
	public Map<String, String> getCronJobStatus( )
	{
		String currentStatus = text
			.getElementText(CURRENT_STATUS)
			.trim();
		String lastResultStatus = text
			.getElementText(LATEST_RESULT_STATUS)
			.trim();
		String lastEndTime = attribute
			.getElementAttribute(LAST_END_TIME, "value")
			.trim();
		final int maxRetries = 15;
		int retries = 0;
		while ( retries < maxRetries || currentStatus == "RUNNING" )
		{
			wait.waitForElementEnabled(REFRESH_BUTTON);
			refreshCronJob();
			wait.waitForElementDisplayed(CURRENT_STATUS);
			currentStatus = text
				.getElementText(CURRENT_STATUS)
				.trim();
			wait.waitForElementDisplayed(LATEST_RESULT_STATUS);
			lastResultStatus = text
				.getElementText(LATEST_RESULT_STATUS)
				.trim();
			lastEndTime = attribute
				.getElementAttribute(LAST_END_TIME, "value")
				.trim();
			if ( currentStatus.equalsIgnoreCase("FINISHED") )
			{
				break;
			}
			else if ( currentStatus.equalsIgnoreCase("RUNNING") || lastEndTime.equalsIgnoreCase("None") )
			{
				retries = retries + 1;
			}
		}
		final Map<String, String> cronJobStatusMap = new HashMap<String, String>();
		cronJobStatusMap.put("LASTRESULTSTATUS", lastResultStatus);
		cronJobStatusMap.put("CURRENTSTATUS", currentStatus);
		cronJobStatusMap.put("JOBEXECUTIONLASTENDTIME", lastEndTime);
		hybrisWaitForPageLoad();
		return cronJobStatusMap;
	}
}
