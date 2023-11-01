package com.vertex.quality.connectors.saptaxservice.tests.chain.flow;

import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.connectors.saptaxservice.enums.*;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPCFATaxServiceBaseTest;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests for SAP Chain Flow Accelerator aka Augustus
 *
 * @author unaqvi
 *
 */
public class SAPCFAABSalesTransactionTests extends SAPCFATaxServiceBaseTest {
        /**
         * Tests a full rated domestic sale with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticSaleOfGoodsStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), sale, net);

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
         * Tests a reduced rated (4%) domestic sale with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticSaleOfGoodsReducedRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFAICTransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), sale, net);

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
         * Tests a full rated international sale with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void InternationalSaleOfGoodsStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests a full rated internation sale with shipment from a different Country (Milano, Italy to North Brabant,
         * Netherlands).
         */
        @Test
        public void InternationalSaleOfGoodsStandardRateSoldVATTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests a rate exempt domestic sale with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticSaleOfGoodsRateExemptTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), sale, net);

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
         * Tests a tax exempt international sale with shipment from a different Country (North Brabant, Netherlands
         * to Canton of Zurich, Switzerland).
         */
        @Test
        public void InternationalSaleOfGoodsRateExemptTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.CANTON_OF_ZURICH.getAbbreviation(), SAPCity.ZURICH.getFullName(), sale, net);

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
         * Tests a tax exempt international sale with shipment from a different Country (North Brabant, Netherlands
         * to Canton of Zurich, Switzerland).
         */
        @Test
        public void InternationalSaleOfGoodsRateExemptCHTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultCFATransaction(SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        "EUR", SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), SAPCountry.SWITZERLAND.getCountryAbbreviation(),
                        SAPRegion.CANTON_OF_ZURICH.getAbbreviation(), SAPCity.ZURICH.getFullName(), sale, net);

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
         * Tests a tax exempt domestic sale with shipment from a different State (Paris, Island of France
         * to Le Havre, Normandy) in France.
         */
        @Test
        public void DomesticSaleOfGoodsRateExemptFRTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultBusinessPartnerCFATransaction(SAPCountry.FRANCE.getCountryAbbreviation(),
                        "EUR", SAPRegion.ISLAND_OF_FRANCE.getAbbreviation(), SAPCity.PARIS.getFullName(), SAPCountry.FRANCE.getCountryAbbreviation(),
                        SAPRegion.NORMANDY.getAbbreviation(), SAPCity.LE_HAVRE.getFullName(), sale, net);

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
         * Tests a full rated intra community sale with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void IntraCommunitySaleOfGoodsTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultIntraCommunityCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests a full rated Distance Selling B2C sale with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void SaleOfGoodsDistanceSellingB2CTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultDistanceSellingCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests a full rated Export to San Marino.
         */
        @Test
        public void ExporttoSanMarinoTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportSMCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.SAN_MARINO.getCountryAbbreviation(), SAPCity.SAN_MARINO.getFullName(), sale, net);

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
         * Tests a full rated Export to Vatican.
         */
        @Test
        public void ExportToVaticanTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportVCCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.VATICAN.getCountryAbbreviation(), SAPCity.VATICAN_CITY.getFullName(), sale, net);

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
         * Tests a full rated Export to China.
         */
        @Test
        public void ExportToChinaTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultExportVCCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.CHINA.getCountryAbbreviation(), SAPCity.YUHANG_QU.getFullName(), sale, net);

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
         * Tests a full rated domestic sale with shipment from a different State (Roma, RM to Milano, MI)
         * in Italy.
         */
        @Test
        public void DomesticSupplyOfServicesStandardRateTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultSupplyOfServicesCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.ITALY.getCountryAbbreviation(),
                        SAPRegion.MILAN.getAbbreviation(), SAPCity.MILANO.getFullName(), sale, net);

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
         * Tests a full rated intra community supply of services to a different Country (Roma, RM to 's-Hertogenbosch, NL)
         */
        @Test
        public void IntraCommunitySupplyOfServicesTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultIntraCommunityServicesCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests a full rated Service Distance Selling B2C sale with shipment from a different Country (Roma, RM to 's-Hertogenbosch, NL)
         * in Italy.
         */
        @Test
        public void SupplyOfServicesDistanceSellingB2CTest() {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultServiceDistanceSellingCFATransaction(SAPCountry.ITALY.getCountryAbbreviation(),
                        "EUR", SAPRegion.ROME.getAbbreviation(), SAPCity.ROMA.getFullName(), SAPCountry.NETHERLANDS.getCountryAbbreviation(),
                        SAPRegion.NORTH_BRABANT.getAbbreviation(), SAPCity.HERTOGENSBOSCH.getFullName(), sale, net);

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
         * Tests sales direct payload for Italy
         */
        @Test
        public void DirectPayLoadSalesTest( )
        {
                SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",SAPCountry.ITALY.getCountryAbbreviation(),"SAP_TAX_SERVICE_IT",
                        "OGR1C",sale,net);

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