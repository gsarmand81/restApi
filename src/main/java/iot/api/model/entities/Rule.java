package iot.api.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rules")
public class Rule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RULE_ID")
	private long id;

	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@NotNull
	@Column(name = "PARAM_ID")
	private long paramId;
	
	@NotNull
	@Column(name = "OPERATOR")
	private String operator;
	
	@NotNull
	@Column(name = "VALUE_COMPARE")
	private long valueCompare;
	
	@NotNull
	@Column(name = "PARAM_ID_ACTION")
	private long paramIdAction;
	
	@NotNull
	@Column(name = "VALUE_ACTION")
	private long valueAction;
	
	@NotNull
	@Column(name = "ENABLE")
	private boolean enable;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getParamId() {
		return paramId;
	}


	public void setParamId(long paramId) {
		this.paramId = paramId;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public long getValueCompare() {
		return valueCompare;
	}


	public void setValueCompare(long valueCompare) {
		this.valueCompare = valueCompare;
	}


	public long getParamIdAction() {
		return paramIdAction;
	}


	public void setParamIdAction(long paramIdAction) {
		this.paramIdAction = paramIdAction;
	}


	public long getValueAction() {
		return valueAction;
	}


	public void setValueAction(long valueAction) {
		this.valueAction = valueAction;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}
			
	@Override
	public String toString() {
		return "Rule [id=" + id + ", name=" + name + ", paramId=" + paramId + ", operator=" + operator
				+ ", valueCompare=" + valueCompare + ", paramIdAction=" + paramIdAction + ", valueAction=" + valueAction
				+ ", enable=" + enable + "]";
	}
}


