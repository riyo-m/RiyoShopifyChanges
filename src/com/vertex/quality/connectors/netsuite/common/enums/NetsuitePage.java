package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum NetsuitePage
{
	HOMEPAGE("/center/card.nl"),
	SALES("/accounting/transactions/salesord.nl"),
	PREFERENCES("/setup/general.nl"),
	SUBSIDIARY("/common/otherlists/subsidiarytype.nl?id="),
	SUBSIDIARY_LIST("/common/otherlists/subsidiarylist.nl"),
	EDIT_MOD("&e=T");

	private String url;

	NetsuitePage( String customListTitle )
	{
		this.url = customListTitle;
	}
}