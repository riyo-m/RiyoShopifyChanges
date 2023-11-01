package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Rules Mapping Module Tab containing
 * Precalc and postcalc Rules Mapping, Condition sets and Default Mapping tabs
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum RulesMapping
{
	RuleMappingDetails("Pre-Calculation Rules", "Add Pre-Calculation Rules", "View Pre-Calculation Rules",
		"Edit Pre-Calculation Rules", "Post-Calculation Rules", "Add Post-Calculation Rules",
		"View Post-Calculation Rules", "Edit Post-Calculation Rules", "Condition Sets", "View Condition Set",
		"Add New Condition Set", "Edit Condition Set", "Copy Condition Set", "Default Mapping",
		"Are you sure you want to Disable the Condition Set? Associated Rules will also be Disabled",
		"Are you sure you want to add an End Date to the Condition Set? Associated Rules will also be End Dated.",
		"Are you sure you want to copy the associated Pre and Post-Calculation Rules? These Rules will be Enabled",
		"01/01/1900", "VTX DEFAULT CONDITION SET", "VTX_AR_ONLY", "VTX_AP_ONLY", "VTX_PO_ONLY", "VTX_OM_ONLY",
		"VTX_O2C_ONLY", "VTX_P2P_ONLY");

	public String headerPreRuleMappingPage;
	public String headerPreAddRuleMappingPage;
	public String headerPreViewRuleMappingPage;
	public String headerPreEditRuleMappingPage;
	public String headerPostRuleMappingPage;
	public String headerPostAddRuleMappingPage;
	public String headerPostViewRuleMappingPage;
	public String headerPostEditRuleMappingPage;
	public String headerConditionSetPage;
	public String headerViewConditionSetPage;
	public String headerAddConditionSetPage;
	public String headerEditConditionSetPage;
	public String headerCopyConditionSetPage;
	public String headerDefaultMappingPage;
	public String messageEditConditionSetPage;
	public String messageEditEndDateConditionSetPage;
	public String messageCopyConditionSetPage;
	public String defaultStartDate;
	public String defaultConditionSet;
	public String arOnlyConditionSet;
	public String apOnlyConditionSet;
	public String poOnlyConditionSet;
	public String omOnlyConditionSet;
	public String o2cOnlyConditionSet;
	public String p2pOnlyConditionSet;

	RulesMapping( String headerPreRuleMappingPage, String headerPreAddRuleMappingPage,
		String headerPreViewRuleMappingPage, String headerPreEditRuleMappingPage, String headerPostRuleMappingPage,
		String headerPostAddRuleMappingPage, String headerPostViewRuleMappingPage, String headerPostEditRuleMappingPage,
		String headerConditionSetPage, String headerViewConditionSetPage, String headerAddConditionSetPage,
		String headerEditConditionSetPage, String headerCopyConditionSetPage, String headerDefaultMappingPage,
		String messageEditConditionSetPage, String messageEditEndDateConditionSetPage,
		String messageCopyConditionSetPage, String defaultStartDate, String defaultConditionSet,
		String arOnlyConditionSet, String apOnlyConditionSet, String poOnlyConditionSet, String omOnlyConditionSet,
		String o2cOnlyConditionSet, String p2pOnlyConditionSet )
	{
		this.headerPreRuleMappingPage = headerPreRuleMappingPage;
		this.headerPreAddRuleMappingPage = headerPreAddRuleMappingPage;
		this.headerPreViewRuleMappingPage = headerPreViewRuleMappingPage;
		this.headerPreEditRuleMappingPage = headerPreEditRuleMappingPage;
		this.messageEditConditionSetPage = messageEditConditionSetPage;
		this.messageEditEndDateConditionSetPage = messageEditEndDateConditionSetPage;
		this.messageCopyConditionSetPage = messageCopyConditionSetPage;
		this.headerPostRuleMappingPage = headerPostRuleMappingPage;
		this.headerPostAddRuleMappingPage = headerPostAddRuleMappingPage;
		this.headerPostViewRuleMappingPage = headerPostViewRuleMappingPage;
		this.headerPostEditRuleMappingPage = headerPostEditRuleMappingPage;
		this.headerConditionSetPage = headerConditionSetPage;
		this.headerViewConditionSetPage = headerViewConditionSetPage;
		this.headerAddConditionSetPage = headerAddConditionSetPage;
		this.headerEditConditionSetPage = headerEditConditionSetPage;
		this.headerCopyConditionSetPage = headerCopyConditionSetPage;
		this.headerDefaultMappingPage = headerDefaultMappingPage;
		this.defaultStartDate = defaultStartDate;
		this.defaultConditionSet = defaultConditionSet;
		this.arOnlyConditionSet = arOnlyConditionSet;
		this.apOnlyConditionSet = apOnlyConditionSet;
		this.poOnlyConditionSet = poOnlyConditionSet;
		this.omOnlyConditionSet = omOnlyConditionSet;
		this.o2cOnlyConditionSet = o2cOnlyConditionSet;
		this.p2pOnlyConditionSet = p2pOnlyConditionSet;
	}
}
