package com.geo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geo.bo.Hello;

@RestController
@RequestMapping("/hello")
public class HelloRestController {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(HelloRestController.class);
    
	@Value("${boo}")
	private String booMessage;
	
	@Autowired
	private Hello hello;
	
	@RequestMapping(method = RequestMethod.GET)
	private String sayHello(){
		LOGGER.info("GET / returns " + hello.getMessage());
		return hello.getMessage();
	}
	
	@RequestMapping(path="/boo",method = RequestMethod.GET)
	private String sayBoo(){
		LOGGER.info("GET / returns boo");
		return booMessage;
	}
}
