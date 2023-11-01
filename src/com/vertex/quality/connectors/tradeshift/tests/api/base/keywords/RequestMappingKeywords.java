package com.vertex.quality.connectors.tradeshift.tests.api.base.keywords;

import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class RequestMappingKeywords{
    RequestMappingBaseTest rqMap = new RequestMappingBaseTest();

    /**
     * creates a request mapping
     *
     * @param tenantId
     *
     * @return id of created request mapping
     */
    public String createRequestMapping(String tenantId, String fieldCode, String tradeshiftFieldId) {
        return rqMap.createRequestMapping(tenantId,fieldCode,tradeshiftFieldId);
    }

    /**
     * creates a request mapping with a duplicate fieldID or source field ID
     *
     * @param tenantId
     * @param fieldCode
     * @param tradeshiftFieldId
     * @param duplicateFieldCode
     *
     * @return the id of the created mapping
     */
    public String createRequestMappingDuplicate(String tenantId, String fieldCode, String tradeshiftFieldId, Boolean duplicateFieldCode) {
        return rqMap.createRequestMappingDuplicate(tenantId,fieldCode,tradeshiftFieldId,duplicateFieldCode);
    }

    /**
     * gets the tradeshift field id from a specified request mapping
     *
     * @param tenantId
     *
     * @return tradeshift field id of a request mapping
     */
    public String getRequestMappingTradeshiftFieldID(String tenantId) {
        return rqMap.getRequestMappingTradeshiftFieldID(tenantId);
    }

    /**
     * gets the vertex field id from a specified request mapping
     *
     * @param tenantId
     *
     * @return vertex field id of a request mapping
     */
    public String getRequestVertexFieldID(String tenantId) {
        return rqMap.getRequestVertexFieldID(tenantId);
    }

    /**
     * deletes specified request mapping; successfully
     *
     * @param tenantId
     * @param requestMappingId
     */
    public void deleteRequestMapping(String tenantId, String requestMappingId) {
        rqMap.deleteRequestMapping(tenantId,requestMappingId);
    }

}
