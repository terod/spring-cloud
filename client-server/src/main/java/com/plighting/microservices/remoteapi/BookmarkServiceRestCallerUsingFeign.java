package com.plighting.microservices.remoteapi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("bookmark-service")
public interface BookmarkServiceRestCallerUsingFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/hello/boo")
	public String getBooMessage();
}
