package com.vertex.quality.connectors.saptaxservice.tests.chain.flow;

import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCFATaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCity;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPCFATaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests for SAP Chain Flow Accelerator aka Augustus
 *
 * @author unaqvi
 *
 */
public class SAPCFAABPurchasesTransactionTests extends SAPCFATaxServiceBaseTest {
        /**
         * Tests a full rated domestic purchase with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticPurchaseOfGoodsStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("109800.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("19800.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests a reduced rated (4%) domestic purchase with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticPurchaseOfGoodsReducedRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFAICTransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("93600.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("3600.0"))
                        .body("taxLines[0].totalRate", equalTo("4.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("4.00"));
        }

        /**
         * Tests a full rated international purchase with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void InternationalPurchaseOfGoodsStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("109800.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("19800.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests a full rated international purchase with shipment from a different Country (Milano, Italy to
         * North Brabant, Netherlands)
         */
        @Test
        public void InternationalPurchaseOfGoodsStandardRateSoldVATTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("109800.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("19800.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests a tax exempt domestic purchase with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticPurchaseOfGoodsRateExemptTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a tax exempt international purchase with shipment from a different Country (North Brabant, Netherlands
         * to Canton of Zurich, Switzerland).
         */
        @Test
        public void InternationalPurchaseOfGoodsRateExemptTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.CANTON_OF_ZURICH.getAbbreviation(), SAPCity.ZURICH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a tax exempt international purchase with shipment from a different Country (North Brabant, Netherlands
         * to Canton of Zurich, Switzerland).
         */
        @Test
        public void InternationalPurchaseOfGoodsRateExemptCHTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        "EUR", SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), SAPCountry.SWITZERLAND.getCountryAbbreviation(),
                        SAPRegion.CANTON_OF_ZURICH.getAbbreviation(), SAPCity.ZURICH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.NETHERLANDS
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a tax exempt domestic purchase with shipment from a different State (Paris, Island of France
         * to Le Havre, Normand) in France.
         */
        @Test
        public void DomesticPurchaseOfGoodsRateExemptFRTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.FRANCE.getCountryAbbreviation(),
                        "EUR", SAPRegion.ISLAND_OF_FRANCE.getAbbreviation(), SAPCity.PARIS.getFullName(), SAPCountry.FRANCE.getCountryAbbreviation(),
                        SAPRegion.NORMANDY.getAbbreviation(), SAPCity.LE_HAVRE.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.FRANCE
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a full rated intra community purchase with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         */
        @Test
        public void IntraCommunityPurchaseOfGoodsTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultIntraCommunityCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("108900.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("18900.0"))
                        .body("taxLines[0].totalRate", equalTo("21.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.NETHERLANDS
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("21.00"));
        }

        /**
         * Tests a full rated Distance Selling B2C sale with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void PurchaseOfGoodsDistancePurchasingB2CTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultDistanceSellingCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("244.0"))
                        .body("subTotal", equalTo("200.0"))
                        .body("totalTax", equalTo("44.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests a full rated Import From San Marino.
         */
        @Test
        public void ImportFromSanMarinoTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportSMCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.SAN_MARINO.getCountryAbbreviation(), SAPCity.SAN_MARINO.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a full rated Import From Vatican.
         */
        @Test
        public void ImportFromVaticanTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportVCCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.VATICAN.getCountryAbbreviation(), SAPCity.VATICAN_CITY.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a full rated Import From China.
         */
        @Test
        public void ImportFromChinaTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportVCCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.CHINA.getCountryAbbreviation(), SAPCity.YUHANG_QU.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("90000.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("0.0"))
                        .body("taxLines[0].totalRate", equalTo("0.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("0.0"));
        }

        /**
         * Tests a full rated domestic acquisition of services from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticAcquisitionOfServicesStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultSupplyOfServicesCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("109800.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("19800.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests a full rated intra community acquisition of services from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         */
        @Test
        public void IntraCommunityAcquisitionOfServicesTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultIntraCommunityServicesCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("108900.0"))
                        .body("subTotal", equalTo("90000.0"))
                        .body("totalTax", equalTo("18900.0"))
                        .body("taxLines[0].totalRate", equalTo("21.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.NETHERLANDS
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("21.00"));

        }

        /**
         * Tests a full rated Service Distance Acquiring B2C purchase with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void AcquiringOfServicesDistanceBuyingB2CTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultServiceDistanceSellingCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), purchase, net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("244.0"))
                        .body("subTotal", equalTo("200.0"))
                        .body("totalTax", equalTo("44.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

        /**
         * Tests purchase direct payload for Italy
         */
        @Test
        public void DirectPayLoadPurchaseTest( )
        {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",SAPCountry.ITALY.getCountryAbbreviation(),"SAP_TAX_SERVICE_IT",
                        "OGR1C",purchase,net);

                Response response = new APIRequestBuilder.Builder(baseUrl, SAPCFATaxServiceEndpoints.CFA_QUOTE,
                        quoteInput).createRequestAndGetResponse();
                response
                        .then()
                        .statusCode(successResponseCode)
                        .and()
                        .body("total", equalTo("1220.0"))
                        .body("subTotal", equalTo("1000.0"))
                        .body("totalTax", equalTo("220.0"))
                        .body("taxLines[0].totalRate", equalTo("22.0000"))
                        .body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.ITALY
                                .getFullName()
                                .toUpperCase()))
                        .body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
                        .body("taxLines[0].taxValues[0].rate", equalTo("22.00"));
        }

}