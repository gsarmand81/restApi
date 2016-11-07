package iot.api.model.entities.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.Fridge;

@Transactional
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

	Fridge findById(Long id);

}