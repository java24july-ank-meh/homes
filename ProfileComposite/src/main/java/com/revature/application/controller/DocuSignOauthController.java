package com.revature.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.AuthenticationApi.LoginOptions;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.client.Pair;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;
import com.revature.application.model.MrSingletonState;
import com.revature.application.util.RandomString;
import com.sun.jersey.api.client.GenericType;

import io.swagger.annotations.ApiParam;

@Component
@Controller
@RequestMapping("profilecomposite") // change this later after testing
public class DocuSignOauthController {
	// Important stuff! Do not delete.... Feel free to edit this URI to make it more professional though
	private final String RedirectionUri = "atItAgainTheRedirection";

	// The date must be formatted as YYYY-MM-DD
	//TODO: Possibly turn this into a POST with the QueryParam turned into a RequestAttribute instead
	//Might not work for testing since I can't set the date in the url at that point.
	@GetMapping("atItAgain")
	public String updateDocusignTry3(@QueryParam("date") String date) {
		/*
		 * Most of this entire method is taken from the DocuSign example page:
		 * https://www.docusign.com/developer-center/api-overview
		 * Actually, it was taken from the test found there but more information on that in the other method
		 * riiiiight below here that gets called after the user is redirected.
		 */

		MrSingletonState.setDate(date);
		
		
		String OAuthBaseUrl = "account-d.docusign.com";
		// To get your own integrator key, see the DocuSign developer website:
		// https://www.docusign.com/developer-center
		String IntegratorKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		String ClientSecret = "40342a64-f8d8-44ca-b57a-43804ffd8248";// set up with integrator key setup
		String BaseUrl = "https://demo.docusign.net/restapi"; // Only for sandbox/ demo use.
		String fullRedirectUri = "http://localhost:8105/profilecomposite/" + RedirectionUri;

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
	public ResponseEntity<Object> updateDocusignTry3Part2(@ApiParam("code") String code) {
		// once redirected here, we will have the code (denoted as the code path
		// variable that we can
		// then exchange for the actual oauth token that we need to use for sending REST
		// requests

		String OAuthBaseUrl = "account-d.docusign.com";
		// To get your own integrator key, see the DocuSign developer website:
		// https://www.docusign.com/developer-center
		String IntegratorKey = "6b512969-dc6a-4a85-b522-3094833f0373";
		String ClientSecret = "40342a64-f8d8-44ca-b57a-43804ffd8248";// set up with integrator key setup
		String BaseUrl = "https://demo.docusign.net/restapi"; // Only for sandbox/ demo use.

		/*
		 * Much of this following code was adapted from the docusign java client libraries on GitHub:
		 * https://github.com/docusign/docusign-java-client/blob/master/src/test/java/SdkUnitTests.java
		 * The specific test I used as the base of this was the OauthLoginTest.
		 * 
		 * For future integration with Docusign, please refer to this specific GitHub
		 * repository as it is far more helpful to use compared to the terrible standard
		 * REST API documentation provided by DocuSign. Trust me, the normal
		 * documentation is terrible and is basically only good for getting yourself
		 * confused and understanding some of the structure of the DocuSign models.
		 * 
		 * Base DocuSign Java GitHub repository:
		 * https://github.com/docusign/docusign-java-client DocuSign developer center
		 * (if you should need to look at it): https://www.docusign.com/developer-center
		 */

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
			// TODO: Try to see if I can get the actual access token personally.
			// If that is not possible, I might have to move to standard REST calls to
			// finish all of this

			/********* LEFT OFF HERE ********/
			/*
			 * References for picking back up on work:
			 * https://docs.docusign.com/esign/restapi/Envelopes/Envelopes/listStatusChanges/
			 * https://docs.docusign.com/esign/guide/authentication/oa2_auth_code.html
			 * https://docs.docusign.com/esign/guide/authentication/auth_server.html#using-the-state-parameter
			 */

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

			// TODO: Actually implement the business logic finally. Yep. Still not done with that.
			
			
			/****** Use the service of GET /restapi/v2/accounts/{accountID}/envelopes?from_date={date}
			 * See the documentation at https://docs.docusign.com/esign/restapi/Envelopes/Envelopes/listStatusChanges/ *******/
			
			//Create the query params needed for invocation
			List<Pair> queryParams = new ArrayList<Pair>();
			queryParams.add(new Pair("from_date", MrSingletonState.getDate()));
			
			//create the header information needed for the call
			
			String retort = apiClient.invokeAPI("/v2/accounts/" + loginInfo.getLoginAccounts().get(0).getAccountId() + "/envelopes",
					"GET", queryParams, null, new HashMap<String, String>(), new HashMap<String, Object>(),
					null, "Accept", new String[0], new GenericType<String>(String.class));
			
			System.out.println(retort);
			
			
			
			
			
			
			return ResponseEntity.ok(code.toString());

		} catch (ApiException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@GetMapping("updateStart")
	public String updateDocusignTry3PartUno(@ApiParam("code") String code) {
		return "redirect:/update-docusign-records-request.html";
	}

}
