package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * ERP Purchase Requisitions hub page.
 *
 * @author msalomone
 */
public class OracleCloudProcurementPurchaseReqsPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By purchaseReqsHeaderLoc = By.xpath("//table[contains(@id, 'pt1:pgl4')]/tbody/tr/td[3]");
    protected By linksTag = By.tagName("A");

    public OracleCloudProcurementPurchaseReqsPage( WebDriver driver ) { super(driver); }

    /**
     * Click the Manage Requisitions button at the top-right section of
     * the Purchase Requisitions page.
     * @param page A PurchaseReqs page object.
     *
     * @return a Manage Requisitions page.
     */
    public <T extends OracleCloudBasePage> T clickManageReqsButton(OracleCloudPageNavigationData page)
    {
        WebElement header = wait.waitForElementDisplayed(purchaseReqsHeaderLoc);

        List<WebElement> headerLinkList = wait.waitForAllElementsDisplayed(linksTag, header);
        WebElement link = element.selectElementByText(headerLinkList, page.getPageName());

        wait.waitForElementEnabled(link);
        wait.waitForElementNotEnabled(blockingPlane);
        click.clickElementCarefully(link);

        try {
            wait.waitForElementNotDisplayedOrStale(link, 15);
        } catch (TimeoutException e) {
            link.click();
            wait.waitForElementNotDisplayedOrStale(link, 10);
        }

        Class pageClass = page.getPageClass();

        return initializePageObject(pageClass);
    }
}
