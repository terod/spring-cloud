package com.plighting.microservices;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableWebSecurity
@Profile("dev-basic")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().logout().logoutSuccessUrl("/bookmark").and()
				.authorizeRequests().antMatchers("/client-server/**")
				.access("hasRole('ROLE_BOOKMARK')")
		.and()
		.rememberMe()
		.and()
		.csrf()
		.csrfTokenRepository(csrfTokenRepository())
		.and()
		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		
			
		
		// .antMatchers("/client-server/resource/**").permitAll();
		// .and()
		// .requiresChannel()
		// .antMatchers("/bookmark/**").requiresSecure();
	}
	
	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if(csrfToken != null){
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrfToken.getToken();
					if(cookie == null || token != null && !token.equals(cookie.getValue())){
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository(){
		HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfTokenRepository.setHeaderName("X-XSRF-TOKEN");
		return csrfTokenRepository;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password")
				.roles("USER").and().withUser("george").password("password")
				.roles("BOOKMARK");
	}
}
