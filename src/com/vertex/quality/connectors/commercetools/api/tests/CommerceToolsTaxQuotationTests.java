package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-203...CCT- Validate the Tax Calculation Endpoint for Quotation.
 * this test case sends a generate Token request and then get's the token from the response.
 * then sends Create Cart request for the same token and generate cartId and version.
 * sends setShipping address request for setting Shipping To address for same cartId and version.
 * sends addLineItem request for same cartId and version.
 *
 * @author vivek_kumar
 */

public class CommerceToolsTaxQuotationTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for quotation request.
	 */
	@Test(groups = { "commercetools_api_smoke" })
	public void createQuotationRequestTest( )
	{
		validateToken();
		Response response = createCartRequest(commerceToolsAccessToken);

		JsonPath extractor = response.jsonPath();
		String cartId = extractor.get("id");
		Integer versionString = extractor.get("version");
		Long version = Long.valueOf(versionString);

		String streetName = "Boulevard Laurier G12a", streetNumber = "2450", postal = "G1V 2L1", Shipping_City
			= "Quebec City", Shipping_State = "", Shipping_Country = "CA";
		Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
			postal, Shipping_City, Shipping_State, Shipping_Country);
		JsonPath extractor1 = cartResponse.jsonPath();
		Integer versionString1 = extractor1.get("version");
		try
		{
			Long version1 = Long.valueOf(versionString1);
			long quantity = 1;

			Response addLineResponse = addLineItemRequest(commerceToolsAccessToken, cartId, version1, quantity);
			assertStatus(addLineResponse, ResponseCodes.SUCCESS);
		}catch (Exception e)
		{
			Assert.assertTrue(false,"Extension time out 504 ");
		}

	}
}
