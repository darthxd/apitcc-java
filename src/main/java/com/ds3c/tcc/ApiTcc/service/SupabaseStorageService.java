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
    @Value("${supabase.service-role-key}") private String serviceRoleKey;
    @Value("${supabase.bucket-name}") private String bucketName;
    @Value("${supabase.bucket-public}") private Boolean bucketPublic;

    private final RestClient restClient;

    public SupabaseStorageService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(supabaseUrl+"/storage/v1/object/"+bucketName)
                .defaultHeader("Authorization", "Bearer "+serviceRoleKey)
                .defaultHeader("apikey", serviceRoleKey)
                .build();
    }

    public String uploadFile(MultipartFile file, String path) throws IOException {
        MediaType contentType = MediaType.parseMediaType(file.getContentType() == null
        ? "application/octet-stream" : file.getContentType());

        try {
            restClient.put()
                    .uri(path)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new IOException("Error uploading the file. "+e);
        }

        if (bucketPublic)
            return supabaseUrl+"/storage/v1/object/"+bucketName+path;

        return generateSignedUrl(path);
    }

    private String generateSignedUrl(String path) {
        try {
            String response = restClient.post()
                    .uri(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"expiresIn\":3600}")
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);

            if (node.has("signedURL")) {
                return node.get("signedURL").asText();
            } else {
                throw new RuntimeException("Error generating the signed URL. "+response);
            }
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Error generating the signed URL. "+e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error generating the signed URL. "+e.getMessage());
        }
    }
}
