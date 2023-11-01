package com.vertex.quality.connectors.orocommerce.components.admin;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroSystemConfigurationsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the menu that appears after clicking on the system tab in the main menu
 * contains all the methods necessary to interact with it
 *
 * @author alewis
 */
public class systemMenu extends VertexComponent {

    public systemMenu(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    By mainMenuParentContLoc = By.id("side-menu");
    By configurationsTabContLoc = By.className("side-menu-overlay");
    By configurationsTabLoc = By.cssSelector("span[title='Configuration']");

    /**
     * locates and clicks on the configurations tab in the system menu
     *
     * @return new object of the system configurations page
     */
    public OroSystemConfigurationsPage goToConfigurationsPage() {
        OroSystemConfigurationsPage configurationsPage;
        WebElement configurationsMenuParentCont = wait.waitForElementPresent(mainMenuParentContLoc);
        WebElement configurationsMenuCont = wait.waitForElementPresent(configurationsTabContLoc,
                configurationsMenuParentCont);
        WebElement configurationsTab = wait.waitForElementPresent(configurationsTabLoc, configurationsMenuCont);
        click.javascriptClick(configurationsTab);
        waitForPageLoad();
        configurationsPage = new OroSystemConfigurationsPage(driver);
        return configurationsPage;
    }
}
