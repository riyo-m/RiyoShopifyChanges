package com.vertex.quality.connectors.orocommerce.enums;

import lombok.Getter;

/**
 * class contains all addresses data related to Oro.
 *
 * @author alewis
 */
@Getter
public enum OroAddresses {

    US_PA_BERWYN("1041 Old Cassatt Road", "Berwyn", "Pennsylvania", "19312", "United States"),
    US_PA_KOP("2301 Renaissance Blvd", "King of Prussia", "Pennsylvania", "19406", "United States"),
    US_PA_LANCASTER("201 Granite Run Drive", "Lancaster", "Pennsylvania", "17601", "United States"),
    US_PA_BROOKHAVEN("60 Ruth Road", "Brookhaven", "Pennsylvania", "19015", "United States"),
    US_PA_PHILADELPHIA("1400 John F Kennedy Blvd", "Philadelphia", "Pennsylvania", "19107", "United States"),
    US_PA_GETTYSBURG("1270 York Road", "Gettysburg", "Pennsylvania", "17325", "United States"),
    US_NY_NEW_YORK("691 Broadway", "NEW YORK", "New York", "10001", "United States"),
    US_CA_LOS_ANGELES("5950 Broadway", "LOS ANGELES", "California", "90030", "United States"),
    AUSTRIA_MITTELBERG("Moosstraße 7", "Mittelberg", "Vorarlberg", "6993", "Austria"),
    GERMANY_BERLIN_1("Oranienstraße 138", " Berlin", "Berlin", "10969", "Germany"),
    GERMANY_BERLIN_2("Allée du Stade", "Berlin", "Berlin", "13405", " Germany"),
    GREECE_ANALIPSI("Mitropoleos 80", "Athens", "Athens", "105 56,", "Greece"),
    FRANCE_MARSEILLE("38-40 Quai de Rive Neuve", "Marseille", "Provence-Alpes-Côte d'Azur", "13007", "France"),
    FRANCE_SAINT_TROPEZ("", "Saint-Tropez", "", "83150", "FR"),
    FRANCE_PARIS("Port de la Bourdonnais", "Paris", "Île-de-France", "75007", "France"),
    CANADA_QUEBEC("2450 Boulevard Laurier G12a", "Quebec City", "Quebec", "G1V 2L1", "Canada"),
    CANADA_NEW_BRUNSWICK("21 Pettingill Rd", "Quispamsis", "New Brunswick", "E2E 6B1", "Canada"),
    CANADA_BRITISH_COLUMBIA("1820 Store St", "Victoria", "British Columbia", "V8T 4R2", "Canada"),
    CANADA_ONTARIO("375 Bank St", "Ottawa", "Ontario", "K2P 0T2", "Canada"),
    CANADA_GRAND_MANAN("11 Bancroft Point Rd", "Grand Manan", "New Brunswick", "E5G 4C1", "Canada"),
    CANADA_QUISPAMSIS("21 Pettingill Rd", "Quispamsis", "New Brunswick", "E2E 6B1", "Canada"),
    SG_CENTRAL_SG("510 Upper Jurong Road", "", "Central Singapore", "638365", "Singapore"),
    JP_TOKYO("5 Chome-4 Ginza", "Tokyo", "Tokyo", "104-0061", "Japan"),
    JP_KAWASAKI("Chome-16-1 Sugao", "Kawasaki", "Kanagawa", "216-8511", "Japan"),
    CR_SAN_JOSE("Av. 14", "San Jose", "", "10105", "Costa Rica"),
    COL_BOGOTA("Tv. 4 Bis #57-52", "Bogota", "Tolima", "110111", "Colombia"),
    CHINA_CHANGSHA("E 2nd Ring Rd, Furong Qu", "Changsha", "Hunan", "410016", "China"),
    JM_NERGIL("Av. 7 Mile Beach, Norman Manley Blvd", "Nergil", "Westmoreland", "JMDWD14", "Jamaica"),
    JM_OHO_RIOS("Av. White River Bay", "Oho Rios", "Saint Ann", "JMAKN04", "Jamaica"),
    US_TX_DALLAS("4505 Ridgeside Dr", "Dallas", "TX", "75244-4902", "US"),
    US_ID_HAYDEN_LAKE("", "Hayden Lake", "ID", "83835", "United States"),
    US_TN_NASHVILLE("", "Nashville", "TN", "37219", "United States"),
    US_CA_RED_BLUFF("", "Red Bluff", "CA", "96080-1274", "United States"),
    US_AL_BIRMINGHAM("2473 Hackworth Rd", "Birmingham", "AL", "35214", "United States"),
    US_AZ_APACHE_JUNCTION("2580 Apache Trail", "Apache Junction", "AZ", "85220", "United States"),
    US_CA_UNIVERSITY("100 Universal City Plazza", "University City", "California", "91608-1002", "United States"),
    US_CO_HIGHLANDS_RANCH("10599 Skyreach Rd", "Highlands Ranch", "CO", "80126-5635", "United States"),
    US_DE_NEWARK("252 Chapman Rd", "Newark", "Delaware", "19702-5436", "United States"),
    US_DE_WILMINGTON("100 N Orange St", "Wilmington", "Delaware", "19801", "United States"),
    US_FL_ORLANDO("8701 World Center Dr", "Orlando", "FL", "32821-6358", "United States"),
    US_LA_NEW_ORLEANS("5300 St Charles Ave", "New Orleans", "Louisiana", "70118", "United States"),
    US_PR_PONCE("30 Calle Mayor Cantera", "Ponce", "Puerto Rico", "730", "United States"),
    US_WA_SAMMAMISH("NE 8th St 22833", "Sammamish", "Washington", "98074", "United States"),
    INDIA_PORT_BLAIR("Lamba Line", "Port Blair", "Andaman and Nicobar Islands", "744103", "IN"),
    INDIA_RAMDASPETH("Manomay Plaza, Plot No.272, Central Bazar Road", "New Ramdaspeth", "Maharashtra", "440010", "IN"),
    NJ_ADDRESS("883 Route 1", "Edison", "New Jersey", "08817-4677", "United States"),
    ALASKA_ADDRESS("6525 Glacier Hwy Juneau", "Juneau", "Alaska", "99801", "United States");

    private String Line1;
    private String city;
    private String state;
    private String zip;
    private String country;

    OroAddresses(final String Line1, final String city, final String state, final String zip, final String country) {
        this.Line1 = Line1;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
}
