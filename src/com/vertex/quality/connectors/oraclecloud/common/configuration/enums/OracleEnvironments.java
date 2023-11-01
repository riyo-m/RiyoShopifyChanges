package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains all Oracle environment names.
 *
 * @author msalomone
 */
@Getter
public enum OracleEnvironments {

    DEV1(1, "DEV"),
    ECOGTEST(2, "QA1"),
    EHYGTEST(3, "QA2"),
    DEV3(4, "DEV3");

    private int id;
    private String environmentName;

    OracleEnvironments( int environmentId, String environmentName )
    {
        this.id = environmentId;
        this.environmentName = environmentName;
    }
}
