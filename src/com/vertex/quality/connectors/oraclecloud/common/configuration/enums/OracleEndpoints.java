package com.vertex.quality.connectors.oraclecloud.common.configuration.enums;

import lombok.Getter;

/**
 * Contains all Oracle api endpoints.
 *
 * @author msalomone
 */
@Getter
public enum OracleEndpoints {

    PROCESSES(1, ":443/fscmRestApi/resources/11.13.18.05/erpintegrations"),
    REPORTING(2, ":443/xmlpserver/services/ExternalReportWSSService"),
    GET_ALL_HOLDS(3, ":443/fscmRestApi/resources/11.13.18.05/invoiceHolds"),
    UPDATE_HOLD(4, ":443/fscmRestApi/resources/11.13.18.05/invoiceHolds/");

    private int id;
    private String endpointName;

    OracleEndpoints( final int endpointId, final String endpointName )
    {
        this.id = endpointId;
        this.endpointName = endpointName;
    }
}