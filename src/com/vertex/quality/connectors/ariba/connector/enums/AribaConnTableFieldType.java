package com.vertex.quality.connectors.ariba.connector.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * the various types of fields in the tables in configuration menus on the ariba connector ui
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnTableFieldType
{
	DROPDOWN(By.tagName("select")),
	CHECKBOX(By.tagName("input")),
	TEXT_FIELD(By.tagName("input")),
	DIALOG_LINK(By.tagName("a"));

	private final By loc;

	AribaConnTableFieldType( final By fieldInputElementLoc ) {this.loc = fieldInputElementLoc;}
}
