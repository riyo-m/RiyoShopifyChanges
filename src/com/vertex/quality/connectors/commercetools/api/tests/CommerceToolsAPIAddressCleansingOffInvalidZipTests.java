package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-226...Test Case - Address Cleansing OFF with Invalid Zip
 * this test case performs address cleansing on Administrative Origin(BILL_FROM).
 * we pass address information with invalid zip.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPIAddressCleansingOffInvalidZipTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for creating address cleansing request.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void addressCleansingInvalidZipRequestTest( )
	{
		createDriver();
		CommerceToolsAddressCleanseTest();
		final String message = "No tax areas were found during the lookup";
		String streetAddress1 = "Market St.", streetAddress2 = "2955", city = "Philadelphia",mainDivision ="PA",
			subDivision ="",postalCode ="99999", country ="US";
			Response response = addressCleansingRequest(streetAddress1,streetAddress2,city,mainDivision,
				subDivision,postalCode,country);
			assertStatus(response, ResponseCodes.SUCCESS);

		JsonPath extractor = response.jsonPath();
		String healthStatus = extractor.get("message");
		Assert.assertEquals(healthStatus, message);
	}
}
