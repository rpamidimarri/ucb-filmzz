package com.filmzz.tmdb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.type.TypeReference;

import com.filmzz.tmdb.model.TmdbMovie;
import com.filmzz.tmdb.persistence.ActiveMovie;
import com.filmzz.tmdb.persistence.MovieService;

public class TmdbApiClient {
	private HttpClient httpClient;
	private ObjectMapper objectMapper;
	private MovieService movieService;
	private static final String BASE_URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing";
	private static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
	private static final String BASE_URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming";
	private static final String API_KEY = "api_key";
	private static final String API_KEY_VALUE = "bd77e1c9326f3b7eb9a7f6df5e87bef6";
	private static final String PAGE = "page";
	private static final String TOTAL_PAGES = "total_pages";
	private static final String TOTAL_RESULTS = "total_results";
	private static final String RESULTS = "results";
	private static final String STATUS_RUNNING = "running";
	private static final String STATUS_UPCOMING = "upcoming";

	public TmdbApiClient() {
		httpClient = createHttpClient();
		objectMapper = new ObjectMapper();
		movieService = new MovieService();
	}

	public static void main(String[] args) {
		TmdbApiClient apiClient = new TmdbApiClient();
		apiClient.downloadActiveMovies();
	}
	
	private List<String> getIgnoredTitles() {
		List<String> ignoredTitles = new ArrayList<String>();
		ignoredTitles.add("24");
		ignoredTitles.add("fake");
		ignoredTitles.add("parents");
		ignoredTitles.add("countdown");
		ignoredTitles.add("the ticket");
		ignoredTitles.add("five");
		return ignoredTitles;
	}

	public void downloadActiveMovies() {
		List<String> ignoredTitles = getIgnoredTitles();
		Set<String> setOfAlreadySavedTmdbIds = new HashSet<String>();
		int existingExecCount = movieService.getLatestExecutionCount();
		int execCount = 1;
		if (existingExecCount > 0) {
			System.out.println("We have downloaded active movies " + existingExecCount + " times so far. We are going to download the movies once more");
			execCount = existingExecCount + 1;
		}

		Map<String, String> defaultQuery = createQueryString();
		List<TmdbMovie> moviesNowPlaying = getListOfMovies(BASE_URL_NOW_PLAYING, defaultQuery);
		if (moviesNowPlaying == null || moviesNowPlaying.isEmpty()) {
			System.out.println("SOMETHING WENT WRONG! NO CURRENTLY RUNNING MOVIES FOUND");
		}

		System.out.println("Total number of movies now playing= " + moviesNowPlaying.size());
		for (TmdbMovie movie : moviesNowPlaying) {
			if (!setOfAlreadySavedTmdbIds.contains(String.valueOf(movie.getTmdbId()))) {
				boolean searchForTweets = true;
				if (ignoredTitles.contains(movie.getTitle().toLowerCase())) {
					searchForTweets = false;
				}
				ActiveMovie activeMovie = new ActiveMovie(execCount, STATUS_RUNNING, movie, searchForTweets);
				System.out.println("Saving the currently running movie:" + activeMovie.getTitle() + " with the primaryKey:" +activeMovie.getTmdbIdCount());
				movieService.createActiveMovie(activeMovie);	
				setOfAlreadySavedTmdbIds.add(String.valueOf(movie.getTmdbId()));
			} else {
				System.out.println("The currently running movie:" + movie.getTitle() + " has already been saved");
			}
		}

		List<TmdbMovie> moviesUpcoming = getListOfMovies(BASE_URL_UPCOMING, defaultQuery);
		if (moviesUpcoming == null || moviesUpcoming.isEmpty()) {
			System.out.println("SOMETHING WENT WRONG! NO UPCOMING MOVIES FOUND");
		}

		System.out.println("Total number of movies upcoming= " + moviesUpcoming.size());	
		for (TmdbMovie movie : moviesUpcoming) {
			if (!setOfAlreadySavedTmdbIds.contains(String.valueOf(movie.getTmdbId()))) {
				boolean searchForTweets = true;
				if (ignoredTitles.contains(movie.getTitle().toLowerCase())) {
					searchForTweets = false;
				}
				ActiveMovie activeMovie = new ActiveMovie(execCount, STATUS_UPCOMING, movie, searchForTweets);
				System.out.println("Saving the upcoming movie:" + activeMovie.getTitle() + " with the primaryKey:" +activeMovie.getTmdbIdCount());
				movieService.createActiveMovie(activeMovie);	
				setOfAlreadySavedTmdbIds.add(String.valueOf(movie.getTmdbId()));
			} else {
				System.out.println("The upcoming movie:" + movie.getTitle() + " has already been saved");
			}
		}
	}

	public List<TmdbMovie> getListOfMovies(String url, Map<String, String> queryString) {
		JsonNode response = null;
		try {
			response = retrieveEntityList(url, queryString, -1);
		} catch (Exception e) {
			e.printStackTrace();
			//Return if exception
			return null;
		}

		//We have read the first page
		int numberOfPagesRead = 1;

		int totalNumberOfPages = getIntField(response, TOTAL_PAGES);
		System.out.println("Total number of pages= " + totalNumberOfPages + " for url= " + url.toString());
		int totalNumberOfMovies = getIntField(response, TOTAL_RESULTS);
		System.out.println("Total number of movies= " + totalNumberOfMovies + " for url= " + totalNumberOfMovies);

		List<TmdbMovie> moviesWithLimitedInformation = new ArrayList<TmdbMovie>();
		try {
			moviesWithLimitedInformation.addAll(this.retrieveEntities(response, RESULTS, new TypeReference<List<TmdbMovie>>() {
			}));
		} catch (Exception e) {
			e.printStackTrace();
			//Return if exception
			return null;
		}

		//Note - we are going to rely ONLY on the total_pages returned in the first response. 
		//It is possible that TMDB may return an updated and incorrect total_pages in later responses causing us to go into an 
		//infinite loop. To avoid that, just honor the total_pages in the first response.
		while (totalNumberOfPages > numberOfPagesRead) {
			try {
				response = retrieveEntityList(url, queryString, numberOfPagesRead + 1);
			} catch (Exception e) {
				e.printStackTrace();
				//Return if exception
				return null;
			}
			numberOfPagesRead++;

			try {
				moviesWithLimitedInformation.addAll(this.retrieveEntities(response, RESULTS, new TypeReference<List<TmdbMovie>>() {
				}));
			} catch (Exception e) {
				e.printStackTrace();
				//Return if exception
				return null;
			}
		}


		//We now have the list of all movies in all the pages. Now, for every movie, we need to make a call to find extra information 
		//about the movie
		if (moviesWithLimitedInformation == null || moviesWithLimitedInformation.isEmpty()) {
			System.out.println("The API call to TMDB url= " + url + " returned no movies!");
			return null;
		}

		System.out.println("Number of movies retrieved= " + moviesWithLimitedInformation.size() + " for url=" + url);
		System.out.println("About to make API calls to get more information about all these movies for url=" + url);

		List<TmdbMovie> moviesWithAllFields = new ArrayList<TmdbMovie>();
		for (TmdbMovie movie : moviesWithLimitedInformation) {
			moviesWithAllFields.add(getExtraInformationAboutMovie(String.valueOf(movie.getTmdbId())));
		}

		return moviesWithAllFields;
	}

	private int getIntField(JsonNode node, String fieldName) {
		JsonNode n = node.get(fieldName);
		return n.asInt();
	}

	private <T> T retrieveEntities(JsonNode response, String rootName, TypeReference<T> typeReference)
			throws Exception {
		ArrayNode array = (ArrayNode) response.get(rootName);
		if (array != null) {
			return this.readJsonObject(array.toString(), typeReference);
		}
		return (T) Collections.emptyList();
	}

	private <T> T readJsonObject(String jsonString, TypeReference<T> typeReference) throws Exception {
		T response = objectMapper.readValue(jsonString, typeReference);
		return response;
	}

	private JsonNode retrieveEntityList(String action, Map<String, String> queryString, int pageNumber)
			throws Exception {
		if (pageNumber >= 1) {
			queryString.put(PAGE, String.valueOf(pageNumber));
		}
		return get(action, queryString);
	}

	private JsonNode get(String formattedUrl, Map<String, String> queryString) throws Exception {
		URI uri = createUri(formattedUrl, queryString);
		System.out.println("URI for the request : " + uri.toString());
		HttpGet request = new HttpGet(uri);
		return retrieveEntity(request);
	}

	private URI createUri(String formattedUrl, Map<String, String> queryString) throws Exception {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (final Map.Entry<String, String> entry : queryString.entrySet()) {
			NameValuePair nvp = new NameValuePair() {
				public String getName() {
					return entry.getKey();
				}

				public String getValue() {
					return entry.getValue();
				}
			};

			nameValuePairs.add(nvp);
		}

		return new URIBuilder(formattedUrl).setParameters(nameValuePairs).build();
	}

	private TmdbMovie getExtraInformationAboutMovie(String id) {
		Map<String, String> queryString = createQueryString();
		JsonNode jsonObject = null;
		try {
			String userUrl = BASE_URL_MOVIE + id;
			URI uri = createUri(userUrl, queryString);
			HttpGet request = new HttpGet(uri);
			jsonObject = retrieveEntity(request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception in making the API request to Tmdb", e);
		}

		try {
			return readJsonObject(jsonObject.toString(), new TypeReference<TmdbMovie>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception in parsing the Tmdb API response to a Movie", e);
		}		
	}



	private JsonNode retrieveEntity(HttpUriRequest request) throws Exception {
		System.out.println(String.format("Going to make %s request to %s", request.getMethod().toString(), request.getURI().toString()));
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpClient.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		byte[] bytes = getResponseBodyBytes(response);

		String resultString = new String(bytes, "UTF8");
		if (responseCode == 403) {
			System.out.println(String.format("Authentication failed: %s", resultString));
			throw new RuntimeException("Could not get authenticated . The header sent is incorrect. Error=" + resultString);
		}

		//Rate limit exceeded - TMDB has a limit of 40 every 10 sec.
		//Every time we see a rate limit error, just wait for 10 seconds and retry.
		if (responseCode == 429) {
			System.out.println("Rate limit exceeded. Sleeping for 10 seconds.");
			Thread.sleep(10000);
			System.out.println("Retrying the API endpoint " + request.getURI().toString() + " after sleeping for 10 seconds...");
			return retrieveEntity(request);
		}

		if (responseCode < 200 || responseCode > 300) {
			System.out.println(String.format("Request failed with errorCode: %s, response:%s", responseCode, resultString));
			throw new RuntimeException("Could not get the intended output from TMDB. ResponseCode =" + responseCode + " Error=" + resultString);
		}

		if (resultString == null || resultString.isEmpty()) {
			System.out.println("No response body found.");
			return null;
		}

		return objectMapper.readTree(resultString);
	}


	private byte[] getResponseBodyBytes(HttpResponse response) throws IOException {
		InputStream ioStream = response.getEntity().getContent();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(ioStream, baos);
		byte[] bytes = baos.toByteArray();
		baos.close();
		ioStream.close();
		return bytes;
	}


	private HttpClient createHttpClient() {
		HttpClientConnectionManager cm = new BasicHttpClientConnectionManager();

		RequestConfig defaultRequestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
				.setStaleConnectionCheckEnabled(true)
				.setConnectTimeout(35000)
				.setSocketTimeout(160000)
				.build();

		return HttpClientBuilder.create()
				.setConnectionManager(cm)
				.disableAutomaticRetries()
				.setDefaultRequestConfig(defaultRequestConfig)
				.build();
	}

	public Map<String, String> createQueryString() {
		Map<String, String> queryString = new HashMap<String, String>();
		queryString.put(API_KEY, API_KEY_VALUE);
		return queryString;
	}
}
