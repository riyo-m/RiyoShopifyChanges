package com.vertex.quality.connectors.saptaxservice.pojos;

import lombok.*;

import java.util.List;

/**
 * Represents the SAP Tax Service quote
 *
 * @author hho
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class SAPQuoteInput
{
	@Singular
	private List<SAPBusinessPartnerExemptionDetail> BusinessPartnerExemptionDetails;
	@Singular
	private List<SAPLocation> Locations;
	@Singular("Party")
	private List<SAPParty> Party;
	private String businessPartnerId;
	private String cashDiscountPercent;
	private String companyId;
	private String currency;
	private String date;
	private String grossOrNet;
	private String id;
	private String isCashDiscountPlanned;
	private String isCompanyDeferredTaxEnabled;
	private String isTraceRequired;
	private String isTransactionWithinTaxReportingGroup;
	@Singular
	private List<SAPItem> Items;
	private String operationNatureCode;
	private String saleOrPurchase;
}
