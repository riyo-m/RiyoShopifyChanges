package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * custom field type POJO for commercetools
 *
 * @author Mayur.Kumbhar
 */

public class CommerceToolsCustomFieldType
{
	
    public String fieldTypeName;

	public CommerceToolsCustomFieldType(String customFieldTypeName) {
		this.fieldTypeName = customFieldTypeName;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
}