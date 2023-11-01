package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Vertex XML Log page common methods and object declaration page
 *
 * @author SGupta
 */

public class DFinanceXMLInquiryPage extends DFinanceBasePage
{
	public DFinanceXMLInquiryPage( WebDriver driver )
	{
		super(driver);
	}

	protected By SELECT_RESPONSE = By.cssSelector("input[value ='Response']");
	protected By LOG_RESPONSE = By.xpath("//textarea[contains(@id, 'XMLContent_textArea')]");
	protected By SEARCH_ADDRESS = By.cssSelector("input[tabindex*='-1'][tittle ='%s']");
	protected By FIRST_ROW = By.xpath("//h1[contains(text(), 'Vertex XML Inquiry')]");
	protected By LOG_REQUEST = By.xpath("//textarea[contains(@id, 'XMLContent_textArea')]");
	protected By DOC_ID = By.cssSelector("div[data-dyn-columnname='VTXXmlFiles_DocumentId']");
	protected By DOC_ID_DROPDOWN = By.xpath("//div[text()='Document Id']");
	protected By DOC_TYPE = By.xpath("//div[@aria-rowindex='2']//input[contains(@id,'VTXXmlFiles_VTXDocType')]");
	protected By DOC_TYPE_DROPDOWN = By.xpath("//div[text()='D365 Document' and contains(@class, 'header')]");
	protected By REQUEST_TYPE=By.xpath("(//input[contains(@id,'VTXXmlFiles_VTXRequestType')])[1]");
	protected By REQUEST_TYPE_SELECTION = By.cssSelector("div[data-dyn-columnname='VTXXmlFiles_VTXRequestType']");
	protected By REQUEST_TYPE_DROPDOWN = By.xpath("//div[text()='Request type'][@role='presentation']");
	protected By FILTER = By.cssSelector("input[name*='Filter']");
	protected By DOCUMENT_TYPE_FILTER = By.xpath("//input[contains(@name,'VTXXMLDocType')]");
	protected By APPLY = By.cssSelector("span[id*='Apply']");
	protected By APPLY_DOCUMENT_TYPE = By.xpath("//button[contains(@id, 'VTXXmlFiles_VTXXMLDocType_ApplyFilters')]");
	protected By CREATED_DROPDOWN = By.xpath("//div[text()='Created date and time']");
	protected By CREATED_NEWEST_OPTION = By.xpath("//span[text()='Sort newest to oldest']");
	protected By REQUEST = By.xpath("//input[@value='Request']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"]");
	protected By RESPONSE = By.xpath("//input[@value='Response']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"]");
	protected By QUOTE = By.xpath("//input[@value='Quotation']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"]");
	protected By INVOICE = By.xpath("//input[@value='Invoice']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"]");
	protected By VALIDATION = By.xpath("//input[@value='Validation']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"]");
	Actions action = new Actions(driver);
	protected By REFRESH_LINK = By.xpath("//*[contains(@id,\"RefreshButton\")]/div/span[1]");

	protected String DOCUMENT_ROW_FORMAT = "//input[@value='%s']/ancestor::div[contains(@class, 'Wrapper')]//input[@value='%s']/ancestor::div[contains(@class, 'Wrapper')]//input[@value='%s']";

	/**
     * Select the correct response
     */
	public boolean clickResponse( )
	{
		jsWaiter.sleep(5000);
		boolean isResponsePresent = false;

		if(element.isElementPresent(RESPONSE)) {
			scroll.scrollElementIntoView(RESPONSE);
			wait.waitForElementDisplayed(RESPONSE);
			click.clickElement(RESPONSE);
			isResponsePresent = true;
		}
		return isResponsePresent;
	}

	/**
	 * Search the address
	 */
	public void searchAddress( )
	{
		wait.waitForElementDisplayed(SEARCH_ADDRESS);
		click.clickElement(SEARCH_ADDRESS);
	}

	/**
	 * Get the XML response
	 */
	public String getLogResponseValue( )
	{
		String responseDetails = "";

		if(clickResponse()){
			WebElement text = driver.findElement(LOG_RESPONSE);
			responseDetails = text.getAttribute("value");
		}
		return responseDetails;
	}

	/**
	 * Select the first row in the xml inquiry page
	 */
	public void clickFirstRow( )
	{
		wait.waitForElementDisplayed(FIRST_ROW);
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(@id, 'VTXxmlGrid_RowTemplate_Row0')]"));
		list.get(0).click();
	}

	/**
	 * Get the XML request text area
	 */
	public String getLogRequestValue( )
	{
		WebElement text = driver.findElement(By.xpath("//textarea[contains(@id, 'XMLContent_textArea')]"));
		String requestDetails = text.getAttribute("value");
		return requestDetails;
	}

	/**
	 * Sort the XML inquiry table to show newest documents first
	 */
	public void sortByNewest() {
		wait.waitForElementDisplayed(CREATED_DROPDOWN);
		click.clickElementIgnoreExceptionAndRetry(CREATED_DROPDOWN);

		WebElement newestOption = wait.waitForElementDisplayed(CREATED_NEWEST_OPTION);
		click.clickElementIgnoreExceptionAndRetry(newestOption);
		jsWaiter.sleep(1000);
	}

	/**
	 * Filter XML inquiry
	 * @param docID
	 */
	public void getDocumentID(String docID)
	{
		refreshPage();
		if(!element.isElementPresent(DOC_ID)) {
			driver.manage().window().maximize();
			waitForPageLoad();
			if(!element.isElementPresent(DOC_ID)) {
				WebElement docuIdElement = wait.waitForElementPresent(DOC_ID);
				action.moveToElement(docuIdElement).perform();
			}
		}
		hover.hoverOverElement(DOC_ID);
		click.javascriptClick(DOC_ID_DROPDOWN);
		WebElement filterEle = wait.waitForElementEnabled(FILTER);
		text.enterText(filterEle,docID);
		WebElement fieldElem = wait.waitForElementEnabled(filterEle);
		fieldElem.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Gets the document type
	 * @returns returns the value found
	 */
	public String getDocumentType(){
		waitForPageLoad();

		wait.waitForElementDisplayed(DOC_TYPE);
		String docType = attribute.getElementAttribute(DOC_TYPE, "title");

		return docType;
	}

	/**
	 * Filters the Document Type
	 * @param docType
	 */
	public void selectDocType(String docType)
	{
		hover.hoverOverElement(DOC_TYPE);
		click.javascriptClick(DOC_TYPE_DROPDOWN);
		WebElement filterEle = wait.waitForElementDisplayed(DOCUMENT_TYPE_FILTER);
		text.enterText(filterEle,docType);
		driver.manage().window().setSize(new Dimension(1024,768));
		click.clickElementCarefully(APPLY_DOCUMENT_TYPE);
	}

	/**
	 * Gets the Request type
	 * @returns returns the type found
	 */
	public String getRequestType(){
		wait.waitForElementPresent(REQUEST_TYPE);
		String requestType = attribute.getElementAttribute(REQUEST_TYPE, "value");
		return requestType;
	}

	/**
	 * Gets and selects the Request Type
	 * @param requestType
	 */
	public void getAndSelectRequestType(String requestType){
		WebElement requestEle = wait.waitForElementPresent(REQUEST_TYPE_SELECTION);
		action.moveToElement(requestEle).click(requestEle).perform();
		WebElement filterEle = wait.waitForElementEnabled(FILTER);
		text.setTextFieldCarefully(filterEle,requestType,false);
		click.javascriptClick(APPLY);
	}

	/**
	 * allow page to refresh until the Grid has loaded to display based on F&O processes
	 */
	public void waitForGridToLoad()
	{
		waitForPageLoad();
		waitForGridToLoad();
		By NoElementDisplayed = By.xpath("//*[contains(@id,\"EmptyGridIcon\")]/div");
		int i = 0;
		while(element.isElementDisplayed(NoElementDisplayed) && i<60)
		{
			jsWaiter.sleep(10000);
			click.javascriptClick(REFRESH_LINK);
			i++;
		}
	}

	/**
	 * It clicks on First Request
	 */
	public void clickOnFirstRequest()
	{
		List<WebElement> buttonList = wait.waitForAllElementsPresent(REQUEST);
		WebElement newButton = buttonList.get(0);
		action.click(newButton).perform();
		jsWaiter.sleep(1000); // Static wait till request opens
	}

	/**
	 * It clicks on First Response
	 */
	public void clickOnFirstResponse()
	{
		List<WebElement> buttonList = wait.waitForAllElementsPresent(RESPONSE);
		WebElement newButton = buttonList.get(0);
		click.clickElementCarefully(newButton);
		jsWaiter.sleep(1000); // Static wait till request opens
	}

	/**
	 * Return the actual number of inquiry
	 * @return size of Inquiry list
	 */
	public int getNumberOfInquiries(){
		jsWaiter.sleep(5000);

		int inquiryAmount = 0;

		if(element.isElementPresent(QUOTE)){
			List<WebElement> quoteRequestType = wait.waitForAllElementsDisplayed(QUOTE);
			inquiryAmount = quoteRequestType.size();
		}else if(element.isElementPresent(INVOICE) && element.isElementPresent(VALIDATION)){
			List<WebElement> invoiceRequestType = wait.waitForAllElementsDisplayed(INVOICE);
			List<WebElement> validationRequestType = wait.waitForAllElementsDisplayed(VALIDATION);
			inquiryAmount = invoiceRequestType.size() + validationRequestType.size();
		}

		return inquiryAmount;
	}

	/**
	 * Validates the Financial Dimensions in the XML
	 * @param projectNumber - defined project number
	 * @param generalLedgerAccount - defind general ledger account
	 * @param departmentCode - defined department code
	 * @param costCenter - defined cost center
	 *
	 * @return the total amount of times the each items appear
	 */
	public int financialDimensionAppearanceXML(String projectNumber, String generalLedgerAccount, String departmentCode, String costCenter){
		String value = getLogRequestValue();
		Pattern p = Pattern.compile(projectNumber), g = Pattern.compile(generalLedgerAccount),
				d = Pattern.compile(departmentCode), c = Pattern.compile(costCenter);
		Matcher projectNumberMatch = p.matcher(value), generalLedgerAccountMatch = g.matcher(value),
		          departmentCodeMatch = d.matcher(value), costCenterMatch = c.matcher(value);

		int count = 0;

		while(projectNumberMatch.find() || generalLedgerAccountMatch.find() ||
			  departmentCodeMatch.find() || costCenterMatch.find()){
			count+=1;
		}
		VertexLogger.log("Count: "+count);
		return count;

	}

	public boolean verifyExistenceOfDocument(String messageType, String documentType, String documentNo) {
		By documentRowLoc = By.xpath(String.format(DOCUMENT_ROW_FORMAT, messageType, documentType, documentNo));

		wait.waitForElementDisplayed(documentRowLoc,5);
		return element.isElementDisplayed(documentRowLoc);
	}


}
