package com.vertex.quality.connectors.sitecorexc.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Homepage of SiteCoreXc Admin.
 *
 * @author Vivek.Kumar
 */
public class SiteCoreXCAdminHomePage extends VertexPage {
    public SiteCoreXCAdminHomePage(WebDriver driver) {
        super(driver);
    }

    protected By healthCheck = By.xpath("//span[contains(text(),'Vertex Health Check')]");
    protected By healthCheckStatus = By.xpath("//p[contains(text(),'Vertex O Series')]");

    /**
     * clicks the vertex health Check Icon for the SiteCoreXC connector.
     */
    public void clickVertexHealthCheck() {
        WebElement healthCheckIcon = wait.waitForElementDisplayed(healthCheck);
        click.clickElementCarefully(healthCheckIcon);
    }

    /**
     * Checks the successful message in admin page while health check.
     *
     * @return health check message field true or false
     */
    public boolean checkHealthCheckMessage() {
        WebElement healthStatusMessageField = wait.waitForElementDisplayed(healthCheckStatus);
        boolean checkHealthCheckMessageField = healthStatusMessageField.isDisplayed();
        return checkHealthCheckMessageField;
    }

}
