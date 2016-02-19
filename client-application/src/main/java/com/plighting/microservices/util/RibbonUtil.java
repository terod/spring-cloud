package com.plighting.microservices.util;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

@Component
public class RibbonUtil {
	private static final Logger LOG = LoggerFactory.getLogger(RibbonUtil.class);

	@Autowired
	private LoadBalancerClient loadBalancer;

	public URI getServiceUrl(String serviceId, String fallbackUri) {
		URI uri = null;
		try {
			ServiceInstance instance = loadBalancer.choose(serviceId);
			uri = instance.getUri();
			LOG.debug("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

		} catch (RuntimeException e) {
			// Eureka not available, use fallback
			uri = URI.create(fallbackUri);
			LOG.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", serviceId, uri);
		}

		return uri;
	}

}
