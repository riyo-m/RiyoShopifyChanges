package com.vertex.quality.connectors.shopify.ui.tests.admin.onboarding;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyAdminOrdersPage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyNewLoginPage;
import org.testng.annotations.Test;

public class ShopifyNewLogin extends ShopifyUIBaseTest
{
	@Test
	public void shopifyLogin( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		ShopifyNewLoginPage newLoginPage = new ShopifyNewLoginPage(driver);
     	newLoginPage.loginNavigation();
		newLoginPage.enterEmailField(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text);
		newLoginPage.clickContinueWithEmailBtn();
		newLoginPage.enterPassField(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);
		newLoginPage.clickLoginbtn();

		ShopifyAdminOrdersPage shopfiyOrders = new ShopifyAdminOrdersPage(driver);
		shopfiyOrders.removeAllAppliedFiltersIfAny();


		newLoginPage.connectPartnerAcc();
		newLoginPage.searchField("Riyo");

		newLoginPage.selectVtxQa();
		newLoginPage.clickSetting();
		newLoginPage.clickTaxAndDuties();
		Thread.sleep(6000);
	}
}
