package iot.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import iot.api.mqtt.MqttPublishSubscribeUtility;



@Controller
public class MqttController {

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	@RequestMapping("/mqtt")
	@ResponseBody
	public String testMqtt() {

		MqttPublishSubscribeUtility mqttPublishSubscribeUtility = new MqttPublishSubscribeUtility();

		JsonObject payload = new JsonObject();
		payload.addProperty("Id", "Id");
        payload.addProperty("blinkStatus", "blinkStatus");
        payload.addProperty("dateTimeStamp",  "dateTimeStamp");
        payload.addProperty("blinkCounter",  "blinkCounter");
		
        
		//Connect to MQTT Broker, Publish the message to topic 
		mqttPublishSubscribeUtility.mqttConnectNPublishNSubscribe(payload);
		
		return "Invoke Success!!";
	}

} 