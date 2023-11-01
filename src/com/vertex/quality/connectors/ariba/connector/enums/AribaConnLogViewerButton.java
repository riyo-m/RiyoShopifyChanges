package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnViewLoggedXMLMessagesPage;
import lombok.Getter;
import org.openqa.selenium.By;

/**
 * the buttons on the {@link AribaConnViewLoggedXMLMessagesPage}
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnLogViewerButton
{
	SEARCH(By.id("searchBtn")),
	FIRST_LOG(By.id("firstBtn")),
	PREVIOUS_LOG(By.id("prevBtn")),
	NEXT_LOG(By.id("nextBtn")),
	LAST_LOG(By.id("lastBtn"));

	private By loc;

	AribaConnLogViewerButton( final By buttonLoc )
	{
		this.loc = buttonLoc;
	}
}
