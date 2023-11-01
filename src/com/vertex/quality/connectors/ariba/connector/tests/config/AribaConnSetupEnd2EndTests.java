package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.*;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.*;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnComponentTaxType;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnCustomFieldMapping;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnExternalTaxType;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnTaxMappingRule;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;
import java.util.List;

/**
 * This tests the whole
 *
 * @author ssalisbury
 */
public class AribaConnSetupEnd2EndTests extends AribaConnMenuBaseTest
{
	protected final String end2EndTenantVariantId = "AUTOsetupTenantVariantID";
	protected final String end2EndTenantDisplayName = "AUTOsetupTenantDisplayName";

	protected final String defaultVendorTaxCode = "I1";
	protected final String defaultConsumerTaxCode = "U1";

	/**
	 * JIRA ticket CARIBA-260
	 */
	@Test(groups = {"ariba_ui", "ariba_regression" })
	public void tenantFullSetupHappyTest( )
	{
		// @formatter:off
		List<AribaConnCustomFieldMapping> fieldMappings = List.of(
			new AribaConnCustomFieldMapping(AribaConnAribaFieldType.STRING, "testMapVendorCode", "VendorCode"),
			new AribaConnCustomFieldMapping(AribaConnAribaFieldType.STRING, "testMapVendorClass", "VendorClass"),
			new AribaConnCustomFieldMapping(AribaConnAribaFieldType.STRING, "testMapUsage", "Usage"),
			new AribaConnCustomFieldMapping(AribaConnAribaFieldType.STRING, "testMapUsageClass", "UsageClass"));
		//TODO test 'PurchaseClass' and 'PurchaseCode' VertexField values?


		List<AribaConnExternalTaxType> externalTaxTypes = List.of(
			new AribaConnExternalTaxType("GSTTax", true, false,"VS1"),
			new AribaConnExternalTaxType("HSTTax", true, false,"VS1"),
			new AribaConnExternalTaxType("PSTTax", false, false,"NVV"),
			new AribaConnExternalTaxType("QSTTax", true, false,"VS3"),
			new AribaConnExternalTaxType("VATRCTax", true, true,""),
			new AribaConnExternalTaxType("VATTax", true, false,"VST"),
			new AribaConnExternalTaxType("SalesTax", false, false,"NVV"),
			new AribaConnExternalTaxType("UseTax", false, true,""),
			new AribaConnExternalTaxType("SalesTaxState", false, false,"NVV"),
			new AribaConnExternalTaxType("SalesTaxDistrict", false, false,"NVV"),
			new AribaConnExternalTaxType("SalesTaxCounty", false, false,"NVV"),
			new AribaConnExternalTaxType("UseTaxCity", false, false,"NVV"));


		List<AribaConnComponentTaxType> componentTaxTypes = List.of(
			AribaConnComponentTaxType.builder().componentTaxType("GSTHST").externalTaxType("GSTTax")
								   .externalTaxType("HSTTax")
								   .build(),
			AribaConnComponentTaxType.builder().componentTaxType("PSTTax").externalTaxType("PSTTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("PSTCUT")
								   .externalTaxType("PSTTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("QSTTax").externalTaxType("QSTTax")
								   .build(),
			AribaConnComponentTaxType.builder().componentTaxType("VATRCTax")
								   .externalTaxType("VATRCTax")
								   .build(),
			AribaConnComponentTaxType.builder().componentTaxType("VATTax").externalTaxType("VATTax")
							.build(),
			AribaConnComponentTaxType.builder().componentTaxType("SalesTaxState").externalTaxType("SalesState")
								   .externalTaxType("SalesTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("SalesTaxCounty").externalTaxType("SalesTax")
								   .externalTaxType("SalesTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("SalesTaxCity").externalTaxType("SalesLocal")
								   .externalTaxType("SalesTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("SalesTaxDistrict").externalTaxType("SalesLocal")
								   .externalTaxType("SalesTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("UseTaxState")
								   .externalTaxType("UseTax").externalTaxType("UseTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("UseTaxCounty")
								   .externalTaxType("UseTax").externalTaxType("UseTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("UseTaxCity")
								   .externalTaxType("UseTax").externalTaxType("UseTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("UseTaxDistrict")
								   .externalTaxType("UseTax").externalTaxType("UseTax").build(),
			AribaConnComponentTaxType.builder().componentTaxType("SalesTax").externalTaxType("SalesTax")
								 .build(),
			AribaConnComponentTaxType.builder().componentTaxType("UseTax")
								   .externalTaxType("UseTax").build());

		List<AribaConnTaxMappingRule> taxMappingRules = List.of(
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("GSTHST").vertexType(AribaConnVertexTaxType.VAT).jurisdictionLevel(
				AribaConnVertexJurisdictionLevel.COUNTRY).vertexImpositionName("GST/HST").build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("PSTTax").vertexType(AribaConnVertexTaxType.SALES)
								 .vertexImpositionName("Provincial Sales Tax (PST)").build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("PSTCUT").vertexType(AribaConnVertexTaxType.CONSUMERS_USE)
								 .vertexImpositionName("Provincial Sales Tax (PST)").build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("QSTTax").vertexType(AribaConnVertexTaxType.VAT)
								 .vertexImpositionName("Quebec Sales Tax (VAT)").build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("VATRCTax").vertexType(AribaConnVertexTaxType.VAT)
								 .vatType(AribaConnVertexVATTaxType.INPUT_OUTPUT).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("VATTax").vertexType(AribaConnVertexTaxType.VAT)
								 .vatType(AribaConnVertexVATTaxType.INPUT_OUTPUT).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxState").vertexType(AribaConnVertexTaxType.SALES)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.STATE).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxState").vertexType(AribaConnVertexTaxType.SELLER_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.STATE).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCounty").vertexType(AribaConnVertexTaxType.SALES)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.COUNTY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCounty").vertexType(AribaConnVertexTaxType.SELLER_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.COUNTY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCounty").vertexType(AribaConnVertexTaxType.SALES)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.PARISH).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCounty").vertexType(AribaConnVertexTaxType.SELLER_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.PARISH).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCity").vertexType(AribaConnVertexTaxType.SALES)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.CITY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxCity").vertexType(AribaConnVertexTaxType.SELLER_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.CITY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxDistrict").vertexType(AribaConnVertexTaxType.SALES)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.TRANSIT_DISTRICT).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTaxDistrict").vertexType(AribaConnVertexTaxType.SELLER_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.TRANSIT_DISTRICT).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("UseTaxState").vertexType(AribaConnVertexTaxType.CONSUMERS_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.STATE).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("UseTaxCounty").vertexType(AribaConnVertexTaxType.CONSUMERS_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.COUNTY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("UseTaxCity").vertexType(AribaConnVertexTaxType.CONSUMERS_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.CITY).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("UseTaxDistrict").vertexType(AribaConnVertexTaxType.CONSUMERS_USE)
								 .jurisdictionLevel(AribaConnVertexJurisdictionLevel.TRANSIT_DISTRICT).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTax").vertexType(AribaConnVertexTaxType.SALES).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("SalesTax").vertexType(AribaConnVertexTaxType.SELLER_USE).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("UseTax").vertexType(AribaConnVertexTaxType.CONSUMERS_USE).build(),
			AribaConnTaxMappingRule.builder().aribaComponentTaxType("VATTax").vertexType(AribaConnVertexTaxType.VAT).build());
		// @formatter:on

		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		AribaConnManageTenantPage postCheckTenantPage = tenantPage;
		if ( tenantPage.tenantSelector.doesTenantExist(end2EndTenantVariantId) )
		{
			AribaConnMenuBasePage postEliminatePage = eliminateTenant(tenantPage, end2EndTenantVariantId);
			postCheckTenantPage = connUtil.openConfigMenu(postEliminatePage,
				AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		}
		postCheckTenantPage.createNewTenant();
		postCheckTenantPage.setVariantId(end2EndTenantVariantId);
		postCheckTenantPage.setDisplayName(end2EndTenantDisplayName);
		postCheckTenantPage.submitChanges();

		AribaConnMenuBasePage postWipePage = connUtil.wipeTenantDataRows(end2EndTenantVariantId, postCheckTenantPage);

		AribaConnConnectionPropertiesPage connectionPage = connUtil.openConfigMenu(postWipePage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);

		connectionPage.setTextField(AribaConnConnectionPropertiesTextField.VENDOR_TAX_CODE, defaultVendorTaxCode);
		connectionPage.setTextField(AribaConnConnectionPropertiesTextField.CONSUMER_TAX_CODE, defaultConsumerTaxCode);
		connectionPage.setXMLLogCheckbox(true);
		connectionPage.submitChanges();

		AribaConnCustomFieldMappingPage fieldMappingPage = connUtil.openConfigMenu(connectionPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);
		List<Integer> newFieldMappingIndices = fieldMappingPage.addFilledConfigRows(
			fieldMappings.toArray(new AribaConnCustomFieldMapping[0]));
		//TODO verify that those mappings have the expected values in the pojos that they were made from

		AribaConnExternalTaxTypesPage externalTaxTypesPage = connUtil.openConfigMenu(fieldMappingPage,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE);
		List<Integer> newExternalTaxTypeIndices = externalTaxTypesPage.addFilledExternalTaxTypes(
			externalTaxTypes.toArray(new AribaConnExternalTaxType[0]));
		//TODO verify that those external tax types have the expected values in the pojos that they were made from

		AribaConnComponentTaxTypesPage componentTaxTypesPage = connUtil.openConfigMenu(externalTaxTypesPage,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE);
		List<Integer> newComponentTaxTypeIndices = componentTaxTypesPage.addFilledComponentTaxTypes(
			componentTaxTypes.toArray(new AribaConnComponentTaxType[0]));
		//TODO verify that those component tax types have the expected values in the pojos that they were made from

		AribaConnTaxRulesPage taxRulesPage = connUtil.openConfigMenu(componentTaxTypesPage,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES);
		List<Integer> newTaxRuleIndices = taxRulesPage.addFilledTaxRules(
			taxMappingRules.toArray(new AribaConnTaxMappingRule[0]));
		//TODO verify that those tax rules have the expected values in the pojos that they were made from

	}
}
