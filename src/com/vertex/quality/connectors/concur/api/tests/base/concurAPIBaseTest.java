package com.vertex.quality.connectors.concur.api.tests.base;

        import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
        import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
        import io.restassured.response.Response;
        import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;

/**
 * class containing methods to interact with connector and concur
 */
public class concurAPIBaseTest extends VertexAPITestUtilities {

    /**
     * method to submit the invoice
     * checks status code
     */
    public String concurInvoice(String countryCode, String currencyCode, String invoiceAmount, String invoiceDate,
                                String invoiceNumber, String invoiceName, String companyShipToAddressCode,
                                String vendorCode, String expenseTypeCode, String expenseDecription, String quantity,
                                String unitPrice, String shipToPostalCode)
    {
        createConnectorAccessToken();
        Response response = given().auth().oauth2(getConcurToken()).contentType("application/json").body("{\n" +
                "    \"EmployeeEmailAddress\": \"User1@Vertex-connect-pro.com\",\n" +
                "    \"EmployeeLoginId\": \"User1@Vertex-connect-pro.com\",\n" +
                "    \"CountryCode\": \"" + countryCode + "\",\n" +
                "    \"CurrencyCode\": \"" + currencyCode + "\",\n" +
                "    \"InvoiceAmount\": \"" + invoiceAmount + "\",\n" +
                "    \"InvoiceDate\": \"" + invoiceDate + "\",\n" +
                "    \"InvoiceNumber\": \"" + invoiceNumber + "\",\n" +
                "    \"Name\": \"" + invoiceName + "\",\n" +
                "    \"CompanyShipToAddressCode\": \"" + companyShipToAddressCode + "\",\n" +
                "    \"VendorRemitToIdentifier\": {\n" +
                "        \"VendorCode\": \"" + vendorCode + "\"\n" +
                "    },\n" +
                "    \"OrgUnit1\": \"Best Buy\",\n" +
                "    \"OrgUnit2\": \"12\",\n" +
                "    \"LineItems\": [\n" +
                "        {\n" +
                "            \"ExpenseTypeCode\": \"" + expenseTypeCode + "\",\n" +
                "            \"Description\": \"" + expenseDecription + "\",\n" +
                "            \"Quantity\": \"" + quantity + "\",\n" +
                "            \"UnitPrice\": \"" + unitPrice + "\",\n" +
                "            \"ShipToPostalCode\": \"" + shipToPostalCode + "\",\n"+
                "            \"Allocations\": [\n" +
                "                {\n" +
                "                    \"Percentage\": \"60\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Percentage\": \"40\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}").post("https://us.api.concursolutions.com/api/v3.0/invoice/paymentrequest");
        System.out.println(response.body().toString());
        response.then().statusCode(200); //don't need
        String id = response.body().path("Response.ID");

        return id;
    }

    /**
     * method to check for correct tax value
     */
    public void checkForCorrectTax(String  id, String correctTax) {
        Response response = given().auth().oauth2(getConcurToken()).get("https://us.api.concursolutions.com/api/v3.0/invoice/salestaxvalidationrequest?limit=1");
        response.then().statusCode(200);
        ValidatableResponse validatableResponse = response.then().body("Invoices.Items.Invoice.Tax", hasToString(correctTax));
    }

    /**
     * login to connector to get cookie. Used to access all other apis in connector
     */
    public String createConnectorAccessToken() {
        Response responseToken = given().contentType("application/json").body("{\n" +
                "\t\"username\": \"concur\",\n" +
                "\t\"password\": \"concurPW1\"\n" +
                "}").post("https://concur.dev.vertexconnectors.com/api/accesstokens");

        responseToken.getCookie("concurUserSessionId");
        responseToken.then().statusCode(201);

        String token = responseToken.body().path("token");

        return token;
    }

    /**
     * Gets the access token used to make calls to concur
     */
    public String getConcurToken() {
        Response responseToken = given().contentType("application/json").body("{\n" +
                "\t\"username\": \"c7dd6b2d-f8de-45e3-a8a2-dbff6a8a32b1\",\n" +
                "\t\"password\": \"at-2nqhncf244dn09ci30xtdhz2igd6h9\",\n" +
                "\t\"trustedId\": \"$Concur\"\n" +
                "}")
                .post("https://concur.dev.vertexconnectors.com/api/tokens");
        responseToken.getCookie("concurUserSessionId");
        responseToken.then().statusCode(200);

        String accessToken = responseToken.body().path("access_token");
        System.out.println(accessToken);

        return accessToken;
    }

    /**
     * Gets the access token used to make calls to concur
     */
    public void getConcurTokenPeriodTrustedID() {
        Response responseToken = given().contentType("application/json").body("{\n" +
                "\t\"username\": \"c7dd6b2d-f8de-45e3-a8a2-dbff6a8a32b1\",\n" +
                "\t\"password\": \"at-pmsdze6ogzq2bao3hpal3c300b\",\n" +
                "\t\"trustedId\": \"$tr.nTVL5dFF\"\n" +
                "}")
                .post("https://concur.dev.vertexconnectors.com/api/tokens");
        responseToken.getCookie("concurUserSessionId");
        responseToken.then().statusCode(200);

        String accessToken = responseToken.body().path("access_token");
        System.out.println(accessToken);
    }

    /**
     * Runs the batch job to process invoices and make tax call to Vertex
     */
    public static String runBatchJob() {
        Response response = given().post("https://concur.dev.vertexconnectors.com/api/jobs/invoices");

        response.then().statusCode(200);
        //Doing this because it takes 5 seconds for the batch job to process the invoices
        try
        {
            Thread.sleep(6000);
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }

        String batchID = response.body().path("id");

        return batchID;
    }

    /**
     * Runs the batch job to process invoices and make tax call to Vertex
     */
    public static String runBatchJob(String token) {
        Response response = given().header("Cookie","concurUserSessionId=" + token).post("https://concur.dev.vertexconnectors.com/api/jobs/invoices");

        response.then().statusCode(200);
        //Doing this because it takes 5 seconds for the batch job to process the invoices
        try
        {
            Thread.sleep(6000);
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }

        int batchID = response.body().path("id");

        String batchIDString = Integer.toString(batchID);

        return batchIDString;
    }

    public String getJobStatus(String batchID,String token) {
        try
        {
            Thread.sleep(100000); //Job takes slightly over 1 minute to process
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        Response response = given().header("Cookie","concurUserSessionId=" + token).get("https://concur.dev.vertexconnectors.com/api/jobs/" + batchID);
        response.then().statusCode(200);
        ValidatableResponse validatableResponse = response.then().body("status", hasToString("COMPLETED"));

        return "got here";
    }

}