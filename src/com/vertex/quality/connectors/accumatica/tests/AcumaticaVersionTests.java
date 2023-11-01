package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaCustomizationProjectsPage;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests related to the Vertex Connector Version on Acumatica.
 *
 * @author saidulu kodadala ssalisbury
 */
@Test(groups = { "version" })
public class AcumaticaVersionTests extends AcumaticaBaseTest
{
	//fixme this occasionally can't find any displayed table rows & so fails

	/**
	 * To verify the Vertex Connector Version
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author saidulu kodadala, ssalisbury
	 */
	@Ignore
	@Test(groups = { "smoke" })
	public void vertexConnectorVersionTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to customization Projects page
		AcumaticaCustomizationProjectsPage customizationProjectsPage = homePage.openMenuPage(
			AcumaticaGlobalSubMenuOption.CUSTOMIZATION, AcumaticaLeftPanelLink.CUSTOMIZATION_PROJECTS);
		assertTrue(isConnectorProjectPublished(customizationProjectsPage));
	}

	/**
	 * checks that (a version of) the Vertex Connector 'project' is published and has a name,
	 * then logs what that published project's name is
	 *
	 * @param customizationProjectsPage the page which lists projects which modify the Acumatica
	 *                                  experience, like Vertex's tax calculation connector
	 *
	 * @return whether a project (hopefully a version of the Vertex Connector) is published and has
	 * a name
	 */
	protected boolean isConnectorProjectPublished( final AcumaticaCustomizationProjectsPage customizationProjectsPage )
	{
		boolean isPublished = false;

		if ( !customizationProjectsPage.isSomeProjectPublished() )
		{
			VertexLogger.log("Connector Project isn't published", VertexLogLevel.ERROR);
		}
		else
		{
			String projectName = customizationProjectsPage.getPublishedProjectName();
			if ( projectName == null )
			{
				VertexLogger.log("The published (connector?) project has no name", VertexLogLevel.ERROR);
			}
			else
			{
				String projectNameMessage = String.format("Project Name is : %s", projectName);
				VertexLogger.log(projectNameMessage);
				isPublished = true;
			}
		}

		return isPublished;
	}
}
