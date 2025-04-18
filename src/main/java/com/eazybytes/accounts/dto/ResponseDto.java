package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(description = "Response object", name = "Response Object")
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}
