package com.vertex.quality.connectors.ariba.api.tests.base;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;

public abstract class AribaAPIPostingBaseTest extends AribaAPIBaseTest
{
	@Override
	protected AribaAPIType getDefaultAPIType( ) { return AribaAPIType.INVOICE_RECONCILIATION;}

	@Override
	protected AribaAPIRequestType getDefaultAPIRequestType( ) { return AribaAPIRequestType.ERP_POSTING; }
}
