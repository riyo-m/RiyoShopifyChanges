package com.vertex.quality.connectors.hybris.enums.electronics;

public enum HybrisProductNames
{
	POWERSHOTNAME("PowerShot A480"),
	MONOPODNAME("Monopod 100 - Floor Standing Monopod"),
	TMAXP3200NAME("T-MAX P3200 Film");

	String productname;

	HybrisProductNames( String productname )
	{
		this.productname = productname;
	}

	public String getproductName( )
	{
		return productname;
	}
}
