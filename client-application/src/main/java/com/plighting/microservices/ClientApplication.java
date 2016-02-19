package com.plighting.microservices;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.plighting.microservices.util.EurekaClientUtil;
import com.plighting.microservices.util.RibbonUtil;

@SpringBootApplication
@EnableEurekaClient
@PropertySource("classpath:application.properties")
public class ClientApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ClientApplication.class);
		app.setWebEnvironment(false);
		ConfigurableApplicationContext ctx = app.run(args);

		/*for (String name : ctx.getBeanDefinitionNames())
			LOGGER.info(name);
		*/
		EurekaClientUtil clientUtil = ctx.getBean("eurekaClientUtil", EurekaClientUtil.class);
		clientUtil.getServiceInfo("bookmark_service");
		//clientUtil.sendRequestToServiceUsingEurekaBean();
		
		RibbonUtil clientUtil2 = ctx.getBean("ribbonUtil", RibbonUtil.class);
		LOGGER.info("First time : " +  clientUtil2.getServiceUrl("bookmark_service", "http://www.gmail.com"));
		LOGGER.info("Second time : " + clientUtil2.getServiceUrl("bookmark_service", "http://www.gmail.com"));
		LOGGER.info("Third time : " + clientUtil2.getServiceUrl("bookmark_service", "http://www.gmail.com"));
		
		sendRequest(clientUtil2.getServiceUrl("bookmark_service", "http://localhost:8090"));
		
	}

	private static void sendRequest(URI serviceUrl) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String requestString = serviceUrl + "/hello";
		HttpGet httpGet = new HttpGet(requestString);
		LOGGER.info("Request string :" + requestString);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String a = bufferedReader.readLine();
			LOGGER.info("Response is: " + a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
