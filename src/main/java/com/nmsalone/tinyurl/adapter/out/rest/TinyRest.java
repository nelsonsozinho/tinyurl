package com.nmsalone.tinyurl.adapter.out.rest;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class TinyRest implements Serializable {

    private String originalUrl;

    private String tinyUrl;

}
