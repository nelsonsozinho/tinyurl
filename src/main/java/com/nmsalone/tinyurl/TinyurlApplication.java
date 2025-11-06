package com.nmsalone.tinyurl;

import com.nmsalone.tinyurl.port.out.UrlRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories(basePackageClasses = UrlRepository.class)
public class TinyurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyurlApplication.class, args);
	}

}
