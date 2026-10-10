package com.pipelinecrm.dto;

import com.pipelinecrm.model.Contact;

public record ContactResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Long companyId,
        String companyName) {

    public static ContactResponse from(Contact c) {
        return new ContactResponse(
                c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getPhone(),
                c.getCompany().getId(), c.getCompany().getName());
    }
}
