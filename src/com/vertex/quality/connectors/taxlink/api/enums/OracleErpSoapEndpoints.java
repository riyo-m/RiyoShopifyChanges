package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains valid API endpoints for Oracle ERP web services.
 *
 * @author Aman.Jain
 */
@Getter
public enum OracleErpSoapEndpoints
{

	OERP_INTERGRATION(1,":443/fscmService/ErpIntegrationService");

    public int id;
    public String oracle_endpoint;

    OracleErpSoapEndpoints( int endpointId, String urlEndpoint )
    {
        this.id = endpointId;
        this.oracle_endpoint = urlEndpoint;
    }
}
