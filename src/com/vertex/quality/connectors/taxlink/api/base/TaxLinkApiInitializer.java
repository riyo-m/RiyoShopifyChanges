package com.vertex.quality.connectors.taxlink.api.base;

import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkDatabaseSettings;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;

/**
 * Handler for instantiating default settings for Taxlink test runs.
 *
 * @author msalomone, ajain
 */
public class TaxLinkApiInitializer
{
	public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();
	private static TaxLinkApiInitializer initialize;
	public static TaxLinkDatabaseSettings databaseSettings = TaxLinkDatabaseSettings.getDatabaseSettingsInstance();

	/**
	 * Provides initialization for all Taxlink settings used
	 * throughout test run in project 'vertex-tl-ui'. Call this method before all
	 * API test classes.
	 *
	 * @return initialize
	 */
	public static TaxLinkApiInitializer initializeVertextlUIApiTest( )
	{
		if ( initialize == null )
		{
			settings.initializeSettingsVertexTlUI();
		}
		return initialize;
	}

	/**
	 * Provides initialization for all Taxlink settings used
	 * throughout test run in project 'vertex-tl-auth'. Call this method before
	 * API test classes.
	 *
	 * @return initialize
	 */
	public static TaxLinkApiInitializer initializeVertextlAuthApiTest( )
	{
		if ( initialize == null )
		{
			settings.initializeSettingsVertexTlAuth();
		}
		return initialize;
	}

	/**
	 * Streamlined initializer for the calc tests.
	 *
	 * @author msalomone
	 */
	public static void initializeCalcApiTest( )
	{
		settings.initializeCalcAPISettings();
	}

	/**
	 * Streamlined initializer for the calc Batch tests.
	 *
	 * @author ajain
	 */
	public static void initializeOracleErpApiTest()
	{
		settings.initializeOracleERPSettings();
	}

	/**
	 * Streamlined initializer for the taxlink database.
	 *
	 * @author ajain
	 */
	public static void initializeDbSettingsTest()
	{
		databaseSettings.initializeDbSettings();
	}
}
