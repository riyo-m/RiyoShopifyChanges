package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * CSAPCT-230...Test Case - CCT- Address Cleansing OFF with Invalid City and Zip
 * this test case performs address cleansing on Administrative Origin(BILL_FROM).
 * we pass address information with invalid zip.
 *
 * @author vivek_kumar
 */

public class CommerceToolsAPIAddressInvalidCityZipTests extends CommerceToolAPITestUtilities
{

	/**
	 * Test case for creating address cleansing request.
	 */
	@Test(groups = { "commercetools_api_regression" })
	public void addressCleansingInvalidCityZipRequestTest( )
	{
		createDriver();
		CommerceToolsAddressCleanseTest();
		String streetAddress1 = "Market St. ", streetAddress2 = "2473", city = "Chester", mainDivision = "PA",
			subDivision = "", postalCode = "99999 ", country = "US";
		Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision, subDivision,
			postalCode, country);
		assertStatus(response, ResponseCodes.SUCCESS);
	}
}
