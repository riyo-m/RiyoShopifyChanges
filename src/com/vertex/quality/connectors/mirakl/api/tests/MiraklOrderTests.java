package com.vertex.quality.connectors.mirakl.api.tests;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.connectors.mirakl.api.base.MiraklAPIBaseTest;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklAPITestUtilities;
import com.vertex.quality.connectors.mirakl.common.utils.MiraklDeclareGlobals;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class MiraklOrderTests extends MiraklAPIBaseTest
{
	private String operatorId = null, orderCreationDate = null;

	/**
	 * Test to create custom single orders
	 */
	@Test(groups = { "mirakl_regression" })
	public void createCustomOrderTest( )
	{
		miraklOrderString = getOrdersData();
		Response response = createOrdersEndpoint(miraklOrderString);
		orderCreationDate = response
			.getBody()
			.path("orders[0].created_date");
		Assert.assertEquals(response.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * Test doing sync and get orders
	 */
	@Test(groups = { "mirakl_regression" })
	public void pullMiraklOrdersTest( )
	{
		createCustomOrderTest();
		operatorId = createSpecificOperatorsEndpoint();
		Response syncResponse;
		String syncPayload;
		syncResponse = putSync(operatorId);
		syncPayload = syncResponse
			.body()
			.path("orders.status[0].state");
		MiraklDeclareGlobals
			.getInstance()
			.getArrayList()
			.add(operatorId);
		assertEquals(syncPayload, MiraklOperatorsData.ORDER_STATUS_WAITING.data);
		Response ordersResponse = getOrders(operatorId);
		String ordersPayload = ordersResponse
			.body()
			.path("content[0].status");
		assertEquals(ordersResponse.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
		assertEquals(ordersPayload, MiraklOperatorsData.ORDER_STATUS_PROCESSED.data);
	}

	/**
	 * Test doing getting outbound logs and outbound log details
	 */
	@Test(groups = { "mirakl_regression" })
	public void getOutboundOrderLogsTest( )
	{
		pullMiraklOrdersTest();
		Response outboundLogsResponse;
		ArrayList<JSONObject> outboundPayload;
		do
		{
			outboundLogsResponse = getOutboundLogs(operatorId);
			outboundPayload = outboundLogsResponse
				.body()
				.path("content");
		}
		while ( outboundPayload.size() == 0 );
		Response outboundLogsDetailResponse = getOutboundLogDetails(operatorId);
		assertEquals(outboundLogsDetailResponse.getStatusCode(), ResponseCodes.SUCCESS.getResponseCode());
	}

	/**
	 * Tests inbound log responses for the "freight" value
	 */
	@Test(groups = { "mirakl_regression" })
	public void shipFromAddressTaxTest( )
	{
		pullMiraklOrdersTest();
		Response inboundLogsDetailResponse = getInboundLogDetails(operatorId);
		String inboundLogsPayload = getInboundTaxEngineRequestLog(inboundLogsDetailResponse);
		assertTrue(
			inboundLogsPayload.contains("<Freight>" + MiraklOperatorsData.PARIS_FREIGHT_VALUES.data + "</Freight>"));
	}

	/**
	 * Test to ensure that Price and Shipping price are properly grouped for line items
	 * based on shipping location
	 *
	 * MIR-373
	 */
	@Test(groups = { "mirakl_regression" })
	public void consignmentTest( )
	{
		String miraklId = getMiraklOperatorId();
		miraklOrderString = getMultiLineData();
		createOrdersEndpoint(miraklOrderString);
		putSync(miraklId);
		Response inboundLogsDetailResponse = getInboundLogDetails(miraklId);
		String inboundLogsPayload = getInboundTaxEngineRequestLog(inboundLogsDetailResponse);
		// Address 1
		// 165.00 is the combined price of the first three line items in this order along with their shipping cost
		assertTrue(inboundLogsPayload.contains(
			"<FlexibleFields>\n" + "        <FlexibleNumericField fieldId=\"10\">165.00</FlexibleNumericField>\n" +
			"      </FlexibleFields>"));
		// Address 2
		// 176.00 is the combined price of the last two line items along with their shipping price
		assertTrue(inboundLogsPayload.contains(
			" <FlexibleFields>\n" + "        <FlexibleNumericField fieldId=\"10\">176.00</FlexibleNumericField>\n" +
			"      </FlexibleFields>"));
	}

	/**
	 * Test to create demo orders
	 * MIR-808
	 */
	@Test(groups = { "mirakl_bulk" })
	public void createTenOrdersTest( )
	{
		createBulkOrders(10);
	}

	/**
	 * Test to create demo orders
	 * MIR-352
	 */
	@Test(groups = { "mirakl_bulk" })
	public void createOrdersTest( )
	{
		createBulkOrders(1);
	}

	/**
	 * Checks order logs for the 'shipping_from' value
	 *
	 * MIR-476
	 */
	@Test(groups = { "mirakl_regression" })
	public void shipFromAddressOrderTest( )
	{
		createCustomOrderTest();
		Response orderLogResponse = getOrderLogs(orderCreationDate);
		LinkedHashMap orderShipFrom = getOrderLinesFromLog(orderLogResponse);
		assertTrue(orderShipFrom.containsKey("shipping_from"));
	}

	/**
	 * Submits and order in Mirakl. Checks to make sure order is processed and not listed in OR11 get orders
	 *
	 * MIR-653
	 */
	@Test(groups = { "mirakl_regression" })
	public void OrderProcessedTestTest( )
	{
		miraklOrderString = getOrdersData();
		Response response = createOrdersEndpoint(miraklOrderString);
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		String orderID = response
				.getBody()
				.path("orders[0].order_id");
		System.out.println(orderID);
		acceptOrder(orderID);
		putSync(operatorId);
		Response listOrdersResponse = listOrders(orderID);
		int totalCount = listOrdersResponse.getBody()
			.path("total_count");
		Assert.assertEquals(totalCount,0);
	}
}