package iot.api.model.entities.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.SensorEvent;


@Transactional
public interface SensorEventRepository extends JpaRepository<SensorEvent, Long> {
	
	  SensorEvent findTop1BySensorIdAndFridgeIdOrderByTimestampDesc(long sensor_id, long fridge_id);

	  List<SensorEvent> findByFridgeIdAndSensorIdOrderByTimestampDesc(long fridge_id,long sensor_id);
	  
	  List<SensorEvent> findByFridgeIdAndSensorIdOrderByTimestampAsc(long fridge_id,long sensor_id);

	  SensorEvent findTop1ByFridgeIdAndSensorIdOrderByTimestampDesc(long fridge_id,long sensor_id);

	  
	  //http://docs.spring.io/spring-data/jpa/docs/1.4.1.RELEASE/reference/html/jpa.repositories.html
}