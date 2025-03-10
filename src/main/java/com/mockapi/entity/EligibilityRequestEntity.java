package com.mockapi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "eligibility")
public class EligibilityRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false, unique = true)
    private String requestId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String eligibilityCheck;

    @Column(nullable = true)
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(nullable = true)
    private Boolean eligible;

    @Column(nullable = true)
    private String errorCode;

    public EligibilityRequestEntity(String clientId, String requestId, String productId, String eligibilityCheck, LocalDate dateOfBirth, AddressEntity address) {
        this.clientId = clientId;
        this.requestId = requestId;
        this.productId = productId;
        this.eligibilityCheck = eligibilityCheck;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
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
