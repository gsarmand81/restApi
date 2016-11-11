package iot.api.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "fridges")
public class Fridge {

	@Id	
	@Column(name = "FRIDGE_ID")
	private long id;
	
	@NotNull
	@Column(name = "ARDUINO")
	private String arduino;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID")
	private Store store;

	@Transient
	private List<SensorEvent> lastData;
	
	@Transient
	private long consume;
	
	public Fridge(String arduino) {
		this.arduino = arduino;
	}
	
	public Fridge() {
	}

	public Fridge(long id, String arduino, Store store) {
		super();
		this.id = id;
		this.arduino = arduino;
		this.store = store;
	}
	
	public Fridge(String arduino, Store store) {
		this.arduino = arduino;
		this.store = store;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<SensorEvent> getLastData() {
		return lastData;
	}

	public void setLastData(List<SensorEvent> lastData) {
		this.lastData = lastData;
	}

	public long getConsume() {
		return consume;
	}

	public void setConsume(long consume) {
		this.consume = consume;
	}

	@Override
	public String toString() {
		return "Fridge [id=" + id + ", arduino=" + arduino + ", store=" + store + ", lastData=" + lastData
				+ ", consume=" + consume + "]";
	}

	
}
