package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * CSAPCT-281 Address Cleansing OFF With Following Settings in CommerceTools
 * Address Cleansing Toggle Off
 *
 * @author Mohit.Nirwan
 */

public class CTCShipToAddressCleansingOffTests extends CommerceToolAPITestUtilities {


    /**
     * CSAPCT-291
     * test for validating address cleansing, with invalid state and zip
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressCleansingOffInvalidZipRequestTest( )
    {
        addresssCleansingTurnOffCT();
        final String message = "No tax areas were found during the lookup";
        String streetAddress1 = "2955 Market St.", streetAddress2 = "", city = "Philadelphia",mainDivision ="DE",
                subDivision ="",postalCode ="99999", country ="US";
        Response response = addressCleansingRequest(streetAddress1,streetAddress2,city,mainDivision,
                subDivision,postalCode,country);
        JsonPath extractor = response.jsonPath();
        String healthStatus = extractor.get("message");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(healthStatus.contains(message));
        addresssCleansingTurnOnCT();
        softAssert.assertAll();
    }

    /**
     * CSAPCT-290
     * test for address cleansing, with invalid city and zip
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressCleansingInvalidCityZipRequestTest( )
    {
        addresssCleansingTurnOffCT();
        final String message = "No tax areas were found during the lookup";
        String streetAddress1 = "Market St. ", streetAddress2 = "2473", city = "Chester", mainDivision = "PA",
                subDivision = "", postalCode = "99999 ", country = "US";
        Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision, subDivision,
                postalCode, country);
        JsonPath extractor = response.jsonPath();
        String healthStatus = extractor.get("message");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(healthStatus.contains(message));
        addresssCleansingTurnOnCT();
        softAssert.assertAll();
    }

    /**
     * CSAPCT-289
     * test for address cleansing with invalid zip
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressCleansingInvalidZipRequestTest( )
    {
        addresssCleansingTurnOffCT();
        final String message = "No tax areas were found during the lookup";
        String streetAddress1 = "2955 Market St.", streetAddress2 = "", city = "Philadelphia",mainDivision ="PA",
                subDivision ="",postalCode ="99999", country ="US";
        Response response = addressCleansingRequest(streetAddress1,streetAddress2,city,mainDivision,
                subDivision,postalCode,country);
        JsonPath extractor = response.jsonPath();
        String healthStatus = extractor.get("message");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(healthStatus.contains(message));
        addresssCleansingTurnOnCT();
        softAssert.assertAll();
    }

    /**
     * CSAPCT-292
     * test for address cleansing with no zip
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressCleansingNoZipRequestTest( )
    {
        addresssCleansingTurnOffCT();
        String streetAddress1 = "2955 Market St.", streetAddress2 = "", city = "Philadelphia", mainDivision = "PA",
                subDivision = "", postalCode = "", country = "US";
        Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision, subDivision,
                postalCode, country);
        assertStatus(response, ResponseCodes.SUCCESS);
        addresssCleansingTurnOnCT();
    }

    /**
     * CSAPCT-285
     * validating address cleansing with address cleansing toggle off from CommerceTools
     */
    @Test(groups = { "commercetools_api_regression" })
    public void validateAddressWIthAddressCleansingOffTest(){
        addresssCleansingTurnOffCT();
        String streetAddress1 = "2473 Hackworth Road apt 2", streetAddress2 = "", city = "Chester",mainDivision ="AL",
                subDivision ="",postalCode ="", country ="US";
        Response response = addressCleansingRequest(streetAddress1,streetAddress2,city,mainDivision,
                subDivision,postalCode,country);
        assertStatus(response, ResponseCodes.SUCCESS);
        addresssCleansingTurnOnCT();
    }

}
