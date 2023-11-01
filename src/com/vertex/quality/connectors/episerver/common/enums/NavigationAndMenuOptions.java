package com.vertex.quality.connectors.episerver.common.enums;

/**
 * Enum or Data file that contains value of Epi Server site's navigation menu & Header options
 *
 * @author Shivam.Soni
 */
public class NavigationAndMenuOptions {

    /**
     * Site navigation options
     */
    public enum SiteNavigationOptions {
        DASHBOARD("Dashboard"),
        CMS_EDIT("CMS Edit"),
        COMMERCE("Commerce");

        public String text;

        SiteNavigationOptions(String text) {
            this.text = text;
        }
    }

    /**
     * CMS Page Header menu's options for EpiServer 14.14 and above
	 * Values are for SVG data-icon names.
     */
    public enum CmsLeftMenuOptionsSVG {
        EDIT("file-pen"),
        DASHBOARD("gauge"),
        REPORTS("chart-mixed"),
		Audiances("user-group"),

		Settings("gear"),
        Add_Ons("puzzle-piece");

        public String text;

		CmsLeftMenuOptionsSVG(String text) {
            this.text = text;
        }
    }


	public enum CmsMenuOptions {
		EDIT("Edit"),
		ADMIN("Admin"),
		REPORTS("Reports"),
		VERTEX_O_SERIES("Vertex O Series"),
		VISITOR_GROUPS("Visitor Groups");

		public String text;

		CmsMenuOptions(String text) {
			this.text = text;
		}
	}
    /**
     * CMS Page -> Admin Tab Submenu options
     */
    public enum CmsSubmenuOptions {
        ADMIN("Admin"),
        CONFIG("Config"),
        CONTENT_TYPE("Content Type");

        public String text;

        CmsSubmenuOptions(String text) {
            this.text = text;
        }
    }

    /**
     * Commerce Page Header menu's options
     */
    public enum CommerceMenuOptions {
        CATALOG("Catalog"),
        MARKETING("Marketing"),
        ORDER_MANAGEMENT("Order Management"),
        ADMINISTRATION("Settings"),
        COMMERCE_MANAGER("Commerce Manager"),
        REPORTS("Reports"),
        SETTINGS("Settings");

        public String text;

        CommerceMenuOptions(String text) {
            this.text = text;
        }
    }

    /**
     * Administration Tab names
     */
    public enum AdministrationTabs {
        MARKETS("Markets"),
        WAREHOUSES("Warehouses"),
        CATALOG_INDEXING("Catalog Indexing"),
        TAX_CONFIGURATIONS("Tax Configurations"),
        SHIPPING("Shipping"),
        PAYMENTS("Payments"),
        DICTIONARIES("Dictionaries");

        public String text;

        AdministrationTabs(String text) {
            this.text = text;
        }
    }

    /**
     * Order Management Tab names
     */
    public enum OrderManagementTabs {
        CARTS("Carts"),
        ORDERS("Orders"),
        SUBSCRIPTIONS("Subscriptions");

        public String text;

        OrderManagementTabs(String text) {
            this.text = text;
        }
    }

    /**
     * Order Details Tab names
     */
    public enum OrderDetailsTabs {
        SUMMARY("Summary"),
        FORM_DETAILS("Form Details"),
        RETURN_AND_EXCHANGE("Return & Exchange"),
        CONTACT_DETAILS("Contact Details"),
        NOTES("Notes");

        public String text;

        OrderDetailsTabs(String text) {
            this.text = text;
        }
    }

    /**
     * Vertex O-Series Tab names
     */
    public enum VertexOSeriesTabs {
        COMPANY_ADDRESS("Company Address"),
        INVOICING("Invoicing"),
        SETTINGS("Settings");

        public String text;

        VertexOSeriesTabs(String text) {
            this.text = text;
        }
    }

    /**
     * Commerce Page submenu options
     */
    public enum CommerceSubmenuOptions {
        // Specific to v3.2.3
        CAMPAIGN_STATUS("CAMPAIGN STATUS"),
        DISCOUNT_TYPE("DISCOUNT TYPE"),
        MARKET("MARKET"),

        // Specific to v3.2.4
        Campaign_Status("Campaign Status"),
        Discount_Type("Discount Type"),
        Market("Market");

        public String text;

        CommerceSubmenuOptions(String text) {
            this.text = text;
        }

        /**
         * Submenu: Campaign Status' nested options
         */
        public enum CampaignStatusOptions {
            ALL("All"),
            ACTIVE("Active"),
            SCHEDULED("Scheduled"),
            EXPIRED("Expired"),
            INACTIVE("Inactive");

            public String text;

            CampaignStatusOptions(String text) {
                this.text = text;
            }
        }

        /**
         * Submenu: Discount Type's nested options
         */
        public enum DiscountTypeOptions {
            ITEM("Item"),
            ORDER("Order"),
            SHIPPING("Shipping");

            public String text;

            DiscountTypeOptions(String text) {
                this.text = text;
            }
        }
    }
}
