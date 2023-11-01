package com.vertex.quality.connectors.ariba.supplier.tests;

import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierCreateInvoicePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierHomePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInboxPage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInvoiceReviewAndSubmitPage;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * this class represents all the tests performed on the login and creating invoice process
 * contains test on navigation and calculations
 *
 * @author osabha
 */
//@Test(groups = { "supplier" })
public class AribaSupplierLogInAndInvoicingTests extends AribaSupplierBaseTest
{ // this is just a template test that logs in and creates invoice for a given PO.
	// it will be used later in complete end to end test scenarios


	@Test(groups = { "supplier" })
	public void LoginAndInvoicingTest( )
	{
		String poNumber = null;
		String inboxButton = "INBOX";
		AribaSupplierHomePage supplierHomePage = signInToSupplier(testStartPage);
		AribaSupplierInboxPage inboxPage = supplierHomePage.headerPane.clickInboxButton(inboxButton);
		try
		{
			poNumber = SQLConnection.retrieveOrderNumber("basicRequisitionTaxExemptProductTest");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		inboxPage.selectTargetPo(poNumber);
		inboxPage.clickCreateInvoiceButton();
		AribaSupplierCreateInvoicePage createInvoicePage = inboxPage.selectStandardInvoice();
		createInvoicePage.enterInvoiceNumber();
		createInvoicePage.taxDetails.enableCheckBoxByDisplayText("Header level tax");
		createInvoicePage.taxDetails.setTaxCategory("Sales");
		createInvoicePage.taxDetails.enterTaxAmount("$123.00 USD");
		createInvoicePage.clickUpdateButton();
		createInvoicePage.clickSaveButton();
		AribaSupplierInvoiceReviewAndSubmitPage invoiceReview = createInvoicePage.clickNext();
		invoiceReview.clickSubmitButton();
	}
}
