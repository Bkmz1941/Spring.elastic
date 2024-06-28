package com.ilya.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
public class Vehicle {
    @Id
    @Field(type = FieldType.Keyword)
    public String id;

    @Field(type = FieldType.Integer)
    public String number;
}
