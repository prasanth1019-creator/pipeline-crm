package com.pipelinecrm.service;

import com.pipelinecrm.dto.DealRequest;
import com.pipelinecrm.dto.DealResponse;
import com.pipelinecrm.exception.ResourceNotFoundException;
import com.pipelinecrm.model.Contact;
import com.pipelinecrm.model.Deal;
import com.pipelinecrm.model.DealStage;
import com.pipelinecrm.repository.ContactRepository;
import com.pipelinecrm.repository.DealRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final ContactRepository contactRepository;

    public DealService(DealRepository dealRepository, ContactRepository contactRepository) {
        this.dealRepository = dealRepository;
        this.contactRepository = contactRepository;
    }

    @Transactional
    public DealResponse create(DealRequest request) {
        Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact not found with id " + request.contactId()));

        Deal deal = new Deal();
        deal.setTitle(request.title());
        deal.setAmount(request.amount());
        deal.setStage(request.stage() != null ? request.stage() : DealStage.NEW);
        deal.setContact(contact);
        return DealResponse.from(dealRepository.save(deal));
    }

    @Transactional(readOnly = true)
    public List<DealResponse> getAll() {
        return dealRepository.findAll().stream().map(DealResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DealResponse getById(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found with id " + id));
        return DealResponse.from(deal);
    }
}
