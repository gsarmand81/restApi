package iot.api.utility;

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
import iot.api.rule.properties.RulesExecutor;
import iot.api.model.entities.repositories.FridgeRepository;
import iot.api.model.entities.repositories.SensorEventRepository;


@Component
public class ExecuteHelper {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@Autowired
	private FridgeRepository fridgeRepository;

	@Autowired
	private SensorRepository sensorDao;

	@Autowired
	private SensorEventRepository sensorEventDao;

	@Autowired
	private RulesExecutor executeRules;

	@Autowired
	private Utility utility;

	@Value("${execute.rules}")
	private boolean flagRules;
	
	public void execute(String topic, MqttMessage message) {
		
		Long sensorId = utility.mapSensor(topic);		
		String value = utility.tranformGeneralData(topic, new String(message.getPayload()));
		
		logger.info("Values to execute SensorId: " + sensorId + " Value: " + value);
		
		persist(sensorId,value);
		if (flagRules)
			executeRules.executeRules(sensorId,value);
		 		
	}

	public void persist(long sensorId, String value) {

		try {		
			//TODO Logica para depositar el mensaje de acuerdo al sensor
			Fridge fridge = fridgeRepository.findById(0l);
			Sensor sensor = sensorDao.findById(sensorId);			
			
			SensorEvent sensorEvent = new SensorEvent(fridge,sensor);		
			sensorEvent.setTimestamp(new Date());
			//TODO Cambiar campo de tabla a numero (Double,Long,Integer)
			sensorEvent.setValue(value);						
			sensorEventDao.save(sensorEvent);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
