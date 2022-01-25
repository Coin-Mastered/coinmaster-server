package com.coinmaster.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class ExchangeRates {
	@JsonProperty("currency")
    private String currency;

    @JsonProperty("rates")
    private Map<String, Double> rates;
    
    private ExchangeRates() {}
    
    private static ExchangeRates currentExchangeRates;
    
    public static ExchangeRates getCurrentExchangeRates() {
    	return currentExchangeRates;
    }
    
    public static double getUserValue(User u) {
    	return u.getWallets().stream().mapToDouble(w -> {
    		double value = w.getAmount() / currentExchangeRates.rates.get(w.getAssetName()).doubleValue();
    		System.out.println("User: " + w.getUser().getUsername() + " Asset: " + w.getAssetName() + " Value: $" + value);
    		return value;
    	}).sum();
    }

    public static int compareUserValue(User l, User r) {
    	Double lValue = getUserValue(l);
    	Double rValue = getUserValue(r);
    	System.out.println(r.getUsername() + ": " + rValue);
    	System.out.println(l.getUsername() + ": " + rValue);
    	if(lValue < rValue) {
    		return 1;
    	}
    	else if(rValue < lValue) {
    		return -1;
    	}
	    return 0;
	}
    
    public static void updateExchangeRates() {
    	StringBuffer content = new StringBuffer();
        try {
            URL url = new URL("https://api.coinbase.com/v2/exchange-rates");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            
            int status = con.getResponseCode();
            System.out.println(status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(content.toString());
            currentExchangeRates = mapper.readValue(rootNode.get("data").toString(), ExchangeRates.class);
            System.out.println(currentExchangeRates);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
