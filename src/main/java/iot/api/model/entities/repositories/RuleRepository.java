package iot.api.model.entities.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.Rule;

@Transactional
public interface RuleRepository extends JpaRepository<Rule, Long> {


}