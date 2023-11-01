package com.vertex.quality.connectors.coupa.tests.api.base.keywords;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaRequestMappingBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CoupaRequestMappingKeywords {

    CoupaRequestMappingBaseTest mappings = new CoupaRequestMappingBaseTest();

    /**
     * gets the id of a specified request mapping
     *
     * @return id of created request mapping
     */
    public int getRequestMappingID() {
        return mappings.getRequestMappingID();
    }

    /**
     * gets the vertex field id from a specified request mapping
     *
     * @return vertex field id of a request mapping
     */
    public String getRequestVertexFieldID() {
        return mappings.getRequestVertexFieldID();
    }


    /**
     * gets the coupa field id from a specified request mapping
     *
     * @return coupa field id of a request mapping
     */
    public String getRequestMappingCoupaFieldID() {
        return mappings.getRequestMappingCoupaFieldID();
    }

    /**
     * creates a request mapping
     *
     * @return id of created request mapping
     */
    public String createRequestMapping(String fieldCode, String coupaFieldId) {
        return mappings.createRequestMapping(fieldCode,coupaFieldId);
    }

    /**
     * creates a request mapping with a duplicate fieldID or source field ID
     */
    public void createRequestMappingDuplicate(String fieldCode, String coupaFieldId) {
       mappings.createRequestMappingDuplicate(fieldCode,coupaFieldId);
    }

    /**
     * deletes specified request mapping; successfully
     */
    public void deleteRequestMapping(String id) {
        mappings.deleteRequestMapping(id);
    }

    /**
     * deletes specified request mapping; with invalid url
     */
    public void deleteRequestMappingInvalidIDURL(String tenantID, String requestID, String fieldCode, String coupaFieldId) {
        mappings.deleteRequestMappingInvalidIDURL(tenantID,requestID,fieldCode,coupaFieldId);
    }
}
