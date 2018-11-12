package io.github.jhipster.masterloanauthentication.repository.search;

import io.github.jhipster.masterloanauthentication.domain.Userloan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Userloan entity.
 */
public interface UserloanSearchRepository extends ElasticsearchRepository<Userloan, Long> {
}
