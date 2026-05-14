package com.ezmeal.company.application.dto.response;

import com.ezmeal.company.domain.model.Company;
import java.util.UUID;

public record CompanyInfo(
        UUID companyId,
        String name,
        UUID managerUserId
) {
    public static CompanyInfo from(Company company) {
        return new CompanyInfo(
                company.getId(),
                company.getName(),
                company.getManagerUserId()
        );
    }
}
