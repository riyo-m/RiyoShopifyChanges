package com.vertex.quality.connectors.bigcommerce.common.enums;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceAddress;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidAddress;
import lombok.Getter;

/**
 * contains address used for Big Commerce test data
 *
 * @author osabha ssalisbury
 */
@Getter
public enum BigCommerceTestDataAddress {
    US_PA_ADDRESS_1("1270 York Road", "Gettysburg", "United States", "Pennsylvania", "17325"),
    WA_SAMMAMISH_ADDRESS("NE 8th St22833", "Sammamish", "United States", "Washington", "98074"),
    NEWJERSEY_ADDRESS("883 Route 1 Edison", "Edison", "United States", "New Jersey", "08817"),
    DE_ADDRESS("100 N Orange St Wilmington", "Wilmington", "United States", "Delaware", "19801"),
    US_PA_ADDRESS_2("1 Citizens Bank Way", "Philadelphia", "United States", "Pennsylvania", "19148"),
    NEW_ORLEANS("5300 St Charles Ave", "New Orleans", "United States", "Los Angeles", "70115"),
    CO_ADDRESS("1575 Space Center Drive Colorado Springs", "Colorado Springs", "United States", "Colorado", "80915"),
    US_PA_ADDRESS_3("2301 Renaissance Blvd", "King of Prussia", "United States", "Pennsylvania", "19406"),
    US_PA_ADDRESS_4("904 S HARVARD BLVD 27", "LOS ANGELES", "United States", "California", "90005"),
    US_CA_ADDRESS_1("685 Market St", "San Francisco", "United States", "California", "94105"),
    FRANCE_PARIS_ADDRESS("Port de la Bourdonnais", "Paris", "France", "", "75007"),
    GERMANY_BERLIN_ADDRESS("Allée du Stade", "Berlin", "Germany", "Stade", "13405"),
    US_CA_ADDRESS("100 Universal City Plazza", "University City", "US", "California", "91608-1002"),
    AUSTRIA_MITTLEBERG_ADDRESS("Moosstraße 7", "MITTELBERG", "AUSTRIA", "Moosstraße", "6993"),
    BELGIUM_ADDRESS("Rue Léopold 2", "La Louvière", "Belgium", "Rue Léopold 2", "7100"),
    CZECH_ADDRESS("Žižkova 1472", "Brodek U Prostejova", "The Czech Republic", "Olomoucký kraj", "798 07"),
    DENMARK_ADDRESS("Buddingevej 195", "Søborg", "Denmark", "Buddingevej", "2860"),
    FINLAND_ADDRESS("Hakulintie 68", "Turku", "Finland", "Varsinais-Suomi", "20350"),
    IRELAND_ADDRESS("Parnell Pk.", "Thurles", "Ireland", "County Tipperary", "47546"),
    ITALY_ADDRESS("Via Croce Rossa 54", "Asuni", "Italy", "Oristano", "09080"),
    PORTUGESE_ADDRESS("Via Croce Rossa 54", "Asuni", "Italy", "Oristano", "09080"),
    SLOVENIA_ADDRESS("Kolodvorska 44", "Kranj", "Slovenia", "Kolodvorska", "4502"),
    SPAIN_ADDRESS("Calle Porvenir", "Sevilla", "Spain", "Calle Porvenir", "41013"),
    POLAND_ADDRESS("ul. Kosmonautów 25", "Racibórz", "Poland", "ul. Kosmonautów 25", "47-404"),
    SWEDEN_ADDRESS("Bonaröd 97", "Dalstorp", "Sweden", "Bonaröd 97", "510 95"),
    UNITED_KINGDOM_ADDRESS("13 Merthyr Road", "Burghfield Hill", "United Kingdom", "Burghfield Hill", "RG7 1DA"),
    NETHERLANDS_ADDRESS("Houtkrocht 90", "Noordwijk", "Netherlands", "Zuid-Holland", "2201PJ"),
    GREECE_THESSALONKIKI_ADDRESS("Paralia Schoinonta", "Analipsi", "Greece", "Thessalonkiki", "85900"),
    US_CA_ADDRESS_2("5950 Broadway", "Los Angeles", "United States", "California", "90030"),
    US_AZ_ADDRESS_1("1031 Olive Rd", "Tucson", "United States", "Arizona", "85721"),
    US_AZ_ADDRESS_2("6414 1st St", "Woodruff", "United States", "Arizona", "85942"),
    US_AZ_ADDRESS_3("11039 N 87th Ave", "Peoria", "United States", "Arizona", "85345"),
    CAN_ONTARIO_ADDRESS_1("100 Queen St W", "Toronto", "Canada", "Ontario", "M5H 2N1"),
    CAN_ONTARIO_BAD_ADDRESS(null, null, null, null, null),
    CAN_NEW_BRUNSWICK_ADDRESS_1("435 Brookside Dr", "Fredericton", "Canada", "New Brunswick", "E3A 8V4"),
    CAN_BRITISH_COLUMBIA_ADDRESS_1("370 E Broadway", "Vancouver", "Canada", "British Columbia", "V5T 4G5"),
    CAN_NEW_BRUNSWICK_ADDRESS_2("11 Bancroft Point Rd", "Grand Manan", "Canada", "New Brunswick", "E5G 4C1"),
    CAN_NEW_BRUNSWICK_ADDRESS_3("21 Pettingill Rd", "Quispamsis", "Canada", "New Brunswick", "E2E 6B1"),
    CAN_ONTARIO_ADDRESS_2("375 Bank St", "Ottawa", "Canada", "Ontario", "K2P 1Y2"),
    CAN_BRITISH_COLUMBIA_ADDRESS_2("1820 Store St Victoria", "Vancouver", "Canada", "British Columbia", "V8T 4R4"),
    CAN_QUEBEC_ADDRESS("2450 Boulevard Laurier G12a", "BOUL LAURIER", "Canada", "Quebec", "G1V 2L1");

    private String line1;
    private String city;
    //these have to use underscores because they correspond to keys in the JSON for a quote request
    private String country_name;
    private String region_name;
    private String postal_code;

    protected BigCommerceAddress pojo;

    BigCommerceTestDataAddress(final String line1, final String city, final String countryName,
                               final String regionName, final String postalCode) {
        this.line1 = line1;
        this.city = city;
        this.country_name = countryName;
        this.region_name = regionName;
        this.postal_code = postalCode;

        this.pojo = new BigCommerceValidAddress(this);
    }

    public BigCommerceAddress buildPojo() {
        BigCommerceAddress pojoCopy = this.pojo.copy();
        return pojoCopy;
    }
}





