package com.nmsalone.tinyurl.application.usecase;

import com.nmsalone.tinyurl.adapter.out.rest.TinyRest;

public interface RecoverUrlUseCase {

    TinyRest findUrl(String tinyUrl);

}
