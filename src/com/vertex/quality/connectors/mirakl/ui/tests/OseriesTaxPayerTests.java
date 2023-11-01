package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.OseriesUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.pages.OSeriesTaxpayers;
import org.testng.annotations.Test;

/**
 * this class represents OSeries delete taxpayers test
 *
 * @author rohit-mogane
 */
public class OseriesTaxPayerTests extends OseriesUIBaseTest
{
	OSeriesTaxpayers taxPayers;

	/**
	 * this method is to delete the particular taxpayers from O-Series
	 *
	 * MIR-337
	 */
	@Test(groups = "oseries")
	public void deleteParticularTaxPayersTest( )
	{
		loginToOSeries();
		taxPayers = new OSeriesTaxpayers(driver);
		taxPayers.selectAsOfDate();
		taxPayers.selectMyEnterpriseTab();
		taxPayers.selectTaxPayersTab();
		taxPayers.deleteTaxPayersList(taxPayersToIgnore);
	}
}
