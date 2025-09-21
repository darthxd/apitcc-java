package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.dto.Student.BiometryResponseDTO;
import com.ds3c.tcc.ApiTcc.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class BiometryService {
    private final RestClient restClient;
    private final StudentService studentService;

    public BiometryService(
            RestClient.Builder builder,
            StudentService studentService,
            @Value("${arduino.base-url}") String arduinoBaseUrl) {
        this.restClient = builder
                .baseUrl(arduinoBaseUrl)
                .build();
        this.studentService = studentService;
    }

    public boolean enrollFingerPrint(Long studentId) {
        ResponseEntity response = restClient.post()
                .uri("/api/fingerprint/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"studentId\":" + studentId + "}")
                .retrieve()
                .toBodilessEntity();

        if (response.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        return true;
    }

    public boolean resetSensor() {
        ResponseEntity response = restClient.post()
                .uri("/api/fingerprint/reset")
                .retrieve()
                .toBodilessEntity();
        if (response.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        return true;
    }

    public Optional<Student> readFingerprint() {
         ResponseEntity<BiometryResponseDTO> response = restClient.post()
                .uri("/api/fingerprint/read")
                .retrieve()
                .toEntity(BiometryResponseDTO.class);
         if (response.getStatusCode() != HttpStatus.OK) {
             return Optional.empty();
         }
         Student student = studentService.getStudentById(
                 response.getBody().getStudentId());
         return Optional.of(student);
    }
}
