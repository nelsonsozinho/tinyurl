package com.nmsalone.tinyurl.application.service;

import com.nmsalone.tinyurl.adapter.out.persistence.Url;
import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;
import com.nmsalone.tinyurl.port.out.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortenUrlUseCaseServiceTest {

    @Mock
    private UrlRepository repository;

    @InjectMocks
    private ShortenUrlUseCaseService service;

    @Test
    void shortenUrl_setsTinyUrl_and_savesTwice() {
        String original = "http://example.com";

        AtomicInteger callCount = new AtomicInteger();
        when(repository.save(any(Url.class))).thenAnswer(invocation -> {
            Url arg = invocation.getArgument(0);
            if (callCount.getAndIncrement() == 0) {
                // first save returns the entity with an id assigned by persistence
                Url saved = new Url();
                saved.setId(125L);
                saved.setOriginalUrl(arg.getOriginalUrl());
                return saved;
            } else {
                // second save returns the same object passed (after tinyUrl was set)
                return arg;
            }
        });

        TinyRest result = service.shortenUrl(original);

        assertNotNull(result);
        assertEquals(original, result.getOriginalUrl());
        String expectedTiny = "/" + ShortenUrlUseCaseService.encodeBase62(125L);
        assertEquals(expectedTiny, result.getTinyUrl());
        verify(repository, times(2)).save(any(Url.class));
    }

}
