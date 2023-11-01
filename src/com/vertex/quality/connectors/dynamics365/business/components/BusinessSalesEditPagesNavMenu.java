package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents the navigation options shared between all different pages editing
 * sales transactions
 *
 * @author cgajes
 */
public class BusinessSalesEditPagesNavMenu extends VertexComponent
{
	protected By navButtonLoc = By.className("ms-Button-textContainer");
	protected By navButtonConLoc = By.className("ms-Button-flexContainer");
    protected By ellipsis = By.xpath("//div[@data-ellipsis-button='true']//div[@class='display-inherit']");
	protected By buttonConLoc = By.className("nav-bar-content");
	protected By buttonLoc = By.className("ms-Button-flexContainer");
	protected By makeInvoiceButtonLoc = By.cssSelector(
		"button[aria-label*='Make Invoice'][title='Convert the sales quote to a sales invoice.']");
	protected By postButtonLoc = By.cssSelector(
		"*[aria-label*='Post'][title='Finalize the document or journal by posting the amounts and quantities to the related accounts in your company books. (F9)']");
	protected By copyDocumentButtonLoc = By.cssSelector("span[aria-label='Copy Document...']");
	protected By moreOptionsButtonConLoc = By.xpath("(//*[text()='More options' and not(@data-is-focusable)])[1]");
	protected By moreOptionsActionsButtonLoc = By.xpath("//*[text()='Actions' and not(@data-is-focusable)]");
	protected By recalculateTaxButtonLoc = By.xpath("//*[text()='Recalculate Tax' and not(@data-is-focusable)]");
	protected By vertexTaxDetailButtonLoc = By.xpath("//*[text()='Vertex Tax Details' and not(@data-is-focusable)]");
	protected By printButtonLoc = By.cssSelector("span[aria-label='Print...']");
	protected By printConfirmationButtonLoc = By.cssSelector("span[aria-label='Print Confirmation...']");
	protected By actionsPostingButtonLoc = By.xpath("//li[contains(@class,'ms-ContextualMenu-item')]//button[@aria-label='Posting']");
	protected By actionsOtherPostingButtonLoc = By.xpath("(//span[text()='Other'])[2]");
	protected By postingButton = By.xpath(".//div[starts-with(@class, 'button')]/.//span/span/span[contains(text(), 'Posting')]");
	protected By postingButtonLoc = By.xpath("//div[contains(@class, 'ms-nav-ColumnFocusZone')]//span[contains(@class, 'command')]/span[@aria-label='Posting' and text() = 'Posting']");
	protected By postingTabLoc = By.xpath("//span[contains(@class,'button')]//span[@aria-label='Posting'][not(@tabindex='-1')]");
	protected By related = By.xpath("//*[text()='Related' and not(@data-is-focusable)]");
	protected By navigateTabLoc1 = By.xpath("(//span[contains(.,'Navigate')])[10]");
	protected By processTab = By.xpath("//*[contains(@aria-label,'Process')][@data-is-focusable='true']");
	protected By navigateTabInReturn = By.xpath("(//span[contains(.,'Navigate')])[7]");
	protected By draftInvoiceButtonLoc = By.cssSelector("*[aria-label='Draft Invoice...']");
	protected By testReportButtonLoc = By.cssSelector("button[aria-label='Test Report...']");
	protected By actionTab = By.cssSelector("span[aria-label*='Actions']");
	protected By recalculateButton = By.xpath("//span//div[text()='Recalculate Tax']");
	protected By printSend = By.xpath("//span[@aria-label='Print/Send'][not (@tabindex)]");
	protected By mainFrameLoc = By.className("designer-client-frame");
	protected By functionsTab= By.xpath("//div[text()='Functions']");
	protected By moveNegativeActions=By.xpath("//button[@aria-label='Move Negative Lines...']");
	protected By docTypeFieldNegativeLines=By.xpath("//a[contains(text(),'To Document Type')]/..//select");
	protected By releaseTabLoc=By.xpath("//div[@data-automation-id=\"visibleContent\" and not(@tabindex)]//span[contains(@class,'button')]//span[@aria-label='Release']");
	protected By serviceMgmtMoreOptionsHeader=By.xpath("//*[@title='Reveal secondary actions' and (@data-is-focusable='true')]");
	protected By serviceMgmtMoreOptionsHeader3rd=By.xpath("(//a[@title='Reveal secondary actions']//span[text()='More options'])[3]");
	protected By postButton = By.xpath("//span[text()='Post...']");
	Actions actions = new Actions(driver);

	public BusinessSalesEditPagesNavMenu( WebDriver driver, VertexPage parent ) {super(driver, parent);}

	/**
	 * this method locates the process button
	 * from nav panel on the dialog page open and then clicks on it
	 */
	public void clickProcess( )
	{
		WebElement processButton = null;
		String expectedText = "Process";
		List<WebElement> listContainers = wait.waitForAllElementsPresent(buttonConLoc);
		for ( WebElement container : listContainers )
		{
			List<WebElement> processButtons = element.getWebElements(buttonLoc, container);
			processButton = element.selectElementByText(processButtons, expectedText);
			if ( processButton != null )
			{
				click.clickElementIgnoreExceptionAndRetry(processButton);
				break;
			}
		}
	}
	/**
	 * Locates the Process button in the process submenu and clicks on it
	 * @author bhikshapathi
	 */
	public void clickProcessButton( )
	{
		WebElement processButton = wait.waitForElementEnabled(processTab);
		click.clickElement(processButton);
	}
	/**
	 * Locates the Make Invoice button in the process submenu and clicks on it
	 */
	public void selectMakeInvoiceButton( )
	{
		WebElement makeInvoiceButton = wait.waitForElementEnabled(makeInvoiceButtonLoc);
		click.clickElement(makeInvoiceButton);
	}

	/**
	 * Locates Posting button and clicks on it
	 */
	public void clickPostingButton( )
	{
		wait.waitForElementEnabled(postingButton);
		click.clickElementIgnoreExceptionAndRetry(postingButton);
	}

	/**
	 * Locates the Post button in the posting submenu and clicks on it
	 */
	public void selectPostButton( )
	{
		WebElement postButton = wait.waitForElementEnabled(postButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(postButton);
	}
	/**
	 * Locates the Posting button in the posting submenu and clicks on it
	 */
	public void clickPostingTab( )
	{
		WebElement postButton = wait.waitForElementDisplayed(postingTabLoc);
		click.clickElementCarefully(postButton);
	}
	/**
	 * Locates the Release button and clicks on it
	 */
	public void clickReleaseTab( )
	{
		WebElement postButton = wait.waitForElementEnabled(releaseTabLoc);
		click.clickElementCarefully(postButton);
	}

	/**
	 * Locates the Prepare button and clicks on it
	 */
	public void clickPrepareButton( )
	{
		String expectedText = "Prepare";
		List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
		for ( WebElement container : listContainers )
		{
			List<WebElement> navigateButtons = container.findElements(navButtonLoc);
			WebElement navigateButton = element.selectElementByText(navigateButtons, expectedText);
			if ( navigateButton != null && navigateButton.isDisplayed() )
			{
				click.clickElementIgnoreExceptionAndRetry(navigateButton);
				break;
			}
		}
	}

	/**
	 * locates the copy document button and clicks on it in the prepare submenu and
	 * clicks on it
	 */
	public void selectCopyDocument( )
	{
		WebElement copyDocument = wait.waitForElementEnabled(copyDocumentButtonLoc);
		click.clickElement(copyDocument);
	}

	/**
	 * locates navigate button and clicks on it
	 */
	public void clickBaseNavigateButton( )
	{
		WebElement navigateButton = null;
		String expectedText = "Navigate";
		List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
		for ( WebElement container : listContainers )
		{
			List<WebElement> navigateButtons = container.findElements(navButtonLoc);
			navigateButton = element.selectElementByText(navigateButtons, expectedText);
			if ( navigateButton != null && navigateButton.isDisplayed() )
			{
				click.clickElement(navigateButton);
				break;
			}
		}
	}
	/**
	 * Clicks on Ellipsis Icon
	 */
	public void clickOnEllipsisIcon()
	{
		List<WebElement> ellipsisContainer = wait.waitForAllElementsPresent(ellipsis,5);
		WebElement ellipsisEle = ellipsisContainer.get((ellipsisContainer.size())-1);
		if(element.isElementDisplayed(ellipsisEle)) {
			click.clickElementIgnoreExceptionAndRetry(ellipsisEle);
		}
	}

	/**
	 * locates the more options button and clicks on it
	 */
	public void clickMoreOptions( )
	{
		try
		{
			WebElement moreOptionEle = wait.waitForElementDisplayed(moreOptionsButtonConLoc,2);
			actions.moveToElement(moreOptionEle).perform();
		}
		catch ( ElementNotInteractableException e ) {
		}

		click.clickElementIgnoreExceptionAndRetry(moreOptionsButtonConLoc);

	}

	/**
	 * locate and click on the actions button that appears after clicking more options
	 */
	public void clickMoreOptionsActionsButton( )
	{

		if (!element.isElementDisplayed(moreOptionsActionsButtonLoc)) {
			clickMoreOptions();
		}
		try
		{
			click.clickElementCarefully(moreOptionsActionsButtonLoc);
		}
		catch ( Exception e )
		{
			click.javascriptClick(moreOptionsActionsButtonLoc);
		}

	}

	/**
	 * locates the Functions in Actions menu
	 */
	public void selectFunctions( )
	{   waitForPageLoad();
		WebElement function = wait.waitForElementPresent(functionsTab);
		click.clickElement(function);
	}

	/**
	 * locate and click on the other button in posting submenu
	 */
	public void selectOtherFunctionsButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(actionsOtherPostingButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(actionsOtherPostingButtonLoc);
	}
	/**
	 * select Move Negative Lines
	 */
	public void selectMoveNegativeLines( )
	{   waitForPageLoad();
		WebElement function = wait.waitForElementPresent(moveNegativeActions);
		click.clickElementIgnoreExceptionAndRetry(function);
	}

	/**
	 * locate and click on the posting button in the Actions submenu
	 */
	public void selectActionsPostingButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(actionsPostingButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(actionsPostingButtonLoc);
	}

	/**
	 * locate and click on the other button in posting submenu
	 */
	public void selectOtherPostingButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(actionsOtherPostingButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(actionsOtherPostingButtonLoc);
	}

	/**
	 * selects the doc type from a dropdown
	 */
	public void  selectOrderDocType(){
		dropdown.selectDropdownByDisplayName(docTypeFieldNegativeLines,"Order");
	}
	/**
	 * locate and click on the Draft Invoice button in the Actions/Posting submenu
	 * On the invoice page
	 */
	public void selectDraftInvoiceButton( )
	{
		WebElement draftButton = wait.waitForElementEnabled(draftInvoiceButtonLoc);
		click.clickElement(draftButton);
	}

	/**
	 * locate and click on the Test Report button in the Actions/Posting submenu
	 * On the credit memo page
	 */
	public void selectTestReportButton( )
	{
		WebElement testReportButton = wait.waitForElementEnabled(testReportButtonLoc);
		click.clickElement(testReportButton);
	}

	/**
	 * Locates the Recalculate Tax button in the Actions submenu and clicks on it
	 */
	public void selectRecalculateTaxButton( )
	{
		waitForPageLoad();

		if (!element.isElementDisplayed(recalculateTaxButtonLoc)) {
			clickMoreOptionsActionsButton();
		}
		try {
			WebElement recalcTaxButton = wait.waitForElementDisplayed(recalculateTaxButtonLoc);
			click.clickElement(recalcTaxButton);
		} catch (TimeoutException e) {

		}
	}

	/**
	 * locate and click on the navigate button that appears after clicking more options
	 */
	public void clickMoreOptionsNavigateButton( )
	{
		WebElement navigateButton = null;
		String expectedText = "Navigate";
		List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
		for ( WebElement container : listContainers )
		{
			List<WebElement> navigateButtons = container.findElements(navButtonLoc);
			Collections.reverse(navigateButtons);
			navigateButton = element.selectElementByText(navigateButtons, expectedText);
			if ( navigateButton != null && navigateButton.isDisplayed() )
			{
				try
				{
					click.clickElement(navigateButton);
					break;
				}
				catch ( ElementNotInteractableException e )
				{
					// clicking navigateButton element consistently fails without this, but works fine with the empty catch
				}
			}
		}
	}
	/**
	 * locates the vertex tax details button in the
	 * more options navigate submenu and clicks on it
	 * @author bhikshapathi
	 */
	public void selectRelated( )
	{
		waitForPageLoad();
		WebElement navigateButton = wait.waitForElementDisplayed(related);
		click.clickElementIgnoreExceptionAndRetry(navigateButton);

	}
	/**
	 * locates the vertex tax details button in the
	 * more options navigate submenu and clicks on it
	 * @author bhikshapathi
	 */
	public void selectActions( )
	{   waitForPageLoad();
		WebElement actionsButton = wait.waitForElementPresent(actionTab);
		click.clickElementIgnoreExceptionAndRetry(actionsButton);

	}
	/**
	 * locates the vertex tax details button in the
	 * more options navigate submenu and clicks on it
	 * @author bhikshapathi
	 */
	public void selectRecalculateTax( )
	{   waitForPageLoad();
		WebElement recalculateTax = wait.waitForElementPresent(recalculateButton);
		click.clickElement(recalculateTax);
	}
	/**
	 * select Navigate From Invoice
	 * @author bhikshapathi
	 */
	public void selectNavigateFromInvoice( )
	{
		WebElement vertexTaxDetailsButton = wait.waitForElementPresent(related);
		click.clickElement(vertexTaxDetailsButton);
	}
	/**
	 * select Navigate From Invoice
	 * @author bhikshapathi
	 */
	public void selectNavigateFromReturnOrde( )
	{
		WebElement vertexTaxDetailsButton = wait.waitForElementPresent(navigateTabInReturn);

		click.clickElement(vertexTaxDetailsButton);
	}
	/**
	 * locates the vertex tax details button in the
	 * more options navigate submenu and clicks on it
	 */
	public void selectVertexTaxDetails( )
	{
		WebElement vertexTaxDetailsButton = wait.waitForElementPresent(vertexTaxDetailButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(vertexTaxDetailsButton);
	}

	/**
	 * locates the print/send button and clicks on it
	 */
	public void clickPrintSendButton( )
	{
		click.clickElementCarefully(printSend);
	}

	/**
	 * locates the print button in the print/send submenu and clicks on it
	 */
	public void selectPrint( )
	{
		WebElement printButton = wait.waitForElementEnabled(printButtonLoc);

		click.clickElement(printButton);
	}

	/**
	 * locates the print confirmation button in the print/send submenu and clicks on it
	 */
	public void selectPrintConfirmation( )
	{
		WebElement printConfButton = wait.waitForElementEnabled(printConfirmationButtonLoc);

		click.clickElement(printConfButton);
	}

	/**
	 * click more options in service Item sheets
	 */
	public void serviceMgmtClickMoreOptions(){
		waitForPageLoad();
		if(element.isElementDisplayed(serviceMgmtMoreOptionsHeader)){
			click.clickElementIgnoreExceptionAndRetry(serviceMgmtMoreOptionsHeader);
		}else if (element.isElementDisplayed(serviceMgmtMoreOptionsHeader3rd)){
			click.clickElementIgnoreExceptionAndRetry(serviceMgmtMoreOptionsHeader3rd);
		}
	}

	/**
	 * Clicks the selected option during posting
	 * @param selectPostingOption
	 */
	public void selectPostingOption(String selectPostingOption){
		WebElement selectedPostingOption = wait.waitForElementDisplayed(By.xpath("//ul//li//input[@title='"+selectPostingOption+"']"));
		click.clickElementCarefully(selectedPostingOption);
	}

	/**
	 * Clicks the Post... button
	 */
	public void clickPostButton(){
		wait.waitForElementDisplayed(postButton);
		click.clickElementCarefully(postButton);
	}
}
