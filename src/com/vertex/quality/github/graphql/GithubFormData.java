package com.vertex.quality.github.graphql;

import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.github.enums.GithubDates;
import com.vertex.quality.github.utils.GithubDateFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that creates the excel file from the data
 *
 * @author hho
 */
public class GithubFormData
{
	protected GithubDataResult dataResult;
	private static String DEFAULT_FILE_NAME = "commits-tracker.csv";
	private static String DEFAULT_PATH = CommonDataProperties.PROJECT_PATH;

	public GithubFormData( GithubDataResult dataResult )
	{
		this.dataResult = dataResult;
	}

	/**
	 * Writes the data in a nice format to a csv
	 */
	public void writeData( GithubDates dateFormat, String[] reposToIgnore, String pastDate ) throws Exception
	{
		String fileSeparator = System.getProperty("file.separator");

		List<String> teamRepos = dataResult.getTeamRepositories();
		teamRepos = dataResult.removeRepos(teamRepos, reposToIgnore);
		List<String> dates = GithubDateFormatter.getDatesUntilPastDate(pastDate);

		try ( BufferedWriter writer = Files.newBufferedWriter(
			Paths.get(DEFAULT_PATH + fileSeparator + DEFAULT_FILE_NAME)) )
		{
			CSVPrinter csvPrinter = new CSVPrinter(writer, createHeader(dateFormat, dates));

			for ( String repo : teamRepos )
			{
				//				System.out.println("Working on repo {" + repo + "}");
				List<String> branches = dataResult.getBranches(repo);
				List<List<Integer>> branchCommits = new ArrayList<>();

				int previousDateCounter;
				for (
					previousDateCounter = 0;
					previousDateCounter + dateFormat.date < dates.size() ; previousDateCounter += dateFormat.date )
				{
					String previousDate = dates.get(previousDateCounter);
					String nextDate = dates.get(previousDateCounter + dateFormat.date);
					branchCommits.add(dataResult.getRepositoryCommits(repo, previousDate, nextDate));
				}

				if ( previousDateCounter < dates.size() )
				{
					String previousDate = dates.get(previousDateCounter);
					String nextDate = dates.get(dates.size() - 1);
					String endDate = GithubDateFormatter.incrementDay(nextDate);
					branchCommits.add(dataResult.getRepositoryCommits(repo, previousDate, endDate));
				}

				for ( int i = 0 ; i < branches.size() ; i++ )
				{
					csvPrinter.print(repo);
					csvPrinter.print(branches.get(i));

					for ( List<Integer> commits : branchCommits )
					{
						csvPrinter.print(commits.get(i));
					}
					csvPrinter.println();
				}
				csvPrinter.println();
			}
		}
		catch ( Exception e )
		{
			throw new Exception(e);
		}
	}

	/**
	 * Creates the header with the dates
	 *
	 * @param dateFormat the date format
	 * @param dates      the list of dates
	 *
	 * @return the header for the CSV file
	 */
	private CSVFormat createHeader( GithubDates dateFormat, List<String> dates )
	{
		List<String> dateRanges = new ArrayList<>();
		dateRanges.add("Repository name");
		dateRanges.add("Branch name");
		int previousDateCounter;
		for (
			previousDateCounter = 0;
			previousDateCounter + dateFormat.date < dates.size() ; previousDateCounter += dateFormat.date )
		{
			String previousDate = dates.get(previousDateCounter);
			String nextDate = dates.get(previousDateCounter + dateFormat.date - 1);
			dateRanges.add(previousDate + " - " + nextDate);
		}

		if ( previousDateCounter < dates.size() )
		{
			String previousDate = dates.get(previousDateCounter);
			String nextDate = dates.get(dates.size() - 1);
			dateRanges.add(previousDate + " - " + nextDate);
		}
		CSVFormat format = CSVFormat.DEFAULT.withHeader(dateRanges.toArray(new String[dateRanges.size()]));
		return format;
	}
}
