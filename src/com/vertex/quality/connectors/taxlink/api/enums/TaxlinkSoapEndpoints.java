package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains valid API endpoints for Taxlink web services.
 *
 * @author msalomone
 */
@Getter
public enum TaxlinkSoapEndpoints {

    TAX_INTEGRATION(1, ":443/vertex-tl/services/TaxIntegrationService"),
	OERP_INTERGRATION(2,":443/fscmService/ErpIntegrationService");

    public int id;
    public String endpoint;

    TaxlinkSoapEndpoints( int endpointId, String urlEndpoint )
    {
        this.id = endpointId;
        this.endpoint = urlEndpoint;
    }
}
