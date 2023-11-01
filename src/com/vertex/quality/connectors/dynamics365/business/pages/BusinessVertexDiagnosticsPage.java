package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the Vertex diagnostics page
 *
 * @author cgajes
 */
public class BusinessVertexDiagnosticsPage extends BusinessBasePage
{
	protected By dialogBoxLoc = By.className("ms-nav-content");

	protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
	protected By typeSelectLoc = By.xpath("//div[@controlname='Type']//select");
	protected By accountOrDocumentNumberLoc = By.xpath("//div[@controlname='Account/Document #']//input");
	protected By sendToEmailAddressLoc = By.xpath("//div[@controlname='Email']//input");
	protected By actionsButtonLoc = By.cssSelector("button[aria-label='Actions']");
	protected By runDiagnosticsButtonLoc = By.cssSelector("button[aria-label='Run Diagnostics']");
	protected By dialogTextLoc = By.xpath("//p[contains(@class, 'staticstringcontrol') and not(@tabindex)]");

	public BusinessVertexDiagnosticsPage( WebDriver driver ) { super(driver); }

	public String getDialogBoxText( )
	{
		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement dialogText = wait.waitForElementDisplayed(dialogTextLoc, dialog);

		String txt = dialogText.getText();

		return txt;
	}

	/**
	 * Selects the document or customer type from the dropdown lits
	 *
	 * @param type
	 */
	public void selectType( String type )
	{
		WebElement typeSelectField = wait.waitForElementDisplayed(typeSelectLoc);
		dropdown.selectDropdownByDisplayName(typeSelectField, type);
	}

	/**
	 * Inputs the account or document number into the field
	 *
	 * @param num
	 */
	public void inputAccountOrDocumentNumber( String num )
	{
		WebElement field = wait.waitForElementEnabled(accountOrDocumentNumberLoc);
		text.enterText(field, num);
	}

	/**
	 * Inputs the email address where results will be sent into the field
	 *
	 * @param email
	 */
	public void inputEmailAddress( String email )
	{
		WebElement field = wait.waitForElementEnabled(sendToEmailAddressLoc);
		jsWaiter.sleep(5000);
		text.enterText(field, email);
	}

	public void runDiagnostic( )
	{
		WebElement actionsButton = wait.waitForElementEnabled(actionsButtonLoc);
		click.clickElement(actionsButton);

		WebElement runDiagnosticButton = wait.waitForElementDisplayed(runDiagnosticsButtonLoc);
		click.clickElement(runDiagnosticButton);
	}
}
