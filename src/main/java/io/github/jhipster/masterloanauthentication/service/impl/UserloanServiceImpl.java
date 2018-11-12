package io.github.jhipster.masterloanauthentication.service.impl;

import io.github.jhipster.masterloanauthentication.service.UserloanService;
import io.github.jhipster.masterloanauthentication.domain.Userloan;
import io.github.jhipster.masterloanauthentication.repository.UserloanRepository;
import io.github.jhipster.masterloanauthentication.repository.search.UserloanSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Userloan.
 */
@Service
@Transactional
public class UserloanServiceImpl implements UserloanService {

    private final Logger log = LoggerFactory.getLogger(UserloanServiceImpl.class);

    private final UserloanRepository userloanRepository;

    private final UserloanSearchRepository userloanSearchRepository;

    public UserloanServiceImpl(UserloanRepository userloanRepository, UserloanSearchRepository userloanSearchRepository) {
        this.userloanRepository = userloanRepository;
        this.userloanSearchRepository = userloanSearchRepository;
    }

    /**
     * Save a userloan.
     *
     * @param userloan the entity to save
     * @return the persisted entity
     */
    @Override
    public Userloan save(Userloan userloan) {
        log.debug("Request to save Userloan : {}", userloan);
        Userloan result = userloanRepository.save(userloan);
        userloanSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userloans.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Userloan> findAll() {
        log.debug("Request to get all Userloans");
        return userloanRepository.findAll();
    }


    /**
     * Get one userloan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Userloan> findOne(Long id) {
        log.debug("Request to get Userloan : {}", id);
        return userloanRepository.findById(id);
    }

    /**
     * Delete the userloan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Userloan : {}", id);
        userloanRepository.deleteById(id);
        userloanSearchRepository.deleteById(id);
    }

    /**
     * Search for the userloan corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Userloan> search(String query) {
        log.debug("Request to search Userloans for query {}", query);
        return StreamSupport
            .stream(userloanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
