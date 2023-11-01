package com.vertex.quality.connectors.ariba.portal.enums;

import com.vertex.quality.connectors.ariba.portal.interfaces.AribaPortalTextField;
import lombok.Getter;

/**
 * contains all the names of the item detils page fields
 *
 * @author osabha
 */
@Getter
public enum AribaCatalogItemDetailsTextFields implements AribaPortalTextField

{
	// @formatter off;
	PURCHASING_ORGANIZATION("Purch Org:", true),
	COMMODITY_CODE("Commodity Code:", true),
	COST_CENTER("Cost Center:", true),
	BILL_TO("Bill To:", true),
	PLANT("Plant:", true),
	SHIPPING_PURCHASE_GROUP("Purchase Group:", true),
	MATERIAL_GROUP("Material Group:", true),
	QUANTITY("Qty:", false),
	DELIVER_TO("Deliver To:", false),
	PRICE("Price:", false),
	TAX_JURISDICTION_CODE("Tax Jurisdiction code:", false),
	VENDOR("Vendor:", true),
	SUPPLIER_PART_NUMBER("Supplier Part Number:", false),
	SUPPLIER_AUXILIARY_PART_ID("Supplier Auxiliary Part ID:", false);
	// @formatter on;

	public String labelText;
	public boolean isCombobox;

	AribaCatalogItemDetailsTextFields( final String text, final boolean isCombo )
	{
		this.labelText = text;
		this.isCombobox = isCombo;
	}

	public String getLabel( )
	{
		return this.labelText;
	}

	public boolean isCombobox( )
	{
		return this.isCombobox;
	}
}

