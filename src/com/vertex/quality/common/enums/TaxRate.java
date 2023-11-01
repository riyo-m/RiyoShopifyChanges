package com.vertex.quality.common.enums;

/**
 * This is an enum which holds all the states/cities tax rates which are used in the test scripts
 *
 * @author Shiva Mothkula
 */
public enum TaxRate
{
	AL(10.00),
	CA(7.75),
	IL(6.25),
	MI(6.00),
	NC(7.50),
	NJ(6.625),
	NY(8.875),
	WA(10.00),
	UniversalCity(9.50),
	Tysons(6.00),
	VertexDisabled(5.00),
	Bothell(9.105),
	LosAngeles(9.50),
	Edison(6.6250),
	Juneau(5.00),

	// Canada Province 
	BC(0.0),
	ON(0.0);

	public double tax;

	TaxRate( double tax )
	{
		this.tax = tax;
	}
}
