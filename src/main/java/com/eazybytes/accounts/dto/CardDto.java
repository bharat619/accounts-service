package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardDto {
    @NotEmpty(message = "Mobile Number should not be empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number should be 10 digits")
    @Schema(description = "Mobile Number", example = "1234567890")
    private String mobileNumber;
    @NotEmpty(message = "Card Number should not be empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card Number should be 12 digits")
    @Schema(description = "Card Number", example = "123456789012")
    private String cardNumber;
    @NotEmpty(message = "Card Type should not be empty")
    @Schema(description = "Type of Card", example = "Credit")
    private String cardType;
    @Positive(message = "Total Limit should not be negative")
    @Schema(description = "Total Limit", example = "10000")
    private Integer totalLimit;
    @PositiveOrZero(message = "Amount Used should not be negative")
    private Integer amountUsed;
    @PositiveOrZero(message = "Available Amount should not be negative")
    private Integer availableAmount;
}
