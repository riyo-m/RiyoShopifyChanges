package com.vertex.quality.connectors.dynamics365.retail.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Retail --> Channel set up, POS set up - Register page for backup tax group validation
 *
 * @author Sgupta,dpatel
 */

public class DRetailPOSRegisterPage extends DFinanceBasePage
{
	protected By SELECT_REGISTER = By.xpath("//div[@aria-rowindex='2']//input[contains(@id, 'RBOTerminalTable_terminalId')]");
	protected By VERTEX_LITE = By.xpath("//button[contains(text(),'Vertex LITE')]");
	protected By BACKUP_TAX_GRP = By.cssSelector("input[id*='VTXTaxGroupBackup_input']");
	protected By ENTER_TEXT = By.xpath("//div[@data-dyn-controlname='RetailTerminalTable_VTXTaxGroupBackup']/span[2]");
	protected final By VERTEX_LITE_HEADER = By.xpath("//div[@data-dyn-controlname='TabVTXVertexLITE']//div[@aria-level]");
	Actions actions = new Actions(driver);


	public DRetailPOSRegisterPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Select the first POS Register from the list
	 */
	public void selectRegister( )
	{
		WebElement selectRegister = wait.waitForElementDisplayed(SELECT_REGISTER);
		click.javascriptClick(SELECT_REGISTER);
		selectRegister.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Expand Vertex Lite
	 */
	public void clickVetexLite( )
	{
		WebElement vertexLite = wait.waitForElementPresent(VERTEX_LITE);
		WebElement CUSTOMER_HEADER = driver.findElement(VERTEX_LITE_HEADER);
		if ( CUSTOMER_HEADER
			.getAttribute("aria-level")
			.equals("2") )
		{
			actions.moveToElement(vertexLite).click(vertexLite).perform();
		}
		waitForPageLoad();
	}

	/**
	 * Verify Backup Tax Group Field is displayed and enter value
	 */
	public void setBackupTaxGRP(String backupTaxGrp )
	{
		wait.waitForElementDisplayed(BACKUP_TAX_GRP);
		click.clickElement(BACKUP_TAX_GRP);
		text.setTextFieldCarefully(BACKUP_TAX_GRP,backupTaxGrp);
	}

}
