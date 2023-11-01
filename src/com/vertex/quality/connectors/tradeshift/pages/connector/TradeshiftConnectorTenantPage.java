package com.vertex.quality.connectors.tradeshift.pages.connector;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TradeshiftConnectorTenantPage extends VertexPage {
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
    protected By TaxCodeConfigTab = By.id("taxCodeConfigurationTab");
    protected By GeneralTab = By.id("generalTab");
    protected By AccuralTab = By.id("accrualsTab");
    protected By AccuralCheckbox= By.className("custom__cb__label-text");
    protected By AccuralInput = By.id("partialAccrualThresholdAmount_input");
    protected By TaxEngineResponse = By.xpath("//*/div[contains(@id,'reqResLogEnabled')]/div/div/div");
	protected By VendorTaxCode = By.id("vendorTaxCode");
	protected By ConsumerTaxCode = By.id("consumerTaxCode");
	protected By PartialTaxCode = By.id("partialTaxCode");

    //These xpaths are not ideal but the only way the options are able to be interacted with
    protected By TaxEngineResponseEnable = By.xpath("/html/body/div/div[2]/div/main/div/div/div[2]/div/div/form/div/div[2]/div[3]/div/div[2]/div/div[1]");
    protected By TaxEngineResponseDisable = By.xpath("/html/body/div/div[2]/div/main/div/div/div[2]/div/div/form/div/div[2]/div[3]/div/div[2]/div/div[2]");

    public TradeshiftConnectorTenantPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Adds a new tenant to the connector
     * */
    public void addTenant(String oseriesURL){
        WebElement add = wait.waitForElementDisplayed(addTenantButton);
        click.clickElementCarefully(add);
        WebElement tId = wait.waitForElementDisplayed(tenantId);
        text.clickElementAndEnterText(tId,"99");
        WebElement tName = wait.waitForElementDisplayed(tenantName);
        text.clickElementAndEnterText(tName,"Tradeshift test");
        WebElement taxpayer = wait.waitForElementDisplayed(defaultTaxpayer);
        text.clickElementAndEnterText(taxpayer,"connector-tradeshift-qa");
        WebElement url = wait.waitForElementDisplayed(taxEngineURL);
        text.clickElementAndEnterText(url,oseriesURL);
		toTaxCongfigurationTab();
		setTaxCodeConfig();
        toUnEceTab();
        addDefaultTenantCodes("AA","VAT");
        clickSaveTenant();
    }

    /**
     * attempts to add a duplicate tenant to the connector
     * and obtains the error message
     *
     * @return the error message
     * */
    public String addDuplicateTenant(){
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        WebElement add = wait.waitForElementDisplayed(addTenantButton);
        click.clickElementCarefully(add);
        toGeneralTab();
        WebElement tId = wait.waitForElementDisplayed(tenantId);
        text.clickElementAndEnterText(tId,"99");
        WebElement tName = wait.waitForElementDisplayed(tenantName);
        text.clickElementAndEnterText(tName,"Tradeshift test");
        WebElement taxpayer = wait.waitForElementDisplayed(defaultTaxpayer);
        text.clickElementAndEnterText(taxpayer,"connector-tradeshift-qa");
        WebElement url = wait.waitForElementDisplayed(taxEngineURL);
        text.clickElementAndEnterText(url,"https://oseries9-final.vertexconnectors.com/vertex-ws/");
        toUnEceTab();
        addDefaultTenantCodes("dc1","dc2");
        clickSaveTenant();
        String eMessage = getErrorMessageText();
        WebElement exitTenant = wait.waitForElementDisplayed(exitCreateTenant);
        click.clickElementCarefully(exitTenant);

        return eMessage;
    }

    /**
     * deletes the tenant associated with the provided tenant id
     *
     * @param tenantId
     * */
    public void deleteTenant(String tenantId){
        jsWaiter.sleep(2000);
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        for (WebElement oneRow : rows) {
            List<WebElement> columns = oneRow.findElements(By.tagName("td"));
            if(columns.size() > 0) {
                if (columns.get(0).getText().equals(tenantId)) {
                    List<WebElement> buttons = columns.get(5).findElements(By.tagName("button"));
                    click.clickElementCarefully(buttons.get(1));
                }
            }
        }
    }

    /**
     * presses the edit button of the tenant associated with the provided tenant id
     *
     * @param tenantId
     * */
    public void editTenant(String tenantId){
        jsWaiter.sleep(2000);
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        for (WebElement oneRow : rows) {
            List<WebElement> columns = oneRow.findElements(By.tagName("td"));
            if(columns.size() > 0) {
                if (columns.get(0).getText().equals(tenantId)) {
                    List<WebElement> buttons = columns.get(5).findElements(By.tagName("button"));
                    click.clickElementCarefully(buttons.get(0));
                }
            }
        }
    }

    /**
     * Adds a UN/ECE code to a tenant
     * */
    public void addUnEceTenantCodes(){
        toUnEceTab();
        WebElement UNECESourceCategory = wait.waitForElementDisplayed(UnEceCategoryDropDown);
        click.clickElementCarefully(UNECESourceCategory);
        //give the dropdown time to come show up
        jsWaiter.sleep(1000);
        WebElement UnEceDropValue = wait.waitForElementDisplayed(UnEceSourceFlex1);
        click.clickElementCarefully(UnEceDropValue);
        WebElement UNECESourceScheme = wait.waitForElementDisplayed(UnEceSchemeDropDown);
        click.clickElementCarefully(UNECESourceScheme);
        //give the dropdown time to come show up
        jsWaiter.sleep(1000);
        WebElement UnEceDropValue2 = wait.waitForElementDisplayed(UnEceSourceFlex2);
        click.clickElementCarefully(UnEceDropValue2);
        clickSaveTenant();
    }

    /**
     * Adds a default UN/ECE code to a tenant
     *
     * @param categoryCode
     * @param schemeCode
     * */
    public void addDefaultTenantCodes(String categoryCode, String schemeCode){
        WebElement customUNECECategorySource = wait.waitForElementDisplayed(DefaultUnEceCategoryCode);
        click.clickElementCarefully(customUNECECategorySource);
        text.enterText(customUNECECategorySource,categoryCode);
        WebElement customUNECESchemeSource = wait.waitForElementDisplayed(DefaultUnEceSchemeCode);
        click.clickElementCarefully(customUNECESchemeSource);
        text.enterText(customUNECESchemeSource,schemeCode);
    }

    /**
     * Clears the UN/ECE code dropdowns from a tenant
     * */
    public void clearTenantCodes(){
        WebElement UnEceCategorySourceClear = wait.waitForElementDisplayed(UnEceCategoryDropClearButton);
        click.clickElementCarefully(UnEceCategorySourceClear);
        WebElement UnEceSchemeSourceClear = wait.waitForElementDisplayed(UnEceSchemeDropClearButton);
        click.clickElementCarefully(UnEceSchemeSourceClear);
        clickSaveTenant();
    }

    /**
     * Gets the value of the UN/ECE Category code
     *
     * @return category code value
     * */
    public String getUnEceCategoryCodeDropDownValue(){
        WebElement UNECECategorySource = wait.waitForElementDisplayed(UnEceCategoryDropDown);
        String categoryValue = UNECECategorySource.getText();

        return categoryValue;
    }

    /**
     * Gets the value of the UN/ECE Scheme code
     *
     * @return scheme code value
     * */
    public String getUnEceSchemeCodeDropDownValue(){
        WebElement UNECESchemeSource = wait.waitForElementDisplayed(UnEceSchemeDropDown);
        String schemeValue = UNECESchemeSource.getText();

        return schemeValue;
    }

    /**
     * Gets the value of the default UN/ECE Category code
     *
     * @return category code value
     * */
    public String getUnEceCategoryCodeDefaultValue(){
        WebElement UNECECategorySource = wait.waitForElementDisplayed(DefaultUnEceCategoryCode);
        String categoryValue = UNECECategorySource.getAttribute("value");

        return categoryValue;
    }

    /**
     * Gets the value of the default UN/ECE Scheme code
     *
     * @return scheme code value
     * */
    public String getUnEceSchemeCodeDefaultValue(){
        WebElement UNECESchemeSource = wait.waitForElementDisplayed(DefaultUnEceSchemeCode);
        String schemeValue = UNECESchemeSource.getAttribute("value");

        return schemeValue;
    }

    /**
     * Deletes the default UN/ECE codes from a tenant
     * */
    public void clearDefaultTenantCodes(){
        WebElement UnEceCategorySource = wait.waitForElementDisplayed(DefaultUnEceCategoryCode);
        click.performDoubleClick(UnEceCategorySource);
        UnEceCategorySource.sendKeys(Keys.BACK_SPACE);
        WebElement UnEceSchemeSource = wait.waitForElementDisplayed(DefaultUnEceSchemeCode);
        click.performDoubleClick(UnEceSchemeSource);
        click.performDoubleClick(UnEceSchemeSource);
        UnEceSchemeSource.sendKeys(Keys.BACK_SPACE);
    }

    /**
     * Clicks the save button on the edit tenant menu
     * */
    public void clickSaveTenant(){
        WebElement save = wait.waitForElementDisplayed(saveButton);
        click.clickElementCarefully(save);
    }

    /**
     * Navigates to the UN/ECE tab of the tenant creation page
     * */
    public void toUnEceTab(){
        WebElement unECETab = wait.waitForElementDisplayed(UnEceCodeTab);
        click.clickElementCarefully(unECETab);
    }

    /**
     * navigates to the logging page on the tenant creation menu
     * */
    public void toLoggingTab(){
        //Often there is a popup depending on the scenario that only covers this tab, the pop up goes away after 3 seconds
        jsWaiter.sleep(3000);
        WebElement loggingTab = wait.waitForElementDisplayed(LoggingTab);
        click.clickElementCarefully(loggingTab);
    }

	/**
	 * navigates to the tax code configuration tab on the tenant creation menu
	 * */
	public void toTaxCongfigurationTab(){
		WebElement loggingTab = wait.waitForElementDisplayed(TaxCodeConfigTab);
		click.clickElementCarefully(loggingTab);
	}


    /**
     * navigates to the general page on the tenant creation menu
     * */
    public void toGeneralTab(){
        WebElement generalTab = wait.waitForElementDisplayed(GeneralTab);
        click.clickElementCarefully(generalTab);
    }

    /**
     * navigates to the accrual tab on the tenant creation menu
     * */
    public void toAccrualTab(){
        WebElement accrualsTab = wait.waitForElementDisplayed(AccuralTab);
        click.clickElementCarefully(accrualsTab);
    }

    /**
     * Gets the error message text when a tenant cannot be created
     *
     * @return the error message
     * */
    public String getErrorMessageText(){
        WebElement error = wait.waitForElementDisplayed(errorMessage);
        String message = error.getText();
        wait.waitForElementNotDisplayed(error);
        return message;
    }

    /**
     * enables the accrual checkbox
     * and input a value for accrual amount
     * */
    public void enableAccruals(){
        toAccrualTab();
        WebElement accrualCheck = wait.waitForElementDisplayed(AccuralCheckbox);
        click.clickElementCarefully(accrualCheck);
        WebElement accrualInput = wait.waitForElementDisplayed(AccuralInput);
        click.clickElementCarefully(accrualInput);
        text.enterText(accrualInput,"1");
    }

    /**
     * gets the value of the accruals text input if accruals is enabled
     *
     * @return input value or null if accruals is disabled
     * */
    public String getAccrualsValue(){
        WebElement accrualCheck = wait.waitForElementDisplayed(AccuralCheckbox);
        if(accrualCheck.isEnabled()) {
            WebElement accrualInput = wait.waitForElementDisplayed(AccuralInput);
            return accrualInput.getAttribute("value");
        }
        return null;
    }

    /**
	 * Gets the vendor tax code on the tenant tax code configuration menu
	 *
	 * @return Vendor tax code
	 * */
    public String getVendorTaxCode(){
    	return wait.waitForElementEnabled(VendorTaxCode).getAttribute("value");
	}

	/**
	 * Gets the Consumer tax code on the tenant tax code configuration menu
	 *
	 * @return Consumer tax code
	 * */
	public String getConsumerTaxCode(){
		return wait.waitForElementEnabled(ConsumerTaxCode).getAttribute("value");
	}

	/**
	 * Gets the Partial tax code on the tenant tax code configuration menu
	 *
	 * @return Partial tax code
	 * */
	public String getPartialTaxCode(){
		return wait.waitForElementEnabled(PartialTaxCode).getAttribute("value");
	}

    /**
     * gets the value of the tax engine request dropdown
     *
     * @return dropdown value
     * */
    public String getTaxEngineResponseValue(){
        WebElement responseDropdown = wait.waitForElementDisplayed(TaxEngineResponse);
        return responseDropdown.getText();
    }

    /**
     * gets the value of the tax engine response dropdown,
     * if 'Disabled' it is set to 'Enabled'
     * */
    public void enableTaxEngineResponseValue(){
        WebElement responseDropdown = wait.waitForElementDisplayed(TaxEngineResponse);
        if (!responseDropdown.getText().equals("Enabled")) {
            click.clickElementCarefully(responseDropdown);
            WebElement enableButton = wait.waitForElementDisplayed(TaxEngineResponseEnable);
            click.clickElementCarefully(enableButton);
        }
        clickSaveTenant();
    }

    /**
     * gets the value of the tax engine response dropdown,
     * if 'Enabled' it is set to 'Disabled'
     * */
    public void disableTaxEngineResponseValue(){
        WebElement responseDropdown = wait.waitForElementDisplayed(TaxEngineResponse);
        if (!responseDropdown.getText().equals("Disabled")) {
            click.clickElementCarefully(responseDropdown);
            WebElement disableButton = wait.waitForElementDisplayed(TaxEngineResponseDisable);
            click.clickElementCarefully(disableButton);
        }
        clickSaveTenant();
    }

    /**
	 * Sets the tax code configuration for a tenant
	 * */
    public void setTaxCodeConfig(){
    	text.clickElementAndEnterText(VendorTaxCode,"VENDOR_CODE");
		text.clickElementAndEnterText(ConsumerTaxCode,"CONSUMER_CODE");
		text.clickElementAndEnterText(PartialTaxCode,"PARTIAL_CODE");
		clickSaveTenant();
	}



}
