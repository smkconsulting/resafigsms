package com.smkconsulting.resafigsms.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class SmsManager {
    private final String urlSmsBalance = "https://api.orange.com/sms/admin/v1/contracts";
    private final String urlSmsUsage = "https://api.orange.com/sms/admin/v1/statistics";
    private final String urlSmsPurchaseHistory = "https://api.orange.com/sms/admin/v1/purchaseorders";

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = null;

    public JSONObject sendMessage(String senderAddress, String address, String message, String token){

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.orange.com/smsmessaging/v1/outbound/tel:"+senderAddress+"/requests"))
        .POST(HttpRequest.BodyPublishers.ofString("{\"outboundSMSMessageRequest\":{ \n"
                +"      \"address\": \"tel:"+address+"\", \n"
                +"      \"senderAddress\":\"tel:"+senderAddress+"\",\n"
                +"      \"outboundSMSTextMessage\":{ \n"
                +"      \"message\": \""+message+"\" \n"
                +"      } \n"
                +"      } \n"
                +"      }"))
        .header("Authorization", "Bearer "+token)
        .header("Content-Type", "application/json")
        .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            
            e.printStackTrace();
        } catch (InterruptedException e) {
           
            e.printStackTrace();
        }

        return new JSONObject(response.body().toString());
    }

    public JSONObject viewSmsBalance(String token){
        return getResponse(urlSmsBalance, token);
    }

    public JSONObject viewSmsUsage(String token){
        return getResponse(urlSmsUsage, token);
    }
  

    public JSONObject viewSmsPurchaseHistory(String token){
        return getResponse(urlSmsPurchaseHistory,token);
    }

    private JSONObject getResponse(String url, String token) {
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url)).GET()
                    .header("Authorization","Bearer "+token)
                    .build();
            
        try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return new JSONObject(response.body().toString());
    }

}
