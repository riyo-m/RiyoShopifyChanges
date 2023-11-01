package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * CSAPCT-281 Validate Sales Order with Address Cleansing Continue to Calc OFF with Invalid Zip
 *  With Following Setting in Oseries
 *  Continue IF Address Cleansing Unavailable is: OFF**  && Continue if address fails to cleanse OFF
 *
 *  @author Mohit.Nirwan
 */

public class CTCValidateSalesOrderWithAddressCleansingCalcOffInvalidZipTests extends CommerceToolAPITestUtilities
{
    /**
     * CSAPCT-288
     * validating sales order with address cleansing settings from oseries turned off
     */
    @Test(groups = { "commercetools_api_regression" })
    public void validateSalesOrderWithAddressCleansingCalcOffInvalidZipTest(){

        final String message = "No tax areas were found during the lookup";
        validateToken();
        addressCleanseSettingsOseries();
        Response response = createCartRequest(commerceToolsAccessToken);
        JsonPath extractor = response.jsonPath();
        String cartId = extractor.get("id");
        Integer versionString = extractor.get("version");
        Long version = Long.valueOf(versionString);
        String streetName = "Hackworth Road apt 2", streetNumber = "2473", postal = "99999", Shipping_City = "Chester", Shipping_State = "AL", Shipping_Country = "US";
        Response cartResponse = setShippingAddressRequest(commerceToolsAccessToken, cartId, version, streetName, streetNumber,
                postal, Shipping_City, Shipping_State, Shipping_Country);
        JsonPath extractor1 = cartResponse.jsonPath();
        String healthStatus = extractor1.get("message");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(healthStatus.contains(message));
        cleanUpAddressCleanseSettings();
        softAssert.assertAll();
    }

}
