package com.smkconsulting.resafigsms;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.smkconsulting.resafigsms.services.AccessTokenManager;
import com.smkconsulting.resafigsms.services.SmsManager;

@SpringBootApplication
public class ResafigsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResafigsmsApplication.class, args);

		AccessTokenManager accessTokenManager = new AccessTokenManager();
		String token =accessTokenManager.generateAccessToken("Basic N1ptZFdTY0NUM1VCZkg1SFEzZW0yNWdna3h0WElBbDA6a2s5NHpRZ2xZcVdCOGZiMQ==");
		String message = "";
		SmsManager smsManager = new SmsManager();
		JSONObject jsonObject = smsManager.sendMessage("+2250000", "+2250709768110", message, token);
		JSONObject jsonObject2 = smsManager.sendMessage("+2250000", "+2250584608700", message, token);
		JSONObject jsonObjectSmsBalance = smsManager.viewSmsBalance(token);
		JSONObject jsonObjectSmsUsage = smsManager.viewSmsUsage(token);
		JSONObject jsonObjectPurchaseHistory= smsManager.viewSmsPurchaseHistory(token);

		System.out.println(jsonObject);
		System.out.println(jsonObject2);
		System.out.println(jsonObjectPurchaseHistory);
		System.out.println(jsonObjectSmsBalance);
		System.out.println(jsonObjectSmsUsage);
	}

}
