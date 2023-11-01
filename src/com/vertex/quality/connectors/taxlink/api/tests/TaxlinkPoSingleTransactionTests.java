package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.DataKeywords;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.initializeCalcApiTest;

/**
 * API tests that use singular transactions per scenario.
 *
 * @author msalomone, ajain, mgaikwad
 */
public class TaxlinkPoSingleTransactionTests
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TL_regression" })
	private void setup( ) { initializeCalcApiTest(); }

	/**
	 * Test for AP Invoice Matched PO Decimal Line Amount Unit Price Quantity Test
	 */
	@Test(groups = { "TL_regression" })
	public void createApInvoiceMatchedPoDecimalLineAmtUnitPriceQtyTest( )
	{
		webservice.sendPoSingleRequest("po-apinvmatchdecimalamtpriceqty", "POAPINVMATCGDECIMALAMTPRICEQTY");
		data.validatePOTaxAmtTaxRateLineAmtForSingleLine("po-apinvmatchdecimalamtpriceqty", "0.1", "6", "1.56");
	}

	/**
	 * Test for PO Decimal Line Amount Unit Price Quantity Test for AR
	 */
	@Test(groups = { "TL_regression" })
	public void createPoDecimalLineAmtUnitPriceQtyTest( )
	{
		webservice.sendPoSingleRequest("po-createdecimallineamtunitpriceqty", "POCREATEDECIMALLINEAMTUNITPRICEQTY");
		data.validatePOTaxAmtTaxRateLineAmtForSingleLine("po-createdecimallineamtunitpriceqty", "0.1", "6", "1.5639002584028");
	}

	/**
	 * Test for a PO with two lines : one line with
	 * a large decimal price, and another with a large number of
	 * decimals for the qty.
	 */
	@Test(groups = { "TL_regression" })
	public void createPoWith2LinesTest( )
	{
		webservice.sendPoSingleRequest("po-createpowithtwolines", "POCREATEPOWITHTWOLINES");
		data.validatePoTaxAmtTaxRateLineAmtForTwoLines("po-createpowithtwolines", "33.8", "6", "563.21597", "34.49",
			"6", "574.89635");
	}

	/**
	 * Test for a PO with a description containing 240 characters that includes a combination
	 * of numbers, letters, and special characters.
	 */
	@Test(groups = { "TL_regression" })
	public void createPo240CharDescriptionTest( )
	{
		webservice.sendPoSingleRequest("po-create240charsdescription", "POCREATE240CHARSDESC");
		data.validatePOTaxAmtTaxRateLineAmtForSingleLine("po-create240charsdescription", "6", "6", "100");
	}

	/**
	 * Test for a PO and an invoice matching to the PO before
	 * verifying that Oracle successfully saves it with 240 chars description
	 */
	@Test(groups = { "TL_regression" })
	public void createApInvoiceMatchedPoWith240CharTest( )
	{
		webservice.sendPoSingleRequest("po-createApInvoiceMatchedPo240charsdescription",
			"POAPINVOICEMATCHEDCREATE240CHARSDESC");
		data.validatePOTaxAmtTaxRateLineAmtForSingleLine("po-createApInvoiceMatchedPo240charsdescription", "6", "6",
			"100");
	}
	/** Test for create AP invoice using the PO created
	 **/
	@Test(groups = { "TL_regression" })
	public void createApInvoiceMatchedWithPoTest()
	{
		webservice.sendPoSingleRequest("po-createapinvoicematchwithpo","POCREATEAPINVOICEMATCHEDWITHPO");
		data.validatePoTaxAmtTaxRateLineAmtForTwoLines("po-createapinvoicematchwithpo","33.8","6","563.21597","34.49","6","574.89635");
	}

}


