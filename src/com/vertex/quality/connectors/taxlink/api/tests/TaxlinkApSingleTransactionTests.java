package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.DataKeywords;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.initializeCalcApiTest;

/**
 * API tests that use singular transactions per scenario.
 *
 * @author msalomone, ajain
 */
public class TaxlinkApSingleTransactionTests
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TL_smoke", "TL_regression" })
	private void setup( ) { initializeCalcApiTest(); }


	/** Test for performing smoke Test for AP
	 * */
	@Test(groups = { "TL_smoke" })
	public void apSmokeTest( )
	{
		webservice.sendApSingleRequest("ap-smoke", "APSMOKE");
		data.validateTaxAmtTaxRateLineAmtForTwoLines("ap-smoke", "45", "4.5", "1000","3.75", "0.375","1000");
	}

	/** Test for performing PrePayment Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apPrePaymentTest( )
	{
		webservice.sendApSingleRequest("ap-prepayment", "APPREPAYMENT");
		data.ValidateApLineAmounts("ap-prepayment", "500", "500");
	}

	/** Test for performing Tax Only Invoice Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apTaxOnlyInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-taxonlyinvoice", "APTAXONLYINVOICE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-taxonlyinvoice", "100","6","100");
	}

	/** Test for performing Recalculate Invoice Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apRecalculateInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-recalculateinv", "APRECALCULATEINV");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-recalculateinv", "60","6","1000");
	}

	/** Test for performing Invoice With Yen Currency Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apInvoiceWithYenCurrencyTest( )
	{
		webservice.sendApSingleRequest("ap-invoicewithyencurrency", "APINVYENCURRENCY");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-invoicewithyencurrency", "6","6","100");
	}

	/** Test for performing Invoice Default Exchange Rate Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apInvoiceDefaultExchangeRateTest( )
	{
		webservice.sendApSingleRequest("ap-invoicedefaultexchangerate", "APINVDEFAULTEXCHANGERATE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-invoicedefaultexchangerate", "0","0","34");
	}

	/** Test for performing Cancel Invoice Payment Void Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apCancelInvoicePaymentVoidTest( )
	{
		webservice.sendApSingleRequest("ap-cancelinvoicepaymentvoid", "APCANCELINVPAYVOID");
		data.validateTaxAmtTaxRateLineAmtForTwoLines("ap-cancelinvoicepaymentvoid", "40","4","1000","45","4.5","1000");
	}

	/** Test for performing Invoice Override Tax Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apInvoiceOverrideTaxTest( )
	{
		webservice.sendApSingleRequest("ap-invoiceoverridetax", "APINVOVERRIDETAX");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-invoiceoverridetax", "45","4.5","1000");
	}

	/** Test for performing I-Supplier Invoice Charged Tax Equal Calc Tax Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apISupplierInvoiceChargedTaxEqualCalcTaxTest( )
	{
		webservice.sendApSingleRequest("ap-isupplierinvoicechargedtaxequalcalctax", "APISUPPINVCHARGETAXEQUALCALCTAX");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-isupplierinvoicechargedtaxequalcalctax", "60","6","1000");
	}

	/** Test for performing I-Supplier Invoice Charged Tax Less Than Calc Tax Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apISupplierInvoiceChargedTaxLessThanCalcTaxTest( )
	{
		webservice.sendApSingleRequest("ap-isupplierinvoicechargedtaxlessthancalctax", "APISUPPINVCHARGETAXLESSTHANCALCTAX");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-isupplierinvoicechargedtaxlessthancalctax", "60","6","1000");
	}

	/** Test for performing I-Supplier Invoice Charged Tax Zero Calc Tax Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apISupplierInvoiceChargedTaxZeroCalcTaxTest( )
	{
		webservice.sendApSingleRequest("ap-isupplierinvoicechargedtaxzerocalctax", "APISUPPINVCHARGETAXLZEROCALCTAX");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-isupplierinvoicechargedtaxzerocalctax", "60","6","1000");
	}

	/** Test for performing I-Supplier Line Level Tax Test for AP
	 * */
	@Test(groups = { "TL_regression" })
	public void apISupplierLineLevelTaxTest( )
	{
		webservice.sendApSingleRequest("ap-isupplierlineleveltax", "APISUPPLIERLINELEVELTAX");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-isupplierlineleveltax", "6","6","100");
	}

	/**
	 * Test for AP tax only invoice using the newly added tax rate code 'VTX_CA_GST_IMP'
	 */
	@Test(groups = { "TL_regression" })
	public void apCaGstImportTaxOnlyInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-cagstimporttaxonlyinvoice", "APCAGSTIMPORTTAXONLYINVOICE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-cagstimporttaxonlyinvoice", "100","0","100");
	}

	/**
	 * Test for AP tax only invoice using the newly added tax rate code 'VTX_CA_HST_IMP'
	 */
	@Test(groups = { "TL_regression" })
	public void apCaHstImportTaxOnlyInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-cahstimporttaxonlyinvoice", "APCAHSTIMPORTTAXONLYINVOICE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-cahstimporttaxonlyinvoice", "100","0","100");
	}

	/**
	 * Test for AP tax only invoice using the newly added tax rate code 'VTX_CA_QST_IMP'
	 */
	@Test(groups = { "TL_regression" })
	public void apCaQstImportTaxOnlyInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-caqstimporttaxonlyinvoice", "APCAQSTIMPORTTAXONLYINVOICE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-caqstimporttaxonlyinvoice", "100","0","100");
	}

	/**
	 * Test for AP tax only invoice using the newly added tax rate code 'VTX_CA_PST_IMP'
	 */
	@Test(groups = { "TL_regression" })
	public void apCaPstImportTaxOnlyInvoiceTest( )
	{
		webservice.sendApSingleRequest("ap-capstimporttaxonlyinvoice", "APCAPSTIMPORTTAXONLYINVOICE");
		data.validateTaxAmtTaxRateLineAmtForSingleLine("ap-capstimporttaxonlyinvoice", "100","0","100");
	}
}


