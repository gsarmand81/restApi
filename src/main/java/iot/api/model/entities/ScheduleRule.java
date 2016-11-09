package iot.api.model.entities;

import java.sql.Date;

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
	@Column(name = "INIT_DATE")
	private Date initDate;
	
	@NotNull
	@Column(name = "END_DATE")
	private Date endDate;
	
	@NotNull
	@Column(name = "SENSOR_ID")
	private long sensorId;
	
	@NotNull
	@Column(name = "TYPE")
	private long type;
	
	@NotNull
	@Column(name = "DEFAULT_VALUE")
	private long defaultValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	@Override
	public String toString() {
		return "ScheduleRule [id=" + id + ", initDate=" + initDate + ", endDate=" + endDate + ", sensorId=" + sensorId
				+ ", type=" + type + ", defaultValue=" + defaultValue + "]";
	}

}
