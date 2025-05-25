package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold customer, account and loans information"
)
public class CustomerDetailsDto {
    @Schema(description = "Name of the customer", example = "EazyBytes")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 and 30")
    private String name;

    @Schema(description = "Email of the customer", example = "xH2mM@example.com")
    @NotEmpty(message = "Email cannot bu empty")
    @Email(message = "Email address is not valid")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "1234567890")
    @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number is not valid")
    private String mobileNumber;

    @Schema(description = "Account details")
    private AccountsDto accountsDto;

    @Schema(description = "Loan details")
    private LoanDto loansDto;

    @Schema(description = "Card details")
    private CardDto cardDto;
}
