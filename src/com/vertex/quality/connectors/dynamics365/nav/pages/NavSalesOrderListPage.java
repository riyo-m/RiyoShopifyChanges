package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.components.NavPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the sales order list page
 *
 * @author bhikshapathi
 */
public class NavSalesOrderListPage extends NavBasepage
{
    public NavPagesNavMenu pageNavMenu;

    protected By orderGridLoc = By.cssSelector("table[id*='_BusinessGrid']");
    protected By orderRowsLoc = By.cssSelector("table tbody tr");
    protected By orderRowSegmentLoc = By.cssSelector("tr td");
    protected By openLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");
    protected By firstOrder = By.xpath("(//tbody/tr[1]/td[3]/a[@tabindex='0'])");

    public NavSalesOrderListPage( WebDriver driver )
    {
        super(driver);
        this.pageNavMenu = new NavPagesNavMenu(driver, this);
    }

    /**
     * Gets an order from the list based on row
     *
     * @param rowNum
     *
     * @return element of the row that contains clickable link and number
     */
    public WebElement getOrderFromList(int rowNum )
    {
        rowNum--;
        List<WebElement> gridList = wait.waitForAllElementsPresent(orderGridLoc);
        WebElement grid = gridList.get(gridList.size() - 1);

        List<WebElement> gridRows = wait.waitForAllElementsPresent(orderRowsLoc, grid);
        WebElement itemRow = gridRows.get(rowNum);

        WebElement selectedOrder = null;
        List<WebElement> segments = wait.waitForAllElementsPresent(orderRowSegmentLoc, itemRow);
        for ( WebElement segment : segments )
        {
            try
            {
                WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
                if ( ele
                        .getAttribute("title")
                        .contains("Open record") )
                {
                    selectedOrder = ele;
                }
            }
            catch ( TimeoutException e )
            {

            }
        }

        return selectedOrder;
    }

    /**
     * Gets the number of the customer in the specified row
     *
     * @return the customer's document number
     */
    public String getCustomerNumberByRowIndex()
    {
        waitForPageLoad();
        WebElement customer = wait.waitForElementDisplayed(firstOrder);

        String number = customer.getText();

        return number;
    }

    /**
     * Opens a sales order displayed on the list page, based on specified row
     *
     * @return sales order page
     */
    public NavSalesOrderPage openOrderByRowIndex(int rowIndex )
    {
        WebElement select = getOrderFromList(rowIndex);

        click.clickElement(select);

        return initializePageObject(NavSalesOrderPage.class);
    }
}
