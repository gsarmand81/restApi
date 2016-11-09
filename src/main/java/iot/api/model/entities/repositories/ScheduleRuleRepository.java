package iot.api.model.entities.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.ScheduleRule;

@Transactional
public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Long> {
	
	List<ScheduleRule> findAll();

}
