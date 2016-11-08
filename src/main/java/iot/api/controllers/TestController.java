package iot.api.controllers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.rule.properties.ExecuteRules;
import iot.api.rule.properties.RulesProperties;


@Controller
public class TestController {

	@Autowired
	private ExecuteRules executeRules;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		System.out.println("Entra a Test!");
		
		executeRules.executeRules("","");
		
		return "Invoke Success!!";
	}

} 