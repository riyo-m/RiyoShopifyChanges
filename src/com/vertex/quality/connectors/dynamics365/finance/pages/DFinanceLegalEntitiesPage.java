package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import org.openqa.selenium.*;

/**
 * This class represents Legal Entities page
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author Mario Saint-Fleur
 */

public class DFinanceLegalEntitiesPage extends DFinancePostSignOnPage
{

    protected By SEARCH_INPUT = By.xpath("//input[contains(@id, 'QuickFilter_Input')]");
    protected By ADDRESS_MORE_OPTIONS = By.xpath("//span[contains(text(), 'More options')]");

    public DFinanceLegalEntitiesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Searches for a legal entity
     * @param entity
     */
    public void searchCompany(String entity) {
        wait.waitForElementDisplayed(SEARCH_INPUT);
        text.selectAllAndInputText(SEARCH_INPUT, entity + Keys.ARROW_UP + Keys.ENTER);
        waitForPageLoad();
    }

    /**
     * Clicks 'More options' on Address tab
     */
    public void clickAddressMoreOptions() {
        wait.waitForElementDisplayed(ADDRESS_MORE_OPTIONS);
        click.clickElementIgnoreExceptionAndRetry(ADDRESS_MORE_OPTIONS);
        waitForPageLoad();

        click.clickElementCarefully(ADDRESS_MORE_OPTIONS);
    }

}
