package iot.api.rule.properties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iot.api.model.entities.Rule;
import iot.api.model.entities.ScheduleRule;
import iot.api.model.entities.repositories.RuleRepository;
import iot.api.model.entities.repositories.ScheduleRuleRepository;
import iot.api.mqtt.MqttPublishSubscribeUtilityStorage;
import iot.api.utility.Utility;

@Component
public class RulesExecutor {

	@Autowired
	private MqttPublishSubscribeUtilityStorage mqttClients;

	@Autowired
	private RuleRepository ruleRepository;

	@Autowired
	private ScheduleRuleRepository scheduleRuleRepository;

	@Autowired
	private Utility utility;

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

    private static String  HOUR_FORMAT = "HH:mm";

	public String executeScheduleRules(long sensor_id, String value) {

		List<ScheduleRule> rules = scheduleRuleRepository.findAll();
		for (ScheduleRule scheduleRule : rules) {

			if (scheduleRule.getSensorId() == sensor_id && scheduleRule.isEnabled()){

				String initHour = Long.toString(scheduleRule.getInitHour());
				String endHour = Long.toString(scheduleRule.getEndHour());
								
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
		        String hour = sdfHour.format(cal.getTime());
		        
				logger.info("ExecuteScheduler initHour: " + initHour +
						" endHour: " + endHour + " hour: " + hour);
				
				if(((hour.compareTo(initHour) >= 0)
		                && (hour.compareTo(endHour) <= 0))) {
					return Long.toString(scheduleRule.getDefaultValue());
				}
			}
		}
		
		return null;
	}

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
		if (rule.getParamId() == sensor_id && rule.isEnable()){

			switch(rule.getOperator()) {

			case "=":
				logger.info("Executing Rule operator: doubleValue: " +
						doubleValue + " ==  rule.getValueCompare(): " + rule.getValueCompare());
				if (doubleValue == rule.getValueCompare()) {

					send(rule.getParamIdAction(),rule.getValueAction());
				} //Si no aplica no hago nada
				break;

			case "<":
				logger.info("Executing Rule operator: doubleValue: " +
						doubleValue + " <  rule.getValueCompare(): " + rule.getValueCompare());
				if (doubleValue < rule.getValueCompare()) {

					send(rule.getParamIdAction(),rule.getValueAction());
				} //
				break;

			case ">":
				logger.info("Executing Rule operator: doubleValue: " +
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
}
