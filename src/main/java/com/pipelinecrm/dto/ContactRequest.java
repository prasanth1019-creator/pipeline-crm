package com.pipelinecrm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContactRequest(
        @NotBlank(message = "firstName is required") String firstName,
        @NotBlank(message = "lastName is required") String lastName,
        @NotBlank(message = "email is required") @Email(message = "email is not valid") String email,
        String phone,
        @NotNull(message = "companyId is required") Long companyId) {
}
