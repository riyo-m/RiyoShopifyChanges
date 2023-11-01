package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains credentials for accessing O Series and Taxlink
 * databases. New credential names must be added here.
 *
 * Please visit the team's infrastructure page for more
 * environment information:
 * https://vertexinc.atlassian.net/wiki/spaces/CON/pages/827884323/Infrastructure
 *
 * @author msalomone
 */
@Getter
public enum DatabaseCredentials {

    VERTEX(1, "VERTEX"), // Has access to O Series dbs
    XXVERTEX(2, "XXVERTEX"), // Has access to Taxlink dbs
    XXVERTEX2(3, "XXVERTEX"); // Has access to Taxlink dbs, newer login using different pw.

    private int id;
    private String credentialName;

    DatabaseCredentials( int credentialId, String credentialName )
    {
        this.id = credentialId;
        this.credentialName = credentialName;
    }
}
