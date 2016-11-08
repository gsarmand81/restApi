package iot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;
import iot.api.rule.properties.RulesProperties;


@Configuration
@EnableConfigurationProperties
@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");
	
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
		
		logger.info("Properties Value [topic]: " + topic );
		logger.info("Properties Value [pins]: " + pins);

		String[] pin = pins.split(",");    	
		for (int i = 0; i < pin.length; i++) {
			clientStorage.addMqttClient(topic, pin[i]);
		}

		return clientStorage;

	}


}
