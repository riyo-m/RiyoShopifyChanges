package com.vertex.quality.connectors.sitecorexc.admin.tests;

import com.vertex.quality.connectors.sitecorexc.admin.pages.SiteCoreXCAdminHomePage;
import com.vertex.quality.connectors.sitecorexc.common.tests.SiteCoreXCAdminBaseTest;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertTrue;

/**
 * health check tests class for SiteCoreXC Vertex.
 *
 * @author Vivek.Kumar
 */
public class SiteCoreXCAdminHealthCheckTests extends SiteCoreXCAdminBaseTest {
    /**
     * this method is to check health check for SiteCoreXC Vertex health check.
     */
    @Test(groups = "ui")
    public void healthCheckTest() {
        SiteCoreXCAdminHomePage homePage = signInToAdmin(testStartPage);
        homePage.clickVertexHealthCheck();
        String parent = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String child : allWindows) {
            if (!parent.equalsIgnoreCase(child))
                driver.switchTo().window(child);
        }
        assertTrue(homePage.checkHealthCheckMessage());
    }
}
