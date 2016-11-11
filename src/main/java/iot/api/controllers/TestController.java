package iot.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iot.api.utility.ExecuteHelper;


@Controller
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	@Autowired
	private ExecuteHelper executeHelper;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		logger.info("[TestController] Entra a Test ......");
		
		
		return "Invoke Success!!";
	}

} 