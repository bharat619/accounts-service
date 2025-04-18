package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account details"
)
public class AccountsDto {
    @Schema(description = "Account number", example = "1234567890")
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "($|\\d{10})", message = "Account number is not valid")
    private Long accountNumber;

    @NotEmpty
    @Schema(description = "Account type", example = "Savings")
    private String accountType;

    @NotEmpty
    @Schema(description = "Branch address", example = "Pune")
    private String branchAddress;
}
