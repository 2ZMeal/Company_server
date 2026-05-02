package com.ezmeal.company.application.dto.request;

public record CompanyCreateRequest(
        String name,
        String lotAddress,
        String roadAddress,
        String description
) {
}
