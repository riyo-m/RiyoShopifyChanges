package com.vertex.quality.connectors.ariba.api.pojos;

import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.enums.AribaCustomMappings;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to setup the hash map that will contain the data to send for the API
 *
 * @author hho
 */
public class AribaAPIDataMap
{
	@Getter
	private final Map<String, Object> requestData;

	public AribaAPIDataMap( )
	{
		this.requestData = new HashMap<>();
	}

	/* Per Dave, the methods that modify the hashmap do not need javadocs
	 * All of these setter methods modify the hashmap, putting in various new key:value pairs.
	 * The keys are pre-defined in free marker template files (base.ftl and item.ftl located in resources
	 */
	public void setGeneralRequiredValues( final String partition, final String tenant, final String documentDate )
	{
		setPartitionField(partition);
		setTenantField(tenant);
		setDocumentDateField(documentDate);
	}

	public void setTenantField( final String value )
	{
		requestData.put("variant", value);
	}

	public void setPartitionField( final String value )
	{
		requestData.put("partition", value);
	}

	public void setDocumentDateField( final String value )
	{
		requestData.put("documentDate", value);
	}

	public void setGeneralOptionalValues( final String companyCode, final String organizationName,
		final String organizationUnit, final String uniqueName, final String relatedDocument )
	{
		requestData.put("companyCode", companyCode);
		requestData.put("organizationName", organizationName);
		requestData.put("organizationUnit", organizationUnit);
		requestData.put("uniqueName", uniqueName);
		requestData.put("relatedDocument", relatedDocument);
	}

	public void addIsRetryField( boolean isRetry )
	{

		String retry = String.valueOf(isRetry);

		requestData.put("isRetry", retry);
	}

	public void setDocumentSubType( final String documentSubType )
	{
		requestData.put("DocumentSubType", documentSubType);
	}

	public void setDocumentTypeFields( final AribaAPIType type, final AribaAPIRequestType requestType )
	{
		setTypeField(type);
		setRequestTypeField(requestType);
	}

	public void setTypeField( final AribaAPIType type )
	{
		requestData.put("type", type.getValue());
	}

	public void setRequestTypeField( final AribaAPIRequestType requestType )
	{
		requestData.put("requestType", requestType.getValue());
	}

	public void setNumberOfItems( final int numberOfItems )
	{
		requestData.put("numberOfItems", numberOfItems);
	}

	public void setItemRequiredValues( final int itemNumber, final String numberInCollection,
		final String parentLineNumber, final String quantity, final String taxExclusion )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "NumberInCollection", numberInCollection);
		requestData.put(itemNo + "ParentLineNumber", parentLineNumber);
		requestData.put(itemNo + "Quantity", quantity);
		requestData.put(itemNo + "TaxExclusion", taxExclusion);
	}

	public void setItemOriginalSupplierAmount( final int itemNumber, final String originalSupplierAmount,
		final String originalSupplierAmountCurrency )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "OriginalSupplierAmount", originalSupplierAmount);
		requestData.put(itemNo + "OriginalSupplierAmountCurrency", originalSupplierAmountCurrency);
	}

	public void setItemLineType( final int itemNumber, final String lineTypeCategory, final String lineTypeName )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "HasLineType", true);
		requestData.put(itemNo + "LineTypeCategory", lineTypeCategory);
		requestData.put(itemNo + "LineTypeName", lineTypeName);
	}

	public void setItemSAPPlant( final int itemNumber, final String SAPPlant )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "SAPPlant", SAPPlant);
	}

	public void setItemSupplierID( final int itemNumber, final String supplierID )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "SupplierID", supplierID);
	}

	public void setItemNumberOfTaxItems( final int itemNumber, final int numberOfTaxItems )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "NumberOfTaxItems", numberOfTaxItems);
	}

	public void setTaxItemForPostingCall( final int itemNumber, final int taxItemNumber, final String finalAmount,
		final boolean buyerPayable, final String originalSupplierAmount, final String taxType )
	{

	}

	public void setItemBuyerTaxRegistrationNumber( final int itemNumber, final String buyerTaxRegistrationNumber )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "BuyerTaxRegistrationNumber", buyerTaxRegistrationNumber);
	}

	public void setItemSellerTaxRegistrationNumber( final int itemNumber, final String sellerTaxRegistrationNumber )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "SellerTaxRegistrationNumber", sellerTaxRegistrationNumber);
	}

	public void setItemNetAmountValues( final int itemNumber, final String netAmount, final String netAmountCurrency )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "HasNetAmount", true);
		requestData.put(itemNo + "NetAmount", netAmount);
		requestData.put(itemNo + "NetAmountCurrency", netAmountCurrency);
	}

	public void setItemChargeAmountValues( final int itemNumber, final String chargeAmount,
		final String chargeAmountCurrency, final String chargeTypeName )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "HasChargeDetail", true);
		requestData.put(itemNo + "HasChargeAmount", true);
		requestData.put(itemNo + "ChargeAmount", chargeAmount);
		requestData.put(itemNo + "ChargeAmountCurrency", chargeAmountCurrency);
		requestData.put(itemNo + "ChargeTypeName", chargeTypeName);
	}

	public void setItemCommodityCode( final int itemNumber, final String commodityCode )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "CommodityCodeName", commodityCode);
	}

	public void setItemDescriptionCommonCommodityCode( final int itemNumber, final String domain,
		final String uniqueName, final String unitOfMeasure )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "HasDescription", true);
		requestData.put(itemNo + "HasCommonCommodityCode", true);
		requestData.put(itemNo + "CommonCommodityCodeDomain", domain);
		requestData.put(itemNo + "CommonCommodityCodeName", uniqueName);
		requestData.put(itemNo + "UnitOfMeasureName", unitOfMeasure);
	}

	public void setItemIncoTermsDetail( final int itemNumber, final String incoTermsDetail )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "IncoTermsDetail", incoTermsDetail);
	}

	public void setItemIncoTermsCode( final int itemNumber, final String incoTermsCode )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "IncoTermsCodeName", incoTermsCode);
	}

	public void setAddress( int itemNumber, final AribaAPIAddressTypes addressType, final String city,
		final String country, final String line1, final String line2, final String line3, final String zip,
		final String state )
	{
		String address = addressType.getAddressType();
		String itemNo = "item" + itemNumber;

		requestData.put(itemNo + "Has" + address + "Address", true);
		requestData.put(itemNo + address + "City", city);
		requestData.put(itemNo + address + "Country", country);
		requestData.put(itemNo + address + "Line1", line1);
		requestData.put(itemNo + address + "Line2", line2);
		requestData.put(itemNo + address + "Line3", line3);
		requestData.put(itemNo + address + "Zip", zip);
		requestData.put(itemNo + address + "State", state);
	}

	public void setItemCustomFields( final int itemNumber, final AribaCustomMappings customBooleanName,
		final String customBoolean, final AribaCustomMappings customDateName, final String customDate,
		final AribaCustomMappings customIntegerName, final String customInteger,
		final AribaCustomMappings customStringName, final String customString )
	{
		String itemNo = "item" + itemNumber;

		requestData.put(itemNo + "HasCustomFields", true);
		requestData.put(itemNo + "CustomBooleanName", customBooleanName.getValue());
		requestData.put(itemNo + "CustomBoolean", customBoolean);
		requestData.put(itemNo + "CustomDateName", customDateName.getValue());
		requestData.put(itemNo + "CustomDate", customDate);
		requestData.put(itemNo + "CustomIntegerName", customIntegerName.getValue());
		requestData.put(itemNo + "CustomInteger", customInteger);
		requestData.put(itemNo + "CustomStringName", customStringName.getValue());
		requestData.put(itemNo + "CustomString", customString);
	}

	public void setItemCustomMoneyFields( final int itemNumber, final AribaCustomMappings customMoneyName,
		final String customMoneyAmount, final String customMoneyAmountInReportingCurrency,
		final String customMoneyApproxAmount, final String customMoneyConversionDate, final String customMoneyCurrency )
	{
		String itemNo = "item" + itemNumber;
		requestData.put(itemNo + "HasCustomFields", true);
		requestData.put(itemNo + "HasCustomMoney", true);
		requestData.put(itemNo + "CustomMoneyName", customMoneyName.getValue());
		requestData.put(itemNo + "CustomMoneyAmount", customMoneyAmount);
		requestData.put(itemNo + "CustomMoneyAmountInReportingCurrency", customMoneyAmountInReportingCurrency);
		requestData.put(itemNo + "CustomMoneyApproxAmount", customMoneyApproxAmount);
		requestData.put(itemNo + "CustomMoneyConversionDate", customMoneyConversionDate);
		requestData.put(itemNo + "CustomMoneyCurrency", customMoneyCurrency);
	}

	public void setGeneralCustomFields( final AribaCustomMappings customBooleanName, final String customBoolean,
		final AribaCustomMappings customDateName, final String customDate, final AribaCustomMappings customIntegerName,
		final String customInteger, final AribaCustomMappings customStringName, final String customString )
	{
		requestData.put("hasCustomFields", true);
		requestData.put("customBooleanName", customBooleanName.getValue());
		requestData.put("customBoolean", customBoolean);
		requestData.put("customDateName", customDateName.getValue());
		requestData.put("customDate", customDate);
		requestData.put("customIntegerName", customIntegerName.getValue());
		requestData.put("customInteger", customInteger);
		requestData.put("customStringName", customStringName.getValue());
		requestData.put("customString", customString);
	}

	public void setCustomFieldsStatus( boolean enableCustomFields )
	{
		requestData.put("hasCustomFields", enableCustomFields);
	}

	public void addCustomDateField( AribaCustomMappings customField, String customFieldValue )
	{
		requestData.put("customDateName", customField.getValue());
		requestData.put("customDate", customFieldValue);
	}

	public void addCustomStringField( AribaCustomMappings customField, String customFieldValue )
	{
		requestData.put("customStringName", customField.getValue());
		requestData.put("customString", customFieldValue);
	}

	public void addCustomIntegerField( AribaCustomMappings customField, String customFieldValue )
	{
		requestData.put("customIntegerName", customField.getValue());
		requestData.put("customInteger", customFieldValue);
	}

	public void addCustomBooleanField( AribaCustomMappings customField, String customFieldValue )
	{
		requestData.put("customBooleanName", customField.getValue());
		requestData.put("customBoolean", customFieldValue);
	}

	public void addCustomBooleanField( String customField, String customFieldValue )
	{
		requestData.put("customBooleanName", customField);
		requestData.put("customBoolean", customFieldValue);
	}

	public void addCustomMoneyField( AribaCustomMappings customField, String customFieldValue )
	{
		requestData.put("hasCustomMoney", true);
		requestData.put("customMoneyName", customField.getValue());
		requestData.put("customMoneyAmount", customFieldValue);
		requestData.put("customMoneyAmountInReportingCurrency", null);
		requestData.put("customMoneyApproxAmount", null);
		requestData.put("customMoneyConversionDate", null);
		requestData.put("customMoneyCurrency", null);
	}

	public void setDocumentUniqueName( String documentName )
	{

		requestData.put("uniqueName", documentName);
	}

	public void setTaxItemInformationForPosting( final int itemIndex, int parentLineItemNumber,
		final String finalTaxAmount, final boolean buyerPayable, final String supplierTaxAmount, final String taxType )
	{
		setItemLineType(itemIndex, "2", taxType);
		String parentLineItem = String.valueOf(parentLineItemNumber);
		String numberInCollection = String.valueOf(itemIndex);
		setItemNumberOfTaxItems(parentLineItemNumber, 1);
		setItemNetAmountValues(itemIndex, finalTaxAmount, "USD");
		setItemRequiredValues(itemIndex, numberInCollection, parentLineItem, "1", "true");
		setItemSupplierID(itemIndex, "Automation New Ariba Test Supplier");
		setItemBuyerTaxRegistrationNumber(itemIndex, "");
		setItemSellerTaxRegistrationNumber(itemIndex, "");
		setItemDescriptionCommonCommodityCode(itemIndex, "unspsc", "432320", "EA");
		setItemCommodityCode(itemIndex, "014");
		//****************************//
		String taxItemNo = "TaxItem" + "1";
		String itemNo = "item" + itemIndex;
		String itemTaxItemNo = "item" + parentLineItemNumber + taxItemNo;

		requestData.put(itemNo + "FinalAmount", finalTaxAmount);
		requestData.put(itemNo + "FinalAmountCurrency", "USD");

		String isBuyerPayable = String.valueOf(buyerPayable);

		if ( supplierTaxAmount != null )
		{
			requestData.put(itemNo + "OriginalSupplierAmount", supplierTaxAmount);
			requestData.put(itemNo + "OriginalSupplierAmountCurrency", "USD");
			requestData.put(itemTaxItemNo + "OriginalSupplierAmount", supplierTaxAmount);
			requestData.put(itemTaxItemNo + "OriginalSupplierAmountCurrency", "USD");
		}

		requestData.put("item" + parentLineItem + "HasTaxItems", true);
		requestData.put(itemTaxItemNo + "FinalAmount", finalTaxAmount);
		requestData.put(itemTaxItemNo + "FinalAmountCurrency", "USD");
		requestData.put(itemTaxItemNo + "TaxType", taxType);
		requestData.put(itemTaxItemNo + "IsBuyerPayableTax", isBuyerPayable);
	}
}
