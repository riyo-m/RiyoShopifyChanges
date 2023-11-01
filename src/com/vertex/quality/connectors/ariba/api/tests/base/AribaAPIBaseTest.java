package com.vertex.quality.connectors.ariba.api.tests.base;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.tests.VertexAPIBaseTest;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIAddressTypes;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAddresses;
import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;

/**
 * Base test for Ariba API
 *
 * @author hho osabha
 */

public abstract class AribaAPIBaseTest extends VertexAPIBaseTest
{
	protected AribaAPITestUtilities apiUtil;

	// Preset data used in most cases for SOAP API
	protected final ResponseCodes statusSuccess = AribaAPITestUtilities.STATUS_SUCCESS;
	protected final String tenant = AribaAPITestUtilities.TENANT;
	protected final String companyCode = AribaAPITestUtilities.COMPANY_CODE;
	protected final String defaultCommodityCode = AribaAPITestUtilities.DEFAULT_COMMODITY_CODE;

	abstract protected AribaAPIType getDefaultAPIType( );
	abstract protected AribaAPIRequestType getDefaultAPIRequestType( );

	/**
	 * performs the setup which is necessary for api tests of ariba
	 */
	@Override
	protected void setupTestCase( )
	{
		this.apiUtil = new AribaAPITestUtilities(getDefaultAPIType(), getDefaultAPIRequestType());
	}

	protected void setItemBillingAddresses( AribaAddresses address, int itemIndex )
	{
		apiUtil.requestData.setAddress(itemIndex, AribaAPIAddressTypes.BILLING, address.getCity(), address.getCountry(),
			address.getLine1(), null, null, address.getZip(), address.getState());
	}

	protected void setItemShipToAddress( AribaAddresses address, int itemIndex )
	{
		apiUtil.requestData.setAddress(itemIndex, AribaAPIAddressTypes.SHIP_TO, address.getCity(), address.getCountry(),
			address.getLine1(), null, null, address.getZip(), address.getState());
	}

	protected void setItemShipFromAddress( AribaAddresses address, int itemIndex )
	{
		apiUtil.requestData.setAddress(itemIndex, AribaAPIAddressTypes.SHIP_FROM, address.getCity(),
			address.getCountry(), address.getLine1(), null, null, address.getZip(), address.getState());
		setItemSupplierAddress(address, itemIndex);
	}

	protected void setItemSupplierAddress( AribaAddresses address, int itemIndex )
	{
		apiUtil.requestData.setAddress(itemIndex, AribaAPIAddressTypes.SUPPLIER, address.getCity(),
			address.getCountry(), address.getLine1(), null, null, address.getZip(), address.getState());
	}

	/**
	 * @param itemIndex
	 * @param quantity
	 * @param itemPrice
	 * @param taxExclusion
	 */
	protected void setItemInformationForRequisition( int itemIndex, double quantity, double itemPrice,
		boolean taxExclusion )
	{

		String taxExclusionString = Boolean.toString(taxExclusion);
		String itemQuantity = String.valueOf(quantity);
		String numberInCollection = String.valueOf(itemIndex);
		String netAmount = String.valueOf(itemPrice * quantity);
		apiUtil.requestData.setItemRequiredValues(itemIndex, numberInCollection, "0", itemQuantity, taxExclusionString);
		//TODO commenting this out for now, need to decide whether to delete or implement differently after team discussion
		//apiUtil.requestData.setItemSupplierID(itemIndex, "Automation New Ariba Test Supplier");
		apiUtil.requestData.setItemOriginalSupplierAmount(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemNetAmountValues(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(itemIndex, "unspsc", "432320", "EA");
		apiUtil.requestData.setItemCommodityCode(itemIndex, "014");
	}

	protected void setItemInformationForReconciliation( int itemIndex, int quantity, double itemPrice,
		boolean taxExclusion, int parentLineItemNumber )
	{
		String parentLineItem = String.valueOf(parentLineItemNumber);
		String taxExclusionString = Boolean.toString(taxExclusion);
		String itemQuantity = String.valueOf(quantity);
		String numberInCollection = String.valueOf(itemIndex);
		String netAmount = String.valueOf(itemPrice * quantity);
		apiUtil.requestData.setItemRequiredValues(itemIndex, numberInCollection, parentLineItem, itemQuantity,
			taxExclusionString);
		//TODO commenting this out for now, need to decide whether to delete or implement differently after team discussion
		//apiUtil.requestData.setItemSupplierID(itemIndex, "Automation New Ariba Test Supplier");
		apiUtil.requestData.setItemOriginalSupplierAmount(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemNetAmountValues(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(itemIndex, "unspsc", "432320", "EA");
		apiUtil.requestData.setItemCommodityCode(itemIndex, "014");
	}

	protected void setTaxItemInformationForReconciliation( int itemIndex, int quantity, double itemPrice,
		int parentLineItemNumber )
	{
		String parentLineItem = String.valueOf(parentLineItemNumber);

		String itemQuantity = String.valueOf(quantity);
		String numberInCollection = String.valueOf(itemIndex);
		String netAmount = String.valueOf(itemPrice * quantity);
		apiUtil.requestData.setItemRequiredValues(itemIndex, numberInCollection, parentLineItem, itemQuantity, "true");
		apiUtil.requestData.setItemSupplierID(itemIndex, "Automation New Ariba Test Supplier");
		apiUtil.requestData.setItemOriginalSupplierAmount(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemNetAmountValues(itemIndex, netAmount, "USD");
	}

	protected void setCatalogItemInformationForPosting( int itemIndex, double quantity, double itemPrice,
		boolean taxExclusion, int parentLineItemNumber )
	{
		String parentLineItem = String.valueOf(parentLineItemNumber);
		String taxExclusionString = Boolean.toString(taxExclusion);
		String itemQuantity = String.valueOf(quantity);
		String numberInCollection = String.valueOf(itemIndex);
		String netAmount = String.valueOf(itemPrice * quantity);
		apiUtil.requestData.setItemRequiredValues(itemIndex, numberInCollection, parentLineItem, itemQuantity,
			taxExclusionString);
		apiUtil.requestData.setItemSupplierID(itemIndex, "Automation New Ariba Test Supplier");
		apiUtil.requestData.setItemBuyerTaxRegistrationNumber(itemIndex, "");
		apiUtil.requestData.setItemSellerTaxRegistrationNumber(itemIndex, "");
		apiUtil.requestData.setItemOriginalSupplierAmount(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemNetAmountValues(itemIndex, netAmount, "USD");
		apiUtil.requestData.setItemDescriptionCommonCommodityCode(itemIndex, "unspsc", "432320", "EA");
		apiUtil.requestData.setItemCommodityCode(itemIndex, "014");
		apiUtil.requestData.setItemLineType(itemIndex, "1", "catalogItem");


	}
}
