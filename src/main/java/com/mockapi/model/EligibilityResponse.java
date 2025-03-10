package com.mockapi.model;

public class EligibilityResponse {

    private String clientId;
    private String requestId;
    private String productId;
    private String eligibilityCheck;
    private Boolean eligible;
    private String errorCode;


    public EligibilityResponse(String clientId, String requestId, String productId, String eligibilityCheck, Boolean eligible, String errorCode) {
        this.clientId = clientId;
        this.requestId = requestId;
        this.productId = productId;
        this.eligibilityCheck = eligibilityCheck;
        this.eligible = eligible;
        this.errorCode = errorCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEligibilityCheck() {
        return eligibilityCheck;
    }

    public void setEligibilityCheck(String eligibilityCheck) {
        this.eligibilityCheck = eligibilityCheck;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
