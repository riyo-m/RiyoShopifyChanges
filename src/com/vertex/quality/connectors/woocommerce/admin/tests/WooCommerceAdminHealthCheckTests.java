package com.vertex.quality.connectors.woocommerce.admin.tests;

import com.vertex.quality.connectors.woocommerce.admin.pages.WooCommerceAdminHomePage;
import com.vertex.quality.connectors.woocommerce.admin.tests.base.WooCommerceAdminBaseTest;
import com.vertex.quality.connectors.woocommerce.enums.WooCommerceData;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CWOO-475 WOO -  Connection to Vertex (Healthcheck)
 * health check tests class for WooCommerce Vertex.
 *
 * @author Vivek.Kumar
 */
public class WooCommerceAdminHealthCheckTests extends WooCommerceAdminBaseTest {
    /**
     * CWOO-473 Perform a Healthcheck - valid address and tax calc
     * this method is to check health check for WooCommerce Vertex health check.
     */
    @Test(groups = {"woo_smoke", "woo_healthCheck"})
    public void healthCheckValidUrlTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickVertexIcon();
        assertEquals(homePage.clickVertexConnectionTest(), WooCommerceData.EXPECTED_HEALTH_CHECK_MESSAGE.data);
    }

    /**
     * CWOO-474 Perform a Healthcheck - Invalid address URL.
     * this method is to check health check for Invalid WooCommerce Vertex health check.
     */
    @Test(groups = {"woo_smoke", "woo_healthCheck"})
    public void healthCheckInvalidUrlTest() {
        WooCommerceAdminHomePage homePage = signInToAdmin(testStartPage);
        try {
            homePage.clickVertexIcon();
            homePage.changeAddressUrl(WooCommerceData.BAD_URL.data);
            homePage.changeTaxUrl(WooCommerceData.BAD_URL.data);
            homePage.clickSaveButtonField();
            assertEquals(homePage.clickVertexConnectionTest(), WooCommerceData.ERROR_HEALTH_CHECK_MESSAGE.data);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            homePage.clickVertexIcon();
            homePage.changeAddressUrl(WooCommerceData.ADDRESS_CORRECT_URL.data);
            homePage.changeTaxUrl(WooCommerceData.TAX_CORRECT_URL.data);
            homePage.clickSaveButtonField();
        }
    }
}
