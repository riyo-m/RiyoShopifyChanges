package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * generic repersentation of page class for product type
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsProductTypePage extends CommerceToolsBasePage
{

	public CommerceToolsProductTypePage( final WebDriver driver )
	{
		super(driver);
	}

	private final By settingsIcon=By.xpath("//*[contains(text(),'Settings')]");
	private final By productTypesLink=By.xpath("//*[contains(text(),'Product types')]");
	private final By createProductTypeLink=By.xpath("//a[contains(text(),'Create a product type')]");
	private final By productNameTextBox=By.xpath("//input[@id='name']");
	private final By productDescriptionTextBox=By.xpath("//textarea[@id='description']");
	private final By productKeyTextBox=By.xpath("//input[@id='key']");
	private final By saveButton=By.xpath("//button[@data-track-label='save']");
	private final By cancelButton=By.xpath("//button[@data-track-label='cancel']");
	private final By productTypeListLink=By.xpath("//*[contains(text(),'To product type list')]");
	private final By hideButton=By.xpath("//button[@label='Hide notification']");

	/**
	 * click on settings icon on merchant center
	 */
	public void clickOnSettingsIcon()
	{
		WebElement settingsIconField=wait.waitForElementDisplayed(settingsIcon);
		click.moveToElementAndClick(settingsIconField);
	}

	/**
	 * click on product Type link on merchant center
	 */
	public void clickOnProductTypes()
	{
		WebElement productTypeField=wait.waitForElementDisplayed(productTypesLink);
		click.moveToElementAndClick(productTypeField);

	}

	/**
	 * click on add product Type button on merchant center
	 */
	public void clickOnCreateProductTypeLink()
	{
		WebElement addProductTypeButtonField=wait.waitForElementDisplayed(createProductTypeLink);
		click.moveToElementAndClick(addProductTypeButtonField);
	}

	/**
	 * enter product type name on merchant center
	 * @param productName
	 */
	public void enterProductTypeName(final String productName)
	{
		WebElement productTypeNameField=wait.waitForElementDisplayed(productNameTextBox);
		text.enterText(productTypeNameField,productName);
	}

	/**
	 * enter product description on merchant center
	 * @param productDescription
	 */
	public void enterProductDescription(final String productDescription)
	{
		WebElement productDescriptionField=wait.waitForElementDisplayed(productDescriptionTextBox);
		text.enterText(productDescriptionField,productDescription);
	}

	/**
	 * enter product key on product type of merchant center
	 * @param productKey
	 */
	public void enterProductKey(final String productKey)
	{
		WebElement productKeyField=wait.waitForElementDisplayed(productKeyTextBox);
		text.enterText(productKeyField,productKey);
	}

	/**
	 * click on save button of product type
	 */
	public void clickOnSaveButton()
	{
		WebElement saveButtonField=wait.waitForElementEnabled(saveButton);
		click.moveToElementAndClick(saveButtonField);
	}

	/**
	 * click on cancel button of product type.
	 */
	public void clickOnCacelButton()
	{
		WebElement cancelButtonField=wait.waitForElementDisplayed(cancelButton);
		click.moveToElementAndClick(cancelButtonField);
	}

	/**
	 * click on product type List
	 */
	public void clickOnProductTypeList()
	{
		WebElement productTypeListField=wait.waitForElementDisplayed(productTypesLink);
		click.moveToElementAndClick(productTypeListField);
	}

	/**
	 * hide button on CT
	 */
	public void clickOnHideButton()
	{
		WebElement hidButtonField=wait.waitForElementDisplayed(hideButton);
		if(hidButtonField.isDisplayed())
		{
			click.moveToElementAndClick(hidButtonField);
		}

	}


}
