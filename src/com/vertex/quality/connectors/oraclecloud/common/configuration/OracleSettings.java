package com.vertex.quality.connectors.oraclecloud.common.configuration;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.configuration.enums.*;
/**
 * All components needed to run Oracle tests. Each component stored as an enum.
 *
 * @author msalomone
 */
public class OracleSettings {
    private static OracleSettings instance = null;

    public String environment;
    public String endpoint;
    public String URL;
    public String username;
    public String password;
    public String instance_name;

    private OracleSettings() {

    }

    /**
     * Confines the class into a singleton, and returns the only
     * instance.
     * @return instance - the only instance of OracleSettings
     */
    public static OracleSettings getOracleSettingsInstance()
    {
        if (instance == null)
            instance = new OracleSettings();

        return instance;
    }

    /**
     * Assign default values to all Oracle settings.
     */
    public void initializeSettings() {
        VertexLogger.log("Initializing settings for test run.");
        environment = updateEnvironment(OracleEnvironments.ECOGTEST);
        endpoint = updateEndpoint(OracleEndpoints.PROCESSES);
        URL = environment+endpoint;
        username = updateUsername(OracleCredentials.MCURRY2);
        password = updatePassword(OracleCredentials.MCURRY2);
        instance_name = updateInstanceName(OracleEnvironments.ECOGTEST);
    }

    /**
     * Updates a setting based on enum value.
     */
    public void update(OracleEnvironments newEnvironment, OracleEndpoints newEndpoint, OracleCredentials newCredential)
    {
        VertexLogger.log("Updating automation settings.");
        environment = updateEnvironment(newEnvironment);
        endpoint = updateEndpoint(newEndpoint);
        URL = environment+endpoint;
        username = updateUsername(newCredential);
        password = updatePassword(newCredential);
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param newEndpoint Oracle endpoint represented as an Enum.
     */
    public void setEndpoint(OracleEndpoints newEndpoint)
    {
        VertexLogger.log("Updating Oracle Process Endpoint");
        endpoint = updateEndpoint(newEndpoint);
        URL = environment+endpoint;
    }

    /**
     * Updates a setting based on enum value.
     *
     * @param instance Oracle instance represented as an Enum.
     */
    public void setInstance(OracleEnvironments instance)
    {
        VertexLogger.log("Updating Oracle Process Endpoint");
        instance_name = updateInstanceName(instance);
    }

    /**
     * Returns the environment URL based on the descriptor provided.
     *
     * @param env Oracle environment represented as an Enum.
     * @return environment
     */
    private String updateEnvironment(OracleEnvironments env) {
        String environment = "";
        switch(env) {
            case DEV1:
                environment = "https://ecog-dev1.fa.us2.oraclecloud.com";
                break;
            case ECOGTEST:
                environment = "https://ecog-test.fa.us2.oraclecloud.com";
                break;
            case EHYGTEST:
                environment = "https://ehyg-test.fa.us6.oraclecloud.com";
                break;
            case DEV3:
                environment = "https://ecog-dev3.fa.us2.oraclecloud.com";
                break;
            default:
                environment = "https://ecog-dev1.fa.us2.oraclecloud.com";
        }
        return environment;
    }

    /**
     * Returns the rest endpoint based on the descriptor provided.
     *
     * @param ep Oracle api endpoint represented as an Enum.
     * @return endpoint
     */
    private String updateEndpoint(OracleEndpoints ep) {
        String endpoint = "";
        switch(ep) {
            case PROCESSES:
                endpoint = ":443/fscmRestApi/resources/11.13.18.05/erpintegrations";
                break;
            case GET_ALL_HOLDS:
                endpoint = ":443/fscmRestApi/resources/11.13.18.05/invoiceHolds";
                break;
            case UPDATE_HOLD:
                endpoint = ":443/fscmRestApi/resources/11.13.18.05/invoiceHolds/"; //Code must append holdId at the end
                break;
            default:
                endpoint = ":443/fscmRestApi/resources/11.13.18.05/erpintegrations";
        }
        return endpoint;
    }

    /**
     * Returns the username associated with the descriptor.
     *
     * @param user Pre-existing user credential in any Oracle machine represented as an Enum.
     * @return username
     */
    private String updateUsername(OracleCredentials user) {
        String username = "";
        switch(user) {
            case TESTAUTO:
                username = "testautomation";
                break;
            case MCURRY:
                username = "mcurry";
                break;
            case MCURRY2:
                username = "mcurry";
                break;
            case GSRINIVA:
                username = "gsriniva";
                break;
            default:
                username = "mcurry";
        }
        return username;
    }

    /**
     * Returns the password associated with the descriptor.
     *
     * @param user Pre-existing user credential in any Oracle machine represented as an Enum
     * @return password
     */
    private String updatePassword(OracleCredentials user) {
        String password = "";
        switch(user) {
            case TESTAUTO:
                password = "Autom@tion1_";
                break;
            case MCURRY:
                password = "W3lc0m3";
                break;
            case MCURRY2:
                password = "W3lc0m3!";
                break;
            case GSRINIVA:
                password = "Vertex123";
                break;
            default:
                password = "W3lc0m3";
        }
        return password;
    }

    /**
     * Returns a shortened name related to the instance in use.
     *
     * @param instance Current Oracle Fusion instance test settings are pointed toward.
     * @return instanceName Shortened name of fusion instance.
     */
    private String updateInstanceName(OracleEnvironments instance) {
        String instanceName = "";
        switch(instance) {
            case DEV1:
                instanceName = "ecog-dev1-us2";
                break;
            case ECOGTEST:
                instanceName = "ecog-test-us2";
                break;
            case DEV3:
                instanceName = "ecog-dev3-us2";
                break;
            default:
                instanceName = "ecog-dev1-us2";
        }
        return instanceName;
    }

}
