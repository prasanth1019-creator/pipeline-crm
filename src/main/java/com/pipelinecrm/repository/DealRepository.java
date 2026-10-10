package com.pipelinecrm.repository;

import com.pipelinecrm.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
