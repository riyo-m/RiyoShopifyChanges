package com.vertex.quality.connectors.taxlink.ui_e2e.enums;

/**
 * this class represents the Test Data for smoke test cases
 * for Onboarding Dashboard.
 *
 * @author mgaikwad
 */

public enum OnboardingDashboard
{
	INSTANCE_DETAILS("ecog-dev3", "DEV", "us2", "vertex.accelerator", "Oracle10_", "ondpass1tid", "ecog-dev3-us2",
		"TestUIVertex Onboarding Dashboard", "Configure Customer", "Configure Customer", "ecog-dev4", "us1","test123","test123");

	public String instanceName;
	public String instanceType;
	public String oracleDataCenter;
	public String cloudERPUsername;
	public String cloudERPPassword;
	public String trustedID;
	public String instanceNameWithDC;
	public String headerOnboardingDashboardPage;
	public String headerViewInstancePage;
	public String headerEditInstancePage;
	public String instanceNameNegativeTest;
	public String dataCenterNegativeTest;
	public String clientIDTest;
	public String clientSecretTest;

	OnboardingDashboard( String instanceName, String instanceType, String oracleDataCenter, String cloudERPUsername,
		String cloudERPPassword, String trustedID, String instanceNameWithDC, String headerOnboardingDashboardPage,
		String headerViewInstancePage, String headerEditInstancePage, String instanceNameNegativeTest,
		String dataCenterNegativeTest, String clientIDTest, String clientSecretTest )
	{
		this.instanceName = instanceName;
		this.instanceType = instanceType;
		this.oracleDataCenter = oracleDataCenter;
		this.cloudERPUsername = cloudERPUsername;
		this.cloudERPPassword = cloudERPPassword;
		this.trustedID = trustedID;
		this.instanceNameWithDC = instanceNameWithDC;
		this.headerOnboardingDashboardPage = headerOnboardingDashboardPage;
		this.headerViewInstancePage = headerViewInstancePage;
		this.headerEditInstancePage = headerEditInstancePage;
		this.instanceNameNegativeTest = instanceNameNegativeTest;
		this.dataCenterNegativeTest = dataCenterNegativeTest;
		this.clientIDTest = clientIDTest;
		this.clientSecretTest = clientSecretTest;
	}
}