package iot.api.models;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;



@Transactional
public interface SensorDao extends JpaRepository<Sensor, Long> {

	List<Sensor> findById(Long id);


} // class UserDao