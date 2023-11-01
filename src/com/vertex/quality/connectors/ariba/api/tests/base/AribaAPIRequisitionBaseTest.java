package com.vertex.quality.connectors.ariba.api.tests.base;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;

public abstract class AribaAPIRequisitionBaseTest extends AribaAPIBaseTest
{
	@Override
	protected AribaAPIType getDefaultAPIType( ) { return AribaAPIType.REQUISITION;}

	@Override
	protected AribaAPIRequestType getDefaultAPIRequestType( ) { return AribaAPIRequestType.TAX_CALCULATION; }
}
