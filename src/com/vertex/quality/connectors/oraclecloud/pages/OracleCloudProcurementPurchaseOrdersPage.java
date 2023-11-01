package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OracleCloudProcurementPurchaseOrdersPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By purchaseOrdersTasksTabLoc = By.cssSelector("div[title='Tasks']");
    protected By purchaseOrdersMenuLoc = By.cssSelector("td[id*='FOTpd1::tabb']");
    protected By createOrderPopupLoc = By.cssSelector("div[id*='TaskPopup::content']");
    protected By linksTag = By.tagName("A");

    public OracleCloudProcurementPurchaseOrdersPage( WebDriver driver ) { super(driver); }

    /**
     * Opens the tasks menu by clicking the Tasks tab
     * on the right side of the screen.
     *
     * @return opened tasks menu.
     */
    public WebElement openTasksTab( )
    {
        WebElement menu;
        WebElement tasksTab;

        tasksTab = wait.waitForElementEnabled(purchaseOrdersTasksTabLoc);
        click.clickElement(tasksTab);
        menu = wait.waitForElementDisplayed(purchaseOrdersMenuLoc);

        return menu;
    }

    /**
     * Clicks on a link on the opened purchase orders tab menu to navigate to another PO page.
     *
     * @param page to navigate to
     *
     * @return initialized page that has been navigated to
     */
    public <T extends OracleCloudBasePage> T clickMenuLink(OracleCloudPageNavigationData page, WebElement menu )
    {
        List<WebElement> panelLinkList = wait.waitForAllElementsDisplayed(linksTag, menu);
        WebElement link = element.selectElementByText(panelLinkList, page.getPageName());

        wait.waitForElementEnabled(link);
        wait.waitForElementNotEnabled(blockingPlane);
        click.clickElementCarefully(link);

        if(page.getPageName().equals("Create Order")) {
            wait.waitForElementDisplayed(createOrderPopupLoc);
        }
        else {
            try {
                wait.waitForElementNotDisplayedOrStale(menu, 15);
            } catch (TimeoutException e) {
                link.click();
                wait.waitForElementNotDisplayedOrStale(menu, 10);
            }
        }

        Class pageClass = page.getPageClass();

        return initializePageObject(pageClass);
    }
}
