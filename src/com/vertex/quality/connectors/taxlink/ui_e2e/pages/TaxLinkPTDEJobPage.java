package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class contains methods for verifying PTDE job status in TaxLink
 *
 * @author Shilpi.Verma
 */
public class TaxLinkPTDEJobPage extends TaxLinkBasePage
{
	public TaxLinkPTDEJobPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "h1")
	WebElement headerJobPage;

	@FindBy(xpath = "//input[@placeholder = 'PTDE Zip File Name']")
	WebElement inputField;

	@FindBy(xpath = "//button[contains(text(), 'SEARCH')]")
	WebElement searchButton;

	@FindBy(xpath = "//h1[contains(text(), 'Batch Job Details')]")
	WebElement textBatchJobDetails;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'jobStatusHistory.jobId']")
	List<WebElement> summaryTableJobDetails;

	/**
	 * Method to return columns
	 *
	 * @return
	 */
	public By tableColumn( String column )
	{
		By columnName = By.xpath(".//following-sibling::div[@col-id = '" + column + "']");

		return columnName;
	}

	/**
	 * Method to verify summary table of PTDE job status
	 */
	public boolean ptdeJobStatus( )
	{
		boolean final_flag = false;
		String zipFileName = String.valueOf(getFileReadPath().get(0));

		navigateToPTDEJobStatus();

		wait.waitForElementDisplayed(headerJobPage);
		boolean header_flag = headerJobPage
			.getText()
			.equals("Batch Job Status");

		text.enterText(inputField, zipFileName);
		wait.waitForElementEnabled(searchButton);

		click.clickElement(searchButton);
		wait.waitForElementDisplayed(textBatchJobDetails);

		boolean summaryTable_flag = !summaryTableJobDetails.isEmpty();

		summaryTableJobDetails.forEach(col ->
		{

			String jobId = col.getText();
			String stageSeqId = col
				.findElement(tableColumn("jobStatusHistory.jobStage.stageSequenceId"))
				.getText();
			String stageName = col
				.findElement(tableColumn("jobStatusHistory.jobStage.stageName"))
				.getText();
			String statusCode = col
				.findElement(tableColumn("jobStatusHistory.statusCode"))
				.getText();
			String timeStamp = col
				.findElement(tableColumn("jobStatusHistory.processTimestamp"))
				.getText();
			String resFileName = col
				.findElement(tableColumn("taxCalculationJobDetails.responseTaxFileName"))
				.getText();
			String downloadFileName = col
				.findElement(tableColumn("taxCalculationJobDetails.ucmDownloadFileName"))
				.getText();

			LinkedHashMap<String, String> hmap = new LinkedHashMap<>();
			hmap.put("Job Id", jobId);
			hmap.put("Stage Sequence Id", stageSeqId);
			hmap.put("Stage Name", stageName);
			hmap.put("Stage Status", statusCode);
			hmap.put("Processed Date", timeStamp);
			hmap.put("Response File Name", resFileName);
			hmap.put("Download File Name", downloadFileName);

			VertexLogger.log(String.valueOf(hmap));
		});

		try
		{
			deleteFile();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		if ( header_flag && summaryTable_flag )
		{
			final_flag = true;
		}
		return final_flag;
	}
}
