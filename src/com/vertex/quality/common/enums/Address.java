package com.vertex.quality.common.enums;

/**
 * This is an enum which holds all the addresses (both USA & Canada) which are used in the test
 * scripts
 *
 * @author Shiva Mothkula
 */
public enum Address
{
	// @formatter:off
	Alaska("6525 Glacier Hwy", null, "6525 Glacier Hwy", null, "Juneau", State.AK, null, "99801", "92805-5433", Country.USA),

	Anaheim("1816 E Morava Ave", null, "1816 E Morava Ave", null, "Anaheim", State.CA, null, "92805", "92805-5433", Country.USA),
	
	Bothell("20225 Bothell Everett Hwy", null, "20225 Bothell Everett Hwy", null, "Bothell", State.WA, null, "98012", "98012-8170", Country.USA),
	
	Berwyn("1041 Old Cassatt Rd", null, null, null, "Berwyn", State.PA, null, "19312", "19312-1152", Country.USA),
	
	Birmingham("2473 Hackworth Rd", "Suite 222", null, null, "Birmingham", State.AL, null, "35214", null, Country.USA),

	Colorado("10599 Skyreach Rd", null,null,null,"Highland Ranch",State.CO,	null, "80126", "80126-5635", Country.USA),

	Delaware("100 N Orange St", null, "100 N Orange St", null, "Wilmington", State.DE, null, "19801", "92805-5433", Country.USA),

	Durham("4005 Durham-Chapel Hill Blvd", null, null, null, "Durham", State.NC, null, "27707", null, Country.USA),
	
	Hammond("506 SW Railroad Ave", null, null, null, "Hammond", State.LA, null, "70403", "70403-4954", Country.USA),	
	
	Knoxville("550 W Main St", null, null, null, "Knoxville", State.TN, null, "37902", "37902-2515", Country.USA),
	
	Miramar("1800 S University Dr", null, null, null, "Miramar", State.FL, null, "33025", "33025-2231", Country.USA),
	
	Folsom("2495 Iron Point Rd #11", null, "2495 Iron Point Rd Ste 1230 # 11", null, "Folsom", State.CA, null, "95630", "95630-8710", Country.USA), 
	
	Illinois("5333 Prairie Stone Pkwy", null, "5333 Prairie Stone Pkwy", null, "Hoffman Estates", State.IL, null, "60192", "60192-3720", Country.USA),

	Louisiana("5300 St Charles Ave", null, null, null, "New Orleans", State.LA, null, "70118", null, Country.USA),

	GrandRapids("5120 28th St SE", null, null, null, "Grand Rapids", State.MI, null, "49512", null, Country.USA),
	
	Edison("883 Route 1", null, "883 US Highway 1", null, "Edison", State.NJ, null, "08817", "08817-4677", Country.USA),
	
	NewYork("45 Rockefeller Plaza", "1041 Old Cassatt Rd", "45 Rockefeller Plz", null, "New York", State.NY, null, "10111", "10111-0100", Country.USA),
	
	Dallas("4505 Ridgeside Dr", null, null, null, "Dallas", State.TX, null, "75244", "75244-7524", Country.USA),
	
	UniversalCity("100 Universal City Plaza", null, "100 Universal City Plz", null, "Universal City", State.CA, null, "91608", "91608-1002", Country.USA),

	Chester("141 Filbert Street", null, null, null, "Chester", State.PA, null, "19013", null, Country.USA),

	Tysons("1961 Chain Bridge Rd", null, null, null, "Tysons", State.VA, null, "22102", null, Country.USA),

	Wisconsin("1440 Monroe Street", null, null, null, "Madison", State.WI, null, "53711", null, Country.USA),

	PaloAlto("3075B Hansen Way", null, "3075B Hansen Way", null, "Palo Alto", State.CA, null, "94304", "94304-1000", Country.USA),

	Washington("22833 NE 8th St", "Suite 100", null, null, "Sammamish", State.WA, null, "98074", null, Country.USA),
	
	SanMateo("1530 3rd St", null, "1530 E 3rd Ave", null, "San Mateo", State.CA,	
			null, "94401", "94401-2112", Country.USA),	
	
	VertexKOPAddress("2301 Renessaince Blvd", null, null, null, "King of Prussia", State.PA, null, "19406", null, Country.USA),

	InvalidAddress("45 Rockefeller Plaza", null, null, null, "Hyderabad", State.CA, null, "99999", null, Country.USA),

	ChesterInvalidAddress("2473 Hackworth Road apt 2", null, null, null, "Chester", State.AL, null, "99999", null, Country.USA),

	Pittsburgh("1000 Airport Blvd", null, null, null, "Pittsburgh", State.PA, null, "15231", "15231-1152", Country.USA),

	LosAngeles("5950 Broadway", null, null, null, "Los Angeles", State.CA, null, "90030", "90030-1145", Country.USA),

	Gettysburg("1270 York Road", null, null, null, "Gettysburg", State.PA, null, "17325", "17325-7562", Country.USA),

	Philadelphia("2955 Market St", null, null, null, "Philadelphia", State.PA, null, "19104", "19104-2828", Country.USA),

	Juneau("6525 Glacier Hwy", null, null, null, "Juneau", State.AK, null, "99801", "99801-7905", Country.USA),

	SaltLakeCity("4924 S Murray Blvd", null ,null, null, "Salt Lake City", State.UT, null, "84123", "84123-4664", Country.USA),

	ColoradoSprings("1575 Space Center Drive", null, null, null, "Colorado Springs", State.CO, null, "80915", "80915-2441", Country.USA),
	Spokane("3104 E Palouse Hwy Ste A APT#303", null, "3104 E Palouse Hwy Ste A", "APT#303", "Spokane", State.WA, null, "99223", null, Country.USA),
	// Canada Addresses
	Quebec("119 St Jacques St", null, null, null, "Montreal", null, Province.QC, "H2Y", "H2Y 1L6", Country.CAN),
	Toronto("250 Yonge Street", "34th Floor", null, null, "Toronto", null, Province.ON,"M5B 2L7", null, Country.CAN),
	Vancouver("1055 Dunsmuir Street", "Suite 574", null, null, "Vancouver", null, Province.BC, "V7X 1L3", null, Country.CAN),
	Northbrook("12265 ON-41", null, null, null, "Northbrook", null, Province.ON, "K0H 2G0", null, Country.CAN),
	Victoria("1820 Store St", null, null, null, "Victoria", null, Province.BC, "V8T 4R4", null, Country.CAN),
	QuebecCity("2450 Boulevard Laurier G12a", null, null, null, "Quebec City", null, Province.QC, "G1V 2L1", null, Country.CAN),
	GrandManan("11 Bancroft Point Rd", null, null, null, "Grand Manan", null, Province.NB, "E5G 4C1", null, Country.CAN),
	Quispamsis("21 Pettinghill Rd", null ,null, null, "Quispamsis", null, Province.NB, "E2E 6B1", null, Country.CAN),
	Ottawa("375 Bank St", null, null, null, "Ottawa", null, Province.ON, "K2P 1Y2", null, Country.CAN),

	// Europe Addresses
	Paris("Port de la Bourdonnais", null, null, null, "Paris", null, null, "75007", null, Country.FR),
	Berlin("Allée du Stade", null, null, null, "Berlin", null, null, "13405", null, Country.DE),
	BerlinAlternate("Oranienstraße 138", null, null, null, "Berlin", null, null, "10969", null, Country.DE),
	Marseille("38-40 Quai de Rive Neuve", null, null, null, "Marseille", null, null, "13007", null, Country.FR),
	Analipsi("Paralia Schoinonta", null, null, null, "Analipsi", null, null,"859 00", null, Country.GR),
	LaLouviere("Rue Léopold 2", null, null, null, "La Louvière", null, null, "7100", null, Country.BE),
	Mittelberg("Moosstraße 7", null, null, null, "Mittelberg", null, null, "6993", null, Country.AT),
	Bruxelles("5, Boulevard Anspach 1", null, null, null, "Bruxelles", null, null, "1000", null, Country.BE),
	Denmark("195 Buddingevej", null, null, null, "Soborg", null, null, "2860", null, Country.DK),


	// India / Asia Addresses
	Chandigarh("D 40D Sector 40B", null, null, null, "Chandigarh", State.CH,null, "160036", null, Country.IND),
	NewRamdaspeth("Manomay Plaza, Plot No.272, Central Bazar Road", null, null, null, "New Ramdaspeth", State.MH, null, "440010", null, Country.IND),
	NewRamdaspethAlternate("Manomay Plaza, Plot No.275, Central Bazar Road", null, null, null, "New Ramdaspeth", State.MH, null, "440010", null, Country.IND),
	PortBlair("Airport Authority Colony", null, null, null, "Port Blair", State.AN, null, "744102", null, Country.IND),
	Singapore("510 Upper Jurong Road", null, null, null, "Singapore", null, null, "638365",null,Country.SG),
	Japan("5 Chome-4-８ Ginza, Chūō-ku", null, null, null,"Tōkyō-to", null, null, "104-0061", null, Country.JP),
	MongKok("555 Shanghai St", null, null, null, "Mong Kok", null , null, "00000", null, Country.HKG),
	ShuenWan("66 Tai Mei Tuk Rd", null, null, null, "Shuen Wan", null, null, "00000", null, Country.HKG),

	//Latin America (LATAM) Addresses
	BelizeCity("St. Thomas Street", null, null, null, "Belize City", null, null,  null, null, Country.BZ),
	SanIgnacio("BUENA VISTA STREET SAN IGNACIO CAYO BELIZE", null, null, null, "San Ignacio", null, null, "110111", null, Country.BZ),
	SanJose("Av. 14", null, null ,null, "San José", null, null, null, null, Country.CRI),
	Borgota("Tv. 4 Bis #57-52", null, null, null, "Borgota", null, null, "110111", null, Country.COL),
	;

	// @formatter:on

	public String addressLine1;
	public String addressLine2;
	public String city;
	public String zip5;
	public String cleansedAddressLine1;
	public String cleansedAddressLine2;
	public String zip9;
	public State state;
	public Province province;
	public Country country;

	Address( String addressLine1, String addressLine2, String cleansedAddressLine1, String cleansedAddressLine2,
		String city, State state, Province province, String zip5, String zip9, Country country )
	{
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.cleansedAddressLine1 = cleansedAddressLine1;
		this.cleansedAddressLine2 = cleansedAddressLine2;
		this.city = city;
		this.state = state;
		this.province = province;
		this.zip5 = zip5;
		this.zip9 = zip9;
		this.country = country;
	}
}
