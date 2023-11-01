package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

/**
 * Shopify Vertex on-boarding page -> contains variables & helper methods
 *
 * @author Shivam.Soni
 */
public class ShopifyVertexOnBoardingPage extends ShopifyPage {

    protected By unauthorizedAccessText = By.xpath(".//body//h1");
    protected By connectVertexAccountButton = By.xpath(".//button[normalize-space(.)='Connect Vertex Account']");
    protected By companyCodeBox = By.id("companyCode");
    protected By companyCodeErrorField = By.id("CompanyIderrormsg");
    protected By clientIdBox = By.id("clientId");
    protected By clientIdErrorField = By.id("clientIderrormsg");
    protected By clientSecretBox = By.id("clientSecret");
    protected By clientSecretErrorField = By.id("clientSecreterrormsg");
    protected By formItemErrorField = By.xpath(".//p[@class='form-item__error-message']");
    protected By taxCalcUrlBox = By.id("vodOnpremiseUrl");
    protected By connectButton = By.xpath(".//button[text()='Connect']");
    protected By cancelButton = By.xpath(".//button[text()='Cancel']");

    private final String oscStageCompanyCode1 = ShopifyDataUI.OSeriesDetails.STAGE_OSC_COMPANY_CODE_1.text;
    private final String oscStageCompanyCode2 = ShopifyDataUI.OSeriesDetails.STAGE_OSC_COMPANY_CODE_2.text;
    private final String oscStageClientID = ShopifyDataUI.OSeriesDetails.STAGE_OSC_CLIENT_ID.text;
    private final String oscStageClientSecret = ShopifyDataUI.OSeriesDetails.STAGE_OSC_CLIENT_SECRET.text;
    private final String oscQACompanyCode = ShopifyDataUI.OSeriesDetails.QA_OSC_COMPANY_CODE.text;
    private final String oscQAClientID = ShopifyDataUI.OSeriesDetails.QA_OSC_CLIENT_ID.text;
    private final String oscQAClientSecret = ShopifyDataUI.OSeriesDetails.QA_OSC_CLIENT_SECRET.text;
    private final String vodCompanyCode = ShopifyDataUI.OSeriesDetails.VOD_COMPANY_CODE.text;
    private final String vodClientID = ShopifyDataUI.OSeriesDetails.VOD_CLIENT_ID.text;
    private final String vodClientSecret = ShopifyDataUI.OSeriesDetails.VOD_CLIENT_SECRET.text;
    private final String vodTaxCalcURL = ShopifyDataUI.OSeriesDetails.VOD_TAX_CALC_URL.text;

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyVertexOnBoardingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * This will hit Vertex On-boarding URL directly.
     */
    public void directLoadOnBoardingPage() {
        driver.get(ShopifyDataUI.AdminPanelData.SHOPIFY_VTX_ON_BOARDING.text);
        waitForPageLoad();
    }

    /**
     * Reads the un authorized access attempts error message
     *
     * @return error message
     */
    public String getUnauthorizedAccessMessage() {
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(".//body"));
        return text.getElementText(unauthorizedAccessText);
    }

    /**
     * Reads the error message of Company Code
     *
     * @return error message
     */
    public String getCompanyCodeErrorMessage() {
        waitForPageLoad();
        return text.getElementText(companyCodeErrorField);
    }

    /**
     * Reads the error message of Client ID
     *
     * @return error message
     */
    public String getClientIdErrorMessage() {
        waitForPageLoad();
        return text.getElementText(clientIdErrorField);
    }

    /**
     * Reads the error message of Client Secret
     *
     * @return error message
     */
    public String getClientSecretErrorMessage() {
        waitForPageLoad();
        return text.getElementText(clientSecretErrorField);
    }

    /**
     * Reads the error message of Item Form
     *
     * @return error message
     */
    public String getItemErrorMessage() {
        waitForPageLoad();
        return text.getElementText(formItemErrorField);
    }

    /**
     * Does the vertex on-boarding based on the given O Series instance name
     * If the instance is not supported in the MVP or name doesn't match then it will do on-boarding with default decided instance
     *
     * @param oSeriesInstanceName name of O Series instance for which On-boarding to be done
     * @return Taxes nad duties page
     */
    public ShopifyAdminSettingsTaxesPage doVertexOnBoarding(String oSeriesInstanceName) {
        waitForPageLoad();
        clickConnectVertexAccount();
        waitForPageLoad();
        switch (oSeriesInstanceName.toLowerCase(Locale.ROOT)) {
            case "on prem":
                VertexLogger.log(oSeriesInstanceName + " is not supported yet for Shopify MVP & hence redirecting to default installation");
                // Not putting break as we don't want this to be failed in the automation run.
                // We want it to be done the installation of Vertex App/ Service with at least default decided option
                // Once in GA Release Connector will have support of all the other O Series instances then this block will have respective implementation.
            case "vod dev":
                enterCompanyCode(vodCompanyCode);
                enterClientID(vodClientID);
                enterClientSecret(vodClientSecret);
                enterTaxCalcUrl(vodTaxCalcURL);
                VertexLogger.log("Vertex's Taxation App/ Service is on boarded with: " + oSeriesInstanceName, VertexLogLevel.INFO);
                break;
            case "classic cloud qa":
                VertexLogger.log(oSeriesInstanceName + " is not supported yet for Shopify MVP & hence redirecting to default installation");
                // Not putting break as we don't want this to be failed in the automation run.
                // We want it to be done the installation of Vertex App/ Service with at least default decided option
                // Once in GA Release Connector will have support of all the other O Series instances then this block will have respective implementation.
            case "classic cloud stage":
                VertexLogger.log(oSeriesInstanceName + " is not supported yet for Shopify MVP & hence redirecting to default installation");
                // Not putting break as we don't want this to be failed in the automation run.
                // We want it to be done the installation of Vertex App/ Service with at least default decided option
                // Once in GA Release Connector will have support of all the other O Series instances then this block will have respective implementation.
            case "osc qa":
                enterCompanyCode(oscQACompanyCode);
                enterClientID(oscQAClientID);
                enterClientSecret(oscQAClientSecret);
                VertexLogger.log("Vertex's Taxation App/ Service is on boarded with: " + oSeriesInstanceName, VertexLogLevel.INFO);
                break;
            case "osc stage":
                enterCompanyCode(oscStageCompanyCode2);
                enterClientID(oscStageClientID);
                enterClientSecret(oscStageClientSecret);
                VertexLogger.log("Vertex's Taxation App/ Service is on boarded with: " + oSeriesInstanceName, VertexLogLevel.INFO);
                break;
            default:
                VertexLogger.log("Given option didn't matched hence, on boarding with default option");
                enterCompanyCode(oscStageCompanyCode1);
                enterClientID(oscStageClientID);
                enterClientSecret(oscStageClientSecret);
                VertexLogger.log("Vertex's Taxation App/ Service is on boarded with: Stage OSC", VertexLogLevel.INFO);
                break;
        }
        clickConnect();
        waitForPageLoad();
        return new ShopifyAdminSettingsTaxesPage(driver);
    }

    /**
     * Clicks on Connect vertex account button
     */
    public void clickConnectVertexAccount() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(connectVertexAccountButton));
        waitForPageLoad();
    }

    /**
     * Enters company code
     *
     * @param value value of company code
     */
    public void enterCompanyCode(String value) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(companyCodeBox), value);
        waitForPageLoad();
    }

    /**
     * Enters client id
     *
     * @param value value of client id
     */
    public void enterClientID(String value) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(clientIdBox), value);
        waitForPageLoad();
    }

    /**
     * Enters client secret
     *
     * @param value value of client secret
     */
    public void enterClientSecret(String value) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(clientSecretBox), value);
        waitForPageLoad();
    }

    /**
     * Enters Tax Calc URL
     *
     * @param value value of Tax Calc URL
     */
    public void enterTaxCalcUrl(String value) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(taxCalcUrlBox), value);
        waitForPageLoad();
    }

    /**
     * Clicks on Connect button
     */
    public void clickConnect() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(connectButton));
        waitForPageLoad();
    }

    /**
     * Clicks on Cancel button
     */
    public void clickCancel() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(cancelButton));
        waitForPageLoad();
    }
}