package com.vertex.quality.connectors.ariba.connector.enums.forcomponents;

import com.vertex.quality.connectors.ariba.connector.components.AribaConnNavPanel;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.*;
import org.openqa.selenium.By;

/**
 * the html-locators of the buttons/tabs for the different pages
 * in this site for configuring this connector
 * these are found in a dropdown from the configuration menu button/tab
 * in the navigation menu along this configuration site's left side: {@link AribaConnNavPanel}
 *
 * @author ssalisbury
 */
public enum AribaConnNavConfigurationMenuOption
{
	CREATE_OR_MODIFY_TENANTS(By.id("tenant-maint-link"), AribaConnManageTenantPage.class),
	CONFIGURE_CONNECTION_PROPERTIES(By.id("connector-config-link"), AribaConnConnectionPropertiesPage.class),
	MODIFY_CUSTOM_FIELD_MAPPING(By.id("custom-field-map-link"), AribaConnCustomFieldMappingPage.class),
	MODIFY_ACCOUNT_FIELD_MAPPING(By.id("acct-field-map-link"), AribaConnAccountingFieldMappingPage.class),
	ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE(By.id("component-tax-types-link"), AribaConnComponentTaxTypesPage.class),
	ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE(By.id("summary-tax-type-maint-link"), AribaConnExternalTaxTypesPage.class),
	MODIFY_TAX_RULES(By.id("tax-rules-link"), AribaConnTaxRulesPage.class),
	VIEW_LOGGED_XML_MESSAGES(By.id("xml-log-viewer-link"), AribaConnViewLoggedXMLMessagesPage.class);

	public final By menuLoc;
	//the type of the page that this links to
	public final Class<? extends AribaConnMenuBasePage> returnPageType;

	AribaConnNavConfigurationMenuOption( final By loc, final Class<? extends AribaConnMenuBasePage> pageType )
	{
		this.menuLoc = loc;
		this.returnPageType = pageType;
	}
}
