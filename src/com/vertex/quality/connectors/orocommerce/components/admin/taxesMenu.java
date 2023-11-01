package com.vertex.quality.connectors.orocommerce.components.admin;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroVertexLogsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author alewis
 */
public class taxesMenu extends VertexComponent {

    public taxesMenu(WebDriver driver, VertexPage parent )
    {
        super(driver, parent);
    }

    By mainMenuParentContLoc = By.id("side-menu");
    By vertexLogsTabContLoc = By.className("side-menu-overlay");
    By vertexLogsTabLoc = By.cssSelector("span[title='Vertex Logs']");

    /**
     * locates and clicks on the configurations tab in the system menu
     * @return new object of the system configurations page
     */
    public OroVertexLogsPage goToVertexLogsPage( )
    {
        OroVertexLogsPage taxLogsPage;
        WebElement vertexLogsMenuParentCont = wait.waitForElementPresent(mainMenuParentContLoc);
        WebElement configurationsMenuCont = wait.waitForElementDisplayed(vertexLogsTabContLoc,
                vertexLogsMenuParentCont);
        WebElement taxLogsTab= wait.waitForElementEnabled(vertexLogsTabLoc, configurationsMenuCont);
        click.clickElementCarefully(taxLogsTab);
        taxLogsPage = new OroVertexLogsPage(driver);
        return taxLogsPage;
    }
}