package com.ds3c.tcc.ApiTcc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}") private String supabaseUrl;
    @Value("${supabase.bucket-name}") private String bucketName;
    @Value("${supabase.bucket-public}") private Boolean bucketPublic;

    private final RestClient restClient;

    public SupabaseStorageService(
            RestClient.Builder builder,
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.service-role-key}") String serviceRoleKey) {

        this.restClient = builder
                .baseUrl(supabaseUrl + "/storage/v1/object")
                .defaultHeader("Authorization", "Bearer " + serviceRoleKey)
                .defaultHeader("apikey", serviceRoleKey)
                .build();
    }

    public String uploadFile(MultipartFile file, String path) throws IOException {
        MediaType contentType = MediaType.parseMediaType(
                file.getContentType() == null ? "application/octet-stream" : file.getContentType()
        );

        String endpoint = String.format("/%s/%s", bucketName, path);

        try {
            restClient.put()
                    .uri(endpoint)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new IOException("Error uploading the file: " + e.getResponseBodyAsString(), e);
        }

        if (bucketPublic)
            return String.format("%s/storage/v1/object/public/%s/%s", supabaseUrl, bucketName, path);

        return supabaseUrl+"/storage/v1"+generateSignedUrl(path);
    }

    private String generateSignedUrl(String path) {
        String endpoint = String.format("/sign/%s/%s", bucketName, path);

        try {
            String response = restClient.post()
                    .uri(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"expiresIn\":3600}")
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);

            if (node.has("signedURL")) {
                return node.get("signedURL").asText();
            } else {
                throw new RuntimeException("Invalid response from Supabase: " + response);
            }
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Error generating signed URL: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error generating signed URL: " + e.getMessage(), e);
        }
    }
}
