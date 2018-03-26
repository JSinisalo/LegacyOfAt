package com.hert.legacyofatbackend.db;

import org.springframework.data.repository.CrudRepository;

public interface GuserRepository extends CrudRepository<Guser, Long> {

    Guser findByName(String name);
    Guser findBygId(String gId);
}