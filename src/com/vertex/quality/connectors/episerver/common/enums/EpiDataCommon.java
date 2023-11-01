package com.vertex.quality.connectors.episerver.common.enums;

public class EpiDataCommon
{
	public enum DefaultAmounts
	{
		// @formatter:off
		DEFAULT_QUANTITY("1"),
		DEFAULT_ADD_ORDER_DISCOUNT("-0.00"),
		DEFAULT_DISCOUNT("0.00"),
		DEFAULT_SHIPPING_DISCOUNT("-10.00"),
		DEFAULT_HANDLING_COST("0.00"),
		PRODUCT_QUANTITY_10("10"),
		PRODUCT_QUANTITY("7");
		// @formatter:on

		public String text;

		DefaultAmounts( String text )
		{
			this.text = text;
		}
	}

	public enum ProductPrices
	{
		// @formatter:off
		PRODUCT_1_UNIT_PRICE("29.50"),
		PRODUCT_2_UNIT_PRICE("24.50"),
		PRODUCT_3_UNIT_PRICE("16.50"),
		PRODUCT_4_UNIT_PRICE("14.00"),
		PRODUCT_5_UNIT_PRICE("18.50"),
		PRODUCT_6_UNIT_PRICE("33.50"),
		PRODUCT_7_UNIT_PRICE("84.50"),
		PRODUCT_8_UNIT_PRICE("33.50");
		// @formatter:on

		public String text;

		ProductPrices( String text )
		{
			this.text = text;
		}
	}

	public enum DeliveryMethodCosts
	{
		// @formatter:off
		REGULAR_SHIPPING_SUB_TOTAL("5.00"),
		FAST_SHIPPING_SUB_TOTAL("15.00"),
		FAST_SHIPPING_TOTAL("5"),
		EXPRESS_SHIPPING_SUB_TOTAL("20.00");
		// @formatter:on

		public String text;

		DeliveryMethodCosts( String text )
		{
			this.text = text;
		}
	}

	public enum Taxes
	{
		// @formatter:off
		TAX_0("0.00"),
		TAX_1("0.99"),
		TAX_3("0.48"),
		TAX_4("5.35"),
		TAX_5("2.44"),
		TAX_6("3.06"),
		TAX_7("5.03"),
		TAX_8("6.67"),
		TAX_9("1.67"),
		TAX_10("2.83"),
		TAX_11("1.82"),
		TAX_12("2.95"),
		TAX_13("3.95"),
		TAX_14("2.80"),
		TAX_15("3.24"),
		TAX_16("47.00"),
		TAX_17("2.37"),
		TAX_18("3.06"),
		TAX_19("4.96"),
		TAX_20("3.45"),
		TAX_21("2.96"),
		TAX_22("1.15"),
		TAX_COST("47.00"),
		TAX_23("7.04");
		// @formatter:on

		public String text;

		Taxes( String text )
		{
			this.text = text;
		}
	}

	public enum DiscountPrice
	{
		// @formatter:off
		ADDITIONAL_DISCOUNT("-50.00"),
		PRODUCT_DISCOUNT("6.70");
		// @formatter:on

		public String text;

		DiscountPrice( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Enum or Data that contains different URLs of Epi Server connector
	 *
	 * @author Shivam.Soni
	 */
	public enum EpiURLs {

		EPI_COMMERCE_MANAGER_URL("http://3.19.98.0:8081/"),
		EPI_STORE_URL("http://3.19.98.0/"),
		EPI_STORE_URL_324("http://3.19.98.0:8083"),
		EPI_STORE_URL_325("http://172.28.11.28:8083");

		public String text;

		EpiURLs( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Enum or Data that contains different URLs of Epi Server connector
	 *
	 * @author Shivam.Soni
	 */
	public enum EpiCredentials {

		EPI_324_CUSTOMER_CLASS_USER("test_epi@vertexinc.com"),
		EPI_324_CUSTOMER_CODE_USER("connectorsdevelopment@vertexinc.com"),
		EPI_324_STORE_USER("admin@example.com"),
		EPI_324_STORE_PASS("Episerver123!");

		public String text;

		EpiCredentials( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Enum that contains warehouse names of Episerver
	 *
	 * @author Shivam.Soni
	 */
	public enum WarehouseNames {
		HANOI_STORE("Hanoi store"),
		LONDON_STORE("London store"),
		NEW_YORK_STORE("New York store"),
		STOCKHOLM_STORE("Stockholm store"),
		SYDNEY_STORE("Sydney store"),
		TOKYO_STORE("Tokyo store");

		public String text;

		WarehouseNames(String text)
		{
			this.text = text;
		}
	}

	/**
	 * Enum that contains different product names of Episerver's store
	 *
	 * @author Shivam.Soni
	 */
	public enum ProductNames {
		EXEMPTED_PRODUCT1("SKU-41071811"),
		FADED_GLORY_MENS_SHOES("SKU-36127195"),
		PUMA_RED_LEATHER_LOAFERS("SKU-44477844"),
		PUMA_BLACK_SNEAKERS("SKU-39850363"),
		WOMEN_CLOSED_TOE_SHOES("SKU-36921911"),
		WOMEN_HIGH_HEEL_SHOES("SKU-37323420"),
		CLASSIC_MID_HEELED("SKU-36276861");

		public String text;

		ProductNames(String text)
		{
			this.text = text;
		}
	}

	/**
	 * Enum that contains default values of order contact details
	 *
	 * @author Shivam.Soni
	 */
	public enum DefaultContactDetails {
		WAREHOUSE_FIRST_NAME("TesterFirstName"),
		WAREHOUSE_LAST_NAME("TesterLastName"),
		WAREHOUSE_ORGANIZATION("Test Organization"),
		WAREHOUSE_DEFAULT_CONTACT("9876543210"),
		WAREHOUSE_EMAIL_DOMAIN("@example.com"),
		CUSTOMER_FIRST_NAME("AutomationFirstName"),
		CUSTOMER_LAST_NAME("AutomationLastName"),
		CUSTOMER_CONTACT_NO("1234567890"),
		CUSTOMER_EMAIL_ID("automation.tester@test.com");

		public String text;

		DefaultContactDetails(String text)
		{
			this.text = text;
		}
	}

	/**
	 * Epi-commerce order status
	 *
	 * @author Shivam.Soni
	 */
	public enum OrderStatus {
		ON_HOLD("On Hold"),
		PARTIALLY_SHIPPED("Partially Shipped"),
		IN_PROGRESS("In Progress"),
		COMPLETED("Completed"),
		CANCELLED("Cancelled"),
		AWAITING_EXCHANGE("Awaiting Exchange");

		public String text;

		OrderStatus(String text)
		{
			this.text = text;
		}
	}

	/**
	 * Epi-commerce tax rate percentages
	 *
	 * @author Shivam.Soni
	 */
	public enum TaxRates {
		PA_CO_TAX(8.2),
		PA_LA_TAX(9.45),
		PA_TAX(6.0),
		PA_WA_TAX(10.1),
		PA_WI_TAX(5.5),
		PA_UT_TAX(7.45),
		CANBC_CANQC_TAX(14.975),
		CANBC_CANON_TAX(13.0),
		CANQC_CANBC_SHIPPING_TAX(7.0),
		CANQC_CANBC_TAX(12.0),
		CANNB_CANNB_TAX(15.0),
		CAN_VICTORIA_TAX(12.0),
		DE_DE_TAX(19.0),
		DE_FR_TAX(20.0),
		US_FR_TAX(20.0),
		CR_CO_TAX(19.0),
		FR_DE_TAX(19.0),
		FR_DE_SUP_TAX(19.0),
		FR_DE_CUS_TAX(20.0),
		SW_DE_CUS_TAX(25.0),
		FR_DE_DDP_TAX(19.0),
		FR_DE_EXW_TAX(19.0),
		DE_DE_DDP_TAX(19.0),
		SG_JP_SUP_TAX(10.0),
		FR_GR_SUP_TAX(24.0),
		DE_AT_SUP_TAX(20.0),
		INDIA_GST(18.0),
		LATAM_BZ_BZ_TAX(12.5),
		ZERO_TAX(0.0);

		public double tax;

		TaxRates(double tax)
		{
			this.tax = tax;
		}
	}

	/**
	 * Epi-commerce coupons
	 *
	 * @author Shivam.Soni
	 */
	public enum Coupons {
		TEN_DOLLAR_ITEM("10DollarItem"),
		FIVE_PERCENT_ITEM("5PercentItem"),
		FIVE_DOLLAR_ITEM("5DollarItem"),
		ONE_OFF_SHIP("1OFFShip");

		public String text;

		Coupons(String text)
		{
			this.text = text;
		}
	}

	/**
	 * Discount amount or percent values
	 *
	 * @author Shivam.Soni
	 */
	public enum CouponAmountOrPercent {
		VALUE_ONE("1"),
		VALUE_TEN("10"),
		VALUE_FIVE("5");

		public String text;

		CouponAmountOrPercent(String text)
		{
			this.text = text;
		}
	}
}
