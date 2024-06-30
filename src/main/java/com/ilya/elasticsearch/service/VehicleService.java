package com.ilya.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilya.elasticsearch.document.Vehicle;
import com.ilya.elasticsearch.helper.Indices;
import com.ilya.elasticsearch.service.elasticsearch.ElasticsearchRequestDto;
import com.ilya.elasticsearch.service.elasticsearch.ElasticsearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VehicleService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private ElasticsearchClient elasticsearchClient;
    private static final Logger LOG = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    public VehicleService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<Vehicle> search(ElasticsearchRequestDto dto) {
        return this.searchInternal(ElasticsearchUtil.buildSearchRequest(Indices.VEHICLE_INDEX, dto));
    }

    private List<Vehicle> searchInternal(SearchRequest request) {
        try {
            if (request == null) {
                LOG.error("Failed to build search request");
                return Collections.emptyList();
            }
            final SearchResponse<Vehicle> response = elasticsearchClient.search(request, Vehicle.class);
            List<Hit<Vehicle>> searchHits = response.hits().hits();
            final List<Vehicle> vehicles = new ArrayList<>();
            for (Hit<Vehicle> hit : searchHits) {
                vehicles.add(hit.source());
            }
            return vehicles;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Boolean index(final Vehicle vehicle) {
        try {
            String vehicleAsString = MAPPER.writeValueAsString(vehicle);

            IndexRequest<JsonData> request = IndexRequest.of(b -> b
                    .id(vehicle.getId())
                    .withJson(new StringReader(vehicleAsString))
                    .index(Indices.VEHICLE_INDEX)
            );
            IndexResponse response = elasticsearchClient.index(request);
            return response != null && response.result().equals(Result.Created);
//            return false;
        } catch (Throwable throwable) {
            LOG.error(throwable.getMessage(), throwable);
            return false;
        }
    }

    public Vehicle getById(final String vehicleId) {
        try {
            GetResponse<Vehicle> response = elasticsearchClient.get(
                    GetRequest.of(e -> e
                            .index(Indices.VEHICLE_INDEX)
                            .id(vehicleId)
                    ),
                    Vehicle.class
            );
            if (!response.found()) {
                return null;
            }
            return response.source();
        } catch (Throwable throwable) {
            LOG.error(throwable.getMessage(), throwable);
            return null;
        }
    }
}
