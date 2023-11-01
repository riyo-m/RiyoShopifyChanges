package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * This class represents the All Vendors page
 * it contains all the methods necessary to interact with the page
 *
 * @author Vivek Olumbe
 */

public class DFinanceAllVendorsPage extends DFinanceBasePage {

    protected By SEARCH_INPUT = By.xpath("//input[contains(@id, 'QuickFilterControl')]");

    public DFinanceAllVendorsPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Searches for a vendor
     * @param vendor
     */
    public void searchVendor(String vendor) {
        wait.waitForElementDisplayed(SEARCH_INPUT);
        text.selectAllAndInputText(SEARCH_INPUT, vendor + Keys.ARROW_UP + Keys.ENTER);
        waitForPageLoad();

        By searchResult = By.cssSelector(
                String.format("[id*='VendTable_AccountNum'][value*='%s']", vendor));
        click.clickElementIgnoreExceptionAndRetry(searchResult);
        text.pressEnter(searchResult);
        waitForPageLoad();
    }
}
