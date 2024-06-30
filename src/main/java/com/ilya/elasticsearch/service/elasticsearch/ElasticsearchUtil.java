package com.ilya.elasticsearch.service.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ElasticsearchUtil {

    private ElasticsearchUtil() {
    }

    public static SearchRequest buildSearchRequest(final String indexName, final ElasticsearchRequestDto dto) {
        return SearchRequest.of(
                sr -> sr.index(indexName)
                        .query(ElasticsearchUtil.getQuery(dto))
        );
    }

    public static Query getQuery(ElasticsearchRequestDto dto) {
        if (dto == null) return null;

        final List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)) return null;

        if (fields.size() > 1) {
            return Query.of(
                    q -> q.multiMatch(
                            m -> m.query(dto.getSearchTerm())
                                    .fields(fields)
                                    .type(TextQueryType.CrossFields)
                                    .operator(Operator.And)
                    )
            );
        }

        return fields.stream().findFirst().map(
                f -> Query.of(
                        q -> q.match(
                                m -> m.query(dto.getSearchTerm())
                                        .field(f)
                                        .operator(Operator.And)
                        )
                )
        ).orElse(null);
    }
}
