package com.vertex.quality.connectors.ariba.api.tests.other;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * represents all the negative test cases
 *
 * @author osabha
 */
public class AribaAPINegativeTests extends AribaAPIBaseTest
{
	@Override
	protected AribaAPIType getDefaultAPIType( ) { return null; }

	@Override
	protected AribaAPIRequestType getDefaultAPIRequestType( ) { return null; }
	/**
	 * sends an xml request with mismatching request type and Type fields values.
	 * CARIBA-687
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void mismatchingTypeAndRequestTypeTest( )
	{
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Philadelphia", "US", null, null, null,
			"19107", "PA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_FROM, "King of Prussia", "US", null, null, null,
			"19406", "PA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Berwyn", "US", null, null, null, "19312",
			"PA");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "Berwyn", "US", null, null, null, "19312",
			"PA");
		apiUtil.requestData.setTypeField(AribaAPIType.REQUISITION);
		apiUtil.requestData.setRequestTypeField(AribaAPIRequestType.ERP_POSTING);
		Response response = apiUtil.sendXMLRequest("default");

		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, false);
		assertTrue(isResponseCorrect);
	}

	/**
	 * CARIBA-900
	 * tests the connector behavior for messages with unregistered tenant name
	 * expected behavior to default to the Default tenant account.
	 * asserting for the success response true.
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void wrongTenantIdTest( )
	{
		final String wrongTenantName = "WrongTenantName";
		apiUtil.requestData.setTenantField(wrongTenantName);
		apiUtil.requestData.setPartitionField(wrongTenantName);
		apiUtil.requestData.setTypeField(AribaAPIType.REQUISITION);
		apiUtil.requestData.setRequestTypeField(AribaAPIRequestType.TAX_CALCULATION);
		Response response = apiUtil.sendXMLRequest();
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);
	}

	/**
	 * Tests to see if using a specified, non-existing tenant, it should fall back to using the default tenant
	 * configurations in order
	 * for the request to go through
	 * however it still reports back a response with the same tenant id used in the request.
	 * CARIBA-429
	 */
	@Test(groups = { "ariba_api","ariba_smoke","ariba_regression" })
	public void useDefaultIfSpecifiedTenantIsNotDefinedTest( )
	{
		String testValue = "nonexistant_tenant";

		apiUtil.requestData.setTenantField(testValue);
		apiUtil.requestData.setPartitionField(testValue);
		apiUtil.requestData.setTypeField(AribaAPIType.REQUISITION);
		apiUtil.requestData.setRequestTypeField(AribaAPIRequestType.TAX_CALCULATION);
		Response response = apiUtil.sendXMLRequest();
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response
				.then()
				.statusCode(200)
				.body("**.find { it.name() == 'TaxServiceExportReply' }.@variant", equalTo("nonexistant_tenant"));
	}
}