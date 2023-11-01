package com.vertex.quality.connectors.ariba.portal.enums;

/**
 * Enum listing out the various possible options in the Actions menu
 *
 * @author dgorecki
 */
public enum AribaPortalLineItemActions
{
	// @formatter:off
	COPY_SELECTED_LINES(	"Copy Selected Lines"),
	EDIT_DETAILS(			"Edit Details"),
	EDIT_DISCOUNT(			"Edit Discount"),
	EDIT_CHARGES(			"Edit Charges"),
	DELETE_SELECTED_LINES(	"Delete Selected Lines"),
	DELETE_DISCONTS(		"Delete Discounts"),
	DELETE_CHARGES(			"Delete Charges");
	// @formatter:on

	private String optionText;

	AribaPortalLineItemActions( final String optionText )
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
