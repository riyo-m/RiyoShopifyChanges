package com.vertex.quality.connectors.saptaxservice.utils;

import com.vertex.quality.common.enums.*;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxCode;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.pojos.*;
import io.restassured.response.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.List;

/**
 * a utility class for api testing of sap chain flow accelerator tax service
 *
 * @author unaqvi
 */
public class SAPCFATaxServiceAPITestUtilities extends VertexAPITestUtilities
{
	public static final DBConnectorNames CONNECTOR_NAME = DBConnectorNames.CHAIN_FLOW_ACCELERATOR;
	public static final DBEnvironmentNames ENVIRONMENT_NAME = DBEnvironmentNames.QA;
	public static final DBEnvironmentDescriptors ENVIRONMENT_DESCRIPTOR
		= DBEnvironmentDescriptors.SAP_CHAIN_FLOW_TAX_SERVICE_QUOTE;

	public static final String ID = "123456";
	public static final String DATE = "2018-08-14T04:00:00.000Z";
	public static final String EU_CURRENCY = "EUR";
	public static final String SALE = "s";
	public static final String PURCHASE = "p";
	public static final String GROSS = "g";
	public static final String NET = "n";
	public static final String YES = "y";
	public static final String NO = "n";
	public static final String ITEM_TYPE_MATERIAL = "m";
	public static final String ITEM_TYPE_SERVICE = "s";
	public static final String STRING_VALUE = "string";
	public static final int SUCCESS_RESPONSE_CODE = ResponseCodes.SUCCESS.getResponseCode();
	public static final int BAD_REQUEST_RESPONSE_CODE = ResponseCodes.BAD_REQUEST.getResponseCode();

	public SAPCFATaxServiceAPITestUtilities( )
	{
		loadAPIAccessInformation();
	}

	@Override
	protected void loadAPIAccessInformation( )
	{
		try
		{
			EnvironmentInformation environmentInformation = SQLConnection.getEnvironmentInformation(CONNECTOR_NAME,
				ENVIRONMENT_NAME, ENVIRONMENT_DESCRIPTOR);
			EnvironmentCredentials environmentCredentials = SQLConnection.getEnvironmentCredentials(
				environmentInformation);
			baseUrl = environmentInformation.getEnvironmentUrl();
			setBasicAuth(environmentCredentials.getUsername(), environmentCredentials.getPassword());
		}
		catch ( Exception e )
		{
			VertexLogger.log("failed to retrieve the base url and credentials for a test of the  SAP tax service API",
				VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sets up a default cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultCFATransaction( final String shipFromCountry, String EU_CURRENCY,
		String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
			.builder()
			.quantity("90")
			.unitPrice("1000.00")
			.id("1")
			.itemType("m")
			.build();
		SAPLocation shipFrom = SAPLocation
			.builder()
			.zipCode("1234")
			.type("SHIP_FROM")
			.country(shipFromCountry)
			.state(shipFromRegion)
			.city(shipFromCity)
			.build();
		SAPLocation shipTo = SAPLocation
			.builder()
			.zipCode("5678")
			.type("SHIP_TO")
			.country(shipToCountry)
			.state(shipToRegion)
			.city(shipToCity)
			.build();
		return SAPQuoteInput
			.builder()
			.id(ID)
			.date(DATE)
			.saleOrPurchase(saleOrPurchase)
			.grossOrNet(grossOrNet)
			.currency(EU_CURRENCY)
			.companyId("2310")
			.Item(itemOne)
			.Location(shipFrom)
			.Location(shipTo)
			.build();
	}

	/**
	 * Sets up a default item classification cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */

	public SAPQuoteInput setupDefaultCFAICTransaction(final String shipFromCountry, String EU_CURRENCY,
			String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem.ItemClassifications classifications = SAPItem.ItemClassifications
				.builder()
				.itemStandardClassificationSystemCode("UNSPSC")
				.itemStandardClassificationCode("55101504")
				.build();
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemType("m")
				.itemClassification(classifications)
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("1234")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5678")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.companyId("2310")
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.build();
	}
	/**
	 * Sets up a default Business Partner cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultBusinessPartnerCFATransaction( final String shipFromCountry, String EU_CURRENCY,
													 String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemType("m")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("1234")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5678")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPBusinessPartnerExemptionDetail BusinessPartner = SAPBusinessPartnerExemptionDetail
				.builder()
				.exemptionReasonCode("1")
				.locationType("SHIP_TO")
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.companyId("2310")
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.BusinessPartnerExemptionDetail(BusinessPartner)
				.build();
	}
	/**
	 * Sets up a default Intra Community cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultIntraCommunityCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																	String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemType("m")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("1234")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5678")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationtwo = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL004445879B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationthree = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT09417760155")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationfour = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL801161988B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.taxRegistration(taxRegistrationtwo)
				.build();
		SAPParty partyTwo = SAPParty
				.builder()
				.id("2")
				.role("SHIP_TO")
				.taxRegistration(taxRegistrationthree)
				.taxRegistration(taxRegistrationfour)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.companyId("2310")
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.Party(partyTwo)
				.build();
	}
	/**
	 * Sets up a default Distance Selling cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultDistanceSellingCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																   String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("2")
				.unitPrice("100.00")
				.id("1")
				.itemCode("H11")
				.itemType("m")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("1234")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5678")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationtwo = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL004445879B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.taxRegistration(taxRegistrationtwo)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.build();
	}
	/**
	 * Sets up a default SM Export cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToCity    the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultExportSMCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																	String shipFromRegion, String shipFromCity, String shipToCountry, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemCode("id12312")
				.itemType("m")
				.shippingCost("N")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("00173")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("47031")
				.type("SHIP_TO")
				.country(shipToCountry)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.build();
	}
	/**
	 * Sets up a default VC Export cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToCity    the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultExportVCCFATransaction( final String shipFromCountry, String EU_CURRENCY,
														   String shipFromRegion, String shipFromCity, String shipToCountry, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemCode("id12312")
				.itemType("m")
				.shippingCost("N")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("00173")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("1")
				.type("SHIP_TO")
				.country(shipToCountry)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.build();
	}
	/**
	 * Sets up a default China Export cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToCity    the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultExportChinaCFATransaction( final String shipFromCountry, String EU_CURRENCY,
															 String shipFromRegion, String shipFromCity, String shipToCountry, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemCode("id12312")
				.itemType("m")
				.shippingCost("N")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("00173")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.type("SHIP_TO")
				.country(shipToCountry)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.build();
	}
	/**
	 * Sets up a default cfa services transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultSupplyOfServicesCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																   String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemType("s")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("00173")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("20077")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationtwo = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationthree = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT05067120153")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationfour = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("IT05067120153")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.taxRegistration(taxRegistrationtwo)
				.build();
		SAPParty partyTwo = SAPParty
				.builder()
				.id("2")
				.role("SHIP_TO")
				.taxRegistration(taxRegistrationthree)
				.taxRegistration(taxRegistrationfour)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.companyId("2310")
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.Party(partyTwo)
				.build();
	}
	/**
	 * Sets up a default Intra Community Supply of Services cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultIntraCommunityServicesCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																   String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("90")
				.unitPrice("1000.00")
				.id("1")
				.itemType("s")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("1234")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5678")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationtwo = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL004445879B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationthree = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT09417760155")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationfour = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL801161988B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.taxRegistration(taxRegistrationtwo)
				.build();
		SAPParty partyTwo = SAPParty
				.builder()
				.id("2")
				.role("SHIP_TO")
				.taxRegistration(taxRegistrationthree)
				.taxRegistration(taxRegistrationfour)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.companyId("2310")
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.Party(partyTwo)
				.build();
	}
	/**
	 * Sets up a default Service Distance Selling cfa transaction
	 *
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipFromCity	  the origin city
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param shipToCity      the destination city
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 */
	public SAPQuoteInput setupDefaultServiceDistanceSellingCFATransaction( final String shipFromCountry, String EU_CURRENCY,
																	String shipFromRegion, String shipFromCity, String shipToCountry, String shipToRegion, String shipToCity, String saleOrPurchase, String grossOrNet)
	{
		SAPItem itemOne = SAPItem
				.builder()
				.quantity("2")
				.unitPrice("100.00")
				.id("000010")
				.itemCode("H11")
				.itemType("s")
				.build();
		SAPLocation shipFrom = SAPLocation
				.builder()
				.zipCode("00173")
				.type("SHIP_FROM")
				.country(shipFromCountry)
				.state(shipFromRegion)
				.city(shipFromCity)
				.build();
		SAPLocation shipTo = SAPLocation
				.builder()
				.zipCode("5232")
				.type("SHIP_TO")
				.country(shipToCountry)
				.state(shipToRegion)
				.city(shipToCity)
				.build();
		SAPParty.TaxRegistration taxRegistrationone = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_FROM")
				.taxNumber("IT02992760963")
				.taxNumberTypeCode("1")
				.build();
		SAPParty.TaxRegistration taxRegistrationtwo = SAPParty.TaxRegistration
				.builder()
				.locationType("SHIP_TO")
				.taxNumber("NL004445879B01")
				.taxNumberTypeCode("1")
				.build();
		SAPParty partyOne = SAPParty
				.builder()
				.id("1")
				.role("SHIP_FROM")
				.taxRegistration(taxRegistrationone)
				.taxRegistration(taxRegistrationtwo)
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.Location(shipFrom)
				.Location(shipTo)
				.Party(partyOne)
				.build();
	}
	/**
	 * Sets up a default direct payload request
	 *
	 * @param taxCategory    the item's tax category
	 * @param saleOrPurchase sale or purchase
	 * @param grossOrNet     gross or net
	 *
	 * @return the default direct payload request
	 */
	public SAPQuoteInput setupDefaultDirectPayloadRequest( String taxCategory, String taxCodeCountry, String companyID,
														   final String taxCode, final String saleOrPurchase,
														   final String grossOrNet )
	{
		SAPItem itemOne = SAPItem
				.builder()
				.taxCategory(taxCategory)
				.taxCodeCountry("IT")
				.taxCode("OGR1C")
				.quantity("1")
				.unitPrice("1000.00")
				.id("000010")
				.build();
		return SAPQuoteInput
				.builder()
				.id(ID)
				.date(DATE)
				.saleOrPurchase(saleOrPurchase)
				.grossOrNet(grossOrNet)
				.currency(EU_CURRENCY)
				.Item(itemOne)
				.companyId("SAP_TAX_SERVICE_IT")
				.build();
	}

}


