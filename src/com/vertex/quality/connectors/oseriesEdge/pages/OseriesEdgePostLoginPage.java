package com.vertex.quality.connectors.oseriesEdge.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * Common functions for anything related to OseriesEdge Homepage.
 *
 * @author- Laxmi Lama-Palladino
 */

public class OseriesEdgePostLoginPage extends OseriesEdgeLoginPage
{
    public OseriesEdgePostLoginPage( WebDriver driver )
    {
        super(driver);
    }
    protected By OSERIES_EDGE_LINK = By.xpath(".//div[@data-testid='O Series Edge']");
    protected By OSERIES_EDGE_PAGE = By.xpath("//*/div/div[@class='product-name'][contains(text(),'O Series Edge')]");

    /**
     * Verify OseriesEdge Link is visible
     */
    public boolean getOseriesEdgeLinkIsVisible( )
    {
        waitForPageLoad();
        wait.waitForElementDisplayed(OSERIES_EDGE_LINK);
        return element.isElementDisplayed(OSERIES_EDGE_LINK);
    }
    /**
     * Clicks the Oseries Edge Tab
     */
    public void goToOSeriesEdgePage()
    {
        wait.waitForElementDisplayed(OSERIES_EDGE_PAGE);
        click.clickElementCarefully(OSERIES_EDGE_PAGE);
    }

}
