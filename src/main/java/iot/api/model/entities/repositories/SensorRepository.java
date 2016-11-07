package iot.api.model.entities.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.Sensor;



@Transactional
public interface SensorRepository extends JpaRepository<Sensor, Long> {

	Sensor findById(Long id);

}