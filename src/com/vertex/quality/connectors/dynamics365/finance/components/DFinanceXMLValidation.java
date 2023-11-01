package com.vertex.quality.connectors.dynamics365.finance.components;

/**
 * XML validation method class
 * @author dpatel
 */
public class DFinanceXMLValidation {

    /**
     * Get Buyer XML
     *
     * @param address1
     * @param cityA
     * @param company
     * @param countryA
     * @param mainDivisionA
     * @param postalCodeA
     * @param address2
     * @param cityB
     * @param company
     * @param countryB
     * @param mainDivisionB
     * @param postalCodeB
     */
    public String getBuyerXML( String company, String address1, String cityA, String mainDivisionA, String postalCodeA,
    String countryA, String address2, String cityB, String mainDivisionB, String postalCodeB, String countryB)
    {
        return String.format("<Buyer>\n" +
                        "\t\t\t<Company>%s</Company>\n" +
                        "\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>%s</StreetAddress1>\n" +
                        "\t\t\t\t<City>%s</City>\n" +
                        "\t\t\t\t<MainDivision>%s</MainDivision>\n" +
                        "\t\t\t\t<PostalCode>%s</PostalCode>\n" +
                        "\t\t\t\t<Country>%s</Country>\n" +
                        "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                        "\t\t\t</Destination>\n" +
                        "\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>%s</StreetAddress1>\n" +
                        "\t\t\t\t<City>%s</City>\n" +
                        "\t\t\t\t<MainDivision>%s</MainDivision>\n" +
                        "\t\t\t\t<PostalCode>%s</PostalCode>\n" +
                        "\t\t\t\t<Country>%s</Country>\n" +
                        "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                        "\t\t\t</AdministrativeDestination>",company,address1,cityA,mainDivisionA,postalCodeA,countryA,
                address2,cityB,mainDivisionB,postalCodeB,countryB);
    }

    /**
     * Get Vendor XML
     *
     * @param address1
     * @param city
     * @param vendorCode
     * @param country
     * @param mainDivision
     * @param postalCode
     * @param currencyConversionRate
     */
    public String getVendorClassXML( String vendorCode, String address1, String city, String mainDivision, String postalCode,
                                String country,String currencyConversionRate, String taxRegNumber)
    {
        return String.format("<Vendor>\n" +
                "\t\t\t<VendorCode classCode=\"%s\">%s</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>%s</StreetAddress1>\n" +
                "\t\t\t\t<City>%s</City>\n" +
                "\t\t\t\t<MainDivision>%s</MainDivision>\n" +
                "\t\t\t\t<PostalCode>%s</PostalCode>\n" +
                "\t\t\t\t<Country>%s</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">%s</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>%s</StreetAddress1>\n" +
                "\t\t\t\t<City>%s</City>\n" +
                "\t\t\t\t<MainDivision>%s</MainDivision>\n" +
                "\t\t\t\t<PostalCode>%s</PostalCode>\n" +
                "\t\t\t\t<Country>%s</Country>\n" +
                "\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"%s\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>%s</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>\n",vendorCode,vendorCode,address1,city,mainDivision,postalCode,country,currencyConversionRate,address1,city,mainDivision,postalCode,country,country,taxRegNumber);
    }
    /**
     * Get Vendor XML
     *
     * @param address1
     * @param city
     * @param vendorCode
     * @param country
     * @param mainDivision
     * @param postalCode
     * @param currencyConversionRate
     */
    public String getVendorXML( String vendorCode, String address1, String city, String mainDivision, String postalCode,
                               String country,String currencyConversionRate, String taxRegNumber)
    {
        return String.format("<Vendor>\n" +
                "\t\t\t<VendorCode>%s</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>%s</StreetAddress1>\n" +
                "\t\t\t\t<City>%s</City>\n" +
                "\t\t\t\t<MainDivision>%s</MainDivision>\n" +
                "\t\t\t\t<PostalCode>%s</PostalCode>\n" +
                "\t\t\t\t<Country>%s</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">%s</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"false\" isoCountryCode=\"%s\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>%s</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>",vendorCode,vendorCode,address1,city,mainDivision,postalCode,country,currencyConversionRate,country,taxRegNumber);
    }

    /**
     * Get the Input Tax XML
     *
     * @param jurisdictionLevel
     * @param impositionType
     * @param country
     * @param inputAmount
     */
    public String getInputTaxXML(String jurisdictionLevel, String impositionType, String country, String inputAmount){
        return String.format("<InputTax>\n" +
                "\t\t\t\t<TaxingJurisdictionLocation jurisdictionLevel=\"%s\" impositionType=\"%s\">\n" +
                "\t\t\t\t\t<Country>%s</Country>\n" +
                "\t\t\t\t</TaxingJurisdictionLocation>\n" +
                "\t\t\t\t<InputAmount>%s</InputAmount>\n" +
                "\t\t\t</InputTax>",jurisdictionLevel, impositionType, country, inputAmount);
    }

    /**
     * Get Germany Buyer XML
     */
    public String getGermanyBuyerXML()
    {
        return getBuyerXML("DEMF","Bahnhofstraße 5","Berlin","BE","10115","deu", "Bahnhofstraße 5", "Berlin","BE", "10115", "deu");
    }

    /**
     * Get Germany Buyer XML
     */
    public String getGermanyBuyerXMLForPO()
    {
        return getBuyerXML("DEMF", "Bahnhofstraße 5","Berlin", "BE", "10115", "deu","Bahnhofstraße 5","Berlin","BE","10115","deu");
    }

    /**
     * Get Germany Vendor XML
     */
    public String getGermanyVendorClassXML()
    {
        return getVendorClassXML("DE_TX_001","Amtsstrasse 23","Berlin","BE","10115","DEU", "1",
                "DE123456789");
    }

    /**
     * Get Germany Vendor XML
     */
    public String getGermanyVendorXML()
    {
        return getVendorXML("DE_TX_001","Amtsstrasse 23","Berlin","BE","10115","DEU", "1",
                "DE123456789");
    }

    /**
     * Get France Vendor XML
     */
    public String getFranceVendorXML()
    {
        return getVendorXML("FR_SI_000001","2 av Carnot","Paris","IF","91940","FRA", "1",
                "12345678912");
    }

    /**
     * Get France Vendor XML
     */
    public String getFranceVendorClassXML()
    {
        return getVendorClassXML("FR_SI_000001","2 av Carnot","Paris","IF","91940","FRA", "1",
                "12345678912");
    }

    /**
     * Get Input Tax XML for Germany Overcharge
     */
    public String getGermanyInputTaxOverChargedXML()
    {
        return getInputTaxXML("COUNTRY", "VAT", "GERMANY", "50.28");
    }

    /**
     * Get Input Tax XML for Germany Undercharged
     */
    public String getGermanyInputTaxXML()
    {
        return getInputTaxXML("COUNTRY", "VAT", "GERMANY", "2.5");
    }

    /**
     * Get Input Tax XML for Germany Reversed Charged
     */
    public String getGermanyInputTaxXMLForReversedCharge()
    {
        return getInputTaxXML("COUNTRY", "VAT", "GERMANY", "0.00");
    }
}
