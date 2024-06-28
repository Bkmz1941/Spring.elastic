package com.ilya.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(ElasticsearchApplication.class, args);
		System.out.println("Started");
	}

}
