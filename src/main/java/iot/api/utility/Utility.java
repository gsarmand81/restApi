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
	
	public long mapSensor(String topic) {
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
	
	public String tranformData(String topic,String value) {

		long id = mapSensor(topic);

		try {
			if (id == 0) {
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

}
