package com.vertex.quality.connectors.netsuite.common.enums;

import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import lombok.Getter;
import lombok.NonNull;

@Getter
public enum NetsuiteTestItems {

    grossPriceItem( NetsuiteItem
            .builder(NetsuiteItemName.DISCOUNT_TAX_ITEM)
            .quantity("1")
            .amount("100.00")
            .build()
    ),

    testItem25off( NetsuiteItem
            .builder(NetsuiteItemName.TESTITEM25OFF )
            .quantity("1")
            .amount("100.00")
            .build()
    );

        private NetsuiteItem item;

        NetsuiteTestItems( @NonNull NetsuiteItem item)
        {
            this.item = item;

        }
}

