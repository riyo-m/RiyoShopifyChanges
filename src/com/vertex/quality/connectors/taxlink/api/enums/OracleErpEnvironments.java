package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains all Oracle ERP environment names.
 *
 * @author Aman.Jain
 */
@Getter
public enum OracleErpEnvironments
{

    ECOGTEST(1, "https://ecog-test.fa.us2.oraclecloud.com/",
            "https://ecog-test.fa.us2.oraclecloud.com"),
    DEV1(2, "https://ecog-dev1.login.us2.oraclecloud.com/",
            "https://ecog-dev1.login.us2.oraclecloud.com"),
	DEV3(3, "https://ecog-dev3.login.us2.oraclecloud.com/",
		"https://ecog-dev1.login.us2.oraclecloud.com");



    public int id;
    public String environmentURL;
    public String oracle_baseURL;

    OracleErpEnvironments( int environmentId, String environmentURL, String baseURL )
    {
        this.id = environmentId;
        this.environmentURL = environmentURL;
        this.oracle_baseURL = baseURL;
    }
}
