package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteInstalledBundlesPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Contains all tests related to outputting the bundle version for one
 * world's Vertex Tax Calculation
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "version" })
public class NetsuiteOWVersionTests extends NetsuiteBaseOWTest
{
	/**
	 * Goes to the list of installed bundles on the Netsuite One World site
	 * and searches for the Vertex Tax Calculation, then prints out its
	 * information
	 * CNSL-230
	 */
	//TODO Install Vertex Tax Calculation Bundle and uncomment
	@Ignore
	@Test(groups = { "netsuite_ow_smoke" })
	public void outputVertexTaxCalculationBundleTest( )
	{
		String bundleName = "Vertex Tax Calculation OneWorld";
		NetsuiteNavigationMenus navigationMenus = getInstalledBundlesMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteInstalledBundlesPage installedBundlesPage = setupManagerPage.navigationPane.navigateThrough(
			navigationMenus);

		//      ***** Bundle Doesn't Exist For One World *****
		String vertexBundleId = installedBundlesPage.getBundleId(bundleName);
		VertexLogger.log("Vertex Tax Calculation bundle id - " + vertexBundleId, VertexLogLevel.INFO);

		String vertexVersionNumber = installedBundlesPage.getVersionNumber(bundleName);
		VertexLogger.log("Vertex Tax Calculation version number - " + vertexVersionNumber, VertexLogLevel.INFO);

		boolean isVertexBundleOkay = installedBundlesPage.isStatusOkay(bundleName);
		VertexLogger.log("Vertex Tax Calculation bundle status - " + isVertexBundleOkay, VertexLogLevel.INFO);

		String vertexInstallationDate = installedBundlesPage.getInstalledOn(bundleName);
		VertexLogger.log("Vertex Tax Calculation installation date - " + vertexInstallationDate, VertexLogLevel.INFO);

		String vertexLastUpdateDate = installedBundlesPage.getLastUpdate(bundleName);
		VertexLogger.log("Vertex Tax Calculation last update date - " + vertexLastUpdateDate, VertexLogLevel.INFO);
	}
}
