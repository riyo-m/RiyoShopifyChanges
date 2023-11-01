package com.vertex.quality.connectors.tradeshift.pages.connector;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TradeshiftConnectorRequestMappingPage extends VertexPage {
    protected By versionNumberText = By.id("connectorVersion");
    protected By databaseStatusText = By.id("dbStatus");
    protected By tenantStatusText = By.xpath("//*[@id=\"root\"]/div[2]/div/main/div/div/div[3]/div/table/tbody/tr/td[3]");
    protected By configurationDropdown = By.xpath("//button/span[contains(text(),'Configuration')]");
    protected By tenantsLink = By.xpath("//a/span[contains(text(),'Tenants')]");
    protected By addTenantButton = By.id("btn-id-tenantFormDrawer");
    protected By tenantId = By.id("id");
    protected By tenantName = By.id("name");
    protected By defaultTaxpayer = By.id("taxEngineDefaultTaxpayer");
    protected By taxEngineURL = By.id("taxEngineUrl");
    protected By saveButton = By.id("saveTenant");
    protected By tenantDeleteButton = By.xpath("//*[@id=\"root\"]/div[2]/div/main/div/div/div[1]/div/table/tbody/tr[2]/td[6]/button[2]");
    protected By requestMappingLink = By.xpath("//li/a/span[contains(text(),'Request Mappings')]");
    protected By addRequestMappingButton = By.id("btn-id-requestMappingFormDrawer");
    protected By tsRequestMappingField = By.id("tradeshiftFieldId");
    protected By vertexRequestMappingField = By.xpath("//*[@id=\"fieldId\"]/div/div[1]");
    protected By taxpayerDropDownOption = By.id("react-select-3-option-0");
    protected By saveRequestMappingButton = By.id("saveReqMap");
    protected By errorMessage = By.xpath("//div/div[contains(text(),'Tenant with identifier 99 already exists. Unable to create duplicate tenant')]");
    protected By errorMessageRQMapping = By.xpath("//div/div[2][contains(text(),'Unknown error occurred. Please contact the administrator.')]");
    protected By exitCreateTenant = By.className("btn-close");
    protected By UnEceCategoryDropDown = By.id("taxEngineUnEceTaxCategorySource");
    protected By UnEceSchemeDropDown = By.id("taxEngineUnEceTaxSchemeSource");
    protected By UnEceSourceFlex1 = By.xpath("//div/div[contains(text(),\"Flex Code 1\")]");
    protected By UnEceSourceFlex2 = By.xpath("//div/div[contains(text(),\"Flex Code 2\")]");
    protected By UnEceCategoryDropClearButton = By.xpath("//*[@id=\"taxEngineUnEceTaxCategorySource\"]/div/div[2]/div[1]");
    protected By UnEceSchemeDropClearButton = By.xpath("//*[@id=\"taxEngineUnEceTaxSchemeSource\"]/div/div[2]/div[1]");
    protected By DefaultUnEceCategoryCode = By.id("taxEngineDefaultTaxCategory");
    protected By DefaultUnEceSchemeCode = By.id("taxEngineDefaultTaxScheme");
    protected By UnEceCodeTab = By.id("uNECETab");
    protected By LoggingTab = By.id("loggingTab");
    protected By GeneralTab = By.id("generalTab");


    public TradeshiftConnectorRequestMappingPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Gets the error message text when a RequestMapping cannot be created
     *
     * @return the error message
     * */
    public String getReqMapErrorMessageText(){
        WebElement error = wait.waitForElementDisplayed(errorMessageRQMapping);
        String message = error.getText();
        wait.waitForElementNotDisplayed(error);
        return message;
    }

    /**
     * Add a request mapping
     * */
    public void addRequestMapping(){
        //wait for other request mappings to load
        jsWaiter.sleep(2000);
        WebElement addRequest = wait.waitForElementDisplayed(addRequestMappingButton);
        click.clickElementCarefully(addRequest);
        WebElement tsMappingField = wait.waitForElementDisplayed(tsRequestMappingField);
        text.clickElementAndEnterText(tsMappingField,"Account Segment 99");
        WebElement vertexMappingField = wait.waitForElementDisplayed(vertexRequestMappingField);
        click.clickElementCarefully(vertexMappingField);
        WebElement taxpayer = wait.waitForElementDisplayed(taxpayerDropDownOption);
        click.clickElementCarefully(taxpayer);
        WebElement saveRequestMapping = wait.waitForElementDisplayed(saveRequestMappingButton);
        click.clickElementCarefully(saveRequestMapping);
    }

    public String addDuplicateRequestMapping(){
        WebElement addRequest = wait.waitForElementDisplayed(addRequestMappingButton);
        click.clickElementCarefully(addRequest);
        WebElement tsMappingField = wait.waitForElementDisplayed(tsRequestMappingField);
        text.clickElementAndEnterText(tsMappingField,"Account Segment 99");
        WebElement vertexMappingField = wait.waitForElementDisplayed(vertexRequestMappingField);
        click.clickElementCarefully(vertexMappingField);
        WebElement taxpayer = wait.waitForElementDisplayed(taxpayerDropDownOption);
        click.clickElementCarefully(taxpayer);
        WebElement saveRequestMapping = wait.waitForElementDisplayed(saveRequestMappingButton);
        click.clickElementCarefully(saveRequestMapping);
        String eMessage = getReqMapErrorMessageText();
        WebElement exitTenant = wait.waitForElementDisplayed(exitCreateTenant);
        click.clickElementCarefully(exitTenant);

        return eMessage;
    }

    /**
     * Deletes the request mapping associated with the
     * provided tradeshift field
     *
     * @param tsField
     * */
    public void deleteRequestMapping(String tsField){
        jsWaiter.sleep(2000);
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        for (WebElement oneRow : rows) {
            List<WebElement> columns = oneRow.findElements(By.tagName("td"));
            if(columns.size() > 0) {
                if (columns.get(0).getText().equals(tsField)) {
                    List<WebElement> buttons = columns.get(2).findElements(By.tagName("button"));
                    click.clickElementCarefully(buttons.get(1));
                }
            }
        }
    }
}
