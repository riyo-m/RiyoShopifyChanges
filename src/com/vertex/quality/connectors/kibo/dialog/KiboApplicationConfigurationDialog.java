package com.vertex.quality.connectors.kibo.dialog;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class KiboApplicationConfigurationDialog extends VertexDialog
{
	public KiboApplicationConfigurationDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By companyCodeLoc = By.id("code_input");
	protected By authenticationTrustedIDLoc = By.cssSelector("label[for='authType1']");
	protected By authenticationUserPassLoc = By.cssSelector("label[for='authType2']");
	protected By getConnectionButtonContainerLoc = By.cssSelector("span[class='site-nav__link-text']");
	protected By cloudProductLoc = By.cssSelector("option[value='vertexCloud']");
	protected By errorMessageLoc = By.className("error-text");
	protected By cloudTrustedIdFieldLoc = By.id("tid_input");
	protected By productFieldClass = By.className("span12");
	protected By oseriesPassFieldLoc = By.id("pass_input");
	protected By oseriesUsernameField = By.id("user_input");
	protected By oseriesProductLoc = By.cssSelector("option[value='vertexOSeries']");
	protected By taxDateFieldLoc = By.id("date_sel");
	protected By saveButtonLoc = By.cssSelector("button[type='submit']");
	protected By oseriesLinkFieldLoc = By.id("tax_wsdl_input");
	protected By oseriesTrustedIdLoc = By.cssSelector("input[name='trustedIdOSeries']");
	protected By iFrameName = By.name("mozu-iframe");
	protected By invoiceCheckBoxLoc = By.id("invoice_checkbox");
	protected By closeButtonLoc = By.className("mozu-c-modal__close--default");
	protected By connectionButtonContainerLoc = By.cssSelector("span[class='site-nav__link-text']");
	protected By addressCleansingCheckBoxLoc = By.xpath(".//input[@id='address_checkbox']");
	protected By validateConnectionButtonLoc = By.className("btn--secondary");
	protected By productFieldLoc = By.className("span12");
	protected By settingDialog = By.className("site-container");
    protected By optionsMenu = By.xpath(".//a[normalize-space(.)='Options']");
    protected By connectionMenu = By.xpath(".//a[normalize-space(.)='Connection']");
	protected By flexFieldsMenu = By.xpath(".//a[normalize-space(.)='Flex Fields']");
	protected By allCodeFields = By.xpath(".//h2[text()='Code Fields']/following-sibling::div[1]//tbody//tr");
	protected By allNumericFields = By.xpath(".//h2[text()='Numeric Fields']/following-sibling::div[1]//tbody//tr");
	protected By allDateFields = By.xpath(".//h2[text()='Date Fields']/following-sibling::div[1]//tbody//tr");
    protected By taxCalcInput = By.xpath(".//label[text()='Calculate Tax Endpoint']/following-sibling::input");
    protected By addressCleansingInput = By.xpath(".//label[text()='Address Cleansing Endpoint']/following-sibling::input");
    protected By allSections = By.xpath(".//div//h2");
	protected By savedButton = By.xpath(".//button[text()='Saved'][@disabled]");
	protected By successButton = By.xpath(".//button[text()='Success'][@disabled]");
	// Below all Xpath are dynamic, It is used to set & remove values in tabular formatted UI form & for that we need dynamic value of row
	// Used in setCodeFields, setNumericFields, setDateFields, removeAllCodeFields, removeAllNumericFields, removeAllDateFields methods.
	protected String codeCommonXpath = ".//h2[text()='Code Fields']/following-sibling::div[1]//tbody//tr[";
	protected String numericCommonXpath = ".//h2[text()='Numeric Fields']/following-sibling::div[1]//tbody//tr[";
	protected String dateCommonXpath = ".//h2[text()='Date Fields']/following-sibling::div[1]//tbody//tr[";
	protected String entityCommonXpath = "]//select[contains(@name,'Entity')]";
	protected String fieldCommonXpath = "]//select[contains(@name,'Field')]";
	//****************************Connection**************************//

	/**
	 * Switch to the iFrame of vertex application.
	 */
	public void switchToApplicationFrame() {
		waitForPageLoad();
		window.switchToFrame(iFrameName);
	}

	/**
	 * uses the getter method to locate the connection tab from the left nav panel and then clicks
	 * on it
	 */
	public void clickConnectionButton( )
	{
		WebElement connectionButton = getConnectionTab();
		connectionButton.click();
	}

    /**
     * Clicks on connection to go to options menu setting on vertex application page.
     */
    public void gotoConnection() {
        switchToApplicationFrame();
        WebElement dialog = wait.waitForElementPresent(settingDialog);
        WebElement options = wait.waitForElementPresent(connectionMenu, dialog);
        click.moveToElementAndClick(options);
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSections);
    }

    /**
     * Enter tax calculation url for O-Series from Kibo.
     *
     * @param url pass O-Series tax calc url
     */
    public void enterTaxationUrl(String url) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSections);
        WebElement taxUrlField = wait.waitForElementPresent(taxCalcInput);
        text.enterText(taxUrlField, url);
        text.pressTab(taxUrlField);
    }

    /**
     * Enter address cleansing url for O-Series from Kibo.
     *
     * @param url pass O-Series cleansing url
     */
    public void enterCleansingUrl(String url) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSections);
        WebElement cleansingField = wait.waitForElementPresent(addressCleansingInput);
        text.enterText(cleansingField, url);
        text.pressTab(cleansingField);
    }

	/**
	 * uses the getter method to locate the product tab from the left nav panel and then clicks on
	 * it
	 */
	public void clickProductField( )
	{
		WebElement productField = getProductField();
		productField.click();
	}

	/**
	 * locates and clicks on the OSeries product from the drop list of ProductNames
	 */
	public void selectOSeriesProduct( )
	{
		WebElement oseries = wait.waitForElementPresent(oseriesProductLoc);
		oseries.click();
	}

	/**
	 * locates and clicks the cloud product from the ProductNames dropdown list
	 */
	public void selectCloudProduct( )
	{
		WebElement cloud = wait.waitForElementPresent(cloudProductLoc);
		cloud.click();
	}

	/**
	 * locates the trustedID button under authentication type and clicks on it
	 */
	public void clickAuthenticationTypeTrustedId( )
	{
		WebElement authenticationType = wait.waitForElementPresent(authenticationTrustedIDLoc);

		authenticationType.click();
	}

	/**
	 * locates the username password button under authentication type and then clicks on it
	 */
	public void clickAuthenticationTypeUsernamePassword( )
	{
		WebElement authenticationType = wait.waitForElementPresent(authenticationUserPassLoc);
		checkbox.setCheckbox(authenticationType, true);
	}

	/**
	 * locates the username field and then clears it and sends Oseries username to the field
	 */
	public void enterOseriesUsername( KiboCredentials username )
	{
		String OseriesUserName = username.value;
		WebElement usernameField = wait.waitForElementPresent(oseriesUsernameField);
		text.enterText(usernameField, OseriesUserName);
	}

	/**
	 * locates the password field and then sends Oseries account password
	 */
	public void enterOseriesPassword( KiboCredentials OSeriesPassword )
	{
		String password = OSeriesPassword.value;
		WebElement passwordField = wait.waitForElementPresent(oseriesPassFieldLoc);
		text.enterText(passwordField, password);
	}

	/**
	 * locates the account end point link field and enters the Oseries account link
	 */
	public void enterOseriesLink( KiboCredentials oseriesLink )
	{
		String link = oseriesLink.value;
		WebElement oseriesEndPointLinkField = wait.waitForElementDisplayed(oseriesLinkFieldLoc);
		text.enterText(oseriesEndPointLinkField, link);
	}

	/**
	 * getter method to locate the connection tab on the left from the left nav panel
	 *
	 * @return connection button WebElement
	 */
	protected WebElement getConnectionTab( )
	{
		WebElement connectionButton = null;
		String expectedText = "Connection";
		List<WebElement> connectionButtonContainers = wait.waitForAllElementsPresent(getConnectionButtonContainerLoc);
		connectionButton = element.selectElementByText(connectionButtonContainers, expectedText);
		return connectionButton;
	}

	/**
	 * getter method to locate the product field within the connection tab
	 *
	 * @return product field WebElement
	 */
	protected WebElement getProductField( )
	{
		WebElement productField = null;
		String expectedText = "Vertex O Series";

		driver
			.switchTo()
			.frame(driver.findElement(iFrameName));
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(productFieldLoc));
		List<WebElement> productFieldClasses = wait.waitForAllElementsPresent(productFieldClass);
		productField = element.selectElementByNestedLabel(productFieldClasses, oseriesProductLoc, expectedText);

		return productField;
	}

	/**
	 * this is a getter method to locate the trusted Id button under  Authentication type
	 *
	 * @return TrustedID WebElement
	 */
	protected WebElement getTrustedIdOSeries( )
	{
		WebElement trustedIdField = wait.waitForElementPresent(oseriesTrustedIdLoc);

		return trustedIdField;
	}

	/**
	 * to locate the trustedID field for Cloud product and then enter the trusted ID for cloud in the field
	 */
	public void enterCloudTrustedId( String trustedID )
	{
		WebElement trustedIDField = wait.waitForElementPresent(cloudTrustedIdFieldLoc);
		text.enterText(trustedIDField, trustedID);
	}

	/**
	 * uses the getter method to locate the trusted Id field for the oseries product and then sends
	 * in the trusted Id
	 */
	public void enterOseriesTrustedId( String trustedId )
	{
		WebElement trustedIdField = getTrustedIdOSeries();
		text.enterText(trustedIdField, trustedId);
	}

	/**
	 * locates the company code field and then enters the company code in
	 */
	public void enterCompanyCode( KiboCredentials companyCode )
	{
		WebElement companyCodeField = wait.waitForElementPresent(companyCodeLoc);
		companyCodeField.clear();
		String companyCodeString = companyCode.value;
		companyCodeField.sendKeys(companyCodeString);
	}

	//**************************************Options*************************//

	/**
	 * uses the getter method to locate the options tab from the left nav panel and then clicks on
	 * it
	 */
	public void clickOptionsButton( )
	{
		WebElement optionsButton = getOptionsButton();
		optionsButton.click();
	}

    /**
     * Clicks on options to go to options menu setting on vertex application page.
     */
	public void gotoOptions() {
		switchToApplicationFrame();
		WebElement dialog = wait.waitForElementPresent(settingDialog);
		WebElement options = wait.waitForElementPresent(optionsMenu, dialog);
		click.moveToElementAndClick(options);
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSections);
	}

	/**
	 * uses the getter method to locate the invoicing check box and then clicks on it if it is
	 * checked
	 */
	public void turnOffInvoicing( )
	{
		WebElement invoiceCheckbox = getInvoiceCheckbox();
		checkbox.setCheckbox(invoiceCheckbox, false);
	}

	/**
	 * uses the getter method to locate the invoicing check box and then clicks on it if it is'nt
	 * checked
	 */
	public void turnOnInvoicing( )
	{
		WebElement invoiceCheckbox = getInvoiceCheckbox();
		checkbox.setCheckbox(invoiceCheckbox, true);
	}

	/**
	 * To enable address cleansing by clicking on the check box if not enabled
	 */
	public void enableAddressCleansing( )
	{
		WebElement addressCleansingCheckBox = wait.waitForElementPresent(addressCleansingCheckBoxLoc);
		checkbox.setCheckbox(addressCleansingCheckBox, true);
	}

	/**
	 * To disable  address cleansing by clicking on the check box if not disabled
	 */
	public void disableAddressCleansing( )
	{
		WebElement enableCleansingCheckBox = wait.waitForElementPresent(addressCleansingCheckBoxLoc);
		checkbox.setCheckbox(enableCleansingCheckBox, false);
	}

	/**
	 * Enable or disable address cleansing
	 *
	 * @param enable pass true to enable & false to disable address cleansing
	 */
	public void enableDisableAddressCleansing(boolean enable) {
		WebElement addressCleansing = wait.waitForElementPresent(addressCleansingCheckBoxLoc);
		if (enable && !addressCleansing.isSelected()) {
			click.javascriptClick(addressCleansing);
		} else if (!enable && addressCleansing.isSelected()){
			click.javascriptClick(addressCleansing);
		}
	}

    /**
     * Enable or disable invoice
     *
     * @param enable pass true to enable & false to disable invoice
     */
	public void enableDisableInvoice(boolean enable) {
		WebElement invoice = wait.waitForElementPresent(invoiceCheckBoxLoc);
        if (enable && !invoice.isSelected()) {
            click.javascriptClick(invoice);
        } else if (!enable && invoice.isSelected()){
            click.javascriptClick(invoice);
        }
	}

	/**
	 * locates the options tab from the left nav panel
	 *
	 * @return options tab WebElement
	 */
	protected WebElement getOptionsButton( )
	{
		String expectedText = "Options";
		WebElement optionsButton = null;
		List<WebElement> connectionButtonContainers = wait.waitForAllElementsPresent(connectionButtonContainerLoc);
		optionsButton = element.selectElementByText(connectionButtonContainers, expectedText);
		return optionsButton;
	}

	/**
	 * getter method to locate the invoicing checkBox
	 *
	 * @return invoice check box WebElement
	 */
	protected WebElement getInvoiceCheckbox( )
	{
		WebElement invoiceCheckbox = wait.waitForElementPresent(invoiceCheckBoxLoc);
		return invoiceCheckbox;
	}

	/**
	 * getter method to locate the taxDateField( under Options)
	 *
	 * @return tax date Field WebElement
	 */
	protected WebElement getTaxDateField( )
	{
		WebElement taxDateField = wait.waitForElementPresent(taxDateFieldLoc);

		return taxDateField;
	}

	//*********************************** Flex Fields ***************************************//

	/**
	 * Clicks on Flex Fields to go to Flex Fields menu setting on vertex application page.
	 */
	public void gotoFlexFields() {
		switchToApplicationFrame();
		WebElement dialog = wait.waitForElementPresent(settingDialog);
		WebElement fFields = wait.waitForElementPresent(flexFieldsMenu, dialog);
		click.moveToElementAndClick(fFields);
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allCodeFields);
		wait.waitForAllElementsPresent(allNumericFields);
		wait.waitForAllElementsPresent(allDateFields);
	}

    /**
     * Select values of Source Entity & Source Field code-fields
     *
     * @param entities pass entities which should be set
     * @param fields   pass fields which should be set against entity
     */
	public void setCodeFields(String[] entities, String[] fields) {
		if (entities.length != fields.length) {
			Assert.fail("lengths of both parameters must be same to set code fields");
		}
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allCodeFields);
		for (int i = 0; i < entities.length; i++) {
            dropdown.selectDropdownByDisplayName(By.xpath(codeCommonXpath + i + 1 + entityCommonXpath), entities[i]);
            dropdown.selectDropdownByDisplayName(By.xpath(codeCommonXpath + i + 1 + fieldCommonXpath), fields[i]);
		}
	}

	/**
	 * Removes all flex fields which are set for code-fields
	 */
	public void removeAllCodeFields() {
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allCodeFields);
		int size = element.getWebElements(allCodeFields).size();
		for (int i = 1; i <= size; i++) {
			dropdown.selectDropdownByIndex(By.xpath(codeCommonXpath + i + entityCommonXpath), 1);
		}
	}

	/**
	 * Select values of Source Entity & Source Field for numeric-fields
	 *
	 * @param entities pass entities which should be set
	 * @param fields   pass fields which should be set against entity
	 */
	public void setNumericFields(String[] entities, String[] fields) {
		if (entities.length != fields.length) {
			Assert.fail("lengths of both parameters must be same to set numeric fields");
		}
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allNumericFields);
		for (int i = 0; i < entities.length; i++) {
			dropdown.selectDropdownByDisplayName(By.xpath(numericCommonXpath + i + 1 + entityCommonXpath), entities[i]);
			dropdown.selectDropdownByDisplayName(By.xpath(numericCommonXpath + i + 1 + fieldCommonXpath), fields[i]);
		}
	}

	/**
	 * Removes all flex fields which are set for numeric-fields
	 */
	public void removeAllNumericFields() {
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allNumericFields);
		int size = element.getWebElements(allNumericFields).size();
		for (int i = 1; i <= size; i++) {
			dropdown.selectDropdownByIndex(By.xpath(numericCommonXpath + i + entityCommonXpath), 1);
		}
	}

	/**
	 * Select values of Source Entity & Source Field for date-fields
	 *
	 * @param entities pass entities which should be set
	 * @param fields   pass fields which should be set against entity
	 */
	public void setDateFields(String[] entities, String[] fields) {
		if (entities.length != fields.length) {
			Assert.fail("lengths of both parameters must be same to set date fields");
		}
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allDateFields);
		for (int i = 0; i < entities.length; i++) {
			dropdown.selectDropdownByDisplayName(By.xpath(dateCommonXpath + i + 1 + entityCommonXpath), entities[i]);
			dropdown.selectDropdownByDisplayName(By.xpath(dateCommonXpath + i + 1 + fieldCommonXpath), fields[i]);
		}
	}

	/**
	 * Removes all flex fields which are set for date-fields
	 */
	public void removeAllDateFields() {
		waitForPageLoad();
		wait.waitForAllElementsPresent(allSections);
		wait.waitForAllElementsPresent(allDateFields);
		int size = element.getWebElements(allDateFields).size();
		for (int i = 1; i <= size; i++) {
			dropdown.selectDropdownByIndex(By.xpath(dateCommonXpath + i + entityCommonXpath), 1);
		}
	}

	//************************************dialog-interact-commands***************************//

	/**
	 * this method clicks the save button from the configure application pop up
	 * frame in the vertex connector page
	 */
	public void clickSaveButton( )
	{
		WebElement saveButton = getSaveButton();

		saveButton.click();
	}

	/**
	 * method to click the X and close the configure application pop up
	 */
	public void closeConfigAppPopup( )
	{
		driver
			.switchTo()
			.parentFrame();
		WebElement closeButton = wait.waitForElementDisplayed(closeButtonLoc);
		closeButton.click();
		waitForPageLoad();
	}

	/**
	 * locates the save button from the right bottom corner
	 *
	 * @return save button WebElement
	 */
	protected WebElement getSaveButton( )
	{
		WebElement saveButton = wait.waitForElementPresent(saveButtonLoc);

		return saveButton;
	}

	/**
	 * getter method to locate the validate connection button
	 *
	 * @return WebElement representing the validate connection button
	 */
	protected WebElement getValidateConnectionButton( )
	{
		WebElement validateButton = wait.waitForElementDisplayed(validateConnectionButtonLoc);

		return validateButton;
	}

	/**
	 * uses the getter method to locate the validate connection button and then clicks on it
	 */
	public void clickValidateConnection( )
	{
		WebElement validateButton = getValidateConnectionButton();
		validateButton.click();
		waitForPageLoad();
		wait.waitForElementPresent(savedButton);
	}

	/**
	 * locates the error message element if present and gets the content of it
	 *
	 * @return String of the message present
	 */
	public String getErrorMessage()	{
		wait.waitForElementPresent(savedButton);
		WebElement errorMessage = wait.waitForElementPresent(errorMessageLoc);
		return errorMessage.getText();
	}

    /**
     * Validate whether connection is success or not!
     *
     * @return true or false based on condition
     */
	public boolean verifySuccessConnection() {
		waitForPageLoad();
		return element.isElementPresent(savedButton) && element.isElementPresent(successButton);
	}
}
