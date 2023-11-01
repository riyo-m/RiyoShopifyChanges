package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains all O Series machines' databases
 * connection URLs.
 *
 * Please visit the team's infrastructure page for more
 * environment information:
 * https://vertexinc.atlassian.net/wiki/spaces/CON/pages/827884323/Infrastructure
 *
 * @author msalomone
 */
@Getter
public enum DatabaseEnvironments {

    KOP16(1, "xxvertex"),
    KOP17(2, "KOP17"),
    BERAS0411(3, "BERAS0411"),
	PHLDAPS0110(4,"PHLDAPS0110");

    private int id;
    private String environmentName;

    DatabaseEnvironments( int environmentId, String environmentName )
    {
        this.id = environmentId;
        this.environmentName = environmentName;
    }
}
