package com.vertex.quality.github.graphql;

import com.vertex.quality.github.enums.GithubDates;
import com.vertex.quality.github.utils.GithubDateFormatter;

import java.util.Scanner;

/**
 * The main program to run to keep track of commits
 *
 * @author hho
 */
public class GraphQLCommitsTracker
{
	private GraphQLCommitsTracker( )
	{
	}

	public static void main( String[] args ) throws Exception
	{
		GithubDates dateFormat;
		String pastDate;
		String githubTeamName;
		String[] untrackedRepos;
		String orgName;

		try ( Scanner s = new Scanner(System.in) )
		{
			System.out.println("Usage: A tool to generate a CSV file that will " + "track commits made by a team");

			//vertexinc
			System.out.println("Enter the Github organization name:");
			orgName = s.nextLine();

			//O-Series Connectors
			System.out.println("Enter the Github team name to track commits for:");
			githubTeamName = s.nextLine();

			//list of repositories space-separated
			System.out.println("Enter the repository names to not keep track of:");
			String repos = s.nextLine();
			untrackedRepos = repos.split(" ");

			System.out.println("Enter the number corresponding to the date format:");
			System.out.println("[1]: Daily");
			System.out.println("[2]: Weekly");
			System.out.println("[3]: Monthly");
			String dateChoice = s.nextLine();

			switch ( dateChoice )
			{
				case "1":
					dateFormat = GithubDates.DAILY;
					break;
				case "2":
					dateFormat = GithubDates.WEEKLY;
					break;
				case "3":
					dateFormat = GithubDates.MONTHLY;
					break;
				default:
					throw new Exception("Bad date choice");
			}

			System.out.println("Enter a date in the format of yyyy-MM-dd to use for the past date:");
			pastDate = s.nextLine();
			if ( !GithubDateFormatter.isValidDate(pastDate) )
			{
				throw new Exception("Bad date format");
			}

			GithubDataResult dataResult = new GithubDataResult(orgName, githubTeamName);
			GithubFormData formData = new GithubFormData(dataResult);
			formData.writeData(dateFormat, untrackedRepos, pastDate);
		}
		catch ( Exception e )
		{
			throw new Exception(e);
		}
	}
}
