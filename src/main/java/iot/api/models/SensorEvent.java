package iot.api.models;

import java.util.Date;

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
@Table(name = "sensor_events")
public class SensorEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private long id;
	
	@NotNull
	@Column(name = "TIMESTAMP")
	private Date timestamp;		
	
	@NotNull
	@Column(name = "VALUE")
	private long value;
	
	@ManyToOne
	@JoinColumn(name = "FRIDGE_ID")
	private Fridge fridge;

	@ManyToOne
	@JoinColumn(name = "SENSOR_ID")
	private Sensor sensor;
	
	public SensorEvent() {
	
	}
	
	public SensorEvent(Fridge fridge, Sensor sensor) {
		this.fridge = fridge;
		this.sensor = sensor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public Fridge getFridge() {
		return fridge;
	}

	public void setFridge(Fridge fridge) {
		this.fridge = fridge;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
}
