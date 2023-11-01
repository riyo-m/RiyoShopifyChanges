package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


/**
 * This class represents the Warehouses page
 * it contains all the methods necessary to interact with the page
 *
 * @author Vivek Olumbe
 */

public class DFinanceWarehousesPage  extends DFinanceBasePage {

    protected By SEARCH_INPUT = By.xpath("//input[contains(@id, 'WarehouseQuickFilter_Input')]");

    public DFinanceWarehousesPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Searches for a warehouse
     * @param warehouse
     */
    public void searchWarehouse(String warehouse) {
        wait.waitForElementDisplayed(SEARCH_INPUT);
        text.selectAllAndInputText(SEARCH_INPUT, warehouse + Keys.ARROW_UP + Keys.ENTER);
        waitForPageLoad();
    }
}
