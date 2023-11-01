package com.vertex.quality.connectors.netsuite.common.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * Represents the POJO for prices on transactions
 *
 * @author hho
 */
@Builder
@Getter
@Setter
public class NetsuitePrices
{
	private String subtotal;
	private String transactionDiscount;
	private String taxAmount;
	private String shippingCost;
	private String handlingCost;
	private String giftCertificateAmount;
	private String total;
	@Singular
	private List<String> itemTaxRates;
	@Singular
	private List<String> itemTaxCodes;
}
