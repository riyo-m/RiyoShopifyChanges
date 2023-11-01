package com.vertex.quality.connectors.saptaxservice.pojos;

import lombok.*;

import java.util.List;

/**
 * Represents an Item in the "Items" JSON array property in the API
 *
 * @author hho
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class SAPItem
{
	@Singular("additionalItemInformation")
	private List<AdditionalItemInformation> additionalItemInformation;
	@Singular("costInformation")
	private List<CostInformation> costInformation;
	@Singular("exemptionDetail")
	private List<ExemptionDetails> exemptionDetails;
	@Singular("itemClassification")
	private List<ItemClassifications> itemClassifications;
	private String certificateId;
	private String id;
	private String itemCode;
	private String itemType;
	private String quantity;
	private String shippingCost;
	private String taxCategory;
	private String taxCode;
	private String taxCodeCountry;
	private String taxCodeRegion;
	private String unitPrice;

	/**
	 * Represents the "additionalItemInformation" field in Items
	 */
	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class AdditionalItemInformation
	{
		private String information;
		private String type;
	}

	/**
	 * Represents the "costInformation" field in Items
	 */
	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class CostInformation
	{
		private String amount;
		private String costType;
	}

	/**
	 * Represents the "exemptionDetails" field in Items
	 */
	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class ExemptionDetails
	{
		private String exemptionCode;
		private String locationType;
	}

	/**
	 * Represents the "itemClassifications" field in Items
	 */
	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class ItemClassifications
	{
		private String itemStandardClassificationCode;
		private String itemStandardClassificationSystemCode;
	}
}
