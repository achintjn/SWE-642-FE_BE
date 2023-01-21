package com.project.rest.webservices.restfuwebservices.spotifyweb;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

@RestController
@RequestMapping("/api")
public class SpotifyAuthContoller {
	
	private static final String clientId = ""; 
	private static final String clientSecret = "";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code");
	private static String code = "";
	
	 private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			    .setClientId(clientId)
			    .setClientSecret(clientSecret)
			    .setRedirectUri(redirectUri)
			    .build();
	//private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
//			    .build();

	@GetMapping("/login")
	public String spotifyLogin() {
		AuthorizationCodeUriRequest build = spotifyApi.authorizationCodeUri()
				.scope("user-read-private, user-read-email, user-top-read")
				.show_dialog(true)
				.build();
		final URI uri = build.execute();
		return uri.toString();
	}
	
	@GetMapping("/get-user-code")
	public String getCodeSpotify(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
		code = userCode;
		
		final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
				    .build();
		
		try {
		      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

		      // Set access and refresh token for further "spotifyApi" object usage
		      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

		      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    } catch (org.apache.hc.core5.http.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		response.sendRedirect("http://localhost:8080/api/get-top-artists");
		
		return spotifyApi.getAccessToken();
	}
	
	@GetMapping("/get-top-artists")
	public Artist[] getTopArtists() {
		GetUsersTopArtistsRequest getTopArtistReq = spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).offset(5).build();
		
		try {
			return getTopArtistReq.execute().getItems();
		} catch (org.apache.hc.core5.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
		
}
