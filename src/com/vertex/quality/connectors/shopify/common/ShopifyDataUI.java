package com.vertex.quality.connectors.shopify.common;

/**
 * Data Class that contains multiple enums for Shopify store
 *
 * @author Shivam.Soni
 */
public class ShopifyDataUI
{

	/**
	 * Name of the Vertex's apps on Shopify domain
	 */
	public enum VertexAppNames
	{
		VERTEX_TAX_STAGE("Vertex Tax (Stage)"),
		VERTEX_PROD("Vertex Tax & Compliance"),
		VERTEX_TAX("Vertex Tax"),
		VERTEX_PRE_PROD("Vertex PrePROD");

		public String text;

		VertexAppNames( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Name of all the O Series instances which are used in Shopify Connector
	 */
	public enum OSeriesInstanceNames
	{
		ON_PREM("On Prem"),
		VOD_DEV("VOD Dev"),
		VOD("VOD"),
		CLASSIC_CLOUD_QA("Classic Cloud QA"),
		CLASSIC_CLOUD_STAGE("Classic Cloud Stage"),
		OSC_QA("OSC QA"),
		OSC_STAGE("OSC Stage");

		public String text;

		OSeriesInstanceNames( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Name of the Vertex's apps on Shopify domain
	 */
	public enum OSeriesDetails
	{
		VOD_CLIENT_ID("ad8cf954d9a5.vertexinc.com"),
		VOD_CLIENT_SECRET("25fcc6816ad0a45cd906b5d8df12a577d487c182979b49316f166c732ba6611d"),
		VOD_COMPANY_CODE("ShopifyVOD"),
		VOD_TAX_CALC_URL("https://connectortest.dev.ondemand.vertexinc.com"),
		QA_OSC_CLIENT_ID("d172149a2f294311bed57307851820d8"),
		QA_OSC_CLIENT_SECRET("9b93e19bea0846529c72bd41333195f9"),
		QA_OSC_COMPANY_CODE("ShopifyQA"),
		STAGE_OSC_CLIENT_ID("a98bb9705d324890929e53c84ccc5aa1"),
		STAGE_OSC_CLIENT_SECRET("e774794429594dfabe460903da02bc21"),
		STAGE_OSC_COMPANY_CODE_1("ShopifyStage"),
		STAGE_OSC_COMPANY_CODE_2("Shopify");

		public String text;

		OSeriesDetails( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Name of the Vertex's apps on Shopify domain
	 */
	public enum AdditionalFees
	{
		CO_RDF(0.28);

		public double value;

		AdditionalFees( double value )
		{
			this.value = value;
		}
	}

	/**
	 * Shopify store's data
	 */
	public enum StoreData
	{
		SHOPIFY_PARTNER_DASHBOARD("https://shopify.dev/"),
		VTX_QA_STORE("https://vtxqa.myshopify.com/"),
		VTX_PROD_STORE("https://prodtestvettexstore.myshopify.com/"),
		VTX_PRE_PROD_STORE("https://vertexpreprodstore.myshopify.com/"),
		QA_STORE("https://vertex-qa.myshopify.com/"),
		EKS_STORE("https://vertex-eks.myshopify.com/"),
		VTX_QA_STORE_KEY("Test123#"),

		VTX_QA_STORE_USER("test_vertex@shopify.com"),
		VTX_QA_STORE_USER_KEY("Test@123"),
		VTX_QA_EXEMPT_USERNAME("Exempt Certi"),
		VTX_QA_EXEMPT_EMAIL("exempt.certi@shopify.com"),
		VTX_QA_EXEMPT_KEY("Test@123"),
		VTX_QA_VTX_CUSTOMER_CODE_USERNAME("Vertex Customer Code"),
		VTX_QA_VTX_CUSTOMER_CODE_EMAIL("vtx.cc@shopify.com"),
		VTX_QA_VTX_CUSTOMER_CODE_KEY("Test@123"),
		VTX_QA_SHOPIFY_EXEMPTED_USERNAME("Shopify Exempted"),
		VTX_QA_SHOPIFY_EXEMPT_EMAIL("shopify.exempt@shopify.com"),
		VTX_QA_SHOPIFY_EXEMPT_KEY("Test@123"),
		VTX_QA_VTX_TAXABLE_USERNAME("Vertex Taxable"),
		VTX_QA_VTX_TAXABLE_EMAIL("vtx.taxable@shopify.com"),
		VTX_QA_VTX_TAXABLE_KEY("Test@123"),
		DEFAULT_CUSTOMER_FIRST_NAME("Auto"),
		DEFAULT_CUSTOMER_LAST_NAME("Test"),
		DEFAULT_CUSTOMER_EMAIL("auto.test@shopify.com");

		public String text;

		StoreData( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify Admin panel's data
	 */
	public enum AdminPanelData
	{
		VTX_QA_ADMIN_PANEL("https://admin.shopify.com/store/vtxqa/"),

		SHOPIFY_DEV("https://shopify.dev/"),
		SHOPIFY_ADMIN("https://www.shopify.com/"),
		VTX_PROD_ADMIN_PANEL("https://admin.shopify.com/store/prodtestvettexstore/"),
		VTX_PRE_PROD("https://admin.shopify.com/store/vertexpreprodstore/"),

		VERTEX_QA_ADMIN_PANEL("https://admin.shopify.com/store/vertex-qa/"),
		VERTEX_EKS_ADMIN_PANEL("https://admin.shopify.com/store/vertex-eks/"),
		SHOPIFY_VTX_ON_BOARDING("https://shopify.cst-stage.vtxdev.net/shopify/onboarding"),
		VTX_QA_ADMIN_USER("connectorsdevelopment@vertexinc.com"),

		VTX_PROD_ADMIN_USER("connectorsdevelopment+shopify@vertexinc.com"),
		VTX_PROD_ADMIN_USER_KEY("&Rl2AOT@x&I9U@Ui"),

		VTX_QA_ADMIN_USER_KEY("Test123#"),
		DEFAULT_CUSTOMER_EMAIL("auto.test@shopify.com");

		public String text;

		AdminPanelData( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify On-boarding page's error messages
	 */
	public enum OnBoardingErrorMSG
	{
		UN_AUTHORIZED_ACCESS("Unauthorized Access"),
		EMPTY_COMPANY_CODE("Company Code is required"),
		EMPTY_CLIENT_ID("Client ID is required"),
		EMPTY_CLIENT_SECRET("Client Secret is required"),
		WRONG_CREDENTIALS("Client ID or Client Secret is incorrect");

		public String text;

		OnBoardingErrorMSG( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify Admin panel's left bar navigation menu options
	 */
	public enum AdminPanelLeftNavigationOptions
	{
		HOME("Home"),
		ORDERS("Orders"),
		PRODUCTS("Products"),
		CUSTOMERS("Customers"),
		CONTENT("Content"),
		FINANCES("Finances"),
		ANALYTICS("Analytics"),
		MARKETING("Marketing"),
		DISCOUNTS("Discounts"),
		SETTINGS("Settings");

		public String text;

		AdminPanelLeftNavigationOptions( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify -> Shopify Order and Order's payment status
	 */
	public enum ShopifyOrderAndPaymentStatus
	{
		PAYMENT_PENDING("Payment pending"),
		UNPAID("Unpaid"),
		OPEN("Open"),
		CLOSED("Closed"),
		PAID("Paid"),
		UNFULFILLED("Unfulfilled"),
		PARTIALLY_FULFILLED("Partially fulfilled"),
		FULFILLED("Fulfilled"),
		ARCHIVED("Archived"),
		IN_PROGRESS("In progress"),
		ON_HOLD("On hold"),
		PARTIALLY_REFUNDED("Partially refunded"),
		RETURN_IN_PROGRESS("Return in progress"),
		REFUNDED("Refunded"),
		RETURNED("Returned");

		public String text;

		ShopifyOrderAndPaymentStatus( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify Admin -> Setting's left bar navigation menu options
	 */
	public enum SettingsLeftNavigationOptions
	{
		STORE_DETAILS("Store details"),
		TAXES_AND_DUTIES("Taxes and duties"),
		LOCATIONS("Locations"),
		APPS_AND_SALES_CHANNELS("Apps and sales channels");

		public String text;

		SettingsLeftNavigationOptions( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify Admin panel's data
	 */
	public enum AdminPanelOrderBulkEditOptions
	{
		CREATE_SHIPPING_LABELS("Create shipping labels"),
		MARK_AS_FULFILLED("Mark as fulfilled"),
		CAPTURE_PAYMENTS("Capture payments");

		public String text;

		AdminPanelOrderBulkEditOptions( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify store's Products
	 */
	public enum Products
	{
		SHOPIFY_GIFT_CARD("Shopify Gift card"),
		SUPER_MARIO_GAME("Super Mario Game"),
		COLLECTION_SNOWBOARD_HYDROGEN("The Collection Snowboard: Hydrogen"),
		THREE_P_FULFILLED_SNOWBOARD("The 3p Fulfilled Snowboard"),
		SHOPIFY_EXEMPTED_PRODUCT("Shopify Exempted Product"),
		VERTEX_EXEMPTED_PRODUCT("Vertex Exempted Product");

		public String text;

		Products( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify Quantities
	 */
	public enum Quantities
	{
		QTY_1("1"),
		QTY_3("3"),
		QTY_5("5"),
		QTY_10("10");

		public String text;

		Quantities( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify amounts -> using in adjustments
	 */
	public enum AdjustmentAmount
	{
		AMOUNT_100("100"),
		AMOUNT_250("250"),
		AMOUNT_500("500");

		public String text;

		AdjustmentAmount( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Shopify store's Shipping Methods
	 */
	public enum ShippingMethods
	{
		FREE("Free"),
		STANDARD("Standard"),
		INTERNATIONAL("International Shipping");

		public String text;

		ShippingMethods( String text )
		{
			this.text = text;
		}
	}

	/**
	 * Dummy Credit Card details
	 */
	public enum CreditCard
	{
		CARD_NUMBER("4242424242424242"),
		NAME_ON_CARD("Auto Test"),
		EXPIRY("12 / 29"),
		SECRET_CODE("111");

		public String text;

		CreditCard( String text )
		{
			this.text = text;
		}
	}
}
