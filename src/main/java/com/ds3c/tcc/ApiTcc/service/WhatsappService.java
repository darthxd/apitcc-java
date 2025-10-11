package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.exception.WhatsappMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsappService {

    private final RestClient restClient;
    private final String phoneNumberId;

    public WhatsappService(
            RestClient.Builder builder,
            @Value("${whatsapp.api.url}") String apiUrl,
            @Value("${whatsapp.api.phone-number-id}") String phoneNumberId,
            @Value("${whatsapp.api.token}") String accessToken
    ) {
        this.restClient = builder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
        this.phoneNumberId = phoneNumberId;
    }

    public void sendMessage(String number, String message) {
        Map<String, Object> text = new HashMap<>();
        text.put("body", message);

        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", "55"+number);
        body.put("type", "text");
        body.put("text", text);

        try {
            String response = restClient.post()
                    .uri("/"+phoneNumberId+"/messages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            System.out.printf("\nMessage sent: %s", response);
        } catch (Exception e) {
            throw new WhatsappMessageException(e.getMessage());
        }
    }
}
