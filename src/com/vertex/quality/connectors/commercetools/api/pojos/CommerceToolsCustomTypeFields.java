package com.vertex.quality.connectors.commercetools.api.pojos;

import java.util.List;

/**
 * custom field type POJO for commercetools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomTypeFields
{
	
	 	public String key;
	    public CommerceToolsCustomFieldName name;
	    public List<String> resourceTypeIds;
	    public CommerceToolsCustomFieldDescription description;
	    public List<CommerceToolsCustomFieldDefinition> fieldDefinitions;
	    
		public CommerceToolsCustomTypeFields(String key, CommerceToolsCustomFieldName name, List<String> resourceTypeIds, CommerceToolsCustomFieldDescription description,
				List<CommerceToolsCustomFieldDefinition> fieldDefinitions) {
		
			this.key = key;
			this.name = name;
			this.resourceTypeIds = resourceTypeIds;
			this.description = description;
			this.fieldDefinitions = fieldDefinitions;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public CommerceToolsCustomFieldName getName() {
			return name;
		}
		public void setName( CommerceToolsCustomFieldName name) {
			this.name = name;
		}
		public List<String> getResourceTypeIds() {
			return resourceTypeIds;
		}
		public void setResourceTypeIds(List<String> resourceTypeIds) {
			this.resourceTypeIds = resourceTypeIds;
		}
		public CommerceToolsCustomFieldDescription getDescription() {
			return description;
		}
		public void setDescription( CommerceToolsCustomFieldDescription description) {
			this.description = description;
		}
		public List<CommerceToolsCustomFieldDefinition> getFieldDefinitions() {
			return fieldDefinitions;
		}
		public void setFieldDefinitions(List<CommerceToolsCustomFieldDefinition> fieldDefinitions) {
			this.fieldDefinitions = fieldDefinitions;
		}

}
