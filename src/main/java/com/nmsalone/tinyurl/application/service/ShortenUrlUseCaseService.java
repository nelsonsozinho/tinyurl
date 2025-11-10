package com.nmsalone.tinyurl.application.service;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;
import com.nmsalone.tinyurl.application.usecase.ShortenUrlUseCase;
import com.nmsalone.tinyurl.domain.exception.UrlAlreadyExistException;
import com.nmsalone.tinyurl.port.out.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ShortenUrlUseCaseService implements ShortenUrlUseCase {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final UrlRepository repository;

    @Transactional
    @Override
    public TinyRest shortenUrl(String originalUrl) {
        Url newUrl = new Url();
        newUrl.setOriginalUrl(originalUrl);
        newUrl.setRegister(LocalDate.now());
        newUrl.setExpiring(LocalDate.now().plusYears(1));
        if(!hasUrlAlreadyRegister(originalUrl)) {
            Url urlSaved = repository.save(newUrl);
            urlSaved.setTinyUrl("/" + encodeBase62(urlSaved.getId()));
            repository.save(urlSaved);

            return TinyRest.builder()
                    .originalUrl(urlSaved.getOriginalUrl())
                    .tinyUrl(urlSaved.getTinyUrl())
                    .build();
        }

        throw new UrlAlreadyExistException(String.format("Url %s is already in our database", originalUrl));
    }

    private Boolean hasUrlAlreadyRegister(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl).isPresent();
    }

    public static String encodeBase62(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(BASE62.charAt(remainder));
            id /= 62;
        }
        return sb.reverse().toString();
    }
}
