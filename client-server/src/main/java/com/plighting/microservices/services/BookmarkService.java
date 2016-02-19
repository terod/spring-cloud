package com.plighting.microservices.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.plighting.microservices.remoteapi.BookmarkServiceRestCaller;
import com.plighting.microservices.remoteapi.BookmarkServiceRestCallerUsingFeign;

@Service
public class BookmarkService {

	@Autowired
	private BookmarkServiceRestCaller bookmarkServiceRestCaller;

	@Autowired
	private BookmarkServiceRestCallerUsingFeign bookmarkServiceRestCallerUsingFeign;

	@HystrixCommand(fallbackMethod = "defaultMessage")
	public Object getHelloFromBookmarkService(Map<String, Object> parameters) {

		return bookmarkServiceRestCaller
				.getHelloFromBookmarkService();

	}

	@HystrixCommand(fallbackMethod = "defaultMessage")
	public Object getBooFromBookmarkService(Map<String, Object> parameters) {

		return bookmarkServiceRestCallerUsingFeign.getBooMessage();

	}

	public Object defaultMessage(Map<String, Object> parameters) {
		return "nothing found hystrix circuit broke";
	}

}
