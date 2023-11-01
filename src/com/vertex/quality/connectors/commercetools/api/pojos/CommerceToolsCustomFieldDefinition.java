package com.vertex.quality.connectors.commercetools.api.pojos;

/**
 * Custom Field Definition POJO for CommerceTools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCustomFieldDefinition
{
	
    public CommerceToolsCustomFieldType type;
    public String name;
    public CommerceToolsCustomFieldLabel label;
    public boolean required;
    public String inputHint;
    
    public CommerceToolsCustomFieldDefinition( CommerceToolsCustomFieldType type, String name, CommerceToolsCustomFieldLabel label, boolean required, String inputHint) {
		
		this.type = type;
		this.name = name;
		this.label = label;
		this.required = required;
		this.inputHint = inputHint;
	}
    
	public CommerceToolsCustomFieldType getType() {
		return type;
	}
	public void setType( CommerceToolsCustomFieldType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommerceToolsCustomFieldLabel getLabel() {
		return label;
	}
	public void setLabel( CommerceToolsCustomFieldLabel label) {
		this.label = label;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getInputHint() {
		return inputHint;
	}
	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}
	
}
