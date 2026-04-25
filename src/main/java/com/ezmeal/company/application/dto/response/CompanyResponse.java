package com.ezmeal.company.application.dto.response;

import com.ezmeal.company.domain.model.Company;
import java.util.UUID;

public record CompanyResponse(
        UUID companyId,
        UUID managerUserId,
        String name,
        String lotAddress,
        String roadAddress,
        String description
) {
    public static CompanyResponse from(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getManagerUserId(),
                company.getName(),
                company.getLotAddress(),
                company.getRoadAddress(),
                company.getDescription()
        );

    }
}
