package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Generate new password Tab in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum GenerateNewPassword
{
	GENERATE_NEW_PASSWORD_DETAILS("Generate New Vertex Accelerator Password",
		"Are you sure you want to generate a new Vertex Accelerator Password?",
		"Please download the instructions included below to upload the new password into the corresponding Oracle Cloud ERP environment.",
		"Vertex Accelerator for Oracle Cloud ERP - Vertex Accelerator Credentials Update", "logintest", "test2");
	public String headerGenNewPwdPage;
	public String confirmOnPopUpPage;
	public String infoOnPopUpPage;
	public String instructionsHeading;
	public String user;
	public String instanceName;

	GenerateNewPassword( String headerGenNewPwdPage, String confirmOnPopUpPage, String infoOnPopUpPage,
		String instructionsHeading, String user, String instanceName )
	{
		this.headerGenNewPwdPage = headerGenNewPwdPage;
		this.confirmOnPopUpPage = confirmOnPopUpPage;
		this.infoOnPopUpPage = infoOnPopUpPage;
		this.instructionsHeading = instructionsHeading;
		this.user = user;
		this.instanceName = instanceName;
	}
}
