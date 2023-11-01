package com.vertex.quality.connectors.netsuite.common.pages.factories;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuitePageTitles;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

public class NetsuitePageFactory extends NetsuiteComponent {

    protected NetsuitePage defaultPage;
    protected WebDriver driver;

    public NetsuitePageFactory(final WebDriver driver, final NetsuitePage parent){
        super(driver, parent);
        this.driver = driver;
        defaultPage = parent;
    }

    public void createPage(){
        defaultPage = new NetsuiteHomepage(driver);
    }


    /**
     * Gets the page class from its title
     *
     * @return the page class
     */
    public Class<? extends NetsuitePage> getPageClass()
    {
		/*Example Input
			"General Preferences - NetSuite (Vertex SuiteTax QA (TSTDRV1847016))"

			"General Preferences - NetSuite (Vertex QA - Single Company (TSTDRV1118550))"

			"General Preferences - NetSuite (Vertex QA - OneWorld (TSTDRV1505402 ))"
		 */

        //SINGLE_COMPANY_GENERAL_PREFERENCES_PAGE	("General Preferences | (Vertex QA - Single Company (TSTDRV1118550))", NetsuiteSCGeneralPreferencesPage.class)
        //ONE_WORLD_GENERAL_PREFERENCES_PAGE		("General Preferences | (Vertex QA - OneWorld (TSTDRV1505402 ))", NetsuiteOWGeneralPreferencesPage.class)
        //Example of environment test: 				("General Preferences | (Vertex OW QA Bundle Upgrade TSTDRV2448223


        NetsuitePageTitles page = null;
        String pageTitle = defaultPage.getPageTitle();

        //tokenize input string...
        String[] titleTokens = pageTitle.split("- NetSuite", 0); // ["General Preferences", "(Vertex QA - OneWorld (TSTDRV1505402 ))"]
        //get page Type (ex. Sales Order, General Preferences, etc)
        String pageType = titleTokens[0] + "-";

        String netsuiteFlavor = NetsuiteBaseTest.netsuiteFlavor.replaceAll("NetSuite","");
        netsuiteFlavor = netsuiteFlavor.replaceAll("\\s+","");
        //get Netsuite Flavor
        String flavorToken = flavorFormatter(netsuiteFlavor); //Expand abbreviated environment names

        String thisFlavor = "";
        Set<String> flavors = NetsuitePageTitles.Default_Page.getFlavors();
        for (String flavor : flavors) {
            if(flavorToken.contains(flavor)){
                thisFlavor = flavor;
            }
        }

        for ( NetsuitePageTitles netsuitePageTitle : NetsuitePageTitles.values() )
        {
            if (netsuitePageTitle.getPageTitle().contains(pageType) && netsuitePageTitle.getPageTitle().contains(thisFlavor) )
            {
                return netsuitePageTitle.getPageClass();
            }
        }
        return null;
    }

    /**
     * Format Page "Flavors"
     * Convert Netsuite abbreviations to long form ( ow --> OneWorld )
     * @param flavorToken
     * @return The formatted String
     */
    protected String flavorFormatter(String flavorToken)
    {
        String longFormFlavor = flavorToken;
        Set<String> abbreviations = new HashSet<String>();

        abbreviations.add(" ow ");abbreviations.add(" sc ");abbreviations.add(" api ");
        abbreviations.add(" OW ");abbreviations.add(" SC ");abbreviations.add(" API ");

        for(String abbrev : abbreviations)
        {
            if(flavorToken.contains(abbrev))
            {
                longFormFlavor = abbrev.trim();
            }
        }

        switch (longFormFlavor) {
            case "ow": case "OW": longFormFlavor = "OneWorld";
                break;
            case "sc": case "SC": longFormFlavor = "Single Company";
                break;
            case "api": case "API": longFormFlavor = "SuiteTax";
                break;
            default: longFormFlavor = flavorToken;
                break;
        }

        return longFormFlavor;
    }
}
