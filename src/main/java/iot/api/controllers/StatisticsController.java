package iot.api.controllers;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import iot.api.model.entities.ChartData;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.repositories.SensorEventRepository;


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
			
	@Autowired
	private SensorEventRepository sensorEventRepository;

} 