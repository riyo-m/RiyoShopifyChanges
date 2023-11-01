package com.vertex.quality.connectors.netsuite.scis.tests;

import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import com.vertex.quality.connectors.netsuite.scis.pages.NetsuiteSCISGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.scis.pages.NetsuiteSCISStorePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class NetsuiteSCISStoreTest extends NetsuiteBaseTest
{

	/*
	 * Checks that when the "Do not trigger
	 */
	@Test
	protected void triggerSCISTest() {
		// data
		String item1 = "Wine Crate Table";
		String item2 = "Birch Log Coffee Table";
		NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();

		// test start
		// set Vertex for SCIS to true
		NetsuiteHomepage homepage = signIntoHomepageAsSCIS();
		NetsuiteSCISGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.selectVertexForSCIS();
		generalPreferencesPage.savePreferences();

		// Check that tax is applied and finish transaction
		NetsuiteSCISStorePage storePage = signIntoStorePage();
		storePage.addItem(item1);
		storePage.addItem(item2);

		String subtotal = storePage.getSubtotal();
		String total = storePage.getTotal();
		assertTrue(!subtotal.equals(total));

		storePage.cash();
		storePage.tenderUp();
		storePage.applyPayment();
		storePage.waitForReceipt();
	}

	/*
	 * Checks that when the "Do not trigger
	 */
	@Test
	protected void doNotTriggerSCISTest() {
		// data
		String item1 = "Wine Crate Table";
		String item2 = "Birch Log Coffee Table";
		NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();

		// set Vertex for SCIS to false
		NetsuiteHomepage homepage = signIntoHomepageAsSCIS();
		NetsuiteSCISGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateThrough(generalPreferencesMenu);
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.unselectVertexForSCIS();
		generalPreferencesPage.savePreferences();

		// Check that tax is not applied and finish transaction
		NetsuiteSCISStorePage storePage = signIntoStorePage();
		storePage.addItem(item1);
		storePage.addItem(item2);

		String subtotal = storePage.getSubtotal();
		System.out.println(subtotal);
		String total = storePage.getTotal();
		System.out.println(total);
		assertTrue(subtotal.equals(total));

		storePage.cash();
		storePage.tenderUp();
		storePage.applyPayment();
		storePage.waitForReceipt();
	}
}
