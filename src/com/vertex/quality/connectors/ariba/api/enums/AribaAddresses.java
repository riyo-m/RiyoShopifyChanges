package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * @author osabha
 */
@Getter
public enum AribaAddresses
{

	US_PA_BERWYN("1041 Old Cassatt Road", "Berwyn", "PA", "19312", "US"),
	US_PA_KOP("2301 Renaissance Blvd", "King of Prussia", "PA", "19406", "US"),
	US_PA_LANCASTER("201 Granite Run Drive", "Lancaster", "PA", "17601", "US"),
	US_PA_BROOKHAVEN("60 Ruth Road", "Brookhaven", "PA", "19015", "US"),
	US_PA_PHILADELPHIA("1400 John F Kennedy Blvd", "Philadelphia", "PA", "19107", "US"),
	US_NY_NEW_YORK("691 Broadway", "NEW YORK", "NY", "10001", "US"),
	US_CA_LOS_ANGELES("5950 Broadway", "LOS ANGELES", "CA", "90030", "US"),
	GERMANY_BERLIN_1("Oranienstraße 138", " Berlin", "", "10969", "Germany"),
	GERMANY_BERLIN_2("Allée du Stade", "Berlin", "", "13405", " Germany"),
	FRANCE_MARSEILLE("38 Quai de Rive Neuve", "Marseille", "", "13007 ", "France"),
	FRANCE_SAINT_TROPEZ("", "Saint-Tropez", "", "83150", "FR"),
	CANADA_QUEBEC("2450 Boulevard Laurier G12a", "Quebec City", "", "G1V 2L1", "CA"),
	CANADA_NEW_BRUNSWICK("21 Pettingill Rd", "Quispamsis", "New Brunswick", "E2E 6B1", "CA"),
	CANADA_BRITISH_COLUMBIA("1820 Store St", "Victoria", "British Columbia", "V8T 4R2", "CA"),
	CANADA_ONTARIO("375 Bank St", "Ottawa", "Ontario", "K2P 0T2", "CA"),
	US_TX_DALLAS("4505 Ridgeside Dr", "Dallas", "TX", "75244-4902", "US"),
	US_ID_HAYDEN_LAKE("", "Hayden Lake", "ID", "83835", "US"),
	US_TN_NASHVILLE("", "Nashville", "TN", "37219", "US"),
	US_CA_RED_BLUFF("", "Red Bluff", "CA", "96080-1274", "US"),
	US_AL_BIRMINGHAM("2473 Hackworth Rd", "Birmingham", "AL", "35214", "US"),
	US_AZ_APACHE_JUNCTION("2580 Apache Trail", "Apache Junction", "AZ", "85120", "US"),
	US_CO_HIGHLANDS_RANCH("10599 Skyreach Rd", "Highlands Ranch", "CO", "80126-5635", "US"),
	US_DE_NEWARK("252 Chapman Rd", "Newark", "DE", "19702-5436", "US"),
	US_FL_ORLANDO("8701 World Center Dr", "Orlando", "FL", "32821-6358", "US"),
	US_LA_NEW_ORLEANS("5300 St Charles Ave", "New Orleans", "LA", "70118", "US"),
	US_PR_PONCE("30 Calle Mayor Cantera", "Ponce", "PR", "730", "US"),
	US_WA_SAMMAMISH("NE 8th St 22833", "Sammamish", "WA", "98074", "US"),
	INDIA_PORT_BLAIR("Varadshree, Laxminagar", "Parvati Pune", "Maharashtra", "411009", "IN"),
	INDIA_RAMDASPETH("Sus Market Road, Plot No.232", "Pune", "Maharashtra", "411021", "IN"),
	US_GA_ATLANTA("275 Baker St NW","Atlanta","Georgia","3031","US");


	private String Line1;
	private String city;
	private String state;
	private String zip;
	private String country;

	AribaAddresses( final String Line1, final String city, final String state, final String zip, final String country )
	{
		this.Line1 = Line1;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
}
