package com.nmsalone.tinyurl.application.service;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;
import com.nmsalone.tinyurl.application.usecase.RecoverUrlUseCase;
import com.nmsalone.tinyurl.port.out.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoverUrlService implements RecoverUrlUseCase {

    private final UrlRepository repository;

    @Override
    @Cacheable(value = "tinyUrlCache", key = "#tinyUrl")
    public TinyRest findUrl(String tinyUrl) {
        final Url response = repository.findByTinyUrl(tinyUrl);
        return TinyRest.builder()
                .tinyUrl(response.getTinyUrl())
                .originalUrl(response.getOriginalUrl())
                .build();
    }


}
