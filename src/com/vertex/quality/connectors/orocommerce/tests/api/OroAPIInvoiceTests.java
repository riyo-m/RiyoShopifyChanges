package com.vertex.quality.connectors.orocommerce.tests.api;

import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import org.json.JSONObject;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

/**
 * Oro Commerce API invoice tests
 *
 * @author alewis
 */
public class OroAPIInvoiceTests extends OroAPIBaseTest{

    /**
     * COROCOM-569
     */
    @Test(groups = { "Oro_API" })
    public void diffBillAndShipTest() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().auth().oauth2(getOroToken()).header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body("{\n" +
                        "  \"data\": {\n" +
                        "    \"type\": \"orders\",\n" +
                        "    \"relationships\": {\n" +
                        "      \"billingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "         \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"billing1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"shippingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "            \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"shipping1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "         \"lineItems\": {\n" +
                        "     \"data\": [\n" +
                        "     {\n" +
                        "      \"type\": \"orderlineitems\",\n" +
                        "      \"id\": \"item1\"\n" +
                        "     }\n" +
                        "     ]\n" +
                        "    }\n" +
                        "   }\n" +
                        "  },\n" +
                        "  \"included\": [\n" +
                        "   {\n" +
                        "    \"type\": \"orderlineitems\",\n" +
                        "    \"id\": \"item1\",\n" +
                        "    \"attributes\": {\n" +
                        "     \"quantity\": \"5\"\n" +
                        "    },\n" +
                        "    \"relationships\": {\n" +
                        "     \"product\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"products\",\n" +
                        "       \"id\": \"24\"\n" +
                        "      }\n" +
                        "     },\n" +
                        "     \"productUnit\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"productunits\",\n" +
                        "       \"id\": \"item\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"billing1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"20\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"shipping1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"21\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("13.6800"));
    }

    /**
     * COROCOM-575
     */
    @Test(groups = { "Oro_API" })
    public void noStateTaxTest() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().auth().oauth2(getOroToken()).header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body("{\n" +
                        "  \"data\": {\n" +
                        "    \"type\": \"orders\",\n" +
                        "    \"relationships\": {\n" +
                        "      \"billingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "         \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"billing1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"shippingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "            \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"shipping1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "         \"lineItems\": {\n" +
                        "     \"data\": [\n" +
                        "     {\n" +
                        "      \"type\": \"orderlineitems\",\n" +
                        "      \"id\": \"item1\"\n" +
                        "     }\n" +
                        "     ]\n" +
                        "    }\n" +
                        "   }\n" +
                        "  },\n" +
                        "  \"included\": [\n" +
                        "   {\n" +
                        "    \"type\": \"orderlineitems\",\n" +
                        "    \"id\": \"item1\",\n" +
                        "    \"attributes\": {\n" +
                        "     \"quantity\": \"5\"\n" +
                        "    },\n" +
                        "    \"relationships\": {\n" +
                        "     \"product\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"products\",\n" +
                        "       \"id\": \"24\"\n" +
                        "      }\n" +
                        "     },\n" +
                        "     \"productUnit\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"productunits\",\n" +
                        "       \"id\": \"item\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"billing1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"20\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"shipping1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"20\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("0.0000"));
    }

    /**
     * COROCOM-576
     */
    @Test(groups = { "Oro_API" })
    public void NoStateTaxOnlyLocalSalesTest() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().auth().oauth2(getOroToken()).header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body("{\n" +
                        "  \"data\": {\n" +
                        "    \"type\": \"orders\",\n" +
                        "    \"relationships\": {\n" +
                        "      \"billingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "         \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"billing1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"shippingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "            \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"shipping1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "         \"lineItems\": {\n" +
                        "     \"data\": [\n" +
                        "     {\n" +
                        "      \"type\": \"orderlineitems\",\n" +
                        "      \"id\": \"item1\"\n" +
                        "     }\n" +
                        "     ]\n" +
                        "    }\n" +
                        "   }\n" +
                        "  },\n" +
                        "  \"included\": [\n" +
                        "   {\n" +
                        "    \"type\": \"orderlineitems\",\n" +
                        "    \"id\": \"item1\",\n" +
                        "    \"attributes\": {\n" +
                        "     \"quantity\": \"5\"\n" +
                        "    },\n" +
                        "    \"relationships\": {\n" +
                        "     \"product\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"products\",\n" +
                        "       \"id\": \"24\"\n" +
                        "      }\n" +
                        "     },\n" +
                        "     \"productUnit\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"productunits\",\n" +
                        "       \"id\": \"item\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"billing1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"23\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"shipping1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"23\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("7.2000"));
    }

    /**
     * COROCOM-631
     */
    @Test(groups = { "Oro_API" })
    public void orderWithTenItemsTest() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().auth().oauth2(getOroToken()).header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body("{\n" +
                        "  \"data\": {\n" +
                        "    \"type\": \"orders\",\n" +
                        "    \"relationships\": {\n" +
                        "      \"billingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "         \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"billing1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"shippingAddress\": {\n" +
                        "        \"data\": {\n" +
                        "            \"type\": \"orderaddresses\",\n" +
                        "            \"id\": \"shipping1\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "         \"lineItems\": {\n" +
                        "     \"data\": [\n" +
                        "     {\n" +
                        "      \"type\": \"orderlineitems\",\n" +
                        "      \"id\": \"item1\"\n" +
                        "     }\n" +
                        "     ]\n" +
                        "    }\n" +
                        "   }\n" +
                        "  },\n" +
                        "  \"included\": [\n" +
                        "   {\n" +
                        "    \"type\": \"orderlineitems\",\n" +
                        "    \"id\": \"item1\",\n" +
                        "    \"attributes\": {\n" +
                        "     \"quantity\": \"10\"\n" +
                        "    },\n" +
                        "    \"relationships\": {\n" +
                        "     \"product\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"products\",\n" +
                        "       \"id\": \"24\"\n" +
                        "      }\n" +
                        "     },\n" +
                        "     \"productUnit\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"productunits\",\n" +
                        "       \"id\": \"item\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"billing1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"25\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   },\n" +
                        "   {\n" +
                        "    \"type\": \"orderaddresses\",\n" +
                        "    \"id\": \"shipping1\",\n" +
                        "    \"relationships\": {\n" +
                        "     \"customerUserAddress\": {\n" +
                        "      \"data\": {\n" +
                        "       \"type\": \"customeruseraddresses\",\n" +
                        "       \"id\": \"25\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("27.3500"));
    }


}