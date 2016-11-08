package iot.api.rule.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("")
@PropertySource("classpath:rules.properties")
public class RulesProperties {

	private Map<String, String> rule = new HashMap<String, String>();
	private Map<String, String> ruleschedule = new HashMap<String, String>();

	public Map<String, String> getRule() {
		return rule;
	}
	public void setRule(Map<String, String> rule) {
		this.rule = rule;
	}
	public Map<String, String> getRuleschedule() {
		return ruleschedule;
	}
	public void setRuleschedule(Map<String, String> ruleschedule){
		this.ruleschedule = ruleschedule;
	}
}
