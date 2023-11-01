package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.common.configuration.OracleSettings;
import com.vertex.quality.connectors.oraclecloud.common.configuration.DatabaseSettings;

public class Initializer {
    private static OracleSettings settings = OracleSettings.getOracleSettingsInstance();
    private static DatabaseSettings dbSettings = DatabaseSettings.getDatabaseSettingsInstance();

    /**
     * Provides initialization for all Oracle settings used
     * throughout test run. Call this method before all
     * test classes.
     */
    public static void initializeApiTest() {
        settings.initializeSettings();
    }

    /** Provides initialization for all Oracle Cloud database
     * related settings. Call this method at the beginning of
     * tests that target a database.
     */
    public static void initializeDbSettings() { dbSettings.initializeDbSettings(); }
}
