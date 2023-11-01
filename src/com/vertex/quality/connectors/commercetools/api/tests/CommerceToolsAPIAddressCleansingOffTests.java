package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * CSAPCT-229...Test Case - CCT- Validate addresses with address cleansing OFF - Administrativel Origin
 * this test case performs address cleansing on Administrative Origin(BILL_FROM).
 * we pass address information with invalid zip.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPIAddressCleansingOffTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for creating address cleansing request.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void addressCleansingOffRequestTest( )
	{
		createDriver();
		CommerceToolsAddressCleanseTest();
		String streetAddress1 = " Hackworth Road apt 2", streetAddress2 = "2473", city = "Birmingham", mainDivision
			= "AL", subDivision = "", postalCode = "", country = "US";
		Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision, subDivision,
			postalCode, country);
		assertStatus(response, ResponseCodes.SUCCESS);
	}
}
