package com.vertex.quality.connectors.mirakl.api.enums;

/**
 * contains data used for Mirakl connector tests
 *
 * @author rohit-mogane
 */
public enum MiraklOperatorsData
{
	MIRAKL_VERSION("0.0.3"),
	MIRAKL_STATUS("UP"),
	MIRAKL_DATABASE_STATUS("UP"),
	OPERATOR_ORDERS_DATE("T00:00:00Z&pageSize=1"),
	API_URL("https://mirakl.qa.vertexconnectors.com/api"),
	VOD_API_URL("https://mirakltesting.eu.ondemand.vertexinc.com/connector-mirakl/api"),
	MIRAKL_ENDPOINT("https://vertexfr-dev.mirakl.net/api"),
	HEALTHCHECK_API_ENDPOINT("/status/public"),
	PRIVATE_HEALTHCHECK_API_ENDPOINT("/status"),
	DEFAULT_CONTENT_TYPE("application/json"),
	MIRAKL_DATA_JSON("/mirakl/mirakl_data_positive.json"),
	MULTI_LINE_SHIPPING_DATA_JSON("/mirakl/mirakl_data_multi_line_shipping.json"),
	MIRAKL_NEGATIVE_DATA_JSON("/mirakl/mirakl_data_negative.json"),
	MIRAKL_DATA_ORDERS_JSON("/mirakl/mirakl_data_orders.json"),
	MIRAKL_DATA_STORES_JSON("/mirakl/mirakl_data_shops.json"),
	OPERATORS_ENDPOINT("/operators"),
	REQUEST_MAPPING_ENDPOINT("/requestmappings"),
	CATEGORIES_ARRAY_KEY("categories"),
	SHOPS_ARRAY_KEY("shops"),
	SHOPS("shops?shop_ids="),
	ORDERS("?order_ids="),
	COUNTRIES_ENDPOINT("/countries"),
	ORDERS_ENDPOINT("/orders"),
	SHOPS_ENDPOINT("/shops"),
	ACTIVITY_STATUS_COMPLETED("Completed Successfully"),
	ACTIVITY_STATUS_RUNNING("Running"),
	ORDER_STATUS_WAITING("WAITING_ACCEPTANCE"),
	ORDER_STATUS_PROCESSED("PROCESSED"),
	SYNC_ENDPOINT("orders/inbound"),
	FAILURES("failures"),
	OUTBOUND_ORDERS("orders/outbound"),
	OUTBOUND_ORDER_DETAILS("orders/outbound/details"),
	INBOUND_ORDER_DETAILS("orders/inbound/details"),
	ORDER_ACCEPT("/accept"),
	OSERIES_URL_NEW("https://oseries9-final.vertexconnectors.com/"),
	OSERIES_QA_USERNAME("mirakl-qa-user"),
	OSERIES_QA_PASSWORD("mirakl-qa-pw"),
	OSERIES_AS_OF_DATE("2021-01-01"),
	PARIS_FREIGHT_VALUES("8.00");

	public String data;

	MiraklOperatorsData( String data )
	{
		this.data = data;
	}
}
