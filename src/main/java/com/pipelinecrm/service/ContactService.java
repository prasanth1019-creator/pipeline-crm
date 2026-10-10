package com.pipelinecrm.service;

import com.pipelinecrm.dto.ContactRequest;
import com.pipelinecrm.dto.ContactResponse;
import com.pipelinecrm.exception.DuplicateResourceException;
import com.pipelinecrm.exception.ResourceNotFoundException;
import com.pipelinecrm.model.Company;
import com.pipelinecrm.model.Contact;
import com.pipelinecrm.repository.CompanyRepository;
import com.pipelinecrm.repository.ContactRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public ContactResponse create(ContactRequest request) {
        if (contactRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Contact already exists with email " + request.email());
        }
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with id " + request.companyId()));

        Contact contact = new Contact();
        contact.setFirstName(request.firstName());
        contact.setLastName(request.lastName());
        contact.setEmail(request.email());
        contact.setPhone(request.phone());
        contact.setCompany(company);
        return ContactResponse.from(contactRepository.save(contact));
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> getAll() {
        return contactRepository.findAll().stream().map(ContactResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ContactResponse getById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + id));
        return ContactResponse.from(contact);
    }
}
