package com.vertex.quality.connectors.netsuite.common.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the transactions page
 *
 * @author hho
 */
public class NetsuiteTransactionsPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected By confirmationAlertloc = By.id("div__alert");

	public NetsuiteTransactionsPage( WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}

	/**
	 * Checks if the sales order was deleted
	 *
	 * @return if the sales order was deleted
	 */
	public boolean isOrderDeleted( )
	{
		WebElement confirmationAlert = wait.waitForElementDisplayed(confirmationAlertloc);
		String confirmation = "Transaction successfully Deleted";

		WebElement confirmationMessageElement = confirmationAlert.findElement(By.className("descr"));
		boolean isConfirmationShown = confirmation.equals(confirmationMessageElement.getText());
		return isConfirmationShown;
	}
}
