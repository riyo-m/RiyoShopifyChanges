package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * this class represents the Test Data for smoke test cases
 * for Onboarding Dashboard.
 *
 * @author mgaikwad
 */

public enum OnboardingDashboard
{
	INSTANCE_DETAILS("ecog-dev3-us2", "TestUIVertex Onboarding Dashboard", "View Onboarded Oracle Instance",
		"Edit Onboarded Oracle Instance", "ecog-dev4", "us1", "test123", "test123");

	public String instanceNameWithDC;
	public String headerOnboardingDashboardPage;
	public String headerViewInstancePage;
	public String headerEditInstancePage;
	public String instanceNameNegativeTest;
	public String dataCenterNegativeTest;
	public String clientIDTest;
	public String clientSecretTest;

	OnboardingDashboard( String instanceNameWithDC, String headerOnboardingDashboardPage, String headerViewInstancePage,
		String headerEditInstancePage, String instanceNameNegativeTest, String dataCenterNegativeTest,
		String clientIDTest, String clientSecretTest )
	{
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