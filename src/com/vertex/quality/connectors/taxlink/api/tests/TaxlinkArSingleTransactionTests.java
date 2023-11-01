package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.DataKeywords;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.initializeCalcApiTest;

/**
 * API tests that use singular transactions per scenario for AR
 *
 * @author ajain
 */
public class TaxlinkArSingleTransactionTests
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { })
	private void setup( ) { initializeCalcApiTest(); }

	/**
	 * Test for verifying Invoice Large Decimal Number for AR
	 */
	@Test(groups = { "TL_regression" })
	public void arInvoiceLargeDecimalNumberTest( )
	{
		webservice.sendArSingleRequest("ar-invoicelargedecimalnumber", "ARINVLARGEDECIMALNUMBER");
		data.validateArTaxAmtTaxRateLineAmtForTwoLines("ar-invoicelargedecimalnumber", "0.94", "4", "23.52", "1.06",
			"4.5", "23.52");
	}

	/**
	 * Test for verifying Invoice Recycling Fee for AR
	 */
	@Test(groups = { "TL_regression" })
	public void arInvoiceRecyclingFeeTest( )
	{
		webservice.sendArSingleRequest("ar-invoicerecyclingfee", "ARINVRECYCLINGFEE");
		data.validateArTaxAmtTaxRateLineAmtForTwoLines("ar-invoicerecyclingfee", "12.5", "1.25", "1000", "60", "6",
			"1000");
	}

	/**
	 * Test for verifying Invoice Tax Only for AR
	 */
	@Test(groups = { "TL_regression" })
	public void arInvoiceTaxOnlyTest( )
	{
		webservice.sendArSingleRequest("ar-invoicetaxonly", "ARINVTAXONLY");
		data.validateArTaxAmtTaxRateLineAmtForSingleLine("ar-invoicetaxonly", "60", "1", "0");
	}

	/**
	 * Test for verifying Invoice Tax Only for AR
	 */
	@Test(groups = { "TL_regression" })
	public void arInvoiceRecalcTest( )
	{
		webservice.sendArSingleRequest("ar-invoicerecalc", "ARINVRECALC");
		data.validateArTaxAmtTaxRateLineAmtForTwoLines("ar-invoicerecalc", "15", "0.375", "4000", "160", "4", "4000");
	}

	/**
	 * Test for Invoice Abbreviated Province for AR
	 */
	@Test(groups = { "TL_regression" })
	public void arInvoiceAbbreviatedProvinceTest( )
	{
		webservice.sendArSingleRequest("ar-invoiceabbreviatedprovince", "ARINVABBREVIATEDPROVINCE");
		data.validateArTaxAmtTaxRateLineAmtForSingleLine("ar-invoiceabbreviatedprovince", "6", "6", "100");
	}
}


