package com.ilya.elasticsearch.service.elasticsearch;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElasticsearchRequestDto {
    private List<String> fields;
    private String searchTerm;
}
