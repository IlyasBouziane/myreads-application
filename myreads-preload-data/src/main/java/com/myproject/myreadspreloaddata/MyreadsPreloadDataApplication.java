package com.myproject.myreadspreloaddata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.myproject.myreadspreloaddata.author.AuthorEntity;
import com.myproject.myreadspreloaddata.author.AuthorRepository;
import com.myproject.myreadspreloaddata.connection.DatastaxAstraConfigs;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(DatastaxAstraConfigs.class)
public class MyreadsPreloadDataApplication {

	@Value("${datadump.location.authors}")
	private String authorsDumpLocation;

	@Value("${datadump.location.books}")
	private String booksDumpLocation;

	@Autowired
	AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyreadsPreloadDataApplication.class, args);
	}

	private void initAuthors() {
		Path path = Paths.get(authorsDumpLocation).toAbsolutePath();
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> {
				// Read and parse each line
				String jsonString = line.substring(line.indexOf("{"));
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(jsonString);

					// Construct the author object
					AuthorEntity author = new AuthorEntity();
					author.setAuthorId(jsonObject.optString("key").replace("/authors/", ""));
					author.setAuthorName(jsonObject.optString("name"));
					author.setAuthorPersonalName(jsonObject.optString("personalName"));

					// Persist the object
					System.out.println("Saving the author : "+author.getAuthorName() +" ...");
					authorRepository.save(author);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initBooks() {

	}

	@PostConstruct
	public void fillDB() {
		// Initialize the persistance of authors data 
		initAuthors();
		// Initialize the persistance of books data
		initBooks();
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DatastaxAstraConfigs astraConfigs) {
		Path bundle = astraConfigs.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
