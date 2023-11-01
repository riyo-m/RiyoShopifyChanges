package com.vertex.quality.connectors.bigcommerce.ui.enums;

import com.vertex.quality.connectors.bigcommerce.ui.components.BigCommerceAdminLeftNavBar;
import lombok.Getter;
import org.openqa.selenium.By;

/**
 * the categories of navigation options in the {@link BigCommerceAdminLeftNavBar}
 *
 * @author ssalisbury
 */
@Getter
public enum BigCommerceAdminNavCategory
{
	CUSTOMERS(By.id("nav-customers"), By.id("nav-group-customers")),
	APPS(By.id("nav-apps"), By.id("nav-group-apps"));

	private final By buttonLoc;
	private final By optionsContainerLoc;

	BigCommerceAdminNavCategory( final By categoryId, final By categoryOptionsContainerId )
	{
		this.buttonLoc = categoryId;
		this.optionsContainerLoc = categoryOptionsContainerId;
	}
}
