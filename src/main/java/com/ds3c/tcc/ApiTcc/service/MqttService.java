package com.ds3c.tcc.ApiTcc.service;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class MqttService implements MqttCallback {
    private final MqttClient client;

    private BiConsumer<String, String> responseHandler;

    public MqttService(
            @Value("${mqtt.broker.username}") String username,
            @Value("${mqtt.broker.password}") String password,
            @Value("${mqtt.broker.uri}") String uri
            ) throws MqttException {
        client = new MqttClient(uri, MqttClient.generateClientId());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.setCallback(this);
        client.connect(options);

        client.subscribe("api/response/#");
        client.subscribe("api/event/#");

        System.out.println("Connected to the MQTT Broker");
    }

    public void publish(String topic, String message) throws MqttException {
        System.out.println("Publishing in "+topic+": "+message);
        client.publish(topic, new MqttMessage(message.getBytes()));
    }

    public void setResponseHandler(BiConsumer<String, String> handler) {
        this.responseHandler = handler;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String msg = message.toString();
        System.out.println("Received ["+topic+"]: "+msg);
        if (responseHandler != null && topic.startsWith("api/response/")) {
            responseHandler.accept(topic, msg);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection with the MQTT lost: "+cause.getMessage());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {}
}
