package com.vertex.quality.connectors.concur.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an expense input
 *
 * @author alewis
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConcurInvoiceExpense
{
	protected String expenseTypeInput;
	protected String lineDescriptionInput;
	protected String quantityInput;
	protected String unitPriceInput;
	protected String totalPayInput;
	protected String calculatedTaxAmountInput;
	protected String calculatedTaxRateInput;
}
