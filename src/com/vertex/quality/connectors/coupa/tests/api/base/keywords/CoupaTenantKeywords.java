package com.vertex.quality.connectors.coupa.tests.api.base.keywords;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaTenantBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CoupaTenantKeywords {

    CoupaTenantBaseTest tenant = new CoupaTenantBaseTest();

    /**
     * Gets tenants
     */
    public boolean doesTenantExist(String tenantId) {
        return tenant.doesTenantExist(tenantId);
    }

    /**
     * Updates a already created tenant
     */
    public void updateTenant(String id, String url) {
        tenant.updateTenant(id,url);
    }

    /**
     * attempts to update an already create tenant with bad id
     */
    public void updateTenantBadIDTenant(String id, String url) {
        tenant.updateTenantBadIDTenant(id,url);
    }

    public void createTenant(String id, String url) {
       tenant.createTenant(id,url);
    }

    public void createTenantLoggingOff(String url) {
        tenant.createTenantLoggingOff(url);
    }

    public void createTenantBadURL(String url) {
        tenant.createTenantBadURL(url);
    }

    public void deleteTenant() {
       tenant.deleteTenant();
    }
}
