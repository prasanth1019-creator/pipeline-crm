package com.pipelinecrm.dto;

import com.pipelinecrm.model.Company;
import java.time.LocalDateTime;

public record CompanyResponse(
        Long id,
        String name,
        String industry,
        String website,
        LocalDateTime createdAt) {

    public static CompanyResponse from(Company c) {
        return new CompanyResponse(c.getId(), c.getName(), c.getIndustry(), c.getWebsite(), c.getCreatedAt());
    }
}
