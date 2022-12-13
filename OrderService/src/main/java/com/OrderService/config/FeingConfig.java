package com.OrderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.OrderService.external.decoder.CustomeErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class FeingConfig {
	
	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomeErrorDecoder();
	}

}
