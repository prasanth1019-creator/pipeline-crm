package com.pipelinecrm.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
        @NotBlank(message = "name is required") String name,
        String industry,
        String website) {
}
