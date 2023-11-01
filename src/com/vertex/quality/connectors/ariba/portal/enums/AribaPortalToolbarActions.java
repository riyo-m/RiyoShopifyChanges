package com.vertex.quality.connectors.ariba.portal.enums;

/**
 * Enum listing out the various possible options in the Actions menu
 *
 * @author dgorecki
 */
public enum AribaPortalToolbarActions
{
	UPDATE_TAXES("Update Taxes"),
	SUBMIT("Submit");

	private String optionText;

	AribaPortalToolbarActions( final String optionText )
	{
		this.optionText = optionText;
	}

	/**
	 * Get the display text of the Action option
	 *
	 * @return a string representation of the display text
	 */
	public String getActionOptionLabel( )
	{
		return this.optionText;
	}
}
