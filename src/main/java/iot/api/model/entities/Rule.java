package iot.api.model.entities;

public class Rule {
	
	//TODO Crear tabla

	private String name;
	
	private String paramId;
	
	private String operator;
	
	private String valueCompare;
	
	private String paramIdAction;
	
	private String valueAction;
	
	private String enable;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValueCompare() {
		return valueCompare;
	}
	public void setValueCompare(String valueCompare) {
		this.valueCompare = valueCompare;
	}
	public String getParamIdAction() {
		return paramIdAction;
	}
	public void setParamIdAction(String paramIdAction) {
		this.paramIdAction = paramIdAction;
	}
	public String getValueAction() {
		return valueAction;
	}
	public void setValueAction(String valueAction) {
		this.valueAction = valueAction;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	@Override
	public String toString() {
		return "Rule [name=" + name + ", paramId=" + paramId + ", operator=" + operator + ", valueCompare="
				+ valueCompare + ", paramIdAction=" + paramIdAction + ", valueAction=" + valueAction + ", enable="
				+ enable + "]";
	}
	
}


