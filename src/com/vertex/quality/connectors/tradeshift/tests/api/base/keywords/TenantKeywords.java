package com.vertex.quality.connectors.tradeshift.tests.api.base.keywords;

import com.vertex.quality.connectors.tradeshift.tests.api.base.TenantBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.MessageLogKeywords;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class TenantKeywords{

    TenantBaseTest tenant = new TenantBaseTest();

    /**
     * Checks if a tenant already exists, if not creates tenant
     */
    public void createTenant(String id) {
        tenant.createTenant(id);
    }

    /**
     * Attempts to create a duplicate tenant
     *
     * @param id
     * @param url
     * */
    public void createDuplicateTenant(String id, String url) {
        tenant.createDuplicateTenant(id,url);
    }

    /**
     * Creates a tenant with an invalid url for negative tests
     *
     * @param id
     */
    public void createTenantBadURL(String id) {
        tenant.createTenantBadURL(id);
    }

    /**
     * Edits a tradeshift tenant resets the dropdown menu selection if applicable
     *
     * @param tenantId
     * @param name
     * @param taxEngineUrl
     * @param defUnEceCategory
     * @param defUnEceScheme
     * @param accrualEnabled
     * @param accrualAmount
     * @param loggingEnabled
     *
     * {"id":"a0cddd0f-aac0-4abe-ad2d-289c1aa862bb","name":"tradeshift","taxEngineUrl":"https://oseries9-final.vertexconnectors.com/vertex-ws/","taxEngineTenancyType":"SINGLE","taxEngineJournaled":false,"performPartialAccruals":true,"taxEngineDefaultTaxpayer":"connector-tradeshift-qa","taxEngineDefaultTaxCategory":"AA","taxEngineDefaultTaxScheme":"VAT","logEnabled":true,"logRetentionDays":30,"partialAccrualThresholdAmount":"5"}
     * */
    public void editTenant(String tenantId, String name, String taxEngineUrl, String defUnEceCategory, String defUnEceScheme,boolean accrualEnabled, String accrualAmount, boolean loggingEnabled, boolean rawRequestEnabled) {
       tenant.editTenant(tenantId, name, taxEngineUrl, defUnEceCategory, defUnEceScheme, accrualEnabled, accrualAmount, loggingEnabled, rawRequestEnabled);
    }

    /**
     * Edits a tradeshift tenant and sets dropdown UN/ECE code
     *
     * @param tenantId
     * @param name
     * @param taxEngineUrl
     * @param defUnEceCategory
     * @param defUnEceScheme
     * @param dropDownCategory
     * @param dropDownScheme
     * @param accrualEnabled
     * @param accrualAmount
     * @param loggingEnabled
     */
    public void editTenant(String tenantId, String name, String taxEngineUrl, String defUnEceCategory, String defUnEceScheme, String dropDownCategory, String dropDownScheme, boolean accrualEnabled, String accrualAmount, boolean loggingEnabled) {
        tenant.editTenant(tenantId, name, taxEngineUrl, defUnEceCategory, defUnEceScheme, dropDownCategory, dropDownScheme, accrualEnabled, accrualAmount, loggingEnabled);

    }

    /**
     * Gets tenants
     *
     * @param tenantId
     *
     * @return does tenant exist boolean
     */
    public boolean doesTenantExist(String tenantId) {
        return tenant.doesTenantExist(tenantId);
    }

    /**
     * Deletes tenants
     */
    public void deleteTenant(String id) {
        tenant.deleteTenant(id);
    }
}

