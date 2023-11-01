package com.vertex.quality.connectors.kibo.enums;

/**
 * contains strings for the locaters of the pre registered Customers to use for automation
 *
 * @author osabha
 */
public enum KiboCustomers {
    Customer1(".x-boundlist-list-ct > div:nth-child(5)"),
    Customer2(".x-boundlist-list-ct > div:nth-child(6)"),
    Customer3(".x-boundlist-list-ct > div:nth-child(46)"),
    Customer4(".x-boundlist-list-ct > div:nth-child(48)"),
    tax_exempt_customer(".x-boundlist-list-ct > div:nth-child(37)"),
    Customer5("can bc"),
    Customer6(".x-boundlist-list-ct > div:nth-child(1)");

    public String value;

    KiboCustomers(String val) {
        this.value = val;
    }
}
