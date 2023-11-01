package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnLogViewerButton;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the XML Logs page in the Ariba Connector's UI
 *
 * @author ssalisbury
 */
public class AribaConnViewLoggedXMLMessagesPage extends AribaConnMenuBasePage
{
	protected final By startDateTimeField = By.id("startDate");
	protected final By endDateTimeField = By.id("endDate");
	//the date field is split into several subfields. you can switch between subfields using the left or right arrow keys.
	// when this is entered into the date field, it moves the cursor to the 1st/leftmost subfield
	protected final String resetDateFieldPosition = Keys.chord(Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT,
		Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT);

	protected final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
	protected final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd");
	protected final DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
	protected final DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("hh");
	protected final DateTimeFormatter minuteFormat = DateTimeFormatter.ofPattern("mm");
	protected final DateTimeFormatter amPmFormat = DateTimeFormatter.ofPattern("a");

	protected final By documentNameField = By.id("docName");

	protected final By currLogIndexDisplay = By.id("curRec");
	protected final By totalLogCountDisplay = By.id("totalRec");

	//'phase'- e.g. Ariba Request, O-Series Request, O-Series Response, Ariba Response :
	// which part of the process of Ariba getting information from O-Series is this from
	protected final By currLogPhaseDisplay = By.id("phase");
	protected final By currLogDateDisplay = By.id("logTime");

	protected final By logContentsDisplay = By.id("logDisplay");

	public AribaConnViewLoggedXMLMessagesPage( WebDriver driver )
	{
		super(driver, "XML Log Viewer");
	}

	/**
	 * checks whether the given button on the xml log viewer page can be seen and clicked on
	 *
	 * @param buttonType which button on the xml log viewer page should be examined
	 *
	 * @return whether the given button on the xml log viewer page can be seen and clicked on
	 *
	 * @author ssalisbury
	 */
	public boolean isLogViewerButtonEnabled( final AribaConnLogViewerButton buttonType )
	{
		boolean isButtonEnabled = element.isElementEnabled(buttonType.getLoc());
		return isButtonEnabled;
	}

	/**
	 * clicks the given button on the xml log viewer page
	 *
	 * @param buttonType which button on the xml log viewer page should be clicked
	 *
	 * @author ssalisbury
	 */
	public void clickLogViewerButton( final AribaConnLogViewerButton buttonType )
	{
		WebElement buttonElem = wait.waitForElementEnabled(buttonType.getLoc());
		click.clickElement(buttonElem);
	}

	/**
	 * checks whether the document name field can be seen and interacted with
	 *
	 * @return whether the document name field can be seen and interacted with
	 *
	 * @author ssalisbury
	 */
	public boolean isDocumentNameFieldEnabled( )
	{
		boolean isNameFieldEnabled = element.isElementEnabled(documentNameField);
		return isNameFieldEnabled;
	}

	/**
	 * sets the string which the document names of the logs in the search result should contain
	 *
	 * @param documentName what document name should be searched for
	 *
	 * @author ssalisbury
	 */
	public void setDocumentName( final String documentName )
	{
		text.enterText(documentNameField, documentName);
	}

	/**
	 * checks whether the start date field can be seen and interacted with
	 *
	 * @return whether the start date field can be seen and interacted with
	 *
	 * @author ssalisbury
	 */
	public boolean isStartDateFieldEnabled( )
	{
		boolean isStartDateFieldEnabled = element.isElementEnabled(startDateTimeField);
		return isStartDateFieldEnabled;
	}

	/**
	 * checks whether the end date field can be seen and interacted with
	 *
	 * @return whether the end date field can be seen and interacted with
	 *
	 * @author ssalisbury
	 */
	public boolean isEndDateFieldEnabled( )
	{
		boolean isEndDateFieldEnabled = element.isElementEnabled(endDateTimeField);
		return isEndDateFieldEnabled;
	}

	/**
	 * Alters {@link #setDateField(By, OffsetDateTime)} to fill the Start date field specifically
	 */
	public void setStartDate( final OffsetDateTime dateTimeVal )
	{
		setDateField(startDateTimeField, dateTimeVal);
	}

	/**
	 * Alters {@link #setDateField(By, OffsetDateTime)} to fill the End date field specifically
	 */
	public void setEndDate( final OffsetDateTime dateTimeVal )
	{
		setDateField(endDateTimeField, dateTimeVal);
	}

	/**
	 * changes one of the date-time fields that constrain the search for logs
	 *
	 * @param dateField   a description of the field element which will be changed
	 * @param dateTimeVal the date-time which should be written to that field
	 *
	 * @author ssalisbury
	 */
	protected void setDateField( final By dateField, final OffsetDateTime dateTimeVal )
	{
		WebElement dateFieldElem = wait.waitForElementEnabled(dateField);
		text.enterText(dateFieldElem, resetDateFieldPosition, false);

		final String monthVal = dateTimeVal.format(monthFormat);
		final String dayVal = dateTimeVal.format(dayFormat);
		final String yearVal = dateTimeVal.format(yearFormat);
		final String hourVal = dateTimeVal.format(hourFormat);
		final String minuteVal = dateTimeVal.format(minuteFormat);
		final String amPmVal = dateTimeVal.format(amPmFormat);

		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, monthVal, false);
		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, dayVal, false);
		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, yearVal, false);

		//for some reason, the year subfield lets you enter more than 4 characters, so you have to manually switch to the next
		// subfield instead of switching to it automatically when you completely fill the previous subfield
		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, Keys.ARROW_RIGHT, false);

		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, hourVal, false);
		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, minuteVal, false);
		wait.waitForElementEnabled(dateFieldElem);
		text.enterText(dateFieldElem, amPmVal, false);
	}

	//---The following functions just check the visibility or text content of various elements of the page

	/**
	 * checks whether the index of the current log can be seen
	 *
	 * @return whether the index of the current log can be seen
	 *
	 * @author ssalisbury
	 */
	public boolean isCurrLogIndexDisplayed( )
	{
		boolean isLogIndexDisplayed = element.isElementDisplayed(currLogIndexDisplay);
		return isLogIndexDisplayed;
	}

	/**
	 * gets the index of the log that's currently being displayed
	 *
	 * @return the index of the log that's currently being displayed
	 * on failure returns -1
	 *
	 * @author ssalisbury
	 */
	public int getCurrLogIndex( )
	{
		int logIndex = -1;
		WebElement indexElem = wait.waitForElementDisplayed(currLogIndexDisplay);
		String indexText = text.getElementText(indexElem);
		try
		{
			logIndex = Integer.parseInt(indexText);
		}
		catch ( NumberFormatException e )
		{
			VertexLogger.log("invalid string displayed for the current log index", VertexLogLevel.WARN);
			e.printStackTrace();
		}
		return logIndex;
	}

	/**
	 * checks whether the number of logs that were retrieved by the most recent search can be seen
	 *
	 * @return whether the number of logs that were retrieved by the most recent search can be seen
	 *
	 * @author ssalisbury
	 */
	public boolean isTotalLogCountDisplayed( )
	{
		boolean isLogCountDisplayed = element.isElementDisplayed(totalLogCountDisplay);
		return isLogCountDisplayed;
	}

	/**
	 * gets the number of logs that were retrieved by the most recent search on the xml log viewer page
	 *
	 * @return the number of logs that were retrieved by the most recent search on the xml log viewer page
	 * on failure returns -1
	 *
	 * @author ssalisbury
	 */
	public int getTotalLogCount( )
	{
		int logCount = -1;
		WebElement countElem = wait.waitForElementDisplayed(totalLogCountDisplay);
		String countText = text.getElementText(countElem);
		try
		{
			logCount = Integer.parseInt(countText);
		}
		catch ( NumberFormatException e )
		{
			VertexLogger.log("invalid string displayed for the number of logs from the current search",
				VertexLogLevel.WARN);
			e.printStackTrace();
		}
		return logCount;
	}

	/**
	 * checks whether the phase of the current log can be seen
	 *
	 * @return whether the phase of the current log can be seen
	 *
	 * @author ssalisbury
	 */
	public boolean isLogPhaseDisplayed( )
	{
		boolean isLogPhaseDisplayed = element.isElementDisplayed(currLogPhaseDisplay);
		return isLogPhaseDisplayed;
	}

	/**
	 * gets the phase of the log that's currently being displayed
	 *
	 * @return the phase of the log that's currently being displayed
	 *
	 * @author ssalisbury
	 */
	public String getCurrLogPhase( )
	{
		String logPhase = null;
		WebElement phaseElem = wait.waitForElementDisplayed(currLogPhaseDisplay);
		logPhase = text.getElementText(phaseElem);
		return logPhase;
	}

	/**
	 * checks whether the date of the current log can be seen
	 *
	 * @return whether the date of the current log can be seen
	 *
	 * @author ssalisbury
	 */
	public boolean isLogDateDisplayed( )
	{
		boolean isLogDateDisplayed = element.isElementDisplayed(currLogDateDisplay);
		return isLogDateDisplayed;
	}

	/**
	 * gets the date of the log that's currently being displayed
	 *
	 * @return the date of the log that's currently being displayed
	 *
	 * @author ssalisbury
	 */
	public String getCurrLogDate( )
	{
		String logDate = null;
		WebElement dateElem = wait.waitForElementDisplayed(currLogDateDisplay);
		logDate = text.getElementText(dateElem);
		return logDate;
	}

	/**
	 * checks whether the contents of the current log can be seen
	 *
	 * @return whether the contents of the current log can be seen
	 *
	 * @author ssalisbury
	 */
	public boolean isLogContentsDisplayed( )
	{
		boolean isLogDisplayed = element.isElementDisplayed(logContentsDisplay);
		return isLogDisplayed;
	}

	/**
	 * gets the contents of the log that's currently being displayed
	 *
	 * @return the contents of the log that's currently being displayed
	 *
	 * @author ssalisbury
	 */
	public String getCurrLogContents( )
	{
		String logContents = null;
		WebElement contentsElem = wait.waitForElementDisplayed(logContentsDisplay);
		logContents = attribute.getElementAttribute(contentsElem, "textContent");
		return logContents;
	}
}
