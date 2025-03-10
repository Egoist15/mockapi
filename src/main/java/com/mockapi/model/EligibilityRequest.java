package com.mockapi.model;

import java.time.LocalDate;

public class EligibilityRequest {

    private String clientId;
    private String requestId;
    private String productId;
    private String eligibilityCheck;
    private LocalDate dateOfBirth;
    private Address address;


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


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
