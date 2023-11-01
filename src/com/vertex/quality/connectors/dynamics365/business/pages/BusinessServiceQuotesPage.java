package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessServiceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the service quote page
 */
public class BusinessServiceQuotesPage extends BusinessServiceBasePage {
    protected By quoteLinkForLineItem=By.xpath("(//span[text()='Quote'])[3]");
    protected By serviceLines=By.xpath("//span[text()='Service Lines']");


    public BusinessServiceQuotesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks the Quote and Service Lines for the line items
     */
    public void clickQuoteAndServiceLines(){
        wait.waitForElementDisplayed(quoteLinkForLineItem);
        click.clickElementCarefully(quoteLinkForLineItem);

        wait.waitForElementDisplayed(serviceLines);
        click.clickElementCarefully(serviceLines);
    }
}
