package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains all existing TaxLink credentials. New credential
 * names must be added here.
 *
 * @author msalomone
 */
@Getter
public enum TaxLinkCredentials {

    LOGINTEST(1, "tluser", "pwd"),
    ABHIJIT(2, "Abhijit", "Oracle1_"),
    KOP16_DEFAULT(3, "u-ecog-dev16", "3CzafTsx"),
	DEMO0063_DEFAULT(4,"u-ecog-dev31216","ckUQ0'2&C5"),
	DEMO0064_DEFAULT(5,"u-ecog-dev1111","1koDX0(vB."),
    DEMO0072_DEFAULT(6,"u-ecog-dev168","K5)NW&x5wR"),
    ONPREM(7,"u-ecog-dev21", "cxGHfA74");

    public int id;
    public String credentialName;
    public String password;

    TaxLinkCredentials( int credentialId, String credentialName, String password )
    {
        this.id = credentialId;
        this.credentialName = credentialName;
        this.password = password;
    }
}
