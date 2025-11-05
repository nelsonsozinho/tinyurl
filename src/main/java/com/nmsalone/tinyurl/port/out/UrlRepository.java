package com.nmsalone.tinyurl.port.out;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByTinyUrl(String tinyUrl);

    Optional<Url> findByOriginalUrl(String originalUrl);

}
