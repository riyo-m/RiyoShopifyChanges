package com.vertex.quality.connectors.ariba.api.tests.invoice;

import com.vertex.quality.connectors.ariba.api.enums.AribaAddresses;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIInvoiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.testng.Assert.assertTrue;

/**
 * Tests the response for the external tax type for invoices
 *
 * @author hho osabha, vgosavi
 */

public class AribaInvoiceAPIExternalTaxTypeTests extends AribaAPIInvoiceBaseTest
{
	/**
	 * verifies that the connector throws an error when sending a completely wrong tax type with the invoice
	 * CARIBA-932
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void useTaxExternalTaxTypeTest( )
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
		setItemInformationForReconciliation(2, 1, 14.35, true, 0);
		apiUtil.requestData.setItemLineType(2, "2", "VATRCTax");
		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, false);
		assertTrue(isResponseCorrect);
	}

	/**
	 * Verifies that the connector throws an error if the tax type passed with the invoice
	 * is not recognized from the Ariba external tax types in the connector config
	 * CARIBA-931
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void canadianExternalTaxTypeTest( )
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
		setItemInformationForReconciliation(2, 1, 14.35, true, 0);
		apiUtil.requestData.setItemLineType(2, "2", "PSTCUT");
		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, false);
		assertTrue(isResponseCorrect);
	}

	/**
	 * Verifies that the tax types are mapped to same external tax type passed in the invoice
	 * CARIBA-933
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void USExternalTaxTypeTest( )
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
		setItemInformationForReconciliation(2, 1, 2, true, 0);
		apiUtil.requestData.setItemLineType(2, "2", "SalesTax");

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.findAll { it.name() == 'Category' }", equalTo("CONSUMERS_USE"))
			.body("**.findAll { it.name() == 'ExternalTaxType' }.UniqueName", hasItems("SalesTax"));
	}
}