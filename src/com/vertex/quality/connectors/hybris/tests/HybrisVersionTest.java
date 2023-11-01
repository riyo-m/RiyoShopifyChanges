package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminExtensionHeadings;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminMenuNames;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminSubMenuNames;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminExtensionsPage;
import com.vertex.quality.connectors.hybris.pages.admin.HybrisAdminHomePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Test to retrieve the Vertex Extension Versions
 *
 * @author Nagaraju Gampa
 */
public class HybrisVersionTest extends HybrisBaseTest
{
	/**
	 * Test to retrieve the Vertex Connector Versions
	 */
	@Test(groups = { "version", "smoke" })
	public void hybrisVertexConnectorVersionTest( )
	{
		// =================Data declarations=================================
		final String menuPlatform = HybrisAdminMenuNames.PLATFORM.getMenuName();
		final String subMenuExtensions = HybrisAdminSubMenuNames.EXTENSIONS.getSubMenuName();
		final int expectedSearchResultCount = 5;
		final String expectedHeadingName = HybrisAdminExtensionHeadings.NAME.getMenuName();
		final String expectedHeadingVersion = HybrisAdminExtensionHeadings.VERSION.getMenuName();
		final String expectedHeadingCore = HybrisAdminExtensionHeadings.CORE.getMenuName();
		final String expectedHeadingHMC = HybrisAdminExtensionHeadings.HMC.getMenuName();
		final String expectedHeadingWeb = HybrisAdminExtensionHeadings.WEB.getMenuName();

		// =================Script Implementation=================================
		// login as Admin user into Hybris-Admin Page
		final HybrisAdminHomePage adminHomePage = loginAsAdminUser();

		// Navigate to Platform -- > Extensions page
		final HybrisAdminExtensionsPage extensionsPage = adminHomePage.navigateToExtensionsPage(menuPlatform,
			subMenuExtensions);

		// Get Actual Result count of Vertex Extension list and Validate
		final int actualSearchResultCount = extensionsPage.searchVertexExtensionsList("vertex");
		assertTrue(Integer
				.toString(actualSearchResultCount)
				.equalsIgnoreCase(Integer.toString(expectedSearchResultCount)),
			"Row Count is not Matching with Expected Count");

		// Get Actual Headings of Vertex Extension list and Validate
		final List<String> ActualHeadings = extensionsPage.getExtensionsTableHeadings();
		assertTrue(ActualHeadings.contains(expectedHeadingName),
			"actual headings do not conatins expected headingName");
		VertexLogger.log(
			String.format("Actual Headings : %s contains Expected Heading: %s", ActualHeadings, expectedHeadingName));
		assertTrue(ActualHeadings.contains(expectedHeadingVersion),
			"actual headings do not conatins expected headingVersion");
		VertexLogger.log(String.format("Actual Headings : %s contains Expected HeadingVersion: %s", ActualHeadings,
			expectedHeadingVersion));
		assertTrue(ActualHeadings.contains(expectedHeadingCore),
			"actual headings do not conatins expected headingCore");
		VertexLogger.log(String.format("Actual Headings : %s contains Expected HeadingCore: %s", ActualHeadings,
			expectedHeadingCore));
		assertTrue(ActualHeadings.contains(expectedHeadingHMC), "actual headings do not conatins expected headingHMC");
		VertexLogger.log(
			String.format("Actual Headings : %s contains Expected HeadingHMC: %s", ActualHeadings, expectedHeadingHMC));
		assertTrue(ActualHeadings.contains(expectedHeadingWeb), "actual headings do not conatins expectedheadingWeb");
		VertexLogger.log(String.format("Actual Headings : %s contains Expected Heading Web: %s", ActualHeadings,
			expectedHeadingWeb));

		// Get Vertex Extension Details
		final Map<String, String> firstLine = extensionsPage.getExtensionVersionDetailsAsDictionary(
			"vertexaddressverification");
		VertexLogger.log(String.format("First line item from the search list : %s ", firstLine));
		final Map<String, String> secondLine = extensionsPage.getExtensionVersionDetailsAsDictionary("vertexapi");
		VertexLogger.log(String.format("Second line item from the search list : %s ", secondLine));
		final Map<String, String> thirdLine = extensionsPage.getExtensionVersionDetailsAsDictionary(
			"vertexb2baddressverification");
		VertexLogger.log(String.format("Third line item from the search list : %s ", thirdLine));
		final Map<String, String> forthLine = extensionsPage.getExtensionVersionDetailsAsDictionary("vertexbackoffice");
		VertexLogger.log(String.format("Fourth line item from the search list : %s ", forthLine));
		final Map<String, String> fifthLine = extensionsPage.getExtensionVersionDetailsAsDictionary(
			"vertextaxcalculation");
		VertexLogger.log(String.format("Fifth line item from the search list : %s ", fifthLine));
	}
}
