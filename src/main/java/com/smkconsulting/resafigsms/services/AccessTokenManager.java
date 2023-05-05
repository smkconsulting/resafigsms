package com.smkconsulting.resafigsms.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class AccessTokenManager {
    public final String tokenUrl = "https://api.orange.com/oauth/v3/token";

    // Generate from Authorization Header
    public String generateAccessToken(String authorizationHeader){
        String token="";
        OAuthJSONAccessTokenResponse oAuthJSONAccessTokenResponse = getOAuthJSONAccessTokenResponse(authorizationHeader);
        if(oAuthJSONAccessTokenResponse.getResponseCode() == 200){
            token = oAuthJSONAccessTokenResponse.getAccessToken();
        }
        return token;
    }

    public String generateAccessToken( String clientID,  String sercretID){
        String token ="";
        OAuthJSONAccessTokenResponse oAuthJSONAccessTokenResponse = getOAuthJSONAccessTokenResponse(generateBase64Encoded(clientID,sercretID));
        if(oAuthJSONAccessTokenResponse.getResponseCode() == 200){
            token = oAuthJSONAccessTokenResponse.getAccessToken();
        }
        return token;

    }

    private String generateBase64Encoded(String clientID, String sercretID) {
        return "Basic "+Base64.getEncoder().encodeToString((clientID+":"+sercretID).getBytes(StandardCharsets.UTF_8));
    }

    private OAuthJSONAccessTokenResponse getOAuthJSONAccessTokenResponse(String authorizationHeader) {
        OAuthClient client = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest request = null;
        OAuthJSONAccessTokenResponse accessTokenResponse = null;
        try {
            request = OAuthClientRequest.tokenLocation(tokenUrl).setGrantType(GrantType.CLIENT_CREDENTIALS).buildBodyMessage();

            //authorizationHeader must include Basic
            request.addHeader("Authorization", authorizationHeader);
            request.addHeader("Accept","application/json");
        } catch (OAuthSystemException e) {
            e.printStackTrace();
           
        }

        try {
            accessTokenResponse = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class);
        } catch (OAuthSystemException | OAuthProblemException e) {
            e.printStackTrace();
           
        }
        
        return accessTokenResponse;
    }
}
