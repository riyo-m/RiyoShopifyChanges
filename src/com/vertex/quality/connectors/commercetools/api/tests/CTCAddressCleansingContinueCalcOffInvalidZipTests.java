package com.vertex.quality.connectors.commercetools.api.tests;

import com.vertex.quality.connectors.commercetools.common.utils.CommerceToolAPITestUtilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CSAPCT-281
 * Address Cleansing with Continue to Calc OFF Setting in Oseries with Invalid Zip - Shipping Address
 * With Following Setting in Oseries
 * Continue IF Address Cleansing Unavailable is: OFF**  && Continue if address fails to cleanse OFF
 *
 * @author Mohit.Nirwan
 */

public class CTCAddressCleansingContinueCalcOffInvalidZipTests extends CommerceToolAPITestUtilities {


    /**
     * CSAPCT-284
     * test for address cleaning with calc off setting
     */
    @Test(groups = { "commercetools_api_regression" })
    public void addressCleansingContinueCalcOffInvalidZipTest() {
        addressCleanseSettingsOseries();
        final String message = "No tax areas were found during the lookup";
        String streetAddress1 = "2473 Hackworth Road apt 2", streetAddress2 = "", city = "Chester", mainDivision = "AL",
                subDivision = "", postalCode = "99999", country = "US";
        Response response = addressCleansingRequest(streetAddress1, streetAddress2, city, mainDivision,
                subDivision, postalCode, country);
        JsonPath extractor = response.jsonPath();
        cleanUpAddressCleanseSettings();
        String healthStatus = extractor.get("message");
        Assert.assertTrue(healthStatus.contains(message));
    }
}
