package io.github.jhipster.masterloanauthentication.repository;

import io.github.jhipster.masterloanauthentication.domain.Userloan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Userloan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserloanRepository extends JpaRepository<Userloan, Long> {

}
