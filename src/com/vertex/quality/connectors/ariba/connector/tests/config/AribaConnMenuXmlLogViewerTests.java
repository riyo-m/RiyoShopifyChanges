package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;

/**
 * Tests of interacting with the View Logged XML Messages menu
 * to retrieve information about queries made to the connector
 * and about the connector's and O-Series' attempts to process
 * and respond to those queries
 *
 * @author ssalisbury
 */
@Test(groups = { "log" })
public class AribaConnMenuXmlLogViewerTests extends AribaConnMenuBaseTest
{
	//TODO smoke tests for text displays visible
	//TODO smoke tests for buttons accessible
	//TODO smoke tests for text/date fields accessible

	//TODO smoke test that you fetch non-null/empty string from log display field after setting start date back one month & hitting search
}
