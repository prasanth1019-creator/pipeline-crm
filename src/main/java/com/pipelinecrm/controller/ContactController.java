package com.pipelinecrm.controller;

import com.pipelinecrm.dto.ContactRequest;
import com.pipelinecrm.dto.ContactResponse;
import com.pipelinecrm.service.ContactService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponse create(@Valid @RequestBody ContactRequest request) {
        return contactService.create(request);
    }

    @GetMapping
    public List<ContactResponse> getAll() {
        return contactService.getAll();
    }

    @GetMapping("/{id}")
    public ContactResponse getById(@PathVariable Long id) {
        return contactService.getById(id);
    }
}
