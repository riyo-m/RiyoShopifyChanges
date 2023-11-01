package com.vertex.quality.connectors.dynamics365.business.pages.accountpayable;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BusinessPurchaseOrdersListPage extends BusinessBasePage {
    protected By searchIcon = By.xpath("//div/i[@data-icon-name='Search']");
    protected By searchInputField=By.cssSelector("input[aria-label*='Search Purchase Orders']");
    protected By firstElement = By.xpath("//td[@controlname='No.']/a[contains(@title,'Open record') and not(@tabindex='-1')]");

    public BusinessPurchaseOrdersListPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets a document from the list
     * @param docNo
     */
    public void searchForPurchaseDocument(String docNo){
        waitForPageLoad();
        WebElement search=wait.waitForElementEnabled(searchIcon);
        click.clickElementCarefully(search);
        WebElement searchField=wait.waitForElementEnabled(searchInputField);
        text.enterText(searchField,docNo);
        text.pressEnter(searchField);
        WebElement doc=wait.waitForElementDisplayed(firstElement);
        click.clickElementCarefully(doc);
        waitForPageLoad();

    }

}
