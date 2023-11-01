package com.vertex.quality.github.graphql;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.vertex.quality.common.enums.ResponseCodes;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

/**
 * Class to format and create data
 *
 * @author hho
 */
public class GithubDataResult
{
	private final String GITHUB_ACCESS_TOKEN = "b67695338ba0575af42d88d5da90f33c1eeeeaeb";
	private final OkHttpClient client;
	protected final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	protected final String branchNamePath = "data.repository.refs.edges[*].node.name";
	protected final String commitsPath = "data.repository.refs.edges[*].node.target.history.totalCount";
	protected final String ERROR_MESSAGE_PATH = "$.message";
	protected final String orgName;
	protected final String teamName;

	/**
	 * Constructor for the data. Requires the Github Org and team name.
	 *
	 * @param orgName  the organization name
	 * @param teamName the team name
	 */
	public GithubDataResult( String orgName, String teamName )
	{
		this.orgName = orgName;
		this.teamName = teamName;
		client = new OkHttpClient.Builder()
			.readTimeout(0, TimeUnit.NANOSECONDS)
			.connectTimeout(0, TimeUnit.NANOSECONDS)
			.writeTimeout(0, TimeUnit.NANOSECONDS)
			.build();
	}

	/**
	 * Gets the list of repositories under the team id
	 *
	 * @return the list of repositories for the team
	 */
	public List<String> getTeamRepositories( ) throws Exception
	{
		String teamId = getTeamId();

		String reposURL = String.format("https://api.github.com/teams/%s/repos", teamId);
		int currentPage = 1;

		String repositoriesPath = "$[*].name";
		List<String> teamRepos = new ArrayList<>();

		Object curDocument = null;

		do
		{
			// Getting JSON response for the current page
			io.restassured.response.Response curPageResponseBody = given()
				.auth()
				.oauth2(GITHUB_ACCESS_TOKEN)
				.and()
				.param("per_page", 100)
				.param("page", currentPage)
				.get(reposURL);
			curDocument = Configuration
				.defaultConfiguration()
				.jsonProvider()
				.parse(curPageResponseBody.asString());

			if ( curPageResponseBody.getStatusCode() != ResponseCodes.SUCCESS.getResponseCode() )
			{
				String errorMessage = JsonPath.read(curPageResponseBody.asString(), ERROR_MESSAGE_PATH);
				throw new Exception(errorMessage);
			}

			// Getting all the repos on the current page from the JSON response
			List<String> currentPageRepos = JsonPath.read(curDocument, repositoriesPath);

			// Adding all repos on current page to a list
			for ( String repo : currentPageRepos )
			{
				teamRepos.add((repo));
			}

			// Incrementing counter to go to next page for more repos
			currentPage++;
		}
		while ( !isLastPage(100, curDocument) );

		return teamRepos;
	}

	/**
	 * Gets the team id
	 */
	private String getTeamId( ) throws Exception
	{
		String teamURL = String.format("https://api.github.com/orgs/%s/teams", orgName);

		io.restassured.response.Response responseBody = given()
			.auth()
			.oauth2(GITHUB_ACCESS_TOKEN)
			.get(teamURL);

		if ( responseBody.getStatusCode() != ResponseCodes.SUCCESS.getResponseCode() )
		{
			String errorMessage = JsonPath.read(responseBody.asString(), ERROR_MESSAGE_PATH);
			throw new Exception(errorMessage);
		}

		String teamIdPath = String.format("$[?(@.name==\"%s\")].id", teamName);

		List<Integer> teamIds = JsonPath.read(responseBody.asString(), teamIdPath);

		return teamIds
			.get(0)
			.toString();
	}

	/**
	 * Checks if the contents of the responseBody is the last page in the pagination
	 *
	 * @param itemsPerPage         items per page for the response body
	 * @param responseBodyDocument the json response as a document object
	 *
	 * @return if the pagination is at the last page
	 */
	private static boolean isLastPage( int itemsPerPage, Object responseBodyDocument )
	{
		String numOfItemsOnCurrentPagePath = "$.length()";
		int numOfItemsOnCurrentPage = JsonPath.read(responseBodyDocument, numOfItemsOnCurrentPagePath);
		return numOfItemsOnCurrentPage < itemsPerPage;
	}

	/**
	 * Gets a list of all branches for the repo
	 *
	 * @param repoName the repo's name
	 *
	 * @return all the branches for the repo
	 */
	public List<String> getBranches( String repoName ) throws Exception
	{
		String branchCommitQuery = "{\"query\": \"{" + "repository(owner:\\\"" + orgName + "\\\", name:\\\"" +
								   repoName + "\\\") {" + "refs(first: 20, refPrefix: \\\"refs/heads/\\\") {" +
								   "edges {" + "node {" + "name" + "}}}}}\"}";

		Object jsonResponse = makePostRequest(branchCommitQuery);
		List<String> branches = JsonPath.read(jsonResponse, branchNamePath);
		return branches;
	}

	/**
	 * Returns the list of commit counts for each branch
	 *
	 * @param repoName the repo name
	 * @param pastDate the past date
	 * @param toDate   the to date
	 *
	 * @return the list of commit counts for each branch in a repository between the given dates
	 */
	public List<Integer> getRepositoryCommits( String repoName, String pastDate, String toDate ) throws Exception
	{
		String oldDate = pastDate + "T05:00:00Z";
		String newDate = toDate + "T04:59:59Z";
		String repoCommitQuery = "{\"query\": \"{" + "repository(owner:\\\"" + orgName + "\\\", name:\\\"" + repoName +
								 "\\\") {" + "refs(first:20, refPrefix:\\\"refs/heads/\\\") {" + "edges {" + "node {" +
								 "name target {" + "...on Commit {" + "history(since:\\\"" + oldDate +
								 "\\\", until:\\\"" + newDate + "\\\") {" + "totalCount" + "}}}}}}}}\"}";

		Object jsonResponse = makePostRequest(repoCommitQuery);
		List<Integer> commits = JsonPath.read(jsonResponse, commitsPath);
		return commits;
	}

	/**
	 * Makes a post request to the Github Graphql api
	 *
	 * @param jsonToSend the json payload to send
	 *
	 * @return the json response
	 */
	private Object makePostRequest( String jsonToSend ) throws Exception
	{
		RequestBody body = RequestBody.create(JSON, jsonToSend);

		Request request = new Request.Builder()
			.addHeader("Authorization", "bearer " + GITHUB_ACCESS_TOKEN)
			.url("https://api.github.com/graphql")
			.post(body)
			.build();

		Response response = client
			.newCall(request)
			.execute();
		Object jsonResponse = Configuration
			.defaultConfiguration()
			.jsonProvider()
			.parse(response
				.body()
				.string());
		response.close();
		return jsonResponse;
	}

	/**
	 * Removes repos from the list
	 *
	 * @param repos         the current repository list
	 * @param reposToRemove the list of repositories to remove
	 *
	 * @return the new repository list
	 */
	protected List<String> removeRepos( List<String> repos, String[] reposToRemove )
	{
		if ( reposToRemove != null )
		{
			for ( String repoToRemove : reposToRemove )
			{
				repos.remove(repoToRemove);
			}
		}
		return repos;
	}
}
