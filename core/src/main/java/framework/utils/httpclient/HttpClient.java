package framework.utils.httpclient;

import static com.jayway.restassured.RestAssured.given;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class HttpClient {

	private String baseUri;
	private int basePort;
	private String basePath;

	public HttpClient(String baseUri, int basePort, String basePath) {
		this.baseUri = baseUri;
		this.basePort = basePort;
		this.basePath = basePath;
	}

	public HttpClient(String baseUri, int basePort) {
		this.baseUri = baseUri;
		this.basePort = basePort;
		this.basePath = "";
	}

	public HttpClient(String baseUri, String basePath) throws Exception {
		this(baseUri);
		this.basePath = basePath;
	}

	public HttpClient(String baseUri) throws Exception {
		this.baseUri = baseUri;
		if (baseUri.matches("http[^s].*")) {
			this.basePort = 8080;
		} else if (baseUri.matches("https.*")) {
			this.basePort = 443;
		} else
			throw new Exception("incorrect protocol in endpoint is set");
		this.basePath = "";
	}

	public String getBaseUri() {
		return baseUri;
	}

	public int getBasePort() {
		return basePort;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getBaseUrl() {
		return getBaseUri() + ":" + getBasePort() + getBasePath();
	}

	@SuppressWarnings("unused")
	private HttpClient baseAuth(String userName, String password, String authUrl) {
		try {
			given().auth().basic(userName, password).get(new URL(authUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unused")
	private HttpClient baseAuthOnForm(String userName, String password, String authUrl) {
		try {
			given().auth().form(userName, password).get(new URL(authUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unused")
	private HttpClient baseAuthWithSSLCertificate(String certURL,
			String password, String authUrl) {
		try {
			given().auth().certificate(certURL, password).get(new URL(authUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unused")
	private HttpClient oauthAuth2(String accessToken, String authUrl) {
		try {
			given().auth().oauth2(accessToken).get(new URL(authUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	public Response callHttpGet(String endPointUrl) {
		Response response = null;
		try {
			response = given().get(new URL(getBaseUrl() + endPointUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public Response callHttpGet(String endPointUrl, Map<String, String> params) {
		Response response = null;
		try {
			response = given().parameters(params).get(new URL(getBaseUrl() + endPointUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public Response callHttpPost(String endPointUrl, String requestBody) {
		Response response = null;
		try {
			return given().body(requestBody).post(new URL(getBaseUrl() + endPointUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public Response callHttpPut(String endPointUrl, String requestBody) {
		Response response = null;
		try {
			response = given().body(requestBody).put(new URL(getBaseUrl() + endPointUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public Response callHttpDelete(String endPointUrl, String requestBody) {
		Response response = null;
		try {
			return given().body(requestBody).delete(new URL(getBaseUrl() + endPointUrl));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public void resetClientProps(){
		RestAssured.reset();
	}
}
