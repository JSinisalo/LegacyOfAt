package com.hert.legacyofatbackend.db;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the Gusers.
 */
public interface GuserRepository extends CrudRepository<Guser, Long> {

    /**
     * Finds a Guser by its name.
     *
     * @param name the name
     * @return the guser
     */
    Guser findByName(String name);

    /**
     * Finds a Guser by its gId.
     *
     * @param gId the g id
     * @return the guser
     */
    Guser findBygId(String gId);
}