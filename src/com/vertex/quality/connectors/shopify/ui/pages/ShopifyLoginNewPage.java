package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.utils.selenium.VertexClickUtilities;
import com.vertex.quality.common.utils.selenium.VertexTextUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;

public class ShopifyLoginNewPage extends ShopifyPage

{
	public ShopifyLoginNewPage( final WebDriver driver ) { super(driver); }

	protected By loginNav = By.xpath("//li[@class='leading-[0]']/preceding::a[text()='Log in']");

	protected By emailText = By.id("account_email");
	protected By continueWithEmail = By.name("commit");
	protected By passwordText = By.id("account_password");
	protected By loginBtn = By.name("commit");
	//	---------------------------------
	protected By searchStore = By.id(":r2:");
	protected By selectStore = By.xpath("//h6/parent::div");
	//---------------------------------------
	protected By enterSettings = By.xpath("//span[text()='Settings']");
	protected By taxAndDuties = By.xpath("//span[text()='Taxes and duties']");
	protected By checkProdActive = By.xpath("//span[text()='Active']");
	protected By backToStore = By.className("XptyV");
	//****************************************
	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");
	protected By browseProduct = By.xpath("//span[text()='Browse']");
	protected By searchExemptProduct = By.name("productPicker");
	protected By searchGiftCard = By.name("productPicker");
	protected By selectShopifyExemptProduct = By.xpath(
		"//div[@class='txPIe']//span[text()='Shopify Exempted Product']");
	protected By giftCard = By.xpath("//span[text()='Shopify Gift card']/parent::div");
	protected By searchProduct = By.xpath("//div[text()='Popular products']");

	protected By selectFirstProduct = By.xpath("//div[@class='txPIe']//span[text()='The 3p Fulfilled Snowboard']");
	protected By addSelectedProduct = By.xpath("//span[text()='Add']");
	//======================================================
	protected By searchNewCustomer = By.xpath(
		"//h2[text()='Customer']/parent::div/following-sibling::div/descendant::div[@class='Polaris-TextField_1spwi']/descendant::input");
	protected By selectNewCustomer = By.xpath("//span[text()='rptest@sp.com']/parent::span/parent::div");
	protected By selectExemptCustomer = By.xpath("//span[text()='rptest@sp.com']/parent::span/parent::div");
	//=======================================================
	protected By collectPayment = By.xpath("//span[text()='Collect payment']");
	protected By markAsPaid = By.xpath("//span[text()='Mark as paid']");
	protected By createPaidOrder = By.xpath("//span[text()='Create order']");
	//	====================================================
	protected By showTaxRates = By.xpath("//span[text()='Show tax rates']");
	protected By closeTaxRate = By.xpath("//span[text()='Close']");
	//	===================================================
	protected By fulfillItem = By.xpath("//span[text()='Fulfill item']");
	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");
	//=======================================================
	protected By orderRefund = By.id("refund-restock");
	protected By enterValue = By.xpath(
		"//div[@class='Polaris-TextField_1spwi Polaris-TextField--hasValue_1mx8d']/child::input[@class='Polaris-TextField__Input_30ock Polaris-TextField__Input--suffixed_1tsyu']");
	protected By refundFullAmount = By.xpath(
		"//span[text()='Send a '] /parent::span/parent::label/parent::div/preceding-sibling::div");
	//=========================================================
	protected By orderLevelDiscount = By.xpath(
		"//div[@class='TPyps']/following-sibling::div/child::span/child::span/child::button/descendant::span[@class='Polaris-Button__Text_yj3uv']");
	protected By enterDiscountAmount = By.xpath("//input[@id='discountFixedAmount']");
	protected By applyDiscount = By.xpath("//button[@class='Polaris-Button_r99lw']/following-sibling::button");
	//========================================================
	protected By lineLevelDiscount = By.xpath("//span[text()='Add discount']");
	protected By lineLevelAmount = By.id("discountFixedAmount");
	protected By applyLineLevelDiscount = By.xpath("//span[text()='Apply']");
	//===========================================================
	protected By productMfPage = By.xpath("//span[text()='Products']");
	protected By productSnowboard = By.xpath("//span[text()='The 3p Fulfilled Snowboard']");
	protected By ageGroup = By.xpath(
		"//button[@id='metafields.ProductVariant.ageGroup']/descendant::span/descendant::div[@class='llRQz']");
	protected By productCategoryId = By.xpath(
		"//button[@id='metafields.Product.productCategoryId']/child::div/child::span/descendant::div[@class='llRQz']");
	protected By zulilyStyleId = By.xpath(
		"//button[@id='metafields.Product.zulilyStyleId']/descendant::span/descendant::div[@class='llRQz']");
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
	//	==================================================================
	protected By taxExclude = By.xpath(
		"//span[text()='Charge tax on this product']/parent::span/preceding-sibling::span/child::span");
	protected By saveTaxExclude = By.xpath("(//span[text()='Save'])[1]");
	protected By taxInclude = By.xpath(
		"//p[text()='Include tax in prices']/parent::div/parent::span/parent::span/preceding-sibling::span/child::span");

public void clickTaxInclude(){
	waitForPageLoad();
	click.clickElement(taxInclude);

}
	public void clickTaxExclude(){
		waitForPageLoad();
		click.clickElement(taxExclude);

	}
	public void clickSaveTaxExclude(){
		waitForPageLoad();
		click.clickElement(saveTaxExclude);
	}
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

	public void enterProductCategoryId( String productCateId )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(productCategoryId), productCateId);
	}

	public void enterZulilyStyleId( String zulilyId )
	{
		waitForPageLoad();
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

	public void metaFieldPage( ) throws InterruptedException
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

	//	==========================================================
	public void loginNewNavigation( )
	{
		waitForPageLoad();
		click.clickElement(loginNav);
	}

	public void enterEmail( String email )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(emailText), email);
	}

	public void enterContinue( )
	{
		waitForPageLoad();
		click.clickElement(continueWithEmail);
	}

	public void enterPass( String pass )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(passwordText), pass);
	}

	public void loginButton( )
	{
		waitForPageLoad();
		click.clickElement(loginBtn);
	}

	public void loginTheApplication( String email, String pass )
	{
		enterEmail(email);
		enterContinue();
		enterPass(pass);
		loginButton();
	}

	// *******************************************************************
	public void searchNewStore( String store )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchStore), store);
	}

	public void selectNewStore( )
	{
		waitForPageLoad();
		click.clickElement(selectStore);
	}

	// ******************************************************************
	public void enterSettingsPage( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(enterSettings);
	}

	public void enterTaxAndDuties( )
	{
		waitForPageLoad();
		click.clickElement(taxAndDuties);
	}

	public void checkProductActive( )
	{
		waitForPageLoad();
		text.verifyText(By.xpath("//span[text()='Active']"), "Active", "Active");
	}

	public void enterBackToStore( )
	{
		waitForPageLoad();
		click.clickElement(backToStore);
	}

	public void settingsPage( ) throws InterruptedException
	{
		enterSettingsPage();
		enterTaxAndDuties();
		checkProductActive();
		enterBackToStore();
	}

	// *********************************************************************
	public void enterNewOrder( )
	{
		waitForPageLoad();
		click.clickElement(enterOrder);
	}

	public void createOrder( )
	{
		waitForPageLoad();
		click.clickElement(createNewOrder);
	}

	public void browseNewProduct( )
	{
		waitForPageLoad();
		click.clickElement(browseProduct);
	}

	public void searchNewProduct( )
	{
		waitForPageLoad();
		click.clickElement(searchProduct);
	}

	public void selectProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectFirstProduct);
	}

	public void createNewOrderPage( )
	{
		enterNewOrder();
		createOrder();
		browseNewProduct();
		searchNewProduct();
		selectProduct();
		addProduct();
	}

	// **************************************************************
	public void clickSearchExemptProduct( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchExemptProduct), product);
	}

	public void clickSearchGiftCard( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchGiftCard), product);
	}

	public void clickSelectShopifyExemptProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectShopifyExemptProduct);
	}

	public void addProduct( )
	{
		waitForPageLoad();
		click.clickElement(addSelectedProduct);
	}

	public void addExemptProductPage( String product )
	{
		enterNewOrder();
		createOrder();
		clickSearchExemptProduct(product);
		clickSelectShopifyExemptProduct();
		addProduct();
	}

	public void selectGiftCard( )
	{
		waitForPageLoad();
		click.clickElement(giftCard);
	}

	// ***************************************************************
	public void searchCustomerField( String customer ) throws InterruptedException
	{
		waitForPageLoad();
		//	click.clickElement(searchNewCustomer);
		sleep(5000);
		text.enterText(wait.waitForElementDisplayed(searchNewCustomer), customer);
	}

	public void selectCustomer( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(selectNewCustomer);
	}

	public void selectCustomerPage( String customer ) throws InterruptedException
	{
		searchCustomerField(customer);
		selectCustomer();
	}

	// *****************************************************************
	public void exemptCustomer( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(selectExemptCustomer);
	}

	public void customerExemptPage( String xmptCustomer ) throws InterruptedException
	{
		searchCustomerField(xmptCustomer);
		exemptCustomer();

		// ******************************************************************
	}

	public void clickCollectPayment( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(collectPayment);
	}

	public void clickMarkPaid( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(markAsPaid);
	}

	public void clickCreatePaidOrder( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(5000);
		click.clickElement(createPaidOrder);
	}

	public void collectPaymentPage( ) throws InterruptedException
	{
		clickCollectPayment();
		clickMarkPaid();
		clickCreatePaidOrder();
	}

	// ***************************************************************
	public void clickShowTaxRates( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(showTaxRates);
	}

	public void clickClose( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(closeTaxRate);
	}

	public void quotationPage( ) throws InterruptedException
	{
		clickShowTaxRates();
		clickClose();
	}

	// *************************************************************
	public void enterFulfillItem( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(fulfillItem);
	}

	public void makeFulfillment( )
	{
		waitForPageLoad();
		click.clickElement(clickFulfillItem);
	}

	public void fulfillOrder( ) throws InterruptedException
	{
		enterFulfillItem();
		makeFulfillment();
	}

	// ***************************************************************
	public void clickOrderRefund( )
	{
		waitForPageLoad();
		click.clickElement(orderRefund);
	}

	public void clickEnterValue( String textField ) throws InterruptedException
	{
		waitForPageLoad();

		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(enterValue), textField);
	}

	public void clickRefundFullAmount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(refundFullAmount);
	}

	public void refundOrderPage( String textField ) throws InterruptedException
	{
		clickOrderRefund();
		clickEnterValue(textField);
		clickRefundFullAmount();
	}
	//	****************************************************************

	public void clickOrderLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(orderLevelDiscount);
	}

	public void clickEnterDiscountAmount( String discount ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(enterDiscountAmount), discount);
	}

	public void clickApplyDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(applyDiscount);
	}

	public void orderLevelDiscountPage( String discount ) throws InterruptedException
	{
		clickOrderLevelDiscount();
		clickEnterDiscountAmount(discount);
		clickApplyDiscount();
	}

	// ***********************************************************************
	public void clickLineLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(4000);
		click.clickElement(lineLevelDiscount);
	}

	public void enterLineLevelAmount( String discount ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(lineLevelAmount), discount);
	}

	public void clickApplyLineLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(applyLineLevelDiscount);
		Thread.sleep(4000);
	}

	public void applyLineLevelDiscountPage( String discount ) throws InterruptedException
	{
		clickLineLevelDiscount();
		enterLineLevelAmount(discount);
		clickApplyLineLevelDiscount();
	}
}