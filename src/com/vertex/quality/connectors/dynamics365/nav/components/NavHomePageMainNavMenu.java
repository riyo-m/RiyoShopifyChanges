package com.vertex.quality.connectors.dynamics365.nav.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.nav.enumes.NavPageTitles;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * this class represents the main navigation menu on the home page
 *
 * @author bhikshapathi
 */
public class NavHomePageMainNavMenu extends VertexComponent
{
    protected By subMenuParentConLoc = By.className("navigation-bar-secondary-group-over-expanded");
    protected By subMenuConLoc = By.className("horizontal-container--1YXMnNmkMLMjmLEal1LgT_");
    protected By mainMenuParentConLoc = By.className("nav-bar-content");
    protected By mainNavTabLoc = By.className("caption--20PrttGVw_09n9_mM8YKgm");
    protected By mainFrameLoc = By.className("designer-client-frame");
    protected By navbarCaption = By.className("ms-navbar-node-caption");
    protected By buttonSubTextContainer = By.className("ms-nav-navpane-haschildren");
    protected By buttonSubText = By.className("ms-nav-navpane-nochildren");
    protected By navbarCaptionTab = By.xpath("//span[@data-automationid='splitbuttonprimary']");
    ArrayList<String> ext = new ArrayList<>(Arrays.asList("Posted Sales Credit Memos","Posted Sales Invoices","Posted Sales Return Receipts"));

    public NavHomePageMainNavMenu(WebDriver driver, VertexPage parent )
    {
        super(driver, parent);
    }
    /**
     * navigates to the first layer navigation menus
     *
     * @param tabTitle name of the first menu to navigate to
     */
    public void goToNavTab( String tabTitle )
    {
        try
        {
            wait.waitForElementPresent(mainFrameLoc, 10);
        }
        catch ( TimeoutException e )
        {
            driver
                    .navigate()
                    .refresh();
            waitForPageLoad();
        }
        driver
                .switchTo()
                .frame(wait.waitForElementPresent(mainFrameLoc, 30));

        List<WebElement> navTabs = wait.waitForAllElementsPresent(navbarCaption);
        for ( WebElement tab : navTabs )
        {
            String tabText = tab.getText();

            if ( tabText.equals(tabTitle) )
            {
                click.clickElementCarefully(tab);
                break;
            }
        }
    }

    /**
     * navigates to the first layer navigation menus
     *
     * @param tabTitle name of the first menu to navigate to
     */
    public void goToNavTab1( String tabTitle )
    {
        List<WebElement> navTabs = wait.waitForAllElementsPresent(navbarCaptionTab);
        for ( WebElement tab : navTabs )
        {
            String tabText = tab.getText();
            if ( tabText.equals(tabTitle) )
            {
                click.clickElementCarefully(tab);
                break;
            }
        }
    }

    /**
     *This Method is for "Posted Invoice", "Posted Memo"
     *
     * @param tabTitle name of the first menu to navigate to
     */
    public void goToSubNavTabFromDocumentType( String documentType,String tabTitle )
    {
        List<WebElement> navTabs = wait.waitForAllElementsPresent(getByObjOfPostedPage(documentType));
        for ( WebElement tab : navTabs )
        {
            String tabText = tab.getText();
            System.out.println(tabText);
            if ( tabText.equals(tabTitle) )
            {
                click.clickElementCarefully(tab);
                break;
            }
        }
    }
    /**
     * navigates to a sub menu from a main one in the main nav panel
     *
     * @param subNavTabTitle name of navigation link to click in submenu
     *
     * @return the class of the page accessed after the navigation
     */
    public <T extends NavBasepage> T goToSubNavTab(String subNavTabTitle )
    {
        List<WebElement> subMenuTabsButtons = wait.waitForAllElementsPresent(buttonSubTextContainer);

        for ( WebElement subMenuTabButton : subMenuTabsButtons )
        {
            String subMenuText = subMenuTabButton.getText();

            if ( subMenuText.equals(subNavTabTitle) )
            {
                click.javascriptClick(subMenuTabButton);
                break;
            }
        }
        jsWaiter.waitForLoadAll();
        return initializePageObject(getPage().getPageClass());
    }

    public String normalize(String raw) {

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(raw);
        while (scanner.hasNext()) {
            sb.append(scanner.next());
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    /**
     * navigates to a sub menu from a main one in the main nav panel
     *
     * @param subNavTabTitle name of navigation link to click in submenu
     *
     * @return the class of the page accessed after the navigation
     */
    public <T extends NavBasepage> T goToChildSubNavTab(String subNavTabTitle )
    {
        List<WebElement> subMenuTabsButtons = wait.waitForAllElementsPresent(buttonSubText);

        for ( WebElement subMenuTabButton : subMenuTabsButtons )
        {
            String subMenuText = subMenuTabButton.getText();

            if ( subMenuText.equals(subNavTabTitle) )
            {
                click.clickElementCarefully(subMenuTabButton);
                break;
            }
        }
        jsWaiter.waitForLoadAll();
        return initializePageObject(getPage().getPageClass());
    }

    /**
     * navigates to a sub menu from a main one in the main nav panel
     *
     * @param documentType   name of tab whose submenu to open
     * @param subTabTitle Sub Tab Title
     * @param subNavTabTitle name of navigation link to click in submenu
     *
     * @return the class of the page accessed after the navigation
     */
    public <T extends NavBasepage> void goToChildSubNavTab1(String documentType, String subTabTitle, String subNavTabTitle )
    {
        goToSubNavTabFromDocumentType(documentType,subTabTitle);

        List<WebElement> subMenuTabsButtons = wait.waitForAllElementsPresent(buttonSubTextContainer);

        for ( WebElement subMenuTabButton : subMenuTabsButtons )
        {
            String subMenuText = subMenuTabButton.getText();

            if ( subMenuText.equals(subNavTabTitle) )
            {
                click.clickElementCarefully(subMenuTabButton);
                break;
            }
        }
    }
    /**
     * It is Getting By object for Given Page Name
     * @param pageName
     * @return By obj of Posted page
     */
    public By getByObjOfPostedPage(String pageName)
    {
        String tabByObj = String.format("//form[contains(@aria-label,'%s')]//span[@data-automationid='splitbuttonprimary']",pageName);
        return By.xpath(tabByObj);
    }
    /**
     * Gets the page title enum
     *
     * @return the page title enum
     */
    private NavPageTitles getPage( )
    {
        NavPageTitles page = null;
        String CurrentPageTitle = parent.getPageTitle();
        for ( NavPageTitles pageTitle : NavPageTitles.values() )
        {
            if ( pageTitle
                    .getPageTitle()
                    .equals(CurrentPageTitle) )
            {
                page = pageTitle;
            }
        }
        return page;
    }
    /**
     * navigates to a sub menu from a main one in the main nav panel
     *
     * @param subNavTabTitle name of navigation link to click in submenu
     *
     * @return the class of the page accessed after the navigation
     */
    public <T extends NavBasepage> T goToSubNavTabForSalesOrder(String subNavTabTitle )
    {
        waitForPageLoad();
        List<WebElement> subMenuTabsButtons = wait.waitForAllElementsPresent(buttonSubTextContainer);

        for ( WebElement subMenuTabButton : subMenuTabsButtons )
        {
            String subMenuText = subMenuTabButton.getText();

            if ( subMenuText.equals(subNavTabTitle) )
            {
                click.clickElementCarefully(subMenuTabButton);
                break;
            }
        }
        jsWaiter.waitForLoadAll();
        return initializePageObject(getPage().getPageClass());
    }
}
