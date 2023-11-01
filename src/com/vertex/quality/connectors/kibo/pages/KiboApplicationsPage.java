package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the applications page
 * contains methods necessary to select the vertex link tax connector and other interactions with
 * the page
 *
 * @author osabha
 */
public class KiboApplicationsPage extends VertexPage
{
	public KiboApplicationsPage( WebDriver driver )
	{
		super(driver);
	}

	protected By listContainerLoc = By.className("react-grid-table-container");
	protected By tableTag = By.tagName("tbody");
	protected By rowTag = By.tagName("tr");
	protected By rowCellTag = By.tagName("span");
	protected By vertexConnectorLoc = By.cssSelector("td:nth-child(5)");
	protected By vertexTaxLink = By.xpath("(.//td[normalize-space(.)='Vertex Tax Link'])[2]");

	/**
	 * getter method to locate the vertex tax link WebElement
	 * the method locates all and only  the vertexInc ProductNames/Apps (other apps might be present and
	 * enabled but will be avoided ) and then selects the one that is enabled
	 *
	 * @return vertex tax link button WebElement
	 */
	protected WebElement getVertexConnector( )
	{
		String xpath
			= "//*[@id=\"application-mount\"]/div/div[1]/div[5]/article/div/div[4]/table/tbody/tr[5]/td[6]/span/span";

		String expectedVersion = "1.2.0";
		String expectedText = "Vertex Inc";
		WebElement vertexConnector = null;
		vertexConnector = wait.waitForElementDisplayed(By.xpath(xpath));
		//		List<WebElement> vertexRows = new ArrayList<>();
		//		WebElement listSupContainer = wait.waitForElementPresent(listContainerLoc);
		//		WebElement listContainer = wait.waitForElementPresent(tableTag, listSupContainer);
		//		List<WebElement> allRows = wait.waitForAllElementsDisplayed(rowTag, listContainer);
		//		for ( WebElement row : allRows )
		//		{
		//			List<WebElement> rowCells = wait.waitForAllElementsPresent(rowCellTag, row);
		//			for ( WebElement rowCell : rowCells )
		//			{
		//				String rowCellText = text.getElementText(rowCell);
		//				if ( expectedText.equals(rowCellText) )
		//				{
		//					vertexRows.add(row);
		//				}
		//			}
		//		}
		//
		//		for ( WebElement vertexRow : vertexRows )
		//		{
		//			int versionCellIndex = getColumnIndex("Version");
		//			List<WebElement> allCells = wait.waitForAnyElementsDisplayed(By.tagName("td"), vertexRow);
		//			allCells.removeIf(cell -> !element.isElementDisplayed(cell));
		//			WebElement connectorVersionCell = allCells.get(versionCellIndex);
		//			String connectorVersionText = text.getElementText(connectorVersionCell);
		//			if ( expectedVersion.equals(connectorVersionText) )
		//			{
		//				vertexConnector = connectorVersionCell;
		//				break;
		//			}
		//		}

		return vertexConnector;
	}

	/**
	 * gets the column index by title
	 *
	 * @param targetColumnTitle title of the target column
	 *
	 * @return index of the column
	 */
	public int getColumnIndex( final String targetColumnTitle )
	{
		int columnIndex = 0;
		WebElement listSupContainer = wait.waitForElementPresent(By.className("react-grid-header-fixed-container"));
		List<WebElement> columns = wait.waitForAllElementsPresent(By.tagName("th"), listSupContainer);
		columnIndex = element.findElementPositionByText(columns, targetColumnTitle);

		return columnIndex;
	}

	/**
	 * uses getter method to locate the vertex connector button and then clicks on it
	 *
	 * @return new instance of the vertex connector page class
	 */
	public KiboVertexConnectorPage clickVertexConnector( )
	{
		WebElement vertexConnector = getVertexConnector();
		KiboVertexConnectorPage connectorPage = null;
		if ( vertexConnector != null )
		{
			click.clickElement(vertexConnector);
			connectorPage = initializePageObject(KiboVertexConnectorPage.class);
		}

		return connectorPage;
	}

    /**
     * Select vertex's connector to go to connector settings page.
     */
    public void selectVertexConnector() {
        WebElement connector = wait.waitForElementPresent(vertexTaxLink);
        click.moveToElementAndClick(connector);
        waitForPageLoad();
    }
}
