package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains O Series and Taxlink database names.
 * New database names must be added here.
 *
 * Please visit the team's infrastructure page for more
 * environment information:
 * https://vertexinc.atlassian.net/wiki/spaces/CON/pages/827884323/Infrastructure
 *
 * @author msalomone
 */
@Getter
public enum DatabaseNames {

    VTX9_JRNL(1, "VTX9_JRNL"), // An O Series db
    XXVERTEX(2, "XXVERTEX"); // A Taxlink db

    private int id;
    private String databaseName;

    DatabaseNames( int databaseId, String databaseName )
    {
        this.id = databaseId;
        this.databaseName = databaseName;
    }
}
