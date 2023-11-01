package com.vertex.quality.connectors.oseriesEdge.pages;

import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;


import java.util.List;

/**
 * Common functions for anything related to Oseries Edge Template Page.
 *
 * @author Laxmi Lama-Palladino
 */

public class OseriesEdgeTemplatePage extends OseriesEdgeBasePage {


    protected By ADD_TEMPLATE = By.xpath("//button[@name='add_template']");
    protected By SEARCH_TEMPLATE_NAME = By.xpath("//*[@placeholder='Search Template Name..']");
    protected By ADD_TEMP_NAME = By.xpath("//*/input[@placeholder='Template Name']");
    protected By IMPOSITION = By.xpath("(//div[@class='ant-select-selector'])[4]");
    protected By IMPOSITION_TYPES = By.xpath(".//div[@class='ant-select-item-option-content']");
    protected By JURISDICTION_BOX = By.xpath("//div[@class='ant-row jurisdictions_search_box']");
    protected By JURISDICTION_TYPES = By.xpath("//div[239]//span[3]//span[1]");
    protected By TAX_LOCATION = By.xpath("(//input[@class='ant-checkbox-input'])[2]");
    protected By TAX_BOX = By.xpath("(//span[@class='ant-checkbox-inner'])[3]");
    protected By GIS_MILES = By.xpath("(//input[@class='ant-input vtx-input location-radius'])");
    protected By ENGINE_VERSION = By.xpath("(//div[@class='ant-select-selector'])[5]");
    protected By LATEST_ENGINE = By.xpath("(//div[@class='rc-virtual-list-holder-inner']//div/descendant::div[@class='ant-select-item-option-content'])");
    protected By TEE_LOCATION = By.xpath("(//div[@class='ant-select-selector'])[6]");
    protected By TEE_STORED_NAME = By.xpath("(//div[@class='rc-virtual-list-holder-inner']//div/descendant::div[@class='ant-select-item-option-content'])");
    protected By TEE_FILE_DROPDOWN = By.xpath("//span[contains(@title,'latest')]");
    protected By SELECT_TEE = By.xpath("(//div[@class='rc-virtual-list-holder-inner']//div/descendant::div[@class='ant-select-item-option-content'])");
    protected By TEE_UPLOAD_BUTTON = By.xpath("//button[@class='ant-btn ant-btn-default vtx-button vtx-btn-default upload-Btn']");
    protected By TEE_UPLOAD_LOCATION = By.xpath("//input[@type='file']");
    protected By UPLOAD_TEE = By.xpath("(//button[@data-automation-class='vtx-button']//span[contains(text(),'Upload')])[2]");
    protected By MANAGE_FILE = By.xpath("//*[contains(text(),'Manage Files')]");
    protected By JOURNAL_AUTOMATION = By.xpath("(//*[@type='checkbox']//following::input[9])[1]");
    protected By REPOSITORY_TYPE = By.xpath("(//div[@class='ant-select-selector'])[8]");
    protected By DOCKER_TYPE = By.cssSelector("div[title='Docker Hub'] div[class='ant-select-item-option-content']");
    protected By DOCKER_NAME = By.xpath("//input[@placeholder='Repository Name']");
    protected By DOCKER_URL = By.xpath("//input[@placeholder='Repository URL']");
    protected By DOCKER_USERNAME = By.xpath("//input[@placeholder='Username']");
    protected By DOCKER_PASSWORD = By.xpath("//input[@placeholder='Password']");
    protected By AUTO_PUSH_REPOSITORY = By.xpath("(//input[@name='radioGroup'])[3]");
    protected By SAVE_TEMPLATE_BUTTON = By.xpath("//button[@type='submit']");
    protected By TELEMETRY_DATA = By.xpath("(//input[@name='radioGroup'])[5]");
    protected By SEARCH_ICON = (By.cssSelector(".ant-input"));
    protected By EDIT_TEMPLATE = (By.linkText("Edit"));

    public String tempName = "AppleTest";

    public OseriesEdgeTemplatePage(WebDriver driver)
    {
        super(driver);
    }

    /**
     * Verify OseriesEdge Add Template is visible
     */
    public boolean getOseriesEdgeAddTemplateIsVisible( )
    {
        waitForPageLoad();
        return element.isElementDisplayed(ADD_TEMPLATE);
    }

    /**
     * Clicks the OseriesEdge Add TemplateButton
     */
    public void goToTemplateButton() {
        wait.waitForElementDisplayed(ADD_TEMPLATE);
        click.clickElementCarefully(ADD_TEMPLATE);
    }
    /**
     * Click and add Template Name after add template button
     * @return
     */
    public String templateName()
    {
       WebElement tempNameToBeAdded = wait.waitForElementPresent(ADD_TEMP_NAME);
       tempNameToBeAdded.sendKeys(tempName);
       return tempName;

    }
    /**
     * Click the imposition and select the types
     * @return
     */
    public boolean imposition()
    {
        WebElement impositionAdd = wait. waitForElementEnabled(IMPOSITION);
        impositionAdd.click();
        wait.waitForElementPresent(IMPOSITION_TYPES);
        return true;
    }
    /**
     * Select the imposition type
     * @return
     */
    public boolean impType()
    {
        wait.waitForElementPresent(IMPOSITION_TYPES);
        jsWaiter.sleep(1000);
        List<WebElement> impDropdown = driver.findElements(IMPOSITION_TYPES);

        for(WebElement impDrop : impDropdown){
            if(impDrop.getText().equals("General Sales and Use Tax"))
            {
                wait.waitForElementPresent(IMPOSITION_TYPES);
                Actions dropClick = new Actions(driver);
                dropClick
                        .moveToElement(impDrop)
                        .click()
                        .build()
                        .perform();
                return true;
            }
        }
        return false;
    }
    /**
     * Click on USA Jurisdiction
     */
    public boolean jurisdiction() {

        wait.waitForElementPresent(JURISDICTION_BOX);
        click.clickElementCarefully(JURISDICTION_BOX);
        WebElement element = driver.findElement(JURISDICTION_TYPES);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element);
        click.javascriptClick(JURISDICTION_TYPES);

          return true;

    }
    /**
     * Click on taxpayer location and add miles
     */
    public boolean taxLocation()
    {
        wait.waitForElementPresent(TAX_LOCATION);
        click.moveToElementAndClick(TAX_LOCATION);

        wait.waitForElementPresent(TAX_BOX);
        click.moveToElementAndClick(TAX_BOX);

        WebElement miles = wait.waitForElementPresent(GIS_MILES);
        String st = Keys.chord(Keys.CONTROL, "20");
        miles.sendKeys(st);
        miles.sendKeys(Keys.BACK_SPACE);
        miles.sendKeys(Keys.BACK_SPACE);

        miles.sendKeys("10");
        return true;

    }
    /**
     * CLick on engine version and select latest
     */
    public boolean engineVersion() {

        wait.waitForElementPresent(ENGINE_VERSION);
        click.clickElementCarefully(ENGINE_VERSION);
        List<WebElement> versionList = driver.findElements(LATEST_ENGINE);

        for(int i=0; i<versionList.size(); i++)
        {
            if(versionList.get(i).getText().contains("9.0.6.0.47")){
                versionList.get(i).click();
                break;
            }
        }
        return true;
    }
    /**
     * Get the storage location for TEE
     */
    public boolean storageForTeeFile() {

        wait.waitForElementPresent(TEE_LOCATION);
        click.clickElementCarefully(TEE_LOCATION);
        List<WebElement> teeStore = driver.findElements(TEE_STORED_NAME);

        for (int i = 0; i < teeStore.size(); i++) {
            teeStore.get(i).getText();
            if (teeStore.get(i).getText().contains("laxmi")) {
                teeStore.get(i).click();
                break;
            }
        }
        return true;
    }
    /**
     * Get the storage location for TEE
     */
    public boolean teeExtractDropDown() {

        wait.waitForElementPresent(TEE_FILE_DROPDOWN);
        click.javascriptClick(TEE_FILE_DROPDOWN);

        List<WebElement> teeExtract = driver.findElements(SELECT_TEE);

        for (WebElement webElement : teeExtract) {
            webElement.getText();
            if (webElement.getText().contains("perf_poc_2_20211130110624.zip")) {
                webElement.click();
                break;
            }
        }
        return true;
    }
    /**
     * CLick on the upload button and upload the TEE File.
     */
    public boolean uploadTeeFile() {
        wait.waitForElementPresent(TEE_UPLOAD_BUTTON);
        click.javascriptClick(TEE_UPLOAD_BUTTON);

        WebElement file = wait.waitForElementPresent(TEE_UPLOAD_LOCATION);
       //file.sendKeys("C:\\Users\\laxmi.lamapalladino\\Downloads\\perf_poc_2_20211130110624.zip");
        file.sendKeys("C:\\ConnectorDev\\ConnectorQuality\\resources\\zipfile\\oseriesEdge\\Acme-IL_ID_WA_Test.zip");

        WebElement fileUpload = wait.waitForElementPresent(UPLOAD_TEE);
        fileUpload.click();
        jsWaiter.sleep(15000);
        return true;
    }
    /**
     * CLick on Manage File
     */
    public boolean manageFile(){
        wait.waitForElementPresent(MANAGE_FILE);
        click.javascriptClick(MANAGE_FILE);
        return true;
    }
    /**
     * Click on disable for Journal automation
     */
    public boolean journalAutomationEnable(){
        wait.waitForElementPresent(JOURNAL_AUTOMATION);
        click.javascriptClick(JOURNAL_AUTOMATION);
        return true;
    }
    /**
     * Click on Docker for Repository type
     */
    public boolean selectRepository(){
        wait.waitForElementPresent(REPOSITORY_TYPE);
        click.javascriptClick(REPOSITORY_TYPE);

        return true;
    }
    /**
     * fill up docker credential after it is enabled
     */
    public boolean dockerInformation(){

        wait.waitForElementPresent(REPOSITORY_TYPE);
        click.clickElementCarefully(REPOSITORY_TYPE);
        wait.waitForElementPresent(DOCKER_TYPE);
        click.javascriptClick(DOCKER_TYPE);

        WebElement dockName = wait.waitForElementPresent(DOCKER_NAME);
        click.javascriptClick(DOCKER_NAME);
        dockName.sendKeys("dockerName");
        WebElement dockUrl = wait.waitForElementDisplayed(DOCKER_URL);
        dockUrl.sendKeys("registry.docker.hub/dockerUrl");
        WebElement docUsename = wait.waitForElementPresent(DOCKER_USERNAME);
        docUsename.sendKeys("dockerUsername");
        WebElement docPassword = wait.waitForElementPresent(DOCKER_PASSWORD);
        docPassword.sendKeys("dockerPassword");

        return true;
    }

    /**
     * Click on yes for auto push repository
     */
    public boolean autoPushRepository(){
        wait.waitForElementPresent(AUTO_PUSH_REPOSITORY);
        click.clickElement(AUTO_PUSH_REPOSITORY);
        return true;
    }
    /**
     * click no for Telemetry data return
     */
    public boolean telemetryData(){
        wait.waitForElementPresent(TELEMETRY_DATA);
        click.clickElement(TELEMETRY_DATA);
        return true;
    }
    /**
     * Click on Save Template Button
     */
    public boolean saveTemplateButton(){
        wait.waitForElementPresent(SAVE_TEMPLATE_BUTTON);
        click.clickElement(SAVE_TEMPLATE_BUTTON);
        return true;
    }
    /**
     * Verify OseriesEdge Search Template Name is visible
     */
    public boolean getOseriesEdgeSearchTemplateNameBoxIsVisible()
    {
        waitForPageLoad();
        return element.isElementDisplayed(SEARCH_ICON);
    }
    /**
     * User is able to type Template name and edit field
     */
    public boolean typeTemplateNameBox()
    {

        WebElement tempName = wait.waitForElementPresent(SEARCH_TEMPLATE_NAME);
        tempName.sendKeys("qaDockerTest");
        text.pressEnter(tempName);

        WebElement editTemp = wait.waitForElementPresent(EDIT_TEMPLATE);
        editTemp.click();

        WebElement clickDisable = wait.waitForElementPresent(By.xpath("(//input[@name='radioGroup'])[2]"));
        clickDisable.isSelected();
        wait.waitForElementPresent(SAVE_TEMPLATE_BUTTON);
        click.clickElement(SAVE_TEMPLATE_BUTTON);

        return true;

    }

    /**
     * User can click on the search icon
     */
    public void templateFound() {

        WebElement iconImage = wait.waitForElementDisplayed(SEARCH_ICON);
        click.clickElementCarefully(iconImage);

    }
}