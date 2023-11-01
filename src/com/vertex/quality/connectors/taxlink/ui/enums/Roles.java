package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Roles tab.
 *
 * @author mgaikwad
 */
public enum Roles
{

	ROLES("Roles", "Onboarding UI Administrator", "FI Administrator", "User", "Read Only User", "User Maintenance",
		"This user can add a new Oracle Cloud ERP instance. This role only enables the ADD function with the Onboarding Dashboard. After adding the instance, the FI Administrator role is granted to the user for the instance in which it was added.",
		"This role is tied to a specific Oracle Cloud ERP instance. A user with the FI Administrator role has full access, except for adding an instance in the Onboarding Dashboard.",
		"This user is tied to a specific Oracle Cloud ERP instance and cannot add a business unit or enable retry job or Offline BIPs.",
		"This user can view all screens, but cannot update any fields.",
		"This user can sync users and grant any role to users in any instance.");

	public String pageHeaderTextRoles;
	public String onboardingRoleNameText;
	public String FIRoleNameText;
	public String userRoleNameText;
	public String readOnlyUserRoleNameText;
	public String userMaintenanceRoleNameText;
	public String onboardingRoleDescText;
	public String FIRoleDescText;
	public String userRoleDescText;
	public String readOnlyUserRoleDescText;
	public String userMaintenanceRoleDescText;

	Roles( String pageHeaderTextRoles, String onboardingRoleNameText, String FIRoleNameText, String userRoleNameText,
		String readOnlyUserRoleNameText, String userMaintenanceRoleNameText, String onboardingRoleDescText,
		String FIRoleDescText, String userRoleDescText, String readOnlyUserRoleDescText,
		String userMaintenanceRoleDescText )
	{
		this.pageHeaderTextRoles = pageHeaderTextRoles;
		this.onboardingRoleNameText = onboardingRoleNameText;
		this.FIRoleNameText = FIRoleNameText;
		this.userRoleNameText = userRoleNameText;
		this.readOnlyUserRoleNameText = readOnlyUserRoleNameText;
		this.userMaintenanceRoleNameText = userMaintenanceRoleNameText;
		this.onboardingRoleDescText = onboardingRoleDescText;
		this.FIRoleDescText = FIRoleDescText;
		this.userRoleDescText = userRoleDescText;
		this.readOnlyUserRoleDescText = readOnlyUserRoleDescText;
		this.userMaintenanceRoleDescText = userMaintenanceRoleDescText;
	}
}
