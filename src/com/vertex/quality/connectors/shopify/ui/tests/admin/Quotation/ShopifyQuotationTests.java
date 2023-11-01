package com.vertex.quality.connectors.shopify.ui.tests.admin.Quotation;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage.*;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage.VerifyTaxPage;
import org.testng.annotations.Test;

public class ShopifyQuotationTests extends ShopifyUIBaseTest
{
	LoginPage quotationLoginPage;
	SettingsPage quotationSettingsPage;
	CreateOrderPage quotationOrderPage;
	ExemptProductPage quotationExemptProductPage;
	CustomerPage quotationCustomerPage;
	CustomerExemptPage quotationCustomerExemptPage;
	QuotationTaxPage checkQuotationTaxPage;
	CollectPaymentPage quotationCollectPaymentPage;
	RefundPage quotationRefundPage;
	OrderLevelDiscountPage quotationOrderLevelDiscountPage;
	LineLevelDiscountPage quotationLineLevelDiscountPage;
	GiftCardPage quotationGiftCardPage;
	MetafieldPage quotationMetafieldPage;
	TaxExcludePage quotationTaxExcludePage;
	TaxIncludePage quotationTaxIncludePage;
	ColoradoPage quotationColoradoPage;
	ItemFulfillmentQPage itemFulFillPage;
	VerifyTaxPage verifyInvoiceTaxPage;

	public void shopifyAdminLoginTest( ) throws InterruptedException
	{

		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);
	}

//	@Test
//	public void orderFulfillRefund( ) throws InterruptedException
//	{
//		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
//		quotationLoginPage = new LoginPage(driver);
//		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
//			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);
//
//		quotationSettingsPage = new SettingsPage(driver);
//		quotationSettingsPage.settingsTaxPage();
//
//		quotationOrderPage = new CreateOrderPage(driver);
//		quotationOrderPage.createNewOrderPage();
//
//		quotationCustomerPage = new CustomerPage(driver);
//		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
//
//		verifyInvoiceTaxPage = new VerifyTaxPage(driver);
//		verifyInvoiceTaxPage.quotationPage();
//
//		quotationCollectPaymentPage = new CollectPaymentPage(driver);
//		quotationCollectPaymentPage.collectPaymentPage();
//
//		verifyInvoiceTaxPage = new VerifyTaxPage(driver);
//		verifyInvoiceTaxPage.invoiceTax();
//
//		quotationRefundPage = new RefundPage(driver);
//		quotationRefundPage.refundOrderPage("1");
//	}

	@Test
	public void orderLevelDiscount( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationSettingsPage = new SettingsPage(driver);
		quotationSettingsPage.settingsTaxPage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");

		quotationOrderLevelDiscountPage = new OrderLevelDiscountPage(driver);
		quotationOrderLevelDiscountPage.orderLevelDiscountPage("100");

		checkQuotationTaxPage = new QuotationTaxPage(driver);
		checkQuotationTaxPage.quotationPage();
	}

	@Test
	public void lineLevelDiscount( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationSettingsPage = new SettingsPage(driver);
		quotationSettingsPage.settingsTaxPage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");

		quotationLineLevelDiscountPage = new LineLevelDiscountPage(driver);
		quotationLineLevelDiscountPage.applyLineLevelDiscountPage("150");

		checkQuotationTaxPage = new QuotationTaxPage(driver);
		checkQuotationTaxPage.quotationPage();
	}

	@Test
	public void customerExemption( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationSettingsPage = new SettingsPage(driver);
		quotationSettingsPage.settingsTaxPage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerExemptPage = new CustomerExemptPage(driver);
		quotationCustomerExemptPage.customerExemptPage("rpaul@test.com");
	}

	@Test
	public void productExemptPage( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationSettingsPage = new SettingsPage(driver);
		quotationSettingsPage.settingsTaxPage();

		quotationExemptProductPage = new ExemptProductPage(driver);
		quotationExemptProductPage.exemptProductPage(" Shopify Exempted Product");

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
	}

	@Test
	public void giftCard( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationSettingsPage = new SettingsPage(driver);
		quotationSettingsPage.settingsTaxPage();

		quotationGiftCardPage = new GiftCardPage(driver);
		quotationGiftCardPage.giftCard();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
	}

//	@Test
//	public void metaField( ) throws InterruptedException
//	{
//		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
//		quotationLoginPage = new LoginPage(driver);
//		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
//			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);
//
//		quotationMetafieldPage = new MetafieldPage(driver);
//		quotationMetafieldPage.metaFieldTest();
//
//		quotationOrderPage = new CreateOrderPage(driver);
//		quotationOrderPage.createNewOrderPage();
//
//		quotationCustomerPage = new CustomerPage(driver);
//		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
//
//		checkQuotationTaxPage = new QuotationTaxPage(driver);
//		checkQuotationTaxPage.quotationPage();
//	}

	@Test
	public void taxExclude( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationTaxExcludePage = new TaxExcludePage(driver);
		quotationTaxExcludePage.markTaxExclude();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");

		checkQuotationTaxPage = new QuotationTaxPage(driver);
		checkQuotationTaxPage.quotationPage();
	}

	@Test
	public void taxInclude( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationTaxIncludePage = new TaxIncludePage(driver);
		quotationTaxIncludePage.enterTaxIncludePage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
	}

	@Test
	public void colorado( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);
		Thread.sleep(2000);
		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationColoradoPage = new ColoradoPage(driver);
		quotationColoradoPage.selectColoradoCustomer("riyoco@sp.com");

		checkQuotationTaxPage = new QuotationTaxPage(driver);
		checkQuotationTaxPage.quotationPage();

		quotationCollectPaymentPage = new CollectPaymentPage(driver);
		quotationCollectPaymentPage.collectPaymentPage();

		itemFulFillPage = new ItemFulfillmentQPage(driver);
		itemFulFillPage.fulfillOrder();

		checkQuotationTaxPage = new QuotationTaxPage(driver);
		checkQuotationTaxPage.invoiceTax();
	}

	@Test
	public void taxIncludeWithDiscount( ) throws InterruptedException
	{

		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationTaxIncludePage = new TaxIncludePage(driver);
		quotationTaxIncludePage.enterTaxIncludePage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");

		quotationOrderLevelDiscountPage = new OrderLevelDiscountPage(driver);
		quotationOrderLevelDiscountPage.orderLevelDiscountPage("100");
	}

	@Test
	public void shippingDiscount( ) throws InterruptedException
	{

		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		quotationLoginPage = new LoginPage(driver);
		quotationLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		quotationTaxIncludePage = new TaxIncludePage(driver);
		quotationTaxIncludePage.enterTaxIncludePage();

		quotationOrderPage = new CreateOrderPage(driver);
		quotationOrderPage.createNewOrderPage();

		quotationCustomerPage = new CustomerPage(driver);
		quotationCustomerPage.selectCustomerPage("rptest@sp.com");
	}
}