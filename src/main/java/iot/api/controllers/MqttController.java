package iot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;

@Controller
public class MqttController {

	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;
	
	@RequestMapping("/mqtt")
	@ResponseBody
	public String testMqtt(@RequestParam(value="topic") String completeNameTopic,
			@RequestParam(value="message") String message) {

		mqttClients.getMqttClient(completeNameTopic).mqttConnectNPublishNSubscribe(message);

		return "Invoke Success!!";
	}

} 