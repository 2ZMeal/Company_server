package com.ezmeal.company.infrastructure.persistence.company;

import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaCompanyRepositoryCustom {
    Page<Company> searchCompanies(CompanySearchRequest request, Pageable pageable);
}
