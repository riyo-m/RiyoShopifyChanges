package com.vertex.quality.connectors.ariba.portal.dialogs.invoice;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * this class represents the tax details dialog when adding them to a non po invoice
 * contains all the methods necessary to interact with it
 *
 * @author osabha
 */
public class AribaPortalNoPoInvoiceAddTaxDialog extends VertexDialog
{
	protected final By visibleCheckBoxLoc = By.className("w-rdo-native");
	protected final By DIALOG_CLASS = By.className("w-dlg-inner-wrapper");
	protected final By searchFieldLoc = By.cssSelector("input[aria-label='Search for a specific value']");
	protected final By searchButtonLoc = By.cssSelector("button[title='Search for a specific value in the list']");
	protected final By selectButtonLoc = By.xpath("//*/tr[contains(@class,'firstRow')]//*/button");
	protected final By checkBoxContLoc = By.className("w-rdo-container");
	protected final By clickableCheckBoxLoc = By.className("w-rdo-dsize");
	protected final By addButtonLoc = By.cssSelector("button[class='w-btn w-btn-primary aw7_w-btn-primary']");
	protected final By dialogFieldsLoc = By.cssSelector("tr[bh='ROV']");
	protected final By dialogHeaderLoc = By.id("headerdialog");
	protected final By fieldElemLoc = By.className("w-chInput");

	public AribaPortalNoPoInvoiceAddTaxDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * searches and selects the desired tax type to add
	 *
	 * @param taxType String of the tax type to select from the list of tax types
	 */
	public void selectTaxType( String taxType )
	{
		String expectedText = "Choose Value for Tax Type";
		WebElement addTaxDialog = wait.waitForElementDisplayed(DIALOG_CLASS);

		{
			WebElement fieldElem = wait.waitForElementEnabled(fieldElemLoc, addTaxDialog);
			fieldElem.sendKeys(Keys.ENTER);
			wait.waitForElementNotDisplayedOrStale(addTaxDialog, 5);
			WebElement chooseValueDialog = wait.waitForElementPresent(DIALOG_CLASS);
			WebElement dialogHeader = wait.waitForElementDisplayed(dialogHeaderLoc, chooseValueDialog);
			String headerText = dialogHeader.getText();
			String cleanText = text.cleanseWhitespace(headerText);

			if ( expectedText.equals(cleanText) )
			{
				WebElement searchField = wait.waitForElementEnabled(searchFieldLoc, chooseValueDialog);
				text.enterText(searchField, taxType);
				WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc, chooseValueDialog);
				click.clickElement(searchButton);
			}

			try{
				WebElement selectButton = wait.waitForElementEnabled(selectButtonLoc, chooseValueDialog);
				click.clickElement(selectButton);
			}catch(org.openqa.selenium.StaleElementReferenceException ex){
				WebElement selectButton = wait.waitForElementEnabled(selectButtonLoc, chooseValueDialog);
				click.clickElement(selectButton);
			}

			waitForPageLoad();
			try
			{
				if ( dialogHeader.isDisplayed() )
				{
					WebElement select = wait.waitForElementEnabled(selectButtonLoc, chooseValueDialog);
					click.clickElement(select);
					waitForPageLoad();
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}

		waitForPageLoad();
	}

	/**
	 * located the radio buttons for the look up tax options and
	 * makes sure the desired option is selected
	 */
	public void enableLookUpByTaxType( )
	{
		WebElement dialogContainer = wait.waitForElementPresent(DIALOG_CLASS);
		List<WebElement> checkButtons = wait.waitForAllElementsPresent(checkBoxContLoc, dialogContainer);
		WebElement desiredButton = checkButtons.get(0);
		WebElement visibleCheckBox = wait.waitForElementPresent(visibleCheckBoxLoc, desiredButton);
		boolean isChecked = checkbox.isCheckboxChecked(visibleCheckBox);
		if ( !isChecked )
		{
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, desiredButton);
			click.clickElement(clickableCheckBox);
		}
		else
		{
			System.out.println("Already checked");
		}
	}

	/**
	 * locates and clicks on the add button in the add tax dialog
	 */
	public void clickAddButton( )
	{
		WebElement dialogContainer = wait.waitForElementPresent(DIALOG_CLASS);
		WebElement addButton = wait.waitForElementEnabled(addButtonLoc, dialogContainer);
		click.clickElement(addButton);
		try
		{
			wait.waitForElementNotDisplayed(DIALOG_CLASS);
		}
		catch ( StaleElementReferenceException e )
		{
		}
	}

	/**
	 * locates the tax amount field and enters it
	 *
	 * @param amount tax amount of the tax type selected
	 */
	public void enterTaxAmount( final String amount ) throws InterruptedException
	{
		WebElement dialogContainer = wait.waitForElementDisplayed(DIALOG_CLASS);
		List<WebElement> fieldsLabels = wait.waitForAnyElementsDisplayed(dialogFieldsLoc, dialogContainer);
		WebElement amountFieldCont = element.selectElementByText(fieldsLabels, "Amount: USD");
		WebElement amountField = wait.waitForElementDisplayed(By.tagName("input"), amountFieldCont);
		text.enterText(amountField, amount);
		Thread.sleep(4000);
	}

	/**
	 * adds tax to item
	 *
	 * @param taxAmount tax amount to add
	 * @param taxType   tax type
	 */
	public void addTaxesToItem( final String taxType, final String taxAmount ) throws InterruptedException
	{
		enableLookUpByTaxType();
		selectTaxType(taxType);
		enterTaxAmount(taxAmount);
		clickAddButton();
	}
}