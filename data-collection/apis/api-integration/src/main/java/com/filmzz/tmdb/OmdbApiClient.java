package com.filmzz.tmdb;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;

import com.filmzz.tmdb.model.OmdbMovie;

public class OmdbApiClient extends BaseApiClient {
	private static final String BASE_URL = "http://www.omdbapi.com/";
	private static final String TOMATOES_KEY = "tomatoes";
	private static final String TOMATOES_VALUE = "true";
	private static final String SEARCH_KEY = "t";
	private static final String TYPE_KEY = "type";
	private static final String TYPE_VALUE = "movie";
	private static final String YEAR_KEY = "y";
	
	public OmdbApiClient() {
		super();
	}
	
	public OmdbMovie getOmdbMovie(String title, String type, String year) {
		URI uri = null;
		try {
			uri = createUri(BASE_URL, createQueryString(title, type, year));
			HttpGet request = new HttpGet(uri);
			JsonNode response = retrieveEntity(request);
			return readJsonObject(response.toString(), new TypeReference<OmdbMovie>() {
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public Map<String, String> createQueryString(String title, String type, String year) {
		Map<String, String> queryString = new HashMap<String, String>();
		queryString.put(TOMATOES_KEY, TOMATOES_VALUE);
		if (type == null || type.isEmpty()) {
			type = TYPE_VALUE;
		}
		queryString.put(TYPE_KEY, type);
		queryString.put(SEARCH_KEY, title);
		if (year != null && !year.isEmpty()) {
			queryString.put(YEAR_KEY, year);
		}
		return queryString;
	}
	
	public static void main(String[] args) {
		OmdbApiClient client = new OmdbApiClient();
		System.out.println("3 idiots, 2009 :" + client.getOmdbMovie("3 idiots", "movie", "2009"));
		System.out.println("My Name Is Khan 2009 :" + client.getOmdbMovie("My name is khan", "movie", "2010"));
		System.out.println("$#*! My Dad Says :" + client.getOmdbMovie("$#*! My Dad Says", "series", null));
	}

}
