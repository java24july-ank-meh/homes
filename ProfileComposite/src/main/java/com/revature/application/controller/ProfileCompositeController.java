package com.revature.application.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.AuthenticationApi.LoginOptions;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@RestController
//@RequestMapping("profilecomposite")
public class ProfileCompositeController {

	@GetMapping("{id}")
	public ResponseEntity<Object> getProfileInfo(@PathVariable("id") String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8090/associates/" + id);
		JsonObject unitJson = getJsonFromService(
				"http://localhost:8093/unit/" + compositeObj.get("unitId").getAsString());
		JsonObject complexJson = getJsonFromService(
				"http://localhost:8093/complex/" + compositeObj.get("complexId").getAsString());

		// profile needs to know complex name
		compositeObj.add("complexName", complexJson.get("name"));
		// profile needs to know room number
		compositeObj.add("unitName", unitJson.get("unitNumber"));
		// profile needs to know id for routing
		compositeObj.add("unitId", unitJson.get("unitId"));
		return ResponseEntity.ok(compositeObj.toString());
	}

	@GetMapping("updateDocusign/{id}")
	public ResponseEntity<Object> updateDocusign(@PathVariable("id") String id)
			throws ClientProtocolException, IOException {
		String uName = "dduckett7537@gmail.com";
		String pass = "FalloutRWBY117";
		String intKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		String otherIntKey = "Bearer " + intKey;

		// add in the header stuff
		HttpClient httpClient = HttpClientBuilder.create().build();
		MultivaluedMap<String, String> qP = new MultivaluedMapImpl();
		qP.add("Username", uName);
		qP.add("Password", pass);
		qP.add("IntegratorKey", intKey);

		// Try another way
		// X-DocuSign-Authentication={"Username":"uName","Password":"pass","IntegratorKey":"intKey"}
		// X-DocuSign-Authentication={"Username":"dduckett7537@gmail.com","Password":"FalloutRWBY117","IntegratorKey":"6b512969-dc6a-4a85-b522-3094833f0373"}
		String entityStuff = "X-DocuSign-Authentication={\"Username\":\"" + uName + "\",\"Password\":\"" + pass
				+ "\",\"IntegratorKey\":\"" + intKey + "\"}";

		HttpPost request = new HttpPost("https://demo.docusign.net/restapi/v2/login_information");
		StringEntity params = new StringEntity(entityStuff);
		request.addHeader("content-type", "application/x-www-form-urlencoded");
		request.setEntity(params);
		HttpResponse resp = httpClient.execute(request);

		// X-DocuSign-Authentication
		/*
		 * Client client = Client.create(); WebResource resource =
		 * client.resource("https://demo.docusign.net/restapi/v2/login_information");
		 * ClientResponse resp = null; resp = resource.queryParams(qP)
		 * .header("Content-Type", "application/json;charset=UTF-8") .header("Username",
		 * uName) .header("Password", pass) .header("IntegratorKey", intKey)
		 * .get(ClientResponse.class);
		 * 
		 * //resp = resource.get(ClientResponse.class);
		 */
		String temp = resp.getEntity().toString();
		// JsonObject connectionStuff = new JsonParser().parse(temp).getAsJsonObject();

		// System.out.println(connectionStuff);
		System.out.println(temp);

		// return ResponseEntity.ok(connectionStuff.toString());
		return ResponseEntity.ok(temp.toString());

	}

	/* class to store needed information of envelopes (documents) associated with
	 * someone */
	class EnvelopeInfo {
		String status;
		String recipientsUri;
		String envelopeUri;
		String envelopeId;
		// TODO: Possibly add in the signer's email

		public EnvelopeInfo(String status, String recipientsUri, String envelopeUri, String envelopeId) {
			this.status = status;
			this.recipientsUri = recipientsUri;
			this.envelopeUri = envelopeUri;
			this.envelopeId = envelopeId;
		}
	}

	// The date must be formatted as YYYY-MM-DD
	@GetMapping("totallyDifferentAttampt/{date}")
	public ResponseEntity<Object> updateDocusignTry2(@PathVariable("date") String date) {
		/*
		 * Most of this entire method is taken from the DocuSign example page:
		 * https://www.docusign.com/developer-center/api-overview
		 */

		// Enter your DocuSign credentials
		String UserName = "dduckett7537@gmail.com";
		String Password = "FalloutRWBY117";
		String IntegratorKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		/*
		 * String uName = "dduckett7537@gmail.com"; String pass = "FalloutRWBY117";
		 * String intKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		 */

		// for production environment update to "www.docusign.net/restapi"
		String BaseUrl = "https://demo.docusign.net/restapi";

		// initialize the api client for the desired environment
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(BaseUrl);

		// create JSON formatted auth header
		String creds = "{\"Username\":\"" + UserName + "\",\"Password\":\"" + Password + "\",\"IntegratorKey\":\""
				+ IntegratorKey + "\"}";
		apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);

		// assign api client to the Configuration object
		Configuration.setDefaultApiClient(apiClient);

		try {
			// login call available off the AuthenticationApi
			AuthenticationApi authApi = new AuthenticationApi();

			// login has some optional parameters we can set
			AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
			loginOps.setApiPassword("true");
			loginOps.setIncludeAccountIdGuid("true");
			LoginInformation loginInfo = authApi.login(loginOps);

			// note that a given user may be a member of multiple accounts
			List<LoginAccount> loginAccounts = loginInfo.getLoginAccounts();

			System.out.println("LoginInformation: " + loginAccounts);

			// First, let us grab that base URL
			String baseURL = loginAccounts.get(0).getBaseUrl().split("/v2")[0];

			// make a list for all of the envelopes that we are going to get
			List<EnvelopeInfo> envelopes = new ArrayList<EnvelopeInfo>();

			// get the account that is default (sort of recomended by DocuSign's API
			// documentation)
			LoginAccount defaultAccount = null;
			for (LoginAccount account : loginAccounts) {
				if (account.getIsDefault().equals("true")) {
					defaultAccount = account;
					break;
				}
			}

			// check to make sure that the envelope is not already in our list and then only
			// keep the
			// ones that have been completed which we will use to update Associates in our
			// database

			/*
			 * Path for using the get changes after a certain date method from the DocuSign
			 * web service: /restapi/v2/accounts/[ACCOUNT_ID]/envelopes?from_date=[DATE]
			 * DATE = date (it's an input parameter for this method/mapping) ACCOUNT_ID =
			 * you know what this is.
			 */
			envelopes.addAll(getCompletedNewEnvelopesFromAccount(defaultAccount, envelopes, baseURL));

			// boolean toBeIncluded = true;
			// for() { //for each envelope returned by account call done by date
			// for() {//for each envelope already in the envelopes list
			// if() {//if the envelope is already in the list or the envelope is not
			// complete
			// toBeIncluded = false;
			// break;
			// }
			// }
			// }
			//
			// if(toBeIncluded) {
			// //TODO: Add logic for adding envelope to the envelopes List
			// }

			/*
			 * Okay. Now we need to go ahead and use this to get the completed documents
			 * based off of some specified date and time
			 */

			return ResponseEntity.ok(loginAccounts);
		} catch (com.docusign.esign.client.ApiException ex) {
			System.out.println("Exception: " + ex);
		}

		return null;

	}

	private Collection<? extends EnvelopeInfo> getCompletedNewEnvelopesFromAccount(LoginAccount account,
			List<EnvelopeInfo> envelopes, String baseUrl) {
		// First, use the below to get the envelopes needed
		/*
		 * Path for using the get changes after a certain date method from the DocuSign
		 * web service: /restapi/v2/accounts/[ACCOUNT_ID]/envelopes?from_date=[DATE]
		 * DATE = date (it's an input parameter for this method/mapping) ACCOUNT_ID =
		 * you know what this is.
		 */

		// Next.... let's try testing to see what sort of response we will get from the
		// recipientsUri

		return null;
	}


	//Important stuff! Do not delete.... Feel free to edit this URI to make it more professional though
	private final String RedirectionUri = "atItAgainTheRedirection/{date}";
	// The date must be formatted as YYYY-MM-DD
	@GetMapping("atItAgain/{date}")
	public String updateDocusignTry3(@PathVariable("date") String date) {
		/*
		 * Most of this entire method is taken from the DocuSign example page:
		 * https://www.docusign.com/developer-center/api-overview
		 * Actually, it was taken from the test found there but more information on that in the other method
		 * riiiiight below here that gets called after the user is redirected.
		 */

		String OAuthBaseUrl = "account-d.docusign.com";
		//To get your own integrator key, see the DocuSign developer website: https://www.docusign.com/developer-center
		String IntegratorKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		String ClientSecret = "40342a64-f8d8-44ca-b57a-43804ffd8248";//set up with integrator key setup
		String BaseUrl = "https://demo.docusign.net/restapi"; //Only for sandbox/ demo use.
		String fullRedirectUri = "http://localhost:8105/profilecomposite/" + RedirectionUri.replace("{date}", date);

		try {
			ApiClient apiClient = new ApiClient("https://" + OAuthBaseUrl, "docusignAccessCode", IntegratorKey,
					ClientSecret);
			apiClient.setBasePath(BaseUrl);
			// make sure to pass the redirect uri
			apiClient.configureAuthorizationFlow(IntegratorKey, ClientSecret, fullRedirectUri);
			Configuration.setDefaultApiClient(apiClient);

			// get DocuSign OAuth authorization url
			String oauthLoginUrl;
			oauthLoginUrl = apiClient.getAuthorizationUri();
			// open DocuSign OAuth login in the browser
			return "redirect:" + oauthLoginUrl;

		} catch (OAuthSystemException e) {
			// You get this exception if something goes wrong with oauthLoginUrl =
			// apiClient.getAuthorizationUri();
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	// The date must be formatted as YYYY-MM-DD
	@GetMapping(RedirectionUri)
	public ResponseEntity<Object> updateDocusignTry3Part2(@PathVariable("date") String date,
			@PathVariable("code") String code, @PathVariable("state") String state) {
		//once redirected here, we will have the code (denoted as the code path variable that we can
		//then exchange for the actual oauth token that we need to use for sending REST requests
		
		String OAuthBaseUrl = "account-d.docusign.com";
		//To get your own integrator key, see the DocuSign developer website: https://www.docusign.com/developer-center
		String IntegratorKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		String ClientSecret = "40342a64-f8d8-44ca-b57a-43804ffd8248";//set up with integrator key setup
		String BaseUrl = "https://demo.docusign.net/restapi"; //Only for sandbox/ demo use.
		
		
		/* Much of this following code was adapted from the docusign java client libraries on GitHub:
		 * https://github.com/docusign/docusign-java-client/blob/master/src/test/java/SdkUnitTests.java
		 * The specific test I used as the base of this was the OauthLoginTest.
		 * 
		 * For future integration with Docusign, please refer to this specific GitHub repository as it is far
		 * more helpful to use compared to the terrible standard REST API documentation provided by DocuSign.
		 * Trust me, the normal documentation is terrible and is basically only good for getting yourself confused
		 * and understanding some of the structure of the DocuSign models.
		 * 
		 * Base DocuSign Java GitHub repository: https://github.com/docusign/docusign-java-client
		 * DocuSign developer center (if you should need to look at it): https://www.docusign.com/developer-center
		 * */

		try {
			ApiClient apiClient = new ApiClient("https://" + OAuthBaseUrl, "docusignAccessCode", IntegratorKey,
					ClientSecret);
			apiClient.setBasePath(BaseUrl);
			// make sure to pass the redirect uri
			apiClient.configureAuthorizationFlow(IntegratorKey, ClientSecret, RedirectionUri);
			Configuration.setDefaultApiClient(apiClient);

			// assign it to the token endpoint
			apiClient.getTokenEndPoint().setCode(code);

			// ask to exchange the auth code with an access code
			apiClient.updateAccessToken();
			//TODO: Try to see if I can get the actual access token personally.
			//If that is not possible, I might have to move to standard REST calls to finish all of this
			
			/*********LEFT OFF HERE********/
			/* References for picking back up on work:
			 * https://docs.docusign.com/esign/restapi/Envelopes/Envelopes/listStatusChanges/
			 * https://docs.docusign.com/esign/guide/authentication/oa2_auth_code.html
			 * https://docs.docusign.com/esign/guide/authentication/auth_server.html#using-the-state-parameter */
			
			
			
			
			
			
			
			
			

			// now that the API client has an OAuth token, let's use in all
			// DocuSign APIs
			AuthenticationApi authApi = new AuthenticationApi(apiClient);
			AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
			loginOps.setApiPassword("true");
			loginOps.setIncludeAccountIdGuid("true");
			LoginInformation loginInfo;
			loginInfo = authApi.login(loginOps);

			List<LoginAccount> loginAccounts = loginInfo.getLoginAccounts();

			System.out.println("LoginInformation: " + loginInfo);

			// parse first account's baseUrl
			String[] accountDomain = loginInfo.getLoginAccounts().get(0).getBaseUrl().split("/v2");

			// below code required for production, no effect in demo (same
			// domain)
			apiClient.setBasePath(accountDomain[0]);
			Configuration.setDefaultApiClient(apiClient);
			
			//TODO: Actually implement the business logic finally. Yep. Still not done with that.
			return ResponseEntity.ok(code.toString());

		} catch (ApiException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8090/associates");
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
}
