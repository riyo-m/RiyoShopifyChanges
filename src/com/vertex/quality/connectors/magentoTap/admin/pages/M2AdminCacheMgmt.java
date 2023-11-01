package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Cache Management Page
 *
 * @author alewis
 */
public class M2AdminCacheMgmt extends MagentoAdminPage {
    protected By flushMagentoCacheButtonId = By.id("flush_magento");

    protected By cacheTableRows = By.cssSelector("table[id='cache_grid_table'] tbody tr");
    protected By checkboxClass = By.className("admin__control-checkbox");
    protected By statusClass = By.cssSelector("[class*='grid-severity']");

    protected By completionMessageClass = By.className("message-success");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminCacheMgmt(WebDriver driver) {
        super(driver);
    }

    /**
     * Click the Flush Magento Cache button to flush the selected caches
     *
     * @return the message that appears when the flush has completed
     */
    public String clickFlushMagentoCacheButton() {
        WebElement flushMagentoCacheButton = wait.waitForElementEnabled(flushMagentoCacheButtonId);
        click.clickElement(flushMagentoCacheButton);

        WebElement message = wait.waitForElementDisplayed(completionMessageClass);
        String messageDisplay = message.getText();

        return messageDisplay;
    }

    /**
     * Clicks the checkmarks of all cache types with the invalidated status
     */
    public void checkInvalidatedCacheTypes() {
        String invalidated = "INVALIDATED";
        List<WebElement> rowsList = wait.waitForAllElementsDisplayed(cacheTableRows);

        for (WebElement row : rowsList) {
            WebElement cacheStatus = wait.waitForElementDisplayed(statusClass, row);
            String statusString = cacheStatus.getText();

            if (invalidated.equals(statusString)) {
                WebElement checkbox = wait.waitForElementEnabled(checkboxClass, row);
                click.clickElement(checkbox);
            }
        }
    }
}
