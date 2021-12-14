package com.myproject.myreadspreloaddata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.myproject.myreadspreloaddata.author.AuthorEntity;
import com.myproject.myreadspreloaddata.author.AuthorRepository;
import com.myproject.myreadspreloaddata.book.BookEntity;
import com.myproject.myreadspreloaddata.book.BookRepository;
import com.myproject.myreadspreloaddata.connection.DatastaxAstraConfigs;

import org.json.JSONArray;
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

	@Autowired
	BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyreadsPreloadDataApplication.class, args);
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
					author.setAuthorId(jsonObject.getString("key").replace("/authors/", ""));
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
		Path path = Paths.get(booksDumpLocation);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		try (Stream<String> lines = Files.lines(path)){
			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {
					JSONObject jsonObject = new JSONObject(jsonString);

					// Construct the book object
					BookEntity book = new BookEntity();
					book.setBookId(jsonObject.getString("key").replace("/works/", ""));
					book.setBookName(jsonObject.optString("title"));
					JSONObject descriptionObject = jsonObject.optJSONObject("description");
					if(descriptionObject != null){
						book.setBookDescription(descriptionObject.optString("value"));
					}
					JSONObject publishedObj = jsonObject.optJSONObject("created");
					if(publishedObj != null){
						book.setPublishedDate(LocalDate.parse(publishedObj.getString("value"),dateTimeFormatter));
					}
					JSONArray coversJson = jsonObject.optJSONArray("covers");
					if(coversJson != null){
						List<String> coversString = new ArrayList<>();
						for(int i=0; i<coversJson.length();i++){
							coversString.add(coversJson.getString(i));
						}
						book.setCoversIds(coversString);
					}
					
					JSONArray authorsJsonArray = jsonObject.optJSONArray("authors");
					if(authorsJsonArray != null){
						List<String> authorsIds = new ArrayList<>();
						//List<String> authorsNames = new ArrayList<>();
						for(int i=0;i<authorsJsonArray.length();i++){
							String authorId = authorsJsonArray
								.getJSONObject(i)
								.getJSONObject("author")
								.getString("key")
								.replace("/authors/", "");
							authorsIds.add(authorId);
							/* 
							// Look into the database for authors names
							AuthorEntity authorName = authorRepository.findByAuthorId(authorId);
							authorsNames.add(authorName.getAuthorName());
							*/
						}
						book.setAuthorsIds(authorsIds);

						// Look into the database for authors names (Better way)
						List<String> authorsNames = authorsIds.stream()
							.map(id -> authorRepository.findById(id))
							.map(optAuthor -> {
								if(!optAuthor.isPresent()){
									return "Unknown author";
								} else {
									return optAuthor.get().getAuthorName();
								}
							})
							.collect(Collectors.toList());
						book.setAuthorsNames(authorsNames);
					}

					//Persist the book object
					System.out.println("Inserting book with id :"+ book.getBookName() + " ...");
					bookRepository.save(book);
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



}
