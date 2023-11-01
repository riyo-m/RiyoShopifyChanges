package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessSalesQuotesPage;
import com.vertex.quality.connectors.dynamics365.nav.components.NavPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the sales quote list page
 *
 * @author bhikshapathi
 */
public class NavSalesQuotesListPage extends NavBasepage
{
    public NavPagesNavMenu pageNavMenu;

    protected By quoteGridLoc = By.cssSelector("table[id*='_BusinessGrid']");
    protected By quoteRowsLoc = By.cssSelector("table tbody tr");
    protected By quoteRowSegmentLoc = By.cssSelector("tr td");
    protected By openQuoteLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");
    protected By firstQuote = By.xpath("//tbody/tr[1]/td[3]/a[@tabindex='0']");

    public NavSalesQuotesListPage( WebDriver driver )
    {
        super(driver);

        this.pageNavMenu = new NavPagesNavMenu(driver, this);
    }

    /**
     * Gets a quote from the list based on row
     *
     * @param rowNum
     *
     * @return element of the row that contains clickable link and number
     */
    public WebElement getQuoteFromList(int rowNum )
    {
        rowNum--;
        List<WebElement> gridList = wait.waitForAllElementsPresent(quoteGridLoc);
        WebElement grid = gridList.get(gridList.size() - 1);

        List<WebElement> gridRows = wait.waitForAllElementsPresent(quoteRowsLoc, grid);
        WebElement itemRow = gridRows.get(rowNum);

        WebElement selectedQuote = null;
        List<WebElement> segments = wait.waitForAllElementsPresent(quoteRowSegmentLoc, itemRow);
        for ( WebElement segment : segments )
        {
            try
            {
                WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
                if ( ele
                        .getAttribute("title")
                        .contains("Open record") )
                {
                    selectedQuote = ele;
                }
            }
            catch ( TimeoutException e )
            {

            }
        }

        return selectedQuote;
    }
    /**
     * Gets the number of the customer in the specified row
     *
     * @return the customer's document number
     */
    public String getCustomerNumberByRowIndex()
    {
        waitForPageLoad();
        WebElement customer = wait.waitForElementDisplayed(firstQuote);

        String number = customer.getText();

        return number;
    }

    /**
     * Opens a sales quote displayed on the list page, based on specified row
     *
     * @return sales quote page
     */
    public NavSalesQuotesPage openQuoteByRowIndex(int rowIndex )
    {
        WebElement select = getQuoteFromList(rowIndex);

        click.clickElement(select);

        return initializePageObject(NavSalesQuotesPage.class);
    }
}
