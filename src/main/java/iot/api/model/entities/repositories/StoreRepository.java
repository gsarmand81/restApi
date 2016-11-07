package iot.api.model.entities.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import iot.api.model.entities.Store;


@Transactional
public interface StoreRepository extends JpaRepository<Store, Long> {

	Store findById(Long id);
	List<Store> findByName(String name);
	List<Store> findAll();
		
}