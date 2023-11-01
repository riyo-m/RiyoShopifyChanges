package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Represents the Netsuite authentication page right after logging in
 * Requires the user to answer one of three authentication question
 *
 * @author hho
 */
public class NetsuiteAuthenticationPage extends NetsuitePage
{
	protected By authenticationQuestionText = By.tagName("form");
	protected By authenticationAnswerField = By.name("answer");
	protected By authenticationSubmitButton = By.xpath("//input[@value='Submit' or @value='submit' or @type='Submit' or @type='submit' or @name='submitter']");

	public NetsuiteAuthenticationPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Gets the question's element and returns its text, the question
	 *
	 * @return the authentication question text
	 */
	private String getAuthenticationQuestion( )
	{
		WebElement authQuestionElement = findAuthenticationQuestionElement();
		return (authQuestionElement != null ? authQuestionElement.getText() : "");
	}

	/**
	 * Helper method to isAuthenticationQuestionPresent() and getAuthenticationQuestion()
	 * Finds the web element that contains the authentication question
	 *
	 * @return the web element that contains the authentication question
	 */
	private WebElement findAuthenticationQuestionElement( )
	{
		try {
			WebElement form = wait.waitForElementPresent(authenticationQuestionText,20);
			List<WebElement> tds = form.findElements(By.tagName("td"));

			WebElement authQuestion = null;

			for ( int i = 0 ; i < tds.size() && authQuestion == null ; i++ )
			{
				WebElement tmpElement = tds.get(i);
				if ( tmpElement
						 .getText()
						 .trim()
						 .equals("Question:") && i + 1 < tds.size() )
				{
					if ( i + 1 < tds.size() )
					{
						authQuestion = tds.get(i+1);
					}
				}
			}
			return authQuestion;

		}catch(Exception e){
			VertexLogger.log("Error: authentication questions not found. They are either missing or have been changed\n", VertexLogLevel.ERROR );
			e.printStackTrace();
		}
			return null;
	}

	/**
	 * inputs the authentication answer depending on the question into the field
	 *
	 * @param authenticationAnswers the authentication answers
	 */
	public void answerAuthenticationQuestion( String... authenticationAnswers )
	{
		System.out.println("Entering Authentication Answer");

			String question = getAuthenticationQuestion();
			WebElement answerField = wait.waitForElementPresent(authenticationAnswerField);
			answerField.clear();
			String answer = "";

			switch ( question )
			{
				case "What school did you attend for sixth grade?":
					answer = authenticationAnswers[0];
					break;
				case "What is your oldest sibling's middle name?":
					answer = authenticationAnswers[1];
					break;
				case "In what city or town was your first job?":
					answer = authenticationAnswers[2];
					break;
			}

			System.out.println("Authentication Answer is: " + answer );
			//jsWaiter.sleep(600000);
			text.enterText(answerField, answer);
			System.out.println("Answer entered successfully");
			return;
	}

	/**
	 * Clicks the submit button on the authentication page
	 * If successful, should redirect to the Netsuite homepage
	 *
	 * @return the Netsuite homepage on success
	 */
	public NetsuiteHomepage loginToHomepage(WebDriver drive )
	{
		//Actions loginActions = new Actions(driver);
		//jsWaiter.sleep(600000);
		WebElement targetElement = wait.waitForElementDisplayed(authenticationSubmitButton);
		targetElement.click();
//		loginActions
//			.moveToElement(targetElement)
//			.click(targetElement);
//		loginActions.perform();
		System.out.println("waiting for the homepage to load");
		waitForPageLoad();
		System.out.println("Returning Homepage object");
		return initializePageObject(NetsuiteHomepage.class);
	}
}

