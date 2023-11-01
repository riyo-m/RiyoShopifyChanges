package com.vertex.quality.connectors.oseriesEdge.pages;

import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Common functions for anything related to Oseries Edge landing  Page.
 *
 * @author Laxmi Lama-Palladino
 */

public class OseriesEdgeHomePage extends OseriesEdgeBasePage {

    protected By TEMPLATE_TAB = By.xpath("//*/div/nav/ul/li/div/a[@class='menu-item-content'][contains(text(),'Templates')]");
    protected By SETTINGS_TAB = By.xpath("//*/div/nav/ul/li/div/a[@class='menu-item-content'][contains(text(),'Settings')]");
    protected By INSTANCES_TAB = By.xpath("//*/div/nav/ul/li/div/a[@class='menu-item-content'][contains(text(),'Instances')]");
    protected By IMAGE_TAB = By.xpath("//*/div/a[@data-testid='home']");
    protected By SELECT_TEMPLATE = By.cssSelector("[class='ant-select vtx-select template_dropdown ant-select-single ant-select-show-arrow']");
            //("(//div[@class='ant-select-selector'])[3]");
    protected By TEMPLATE_IMAGE = By.xpath//("(//div[@class='ant-select-item-option-content'])[11]");
            ("(//div[@class='ant-select-item-option-content'])[5]");
    protected By GENERATE_IMAGE = By.xpath("//button[@class='ant-btn ant-btn-primary vtx-button vtx-btn-primary']");
    protected By ACTION_BUTTON = By.xpath("//span[contains(text(),'Actions')]");
    protected By ACTION_PUSH = By.xpath("//span[contains(text(),'Push')]");
    protected By ACTION_DELETE = By.xpath("//span[contains(text(),'Delete')]");

    public OseriesEdgeHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Click on Template tab
     */
    public void goToTemplateTab(){
        wait.waitForElementDisplayed(TEMPLATE_TAB);
        click.clickElementCarefully(TEMPLATE_TAB);
    }

    /**
     * Verify OseriesEdge Add Settings button is visible
     */
    public boolean getOseriesEdgesettingsButtonIsVisible( )
    {
        waitForPageLoad();
        return element.isElementDisplayed(SETTINGS_TAB);
    }
    /**
     * Click on Settings tab
     */
    public void goToSettingsTab(){
        wait.waitForElementDisplayed(SETTINGS_TAB);
        click.clickElementCarefully(SETTINGS_TAB);
    }
    /**
     * Verify Instance Button is visible
     */
    public boolean getOseriesEdgeInstanceButtonIsVisible()
    {
        waitForPageLoad();
        return element.isElementDisplayed(INSTANCES_TAB);
    }
    /**
     * Click on Instances tab
     */
    public void goToInstancesTab(){
        wait.waitForElementDisplayed(INSTANCES_TAB);
        click.clickElementCarefully(INSTANCES_TAB);
    }

    /**
     * Clicks on the Image Tab
     * @author Laxmi LamaPalladino
     * @return
     */
    public boolean verifyOseriesImageTab(){
        waitForPageLoad();
        return element.isElementDisplayed(IMAGE_TAB);
    }
    /**
     * CLick on Image Tab
     * @author Laxmi Lama-Palladino
     */
    public void goToOseriesImageTab(){
        WebElement edgeImageButton = wait.waitForElementDisplayed(IMAGE_TAB);
        click.clickElementCarefully(edgeImageButton);
    }
    /**
     * Click on template name to generate image
     * @author Laxmi LamaPalladino
     */
    public boolean generateImage() throws InterruptedException {
        wait.waitForElementPresent(SELECT_TEMPLATE);
        click.clickElement(SELECT_TEMPLATE);

        wait.waitForElementPresent(TEMPLATE_IMAGE);
        click.javascriptClick(TEMPLATE_IMAGE);

        wait.waitForElementEnabled(GENERATE_IMAGE);
        click.javascriptClick(GENERATE_IMAGE);
        Thread.sleep(2000);

        wait.waitForElementPresent(By.xpath("(//span[contains(text(),'OK')])"));
        click.javascriptClick(By.xpath("(//span[contains(text(),'OK')])"));


        return true;
    }

    /**
     * This method verifies a user can generate an image container and delete it using action butto
     * @author Laxmi.LamaPalladino
     * @return
     */
    public boolean clickAction(){

        wait.waitForElementPresent(TEMPLATE_IMAGE);
        click.javascriptClick(TEMPLATE_IMAGE);
        wait.waitForElementPresent(TEMPLATE_IMAGE);
        click.javascriptClick(TEMPLATE_IMAGE);
        waitForPageLoad();
        wait.waitForElementPresent(ACTION_BUTTON);
        click.moveToElementAndClick(ACTION_BUTTON);
        wait.waitForElementPresent(ACTION_DELETE);
        click.javascriptClick(ACTION_DELETE);
        wait.waitForElementPresent(By.xpath("(//span[contains(text(),'OK')])"));
        click.javascriptClick(By.xpath("(//span[contains(text(),'OK')])"));

        return true;
    }


}