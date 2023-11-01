package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 *  custom field name POJO for commercetools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomFieldName
{
	 
    public String fieldName;

	public CommerceToolsCustomFieldName(String customFieldName) {
		
		this.fieldName = customFieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String customFieldNameEn) {
		this.fieldName = customFieldNameEn;
	}
}