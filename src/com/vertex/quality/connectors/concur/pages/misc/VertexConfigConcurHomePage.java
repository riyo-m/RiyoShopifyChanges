package com.vertex.quality.connectors.concur.pages.misc;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the Home page of the Vertex Concur environment
 *
 * @author alewis
 */
public class VertexConfigConcurHomePage extends VertexPage {

    protected By invoiceBatchJobButton = By.id("startInvBatch");
    protected By statusInvoiceBatchJob = By.id("invStatus");
    protected By statusStartTime = By.id("invStartTime");
    protected By statusEndTime = By.id("invEndTime");
    protected By verifyConnector = By.xpath("//main[@class='content__container']//*[contains(text(),'VERTEX® TAX LINKS FOR SAP® CONCUR® System Status')]");
    protected By tokenStateID = By.id("tokState");
    protected By tokenScheduleID = By.id("tokJobSche");
    protected By invoiceJobScheduleID = By.id("invJobSche");
    protected By configDropdown = By.xpath("//button/span[contains(text(),'Configuration')]");
    protected By customFieldMapping = By.xpath("//li/a/span[contains(text(),'Custom Field Mapping')]");
    protected By taxPayerMapping = By.xpath("//li/a/span[contains(text(),'Vertex Taxpayer Mapping')]");
    protected By fieldLevelSelect = By.id("concurFieldLevel");
    protected By fieldNameSelect = By.id("concurFieldName");
    protected By vFlexFieldTypeSelect = By.id("vertexFieldType");
    protected By vFlexFieldSelect = By.id("vertexFieldName");
    protected By addFieldMappingButton = By.id("add");
    protected By lastFieldMappingDeleteButton = By.xpath("//*[@id=\"root\"]/div[2]/div/main/div/div/div[2]/table/tbody/tr[3]/td[5]/button");

    public VertexConfigConcurHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * click start invoice batch job
     */
    public void clickStartInvoiceJobButton() {
        waitForPageLoad();
        WebElement invoiceButton = wait.waitForElementDisplayed(invoiceBatchJobButton);
        click.clickElement(invoiceButton);
    }

    /**
     * get the status of invoice batch job
     *
     * @return statusValue
     */
    public String getStatusInvoiceBatchJob() {
        waitForPageLoad();
        WebElement jobStatus = wait.waitForElementDisplayed(statusInvoiceBatchJob);
        String statusValue = text.getElementText(jobStatus);
        return statusValue;
    }

    /**
     * verifies the text in home page
     *
     * @return statusValue
     */
    public boolean verifyVertexConcurText() {
        waitForPageLoad();
        boolean status = element.isElementDisplayed(verifyConnector);
        return status;
    }

    /**
     * verifies the job start time in home page
     *
     * @return jobTime true or false
     */
    public boolean verifyJobStartTime() {
        waitForPageLoad();
        boolean jobTime = element.isElementDisplayed(statusStartTime);
        return jobTime;
    }

    /**
     * verifies the job end time in home page
     *
     * @return jobTime true or false
     */
    public boolean verifyJobEndTime() {
        waitForPageLoad();
        boolean jobTime = element.isElementDisplayed(statusEndTime);
        return jobTime;
    }

    /**
     * gets the token status
     *
     * @return status of job
     */
    public String getTokenStatus() {
        WebElement tokenState = wait.waitForElementDisplayed(tokenStateID);
        String statusText= text.getElementText(tokenState);

        return statusText;
    }

    /**
     * Gets Token Job Schedule
     *
     * @return schedule for token job
     */
    public String checkScheduleTokenJob() {
        WebElement tokenSchedule = wait.waitForElementDisplayed(tokenScheduleID);
        String tokenScheduleText = text.getElementText(tokenSchedule);

        return tokenScheduleText;
    }


    /**
     * Gets Invoice Job Schedule
     *
     * @return Schedule for Invoice Job
     */
    public String checkScheduleInvoiceJob() {
        WebElement invoiceSchedule = wait.waitForElementDisplayed(invoiceJobScheduleID);
        String invoiceScheduleText = text.getElementText(invoiceSchedule);

        return invoiceScheduleText;

    }

    /**
     * Clicks the "Configuration" tab on the connector ui
     * */
    public void clickConfiguration(){
        WebElement config = wait.waitForElementDisplayed(configDropdown);
        click.clickElementCarefully(config);
    }

    /**
     * Clicks "Custom Field Mapping" from the configuration dropdown
     * */
    public void clickCustomFieldMapping(){
        WebElement customMapping = wait.waitForElementDisplayed(customFieldMapping);
        click.clickElementCarefully(customMapping);
    }

    /**
     * Clicks "Taxpayer Mapping" from the configuration dropdown
     * */
    public void clickTaxPayerMapping(){
        WebElement payerMapping = wait.waitForElementDisplayed(taxPayerMapping);
        click.clickElementCarefully(payerMapping);
    }

    /**
     * Adds a new field mapping to the connector
     * */
    public void addFieldMapping(){

        WebElement concurFieldLevel = wait.waitForElementDisplayed(fieldLevelSelect);
        click.clickElementCarefully(concurFieldLevel);

        //needed because the dropdown is not considered interactable using the clicked element
        WebElement currentElement = driver.switchTo().activeElement();
        currentElement.sendKeys(Keys.ENTER);

        WebElement concurFieldName = wait.waitForElementDisplayed(fieldNameSelect);
        click.clickElementCarefully(concurFieldName);

        currentElement = driver.switchTo().activeElement();
        currentElement.sendKeys(Keys.ENTER);

        WebElement vFlexFieldType = wait.waitForElementDisplayed(vFlexFieldTypeSelect);
        click.clickElementCarefully(vFlexFieldType);

        currentElement = driver.switchTo().activeElement();
        currentElement.sendKeys(Keys.ENTER);

        WebElement vFlexField = wait.waitForElementDisplayed(vFlexFieldSelect);
        click.clickElementCarefully(vFlexField);

        currentElement = driver.switchTo().activeElement();
        currentElement.sendKeys(Keys.DOWN);
        currentElement.sendKeys(Keys.DOWN);
        currentElement.sendKeys(Keys.DOWN);
        currentElement.sendKeys(Keys.ENTER);

        WebElement addMappingButton = wait.waitForElementDisplayed(addFieldMappingButton);
        click.clickElementCarefully(addMappingButton);
    }

    /**
     * Deletes the last field mapping created
     * */
    public void deleteFieldMapping(){
        WebElement fieldMapping = wait.waitForElementDisplayed(lastFieldMappingDeleteButton);
        click.clickElementCarefully(fieldMapping);
    }
}