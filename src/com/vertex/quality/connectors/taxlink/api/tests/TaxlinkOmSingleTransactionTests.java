package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.connectors.taxlink.api.keywords.DataKeywords;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiInitializer.initializeCalcApiTest;

/**
 * API tests that use singular transactions per scenario for OM
 *
 * @author ajain
 */
public class TaxlinkOmSingleTransactionTests
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();
	String errorMessage
		= "VERTEX-0008-Vertex Tax Engine Error:VertexInvalidAddressException:No tax areas were found during the lookup. The address fields are inconsistent for the specified asOfDate. (Street Information=THIS IS A BAD ADDR, Street Information 2=null, ";

	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TL_regression" })
	private void setup( ) { initializeCalcApiTest(); }

	/**
	 * Test for verifying creating Order Ship to PA Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omCreateOrderShipToPaTest( )
	{
		webservice.sendOmSingleRequest("om-createordershiptopa", "OMCREATEORDERSHIPTOPA");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-createordershiptopa", "60", "6", "1000");
	}

	/**
	 * Test for verifying creating Order upto five decimals Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omCreateOrderUpToFiveDecimalsTest( )
	{
		webservice.sendOmSingleRequest("om-createorderuptofivedecimals", "OMCREATEORDERUPTOFIVEDECIMALS");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-createorderuptofivedecimals", "0.72", "6",
			"12.080904305900000750284561945591121912");
	}

	/**
	 * Test for verifying creating Order for Two Lines Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omCreateOrderTwoLinesTest( )
	{
		webservice.sendOmSingleRequest("om-createordertwolines", "OMCREATEORDERTWOLINES");
		data.validateOmTaxAmtTaxRateLineAmtForTwoLines("om-createordertwolines", "60", "6", "1000", "0.72", "6",
			"12.080904305900000750284561945591121912");
	}

	/**
	 * Test for verifying creating Order with Bad ShipTo Address Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omCreateOrderBadShipToAddressTest( )
	{
		webservice.sendOmSingleRequest("om-createorderbadshiptoaddress", "OMCREATEORDERBADSHIPTOADDRESS");
		data.validateErrorStringInResp("om-createorderbadshiptoaddress", errorMessage);
	}

	/**
	 * Test for verifying Edit Order Add line Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omEditOrderAddLineTest( )
	{
		webservice.sendOmSingleRequest("om-editorderaddline", "OMEDITORDERADDLINE");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-editorderaddline", "60", "6", "1000");
	}

	/**
	 * Test for verifying Edit Order and Delete Unreferenced Item Test for OM
	 */
	@Test(groups = { "TL_regression" })
	public void omEditOrderDeleteUnreferencedLineItemTest( )
	{
		webservice.sendOmSingleRequest("om-editorderdelunreferencedlineitem", "OMEDITORDERDELUNREFERENCEDLINEITEM");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-editorderdelunreferencedlineitem", "60", "6", "1000");
	}

	/**
	 * Test for verifying Edit Order and Add Unreferenced Item Test for OM
	 */
	@Test()
	public void omEditOrderAddUnreferencedLineItemTest( )
	{
		webservice.sendOmSingleRequest("om-editorderaddunreferencedlineitem", "OMEDITORDERADDUNREFERENCEDLINEITEM");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-editorderaddunreferencedlineitem", "240", "6", "4000");
	}

	/**
	 * Test for Edit an Order in UI and Change Line Quantity
	 */
	@Test(groups = { "TL_regression" })
	public void omEditOrderChangeLineQuantityTest( )
	{
		webservice.sendOmSingleRequest("om-editorderchangelinequantity", "OMEDITORDERCHANGELINEQUANTITY");
		data.validateOmTaxAmtTaxRateLineAmtForSingleLine("om-editorderchangelinequantity", "60", "6", "1000");
	}
}




