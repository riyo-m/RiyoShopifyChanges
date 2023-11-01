package com.vertex.quality.connectors.ariba.connector.enums.forcomponents;

import com.vertex.quality.connectors.ariba.connector.components.AribaConnNavPanel;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSystemStatusPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnVertexServicesPage;
import org.openqa.selenium.By;

/**
 * the buttons/tabs in the navigation menu along this site's left side: {@link AribaConnNavPanel}
 *
 * @author ssalisbury
 */
public enum AribaConnNavOption
{
	VERTEX_SERVICES(By.id("vertex-services-link"),
		"\n                            Vertex Services\n                        ", AribaConnVertexServicesPage.class),
	SYSTEM_STATUS(By.id("system-status-link"), "\n                            System status\n                        ",
		AribaConnSystemStatusPage.class),
	CONFIGURATION_MENU(By.id("config-menu-button"),
		"\n                            Configuration Menu\n                            \n                        ",
		null),
	COLLAPSE_MENU(By.id("collapse-button"), " Collapse Menu", null);

	public final By navButtonLoc;
	//TODO figure out why navButtonText used to exist & either restore that functionality or delete this field
	public final String navButtonText;
	//the type of the page that this links to (null means that the navigation option is just a button,
	// e.g. a button for expanding/collapsing a dropdown or the whole nav panel)
	public final Class<? extends AribaConnBasePage> returnPageType;

	AribaConnNavOption( final By loc, final String text, final Class<? extends AribaConnBasePage> pageType )
	{
		this.navButtonLoc = loc;
		this.navButtonText = text;
		this.returnPageType = pageType;
	}
}
