package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessSalesInvoicePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
/**
 * Gets an invoice from the list based on row
 * @author bhikshapathi
 */
public class NavSalesInvoiceListPage extends NavBasepage
{
    protected By invoiceGridLoc = By.cssSelector("table[id*='_BusinessGrid']");
    protected By invoiceRowsLoc = By.cssSelector("table tbody tr");
    protected By invoiceRowSegmentLoc = By.cssSelector("tr td");
    protected By openLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");
    protected By firstOrder = By.xpath("(//tbody/tr[1]/td[3]/a[@tabindex='0'])");
    protected By docHeading= By.xpath("(//th[@abbr='No.'])[1]");
    protected By filter=By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By searchPopUp=By.xpath("//div/p[contains(.,'Only show lines where \"No.\" is')]/../../../div/div/input[1]");
    protected By okay=By.xpath("(//button/span[contains(.,'OK')])");
    public NavSalesInvoiceListPage( WebDriver driver ) { super(driver); }

    /**
     * Gets an invoice from the list based on row
     *
     * @param rowNum
     *
     * @return element of the row that contains clickable link and number
     */
    public WebElement getInvoiceFromList(int rowNum )
    {
        List<WebElement> gridList = wait.waitForAllElementsPresent(invoiceGridLoc);
        WebElement grid = gridList.get(gridList.size() - 1);

        List<WebElement> gridRows = wait.waitForAllElementsPresent(invoiceRowsLoc, grid);
        WebElement itemRow = gridRows.get(rowNum);

        WebElement selectedInvoice = null;
        List<WebElement> segments = wait.waitForAllElementsPresent(invoiceRowSegmentLoc, itemRow);
        for ( WebElement segment : segments )
        {
            try
            {
                WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
                if ( ele
                        .getAttribute("title")
                        .contains("Open record") )
                {
                    selectedInvoice = ele;
                }
            }
            catch ( TimeoutException e )
            {

            }
        }

        return selectedInvoice;
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
     * Opens a sales invoice displayed on the list page, based on specified row
     *
     * @return sales invoice page
     */
    public BusinessSalesInvoicePage openInvoiceByRowIndex(int rowIndex )
    {
        WebElement select = getInvoiceFromList(rowIndex);

        click.clickElement(select);

        return initializePageObject(BusinessSalesInvoicePage.class);
    }

    /**
     * filters the Invoice number
     * @param invNo
     */
    public void filterDocuments(String invNo){
        WebElement docNoMenu=wait.waitForElementDisplayed(docHeading);
        click.clickElementCarefully(docNoMenu);
        click.clickElementCarefully(filter);
        WebElement searchDoc=wait.waitForElementDisplayed(searchPopUp);
        text.enterText(searchDoc,invNo);
        click.clickElementCarefully(okay);
        String itemNoLink=String.format("//a[@title='Open record \"%s\" in a new window']",invNo);
        click.clickElementCarefully(By.xpath(itemNoLink));
        waitForPageLoad();
    }
}
