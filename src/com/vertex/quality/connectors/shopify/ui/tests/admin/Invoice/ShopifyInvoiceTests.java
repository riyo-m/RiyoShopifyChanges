package com.vertex.quality.connectors.shopify.ui.tests.admin.Invoice;

import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage.*;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ShopifyInvoiceTests extends ShopifyUIBaseTest
{
	LoginInvPage invoiceLoginPage;
	CreateOrderInvPage invoiceOrderCreateInvPage;
	ExemptProductInvPage invoiceExemptProductInvPage;
	CustomerInvPage invoiceCustomerInvPage;
	CustomerExemptInvPage invoiceCustomerExemptInvPage;
	ItemFulfillmentQPage invoiceFulfillmentPage;
	//	InvoiceTaxPage checkInvoiceTaxPage;
	CollectPaymentInvPage invoiceCollectPaymentInvPage;
	RefundInvPage invoiceRefundInvPage;
	OrderLevelDiscountInvPage invoiceOrderLevelDiscountInvPage;
	LineLevelDiscountInvPage invoiceLineLevelDiscountInvPage;
	GiftCardPage invoiceGiftCard;
	PartialFulfillmentPage invoicePartialFulfillmentPage;
	OrderDeSelectPage invoiceOrderDeSelectPage;
	VerifyTaxPage invoiceVerifyTaxPage;

	double quotationTax;

	double invoiceTax;
	double customerQuotationExempt;

	double productTaxExempt;

	@Test
	public void orderFulfillRefund( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceOrderCreateInvPage = new CreateOrderInvPage(driver);
		invoiceOrderCreateInvPage.createNewOrderPage();

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		quotationTax = invoiceVerifyTaxPage.verifyQuotationTaxRates();
		System.out.println("Quotation Tax" + quotationTax);

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();

		invoiceTax = invoiceVerifyTaxPage.verifyInvoiceTaxRates();
		System.out.println("Invoice Tax" + quotationTax);
		assertEquals(quotationTax, invoiceTax);

		invoiceRefundInvPage = new RefundInvPage(driver);
		invoiceRefundInvPage.refundOrderPage("1");
	}

	@Test
	public void orderLevelDiscount( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceOrderCreateInvPage = new CreateOrderInvPage(driver);
		invoiceOrderCreateInvPage.createNewOrderPage();

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceOrderLevelDiscountInvPage = new OrderLevelDiscountInvPage(driver);
		invoiceOrderLevelDiscountInvPage.orderLevelInvPage();

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		quotationTax = invoiceVerifyTaxPage.verifyQuotationTaxRates();
		System.out.println("Quotation Tax" + quotationTax);

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();

		invoiceTax = invoiceVerifyTaxPage.verifyInvoiceTaxRates();
		System.out.println("Invoice Tax" + quotationTax);
		assertEquals(quotationTax, invoiceTax);
	}

	@Test
	public void lineLevelDiscount( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);

		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceOrderCreateInvPage = new CreateOrderInvPage(driver);
		invoiceOrderCreateInvPage.createNewOrderPage();

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceLineLevelDiscountInvPage = new LineLevelDiscountInvPage(driver);
		invoiceLineLevelDiscountInvPage.applyLineLevelDiscountPage("150");

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		quotationTax = invoiceVerifyTaxPage.verifyQuotationTaxRates();
		System.out.println("Quotation Tax" + quotationTax);

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();

		invoiceTax = invoiceVerifyTaxPage.verifyInvoiceTaxRates();
		System.out.println("Invoice Tax" + quotationTax);
		assertEquals(quotationTax, invoiceTax);
	}

	@Test
	public void customerExemption( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceOrderCreateInvPage = new CreateOrderInvPage(driver);
		invoiceOrderCreateInvPage.createNewOrderPage();

		invoiceCustomerExemptInvPage = new CustomerExemptInvPage(driver);
		invoiceCustomerExemptInvPage.customerExemptPage("rpaul@test.com");

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		customerQuotationExempt = invoiceVerifyTaxPage.verifyCustomerQuotationExempt();
		System.out.println("Customer Exempt No Tax" + customerQuotationExempt);

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();
	}

	@Test
	public void productExemptPage( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceExemptProductInvPage = new ExemptProductInvPage(driver);
		invoiceExemptProductInvPage.addExemptProductPage(" Shopify Exempted Product");

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		productTaxExempt = invoiceVerifyTaxPage.verifyProductTaxExempt();
		System.out.println("Product Tax =  " + productTaxExempt);

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();

//		invoiceTax = invoiceVerifyTaxPage.verifyInvoiceTaxRates();
//		assertEquals(quotationTax, invoiceTax);
	}

	@Test
	public void giftCard( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceGiftCard = new GiftCardPage(driver);
		invoiceGiftCard.giftCard();

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		quotationTax = invoiceVerifyTaxPage.verifyQuotationTaxRates();
		System.out.println(quotationTax);

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();


	}

	@Test
	public void partialFulfillment( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoicePartialFulfillmentPage = new PartialFulfillmentPage(driver);
		invoicePartialFulfillmentPage.createNewOrderPage();

		invoiceOrderLevelDiscountInvPage = new OrderLevelDiscountInvPage(driver);
		invoiceOrderLevelDiscountInvPage.orderLevelInvPage();

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceOrderDeSelectPage = new OrderDeSelectPage(driver);
		invoiceOrderDeSelectPage.fulfillPartialOrder();
	}

	@Test
	public void ProductClassExemption( ) throws InterruptedException
	{
		loadShopifyAdmin(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_PANEL.text);
		invoiceLoginPage = new LoginInvPage(driver);
		invoiceLoginPage.loginTheApplication(ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text,
			ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text);

		invoiceExemptProductInvPage = new ExemptProductInvPage(driver);
		invoiceExemptProductInvPage.addExemptProductPage(" Shopify Exempted Product");

		invoiceCustomerInvPage = new CustomerInvPage(driver);
		invoiceCustomerInvPage.selectCustomerPage("rptest@sp.com");

		invoiceVerifyTaxPage = new VerifyTaxPage(driver);
		quotationTax = invoiceVerifyTaxPage.verifyQuotationTaxRates();
		System.out.println(quotationTax);

		invoiceCollectPaymentInvPage = new CollectPaymentInvPage(driver);
		invoiceCollectPaymentInvPage.collectPaymentPage();

		invoiceFulfillmentPage = new ItemFulfillmentQPage(driver);
		invoiceFulfillmentPage.fulfillOrder();

		invoiceTax = invoiceVerifyTaxPage.verifyInvoiceTaxRates();
		assertEquals(quotationTax, invoiceTax);
	}
}