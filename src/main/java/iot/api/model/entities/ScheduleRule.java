package iot.api.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "schedule_rules")
public class ScheduleRule {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SCHEDULE_RULE_ID")
	private long id;
	
	@NotNull
	@Column(name = "INIT_HOUR")
	private long initHour;
	
	@NotNull
	@Column(name = "END_HOUR")
	private long endHour;
	
	@NotNull
	@Column(name = "SENSOR_ID")
	private long sensorId;
	
	@NotNull
	@Column(name = "TYPE")
	private long type;
	
	@NotNull
	@Column(name = "DEFAULT_VALUE")
	private long defaultValue;

	@NotNull
	@Column(name = "ENABLED")
	private boolean enabled;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInitHour() {
		return initHour;
	}

	public void setInitHour(long initHour) {
		this.initHour = initHour;
	}

	public long getEndHour() {
		return endHour;
	}

	public void setEndHour(long endHour) {
		this.endHour = endHour;
	}

	public long getSensorId() {
		return sensorId;
	}

	public void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(long defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "ScheduleRule [id=" + id + ", initHour=" + initHour + ", endHour=" + endHour + ", sensorId=" + sensorId
				+ ", type=" + type + ", defaultValue=" + defaultValue + ", enabled=" + enabled + "]";
	}
	

}
