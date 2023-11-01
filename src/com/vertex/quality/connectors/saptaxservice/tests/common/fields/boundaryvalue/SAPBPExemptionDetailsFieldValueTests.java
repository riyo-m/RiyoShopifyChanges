package com.vertex.quality.connectors.saptaxservice.tests.common.fields.boundaryvalue;

import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPBusinessPartnerExemptionDetail;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPItem;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPLocation;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests different boundary values for BusinessPartnerExemptionDetails
 *
 * @author hho
 */
@Test(groups = { "boundary_value" })
public class SAPBPExemptionDetailsFieldValueTests extends SAPTaxServiceBaseTest
{
	/**
	 * Sets up the basic quote input with bp exemption details
	 *
	 * @param jsonPath   the json path to edit in BP exemption details
	 * @param fieldName  the field in BP exemption details
	 * @param fieldValue the field's value in BP exemption details
	 *
	 * @return the response
	 */
	protected Response setupQuoteInputWithBPExemptionDetails( String jsonPath, String fieldName, Object fieldValue )
	{
		SAPItem itemOne = SAPItem
			.builder()
			.quantity("1")
			.unitPrice("500")
			.itemType(itemTypeMaterial)
			.build();
		SAPBusinessPartnerExemptionDetail bpExemptionDetail = SAPBusinessPartnerExemptionDetail
			.builder()
			.exemptionReasonCode(stringValue)
			.locationType("SHIP_TO")
			.build();
		SAPLocation shipFromLocation = SAPLocation
			.builder()
			.state(SAPRegion.PENNSYLVANIA.getAbbreviation())
			.country(SAPCountry.UNITED_STATES.getCountryAbbreviation())
			.type("SHIP_FROM")
			.build();
		SAPLocation shipToLocation = SAPLocation
			.builder()
			.state(SAPRegion.PENNSYLVANIA.getAbbreviation())
			.country(SAPCountry.UNITED_STATES.getCountryAbbreviation())
			.type("SHIP_TO")
			.build();
		SAPQuoteInput quoteInput = SAPQuoteInput
			.builder()
			.businessPartnerId(stringValue)
			.cashDiscountPercent(stringValue)
			.companyId(stringValue)
			.currency(stringValue)
			.date(date)
			.grossOrNet(net)
			.id(stringValue)
			.isCashDiscountPlanned(no)
			.isCompanyDeferredTaxEnabled(no)
			.isTraceRequired(yes)
			.isTransactionWithinTaxReportingGroup(yes)
			.Item(itemOne)
			.saleOrPurchase(sale)
			.BusinessPartnerExemptionDetail(bpExemptionDetail)
			.Location(shipFromLocation)
			.Location(shipToLocation)
			.build();

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField(jsonPath, fieldName, fieldValue)
			.createRequestAndGetResponse();

		return response;
	}

	/**
	 * Should be valid because exemptionReasonCode is not required
	 */
	@Ignore
	@Test
	public void nullExemptionReasonCodeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]",
			"exemptionReasonCode", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be valid because locationType isn't a required field
	 */
	@Ignore
	@Test
	public void nullLocationTypeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]", "locationType",
			null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be valid because exemptionReasonCode is set and locationType is an enum value
	 */
	@Ignore
	@Test
	public void emptyExemptionReasonCodeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]",
			"exemptionReasonCode", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be invalid because locationType is an enum
	 */
	@Ignore
	@Test
	public void emptyLocationTypeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]", "locationType",
			"");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be valid because exemptionReasonCode is a valid value
	 */
	@Ignore
	@Test
	public void validExemptionReasonCodeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]",
			"exemptionReasonCode", "A");

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be valid because locationType is an enum value
	 */
	@Ignore
	@Test
	public void validLocationTypeTest( )
	{
		Response response = setupQuoteInputWithBPExemptionDetails("businessPartnerExemptionDetails[0]", "locationType",
			"SHIP_TO");

		response
			.then()
			.statusCode(successResponseCode);
	}
}
