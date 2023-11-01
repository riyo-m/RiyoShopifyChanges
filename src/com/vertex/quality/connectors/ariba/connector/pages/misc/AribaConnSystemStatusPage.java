package com.vertex.quality.connectors.ariba.connector.pages.misc;

import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * representation of the page of this configuration site which displays the state of the connector's access to other
 * systems that it depends on (e.g. databases and an O Series instance)
 *
 * @author ssalisbury
 */
public class AribaConnSystemStatusPage extends AribaConnBasePage
{
	protected final By clearCachesButton = By.id("cacheClear");

	protected final By configDatabaseStatusLoc = By.xpath("//*/th[@id='config-status']/span");
	protected final By oseriesInstancesStatusTableLoc = By.id("oseries-table");

	// :( assumes that the status is the second column of each table
	protected final int statusColumnIndex = 1;

	public AribaConnSystemStatusPage( WebDriver driver )
	{
		super(driver, "Dependency Status");
	}

	/**
	 * checks whether the button, which clears the connector's caches & retrieves fresh data from the database,
	 * can be seen and clicked by the user
	 *
	 * @return whether the button is displayed and enabled
	 */
	public boolean isClearCachesButtonEnabled( )
	{
		boolean isClearButtonEnabled = element.isElementEnabled(clearCachesButton);
		return isClearButtonEnabled;
	}

	/**
	 * clicks the button to clear the connector's caches & retrieve fresh data from the database
	 */
	public void clickClearCachesButton( )
	{
		click.clickElementCarefully(clearCachesButton);
	}

	/**
	 * checks whether the Vertex System Status page is displaying the state of the connector's connection to the
	 * configuration database
	 *
	 * @return whether the Vertex System Status page is displaying the state of the connector's connection to the
	 * configuration database
	 *
	 * @author ssalisbury
	 */
	public boolean isConfigDatabaseStatusDisplayed( )
	{
		WebElement configDatabaseStatusElement = wait.waitForElementDisplayed(configDatabaseStatusLoc);
		boolean isDatabaseStatusDisplayed = element.isElementDisplayed(configDatabaseStatusElement);

		return isDatabaseStatusDisplayed;
	}

	/**
	 * collects the status of the connector's connection to the configuration database
	 *
	 * @return the status of the connector's connection to the configuration database
	 *
	 * @author ssalisbury
	 */
	public boolean getConfigDatabaseStatus( )
	{
		boolean status = false;
		WebElement configDatabaseStatusElement = wait.waitForElementDisplayed(configDatabaseStatusLoc);
		String databaseStatusText = text.getElementText(configDatabaseStatusElement);
		if(databaseStatusText.equals("OK")){
			status = true;
		}
		return status;
	}

	/**
	 * checks whether the Vertex System Status page is displaying the state of the connector's connection to the
	 * given O Series instance
	 *
	 * @param targetInstanceLabel the label for the O Series instance
	 *
	 * @return whether the Vertex System Status page is displaying the state of the connector's connection to the
	 * given O Series instance
	 *
	 * @author ssalisbury
	 */
	public boolean isOSeriesStatusDisplayed( final String targetInstanceLabel )
	{
		boolean isDatabaseStatusDisplayed = false;
		String oseriesTable = String.format("//*/table[@id='oseries-table']/thead/following-sibling::tbody/tr/td[@class='ok'][contains(text(),'%s')]//following-sibling::td[@title='OK'][contains(text(),'OK')]", targetInstanceLabel);

		By oseriesInstanceStatus = By.xpath(oseriesTable);

		WebElement configDatabaseStatusElement = wait.waitForElementDisplayed(oseriesInstanceStatus);


		return isDatabaseStatusDisplayed;
	}

	/**
	 * collects the status of the connector's connection to the given O Series instance
	 *
	 * @param targetInstanceLabel the label for the O Series instance
	 *
	 * @return the status of the connector's connection to the O Series instance
	 * returns null if the status element can't be found
	 *
	 * @author ssalisbury
	 */
	public String getOSeriesStatus( final String targetInstanceLabel )
	{
		String databaseStatusText = null;
		WebElement configDatabaseStatusElement = getOSeriesInstancesStatus(targetInstanceLabel);
		if ( configDatabaseStatusElement != null )
		{
			databaseStatusText = text.getElementText(configDatabaseStatusElement);
		}

		return databaseStatusText;
	}

	/**
	 * finds the element which displays how well the connector is able to access the given O Series instance
	 *
	 * @param targetInstanceLabelStart the beginning of the label for the O Series instance
	 *
	 * @return the element which displays how well the connector is able to access the given O Series instance
	 *
	 * @author ssalisbury
	 */
	protected WebElement getOSeriesInstancesStatus( final String targetInstanceLabelStart )
	{
		WebElement statusElem = null;
		WebElement oseriesInstancesTableElem = wait.waitForElementDisplayed(oseriesInstancesStatusTableLoc);

		List<WebElement> instanceEntries = wait.waitForAllElementsDisplayed(By.tagName("tr"),
			oseriesInstancesTableElem);

		WebElement targetInstanceEntry = null;
		for ( WebElement entry : instanceEntries )
		{
			// :( assumes that the row name/label is the first column
			WebElement instancesLabel = wait.waitForElementDisplayed(By.tagName("td"), entry);
			final String tenantsList = text.getElementText(instancesLabel);
			if ( tenantsList != null && tenantsList.startsWith(targetInstanceLabelStart) )
			{
				targetInstanceEntry = entry;
			}
		}

		if ( targetInstanceEntry != null )
		{
			List<WebElement> rowCells = wait.waitForAllElementsPresent(By.tagName("td"), targetInstanceEntry);
			if ( statusColumnIndex < rowCells.size() )
			{
				statusElem = rowCells.get(statusColumnIndex);
			}
		}

		return statusElem;
	}

	public boolean getOSeriesConnection(String oseriesInstance){
		boolean status = false;
		String instance = String.format("//*/table[@id='oseries-table']/thead/following-sibling::tbody/tr/td[@class='ok'][contains(text(),'%s')]//following-sibling::td[@title='OK'][contains(text(),'OK')]",oseriesInstance);

		try{
			WebElement oseriesStatus = wait.waitForElementDisplayed(By.xpath(instance));
			if(oseriesStatus.getText().equals("OK")){
				status = true;
			}
		}catch(org.openqa.selenium.TimeoutException e){

		}

		return status;

	}
}
