package com.afklm.cargo.cgoor.components;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.sklsft.commons.log.AccessLogger;
import org.sklsft.commons.rest.client.RestClient;
import org.sklsft.commons.rest.client.exception.ErrorReportHandler;
import org.sklsft.commons.rest.client.interceptors.CorrelationIdInterceptor;
import org.sklsft.commons.rest.client.interceptors.RestClientLoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestClientConfig {
	
	@Inject
	private Environment env;
	
	@Inject
	private AccessLogger accessLogger;
	
	@Inject
	private ObjectMapper objectMapper;
	
	@Bean
	public SimpleClientHttpRequestFactory requestFactory() {
		return new SimpleClientHttpRequestFactory();
	}
	
	@Bean
	public BufferingClientHttpRequestFactory bufferedHttpRequestFactory() {
		return new BufferingClientHttpRequestFactory(requestFactory());
	}
	
	@Bean
	public ErrorReportHandler errorReportHandler() {
		ErrorReportHandler result = new ErrorReportHandler();
		result.setObjectMapper(objectMapper);
		return result;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate result = new RestTemplate(); 
		result.setErrorHandler(errorReportHandler());
		result.setRequestFactory(bufferedHttpRequestFactory());
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new CorrelationIdInterceptor());
		
		RestClientLoggerInterceptor loggerInterceptor = new RestClientLoggerInterceptor();
		loggerInterceptor.setAccessLogger(accessLogger);
		loggerInterceptor.setTraceSentBody(env.getRequiredProperty("accessLog.traceSentBody", Boolean.class));
		loggerInterceptor.setTraceReceivedBody(env.getRequiredProperty("accessLog.traceReceivedBody", Boolean.class));
		interceptors.add(loggerInterceptor);
		
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
		
		result.setInterceptors(interceptors);
		
		return result;
	}
	
	@Bean
	public RestClient cgoawbRestClient() {
		RestClient result = new RestClient();
		result.setRestServerUrl(env.getRequiredProperty("restServer.url"));
		result.setRestTemplate(restTemplate());
		return result;
	}
	
	
}