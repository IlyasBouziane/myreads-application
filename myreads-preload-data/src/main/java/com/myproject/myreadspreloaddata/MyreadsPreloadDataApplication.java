package com.myproject.myreadspreloaddata;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import com.myproject.myreadspreloaddata.author.AuthorEntity;
import com.myproject.myreadspreloaddata.author.AuthorRepository;
import com.myproject.myreadspreloaddata.connection.DatastaxAstraConfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraConfigs.class)
public class MyreadsPreloadDataApplication {

	@Autowired
	 AuthorRepository authorRepository;
	public static void main(String[] args) {
		SpringApplication.run(MyreadsPreloadDataApplication.class, args);
	}

	@PostConstruct
	public void createDB(){
		AuthorEntity authorEntity = new AuthorEntity();
		authorEntity.setAuthorId("id");
		authorEntity.setAuthorName("name");
		authorEntity.setAuthorPersonalName("personalName");
		AuthorEntity obj= authorRepository.save(authorEntity);
		System.out.println("Helloooooooo "+obj);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastaxAstraConfigs astraConfigs){
		Path bundle = astraConfigs.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
