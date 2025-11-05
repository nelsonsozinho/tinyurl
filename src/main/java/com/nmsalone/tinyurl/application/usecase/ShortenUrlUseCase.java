package com.nmsalone.tinyurl.application.usecase;

import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;

public interface ShortenUrlUseCase {

    TinyRest shortenUrl(String originalUrl);

}
