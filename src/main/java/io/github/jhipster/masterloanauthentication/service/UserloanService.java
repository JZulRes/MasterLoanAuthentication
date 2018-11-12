package io.github.jhipster.masterloanauthentication.service;

import io.github.jhipster.masterloanauthentication.domain.Userloan;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Userloan.
 */
public interface UserloanService {

    /**
     * Save a userloan.
     *
     * @param userloan the entity to save
     * @return the persisted entity
     */
    Userloan save(Userloan userloan);

    /**
     * Get all the userloans.
     *
     * @return the list of entities
     */
    List<Userloan> findAll();


    /**
     * Get the "id" userloan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Userloan> findOne(Long id);

    /**
     * Delete the "id" userloan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userloan corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Userloan> search(String query);
}
