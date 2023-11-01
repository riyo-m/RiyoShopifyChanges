package com.vertex.quality.connectors.taxlink.common.configuration;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.OracleEnvironments;
import com.vertex.quality.connectors.taxlink.api.enums.*;

/**
 * Initializes and updates static settings used for test runs.
 *
 * @author msalomone ,ajain
 */
public class TaxLinkSettings {
    private static TaxLinkSettings instance = null;

    public String taxlink_apiEndPointUrl;
    public String oracle_environment;
    public String base_URL;
    public String username;
    public String password;
    public String soap_endpointUrl;
	public String token;
	public String oerp_endpointUrl;
	public String oracle_baseURL;
	public String oracle_username;
	public String oracle_password;

    private TaxLinkSettings() {

    }

    /**
     * Confines the class into a singleton, and returns the only
     * instance.
     * @return instance - the only instance of TaxLinkSettings
     */
	public static TaxLinkSettings getTaxLinkSettingsInstance( )
	{
		if ( instance == null )
		{
			instance = new TaxLinkSettings();
		}

		return instance;
	}

	/**
	 * Assign default values to all TaxLink settings.
	 */
	public void initializeSettingsVertexTlUI( )
	{
		VertexLogger.log("Initializing settings for test run.");
		TaxLinkEnvironments tlEnv = TaxLinkEnvironments.DEMO0063_UI;
		OracleEnvironments oerpEnv = OracleEnvironments.DEV3;
		TaxLinkCredentials tlCred = TaxLinkCredentials.LOGINTEST;
		TaxlinkSoapEndpoints tlSoapEp = TaxlinkSoapEndpoints.TAX_INTEGRATION;

		taxlink_apiEndPointUrl = updateTaxLinkEnvironment(tlEnv);
		oracle_environment = updateOracleEnvironment(oerpEnv);
		base_URL = updateBaseURL(tlEnv);
		username = updateUsername(tlCred);
		password = updatePassword(tlCred);
		soap_endpointUrl = updateSoapEndpoint(tlSoapEp);
	}

	/**
	 * Assign default values to all TaxLink settings.
	 */
	public void initializeSettingsVertexTlAuth( )
	{
		VertexLogger.log("Initializing settings for test run.");
		TaxLinkEnvironments tlEnv = TaxLinkEnvironments.DEMO0063_AUTH;
		OracleEnvironments oerpEnv = OracleEnvironments.DEV3;
		TaxLinkCredentials tlCred = TaxLinkCredentials.LOGINTEST;
		TaxlinkSoapEndpoints tlSoapEp = TaxlinkSoapEndpoints.TAX_INTEGRATION;

		taxlink_apiEndPointUrl = updateTaxLinkEnvironment(tlEnv);
		oracle_environment = updateOracleEnvironment(oerpEnv);
		base_URL = updateBaseURL(tlEnv);
		username = updateUsername(tlCred);
		password = updatePassword(tlCred);
		soap_endpointUrl = updateSoapEndpoint(tlSoapEp);
	}
	/**
	 * Assign default values to all Calc API settings.
	 */
	public void initializeCalcAPISettings( )
	{
		VertexLogger.log("Initializing settings for test run.");
		TaxLinkEnvironments tlEnv = TaxLinkEnvironments.DEMO0030;
		OracleEnvironments oerpEnv = OracleEnvironments.DEV1;
		TaxLinkCredentials tlCred = TaxLinkCredentials.KOP16_DEFAULT;
		TaxlinkSoapEndpoints tlSoapEp = TaxlinkSoapEndpoints.TAX_INTEGRATION;

		taxlink_apiEndPointUrl = updateTaxLinkEnvironment(tlEnv);
		oracle_environment = updateOracleEnvironment(oerpEnv);
		base_URL = updateBaseURL(tlEnv);
		username = updateUsername(tlCred);
		password = updatePassword(tlCred);
		soap_endpointUrl = updateSoapEndpoint(tlSoapEp);
	}

	/**
	 * Assign default values to all Calc API settings for OERP
	 */
	public void initializeOracleERPSettings( )
	{
		VertexLogger.log("Initializing OERP settings for test run.");
		OracleErpEnvironments oerpEnv = OracleErpEnvironments.ECOGTEST;
		OracleErpCredentials oerpCred = OracleErpCredentials.MCURRY;
		OracleErpSoapEndpoints oerpSoapEp = OracleErpSoapEndpoints.OERP_INTERGRATION;

		oracle_environment = updateOracleErpEnvironment(oerpEnv);
		oracle_baseURL = updateOracleErpBaseURL(oerpEnv);
		oracle_username = updateOracleErpUsername(oerpCred);
		oracle_password = updateOracleErpPassword(oerpCred);
		oerp_endpointUrl = updateOracleSoapEndpoint(oerpSoapEp);
	}

	/**
	 * Updates a setting based on enum value.
	 *
	 * @param newEnvironment Taxlink environment represented as an Enum.
	 */
	public void setTaxLinkEnvironment( TaxLinkEnvironments newEnvironment )
	{
		VertexLogger.log("Updating Taxlink Environment.");
		taxlink_apiEndPointUrl = updateTaxLinkEnvironment(newEnvironment);
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param newEnvironment Oracle environment represented as an Enum.
     */
    public void setOracleEnvironment(OracleEnvironments newEnvironment)
    {
        VertexLogger.log("Updating Taxlink Environment.");
        oracle_environment = updateOracleEnvironment(newEnvironment);
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param newUsername Taxlink credential represented as an enum.
     */
    public void setUsername(TaxLinkCredentials newUsername)
    {
        VertexLogger.log("Updating Taxlink login username.");
        username = updateUsername(newUsername);
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param newPwdCredential Taxlink credential represented as an enum.
     */
    public void setPassword(TaxLinkCredentials newPwdCredential) {
        VertexLogger.log("Updating Taxlink login password.");
        password = updatePassword(newPwdCredential);
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param newSoapWebService SOAP ws credential represented as an enum.
     */
    public void setSoapEndpoint(TaxlinkSoapEndpoints newSoapWebService) {
        VertexLogger.log("Updating Taxlink's current SOAP endpoint target.");
        soap_endpointUrl = updateSoapEndpoint(newSoapWebService);
    }

	/**
	 * Updates a setting based on enum value.
	 *
	 * @param newSoapWebService SOAP ws credential represented as an enum.
	 */
	public void setOracleSoapEndpoint(OracleErpSoapEndpoints newSoapWebService) {
		VertexLogger.log("Updating Oracle's current SOAP endpoint target.");
		oerp_endpointUrl = updateOracleSoapEndpoint(newSoapWebService);
	}

    // Internal-use setting update methods below.

    /**
     * Returns the environment URL based on the descriptor provided.
     *
     * @param env Taxlink environment represented as an Enum.
     * @return environment
     */
    private String updateTaxLinkEnvironment(TaxLinkEnvironments env )
	{
		String environment = "";
		switch ( env )
		{
			case DEMO0063_UI:
			case DEMO0063_AUTH:
				environment = env.environmentURL;
				break;
		}
		return environment;
    }

    /**
     * Returns the environment URL based on the descriptor provided.
     *
     * @param env Oracle environment represented as an Enum.
     * @return environment
     */
    private String updateOracleEnvironment(OracleEnvironments env) {
        String environment = "";
        switch(env) {
            case DEV3:
                environment = "https://ecog-dev3.fa.us2.oraclecloud.com";
                break;
			case ECOGTEST:
				environment = "https://ecog-test.fa.us2.oraclecloud.com";
				break;
            default:
                environment = "https://ecog-dev1.fa.us2.oraclecloud.com";
        }
        return environment;
    }

    /**
     * Returns the URL stem of a Taxlink environment based on the descriptor provided.
     *
     * @param env Taxlink environment base URL represented as an Enum.
     * @return baseURL
     */
    private String updateBaseURL(TaxLinkEnvironments env) {
        String baseURL = "";
        baseURL = env.baseURL;
        return baseURL;
    }

    /**
     * Returns the username associated with the descriptor.
     *
     * @param user Pre-existing user credential for a Taxlink machine represented as an Enum.
     * @return username
     */
    private String updateUsername(TaxLinkCredentials user) {
        String username = "";
        username = user.credentialName;
        return username;
    }

    /**
     * Returns the password associated with the descriptor.
     *
     * @param user Pre-existing user credential for a Taxlink machine represented as an Enum
     * @return password
     */
    private String updatePassword(TaxLinkCredentials user) {
        String password = "";
        password = user.password;
        return password;
    }

    /**
     * Returns the complete endpoint URL including the instance stem.
     *
     * @param endpoint Taxlink SOAP web service endpoint represented as an Enum
     * @return completeEndpoint
     */
	private String updateSoapEndpoint( TaxlinkSoapEndpoints endpoint )	{
		String completeEndpoint = base_URL + endpoint.endpoint;
		return completeEndpoint;
	}

	/**
	 * Returns the complete OERP endpoint URL including the instance stem.
	 *
	 * @param endpoint Oracle SOAP web service endpoint represented as an Enum
	 * @return completeEndpoint
	 */
	private String updateOracleSoapEndpoint(OracleErpSoapEndpoints endpoint) {
		String completeEndpoint = oracle_baseURL + endpoint.oracle_endpoint;
		return completeEndpoint;
	}
	/**
	 * Returns the username associated with the descriptor.
	 *
	 * @param user Pre-existing user credential for oracle machine represented as an Enum.
	 * @return username
	 */
	private String updateOracleErpUsername(OracleErpCredentials user) {
		String username = "";
		username = user.oracle_username;
		return username;
	}

	/**
	 * Returns the password associated with the descriptor.
	 *
	 * @param user Pre-existing user credential for oracle machine represented as an Enum
	 * @return password
	 */
	private String updateOracleErpPassword(OracleErpCredentials user) {
		String password = "";
		password = user.oracle_password;
		return password;
	}

	/**
	 * Returns the URL stem of a Oracle environment based on the descriptor provided.
	 *
	 * @param env Oracle environment base URL represented as an Enum.
	 * @return baseURL
	 */
	private String updateOracleErpBaseURL( OracleErpEnvironments env) {
		String baseURL = "";
		baseURL = env.oracle_baseURL;
		return baseURL;
	}
	/**
	 * Returns the environment URL based on the descriptor provided.
	 *
	 * @param env Oracle environment represented as an Enum.
	 * @return environment
	 */
	private String updateOracleErpEnvironment(OracleErpEnvironments env) {
		String environment = "";
		switch(env) {
			case DEV3:
				environment = "https://ecog-dev3.fa.us2.oraclecloud.com";
				break;
			case ECOGTEST:
				environment = "https://ecog-test.fa.us2.oraclecloud.com";
				break;
			default:
				environment = "https://ecog-dev1.fa.us2.oraclecloud.com";
		}
		return environment;
	}

}
