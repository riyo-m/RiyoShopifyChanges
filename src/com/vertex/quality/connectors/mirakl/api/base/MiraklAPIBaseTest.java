package com.vertex.quality.connectors.mirakl.api.base;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklAPITestUtilities;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals.miraklApiUrl;
import static io.restassured.RestAssured.given;

/**
 * Mirakl class to store common api methods
 *
 * @author alewis
 */
public class MiraklAPIBaseTest extends MiraklAPITestUtilities
{

	private String miraklOperatorId;
	protected String miraklOrderString, miraklShopsString, miraklOperatorsString;

	/**
	 * Gets the miraklOperaterId for use in other tests
	 *
	 * @return the operator id for this test run
	 */
	public String getMiraklOperatorId( )
	{
		return miraklOperatorId;
	}

	/**
	 * Sends the endpoint url for health check.
	 *
	 * @return healthCheckResponse health check api response
	 */
	protected Response healthCheckEndpoint( )
	{
		final String statusEndpoint = MiraklOperatorsData.HEALTHCHECK_API_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), statusEndpoint);
		Response healthCheckResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return healthCheckResponse;
	}

	/**
	 * Checks the status of a specified operator in the mirakl connector
	 *
	 * @param operatorId
	 *
	 * @return status of the operator
	 */
	protected String privateHealthCheckOperatorEndpoint( String operatorId )
	{
		final String statusEndpoint = MiraklOperatorsData.PRIVATE_HEALTHCHECK_API_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), statusEndpoint);
		String status = getOperatorStatus(requestUrl, operatorId);
		return status;
	}

	/**
	 * Checks the status of the mirakl connector
	 *
	 * @return status of the connector
	 */
	protected String privateHealthCheckStatusEndpoint( )
	{
		final String statusEndpoint = MiraklOperatorsData.PRIVATE_HEALTHCHECK_API_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), statusEndpoint);
		String status = getConnectorStatus(requestUrl);
		return status;
	}

	/**
	 * reads categories json data for product class.
	 *
	 * @return JsonString categories data from json file
	 */
	protected String getCategoriesData( )
	{
		InputStream categoriesInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getCategories(categoriesInput);
	}

	/**
	 * reads operators json data for creating operators
	 *
	 * @return JsonString operators data from json file
	 */
	protected String getOperatorsData( )
	{
		InputStream operatorsInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getOperators(operatorsInput);
	}

	/**
	 * Gets the mapping json data for posting
	 *
	 * @param operatorId to test creation of field mapping
	 *
	 * @return getMappingData return mapping data from json file
	 */
	protected String putMappingData( String operatorId )
	{
		InputStream operatorsInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getMappingData(operatorsInput, operatorId);
	}

	/**
	 * Gets the bad mapping json for posting
	 *
	 * @param operatorId pass wrong operator id to test field mapping
	 *
	 * @return getMappingData return mapping data from json file
	 */
	protected String putBadMappingData( String operatorId )
	{
		InputStream operatorsInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_NEGATIVE_DATA_JSON.data);
		return getMappingData(operatorsInput, operatorId);
	}

	/**
	 * reads countries json data for product class.
	 *
	 * @return JsonString countries data from json file
	 */
	protected String getCountriesData( )
	{
		InputStream categoriesInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getCountries(categoriesInput);
	}

	/**
	 * reads orders json data.
	 *
	 * @return JsonString orders data from json file
	 */
	protected String getOrdersData( )
	{
		InputStream ordersInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getOrders(ordersInput);
	}

	/**
	 * Gets new json data for orders with multiple line items
	 *
	 * @return a new order
	 */
	protected String getMultiLineData( )
	{
		InputStream ordersInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MULTI_LINE_SHIPPING_DATA_JSON.data);
		return changeOrderid(ordersInput);
	}

	/**
	 * reads shops json data.
	 *
	 * @return JsonString shops data from json file
	 */
	protected String getShopsData( )
	{
		InputStream shopsInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_DATA_JSON.data);
		return getShops(shopsInput);
	}

	/**
	 * reads operators json data for creating a bad operators
	 *
	 * @return JsonString operators data from json file
	 */
	protected String putNegativeOperatorsData( )
	{
		InputStream operatorsInput = MiraklAPITestUtilities.class.getResourceAsStream(
			MiraklOperatorsData.MIRAKL_NEGATIVE_DATA_JSON.data);
		return getOperators(operatorsInput);
	}

	/**
	 * Sends the endpoint url for product class.
	 *
	 * @param operatorId to create product category for particular operator
	 *
	 * @return productClassResponse product class api response
	 */
	protected Response productClassEndpoint( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/categories";
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response productClassResponse = generateRequestSpecification()
			.when()
			.put(requestUrl);
		return productClassResponse;
	}

	/**
	 * Sends the endpoint url for create operator api.
	 *
	 * @param operatorsString body parameter to create operator
	 *
	 * @return createOperatorsResponse create operators api response
	 */
	protected Response createOperatorsEndpoint( String operatorsString )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response createOperatorsResponse = generateRequestSpecification()
			.body(operatorsString)
			.post(requestUrl);
		return createOperatorsResponse;
	}

	/**
	 * Creates an operator specified by operatorId
	 *
	 * @return the id of the created operator
	 */
	protected String createSpecificOperatorsEndpoint( )
	{
		String miraklOperatorsString = getOperatorsData();
		Response response = createOperatorsEndpoint(miraklOperatorsString);
		miraklOperatorId = response
			.getBody()
			.asString();
		return miraklOperatorId;
	}

	/**
	 * Sends the endpoint url to get operators api.
	 *
	 * @return getOperatorsResponse get operators api response
	 */
	protected Response getOperatorsEndpoint( )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response getOperatorsResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return getOperatorsResponse;
	}

	/**
	 * Sends the endpoint url for update operators api.
	 *
	 * @param operatorsString body to update operator
	 * @param operatorId      to update original operator
	 *
	 * @return getOperatorsResponse get operators api response
	 */
	protected Response updateOperatorsEndpoint( String operatorsString, String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response getOperatorsResponse = generateRequestSpecification()
			.body(operatorsString)
			.put(requestUrl);
		return getOperatorsResponse;
	}

	/**
	 * Sends the endpoint url for delete operators api.
	 *
	 * @param operatorId to delete particular operator
	 *
	 * @return getOperatorsResponse get operators api response
	 */
	protected Response deleteOperatorsEndpoint( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response getOperatorsResponse = generateRequestSpecification()
			.when()
			.delete(requestUrl);
		return getOperatorsResponse;
	}

	/**
	 * Sends the endpoint url for product class status api.
	 *
	 * @param miraklOperatorId to get status for particular operator
	 * @param activityId       to get status for this particular activityId
	 *
	 * @return productClassStatusResponse product class status api response
	 */
	protected Response getStatusEndpoint( String miraklOperatorId, String activityId, String type )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + miraklOperatorId + "/" +
									  type + "/statuses/" + activityId;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response productClassStatusResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return productClassStatusResponse;
	}

	/**
	 * Sends the endpoint url for create operator api.
	 *
	 * @param countriesString countries json input
	 *
	 * @return createOperatorsResponse create operators api response
	 */
	protected Response createCountriesEndpoint( String countriesString )
	{
		final String configEndpoint = MiraklOperatorsData.COUNTRIES_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response createOperatorsResponse = generateRequestSpecification()
			.body(countriesString)
			.post(requestUrl);
		return createOperatorsResponse;
	}

	/**
	 * Sends the endpoint url to get operators api.
	 *
	 * @return getCountriesResponse get countries api response
	 */
	protected Response getCountriesEndpoint( )
	{
		final String configEndpoint = MiraklOperatorsData.COUNTRIES_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response getCountriesResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return getCountriesResponse;
	}

	/**
	 * Sends the endpoint url for shops.
	 *
	 * @param operatorId operator for which shops to be synced
	 *
	 * @return shopsResponse shops api response
	 */
	protected Response shopsEndpoint( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/shops";
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response shopsResponse = generateRequestSpecification()
			.when()
			.put(requestUrl);
		return shopsResponse;
	}

	/**
	 * This test will delete the specific operator for Mirakl connector
	 */
	//@AfterGroups("mirakl_regression")
	public void deleteOperatorsTest( )
	{
		Response response = deleteOperatorsEndpoint(miraklOperatorId);
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS_NO_CONTENT.getResponseCode());
	}

	/**
	 * Does a put request for a sync of the inbound orders in for the Mirakl connector
	 *
	 * @param operatorId to sync orders for the operator
	 *
	 * @return response from sync put request
	 */
	public Response putSync( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.SYNC_ENDPOINT.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response syncResponse = generateRequestSpecification()
			.when()
			.put(requestUrl);
		return syncResponse;
	}

	/**
	 * Does a put request for a get inbound orders in for the Mirakl connector
	 *
	 * @param operatorId to get all orders for the operator
	 *
	 * @return response from get orders request
	 */
	public Response getOrders( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.SYNC_ENDPOINT.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response syncResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return syncResponse;
	}

	/**
	 * Does a get request for a get inbound order details for the Mirakl connector
	 *
	 * @param operatorId to get order details for the particular operator
	 *
	 * @return response from get details for request
	 */
	public Response getOrderDetails( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.SYNC_ENDPOINT.data + MiraklOperatorsData.FAILURES.data + "?" +
									  getCurrentDate() + MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response syncResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return syncResponse;
	}

	/**
	 * Does a get request for a get outbound order logs for the Mirakl connector
	 *
	 * @param operatorId to get outbound order logs for the operator
	 *
	 * @return get outbound logs response
	 */
	public Response getOutboundLogs( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.OUTBOUND_ORDERS.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response ordersResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return ordersResponse;
	}

	/**
	 * Does a get request for a get outbound order logs for the Mirakl connector
	 *
	 * @param operatorId to get outbound order log details for the operator
	 *
	 * @return get outbound logs response
	 */
	public Response getOutboundLogDetails( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.OUTBOUND_ORDER_DETAILS.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response ordersResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return ordersResponse;
	}

	/**
	 * Does a get request for inbound order logs for a Mirakl operator
	 *
	 * @param operatorId to get inbound order logs for the operator
	 *
	 * @return inbound logs response
	 */
	public Response getInboundLogs( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.INBOUND_ORDER_DETAILS.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response ordersResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return ordersResponse;
	}

	/**
	 * Api request for inbound order logs for a Mirakl operator
	 *
	 * @param operatorId to get inbound order log details for the operator
	 *
	 * @return inbound order logs for the provided operator
	 */
	public Response getInboundLogDetails( String operatorId )
	{
		final String configEndpoint = MiraklOperatorsData.OPERATORS_ENDPOINT.data + "/" + operatorId + "/" +
									  MiraklOperatorsData.INBOUND_ORDER_DETAILS.data + "?" + getCurrentDate() +
									  MiraklOperatorsData.OPERATOR_ORDERS_DATE.data;
		final String requestUrl = String.format("%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), configEndpoint);
		Response ordersResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return ordersResponse;
	}

	/**
	 * Api request for order logs for a Mirakl operator
	 *
	 * @return order log response
	 */
	public Response getOrderLogs( String date )
	{
		final String configEndpoint = "/orders?start_update_date=" + date;
		final String requestUrl = String.format("%s%s", MiraklOperatorsData.MIRAKL_ENDPOINT.data, configEndpoint);
		Response ordersResponse = generateMiraklRequestSpecificationFront()
			.when()
			.get(requestUrl);
		return ordersResponse;
	}

	/**
	 * Gets the tax engine response from an Operator log response
	 *
	 * @param operatorLogResponse to get tax engine request log
	 *
	 * @return tax engine request log for the provided operator
	 */
	public String getInboundTaxEngineRequestLog( Response operatorLogResponse )
	{
		String payload = operatorLogResponse
			.body()
			.path("content.taxEngineRequestPayload[0]");
		return payload;
	}

	/**
	 * Gets the order_lines values from an order
	 *
	 * @return order_lines data
	 */
	public LinkedHashMap getOrderLinesFromLog( Response operatorLogResponse )
	{
		LinkedHashMap payload = operatorLogResponse
			.body()
			.path("orders[0].order_lines[0]");
		return payload;
	}

	/**
	 * Does a get request for a get shops for the Mirakl connector
	 *
	 * @param shopID to get all shops
	 *
	 * @return get shops response
	 */
	public Response getShops( String shopID )
	{
		final String configEndpoint = "/" + MiraklOperatorsData.SHOPS.data + shopID;
		final String requestUrl = String.format("%s%s", MiraklOperatorsData.MIRAKL_ENDPOINT.data, configEndpoint);
		Response shopsResponse = generateMiraklRequestSpecificationFront()
			.when()
			.get(requestUrl);
		return shopsResponse;
	}

	/**
	 * Create bulk orders based on count provided
	 *
	 * @param count how many bulk orders to create
	 */
	protected void createBulkOrders( int count )
	{
		try
		{
			for ( int i = 0 ; i < count ; i++ )
			{
				InputStream ordersInput = MiraklAPITestUtilities.class.getResourceAsStream(
					MiraklOperatorsData.MIRAKL_DATA_ORDERS_JSON.data);
				JSONTokener inputToken = new JSONTokener(ordersInput);
				JSONObject ordersObject = new JSONObject(inputToken);
				JSONObject orders = (JSONObject) ordersObject.get("orders_input");
				JSONObject customersObject = (JSONObject) ordersObject.get("customer_input");
				JSONArray customersArray = (JSONArray) customersObject.get("customers");
				JSONObject offersObject = (JSONObject) ordersObject.get("offers_input");
				JSONArray offersArray = (JSONArray) offersObject.get("offers");
				JSONArray shippingZonesArray = (JSONArray) ordersObject.get("shipping_zone_code_input");
				JSONArray orderTaxModesArray = (JSONArray) ordersObject.get("order_tax_mode_input");
				JSONArray paymentWorkflowsArray = (JSONArray) ordersObject.get("payment_workflow_input");
				orders.put("commercial_id", "MIR-BULK-ORDER-" + getDateTime());
				orders.put("customer", getRandomArrayVal(customersArray));
				orders.put("offers", getRandomArrayVal(offersArray));
				orders.put("order_tax_mode", getRandomArrayVal(orderTaxModesArray));
				orders.put("payment_workflow", getRandomArrayVal(paymentWorkflowsArray));
				orders.put("shipping_zone_code", getRandomArrayVal(shippingZonesArray));
				miraklOrderString = orders.toString();
				Response response = createOrdersEndpoint(miraklOrderString);
				Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
				VertexLogger.log("Order created: " + response.path("orders.commercial_id"));
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log(e.toString());
		}
	}

	/**
	 * Create bulk shops based on count provided
	 *
	 * @param count how many bulk shops to create
	 */
	protected void createBulkShops( int count )
	{
		for ( int i = 0 ; i < count ; i++ )
		{
			InputStream shopsInput = MiraklAPITestUtilities.class.getResourceAsStream(
				MiraklOperatorsData.MIRAKL_DATA_STORES_JSON.data);
			JSONTokener inputToken = new JSONTokener(shopsInput);
			JSONObject shopsObject = new JSONObject(inputToken);
			JSONArray shopDataArray = (JSONArray) shopsObject.get("shops");
			JSONObject shops = (JSONObject) shopDataArray.get(0);
			JSONObject addressObject = (JSONObject) shopsObject.get("address_input");
			JSONArray addressArray = (JSONArray) addressObject.get("address");
			JSONArray shippingCountryArray = (JSONArray) shopsObject.get("shipping_country_input");
			shops.put("address", getRandomArrayVal(addressArray));
			shops.put("email", "sgirism+mirakl" + getDateTime() + "@gmail.com");
			shops.put("password", "VertexMirakl" + getDateTime() + "$");
			shops.put("shipping_country", getRandomArrayVal(shippingCountryArray));
			shops.put("shop_name", "MiraklStore -" + getDateTime());
			JSONArray finalArray = new JSONArray();
			finalArray.put(shops);
			JSONObject shopApiInput = new JSONObject();
			shopApiInput.put("shops", finalArray);
			miraklShopsString = shopApiInput.toString();
			Response response = createShopsEndpoint(miraklShopsString);
			Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
		}
	}

	/**
	 * API request for creating orders
	 *
	 * @param orderString body for creating order
	 *
	 * @return createOrdersResponse order created response
	 */
	public Response createOrdersEndpoint( String orderString )
	{
		final String configEndpoint = MiraklOperatorsData.ORDERS_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklOperatorsData.MIRAKL_ENDPOINT.data, configEndpoint);
		Response createOrdersResponse = generateMiraklRequestSpecificationFront()
			.body(orderString)
			.post(requestUrl);
		return createOrdersResponse;
	}

	/**
	 * API request to create shops
	 *
	 * @param shopString body for creating shops
	 *
	 * @return createShopsResponse shops created response
	 */
	public Response createShopsEndpoint( String shopString )
	{
		final String configEndpoint = MiraklOperatorsData.SHOPS_ENDPOINT.data;
		final String requestUrl = String.format("%s%s", MiraklOperatorsData.MIRAKL_ENDPOINT.data, configEndpoint);
		Response createShopsResponse = generateMiraklRequestSpecificationOperator()
			.body(shopString)
			.post(requestUrl);
		return createShopsResponse;
	}

	/**
	 * Creates a request mapping for the specified operator
	 *
	 * @param operatorId to create request mapping for the operator
	 *
	 * @return the mapping response
	 */
	protected Response createRequestMapping( String operatorId )
	{
		final String requestMappingEndpoint = MiraklOperatorsData.REQUEST_MAPPING_ENDPOINT.data;
		final String requestUrl = String.format("%s/%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), "operators/" + operatorId, requestMappingEndpoint);
		Response createMappingResponse = generateRequestSpecification()
			.body(putMappingData(operatorId))
			.post(requestUrl);
		return createMappingResponse;
	}

	/**
	 * Creates a request mapping for the specified operator for a failed request
	 *
	 * @param operatorId to create request mapping for the wrong operator url
	 *
	 * @return the mapping response
	 */
	protected Response createRequestMappingBadOperatorURL( String operatorId )
	{
		final String requestMappingEndpoint = MiraklOperatorsData.REQUEST_MAPPING_ENDPOINT.data;
		final String requestUrl = String.format("%s/%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), "operators/bad", requestMappingEndpoint);
		Response createRQResponse = generateRequestSpecification()
			.body(putMappingData(operatorId))
			.post(requestUrl);
		createRQResponse
			.then()
			.statusCode(404);
		return createRQResponse;
	}

	/**
	 * Creates a request mapping for the specified operator with a bad request body
	 *
	 * @param operatorId to create request mapping for the wrong operator JSON
	 *
	 * @return the mapping response
	 */
	protected Response createRequestMappingBadOperatorJSON( String operatorId )
	{
		final String requestMappingEndpoint = MiraklOperatorsData.REQUEST_MAPPING_ENDPOINT.data;
		final String requestUrl = String.format("%s/%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), "operators/" + operatorId, requestMappingEndpoint);
		Response createRQResponse = generateRequestSpecification()
			.body(putMappingData(operatorId))
			.post(requestUrl);
		createRQResponse
			.then()
			.statusCode(201);
		return createRQResponse;
	}

	/**
	 * gets the request mappings for the specified operator
	 *
	 * @param operatorId to get request mapping response for the operator
	 *
	 * @return the mapping response
	 */
	protected Response getRequestMappingEndpoints( String operatorId )
	{
		final String requestMappingEndpoint = MiraklOperatorsData.REQUEST_MAPPING_ENDPOINT.data;
		final String requestUrl = String.format("%s/%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), "operators/" + operatorId, requestMappingEndpoint);
		Response createRQResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		return createRQResponse;
	}

	/**
	 * deletes a request mapping for the specified operator
	 *
	 * @param operatorId
	 *
	 * @return the mapping response
	 */
	protected Response deleteRequestMappingEndpoint( String operatorId, String mappingId )
	{
		ArrayList mapping = getRequestMappingEndpoints(operatorId)
			.body()
			.path("id");
		final String requestMappingEndpoint = MiraklOperatorsData.REQUEST_MAPPING_ENDPOINT.data;
		final String requestUrl = String.format("%s%s%s%s", MiraklDeclareGlobals
			.getInstance()
			.getMiraklApiUrl(), "/operators/" + operatorId, requestMappingEndpoint, "/" + mappingId);
		Response createRQResponse = generateRequestSpecification()
			.when()
			.delete(requestUrl);
		return createRQResponse;
	}

	/**
	 * To get the request mapping ID
	 *
	 * @param operatorId for which requesting request mapping id
	 *
	 * @return request mapping id for the particular operator
	 */
	public String getRequestMappingId( String operatorId )
	{
		Response mappingResponse = getRequestMappingEndpoints(operatorId);
		String mappingId = mappingResponse
			.getBody()
			.path("id")
			.toString();
		mappingId = mappingId.replaceAll("[\\[\\](){}]", "");
		return mappingId;
	}

	/**
	 * Delete all operators created through regression suite
	 *
	 * @param operators list of operators created in regression suite
	 */
	public void deleteMiraklGlobalOperators( ArrayList<String> operators )
	{
		for ( String operator : operators )
		{
			deleteOperatorsEndpoint(operator);
		}
	}

	/**
	 * OR21 - Accept order lines
	 *
	 * @param orderID
	 *
	 */
	public void acceptOrder(String orderID) {
		String url = MiraklOperatorsData.MIRAKL_ENDPOINT.data + MiraklOperatorsData.ORDERS_ENDPOINT.data +"/" + orderID + MiraklOperatorsData.ORDER_ACCEPT.data;
		Response response = given().contentType("application/json").header("Authorization", "e67db8ef-0387-4bb3-9903-c2b323e78e19")
				.body("{\n" +
						"  \"order_lines\": [\n" +
						"    {\n" +
						"      \"accepted\": true,\n" +
						"      \"id\": \"" + orderID + "-1" + "\"\n" +
						"    }\n" +
						"  ]\n" +
						"}").put(url);

		response.then().statusCode(204);

	}

	/**
	 * OR11 - List orders in received state
	 *
	 * @param orderID
	 *
	 * @return response for list orders get
	 */
	public Response listOrders(String orderID) {
		String url = MiraklOperatorsData.MIRAKL_ENDPOINT.data + MiraklOperatorsData.ORDERS_ENDPOINT.data
				+ MiraklOperatorsData.ORDERS.data + orderID;
		Response listOrdersResponse = given().header("Authorization","e67db8ef-0387-4bb3-9903-c2b323e78e19").get(url);

		listOrdersResponse.then().statusCode(200);

		return listOrdersResponse;
	}
}