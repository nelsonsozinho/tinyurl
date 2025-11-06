package com.nmsalone.tinyurl.application.service;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;
import com.nmsalone.tinyurl.application.usecase.ShortenUrlUseCase;
import com.nmsalone.tinyurl.domain.exception.UrlAlreadyExistException;
import com.nmsalone.tinyurl.port.out.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

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

    public static String encodeBase62(ObjectId id) {
        byte[] bytes = ObjectId.get().toByteArray();
        BigInteger bigInteger = new BigInteger(1, bytes);
        StringBuilder builder = new StringBuilder();
        while(bigInteger.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = bigInteger.divideAndRemainder(BigInteger.valueOf(62));
            builder.append(BASE62.charAt(divRem[1].intValue()));
            bigInteger = divRem[0];
        }
        return builder.reverse().toString();
    }
}
