package com.vertex.quality.connectors.oraclecloud.keywords.api;

import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleCredentials;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleEndpoints;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleEnvironments;

/**
 * Keywords for handling Oracle-related framework settings.
 *
 */
public class SettingsKeywords {

    private OracleSettings settings = OracleSettings.getOracleSettingsInstance();

    /**
     * Update all framework settings.
     */
    public void updateSettings(OracleEnvironments environment, OracleEndpoints endpoint, OracleCredentials credential) {
        settings.update(environment, endpoint, credential);
    }
}
