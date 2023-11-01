package com.vertex.quality.connectors.taxlink.api.pojos;

import lombok.Getter;
import lombok.Setter;

/**
 * This is a POJO class for creating payload for POST request of Tax Action Ranges
 *
 * @author Shilpi.Verma
 */

@Getter
@Setter
public class TaxActionRanges
{
	private String businessUnitId;
	private String businessUnitName;
	private Object countryCode;
	private String enabledFlag;
	private Object endDate;
	private Object fusionInstanceId;
	private String holdFlag;
	private Object holdReasonCode;
	private String invoiceSource;
	private String legalEntityId;
	private String legalEntityName;
	private String rangeFromValue;
	private String rangeToValue;
	private String rangeType;
	private String startDate;
	private Object taxActionSetupDtlId;
	private Object taxActionSetupHdrId;
	private String taxDeterminationResultCode;
	private String taxResultAction;
}
