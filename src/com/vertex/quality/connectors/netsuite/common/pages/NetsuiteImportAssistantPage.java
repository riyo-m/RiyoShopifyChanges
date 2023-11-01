package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

/**
 * Netsuite Import Assistant Page
 */
public class NetsuiteImportAssistantPage extends NetsuitePage
{

	private By importTypeDropdown = By.id("inpt_recordtype1");
	private By recordTypeDropdown = By.id("inpt_recordsubtype2");
	private By multiCSVQuantityRadio = By.xpath("//*[@id=\"filetype_fs_inp\"][2]");
	private By primaryFileUploadButton = By.id("fileupload0");
	private By transactionItemsUploadButton = By.id("fileupload2");
	private By nextPageButton = By.id("next");
	private By secondaryNextPageButton = By.id("secondarynext");
	private By primaryKeyColumnDropdown = By.id("primarykeycolumn_fs");
	private By item1ColumnDropdown = By.id("keycolumn1_fs");
	private By importMapNameTextbox = By.id("mapname");
	private By confirmationAlertloc = By.id("div__alert");
	private By saveAndRunButton = By.id("btn_multibutton_saveandexecute");

	public NetsuiteImportAssistantPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Selects Import Type
	 * @param type
	 */
	public void selectImportType(String type)
	{
		setDropdownToValue(importTypeDropdown, type);
	}

	/**
	 * Select Record Type
	 * @param type
	 */
	public void selectRecordType(String type)
	{
		setDropdownToValue(recordTypeDropdown, type);
	}

	/**
	 * Select Upload Type
	 * @param radioLabel
	 */
	public void selectUploadType(String radioLabel)
	{
		selectRadioButton(radioLabel);
	}

	/**
	 * Upload primary file
	 * @param filepath
	 */
	public void uploadPrimaryFile(String filepath)
	{
		WebElement upload = element.getWebElement(primaryFileUploadButton);
		upload.sendKeys(filepath);
	}

	/**
	 * Upload Items file
	 * @param filepath
	 */
	public void uploadItemsFile(String filepath)
	{
		WebElement upload = element.getWebElement(transactionItemsUploadButton);
		upload.sendKeys(filepath);
	}

	/**
	 * Go to next Item
	 */
	public void goNext()
	{
		WebElement nextPageElement = wait.waitForElementDisplayed(nextPageButton);
		click.clickElement(nextPageElement);
	}

	/**
	 * Go to Secondary item
	 */
	public void goSecondaryNext()
	{
		WebElement nextPageElement = wait.waitForElementDisplayed(secondaryNextPageButton);
		click.clickElement(nextPageElement);
	}

	/**
	 * Select Primary File Key
	 * @param key
	 */
	public void selectPrimaryFileKey(String key)
	{
		setDropdownToValue(primaryKeyColumnDropdown, key);
	}

	/**
	 * Select Item File Key
	 * @param key
	 */
	public void selectItemFileKey(String key)
	{
		setDropdownToValue(item1ColumnDropdown, key);
	}

	/**
	 * Enter Random Map Name
	 */
	public void enterRandomMapName()
	{
		String uuid = UUID.randomUUID().toString().replace("-", "");
		text.enterText(importMapNameTextbox, uuid);
	}

	/**
	 * Save and Run
	 */
	public void saveAndRun()
	{
		WebElement saveAndRunElement = wait.waitForElementDisplayed(saveAndRunButton);
		click.clickElement(saveAndRunElement);
	}

	/**
	 * Click Alert
	 * @return
	 */
	public NetsuiteJobStatusPage clickAlert()
	{
		WebElement confirmationAlert = element.getWebElement(confirmationAlertloc);
		WebElement importJobStatusButton = element.getWebElement(By.tagName("a"), confirmationAlert);
		click.clickElement(importJobStatusButton);
		return initializePageObject(NetsuiteJobStatusPage.class);
	}
}
