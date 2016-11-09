package iot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.rule.RulesExecutor;


@Controller
public class TestController {

	@Autowired
	private RulesExecutor executeRules;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		System.out.println("Entra a Test!");
		
		executeRules.executeRules(0l,"1200");
		
		return "Invoke Success!!";
	}

} 