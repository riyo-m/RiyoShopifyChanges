package com.vertex.quality.connectors.dynamics365.nav.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessEditSalesPageTitles;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.nav.enumes.NavEditSalesPageTitles;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the navigation menu on the first page accessed after the main navigation is done
 *
 * @author bhikshapathi
 */
public class NavPagesNavMenu extends VertexComponent
{   protected By newButtonLoc = By.cssSelector("a[title='Create a new entry.']");
    protected By searchButtonLoc = By.className("ms-SearchBox");
    protected By searchFieldLoc = By.cssSelector("input[aria-label='Search Customers']");
    public NavPagesNavMenu(WebDriver driver, VertexPage parent )
    {
        super(driver, parent);
    }

    /**
     * clicks the search button and inputs a document name or number to search for
     *
     * @param docNameOrNumber
     */
    public void searchForDocument( String docNameOrNumber )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(searchButtonLoc);
        WebElement newButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(newButton);

        WebElement inputField = wait.waitForElementDisplayed(searchFieldLoc);
        text.enterText(inputField, docNameOrNumber);
        text.pressEnter(inputField);
    }
    /**
     * clicks on the +New button
     *
     * @return the class of the page accesses by the click.
     */
    public <T extends NavSalesBasePage> T clickNew( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
        WebElement newButton = buttonList.get(buttonList.size() - 1);
        click.javascriptClick(newButton);
        waitForPageLoad();
        return initializePageObject(getPage().getPageClass());
    }
    /**
     * Gets the page title enum
     *
     * @return the page title enum
     */
    private NavEditSalesPageTitles getPage( )
    {
        NavEditSalesPageTitles page = null;
        String pageTitle = parent.getPageTitle();
        for ( NavEditSalesPageTitles title : NavEditSalesPageTitles.values() )
        {
            if ( title
                    .getPageTitle()
                    .equals(pageTitle) )
            {
                page = title;
            }
        }
        return page;
    }

}
