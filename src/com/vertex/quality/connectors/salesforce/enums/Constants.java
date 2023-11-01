package com.vertex.quality.connectors.salesforce.enums;

public class Constants
{
	public enum TaxAreaId
	{
		IL_TAXAREA_ID("140313460"),
		PA_TAXAREA_ID("390290000"),
		LA_TAXAREA_ID("191050360"),
		UC_TAXAREA_ID("50371900"),
		TN_TAXAREA_ID("430930400");

		public String text;

		TaxAreaId( String text )
		{
			this.text = text;
		}
	}

	public enum AddressSection
	{
		ADMIN_SECTION("Administrative Origin Address"),
		PHYSICAL_SECTION("Default Physical Origin Address"),
		CURRENT_SECTION("Current"),
		CORRECTED_SECTION("Corrected");

		public String text;

		AddressSection( String text )
		{
			this.text = text;
		}
	}

	public enum AddressType
	{
		BILLING_ADDRESS("Billing"),
		SHIPPING_ADDRESS("Shipping");
		public String text;

		AddressType( String text )
		{
			this.text = text;
		}
	}

	public enum LogDetails
	{
		CALL_OUT("Callout Success");

		public String text;

		LogDetails( String text )
		{
			this.text = text;
		}
	}

	public enum Stage
	{
		CLOSED("Closed Won"),
		PROSPECT("Prospecting");
		public String text;

		Stage( String text )
		{
			this.text = text;
		}
	}

	public enum DeliveryTerm
	{
		NONE("--None--"),
		SUP("SUP");
		public String text;

		DeliveryTerm( String text )
		{
			this.text = text;
		}
	}

	public enum ButtonTitles
	{
		EDIT_BUTTON("Edit Address"),
		VALIDATE_BUTTON("Validate Address"),
		FETCH_TAXAREA_BUTTON("Fetch Tax Area ID");
		public String text;

		ButtonTitles( String text )
		{
			this.text = text;
		}
	}

	public enum ValidityCheckSections
	{
		INVALID_FIELD_MAPPINGS("Invalid Object/Field Mappings"),
		DATA_TYPE_MISMATCHES("Data Type Mismatches"),
		INSUFFICIENT_FIELD_LEVEL_PERMISSIONS("Insufficient Field Level Permissions"),
		INCOMPLETE_DESTINATION_MAPPINGS("Incomplete Destination Mappings");
		public String text;

		ValidityCheckSections( String text )
		{
			this.text = text;
		}
	}

	public enum NewMapping
	{
		MAPPING_NAME("CHKFORMAPPING"),
		VERTEX_XML_MASSAGE("Quote"),
		TRANSACTION_TYPE("Sale"),
		TRANSACTION_OBJECT("Opportunity"),
		LINE_OBJECT("OpportunityLineItem ... (OpportunityId)"),
		TRANSACTION_DATE("Created Date"),
		DOCUMENT_NUMBER("Opportunity ID"),
		LINE_TAX_AMOUNT("Tax Amount"),
		EXTENDED_PRICE("Total Price"),
		QUANTITY("Quantity");

		public String text;

		NewMapping( String text )
		{
			this.text = text;
		}
	}
}
