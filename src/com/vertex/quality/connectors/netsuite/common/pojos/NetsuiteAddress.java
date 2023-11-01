package com.vertex.quality.connectors.netsuite.common.pojos;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import lombok.*;

/**
 * Represents an address in Netsuite
 *
 * @author hho
 */
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class NetsuiteAddress
{
	@Builder.Default
	private String fullAddressLine1 = "";
	@Builder.Default
	private String addressLine1 = "";
	@Builder.Default
	private String addressLine2 = "";
	@Builder.Default
	private String addressLine3 = "";
	@Builder.Default
	private String city = "";
	private State state;
	private Country country;
	@Builder.Default
	private String zip5 = "";
	@Builder.Default
	private String zip9 = "";
	@Builder.Default
	private String attention = "";
	@Builder.Default
	private String addressee = "";

	private static NetsuiteAddressBuilder builder( )
	{
		return new NetsuiteAddressBuilder();
	}

	public static NetsuiteAddressBuilder builder( String zip5 )
	{
		return builder().zip5(zip5);
	}

	/**
	 * Gets the cleansed dialog address
	 *
	 * @return the cleansed address
	 */
	public String getCleansedDialogAddress( )
	{
		String cleansedAddress = "";

		String zip = zip9;
		if ( zip == null || zip.isEmpty() )
		{
			zip = zip5;
		}

		String address = fullAddressLine1;
		if ( address == null || address.isEmpty() )
		{
			address = addressLine1;
		}

		cleansedAddress += address + "\n";
		if ( addressLine2 != null && !addressLine2.isEmpty() )
		{
			cleansedAddress += addressLine2 + "\n";
		}

		if( city != null){
			cleansedAddress += city+ " ";
		}

		if( state != null) {
			cleansedAddress += state.abbreviation;
		}

		cleansedAddress += " " + zip;

		return cleansedAddress;
	}

	/**
	 * Gets the cleansed address
	 *
	 * @return the cleansed address
	 */
	public String getCleansedAddress( )
	{
		String cleansedAddress = "";
		if ( attention != null && !attention.isEmpty() )
		{
			cleansedAddress += attention + "\n";
		}

		if ( addressee != null && !addressee.isEmpty() )
		{
			cleansedAddress += addressee + "\n";
		}
		cleansedAddress += getCleansedDialogAddress() + "\n" + country.fullName;
		return cleansedAddress;
	}
}
