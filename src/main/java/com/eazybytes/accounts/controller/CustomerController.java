package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
@Tag(name = "CRUD API for Accounts in EazyBank", description = "CRUD API for Accounts in EazyBank")
public class CustomerController {
    private final ICustomerService iCustomerService;

    @GetMapping("/customer")
    @Operation(
            summary = "REST API to fetch Customer details",
            description = "REST API to fetch Customer details"
    )
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam  @Pattern(regexp = "($|\\d{10})") String mobileNumber) {
        CustomerDetailsDto customerDetailsDto =  iCustomerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDetailsDto);

    }
}
