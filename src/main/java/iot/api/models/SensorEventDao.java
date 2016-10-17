package iot.api.models;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;



@Transactional
public interface SensorEventDao extends JpaRepository<SensorEvent, Long> {



} // class UserDao