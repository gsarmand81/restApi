package iot.api.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@Value("${name.base.topic}")
	private String baseTopic;

	//TODO Obtener todo esto de properties
	public long mapSensor(String topic) {

		//Topic: test_topic/D12/value/set baseTopic: test_topic

		//TODO Convert A0/A1 to sensorId, repensar esta parte es una redundancia 
		String[] data = topic.split("/");
		String pin = data[1];

		long value = -99l;
		
		if (pin.equals("D2")) {
			value = 0l;
		} else if (pin.equals("A0")) {
			value = 1l;
		} else if (pin.equals("A1")) {
			value = 2l;
		} else if (pin.equals("A2")) {
			value = 3l;
		} else if (pin.equals("D13")) {
			value = 4l;
		} else if (pin.equals("D12")) {
			value = 5l;
		} else if (pin.equals("D3")) {
			value = 6l;
		} else if (pin.equals("D8")) {
			value = 7l;
		} else if (pin.equals("A3")) {
			value = 8l;
		} else if (pin.equals("A4")) {
			value = 9l;
		}														

		logger.info("Topic: " + topic + " baseTopic: " + baseTopic + " Pin: " + pin + " Value: " + value);
		
		return value;
	}

	//TODO Cambiar a que regrese Double
	public String transformTemperature(String topic,String value) {

		long id = mapSensor(topic);

		if (id == 1 || id == 2) {			
			double temp = (Double.parseDouble(value)/1024) * 5000 / 10;
			logger.info("SensorId: " + id + " Temperatura : " + temp);
			return String.valueOf(temp);
		}

		return value;
	}

	public String transformPressure(String topic,String value) {

		long id = mapSensor(topic);

		if (id == 3) {
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
		}

		return value;
	}

	//TODO Cambiar a que regrese Int
	public String transformActuator(String topic,String value) {

		long id = mapSensor(topic);

		if (id == 4 || id == 5 || id == 6 || id == 7) {
			if (value.equals("true")) {
				return "1";
			} 
			if (value.equals("false")) {
				return "0";
			} 
			return value;
		}

		return value;

	}

	public String tranformGeneralData(String topic,String value) {

		value = transformTemperature(topic, value);
		value = transformActuator(topic, value);

		return value;
	}

	public String getPort(long sensorId) {

		if (sensorId == 0l) {
			return "D2";
		} else if (sensorId == 1l) {
			return "A0";
		} else if (sensorId == 2l) {
			return "A1";
		} else if (sensorId == 3l) {
			return "A2";
		} else if (sensorId == 4l) {
			return "D13";
		} else if (sensorId == 5l) {
			return "D12";
		} else if (sensorId == 6l) {
			return "D3";
		} else if (sensorId == 7l) {
			return "D8";
		} else if (sensorId == 8l) {
			return "A3";
		} else if (sensorId == 9l) {
			return "A4";
		}

		return  "";
	}

	public String getCompleteNameTopicPublish(long sensorId) {
		return baseTopic + "/" + getPort(sensorId) + "/value/set";
	}

	public String getCompleteNameTopicSubscribe(long sensorId) {
		return baseTopic + "/" + getPort(sensorId) + "/value/get";
	}

}
