package com.myproject.myreadspreloaddata;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import connection.DatastaxAstraConfigs;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraConfigs.class)
public class MyreadsPreloadDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyreadsPreloadDataApplication.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastaxAstraConfigs astraConfigs){
		Path bundle = astraConfigs.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
