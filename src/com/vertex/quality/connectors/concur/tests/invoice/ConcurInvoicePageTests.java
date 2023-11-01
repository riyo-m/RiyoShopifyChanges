package com.vertex.quality.connectors.concur.tests.invoice;

import com.vertex.quality.connectors.concur.enums.ConcurHeaderTab;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurEnterInvoiceDetailsPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurInvoicePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurViewInvoiceDetailsPage;
import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceExpense;
import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceRow;
import com.vertex.quality.connectors.concur.tests.base.ConcurUIBaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests functionality of invoice page
 *
 * @author alewis
 */
@Test(groups = { "smoke", "invoice" })
public class ConcurInvoicePageTests extends ConcurUIBaseTest
{
	//Eventually put strings in SQL
	protected final String vendorNameInput = "Harry Inc";
	protected final String address1Input = "2301 Renaissance Blvd";
	protected final String address2Input = "";
	protected final String cityInput = "King of Prussia";
	protected final String stateInput = "PA";
	protected final String zipCodeInput = "19406";
	protected final String countryInput = "united states";
	protected final String currencyInput = "USD-US, Dollar";

	protected final String expectedInvoiceName = "Vertex Test Input";
	protected final String expectedInvoiceDate = "06/07/2019";
	protected final String expectedCurreny = "USD-US, Dollar";
	protected final String totalInvoiceAmmountInput = "10";
	protected final String taxProviderCompanyInput = "Geek Squad";
	protected final String shipToAddressValue = "PACS (D_US_PA1)";

	protected final String expenseTypeInput = "Fuel";
	protected final String lineDescriptionInput = "87 octane";
	protected final String quantityInput = "1";
	protected final String unitPriceInput = "10";
	protected final String expectedApprovalStatus = "Pending Approval\n" + "Approver, Approver";

	protected final String expectedPaymentStatus = "Not Paid";
	protected final String invoiceNumber = "";
	protected final String expectedTotalPay = "$10.00";

	// all tax currently expected to be 0
	protected final String expectedCalculatedTaxAmount = "$0.00";
	protected final String expectedCalculatedTaxRate = " ";

	/**
	 * tests the tax on an pennsylvanian invoice
	 */
	@Test
	public void checkTaxOnInvoicePATest( )
	{
		String randomInvoiceNumber = getRandomIntegerBetweenRange(100000001.0, 999999999.0);

		String expectedInvoiceNumber = String.format("%s%s", invoiceNumber, randomInvoiceNumber);

		//navigate to invoice page
		ConcurInvoicePage invoicePage = navigateToHeaderTabPage(ConcurHeaderTab.INVOICE, testStartPage);

		//create new invoice
		invoicePage.createNewInvoice();
		//request new vendor

		//ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.requestNewVendor(vendorNameInput, address1Input,
		//address2Input, cityInput, stateInput, zipCodeInput, countryInput, currencyInput);

		ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.selectVendor("Media");

		//enter invoice details
		enterDetailsPage.enterInvoiceDetails(expectedInvoiceName, expectedInvoiceNumber, expectedInvoiceDate,
			expectedCurreny, totalInvoiceAmmountInput, taxProviderCompanyInput, shipToAddressValue);

		ArrayList<ConcurInvoiceExpense> exp = new ArrayList<>();
		exp.add(new ConcurInvoiceExpense(expenseTypeInput, lineDescriptionInput, quantityInput, unitPriceInput,
			expectedTotalPay, null, null));

		//add expenses
		ConcurInvoicePage concurInvoicePage = enterDetailsPage.handleAmountRemainingPopup(exp);

		ConcurInvoiceRow invoiceData = concurInvoicePage.validateInvoiceCreation(expectedInvoiceNumber);

		String actualInvoiceName = invoiceData.getInvoiceName();
		String actualVendorName = invoiceData.getVendorName();
		String actualInvoiceNumber = invoiceData.getInvoiceNumber();
		String actualInvoiceDate = invoiceData.getInvoiceDate();
		String actualApprovalStatus = invoiceData.getApprovalStatus();
		String actualPaymentStatus = invoiceData.getPaymentStatus();
		String actualTotalPay = invoiceData.getTotalPay();

		assertEquals(expectedInvoiceName, actualInvoiceName);
		assertEquals(vendorNameInput, actualVendorName);
		assertEquals(expectedInvoiceNumber, actualInvoiceNumber);
		assertEquals(expectedInvoiceDate, actualInvoiceDate);
		assertEquals(expectedApprovalStatus, actualApprovalStatus);
		assertEquals(expectedPaymentStatus, actualPaymentStatus);
		assertEquals(expectedTotalPay, actualTotalPay);
	}

	/**
	 * tests the tax on an alaskan invoice
	 */
	@Test(groups = { "us_tax_calc" })
	public void checkTaxOnInvoiceAlaskaTest( )
	{
		String vendorNameInputAlaska = "AlaskaVendor";
		String expectedInvoiceDateAlaska = "06/07/2019";
		String expectedApprovalStatusAlaska = "Pending Approval\n" + "Approver, Approver";
		String expectedPaymentStatusAlaska = "Not Paid";
		String expectedTotalPayAlaska = "$10.00";

		String randomInvoiceNumber = getRandomIntegerBetweenRange(100000001.0, 999999999.0);

		String expectedInvoiceNumber = String.format("%s%s", invoiceNumber, randomInvoiceNumber);

		//navigate to invoice page
		ConcurInvoicePage invoicePage = navigateToHeaderTabPage(ConcurHeaderTab.INVOICE, testStartPage);

		//create new invoice
		invoicePage.createNewInvoice();
		//request new vendor

		ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.selectVendor("Anchorage");

		//enter invoice details
		enterDetailsPage.enterInvoiceDetails(expectedInvoiceName, expectedInvoiceNumber, expectedInvoiceDate,
			expectedCurreny, totalInvoiceAmmountInput, taxProviderCompanyInput,shipToAddressValue);

		ArrayList<ConcurInvoiceExpense> exp = new ArrayList<>();
		exp.add(new ConcurInvoiceExpense(expenseTypeInput, lineDescriptionInput, quantityInput, unitPriceInput,
			expectedTotalPay, null, null));

		//add expenses
		ConcurInvoicePage concurInvoicePage = enterDetailsPage.handleAmountRemainingPopup(exp);

		ConcurInvoiceRow invoiceData = concurInvoicePage.validateInvoiceCreation(expectedInvoiceNumber);

		String actualInvoiceName = invoiceData.getInvoiceName();
		String actualVendorName = invoiceData.getVendorName();
		String actualInvoiceNumber = invoiceData.getInvoiceNumber();
		String actualInvoiceDate = invoiceData.getInvoiceDate();
		String actualApprovalStatus = invoiceData.getApprovalStatus();
		String actualPaymentStatus = invoiceData.getPaymentStatus();
		String actualTotalPay = invoiceData.getTotalPay();

		assertEquals(expectedInvoiceName, actualInvoiceName);
		assertEquals(vendorNameInputAlaska, actualVendorName);
		assertEquals(expectedInvoiceNumber, actualInvoiceNumber);
		assertEquals(expectedInvoiceDateAlaska, actualInvoiceDate);
		assertEquals(expectedApprovalStatusAlaska, actualApprovalStatus);
		assertEquals(expectedPaymentStatusAlaska, actualPaymentStatus);
		assertEquals(expectedTotalPayAlaska, actualTotalPay);

	}

	/**
	 * tests the tax on a pa invoice, new invoice page after submission
	 */
	@Test(groups = { "us_tax_calc" })
	public void checkTaxOnNewInvoicePageTest( )
	{
		String randomInvoiceNumber = getRandomIntegerBetweenRange(100000001.0, 999999999.0);

		String expectedInvoiceNumber = String.format("%s%s", invoiceNumber, randomInvoiceNumber);

		//navigate to invoice page
		ConcurInvoicePage invoicePage = navigateToHeaderTabPage(ConcurHeaderTab.INVOICE, testStartPage);

		//create new invoice
		invoicePage.createNewInvoice();
		//request new vendor

		ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.selectVendor("Media");

		//enter invoice details
		enterDetailsPage.enterInvoiceDetails(expectedInvoiceName, expectedInvoiceNumber, expectedInvoiceDate,
			expectedCurreny, totalInvoiceAmmountInput, taxProviderCompanyInput,shipToAddressValue);

		ArrayList<ConcurInvoiceExpense> exp = new ArrayList<>();
		exp.add(new ConcurInvoiceExpense(expenseTypeInput, lineDescriptionInput, quantityInput, unitPriceInput,
			expectedTotalPay, null, null));

		//add expenses
		ConcurInvoicePage concurInvoicePage = enterDetailsPage.handleAmountRemainingPopup(exp);

		ConcurInvoiceRow invoiceData = concurInvoicePage.validateInvoiceCreation(expectedInvoiceNumber);

		concurInvoicePage.selectInvoice(vendorNameInput);

		assertTrue(true);
	}

	/**
	 * tests the calculated tax rate and amount in itemization summary section
	 */
	public void checkTaxRateAndAmountTest( )
	{
		String randomInvoiceNumber = getRandomIntegerBetweenRange(100000001.0, 999999999.0);

		String expectedInvoiceNumber = String.format("%s%s", invoiceNumber, randomInvoiceNumber);

		//navigate to invoice page
		ConcurInvoicePage invoicePage = navigateToHeaderTabPage(ConcurHeaderTab.INVOICE, testStartPage);

		//create new invoice
		invoicePage.createNewInvoice();

		//request new vendor
		ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.selectVendor("Media");

		//enter invoice details
		enterDetailsPage.enterInvoiceDetails(expectedInvoiceName, expectedInvoiceNumber, expectedInvoiceDate,
			expectedCurreny, totalInvoiceAmmountInput, taxProviderCompanyInput,shipToAddressValue);

		ArrayList<ConcurInvoiceExpense> exp = new ArrayList<>();
		exp.add(new ConcurInvoiceExpense(expenseTypeInput, lineDescriptionInput, quantityInput, unitPriceInput,
			expectedTotalPay, null, null));

		//add expenses
		ConcurInvoicePage concurInvoicePage = enterDetailsPage.handleAmountRemainingPopup(exp);

		concurInvoicePage.validateInvoiceCreation(expectedInvoiceNumber);

		// open invoice
		ConcurViewInvoiceDetailsPage viewInvoicePage = concurInvoicePage.selectInvoice(vendorNameInput);

		ConcurInvoiceExpense expenseDetails = viewInvoicePage.getItemDetailsForItemOne();

		String taxAmount = expenseDetails.getCalculatedTaxAmountInput();
		String taxRate = expenseDetails.getCalculatedTaxRateInput();

		// verify
		assertEquals(expectedCalculatedTaxAmount, taxAmount);
		assertEquals(expectedCalculatedTaxRate, taxRate);

		// delete invoice
		enterDetailsPage = viewInvoicePage.recallInvoice();
		enterDetailsPage.deleteInvoiceReturnToList();
	}

	/**
	 * helper function to generated random number in range. Used to make 'unique' invoice number
	 *
	 * @param min minimum number of range
	 * @param max maximum number of range
	 *
	 * @return random integer between min and max
	 */
	public static String getRandomIntegerBetweenRange( double min, double max )
	{
		double x = (int) (Math.random() * ((max - min) + 1)) + min;
		int x1 = (int) x;
		String x2 = Integer.toString(x1);
		return x2;
	}
}
