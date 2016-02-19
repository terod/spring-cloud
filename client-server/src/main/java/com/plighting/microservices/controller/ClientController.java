package com.plighting.microservices.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.plighting.microservices.services.BookmarkService;

@Controller
@RequestMapping("/")
public class ClientController {

	@Autowired
	private BookmarkService bookmarkService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getLandingPage(ModelAndView modelAndView,
			HttpServletResponse httpResponse) {
		Object message = bookmarkService
				.getHelloFromBookmarkService(new HashMap<String, Object>());
		modelAndView.addObject("message", message);
		modelAndView.setViewName("hello");
		httpResponse.addHeader("xcorg", (String) message);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/booboo")
	public ModelAndView getBoo(ModelAndView modelAndView) {
		modelAndView.addObject("message", bookmarkService
				.getBooFromBookmarkService(new HashMap<String, Object>()));
		modelAndView.setViewName("boo");
		return modelAndView;
	}
}
