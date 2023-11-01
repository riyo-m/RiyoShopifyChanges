package com.vertex.quality.connectors.taxlink.common.webservices;

import com.google.common.collect.Maps;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.api.base.TaxLinkApiBaseTest;
import com.vertex.quality.connectors.taxlink.common.TaxLinkReadExcel;
import com.vertex.quality.connectors.taxlink.common.TaxLinkWriteExcel;
import com.vertex.quality.connectors.taxlink.common.configuration.TaxLinkSettings;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * This page contains all the methods for API tests of System Profile Options
 *
 * @author Shilpi.Verma
 */

public class SystemProfileOptionsAPI extends TaxLinkApiBaseTest {
    public static TaxLinkSettings settings = TaxLinkSettings.getTaxLinkSettingsInstance();

    protected String url;

    public SystemProfileOptionsAPI() {
        requestEndpoint = tlEndpoint.endpoint;
    }

    /**
     * Sends GET request for fetching details based on path and query parameters and end points
     *
     * @param pathParam1
     * @param pathParam2
     * @param endPointUrl
     */
    public void sendGetRequest_SystemProfileList(String pathParam1, String pathParam2, String endPointUrl) throws Exception {

        restResponse = given()
                .baseUri(endPointUrl)
                .queryParam("accessType", "SYSTEM")
                .log()
                .headers()
                .auth()
                .preemptive()
                .basic(settings.username, settings.password)
                .contentType(ContentType.JSON)
                .when()
                .get(pathParam1)
                .then()
                .log()
                .status()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        List<Object> profOptIdList = restResponse
                .getBody()
                .jsonPath()
                .getList("profileOptionId");

        List<Object> profOptNameList = restResponse
                .getBody()
                .jsonPath()
                .getList("profileOptionName");

        Iterator<Object> profOptIdListItr = profOptIdList.iterator();
        Iterator<Object> prOptNameItr = profOptNameList.iterator();
        Map<Object, Object> nameMap = new TreeMap<>();
        while (profOptIdListItr.hasNext() && prOptNameItr.hasNext()) {
            nameMap.put(profOptIdListItr.next(), prOptNameItr.next());
        }

        Map<Object, List<Object>> sysProOptMap = new TreeMap<>();
        Set<Object> emptyJSON = new HashSet<>();
        for (final Object s : nameMap.values()) {
            restResponse = given()
                    .baseUri(endPointUrl)
                    .log()
                    .headers()
                    .auth()
                    .preemptive()
                    .basic(settings.username, settings.password)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(pathParam2 + "/" + s)
                    .then()
                    .log()
                    .status()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            try {
                Object prOptName = restResponse
                        .getBody()
                        .jsonPath()
                        .getList("profileOptions.profileOptionName")
                        .get(0);

                Object userPrOptName = restResponse
                        .getBody()
                        .jsonPath()
                        .getList("profileOptions.userProfileOptionName")
                        .get(0);

                Object limitLevelId = restResponse
                        .getBody()
                        .jsonPath()
                        .getList("profileOptions.limitLevelId")
                        .get(0).toString();

                Object prOptValue = restResponse
                        .getBody()
                        .jsonPath()
                        .getList("profileOptionValue")
                        .get(0);

                sysProOptMap.put(prOptName,
                        Arrays.asList(userPrOptName, limitLevelId, prOptValue));
            } catch (Exception e) {
                VertexLogger.log(s + " : has empty JSON response");
                emptyJSON.add(s);
            }
        }
        
        TaxLinkWriteExcel xl_write = new TaxLinkWriteExcel();
        xl_write.export("select po.PROFILE_OPTION_ID,po.PROFILE_OPTION_NAME, po.USER_PROFILE_OPTION_NAME, po.LIMIT_LEVEL_ID, pov.PROFILE_OPTION_VALUE \n" +
                "from vtx_profile_options_t po, vtx_profile_option_values_t pov\n" +
                "where po.profile_option_id = pov.profile_option_id\n" +
                "and po.access_type = 'SYSTEM'\n" +
                "and pov.LEVEL_ID = 1000 ", "Sheet1");

        TaxLinkReadExcel xl = new TaxLinkReadExcel();
        TreeMap<Object, List<Object>> excelMap = xl.getExcelData("Sheet1");

        VertexLogger.log("System Profile Options missing in CSV file but present in JSON response: " + Maps
                .difference(excelMap, sysProOptMap)
                .entriesOnlyOnRight()
                .keySet());

        Set<Object> set = Maps
                .difference(excelMap, sysProOptMap)
                .entriesOnlyOnRight()
                .keySet();

        VertexLogger.log(set.size() + " entries will now be removed from System Profile map.");

        sysProOptMap
                .keySet()
                .removeAll(set);

        VertexLogger.log("Extra system profile options which are not present in CSV file are now removed.");

        Set<Object> excelSet = Maps
                .difference(excelMap, sysProOptMap)
                .entriesOnlyOnLeft()
                .keySet();

        if (excelSet.size() > 0) {
            VertexLogger.log("System Profile Options missing in JSON response but present in CSV file: " + excelSet);
        } else {
            VertexLogger.log("No System Profile Options are missing from JSON response.");
        }

        assertEquals(excelMap, sysProOptMap);
    }
}
