package com.vertex.quality.connectors.kibo.enums;

/**
 * contains strings for Kibo product names for both back office and front store
 *
 * @author osabha
 */
public enum KiboProductNames {
    //back office
    PRODUCT_GETTA_PATENT_PUMP("Treven Getta Patent Pump"),
    PRODUCT_CAMMY_PUMP("Roja Cammy Pump"),
    PRODUCT_CHEETAH_PUMP("Maxis Brynn Cheetah Pump"),
    PRODUCT_IMAGEN_PUMP("Laurel Ann Imagen Pump"),
    PRODUCT_KAILAS_PATENT_PUMP("Piona Kailas Patent Pump"),
    PRODUCT_TRENCH_PUMP("Parsons Trench-89 Pump"),
    PRODUCT_MIRANDA_PUMP("Meredith Angela Miranda Pump"),
    PRODUCT_MIRA_PUMP("Reid Mira Pump"),
    PRODUCT_DRIVING_CAP("Leather Driving Cap"),
    PRODUCT_TEST_SHOES("TestShoes"),
    PRODUCT_T_SHIRT("Tshirt"),
    PRODUCT_BACKPACK("Osprey Exos 58 BCKPCK3"),

    //************** STORE FRONT PRODUCTS **************//
    SAFARI_ROAD("Safari Road"),
    MARMOT_THOR_3_PERSON_TENT("Marmot Thor 3 Person Tent"),
    SNOWSHOE_KIT("Bellweather Alpinist 25 Snowshoe Kit"),
    MEN_OUTWEST_TRAIL_PRO_40_PACK("Men’s OutWest Trail Pro 40 Pack"),
    NORTH_FACE_MICA_TENT("North Face Mica Tent"),
    ADJUSTABLE_SKI_POLES("Black Diamond Adjustable Ski Poles"),

    //**************product-names*****************//
    LEATHER_DAMASK_CLUTCH("acc-1003"),

    LEATHER_SLOUCH_BAG("bag-1001"),

    MEREDITH_ANGELA_MIRANDA_PUMP("1014"),

    LEATHER_DRIVING_CAP("acc-1001"),

    // Product class discounted product
    PRODUCT_VTXP_CODE("HatOnSale");

    public String value;

    KiboProductNames(String val) {
        this.value = val;
    }
}