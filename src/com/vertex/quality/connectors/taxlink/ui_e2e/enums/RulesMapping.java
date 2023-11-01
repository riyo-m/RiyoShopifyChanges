package com.vertex.quality.connectors.taxlink.ui_e2e.enums;

/**
 * Enums for Rules Mapping Module Tab containing
 * Rules Mapping tab, Condition sets tab and Default Mapping tab
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum RulesMapping
{
	RuleMappingDetails("Pre-Calculation Rules","VTX_AP_ONLY","Post-Calculation Rules","VTX DEFAULT CONDITION SET");

	public String headerPreRuleMappingPage;
	public String apOnlyConditionSet;
	public String headerPostRuleMappingPage;
	public String defaultConditionSet;

	RulesMapping( String headerPreRuleMappingPage, String apOnlyConditionSet,String headerPostRuleMappingPage,String defaultConditionSet )
	{
		this.headerPreRuleMappingPage = headerPreRuleMappingPage;
		this.apOnlyConditionSet = apOnlyConditionSet;
		this.headerPostRuleMappingPage = headerPostRuleMappingPage;
		this.defaultConditionSet = defaultConditionSet;
	}
}
