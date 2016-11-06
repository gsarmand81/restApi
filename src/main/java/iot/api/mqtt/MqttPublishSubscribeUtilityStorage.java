package iot.api.mqtt;

import java.util.Hashtable;

public class MqttPublishSubscribeUtilityStorage {

	private Hashtable<String,MqttPublishSubscribeUtility> mqttClients = new Hashtable<String,MqttPublishSubscribeUtility>();

	public void addMqttClient(String topic,String pin) {
		mqttClients.put(topic + pin,new MqttPublishSubscribeUtility(topic + pin));
	}
	
	public MqttPublishSubscribeUtility getMqttClient(String completeNameTopic) {
		return mqttClients.get(completeNameTopic);
	}
}
