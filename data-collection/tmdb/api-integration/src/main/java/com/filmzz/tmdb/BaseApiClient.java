package com.filmzz.tmdb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class BaseApiClient {
	private HttpClient httpClient;
	private ObjectMapper objectMapper;

	public BaseApiClient() {
		httpClient = createHttpClient();
		objectMapper = new ObjectMapper();
	}

	public int getIntField(JsonNode node, String fieldName) {
		JsonNode n = node.get(fieldName);
		return n.asInt();
	}

	public <T> T retrieveEntities(JsonNode response, String rootName, TypeReference<T> typeReference)
			throws Exception {
		ArrayNode array = (ArrayNode) response.get(rootName);
		if (array != null) {
			return this.readJsonObject(array.toString(), typeReference);
		}
		return (T) Collections.emptyList();
	}

	public <T> T readJsonObject(String jsonString, TypeReference<T> typeReference) throws Exception {
		T response = objectMapper.readValue(jsonString, typeReference);
		return response;
	}

	public JsonNode get(String formattedUrl, Map<String, String> queryString) throws Exception {
		URI uri = createUri(formattedUrl, queryString);
		System.out.println("URI for the request : " + uri.toString());
		HttpGet request = new HttpGet(uri);
		return retrieveEntity(request);
	}

	public URI createUri(String formattedUrl, Map<String, String> queryString) throws Exception {
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

	public JsonNode retrieveEntity(HttpUriRequest request) throws Exception {
		System.out.println(String.format("Going to make %s request to %s", request.getMethod().toString(), request.getURI().toString()));
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpClient.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		byte[] bytes = getResponseBodyBytes(response);

		String resultString = new String(bytes, "UTF8");
		//Rate limit exceeded - TMDB has a limit of 40 every 10 sec. And for OMDB too, wait for 10 sec
		//Every time we see a rate limit error, just wait for 10 seconds and retry.
		if (responseCode == 429 || responseCode == 403) {
			System.out.println("Rate limit exceeded. Sleeping for 10 seconds.");
			Thread.sleep(10000);
			System.out.println("Retrying the API endpoint " + request.getURI().toString() + " after sleeping for 10 seconds...");
			return retrieveEntity(request);
		}

		if (responseCode < 200 || responseCode > 300) {
			System.out.println(String.format("Request failed with errorCode: %s, response:%s", responseCode, resultString));
			throw new RuntimeException("Could not get the intended output. ResponseCode =" + responseCode + " Error=" + resultString);
		}

		if (resultString == null || resultString.isEmpty()) {
			System.out.println("No response body found.");
			return null;
		}

		return objectMapper.readTree(resultString);
	}


	public byte[] getResponseBodyBytes(HttpResponse response) throws IOException {
		InputStream ioStream = response.getEntity().getContent();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(ioStream, baos);
		byte[] bytes = baos.toByteArray();
		baos.close();
		ioStream.close();
		return bytes;
	}


	public HttpClient createHttpClient() {
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
}
