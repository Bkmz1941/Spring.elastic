package com.ilya.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.*;
import com.ilya.elasticsearch.helper.Indices;
import com.ilya.elasticsearch.helper.Util;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class IndexService {
    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);
    private ElasticsearchClient elasticsearchClient;
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);

    @Autowired
    public IndexService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostConstruct
    public void tryToCreateIndices() throws IOException {
        final String settings = Util.loadAsString("/static/es-settings.json");

        for (final String indexName: INDICES_TO_CREATE) {
            try (InputStream json = new ClassPathResource("/static/mapping/" + indexName + ".json").getInputStream()) {
                boolean indexExists = elasticsearchClient.indices().exists(ExistsRequest.of(e -> e.index(indexName))).value();
                if (indexExists) continue;

                if (settings == null) {
                    LOG.error("Filed to create index with name {}", indexName);
                }

                final CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                        .index(indexName)
                        .withJson(json)
                        .build();

                elasticsearchClient.indices().create(createIndexRequest);
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
