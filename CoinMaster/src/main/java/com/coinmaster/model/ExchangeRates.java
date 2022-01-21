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
    
    public static ExchangeRates getCurrentExchangeRates() {
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
            ExchangeRates exchangeRates = mapper.readValue(rootNode.get("data").toString(), ExchangeRates.class);
            System.out.println(exchangeRates);
            return exchangeRates;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
