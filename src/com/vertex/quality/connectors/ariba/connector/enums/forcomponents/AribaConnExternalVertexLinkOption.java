package com.vertex.quality.connectors.ariba.connector.enums.forcomponents;

import com.vertex.quality.connectors.ariba.connector.components.AribaConnFooterPane;
import org.openqa.selenium.By;

/**
 * this configuration site's external links to different webpages,
 * mostly legal disclaimers, on Vertex's main public website Note- these links
 * are currently all located inside the footer of this site: {@link AribaConnFooterPane}
 *
 * @author ssalisbury
 */
public enum AribaConnExternalVertexLinkOption
{
	// @formatter:off		locator							page title						opens new tab		default expected URL
	VERTEX_HOME_PAGE(	 By.id("home-page-link"),		"Homepage | Vertex, Inc.",				true,	"https://www.vertexinc.com/"),
	TERMS_OF_USE(		 By.id("terms-of-use-link"),	"Terms of Use | Vertex, Inc.",			false,	"https://www.vertexinc.com/terms-of-use"),
	PRIVACY_POLICY(		 By.id("privacy-policy-link"),	"Vertex Privacy Policy | Vertex, Inc.",	false,	"https://www.vertexinc.com/vertex-privacy-policy");
	// @formatter:on

	public final By linkLoc;
	public final String linkedPageTitle;
	public final boolean opensInNewTab;
	public final String expectedAddress;

	AribaConnExternalVertexLinkOption( final By loc, final String pageTitle, final boolean opensNew,
		final String address )
	{
		this.linkLoc = loc;
		this.linkedPageTitle = pageTitle;
		this.opensInNewTab = opensNew;
		this.expectedAddress = address;
	}
}
