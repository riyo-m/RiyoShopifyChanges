package com.vertex.quality.connectors.coupa.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * represents the login page of the Vertex Coupa environment
 * contains all the functions needed to login
 *
 * @author alewis
 */

public class VertexCoupaSignInPage extends VertexPage {
    protected By userName = By.id("username");
    protected By passWord = By.id("password");
    protected By login = By.xpath("//button[@id='Login_button']");
    protected By connectorName = By.id("connectorName");
    protected By clickHere = By.xpath("//a[contains(text(),'here')]");
    protected By configuration = By.xpath("//span[@class='site-nav__link-text'][contains(text(),'Configuration')]");
    protected By tenants = By.xpath("//span[@class='site-nav__link-text'][contains(text(),'Tenants')]");
    protected By requestMappings = By.xpath("//span[@class='site-nav__link-text'][contains(text(),'Request Mappings')]");
    protected By coupaTenantId = By.xpath("//div[@id='coupaTenantId']");
    protected By addTenant = By.xpath("//button[@id='btn-id-tenantFormDrawer']");
    protected By requestMapping = By.xpath("//button[@id='btn-id-requestMappingFormDrawer']");
    protected By deleteRequestMapping = By.xpath("//tr[4]//td[3]//button[2]");
    protected By coupaTenantID = By.xpath("//input[@id='id']");
    protected By coupaTenantName = By.xpath("//input[@id='name']");
    protected By coupaField = By.id("coupaFieldId");
    protected By coupaVertexField = By.xpath("//div[@id='fieldId']");
    protected By defaultTaxpayer = By.xpath("//input[@id='taxEngineDefaultTaxpayer']");
    protected By taxEngineUrl = By.xpath("//input[@id='taxEngineUrl']");
    protected By logRetentionDays = By.xpath("//input[@id='react-select-4-input']");
    protected By saveTenantButton = By.xpath("//button[@id='saveTenant']");
    protected By saveReqMap = By.xpath("//button[@id='saveReqMap']");
    protected By coupaVersion = By.id("connectorVersion");
    protected By buttonClass = By.className("btn-txt");
    protected By logEnabledID = By.id("logEnabled");
    protected By tenantUrl = By.xpath("//*[@id=\"root\"]/div[2]/div/main/div/div/div[3]/div/table/tbody/tr/td[2]");
    protected By tenantStatus = By.xpath("//*[@id=\"root\"]/div[2]/div/main/div/div/div[3]/div/table/tbody/tr/td[3]");
    protected By logging = By.xpath("//*[@id=\"logEnabled\"]/div/div[1]/div[1]");
    protected By interval = By.xpath("//*[@id=\"logRetentionDays\"]/div/div[2]");



    public String tenantURL = "https://oseries9-final.vertexconnectors.com/vertex-ws/";
    public String vertexCoupaURL = "https://coupa.dev.vertexconnectors.com/";
    public String vertexCoupaQAURL = "https://coupa.qa.vertexconnectors.com/";
    public String adminUsername = "coupaUser";
    public String adminPassword = "vertex";

    public VertexCoupaSignInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to login for VertexCoupa login
     */
    public void vertexCoupaLogin() {
        driver.get(vertexCoupaURL);
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        preLoginClickHere();
        signInPage.enterUsername(adminUsername);
        signInPage.enterPassword(adminPassword);
        signInPage.clicklogin();
        waitForPageLoad();
        assertTrue(signInPage.verifyVertexCoupaLogin());
    }

    /**
     * Method to login for VertexCoupa login
     */
    public void vertexCoupaQALogin() {
        driver.get(vertexCoupaQAURL);
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        preLoginClickHere();
        signInPage.enterUsername(adminUsername);
        signInPage.enterPassword(adminPassword);
        signInPage.clicklogin();
        waitForPageLoad();
    }

    /**
     * enters a string into the 'username' text box
     *
     * @param uName the string that is entered into the 'username' text box
     */
    public void enterUsername(String uName) {
        WebElement userNameField = wait.waitForElementDisplayed(userName);
        click.clickElementCarefully(userNameField);
        userNameField.clear();
        text.enterText(userNameField, uName);
    }

    /**
     * enters a string into the 'password' text box
     *
     * @param pwd the string that is entered into the 'password' text box
     */
    public void enterPassword(String pwd) {
        WebElement passwordField = wait.waitForElementDisplayed(passWord);
        click.clickElementCarefully(passwordField);
        passwordField.clear();
        text.enterText(passwordField, pwd);
    }

    /**
     * click on login button
     */
    public void clicklogin() {
        WebElement submit = wait.waitForElementDisplayed(login);
        click.clickElementCarefully(submit);
    }

    /**
     * click on click here link
     */
    public void preLoginClickHere() {
        WebElement coupaClickHere = wait.waitForElementDisplayed(clickHere);
        click.clickElementCarefully(coupaClickHere);
    }

    /**
     * verify the login page
     */
    public boolean verifyVertexCoupaLogin() {
        WebElement connectorNameFiled = wait.waitForElementDisplayed(connectorName);
        boolean status = connectorNameFiled.isDisplayed();
        return status;
    }

    /**
     * verify the coupa connector version
     */
    public String getCoupaVersion() {
        waitForPageLoad();
        WebElement versionField = wait.waitForElementDisplayed(coupaVersion,5);
        String versionValue = text.getElementText(versionField);
        return versionValue;
    }

    /**
     * click on configuration link
     */
    public void clickConfiguration() {
        wait.waitForElementEnabled(configuration);
        click.clickElement(configuration);
        waitForPageLoad();
    }

    /**
     * click on Tenants button
     */
    public void clickTenants() {
        wait.waitForElementEnabled(tenants);
        click.clickElement(tenants);
        waitForPageLoad();
    }

    /**
     * click on RequestMappings button
     */
    public void clickRequestMappings() {
        wait.waitForElementEnabled(requestMappings);
        click.clickElement(requestMappings);
        waitForPageLoad();
    }

    /**
     * select the Coupa Tenant ID
     */
    public void selectCoupaTenantId(String coupaID) {
        WebElement tenantID = wait.waitForElementDisplayed(coupaTenantId);
        click.clickElementCarefully(tenantID);
        waitForPageLoad();
        dropdown.selectDropdownByValue(tenantID, coupaID);
        tenantID.sendKeys(Keys.TAB);
    }

    /**
     * select the Coupa Tenant ID
     */
    public String getCoupaIdText() {
        WebElement tenantID = wait.waitForElementDisplayed(coupaTenantId);
        String text = tenantID.getText();
        return text;
    }

    /**
     * adding the new tenant
     */
    public void addTenant( ) {
        wait.waitForElementEnabled(addTenant);
        click.clickElement(addTenant);
        waitForPageLoad();
    }

    /**
     * enter the tenant details
     * @param coupaTenantIDInput the string that is entered into the 'coupatenantID' text box
     * @param coupaTenantNameInput the string that is entered into the 'coupaTenantName' text box
     * @param defaultTaxpayerInput the string that is entered into the 'defaultTaxpayer' text box
     * @param taxEngineUrlInput the string that is entered into the 'taxEngineUrl' text box
     * @return checkField it will capture value from LogEnabled field
     */
    public String addTenantDetails( final String coupaTenantIDInput,final String coupaTenantNameInput,final String defaultTaxpayerInput, final String taxEngineUrlInput,
                                    final String logIntervalDaysInput)
    {
        waitForPageLoad();
        //enter coupa tenant id
        wait.waitForElementEnabled(coupaTenantID);
        text.enterText(coupaTenantID, coupaTenantIDInput);
        //enter coupa tenant name
        wait.waitForElementEnabled(coupaTenantName);
        text.enterText(coupaTenantName, coupaTenantNameInput);
        //enter default taxpayer
        wait.waitForElementEnabled(defaultTaxpayer);
        text.enterText(defaultTaxpayer, defaultTaxpayerInput);
        //enter coupa tenant url
        wait.waitForElementEnabled(taxEngineUrl);
        text.enterText(taxEngineUrl, taxEngineUrlInput);
        //capture the field enabled
        WebElement logEnabled = wait.waitForElementEnabled(logging);
        String checkField = text.getElementText(logEnabled);

        WebElement intervalBox = wait.waitForElementDisplayed(interval);
        click.clickElementCarefully(intervalBox);
        By intervalOption = By.id("react-select-3-option-"+logIntervalDaysInput);
        WebElement intervalSelect = wait.waitForElementDisplayed(intervalOption);
        click.clickElementCarefully(intervalSelect);

        //click on save
        wait.waitForElementEnabled(saveTenantButton);
        click.clickElementCarefully(saveTenantButton);

        //give time for the cleanup function to run otherwise it skips
        jsWaiter.sleep(1000);
        return checkField;
    }

    /**
     * adding the request mapping
     */
    public void clickAddRequestMapping( ) {
        wait.waitForElementEnabled(requestMapping);
        click.clickElement(requestMapping);
        waitForPageLoad();
    }

    /**
     * enter the request mapping details
     * @param coupaFieldInput the string that is entered into the 'defaultTaxpayer' text box
     * @param coupaVertexFieldInput the string that is entered into the 'taxEngineUrl' text box
     */
    public void addRequestMappingDetails( final String coupaFieldInput,final String coupaVertexFieldInput)
    {
        waitForPageLoad();
        //enter coupa tenant id
        WebElement coupaDrop = wait.waitForElementEnabled(coupaField);
        click.clickElementCarefully(coupaDrop);
        By coupaInput = By.xpath("//div[contains(text(),'"+coupaFieldInput+"')]");
        WebElement dropValue = wait.waitForElementDisplayed(coupaInput);
        jsWaiter.sleep(1000);
        click.clickElementCarefully(dropValue);
        //enter coupa tenant name
        WebElement vertexDrop = wait.waitForElementEnabled(coupaVertexField);
        click.clickElementCarefully(vertexDrop);
        By vertexInput = By.xpath("//div[contains(text(),'"+coupaVertexFieldInput+"')]");
        WebElement vertDropValue = wait.waitForElementDisplayed(vertexInput);
        jsWaiter.sleep(1000);
        click.clickElementCarefully(vertDropValue);
        //click on save
        wait.waitForElementEnabled(saveReqMap);
        click.clickElementCarefully(saveReqMap);
    }

    /**
     * Delete the request mapping
     */
    public void deleteRequestMapping( ) {
        wait.waitForElementEnabled(deleteRequestMapping);
        click.clickElement(deleteRequestMapping);
        waitForPageLoad();
    }

    /**
     * adding the new tenant
     * @return checkField it will capture text from logValue column
     */
    public String getLogEnabledValue(String tenantId ) {
        waitForPageLoad();
        String logValue = driver.findElement(By.xpath("//tbody/tr/td[contains(.,'"+ tenantId +"')]/../td[5]")).getText();

        return logValue;
    }

    /**
     * adding the new tenant
     */
    public Boolean enableTenantVerification(String value,String value1 ) {
        if(value.equalsIgnoreCase("Enabled")) {
            assertTrue(value1.equalsIgnoreCase("yes"));
            return true;
        }
        return false;
    }

    /**
     * click edit button on tenant page
     */
    public void clickEditButton() {
        List<WebElement> editButtons = wait.waitForAllElementsDisplayed(buttonClass);

        for (WebElement editButton : editButtons) {
            String editButtonText = editButton.getText();
            if (editButtonText.equals("Edit")) {
                click.clickElementCarefully(editButton);
            }
        }
    }

    /**
     * returns the tenant URL in the table on connector page
     *
     * @return the tenant URL
     * */
    public String getTenantUrl(){
        WebElement url = wait.waitForElementDisplayed(tenantUrl);
        return url.getText();
    }

    /**
     * returns the tenant status in the table on connector page
     *
     * expect UP/DOWN
     *
     * @return status
     * */
    public String getTenantStatus(){
        WebElement status = wait.waitForElementDisplayed(tenantStatus);
        return status.getText();
    }

    /**
     * Deletes a tenant by its tenant id for
     * @param tenantId the id of the tenant to be deleted
     * */
    public void deleteTenant(String tenantId){
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
     * Deletes a request mapping by its Coupa Field category
     * @param coupaField the value in the Coupa Field category for this mapping
     * */
    public void deleteMapping(String coupaField){
        jsWaiter.sleep(1000);
        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        for (WebElement oneRow : rows) {
            List<WebElement> columns = oneRow.findElements(By.tagName("td"));
            if(columns.size() > 0) {
                if (columns.get(0).getText().equals(coupaField)) {
                    List<WebElement> buttons = columns.get(2).findElements(By.tagName("button"));
                    click.clickElementCarefully(buttons.get(1));
                }
            }
        }
    }
}
