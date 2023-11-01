package com.vertex.quality.connectors.netsuite.common.pojos;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteExpenseCategory;
import lombok.*;

/**
 * Represents an expense in Netsuite
 *
 * @author ravunuri
 */
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class NetsuiteExpense
{
	@NonNull
	private NetsuiteExpenseCategory category;
	private String currency;
	private String amount;

	private static NetsuiteExpense.NetsuiteExpenseBuilder builder( )
	{
		return new NetsuiteExpense.NetsuiteExpenseBuilder();
	}

	public static NetsuiteExpense.NetsuiteExpenseBuilder builder(NetsuiteExpenseCategory category )
	{
		return builder().category(category);
	}
}
