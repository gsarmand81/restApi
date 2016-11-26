package iot.api.utility;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

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
import iot.api.rule.RulesExecutor;
import iot.api.model.entities.repositories.FridgeRepository;
import iot.api.model.entities.repositories.SensorEventRepository;


@Component
public class ExecuteHelper {

	private static final Logger logger_out = LoggerFactory.getLogger("sys.out.log");

	private static final Logger logger_stat = LoggerFactory.getLogger("sys.out.stat");

	@Autowired
	private FridgeRepository fridgeRepository;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private SensorEventRepository sensorEventRepository;

	@Autowired
	private RulesExecutor executeRules;

	@Autowired
	private Utility utility;

	@Value("${execute.rules}")
	private boolean flagRules;

	public void execute(String topic, MqttMessage message) {

		Long sensorId = utility.mapSensor(topic);		
		String value = utility.tranformGeneralData(topic, new String(message.getPayload()));

		logger_out.info("Values to execute SensorId: " + sensorId + " Value: " + value);

		persist(sensorId,value);

		if (flagRules)
			executeRules.executeRules(sensorId,value);

	}

	public void persist(long sensorId, String value) {

		try {		
			//TODO Logica para depositar el mensaje de acuerdo al sensor
			Fridge fridge = fridgeRepository.findById(0l);
			Sensor sensor = sensorRepository.findById(sensorId);			

			SensorEvent sensorEvent = new SensorEvent(fridge,sensor);		
			sensorEvent.setTimestamp(new Date());
			//TODO Cambiar campo de tabla a numero (Double,Long,Integer)
			sensorEvent.setValue(value);						
			sensorEventRepository.save(sensorEvent);

		} catch (Exception e) {
			logger_out.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public long statisticsPower(long fridgeId) {

		List<SensorEvent> thermostatData = sensorEventRepository.
				findByFridgeIdAndSensorIdOrderByTimestampAsc(fridgeId,4l);
		List<SensorEvent> lightData = sensorEventRepository.
				findByFridgeIdAndSensorIdOrderByTimestampAsc(fridgeId,5l);
		
		logger_stat.info("Calculate Thermostat");
		long totalThermostat = calculateData(thermostatData);
		logger_stat.info("Calculate Lightt");
		long totalLightData = calculateData(lightData);
		
		//TODO Para sacar el consumo total tengo divir el tiempo entre 3,600,000 para convertilo en hora
		// y multiplicarlo por Kw respectivo para termostato y luz
		long totalStatistics = totalThermostat + totalLightData;
		logger_stat.info("totalStatistics: " + totalStatistics);
		
		return totalStatistics;

	}


	private long calculateData(List<SensorEvent> data) {

		ListIterator<SensorEvent> iterator = data.listIterator();
		
		long total = 0;

		while (iterator.hasNext()) {

			SensorEvent next = iterator.next();
			int previousIndex = iterator.previousIndex() - 1;

			if (previousIndex >= 0) {

				SensorEvent previous = data.get(previousIndex);

				logger_stat.info("----------------------------------------------------------------");
				logger_stat.info("Next[Id]: " + next.getId() + " Next[TimeStamp]: " + 
						next.getTimestamp() + " Next[Value]: " + next.getValue());			
				logger_stat.info("PreviousIndex: " + previousIndex);			 
				logger_stat.info("Previous[Id]: " + previous.getId() + " Previous[TimeStamp]: " + 
						previous.getTimestamp() + " Previous[Value]: " + previous.getValue());					

				//Ontengo la diferencia en milisegundos.
				long diff = next.getTimestamp().getTime() - previous.getTimestamp().getTime();
				logger_stat.info("Diff: " + diff + " [ms]");

				//	   Next 	Previous 	Suma
				//     1		1			Si		 	
				//     0		1			Si
				//	   1		0			No
				//     0		0			No

				
				if (next.getValue().equals("1") && previous.getValue().equals("1")) {
					total = total + diff;
				} else if (next.getValue().equals("0") && previous.getValue().equals("1")) {
					total = total + diff;
				}

				logger_stat.info("Total: " + total + " [ms]");

				logger_stat.info("----------------------------------------------------------------");

			}
		}

		return total;
	}
		
}


