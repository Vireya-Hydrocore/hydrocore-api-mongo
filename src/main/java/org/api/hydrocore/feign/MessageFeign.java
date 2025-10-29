package org.api.hydrocore.feign;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class MessageFeign {

    public static String sendEmail(String from, String to, String subject, String text, String domain) throws UnirestException {
        String apiKey = System.getenv("MAILGUN_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("Variável de ambiente MAILGUN_API_KEY não definida");
        }

        HttpResponse<String> response = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", from)
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", text)
                .asString();

        String body = response.getBody();

        if (body != null && body.startsWith("{")) {
            JSONObject json = new JSONObject(body);
            return json.optString("message", body);
        } else {
            return body;
        }
    }
}
