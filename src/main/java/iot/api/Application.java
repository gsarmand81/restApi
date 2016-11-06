package iot.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;

@SpringBootApplication
@Configuration
public class Application {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
	}

	@Value("${name.base.topic}")
	private String topic;

	@Value("${name.arduino.pins}")
	private String pins;

	@Bean
	public MqttPublishSubscribeUtilityStorage MqttPublishSubscribeUtilityStorage() {

		MqttPublishSubscribeUtilityStorage clientStorage = new MqttPublishSubscribeUtilityStorage();
		
		System.out.println("Valor properties: " + topic);

		String[] pin = pins.split(",");    	
		for (int i = 0; i < pin.length; i++) {
			System.out.println("PIN: " + pin[i]);
			clientStorage.addMqttClient(topic, pin[i]);
		}

		return clientStorage;

	}


}
