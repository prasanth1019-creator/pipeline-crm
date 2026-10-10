package com.pipelinecrm.service;

import com.pipelinecrm.dto.CompanyRequest;
import com.pipelinecrm.dto.CompanyResponse;
import com.pipelinecrm.exception.DuplicateResourceException;
import com.pipelinecrm.exception.ResourceNotFoundException;
import com.pipelinecrm.model.Company;
import com.pipelinecrm.repository.CompanyRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
        if (companyRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Company already exists: " + request.name());
        }
        Company company = new Company(request.name(), request.industry(), request.website());
        return CompanyResponse.from(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getAll() {
        return companyRepository.findAll().stream().map(CompanyResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
        return CompanyResponse.from(company);
    }
}
