package com.plighting.microservices.remoteapi;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class BookmarkServiceRestCaller {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookmarkServiceRestCaller.class);

	@Autowired
	// use the "smart" Eureka-aware RestTemplate
	private RestTemplate restTemplate;

	public Object getHelloFromBookmarkService() {

		ResponseEntity<String> exchange = this.restTemplate.exchange(
				"http://bookmark-service/hello", HttpMethod.GET, null,
				String.class);

		LOGGER.info("Response from bookmark service : " + exchange.getBody());
		return exchange.getBody().toString();

	}

}
