package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains all existing Oracle credentials. New credential
 * names must be added here.
 *
 * @author msalomone
 */
@Getter
public enum OracleCredentials {

    TESTAUTO(1, "testautomation"),
    MCURRY(2, "mcurry"),
    MCURRY2(3, "mcurry"), // For EHYGTEST (maps to a different password)
    GSRINIVA(4, "gsriniva"); // For use with ecog-dev3 (Guru's credentials)

    private int id;
    private String credentialName;

    OracleCredentials( int credentialId, String credentialName )
    {
        this.id = credentialId;
        this.credentialName = credentialName;
    }
}
