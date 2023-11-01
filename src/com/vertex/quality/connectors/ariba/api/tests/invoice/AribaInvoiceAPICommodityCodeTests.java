package com.vertex.quality.connectors.ariba.api.tests.invoice;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaCustomMappings;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIInvoiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * Tests commodity codes for invoices
 *
 * @author hho osabha, vgosavi
 */

public class AribaInvoiceAPICommodityCodeTests extends AribaAPIInvoiceBaseTest
{
	/**
	 * Creates an invoice verification call with vendor code exemption
	 * sends two line items to verify, one with exempt vendor and one without
	 * verifies the exemption is working for one vendor only.
	 * CARIBA-311
	 */
	@Test(groups = { "ariba_api","ariba_regression" })
	public void taxExemptVendorTest( )
	{
		apiUtil.requestData.setNumberOfItems(2);
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "Hayden Lake", "US", null, null, null, "83835",
			"ID");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "Nashville", "US", null, null, null, "37219",
			"TN");
		apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Red Bluff", "US", null, null, null,
			"96080-1274", "CA");
		apiUtil.requestData.setItemNetAmountValues(1, "100", "USD");
		apiUtil.requestData.setItemIncoTermsDetail(1, "Kitchen");
		apiUtil.requestData.setItemIncoTermsCode(1, "DAP");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(1, "custom", defaultCommodityCode, "EA");
		apiUtil.requestData.setItemCommodityCode(1, defaultCommodityCode);
		apiUtil.requestData.setAddress(2, AribaAPIAddressTypes.BILLING, "Hayden Lake", "US", null, null, null, "83835",
			"ID");
		apiUtil.requestData.setAddress(2, AribaAPIAddressTypes.SHIP_TO, "Nashville", "US", null, null, null, "37219",
			"TN");
		apiUtil.requestData.setAddress(2, AribaAPIAddressTypes.SUPPLIER, "Red Bluff", "US", null, null, null,
			"96080-1274", "CA");
		apiUtil.requestData.setItemNetAmountValues(2, "350", "USD");
		apiUtil.requestData.setItemIncoTermsDetail(2, "Kitchen");
		apiUtil.requestData.setItemIncoTermsCode(2, "DAP");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(2, "custom", defaultCommodityCode, "EA");
		apiUtil.requestData.setItemRequiredValues(2, "2", "1", "2", "false");
		apiUtil.requestData.setItemCommodityCode(2, defaultCommodityCode);

		apiUtil.requestData.setCustomFieldsStatus(true);
		apiUtil.requestData.addCustomStringField(AribaCustomMappings.ARIBA_VENDOR_CODE_FIELD, "SU_INTERNAL38124231");

		Response response = apiUtil.sendXMLRequest("default");
		boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
		assertTrue(isResponseCorrect);

		response.then().statusCode(200)
				.assertThat()
				.body("**.find{it.name()=='Currency'}.UniqueName", equalTo("USD"))
				.and()
				.body("**.find{it.name()=='TaxAmount'}.Amount", equalTo("9"));
	}
}