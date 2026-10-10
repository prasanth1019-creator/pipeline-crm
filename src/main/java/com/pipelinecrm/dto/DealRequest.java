package com.pipelinecrm.dto;

import com.pipelinecrm.model.DealStage;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DealRequest(
        @NotBlank(message = "title is required") String title,
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.0", message = "amount cannot be negative") BigDecimal amount,
        DealStage stage,
        @NotNull(message = "contactId is required") Long contactId) {
}
