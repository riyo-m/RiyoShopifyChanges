package com.vertex.quality.connectors.netsuite.common.enums;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import lombok.Getter;

/**
 * Item names in Netsuite
 *
 * @author hho
 */
@Getter
public enum NetsuiteAddresses
{
    PA_ADDRESS(NetsuiteAddress
            .builder("19103")
            .addressLine1("1 Franklin Town Blvd")
            .city("Philadelphia")
            .state(State.PA)
            .country(Country.USA)
            .build()
    ),

    FR_ADDRESS(NetsuiteAddress
            .builder("75007")
            .addressLine1("Port de la Bourdonnais")
            .city("Paris")
            .country(Country.FR)
            .build()
    ),

    DE_ADDRESS(NetsuiteAddress
            .builder("13405")
            .addressLine1("Allée du Stade")
            .city("Berlin")
            .country(Country.DE)
            .build()
    ),

    PA_LOGAN_SQUARE(NetsuiteAddress
            .builder("19103")
            .addressLine1("1 Logan square")
            .city("Philadelphia")
            .state(State.PA)
            .country(Country.USA)
            .build()
    ),

    PH_RIZAL_PARK(NetsuiteAddress
            .builder("0913")
            .addressLine1("1 Rizal Park")
            .city("Manila")
            .country(Country.PH)
            .build()
    ),

	PK_THANDI_SARAK(NetsuiteAddress
		.builder("71000")
		.addressLine1("Thandi Sarak")
		.city("Hyderabad")
		.state(State.SN)
		.country(Country.PK)
		.build()
	),

    VN_EXEMPT_ADDRESS(NetsuiteAddress
        .builder("550000")
        .addressLine1("16 Hoàng Sa, Mân Thái, Sơn Trà")
        .city("Da Nang")
        .country(Country.VN)
        .build()
    );

    private NetsuiteAddress address;
    NetsuiteAddresses( NetsuiteAddress address)
    {
        this.address = address;
    }
}
