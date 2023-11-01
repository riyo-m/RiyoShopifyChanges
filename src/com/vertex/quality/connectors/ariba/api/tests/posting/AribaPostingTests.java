package com.vertex.quality.connectors.ariba.api.tests.posting;

import com.vertex.quality.connectors.ariba.api.enums.AribaAddresses;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIPostingBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * @author osabha, vgosavi
 */
public class AribaPostingTests extends AribaAPIPostingBaseTest
{
	/**
	 * CARIBA-646
	 * WE DONT ACCRUE IF THEY PICK THE VENDOR
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void underTaxedAcceptVendorTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_PA_LANCASTER, 1);
		setItemShipToAddress(AribaAddresses.US_PA_LANCASTER, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_QUEBEC, 1);

		setItemBillingAddresses(AribaAddresses.US_PA_LANCASTER, 2);
		setItemShipToAddress(AribaAddresses.US_PA_LANCASTER, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_QUEBEC, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "5.00", false, "5.00", "SalesTax");

		apiUtil.requestData.setItemNumberOfTaxItems(1, 1);
		String documentID = apiUtil.getDocumentId();
		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("SELLER_USE"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("SalesTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("6"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("5.0"));
	}

	/**
	 * CARIBA-648
	 * checking there is a vat memo
	 * notice the vat memo, only concerned with vat tax types involved
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void overTaxedAcceptVendorTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "50.00", false, "50.00", "GSTTax");
		apiUtil.requestData.setItemNumberOfTaxItems(1, 1);

		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("VAT"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("GSTTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("50"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("50.0"));
	}

	/**
	 * CARIBA-1036
	 * sending a posting call for a reconciled invoice where we picked vertex
	 * taxes for an underTaxed invoice
	 * we should see an accrual call, but no value because the scenario is VAT
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void underVatTaxedPickVertexTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "15.00", false, "10.00", "GSTTax");
		apiUtil.requestData.setItemNumberOfTaxItems(1, 1);

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("VAT"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("GSTTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("10"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("10.0"));
	}

	/**
	 * CARIBA-1019
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void vatMemoTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 1);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 1);

		setItemBillingAddresses(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipToAddress(AribaAddresses.CANADA_NEW_BRUNSWICK, 2);
		setItemShipFromAddress(AribaAddresses.CANADA_BRITISH_COLUMBIA, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "15.00", false, "10.00", "GSTTax");
		apiUtil.requestData.setItemNumberOfTaxItems(1, 1);

		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("VAT"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("GSTTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("10"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("10.0"));
	}

	/**
	 * CARIBA-1029
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void fullAccrualCodeTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 1);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 2);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "6.00", false, null, "SalesTax");

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("CONSUMERS_USE"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("SalesTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("6"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("6.0"));
	}

	/**
	 * CARIBA-1030
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void partialAccrualCodeTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 1);

		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 2);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "6.00", false, "3.00", "SalesTax");

		apiUtil.requestData.setItemNumberOfTaxItems(1, 1);
		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("P1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("CONSUMERS_USE"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("SalesTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("6"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("3.0"));
	}

	/**
	 * CARIBA-1034
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void noAccrualValueCodeTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 1);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 2);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "6.00", false, "6.00", "SalesTax");

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("SALES"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("SalesTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("6"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("6.0"));
	}

	/**
	 * CARIBA-1035
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void fullAccrualWithSupplierZeroTaxTest( )
	{
		apiUtil.requestData.addIsRetryField(false);
		apiUtil.requestData.setNumberOfItems(2);
		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 1);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 1);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 1);

		setItemBillingAddresses(AribaAddresses.US_NY_NEW_YORK, 2);
		setItemShipToAddress(AribaAddresses.US_PA_BERWYN, 2);
		setItemShipFromAddress(AribaAddresses.US_PA_KOP, 2);

		setCatalogItemInformationForPosting(1, 1, 100, false, 0);
		apiUtil.requestData.setTaxItemInformationForPosting(2, 1, "6.00", false, "0.0", "SalesTax");

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
		response
			.then()
			.assertThat()
			.body("**.find { it.name() == 'TaxCode' }.UniqueName", equalTo("I1"))
			.and()
			.body("**.find { it.name() == 'Success' }", equalTo("true"))
			.and()
			.body("**.find { it.name() == 'Category' }", equalTo("CONSUMERS_USE"))
			.and()
			.body("**.find { it.name() == 'ExternalTaxType' }.UniqueName", equalTo("SalesTax"))
			.and()
			.body("**.find { it.name() == 'Percent' }", equalTo("6"))
			.and()
			.body("**.find { it.name() == 'TaxAmount' }.Amount", equalTo("6.0"));
	}
}