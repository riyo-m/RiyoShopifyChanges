package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnConnectionPropertiesPage;
import lombok.Getter;
import org.openqa.selenium.By;

/**
 * the text input fields on the {@link AribaConnConnectionPropertiesPage}
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnConnectionPropertiesTextField
{
	CONNECTION_URL(By.id("url"), "qoinfg44::;42l84}{:920///"),
	VENDOR_TAX_CODE(By.id("vendor"), "ofxhioan4@^!&kj4 490tu"),
	CONSUMER_TAX_CODE(By.id("consumer"), "|:>:J($@Hufiabv34498537)");

	private By loc;
	/*
	 * NOTE- these test values are defined so as to be almost certainly different
	 * from what's normally in the database so that, after putting them into the
	 * fields, the test can be relatively certain that the fields contain these
	 * values because of the test's activity and not because the fields were already
	 * filled with that text from the database
	 */
	private String invalidText;

	AribaConnConnectionPropertiesTextField( final By loc, final String defaultText )
	{
		this.loc = loc;
		this.invalidText = defaultText;
	}
}
