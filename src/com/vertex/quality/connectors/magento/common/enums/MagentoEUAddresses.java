package com.vertex.quality.connectors.magento.common.enums;

import lombok.Getter;

/**
 * European countries' addresses for MagentoTap
 *
 * @author Shivam.Soni
 */
@Getter
public enum MagentoEUAddresses {
    BELGIUM_ADDRESS("Lepelstraat 8", "Lommel", "Belgium", "Limburg", "3920"),
    BULGARIA_ADDRESS("Pizza Djikov, бл.65, bul. Vardar", "Sofia", "Bulgaria", "Sofia City", "1309"),
    CROATIA_ADDRESS("Trg Stjepana Radića 1", "Zagreb", "Croatia", "Grad Zagreb", "10000"),
    CZECHIA_ADDRESS("ev.254, Nad Kotlaskou Ⅲ", "Libeň, Prague", "Czechia", "Prague", "180 00"),
    CYPRUS_ADDRESS("6, Ioanni Psychari", "Nicosia", "Cyprus", "South Cyprus", "2363"),
    DENMARK_ADDRESS("Ribelandevej 29", "Christiansfeld", "Denmark", "Syddanmark", "6070"),
    ESTONIA_ADDRESS("25, Rahumäe tee", "Tallinn", "Estonia", "Harjumaa", "11622"),
    FINLAND_ADDRESS("Hakulintie 68", "Turku", "Finland", "Varsinais-Suomi", "20350"),
    GREECE_ADDRESS("25, Dim. Papadimitriou", "Paiania", "Greece", "Attiki", "190 02"),
    HUNGARY_ADDRESS("Evelin útja 68. 43. ajtó", "Budapest", "Hungary", "Bács-Kiskun", "7202"),
    IRELAND_ADDRESS("Parnell Pk.", "Thurles", "Ireland", "County Tipperary", "47546"),
    ITALY_ADDRESS("Via Croce Rossa 54", "Asuni", "Italy", "Oristano", "09080"),
    LATVIA_ADDRESS("1, Lazdu iela", "Mārupes pagasts", "Latvia", "Naukšēnu novads", "LV-2166"),
    LITHUANIA_ADDRESS("09 Doe gatvė", "Marijampolė", "Lithuania", "Šiaulių Apskritis", "LT-27546"),
    LUXEMBOURG_ADDRESS("1, Am Duarref", "Troisvierges", "Luxembourg", "Canton Clervaux", "9948"),
    MALTA_ADDRESS("12, Triq l-Arznu", "Saint John", "Malta", "Central Region", "SGN 1751"),
    POLAND_ADDRESS("Łowcza 34", "Grodzisk Mazowiecki", "Poland", "mazowieckie", "05-827"),
    PORTUGAL_ADDRESS("Avenida Duque Ávila 24", "Miragaia", "Portugal", "Lisboa", "2530-424"),
    ROMANIA_ADDRESS("10D, Strada Lirei", "Oradea", "Romania", "Bihor", "410370"),
    SLOVAKIA_ADDRESS("681/68, Gronárska", "Devín", "Slovakia", "Bratislava", "841 10"),
    SLOVENIA_ADDRESS("16, Triglavska ulica", "Ljubljana", "Slovenia", "Bežigrad", "1113"),
    SPAIN_ADDRESS("C/ Benito Guinea 54", "Arenys De Munt", "Spain", "Barcelona", "08358"),
    SWEDEN_ADDRESS("7, Surbrunnsgatan", "Östermalm", "Sweden", "Södermanlands län", "114 20"),
    NETHERLANDS_ADDRESS("Houtkrocht 90", "Noordwijk", "Netherlands", "Zuid-Holland", "2201PJ");

    public final String addressLine1;
    public final String city;
    public final String country;
    public final String region;
    public final String zip5;

    MagentoEUAddresses(final String line1, final String city, final String countryName,
                       final String regionName, final String postalCode) {
        this.addressLine1 = line1;
        this.city = city;
        this.country = countryName;
        this.region = regionName;
        this.zip5 = postalCode;
    }
}
