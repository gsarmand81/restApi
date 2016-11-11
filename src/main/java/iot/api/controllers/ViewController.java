package iot.api.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iot.api.model.entities.Fridge;
import iot.api.model.entities.Sensor;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.Store;
import iot.api.model.entities.repositories.FridgeRepository;
import iot.api.model.entities.repositories.SensorEventRepository;
import iot.api.model.entities.repositories.SensorRepository;
import iot.api.model.entities.repositories.StoreRepository;
import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;
import iot.api.utility.ExecuteHelper;

@Controller
public class ViewController {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private FridgeRepository fridgeRepository;

	@Autowired
	private SensorEventRepository sensorEventRepository;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;
	
	@Autowired
	private ExecuteHelper executeHelper;

	@RequestMapping(value = {"/","/store"})
	public String store(Model model) {   
		
		List<Store> stores = storeRepository.findAll();
		
		model.addAttribute("stores", stores);
		
		return "store";
	}

	@RequestMapping("/fridges")
	public String fridges(@RequestParam(value="store_id") long store_id,Model model) {

		Store store = storeRepository.findById(store_id);
		List<Fridge> fridges = store.getFridges();
		
		completeFridgeData(fridges);
			
		model.addAttribute("store", store);
		model.addAttribute("fridges", fridges);
		
		return "fridges";
	}


	@RequestMapping("/modifyFridge")
	public String modifyFridge(@RequestParam(value="store_id") long store_id,
			@RequestParam(value="fridge_id") long fridge_id,
			@RequestParam(value="sensor_id") long sensor_id, Model model) {

		Store store = storeRepository.findById(store_id);
		Fridge fridge = fridgeRepository.findById(fridge_id);
		Sensor sensor = sensorRepository.findById(sensor_id);

		model.addAttribute("store", store);
		model.addAttribute("fridge", fridge);
		model.addAttribute("sensor", sensor);
		
		return "modifyFridge";
	}


	@RequestMapping("/modifyParamter")
	public String send(@RequestParam(value="store_id") long store_id,
			@RequestParam(value="fridge_id") long fridge_id, @RequestParam(value="sensor_id") long sensor_id,
			@RequestParam(value="sensor_value") String sensor_value, Model model) {

		//TODO Poner esta logica en un lugar adecuado
		//------------------------------------------------------
		String completeNameTopic = "";

		if (sensor_id == 5 ) {
			completeNameTopic = "test_topic/D12/value/set"; 
		} else if (sensor_id == 4) {
			completeNameTopic = "test_topic/D13/value/set";
		}

		mqttClients.getMqttClient(completeNameTopic).mqttConnectNPublishNSubscribe(sensor_value);

		try {
			Thread.sleep(1000l);
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}	
		
		//Aqui validar horario de entrada ScheduleRule
		//------------------------------------------------------
		
		Store store = storeRepository.findById(store_id);

		List<Fridge> fridges = store.getFridges();
		
		completeFridgeData(fridges);

		model.addAttribute("store", store);
		model.addAttribute("fridges", fridges);
		
		return "fridges";
	}

	@RequestMapping("/rules")
	public String send(@RequestParam(value="store_id") long store_id,
			@RequestParam(value="fridge_id") long fridge_id,  Model model) {
		
		//TODO Agregar logica de reglas.
		List<Store> stores = storeRepository.findAll();
		
		model.addAttribute("stores", stores);
		
		return "rules";		
	}
	
	//TODO Poner esta logica en un lugar adecuado
	private List<SensorEvent> getLastDataByFridge(Fridge fridge) {

		List<SensorEvent> events = new ArrayList<SensorEvent>();

		SensorEvent lastPresenceEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(0l,fridge.getId());
		SensorEvent lastInternalTempEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(1l,fridge.getId());
		SensorEvent lastExternalTempEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(2l,fridge.getId());
		SensorEvent lastWeightEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(3l,fridge.getId());
		SensorEvent lastThermostatEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(4l,fridge.getId());
		SensorEvent lastLightEvent = sensorEventRepository.
				findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(5l,fridge.getId());

		if (lastPresenceEvent != null)
			events.add(lastPresenceEvent);
		if (lastInternalTempEvent != null)
			events.add(lastInternalTempEvent);
		if (lastExternalTempEvent != null)
			events.add(lastExternalTempEvent);
		if (lastWeightEvent != null)
			events.add(lastWeightEvent);
		if (lastThermostatEvent != null)
			events.add(lastThermostatEvent);
		if (lastLightEvent != null)		
			events.add(lastLightEvent);				

		return events;

	}

	private void completeFridgeData(List <Fridge> fridges){

		//By reference add last data
		List<SensorEvent> events;

		//GET last data for each fridge.
		for (Iterator iterator = fridges.iterator(); iterator.hasNext();) {
			Fridge fridge = (Fridge) iterator.next();
			
			events = getLastDataByFridge(fridge);
			long consume = executeHelper.statisticsPower(fridge.getId());
			
			fridge.setLastData(events);
			fridge.setConsume(consume);
		}

	}

}