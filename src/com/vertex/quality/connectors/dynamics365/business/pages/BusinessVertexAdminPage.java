package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import static com.vertex.quality.connectors.salesforce.TestRerunListener.retryOnError;

/**
 * represents Vertex Admin Page
 *
 * @author osabha, cgajes, bhikshapathi
 */
public class BusinessVertexAdminPage extends BusinessBasePage
{
	protected By categoryHeadersLocs = By.className("caption-text");
	protected By dialogBoxLoc = By.className("ms-nav-content-box");
	protected By transparentOverlayLoc = By.className("overlay-transparent");
	protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
	protected By flexFieldActionDropdownLoc = By.cssSelector("a[title='Actions for Flex Fields List']");
	protected By flexFieldOptionsLocs = By.className("ms-nav-ctxmenu-item");
	protected By commonTableLoc = By.cssSelector("table[id*='BusinessGrid']");
	protected By tableRowLoc = By.cssSelector("tbody tr");
	protected By showMoreLoc = By.cssSelector("a[title='Show more options']");
	protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
	protected By documentNoOpenMenu = By.xpath("//div[@class='ms-nav-grid-columncaption-ctxmenuarrow']/a[contains(@aria-label,'Document/Record No.')]");
	protected By docNoOpenMenu = By.xpath("(//th[@abbr='Document/Record No.'])[2]");
	protected By xmlOpenMenu = By.xpath("(//th[@abbr='XML Type'])[2]");
	protected By dateOpenMenu = By.xpath("(//th[@abbr='Date Created'])[2]");
	protected By documentFilter = By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
	protected By docDescending = By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[2]");
	protected By documentSearch = By.cssSelector("div.ms-nav-content-box.message-dialog input");
	protected By submitingButton = By.xpath("//button/span[contains(.,'OK')]");
	protected By submitingButton1 = By.xpath("//div[@class='ms-nav-actionbar-container has-actions']/button[contains(.,'OK')]");
	protected By log_Responce = By.xpath("//tbody/tr[1]/td[3]/a[@tabindex='0']");
	protected By xmlFrame = By.xpath("//form/main//div[@class='control-addin-container']/iframe[(not(@tabindex))]");
	protected By docLink = By.linkText("Document/Record No.");
	protected By buttonLoc = By.tagName("button");
	protected By noXml = By.xpath("(//tbody/tr[1]/td/span)[5]");
	protected By responceText1 = By.xpath("//div[@id='controlAddIn']/textarea");
	protected By closeXml = By.xpath("(//span[text()='Close'])[1]/..");
	protected By addressCleasingButtonOn= By.xpath("//div[@controlname='Cleanse Address']//div[@aria-checked='false']");
		protected By invoiceRequestEnabledButtonOn= By.xpath("//div[@controlname='Invoice Request Enabled']//div[@aria-checked='false']");
	protected By addressCleasingButtonOff= By.xpath("//div[@controlname='Cleanse Address']//div[@aria-checked='true']");
	protected By invoiceRequestEnabledButtonOff= By.xpath("//div[@controlname='Invoice Request Enabled']//div[@aria-checked='true']");
	protected By addressCleansingField = By.xpath("//div[@controlname='Min. Cleansing Confidence']/.//input");
	protected By chronous = By.xpath("//span[text()='CRONUS USA, Inc.']");
	protected By customers = By.xpath("//span[@aria-label='Customers']");
	protected By rowCountXML=By.xpath("//tbody[@data-focus-zone='true' and not(@data-is-focusable)]/tr");
	protected By accountPayableOff=By.xpath("//div[@controlname='Accounts Payable Enabled']//div[@aria-checked='false']");
	protected By accountPayableOn=By.xpath("//div[@controlname='Accounts Payable Enabled']//div[@aria-checked='true']");
	protected By accountReceivableOn=By.xpath("//div[@controlname='Accounts Receivable Enabled']//div[@aria-checked='true']");
	protected By accountReceivableOff=By.xpath("//div[@controlname='Accounts Receivable Enabled']//div[@aria-checked='false']");
	protected By xmlTable=By.xpath("//div[@controlname='VER_XML Log Page']//span[contains(.,'(There is nothing to show in this view)')]");
	protected By logARTaxRequestOff=By.xpath("//div[@controlname='Log Tax Request']//div[@aria-checked='false']");
	protected By logARTaxRequestOn=By.xpath("//div[@controlname='Log Tax Request']//div[@aria-checked='true']");
	protected By logARTaxResponseOff=By.xpath("//div[@controlname='Log Tax Reponse']//div[@aria-checked='false']");
	protected By logARTaxResponseOn=By.xpath("//div[@controlname='Log Tax Reponse']//div[@aria-checked='true']");
	protected By logARInvoiceRequestOff=By.xpath("//div[@controlname='Log Invoice Request']//div[@aria-checked='false']");
	protected By logARInvoiceRequestOn=By.xpath("//div[@controlname='Log Invoice Request']//div[@aria-checked='true']");
	protected By logARInvoiceResponseOff=By.xpath("//div[@controlname='Log Invoice Response']//div[@aria-checked='false']");
	protected By logARInvoiceResponseOn=By.xpath("//div[@controlname='Log Invoice Response']//div[@aria-checked='true']");
	protected By logAPTaxRequestOff=By.xpath("//div[@controlname='Log AP Tax Request']//div[@aria-checked='false']");
	protected By logAPTaxRequestOn=By.xpath("//div[@controlname='Log AP Tax Request']//div[@aria-checked='true']");
	protected By logAPTaxResponseOff=By.xpath("//div[@controlname='Log AP Tax Response']//div[@aria-checked='false']");
	protected By logAPTaxResponseOn=By.xpath("//div[@controlname='Log AP Tax Response']//div[@aria-checked='true']");
	protected By logAPInvoiceRequestOff=By.xpath("//div[@controlname='Log AP Invoice Request']//div[@aria-checked='false']");
	protected By logAPInvoiceRequestOn=By.xpath("//div[@controlname='Log AP Invoice Request']//div[@aria-checked='true']");
	protected By logAPInvoiceResponseOff=By.xpath("//div[@controlname='Log AP Invoice Response']//div[@aria-checked='false']");
	protected By logAPInvoiceResponseOn=By.xpath("//div[@controlname='Log AP Invoice Response']//div[@aria-checked='true']");
	protected By logAddressRequestOn=By.xpath("//div[@controlname='Log Address Request']//div[@aria-checked='true']");
	protected By logAddressRequestOff=By.xpath("//div[@controlname='Log Address Request']//div[@aria-checked='false']");
	protected By logAddressResponseOn=By.xpath("//div[@controlname='Log Address Reponse']//div[@aria-checked='true']");
	protected By logAddressResponseOff=By.xpath("//div[@controlname='Log Address Reponse']//div[@aria-checked='false']");
	protected By updateLogServiceManagementOff=By.xpath("//div[@controlname='Service Managment Enabled']//div[@aria-checked='false']");
	protected By updateLogServiceManagementOn=By.xpath("//div[@controlname='Service Managment Enabled']//div[@aria-checked='true']");
	protected By manageTabInFlex=By.xpath("//span[@aria-label='Manage']/../../../../..//button");
	protected By createDeleteTabs=By.xpath("//div[contains(@class,'ms-nav-ColumnFocusZone-Column column-container')]//span[(@aria-label)]");
	protected By flexFieldCategoryLoc=By.xpath("//span[@title='Flex Fields']");
	protected By wideLayout=By.xpath("//button[@aria-label='Toggle wide layout' and @data-is-focusable='true']");
	protected By focusMode=By.xpath("(//button[@title=\"Enter focus mode for this part\"])[1]");
	protected By exitFocusMode=By.xpath("//button[@title=\"Exit focus mode\"]");
	protected By readOnlyModeToggle = By.cssSelector("button[title='Open the page in read-only mode.']");
	protected By editMode=By.xpath("//button[@title='Make changes on the page.']");
	protected By flexValue=By.xpath("//tr[1]/td[@controlname='Value (General)']/span");
	protected By xmlAdminLogNotExpanded = By.xpath("//span[text()='XML Log Admin']//..//..//span[@aria-expanded='false']");
	protected By xmlLog = By.xpath("//span[text()='XML Log']");
	protected By companyCode = By.xpath("//div[@controlname='Vertex Portal Company']//div//input");
	protected By trustedId = By.xpath("//div[@controlname='Trusted ID']//div//input");
	protected By taxCalculationServerAddress = By.xpath("//div[@controlname='Tax Calc Server Address']//div//input");
	protected By addressValidationServerAddress = By.xpath("//div[@controlname='Address Validation Server']//div//input");
	Actions actions = new Actions(driver);
	public BusinessVertexAdminPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates the back arrow and clicks on it to save the changes on the page and close it
	 */
	public void clickBackAndSaveArrow( )
	{
		List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
		WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
		wait.waitForElementEnabled(backArrow);
		click.clickElementIgnoreExceptionAndRetry(backArrow);
		waitForPageLoad();
	}

	/**
	 * Clicks the header for the XML Logs category
	 * to open the section
	 */
	public void openXmlLogCategory( )
	{
		WebElement xmlLogHeader = wait.waitForElementPresent(xmlLog);
		actions.moveToElement(xmlLogHeader).perform();
		actions.moveToElement(xmlLogHeader).click(xmlLogHeader).perform();
	}
	/**
	 * Clicks the header for the XML Logs category
	 * to open the section
	 */
	public void openXmlLogAdmin( )
	{
		scroll.scrollBottom();
		List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
		WebElement xmlLog = element.selectElementByText(categoriesList, "XML Log Admin");
		if(element.isElementPresent(xmlAdminLogNotExpanded)) {
			click.clickElementCarefully(xmlLog);
		}
	}
	/**
	 * Verify No xml after updating address
	 * to open the section
	 */
	public String checkNoXml( )
	{
		WebElement xmlLog = wait.waitForElementDisplayed(noXml);
		String response=xmlLog.getText();
		return response;
	}
	/**
	 * Clicks on filter Documents
	 * to open the section
	 * @author bhikshapathi
	 */
	public void filterDocuments(String docNo) {
		waitForPageLoad();
		jsWaiter.sleep(5000); //static wait till response is available
		WebElement documentOpenMenu = wait.waitForElementPresent(docNoOpenMenu);
		scroll.scrollElementIntoView(documentOpenMenu, PageScrollDestination.VERT_CENTER);
		waitForPageLoad();
		actions.moveToElement(documentOpenMenu).perform();
		try {
			WebElement documentOpenMenuArrow = wait.waitForElementDisplayed(documentNoOpenMenu);
			actions.moveToElement(documentOpenMenuArrow).click(documentOpenMenuArrow).perform();
		}catch(Exception e){
			VertexLogger.log(e.toString());
			WebElement documentOpenMenuArrow = wait.waitForElementPresent(documentNoOpenMenu);
			click.clickElementCarefully(documentOpenMenuArrow);
		}

		waitForPageLoad();

		if (!element.isElementDisplayed(documentFilter)) {
			click.javascriptClick(documentNoOpenMenu);
		}

		click.clickElementCarefully(documentFilter);

		waitForPageLoad();

		if (!element.isElementDisplayed(documentSearch)) {
			click.javascriptClick(documentFilter);
		}

		WebElement docSearch = wait.waitForElementDisplayed(documentSearch);
		text.enterText(docSearch,docNo);
		click.clickElementCarefully(submitingButton);
		waitForPageLoad();
	}
	/**
	 * Clicks on filter Documents
	 * to open the section
	 * @author bhikshapathi
	 */
	public void filterxml(String xmlType ){
		waitForPageLoad();
		WebElement documentOpenMenu = wait.waitForElementDisplayed(xmlOpenMenu);
		actions.moveToElement(documentOpenMenu).click(documentOpenMenu).perform();
		waitForPageLoad();
		click.clickElementCarefully(documentFilter);
		WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
		text.enterText(docSearch,xmlType);
		click.clickElementIgnoreExceptionAndRetry(submitingButton1);
		waitForPageLoad();
	}
	/**
	 * Sorts the documents in descending order (latest first)
	 */
	public void sortDocuments(){
		waitForPageLoad();
		WebElement documentOpenMenu = wait.waitForElementDisplayed(dateOpenMenu);
		actions.moveToElement(documentOpenMenu).click(documentOpenMenu).perform();
		waitForPageLoad();
		click.clickElementCarefully(docDescending);
	}
	/**
	 * Clicks the expand button to show wide layout view of the page
	 */
	public void changeToWideLayout(){
	WebElement wideLayoutButton= wait.waitForElementDisplayed(wideLayout);
	click.clickElementCarefully(wideLayoutButton);
	}

	/**
	 * Clicks the header for the Flex Fields category
	 * to open the section
	 */
	public void openFlexFieldsCategory( )
	{
		if (!element.isElementDisplayed(manageTabInFlex))
		{
			WebElement flexFieldCategory=wait.waitForElementDisplayed(flexFieldCategoryLoc);
			jsWaiter.sleep(5000);
			retryOnError(() -> {
				actions.moveToElement(flexFieldCategory).click(flexFieldCategory).perform();
			}, 25);
		}
		else
		{
			WebElement manage = wait.waitForElementDisplayed(manageTabInFlex);
			retryOnError(() -> {
				actions.moveToElement(manage).perform();
			}, 25);
		}
	}

	/**
	 * Opens the flex field actions menu and selects the option to create
	 * a new flex field
	 *
	 * @return flex field page
	 */
	public BusinessFlexFieldsPage flexFieldCreateNew( )
	{
		WebElement actions = wait.waitForElementDisplayed(flexFieldActionDropdownLoc);
		click.clickElement(actions);

		List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);
		WebElement newAction = element.selectElementByText(actionList, "New");
		click.clickElement(newAction);

		return initializePageObject(BusinessFlexFieldsPage.class);
	}

	/**
	 * Creates a new flex field
	 */
	public void createNewFlexFieldEntry(){
		waitForPageLoad();
		click.javascriptClick(focusMode);
		waitForPageLoad();
		WebElement manageTab=wait.waitForElementDisplayed(manageTabInFlex);
		click.clickElementCarefully(manageTab);

		if (!element.isElementDisplayed(createDeleteTabs)) {
			click.javascriptClick(manageTab);
		}

		List<WebElement> buttons=wait.waitForAllElementsDisplayed(createDeleteTabs);
		WebElement buttonSelect=element.selectElementByText(buttons,"New Line");
		click.clickElement(buttonSelect);
	}

	/**
	 * Click the exit button on enlarged view
	 */
	public void exitFocusMode(){
		click.clickElementCarefully(exitFocusMode);
		waitForPageLoad();
	}
	/**
	 * Click the button on flex fields to make it readonly view
	 */
	public void toggleReadOnlyMode() {
		WebElement toggle = wait.waitForElementPresent(readOnlyModeToggle);
		click.clickElement(toggle);
		wait.waitForElementNotPresent(readOnlyModeToggle);
	}
	/**
	 * Finds a flex field in the flex field table based on the information displayed in the row
	 *
	 * @param source
	 * @param type
	 * @param id
	 * @param value
	 *
	 * @return row containing the flex field
	 */
	public WebElement getFlexFieldRow( String source, String type, String id, String value )
	{
		WebElement getRow = null;
		String expectedText = String.format("%1$s %2$s %3$s %4$s", source, type, id, value);

		List<WebElement> tableList = wait.waitForAllElementsPresent(commonTableLoc);
		WebElement table = tableList.get(tableList.size() - 1);

		List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowLoc, table);

		for ( WebElement row : tableRows )
		{
			String text = row.getText();
			if ( text.startsWith(expectedText) )
			{
				getRow = row;
				break;
			}
		}
		return getRow;
	}

	/**
	 * Gets the value of Flex Fields entry
	 * @return value returns the flex value
	 */
	public String getFlexValue() {
		int rowIndex = 1;
		String firstRowXpath = String.format("//caption[text()='Flex Fields']/../tbody/tr[%s]/td[4]", rowIndex);
		WebElement firstRow = wait.waitForElementDisplayed(By.xpath(firstRowXpath));
		click.javascriptClick(firstRow);
		WebElement valueField = wait.waitForElementPresent(flexValue);
		String value = attribute.getElementAttribute(valueField,"title");
		return value;
	}

	/**
	 * Opens the flex field
	 *
	 * @param rowToOpen
	 *
	 * @return the flex field page
	 */
	public BusinessFlexFieldsPage openFlexFieldRow( WebElement rowToOpen )
	{
		WebElement moreOptionsButton = wait.waitForElementEnabled(showMoreLoc, rowToOpen);
		click.clickElement(moreOptionsButton);

		List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);
		WebElement viewAction = element.selectElementByText(actionList, "View");

		click.clickElement(viewAction);

		return initializePageObject(BusinessFlexFieldsPage.class);
	}

	/**
	 * Deletes the flex field
	 */
	public void deleteField(){
		click.clickElementCarefully(editMode);
		WebElement manageTab=wait.waitForElementDisplayed(manageTabInFlex);
		click.clickElementCarefully(manageTab);
		List<WebElement> buttons=wait.waitForAllElementsDisplayed(createDeleteTabs);
		WebElement buttonSelect=element.selectElementByText(buttons,"Delete Line");
		click.clickElement(buttonSelect);
		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "Yes");
		click.clickElement(button);

		wait.waitForElementNotDisplayedOrStale(button, 15);
	}

	/**
	 * Checks the action options for an existing flex field to ensure there is no option
	 * to edit the field
	 *
	 * @param rowToCheck
	 *
	 * @return true if no edit option is found, false if one is
	 */
	public boolean ensureNoEditOptionOnExistingFlexField( WebElement rowToCheck )
	{
		boolean noEdit = true;

		WebElement moreOptionsButton = wait.waitForElementEnabled(showMoreLoc, rowToCheck);
		click.clickElement(moreOptionsButton);

		List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);

		for ( WebElement actionOption : actionList )
		{
			String text = actionOption.getText();
			if ( "Edit".equalsIgnoreCase(text) )
			{
				noEdit = false;
			}
		}

		WebElement overlay = wait.waitForElementPresent(transparentOverlayLoc);
		click.clickElement(overlay);

		return noEdit;
	}

	/**
	 * Checks each row of the XML log table to see if the given document number
	 * exists in the table (so, if there is an XML log for that document)
	 *
	 * @param docNumber
	 *
	 * @return whether the document number is present, as a boolean
	 */
	public boolean checkXmlTableForDocumentNumber( String docNumber )
	{
		boolean numPresent = false;

		List<WebElement> tableList = wait.waitForAllElementsPresent(commonTableLoc);
		WebElement xmlTable = tableList.get(tableList.size() - 1);
		List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowLoc, xmlTable);

		for ( WebElement row : tableRows )
		{
			String rowText = row.getText();
			if ( rowText.contains(docNumber) )
			{
				numPresent = true;
				break;
			}
		}

		return numPresent;
	}
	/**
	 * Open xml file based on the  type  from table
	 * @return xml content text
	 * @author dpatel, bhikshapathi
	 */
	public String clickOnFirstLinkAndGetTheXml()
	{
		String response_Request_Details;

		navigateToXMLFiles();

		WebDriverWait shortWait = new WebDriverWait(driver, SIX_SECOND_TIMEOUT);
		WebElement ele = wait.waitForElementDisplayed(responceText1);
		response_Request_Details= attribute.getElementAttribute(ele,"value");
		driver.switchTo().parentFrame();
		shortWait
				.ignoring(TimeoutException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(closeXml));
		click.moveToElementAndClick(closeXml);

		return response_Request_Details;
	}

	/**
	 * Open the xml file
	 */
	public void navigateToXMLFiles(){
		waitForPageLoad();
		sortDocuments();
		WebElement logResponse = wait.waitForElementPresent(log_Responce);
		actions.moveToElement(logResponse).click(logResponse).perform();
		waitForPageLoad();
		if(!element.isElementDisplayed(xmlFrame)) {
			click.clickElementIgnoreExceptionAndRetry(logResponse);
		}
		WebElement xmlframe = wait.waitForElementPresent(xmlFrame);
		driver.switchTo().frame(xmlframe);
	}

	/**
	 * Generalized method, for update Confidence Factor
	 * @author bhikshapathi
	 */
	public void updateConfidenceFactor(String AddressCleansing)
	{
		WebElement element=wait.waitForElementDisplayed(addressCleansingField);
		text.clearText(element);
		text.enterText(element,AddressCleansing);

	}
	/**
	 * Get xml Response Count
	 * @author bhikshapathi
	 */
	public int getResponseCount(String orderNo)
	{
		List<WebElement> rows = driver.findElements(By.xpath("//td/span[contains(text(),'"+orderNo+"')]"));
		int count = rows.size();
		return count;
	}
	/**
	 * Generalized method, for invoice Request Enabled Off
	 * @author bhikshapathi
	 */
	public void invoiceRequestEnabledOff()
	{
		WebElement invoiceEnabled;
		if(element.isElementDisplayed(invoiceRequestEnabledButtonOff))
		{
			invoiceEnabled=wait.waitForElementDisplayed(invoiceRequestEnabledButtonOff);
			click.clickElementIgnoreExceptionAndRetry(invoiceEnabled);
		}



	}
	/**
	 * Generalized method, for invoice Request Enabled On
	 * @author bhikshapathi
	 */
	public void invoiceRequestEnabledOn()
	{
		scroll.scrollElementIntoView(invoiceRequestEnabledButtonOn);
		waitForPageLoad();
		if(element.isElementDisplayed(invoiceRequestEnabledButtonOn)){
			click.clickElementCarefully(invoiceRequestEnabledButtonOn);
			waitForPageLoad();
		}

	}
	/**
	 * Generalized method, for update Address Cleansing Off
	 * @author bhikshapathi
	 */
	public void updateAddressCleansingOn()
	{   waitForPageLoad();
		if(element.isElementDisplayed(addressCleasingButtonOn)) {
			click.clickElementCarefully(addressCleasingButtonOn);
			waitForPageLoad();
		}
	}
	/**
	 * Generalized method, for update Address Cleansing Off
	 * @author bhikshapathi
	 */
	public void updateAddressCleansingOff()
	{   waitForPageLoad();
		if (element.isElementDisplayed(addressCleasingButtonOff))
		{
			click.clickElementCarefully(addressCleasingButtonOff);
			waitForPageLoad();
		}
	}

	/**
	 * Navigate Back to Home Page
	 */
	public void navigateBackToHome()
	{
		List<WebElement> mainLink = wait.waitForAllElementsPresent(chronous);
		click.javascriptClick(mainLink.get((mainLink.size())-1));
		List<WebElement> customersEleList = wait.waitForAllElementsPresent(chronous);
		wait.waitForElementDisplayed(customersEleList.get((mainLink.size())-1));
	}

	/**
	 * checks if no calls are made in XML log field
	 */
	public String validatingNoXMLLog(){
		WebElement ele=wait.waitForElementDisplayed(xmlTable);
		String msg=ele.getText();
		return msg;
	}


	/**
	 * Generalized method, for update account Payable to Off
	 */
	public void updateAccountPayableToOff(){
		if(element.isElementDisplayed(accountPayableOn)){
			click.clickElementCarefully(accountPayableOn);
			waitForPageLoad();
		}
}

	/**
	 * Generalized method, for update account Payable to On
	 */
	public void updateAccountPayableToOn(){
		if(element.isElementDisplayed(accountPayableOff)){
			click.clickElementCarefully(accountPayableOff);
			waitForPageLoad();
		}
}
	/**
	 * Generalized method, for update Account Receivable toggle button to Off
	 */
	public void updateAccountReceivableToOff(){
		if(element.isElementDisplayed(accountReceivableOn)){
			click.clickElementCarefully(accountReceivableOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Account Receivable toggle button to On
	 */
	public void updateAccountReceivableToOn(){
		if(element.isElementDisplayed(accountReceivableOff)){
			click.clickElementCarefully(accountReceivableOff);
			waitForPageLoad();
		}
	}
	/**
	 * Generalized method, for update Log ARTaxRequest to Off
	 */
	public void updateLogARTaxRequestToOff(){
		if(element.isElementDisplayed(logARTaxRequestOn)){
			click.clickElementCarefully(logARTaxRequestOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log ARTaxRequest to On
	 */
	public void updateLogARTaxRequestToOn(){
		if(element.isElementDisplayed(logARTaxRequestOff)){
			click.clickElementCarefully(logARTaxRequestOff);
			waitForPageLoad();
		}
	}
	/**
	 *Generalized method, for update Log ARTaxResponse to Off
	 */

	public void updateLogARTaxResponseOff(){
		if(element.isElementDisplayed(logARTaxResponseOn)){
			click.clickElementCarefully(logARTaxResponseOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log ARTaxResponse to On
	 */
	public void updateLogARTaxResponseOn(){
		if(element.isElementDisplayed(logARTaxResponseOff)){
			click.clickElementCarefully(logARTaxResponseOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update log AR Invoice Request to Off
	 */
	public void updateLogARInvoiceRequestOff(){
		if(element.isElementDisplayed(logARInvoiceRequestOn)){
			click.clickElementCarefully(logARInvoiceRequestOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log AR Invoice Request to On
	 */
	public void updateLogARInvoiceRequestOn(){
		if(element.isElementDisplayed(logARInvoiceRequestOff)){
			click.clickElementCarefully(logARInvoiceRequestOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update log AR Invoice Response to Off
	 */
	public void updateLogARInvoiceResponseOff(){
		if(element.isElementDisplayed(logARInvoiceResponseOn)){
			click.clickElementCarefully(logARInvoiceResponseOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log AR Invoice Response to On
	 */
	public void updateLogARInvoiceResponseOn(){
		if(element.isElementDisplayed(logARInvoiceResponseOff)){
			click.clickElementCarefully(logARInvoiceResponseOff);
			waitForPageLoad();
		}
	}
	/**
	 * Generalized method, for update Log AP TaxRequest to Off
	 */
	public void updateLogAPTaxRequestToOff(){
		if(element.isElementDisplayed(logAPTaxRequestOn)){
			click.clickElementCarefully(logAPTaxRequestOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log AP TaxRequest to On
	 */
	public void updateLogAPTaxRequestToOn(){
		if(element.isElementDisplayed(logAPTaxRequestOff)){
			click.clickElementCarefully(logAPTaxRequestOff);
			waitForPageLoad();
		}
	}
	/**
	 *Generalized method, for update Log AP TaxResponse to Off
	 */

	public void updateLogAPTaxResponseOff(){
		if(element.isElementDisplayed(logAPTaxResponseOn)){
			click.clickElement(logAPTaxResponseOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log AP TaxResponse to On
	 */
	public void updateLogAPTaxResponseOn(){
		if(element.isElementDisplayed(logAPTaxResponseOff)){
			click.clickElementCarefully(logAPTaxResponseOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update log AP Invoice Request to Off
	 */
	public void updateLogAPInvoiceRequestOff(){
		if(element.isElementDisplayed(logAPInvoiceRequestOn)){
			click.clickElementCarefully(logAPInvoiceRequestOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log AP Invoice Request to On
	 */
	public void updateLogAPInvoiceRequestOn(){
		if(element.isElementDisplayed(logAPInvoiceRequestOff)){
			click.clickElementCarefully(logAPInvoiceRequestOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update log AP Invoice Response to Off
	 */
	public void updateLogAPInvoiceResponseOff(){
		if(element.isElementDisplayed(logAPInvoiceResponseOn)){
			click.clickElementCarefully(logAPInvoiceResponseOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update log AP Invoice Response to On
	 */
	public void updateLogAPInvoiceResponseOn(){
		if(element.isElementDisplayed(logAPInvoiceResponseOff)){
			click.clickElementCarefully(logAPInvoiceResponseOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log address Request to Off
	 */
	public void updateLogAddressRequestOff(){
		if(element.isElementDisplayed(logAddressRequestOn)){
			click.clickElementCarefully(logAddressRequestOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log address Request to On
	 */
	public void updateLogAddressRequestOn(){
		if(element.isElementDisplayed(logAddressRequestOff)){
			click.clickElementCarefully(logAddressRequestOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log address Request to Off
	 */
	public void updateLogAddressResponseOff(){
		if(element.isElementDisplayed(logAddressResponseOn)){
			click.clickElementCarefully(logAddressResponseOn);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, for update Log address Request to On
	 */
	public void updateLogAddressResponseOn(){
		if(element.isElementDisplayed(logAddressResponseOff)){
			click.clickElementCarefully(logAddressResponseOff);
			waitForPageLoad();
		}
	}
	/**
	 * Generalized method, enable service management to On and to log
	 */
	public void updateServiceManagementOn(){
		if(element.isElementDisplayed(updateLogServiceManagementOff)){
			click.clickElementCarefully(updateLogServiceManagementOff);
			waitForPageLoad();
		}
	}

	/**
	 * Generalized method, to edit contents
	 */
	public void editMode() {
		WebElement editButton = wait.waitForElementPresent(editMode);
		click.clickElement(editButton);
		wait.waitForElementNotPresent(editMode);
	}

	/**
	 * Enter "Company Code"
	 * @return - company code value
	 * @param companyCodeVal
	 */
	public String setCompanyCode( String companyCodeVal )
	{
		String companyCodeOutput = attribute.getElementAttribute(companyCode, "value");

		wait.waitForElementDisplayed(companyCode);
		click.clickElementCarefully(companyCode);
		text.enterText(companyCode, companyCodeVal);

		return companyCodeOutput;

	}

	/**
	 * Enter "Trusted ID"
	 * return - trusted id value
	 * @param trustedID
	 */
	public String setTrustedId( String trustedID )
	{
		String trustedIdOutput = attribute.getElementAttribute(trustedId, "value");

		wait.waitForElementDisplayed(trustedId);
		click.clickElementCarefully(trustedId);
		text.enterText(trustedId, trustedID);

		return trustedIdOutput;
	}

	/**
	 * Enter "Tax Calculation URL"
	 * return - tax calculation url
	 * @param taxCalculationUrl
	 */
	public String setTaxCalculationServerAddress( String taxCalculationUrl )
	{
		String taxCalculationOutput = attribute.getElementAttribute(taxCalculationServerAddress, "value");

		wait.waitForElementDisplayed(taxCalculationServerAddress);
		click.clickElementCarefully(taxCalculationServerAddress);
		text.enterText(taxCalculationServerAddress, taxCalculationUrl);

		return taxCalculationOutput;
	}

	/**
	 * Enter "Address Validation URL"
	 * return - address validation url
	 * @param addressValidationUrl
	 */
	public String setAddressValidationServerAddress( String addressValidationUrl )
	{
		String addressValidationOutput = attribute.getElementAttribute(addressValidationServerAddress, "value");

		wait.waitForElementDisplayed(addressValidationServerAddress);
		click.clickElementCarefully(addressValidationServerAddress);
		text.enterText(addressValidationServerAddress, addressValidationUrl);

		return addressValidationOutput;
	}

}
