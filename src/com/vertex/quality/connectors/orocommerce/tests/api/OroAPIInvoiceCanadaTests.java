package com.vertex.quality.connectors.orocommerce.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasToString;

/**
 * Oro Commerce API Canada invoice tests
 *
 * @author alewis
 */
public class OroAPIInvoiceCanadaTests extends OroAPIBaseTest {

    /**
     * COROCOM-579
     */
    @Test(groups = { "Oro_API" })
    public void CanadaToUSInvoiceTest() {

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
                        "       \"id\": \"14\"\n" +
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
                        "       \"id\": \"14\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("8.6400"));
    }

    /**
     * COROCOM-578
     */
    @Test(groups = { "Oro_API" })
    public void UStoCanadaInvoiceTest() {

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
                        "       \"id\": \"22\"\n" +
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
                        "       \"id\": \"22\"\n" +
                        "      }\n" +
                        "     }\n" +
                        "    }\n" +
                        "   }\n" +
                        "  ]\n" +
                        " }")
                .post("https://vertexdev.razoyo.com/api/orders");

        response.then().statusCode(201);
        ValidatableResponse validatableResponse = response.then().body("data.attributes.totalTaxAmount",hasToString("8.6400"));
    }

    /**
     * COROCOM-565
     */
    @Test(groups = { "Oro_API" })
    public void britishColumbiaToOntarioTest() {

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
                        "     \"quantity\": \"1\"\n" +
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
                        "       \"id\": \"26\"\n" +
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
                        "       \"id\": \"26\"\n" +
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

    /**
     * COROCOM-566
     */
    @Test(groups = { "Oro_API" })
    public void  britishColumbiaToQuebecTest() {

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
                        "     \"quantity\": \"1\"\n" +
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
                        "       \"id\": \"27\"\n" +
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
                        "       \"id\": \"27\"\n" +
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
     * COROCOM-582
     */
    @Test(groups = { "Oro_API" })
    public void quebecToBritishColumbiaTest() {

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
                        "     \"quantity\": \"1\"\n" +
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
                        "       \"id\": \"28\"\n" +
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
                        "       \"id\": \"28\"\n" +
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
     * COROCOM-580
     */
    @Test(groups = { "Oro_API" })
    public void newBrunswickInvoiceTest() {

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
                        "     \"quantity\": \"1\"\n" +
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
                        "       \"id\": \"29\"\n" +
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
                        "       \"id\": \"29\"\n" +
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
}
