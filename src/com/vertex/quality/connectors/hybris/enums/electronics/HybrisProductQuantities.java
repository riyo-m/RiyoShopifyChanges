package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Test data file or Enums for Hybris Product's different quantities
 */
public enum HybrisProductQuantities {

    QUANTITY_ONE("1"),
    QUANTITIES_FIVE("5"),
    QUANTITIES_TEN("10");

    String quantity;

    /**
     * Parameterized constructor of the enum file that sets the value of quantity.
     *
     * @param quantity value of quantity
     */
    HybrisProductQuantities(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method of String variable that is used to get the value of quantity.
     *
     * @return quantity's value
     */
    public String getQuantity() {
        return quantity;
    }
}
