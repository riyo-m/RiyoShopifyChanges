package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.interfaces.AcumaticaTableColumn;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Customization project page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaCustomizationProjectsPage extends AcumaticaPostSignOnPage
{
	//need to check displayed, even in tableDataBodyWrapper, because of invisible 'newRow' element at bottom of tbody
	protected final By tableCellCheckbox = By.className("control-icon");

	protected final String toggledCheckboxAttribute = "icon";
	protected final String toggledCheckboxString = "GridCheck";

	public AcumaticaCustomizationProjectsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * checks that at least one project has been published.
	 * that is, it checks that at least one check box is checked in the 'Published' column of the
	 * projects table
	 *
	 * @return whether at least one project has been published
	 */
	public boolean isSomeProjectPublished( )
	{
		boolean flag = false;

		int firstPublishedProjectIndex = getIndexOfFirstPublishedProject();
		if ( firstPublishedProjectIndex >= 0 )
		{
			flag = true;
		}

		return flag;
	}

	/***
	 * Gets the name of the first published project
	 * @return the name of the first published project
	 */
	public String getPublishedProjectName( )
	{
		String projectName = null;

		int publishedProjectIndex = getIndexOfFirstPublishedProject();
		if ( publishedProjectIndex >= 0 )
		{
			projectName = getProjectName(publishedProjectIndex);
		}

		return projectName;
	}

	/**
	 * gets the name of one of the projects
	 *
	 * @param projectIndex which project's name should be retrieved
	 *                     0-indexed
	 *
	 * @return the name of one of the projects
	 */
	public String getProjectName( final int projectIndex )
	{
		String projectName = null;

		int projectNameColumnIndex = getTableColumnIndex(TableColumn.PROJECT_NAME);

		if ( projectNameColumnIndex >= 0 )
		{
			//each row is for a different project
			List<WebElement> tableRows = getTableRows();
			WebElement projectRow = tableRows.get(projectIndex);

			List<WebElement> rowDataCells = wait.waitForAllElementsDisplayed(tableDataCell, projectRow);
			WebElement publishedCheckboxCell = rowDataCells.get(projectNameColumnIndex);
			if ( element.isElementDisplayed(publishedCheckboxCell) )
			{
				projectName = publishedCheckboxCell.getText();
			}
		}

		return projectName;
	}

	/**
	 * finds the index of the first published customization project, which should hopefully be the current version of
	 * the vertex connector
	 *
	 * @return the index of the first published customization project, which should hopefully be the current version of
	 * the vertex connector
	 * returns -1 if no customization project is published
	 */
	public int getIndexOfFirstPublishedProject( )
	{
		int publishedProjectIndex = -1;

		int publishedCheckboxColumnIndex = getTableColumnIndex(TableColumn.PUBLISHED);

		if ( publishedCheckboxColumnIndex >= 0 )
		{
			//this is to help debug a weird, very intermittent timing issue
			int numDisplayedPublishCheckboxes = 0;

			//each row is for a different project
			List<WebElement> tableRows = getTableRows();
			WebElement projectRow = null;
			for ( int i = 0 ; i < tableRows.size() ; i++ )
			{
				projectRow = tableRows.get(i);

				List<WebElement> rowDataCells = wait.waitForAllElementsDisplayed(tableDataCell, projectRow);
				WebElement publishedCheckboxCell = rowDataCells.get(publishedCheckboxColumnIndex);
				if ( element.isElementDisplayed(publishedCheckboxCell) )
				{
					//this is to help debug a weird, very intermittent timing issue
					numDisplayedPublishCheckboxes++;

					WebElement publishedCheckbox = wait.waitForElementDisplayed(tableCellCheckbox,
						publishedCheckboxCell);

					final String publishedCheckboxState = attribute.getElementAttribute(publishedCheckbox,
						toggledCheckboxAttribute);
					if ( toggledCheckboxString.equals(publishedCheckboxState) )
					{
						publishedProjectIndex = i;
						break;
					}
				}
			}

			//this is to help debug a weird, very intermittent timing issue
			if ( publishedProjectIndex < 0 )
			{
				VertexLogger.log(
					String.format("header label for 'published' checkbox column was %d", publishedCheckboxColumnIndex),
					VertexLogLevel.ERROR);
				VertexLogger.log(String.format("number of table rows: %d", tableRows.size()), VertexLogLevel.ERROR);
				VertexLogger.log(
					String.format("number of displayed publish checkboxes: %d", numDisplayedPublishCheckboxes),
					VertexLogLevel.ERROR);
			}
		}
		else //this is to help debug a weird, very intermittent timing issue
		{
			VertexLogger.log("can't find header label for 'published' checkbox column", VertexLogLevel.ERROR);
		}

		return publishedProjectIndex;
	}

	/**
	 * note that the published column is just text boxes & so doesn't have a cell editor overlay
	 */
	protected enum TableColumn implements AcumaticaTableColumn
	{
		PUBLISHED("Published", INVALID_END_OF_ID),
		PROJECT_NAME("Project Name", "_grid_lv0_edName");

		TableColumn( final String labelText, final String endOfIdAttribute )
		{
			this.label = labelText;
			String overlayLocatorString = String.format("[id$='%s']", endOfIdAttribute);
			this.editingOverlay = By.cssSelector(overlayLocatorString);
		}

		private String label;
		private By editingOverlay;

		public String getLabel( )
		{
			return label;
		}

		public By getEditingOverlay( )
		{
			return editingOverlay;
		}
	}
}
