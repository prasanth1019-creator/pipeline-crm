package com.pipelinecrm.repository;

import com.pipelinecrm.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
