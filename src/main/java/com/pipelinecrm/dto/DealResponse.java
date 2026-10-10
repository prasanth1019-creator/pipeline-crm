package com.pipelinecrm.dto;

import com.pipelinecrm.model.Deal;
import com.pipelinecrm.model.DealStage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealResponse(
        Long id,
        String title,
        BigDecimal amount,
        DealStage stage,
        Long contactId,
        String contactName,
        LocalDateTime createdAt) {

    public static DealResponse from(Deal d) {
        String name = d.getContact().getFirstName() + " " + d.getContact().getLastName();
        return new DealResponse(
                d.getId(), d.getTitle(), d.getAmount(), d.getStage(),
                d.getContact().getId(), name, d.getCreatedAt());
    }
}
