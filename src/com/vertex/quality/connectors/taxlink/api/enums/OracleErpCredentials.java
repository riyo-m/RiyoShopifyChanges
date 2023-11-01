package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains all existing Oracle ERP credentials. 
 *
 * @author ajain
 */
@Getter
public enum OracleErpCredentials
{

    MCURRY(1, "mcurry", "W3lc0m3!");

    public int id;
    public String oracle_username;
    public String oracle_password;

    OracleErpCredentials( int credentialId, String credentialName, String password )
    {
        this.id = credentialId;
        this.oracle_username = credentialName;
        this.oracle_password = password;
    }
}
