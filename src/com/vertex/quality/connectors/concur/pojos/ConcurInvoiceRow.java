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
public class ConcurInvoiceRow
{
	protected String invoiceName;
	protected String vendorName;
	protected String invoiceNumber;
	protected String invoiceDate;
	protected String approvalStatus;
	protected String paymentStatus;
	protected String totalPay;
}
