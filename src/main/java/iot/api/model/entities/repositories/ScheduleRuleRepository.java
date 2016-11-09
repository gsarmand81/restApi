package iot.api.model.entities.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface ScheduleRuleRepository extends JpaRepository<ScheduleRuleRepository, Long> {
	
	List<ScheduleRuleRepository> findAll();

}
