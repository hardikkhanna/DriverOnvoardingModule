package com.module.models;

// TODO: remove the enum values
public enum DocumentType {
    DRIVING_LICENSE("Driver License"),
    VEHICLE_REGISTRATION("Vehicle Registration"),
    INSURANCE("Insurance Certificate");

    private String documentType;

    DocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public static DocumentType findByKey(String text) {
        for (DocumentType b : DocumentType.values()) {
            if (b.documentType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
