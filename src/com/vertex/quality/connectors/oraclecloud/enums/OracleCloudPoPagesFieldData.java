package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * Storage container for all data used in relation to
 * the Create (Purchase) Order popup page, or Manage (Purchase)
 * Orders page.
 *
 * @author msalomone
 */
@Getter
public enum OracleCloudPoPagesFieldData {

    //Create Purchase Order fields
    STYLE(By.cssSelector("input[id$='StyleName::content']"), "StyleName", null),
    PROCUREMENT_BU(By.cssSelector("input[id$='ProcurementBu::content']"), "ProcurementBu", null),
    REQUISITIONING_BU(By.cssSelector("input[id$='RequisitioningBu::content']"), "ProcurementBu", null),
    SUPPLIER(By.cssSelector("input[id$='Supplier::content']"), "Supplier", null),
    SUPPLIER_SITE(By.cssSelector("input[id$='SupplierSite::content']"), "SuppliersSite", null),
    DEFAULT_SHIP_TO(By.cssSelector("input[id$='DefShipToLoc::content']"), "DefShipToLoc", null),


    // Create PO Line Fields
    LINE(By.cssSelector("input[id$='LineNum::content']"), "LineNum", null),
    TYPE(By.cssSelector("input[id$='LineType::content']"), "LineType", null),
    DESCRIPTION(By.cssSelector("input[id$='ItemDescription::content']"), "ItemDescription", null),
    CATEGORY_NAME(By.cssSelector("input[id$='Category::content']"), "Category", null),
    QUANTITY(By.cssSelector("input[id$='Quantity::content']"), "Quantity", null),
    UOM(By.cssSelector("input[id$='Uom::content']"), "Uom", null),
    PRICE(By.cssSelector("input[id$='UnitPrice::content']"), "UnitPrice", null),
    AMOUNT_PRICE(By.cssSelector("input[id$='AmountAsPrice::content']"), "AmountPrice", null),
    HAZARD_CLASS(By.cssSelector("input[id$='hazardClassId::content']"), "hazardClassId", null),
    PO_CHARGE_ACCOUNT(By.cssSelector("input[id$='LinePoChargeKffCS::content']"), "LinePoChargeKffCS",
            null),
    TRANSACTION_BUS_CATEGORY(By.cssSelector("input[id$='trxBizCategoryId::content']"), "trxBizCategoryId",
            null),
    PRODUCT_TYPE(By.cssSelector("select[id$='productTypeId::content']"), "productTypeId", null),
    INTENDED_USE(By.cssSelector("input[id$='lineIntendedUseId::content']"), "lineIntendedUseId", null),

    //Manage Purchase Orders fields
    ORDER(By.cssSelector("input[id$='value40::content']"), "value40", null);

    private By locator;
    private String idOrName;
    private String buttonTitle;

    /**
     * Sets various points of information for an area on any PO-related page.
     *
     * @param loc        the locator for the field
     * @param identifier identifying portion of the field's name or ID
     * @param button     title of any button associated with the field
     */
    OracleCloudPoPagesFieldData( final By loc, final String identifier, final String button )
    {
        this.locator = loc;
        this.idOrName = identifier;
        this.buttonTitle = button;
    }
}
