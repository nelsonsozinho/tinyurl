package com.nmsalone.tinyurl.adapter.in.web;

import com.nmsalone.tinyurl.adapter.in.rest.TinyUrlRest;
import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;
import com.nmsalone.tinyurl.application.service.ShortenUrlUseCaseService;
import com.nmsalone.tinyurl.application.usecase.RecoverUrlUseCase;
import com.nmsalone.tinyurl.application.usecase.ShortenUrlUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/tiny", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TinyUrlController {

    private final ShortenUrlUseCase tinyService;
    private final RecoverUrlUseCase recoverService;

    @PostMapping
    public ResponseEntity<?> shortUrl(@RequestBody final TinyUrlRest urlRest) {
        TinyRest response = tinyService.shortenUrl(urlRest.getOriginalUrl());
        URI uri = URI.create("/tiny/" + response.getTinyUrl());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> decodeUrl(@PathVariable("shortUrl") final String shortUrl) {
        final TinyRest response = recoverService.findUrl("/" + shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(response.getOriginalUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


}
