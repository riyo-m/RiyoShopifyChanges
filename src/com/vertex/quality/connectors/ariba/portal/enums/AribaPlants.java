package com.vertex.quality.connectors.ariba.portal.enums;

/**
 * describes the 'Plant' locations that are configured in Ariba's website for testing purposes
 *
 * @author dgorecki
 */
public enum AribaPlants
{
	US_AL_BIRMINGHAM("1005 - AL - Birmingham"),
	US_AZ_APACHE_JUNCTION("1016 - AZ - Apache Junction"),
	US_CA_LOS_ANGELES("Los Angeles"),
	US_CO_HIGHLANDS_RANCH("1003 - CO - Highlands Ranch"),
	US_DE_NEWARK("1008 - DE - Newark"),
	US_FL_ORLANDO("1011 - FL - Orlando"),
	US_LA_NEW_ORLEANS("1013 - LA - New Orleans"),
	US_NY_NEW_YORK("New York"),
	US_PA_LANCASTER("1007 - PA - Lancaster"),
	US_PR_PONCE("1017 - PR - Ponce"),
	US_TX_DALLAS("1010 - TX - Dallas"),
	US_WA_SAMMAMISH("1002 - WA - Sammamish"),
	US_PA_GETTYSBURG("1012 - PA - Gettysburg"),
	EU_DE_BERLIN("TestAutomationPL_EUDE_Berlin"),
	CA_NB_PLANT("TestAutomationPL_CA_NB");

	public String plantDisplayName;

	AribaPlants( final String plantDisplayName )
	{
		this.plantDisplayName = plantDisplayName;
	}
}
