package com.vertex.quality.connectors.netsuite.common.pojos;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import lombok.*;

/**
 * Represents an item in Netsuite
 *
 * @author hho
 */
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class NetsuiteItem
{
	@NonNull
	private NetsuiteItemName netsuiteItemName;
	private String quantity;
	private String amount;
	private String location;
	private String memoLocation;
	private String taxCode;
	private String taxDetailCode;
	private String taxType;
	private String taxBasis;
	private String taxRate;
	private String taxAmt;
	private String itemClass;

	private static NetsuiteItemBuilder builder( )
	{
		return new NetsuiteItemBuilder();
	}

	public static NetsuiteItemBuilder builder( NetsuiteItemName netsuiteItemName )
	{
		return builder().netsuiteItemName(netsuiteItemName);
	}

	//Getter for Item Location
	public String getLocation() {

 		location = location == null || location.equals("")  ? "" //Location is Empty, return Empty String
					: location; //Location is set. Return the specified location
		return location;
	}
}
