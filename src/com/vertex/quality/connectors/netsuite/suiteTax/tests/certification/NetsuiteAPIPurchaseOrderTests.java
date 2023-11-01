package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteVendor;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIBillOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIPurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * Tests Purchase Order - Accrual process scenarios for SuiteTax
 *
 * @author ravunuri
 */
public class NetsuiteAPIPurchaseOrderTests extends NetsuiteBaseAPITest {

	/**
	 * Create automation script to take PO into a Vendor Bill - Test THRESHOLD situation and validate the xml
	 * On the vendor bill Vertex CUT Action field should be updated as Within Threshold
	 * On the payment, Vertex Tax details should show the accrual request
	 * Journal entry should be created for the tax from the accrual request.
	 * @author ravunuri
	 * CNSAPI-xxxx
	 */
	@Test(groups = {"netsuite_suite_cert","suite_tax_regression"})
	protected void createPurchaseOrderThreasholdTest( )
	{   //Define Vendors and Items
		NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
		String expectedTaxResult = "Success";
		String requestType = "AccrualRequest";
		String expectedBillItem = "Tax Adjustment";
		String grossAmt = "-2.00";
		String overpayCutAction = "Pay & Accrue"; //"Within Threshold"
		String journalLink = "Journal #JOU";
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1").amount("100.00").build();

		//Create Purchase Order and Validate its XML Response
		NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = changeCutThresholdAmount();
		//Navigate to Enter Purchase Order menu
		NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
		setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
		NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();
		//Verify the “Vertex Tax Error code” field is showing 'Success' status.
		String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
		assertEquals(actualMessage, expectedTaxResult);

		//Create vendor bill, enter the Vendor tax $2 less than the Vertex tax
		NetsuiteAPIBillOrderPage billOrderPage = savedPurchaseOrderPage.billOrder();
		billOrderPage.enterVendorTax("7.63");
		billOrderPage.enterLocation("01: San Francisco");
		//Select the VERTEX UNDERCHARGE ACTION as Pay & Accrue, enter Location, Save Bill and Make Payment
		billOrderPage.selectUnderChargeAction("Pay & Accrue");
		billOrderPage.saveBill();
		billOrderPage.submitPayment();
		billOrderPage.saveOrder();

		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = billOrderPage.element.isElementDisplayed(unrolled_view_locator);
		if (isUnrolledView_Button_Visible){
			billOrderPage.click.clickElement(unrolled_view_locator);
		}
		//Validate the TaxResult=Success and RequestType=AccrualRequest in Vertex Call details
		String actualCallDetails =  billOrderPage.getCallDetailstext();
		assertTrue(actualCallDetails.contains(expectedTaxResult));
		assertTrue(actualCallDetails.contains(requestType));
		//Validate VERTEX ASSOCIATED TRANSACTION field have Journal link
		String actualJournalLink =  billOrderPage.getVertexAssociatedTranstext();
		assertTrue(actualJournalLink.contains(journalLink));
		//Click on Bill in Apply section to access the Bill record
		billOrderPage.accessBill();

		//Validate on the Vendor bill, Tax Adjustment item should be there for the difference amount(-2)
		String actualVendorBillItems =  billOrderPage.getBillItemDetailstext();
		assertTrue(actualVendorBillItems.contains(expectedBillItem));
		assertTrue(actualVendorBillItems.contains(grossAmt));
		//Validate on the vendor bill Vertex CUT TAX ACTION field should be updated as Within Threshold
		String actualCutTaxActionText =  billOrderPage.getVertexCutTaxActiontext();
		assertTrue(actualCutTaxActionText.contains(overpayCutAction));
		//Reset Threshold amount back to '1'
		resetCutThresholdAmount();
	}

	/**
	 * Create automation script to take PO into a Vendor Bill - Test an UNDERCHARGE situation and validate the xml
	 * Undercharge is when Vendor tax less than Vertex tax
	 * @author ravunuri
	 * CNSAPI-xxxx
	 */
	@Test(groups = {"netsuite_suite_cert","suite_tax_regression"})
	protected void createPurchaseOrderUnderchargeTest( )
	{   //Define Vendors and Items
		NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
		String expectedTaxResult = "Success";
		String overpayCutAction = "Overpay";
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1").amount("100.00").build();

		//Create Purchase Order and Validate its XML Response
		NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		//Navigate to Enter Purchase Order menu
		NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
		setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
		NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();
		//Verify the “Vertex Tax Error code” field is showing 'Success' status.
		String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
		assertEquals(actualMessage, expectedTaxResult);

		//Create vendor bill, enter the Vendor tax $2 less than the Vertex tax
		NetsuiteAPIBillOrderPage billOrderPage = savedPurchaseOrderPage.billOrder();
		billOrderPage.enterVendorTax("7.63");
		billOrderPage.enterLocation("01: San Francisco");
		//Select the VERTEX UNDERCHARGE ACTION as Overpay, enter Location, Save Bill and Make Payment
		billOrderPage.selectUnderChargeAction("Overpay");
		billOrderPage.saveBill();
		//Validate on the vendor bill Vertex CUT TAX ACTION field should be updated as Overpay
		String actualCutTaxActionText =  billOrderPage.getVertexCutTaxActiontext();
		assertTrue(actualCutTaxActionText.contains(overpayCutAction));
		billOrderPage.submitPayment();
		billOrderPage.saveOrder();
	}

	/**
	 * Create automation script to take PO into a Vendor Bill - Test an UNDERCHARGE and ACCRUAL situation & validate xml
	 * On the vendor bill Vertex CUT Action field should be updated as Pay & Accrue
	 * On the payment, Vertex Tax details should show the accrual request and Journal entry should be created.
	 * @author ravunuri
	 * CNSAPI-xxxx
	 */
	@Test(groups = {"netsuite_suite_cert","suite_tax_regression"})
	protected void createPOUnderchargeAccrualTest( )
	{   //Define Vendors and Items
		NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
		String expectedTaxResult = "Success";
		String requestType = "AccrualRequest";
		String expectedBillItem = "Tax Adjustment";
		String grossAmt = "-2.00";
		String overpayCutAction = "Pay & Accrue";
		String journalLink = "Journal #JOU";
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1").amount("100.00").build();

		//Create Purchase Order and Validate its XML Response
		NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		//Navigate to Enter Purchase Order menu
		NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
		setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
		NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();
		//Verify the “Vertex Tax Error code” field is showing 'Success' status.
		String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
		assertEquals(actualMessage, expectedTaxResult);

		//Create vendor bill, enter the Vendor tax $2 less than the Vertex tax
		NetsuiteAPIBillOrderPage billOrderPage = savedPurchaseOrderPage.billOrder();
		billOrderPage.enterVendorTax("7.63");
		billOrderPage.enterLocation("01: San Francisco");
		//Select the VERTEX UNDERCHARGE ACTION as Pay & Accrue, enter Location, Save Bill and Make Payment
		billOrderPage.selectUnderChargeAction("Pay & Accrue");
		billOrderPage.saveBill();
		billOrderPage.submitPayment();
		billOrderPage.saveOrder();

		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = billOrderPage.element.isElementDisplayed(unrolled_view_locator);
		if (isUnrolledView_Button_Visible){
			billOrderPage.click.clickElement(unrolled_view_locator);
		}
		//Validate the TaxResult=Success and RequestType=AccrualRequest in Vertex Call details
		String actualCallDetails =  billOrderPage.getCallDetailstext();
		assertTrue(actualCallDetails.contains(expectedTaxResult));
		assertTrue(actualCallDetails.contains(requestType));
		//Validate VERTEX ASSOCIATED TRANSACTION field have Journal link
		String actualJournalLink =  billOrderPage.getVertexAssociatedTranstext();
		assertTrue(actualJournalLink.contains(journalLink));

		//Click on Bill in Apply section to access the Bill record
		billOrderPage.accessBill();
		//Validate on the Vendor bill, Tax Adjustment item should be there for the difference amount(-2)
		String actualVendorBillItems =  billOrderPage.getBillItemDetailstext();
		assertTrue(actualVendorBillItems.contains(expectedBillItem));
		assertTrue(actualVendorBillItems.contains(grossAmt));
		//Validate on the vendor bill Vertex CUT TAX ACTION field should be updated as Within Threshold
		String actualCutTaxActionText =  billOrderPage.getVertexCutTaxActiontext();
		assertTrue(actualCutTaxActionText.contains(overpayCutAction));
	}

	/**
	 * Create automation script to take PO into a Vendor Bill - Test an OVERCHARGE - SHORTPAY situation & validate xml
	 * On the vendor bill Vertex CUT Action field should be updated as Pay & Accrue
	 * On the payment, Vertex Tax details should show the accrual request and Journal entry should be created.
	 * @author ravunuri
	 * CNSAPI-xxxx
	 */
	@Test(groups = {"netsuite_suite_cert","suite_tax_regression"})
	protected void createPOShortPayTest( )
	{   //Define Vendors and Items
		NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
		String expectedTaxResult = "Success";
		String requestType = "InvoiceVerificationRequest";
		String overpayCutAction = "Shortpay";
		String journalLink = "Journal #JOU";
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").build();

		//Create Purchase Order and Validate its XML Response
		NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		//Navigate to Enter Purchase Order menu
		NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
		setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
		NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();
		//Verify the “Vertex Tax Error code” field is showing 'Success' status.
		String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
		assertEquals(actualMessage, expectedTaxResult);

		//Create vendor bill, enter the Vendor tax $2 more than the Vertex tax
		NetsuiteAPIBillOrderPage billOrderPage = savedPurchaseOrderPage.billOrder();
		billOrderPage.enterVendorTax("11.63");
		billOrderPage.enterLocation("01: San Francisco");
		//Select the VERTEX OVERCHARGE ACTION as Shortpay, enter Location, Save Bill and Make Payment
		billOrderPage.selectOverChargeAction("Shortpay");
		billOrderPage.saveBill();
		//Validate the TaxResult=Success and RequestType=AccrualRequest in Vertex Call details
		String actualCallDetails =  billOrderPage.getCallDetailstext();
		assertTrue(actualCallDetails.contains(expectedTaxResult));
		assertTrue(actualCallDetails.contains(requestType));
		billOrderPage.submitPayment();
		billOrderPage.saveOrder();

		//Validate VERTEX ASSOCIATED TRANSACTION field have Journal link
		String actualJournalLink =  billOrderPage.getVertexAssociatedTranstext();
		assertFalse(actualJournalLink.contains(journalLink));
		//Click on Bill in Apply section to access the Bill record
		billOrderPage.accessBill();
		String actualCutTaxActionText =  billOrderPage.getVertexCutTaxActiontext();
		assertTrue(actualCutTaxActionText.contains(overpayCutAction));
	}

	/**
	 * Create automation script to take PO into a Vendor Bill - Test an overcharge Pay Vendor tax situation,
	 * Pay Vendor Bill and check Vertex Tax Journal
	 * @author ravunuri
	 * CNSAPI-xxxx
	 */
	@Test(groups = {"netsuite_suite_cert","suite_tax_regression"})
	protected void createPOOverchargePayTest( )
	{   //Define Vendors and Items
		NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
		String expectedTaxResult = "Success";
		String overpayCutAction = "Pay Vendor Tax";
		String journalLink = "Journal #JOU";
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").build();

		//Create Purchase Order and Validate its XML Response
		NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		//Navigate to Enter Purchase Order menu
		NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
		setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
		NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();
		//Verify the “Vertex Tax Error code” field is showing 'Success' status.
		String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
		assertEquals(actualMessage, expectedTaxResult);

		//Create vendor bill, enter the Vendor tax $2 more than the Vertex tax
		NetsuiteAPIBillOrderPage billOrderPage = savedPurchaseOrderPage.billOrder();
		billOrderPage.enterVendorTax("11.63");
		billOrderPage.enterLocation("01: San Francisco");
		//Select the VERTEX OVERCHARGE ACTION as Pay Vendor Tax, enter Location, Save Bill and Make Payment
		billOrderPage.selectOverChargeAction("Pay Vendor Tax");
		billOrderPage.saveBill();
		billOrderPage.submitPayment();
		billOrderPage.saveOrder();
		//Validate VERTEX ASSOCIATED TRANSACTION field have Journal link
		String actualJournalLink =  billOrderPage.getVertexAssociatedTranstext();
		assertFalse(actualJournalLink.contains(journalLink));
		//Click on Bill in Apply section to access the Bill record
		billOrderPage.accessBill();
		String actualCutTaxActionText =  billOrderPage.getVertexCutTaxActiontext();
		assertTrue(actualCutTaxActionText.contains(overpayCutAction));
	}
}
