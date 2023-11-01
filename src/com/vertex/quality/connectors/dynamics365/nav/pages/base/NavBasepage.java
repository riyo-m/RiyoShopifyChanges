package com.vertex.quality.connectors.dynamics365.nav.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.nav.components.NavCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.nav.components.NavHomePageMainNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.components.NavPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavSalesOrderListPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavVertexDiagnosticsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NavBasepage extends VertexPage
{
    public NavHomePageMainNavMenu mainNavMenu;
    public NavPagesNavMenu pageNavMenu;

    protected By loadingClass = By.className("splash");
    protected By mainFrameLoc = By.className("designer-client-frame");
    protected By dialogBoxLoc = By.className("task-dialog-content-alignbox");
    protected By actions = By.xpath("//span[text()='Actions']");
    protected By customer = By.xpath("//span[text()='Customer']");
    protected By topBarCon = By.id("mouse has-product-menu-bar");
    protected By topBarCon2 = By.id("systemaction-container");
    protected By searchBoxCon = By.className("ms-dyn365-fin");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");

    protected By searchButtonFieldLoc1 = By.xpath("(//a[@title='Search for Page or Report...'])[2]");
    protected By searchForAdmin = By.xpath("//a[@title='Search for Page or Report...']");
    protected By searchButtonFieldLoc = By.cssSelector(".icon-PageSearchSmall");

    protected By searchBarFieldLoc = By.xpath("//div/a[contains(.,'Type page or report name to start search:')]/../div/input");
    protected By searchResultArrow = By.className("icon-RightCaret");
    protected By searchResult = By.xpath("(//div[contains(.,'Vertex Admin')])[34]");
    protected By searchResutCon = By.cssSelector("div[class*='ms-itemContent--']");
    protected By searchResultOfTaxGroups = By.xpath("//td/a[@title='Open Tax Groups']");
    protected By searchResultOfAdmin = By.xpath("//td/a[@title='Open Vertex Admin']");
    protected By searchResultOfDiagnostics = By.xpath("//td/a[@title='Open Vertex Diagnostics']");
    protected By searchResultOfSalesOrder = By.xpath("//td/a[@title='Open Sales Orders'][@tabindex='0']");
    protected  By searchResultOfCompany= By.xpath("//td/a[@title='Open Company Information']");
    protected By buttonLoc = By.tagName("button");

    public NavBasepage( WebDriver driver )
    {
        super(driver);
        this.mainNavMenu = new NavHomePageMainNavMenu(driver, this);
        this.pageNavMenu = new NavPagesNavMenu(driver, this);
    }

    /**
     * Waits for the loading screen, which covers the entire webpage,
     * to disappear
     * waitForPageLoad does not wait long enough for the cover to disappear
     */
    public void waitForLoadingScreen( )
    {
        wait.waitForElementNotPresent(loadingClass);
    }

    public void refreshAndWaitForLoad( )
    {
        refreshPage();
        waitForLoadingScreen();
    }

    public void switchFrame( )
    {
        try
        {
            driver
                    .switchTo()
                    .frame(wait.waitForElementPresent(mainFrameLoc, 30));
        }
        catch ( TimeoutException e )
        {

        }
    }
    /**
     * Navigates to the Vertex Diagnostics page by searching for it
     *
     * @return the Vertex Diagnostics page
     */
    public NavVertexDiagnosticsPage searchForAndNavigateToVertexDiagnosticsPage( )
    {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement searchButton = wait.waitForElementDisplayed(searchForAdmin);
        click.javascriptClick(searchButton);
        waitForPageLoad();
        WebElement searchBar=wait.waitForElementDisplayed(searchBarFieldLoc);
        click.javascriptClick(searchBar);
        waitForPageLoad();
        text.enterText(searchBar,"Vertex Diagnostics");
        WebElement vertexAdmin = wait.waitForElementPresent(searchResultOfDiagnostics);
        wait.waitForElementEnabled(vertexAdmin);
        click.clickElementCarefully(vertexAdmin);
        waitForPageLoad();

        waitForPageLoad();
        NavVertexDiagnosticsPage diagnosticsPage = new NavVertexDiagnosticsPage(driver);

        return diagnosticsPage;
    }
    /**
     * Navigates to the Vertex Admin Configurations page by searching for it
     *
     * @return Vertex Admin page
     */
    public NavVertexAdminPage navigatingToVertexAdminPage( )
    {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement searchButton = wait.waitForElementDisplayed(searchForAdmin);
        click.javascriptClick(searchButton);
        waitForPageLoad();
        WebElement searchBar=wait.waitForElementDisplayed(searchBarFieldLoc);
        click.javascriptClick(searchBar);
        waitForPageLoad();
        text.enterText(searchBar,"Vertex Admin");
        WebElement vertexAdmin = wait.waitForElementPresent(searchResultOfAdmin);
        wait.waitForElementEnabled(vertexAdmin);
        click.clickElementCarefully(vertexAdmin);
        waitForPageLoad();
        NavVertexAdminPage adminPage = new NavVertexAdminPage(driver);
        return adminPage;
    }

    /**
     * Navigates to the Vertex Admin Configurations page by searching for it
     *
     * @author bhikshapathi
     */
    public void searchAndNavigateToTaxGroupsPage( )
    {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc1);
        click.clickElement(searchButton);
        WebElement searchBar=wait.waitForElementDisplayed(searchBarFieldLoc);
        click.clickElementCarefully(searchBar);
        text.enterText(searchBar,"Tax Groups");
        WebElement taxGroups = wait.waitForElementPresent(searchResultOfTaxGroups);
        wait.waitForElementEnabled(taxGroups);
        click.clickElementCarefully(taxGroups);
        waitForPageLoad();
    }
    /**
     * Navigates to the Company Information page by searching for it
     * @return Company Information page
     */
    public NavCompanyInfoDialog navigateToCompanyInfo() {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc, 30);
        WebElement searchField = wait.waitForElementDisplayed(searchForAdmin);
        click.clickElementCarefully(searchField);
        WebElement searchBarField = wait.waitForElementDisplayed(searchBarFieldLoc);
        click.clickElementCarefully(searchBarField);
        text.enterText(searchBarField, "Company Information");
        WebElement companyResults=wait.waitForElementPresent(searchResultOfCompany);
        click.clickElementCarefully(companyResults);
        waitForPageLoad();
        NavCompanyInfoDialog cInfo=new NavCompanyInfoDialog(driver, this);
        return cInfo;
    }
    /**
     * Navigates to the Vertex Admin Configurations page by searching for it
     *
     * @return Vertex Admin page
     */
    public NavVertexAdminPage searchAndNavigateToVertexAdminPage( )
    {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc1);
        click.javascriptClick(searchButton);
        waitForPageLoad();
        WebElement searchBar=wait.waitForElementDisplayed(searchBarFieldLoc);
        click.javascriptClick(searchBar);
        waitForPageLoad();
        text.enterText(searchBar,"Vertex Admin");
        WebElement vertexAdmin = wait.waitForElementPresent(searchResultOfAdmin);
        wait.waitForElementEnabled(vertexAdmin);
        click.clickElementCarefully(vertexAdmin);
        waitForPageLoad();
        NavVertexAdminPage adminPage = new NavVertexAdminPage(driver);
        return adminPage;
    }
    /**
     * Navigates to the Vertex Admin Configurations page by searching for it
     *
     * @return Vertex Admin page
     */
    public NavSalesOrderListPage searchAndNavigateToSalesOrderPage( )
    {
        waitForPageLoad();
        window.switchToFrame(mainFrameLoc,30);
        WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc1);
        click.javascriptClick(searchButton);
        waitForPageLoad();
        WebElement searchBar=wait.waitForElementDisplayed(searchBarFieldLoc);
        click.javascriptClick(searchBar);
        waitForPageLoad();
        text.enterText(searchBar,"Sales Orders");
        WebElement vertexAdmin = wait.waitForElementPresent(searchResultOfSalesOrder);
        wait.waitForElementEnabled(vertexAdmin);
        click.clickElementCarefully(vertexAdmin);
        waitForPageLoad();
        NavSalesOrderListPage orderListPage = new NavSalesOrderListPage(driver);
        return orderListPage;
    }
    /**
     * Generalized method, should work on any popup box
     * Selects "OK"
     */
    public void dialogBoxClickOk( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Yes"
     */
    public void dialogBoxClickYes( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Yes");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Close"
     */
    public void dialogBoxClickClose( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Close");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Click on Actions
     */
    public void clickOnActionBar()
    {
        if(!element.isElementDisplayed(customer))
        {
            WebElement actionEle = wait.waitForElementDisplayed(actions);
            click.clickElementCarefully(actionEle);
        }
    }

}
