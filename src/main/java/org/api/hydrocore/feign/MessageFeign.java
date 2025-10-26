package org.api.hydrocore.feign;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MessageFeign {

    public static JsonNode sendEmail(String from, String to, String subject, String text, String domain) throws UnirestException {
        String apiKey = System.getenv("MAILGUN_API_KEY");
        if (apiKey == null) {
            throw new RuntimeException("Variável de ambiente MAILGUN_API_KEY não definida");
        }

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", from)
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", text)
                .asJson();

        return request.getBody();
    }
}
