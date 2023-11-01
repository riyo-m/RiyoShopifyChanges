package com.vertex.quality.connectors.tradeshift.tests.api.base.keywords;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import static io.restassured.RestAssured.given;

public class TSUtilities extends VertexAPITestUtilities {
    protected String TENANT_ID = "a0cddd0f-aac0-4abe-ad2d-289c1aa862bb";
    protected String signInUsername;
    protected String signInPassword;
    protected String tradeshiftUrl;
    protected String environmentURL;
    protected EnvironmentInformation TradeshiftEnvironment;
    protected EnvironmentCredentials TradeshiftCredentials;
    protected OSeriesConfiguration oSeriesConfiguration;
    public String TRADESHIFT_TAX_LOOKUP_URL = null;
    public String oseriesURL = null;

    public String getTENANT_ID(){
        return TENANT_ID;
    }

    /**
     * Generates access token from Tradeshift
     *
     * @return oseriesURL
     */
    public String getOseriesInstance() {
        DBConnectorNames OSERIES_VAR = SQLConnection.getOSeriesDefaults();
        try {
            oSeriesConfiguration = SQLConnection.getOSeriesConfiguration(OSERIES_VAR);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TRADESHIFT_TAX_LOOKUP_URL = oSeriesConfiguration.getAddressServiceUrl();
        oseriesURL = TRADESHIFT_TAX_LOOKUP_URL.split("services")[0];

        return oseriesURL;
    }
    /**
     * Generates access token from Tradeshift
     *
     * @return token
     */
    public String createAccessToken() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response response = given().header("Content-Type","text/plain").header("Accept","*/*").header("Content-Type","application/json;charset=UTF-8")
                .body("{\"username\":\"testsuperuser\",\"password\":\"testsuperuserpw\"}").post("https://tradeshift.qa.vertexconnectors.com/testapi/accesstokens");

        response.then().statusCode(201);
        String token = response.body().path("token");

        System.out.println(token);

        return token;
    }

    /**
     * gets the current time and subtracts 1 hour
     * then formats to ensure we can get the correct invoice from the api
     *
     * @return the properly formatted date and time minus 1 hour
     */
    public String getCurrentDateTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime currentDayAndTime = LocalDateTime.now();
        String dayAndTimeMinus1h = dateFormatter.format(currentDayAndTime.minusHours(1));
        return dayAndTimeMinus1h;
    }

    /**
     * Generates a random 7 digit string to be used for the
     * invoice number since they have to be unique
     *
     * @return the random invoice number for the invoice number field
     * */
    public static String randomNumber(int length){
        int INVOICE_NUM_LENGTH = length;
        Random num = new Random();
        StringBuilder number = new StringBuilder();
        while(number.length() < INVOICE_NUM_LENGTH){
            number.append(num.nextInt(10));
        }
        return number.toString();
    }

    /**
     * Generates an invoice number for a
     * tradeshift request
     *
     * */
    public String generateInvoiceNumber(){
        StringBuilder invoiceNumber = new StringBuilder();
        invoiceNumber.append(randomNumber(8));
        invoiceNumber.append('-');
        invoiceNumber.append(randomNumber(4));
        invoiceNumber.append('-');
        invoiceNumber.append(randomNumber(4));
        invoiceNumber.append('-');
        invoiceNumber.append(randomNumber(4));
        invoiceNumber.append('-');
        invoiceNumber.append(randomNumber(12));
        return invoiceNumber.toString();
    }

	/**
	 * Changes the invoice number for a
	 * provided invoice
	 *
	 * @param invoiceNum the new invoice number
	 * @param invoiceJSONFile the File to be changed
	 * */
	@SneakyThrows
	public void changeInvoiceNumber(String invoiceNum, File invoiceJSONFile)
	{
		ObjectMapper mapper = new ObjectMapper();
		String key = "DocumentId";

		Map<String,String> invoiceMap = new HashMap<>();
		invoiceMap.put(key,invoiceNum);

		JSONObject newInvoiceJSON = new JSONObject(invoiceMap);
		JSONObject originalInvoiceJSON = mapper.readValue(invoiceJSONFile, JSONObject.class);

		originalInvoiceJSON.put(key,newInvoiceJSON.getAsString(key));

		//Write into the file
		try ( FileWriter file = new FileWriter(invoiceJSONFile))
		{
			file.write(originalInvoiceJSON.toString());
			System.out.println("Successfully updated json object to file...!!");
		}

	}

}
