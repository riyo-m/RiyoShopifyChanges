package com.vertex.quality.connectors.dynamics365.retail.enums;

public enum DRetailStores {

    ALBERTA("Alberta",
            "<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>235 12 Ave SW</StreetAddress1>\n" +
            "\t\t\t\t<City>Calgary</City>\n" +
            "\t\t\t\t<MainDivision>AB</MainDivision>\n" +
            "\t\t\t\t<PostalCode>T2R 1H7</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</PhysicalOrigin>\n" +
            "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>534 17 Ave SW</StreetAddress1>\n" +
            "\t\t\t\t<City>Calgary</City>\n" +
            "\t\t\t\t<MainDivision>AB</MainDivision>\n" +
            "\t\t\t\t<PostalCode>T2S 0B1</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</AdministrativeOrigin>"),
    BOSTON("Boston", ""),
    BRITISH_COLUMBIA("British Columbia",
            "<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>1133 Melville St</StreetAddress1>\n" +
            "\t\t\t\t<City>Vancouver</City>\n" +
            "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
            "\t\t\t\t<PostalCode>V6E 0A3</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</PhysicalOrigin>\n" +
            "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>4554 Main St</StreetAddress1>\n" +
            "\t\t\t\t<City>Vancouver</City>\n" +
            "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
            "\t\t\t\t<PostalCode>V5V 3R5</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</AdministrativeOrigin>"),
    HOUSTON("Houston", ""),
    ONTARIO("Ontario",
            "<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t\t<StreetAddress1>88 Harbord St</StreetAddress1>\n" +
                    "\t\t\t\t\t<City>Toronto</City>\n" +
                    "\t\t\t\t\t<MainDivision>ON</MainDivision>\n" +
                    "\t\t\t\t\t<PostalCode>M5S 1G5</PostalCode>\n" +
                    "\t\t\t\t\t<Country>CAN</Country>\n" +
                    "\t\t\t\t</PhysicalOrigin>\n" +
                    "\t\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t\t<StreetAddress1>1159 Queen St W</StreetAddress1>\n" +
                    "\t\t\t\t\t<City>Toronto</City>\n" +
                    "\t\t\t\t\t<MainDivision>ON</MainDivision>\n" +
                    "\t\t\t\t\t<PostalCode>M6J 1J4</PostalCode>\n" +
                    "\t\t\t\t\t<Country>CAN</Country>\n" +
                    "\t\t\t\t</AdministrativeOrigin>"),
    QUEBEC("Quebec",
            "<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>5625 Av de Monkland</StreetAddress1>\n" +
            "\t\t\t\t<City>Montreal</City>\n" +
            "\t\t\t\t<MainDivision>QC</MainDivision>\n" +
            "\t\t\t\t<PostalCode>H4A 1E2</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</PhysicalOrigin>\n" +
            "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
            "\t\t\t\t<StreetAddress1>1900 Sainte-Catherine O</StreetAddress1>\n" +
            "\t\t\t\t<City>Montreal</City>\n" +
            "\t\t\t\t<MainDivision>QC</MainDivision>\n" +
            "\t\t\t\t<PostalCode>H3H 1M4</PostalCode>\n" +
            "\t\t\t\t<Country>CAN</Country>\n" +
            "\t\t\t</AdministrativeOrigin>");

    public final String value;
    public final String addressXML;

    private DRetailStores(String value, String addressXML) {
        this.value = value;
        this.addressXML = addressXML;
    }

}
