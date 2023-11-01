package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * Custom Field Description POJO for commercetools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomFieldDescription
{
	
    public String customFieldName;
    
    public CommerceToolsCustomFieldDescription(String fieldDescription) {
	
		this.customFieldName = fieldDescription;
	}

	public String getCustomFieldName() {
		return customFieldName;
	}

	public void setCustomFieldName(String fieldDescriptionEn) {
		this.customFieldName = fieldDescriptionEn;
	}

	
}