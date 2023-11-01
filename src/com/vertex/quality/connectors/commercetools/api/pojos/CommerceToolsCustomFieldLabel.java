package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 *custom field Label POJO for commercetools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomFieldLabel
{
	public String customLabel;

    public CommerceToolsCustomFieldLabel(String fieldLabel) {
		
		this.customLabel = fieldLabel;
	}



	public String getCustomLabel() {
		return customLabel;
	}

	public void setCustomLabel(String fieldLabelEn) {
		this.customLabel = fieldLabelEn;
	}
}