package iot.api.models;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface StoreDao extends JpaRepository<Store, Long> {

	List<Store> findById(Long id);
	List<Store> findByName(String name);

} // class UserDao