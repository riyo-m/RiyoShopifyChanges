package com.vertex.quality.connectors.ariba.portal.tests.functionality;

import com.vertex.quality.connectors.ariba.portal.enums.AribaConnectorIntegrationEndpoints;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * contains tests on Ariba functionality, connector functionality
 *
 * @author osabha
 */
public class AribaPortalEndPointTests extends AribaTwoXPortalBaseTest
{
	/**
	 * changes the endpoint which the connector points to
	 * logs out, and then logs back in to verify change happened.
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression"})
	public void changeEndPointTest( )
	{
		boolean isSuccessful = changeEndPointForTwoXPortal(AribaConnectorIntegrationEndpoints.VERTEX_DEV_API_2);
		assertTrue(isSuccessful);
	}
}
