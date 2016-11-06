package iot.api.mqtt;

import java.util.Date;
import java.util.List;


import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import iot.api.models.Fridge;
import iot.api.models.Sensor;
import iot.api.models.SensorDao;
import iot.api.models.SensorEvent;
import iot.api.models.SensorEventDao;
import iot.api.models.Store;
import iot.api.models.StoreDao;


@Component
public class PersistHelper {

	@Autowired
	private StoreDao storeDao;

	@Autowired
	private SensorDao sensorDao;

	@Autowired
	private SensorEventDao sensorEventDao;

	@Value("${name.base.topic}")
	private String baseTopic;


	public void persist(String topic, MqttMessage message) {

		try {		
			//TODO Logica para depositar el mensaje de acuerdo al sensor
			List<Store> stores = storeDao.findById(0l);
			Store store = stores.iterator().next();
			Fridge fridge = store.getFridges().iterator().next();

			//A0/A1/A2 etc
			Long typeSensor = mapSensor(topic);
			List<Sensor> sensors = sensorDao.findById(typeSensor);
			Sensor sensor = sensors.iterator().next();

			System.out.println("TOPIC Sensor Helper: " + topic);
			SensorEvent sensorEvent = new SensorEvent(fridge,sensor);		
			sensorEvent.setTimestamp(new Date());
			sensorEvent.setValue(new String(message.getPayload()));						
			sensorEventDao.save(sensorEvent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private long mapSensor(String topic) {
		//Topic: test_topic/D12/value/set baseTopic: test_topic

		//TODO Convert A0/A1 to sensorId, repensar esta parte es una redundancia 
		System.out.println("Topic: " + topic + " baseTopic: " + baseTopic);
		String[] data = topic.split("/");
		String pin = data[1];
		
		System.out.println("PIN: " + pin);
		
		if (pin.equals("D12")) {
			return 5l;
		} else if (pin.equals("D13")) {
			return 4l;
		} else if (pin.equals("A3")) {
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
