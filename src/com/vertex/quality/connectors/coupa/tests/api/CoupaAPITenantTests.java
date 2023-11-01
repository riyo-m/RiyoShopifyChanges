package com.vertex.quality.connectors.coupa.tests.api;

import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaTenantKeywords;
import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * Coupa API tenant tests
 *
 * @author alewis
 *
 * CCOUPA-1663 - Story
 */
public class CoupaAPITenantTests {
    CoupaUtils utils = new CoupaUtils();
    CoupaTenantKeywords tenants = new CoupaTenantKeywords();

    /**
     * Tests creating and deleting a tenant
     *
     * CCOUPA-1683
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void createTenantTest() {
        tenants.createTenant("test",utils.getOSERIESURL());
        tenants.deleteTenant();
        //createAccessToken();
    }

    /**
     * Negative test; Tests creating a tenant with a bad url generates correct error test
     *
     * CCOUPA-1684
     */
    @Test(groups = { "coupa" , "coupa_api"})
    public void createTenantBadURLTest() {
        tenants.createTenantBadURL("bad url");
    }

    /**
     * Tests updating a tenant
     *
     * CCOUPA-1685
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void updateTenantTest() {
        tenants.updateTenant("1","https://oseries8-dev.vertexconnectors.com/vertex-ws/");
        tenants.updateTenant("1",utils.getOSERIESURL());
    }

    /**
     * Negative test; Tests updating a tenant with bad id
     *
     * CCOUPA-1686
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void updateTenantBadIDTest() {
        tenants.updateTenantBadIDTenant("1","https://oseries9-final.vertexconnectors.com/oseries-ui/");
    }
}
