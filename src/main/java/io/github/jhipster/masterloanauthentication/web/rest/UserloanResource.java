package io.github.jhipster.masterloanauthentication.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.masterloanauthentication.domain.Userloan;
import io.github.jhipster.masterloanauthentication.service.UserloanService;
import io.github.jhipster.masterloanauthentication.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.masterloanauthentication.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Userloan.
 */
@RestController
@RequestMapping("/api")
public class UserloanResource {

    private final Logger log = LoggerFactory.getLogger(UserloanResource.class);

    private static final String ENTITY_NAME = "masterLoanAuthenticationUserloan";

    private final UserloanService userloanService;

    public UserloanResource(UserloanService userloanService) {
        this.userloanService = userloanService;
    }

    /**
     * POST  /userloans : Create a new userloan.
     *
     * @param userloan the userloan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userloan, or with status 400 (Bad Request) if the userloan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/userloans")
    @Timed
    public ResponseEntity<Userloan> createUserloan(@Valid @RequestBody Userloan userloan) throws URISyntaxException {
        log.debug("REST request to save Userloan : {}", userloan);
        if (userloan.getId() != null) {
            throw new BadRequestAlertException("A new userloan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Userloan result = userloanService.save(userloan);
        return ResponseEntity.created(new URI("/api/userloans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userloans : Updates an existing userloan.
     *
     * @param userloan the userloan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userloan,
     * or with status 400 (Bad Request) if the userloan is not valid,
     * or with status 500 (Internal Server Error) if the userloan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/userloans")
    @Timed
    public ResponseEntity<Userloan> updateUserloan(@Valid @RequestBody Userloan userloan) throws URISyntaxException {
        log.debug("REST request to update Userloan : {}", userloan);
        if (userloan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Userloan result = userloanService.save(userloan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userloan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userloans : get all the userloans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userloans in body
     */
    @GetMapping("/userloans")
    @Timed
    public List<Userloan> getAllUserloans() {
        log.debug("REST request to get all Userloans");
        return userloanService.findAll();
    }

    /**
     * GET  /userloans/:id : get the "id" userloan.
     *
     * @param id the id of the userloan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userloan, or with status 404 (Not Found)
     */
    @GetMapping("/userloans/{id}")
    @Timed
    public ResponseEntity<Userloan> getUserloan(@PathVariable Long id) {
        log.debug("REST request to get Userloan : {}", id);
        Optional<Userloan> userloan = userloanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userloan);
    }

    /**
     * DELETE  /userloans/:id : delete the "id" userloan.
     *
     * @param id the id of the userloan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/userloans/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserloan(@PathVariable Long id) {
        log.debug("REST request to delete Userloan : {}", id);
        userloanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/userloans?query=:query : search for the userloan corresponding
     * to the query.
     *
     * @param query the query of the userloan search
     * @return the result of the search
     */
    @GetMapping("/_search/userloans")
    @Timed
    public List<Userloan> searchUserloans(@RequestParam String query) {
        log.debug("REST request to search Userloans for query {}", query);
        return userloanService.search(query);
    }

}
