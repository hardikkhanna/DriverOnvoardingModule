package com.module.request;

import lombok.Data;

@Data
public class DocumentResponse {
    private String documentType;
    private String documentNumber;
    private String expirationDate;
}
