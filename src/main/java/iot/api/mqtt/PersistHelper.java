package iot.api.mqtt;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import iot.api.model.entities.Fridge;
import iot.api.model.entities.Sensor;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.repositories.SensorRepository;
import iot.api.model.entities.repositories.FridgeRepository;
import iot.api.model.entities.repositories.SensorEventRepository;


@Component
public class PersistHelper {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@Autowired
	private FridgeRepository fridgeRepository;

	@Autowired
	private SensorRepository sensorDao;

	@Autowired
	private SensorEventRepository sensorEventDao;
	
	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;

	@Value("${name.base.topic}")
	private String baseTopic;

	public void execute(String topic, MqttMessage message) {
		persist(topic,message);
		//TODO Execute Rules?
	}
	
	public void persist(String topic, MqttMessage message) {

		try {		
			//TODO Logica para depositar el mensaje de acuerdo al sensor
			Fridge fridge = fridgeRepository.findById(0l);

			//A0/A1/A2 etc
			Long typeSensor = mapSensor(topic);
			Sensor sensor = sensorDao.findById(typeSensor);			

			logger.info("TOPIC Sensor Helper: " + topic);
			SensorEvent sensorEvent = new SensorEvent(fridge,sensor);		
			sensorEvent.setTimestamp(new Date());
			sensorEvent.setValue(tranformData(topic,new String(message.getPayload())));						
			sensorEventDao.save(sensorEvent);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private String tranformData(String topic,String value) {

		//TODO Refactorizar esto.
		
		long id = mapSensor(topic);

		try {
			if (id == 0) {

//				String completeNameTopic = "test_topic/D12/value/set";
//
//				if (value.equals("1")) {
//					mqttClients.getMqttClient(completeNameTopic).mqttConnectNPublishNSubscribe("true");
//				} if (value.equals("0")){
//					mqttClients.getMqttClient(completeNameTopic).mqttConnectNPublishNSubscribe("false");
//				}

				return value;
			} else if (id == 1) {			
				double temp = (Double.parseDouble(value)/1024) * 5000 / 10;
				logger.info("Temperatura Interna: " + temp);
				return String.valueOf(temp);
			} else if (id == 2) {
				double temp = (Double.parseDouble(value)/1024) * 5000 / 10;
				logger.info("Temperatura Externa: " + temp);
				return String.valueOf(temp);
			} else if (id == 3) {
				long longValue = Long.parseLong(value);
				if (longValue < 10) {
					return "No pressure";
				} else if (longValue < 200) {
					return "Light touch";
				} else if (longValue < 500) {
					return "Light squeeze";
				} else if (longValue < 800) {
					return "Medium squeeze";
				} else {
					return "Big squeeze";
				}

			} else if (id == 4) {
				if (value.equals("true")) {
					return "1";
				} 
				if (value.equals("false")) {
					return "0";
				} 
				return value;
			} else if (id == 5) {
				if (value.equals("true")) {
					return "1";
				} 
				if (value.equals("false")) {
					return "0";
				} 
				return value;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}

		return "";
	}

	private long mapSensor(String topic) {
		//Topic: test_topic/D12/value/set baseTopic: test_topic

		//TODO Convert A0/A1 to sensorId, repensar esta parte es una redundancia 
		logger.info("Topic: " + topic + " baseTopic: " + baseTopic);
		String[] data = topic.split("/");
		String pin = data[1];

		logger.info("PIN: " + pin);

		if (pin.equals("D12")) {
			return 5l;
		} else if (pin.equals("D13")) {
			return 4l;
		} else if (pin.equals("D2")) {
			return 0l;
		} else if (pin.equals("A0")) {
			return 1l;
		} else if (pin.equals("A1")) {
			return 2l;
		} else if (pin.equals("A2")) {
			return 3l;
		}

		return Long.parseLong(topic.replace(baseTopic, ""));
	}


}
