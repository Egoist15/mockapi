package com.mockapi.service;

import com.mockapi.entity.AddressEntity;
import com.mockapi.entity.EligibilityRequestEntity;
import com.mockapi.model.EligibilityRequest;
import com.mockapi.model.EligibilityResponse;
import com.mockapi.repository.EligibilityRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EligibilityService {

    @Value("${eligibility.address.url}")
    private String ADDRESS_API_URL;

    @Value("${eligibility.oauth.id}")
    private String OAUTH_ID;

    @Value("${eligibility.oauth.token}")
    private String OAUTH_TOKEN;

    private final RestClient restClient;
    private final EligibilityRequestRepository repository;

    public EligibilityService(RestClient restClient, EligibilityRequestRepository repository) {
        this.restClient = restClient;
        this.repository = repository;
    }

    public EligibilityResponse eligibilityCheck(EligibilityRequest request) {
        if (repository.findByRequestId(request.getRequestId()).isPresent()) {
            throw new IllegalArgumentException("Duplicate Request ID: " + request.getRequestId());
        }

        saveInitialRequest(request);

        EligibilityResponse response;
        if ("AGE_CHECK".equals(request.getEligibilityCheck())) {
            response = isAdult(request);
        } else if ("ADDRESS_CHECK".equals(request.getEligibilityCheck())) {
            response = isUSAAddress(request);
        } else {
            response = prepareResponse(false, "INVALID_CHECK_TYPE", "INVALID_CHECK_TYPE");
        }

        repository.updateEligibility(request.getRequestId(), response.isEligible(), response.getErrorCode());
        return response;
    }

    private void saveInitialRequest(EligibilityRequest request) {
        AddressEntity addressEntity = null;

        if ("ADDRESS_CHECK".equals(request.getEligibilityCheck())) {
            if (request.getAddress() == null) {
                throw new IllegalArgumentException("Address is required for ADDRESS_CHECK");
            }
            addressEntity = new AddressEntity(
                    request.getAddress().getStreet(),
                    request.getAddress().getCity(),
                    request.getAddress().getState(),
                    request.getAddress().getZipcode()
            );
        }
                EligibilityRequestEntity entity = new EligibilityRequestEntity(
                request.getClientId(),
                request.getRequestId(),
                request.getProductId(),
                request.getEligibilityCheck(),
                request.getDateOfBirth(),
                addressEntity
                );

        repository.save(entity);

    }

    private EligibilityResponse isAdult(EligibilityRequest request) {
        LocalDate dateOfBirth = request.getDateOfBirth();
        boolean eligible = dateOfBirth != null && Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
        String errorCode = eligible ? null : "AGE_ERROR";
        String errorDescription = eligible ? "VALID AGE" : "INVALID AGE";
        return prepareResponse(eligible, errorCode, errorDescription);
    }

    private EligibilityResponse isUSAAddress(EligibilityRequest request) {
        try {
            boolean isValidAddress = validateAddressWithThirdParty(request);

            if (isValidAddress) {
                return prepareResponse(true, null, "Valid USA Address");
            } else {
                return prepareResponse(false, "ADDRESS_INVALID", "Invalid USA Address");
            }
        } catch (Exception e) {
            return prepareResponse(false, "ADDRESS_API_ERROR", "Address validation service is unavailable");
        }
    }


    private boolean validateAddressWithThirdParty(EligibilityRequest request) {
        if (request.getAddress() == null) {
            return false;
        }

        String url = UriComponentsBuilder.fromUriString(ADDRESS_API_URL)
                .queryParam("auth-id", OAUTH_ID)
                .queryParam("auth-token", OAUTH_TOKEN)
                .queryParam("street", request.getAddress().getStreet())
                .queryParam("city", request.getAddress().getCity())
                .queryParam("state", request.getAddress().getState())
                .queryParam("zipcode", request.getAddress().getZipcode())
                .queryParam("candidates", "1")
                .queryParam("match", "enhanced")
                .queryParam("license", "us-rooftop-geocoding-cloud")
                .toUriString();

        try {
            String response = restClient.get().uri(url).retrieve().body(String.class);
            return response != null && response.contains("\"dpv_match_code\":\"Y\"");
        } catch (Exception e) {
            return false;
        }
    }

    private EligibilityResponse prepareResponse(boolean eligible, String errorCode, String errorDescription) {
        return new EligibilityResponse(
                eligible,
                errorCode,
                errorDescription
        );
    }
}
