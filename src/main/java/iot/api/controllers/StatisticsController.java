package iot.api.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.api.model.entities.ChartData;
import iot.api.model.entities.Fridge;
import iot.api.model.entities.Sensor;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.Store;
import iot.api.model.entities.repositories.SensorRepository;
import iot.api.model.entities.repositories.SensorEventRepository;
import iot.api.model.entities.repositories.StoreRepository;

@RestController
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");


	@RequestMapping("/getChartData")
	public @ResponseBody List<ChartData> login(@RequestParam(value="fridge_id") long fridge_id,
			@RequestParam(value="sensor_id") long sensor_id) {
			
		List<SensorEvent> events = sensorEventRepository.
				findByFridgeIdAndSensorIdOrderByTimestampDesc(fridge_id,sensor_id);		
		List<ChartData> allData = new ArrayList<ChartData>();
		
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			SensorEvent sensorEvent = (SensorEvent) iterator.next();
			ChartData chartData = new ChartData();
			chartData.setX(sensorEvent.getTimestamp().toString());
			chartData.setY(sensorEvent.getValue().toString());
			allData.add(chartData);
		}
		
		logger.info("Size: " + allData.size());
		
		return allData;
	}	
	
	@RequestMapping("/getStore")
	@ResponseBody
	public String getStore() {
		
		Store store = storeRepository.findById(0l);
		return "Exit!";
	}


	@RequestMapping("/saveStore")
	@ResponseBody
	public String saveStore() {

		Store store = new Store();
		store.setName("Tienda_1");
		store.setAddress("Address_1");
		store.setRaspberry("RaspBerry_1");

		Fridge fridge = new Fridge("Arduino_1",store);		

		Set fridges = new HashSet<Fridge>(){{
			add(fridge);
		}};

//		store.setFridges(fridges);
		storeRepository.save(store);		

		return "Invoke Success!!";
	}

	@RequestMapping("/saveSensor")
	@ResponseBody
	public String saveSensor() {

		Sensor sensor1 = new Sensor();
		sensor1.setName("LIGHT");

		Sensor sensor2 = new Sensor();
		sensor2.setName("PRESSURE");

		sensorRepository.save(new HashSet<Sensor>() {{
			add(sensor1);
			add(sensor2);
		}});

		return "Invoke Success!!";
	}

	@RequestMapping("/saveEvent")
	@ResponseBody
	public String saveEvent() {

		Store store = new Store();
		store.setName("Tienda_1");
		store.setAddress("Address_1");
		store.setRaspberry("RaspBerry_1");

		Fridge fridge = new Fridge("Arduino_1",store);		

		Set fridges = new HashSet<Fridge>(){{
			add(fridge);
		}};

//		store.setFridges(fridges);
		storeRepository.save(store);		
		
		Sensor sensor = new Sensor();
		sensor.setName("TEST");
		
		sensorRepository.save(sensor);
		
		SensorEvent sensorEvent = new SensorEvent(fridge,sensor);
		sensorEvent.setTimestamp(new Date());
		sensorEvent.setValue("666");
		sensorEventRepository.save(sensorEvent);
		
		return "Invoke Success!!";
	}

	
	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private SensorEventRepository sensorEventRepository;

} 