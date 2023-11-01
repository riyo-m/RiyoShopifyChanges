package com.vertex.quality.connectors.commercetools.ui.pages;


import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * generic representation of page class for products.
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsProductPage extends CommerceToolsBasePage
{
	public CommerceToolsProductPage( final WebDriver driver )
	{
		super(driver);
	}

	private final By productsIcon=By.xpath("//*[contains(text(),'Products')]");
	private final By addProductLink=By.xpath("//*[contains(text(),'Add product')]");
	private final By selectProductTypeCheckBox=By.xpath("//p[text()='Pants']");
	private final By clickNextButton=By.xpath("//button[@data-track-label='Next']");
	private final By productNameTextBox=By.xpath("//input[@name='name.de-DE']");
	private final By productDescriptionTextBox=By.xpath("//textarea[@name='description.de-DE']");
	private final By addVariantButton=By.xpath("//button[@aria-label='Add variant']");
	private final By SKUTextBox=By.xpath("//input[@name='variant-sku']");
	private final By variantKeyTextBox=By.xpath("//input[@name='variant-key']");
	private final By saveButton=By.xpath("//button[@data-track-label='save']");
	private final By nextButton=By.xpath("//button[@data-track-label='Next']");
	private final By backButton=By.xpath("//button[@data-track-label='Back']");
	private final By saveVariantButton=By.xpath("//button[@data-track-label='Save']");
	private final By variantTab=By.xpath("//a[text()='Variants']");
	private final By pricingTab=By.xpath("//div[@data-testid='cell-0-prices']");
	private final By addPriceButton=By.xpath("//button[@aria-label='Add price']");
	private final By variantProductPrice=By.xpath("//input[@name='value.amount']");
	private final By savePriceButton=By.xpath("//button[@aria-label='Save']");
	private final By cancelPriceButton=By.xpath("//button[@aria-label='Cancel']");
	private final By variantListLink=By.xpath("//span[contains(text(),'To Variant list')]");
	private final By productStatus=By.xpath("//button[@aria-label='Open dropdown']");
	private final By publishProduct=By.xpath("//li[@data-track-component='Publish']");
	private final By hideNotificationButton=By.xpath("//button[@label='Hide notification']");




	/**
	 * method to click on products tab on merchant center.
	 */
	public void clickOnProductsTab()
	{
		WebElement productsIconField=wait.waitForElementDisplayed(productsIcon);
		click.moveToElementAndClick(productsIconField);
	}

	/**
	 * method to click on add products on merchant center
	 */
	public void clickOnAddProduct()
	{
		WebElement addProductsLinkField=wait.waitForElementDisplayed(addProductLink);
		click.moveToElementAndClick(addProductsLinkField);
	}

	/**
	 * method to select product type on merchant center
	 * @param productType
	 */
	public void selectProductType(String productType)
	{
		WebElement productTypeField=wait.waitForElementDisplayed(selectProductTypeCheckBox);
		if(productTypeField.getText().equalsIgnoreCase(productType))
		{
			click.moveToElementAndClick(productTypeField);
		}
		else
		{
			VertexLogger.log("Product Type is not available");
		}

	}

	/**
	 * click on next button.
	 */
	public void clickOnNextButton()
	{
		WebElement nextButtonField=wait.waitForElementEnabled(clickNextButton);
		click.moveToElementAndClick(nextButtonField);
	}

	/**
	 * enter the product name.
	 * @param productName
	 */
	public void enterProductName(final String productName)
	{
		WebElement productNameField=wait.waitForElementDisplayed(productNameTextBox);
		text.enterText(productNameField,productName);
	}

	/**
	 * enter product description
	 * @param productDescription
	 */
	public void enterProductDescription(final String productDescription)
	{
		WebElement productDescriptionField=wait.waitForElementDisplayed(productDescriptionTextBox);
		text.enterText(productDescriptionField,productDescription);
	}

	/**
	 * click on add variant.
	 */
	public void clickOnAddVariant()
	{
		WebElement addVariantField=wait.waitForElementDisplayed(addVariantButton);
		click.moveToElementAndClick(addVariantField);
	}

	/**
	 * enter SKU for the selected variant.
	 * @param SKUName
	 */
	public void enterSKU(final String SKUName)
	{
		WebElement SKUField=wait.waitForElementDisplayed(SKUTextBox);
		text.enterText(SKUField,SKUName);
	}

	/**
	 *
	 * @param variantKey
	 */
	public void enterVariantKey(final String variantKey)
	{
		WebElement variantKeyField=wait.waitForElementDisplayed(variantKeyTextBox);
		text.enterText(variantKeyField,variantKey);
	}

	/**
	 * click on save variant button.
	 */
	public void clickOnSaveVariantButton()
	{
		WebElement saveVariantButtonField=wait.waitForElementDisplayed(saveButton);
		click.moveToElementAndClick(saveVariantButtonField);
	}

	/**
	 * click on next button on variant.
	 */
	public void clickOnNextButtonOnVariant()
	{
		WebElement nextButtonVariant=wait.waitForElementDisplayed(nextButton);
		click.moveToElementAndClick(nextButtonVariant);
	}

	/**
	 * click on save product variant
	 */
	public void clickOnSaveProductVariantButton()
	{
		WebElement saveProductVariantButtonField=wait.waitForElementDisplayed(saveVariantButton);
		click.moveToElementAndClick(saveProductVariantButtonField);
	}

	/**
	 * click on variant tab
	 */
	public void clickOnVariantTab()
	{
		WebElement variantTabField=wait.waitForElementDisplayed(variantTab);
		click.moveToElementAndClick(variantTabField);
	}

	/**
	 * click on price tab for variant
	 */
	public void clickOnPriceToAdd()
	{
		WebElement variantPriceField=wait.waitForElementDisplayed(pricingTab);
		click.moveToElementAndClick(variantPriceField);
	}

	/**
	 * click on add price to variant
	 */
	public void clickOnAddPriceToVariant()
	{
		WebElement addPriceButtonField=wait.waitForElementDisplayed(addPriceButton);
		click.moveToElementAndClick(addPriceButtonField);
	}

	/**
	 * enter variant price
	 * @param priceToVariant
	 */
	public void enterVariantPrice(final String priceToVariant)
	{
		WebElement variantPriceField=wait.waitForElementDisplayed(variantProductPrice);
		text.enterText(variantPriceField,priceToVariant);
	}

	/**
	 * click on save variant price
	 */
	public void clickOnSaveVariantPrice()
	{
		WebElement saveVariantPriceField=wait.waitForElementDisplayed(savePriceButton);
		click.moveToElementAndClick(saveVariantPriceField);
	}

	/**
	 * click on pop up on merchant center
	 */
	public void clickOKPopUp()
	{

		if(alert.isAlertPresent())
		{
			alert.acceptAlert(10);
		}

	}
	/**
	 * click on variant list
	 */
	public void clickOnVariantList() {
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.elementToBeClickable(variantListLink)).click();
	}

	/**
	 * click on publish variant
	 */
	public void clickToPublishVariant()
	{
		WebElement productStatusField=wait.waitForElementDisplayed(productStatus);
		click.moveToElementAndClick(productStatusField);
		WebElement publishProductField=wait.waitForElementDisplayed(publishProduct);
		click.moveToElementAndClick(publishProductField);

	}

	/**
	 * click on hide notification
	 */
	public void clickOnHideNotificationButton()
	{

		WebElement hideButtonField=wait.waitForElementDisplayed(hideNotificationButton);
		if(hideButtonField.isDisplayed())
		{
			click.moveToElementAndClick(hideButtonField);

		}

	}


}
