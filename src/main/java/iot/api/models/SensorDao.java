package iot.api.models;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;



@Transactional
public interface SensorDao extends JpaRepository<Sensor, Long> {



} // class UserDao