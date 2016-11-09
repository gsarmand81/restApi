package iot.api.rule.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.api.model.entities.Rule;
import iot.api.model.entities.repositories.RuleRepository;
import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;

@Component
public class RulesExecutor {
	
	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;

	@Autowired
	private RuleRepository ruleRepository;
	
	public void executeRules(long sensor_id,long value) {

		List<Rule> rules = ruleRepository.findAll();
		for (Rule rule : rules) {
			executeRule(rule,sensor_id,value);
		}
	}

	private void executeRule(Rule rule, long sensor_id,long value) {

		//The rule is enabled and correspond to sensor_id
		if (rule.getParamId() == sensor_id && rule.isEnable()){

			switch(rule.getOperator()) {
			
			case "=":
				if (value == rule.getValueCompare()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //Si no aplica no hago nada
				break;
				
			case "<":
				if (rule.getValueCompare() > rule.getValueAction()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;
				
			case ">":
				if (rule.getValueCompare() < rule.getValueAction()) {
					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;
			}
		}

		System.out.println(rule);
	}
	
	private void send (long paramIdAction, long getValueAction) {
		
		mqttClients.getMqttClient("").mqttConnectNPublishNSubscribe("");
		
	}
}
