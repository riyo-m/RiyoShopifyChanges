package com.vertex.quality.connectors.dynamics365.business.pages.accountpayable;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BusinessVendorListsPage extends BusinessBasePage {
    protected By newButtonLoc = By.cssSelector("[aria-label='New'][title='Create a new entry.']");
    protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By businessToBusinessLoc = By.cssSelector("span[title*='Business-to-Business Vendor (Bank)']");

    public BusinessVendorListsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * clicks on the New button
     */
    public void clickNew() {
        WebElement button = wait.waitForElementDisplayed(newButtonLoc);
        click.javascriptClick(button);
        waitForPageLoad();
    }

    /**
     * selects business-to-business vendor
     * @return the vendor page
     */
    public BusinessVendorPage clickBusinessToBusinessVendor(){
        WebElement dialogBox=wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement businessSelection=wait.waitForElementDisplayed(businessToBusinessLoc,dialogBox);
        click.clickElementIgnoreExceptionAndRetry(businessSelection);
        waitForPageLoad();
        return initializePageObject(BusinessVendorPage.class);

    }
}
