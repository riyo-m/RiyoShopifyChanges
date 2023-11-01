package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.RequestMappingKeywords;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TradeshiftAPIRequestMappingTests{

    protected String TENANT_ID = "a0cddd0f-aac0-4abe-ad2d-289c1aa862bb";
    private RequestMappingKeywords mappings = new RequestMappingKeywords();

    /**
     * Check to see that a request mapping was created successfully
     * with the right info
     *
     * CTRADESHI-367
     */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void checkRequestMappingCreatedTest() {
        String mapping = mappings.createRequestMapping(TENANT_ID,"Mapping2","TAXPAYER");
        String tradeshiftField = mappings.getRequestMappingTradeshiftFieldID(TENANT_ID);
        String vertexField = mappings.getRequestVertexFieldID(TENANT_ID);

        assertEquals(tradeshiftField,"Mapping2");
        assertEquals(vertexField,"TAXPAYER");
        mappings.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Negative test to ensure we cannot create
     * two request mappings with the same field code
     *
     * CTRADESHI-372
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void createRequestMappingDuplicateFieldCodeTest(){
        String mapping = mappings.createRequestMappingDuplicate(TENANT_ID,"mapping","DIVISION", true);
        mappings.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Ensure that mappings can be deleted
     *
     * CTRADESHI-385
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void checkRequestMappingDeletedTest() {
        String id = mappings.createRequestMapping(TENANT_ID,"Mapping","TAXPAYER");
        mappings.deleteRequestMapping(TENANT_ID,id);
    }

    /**
     * Negative test to ensure we cannot create two mappings
     * wih the same field ID
     *
     * CTRADESHI-386
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void duplicateFieldIDTest() {
        String mapping = mappings.createRequestMappingDuplicate(TENANT_ID,"Mapping2","TAXPAYER", false);
        mappings.deleteRequestMapping(TENANT_ID,mapping);
    }

    /**
     * Assure that each tradeshift field ID is valid
     * to create a request mapping
     *
     * CTRADESHI-387
     * */
    @Test(groups = { "tradeshift", "tradeshift_api" })
    public void allFieldIDTest() {
        String mapping1 = mappings.createRequestMapping(TENANT_ID,"mapping1","TAXPAYER");
        mappings.deleteRequestMapping(TENANT_ID,mapping1);
        String mapping2 = mappings.createRequestMapping(TENANT_ID,"mapping2","DIVISION");
        mappings.deleteRequestMapping(TENANT_ID,mapping2);
        String mapping3 = mappings.createRequestMapping(TENANT_ID,"mapping3","DEPARTMENT");
        mappings.deleteRequestMapping(TENANT_ID,mapping3);
    }
}