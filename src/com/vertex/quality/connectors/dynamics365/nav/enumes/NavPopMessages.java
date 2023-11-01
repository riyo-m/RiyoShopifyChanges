package com.vertex.quality.connectors.dynamics365.nav.enumes;

public enum NavPopMessages {

    ADDRESS_CLEANSING_POPUP_MESSAGE("The address cannot be verified. Some of the address information may be missing or incorrect, or it does not exist in the USPS database. This may result in incorrect tax being applied to transactions using this address. Do you want to save the address as entered?");
    public String value;

    NavPopMessages(String alert) {
        this.value=alert;
    }
}
