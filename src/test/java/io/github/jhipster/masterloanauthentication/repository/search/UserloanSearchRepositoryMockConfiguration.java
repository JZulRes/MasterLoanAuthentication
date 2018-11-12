package io.github.jhipster.masterloanauthentication.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UserloanSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UserloanSearchRepositoryMockConfiguration {

    @MockBean
    private UserloanSearchRepository mockUserloanSearchRepository;

}
