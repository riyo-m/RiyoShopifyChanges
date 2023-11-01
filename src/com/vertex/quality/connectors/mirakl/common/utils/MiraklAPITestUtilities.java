package com.vertex.quality.connectors.mirakl.common.utils;

import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * a utility class for api testing of Mirakl connector
 *
 * @author rohit-mogane
 */
public class MiraklAPITestUtilities extends VertexAPITestUtilities
{
	@Override
	protected RequestSpecification generateRequestSpecification( )
	{
		RequestSpecification initialRequestSpecification = super
			.generateRequestSpecification()
			.auth()
			.none()
			.header("Authorization", "63388d70-95ee-4136-ba1b-19f865c88102")
			.header("Connection", "keep-alive")
			.contentType(MiraklOperatorsData.DEFAULT_CONTENT_TYPE.data);
		return initialRequestSpecification;
	}

	/**
	 * Request specification for operator APIs
	 *
	 * @return initialRequestSpecification request specification details
	 */
	protected RequestSpecification generateMiraklRequestSpecificationOperator( )
	{
		RequestSpecification initialRequestSpecification = super
			.generateRequestSpecification()
			.auth()
			.none()
			.header("Authorization", "3ab3af00-d6c0-4d04-8cc8-78d81a7acdfe")
			.header("Connection", "keep-alive")
			.contentType(MiraklOperatorsData.DEFAULT_CONTENT_TYPE.data);
		return initialRequestSpecification;
	}

	/**
	 * specification for front api
	 *
	 * @return initialRequestSpecification request specification details
	 */
	protected RequestSpecification generateMiraklRequestSpecificationFront( )
	{
		RequestSpecification initialRequestSpecification = super
			.generateRequestSpecification()
			.auth()
			.none()
			.header("Authorization", "e67db8ef-0387-4bb3-9903-c2b323e78e19")
			.header("Connection", "keep-alive")
			.contentType(MiraklOperatorsData.DEFAULT_CONTENT_TYPE.data);
		return initialRequestSpecification;
	}

	/**
	 * This method gets categories from json input file
	 *
	 * @param input json file input for categories
	 *
	 * @return categories in string format
	 */
	protected String getCategories( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject categoriesObject = new JSONObject(inputToken);
		Object categories = categoriesObject.get("categories_input");
		return categories.toString();
	}

	/**
	 * This method gets operators from json input file
	 *
	 * @param input json file input for operators
	 *
	 * @return operators in string format
	 */
	protected String getOperators( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject operatorsObject = new JSONObject(inputToken);
		JSONObject operators = (JSONObject) operatorsObject.get("operators_input");
		int randomOperatorId = getRandomNumber();
		operators.put("id", randomOperatorId);
		operators.put("name", "OP" + randomOperatorId);
		return operators.toString();
	}

	/**
	 * converts the request mapping data to a string
	 *
	 * @param input      mapping input from json data file
	 * @param operatorId for the particular operator
	 *
	 * @return mapping json string
	 */
	protected String getMappingData( InputStream input, String operatorId )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject operatorsObject = new JSONObject(inputToken);
		JSONObject operators = (JSONObject) operatorsObject.get("mappings_input");
		operators.put("operatorId", operatorId);
		return operators.toString();
	}

	/**
	 * This method gets countries from json input file
	 *
	 * @param input json file input for countries
	 *
	 * @return countries in string format
	 */
	protected String getCountries( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject operatorsObject = new JSONObject(inputToken);
		JSONObject countries = (JSONObject) operatorsObject.get("countries_input");
		return countries.toString();
	}

	/**
	 * This method gets orders from json input file
	 *
	 * @param input json file input for orders
	 *
	 * @return orders in string format
	 */
	protected String getOrders( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject ordersObject = new JSONObject(inputToken);
		JSONObject orders = (JSONObject) ordersObject.get("orders_input");
		orders.put("commercial_id", "MIR-CUSTOM-ORDER-" + getDateTime());
		return orders.toString();
	}

	/**
	 * This changes the commercial_id of a given order's json data
	 * to create a new order
	 *
	 * @param input json file input for orders
	 *
	 * @return the new orders data
	 */
	protected String changeOrderid( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject ordersObject = new JSONObject(inputToken);
		JSONObject orders = ordersObject.put("commercial_id", "MIR-CUST-ORDER-" + getDateTime());
		return orders.toString();
	}

	/**
	 * This method gets shops from json input file
	 *
	 * @param input json file input for shops
	 *
	 * @return shop in string format
	 */
	protected String getShops( InputStream input )
	{
		JSONTokener inputToken = new JSONTokener(input);
		JSONObject ordersObject = new JSONObject(inputToken);
		JSONObject orders = (JSONObject) ordersObject.get("shops_input");
		orders.put("shop_name", "MIR-CUSTOM-SHOP-" + getDateTime());
		return orders.toString();
	}

	/**
	 * This method gets the operator status from operator json data file
	 *
	 * @param requestUrl
	 * @param operatorId
	 *
	 * @return the status of the operator
	 */
	public String getOperatorStatus( String requestUrl, String operatorId )
	{
		String taxEngineStatus;
		String miraklApiStatus;
		String generalStatus;
		String operatorStatus = "Operator Not Found!";
		Response healthCheckResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		healthCheckResponse
			.then()
			.statusCode(200);
		ArrayList<HashMap<String, String>> operators = healthCheckResponse
			.body()
			.path("detail.operators");
		for ( HashMap<String, String> operator : operators )
		{
			if ( operator
				.get("id")
				.equals(operatorId) )
			{
				taxEngineStatus = operator.get("taxEngineStatus");
				miraklApiStatus = operator.get("miraklApiStatus");
				generalStatus = operator.get("status");

				if ( taxEngineStatus.equals("UP") && miraklApiStatus.equals("UP") && generalStatus.equals("READY") )
				{
					operatorStatus = "UP";

					return operatorStatus;
				}
				else
				{
					String taxEngine = "Tax engine status is: " + taxEngineStatus + "\n";
					String miraklaApi = "Mirakl Api status is: " + miraklApiStatus + "\n";
					String general = "Status is: " + generalStatus + "\n";

					operatorStatus = String.format("%s%s%s", taxEngine, miraklaApi, general);

					return operatorStatus;
				}
			}
		}
		return operatorStatus;
	}

	/**
	 * This method gets the connector status from a requested connector json response
	 *
	 * @param requestUrl
	 *
	 * @return the status of the operator
	 */
	public String getConnectorStatus( String requestUrl )
	{
		String connectorVersion;
		String databaseStatus;
		String detailConnectorVersion;
		String detailDBStatus;
		String connectorStatus = "Health Check failed; Version incorrect or Database down";
		Response healthCheckResponse = generateRequestSpecification()
			.when()
			.get(requestUrl);
		healthCheckResponse
			.then()
			.statusCode(200);
		connectorVersion = healthCheckResponse
			.body()
			.path("connectorVersion");
		databaseStatus = healthCheckResponse
			.body()
			.path("databaseStatus");
		detailConnectorVersion = healthCheckResponse
			.body()
			.path("detail.connectorStatus.version");
		detailDBStatus = healthCheckResponse
			.body()
			.path("detail.databaseStatus.status");

		if ( connectorVersion.equals(MiraklOperatorsData.MIRAKL_VERSION.data) & databaseStatus.equals(
			MiraklOperatorsData.MIRAKL_DATABASE_STATUS.data) & detailConnectorVersion.equals(
			MiraklOperatorsData.MIRAKL_VERSION.data) & detailDBStatus.equals(
			MiraklOperatorsData.MIRAKL_DATABASE_STATUS.data) )
		{
			connectorStatus = "UP";

			return connectorStatus;
		}

		return connectorStatus;
	}

	/**
	 * get random number for operator ID
	 *
	 * @return randomNumber random number in integer format
	 */
	protected int getRandomNumber( )
	{
		Random random = new Random();
		int randomNumber = random.nextInt(9000) + 1000;
		return randomNumber;
	}

	/**
	 * get random value from array for creating orders and shops
	 *
	 * @param randomArray JSONArray from json data file (orders and shops)
	 *
	 * @return random value from JSONArray
	 */
	protected Object getRandomArrayVal( JSONArray randomArray )
	{
		int index = new Random().nextInt(randomArray.length());
		return (Object) randomArray.get(index);
	}

	/**
	 * get current date time
	 *
	 * @return formatted date time
	 */
	protected String getDateTime( )
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	/**
	 * Gets current date and time and formats it for use in api requests
	 *
	 * @return formatted date
	 */
	public String getDateForAPIRequest( )
	{
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		LocalDateTime currentDayAndTime = LocalDateTime.now();
		String dateTime = dateFormatter.format(currentDayAndTime.minusHours(1));
		return dateTime;
	}

	/**
	 * get current date
	 *
	 * @return formatted date for orders API
	 */
	protected String getCurrentDate( )
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		return "from=" + dtf.format(now);
	}
}
