package com.vertex.quality.connectors.ariba.api.tests.invoice;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaCustomMappings;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIInvoiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class AribaInvoiceAPICreditMemoTests extends AribaAPIInvoiceBaseTest {

    /**
     * verifies that the connector throws an error when sending a completely wrong tax type with the invoice
     * CARIBA-1107
     */
    @Test(groups = { "ariba_api","ariba_regression" })
    public void relatedDocumentDateTest( )
    {
        apiUtil.requestData.setDocumentSubType("lineLevelCreditMemo");
        apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SUPPLIER, "Media", "US", "100 Winter St", null, null,
                "19063", "PA");
        apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.BILLING, "King of Prussia", "US", "2301 renaissance blvd", null, null,
                "19406", "PA");
        apiUtil.requestData.setAddress(1, AribaAPIAddressTypes.SHIP_TO, "King of Prussia", "US", "2301 renaissance blvd", null, null,
                "19406", "PA");
        //apiUtil.requestData.setDocumentDateField(testValue);

        Response response = apiUtil.sendXMLRequest("default");
        boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);
        assertTrue(isResponseCorrect);
    }

    private boolean runCustomCreditMemoTest( String testValue, AribaCustomMappings oSeriesField )
    {
        apiUtil.requestData.setDocumentSubType("lineLevelCreditMemo");
        apiUtil.requestData.setCustomFieldsStatus(true);
        apiUtil.requestData.addCustomDateField(oSeriesField, testValue);

        Response response = apiUtil.sendXMLRequest();
        boolean isResponseCorrect = apiUtil.isResponseCorrect(response, statusSuccess, true);

        return isResponseCorrect;
    }
}