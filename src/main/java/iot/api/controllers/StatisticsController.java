package iot.api.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.model.entities.Fridge;
import iot.api.model.entities.Sensor;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.Store;
import iot.api.model.entities.repositories.SensorRepository;
import iot.api.model.entities.repositories.SensorEventRepository;
import iot.api.model.entities.repositories.StoreRepository;

@Controller
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@RequestMapping("/getStore")
	@ResponseBody
	public String getStore() {
		
		Store store = storeDao.findById(0l);
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
		storeDao.save(store);		

		return "Invoke Success!!";
	}

	@RequestMapping("/saveSensor")
	@ResponseBody
	public String saveSensor() {

		Sensor sensor1 = new Sensor();
		sensor1.setName("LIGHT");

		Sensor sensor2 = new Sensor();
		sensor2.setName("PRESSURE");

		sensorDao.save(new HashSet<Sensor>() {{
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
		storeDao.save(store);		
		
		Sensor sensor = new Sensor();
		sensor.setName("TEST");
		
		sensorDao.save(sensor);
		
		SensorEvent sensorEvent = new SensorEvent(fridge,sensor);
		sensorEvent.setTimestamp(new Date());
		sensorEvent.setValue("666");
		sensorEventDao.save(sensorEvent);
		
		return "Invoke Success!!";
	}

	
	@Autowired
	private StoreRepository storeDao;

	@Autowired
	private SensorRepository sensorDao;
	
	@Autowired
	private SensorEventRepository sensorEventDao;

} 