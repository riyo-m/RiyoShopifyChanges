package com.vertex.quality.connectors.dynamics365.business.pages;


import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessServiceBasePage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * contains methods for interacting with service items
 */

public class BusinessServiceItemPage extends BusinessServiceBasePage {
    protected By expandServiceLinesButton = By.xpath("//button[@title='Maximize the page']");
    protected By close=By.xpath("(//div[contains(@class,'ms-nav-content')]//button/span[text()='Close'])[6]");
    protected By saveAndCloseItemSheet= By.xpath("//button[@title='Save and close the page']");
    public BusinessServiceItemPage(WebDriver driver) {
        super(driver);
    }
    /**
     * locates and clicks on the back button on service mgmt tax details dialog
     */
    public void serviceMgmtItemSheetCloseButton(){
        WebElement closeButton = wait.waitForElementDisplayed(close);
        click.clickElementCarefully(closeButton);
        wait.waitForElementNotDisplayedOrStale(closeButton, 5);
    }
    /**
     * Generalized method, to close service Item Worksheet
     */
    public void closeItemSheet()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(saveAndCloseItemSheet);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }

    /**
     * Expands service lines window
     */
    public void expandServiceLines() {
        wait.waitForElementDisplayed(expandServiceLinesButton);
        click.clickElementCarefully(expandServiceLinesButton);
    }

    /**
     * locates the exact cell from the table in service lines
     *
     * @param rowIndex    the row the cell is on
     * @param columnIndex the column the cell is on
     * @return cell, as WebElement
     */
    public WebElement getTableCellInServiceLines(int rowIndex, int columnIndex) {
        WebElement cell = null;

        List<WebElement> tableCons = wait.waitForAllElementsPresent(By.tagName("table"));
        WebElement tableParentCon = tableCons.get(tableCons.size() - 1);
        WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
        findingCell:
        for (WebElement row : tableRows) {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            for (WebElement column : rowColumns) {
                column = rowColumns.get(columnIndex);
                try {
                    cell = column.findElement(By.tagName("input"));
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    cell = column;
                }
                break findingCell;
            }
        }

        return cell;
    }

    /**
     * Enter service item No. for service line
     * @param serviceItemNo
     * @param rowIndex
     */
    public void enterServiceItemNoForServiceLine(String serviceItemNo, int rowIndex){
        WebElement serviceItemNoField = getTableCellInServiceLines(rowIndex, 2);
        wait.waitForElementEnabled(serviceItemNoField);
        click.clickElementIgnoreExceptionAndRetry(serviceItemNoField);
        text.enterText(serviceItemNoField, serviceItemNo);
    }

    /**
     *  Enter the item type for a row in service lines
     * @param itemType
     * @param rowIndex
     */
    public void enterItemTypeForServiceLine(String itemType, int rowIndex){
        WebElement itemTypeField = getTableCellInServiceLines(rowIndex, 4);
        wait.waitForElementEnabled(itemTypeField);
        click.clickElementIgnoreExceptionAndRetry(itemTypeField);
        waitForPageLoad();
        String iType = String.format("//option[text()='%s']", itemType);
        click.clickElementCarefully(By.xpath(iType));
    }

    /**
     * locates the item number field in a service line and enters the item number in it.
     *
     * @param itemNumber desired item number as a string
     * @param rowIndex   which row to write to
     */
    public void enterItemNumberForServiceLine(String itemNumber, int rowIndex) {
        WebElement itemNumberField = getTableCellInServiceLines(rowIndex, 5);
        if(element.isElementPresent(By.xpath("(//th[@abbr='Line No.'])[1]"))){
            itemNumberField = getTableCell(rowIndex, 5);
        }
        click.clickElementIgnoreExceptionAndRetry(itemNumberField);
        waitForPageLoad();

        text.selectAllAndInputText(itemNumberField, itemNumber);
        text.pressTab(By.cssSelector("body"));
    }

    /**
     * Enter the quantity for a row in service lines
     *
     * @param quantity
     * @param rowIndex
     */
    public void enterQuantityForServiceLine(String quantity, int rowIndex) {
        WebElement quantityField = getTableCellInServiceLines(rowIndex, 8);
        scroll.scrollElementIntoView(quantityField);
        wait.waitForElementEnabled(quantityField);
        click.clickElementIgnoreExceptionAndRetry(quantityField);
        text.enterText(quantityField, quantity);
        text.pressTab(quantityField);
    }

    /**
     * Enter the price for a row in service lines
     *
     * @param price
     * @param rowIndex
     */
    public void enterPriceForServiceLine(String price, int rowIndex) {
        WebElement priceField = getTableCellInServiceLines(rowIndex, 12);
        scroll.scrollElementIntoView(priceField);
        wait.waitForElementEnabled(priceField);
        click.clickElementIgnoreExceptionAndRetry(priceField);
        text.enterText(priceField, price);
        text.pressTab(priceField);
    }

    /**
     * Fill in a row in service lines
     * @param itemType
     * @param itemCode
     * @param itemQuantity
     * @param price
     * @param itemIndex
     */
    public void fillInTableInfoForServiceLine(String serviceItem, String itemType, String itemCode, String itemQuantity,
                                                   String price, int itemIndex ) {
        int itemNumber = itemIndex - 1;
        if (serviceItem != null) {
            enterServiceItemNoForServiceLine(serviceItem, itemNumber);
        }
        enterItemTypeForServiceLine(itemType,itemNumber);
        enterItemNumberForServiceLine(itemCode, itemNumber);
        enterQuantityForServiceLine(itemQuantity, itemNumber);
        if(price != null) {
            enterPriceForServiceLine(price, itemNumber);
        }

    }


}
