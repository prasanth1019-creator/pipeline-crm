package com.pipelinecrm.repository;

import com.pipelinecrm.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByNameIgnoreCase(String name);
}
