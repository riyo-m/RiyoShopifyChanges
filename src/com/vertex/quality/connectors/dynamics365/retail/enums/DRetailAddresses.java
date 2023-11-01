package com.vertex.quality.connectors.dynamics365.retail.enums;

public enum DRetailAddresses {

    CALGARY("Calgary",
            "<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>3315 26 Ave SW</StreetAddress1>\n" +
                    "\t\t\t\t<City>Calgary</City>\n" +
                    "\t\t\t\t<MainDivision>AB</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>T3E 0N2</PostalCode>\n" +
                    "\t\t\t\t<Country>CAN</Country>\n" +
                    "\t\t\t</Destination>"),
    HOUSTON("Houston",
            "<Destination taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t\t<StreetAddress1>7770 San Felipe St</StreetAddress1>\n" +
                    "\t\t\t\t\t<City>Houston</City>\n" +
                    "\t\t\t\t\t<MainDivision>TX</MainDivision>\n" +
                    "\t\t\t\t\t<PostalCode>77063-1601</PostalCode>\n" +
                    "\t\t\t\t\t<Country>USA</Country>\n" +
                    "\t\t\t\t</Destination>"),
    MONTREAL("Montreal",
            "<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>6080 Chem. de la Côte-des-Neiges</StreetAddress1>\n" +
                    "\t\t\t\t<City>Montreal</City>\n" +
                    "\t\t\t\t<MainDivision>QC</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>H3S 1Z7</PostalCode>\n" +
                    "\t\t\t\t<Country>CAN</Country>\n" +
                    "\t\t\t</Destination>"),
    TORONTO("Toronto",
            "<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>900 Eglinton Ave W</StreetAddress1>\n" +
            "\t\t\t\t<City>Toronto</City>\n" +
            "\t\t\t\t<MainDivision>ON</MainDivision>\n" +
            "\t\t\t\t<PostalCode>M6C 2B6</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</Destination>"),
    VANCOUVER("Vancouver",
            "<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>960 W King Edward Ave</StreetAddress1>\n" +
                    "\t\t\t\t<City>Vancouver</City>\n" +
                    "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>V5Z 2E2</PostalCode>\n" +
                    "\t\t\t\t<Country>CAN</Country>\n" +
                    "\t\t\t</Destination>");

    public final String value;
    public final String addressXML;
    private DRetailAddresses(String value, String addressXML) {
        this.value = value;
        this.addressXML = addressXML;
    }
}
