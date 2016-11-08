package iot.api.rule.properties;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.api.model.entities.Rule;
import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;

@Component
public class ExecuteRules {

	@Autowired
	private RulesProperties rules;

	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;

	public void executeRules(String sensor_id,String value) {

		Map<String, String> rulesMap = rules.getRule();
		int numberOfRules = rulesMap.size()/ 7 + 1;

		for (int i = 1; i < numberOfRules; i ++) {
			Rule rule = new Rule();
			rule.setName(rulesMap.get(i + ".name"));
			rule.setParamId(rulesMap.get(i + ".param.id.compare"));
			rule.setOperator(rulesMap.get(i + ".operator"));
			rule.setValueCompare(rulesMap.get(i + ".value.compare"));
			rule.setParamIdAction(rulesMap.get(i + ".param.id.action"));
			rule.setValueAction(rulesMap.get(i + ".value.action"));
			rule.setEnable(rulesMap.get(i + ".enable"));
			executeRule(rule,sensor_id,value);
		}

	}

	private void executeRule(Rule rule,String sensor_id,String value) {

		if (rule.getParamId().equals(sensor_id) && rule.getParamId().equals("true")){
			//Solo entra cuando el sensor correponde.
			switch(rule.getOperator()) {
			
			case "=":
				if (value == rule.getValueCompare()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //Si no aplica no hago nada
				break;
				
			case "<":
				if (rule.getValueCompare() == rule.getValueAction()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;
				
			case ">":
				if (rule.getValueCompare() == rule.getValueAction()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;
			}
		}

		System.out.println(rule);
	}
	
	private void send (String paramIdAction,String getValueAction) {
		
	}
}
