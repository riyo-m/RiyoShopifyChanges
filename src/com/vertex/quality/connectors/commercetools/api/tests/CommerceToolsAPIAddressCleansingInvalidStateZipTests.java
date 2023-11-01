package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-231...Test Case - CCT- Address Cleansing OFF with Invalid State and Zip
 * this test case performs address cleansing on Administrative Origin(BILL_FROM).
 * we pass address information with invalid zip.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPIAddressCleansingInvalidStateZipTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for creating address cleansing request.
	 */


	@Test(groups = { "commercetools_api_regression" })
	public void addressCleansingInvalidStateZipRequestTest( )
	{
		createDriver();
		CommerceToolsAddressCleanseTest();
		final String message = "No Tax areas were found during the lookup";
		String streetAddress1 = "Market St. ", streetAddress2 = "2955", city = "Philadelphia", mainDivision = "DE",
			subDivision = "", postalCode = "99999", country = "US";
		Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision, subDivision,
			postalCode, country);
		JsonPath extractor = response.jsonPath();
		String healthStatus = extractor.get("message");
		Assert.assertEquals(healthStatus, message);
	}
}
