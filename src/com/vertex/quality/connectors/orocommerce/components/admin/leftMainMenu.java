package com.vertex.quality.connectors.orocommerce.components.admin;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the main menu on the left side of the admin site on almost all the pages
 * contains all the methods necessary to navigate through this menu
 *
 * @author alewis
 */
public class leftMainMenu extends VertexComponent {
    public systemMenu system;
    public taxesMenu taxes;

    public leftMainMenu(WebDriver driver, VertexPage parent) {
        super(driver, parent);
        this.system = new systemMenu(driver, parent);
        this.taxes = new taxesMenu(driver, parent);
    }

    By mainMenuParentContLoc = By.id("side-menu");
    By mainMenuContLoc = By.id("main-menu");
    By systemTabLocator = By.cssSelector("span[title='System']");
    By taxTabLocator = By.xpath("//span[@title='Taxes']");
    By inventoryTabLoc = By.cssSelector("span[title='Inventory']");
    By warehousesLoc = By.xpath("(.//span[@title='Warehouses'])[2]");
    By allWarehousesLabel = By.xpath(".//h1[text()='All Warehouses']");
    By allWarehousesRows = By.xpath(".//tbody[@class='grid-body']//tr");

    /**
     * locates the system tab in the main menu
     * and clicks on it
     */
    public void goToSystemTab() {
        waitForPageLoad();
        WebElement systemMenuTab = wait.waitForElementPresent(systemTabLocator);
        click.javascriptClick(systemMenuTab);
        waitForPageLoad();
    }

    /**
     * locates the taxes tab in the main menu
     * and clicks on it
     */
    public void goToTaxesTab() {
        WebElement mainMenuParentCont = wait.waitForElementPresent(mainMenuParentContLoc);
        WebElement taxesMenuTab = null;
        WebElement mainMenuContainer = wait.waitForElementDisplayed(mainMenuContLoc, mainMenuParentCont);

        taxesMenuTab = wait.waitForElementEnabled(taxTabLocator, mainMenuContainer);
        click.javascriptClick(taxesMenuTab);
    }

    /**
     * Clicks on Inventory & then select warehouses to go to warehouses page
     */
    public void gotoWarehousePage() {
        WebElement mainMenu = wait.waitForElementPresent(mainMenuParentContLoc);
        WebElement inventory = wait.waitForElementPresent(inventoryTabLoc, mainMenu);
        click.javascriptClick(inventory);
        WebElement wh = wait.waitForElementPresent(warehousesLoc);
        click.javascriptClick(wh);
        waitForPageLoad();
        wait.waitForElementPresent(allWarehousesLabel);
        wait.waitForAllElementsPresent(allWarehousesRows);
    }
}
