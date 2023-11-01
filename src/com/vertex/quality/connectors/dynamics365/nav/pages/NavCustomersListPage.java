package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessCustomerCardPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the customers list page
 *
 * @author bhikshapathi
 */
public class NavCustomersListPage extends NavBasepage
{
    protected By dialogBoxLoc = By.className("ms-nav-content-box");

    protected By newButtonLoc = By.cssSelector("a[aria-label='New'][title='Create a new entry.']");
    protected By businessToBusinessLoc = By.cssSelector("a[aria-label*='Business-to-Business Customer']");
    protected By retailCustomerLoc = By.cssSelector("a[aria-label*='Cash-Payment / Retail Customer']");
    protected By customerGridLoc = By.cssSelector("table[id*='_BusinessGrid']");
    protected By customerRowsLoc = By.cssSelector("table tbody tr");
    protected By customerRowSegmentLoc = By.cssSelector("tr td");
    protected By openLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");
    protected By customersSearch = By.cssSelector("input[aria-label='Search Customers']");
    protected By custSearch = By.cssSelector("input[aria-label*='Search CRONUS USA, Inc., Sales, Customers']");
    protected By customersSearch2 = By.xpath("(//div/i[@data-icon-name='Search'])[2]");
    protected By customersSearch1 = By.xpath("//div[@class='content-header-actions']/div/a[@title='Search']");
    protected By custoSearch = By.className("ms-nav-searchFilter");
    protected By customersButtonLoc = By.xpath("(//span[contains(.,'Customers')])[10]");
    protected By salesButtonLoc = By.xpath("(//i[@class='icon-DownCaret-after'])[18]");
    protected By nameOpenMenu = By.xpath("(//th[@abbr='Name'])[4]");
    protected By nameOpenMenusec = By.xpath("(//th[@title='Name']/div/a)[4]");
    protected By documentFilter = By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By documentSearch = By.xpath("//div/p[contains(.,'Only show lines where \"Name\" is')]/../../../../div/div/div/input");
    protected By submitingButton = By.xpath("//button/span[contains(.,'OK')]");
    protected By firstCustomer = By.xpath("(//tbody/tr[1]/td[3]/a[@tabindex='0'])");


    public NavCustomersListPage( WebDriver driver ) { super(driver); }

    /**
     * clicks on the +New button and selects business-to-business customer
     *
     * @return the customer card page
     */
    public NavCustomerCardPage clickNewBusinessToBusinessCustomer( )
    {
        waitForPageLoad();
        List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
        WebElement newButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(newButton);

        WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement busToBusType = wait.waitForElementDisplayed(businessToBusinessLoc, dialogBox);

        click.clickElementCarefully(busToBusType);

        waitForPageLoad();
        return initializePageObject(NavCustomerCardPage.class);
    }

    /**
     * clicks on the +New button and selects cash-payment/retail customer
     *
     * @return the customer card page
     */
    public NavCustomerCardPage clickNewRetailCustomer( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
        WebElement newButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(newButton);

        WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement retailType = wait.waitForElementDisplayed(retailCustomerLoc, dialogBox);

        click.clickElementCarefully(retailType);

        waitForPageLoad();
        return initializePageObject(NavCustomerCardPage.class);
    }

    /**
     * Gets a customer from the list based on row
     *
     * @param rowNum
     *
     * @return element of the row that contains clickable link and number
     */
    public WebElement getCustomerFromList( int rowNum )
    {
        rowNum--;
        List<WebElement> gridList = wait.waitForAllElementsPresent(customerGridLoc);
        WebElement grid = gridList.get(gridList.size() - 1);

        List<WebElement> gridRows = wait.waitForAllElementsPresent(customerRowsLoc, grid);
        WebElement itemRow = gridRows.get(rowNum);

        WebElement selectedCustomer = null;
        List<WebElement> segments = wait.waitForAllElementsPresent(customerRowSegmentLoc, itemRow);
        for ( WebElement segment : segments )
        {
            try
            {
                WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
                if ( ele
                        .getAttribute("title")
                        .contains("Open record") )
                {
                    selectedCustomer = ele;
                }
            }
            catch ( TimeoutException e )
            {

            }
        }

        return selectedCustomer;
    }

    /**
     * Gets the number of the customer in the specified row
     *
     * @return the customer's document number
     */
    public String getCustomerNumberByRowIndex()
    {
        WebElement customer = wait.waitForElementDisplayed(firstCustomer);

        String number = customer.getText();

        return number;
    }

    /**
     * Opens a customer displayed on the list page, based on specified row
     *
     * @return customer card page
     */
    public NavCustomerCardPage openCustomerByRowIndex( int rowIndex )
    {
        WebElement select = getCustomerFromList(rowIndex);

        click.clickElement(select);

        return initializePageObject(NavCustomerCardPage.class);
    }
    /**
     * Opens a customer displayed on the list page, based on customer Code
     * @author bhikshapathi
     *
     */
    public void  searchAndOpenCustomer( String customerCode)
    {
        waitForPageLoad();
        WebElement search = wait.waitForElementDisplayed(customersSearch1);
        click.javascriptClick(search);
        text.enterText(search,customerCode);
        waitForPageLoad();
        WebElement search1 = wait.waitForElementEnabled(custoSearch);
        text.enterText(search1,customerCode);
        String itemRow = String.format("//td/a[contains(text(),'%s')]", customerCode);
        click.clickElement(By.xpath(itemRow));
        waitForPageLoad();

    }
    /**
     * Clicks on filter Documents
     * to open the section
     */
    public void filterCustomers(String docNo)
    {
        WebElement documentOpenMenu = wait.waitForElementDisplayed(nameOpenMenu);
        click.moveToElementAndClick(documentOpenMenu);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch,docNo);
        click.clickElementCarefully(submitingButton);
        waitForPageLoad();
        String itemRow = String.format("(//td/span[@title='%s']/../../td/a)[1]", docNo);
        click.clickElement(By.xpath(itemRow));
    }
    /**
     * Clicks sales  link and navigate to the customers list page
     *@bhikshapathi
     */
    public void navigateToCustomers( )
    {
        WebElement button = wait.waitForElementEnabled(salesButtonLoc);
        click.clickElementCarefully(button);
        waitForPageLoad();
        WebElement customerbutton = wait.waitForElementEnabled(customersButtonLoc);
        click.clickElementCarefully(customerbutton);
        waitForPageLoad();
    }
    /**
     * Opens a customer displayed on the list page, based on customer Code
     * @author bhikshapathi
     *
     */
    public void  searchForCustomer( String customerCode)
    {
        waitForPageLoad();
        WebElement search = wait.waitForElementEnabled(customersSearch2);
        click.javascriptClick(search);
        WebElement search1 = wait.waitForElementEnabled(custSearch);
        text.enterText(search1,customerCode);
        String itemRow = String.format("//td/a[contains(text(),'%s')]", customerCode);
        click.clickElement(By.xpath(itemRow));
        waitForPageLoad();

    }
}
