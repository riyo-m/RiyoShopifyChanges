package com.vertex.quality.connectors.ariba.api.tests.requisition;

import com.vertex.quality.connectors.ariba.api.common.webservices.AribaSoapResponse;
import com.vertex.quality.connectors.ariba.api.enums.AribaAddresses;
import com.vertex.quality.connectors.ariba.api.keywords.DataKeywords;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIRequisitionBaseTest;
import com.vertex.quality.connectors.taxlink.api.keywords.WebServiceKeywords;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * contains API Requisition test cases
 *
 * @author osabha, vgosavi
 */
public class AribaRequisitionTests extends AribaAPIRequisitionBaseTest
{
	private WebServiceKeywords webservice = new WebServiceKeywords();
	private DataKeywords data = new DataKeywords();
	AribaSoapResponse aribaSoapResponse = new AribaSoapResponse();

	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression"})
	public void singleLineItemRequisitionTest( )
	{
		String respFileName = "singleLineItemRequisitionTest";
		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.GERMANY_BERLIN_2, 1);
		setItemSupplierAddress(AribaAddresses.GERMANY_BERLIN_1, 1);
		setItemInformationForRequisition(1, 1, 100000, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForStatusTaxTypeTaxAmtForSingleLineRequisition(respFileName, "true", "VATTax", "19000.0");
	}

	/**
	 * CARIBA-973
	 * sends a requisition call to the connector with three line items,
	 * two ducks and one chicken shipping from Canada to Lancaster PA
	 * Verifies Tax Amount calculated
	 * the response will not have taxes because the tax type sales and use tax is filtered in requisition response by
	 * the connector
	 */

	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void multipleItemsReCanToLancasterTest( )
	{
		String respFileName = "multipleItemsReCanToLancasterTest";
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_PA_LANCASTER, 1);
		setItemShipToAddress(AribaAddresses.US_PA_LANCASTER, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_QUEBEC, 1);

		setItemBillingAddresses(AribaAddresses.US_PA_LANCASTER, 2);
		setItemShipToAddress(AribaAddresses.US_PA_LANCASTER, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_QUEBEC, 2);

		setItemInformationForRequisition(1, 1, 100, false);

		setItemInformationForRequisition(2, 2, 25.50, false);

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForStatusTaxTypeTaxAmtForMultipleLineRequisition(respFileName, "NONE", "NONE", "0.0","0.0");
	}

	/**
	 * CARIBA-648
	 * sends a requisition for three line items, two ducks and a chicken
	 * ships from canada to canada
	 * verifies the total tax amount in the oseries request
	 */

	@Test(groups = { "ariba_api","ariba_regression" })
	public void purchaseOrderCAN_CANTest( )
	{
		String respFileName = "purchaseOrderCAN_CANTest";
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);

		setItemInformationForRequisition(1, 1, 100, false);

		setItemInformationForRequisition(2, 2, 25.50, false);

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForStatusTaxTypeTaxAmtForMultipleLineRequisition(respFileName, "VAT", "GSTHST", "15.0" ,"7.6");
	}

	/**
	 * CARIBA-64
	 * sends a requisition for three line items, two ducks and a chicken
	 * ships from canada to canada
	 * verifies the total tax amount in the oseries request
	 */

	@Test(groups = { "ariba_api","ariba_regression" })
	public void purchaseOrderCAN_BC_CAN_ON_Test( )
	{
		String respFileName = "purchaseOrderCAN_BC_CAN_ON_Test";
		apiUtil.requestData.setNumberOfItems(2);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 1);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 2);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setItemInformationForRequisition(1, 1, 100, false);
		setItemInformationForRequisition(2, 2, 	25.50, false);

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForStatusTaxTypeTaxAmtForMultipleLineRequisition(respFileName, "VATTax","GSTTax", "13.0","6.6");
	}

	/**
	 * CARIBA-464
	 * this is asserting for taxes that the buyer will pay the vendor,
	 * all consumer use taxes are not shown here
	 * Same basic idea - submit a requisition and verify we get taxes back
	 */

	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxALTest( )
	{
		String respFileName = "catalogItemTaxALTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_AL_BIRMINGHAM, 1);
		setItemShipToAddress(AribaAddresses.US_AL_BIRMINGHAM, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);

		setItemInformationForRequisition(1, 1, 100, false);

		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","10","10.0");
	}

	/**
		 * CARIBA-465
		 * Same basic idea - submit a requisition and verify we get taxes back.  This one is tricky
		 * though because the address is very obscure and does not easily map back to a single
		 * tax area id
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxAZTest( )
	{
		String respFileName = "catalogItemTaxAZTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_AZ_APACHE_JUNCTION, 1);
		setItemShipToAddress(AribaAddresses.US_AZ_APACHE_JUNCTION, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","8.0","8.0");
	}

	/**
	 * CARIBA-466
	 * Basic single line item requisition test; i like this one because of the number of
	 * jurisdictions that come back
	 */
	@Test(groups = { "ariba_api","ariba_regression" })

	public void catalogItemTaxCATest( )
	{
		String respFileName = "catalogItemTaxCATest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_CA_LOS_ANGELES, 1);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","0","0.0");
	}

	/**
	 * CARIBA-467
	 * another single line line requisition test, this time for colorado
	 */
	@Test(groups = { "ariba_api","ariba_regression" })

	public void catalogItemTaxCOTest( )
	{
		String respFileName = "catalogItemTaxCOTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_CO_HIGHLANDS_RANCH, 1);
		setItemShipToAddress(AribaAddresses.US_CO_HIGHLANDS_RANCH, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SALES", "SalesTaxState","5.0","5.0");
	}

	/**
	 * CARIBA-468
	 * Another single line requisition test, this one is for the best state ever (no sales taxes!)
	 * Consequently the tax rate for this one should be $0.
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxDETest( )
	{
		String respFileName = "catalogItemTaxDETest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_DE_NEWARK, 1);
		setItemShipToAddress(AribaAddresses.US_DE_NEWARK, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","0","0.0");
	}

	/**
	 * CARIBA-469
	 * Here another one, this one using Florida as the jurisdiction.Important note, we used this
	 * one to test tax-exempt items, it is expected that we get 0 taxes back, if we ever do get taxes
	 * back there's something wrong with our setup.
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxFLTest( )
	{
		String respFileName = "catalogItemTaxDETest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_FL_ORLANDO, 1);
		setItemShipToAddress(AribaAddresses.US_FL_ORLANDO, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","6.5","6.5");
	}

	/**
	 * CARIBA-470
	 * Another one, this one is for Louisiana
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxLATest( )
	{
		String respFileName = "catalogItemTaxLATest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_LA_NEW_ORLEANS, 1);
		setItemShipToAddress(AribaAddresses.US_LA_NEW_ORLEANS, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","9.45","9.4");
	}

	/**
	 * CARIBA-472
	 * NY, where the taxes are too high
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxNYTest( )
	{
		String respFileName = "catalogItemTaxNYTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","8.875","8.9");
	}

	/**
	 * CARIBA-473
	 * simple PA test, tax rate is a straight 6%
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxPATest( )
	{
		String respFileName = "catalogItemTaxPATest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_PA_LANCASTER, 1);
		setItemShipToAddress(AribaAddresses.US_PA_LANCASTER, 1);

		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SALES", "SalesTaxState","6","6.0");
	}

	/**
	 * CARIBA-475
	 * Puerto Rico test to ensure we can handle us territories
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxPRTest( )
	{
		String respFileName = "catalogItemTaxPRTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_PR_PONCE, 1);
		setItemShipToAddress(AribaAddresses.US_PR_PONCE, 1);

		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","11.5","11.5");
	}

	/**
	 * CARIBA-476
	 * This one is for Texas
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void catalogItemTaxTXTest( )
	{
		String respFileName = "catalogItemTaxTXTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_TX_DALLAS, 1);
		setItemShipToAddress(AribaAddresses.US_TX_DALLAS, 1);

		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","8.25","8.2");
	}

	/**
	 * CARIBA-477
	 * And this one is for Washington
	 */
	@Test(groups = { "ariba_api","ariba_regression"})
	public void catalogItemTaxWATest( )
	{
		String respFileName = "catalogItemTaxTXTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_WA_SAMMAMISH, 1);
		setItemShipToAddress(AribaAddresses.US_WA_SAMMAMISH, 1);

		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","10.1","10.1");
	}

	/**
	 * CARIBA-1017
	 * Sending a requisition request from India to India
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void requisitionIndiaTaxTypesSupportTest( )
	{
		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.INDIA_RAMDASPETH, 1);
		setItemShipToAddress(AribaAddresses.INDIA_RAMDASPETH, 1);

		setItemSupplierAddress(AribaAddresses.INDIA_PORT_BLAIR, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.body("**.findAll { it.name() == 'Category' }", equalTo("VAT"))
			.body("**.find { it.name() == 'ExternalTaxType'}.UniqueName", equalTo("CGST"))
			.body("**.find { it.name() == 'Percent'}", equalTo("18"))
			.body("**.find { it.name() == 'TaxAmount'}.Amount", equalTo("18.0"));
	}

	/**
	 * CARIBA-1020
	 * Sending a requisition request from France to France
	 * expecting the returned values to Ariba are in Decimal format
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void AribaResponseNumericValuesFormatTest( )
	{
		String respFileName = "AribaResponseNumericValuesFormatTest";
		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.FRANCE_MARSEILLE, 1);
		setItemShipToAddress(AribaAddresses.FRANCE_MARSEILLE, 1);
		setItemSupplierAddress(AribaAddresses.FRANCE_SAINT_TROPEZ, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForAbatementPercentPercentTaxAmountForSingleLineRequisition(respFileName, "100.00", "0","0.0");
	}

	/**
	 * CARIBA-1026
	 * Tests response tax code country default from 'USA' to the destination country from the request
	 * Change default tax code value for VAT tax results. Currently using different values for VAT and VAT Import.
	 * All VAT imposition should use the configuration value for "Vendor Tax Code"
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void aribaResponseTaxCodeCountryForVatTest( )
	{
		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.FRANCE_MARSEILLE, 1);
		setItemShipToAddress(AribaAddresses.FRANCE_MARSEILLE, 1);

		setItemSupplierAddress(AribaAddresses.FRANCE_SAINT_TROPEZ, 1);
		setItemInformationForRequisition(1, 1, 100, false);
		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.rootPath("**.find { it.name() == 'TaxCode'}")
			.body("**.find { it.name() == 'Country'}.UniqueName", equalTo("France"))
			.body("UniqueName", equalTo("I1"));
	}

	/**
	 * CARIBA-1105
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void aribaFullPrecisionRoundingTest( )
	{
		String respFileName = "AribaResponseNumericValuesFormatTest";

		apiUtil.requestData.setNumberOfItems(1);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemInformationForRequisition(1, 1, 1000, false);
		Response response = apiUtil.sendXMLRequest("default");

		String response1 = response.asPrettyString();
		aribaSoapResponse.writeXmlResponseToFile(response1, respFileName);

		data.validateResponseForCategoryTaxTypePercentTaxAmtForSingleLineRequisition(respFileName, "SELLER_USE", "SalesTaxState","8.875","88.8");
	}
}