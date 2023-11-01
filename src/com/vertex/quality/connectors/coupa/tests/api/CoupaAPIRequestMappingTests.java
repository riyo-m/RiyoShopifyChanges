package com.vertex.quality.connectors.coupa.tests.api;

import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaInvoiceKeywords;
import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaMessageLogKeywords;
import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaRequestMappingKeywords;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Coupa API request mapping tests
 *
 * @author alewis
 */
public class CoupaAPIRequestMappingTests {
    CoupaRequestMappingKeywords mappings = new CoupaRequestMappingKeywords();
    CoupaMessageLogKeywords logs = new CoupaMessageLogKeywords();
    CoupaInvoiceKeywords invoices = new CoupaInvoiceKeywords();

    /**
     * Check to see that a request mapping was created successfully
     * with the right info
     *
     * CCOUPA-1668
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void checkRequestMappingCreatedTest() {
        String mapping = mappings.createRequestMapping("USAGE_CLASS","ACCOUNT_SEGMENT_6");
        String coupaField = mappings.getRequestMappingCoupaFieldID();
        String vertexField = mappings.getRequestVertexFieldID();

        assertEquals(coupaField,"ACCOUNT_SEGMENT_6");
        assertEquals(vertexField,"USAGE_CLASS");
        mappings.deleteRequestMapping(mapping);
    }

    /**
     * Negative test, checks to see we get correct error when trying to delete a request mapping with invalid tenant id
     *
     * CCOUPA-1672
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void deleteInvalidTenantIDTest() {
        mappings.deleteRequestMappingInvalidIDURL("1000","2", "USAGE_CODE","ACCOUNT_SEGMENT_10");
    }

    /**
     * Negative test, checks to see we get correct error when attempting to delete a request mapping with invalid mapping request id
     *
     * CCOUPA-1673
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void deleteInvalidMapReqIDTest() {
        mappings.deleteRequestMappingInvalidIDURL("2","4000", "USAGE_CODE","ACCOUNT_SEGMENT_10");
    }

    /**
     * Negative test, test to see correct error from attempting to enter duplicate source field id
     * when creating a request mapping
     *
     * CCOUPA-1674
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void duplicateSourceFieldIDTest() {
        String mapping = mappings.createRequestMapping("USAGE_CODE","ACCOUNT_SEGMENT_10");
        mappings.createRequestMappingDuplicate("USAGE_CODE","ACCOUNT_SEGMENT_10");
        mappings.deleteRequestMapping(mapping);
    }

    /**
     * tests creating a request mapping then deleting and receiving the right response
     *
     * CCOUPA-1675
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void checkRequestMappingDeletedTest() {
        String id = mappings.createRequestMapping("USAGE_CODE","ACCOUNT_SEGMENT_10");
        mappings.deleteRequestMapping(id);
    }

    /**
     * Negative test, test to see correct error from attempting to enter duplicate field id
     * when creating a request mapping
     *
     * CCOUPA-1676
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void duplicateFieldIDTest() {
        String mapping = mappings.createRequestMapping("USAGE_CODE","ACCOUNT_SEGMENT_10");
        mappings.createRequestMappingDuplicate("USAGE_CODE","ACCOUNT_SEGMENT_20");
        mappings.deleteRequestMapping(mapping);
    }

    /**
     * Test to check whether Vertex Flex Code fields can be mapped
     *
     * CCOUPA-1532
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void flexCodeTest() {
        String idOne = mappings.createRequestMapping("FLEX_CODE_1","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idOne);
        String idTwo = mappings.createRequestMapping("FLEX_CODE_2","ACCOUNT_SEGMENT_17");
        mappings.deleteRequestMapping(idTwo);
        String idThree = mappings.createRequestMapping("FLEX_CODE_3","ACCOUNT_SEGMENT_16");
        mappings.deleteRequestMapping(idThree);
        String idFour = mappings.createRequestMapping("FLEX_CODE_4","ACCOUNT_SEGMENT_15");
        mappings.deleteRequestMapping(idFour);
        String idFive = mappings.createRequestMapping("FLEX_CODE_5","ACCOUNT_SEGMENT_14");
        mappings.deleteRequestMapping(idFive);
        String idSix = mappings.createRequestMapping("FLEX_CODE_6","ACCOUNT_SEGMENT_13");
        mappings.deleteRequestMapping(idSix);
        String idSeven = mappings.createRequestMapping("FLEX_CODE_7","ACCOUNT_SEGMENT_12");
        mappings.deleteRequestMapping(idSeven);
        String idEight = mappings.createRequestMapping("FLEX_CODE_8","ACCOUNT_SEGMENT_11");
        mappings.deleteRequestMapping(idEight);
        String idNine = mappings.createRequestMapping("FLEX_CODE_9","ACCOUNT_SEGMENT_10");
        mappings.deleteRequestMapping(idNine);
        String idTen = mappings.createRequestMapping("FLEX_CODE_10","ACCOUNT_SEGMENT_9");
        mappings.deleteRequestMapping(idTen);
        String idEleven = mappings.createRequestMapping("FLEX_CODE_11","ACCOUNT_SEGMENT_8");
        mappings.deleteRequestMapping(idEleven);
        String idTwelve = mappings.createRequestMapping("FLEX_CODE_12","ACCOUNT_SEGMENT_7");
        mappings.deleteRequestMapping(idTwelve);
        String idThirteen = mappings.createRequestMapping("FLEX_CODE_13","ACCOUNT_SEGMENT_6");
        mappings.deleteRequestMapping(idThirteen);
        String idFourteen = mappings.createRequestMapping("FLEX_CODE_14","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idFourteen);
        String idFifteen = mappings.createRequestMapping("FLEX_CODE_15","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idFifteen);
        String idSixteen = mappings.createRequestMapping("FLEX_CODE_16","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idSixteen);
        String idSeventeen = mappings.createRequestMapping("FLEX_CODE_17","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idSeventeen);
        String idEighteen = mappings.createRequestMapping("FLEX_CODE_18","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idEighteen);
        String idNineteen = mappings.createRequestMapping("FLEX_CODE_19","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idNineteen);
        String idTwenty = mappings.createRequestMapping("FLEX_CODE_20","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwenty);
        String idTwentyOne = mappings.createRequestMapping("FLEX_CODE_21","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwentyOne);
        String idTwentyTwo = mappings.createRequestMapping("FLEX_CODE_22","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwentyTwo);
        String idTwentyThree = mappings.createRequestMapping("FLEX_CODE_23","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwentyThree);
        String idTwentyFour = mappings.createRequestMapping("FLEX_CODE_24","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwentyFour);
        String idTwentyFive = mappings.createRequestMapping("FLEX_CODE_25","ACCOUNT_SEGMENT_18");
        mappings.deleteRequestMapping(idTwentyFive);
    }

    /**
     * Tests an invoice request mapping with a 20 character long string
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void longStringValueMappingTest(){
        String mapping = mappings.createRequestMapping("FLEX_CODE_22", "ACCOUNT_SEGMENT_2");
        String invoiceNumber = invoices.sendSegmentCharactersInvoice();
        mappings.deleteRequestMapping(mapping);

        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");

        assertTrue(payload.contains(" <FlexibleCodeField fieldId=\"22\">aaaaaaaaaaaaaaaaaaaa</FlexibleCodeField>"));
    }

    /**
     * Tests an invoice request mapping with a 20 character long numeric value
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void longNumericValueMappingTest(){
        String mapping = mappings.createRequestMapping("FLEX_CODE_22", "ACCOUNT_SEGMENT_2");
        String invoiceNumber = invoices.sendSegmentNumbersInvoice();
        mappings.deleteRequestMapping(mapping);

        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");

        assertTrue(payload.contains(" <FlexibleCodeField fieldId=\"22\">12345678998765432100</FlexibleCodeField>"));
    }

    /**
     * Tests an invoice request mapping with a string of 20 special characters
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void mappingSpecialCharactersTest(){
        String mapping = mappings.createRequestMapping("FLEX_CODE_22", "ACCOUNT_SEGMENT_2");
        String invoiceNumber = invoices.sendSegmentSCharsInvoice();
        mappings.deleteRequestMapping(mapping);

        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");

        assertTrue(payload.contains(" <FlexibleCodeField fieldId=\"22\">!@#$%^=-()_+{}|:+@?`~</FlexibleCodeField>"));
    }
}