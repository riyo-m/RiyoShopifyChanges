package com.vertex.quality.connectors.episerver.common.enums;

public enum CampaignDetails
{
	// @formatter:off
	campaignName("QuickSilver"),
	AvailableFrom("1/30/2018"),
	ExpiresOn("12/30/2021"),
	SchedulingAndStatus("Active"),
	TargetMarket("All"),
	VisitorsGroup("All visitor groups..."),
	ShippingDiscount("$10 off shipping from Women's Shoes"),
	OrderDiscount("$50 off Order over $500"),
	ItemDiscount("20 % off Mens Shoes");
	// @formatter:on

	public String text;

	CampaignDetails( String text )
	{
		this.text = text;
	}
}
