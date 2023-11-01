package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.xml.ws.WebEndpoint;

/**
 * a generic representation of a CommerceTools Custom Applications Page.
 *
 * @author vivek-kumar
 */
public class CommerceToolsCustomApplicationsPage extends CommerceToolsBasePage {

    public CommerceToolsCustomApplicationsPage(WebDriver driver) {

        super(driver);
    }

    protected final By settingButton = By.xpath("//div[contains(text(),'Settings')]");
    protected final By customApplicationIcon = By.xpath("//a[contains(text(),'Custom applications')]");
    protected final By applicationUrl = By.xpath("//input[@id='text-field-2']");
    protected final By customApplicationButton = By.xpath("//span[contains(text(),'Register a custom application')]");
    protected final By customApplicationNameButton = By.xpath("//input[@id='text-field-1']");
    protected final By mainRoutePath = By.xpath("//input[@id='text-field-3']");
    protected final By languages = By.xpath("//span[contains(text(),'Show all languages (3)')]");
    protected final By languagesEn = By.xpath("//input[@id='localized-text-field-1.en']");
    protected final By vertexIcon = By.xpath("//*[@id='connected-square_react_svg__Ebene_1']");
    protected final By registerCustomApplication = By.xpath("//span[contains(text(),'Register custom application')]");
    protected final By activateStatus = By.xpath("//input[@type='checkbox']");
    protected final By activeConnector=By.xpath("//button[@label='Edit']");
    protected final By confirmButton = By.xpath("//span[contains(text(),'Confirm')]");
    protected final By goBackButton = By.xpath("//button[@label='Go back']");
    protected final By projectDropDownList = By.xpath("//input[@id='react-select-2-input']");
    protected final By dashboardIcon=By.xpath("//div[text()='Dashboard']");
    protected final By hideButton=By.xpath("//button[@label='Hide notification']");


    /**
     * click on setting Icon in commercetools
     */
    public void clickSettingIcon() {
        WebElement settingIconField = wait.waitForElementEnabled(settingButton);
        click.moveToElementAndClick(settingIconField);
    }

    /**
     * click on Custom Application in commercetools
     */
    public void clickOnCustomApplication() {
        WebElement customApplication = wait.waitForElementEnabled(customApplicationIcon);
        click.moveToElementAndClick(customApplication);
    }

    /**
     * enter project Name to be selected in commercetools.
     *
     * @param projectName
     */
    public void enterProjectProjectName(String projectName) {
        WebElement project = wait.waitForElementDisplayed(projectDropDownList);
        text.enterText(project, projectName);
        text.pressEnter(project);
    }

    /**
     * click to register Custom Application in commercetools.
     */
    public void clickCustomApplicationNameButton() {
        WebElement customButton = wait.waitForElementEnabled(customApplicationButton);
        click.moveToElementAndClick(customButton);
    }

    /**
     * enter customer Application Name in commercetools.
     *
     * @param name
     */
    public void enterCustomApplicationName(final String name) {
        WebElement nameField = wait.waitForElementEnabled(customApplicationNameButton);
        text.enterText(nameField, name);
    }

    /**
     * enter Application Url.
     *
     * @param url
     */
    public void enterApplicationUrl(final String url) {
        WebElement uelField = wait.waitForElementEnabled(applicationUrl);
        text.enterText(uelField, url);
    }

    /**
     * enter Main Route Pathname in Commercetools.
     *
     * @param path
     */
    public void enterMainRoutePath(final String path) {
        WebElement pathField = wait.waitForElementEnabled(mainRoutePath);
        text.enterText(pathField, path);
    }

    /**
     * click on languages link to select different languages in commercetools
     */
    public void clickLanguagesLink() {
        WebElement languagesField = wait.waitForElementEnabled(languages);
        click.moveToElementAndClick(languagesField);
    }

    /**
     * enter languages to be visible for created custom applications.
     *
     * @param lang
     */
    public void enterLanguage(final String lang) {
        WebElement languageEnglish = wait.waitForElementEnabled(languagesEn);
        text.enterText(languageEnglish, lang);
    }

    /**
     * click to select  Vertex logo Icon in commercetools
     */
    public void clickVertexIcon() {
        WebElement vertexLogoIcon = wait.waitForElementEnabled(vertexIcon);
        click.moveToElementAndClick(vertexLogoIcon);
    }

    /**
     * click on Register Custom Application Button.
     */
    public void clickRegisterCustomApplication() {
        WebElement registerApplicationIcon = wait.waitForElementEnabled(registerCustomApplication);
        click.moveToElementAndClick(registerApplicationIcon);
    }

    /**
     * click on Activate status Button to activate create  Custom Applications.
     */
    public void clickActivateStatus() {
        WebElement activateStatusButton = wait.waitForElementEnabled(activateStatus);
        click.moveToElementAndClick(activateStatusButton);
    }

    /**
     * click to confirm on activation of newly created Custom Applications.
     */
    public void clickConfirmButton() {
        WebElement confirmationButton = wait.waitForElementEnabled(confirmButton);
        click.moveToElementAndClick(confirmationButton);
    }

    /**
     * click to Go Back to Home Page.
     */
    public void clickGoToBackButton() {
        WebElement backButton = wait.waitForElementPresent(goBackButton);
        click.performDoubleClick(backButton);

    }

	/**
	 * click on activate connector button
	 */
	public void clickOnActivateConnector()
	{
		WebElement activeConnectorField=wait.waitForElementDisplayed(activeConnector);
		click.moveToElementAndClick(activeConnectorField);
	}

	/**
	 * click on dashboard
	 */
	public void clickOnDashBoard()
	{
		WebElement dashboardField=wait.waitForElementDisplayed(dashboardIcon);
		click.moveToElementAndClick(dashboardField);
	}

	/**
	 * hide button on CT
	 */
	public void clickOnHideButton()
	{
		WebElement hidButtonField=wait.waitForElementDisplayed(hideButton);
		if(hidButtonField.isDisplayed())
		click.moveToElementAndClick(hidButtonField);
	}
}
