package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the locations page that has the different warehouses
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
public class KiboLocationsPage extends VertexPage {
    protected By warehouseContainerLoc = By.cssSelector("td[class='react-grid-cell']");
    protected By warehouseEditButtonLoc = By.cssSelector(
            "#application-mount > div > div.mozu-c-app.mozu-is-loaded > div:nth-child(5) > article > div > div.react-grid-table-container > table > tbody > tr:nth-child(3) > td.react-grid-action-container");
    protected By duplicateButtonContainerLoc = By.className("react-grid-action-menu-item");
    protected String wareHouseLoc = ".//td//span[text()='<<text_replace>>']";

    public KiboLocationsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * getter method to locate the home base button
     *
     * @return WebElement for home base button
     */
    protected WebElement getHomeBaseButton() {
        WebElement homeBaseButton = null;
        String expectedText = "homebase";
        homeBaseButton = element.selectElementByText(warehouseContainerLoc, expectedText);

        return homeBaseButton;
    }

    /**
     * uses the getter method to locate the home base button and then clicks on it
     *
     * @return new instance of Kibo home base page class
     */
    public KiboHomeBasePage goToHomeBasePage() {
        WebElement homeBaseButton = getHomeBaseButton();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", homeBaseButton);
        waitForPageLoad();
        return initializePageObject(KiboHomeBasePage.class);
    }

    /**
     * getter method to locate the warehouse edit button
     *
     * @return warehouse edit button WebElement
     */
    protected WebElement getWarehouseEditButton() {
        WebElement warehouseEditButton = wait.waitForElementPresent(warehouseEditButtonLoc);

        return warehouseEditButton;
    }

    /**
     * uses the getter method to locate  warehouse edit button and clicks on it
     */
    public void clickWarehouseEditButton() {
        WebElement warehouseEditButton = getWarehouseEditButton();
        warehouseEditButton.click();
    }

    /**
     * getter method to locate Duplicate button
     *
     * @return duplicate button WebElement
     */
    protected WebElement getDuplicateButton() {
        String expectedText = "Duplicate";
        WebElement duplicateButton = element.selectElementByText(duplicateButtonContainerLoc, expectedText);
        return duplicateButton;
    }

    /**
     * getter method to locate the WHCA  button
     *
     * @return WebElement for home base button
     */
    protected WebElement getWHCA() {
        String expectedText = "WHCA2";
        WebElement warehouse = element.selectElementByText(warehouseContainerLoc, expectedText);
        return warehouse;
    }

    /**
     * uses the getter method to locate the warehouseCA and then click on it
     *
     * @return new instance of the warehouseCA page
     */
    public KiboWarehouseCaPage clickWHCA() {
        WebElement whca = getWHCA();
        whca.click();
        return new KiboWarehouseCaPage(driver);
    }

    /**
     * getter method to locate re-cleanse warehouse button
     *
     * @return WebElement for home base button
     */
    protected WebElement getReCleanseWarehouseButton() {
        String expectedText = "ac_reclease";
        WebElement warehouse = element.selectElementByText(warehouseContainerLoc, expectedText);
        return warehouse;
    }

    /**
     * uses the getter method to locate the re-cleanse warehouse and then click on it
     *
     * @return new instance of the re-cleanse warehouse page
     */
    public KiboReCleanseWarehouse clickReCleanseWarehouse() {
        WebElement reCleanseWarehouseButton = getReCleanseWarehouseButton();
        reCleanseWarehouseButton.click();
        return new KiboReCleanseWarehouse(driver);
    }

    /**
     * Selects Ware-House to edit or view Ware-House address
     *
     * @param whValue pass ware-house name
     */
    public void selectWareHouse(String whValue) {
        waitForPageLoad();
        WebElement whName = wait.waitForElementPresent(By.xpath(wareHouseLoc.replace("<<text_replace>>", whValue)));
        click.moveToElementAndClick(whName);
        waitForPageLoad();
    }
}
