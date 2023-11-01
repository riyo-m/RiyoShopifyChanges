package com.vertex.quality.connectors.tradeshift.pages.connector;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Tradeshift Vertex Connector Homepage
 *
 * @author alewis
 */
public class TradeshiftConnectorHomePage extends VertexPage {
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

    public TradeshiftConnectorHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the version number of the Tradeshift connector
     *
     * @return The connector version number
     * */
    public String getConnectorVersionNumber(){
        jsWaiter.sleep(2000);
        WebElement version = wait.waitForElementDisplayed(versionNumberText);
        String versionNumber = version.getText();

        return versionNumber;
    }

    /**
     * Gets the database status of the Tradeshift connector
     *
     * @return The connector database status
     * */
    public String getConnectorDBStatus(){
        jsWaiter.sleep(2000);
        WebElement status = wait.waitForElementDisplayed(databaseStatusText);
        String databaseStatus = status.getText();

        return databaseStatus;
    }

    /**
     * Gets the tenant status of the Tradeshift connector
     *
     * @return The connector tenant status
     * */
    public String getConnectorTenantStatus(){
        jsWaiter.sleep(2000);
        WebElement status = wait.waitForElementDisplayed(tenantStatusText);
        String tenantStatus = status.getText();

        return tenantStatus;
    }

    /**
     * Clicks the configuration dropdown for connector UI navigation
     * */
    public void clickConfigurationDropDown(){
        WebElement configuration = wait.waitForElementDisplayed(configurationDropdown);
        click.clickElementCarefully(configuration);
    }

    /**
     * Navigates to the Tenants page
     * */
    public void toTenantPage(){
        WebElement tenants = wait.waitForElementDisplayed(tenantsLink);
        click.clickElementCarefully(tenants);
    }

    /**
    * Navigates to the request mapping page
    * */
    public void toRequestMappingsPage(){
        WebElement requestMappings = wait.waitForElementDisplayed(requestMappingLink);
        click.clickElementCarefully(requestMappings);
    }
}
