package com.vertex.quality.connectors.ariba.portal.enums;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.config.AribaPortalAdministrationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalToDoPage;
import lombok.Getter;

/**
 * represents the manage list pages
 * contains page name and class
 *
 * @author osabha
 */
@Getter
public enum AribaPortalManageListPage
{
	CORE_ADMINISTRATION("Core Administration", AribaPortalAdministrationPage.class),
	MY_TODO("My To Do", AribaPortalToDoPage.class);

	private String pageName;
	private Class<? extends AribaPortalPostLoginBasePage> pageClass;

	AribaPortalManageListPage( final String text, final Class<? extends AribaPortalPostLoginBasePage> nextPage )
	{
		this.pageName = text;
		this.pageClass = nextPage;
	}
}
