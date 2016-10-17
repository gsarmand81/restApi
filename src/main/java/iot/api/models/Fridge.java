package iot.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "fridges")
public class Fridge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FRIDGE_ID")
	private long id;
	
	@NotNull
	@Column(name = "ARDUINO")
	private String arduino;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID")
	private Store store;

	public Fridge(String arduino) {
		this.arduino = arduino;
	}

	public Fridge(String arduino, Store store) {
		this.arduino = arduino;
		this.store = store;
	}

	public long getIdFridge() {
		return id;
	}

	public void setIdFridge(long idFridge) {
		this.id = idFridge;
	}
	
	public String getArduino() {
		return arduino;
	}

	public void setArduino(String arduino) {
		this.arduino = arduino;
	}
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}
