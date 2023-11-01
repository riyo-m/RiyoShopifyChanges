package com.vertex.quality.connectors.shopify.ui.tests.admin.onboarding;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyLoginNewPage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage.LoginInvPage;
import org.testng.annotations.Test;

import java.awt.*;

public class ShopifyLoginNew extends ShopifyUIBaseTest

{
	@Test
	public void shopifyLogin( ) throws InterruptedException, AWTException
	{

		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		ShopifyLoginNewPage loginNewPage = new ShopifyLoginNewPage(driver);
		LoginInvPage lg = new LoginInvPage(driver);
		//		loginNewPage.loginNewNavigation();
		//		Thread.sleep(3000);
		loginNewPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

//		loginNewPage.clickProductPage();
//		loginNewPage.clickProductSnowboard();
//		//		//	loginNewPage.searchNewStore("VTXQA");
		//	loginNewPage.selectNewStore();
		//		loginNewPage.settingsPage();

		//				loginNewPage.createNewOrderPage();
//		loginNewPage.enterNewOrder();
//		loginNewPage.createOrder();
		//		loginNewPage.browseNewProduct();
		//		loginNewPage.searchNewProduct();
		//		loginNewPage.selectProduct();
		//		loginNewPage.addProduct();

		//		loginNewPage.addExemptProductPage(" Shopify Exempted Product");
//		loginNewPage.clickSearchGiftCard(" Shopify Gift card");
//		loginNewPage.selectGiftCard();
		//		loginNewPage.clickSelectShopifyExemptProduct();
//		loginNewPage.addProduct();

//		loginNewPage.selectCustomerPage("rptest@sp.com");
		//		loginNewPage.searchCustomerField("rptest@sp.com");
		//		loginNewPage.selectCustomer();

		//		loginNewPage.customerExemptPage("rpaul@test.com");
		//		//		loginNewPage.searchCustomerField("rpaul@test.com");
		//		//		loginNewPage.exemptCustomer();
		//
		//		loginNewPage.quotationPage();
		//		//		loginNewPage.clickShowTaxRates();
		//		//		loginNewPage.clickClose();
		//
		//		loginNewPage.orderLevelDiscountPage("100");
		//		//		loginNewPage.clickOrderLevelDiscount();
		//		//		loginNewPage.clickEnterDiscountAmount("100");
		//		//		loginNewPage.clickApplyDiscount();
		//
		//		loginNewPage.applyLineLevelDiscountPage("300");
		//		//		loginNewPage.clickLineLevelDiscount();
		//		//		loginNewPage.enterLineLevelAmount("300");
		//		//		loginNewPage.clickApplyLineLevelDiscount();
		//
		//		loginNewPage.collectPaymentPage();
		//		//		loginNewPage.clickCollectPayment();
		//		//		loginNewPage.clickMarkPaid();
		//		//		loginNewPage.clickCreatePaidOrder();
		//
		//		loginNewPage.fulfillOrder();
		//		//		loginNewPage.enterFulfillItem();
		//		//		loginNewPage.makeFulfillment();
		//
		//		loginNewPage.refundOrderPage("1");
		//		//		loginNewPage.clickOrderRefund();
		//		//		loginNewPage.clickEnterValue("1");
		//		//		loginNewPage.clickRefundFullAmount();

//		lg.loginButton();
	}
}
