package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains existing Taxlink
 *
 * @author msalomone
 */
@Getter
public enum TaxLinkEndpoints {

    OERPCLOUDINSTANCES("vertex-tl-ui/OracleERPCloudInstances/");

    public String endpoint;

    TaxLinkEndpoints( String endpoint)
    {
        this.endpoint = endpoint;
    }
}
