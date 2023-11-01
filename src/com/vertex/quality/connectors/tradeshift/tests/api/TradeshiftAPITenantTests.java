package com.vertex.quality.connectors.tradeshift.tests.api;

import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.tradeshift.tests.api.base.RequestMappingBaseTest;
import com.vertex.quality.connectors.tradeshift.tests.api.base.keywords.*;
import org.testng.annotations.Test;

public class TradeshiftAPITenantTests extends RequestMappingBaseTest {
    private TenantKeywords tenants = new TenantKeywords();
    private static String CONFIG_PROP_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;
    private static ReadProperties readConfig = new ReadProperties(CONFIG_PROP_FILE_PATH);
    private String OSERIESURL = readConfig.getProperty("TEST.VERTEX.TRADESHIFT.OSERIES.URL");

    /**
     * Tests creating and deleting a tenant
     *
     * CTRADESHI-388
     */
    @Test(groups = { "tradeshift" , "tradeshift_api"})
    public void createTenantTest() {
        tenants.createTenant("2");
        tenants.deleteTenant("2");
    }

    /**
     * Negative test to ensure a tenant cannot be
     * created with an invalid tax payer URL
     *
     * CTRADESHI-389
     * */
    @Test(groups = { "tradeshift" , "tradeshift_api"})
    public void createTenantBadURLTest() {
        tenants.createTenantBadURL("1");
    }

    /**
     * Negative test to ensure we cannot create
     * a duplicate tenant in the connector
     *
     * CTRADESHI-390
     * */
    @Test(groups = { "tradeshift" , "tradeshift_api"})
    public void createDuplicateTenantTest() {
        tenants.createDuplicateTenant("2",OSERIESURL);
        tenants.deleteTenant("2");
    }


}