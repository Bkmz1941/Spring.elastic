package com.ilya.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
//@Document(indexName = "person")
//@Setting(settingPath = "/static/es-settings.json")
public class Person {
    @Id
    @Field(type = FieldType.Keyword)
    public String id;

    @Field(type = FieldType.Text)
    public String name;
}
