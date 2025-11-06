package com.nmsalone.tinyurl.port.out;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<Url, Long> {

    Url findByTinyUrl(String tinyUrl);

    Optional<Url> findByOriginalUrl(String originalUrl);

}
