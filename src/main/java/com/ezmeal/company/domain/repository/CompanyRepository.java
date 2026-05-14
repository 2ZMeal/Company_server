package com.ezmeal.company.domain.repository;

import com.ezmeal.company.application.dto.request.CompanySearchRequest;
import com.ezmeal.company.domain.model.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepository {

    Company save(Company company);
    Optional<Company> findByIdAndDeletedAtIsNull(UUID companyId);
    Boolean existsByNameAndDeletedAtIsNull(String name);
    Page<Company> searchCompanies(CompanySearchRequest request, Pageable pageable);

    Optional<Company> findByManagerUserIdAndDeletedAtIsNull(UUID managerUserId);
}
