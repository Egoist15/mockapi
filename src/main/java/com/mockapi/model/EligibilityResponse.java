package com.mockapi.model;

public class EligibilityResponse {


    private boolean eligible;
    private String errorCode;
    private String errorDescription;


    public EligibilityResponse(boolean eligible, String errorCode, String errorDescription) {
        this.eligible = eligible;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
