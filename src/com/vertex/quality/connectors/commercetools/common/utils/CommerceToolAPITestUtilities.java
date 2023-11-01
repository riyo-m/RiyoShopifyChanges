package com.vertex.quality.connectors.commercetools.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.bigcommerce.api.utils.BigCommerceConverter;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolEndpoint;
import com.vertex.quality.connectors.commercetools.api.enums.CommerceToolsProductID;
import com.vertex.quality.connectors.commercetools.api.pojos.*;
import com.vertex.quality.connectors.commercetools.ui.pages.*;
import com.vertex.quality.connectors.commercetools.ui.pages.oseries.OSeriesSettingsPage;
import com.vertex.quality.connectors.commercetools.ui.tests.oseries.OSeriesLoginTest;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * a utility class for api testing of commerce tool
 *
 * @author Vivek.Kumar
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommerceToolAPITestUtilities extends VertexAPITestUtilities {

    protected CommerceToolAPITestUtilities commerceToolUtil;

    private static ReadProperties readEnvUrls;
    private static ReadProperties readCredentials;
    private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
    private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;

    protected String COMMERCETOOLS_ADMIN_USERNAME;
    protected String COMMERCETOOLS_CLIENT_NAME;
    protected String COMMERCETOOLS_API_SCOPE;
    protected String COMMERCETOOLS_ADMIN_PASSWORD;
    protected String COMMERCETOOLS_ADMIN_URL;
    protected String COMMERCETOOLS_APPLICATION_URL;
    protected String COMMERCETOOLS_CUSTOMER_GROUP;
    protected String COMMERCETOOLS_CUSTOMER_PASSWORD;
    protected String COMMERCETOOLS_CUSTOMER_KEY;
    protected String COMMERCETOOLS_CUSTOMER_FIRSTNAME;
    protected String COMMERCETOOLS_CUSTOMER_LASTNAME;
    protected String COMMERCETOOLS_CUSTOMER_EMAIL;
    protected String COMMERCETOOLS_CUSTOMER_NUMBER;
    protected String COMMERCETOOLS_CUSTOMER_GROUP_VALUE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_TRUSTEDID;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_USERNAME;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_PASSWORD;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_ADDRESS_CLEANSING_ENDPOINT;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_TAX_SERVICE_ENDPOINT;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_COMPANY_CODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_ADDRESS1;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_CITY;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTRY_CODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTY_CODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_POSTALCODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTRY;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_ADDRESS1;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_CITY;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTRY_CODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTY_CODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_POSTALCODE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTRY;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_HOST_URL;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_ID;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_SECRET;


    CommerceToolsSignOnPage signOnPage;
    CommerceToolsVertexLogoPage vertexLogo;
    protected int expiresInTime;
    public String commerceToolsAccessToken;

    protected String COMMERCETOOL_ADMIN_CLIENTID;
    protected String COMMERCETOOL_ADMIN_CLIENTSECRET;
    protected String COMMERCETOOL_CART_VERSION;
    protected String COMMERCETOOL_URL;
    protected String COMMERCETOOL_AUTHURL;
    protected String COMMERCETOOL_ORDERNUMBER;
    protected String COMMERCETOOL_HOSTTAX;
    protected String COMMERCETOOL_ADMIN_PROJECTKEY;
    protected String COMMERCETOOL_AUTH_USERNAME;
    protected String COMMERCETOOL_AUTH_PASSWORD;
    protected String COMMERCETOOL_HOST;
    protected String COMMERCETOOL_CONF;
    protected String COMMERCETOOL_URL_ADDRESS_CLEANSING_HOST;
    protected String BEARER_TOKEN;
    protected String TAX_SERVICES;
    CommerceToolsCreateProjectPage projectPage;
    CommerceToolsProductTypePage productType;
    CommerceToolsProductPage productPage;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_ORGANIZATION;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PROJECTNAME;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_TYPE;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_TYPE_DESCRIPTION;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_NAME;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_DESCRIPTION;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_SKU;
    protected String TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_AMOUNT;

    public static final String ID_QUERY_GRANTTYPE = "grant_type";
    public static final String credentials = "client_credentials";
    public static final ContentType DEFAULT_CONTENT_TYPE = ContentType.JSON;
    protected BigCommerceConverter jsonConverter;

    public CommerceToolAPITestUtilities() {
        initializeVariables();
    }

    private void initializeVariables() {
        try {
            File testcredential = new File(TEST_CREDENTIALS_FILE_PATH);
            if (testcredential != null && testcredential.exists()) {
                readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
            } else {
                VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
                        EpiBaseTest.class);
            }

            File testPropFilePath = new File(ENV_PROP_FILE_PATH);
            if (testPropFilePath != null && testPropFilePath.exists()) {
                readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
            } else {
                VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
                        EpiBaseTest.class);
            }

            COMMERCETOOL_ADMIN_CLIENTID = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.ADMIN.CLIENTID");
            COMMERCETOOL_HOSTTAX = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.URL.HOSTTAX");
            COMMERCETOOL_HOST = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.URL.HOST");
            COMMERCETOOL_CONF = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.CONF");
            COMMERCETOOLS_APPLICATION_URL = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.APPLICATION.URL");
            COMMERCETOOL_ADMIN_PROJECTKEY = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.ADMIN.PROJECTKEY");
            COMMERCETOOLS_API_SCOPE = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.API.SCOPE");
            COMMERCETOOLS_CLIENT_NAME = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.CLIENT.NAME");
            TEST_CREDENTIALS_COMMERCETOOLS_TRUSTEDID = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.TRUSTEDID");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_USERNAME = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.TRUSTEDID");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_PASSWORD = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.PASSWORD");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_ADDRESS_CLEANSING_ENDPOINT = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.ADDRESS.CLEANSING.ENDPOINT");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_TAX_SERVICE_ENDPOINT = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.TAX.SERVICE.ENDPOINT");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_COMPANY_CODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.COMPANY.CODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_ADDRESS1 = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.ADDRESS1");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_CITY = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.CITY");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTRY_CODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.COUNTRY.CODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTY_CODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.COUNTY.CODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_POSTALCODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.POSTALCODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_DISPATCHER_COUNTRY = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.COUNTRY");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_ADDRESS1 = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.SELLER.ADDRESS1");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_CITY = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.DISPATCHER.CITY");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTRY_CODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.SELLER.COUNTRY.CODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTY_CODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.SELLER.COUNTY.CODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_POSTALCODE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.SELLER.POSTALCODE");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_SELLER_COUNTRY = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.SELLER.COUNTRY");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_HOST_URL = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.HOST.URL");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_ID = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.CLIENT.ID");
            TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_SECRET = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.VERTEX.CLIENT.SECRET");
            COMMERCETOOL_ADMIN_CLIENTSECRET = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.ADMIN.CLIENTSECRET");
            COMMERCETOOLS_CUSTOMER_GROUP = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.GROUP");
            COMMERCETOOLS_CUSTOMER_GROUP_VALUE = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.GROUP.VALUE");
            COMMERCETOOLS_CUSTOMER_PASSWORD = readCredentials.getProperty(
                    "TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.PASSWORD");
            COMMERCETOOLS_CUSTOMER_KEY = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.KEY");
            COMMERCETOOLS_CUSTOMER_NUMBER = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.NUMBER");
            COMMERCETOOLS_CUSTOMER_FIRSTNAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.FIRSTNAME");
            COMMERCETOOLS_CUSTOMER_LASTNAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.LASTNAME");
            COMMERCETOOLS_CUSTOMER_EMAIL = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.CUSTOMER.EMAIL");
            COMMERCETOOL_URL = readEnvUrls.getProperty("COMMERCETOOL.URL");
            COMMERCETOOLS_ADMIN_URL = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.URL");
            COMMERCETOOLS_ADMIN_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.ADMIN.USERNAME");
            COMMERCETOOLS_ADMIN_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.ADMIN.PASSWORD");
            COMMERCETOOL_ORDERNUMBER = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.ORDER.NUMBER");
            COMMERCETOOL_CART_VERSION = readCredentials.getProperty("COMMERCETOOL.CART.VERSION");
            TAX_SERVICES = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.TAX.SERVICEURL");
            COMMERCETOOL_AUTHURL = readEnvUrls.getProperty("TEST.ENV.COMMERCETOOLS.AUTHURL");
            COMMERCETOOL_AUTH_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.AUTH.USERNAME");
            COMMERCETOOL_AUTH_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.AUTH.PASSWORD");
            COMMERCETOOL_URL_ADDRESS_CLEANSING_HOST = readEnvUrls.getProperty(
                    "TEST.ENV.COMMERCETOOLS.URL.ADDRESS.CLEANSING.HOST");
            BEARER_TOKEN = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.BEARER.TOKEN");
            TEST_CREDENTIALS_COMMERCETOOLS_ORGANIZATION = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.ORGANIZATION");
            TEST_CREDENTIALS_COMMERCETOOLS_PROJECTNAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PROJECTNAME");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_TYPE = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.TYPE");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_NAME = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.NAME");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_DESCRIPTION = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.DESCRIPTION");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_SKU = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.SKU");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_AMOUNT = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.AMOUNT");
            TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_TYPE_DESCRIPTION = readCredentials.getProperty("TEST.CREDENTIALS.COMMERCETOOLS.PRODUCT.TYPE.DESCRIPTION");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * generates the access token using the oauth 2.0
     *
     * @return generated access token
     */
    public Response generateTokenRequest() {
        final String reqURL = COMMERCETOOL_AUTHURL;
        Response response = generateRequestSpecification()
                .queryParam(ID_QUERY_GRANTTYPE, credentials)
                .given()
                .auth()
                .preemptive()
                .basic(COMMERCETOOL_AUTH_USERNAME, COMMERCETOOL_AUTH_PASSWORD)
                .when()
                .post(reqURL);
        return response;
    }

    /**
     * method to validate the token is expires or not
     */
    public void validateToken() {
        if (expiresInTime == 0) {
            generateTokenRequest();
            Response tokenResponse = generateTokenRequest();
            JsonPath jsonPath = tokenResponse.jsonPath();
            commerceToolsAccessToken = jsonPath.get("access_token");
            expiresInTime = jsonPath.get("expires_in");

        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, expiresInTime);
            Date futureDate = cal.getTime();
            Calendar calTo = Calendar.getInstance();
            Date currentDate = calTo.getTime();
            if (currentDate.after(futureDate)) {
                VertexLogger.log("Token is expires");
                Response tokenResponse = generateTokenRequest();
                JsonPath jsonPath = tokenResponse.jsonPath();
                commerceToolsAccessToken = jsonPath.get("access_token");
                expiresInTime = jsonPath.get("expires_in");
            }

        }

    }

    /**
     * set the Shipping From Address for customers.
     *
     * @return set Shipping From Address
     */
    public Response postUsAddress(final String accessToken, String streetAddress1, String streetAddress2, String city,
                                  String mainDivision, String postalCode, String country) {
        CommerceToolPostUsAddress item9 = new CommerceToolPostUsAddress();
        item9.setProjectKey("commercetools-v20-mercury");
        item9.setUsername("mercuryTESTING");
        item9.setPassword("vertex");
        item9.setTrustedId("VTXTST123");
        item9.setCompanyCode("TSTtaxpayer");
        item9.setClientId("B5DJTKstEHpiC4itt_K3sstJ");
        item9.setClientSecret("aF1PkNVK30_Tyx3ugku-YEkMwsqh3rVq");
        item9.setHost("https://api.us-central1.gcp.commercetools.com");
        item9.setAddressCleansingEnabled("true");
        item9.setActive("true");
        item9.setAddressCleansingURL("https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas90");
        item9.setTaxCalculationEndpointURL(
                "https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax90");
        CommerceToolSellerAddress address = new CommerceToolSellerAddress();
        address.setId("89");
        address.setStreetAddress1(streetAddress1);
        address.setStreetAddress2(streetAddress2);
        address.setCity(city);
        address.setMainDivision(mainDivision);
        address.setSubDivision("subDivision");
        address.setPostalCode(postalCode);
        address.setCountry(country);
        item9.setSellerAddress(address);
        CommerceToolDispatcherAddress dispatcher = new CommerceToolDispatcherAddress();
        dispatcher.setId("90");
        dispatcher.setStreetAddress1(streetAddress1);
        dispatcher.setStreetAddress2(streetAddress2);
        dispatcher.setCity(city);
        dispatcher.setMainDivision(mainDivision);
        dispatcher.setSubDivision("subDivision");
        dispatcher.setPostalCode(postalCode);
        dispatcher.setCountry(country);
        item9.setDispatcherAddress(dispatcher);
        item9.setAutoInvoicingEnabled("true");
        item9.setLoggingEnabled("true");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOST, "/" + COMMERCETOOL_CONF);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item9)
                .post(reqURL);
        return response;
    }

    /**
     * create cart for customers.
     */

    public Response createCartRequest(final String accessToken) {
        CommerceToolCreateCart item2 = new CommerceToolCreateCart();
        item2.setCurrency("USD");
        item2.setTaxMode("External");
        item2.setCustomerId("701617a3-aaf2-4572-94f2-699178e6a6ee");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item2)
                .post(reqURL);
        return response;
    }

    /**
     * set Shipping To Address for customers.
     */

    public Response setShippingAddressRequest(final String accessToken, String cartId, Long version, String streetName,
                                              String streetNumber, String postal, String Shipping_City, String Shipping_State, String Shipping_Country) {
        CommerceToolAPISetShippingAddress item4 = new CommerceToolAPISetShippingAddress();
        List<CommerceToolShippingActions> actions = new ArrayList<>();
        item4.setVersion(version);
        CommerceToolShippingActions item7 = new CommerceToolShippingActions();
        item7.setAction("setShippingAddress");
        CommerceToolShippingAddress item8 = new CommerceToolShippingAddress();
        item7.setAddress(item8);
        item8.setId("exampleAddressCalifornia");
        item8.setKey("exampleKeyCal");
        item8.setTitle("My Address");
        item8.setSalutation("Mr.");
        item8.setFirstName("Example");
        item8.setLastName("Person");
        item8.setStreetName(streetName);
        item8.setStreetNumber(streetNumber);
        item8.setAdditionalStreetInfo("");
        item8.setPostalCode(postal);
        item8.setCity(Shipping_City);
        item8.setRegion("");
        item8.setState(Shipping_State);
        item8.setCountry(Shipping_Country);
        item8.setCompany("My Company CommerceToolsCustomFieldName");
        item8.setDepartment("Sales");
        item8.setBuilding("Hightower 1");
        item8.setApartment("247");
        item8.setpOBox("2471");
        item8.setPhone("+49 89 12345678");
        item8.setMobile("+49 171 2345678");
        item8.setEmail("mail@mail.com");
        item8.setFax("+49 89 12345679");
        item8.setAdditionalAddressInfo("no additional Info");
        item8.setExternalId("Information not needed");
        actions.add(item7);
        item4.setActions(actions);
        CommerceToolShippingActions item5 = new CommerceToolShippingActions();
        item5.setAction("setShippingAddress");
        CommerceToolShippingAddress item6 = new CommerceToolShippingAddress();
        item6.setId("exampleAddressCalifornia");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item4)
                .post(reqURL);
        return response;
    }

    /**
     * set Billing Address for customers.
     */
    public Response setBillingAddressRequest(final String accessToken, String cartId, Long version, String streetName,
                                             String streetNumber, String postal, String Shipping_City, String Shipping_State, String Shipping_Country) {
        CommerceToolAPISetShippingAddress item4 = new CommerceToolAPISetShippingAddress();
        List<CommerceToolShippingActions> actions = new ArrayList<>();
        item4.setVersion(version);
        CommerceToolShippingActions item7 = new CommerceToolShippingActions();
        item7.setAction("setBillingAddress");
        CommerceToolShippingAddress item8 = new CommerceToolShippingAddress();
        item7.setAddress(item8);
        item8.setId("exampleAddressCalifornia");
        item8.setKey("exampleKeyCal");
        item8.setTitle("My Address");
        item8.setSalutation("Mr.");
        item8.setFirstName("Example");
        item8.setLastName("Person");
        item8.setStreetName(streetName);
        item8.setStreetNumber(streetNumber);
        item8.setAdditionalStreetInfo("");
        item8.setPostalCode(postal);
        item8.setCity(Shipping_City);
        item8.setRegion("");
        item8.setState(Shipping_State);
        item8.setCountry(Shipping_Country);
        item8.setCompany("My Company CommerceToolsCustomFieldName");
        item8.setDepartment("Sales");
        item8.setBuilding("Hightower 1");
        item8.setApartment("247");
        item8.setpOBox("2471");
        item8.setPhone("+49 89 12345678");
        item8.setMobile("+49 171 2345678");
        item8.setEmail("mail@mail.com");
        item8.setFax("+49 89 12345679");
        item8.setAdditionalAddressInfo("no additional Info");
        item8.setExternalId("Information not needed");
        actions.add(item7);
        item4.setActions(actions);
        CommerceToolShippingActions item5 = new CommerceToolShippingActions();
        item5.setAction("setShippingAddress");
        CommerceToolShippingAddress item6 = new CommerceToolShippingAddress();
        item6.setId("exampleAddressCalifornia");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item4)
                .post(reqURL);
        return response;
    }

    /**
     * add line Items for customers.
     */

    public Response addLineItemRequest(final String accessToken, String cartId, long version1, long quantity) {
        CommerceToolAddLineItem line = new CommerceToolAddLineItem();
        List<CommerceToolAddLineActions> actions = new ArrayList<>();
        line.setVersion(version1);
        CommerceToolAddLineActions line1 = new CommerceToolAddLineActions();
        line1.setAction("addLineItem");
        line1.setProductId("e0f3aa4a-aa35-401a-811a-aeffa0e8db80");
        line1.setVariantId(1);
        line1.setQuantity(quantity);
        line1.setTaxMode("External");
        CommerceToolShippingDetails line2 = new CommerceToolShippingDetails();
        line1.setShippingDetails(line2);
        line2.setAddressKey("AddressKeyStringFromAddress");
        line2.setQuantity(quantity);
        line.setActions(actions);
        actions.add(line1);
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(line)
                .post(reqURL);
        return response;
    }

    /**
     * add line Items for customers with Tax Exempt product.
     */
    public Response addExemptLineItemRequest(final String accessToken, String cartId, long version1, long quantity,
                                             String productId) {
        CommerceToolAddLineItem line = new CommerceToolAddLineItem();
        List<CommerceToolAddLineActions> actions = new ArrayList<>();
        line.setVersion(version1);
        CommerceToolAddLineActions line1 = new CommerceToolAddLineActions();
        line1.setAction("addLineItem");
        line1.setProductId(String.valueOf(productId));
        line1.setVariantId(1);
        line1.setQuantity(quantity);
        line1.setTaxMode("External");
        CommerceToolShippingDetails line2 = new CommerceToolShippingDetails();
        line1.setShippingDetails(line2);
        line2.setAddressKey("AddressKeyStringFromAddress");
        line2.setQuantity(quantity);
        line.setActions(actions);
        actions.add(line1);
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(line)
                .post(reqURL);
        return response;
    }

    /**
     * create order for customers.
     */
    public Response createOrderRequest(final String accessToken, String cartId, long version2) {
        Random random = new Random();
        int createOrder = random.nextInt(999999999);
        CommerceToolCreateOrder item3 = new CommerceToolCreateOrder();
        item3.setId(cartId);
        item3.setVersion(version2);
        item3.setOrderNumber(String.valueOf(createOrder));
        CommerceToolEndpoint createOrderEndpoint = CommerceToolEndpoint.CREATE_ORDER_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createOrderEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item3)
                .post(reqURL);
        return response;
    }

    /**
     * this perform health check for commerce tools endpoint url.
     *
     * @param accessToken
     */
    public Response healthCheckEndpoint(final String accessToken) {
        final CommerceToolEndpoint configEndpoint = CommerceToolEndpoint.COMMERCETOOL_HEALTH_CONTROLLER;
        final String requestUrl = String.format("%s%s", TAX_SERVICES, configEndpoint.getUrlSuffix());
        Response healthCheckResponse = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get(requestUrl);
        return healthCheckResponse;
    }

    /**
     * this perform version check for commerce tools Endpoint Url.
     *
     * @param accessToken
     */
    public Response versionCheckEndpoint(final String accessToken) {
        final CommerceToolEndpoint configEndpoint = CommerceToolEndpoint.COMMERCETOOL_VERSION_CONTROLLER;
        final String requestUrl = String.format("%s%s", TAX_SERVICES, configEndpoint.getUrlSuffix());
        Response versionCheckResponse = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get(requestUrl);
        return versionCheckResponse;
    }

    /**
     * create cart request with access token and currency for customers.
     */
    public Response createCartRequest(final String accessToken, String currency) {
        CommerceToolCreateCart item2 = new CommerceToolCreateCart();
        item2.setCurrency(currency);
        item2.setTaxMode("External");
        item2.setCustomerId("701617a3-aaf2-4572-94f2-699178e6a6ee");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item2)
                .post(reqURL);
        return response;
    }

    /**
     * set the cleansing Address for customers.
     */
    public Response addressCleansingRequest(final String streetAddress1, final String streetAddress2,
                                            final String city, final String mainDivision, final String subDivision, final String postalCode,
                                            final String country) {
        CommerceToolAddressCleansingLists list = new CommerceToolAddressCleansingLists();
        List<CommerceToolAddressCleansingActions> actions1 = new ArrayList<>();
        CommerceToolAddressCleansingActions line3 = new CommerceToolAddressCleansingActions();
        line3.setAsOfDate("2019-10-01");
        CommerceToolCleansingAddress line4 = new CommerceToolCleansingAddress();
        line3.setAddress(line4);
        line4.setStreetAddress1(streetAddress1);
        line4.setStreetAddress2(streetAddress2);
        line4.setCity(city);
        line4.setMainDivision(mainDivision);
        line4.setSubDivision(subDivision);
        line4.setPostalCode(postalCode);
        line4.setCountry(country);
        actions1.add(line3);
        list.setLookupList(actions1);
        CommerceToolEndpoint addressCleansingEndpoint = CommerceToolEndpoint.ADDRESS_CLEANSING_CONTROLLER;
        final String reqURL = String.format("%s%s", TAX_SERVICES, addressCleansingEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .header("projectKey", "commercetools-v20-mercury")
                .auth()
                .oauth2(BEARER_TOKEN)
                .when()
                .body(list)
                .post(reqURL);
        return response;
    }

    /**
     * this asserts that the given REST response carries the expected status code
     *
     * @param response
     * @param expectedStatus
     */
    public void assertStatus(final Response response, final ResponseCodes expectedStatus) {
        response
                .then()
                .assertThat()
                .statusCode(expectedStatus.getResponseCode());
    }

    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    protected WebDriver driver;

    /**
     * initializes the ChromeDriver which interacts with the browser
     *
     * @author Mayur.Kumbhar
     */
    protected void createDriver() {
        WebDriverManager
                .chromedriver()
                .setup();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);
        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--start-maximized");

        options.addArguments("--disable-infobars");
        if (isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned ? new RemoteWebDriver(getDriverServiceUrl(), options) : new ChromeDriver(
                options);
    }

    /**
     * method to get driver service url
     */
    private URL getDriverServiceUrl() {
        try {
            String hostname = "localhost"; 
            if(isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * method to login to commercetools
     */
    public void loginCommerceToolsTest() {
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        vertexLogo = new CommerceToolsVertexLogoPage(driver);
        vertexLogo.clickVertexIcon();
        vertexLogo.switchOffAddressCleansing();
    }

    /**
     * method to switch off the address cleansing toggle in commercetools
     */
    public void addresssCleansingTurnOffCT() {
        createDriver();
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        vertexLogo = new CommerceToolsVertexLogoPage(driver);
        vertexLogo.clickVertexIcon();
        vertexLogo.switchOffAddressCleansing();
        quitDriver();
    }

    /**
     * method to turn on the Address Cleansing Toggle in Oseries
     */
    public void addresssCleansingTurnOnCT() {
        createDriver();
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        vertexLogo = new CommerceToolsVertexLogoPage(driver);
        vertexLogo.clickVertexIcon();
        vertexLogo.switchOnAddressCleansing();
        quitDriver();
    }

    /**
     * send address for Bill from in commercetools
     */
    public void CommerceToolsAddressCleanseTest() {
        loginCommerceToolsTest();
        CommerceToolsBillFromPage shipFrom = new CommerceToolsBillFromPage(driver);
        shipFrom.enterAddress("2955 Market St.");
        shipFrom.enterCity("Philadelphia");
        shipFrom.enterCountryCode("United States");
        shipFrom.enterUSCounty("PA");
        shipFrom.enterPostalCode("99999");
        shipFrom.selectCountry("United States");
        shipFrom.clickSaveButton();
        quitDriver();
    }

    /**
     * quit the driver.
     */
    protected void quitDriver() {
        VertexLogger.log("Quitting ChromeDriver...");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * this method communicates with the address cleansing Endpoint and returns the response
     *
     * @return Response
     */
    public Response addressLookup() {
        CommerceToolsAddress address = new CommerceToolsAddress();
        address.setStreetAddress1("1041 Old Cassett Rd");
        address.setStreetAddress2("Suite 100");
        address.setCity("Berwyn");
        address.setMainDivision("PA");
        address.setSubDivision("subDivision");
        address.setPostalCode("11111-1111");
        address.setState("state");
        address.setCountry("US");
        CommerceToolsAddressCleansingLookupType commerceToolsAddressCleansingLookupType
                = new CommerceToolsAddressCleansingLookupType();
        commerceToolsAddressCleansingLookupType.setExternalJurisdictionCode("123");
        commerceToolsAddressCleansingLookupType.setExternalJurisdictionCountry("US");
        commerceToolsAddressCleansingLookupType.setLookupId("123");
        commerceToolsAddressCleansingLookupType.setAsOfDate("2019-11-08");
        commerceToolsAddressCleansingLookupType.setAddress(address);
        List<CommerceToolsAddressCleansingLookupType> lookupList
                = new ArrayList<CommerceToolsAddressCleansingLookupType>();
        lookupList.add(commerceToolsAddressCleansingLookupType);
        CommerceToolsAddressCleansingRequest commerceToolsAddressCleansingRequest
                = new CommerceToolsAddressCleansingRequest();
        commerceToolsAddressCleansingRequest.setLookupList(lookupList);
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.ADDRESS_CLEANSING_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_URL_ADDRESS_CLEANSING_HOST,
                createCartEndpoint.getUrlSuffix());
        Header header = new Header("projectKey", COMMERCETOOL_ADMIN_PROJECTKEY);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(BEARER_TOKEN)
                .header(header)
                .when()
                .body(commerceToolsAddressCleansingRequest)
                .post(reqURL);
        return response;
    }

    /**
     * changes the address cleansing settings in Oseries
     */
    public void addressCleanseSettingsOseries() {
        createDriver();
        OSeriesLoginTest loginTest = new OSeriesLoginTest();
        loginTest.signIntoOSeries(driver);
        OSeriesSettingsPage settingsPage = new OSeriesSettingsPage(driver);
        settingsPage.clickOnSystemLink();
        settingsPage.clickOnAdministrationLink();
        settingsPage.clickSettingsLink();
        settingsPage.changeOseriesSettingsToFalse("Continue If Address Cleansing Unavailable (Calc Override)");
        settingsPage.changeOseriesSettingsToFalse("Continue If Address Fails To Cleanse (Calc Override)");
        quitDriver();
    }

    /**
     * Resets the Oseries Address Cleansing Settings to Original Values
     */
    public void cleanUpAddressCleanseSettings() {
        createDriver();
        OSeriesLoginTest loginTest = new OSeriesLoginTest();
        loginTest.signIntoOSeries(driver);
        OSeriesSettingsPage settingsPage = new OSeriesSettingsPage(driver);
        settingsPage.clickOnSystemLink();
        settingsPage.clickOnAdministrationLink();
        settingsPage.clickSettingsLink();
        settingsPage.resetSettingsForOSeries("Continue If Address Cleansing Unavailable (Calc Override)");
        settingsPage.resetSettingsForOSeries("Continue If Address Fails To Cleanse (Calc Override)");
        quitDriver();
    }

    /**
     * Method to create custom type for order cancellation.
     *
     * @param accessToken
     * @return
     */
    public Response createCustomType(final String accessToken) {
        String customBody = null;
        CommerceToolsCustomFieldName name = new CommerceToolsCustomFieldName("{ \"en\": \"orderCancel\" }");
        List<String> resourceTypeIds = new ArrayList<>();
        resourceTypeIds.add("order");
        CommerceToolsCustomFieldDescription description = new CommerceToolsCustomFieldDescription("{\"en\":\"Order Cancellation\"}");
        CommerceToolsCustomFieldType type = new CommerceToolsCustomFieldType("Boolean");
        CommerceToolsCustomFieldLabel label = new CommerceToolsCustomFieldLabel("cancellationProcessed");

        CommerceToolsCustomFieldDefinition fieldDefinition = new CommerceToolsCustomFieldDefinition(type, "cancellationProcessed", label, false, "SingleLine");
        List<CommerceToolsCustomFieldDefinition> fieldDefinitions = new ArrayList<CommerceToolsCustomFieldDefinition>();
        fieldDefinitions.add(fieldDefinition);


        CommerceToolsCustomTypeFields customField = new CommerceToolsCustomTypeFields("orderCancel", name, resourceTypeIds, description, fieldDefinitions);
        ObjectMapper jsonBody = new ObjectMapper();

        try {
            customBody = jsonBody.writerWithDefaultPrettyPrinter().writeValueAsString(customField);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.COMMERCETOOL_CUSTOM_TYPE;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(customBody)
                .post(reqURL);
        return response;
    }

    /**
     * Login to commercetools and cancel the order.
     */
    public void loginAndCancelOrder(String orderID) {
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        CommerceToolsOrderPage order = new CommerceToolsOrderPage(driver);
        order.clickOnOrderIcon();
        order.clickOnOrderList();
        String URL = order.getCurrentUrl();
        String newURL = URL + "/" + orderID + "/" + "general";
        driver.navigate().to(newURL);
        order.clickCustomFieldsTab();
        order.selectCustomFields("orderCancel");
        order.selectOrderCancellationReason("YES");
        order.clickOnSaveButton();
        order.clickOnGeneralTab();
        order.selectOrderState("Cancelled");
        order.clickOnSaveButton();
    }

    /**
     * Create New Api Client in Commercetools.
     */
    public void createApiClient(String clientName, String scope) {

        CommerceToolsDeveloperSettingsPage setting = new CommerceToolsDeveloperSettingsPage(driver);
        setting.clickSettingIcon();
        setting.clickDeveloperSettingIcon();
        setting.clickApiClientButton();
        setting.enterApiClientName(clientName);
        setting.enterScope(scope);
        setting.clickCreateApiClientButton();
        setting.clickGoBackButton();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        setting.clickGoBackButton();
        Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
        alert1.accept();
    }

    /**
     * Create Custom Applications in Commercetools.
     */
    public void createCustomApplication(String customApplicationName, String applicationUrl) {

        CommerceToolsCustomApplicationsPage custom = new CommerceToolsCustomApplicationsPage(driver);
        custom.clickSettingIcon();
        custom.clickOnCustomApplication();
        custom.clickCustomApplicationNameButton();
        custom.enterCustomApplicationName(customApplicationName);
        custom.enterApplicationUrl(applicationUrl);
        custom.enterMainRoutePath("vertex-config");
        custom.clickLanguagesLink();
        custom.enterLanguage("Vertex-Config");
        custom.clickVertexIcon();
        custom.clickRegisterCustomApplication();
        custom.clickOnDashBoard();
        custom.clickSettingIcon();
        custom.clickOnCustomApplication();
        custom.clickOnActivateConnector();
        custom.clickActivateStatus();
        custom.clickConfirmButton();
        custom.clickGoToBackButton();
    }

    /**
     * Response to add line item to the cart.
     *
     * @param accessToken
     * @param cartId
     * @param version1
     * @param quantity
     * @return
     */
    public Response addLineItemRequest(final String accessToken, String cartId, long version1, long quantity, final String product) {

        CommerceToolAddLineItem line = new CommerceToolAddLineItem();
        List<CommerceToolAddLineActions> actions = new ArrayList<>();
        line.setVersion(version1);
        CommerceToolAddLineActions line1 = new CommerceToolAddLineActions();
        line1.setAction("addLineItem");
        line1.setProductId(String.valueOf(product));
        line1.setVariantId(1);
        line1.setQuantity(quantity);
        line1.setTaxMode("External");
        CommerceToolShippingDetails line2 = new CommerceToolShippingDetails();
        line1.setShippingDetails(line2);
        line2.setAddressKey("AddressKeyStringFromAddress");
        line2.setQuantity(quantity);
        line.setActions(actions);
        actions.add(line1);
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(line)
                .post(reqURL);
        return response;
    }

    /**
     * add line Items for customers with Exempt product Code.
     */
    public Response addExemptLineItemRequest(final String accessToken, String cartId, long version1, long quantity,
                                             CommerceToolsProductID productId) {
        CommerceToolAddLineItem line = new CommerceToolAddLineItem();
        List<CommerceToolAddLineActions> actions = new ArrayList<>();
        line.setVersion(version1);
        CommerceToolAddLineActions line1 = new CommerceToolAddLineActions();
        line1.setAction("addLineItem");
        line1.setProductId(String.valueOf(productId));
        line1.setVariantId(1);
        line1.setQuantity(quantity);
        line1.setTaxMode("External");
        CommerceToolShippingDetails line2 = new CommerceToolShippingDetails();
        line1.setShippingDetails(line2);
        line2.setAddressKey("AddressKeyStringFromAddress");
        line2.setQuantity(quantity);
        line.setActions(actions);
        actions.add(line1);
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix() + "/" + cartId);
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(line)
                .post(reqURL);
        return response;
    }

    /**
     * create order for customers with multiple line items.
     */
    public Response createOrderRequest(final String accessToken, String cartId, long version2, long version3) {
        Random random = new Random();
        int createOrder = random.nextInt(999999999);
        CommerceToolCreateOrder item3 = new CommerceToolCreateOrder();
        item3.setId(cartId);
        item3.setVersion(version2);
        item3.setOrderNumber(String.valueOf(createOrder));
        CommerceToolEndpoint createOrderEndpoint = CommerceToolEndpoint.CREATE_ORDER_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createOrderEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item3)
                .post(reqURL);
        return response;
    }

    /**
     * Create Cart for exempt customer
     *
     * @param accessToken
     * @return
     */
    public Response createCartRequestforExemptCustomer(final String accessToken) {
        CommerceToolCreateCart item2 = new CommerceToolCreateCart();
        item2.setCurrency("USD");
        item2.setTaxMode("External");
        item2.setCustomerId("d0df44c5-d66c-41e0-874e-0e83d2802e26");
        CommerceToolEndpoint createCartEndpoint = CommerceToolEndpoint.CREATE_CART_CONTROLLER;
        final String reqURL = String.format("%s%s", COMMERCETOOL_HOSTTAX,
                "/" + COMMERCETOOL_ADMIN_PROJECTKEY + createCartEndpoint.getUrlSuffix());
        Response response = generateRequestSpecification()
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .body(item2)
                .post(reqURL);
        return response;
    }

    /**
     * method to create new project in commercetools
     */
    public void createNewCommerceToolsProject(String projectName) {
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        CommerceToolsCustomApplicationsPage custom = new CommerceToolsCustomApplicationsPage(driver);
        projectPage = new CommerceToolsCreateProjectPage(driver);
        projectPage.clickOnUserIcon();
        projectPage.clickOnManageProject();
        projectPage.clickOnAddProjectButton();
        projectPage.enterOrganizationName(TEST_CREDENTIALS_COMMERCETOOLS_ORGANIZATION);
        projectPage.enterProjectName(projectName);
        projectPage.clickCreateProjectButton();
        projectPage.clickOnAccessProject();
    }

    /**
     * method to get all projects in commercetools
     */
    public void openManageProject() {
        driver.get(COMMERCETOOLS_ADMIN_URL);
        signOnPage = new CommerceToolsSignOnPage(driver);
        signOnPage.enterEmailID(COMMERCETOOLS_ADMIN_USERNAME);
        signOnPage.enterPassword(COMMERCETOOLS_ADMIN_PASSWORD);
        signOnPage.clickLoginButton();
        CommerceToolsCustomApplicationsPage custom = new CommerceToolsCustomApplicationsPage(driver);
        projectPage = new CommerceToolsCreateProjectPage(driver);
        projectPage.clickOnUserIcon();
        projectPage.clickOnManageProject();
    }

    /**
     * method to create product type in commercetools
     */

    public void createProductType(String productTypeName, String productTypeDescription) {
        productType = new CommerceToolsProductTypePage(driver);
        productType.clickOnHideButton();
        productType.clickOnSettingsIcon();
        productType.clickOnProductTypes();
        productType.clickOnCreateProductTypeLink();
        productType.enterProductTypeName(productTypeName);
        productType.enterProductDescription(productTypeDescription);
        productType.clickOnSaveButton();
    }

    /**
     * method to create product on merchant center
     */
    public void createProduct(String productTypeName) {
        productPage = new CommerceToolsProductPage(driver);
        productPage.clickOnHideNotificationButton();
        productPage.clickOnProductsTab();
        productPage.clickOnAddProduct();
        productPage.selectProductType(productTypeName);
        productPage.clickOnNextButton();
        productPage.enterProductName(TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_NAME);
        productPage.enterProductDescription(TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_DESCRIPTION);
        productPage.clickOnNextButton();
        productPage.clickOnAddVariant();
        productPage.enterSKU(TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_SKU);
        productPage.clickOnSaveVariantButton();
        productPage.clickOnNextButton();
        productPage.clickOnSaveProductVariantButton();
        productPage.clickOnVariantTab();
        productPage.clickOnPriceToAdd();
        productPage.clickOnAddPriceToVariant();
        productPage.enterVariantPrice(TEST_CREDENTIALS_COMMERCETOOLS_PRODUCT_AMOUNT);
        productPage.clickOnSaveVariantPrice();
        productPage.clickOKPopUp();
        productPage.clickOnVariantList();
        productPage.clickToPublishVariant();

    }

    /**
     * method to add Customer Group in commercetools
     */
    public void addCustomerGroup(String customerGroupName) {
        CommerceToolsAddCustomerGroupPage addCustomerGroup = new CommerceToolsAddCustomerGroupPage(driver);
        addCustomerGroup.clickCustomersLink();
        addCustomerGroup.clickAddCustomerGroupLink();
        addCustomerGroup.enterCustomerGroupName(customerGroupName);
        addCustomerGroup.enterCustomerKeyName(COMMERCETOOLS_CUSTOMER_KEY);
        addCustomerGroup.clickSaveButton();
    }

    /**
     * method to add Customers in commercetools
     */
    public void addCustomers() {
        CommerceToolsAddCustomerPage addCustomer = new CommerceToolsAddCustomerPage(driver);
        addCustomer.clickCustomersLink();
        addCustomer.clickAddCustomersLink();
        addCustomer.enterFirstName(COMMERCETOOLS_CUSTOMER_FIRSTNAME);
        addCustomer.enterLastName(COMMERCETOOLS_CUSTOMER_LASTNAME);
        addCustomer.enterEmail(COMMERCETOOLS_CUSTOMER_EMAIL);
        addCustomer.enterCustomerNumberField(COMMERCETOOLS_CUSTOMER_NUMBER);
        addCustomer.enterCustomerGroupField(COMMERCETOOLS_CUSTOMER_GROUP_VALUE);
        addCustomer.enterCustomerPasswordField(COMMERCETOOLS_CUSTOMER_PASSWORD);
        addCustomer.enterConfirmPasswordField(COMMERCETOOLS_CUSTOMER_PASSWORD);
        addCustomer.clickSaveButton();
    }

    /**
     * method to enter vertex services information on merchant center
     */


    public void enterVertexDetails(String trustedId, String username, String password, String addressCleansingEndpoint, String taxCalculationEndpoint,
                                   String companyCode, String dispatcherAddress, String dispatcherCity, String dispatcherState, String dispatcherCountry, String dispatcherPostalCode,
                                   String sellerAddress, String sellerCity, String sellerState, String sellerCountry, String sellerPostalCode, String hostUrl,
                                   String sCounty, String dCounty) {
        CommerceToolsVertexServicePage vertexServicePage = new CommerceToolsVertexServicePage(driver);
        CommerceToolsCustomApplicationsPage custom = new CommerceToolsCustomApplicationsPage(driver);
        custom.clickOnDashBoard();
        vertexServicePage.clickVertexLogoIcon();
        vertexServicePage.enterTrustedID(trustedId);
        vertexServicePage.enterVertexUserName(username);
        vertexServicePage.enterVertexPassword(password);
        vertexServicePage.enterAddressCleansingEndpoint(addressCleansingEndpoint);
        vertexServicePage.enterTaxServiceEndpoint(taxCalculationEndpoint);
        vertexServicePage.activateAddressCleansing();
        vertexServicePage.activateAutoInvoice();
        vertexServicePage.activateOperatorLog();
        vertexServicePage.enterCompanyCode(companyCode);
        vertexServicePage.enterDispatcherStreetAddress1(dispatcherAddress);
        vertexServicePage.enterDispatcherCity(dispatcherCity);
        vertexServicePage.enterDispatcherCountryCode(dispatcherState);
        vertexServicePage.enterDispatcherUSCounty(dCounty);
        vertexServicePage.enterDispatcherPostalCode(dispatcherPostalCode);
        vertexServicePage.enterDispatcherCountry(dispatcherCountry);
        vertexServicePage.enterSellerAddress1(sellerAddress);
        vertexServicePage.enterSellerCity(sellerCity);
        vertexServicePage.enterSellerCountryCode(sellerState);
        vertexServicePage.enterSellerUSCounty(sCounty);
        vertexServicePage.enterSellerPostalCode(sellerPostalCode);
        vertexServicePage.enterSellerCountry(sellerCountry);
        vertexServicePage.enterHostURL(hostUrl);
        vertexServicePage.enterClientID(TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_ID);
        vertexServicePage.enterClientSecret(TEST_CREDENTIALS_COMMERCETOOLS_VERTEX_CLIENT_SECRET);
        vertexServicePage.clickOnSaveButton();
        quitDriver();
    }
}