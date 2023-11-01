package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * Holds data for fields on the create order page
 * that can be interacted with or hold needed information
 * Due to the nature of the field labeling, makes it easier to find and interact with them
 *
 * @author Tanmay Mody
 */
@Getter
public enum OracleCloudCreateOrderFieldData {

    CUSTOMER(By.cssSelector("input[id*='partyNameId']"),"partyNameId",null),
    PURCHASE_ORDER(By.cssSelector("input[id*='it1::content']"),"it1", null),
    BUSINESS_UNIT(By.cssSelector("select[id*='soc3::content']"), "ic2", "Search: Business Unit"),
    BILL_TO_ACCOUNT(By.cssSelector("input[id*='billToCustomerId']"), "billToCustomerId", null),

    SELECT_ITEM(By.cssSelector("input[id*='itemNumberId']"),"itemNumberId",null),
    SELECT_UNREFERENCED_ITEM(By.cssSelector("input[id*='0:itemNumberId']"), "itemNumberId", null);

    private By locator;
    private String idOrName;
    private String buttonTitle;

    /**
     * Sets various points of information for an area on the create invoice page
     *
     * @param loc        the locator for the field
     * @param identifier identifying portion of the field's name or ID
     * @param button     title of any button associated with the field
     */
    OracleCloudCreateOrderFieldData( final By loc, final String identifier, final String button )
    {
        this.locator = loc;
        this.idOrName = identifier;
        this.buttonTitle = button;
    }
}
