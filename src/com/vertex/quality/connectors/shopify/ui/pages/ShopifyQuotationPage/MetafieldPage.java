package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MetafieldPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public MetafieldPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By productMfPage = By.xpath("//span[text()='Products']");
	protected By productSnowboard = By.xpath("//span[text()='The 3p Fulfilled Snowboard']");
	protected By ageGroup = By.xpath(
		"//button[@id='metafields.ProductVariant.ageGroup']/descendant::span/descendant::div[@class='llRQz']");
	protected By productCategoryId = By.xpath(
		"//button[@id='metafields.Product.productCategoryId']/child::div/child::span/descendant::div[@class='llRQz xXWHT']");
	protected By zulilyStyleId = By.xpath(
		"(//button[@id='metafields.Product.zulilyStyleId']/descendant::span/descendant::div)[2]");
	protected By productId = By.xpath(
		"//button[@id='metafields.ProductVariant.productId']/descendant::span/descendant::div[@class='llRQz']");
	protected By fulfillmentMethod = By.xpath(
		"//p[text()='ProductVariant.fulfillmentMethod']/parent::label/following-sibling::span/descendant::div[@class='llRQz']");
	protected By vendorId = By.xpath(
		"//p[text()='ProductVariant.vendorId']/parent::label/following-sibling::span/descendant::div[@class='llRQz']");
	protected By productMfClass = By.xpath(
		"//p[text()='product_class']/parent::label/following-sibling::span/descendant::div[@class='llRQz']");
	protected By variantFulfillmentMethod = By.xpath(
		"//p[text()='fulfillmentMethod-Variant']/parent::label/following-sibling::span/descendant::div[@class='llRQz']");
	protected By variantVendorId = By.xpath(
		"//p[text()='vendorId']/parent::label/following-sibling::span/descendant::div[@class='llRQz']");
	protected By saveMetafield = By.xpath(
		"//span[text()='Archive product']/ancestor::div[@class='Polaris-LegacyStack__Item_yiyol']/following-sibling::div//button");

	public void clickProductPage( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(productMfPage);
	}

	public void clickProductSnowboard( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		scroll.scrollElementIntoView(productSnowboard);
		click.clickElement(productSnowboard);
		Thread.sleep(5000);
	}

	public void enterAgeGroup( String age )
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(ageGroup);
		text.enterText(wait.waitForElementDisplayed(ageGroup), age);
	}

	public void enterProductCategoryId( String productCateId ) throws InterruptedException
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(productCategoryId);
		click.clickElement(productCategoryId);
		Thread.sleep(2000);
		text.enterText(wait.waitForElementDisplayed(productCategoryId), productCateId);
	}

	public void enterZulilyStyleId( String zulilyId ) throws InterruptedException
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(zulilyStyleId);
		click.clickElement(zulilyStyleId);
		Thread.sleep(2000);
		text.enterText(wait.waitForElementDisplayed(zulilyStyleId), zulilyId);
	}

	public void enterProductId( String prodId )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(productId), prodId);
	}

	public void enterFulfillmentMethod( String fulfillMethod )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(fulfillmentMethod), fulfillMethod);
	}

	public void enterVendorId( String vendorid )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(vendorId), vendorid);
	}

	public void enterProductMfClass( String productMf )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(productMfClass), productMf);
	}

	public void enterVariantFulfillmentMethod( String variantFulfillMethod )
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(variantFulfillmentMethod);
		text.enterText(wait.waitForElementDisplayed(variantFulfillmentMethod), variantFulfillMethod);
	}

	public void enterVariantVendorId( String variantVendor )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(variantVendorId), variantVendor);
	}

	public void clickSaveMetafield( )
	{
		waitForPageLoad();
		click.clickElement(saveMetafield);
	}

	public void metaFieldTest( ) throws InterruptedException
	{
		clickProductPage();
		clickProductSnowboard();
		//		enterAgeGroup();
		enterProductCategoryId("C1234");
		enterZulilyStyleId("Normal");
		//		enterProductId();
		//		enterFulfillmentMethod();
		//		enterVendorId();
		enterProductMfClass("Test");
		enterVariantFulfillmentMethod("FV13");
		enterVariantVendorId("V123");
		clickSaveMetafield();
	}
}
