package com.vertex.quality.connectors.ariba.portal.interfaces;

/**
 * allows child page classes to have their own enums describing their own text input fields
 * and for this class' helper methods to be able to handle text field descriptions (i.e. enum
 * entries) from any child class' specific enum
 *
 * @author ssalisbury
 */
public interface AribaPortalTextField
{
	String getLabel( );

	boolean isCombobox( );
}
