package iot.api.models;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;



@Transactional
public interface StoreDao extends JpaRepository<Store, Long> {



} // class UserDao