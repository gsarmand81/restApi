package iot.api.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.models.Fridge;
import iot.api.models.Sensor;
import iot.api.models.SensorDao;
import iot.api.models.SensorEvent;
import iot.api.models.SensorEventDao;
import iot.api.models.Store;
import iot.api.models.StoreDao;

@Controller
public class DataController {

	// ------------------------
	// PUBLIC METHODS
	// ------------------------
	
	@RequestMapping("/getStore")
	@ResponseBody
	public String getStore() {
		
		List<Store> stores = storeDao.findById(0l);
		for (Iterator iterator = stores.iterator(); iterator.hasNext();) {
			Store store = (Store) iterator.next();
			System.out.println("Store: " + store.getName());
		} 
		
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

		store.setFridges(fridges);
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

		store.setFridges(fridges);
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
	private StoreDao storeDao;

	@Autowired
	private SensorDao sensorDao;
	
	@Autowired
	private SensorEventDao sensorEventDao;

} 