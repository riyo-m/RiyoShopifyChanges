package com.vertex.quality.connectors.coupa.tests.ui;

import com.vertex.quality.connectors.coupa.pages.VertexCoupaSignInPage;
import com.vertex.quality.connectors.coupa.tests.ui.base.CoupaUIBaseTest;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests functionality of switching the selected tenants
 *
 * @author alewis
 */

public class SwitchingTenantTests extends CoupaUIBaseTest {

    /**
     * Tests the switching selected tenants
     * CCOUPA-1661
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void switchTenantTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on request mappings
        signInPage.clickRequestMappings();
        //select the coupa text
        String coupaText = signInPage.getCoupaIdText();
        //click on the tenants
        signInPage.clickTenants();
        //click on request mappings
        signInPage.clickRequestMappings();
        //verify the coupa text1
        String coupaText1 = signInPage.getCoupaIdText();
        //compare the coupa text and coupa text1
        assertEquals(coupaText, coupaText1);
    }

    /**
     * Tests the switching selected tenants
     * CCOUPA-1661
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void enabledTenantTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on the tenants
        signInPage.clickTenants();
        //Click on Add button
        signInPage.addTenant();
        //Enter tenant details and save
        String value = signInPage.addTenantDetails("2", "Coupa-1", "Test-1", "https://oseries8-dev.vertexconnectors.com/vertex-ws/", "30");
        //verify the value enabled or not
        String value1 = signInPage.getLogEnabledValue("2");
        //Verify the enabled value is true or not
        Boolean enabled = signInPage.enableTenantVerification(value, value1);
        signInPage.deleteTenant("2");
        //compare both value and value1
        assertTrue(enabled);
    }

    /**
     * Test adding multiple digits to Coupa Tenant ID
     * CCOUPA-1689
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void addMultipleDigitsToCoupaTenantIDTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on the tenants
        signInPage.clickTenants();
        //Click on Add button
        signInPage.addTenant();
        //Enter tenant details and save
        String value = signInPage.addTenantDetails("15", "Coupa-2", "Test-2", "https://oseries8-dev.vertexconnectors.com/vertex-ws/", "30");
        signInPage.deleteTenant("15");
    }

    /**
     * Test the setting up a tenant you can edit the “Log Retention Interval (Days)” section
     * CCOUPA-1487
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void editLogRetentionIntervalTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on the tenants
        signInPage.clickTenants();
        //Click on Add button
        signInPage.addTenant();
        //Enter tenant details and save
        String value = signInPage.addTenantDetails("10", "Coupa-3", "Test-3", "https://oseries9-dev.vertexconnectors.com/vertex-ws/", "45");
        signInPage.deleteTenant("10");
    }

    /**
     * Test adding and deleting the request mapping
     * CCOUPA-1697
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void addAndDeleteRequestMappingTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on request mappings
        signInPage.clickRequestMappings();
        //click on add request mapping
        signInPage.clickAddRequestMapping();
        //add request mapping details
        signInPage.addRequestMappingDetails("Account Segment 3", "Flex Code 1");
        //delete the request mapping
        signInPage.deleteMapping("Account Segment 3");
    }

    /**
     * Test for checking the status of the connector
     *
     * CCOUPA-1923
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void systemStatusTest(){
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);

        signInPage.vertexCoupaQALogin();

        String tenantStatus = signInPage.getTenantStatus();
        String tenantURL = signInPage.getTenantUrl();
        assertEquals(tenantURL,signInPage.tenantURL);
        assertEquals(tenantStatus,"UP");
    }
}