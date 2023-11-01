package com.vertex.quality.connectors.accumatica.enums;

//TODO add the rest of the submenu homepage classes
//TODO? if merited, when split these into multiple enums by enclosing global tab, also create superclasses for all submenu homepages under that global tab?
//TODO? fill out the other tabs? maybe instead just add as needed
//TODO move the links for links in nav panes to other enums!!
//TODO add link to an enum of the tabs in the module's left panel (null if there's just 1)
//TODO - don't overthink it, I don't want 50 enums for this thing, just make it work - Dave G.

/**
 * Sub menu tabs from acumatica application under the tabs from the AcumaticaGlobalMenuOption enum
 */
public enum AcumaticaGlobalSubMenuOption
{
	//under "ORGANIZATION" primary tab
	COMMUNICATION("Communication", AcumaticaGlobalMenuOption.ORGANIZATION),
	CUSTOMER_MANAGEMENT("Customer Management", AcumaticaGlobalMenuOption.ORGANIZATION),
	ORGANIZATION_STRUCTURE("Organization Structure", AcumaticaGlobalMenuOption.ORGANIZATION),

	//under "FINANCE"
	ACCOUNTS_RECEIVABLE("Accounts Receivable", AcumaticaGlobalMenuOption.FINANCE),
	TAXES("Taxes", AcumaticaGlobalMenuOption.FINANCE),

	//under "DISTRIBUTION"
	INVENTORY("Inventory", AcumaticaGlobalMenuOption.DISTRIBUTION),
	SALES_ORDERS("Sales Orders", AcumaticaGlobalMenuOption.DISTRIBUTION),

	//under "CONFIGURATION"
	COMMON_SETTINGS("Common Settings", AcumaticaGlobalMenuOption.CONFIGURATION),

	//under "SYSTEM"
	CUSTOMIZATION("Customization", AcumaticaGlobalMenuOption.SYSTEM),

	//TODO none of these exist in current code- just delete?
	//WARN the remaining entries aren't submenu tabs of any of the primary tabs, they're page-links in the left-hand navigation pane of one of the submenus

	//OR!!!! in the 4th tab (gear icon) of the nav pane of "Accounts Receivable" submenu under "FINANCE"
	DISCOUNT_CODES("Discount Codes", null),

	//in the 4th tab (gear icon) of the nav pane of "Projects" submenu under "ORGANIZATION"
	//OR!!!! in the 1st tab (pencil icon) of the nav pane of "Common Settings" submenu under "CONFIGURATION"
	ATTRIBUTES("Attributes", null),

	//WARN- I have no clue what this originally meant, and it isn't even used in the codebase right now...
	DETAILS("Attributes", null);

	String displayValue;

	//the global menu tab that this submenu is under
	AcumaticaGlobalMenuOption globalMenuTab;

	AcumaticaGlobalSubMenuOption( final String value, final AcumaticaGlobalMenuOption globalTab )
	{
		this.displayValue = value;
		this.globalMenuTab = globalTab;
	}

	/**
	 * Get UI display value
	 *
	 * @return UI display value
	 */
	public String getValue( )
	{
		return displayValue;
	}

	public AcumaticaGlobalMenuOption getGlobalMenu( )
	{
		return globalMenuTab;
	}

	@Override
	public String toString( )
	{
		return getValue();
	}
}
