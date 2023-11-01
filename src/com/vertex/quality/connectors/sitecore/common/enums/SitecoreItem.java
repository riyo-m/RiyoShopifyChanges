package com.vertex.quality.connectors.sitecore.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

/**
 * represents available items in sitecore
 *
 * @author Shiva Mothkula, Daniel Bondi
 */ public enum SitecoreItem
{
	BlackBerryPhone("BlackBerry Bold 9000 Phone, Black (AT&T)", "245.00"),
	CanonF100Camcorder("Canon VIXIA HF100 Camcorder", "530.00"),
	DiamondPaveEarrings("Diamond Pave Earrings", "569.00"),
	DiamondTennisBracelet("Diamond Tennis Bracelet", "360.00"),
	BlackWhiteDiamondHeart("Black & White Diamond Heart", "130.00"),
	EatingWellInSeason("EatingWell in Season", "51.00"),
	BestGrillingRecipes("Best Grilling Recipes", "245.00"),
	BestSkilletRecipes("The Best Skillet Recipes", "24.00"),
	GenuineLeatherHandbagCellPhoneHolderMoneyPockets("Genuine Leather Handbag with Cell Phone Holder & Many Pockets",
		"35.00"),
	AdobePhotoshopElements7("Adobe Photoshop Elements 7", "75.00"),
	CompaqPentium4Desktop("Compaq Presario SR1519X Pentium 4 Desktop PC with CDRW", "500.00");

	final private String name;
	final private String price;
}
