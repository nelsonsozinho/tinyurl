package com.nmsalone.tinyurl.adapter.out.persistence;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("tiny_url")
@Data
public class Url {

    @Id
    private ObjectId id;

    @Field(name="original_url")
    private String originalUrl;

    @Field(name="tiny_url")
    private String tinyUrl;

}
