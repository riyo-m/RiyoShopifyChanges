package com.vertex.quality.connectors.dynamics365.sales.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the base page for the sales environment pages
 *
 * @author Shruti, v-olumbe
 */
public class SalesBasePage extends VertexPage {

    protected By loadingClass = By.className("progressDot");
    protected By loadingIndicator = By.id("app-loading-indicator");
    protected By fullScreenButton = By.xpath("//button[@title='Enter full screen mode']");
    protected By signInButton1 = By.xpath("//button[@aria-label='Sign in']");
    protected By signInButton2 = By.xpath("//button[@aria-label='Sign In']");
    protected By confirmButton = By.xpath("//button[@data-id='confirmButton']");
    protected By confirmButton2 = By.id("confirmButton_2");
    public SalesBasePage(WebDriver driver) {
        super(driver);}

    public void waitForLoadingScreen()
    {
        waitForPageLoad();

        jsWaiter.sleep(5000);

        if (element.isElementDisplayed(signInButton1)) {
            click.clickElementIgnoreExceptionAndRetry(signInButton1);
        }

        if (element.isElementDisplayed(signInButton2)) {
            click.clickElementIgnoreExceptionAndRetry(signInButton2);
        }

        jsWaiter.sleep(1000);

        if (element.isElementDisplayed(confirmButton)) {
            click.clickElementIgnoreExceptionAndRetry(confirmButton);
        }

        if (element.isElementDisplayed(confirmButton2)) {
            click.clickElementIgnoreExceptionAndRetry(confirmButton2);
        }

        wait.waitForElementNotDisplayed(loadingIndicator);
        wait.waitForElementNotDisplayed(loadingClass);
    }

    public void navigateToApp (String from, String to) {
        waitForPageLoad();

        By currentAppTitle = By.xpath(String.format("//a[@aria-label='%s']", from));
        click.clickElementIgnoreExceptionAndRetry(currentAppTitle);

        wait.waitForElementEnabled(fullScreenButton);
        click.clickElementIgnoreExceptionAndRetry(fullScreenButton);

        waitForPageLoad();

        WebElement container = wait.waitForElementDisplayed(By.id("AppLandingPage"));
        driver.switchTo().frame(container);

        By appLoc = By.xpath(String.format("//*[@title='%s']", to));
        click.clickElementIgnoreExceptionAndRetry(appLoc);

        waitForPageLoad();
        waitForLoadingScreen();
    }


}
