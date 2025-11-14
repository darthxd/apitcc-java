package com.ds3c.tcc.ApiTcc.service;

import com.ds3c.tcc.ApiTcc.model.Student;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class BiometryService {
    private final MqttService mqttService;
    private final StudentService studentService;

    private final ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();

    public BiometryService(
            MqttService mqttService,
            StudentService studentService) {
        this.mqttService = mqttService;
        this.studentService = studentService;
        mqttService.setResponseHandler(this::handleMqttResponse);
    }

    // Enroll fingerprint
    public Boolean enroll(Long studentId) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("studentId", studentId);

            CompletableFuture<String> future = new CompletableFuture<>();
            pendingResponses.put("api/response/enroll", future);

            mqttService.publish("esp32/command/enroll", payload.toString());

            String response = future.get(15, TimeUnit.SECONDS);
            JSONObject json = new JSONObject(response);
            return "ok".equalsIgnoreCase(json.optString("status"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pendingResponses.remove("api/response/enroll");
        }
    }

    // Read fingerprint
    public Optional<Student> read() {
        try {
            CompletableFuture<String> future = new CompletableFuture<>();
            pendingResponses.put("api/response/read", future);

            mqttService.publish("esp32/command/read", "{}");

            String response = future.get(15, TimeUnit.SECONDS);

            JSONObject json = new JSONObject(response);

            if (json.has("studentId")) {
                Integer rm = json.getInt("studentId");
                Student student = studentService.findByRm(rm);
                return Optional.of(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pendingResponses.remove("api/response/read");
        }
        return Optional.empty();
    }

    // Reset sensor
    public Boolean reset() {
        try {
            CompletableFuture<String> future = new CompletableFuture<>();
            pendingResponses.put("api/response/reset", future);

            mqttService.publish("esp32/command/reset", "{}");

            String response = future.get(15, TimeUnit.SECONDS);

            JSONObject json = new JSONObject(response);
            return "ok".equalsIgnoreCase(json.optString("status"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pendingResponses.remove("api/response/reset");
        }
    }

    // Delete fingerprint
    public Boolean delete(Long studentId) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("studentId", studentId);

            CompletableFuture<String> future = new CompletableFuture<>();
            pendingResponses.put("api/response/delete", future);

            mqttService.publish("esp32/command/delete", payload.toString());

            String response = future.get(15, TimeUnit.SECONDS);
            JSONObject json = new JSONObject(response);
            return "deleted".equalsIgnoreCase(json.optString("status"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pendingResponses.remove("api/response/delete");
        }
    }

    private void handleMqttResponse(String topic, String message) {
        CompletableFuture<String> future = pendingResponses.get(topic);
        if (future != null) {
            future.complete(message);
        }
    }
}
