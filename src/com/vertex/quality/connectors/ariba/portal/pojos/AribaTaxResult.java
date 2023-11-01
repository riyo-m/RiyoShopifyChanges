package com.vertex.quality.connectors.ariba.portal.pojos;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * stores the expected values for a component of the taxes on a line item in a PR
 *
 * @author dgorecki ssalisbury osabha
 */
public class AribaTaxResult
{
	public String name;
	public boolean isDeductible;
	public String rate;
	public Money taxablePrice;
	public Money taxAmount;
	public int index;

	/**
	 * constructs a pojo for a tax component
	 *
	 * @param expectedTaxablePrice price to apply tax rate on
	 * @param expectedTaxAmount    amount of tax
	 * @param stateTaxName         state jurisdiction title
	 * @param taxIndex             index of tax component
	 * @param rate                 tax rate for the component
	 * @param isDeductible         boolean for tax component deduction property
	 * @param testCurrency         the currency the tax amounts are in.
	 */
	public AribaTaxResult( final double expectedTaxablePrice, final double expectedTaxAmount, final String stateTaxName,
		final int taxIndex, final String rate, final boolean isDeductible, final CurrencyUnit testCurrency )
	{
		this.name = stateTaxName;
		this.index = taxIndex;
		this.isDeductible = isDeductible;
		this.rate = rate;
		this.taxablePrice = Money.of(testCurrency, expectedTaxablePrice);
		this.taxAmount = Money.of(testCurrency, expectedTaxAmount);
	}
}
