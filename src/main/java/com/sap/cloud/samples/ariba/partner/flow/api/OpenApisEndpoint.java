package com.sap.cloud.samples.ariba.partner.flow.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents API endpoint with basic authorization and API key.
 *
 */
class OpenApisEndpoint {

	private static final String DEBUG_EXECUTING_HTTP_GET_FOR = "Executing HTTP Get for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_GET_FOR = "Executed HTTP Get for [{}{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executing HTTP Post for [{}{}] with JSON content [{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executed HTTP Post for [{}{}] with JSON content [{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR_WITH_BINARY_BODIES = "Executing HTTP Post for [{}{}] with binary bodies...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR_WITH_BINARY_BODIES = "Executed HTTP Post for [{}{}] with binary bodies.";

	private static final String BOUNDARY = "------------------------80b26201cf2ec6a2";
	private static final String MULTIPART_MIXED_BOUNDARY = "multipart/mixed; boundary=" + OpenApisEndpoint.BOUNDARY;

	private static final String AUTHORIZATION_BASIC = "Basic";

	private static final String APIKEY = "apiKey";

	private static final Logger logger = LoggerFactory.getLogger(OpenApisEndpoint.class);

	private String baseUri;
	private Header basicAuthorizationHeader;
	private Header apikeyHeader;

	/**
	 * Constructor.
	 * 
	 * @param baseUri
	 *            base URI that will be called.
	 * @param serviceProviderUser
	 *            the service provider user.
	 * @param serviceProviderPassword
	 *            the service provider password.
	 * @param apiKey
	 *            API key that will be used when calling the API.
	 */
	OpenApisEndpoint(String baseUri, String serviceProviderUser, String serviceProviderPassword, String apiKey) {
		this.baseUri = baseUri;
		this.basicAuthorizationHeader = getBasicAuthorizationHeader(serviceProviderUser, serviceProviderPassword);
		this.apikeyHeader = getApikeyHeader(apiKey);
	}

	private static Header getBasicAuthorizationHeader(String user, String password) {
		return new BasicHeader(HttpHeaders.AUTHORIZATION,
				AUTHORIZATION_BASIC + " " + OpenApisEndpoint.encodeBase64(user, password));
	}

	private static String encodeBase64(String user, String password) {
		return new String(Base64.encodeBase64((user + ":" + password).getBytes()));
	}

	private Header getApikeyHeader(String apikey) {
		return new BasicHeader(APIKEY, apikey);
	}

	/**
	 * Performs HTTP Get request with OAuth authentication for the endpoint with
	 * the given path with the given HTTP headers.
	 * 
	 * @param path
	 *            the path to be called.
	 * @param headers
	 *            map with HTTP header names and values to be included in the
	 *            request.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	CloseableHttpResponse executeHttpGet(String path, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_GET_FOR, baseUri, path);

		HttpGet httpGet = createHttpGet(baseUri + path);
		for (String header : headers.keySet()) {
			httpGet.addHeader(header, headers.get(header));
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpGet);

		logger.debug(DEBUG_EXECUTED_HTTP_GET_FOR, baseUri, path);
		return response;
	}

	private HttpGet createHttpGet(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader(basicAuthorizationHeader);
		httpGet.addHeader(apikeyHeader);

		return httpGet;
	}

	/**
	 * Performs HTTP Post request with OAuth authentication for the endpoint
	 * with the given path.
	 * 
	 * @param path
	 *            the path to be called.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	CloseableHttpResponse executeHttpPost(String path) throws ClientProtocolException, IOException {
		return executeHttpPost(path, null, null);
	}

	/**
	 * Performs HTTP Post request with OAuth authentication for the endpoint
	 * with the given path, with the given JSON as payload and the given HTTP
	 * headers.
	 * 
	 * @param path
	 *            the path to be called.
	 * @param headers
	 *            map with HTTP header names and values to be included in the
	 *            request.
	 * @param jsonContent
	 *            the JSON content to be posted.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	CloseableHttpResponse executeHttpPost(String path, Map<String, String> headers, String jsonContent)
			throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_POST_FOR_WITH_JSON_CONTENT, baseUri, path, jsonContent);

		HttpPost httpPost = createHttpPost(baseUri + path);

		if (headers != null) {
			for (String header : headers.keySet()) {
				httpPost.addHeader(header, headers.get(header));
			}
		}

		if (jsonContent != null) {
			StringEntity input = new StringEntity(jsonContent);
			input.setContentType(MediaType.APPLICATION_JSON);
			httpPost.setEntity(input);
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		logger.debug(DEBUG_EXECUTED_HTTP_POST_FOR_WITH_JSON_CONTENT, baseUri, path, jsonContent);
		return response;
	}

	/**
	 * Performs HTTP Post request with OAuth authentication for the endpoint
	 * with the given path, with the given binary bodies as payload. Uses
	 * multipart/mixed content type.
	 * 
	 * @param path
	 *            the path to be called.
	 * @param binaryBodies
	 *            the payload.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	CloseableHttpResponse executeHttpPost(String path, List<BinaryBody> binaryBodies)
			throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_POST_FOR_WITH_BINARY_BODIES, baseUri, path);

		HttpPost httpPost = createHttpPost(baseUri + path);
		httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MULTIPART_MIXED_BOUNDARY);

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

		for (BinaryBody binaryBody : binaryBodies) {
			multipartEntityBuilder.addBinaryBody(binaryBody.getBinaryBodyName(), binaryBody.getFileStream(),
					ContentType.create(binaryBody.getMediaType()), binaryBody.getFileName());
		}

		HttpEntity httpEntity = multipartEntityBuilder.setBoundary(OpenApisEndpoint.BOUNDARY).build();
		httpPost.setEntity(httpEntity);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		logger.debug(DEBUG_EXECUTED_HTTP_POST_FOR_WITH_BINARY_BODIES, baseUri, path);
		return response;
	}

	private HttpPost createHttpPost(String uri) {
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader(basicAuthorizationHeader);
		httpPost.addHeader(apikeyHeader);

		return httpPost;
	}
}
