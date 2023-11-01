package com.vertex.quality.connectors.hybris.tests.versionDisplay;

import com.vertex.quality.connectors.hybris.enums.admin.*;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminExtensionsPage;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminHomePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class to validate Versions of vertex's services linked in Hybris Connector.
 *
 * @author Shivam.Soni
 */
public class HybrisVersionDisplayTests extends HybrisBaseTest {

    HybrisAdminHomePage adminHomePage;
    HybrisAdminExtensionsPage extensionsPage;

    List<String> headingsFromUI;

    /**
     * CHYB-270 - HYB - Test Case - version displays on the UI
     */
    @Test(groups = {"hybris_smoke"})
    public void hybrisVersionDisplayTest() {
        // login as Admin user into Hybris-Admin Page
        adminHomePage = loginAsAdminUser();

        // Navigate to Platform -- > Extensions page
        extensionsPage = adminHomePage.navigateToExtensionsPage(HybrisAdminMenuNames.PLATFORM.getMenuName(), HybrisAdminSubMenuNames.EXTENSIONS.getSubMenuName());

        // Get Actual Result count of Vertex Extension list and Validate
        extensionsPage.searchVertexExtensionsList("vertex");

        // Get All Headings of Vertex Extension list and Validate
        headingsFromUI = extensionsPage.getExtensionsTableHeadings();
        assertTrue(headingsFromUI.contains(HybrisAdminExtensionHeadings.NAME.getMenuName()), "actual headings do not contains expected headingName");
        assertTrue(headingsFromUI.contains(HybrisAdminExtensionHeadings.VERSION.getMenuName()), "actual headings do not contains expected headingVersion");
        assertTrue(headingsFromUI.contains(HybrisAdminExtensionHeadings.CORE.getMenuName()), "actual headings do not contains expected headingCore");
        assertTrue(headingsFromUI.contains(HybrisAdminExtensionHeadings.WEB.getMenuName()), "actual headings do not contains expected headingWEB");

        // Validating all vertex extensions' version
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_ADDRESS_VERIFICATION.getExtensionName()));
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_API.getExtensionName()));
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_B2B_ADDRESS_VERIFICATION.getExtensionName()));
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_B2B_API.getExtensionName()));
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_BACKOFFICE.getExtensionName()));
        assertEquals(HybrisConnectorVersions.VERSION_1_0_8_0.getVersion(), extensionsPage.getExtensionVersionFromUI(HybrisAdminExtensionNames.EXTENSION_VERTEX_TAX_CALCULATION.getExtensionName()));
    }
}
