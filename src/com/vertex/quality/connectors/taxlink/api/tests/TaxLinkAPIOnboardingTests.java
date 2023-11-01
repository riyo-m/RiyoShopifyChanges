package com.vertex.quality.connectors.taxlink.api.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.TaxLinkConstants;
import com.vertex.quality.connectors.taxlink.common.webservices.OnboardingAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Container for all test scenarios targeting Taxlink's fusion instance onboarding
 * capabilities through API calls.
 *
 * @author msalomone,ajain
 */
public class TaxLinkAPIOnboardingTests extends OnboardingAPI
{
	/**
	 * Configures test settings.
	 */
	@BeforeClass(alwaysRun = true, groups = { "TaxLink_API_regression" })
	private void setup( ) { initializer.initializeVertextlUIApiTest(); }

	/**
	 * Test for getting the onBoarded instance
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getOnboardedInstanceTest( )
	{
		VertexLogger.log("Getting OnBoarding Instance.");
		String receivedInstanceName = getOnboardedInstance();
		assertEquals(TaxLinkConstants.ECOG_DEV3_US2, receivedInstanceName);
	}

	/**
	 * Test for fetching the Oseries Url
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void fetchOseriesUrlTest( )
	{
		VertexLogger.log("Getting Oseries Url.");
		String receivedOSeriesUrl = getOSeriesUrl();
		assertEquals(TaxLinkConstants.DEMO63_URL, receivedOSeriesUrl);
	}

	/**
	 * Test for fetching the oracle erp data center dropdown values
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getErpDataCenterTest( )
	{
		VertexLogger.log("Getting OracleErp DataCenters data.");
		assertTrue(getOracleErpDataCenters());
	}

	/**
	 * Test for fetching the customerName
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getCustomerNameTest( )
	{
		VertexLogger.log("Get customerName");
		String customerName = getCustomerName();
		assertEquals(TaxLinkConstants.TEST_UI_VERTEX, customerName);
	}

	/**
	 * Test for fetching the available services
	 */
	@Test(groups = { "TaxLink_API_regression" })
	public void getServicesNameTest( )
	{
		VertexLogger.log("Get available services");
		assertTrue(getServiceName());
	}
}

