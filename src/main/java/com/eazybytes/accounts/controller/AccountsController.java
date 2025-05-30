package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "CRUD API for Accounts in EazyBank", description = "CRUD API for Accounts in EazyBank")

public class AccountsController {
    private final IAccountService iAccountService;
    private  static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;

    @PostMapping("/create")
    @Operation(
            summary = "REST API to create Account",
            description = "REST API to create Account"
    )
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "REST API to fetch Account details",
            description = "REST API to fetch Account details"
    )
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "($|\\d{10})") String mobileNumber) {
        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
            @ApiResponse(responseCode = "417", description = "Account details not updated",
            content = @Content(schema =  @Schema(implementation = ErrorResponseDto.class))),
    })
    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Account details not deleted")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "($|\\d{10})") String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }

    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        logger.debug("getBuildInfo() invoked");
        throw new NullPointerException();
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String>  getBuildInfoFallback(Throwable throwable) {
        logger.debug("getBuildInfoFallback() invoked");
        return ResponseEntity.status(HttpStatus.OK).body("0,9");
    }

    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        System.out.println(environment.getProperty("MAVEN_HOME"));
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
    }

    @GetMapping("/contactInfo")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        System.out.println(environment.getProperty("MAVEN_HOME"));
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Java 17");
    }
}
