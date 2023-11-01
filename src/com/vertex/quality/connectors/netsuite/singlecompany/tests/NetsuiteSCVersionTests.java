package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteInstalledBundlesPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Contains all tests related to outputting the bundle version for Single Company Vertex Tax Calculation *
 *
 * @author hho, jyareeda
 */

@Test(groups = { "version" })
public class NetsuiteSCVersionTests extends NetsuiteBaseSCTest
{
	/**
	 * Fetches the installed bundles and search for Tax Calculation.
	 * CNSL-646
	 */
	//TODO Install Vertex Tax Calculation Bundle and uncomment
	@Ignore
	@Test(groups = { "netsuite_smoke" })
	public void outputVertexTaxCalculationBundleTest( )
	{
		// To setup the configurations, Signing and navigating to SingleCompany homepage, Setting up General preferences
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		//Setting up the expected bundle name into the variable
		String bundleName = "Vertex Tax Calculation";

		//To fetch and store required navigation menu items
		NetsuiteNavigationMenus navigationMenus = getInstalledBundlesMenu();

		//To navigate through the selected menu's
		NetsuiteInstalledBundlesPage installedBundlesPage = setupManagerPage.navigationPane.navigateThrough(
			navigationMenus);

		//      ***** Bundle Doesn't Exist For Single Company *****
		// Gets the Connector details (bundle name, version, Installation Date, LastUpdated date for the above bundle id)
		boolean isVertexBundleDisplayed = installedBundlesPage.isBundleDisplayed(bundleName);
		VertexLogger.log("Vertex Tax Calculation bundle displayed - " + isVertexBundleDisplayed);

		String vertexBundleId = installedBundlesPage.getBundleId(bundleName);
		VertexLogger.log("Vertex Tax Calculation bundle id - " + vertexBundleId);

		String vertexVersionNumber = installedBundlesPage.getVersionNumber(bundleName);
		VertexLogger.log("Vertex Tax Calculation version number - " + vertexVersionNumber);

		boolean isVertexBundleOkay = installedBundlesPage.isStatusOkay(bundleName);
		VertexLogger.log("Vertex Tax Calculation bundle status - " + isVertexBundleOkay);

		String vertexInstallationDate = installedBundlesPage.getInstalledOn(bundleName);
		VertexLogger.log("Vertex Tax Calculation installation date - " + vertexInstallationDate);

		String vertexLastUpdateDate = installedBundlesPage.getLastUpdate(bundleName);
		VertexLogger.log("Vertex Tax Calculation last update date - " + vertexLastUpdateDate);
	}
}
