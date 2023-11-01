package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavSalesCreditMemoListPage extends NavBasepage {
    protected By NoHeading=By.xpath("(//th[@abbr='No.'])[1]");
    protected By documentFilter=By.xpath("(//a[@title='Filter...' and @class='ms-nav-ctxmenu-itemlink'])");
    protected By documentSearch=By.xpath("//div/p[contains(.,'Only show lines where \"No.\" is')]/../../../div/div/input");
    protected By ok = By.xpath("//span[contains(.,'OK')]");

    public NavSalesCreditMemoListPage(WebDriver driver) { super(driver); }

    /**
     * filters the document number on document list page of doc types
     * @param docNo
     */
    public void filterDocuments(String docNo) {
        WebElement openMenu = wait.waitForElementDisplayed(NoHeading);
        click.moveToElementAndClick(openMenu);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch = wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch, docNo);
        click.clickElementCarefully(ok);
        String itemNoLink=String.format("//a[@title='Open record \"%s\" in a new window']",docNo);
        click.clickElementCarefully(By.xpath(itemNoLink));
        waitForPageLoad();
    }
}
