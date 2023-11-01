package com.vertex.quality.connectors.ariba.api.tests.invoice;

import com.vertex.quality.connectors.ariba.api.enums.AribaAddresses;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIInvoiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * this class contains all the test cases for invoice verification requests
 * @author osabha
 */
public class AribaInvoicingTests extends AribaAPIInvoiceBaseTest
{
	/**
	 * sample test case that runs a happy path test,
	 * will be verifying this later, and associating with a jira ticket
	 * CARIBA-935
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void standardInvoiceVerificationTest( )
	{
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_BERWYN, 1);

		setItemBillingAddresses(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 2);
		setItemSupplierAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_BERWYN, 2);

		setItemInformationForReconciliation(1, 1, 100.00, false, 0);
		setItemInformationForReconciliation(2, 1, 14.35, false, 0);

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.statusCode(200)
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"));
	}

	/**
	 * create invoice and reconcile through payment attach OK to pay to payment
	 * based on Ariba Test: CAN_CNQCUSPA_LHL_Summarized_UnderTaxAcceptVendor
	 * CARIBA-961
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void CAN_CNQC_USPA_LL_Summarized_UnderTaxInvoiceTest( )
	{
		apiUtil.requestData.setNumberOfItems(3);
		setItemBillingAddresses(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_BERWYN, 1);

		setItemBillingAddresses(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 2);
		setItemSupplierAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_BERWYN, 2);

		setItemBillingAddresses(AribaAddresses.US_PA_BERWYN, 3);
		setItemShipToAddress(AribaAddresses.US_CA_LOS_ANGELES, 3);
		setItemSupplierAddress(AribaAddresses.US_PA_BERWYN, 3);
		setItemShipFromAddress(AribaAddresses.US_PA_BERWYN, 3);

		setItemInformationForReconciliation(1, 1, 100, false, 0 - 1);
		setItemInformationForReconciliation(2, 2, 25.5, false, -1);
		setItemInformationForReconciliation(3, 1, 5, true, -1);
		apiUtil.requestData.setItemLineType(3, "2", "SalesTax");

		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.statusCode(200)
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"));
	}

	/**
	 * CARIBA-648
	 * rate for new brunswick is 15 percent
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void purchaseOrderCAN_BC_CAN_ON_Test( )
	{
		apiUtil.requestData.setNumberOfItems(3);

		apiUtil.requestData.setDocumentSubType("standard");
		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 3);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 3);
		setItemSupplierAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 3);
		setItemShipFromAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 3);

		setItemInformationForReconciliation(1, 1, 100, false, -1);
		setItemInformationForReconciliation(2, 2, 25.5, false, -1);
		setTaxItemInformationForReconciliation(3, 1, 50, -1);

		apiUtil.requestData.setItemLineType(3, "2", "VATTax");
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response.then().statusCode(200)
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find{it.name()=='LineItems'}.item[0].ExternalTaxItem.item.TaxAmount.Amount", equalTo("15.0"))
			.and()
			.body("**.find{it.name()=='LineItems'}.item[1].ExternalTaxItem.item.TaxAmount.Amount", equalTo("7.6"));
	}

	/**
	 * CARIBA-649
	 * Rate for Ontario is 13 percent
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void invoiceCAN_BC_CAN_ON_Test( )
	{
		apiUtil.requestData.setNumberOfItems(3);

		apiUtil.requestData.setDocumentSubType("standard");
		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 1);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 2);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 3);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 3);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 3);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 3);

		setItemInformationForReconciliation(1, 1, 100, false, -1);
		setItemInformationForReconciliation(2, 2, 25.5, false, -1);
		setTaxItemInformationForReconciliation(3, 1, 11, -1);

		apiUtil.requestData.setItemLineType(3, "2", "GSTTax");
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response.then().statusCode(200)
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find{it.name()=='LineItems'}.item[0].ExternalTaxItem.item.TaxAmount.Amount", equalTo("13.0"))
			.and()
			.body("**.find{it.name()=='LineItems'}.item[1].ExternalTaxItem.item.TaxAmount.Amount", equalTo("6.6"));
	}

	/**
	 * This Test sends an Invoice Verification Request with three decimal places in the request
	 * the item prices are chosen to get two decimal places in oseries response
	 * the Ariba response should have two decimal places for it's reported values
	 * CARIBA-1009
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void mirroringDecimalPlacesTest( )
	{
		apiUtil.requestData.setNumberOfItems(3);

		apiUtil.requestData.setDocumentSubType("standard");
		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 1);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 1);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 2);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 2);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setItemBillingAddresses(AribaAddresses.CANADA_ONTARIO, 3);
		setItemShipToAddress(AribaAddresses.CANADA_ONTARIO, 3);
		setItemSupplierAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 3);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 3);

		setItemInformationForReconciliation(1, 1, 99.097, false, -1);
		setItemInformationForReconciliation(2, 2, 25.000, false, -1);
		setTaxItemInformationForReconciliation(3, 1, 11.000, -1);

		apiUtil.requestData.setItemLineType(3, "2", "GSTTax");
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response.then().statusCode(200)
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find{it.name()=='LineItems'}.item[0].ExternalTaxItem.item.TaxAmount.Amount", equalTo("12.88"));
		}

	//API LOG FIX REQUIRED
	/**
	 * sends an invoice reconciliation request with addresses from india
	 * CARIBA-1018
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void IndiaTaxCodesInvoiceTest( )
	{
		apiUtil.requestData.setNumberOfItems(2);

		apiUtil.requestData.setDocumentSubType("standard");
		setItemBillingAddresses(AribaAddresses.INDIA_PORT_BLAIR, 1);
		setItemShipToAddress(AribaAddresses.INDIA_PORT_BLAIR, 1);
		setItemSupplierAddress(AribaAddresses.INDIA_RAMDASPETH, 1);
		setItemShipFromAddress(AribaAddresses.INDIA_RAMDASPETH, 1);

		setItemBillingAddresses(AribaAddresses.INDIA_PORT_BLAIR, 2);
		setItemShipToAddress(AribaAddresses.INDIA_PORT_BLAIR, 2);
		setItemSupplierAddress(AribaAddresses.INDIA_RAMDASPETH, 2);
		setItemShipFromAddress(AribaAddresses.INDIA_RAMDASPETH, 2);

		setItemInformationForReconciliation(1, 1, 100, false, -1);
		setTaxItemInformationForReconciliation(2, 1, 11.000, 1);
		apiUtil.requestData.setItemLineType(2, "2", "GSTTax");
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.body("**.find{it.name()=='TaxAuthority'}", equalTo("INDIA"))
			.and()
			.body("**.find{it.name()=='ExternalTaxType'}.UniqueName", equalTo("GSTTax"))
			.and()
		    .body("**.find{it.name()=='TaxAmount'}.Amount", equalTo("18.0"));
	}

	//TODO create ticket when scenario is confirmed
	/**
	 * Validates tax on an invoice with 3 ta items
	 *
	 * Scenario 23/24
	 */
	@Test(groups = { "ariba_api","ariba_smoke", "ariba_regression" })
	public void multiLineItemTaxVerificationTest( )
	{
		apiUtil.requestData.setNumberOfItems(3);
		setItemBillingAddresses(AribaAddresses.US_PA_KOP, 1);
		setItemShipToAddress(AribaAddresses.US_PA_KOP, 1);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 1);

		setItemBillingAddresses(AribaAddresses.US_PA_KOP, 2);
		setItemShipToAddress(AribaAddresses.US_PA_KOP, 2);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 2);

		setItemBillingAddresses(AribaAddresses.US_PA_KOP, 3);
		setItemShipToAddress(AribaAddresses.US_PA_KOP, 3);
		setItemSupplierAddress(AribaAddresses.US_PA_KOP, 3);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 3);

		setItemInformationForRequisition(1,1,100,false);
		setItemInformationForRequisition(2,1,200,false);
		setItemInformationForRequisition(3,1,300,false);


		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		String responseString = response.body().asPrettyString();
		assertTrue(responseString.contains("<ns2:TaxAmount>\n" +
				"                    <ns2:Amount>6.0</ns2:Amount>"));
		assertTrue(responseString.contains("<ns2:TaxAmount>\n" +
				"                    <ns2:Amount>12.0</ns2:Amount>"));
		assertTrue(responseString.contains("<ns2:TaxAmount>\n" +
				"                    <ns2:Amount>18.0</ns2:Amount>"));
	}
}