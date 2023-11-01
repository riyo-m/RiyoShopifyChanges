package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents different domains in sitecore
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreDomain
{

	COMMERCE_CUSTOMERS("CommerceCustomers"),
	COMMERCE_USERS("CommerceUsers"),
	EXTRANET("extranet"),
	SITECORE("sitecore");

	final private String text;
}
