package com.ezmeal.company.application.dto.request;

public record CompanyUpdateRequest(
        String name,
        String lotAddress,
        String roadAddress,
        String description
) {
}
