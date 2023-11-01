package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Users Tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum Users
{
	USERS_DETAILS("USERS", "Users", "logintest");

	public String headerUsersPage;
	public String headerSyncUpPopUp;
	public String superUser;

	Users( String headerUsersPage, String headerSyncUpPopUp, String superUser )
	{
		this.headerUsersPage = headerUsersPage;
		this.headerSyncUpPopUp = headerSyncUpPopUp;
		this.superUser = superUser;
	}
}
