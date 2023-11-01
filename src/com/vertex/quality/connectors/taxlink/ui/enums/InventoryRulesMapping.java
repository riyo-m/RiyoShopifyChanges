package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Inventory Rules Mapping Module Tab containing
 * Inv Pre-calc & post-calc Rules Mapping, Condition sets tabs in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum InventoryRulesMapping
{
	RuleMappingDetails("Pre-Calculation Inventory Rules", "View Pre-Calculation Inventory Rules",
		"Edit Pre-Calculation Inventory Rules", "Inventory Journal Rules", "View Inventory Journal Rules",
		"Edit Inventory Journal Rules", "Inventory Project Rules", "View Inventory Project Rules",
		"Edit Inventory Project Rules", "Copy Inventory Condition Set", "Inventory Condition Sets",
		"View Inventory Condition Set", "Add New Inventory Condition Set", "Edit Inventory Condition Set",
		"Are you sure you want to Disable the Inventory Condition Set? Associated Inventory Rules will also be Disabled",
		"Are you sure you want to add an End Date to the Inventory Condition Set? Associated Inventory Rules will also be End Dated.",
		"Are you sure you want to copy the associated Inventory Rules? These Inventory Rules will be Enabled",
		"VTX_INV_ONLY");

	public String headerInvPreRuleMappingPage;
	public String headerViewInvPreRuleMappingPage;

	public String headerEditInvPreRuleMappingPage;
	public String headerInvJournalRuleMappingPage;
	public String headerViewInvJournalRuleMappingPage;

	public String headerEditInvJournalRuleMappingPage;

	public String headerInvProjectRuleMappingPage;
	public String headerViewInvProjectRuleMappingPage;

	public String headerEditInvProjectRuleMappingPage;
	public String headerCopyConditionSetPage;
	public String headerInvConditionSetPage;
	public String headerInvViewConditionSetPage;
	public String headerInvAddConditionSetPage;
	public String headerInvEditConditionSetPage;
	public String messageEditConditionSetPage;
	public String messageEditEndDateConditionSetPage;
	public String messageCopyConditionSetPage;

	public String invConditionSet;

	InventoryRulesMapping( String headerInvPreRuleMappingPage, String headerViewInvPreRuleMappingPage,
		String headerEditInvPreRuleMappingPage, String headerInvJournalRuleMappingPage,
		String headerViewInvJournalRuleMappingPage, String headerEditInvJournalRuleMappingPage,
		String headerInvProjectRuleMappingPage, String headerViewInvProjectRuleMappingPage,
		String headerEditInvProjectRuleMappingPage, String headerCopyConditionSetPage, String headerInvConditionSetPage,
		String headerInvViewConditionSetPage, String headerInvAddConditionSetPage, String headerInvEditConditionSetPage,
		String messageEditConditionSetPage, String messageEditEndDateConditionSetPage,
		String messageCopyConditionSetPage, String invConditionSet )
	{
		this.headerInvPreRuleMappingPage = headerInvPreRuleMappingPage;
		this.headerViewInvPreRuleMappingPage = headerViewInvPreRuleMappingPage;
		this.headerEditInvPreRuleMappingPage = headerEditInvPreRuleMappingPage;
		this.headerInvJournalRuleMappingPage = headerInvJournalRuleMappingPage;
		this.headerViewInvJournalRuleMappingPage = headerViewInvJournalRuleMappingPage;
		this.headerEditInvJournalRuleMappingPage = headerEditInvJournalRuleMappingPage;
		this.headerInvProjectRuleMappingPage = headerInvProjectRuleMappingPage;
		this.headerViewInvProjectRuleMappingPage = headerViewInvProjectRuleMappingPage;
		this.headerEditInvProjectRuleMappingPage = headerEditInvProjectRuleMappingPage;
		this.headerCopyConditionSetPage = headerCopyConditionSetPage;
		this.messageEditConditionSetPage = messageEditConditionSetPage;
		this.messageEditEndDateConditionSetPage = messageEditEndDateConditionSetPage;
		this.headerInvConditionSetPage = headerInvConditionSetPage;
		this.headerInvViewConditionSetPage = headerInvViewConditionSetPage;
		this.headerInvAddConditionSetPage = headerInvAddConditionSetPage;
		this.headerInvEditConditionSetPage = headerInvEditConditionSetPage;
		this.messageCopyConditionSetPage = messageCopyConditionSetPage;
		this.invConditionSet = invConditionSet;
	}
}
