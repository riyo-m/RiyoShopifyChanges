package com.vertex.quality.connectors.ariba.api.tests.other;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.tests.base.AribaAPIBaseTest;

/**
 * this class contains test cases for all the bugs reported, to verify that their fixes are still valid
 *
 * @author osabha
 */
public class AribaReportedBugsTests extends AribaAPIBaseTest
{

	@Override
	protected AribaAPIType getDefaultAPIType( ) { return AribaAPIType.INVOICE_RECONCILIATION; }

	@Override
	protected AribaAPIRequestType getDefaultAPIRequestType( ) { return AribaAPIRequestType.TAX_CALCULATION; }





}
