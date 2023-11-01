package com.vertex.quality.connectors.episerver.common.enums;

/**
 * Data Class that contains multiple enums for Episerver's commerce manager navigation options
 *
 * @author Shivam.Soni
 */
public class CommerceManagerNavigationOptions {

    /**
     * Navigation options of left panel of commerce manager homepage
     */
    public enum LeftPanelOptions {
        MARKET_MANAGEMENT("Market Management"),
        CUSTOMER_MANAGEMENT("Customer Management"),
        CATALOG_MANAGEMENT("Catalog Management"),
        ORDER_MANAGEMENT("Order Management"),
        REPORTING("Reporting"),
        ADMINISTRATION("Administration");

        public String text;

        LeftPanelOptions(String text) {
            this.text = text;
        }
    }

    /**
     * Administration menu navigation options
     */
    public enum AdministrationMenu {
        SYSTEM_SETTINGS("System Settings"),
        CATALOG_SYSTEM("Catalog System"),
        ORDER_SYSTEM("Order System");

        public String text;

        AdministrationMenu(String text) {
            this.text = text;
        }

        /**
         * Submenu options of Catalog System
         */
        public enum CatalogSystemOptions {
            WAREHOUSES("Warehouses"),
            TAX_CATEGORIES("Tax Categories"),
            META_CLASSES("Meta Classes"),
            META_FIELDS("Meta Fields");

            public String text;

            CatalogSystemOptions(String text) {
                this.text = text;
            }
        }
    }

    /**
     * Order Management Menu navigation options
     */
    public enum OrderManagementMenu {
        PURCHASE_ORDERS("Purchase Orders"),
        PURCHASE_ORDERS_BY_STATUS("Purchase Orders By Status"),
        CARTS("Carts"),
        PAYMENT_PLANS_RECURRING("Payment Plans (recurring)"),
        SHIPPING_RECEIVING("Shipping/Receiving");

        public String text;

        OrderManagementMenu(String text) {
            this.text = text;
        }
    }

    /**
     * Contains all common options of Order Management Submenu
     */
    public enum OrderManagementCommonSubmenu {
        TODAY("Today"),
        THIS_WEEK("This Week"),
        LAST_7_DAYS("Last 7 days"),
        THIS_MONTH("This Month"),
        LAST_30_DAYS("Last 30 days"),
        ALL("All");

        public String text;

        OrderManagementCommonSubmenu(String text) {
            this.text = text;
        }
    }

    /**
     * Shipping/Receiving options
     */
    public enum ShippingReceiving {
        SHIPMENTS("Shipments"),
        RELEASED_FOR_SHIPPING("Released for Shipping"),
        PICK_LISTS("Pick Lists"),
        RETURNS("Returns");

        public String text;

        ShippingReceiving(String text) {
            this.text = text;
        }
    }

    /**
     * Tab Names of Order Page
     */
    public enum OrderPageTabNames {
        SUMMARY("Summary"),
        DETAILS("Details"),
        PAYMENTS("Payments"),
        RETURNS("Returns"),
        NOTES("Notes");

        public String text;

        OrderPageTabNames(String text) {
            this.text = text;
        }
    }

    /**
     * Submenu options of Customer Management
     */
    public enum CustomerManagementSubmenu {
        ORGANIZATIONS("Organizations"),
        CONTACTS("Contacts"),
        ROLES("Roles");

        public String text;

        CustomerManagementSubmenu(String text) {
            this.text = text;
        }
    }
}
