package com.vertex.quality.connectors.taxlink.ui_e2e.enums;

/**
 * Enums for INV Rules Mapping Module Tab containing
 * Rules Mapping tab, Journal output and project output tabs
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public enum InventoryRulesMapping
{
	INVENTORY_RULES_MAPPING("Pre-Calculation Inventory Rules", "VTX_INV_ONLY", "Inventory Journal Rules",
		"Inventory Project Rules");

	public String headerPreCalcInvRulesMappingPage;
	public String invConditionSet;
	public String headerJournalOutputPage;
	public String headerProjectOutputPage;

	InventoryRulesMapping( String headerPreCalcInvRulesMappingPage, String invConditionSet,
		String headerJournalOutputPage, String headerProjectOutputPage )
	{
		this.headerPreCalcInvRulesMappingPage = headerPreCalcInvRulesMappingPage;
		this.invConditionSet = invConditionSet;
		this.headerJournalOutputPage = headerJournalOutputPage;
		this.headerProjectOutputPage = headerProjectOutputPage;
	}
}
