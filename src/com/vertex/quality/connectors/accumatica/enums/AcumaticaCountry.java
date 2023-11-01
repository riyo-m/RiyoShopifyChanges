package com.vertex.quality.connectors.accumatica.enums;

import com.vertex.quality.common.enums.Country;
import lombok.Getter;

/**
 * This extends {@link Country} to include the expanded names of countries in Acumatica
 */
@Getter
public enum AcumaticaCountry
{
	CAN(Country.CAN, "CA - Canada"),
	USA(Country.USA, "US - United States of America");

	private Country baseCountry;
	private String acumaticaName;

	AcumaticaCountry( Country country, String acumaticaName )
	{
		this.baseCountry = country;
		this.acumaticaName = acumaticaName;
	}
}
