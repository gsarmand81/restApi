package iot.api.rule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.api.model.entities.Rule;
import iot.api.model.entities.repositories.RuleRepository;
import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;
import iot.api.utility.Utility;

@Component
public class RulesExecutor {

	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private Utility utility;

	private static String  HOUR_FORMAT = "HH:mm";


	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");    

	public void executeRules(long sensor_id, String value) {

		List<Rule> rules = ruleRepository.findAll();
		for (Rule rule : rules) {
			executeRule(rule,sensor_id,value);
		}
	}

	private void executeRule(Rule rule, long sensor_id, String value) {

		logger.info("Execute Rule: " + rule + " sensor_id: " + sensor_id + " value: " + value);
		
		Double doubleValue = Double.parseDouble(value);

		//The rule is enabled and correspond to sensor_id
		if (rule.getParamId() == sensor_id && rule.isEnable() && workingHours(rule)){

			switch(rule.getOperator()) {

			case "=":
				logger.info("Executing Rule: doubleValue: " +
						doubleValue + " ==  rule.getValueCompare(): " + rule.getValueCompare());
				if (doubleValue == rule.getValueCompare()) {

					send(rule.getParamIdAction(),rule.getValueAction());
				} //Si no aplica no hago nada
				break;

			case "<":
				logger.info("Executing Rule: doubleValue: " +
						doubleValue + " <  rule.getValueCompare(): " + rule.getValueCompare());
				if (doubleValue < rule.getValueCompare()) {

					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;

			case ">":
				logger.info("Executing Rule: doubleValue: " +
						doubleValue + " >  rule.getValueCompare(): " + rule.getValueCompare());
				if (doubleValue > rule.getValueCompare()) {					
					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;
			}
		}

		System.out.println(rule);
	}

	private void send (long paramIdAction, long getValueAction) {

		//Publish only boolean values
		String stringValue = (getValueAction == 1)?"true":"false";
		String completeNameTopic = utility.getCompleteNameTopicPublish(paramIdAction);

		logger.info("Send stringValue: " + stringValue + " completeNameTopic: " + completeNameTopic);

		mqttClients.getMqttClient(completeNameTopic).mqttConnectNPublishNSubscribe(stringValue);

	}

	private boolean workingHours(Rule rule) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
		String hour = sdfHour.format(cal.getTime());

		System.out.println("ExecuteScheduler initHour: " + rule.getInitHour() +
				" endHour: " + rule.getEndHour() + " hour: " + hour);
		System.out.println("Validation: " + ((hour.compareTo(rule.getInitHour()) >= 0)
				&& (hour.compareTo(rule.getEndHour()) <= 0)));
		
		return ((hour.compareTo(rule.getInitHour()) >= 0)
				&& (hour.compareTo(rule.getEndHour()) <= 0));

	}
}
