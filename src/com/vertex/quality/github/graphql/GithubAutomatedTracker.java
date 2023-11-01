package com.vertex.quality.github.graphql;

import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.github.enums.GithubDates;
import com.vertex.quality.github.utils.GithubDateFormatter;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created to allow automation of the github commit tracker
 *
 * @author hho
 */
@Test(groups = { "github", "commit_tracker" })
public class GithubAutomatedTracker
{
	/**
	 * Sets up the github commit tracker writer
	 *
	 * @param date the date
	 */
	protected void setup( GithubDates date ) throws Exception
	{
		GithubDates dateFormat = date;
		GithubDataResult dataResult = new GithubDataResult("vertexinc", "O-Series Connectors");
		GithubFormData formData = new GithubFormData(dataResult);
		String pastDate = GithubDateFormatter.getPastDate(GithubDates.MONTHLY, 2);
		formData.writeData(dateFormat, null, pastDate);
	}

	/**
	 * Prints out the results of the commit tracker
	 */
	protected void readFile( )
	{
		String DEFAULT_FILE_NAME = "commits-tracker.csv";
		String DEFAULT_PATH = CommonDataProperties.PROJECT_PATH;
		String fileSeparator = System.getProperty("file.separator");

		try ( BufferedReader reader = Files.newBufferedReader(
			Paths.get(DEFAULT_PATH + fileSeparator + DEFAULT_FILE_NAME)) )
		{
			String line;
			while ( (line = reader.readLine()) != null )
			{
				System.out.println(line);
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes a CSV file tracking the commits made every day for the past two months
	 * Also prints the results out as well
	 */
	@Test(groups = { "daily" })
	protected void dailyPastTwoMonths( ) throws Exception
	{
		setup(GithubDates.DAILY);
		readFile();
	}

	/**
	 * Writes a CSV file tracking the commits made every week for the past two months
	 * Also prints the results out as well
	 */
	@Test(groups = { "weekly" })
	protected void weeklyPastTwoMonths( ) throws Exception
	{
		setup(GithubDates.WEEKLY);
		readFile();
	}

	/**
	 * Writes a CSV file tracking the commits made every month for the past two months
	 * Also prints the results out as well
	 */
	@Test(groups = { "monthly" })
	protected void monthlyPastTwoMonths( ) throws Exception
	{
		setup(GithubDates.MONTHLY);
		readFile();
	}
}
