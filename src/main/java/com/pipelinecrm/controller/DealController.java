package com.pipelinecrm.controller;

import com.pipelinecrm.dto.DealRequest;
import com.pipelinecrm.dto.DealResponse;
import com.pipelinecrm.service.DealService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DealResponse create(@Valid @RequestBody DealRequest request) {
        return dealService.create(request);
    }

    @GetMapping
    public List<DealResponse> getAll() {
        return dealService.getAll();
    }

    @GetMapping("/{id}")
    public DealResponse getById(@PathVariable Long id) {
        return dealService.getById(id);
    }
}
